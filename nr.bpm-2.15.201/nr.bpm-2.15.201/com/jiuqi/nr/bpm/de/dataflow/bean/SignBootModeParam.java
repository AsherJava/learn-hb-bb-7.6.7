/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;

public class SignBootModeParam {
    private String taskKey;
    private String formSchemeKey;
    private String nodeId;
    private String actionCode;
    private Map<String, DimensionValue> dimensionValueSet;

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

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Map<String, DimensionValue> getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(Map<String, DimensionValue> dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

