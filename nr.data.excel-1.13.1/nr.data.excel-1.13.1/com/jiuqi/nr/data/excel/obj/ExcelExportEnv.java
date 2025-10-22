/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class ExcelExportEnv {
    private DimensionCombination dimensionCombination;
    private String taskKey;
    private String formSchemeKey;

    public ExcelExportEnv(String taskKey, String formSchemeKey) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
    }

    public ExcelExportEnv(DimensionCombination dimensionCombination, String taskKey, String formSchemeKey) {
        this.dimensionCombination = dimensionCombination;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

