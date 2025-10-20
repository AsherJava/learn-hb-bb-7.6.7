/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.util.FormDimensionValue;
import com.jiuqi.nr.definition.util.ParamClearType;
import java.util.ArrayList;
import java.util.List;

public class ParamClearObject {
    private String task;
    private String formScheme;
    private ParamClearType paramClearType;
    private DimensionValueSet dimensionValueSet;
    private List<String> formKeys = new ArrayList<String>();
    private List<FormDimensionValue> formDimensionValues;

    public ParamClearObject(DimensionValueSet dimensionValueSet, ParamClearType paramClearType, String task, String formScheme) {
        this.dimensionValueSet = dimensionValueSet;
        this.paramClearType = paramClearType;
        this.task = task;
        this.formScheme = formScheme;
    }

    public ParamClearObject(String task, String formScheme, ParamClearType paramClearType, DimensionValueSet dimensionValueSet, List<String> formKeys) {
        this.task = task;
        this.formScheme = formScheme;
        this.paramClearType = paramClearType;
        this.dimensionValueSet = dimensionValueSet;
        this.formKeys = formKeys;
    }

    public ParamClearObject() {
    }

    public List<FormDimensionValue> getFormDimensionValues() {
        return this.formDimensionValues;
    }

    public void setFormDimensionValues(List<FormDimensionValue> formDimensionValues) {
        this.formDimensionValues = formDimensionValues;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public ParamClearType getParamClearType() {
        return this.paramClearType;
    }

    public void setParamClearType(ParamClearType paramClearType) {
        this.paramClearType = paramClearType;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }
}

