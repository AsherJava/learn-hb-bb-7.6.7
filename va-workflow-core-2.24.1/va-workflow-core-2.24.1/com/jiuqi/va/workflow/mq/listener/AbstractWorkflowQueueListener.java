/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.trans.service.VaBizErrorService
 */
package com.jiuqi.va.workflow.mq.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.trans.service.VaBizErrorService;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.service.WorkflowCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractWorkflowQueueListener
implements JoinListener {
    private static final Logger log = LoggerFactory.getLogger(AbstractWorkflowQueueListener.class);
    protected final String PRE_KEY = "processInstanceId:";
    @Autowired
    protected ModelDefineService modelDefineService;
    @Autowired
    protected WorkflowCommentService workflowCommentService;
    @Autowired
    protected VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    protected VaBizErrorService vaBizErrorService;
    @Autowired
    protected WorkflowRetractLockService retractLockService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ReplyTo onMessage(String message) {
        String messageId = null;
        String globMsgId = null;
        try {
            ObjectNode param = JSONUtil.parseObject((String)message);
            messageId = param.get("msgId").textValue();
            globMsgId = param.get("globMsgId").textValue();
            String tenantName = param.get("SECURITY_TENANT_KEY").textValue();
            ShiroUtil.ignoreApiAuth();
            ShiroUtil.bindTenantName((String)tenantName);
            this.bindUser(param);
            R r = this.doMessage(param, messageId);
            ReplyTo replyTo = new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)r));
            return replyTo;
        }
        catch (WorkflowException e) {
            log.error("\u5de5\u4f5c\u6d41\u6d88\u606f{}\u5904\u7406\u5f02\u5e38", (Object)messageId, (Object)e);
            this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
            ReplyTo replyTo = new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.error((String)e.getMessage())));
            return replyTo;
        }
        catch (Exception e) {
            log.error("\u5de5\u4f5c\u6d41\u6d88\u606f{}\u5904\u7406\u5f02\u5e38", (Object)messageId, (Object)e);
            this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
            ReplyTo replyTo = new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.error((String)e.getMessage())));
            return replyTo;
        }
        finally {
            ShiroUtil.unbindTenantName();
            ShiroUtil.resetApiAuth();
            ShiroUtil.unbindUser();
        }
    }

    protected abstract R doMessage(ObjectNode var1, String var2) throws InterruptedException;

    protected WorkflowModel getModel(String uniqueCode, Long processDefineVersion) {
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
        WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        return workflowModel;
    }

    private UserLoginDTO bindUser(ObjectNode param) {
        JsonNode userObject = param.get("loginUser");
        UserLoginDTO user = null;
        if (userObject != null) {
            user = (UserLoginDTO)JSONUtil.parseObject((String)userObject.textValue(), UserLoginDTO.class);
            ShiroUtil.unbindUser();
            ShiroUtil.bindUser((UserLoginDTO)user);
        }
        return user;
    }
}

