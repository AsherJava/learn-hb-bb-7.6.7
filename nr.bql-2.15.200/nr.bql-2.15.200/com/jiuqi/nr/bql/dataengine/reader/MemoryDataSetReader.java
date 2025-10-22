/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.query.QueryStatLeafHelper
 *  com.jiuqi.np.dataengine.reader.QueryFieldInfo
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.nr.bql.dataengine.reader;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QueryStatLeafHelper;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;
import com.jiuqi.nr.bql.dataengine.reader.AbstractQueryFieldDataReader;
import com.jiuqi.nr.bql.dataengine.reader.MemoryRowData;
import com.jiuqi.nr.bql.dataengine.reader.MemoryStatLeafRowData;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
    protected List<QueryFieldInfo> columns = new ArrayList<QueryFieldInfo>();

    public MemoryDataSetReader() {
    }

    public MemoryDataSetReader(QueryContext queryContext) {
        super(queryContext);
    }

    public Object readData(QueryField queryField) throws Exception {
        QueryFieldInfo info = this.findQueryFieldInfo(queryField);
        if (info == null || this.rowKey == null) {
            return null;
        }
        Object dataValue = this.readData(info.memoryIndex);
        if (dataValue == null && info.dimensionName != null && this.queryContext.isFormulaRun()) {
            dataValue = this.rowKey.getValue(info.dimensionName);
        }
        return this.convertDataValue(info.fieldDefine, dataValue);
    }

    @Override
    public QueryFieldInfo putIndex(DataModelDefinitionsCache cache, QueryField queryField, int index) {
        QueryFieldInfo queryFieldInfo = super.putIndex(cache, queryField, index);
        queryFieldInfo.memoryIndex = this.columns.size();
        this.columns.add(queryFieldInfo);
        this.columnCount = this.columns.size();
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

    public void setDataSet(Object dataSet) {
    }

    public void loadDatas(QueryContext qContext, QuerySqlBuilder builder) throws Exception {
        int memeryStartIndex = builder.getMemoryStartIndex();
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        MemoryDataSet tableDataSet = (MemoryDataSet)builder.runQuery(qContext);
        for (DataRow row : tableDataSet) {
            DimensionValueSet rowKey = this.buildRowKey(qContext, builder, builder.getLoopDimensions(), rowKeyStartIndex, row, builder.getGroupDims());
            this.readRow(memeryStartIndex, rowKeyStartIndex, row, rowKey, false);
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
            rowKey.setValue("RECORDKEY", (Object)rowIndex);
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

    public void readRow(int memeryStartIndex, int rowKeyStartIndex, DataRow row, DimensionValueSet rowKey, boolean sumDatas) {
        if (this.queryContext.needStatLeaf()) {
            List parentRowKeys;
            QueryStatLeafHelper statLeafHelper = this.queryContext.getStatLeafHelper();
            if (statLeafHelper.needDataRow(rowKey)) {
                MemoryRowData memoryRowData = this.getRowDatas(rowKey, -1);
                memoryRowData.loadData(row, sumDatas, memeryStartIndex, rowKeyStartIndex);
            }
            if ((parentRowKeys = statLeafHelper.getParentRowKeys(rowKey)) != null) {
                for (DimensionValueSet parentRowKey : parentRowKeys) {
                    MemoryStatLeafRowData parentRow = (MemoryStatLeafRowData)this.rowMap.get(parentRowKey);
                    if (parentRow == null) {
                        parentRow = new MemoryStatLeafRowData(parentRowKey, this.columns);
                        this.rowMap.put(parentRowKey, parentRow);
                    }
                    parentRow.loadData(row, sumDatas, memeryStartIndex, rowKeyStartIndex);
                }
            }
        } else {
            MemoryRowData memoryRowData = this.getRowDatas(rowKey, -1);
            memoryRowData.loadData(row, sumDatas, memeryStartIndex, rowKeyStartIndex);
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
        if (builder.getLoopDimensions().size() == 1 && !asFullJoin) {
            String leftJoinDimName = builder.getLoopDimensions().get(0);
            HashMap<Object, Integer> leftJoinRowMap = new HashMap<Object, Integer>();
            for (int i = 0; i < tableDataSet.size(); ++i) {
                DataRow row = tableDataSet.get(i);
                Object leftJoinValue = row.getValue(rowKeyStartIndex);
                leftJoinRowMap.put(leftJoinValue, i);
            }
            for (DimensionValueSet mainRowKey : this.rowMap.keySet()) {
                DataRow row;
                Integer index;
                Object dimValue = mainRowKey.getValue(leftJoinDimName);
                if (dimValue == null || (index = (Integer)leftJoinRowMap.get(dimValue)) == null || (row = tableDataSet.get(index.intValue())) == null) continue;
                this.readRow(memeryStartIndex, rowKeyStartIndex, row, mainRowKey, false);
            }
        } else {
            for (DataRow row : tableDataSet) {
                DimensionValueSet rowKey = this.buildRowKey(qContext, builder, builder.getLoopDimensions(), rowKeyStartIndex, row, null);
                boolean merged = false;
                for (DimensionValueSet mainRowKey : this.rowMap.keySet()) {
                    if (!mainRowKey.isSubsetOf(rowKey)) continue;
                    this.readRow(memeryStartIndex, rowKeyStartIndex, row, mainRowKey, false);
                    merged = true;
                }
                if (merged || !asFullJoin) continue;
                this.readRow(memeryStartIndex, rowKeyStartIndex, row, rowKey, false);
            }
        }
    }

    private DimensionValueSet buildRowKey(QueryContext qContext, QuerySqlBuilder builder, DimensionSet loopDimensions, int rowKeyStartIndex, DataRow row, DimensionSet groupDims) {
        DimensionValueSet masterKeys = qContext.getMasterKeys();
        DimensionValueSet rowKey = new DimensionValueSet();
        for (int i = 0; i < loopDimensions.size(); ++i) {
            String dimName = loopDimensions.get(i);
            if (groupDims != null && !groupDims.contains(dimName)) continue;
            Object value = masterKeys.getValue(dimName);
            if (qContext.needStatLeaf() || value == null || value instanceof List) {
                value = row.getValue(i + rowKeyStartIndex);
            }
            if (value instanceof byte[]) {
                value = Convert.toUUID((Object)value);
            } else if (value instanceof BlobValue) {
                value = Convert.toUUID((byte[])((BlobValue)value)._getBytes());
            } else if (value instanceof Date && dimName.equals("DATATIME")) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime((Date)value);
                value = PeriodType.DAY.fromCalendar(calendar);
            }
            rowKey.setValue(dimName, value);
        }
        return rowKey;
    }

    public void expandByDims(Collection<DimensionValueSet> dimValuesSet) {
        for (DimensionValueSet dimValues : dimValuesSet) {
            this.getRowDatas(dimValues, -1);
        }
    }

    public void expandByDims(DimensionSet expandDims, DimensionSet loopDims, Collection<DimensionValueSet> dimValuesSet) {
        if (expandDims.equals((Object)loopDims)) {
            this.expandByDims(dimValuesSet);
        } else if (loopDims.containsAll(expandDims)) {
            HashSet<DimensionValueSet> exists = new HashSet<DimensionValueSet>();
            for (DimensionValueSet rowKey : this.rowMap.keySet()) {
                DimensionValueSet dim = new DimensionValueSet();
                for (int i = 0; i < expandDims.size(); ++i) {
                    String dimName = expandDims.get(i);
                    dim.setValue(dimName, rowKey.getValue(dimName));
                }
                exists.add(dim);
            }
            for (DimensionValueSet dimValues : dimValuesSet) {
                if (exists.contains(dimValues)) continue;
                this.getRowDatas(dimValues, -1);
            }
        }
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public DimensionValueSet getCurrentRowKey() {
        return this.rowKey;
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
        if (dataValue == null && info.dimensionName != null && this.queryContext.isFormulaRun()) {
            dataValue = rowKeys.getValue(info.dimensionName);
        }
        return this.convertDataValue(info.fieldDefine, dataValue);
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

    public List<DimensionValueSet> getRowKeys() {
        return this.rowMap.keySet().stream().collect(Collectors.toList());
    }

    public void print(StringBuilder buff) {
        Object[] columns = new QueryFieldInfo[this.getFieldInfoSeach().size()];
        for (QueryFieldInfo info : this.getFieldInfoSeach().values()) {
            columns[info.memoryIndex] = info;
        }
        buff.append(Arrays.toString(columns)).append("\n");
        int index = 0;
        int maxCount = 100;
        for (MemoryRowData rowData : this.rowMap.values()) {
            buff.append(rowData).append("\n");
            if (++index < maxCount) continue;
            break;
        }
        if (maxCount < this.rowMap.size()) {
            buff.append("\u66f4\u591a...\n");
        }
        buff.append("\u8bb0\u5f55\u6570:" + this.rowMap.size() + "    \u5217\u6570:" + this.columnCount + "\n");
    }
}

