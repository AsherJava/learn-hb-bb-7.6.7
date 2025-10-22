/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BatchRefreshStatusObj
implements Serializable {
    private static final long serialVersionUID = 3051109533532003514L;
    private String taskKey;
    private String formSchemeKey;
    private Map<String, DimensionValue> dimensionSet;
    private List<String> formKeys;
    private String formKey;

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

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

