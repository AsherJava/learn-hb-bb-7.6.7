/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.budget.common.domain.DefaultTenantDTO
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.budget.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.budget.common.domain.DefaultTenantDTO;
import com.jiuqi.va.mapper.domain.PageDTO;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class PageInfo
implements PageDTO,
DefaultTenantDTO {
    private int pageNum = 1;
    private int limit = 50;
    private boolean pagination = true;
    private int total;

    public int getPageNum() {
        return this.pageNum;
    }

    public PageInfo setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getLimit() {
        return this.limit;
    }

    public PageInfo setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public Integer getTotal() {
        return this.total;
    }

    public PageInfo setTotal(int total) {
        this.total = total;
        return this;
    }

    public boolean isPagination() {
        return this.pagination;
    }

    public PageInfo setPagination(boolean pagination) {
        this.pagination = pagination;
        return this;
    }

    public int getOffset() {
        return (this.pageNum - 1) * this.limit;
    }

    public String toString() {
        return "PageInfo{pageNum=" + this.pageNum + ", limit=" + this.limit + ", total=" + this.total + '}';
    }
}

