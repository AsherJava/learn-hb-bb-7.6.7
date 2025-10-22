/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import java.util.List;

public class Pagination<T> {
    private List<T> content;
    private Integer totalElements;
    private Integer currentPage;
    private Integer pageSize;

    public Pagination() {
    }

    public Pagination(List<T> content, Integer totalElements, Integer currentPage, Integer pageSize) {
        this.content = content;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Integer getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

