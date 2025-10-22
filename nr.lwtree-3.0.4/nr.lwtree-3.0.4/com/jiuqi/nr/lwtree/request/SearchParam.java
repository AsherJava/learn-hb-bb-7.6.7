/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.lwtree.request;

public class SearchParam {
    private int pagesize = 50;
    private int currentPage = 0;
    private String searchText;

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

