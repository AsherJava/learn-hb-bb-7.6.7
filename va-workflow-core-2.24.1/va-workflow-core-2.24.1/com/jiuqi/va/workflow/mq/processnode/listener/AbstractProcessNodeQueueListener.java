/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.trans.service.VaBizErrorService
 */
package com.jiuqi.va.workflow.mq.processnode.listener;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.trans.service.VaBizErrorService;
import com.jiuqi.va.workflow.mq.processnode.listener.VaWorkflowProcessNodeQueueMsgHandler;
import java.text.ParseException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public abstract class AbstractProcessNodeQueueListener
implements JoinListener {
    private static final Logger log = LoggerFactory.getLogger(AbstractProcessNodeQueueListener.class);
    @Autowired
    protected VaWorkflowProcessNodeQueueMsgHandler vaWorkflowProcessNodeQueueMsgHandler;
    @Autowired
    protected VaBizErrorService vaBizErrorService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ReplyTo onMessage(String message) {
        String messageId = null;
        String globMsgId = null;
        try {
            Map param = JSONUtil.parseMap((String)message);
            messageId = param.get("msgId").toString();
            globMsgId = param.get("globMsgId").toString();
            String tenantName = param.get("SECURITY_TENANT_KEY").toString();
            ShiroUtil.ignoreApiAuth();
            ShiroUtil.bindTenantName((String)tenantName);
            this.bindUser(param);
            this.doMessage(param);
            ReplyTo replyTo = new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.ok()));
            return replyTo;
        }
        catch (Exception e) {
            log.error("\u6d88\u606f{}\u6d41\u7a0b\u8f68\u8ff9\u5904\u7406\u5931\u8d25", (Object)messageId, (Object)e);
            if (StringUtils.hasText(globMsgId) && StringUtils.hasText(messageId)) {
                this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
            }
            ReplyTo replyTo = new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.error((String)e.getMessage())));
            return replyTo;
        }
        finally {
            ShiroUtil.unbindTenantName();
            ShiroUtil.resetApiAuth();
            ShiroUtil.unbindUser();
        }
    }

    protected abstract void doMessage(Map<String, Object> var1) throws ParseException;

    private UserLoginDTO bindUser(Map<String, Object> param) {
        Object userObject = param.get("loginUser");
        UserLoginDTO user = null;
        if (userObject != null) {
            user = (UserLoginDTO)JSONUtil.parseObject((String)userObject.toString(), UserLoginDTO.class);
            ShiroUtil.unbindUser();
            ShiroUtil.bindUser((UserLoginDTO)user);
        }
        return user;
    }
}

