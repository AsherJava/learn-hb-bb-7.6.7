/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 */
package com.jiuqi.nr.sensitive.service.impl;

import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.nr.sensitive.dao.SensitiveWordDao;
import com.jiuqi.nr.sensitive.service.CheckSensitiveWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Subscriber(channels={"com.jiuqi.nr.sensitive"})
@Service
public class SensitiveCacheMessageSubscriber
implements MessageSubscriber {
    private final Logger logger = LoggerFactory.getLogger(SensitiveCacheMessageSubscriber.class);
    @Autowired
    private CheckSensitiveWordService checkSensitiveWordService;
    @Autowired
    private SensitiveWordDao sensitiveWordDao;

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        if (fromThisInstance) {
            this.logger.debug("\u5237\u65b0\u7f13\u5b58\u6d88\u606f\u662f\u5426\u7531\u5f53\u524d\u670d\u52a1\u53d1\u51fa,\u4e0d\u9700\u8981\u5904\u7406 {}", message);
            return;
        }
        if (message == null) {
            this.logger.debug("\u6d88\u606f\u4f53\u4e3a\u7a7a,\u4e0d\u9700\u8981\u5904\u7406");
            return;
        }
        this.checkSensitiveWordService.cacheSensitiveWordMap(this.sensitiveWordDao.queryAllSensitiveWordByWordType(0), this.sensitiveWordDao.queryAllSensitiveWordByWordType(1));
    }
}

