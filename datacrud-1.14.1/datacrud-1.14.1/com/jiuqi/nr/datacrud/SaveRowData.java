/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;

public class SaveRowData {
    public static final int NODE = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;
    public static final int DEL = 3;
    public static final int PARTIAL_UPDATE = 4;
    private DimensionCombination combination;
    private DimensionValueSet newKey;
    private DimensionValueSet rowKeys;
    private AbstractData[] linkValues;
    private SaveData saveData;
    private int type;
    private int rowIndex;

    public DimensionCombination getCombination() {
        return this.combination;
    }

    public void setCombination(DimensionCombination combination) {
        this.combination = combination;
    }

    public DimensionValueSet getNewKey() {
        return this.newKey;
    }

    public void setNewKey(DimensionValueSet newKey) {
        this.newKey = newKey;
    }

    public AbstractData[] getLinkValues() {
        return this.linkValues;
    }

    public void setLinkValues(AbstractData[] linkValues) {
        this.linkValues = linkValues;
    }

    public SaveData getSaveData() {
        return this.saveData;
    }

    public void setSaveData(SaveData saveData) {
        this.saveData = saveData;
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

    public boolean isPkUpdate() {
        return this.newKey != null;
    }

    public SaveRowData getNewRow() {
        if (this.isPkUpdate()) {
            DimensionCombinationBuilder combinationBuilder = new DimensionCombinationBuilder(this.newKey);
            this.combination = combinationBuilder.getCombination();
        }
        return this;
    }

    public DimensionValueSet getRowKeys() {
        return this.rowKeys;
    }

    public void setRowKeys(DimensionValueSet rowKeys) {
        this.rowKeys = rowKeys;
    }
}

