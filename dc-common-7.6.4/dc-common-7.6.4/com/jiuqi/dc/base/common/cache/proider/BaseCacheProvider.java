/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.dc.base.common.cache.proider;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.cache.config.SyncCacheConfig;
import com.jiuqi.dc.base.common.cache.intf.impl.CacheDefine;
import com.jiuqi.dc.base.common.cache.intf.impl.SyncCacheMessage;
import com.jiuqi.dc.base.common.cache.proider.ICacheProvider;
import com.jiuqi.dc.base.common.intf.impl.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public abstract class BaseCacheProvider<E>
implements ICacheProvider<E> {
    @Autowired
    private SyncCacheConfig config;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public final void syncCache() {
        this.cleanCache();
        if (this.config.isRedisEnable()) {
            String message = "";
            try {
                message = JsonUtils.writeValueAsString((Object)new SyncCacheMessage(Instance.getInstanceId(), (CacheDefine)this.getCacheDefine()));
                this.stringRedisTemplate.convertAndSend(this.config.getSyncCacheChannel(), (Object)message);
                this.logger.info("{}\u3010{}\u3011\u7f13\u5b58\u540c\u6b65\u6d88\u606f\u53d1\u5e03\u6210\u529f", (Object)this.getCacheDefine().getCode(), (Object)this.getCacheDefine().getName());
            }
            catch (Exception e) {
                this.logger.error("{}\u3010{}\u3011\u7f13\u5b58\u540c\u6b65\u6d88\u606f\u53d1\u5e03\u5931\u8d25,\u6d88\u606f\u4f53\uff1a{}", this.getCacheDefine().getCode(), this.getCacheDefine().getName(), message, e);
            }
        }
    }
}

