/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.domain.common.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class PageVO<T>
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int total;
    private List<T> rows;
    private R rs;

    public PageVO() {
    }

    public PageVO(boolean isEmpty) {
        if (!isEmpty) {
            return;
        }
        this.rows = new ArrayList<T>();
        this.total = 0;
        this.rs = R.ok();
    }

    public PageVO(List<T> list, int total) {
        this.rows = list;
        this.total = total;
        this.rs = R.ok();
    }

    public PageVO(List<T> list, int total, R rs) {
        this.rows = list;
        this.total = total;
        this.rs = rs;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public R getRs() {
        return this.rs;
    }

    public void setRs(R rs) {
        this.rs = rs;
    }
}

