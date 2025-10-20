/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.model;

import com.jiuqi.np.core.model.PageInfo;
import java.io.Serializable;
import java.util.List;

public class PageResult<T>
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> items;
    private PageInfo paging;
    private List<String> sortedBy;

    public PageResult(List<T> items, PageInfo paging, List<String> sortedBy) {
        this.items = items;
        this.paging = paging;
        this.sortedBy = sortedBy;
    }

    public static <T> PageResult<T> build(List<T> items) {
        return new PageResult<T>(items, null, null);
    }

    public static <T> PageResult<T> build(List<T> items, PageInfo paging) {
        return new PageResult<T>(items, paging, null);
    }

    public static <T> PageResult<T> build(List<T> items, PageInfo paging, List<String> sortedBy) {
        return new PageResult<T>(items, paging, sortedBy);
    }

    public List<T> getItems() {
        return this.items;
    }

    public PageInfo getPaging() {
        return this.paging;
    }

    public List<String> getSortedBy() {
        return this.sortedBy;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setPaging(PageInfo paging) {
        this.paging = paging;
    }

    public void setSortedBy(List<String> sortedBy) {
        this.sortedBy = sortedBy;
    }
}

