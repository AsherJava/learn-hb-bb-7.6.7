/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.cache.intf.impl;

import com.jiuqi.dc.base.common.cache.intf.ISyncCacheMessage;
import com.jiuqi.dc.base.common.cache.intf.impl.CacheDefine;

public class SyncCacheMessage
implements ISyncCacheMessage {
    private String id;
    private CacheDefine cacheDefine;
    private Object cache;

    public SyncCacheMessage() {
    }

    public SyncCacheMessage(String id, CacheDefine cacheDefine) {
        this.id = id;
        this.cacheDefine = cacheDefine;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public CacheDefine getCacheDefine() {
        return this.cacheDefine;
    }

    public void setCacheDefine(CacheDefine cacheDefine) {
        this.cacheDefine = cacheDefine;
    }

    @Override
    public Object getCache() {
        return this.cache;
    }

    public void setCache(Object cache) {
        this.cache = cache;
    }
}

