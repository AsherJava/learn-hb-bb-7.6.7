/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.source.search;

public class SearchContextData {
    private int pageSize = 50;
    private int currentPage = 0;
    private String[] keywords;

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String[] getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }
}

