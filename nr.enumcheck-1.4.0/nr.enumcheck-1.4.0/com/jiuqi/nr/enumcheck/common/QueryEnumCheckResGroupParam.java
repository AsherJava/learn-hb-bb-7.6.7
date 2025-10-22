/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryEnumCheckResGroupParam {
    private String taskKey;
    private String formSchemeKey;
    private String asyncTaskId;
    private String viewType;
    private int page;
    private List<String> orgCodes = new ArrayList<String>();
    private Map<String, String> selectDims = new HashMap<String, String>();

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

    public String getViewType() {
        return this.viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public Map<String, String> getSelectDims() {
        return this.selectDims;
    }

    public void setSelectDims(Map<String, String> selectDims) {
        this.selectDims = selectDims;
    }
}

