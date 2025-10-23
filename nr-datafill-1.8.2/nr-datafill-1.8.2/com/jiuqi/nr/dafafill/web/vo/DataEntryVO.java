/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEntryVO {
    Map<String, String> dimensionMap = new HashMap<String, String>();
    List<String> linkList;
    String taskKey;
    String params;
    Map<String, String> paramMap = new HashMap<String, String>();

    public Map<String, String> getDimensionMap() {
        return this.dimensionMap;
    }

    public void setDimensionMap(Map<String, String> dimensionMap) {
        this.dimensionMap = dimensionMap;
    }

    public List<String> getLinkList() {
        return this.linkList;
    }

    public void setLinkList(List<String> linkList) {
        this.linkList = linkList;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Map<String, String> getParamMap() {
        return this.paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}

