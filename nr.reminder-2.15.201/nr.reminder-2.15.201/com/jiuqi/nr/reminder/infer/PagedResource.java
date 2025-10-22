/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.infer;

import java.util.Collections;
import java.util.List;

public class PagedResource<T> {
    private List<T> data;
    private int pageIndex;
    private int pageSize;
    private int totalSize;
    private Integer totalPages;

    public PagedResource(List<T> data, int pageIndex, int pageSize, int totalSize) {
        this.data = data;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.totalPages = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
    }

    public static <T> PagedResource<T> empty(int pageIndex, int pageSize, int totalSize) {
        return new PagedResource(Collections.emptyList(), pageIndex, pageSize, totalSize);
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public int getTotalPages() {
        return this.totalPages;
    }
}

