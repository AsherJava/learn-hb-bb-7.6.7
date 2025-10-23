/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataSchemeCacheMessageSender {
    private final MessagePublisher messageService;
    private final Logger logger = LoggerFactory.getLogger(DataSchemeCacheMessageSender.class);

    public DataSchemeCacheMessageSender(MessagePublisher messageService) {
        this.messageService = messageService;
    }

    public void send(RefreshCache refreshCache) {
        this.logger.info("\u6570\u636e\u65b9\u6848\u7f13\u5b58\u53d1\u9001Redis\u6d88\u606f\uff0c\u5237\u65b0\u7f13\u5b58\uff1a\u53c2\u6570- {}", (Object)refreshCache);
        this.messageService.publishMessage("com.jiuqi.nr.datascheme.runtime", (Object)refreshCache);
    }
}

