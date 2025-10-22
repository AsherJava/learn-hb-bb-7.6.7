/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;

public class DataEntryParam {
    private String formSchemeKey;
    private DimensionValueSet dim;
    private String formKey;
    private String groupKey;
    private List<String> formKeys;
    private List<String> groupKeys;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionValueSet getDim() {
        return this.dim;
    }

    public void setDim(DimensionValueSet dim) {
        this.dim = dim;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getGroupKeys() {
        return this.groupKeys;
    }

    public void setGroupKeys(List<String> groupKeys) {
        this.groupKeys = groupKeys;
    }
}

