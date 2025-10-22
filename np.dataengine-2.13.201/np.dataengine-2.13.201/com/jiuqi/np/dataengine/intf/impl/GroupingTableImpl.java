/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupingTableImpl
extends ReadonlyTableImpl
implements IGroupingTable {
    private static final Logger logger = LoggerFactory.getLogger(GroupingTableImpl.class);
    private final HashSet<DimensionValueSet> groupingRowKeys = new HashSet();
    private HashMap<String, IDataRow> groupingRowMap;
    private HashMap<String, List<IDataRow>> detailRowMap;
    private HashMap<String, List<IDataRow>> firstDimDetailRowMap;

    public GroupingTableImpl(QueryContext qCntext, DimensionValueSet masterKeys, int columnCount) {
        super(qCntext, masterKeys, columnCount);
    }

    @Override
    public IDataRow findGroupingRow(DimensionValueSet groupingKeys) {
        String groupKeyData;
        if (this.groupingRowMap == null) {
            this.groupingRowMap = this.buildGroupingRowMap();
        }
        if (this.groupingRowMap.containsKey(groupKeyData = groupingKeys.toString())) {
            return this.groupingRowMap.get(groupKeyData);
        }
        return null;
    }

    private HashMap<String, IDataRow> buildGroupingRowMap() {
        HashMap<String, IDataRow> rowMap = new HashMap<String, IDataRow>();
        if (this.grpByColsEffectiveInGroupingId == null || this.grpByColsEffectiveInGroupingId.size() <= 0) {
            return rowMap;
        }
        try {
            ArrayList<Integer> groupColumns = new ArrayList<Integer>(this.grpByColsEffectiveInGroupingId.keySet());
            HashMap<Integer, String> groupingDimensions = this.getGroupingDimensions(groupColumns);
            for (IDataRow dataRow : this.dataRows) {
                if (dataRow.getGroupingFlag() < 0) continue;
                DimensionValueSet grpKeys = new DimensionValueSet();
                for (Integer grpColumn : groupColumns) {
                    AbstractData data = dataRow.getValue(grpColumn);
                    if (data.isNull) continue;
                    grpKeys.setValue(groupingDimensions.get(grpColumn), data.getAsObject());
                }
                rowMap.put(grpKeys.toString(), dataRow);
                this.groupingRowKeys.add(grpKeys);
            }
            return rowMap;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return rowMap;
        }
    }

    private HashMap<Integer, String> getGroupingDimensions(List<Integer> groupColumns) {
        HashMap<Integer, String> groupingDims = new HashMap<Integer, String>();
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        for (Integer columnIndex : groupColumns) {
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(columnIndex);
            if (fieldDefine != null) {
                ColumnModelDefine column = dataDefinitionsCache.getColumnModel(fieldDefine);
                String dimName = dataDefinitionsCache.getDimensionName(column);
                groupingDims.put(columnIndex, dimName);
                continue;
            }
            groupingDims.put(columnIndex, columnIndex.toString());
        }
        return groupingDims;
    }

    @Override
    public List<DimensionValueSet> getGroupingDimensionValues() {
        if (this.groupingRowMap == null) {
            this.groupingRowMap = this.buildGroupingRowMap();
        }
        ArrayList<DimensionValueSet> groupKeys = new ArrayList<DimensionValueSet>(this.groupingRowKeys.size());
        groupKeys.addAll(this.groupingRowKeys);
        return groupKeys;
    }

    @Override
    public List<IDataRow> findDetailRowsByGroupKey(DimensionValueSet groupKeys) {
        String groupKeyData;
        if (this.detailRowMap == null) {
            this.detailRowMap = this.buildDetailRowMap();
        }
        if (this.detailRowMap.containsKey(groupKeyData = groupKeys.toString())) {
            return this.detailRowMap.get(groupKeyData);
        }
        return new ArrayList<IDataRow>();
    }

    @Override
    public List<IDataRow> findDetailRowsByGroupKeyByFirstDimension(DimensionValueSet groupKeys, String dimName) {
        String groupKeyData;
        if (this.firstDimDetailRowMap == null) {
            this.firstDimDetailRowMap = this.buildDetailRowMapByFirstDimension(dimName);
        }
        if (this.firstDimDetailRowMap.containsKey(groupKeyData = groupKeys.toString())) {
            return this.firstDimDetailRowMap.get(groupKeyData);
        }
        return new ArrayList<IDataRow>();
    }

    private HashMap<String, List<IDataRow>> buildDetailRowMap() {
        HashMap<String, List<IDataRow>> rowMap = new HashMap<String, List<IDataRow>>();
        if (this.grpByColsEffectiveInGroupingId == null || this.grpByColsEffectiveInGroupingId.size() <= 0) {
            return rowMap;
        }
        try {
            ArrayList<Integer> groupColumns = new ArrayList<Integer>(this.grpByColsEffectiveInGroupingId.keySet());
            HashMap<Integer, String> groupingDimensions = this.getGroupingDimensions(groupColumns);
            for (IDataRow dataRow : this.dataRows) {
                List<Object> detailRows;
                if (dataRow.getGroupingFlag() >= 0) continue;
                DimensionValueSet grpKeys = new DimensionValueSet();
                for (Integer grpColumn : groupColumns) {
                    AbstractData data = dataRow.getValue(grpColumn);
                    if (data.isNull) continue;
                    grpKeys.setValue(groupingDimensions.get(grpColumn), data.getAsObject());
                }
                String groupKeyData = grpKeys.toString();
                if (!rowMap.containsKey(groupKeyData)) {
                    detailRows = new ArrayList();
                    rowMap.put(groupKeyData, detailRows);
                } else {
                    detailRows = rowMap.get(groupKeyData);
                }
                detailRows.add(dataRow);
            }
            return rowMap;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return rowMap;
        }
    }

    private HashMap<String, List<IDataRow>> buildDetailRowMapByFirstDimension(String dimName) {
        HashMap<String, List<IDataRow>> rowMap = new HashMap<String, List<IDataRow>>();
        if (this.grpByColsEffectiveInGroupingId == null || this.grpByColsEffectiveInGroupingId.size() <= 0) {
            return rowMap;
        }
        try {
            ArrayList<Integer> groupColumns = new ArrayList<Integer>(this.grpByColsEffectiveInGroupingId.keySet());
            HashMap<Integer, String> groupingDimensions = this.getGroupingDimensions(groupColumns);
            for (IDataRow dataRow : this.dataRows) {
                if (dataRow.getGroupingFlag() >= 0) continue;
                DimensionValueSet grpKeys = new DimensionValueSet();
                for (Integer grpColumn : groupColumns) {
                    AbstractData data = dataRow.getValue(grpColumn);
                    if (data.isNull) continue;
                    grpKeys.setValue(groupingDimensions.get(grpColumn), data.getAsObject());
                }
                for (int i = 0; i < grpKeys.size(); ++i) {
                    List<Object> detailRows;
                    String name = grpKeys.getName(i);
                    if (!dimName.equals(name)) continue;
                    Object value = grpKeys.getValue(i);
                    DimensionValueSet set = new DimensionValueSet();
                    set.setValue(name, value);
                    String groupKeyData = set.toString();
                    if (!rowMap.containsKey(groupKeyData)) {
                        detailRows = new ArrayList();
                        rowMap.put(groupKeyData, detailRows);
                    } else {
                        detailRows = rowMap.get(groupKeyData);
                    }
                    detailRows.add(dataRow);
                }
            }
            return rowMap;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return rowMap;
        }
    }

    @Override
    public void addDataRows(List<DataRowImpl> allRows) {
        if (allRows == null || allRows.size() <= 0) {
            return;
        }
        this.rowKeySearch = null;
        this.groupingRowMap = null;
        this.detailRowMap = null;
        this.firstDimDetailRowMap = null;
        this.dataRows.addAll(allRows);
    }
}

