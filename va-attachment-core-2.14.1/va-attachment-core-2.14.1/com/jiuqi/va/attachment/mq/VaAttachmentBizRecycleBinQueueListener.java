/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.attachment.mq;

import com.jiuqi.va.attachment.mq.VaAttachmentBizMqConfigure;
import com.jiuqi.va.attachment.service.AttachmentBizRecycleBinService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaAttachmentBizRecycleBinQueueListener
implements JoinListener {
    @Autowired
    private AttachmentBizRecycleBinService attachmentBizRecycleBinService;
    @Autowired
    private JoinTemplate joinTemplate;

    public String getJoinName() {
        return "VA_ATTACHMENT_RECYCLE_BIN_CLEAR_MQ";
    }

    public ReplyTo onMessage(String message) {
        TenantDO tenantDO = (TenantDO)JSONUtil.parseObject((String)message, TenantDO.class);
        ShiroUtil.bindTenantName((String)tenantDO.getTenantName());
        ShiroUtil.ignoreApiAuth();
        Map extInfo = tenantDO.getExtInfo();
        String logid = (String)extInfo.get("_logid");
        this.attachmentBizRecycleBinService.scheduleClear();
        return VaAttachmentBizMqConfigure.getReplyTo(tenantDO, logid, this.joinTemplate);
    }
}

