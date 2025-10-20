/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.catche;

import java.io.Serializable;

public class LocalCacheEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int DEFUALT_VALIDITY_TIME = 20;
    private String cacheKey;
    private transient Object cacheContent;
    private int validityTime = 20;
    private long timeoutStamp = System.currentTimeMillis() + 20000L;

    private LocalCacheEntity() {
    }

    public LocalCacheEntity(String cacheKey, Object cacheContent) {
        this();
        this.cacheKey = cacheKey;
        this.cacheContent = cacheContent;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public long getTimeoutStamp() {
        return this.timeoutStamp;
    }

    public void setTimeoutStamp(long timeoutStamp) {
        this.timeoutStamp = timeoutStamp;
    }

    public int getValidityTime() {
        return this.validityTime;
    }

    public void setValidityTime(int validityTime) {
        this.validityTime = validityTime;
    }

    public Object getCacheContent() {
        return this.cacheContent;
    }

    public void setCacheContent(Object cacheContent) {
        this.cacheContent = cacheContent;
    }
}

