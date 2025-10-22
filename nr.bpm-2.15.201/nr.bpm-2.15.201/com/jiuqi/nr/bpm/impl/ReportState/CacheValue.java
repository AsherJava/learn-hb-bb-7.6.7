/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.ReportState;

import java.io.Serializable;

public class CacheValue
implements Serializable {
    private static final long serialVersionUID = 3295202676727687980L;
    private Serializable cacheItem;
    private boolean isNull;

    public Serializable getCacheItem() {
        return this.cacheItem;
    }

    public void setCacheItem(Serializable cacheItem) {
        this.cacheItem = cacheItem;
    }

    public boolean isNull() {
        return this.isNull;
    }

    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }
}

