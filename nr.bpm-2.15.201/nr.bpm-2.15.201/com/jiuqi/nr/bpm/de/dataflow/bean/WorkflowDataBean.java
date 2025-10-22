/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import java.util.List;

public class WorkflowDataBean {
    private String formSchemeKey;
    private String formKey;
    private String formGroupKey;
    private List<String> formKeys;
    private List<String> formGroupKeys;
    private DimensionValueSet dimSet;
    private IConditionCache conditionCache;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionValueSet getDimSet() {
        return this.dimSet;
    }

    public void setDimSet(DimensionValueSet dimSet) {
        this.dimSet = dimSet;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getFormGroupKeys() {
        return this.formGroupKeys;
    }

    public void setFormGroupKeys(List<String> formGroupKeys) {
        this.formGroupKeys = formGroupKeys;
    }

    public IConditionCache getConditionCache() {
        return this.conditionCache;
    }

    public void setConditionCache(IConditionCache conditionCache) {
        this.conditionCache = conditionCache;
    }
}

