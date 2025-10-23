/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.dto;

import java.util.List;

public class IPageDTO<T> {
    private Integer total;
    private List<T> data;

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

