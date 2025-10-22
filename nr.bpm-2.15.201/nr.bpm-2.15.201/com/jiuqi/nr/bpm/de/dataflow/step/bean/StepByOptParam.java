/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BaseStepParam;
import java.util.List;

public class StepByOptParam
extends BaseStepParam {
    private DimensionValueSet dimensionValue;
    private String formKey;
    private String groupKey;
    private List<String> formKeys;
    private List<String> groupKeys;

    public DimensionValueSet getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(DimensionValueSet dimensionValue) {
        this.dimensionValue = dimensionValue;
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

