/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumDataCheckParam {
    private String batchId;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection dims;
    private List<String> enumNames;
    private boolean ignoreBlank;
    private String filterFormula;
    private Map<String, Object> variableMap = new HashMap<String, Object>();

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

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

    public DimensionCollection getDims() {
        return this.dims;
    }

    public void setDims(DimensionCollection dims) {
        this.dims = dims;
    }

    public List<String> getEnumNames() {
        return this.enumNames;
    }

    public void setEnumNames(List<String> enumNames) {
        this.enumNames = enumNames;
    }

    public boolean isIgnoreBlank() {
        return this.ignoreBlank;
    }

    public void setIgnoreBlank(boolean ignoreBlank) {
        this.ignoreBlank = ignoreBlank;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}

