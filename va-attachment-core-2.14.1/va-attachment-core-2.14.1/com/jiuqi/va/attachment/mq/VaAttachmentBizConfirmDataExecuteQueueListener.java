/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.attachment.mq;

import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.attachment.mq.VaAttachmentBizMqConfigure;
import com.jiuqi.va.attachment.service.AttachmentBizConfirmService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaAttachmentBizConfirmDataExecuteQueueListener
implements JoinListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(VaAttachmentBizConfirmDataExecuteQueueListener.class);
    @Autowired
    private AttachmentBizConfirmService attachmentBizConfirmService;
    @Autowired
    private JoinTemplate joinTemplate;

    public String getJoinName() {
        return "VA_ATTACHMENT_CONFIRM_DATA_EXECUTE_MQ";
    }

    public ReplyTo onMessage(String message) {
        TenantDO tenantDO = (TenantDO)JSONUtil.parseObject((String)message, TenantDO.class);
        ShiroUtil.bindTenantName((String)tenantDO.getTenantName());
        ShiroUtil.ignoreApiAuth();
        Map extInfo = tenantDO.getExtInfo();
        String logid = (String)extInfo.get("_logid");
        this.confirmData();
        return VaAttachmentBizMqConfigure.getReplyTo(tenantDO, logid, this.joinTemplate);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void confirmData() {
        ShiroUtil.ignoreApiAuth();
        try {
            LocalDate endLoacalDate = LocalDate.now().plusDays(-7L);
            Instant endInstant = endLoacalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            BizAttachmentConfirmDTO param = new BizAttachmentConfirmDTO();
            param.setUpdatetime(Date.from(endInstant));
            param.setTenantName("__default_tenant__");
            ShiroUtil.bindTenantName((String)"__default_tenant__");
            this.attachmentBizConfirmService.executeConfirmData(param);
        }
        catch (Exception e) {
            LOGGER.error("\u9644\u4ef6\u786e\u8ba4\u8868\u5b9a\u65f6\u4efb\u52a1\u6570\u636e\u5904\u7406\u5931\u8d25", e);
        }
        finally {
            ShiroUtil.unbindTenantName();
            ShiroUtil.resetApiAuth();
        }
    }
}

