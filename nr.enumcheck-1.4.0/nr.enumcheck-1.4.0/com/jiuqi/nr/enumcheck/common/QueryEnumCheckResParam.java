/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryEnumCheckResParam {
    private String taskKey;
    private String formSchemeKey;
    private String asyncTaskId;
    private String groupKey;
    private Map<String, String> selectDims = new HashMap<String, String>();
    private List<String> orgCodes = new ArrayList<String>();
    private String periodDimValue;
    private int page;
    private int pageSize;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Map<String, String> getSelectDims() {
        return this.selectDims;
    }

    public void setSelectDims(Map<String, String> selectDims) {
        this.selectDims = selectDims;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public String getPeriodDimValue() {
        return this.periodDimValue;
    }

    public void setPeriodDimValue(String periodDimValue) {
        this.periodDimValue = periodDimValue;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

