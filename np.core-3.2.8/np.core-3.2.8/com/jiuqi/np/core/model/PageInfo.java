/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.model;

import java.io.Serializable;

public class PageInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int limit = -1;
    private long offset = -1L;
    private long total = -1L;

    public int getLimit() {
        return this.limit;
    }

    public long getOffset() {
        return this.offset;
    }

    public long getTotal() {
        return this.total;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

