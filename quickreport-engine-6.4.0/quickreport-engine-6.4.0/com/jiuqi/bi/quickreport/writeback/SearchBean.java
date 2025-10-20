/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.writeback;

public class SearchBean {
    private String filter;
    private int maxSize = 0;
    private boolean hasPath = false;

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isHasPath() {
        return this.hasPath;
    }

    public void setHasPath(boolean hasPath) {
        this.hasPath = hasPath;
    }
}

