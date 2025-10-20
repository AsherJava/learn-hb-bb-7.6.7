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
package com.jiuqi.va.bill.mq.listener;

import com.jiuqi.va.bill.mq.listener.VaBillQueueMsgHandler;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.trans.service.VaBizErrorService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public abstract class AbstractBillAfterQueueListener
implements JoinListener {
    private static final Logger log = LoggerFactory.getLogger(AbstractBillAfterQueueListener.class);
    protected String joinName;
    protected int maxConsumers;
    protected VaBillQueueMsgHandler vaBillQueueMsgHandler;
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
            UserLoginDTO user = (UserLoginDTO)JSONUtil.parseObject((String)param.get("loginUser").toString(), UserLoginDTO.class);
            ShiroUtil.unbindUser();
            ShiroUtil.bindUser((UserLoginDTO)user);
            this.doMessage(param);
            ReplyTo replyTo = new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.ok()));
            return replyTo;
        }
        catch (Exception e) {
            log.error("\u6d88\u606f{}\u5904\u7406\u5931\u8d25", (Object)messageId, (Object)e);
            if (StringUtils.hasText(globMsgId) && StringUtils.hasText(messageId)) {
                this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
            }
            ReplyTo replyTo = new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.error((String)this.getErrorMessage())));
            return replyTo;
        }
        finally {
            ShiroUtil.unbindTenantName();
            ShiroUtil.resetApiAuth();
            ShiroUtil.unbindUser();
        }
    }

    protected abstract void doMessage(Map<String, Object> var1);

    protected abstract String getErrorMessage();

    public String getJoinName() {
        return this.joinName;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }
}

