/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.apache.shiro.util.StringUtils
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 */
package com.jiuqi.va.attachment.service.join;

import com.jiuqi.va.attachment.config.VaAttachmentCoreConfig;
import com.jiuqi.va.attachment.entity.AttachmentTableCacheDTO;
import com.jiuqi.va.attachment.storage.AttachmentBizStorage;
import com.jiuqi.va.domain.common.JSONUtil;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component(value="vaAttachmentSyncCacheMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaAttachmentSyncCacheMsgReciver
implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(VaAttachmentSyncCacheMsgReciver.class);

    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        logger.info("\u540c\u6b65\u9644\u4ef6\u8868\u7f13\u5b58{}", (Object)msg);
        if (!StringUtils.hasText((String)msg)) {
            return;
        }
        AttachmentTableCacheDTO messageMap = (AttachmentTableCacheDTO)((Object)JSONUtil.parseObject((String)msg, AttachmentTableCacheDTO.class));
        if (VaAttachmentCoreConfig.getCurrNodeId().equals(messageMap.getCurrNodeId())) {
            return;
        }
        AttachmentBizStorage.syncTableCache(messageMap.getSuffix());
    }
}

