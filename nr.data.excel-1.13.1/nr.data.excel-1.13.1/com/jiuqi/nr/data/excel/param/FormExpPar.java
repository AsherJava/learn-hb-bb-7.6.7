/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.param.BaseExpPar;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class FormExpPar
extends BaseExpPar {
    private String formKey;
    private DimensionCombination dimensionCombination;
    private String dataSnapshotId;
    private boolean gridDataFormatted;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
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

    public boolean isGridDataFormatted() {
        return this.gridDataFormatted;
    }

    public void setGridDataFormatted(boolean gridDataFormatted) {
        this.gridDataFormatted = gridDataFormatted;
    }
}

