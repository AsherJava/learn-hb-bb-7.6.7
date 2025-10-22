/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.np.dataengine.query.old;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.query.MemorySteamLoader;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.old.GroupingQuerySqlBuilder;
import com.jiuqi.np.dataengine.query.old.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryRowData;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryDataSetReader
extends AbstractQueryFieldDataReader {
    private Iterator<DimensionValueSet> iterator;
    private DimensionValueSet rowKey = null;
    private MemoryRowData rowDatas = null;
    private Map<DimensionValueSet, MemoryRowData> rowMap = new LinkedHashMap<DimensionValueSet, MemoryRowData>();
    private final Map<Integer, HashMap<DimensionValueSet, Object>> orderMap = new HashMap<Integer, HashMap<DimensionValueSet, Object>>();
    private int columnCount = 0;
    private int currentIndex = -1;
    private MemorySteamLoader loader;

    public MemoryDataSetReader() {
    }

    public MemoryDataSetReader(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public Object readData(QueryField queryField) throws Exception {
        QueryFieldInfo info = this.findQueryFieldInfo(queryField);
        if (info == null || this.rowKey == null) {
            return null;
        }
        Object dataValue = this.readData(info.memoryIndex);
        if (dataValue == null && info.dimensionName != null) {
            dataValue = this.rowKey.getValue(info.dimensionName);
        }
        return this.convertDataValue(info, dataValue);
    }

    @Override
    public QueryFieldInfo putIndex(DataModelDefinitionsCache cache, QueryField queryField, int index) {
        QueryFieldInfo queryFieldInfo = super.putIndex(cache, queryField, index);
        queryFieldInfo.memoryIndex = this.columnCount++;
        return queryFieldInfo;
    }

    @Override
    public void reset() {
        super.reset();
        this.rowMap.clear();
        this.orderMap.clear();
        this.rowKey = null;
        this.rowDatas = null;
        this.canRead = false;
        this.iterator = null;
        this.currentIndex = -1;
    }

    @Override
    public Object readData(int index) throws Exception {
        if (this.rowDatas != null) {
            Object value = this.rowDatas.getDatas()[index];
            if (value instanceof BlobValue) {
                return ((BlobValue)value)._getBytes();
            }
            return value;
        }
        return null;
    }

    @Override
    public boolean next() {
        if (this.iterator == null) {
            this.iterator = this.rowMap.keySet().iterator();
        }
        if (this.iterator.hasNext()) {
            ++this.currentIndex;
            this.rowKey = this.iterator.next();
        } else {
            this.rowKey = null;
        }
        this.rowDatas = this.rowKey != null ? this.rowMap.get(this.rowKey) : null;
        return this.rowDatas != null;
    }

    @Override
    public void setDataSet(Object dataSet) {
    }

    public void loadDatas(QueryContext qContext, QuerySqlBuilder builder, boolean needOrderJoin, boolean sumDatas) throws Exception {
        int memeryStartIndex = builder.getMemoryStartIndex();
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        MemoryDataSet tableDataSet = (MemoryDataSet)builder.runQuery(qContext);
        HashMap<DimensionValueSet, Integer> rowIndexMap = new HashMap<DimensionValueSet, Integer>();
        int groupingFlag = -1;
        int groupFlagIndex = -1;
        if (builder instanceof GroupingQuerySqlBuilder) {
            GroupingQuerySqlBuilder groupingQuerySqlBuilder = (GroupingQuerySqlBuilder)builder;
            if (groupingQuerySqlBuilder.isWantDetail() && tableDataSet.size() == 1) {
                return;
            }
            groupFlagIndex = tableDataSet.getMetadata().getColumnCount() - 1;
        }
        for (DataRow row : tableDataSet) {
            DimensionValueSet rowKey;
            if (groupFlagIndex > 0) {
                Object groupingValue = row.getValue(groupFlagIndex);
                groupingFlag = Convert.toInt((Object)groupingValue);
            }
            if (!this.checkRowKey(qContext, rowKey = this.buildRowKey(qContext, builder, builder.getLoopDimensions(), rowKeyStartIndex, row, groupingFlag), groupingFlag)) continue;
            this.tryOrderJoin(needOrderJoin, rowIndexMap, rowKey);
            this.readRow(memeryStartIndex, rowKeyStartIndex, row, rowKey, groupingFlag, sumDatas);
        }
    }

    protected void tryOrderJoin(boolean needOrderJoin, HashMap<DimensionValueSet, Integer> rowIndexMap, DimensionValueSet rowKey) {
        if (needOrderJoin && rowKey.hasValue("RECORDKEY")) {
            HashMap<Object, Object> orderItem;
            DimensionValueSet subKey = new DimensionValueSet(rowKey);
            subKey.clearValue("RECORDKEY");
            int rowIndex = 1;
            if (rowIndexMap.containsKey(subKey)) {
                rowIndex = rowIndexMap.get(subKey);
            }
            Object bizValue = rowKey.getValue("RECORDKEY");
            rowKey.setValue("RECORDKEY", rowIndex);
            if (!this.orderMap.containsKey(rowIndex)) {
                orderItem = new HashMap<DimensionValueSet, Object>();
                orderItem.put(rowKey, bizValue);
                this.orderMap.put(rowIndex, orderItem);
            } else {
                orderItem = this.orderMap.get(rowIndex);
                if (!orderItem.containsKey(rowKey)) {
                    orderItem.put(rowKey, bizValue);
                }
            }
            rowIndexMap.put(subKey, ++rowIndex);
        }
    }

    public void readRow(int memeryStartIndex, int rowKeyStartIndex, DataRow row, DimensionValueSet rowKey, int groupingFlag, boolean sumDatas) {
        Object[] datas = this.getRowDatas(rowKey, groupingFlag).getDatas();
        for (int i = 0; i < rowKeyStartIndex; ++i) {
            Object originalValue;
            Object value = row.getValue(i);
            if (sumDatas && (originalValue = datas[memeryStartIndex + i]) != null) {
                if (value == null) {
                    value = originalValue;
                } else if (originalValue instanceof BigDecimal) {
                    value = ((BigDecimal)originalValue).add((BigDecimal)value);
                } else if (originalValue instanceof Number) {
                    value = ((Number)originalValue).doubleValue() + ((Number)value).doubleValue();
                }
            }
            datas[memeryStartIndex + i] = value;
        }
    }

    public MemoryRowData getRowDatas(DimensionValueSet rowKey, int groupingFlag) {
        MemoryRowData datas = this.rowMap.get(rowKey);
        if (datas == null) {
            datas = new MemoryRowData(rowKey, this.columnCount);
            datas.setGroup_flag(groupingFlag);
            this.rowMap.put(rowKey, datas);
        }
        return datas;
    }

    public void loadLeftJoinDatas(QueryContext qContext, QuerySqlBuilder builder, boolean asFullJoin) throws Exception {
        int memeryStartIndex = builder.getMemoryStartIndex();
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        MemoryDataSet tableDataSet = (MemoryDataSet)builder.runQuery(qContext);
        int groupingFlag = -1;
        int groupFlagIndex = -1;
        if (builder instanceof GroupingQuerySqlBuilder) {
            GroupingQuerySqlBuilder groupingQuerySqlBuilder = (GroupingQuerySqlBuilder)builder;
            if (groupingQuerySqlBuilder.isWantDetail() && tableDataSet.size() == 1) {
                return;
            }
            groupFlagIndex = tableDataSet.getMetadata().getColumnCount() - 1;
        }
        for (DataRow row : tableDataSet) {
            if (groupFlagIndex > 0) {
                Object groupingValue = row.getValue(groupFlagIndex);
                groupingFlag = Convert.toInt((Object)groupingValue);
            }
            DimensionValueSet rowKey = this.buildRowKey(qContext, builder, builder.getLoopDimensions(), rowKeyStartIndex, row, groupingFlag);
            boolean merged = false;
            if (!this.checkRowKey(qContext, rowKey, groupingFlag)) continue;
            for (DimensionValueSet mainRowKey : this.rowMap.keySet()) {
                if (!mainRowKey.isSubsetOf(rowKey)) continue;
                this.readRow(memeryStartIndex, rowKeyStartIndex, row, mainRowKey, groupingFlag, false);
                merged = true;
            }
            if (merged || !asFullJoin) continue;
            this.readRow(memeryStartIndex, rowKeyStartIndex, row, rowKey, groupingFlag, false);
        }
    }

    private DimensionValueSet buildRowKey(QueryContext qContext, QuerySqlBuilder builder, DimensionSet loopDimensions, int rowKeyStartIndex, DataRow row, int groupingFlag) {
        int i;
        DimensionValueSet masterKeys = qContext.getMasterKeys();
        DimensionValueSet rowKey = new DimensionValueSet();
        DimensionSet regionLoopDimensions = builder.getQueryRegion().getLoopDimensions();
        if (regionLoopDimensions != null && regionLoopDimensions.size() > loopDimensions.size()) {
            for (i = 0; i < regionLoopDimensions.size(); ++i) {
                Object dimValue;
                String dim = regionLoopDimensions.get(i);
                if (loopDimensions.contains(dim) || (dimValue = masterKeys.getValue(dim)) == null || dimValue instanceof List) continue;
                rowKey.setValue(dim, dimValue);
            }
        }
        for (i = 0; i < loopDimensions.size(); ++i) {
            String dimName = loopDimensions.get(i);
            Object value = masterKeys.getValue(dimName);
            if (value == null || value instanceof List) {
                value = row.getValue(i + rowKeyStartIndex);
            }
            if (value instanceof byte[]) {
                value = Convert.toUUID((Object)value);
            } else if (value instanceof BlobValue) {
                value = Convert.toUUID((byte[])((BlobValue)value)._getBytes());
            }
            if (!builder.getUnitKeyMap().isEmpty() && !builder.getUnitDimensionMap().isEmpty()) {
                String originalDim = builder.getUnitDimensionMap().get(dimName);
                if (originalDim != null) {
                    Object originalValue = builder.getUnitKeyMap().get(value.toString());
                    if (originalValue != null) {
                        if (originalDim.equals(dimName)) {
                            dimName = dimName + "~1";
                        }
                        rowKey.setValue(dimName, value);
                        rowKey.setValue(originalDim, originalValue);
                    } else {
                        rowKey.setValue(dimName, value);
                    }
                } else {
                    rowKey.setValue(dimName, value);
                }
            } else {
                rowKey.setValue(dimName, value);
            }
            if (groupingFlag < 0) continue;
            rowKey.setValue("GroupingFlag", groupingFlag);
        }
        return rowKey;
    }

    private boolean checkRowKey(QueryContext qContext, DimensionValueSet rowKey, int groupingFlag) {
        if (groupingFlag < 0) {
            for (int i = 0; i < rowKey.size(); ++i) {
                if (rowKey.getValue(i) != null) continue;
                StringBuilder msg = new StringBuilder();
                msg.append("\u884c\u7ef4\u5ea6\u503c\u7f3a\u5931:rowKey=").append(rowKey).append(",\u7f3a\u5931\u4e86").append(rowKey.getName(i));
                qContext.getMonitor().exception(new DataValidateException(msg.toString()));
                return false;
            }
        }
        return true;
    }

    public void expandByDims(Collection<DimensionValueSet> dims) {
        for (DimensionValueSet dim : dims) {
            this.getRowDatas(dim, -1);
        }
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public DimensionValueSet getCurrentRowKey() {
        return this.rowKey;
    }

    public void setLoopDimensions(DimensionSet loopDimensions) {
    }

    public int size() {
        return this.rowMap.size();
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("rowCount:" + this.rowMap.size() + "    columnCount:" + this.columnCount + "\n");
        for (MemoryRowData rowData : this.rowMap.values()) {
            buff.append(rowData).append("\n");
        }
        return buff.toString();
    }

    public void resetBizOrderValue() {
        if (this.orderMap.size() <= 0) {
            return;
        }
        LinkedHashMap<DimensionValueSet, MemoryRowData> resultValue = new LinkedHashMap<DimensionValueSet, MemoryRowData>();
        for (Map.Entry<DimensionValueSet, MemoryRowData> rowItem : this.rowMap.entrySet()) {
            HashMap<DimensionValueSet, Object> orderItem;
            Object orderValue;
            DimensionValueSet rowKey = rowItem.getKey();
            if (!rowKey.hasValue("RECORDKEY") || !((orderValue = rowKey.getValue("RECORDKEY")) instanceof Integer) || !this.orderMap.containsKey(orderValue) || !(orderItem = this.orderMap.get(orderValue)).containsKey(rowKey)) continue;
            Object bizValue = orderItem.get(rowKey);
            rowKey.setValue("RECORDKEY", bizValue);
            resultValue.put(rowKey, rowItem.getValue());
        }
        if (resultValue.size() > 0) {
            this.rowMap = resultValue;
        }
    }

    public MemoryRowData getRowDatas() {
        return this.rowDatas;
    }

    public Object getValue(MemoryRowData rowData, DimensionValueSet rowKeys, QueryField queryField) throws Exception {
        QueryFieldInfo info = this.findQueryFieldInfo(queryField);
        if (info == null || rowKeys == null) {
            return null;
        }
        Object dataValue = this.readData(rowData, info.memoryIndex);
        if (dataValue == null && info.dimensionName != null) {
            dataValue = rowKeys.getValue(info.dimensionName);
        }
        return this.convertDataValue(info, dataValue);
    }

    public Object readData(MemoryRowData rowData, int index) throws Exception {
        if (rowData != null) {
            Object value = rowData.getDatas()[index];
            if (value instanceof BlobValue) {
                return ((BlobValue)value)._getBytes();
            }
            return value;
        }
        return null;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public MemoryRowData getRowData(int rowIndex) {
        if (rowIndex < 0 || rowIndex > this.rowMap.size()) {
            return null;
        }
        List rowKeys = this.rowMap.keySet().stream().collect(Collectors.toList());
        DimensionValueSet rowKey = (DimensionValueSet)rowKeys.get(rowIndex);
        MemoryRowData currentData = null;
        if (rowKey != null) {
            currentData = this.rowMap.get(rowKey);
        }
        return currentData;
    }

    public DimensionValueSet getRowKey(int rowIndex) {
        if (rowIndex < 0 || rowIndex > this.rowMap.size()) {
            return null;
        }
        List rowKeys = this.rowMap.keySet().stream().collect(Collectors.toList());
        DimensionValueSet rowKey = (DimensionValueSet)rowKeys.get(rowIndex);
        return rowKey;
    }
}

