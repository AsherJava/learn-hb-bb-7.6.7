/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.cache.CacheProviderManager
 *  com.jiuqi.dc.base.common.cache.intf.impl.SyncCacheMessage
 *  com.jiuqi.dc.base.common.cache.proider.ICacheProvider
 *  com.jiuqi.dc.base.common.intf.impl.Instance
 */
package com.jiuqi.dc.base.impl.listener;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.cache.CacheProviderManager;
import com.jiuqi.dc.base.common.cache.intf.impl.SyncCacheMessage;
import com.jiuqi.dc.base.common.cache.proider.ICacheProvider;
import com.jiuqi.dc.base.common.intf.impl.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value="dcSyncCacheMessageListener")
public class SyncCacheMessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void receiveMessage(String message, String channel) {
        SyncCacheMessage msg = (SyncCacheMessage)JsonUtils.readValue((String)message, SyncCacheMessage.class);
        if (msg == null || msg.getCacheDefine() == null) {
            this.logger.error("\u6d88\u606f\u4f53\u683c\u5f0f\u9519\u8bef\u5bfc\u81f4\u7f13\u5b58\u540c\u6b65\u5931\u8d25\uff0c\u6d88\u606f\u4f53\uff1a{}", (Object)message);
            return;
        }
        if (msg.getId().equals(Instance.getInstanceId())) {
            return;
        }
        ICacheProvider cacheProvider = CacheProviderManager.getCacheProvider((String)msg.getCacheDefine().getCode());
        if (cacheProvider == null) {
            this.logger.error("{}\u3010{}\u3011\u7f13\u5b58\u6d88\u606f\u6ca1\u6709\u5339\u914d\u5230\u7f13\u5b58\u63d0\u4f9b\u5668\uff0c\u6d88\u606f\u4f53\uff1a{}", msg.getCacheDefine().getCode(), msg.getCacheDefine().getName(), message);
            return;
        }
        cacheProvider.cleanCache();
        this.logger.info("{}\u3010{}\u3011\u7f13\u5b58\u6e05\u9664\u6210\u529f", (Object)msg.getCacheDefine().getCode(), (Object)msg.getCacheDefine().getName());
    }
}

