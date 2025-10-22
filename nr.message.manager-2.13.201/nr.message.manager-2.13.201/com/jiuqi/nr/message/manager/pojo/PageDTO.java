/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.pojo;

@Deprecated
public class PageDTO {
    private Integer currentPage;
    private Integer pageSize;

    public PageDTO(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getStartPosition() {
        return (this.currentPage - 1) * this.pageSize;
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

