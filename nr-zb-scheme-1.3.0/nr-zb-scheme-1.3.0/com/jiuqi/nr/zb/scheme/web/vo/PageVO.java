/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import java.util.List;

public class PageVO<E> {
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalNum;
    private Integer totalPage;
    private List<E> data;
    private final Integer skip;

    public PageVO(Integer currentPage, Integer pageSize, Integer totalNum) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = (int)Math.ceil((double)totalNum.intValue() / (double)pageSize.intValue());
        this.currentPage = Math.max(this.currentPage, 1);
        this.currentPage = Math.min(this.totalPage, this.currentPage);
        this.skip = currentPage > 0 ? (currentPage - 1) * pageSize : 0;
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

    public Integer getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<E> getData() {
        return this.data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public Integer getSkip() {
        return this.skip;
    }

    public Integer getLimit() {
        return this.pageSize;
    }
}

