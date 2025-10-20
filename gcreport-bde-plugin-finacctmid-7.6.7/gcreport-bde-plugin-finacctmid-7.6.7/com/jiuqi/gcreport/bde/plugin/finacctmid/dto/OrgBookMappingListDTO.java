/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.plugin.finacctmid.dto;

import java.util.List;

public class OrgBookMappingListDTO {
    private String taskKey;
    private List<String> unitCodes;
    private String searchKey;
    private boolean onlyShowFetchBook;
    private boolean pagination;
    private Integer pageNum;
    private Integer pageSize;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public boolean isOnlyShowFetchBook() {
        return this.onlyShowFetchBook;
    }

    public void setOnlyShowFetchBook(boolean onlyShowFetchBook) {
        this.onlyShowFetchBook = onlyShowFetchBook;
    }

    public boolean isPagination() {
        return this.pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        return "OrgBookMappingListDTO [unitCodes=" + this.unitCodes + ", searchKey=" + this.searchKey + ", onlyShowFetchBook=" + this.onlyShowFetchBook + ", pagination=" + this.pagination + ", pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + "]";
    }
}

