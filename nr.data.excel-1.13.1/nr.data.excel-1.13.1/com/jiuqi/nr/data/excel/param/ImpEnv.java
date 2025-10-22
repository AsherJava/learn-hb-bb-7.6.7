/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class ImpEnv {
    private String taskKey;
    private String formSchemeKey;
    private List<DimensionCombination> dimensionCombinationList;

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

    public List<DimensionCombination> getDimensionCombinationList() {
        return this.dimensionCombinationList;
    }

    public void setDimensionCombinationList(List<DimensionCombination> dimensionCombinationList) {
        this.dimensionCombinationList = dimensionCombinationList;
    }
}

