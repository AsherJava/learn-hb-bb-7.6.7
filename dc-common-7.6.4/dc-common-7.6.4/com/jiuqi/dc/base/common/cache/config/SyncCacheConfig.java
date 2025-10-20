/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SyncCacheConfig {
    private String syncCacheChannel;
    @Value(value="${spring.redis.enabled:true}")
    private Boolean redisEnable;

    public String getSyncCacheChannel() {
        return this.syncCacheChannel;
    }

    @Value(value="${spring.redis.database:0}")
    public void setCacheChannel(int database) {
        this.syncCacheChannel = "DcSyncCacheChannel@" + database;
    }

    public boolean isRedisEnable() {
        return this.redisEnable;
    }

    public void setRedisEnable(Boolean redisEnable) {
        this.redisEnable = redisEnable;
    }
}

