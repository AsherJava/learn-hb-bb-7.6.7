/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model;

import java.io.Serializable;

public class PageInfo
implements Serializable {
    private int pageSize = 0;
    private int pageIndex = 1;
    private int recordSize = -1;
    public static final int RECORD_SIZE_IGNORE = Integer.MAX_VALUE;

    public PageInfo() {
    }

    public PageInfo(int pageSize, int pageIndex) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    public void setRecordSize(int recordSize) {
        this.recordSize = recordSize;
    }

    public static boolean isPageable(PageInfo pageInfo) {
        return pageInfo != null && pageInfo.getPageSize() > 0 && pageInfo.getPageIndex() > 0;
    }
}

