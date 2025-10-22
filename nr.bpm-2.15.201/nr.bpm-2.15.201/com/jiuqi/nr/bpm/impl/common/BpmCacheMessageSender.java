/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.message.MessagePublisher
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.nr.bpm.impl.common.BpmCacheChangedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BpmCacheMessageSender {
    @Autowired
    MessagePublisher messageService;
    private final Logger logger = LoggerFactory.getLogger(BpmCacheMessageSender.class);

    public void sendCacheClearMessage(String cacheName) {
        if (cacheName == null) {
            return;
        }
        try {
            BpmCacheChangedMessage message = new BpmCacheChangedMessage(cacheName);
            this.messageService.publishMessage("com.jiuqi.np.dataengine.auth.authgo.AuthgoCacheChangedMessage", (Object)message);
        }
        catch (Exception e) {
            this.logger.error("\u7f13\u5b58\u540c\u6b65\u9519\u8bef", e);
        }
    }
}

