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
 *  com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader
 *  com.jiuqi.np.dataengine.reader.QueryFieldInfo
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.nr.data.engine.analysis.exe.query;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedGroupingConfig;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisQuerySqlBuilder;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisRowData;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisMemoryDataSetReader
extends AbstractQueryFieldDataReader {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisMemoryDataSetReader.class);
    private Iterator<AnalysisRowData> iterator;
    private DimensionValueSet rowKey = null;
    private AnalysisRowData rowDatas = null;
    private Map<DimensionValueSet, AnalysisRowData> rowMap = new LinkedHashMap<DimensionValueSet, AnalysisRowData>();
    private final Map<Integer, HashMap<DimensionValueSet, Object>> orderMap = new HashMap<Integer, HashMap<DimensionValueSet, Object>>();
    private int columnCount = 0;
    private int[] dataTypes;
    private final List<AnalysisRowData> rows = new ArrayList<AnalysisRowData>();

    public AnalysisMemoryDataSetReader(QueryContext queryContext) {
        super(queryContext);
    }

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

    public QueryFieldInfo putIndex(DataModelDefinitionsCache cache, QueryField queryField, int index) {
        QueryFieldInfo queryFieldInfo = super.putIndex(cache, queryField, index);
        queryFieldInfo.memoryIndex = this.columnCount++;
        return queryFieldInfo;
    }

    public void reset() {
        super.reset();
        this.rowMap.clear();
        this.rowMap.clear();
        this.orderMap.clear();
        this.rowKey = null;
        this.rowDatas = null;
        this.canRead = false;
        this.iterator = null;
        this.dataTypes = null;
    }

    public Object readData(int index) throws Exception {
        if (this.rowDatas != null) {
            Object value = this.rowDatas.readValue(index);
            if (value instanceof BlobValue) {
                return ((BlobValue)value)._getBytes();
            }
            return value;
        }
        return null;
    }

    public boolean next() {
        if (this.iterator == null) {
            this.iterator = this.rows.iterator();
        }
        this.rowDatas = this.iterator.hasNext() ? this.iterator.next() : null;
        this.rowKey = this.rowDatas != null ? this.rowDatas.getRowKey() : null;
        return this.rowDatas != null;
    }

    public void setDataSet(Object dataSet) {
    }

    public void loadDatas(AnalysisContext qContext, AnalysisQuerySqlBuilder builder) throws Exception {
        if (builder.getPrimaryTable() == null || qContext.isDest(builder.getPrimaryTable())) {
            return;
        }
        int memeryStartIndex = builder.getMemoryStartIndex();
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        MemoryDataSet tableDataSet = (MemoryDataSet)builder.runQuery(qContext);
        this.dataTypes = new int[this.columnCount];
        for (QueryFieldInfo info : this.fieldInfoSeach.values()) {
            this.dataTypes[info.memoryIndex] = info.queryField.getDataType();
        }
        for (DataRow row : tableDataSet) {
            DimensionValueSet rowKey = this.buildRowKey(qContext, builder, builder.getLoopDimensions(), rowKeyStartIndex, row);
            this.readRow(qContext, memeryStartIndex, rowKeyStartIndex, row, rowKey);
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

    public AnalysisRowData readRow(AnalysisContext aContext, int memeryStartIndex, int rowKeyStartIndex, DataRow row, DimensionValueSet rowKey) {
        AnalysisRowData datas = this.getRowDatas(aContext, rowKey);
        for (int i = 0; i < rowKeyStartIndex; ++i) {
            Object value = row.getValue(i);
            datas.writeValue(memeryStartIndex + i, value);
        }
        return datas;
    }

    public AnalysisRowData getRowDatas(AnalysisContext aContext, DimensionValueSet rowKey) {
        AnalysisRowData datas = this.rowMap.get(rowKey);
        if (datas == null) {
            datas = new AnalysisRowData(rowKey, this.columnCount, this);
            String batchKeyValue = (String)rowKey.getValue(aContext.getBatchKey());
            if (batchKeyValue != null) {
                int keyOrderIndex = aContext.getKeyOrderIndexes().get(batchKeyValue);
                datas.setKeyOrderIndex(keyOrderIndex);
            }
            this.rowMap.put(rowKey, datas);
            this.rows.add(datas);
        }
        return datas;
    }

    public void loadLeftJoinDatas(AnalysisContext qContext, AnalysisQuerySqlBuilder builder, boolean asFullJoin) throws Exception {
        int memeryStartIndex = builder.getMemoryStartIndex();
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        MemoryDataSet tableDataSet = (MemoryDataSet)builder.runQuery(qContext);
        HashMap<DimensionValueSet, List<DimensionValueSet>> subSetIndex = new HashMap<DimensionValueSet, List<DimensionValueSet>>();
        for (DataRow row : tableDataSet) {
            List matchedRowKeys;
            DimensionValueSet rowKey = this.buildRowKey(qContext, builder, builder.getLoopDimensions(), rowKeyStartIndex, row);
            boolean merged = false;
            if (subSetIndex.isEmpty()) {
                this.buildSubSetIndex(subSetIndex, rowKey);
            }
            if ((matchedRowKeys = (List)subSetIndex.get(rowKey)) != null) {
                for (DimensionValueSet mainRowKey : matchedRowKeys) {
                    this.readRow(qContext, memeryStartIndex, rowKeyStartIndex, row, mainRowKey);
                    merged = true;
                }
            }
            if (merged || !asFullJoin) continue;
            this.readRow(qContext, memeryStartIndex, rowKeyStartIndex, row, rowKey);
        }
    }

    private void buildSubSetIndex(Map<DimensionValueSet, List<DimensionValueSet>> subSetIndex, DimensionValueSet rowKey) {
        for (DimensionValueSet mainRowKey : this.rowMap.keySet()) {
            DimensionValueSet newRowKey = new DimensionValueSet();
            boolean isSubSet = true;
            for (int i = 0; i < rowKey.size(); ++i) {
                String keyName = rowKey.getName(i);
                Object value = mainRowKey.getValue(keyName);
                if (value == null) {
                    isSubSet = false;
                    continue;
                }
                newRowKey.setValue(keyName, value);
            }
            if (!isSubSet) continue;
            List<DimensionValueSet> matchedRowKeys = subSetIndex.get(newRowKey);
            if (matchedRowKeys == null) {
                matchedRowKeys = new ArrayList<DimensionValueSet>();
                subSetIndex.put(newRowKey, matchedRowKeys);
            }
            matchedRowKeys.add(mainRowKey);
        }
    }

    private DimensionValueSet buildRowKey(AnalysisContext qContext, AnalysisQuerySqlBuilder builder, DimensionSet loopDimensions, int rowKeyStartIndex, DataRow row) {
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
                        continue;
                    }
                    rowKey.setValue(dimName, value);
                    continue;
                }
                rowKey.setValue(dimName, value);
                continue;
            }
            rowKey.setValue(dimName, value);
        }
        return rowKey;
    }

    private List<Object> getGroupFieldValues(AnalysisRowData rowDatas, ParsedGroupingConfig groupingConfig) {
        List<QueryField> grouppingFields = groupingConfig.getGroupingQueryFields();
        List<Integer> grouppingFieldIndexes = groupingConfig.getGrouppingFieldIndexes();
        if (grouppingFieldIndexes.isEmpty()) {
            for (QueryField queryField : grouppingFields) {
                int index = this.findMemoryIndex(queryField);
                grouppingFieldIndexes.add(index);
            }
        }
        ArrayList<Object> values = new ArrayList<Object>(grouppingFields.size());
        for (int grouppingFieldIndex : grouppingFieldIndexes) {
            try {
                Object value = rowDatas.readValue(grouppingFieldIndex);
                if (value == null) {
                    value = "";
                }
                values.add(value);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return values;
    }

    public void expandByDims(AnalysisContext aContext, Collection<DimensionValueSet> dims) {
        for (DimensionValueSet dim : dims) {
            this.getRowDatas(aContext, dim);
        }
    }

    public int findMemoryIndex(QueryField queryField) {
        QueryFieldInfo findQueryFieldInfo = this.findQueryFieldInfo(queryField);
        if (findQueryFieldInfo == null) {
            return -1;
        }
        return findQueryFieldInfo.memoryIndex;
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
        for (AnalysisRowData rowData : this.rows) {
            buff.append(rowData).append("\n");
        }
        return buff.toString();
    }

    public void resetBizOrderValue() {
        if (this.orderMap.size() <= 0) {
            return;
        }
        LinkedHashMap<DimensionValueSet, AnalysisRowData> resultValue = new LinkedHashMap<DimensionValueSet, AnalysisRowData>();
        for (Map.Entry<DimensionValueSet, AnalysisRowData> rowItem : this.rowMap.entrySet()) {
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

    public AnalysisRowData getRowDatas() {
        return this.rowDatas;
    }

    public void sort(AnalysisContext aContext) {
        ParsedGroupingConfig groupingConfig = aContext.getGroupingConfig();
        if (groupingConfig != null && groupingConfig.getGroupingQueryFields().size() > 0) {
            for (AnalysisRowData rowDatas : this.rows) {
                rowDatas.setGroupFieldValues(this.getGroupFieldValues(rowDatas, groupingConfig));
            }
        }
        Collections.sort(this.rows);
    }

    public int[] getDataTypes() {
        return this.dataTypes;
    }
}

