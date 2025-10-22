/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.context.infc.impl.NRContext;

public class FileGridDataContext
extends NRContext {
    private String groupKey;
    private String dataSchemeCode;
    private Integer pageSize;
    private Integer currentPage;
    private String order;
    private String sortBy;
    private String searchInfo;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSortBy() {
        return this.sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSearchInfo() {
        return this.searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }
}

