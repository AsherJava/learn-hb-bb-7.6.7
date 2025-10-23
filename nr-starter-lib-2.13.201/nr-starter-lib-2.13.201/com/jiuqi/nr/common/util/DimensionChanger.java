/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.common.util;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimensionChanger {
    private String tableName;
    private Map<String, String> dimensionNameColumnMap = new HashMap<String, String>();
    private Map<String, String> columnDimensionNameMap = new HashMap<String, String>();
    private Map<String, ColumnModelDefine> columnModelDefineMap = new HashMap<String, ColumnModelDefine>();

    public DimensionChanger(String tableName) {
        this.tableName = tableName;
    }

    public void addDimensionNameColumn(String dimensionName, String columnCode, ColumnModelDefine columnModel) {
        this.dimensionNameColumnMap.put(dimensionName, columnCode);
        this.columnDimensionNameMap.put(columnCode, dimensionName);
        this.columnModelDefineMap.put(columnCode, columnModel);
    }

    public String getColumnCode(String dimensionName) {
        return this.dimensionNameColumnMap.get(dimensionName);
    }

    public ColumnModelDefine getColumn(String dimensionName) {
        return this.columnModelDefineMap.get(this.getColumnCode(dimensionName));
    }

    public String getDimensionName(String columnCode) {
        return this.columnDimensionNameMap.get(columnCode);
    }

    public String getDimensionName(ColumnModelDefine columnModel) {
        return this.getDimensionName(columnModel.getCode());
    }

    public ArrayKey getArrayKey(DimensionValueSet dimensionValueSet, List<ColumnModelDefine> rowKeyColumns) {
        int size = rowKeyColumns.size();
        Object[] keys = new Object[size];
        for (int i = 0; i < size; ++i) {
            Object dimensionValue;
            ColumnModelDefine columnModel = rowKeyColumns.get(i);
            String dimensionName = this.getDimensionName(columnModel);
            keys[i] = dimensionValue = dimensionValueSet.getValue(dimensionName);
        }
        ArrayKey arrayKey = new ArrayKey(keys);
        return arrayKey;
    }

    public DimensionValueSet getDimensionValueSet(ArrayKey arrayKey, List<ColumnModelDefine> rowKeyColumns) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < rowKeyColumns.size(); ++i) {
            ColumnModelDefine columnModel = rowKeyColumns.get(i);
            String dimensionName = this.getDimensionName(columnModel);
            dimensionValueSet.setValue(dimensionName, arrayKey.get(i));
        }
        return dimensionValueSet;
    }
}

