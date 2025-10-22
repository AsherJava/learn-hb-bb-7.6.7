/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.param.BaseExpPar;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class SingleExpPar
extends BaseExpPar {
    private List<String> forms;
    private DimensionCombination dimensionCombination;
    private String dataSnapshotId;

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getDataSnapshotId() {
        return this.dataSnapshotId;
    }

    public void setDataSnapshotId(String dataSnapshotId) {
        this.dataSnapshotId = dataSnapshotId;
    }
}

