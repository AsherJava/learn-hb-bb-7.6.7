/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.List;

public class InputRow {
    private DimensionCombination combination;
    private DimensionValueSet newKey;
    private DimensionValueSet rowKeys;
    private List<Object> linkValues = new ArrayList<Object>();
    private int type;
    private int rowIndex;

    public DimensionCombination getCombination() {
        return this.combination;
    }

    public void setCombination(DimensionCombination combination) {
        this.combination = combination;
    }

    public List<Object> getLinkValues() {
        return this.linkValues;
    }

    public void setLinkValues(List<Object> linkValues) {
        this.linkValues = linkValues;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public DimensionValueSet getNewKey() {
        return this.newKey;
    }

    public void setNewKey(DimensionValueSet newKey) {
        this.newKey = newKey;
    }

    public DimensionValueSet getRowKeys() {
        if (this.rowKeys == null) {
            this.rowKeys = this.combination.toDimensionValueSet();
        }
        return this.rowKeys;
    }
}

