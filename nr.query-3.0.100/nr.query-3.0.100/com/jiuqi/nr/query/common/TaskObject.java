/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

import java.util.concurrent.Future;

public class TaskObject {
    private Future<Object> future;
    private String userKey;
    private String blockKey;
    private String cacheType;

    public TaskObject() {
    }

    public TaskObject(Future<Object> future, String userKey, String blockKey, String cacheType) {
        this.future = future;
        this.userKey = userKey;
        this.blockKey = blockKey;
        this.cacheType = cacheType;
    }

    public Future<Object> getFuture() {
        return this.future;
    }

    public void setFuture(Future<Object> future) {
        this.future = future;
    }

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getBlockKey() {
        return this.blockKey;
    }

    public void setBlockKey(String blockKey) {
        this.blockKey = blockKey;
    }

    public String getCacheType() {
        return this.cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }
}

