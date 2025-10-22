/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud;

public class PageInfo {
    private int rowsPerPage;
    private int pageIndex;

    public int getRowsPerPage() {
        return this.rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        if (rowsPerPage < 1) {
            throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef,\u6bcf\u9875\u6761\u6570\u4e0d\u5c0f\u4e8e1");
        }
        this.rowsPerPage = rowsPerPage;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        if (pageIndex < 0) {
            throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef,\u9875\u6570\u4e0d\u5c0f\u4e8e0");
        }
        this.pageIndex = pageIndex;
    }
}

