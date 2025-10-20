/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.CleanUselessAttachmentDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.client.TenantInfoClient
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.attachment.mq;

import com.jiuqi.va.attachment.domain.CleanUselessAttachmentDTO;
import com.jiuqi.va.attachment.mq.VaAttachmentBizMqConfigure;
import com.jiuqi.va.attachment.service.AttachmentBizService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.client.TenantInfoClient;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaAttachmentBizCleanUseLessQueueListener
implements JoinListener {
    @Autowired
    private AttachmentBizService attachmentBizService;
    @Autowired
    private TenantInfoClient tenantInfoClient;
    @Autowired
    private JoinTemplate joinTemplate;

    public String getJoinName() {
        return "VA_ATTACHMENT_CLEAN_USE_LESS_MQ";
    }

    public ReplyTo onMessage(String message) {
        TenantDO tenantDO = (TenantDO)JSONUtil.parseObject((String)message, TenantDO.class);
        ShiroUtil.bindTenantName((String)tenantDO.getTenantName());
        ShiroUtil.ignoreApiAuth();
        Map extInfo = tenantDO.getExtInfo();
        String logid = (String)extInfo.get("_logid");
        this.run();
        return VaAttachmentBizMqConfigure.getReplyTo(tenantDO, logid, this.joinTemplate);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void run() {
        ShiroUtil.ignoreApiAuth();
        try {
            CleanUselessAttachmentDTO param = new CleanUselessAttachmentDTO();
            LocalDate endLoacalDate = LocalDate.now().plusDays(-7L);
            Instant endInstant = endLoacalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date from = Date.from(endInstant);
            param.setEndDate(from);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            param.setSuffix(sdf.format(from));
            if (TenantUtil.isMultiTenant()) {
                List list = this.tenantInfoClient.nameList();
                if (list != null && !list.isEmpty()) {
                    for (String name : list) {
                        param.setTenantName(name);
                        ShiroUtil.bindTenantName((String)name);
                        this.attachmentBizService.clean(param);
                    }
                }
            } else {
                param.setTenantName("__default_tenant__");
                ShiroUtil.bindTenantName((String)"__default_tenant__");
                this.attachmentBizService.clean(param);
            }
        }
        finally {
            ShiroUtil.unbindTenantName();
            ShiroUtil.resetApiAuth();
        }
    }
}

