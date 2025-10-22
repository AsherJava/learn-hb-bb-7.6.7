/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DimensionDataInfo {
    private Map<String, DimensionValue> dimensionSet;
    private List<Integer> dataIndex = new ArrayList<Integer>();
    private List<Integer> newDataIndex = new ArrayList<Integer>();
    private List<Integer> modifyDataIndex = new ArrayList<Integer>();
    private List<Integer> deleteDataIndex = new ArrayList<Integer>();

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public List<Integer> getDataIndex() {
        return this.dataIndex;
    }

    public void setDataIndex(List<Integer> dataIndex) {
        this.dataIndex = dataIndex;
    }

    public List<Integer> getNewDataIndex() {
        return this.newDataIndex;
    }

    public void setNewDataIndex(List<Integer> newDataIndex) {
        this.newDataIndex = newDataIndex;
    }

    public List<Integer> getModifyDataIndex() {
        return this.modifyDataIndex;
    }

    public void setModifyDataIndex(List<Integer> modifyDataIndex) {
        this.modifyDataIndex = modifyDataIndex;
    }

    public List<Integer> getDeleteDataIndex() {
        return this.deleteDataIndex;
    }

    public void setDeleteDataIndex(List<Integer> deleteDataIndex) {
        this.deleteDataIndex = deleteDataIndex;
    }
}

