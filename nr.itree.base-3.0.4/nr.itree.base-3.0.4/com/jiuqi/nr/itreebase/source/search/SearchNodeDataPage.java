/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.source.search;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeDataPage;
import java.util.ArrayList;
import java.util.List;

public class SearchNodeDataPage
implements ISearchNodeDataPage {
    private List<IBaseNodeData> pageData = new ArrayList<IBaseNodeData>();
    private int pageSize = 50;
    private int totalSize = 0;
    private int currentPage = 0;

    @Override
    public List<IBaseNodeData> getPageData() {
        return this.pageData;
    }

    public void setPageData(List<IBaseNodeData> pageData) {
        this.pageData = pageData;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

