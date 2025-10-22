/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.unit.uselector.web.response;

import com.jiuqi.nr.common.itree.INode;
import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private List<INode> pageData = new ArrayList<INode>();
    private int pagesize = 50;
    private int totalCount = 0;
    private int currentPage = 0;
    private boolean maxPage = false;

    public List<INode> getPageData() {
        return this.pageData;
    }

    public void setPageData(List<INode> pageData) {
        this.pageData = pageData;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isMaxPage() {
        return this.maxPage;
    }

    public void setMaxPage(boolean maxPage) {
        this.maxPage = maxPage;
    }
}

