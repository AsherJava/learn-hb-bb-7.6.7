/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.ArrayKey
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.idx.DSIndexEntry;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.ArrayKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DSCompoundIndex
extends DSIndexEntry {
    private int[] cols;
    private List<Integer> numberTypeCols;

    public DSCompoundIndex(BIDataSetImpl dataset, int[] cols) {
        super(dataset);
        this.cols = cols;
        this.numberTypeCols = new ArrayList<Integer>();
        for (int i = 0; i < cols.length; ++i) {
            int dtype = dataset.getMetadata().getColumn(cols[i]).getDataType();
            if (!DataTypes.isNumber((int)dtype) && dtype != 1) continue;
            this.numberTypeCols.add(i);
        }
    }

    public int[] getColumns() {
        return this.cols;
    }

    @Override
    protected Object accept(Object data) {
        if (data instanceof List) {
            return new ArrayKey(this.convertNumberToIntegerIfAbsent(((List)data).toArray()));
        }
        if (data instanceof Object[]) {
            return new ArrayKey(this.convertNumberToIntegerIfAbsent((Object[])data));
        }
        return data;
    }

    @Override
    protected void process(int rowIdx, Object[] rowData) {
        Object[] keyData = new Object[this.cols.length];
        for (int i = 0; i < this.cols.length; ++i) {
            keyData[i] = rowData[this.cols[i]];
        }
        ArrayKey key = new ArrayKey(this.convertNumberToIntegerIfAbsent(keyData));
        ArrayList<Integer> colList = (ArrayList<Integer>)this.valueMap.get(key);
        if (colList == null) {
            colList = new ArrayList<Integer>();
            this.valueMap.put(key, colList);
        }
        colList.add(rowIdx);
    }

    private Object[] convertNumberToIntegerIfAbsent(Object[] data) {
        if (this.numberTypeCols.isEmpty()) {
            return data;
        }
        Object[] cloned = new Object[data.length];
        System.arraycopy(data, 0, cloned, 0, data.length);
        for (Integer col : this.numberTypeCols) {
            Serializable v;
            if (cloned[col] instanceof Number) {
                v = (Number)cloned[col];
                cloned[col.intValue()] = ((Number)v).intValue();
                continue;
            }
            if (!(cloned[col] instanceof Boolean)) continue;
            v = (Boolean)cloned[col];
            cloned[col.intValue()] = ((Boolean)v).booleanValue() ? 1 : 0;
        }
        return cloned;
    }

    public int hashCode() {
        return Arrays.hashCode(this.cols);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DSCompoundIndex)) {
            return false;
        }
        DSCompoundIndex dci = (DSCompoundIndex)obj;
        return Arrays.equals(dci.cols, this.cols);
    }
}

