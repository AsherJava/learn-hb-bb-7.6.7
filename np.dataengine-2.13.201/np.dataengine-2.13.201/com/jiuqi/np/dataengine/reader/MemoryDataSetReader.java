/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.query.GroupingQuerySqlBuilder;
import com.jiuqi.np.dataengine.query.MemorySteamLoader;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.query.QueryStatLeafHelper;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryRowData;
import com.jiuqi.np.dataengine.reader.MemoryStatLeafRowData;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryDataSetReader
extends AbstractQueryFieldDataReader {
    protected Iterator<DimensionValueSet> iterator;
    protected DimensionValueSet rowKey = null;
    protected MemoryRowData rowDatas = null;
    protected Map<DimensionValueSet, MemoryRowData> rowMap = new LinkedHashMap<DimensionValueSet, MemoryRowData>();
    protected final Map<Integer, HashMap<DimensionValueSet, Object>> orderMap = new HashMap<Integer, HashMap<DimensionValueSet, Object>>();
    protected DimensionSet loopDimensions = null;
    protected int currentIndex = -1;
    protected List<String> mainInnerDimensions;
    private MemorySteamLoader loader;
    public static final int MAX_PRINT_ROWS = 10;
    protected int columnCount = 0;
    protected List<QueryFieldInfo> columns = new ArrayList<QueryFieldInfo>();

    public MemoryDataSetReader() {
    }

    public MemoryDataSetReader(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public Object readData(QueryField queryField) throws Exception {
        QueryFieldInfo info = this.findQueryFieldInfo(queryField);
        return this.readDataByFieldInfo(info);
    }

    public Object readDataByFieldInfo(QueryFieldInfo info) throws Exception {
        DimensionRow dimRow;
        String dimKey;
        if (info == null || this.rowKey == null) {
            return null;
        }
        if (info.dimTable != null && (dimKey = (String)this.rowKey.getValue(info.dimTable.getDimensionName())) != null && (dimRow = info.dimTable.findRowByKey(dimKey)) != null) {
            Object dataValue = dimRow.getValue(info.index);
            return this.convertDataValue(info, dataValue);
        }
        Object dataValue = this.readData(info.memoryIndex);
        return this.convertDataValue(info, dataValue);
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
        if (this.loader != null) {
            boolean hasNext;
            DataRow dataRow = this.loader.next();
            boolean bl = hasNext = dataRow != null;
            if (hasNext) {
                try {
                    QuerySqlBuilder builder = this.loader.getBuilder();
                    boolean isLinkedQuery = !builder.getUnitKeyMap().isEmpty() && !builder.getUnitDimensionMap().isEmpty();
                    Map<String, Map<Object, Object>> relationDim1V1Map = this.getRelationDim1V1Map(this.queryContext, builder, isLinkedQuery);
                    this.rowKey = this.buildRowKey(this.loader.getContext(), builder, this.loopDimensions, this.loader.getRowKeyStartIndex(), dataRow, -1, isLinkedQuery, relationDim1V1Map);
                    this.rowDatas = new MemoryRowData(this.rowKey, this.columns.size());
                    this.rowDatas.loadData(dataRow, false, this.loader.getMemeryStartIndex(), this.loader.getRowKeyStartIndex(), this.loader.getBizKeyOrderIndex());
                    ++this.currentIndex;
                    if (this.currentIndex <= 10 && this.loader.getContext().outFMLPlan()) {
                        this.rowMap.put(this.rowKey, this.rowDatas);
                    }
                }
                catch (Exception e) {
                    this.loader.getContext().getMonitor().exception(e);
                }
            }
            return hasNext;
        }
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
        MemoryDataSet tableDataSet = (MemoryDataSet)builder.runQuery(qContext);
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        HashMap<DimensionValueSet, Integer> rowIndexMap = new HashMap<DimensionValueSet, Integer>();
        int groupingFlag = -1;
        int groupFlagIndex = -1;
        this.loopDimensions = builder.getLoopDimensions();
        if (builder instanceof GroupingQuerySqlBuilder) {
            GroupingQuerySqlBuilder groupingQuerySqlBuilder = (GroupingQuerySqlBuilder)builder;
            if (groupingQuerySqlBuilder.isWantDetail() && tableDataSet.size() == 1) {
                return;
            }
            groupFlagIndex = tableDataSet.getMetadata().getColumnCount() - 1;
        }
        TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(builder.getPrimaryTable().getTableName());
        List<String> floatLoopDimensions = this.buildFloatLoopDimensions(tableInfo, this.loopDimensions);
        boolean isLinkedQuery = !builder.getUnitKeyMap().isEmpty() && !builder.getUnitDimensionMap().isEmpty();
        Map<String, Map<Object, Object>> relationDim1V1Map = this.getRelationDim1V1Map(qContext, builder, isLinkedQuery);
        for (DataRow row : tableDataSet) {
            if (groupFlagIndex > 0) {
                Object groupingValue = row.getValue(groupFlagIndex);
                groupingFlag = Convert.toInt((Object)groupingValue);
            }
            DimensionValueSet rowKey = null;
            rowKey = floatLoopDimensions != null ? this.buildFloatJoinRowKey(qContext, builder, floatLoopDimensions, rowKeyStartIndex, row, groupingFlag, isLinkedQuery, relationDim1V1Map) : this.buildRowKey(qContext, builder, this.loopDimensions, rowKeyStartIndex, row, groupingFlag, isLinkedQuery, relationDim1V1Map);
            if (!this.checkRowKey(qContext, rowKey, groupingFlag)) continue;
            if (needOrderJoin) {
                if (!rowKey.hasValue("RECORDKEY")) {
                    Object dataValue = row.getValue(builder.getBizkeyOrderFieldIndex() - 1);
                    rowKey.setValue("RECORDKEY", dataValue);
                }
                this.tryOrderJoin(needOrderJoin, rowIndexMap, rowKey);
            }
            this.readRow(builder, row, rowKey, groupingFlag, sumDatas);
        }
    }

    private Map<String, Map<Object, Object>> getRelationDim1V1Map(QueryContext qContext, QuerySqlBuilder builder, boolean isLinkedQuery) {
        Map<String, Map<Object, Object>> relationDim1V1Map = null;
        if (isLinkedQuery) {
            String linkAlias = qContext.getTableLinkAliaMap().get(builder.getPrimaryTable());
            relationDim1V1Map = qContext.getRelationDim1V1Map(linkAlias);
        }
        return relationDim1V1Map;
    }

    protected List<String> buildFloatLoopDimensions(TableModelRunInfo tableInfo, DimensionSet loopDimensions) {
        ArrayList<String> floatLoopDimensions = null;
        if (tableInfo.getInnerDimensions() != null) {
            if (this.mainInnerDimensions == null) {
                this.mainInnerDimensions = new ArrayList<String>(tableInfo.getInnerDimensions());
            } else if (tableInfo.getInnerDimensions().size() <= this.mainInnerDimensions.size()) {
                floatLoopDimensions = new ArrayList<String>(loopDimensions.size());
                for (int i = 0; i < loopDimensions.size(); ++i) {
                    String dimension = loopDimensions.get(i);
                    int index = tableInfo.getInnerDimensions().indexOf(dimension);
                    if (index >= 0) {
                        floatLoopDimensions.add(this.mainInnerDimensions.get(index));
                        continue;
                    }
                    floatLoopDimensions.add(dimension);
                }
            }
        }
        return floatLoopDimensions;
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

    private void readRow(QuerySqlBuilder builder, DataRow row, DimensionValueSet rowKey, int groupingFlag, boolean sumDatas) {
        int memeryStartIndex = builder.getMemoryStartIndex();
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        int bizKeyOrderIndex = builder.getBizkeyOrderFieldIndex() - 1;
        if (this.queryContext.needStatLeaf()) {
            List<DimensionValueSet> parentRowKeys;
            QueryStatLeafHelper statLeafHelper = this.queryContext.getStatLeafHelper();
            if (statLeafHelper.needDataRow(rowKey)) {
                MemoryRowData memoryRowData = this.getRowDatas(rowKey, groupingFlag);
                memoryRowData.loadData(row, sumDatas, memeryStartIndex, rowKeyStartIndex, bizKeyOrderIndex);
            }
            if ((parentRowKeys = statLeafHelper.getParentRowKeys(rowKey)) != null) {
                for (DimensionValueSet parentRowKey : parentRowKeys) {
                    MemoryStatLeafRowData parentRow = (MemoryStatLeafRowData)this.rowMap.get(parentRowKey);
                    if (parentRow == null) {
                        parentRow = new MemoryStatLeafRowData(parentRowKey, this.columns);
                        this.rowMap.put(parentRowKey, parentRow);
                    }
                    parentRow.loadData(row, sumDatas, memeryStartIndex, rowKeyStartIndex, bizKeyOrderIndex);
                }
            }
        } else {
            MemoryRowData memoryRowData = this.getRowDatas(rowKey, groupingFlag);
            memoryRowData.loadData(row, sumDatas, memeryStartIndex, rowKeyStartIndex, bizKeyOrderIndex);
        }
    }

    public MemoryRowData getRowDatas(DimensionValueSet rowKey, int groupingFlag) {
        MemoryRowData datas = this.rowMap.get(rowKey);
        if (datas == null) {
            datas = new MemoryRowData(rowKey, this.columns.size());
            datas.setGroup_flag(groupingFlag);
            this.rowMap.put(rowKey, datas);
        }
        return datas;
    }

    public void loadLeftJoinDatas(QueryContext qContext, QuerySqlBuilder builder, boolean asFullJoin) throws Exception {
        MemoryDataSet tableDataSet = (MemoryDataSet)builder.runQuery(qContext);
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        int groupingFlag = -1;
        int groupFlagIndex = -1;
        if (builder instanceof GroupingQuerySqlBuilder) {
            GroupingQuerySqlBuilder groupingQuerySqlBuilder = (GroupingQuerySqlBuilder)builder;
            if (groupingQuerySqlBuilder.isWantDetail() && tableDataSet.size() == 1) {
                return;
            }
            groupFlagIndex = tableDataSet.getMetadata().getColumnCount() - 1;
        }
        DimensionSet leftLoopDimensions = builder.getLoopDimensions();
        boolean hasSurplusDim = false;
        if (leftLoopDimensions.containsAll(this.loopDimensions)) {
            hasSurplusDim = true;
        }
        if (leftLoopDimensions.size() == 1 && !asFullJoin && groupFlagIndex < 0) {
            String leftJoinDimName = leftLoopDimensions.get(0);
            HashMap<Object, Integer> leftJoinRowMap = new HashMap<Object, Integer>();
            for (int i = 0; i < tableDataSet.size(); ++i) {
                DataRow row = tableDataSet.get(i);
                Object leftJoinValue = row.getValue(rowKeyStartIndex);
                leftJoinRowMap.put(leftJoinValue, i);
            }
            for (DimensionValueSet mainRowKey : this.rowMap.keySet()) {
                DataRow row;
                Integer rowIndex;
                Object dimValue = mainRowKey.getValue(leftJoinDimName);
                if (dimValue == null || (rowIndex = (Integer)leftJoinRowMap.get(dimValue)) == null || (row = tableDataSet.get(rowIndex.intValue())) == null) continue;
                this.readRow(builder, row, mainRowKey, groupingFlag, false);
            }
        } else {
            DimensionSet rowKeyDimensions;
            DimensionSet loopDimensions = builder.getLoopDimensions();
            TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(builder.getPrimaryTable().getTableName());
            List<String> floatLoopDimensions = this.buildFloatLoopDimensions(tableInfo, loopDimensions);
            ArrayList<String> indexNames = new ArrayList<String>();
            for (int i = 0; i < loopDimensions.size(); ++i) {
                String leftName = leftLoopDimensions.get(i);
                if (!this.loopDimensions.contains(leftName)) continue;
                indexNames.add(leftName);
            }
            if (this.rowMap.size() > 0 && (rowKeyDimensions = this.rowMap.keySet().iterator().next().getDimensionSet()).size() > loopDimensions.size()) {
                for (int i = 0; i < loopDimensions.size(); ++i) {
                    String leftName = leftLoopDimensions.get(i);
                    if (this.loopDimensions.contains(leftName) || !rowKeyDimensions.contains(leftName)) continue;
                    indexNames.add(leftName);
                }
            }
            HashMap hashJoinMap = new HashMap();
            for (DimensionValueSet dimensionValueSet : this.rowMap.keySet()) {
                DimensionValueSet rowDim = new DimensionValueSet();
                for (String indexName : indexNames) {
                    Object value = dimensionValueSet.getValue(indexName);
                    rowDim.setValue(indexName, value);
                }
                List values = hashJoinMap.computeIfAbsent(rowDim, k -> new ArrayList());
                values.add(dimensionValueSet);
            }
            boolean isLinkedQuery = !builder.getUnitKeyMap().isEmpty() && !builder.getUnitDimensionMap().isEmpty();
            Map<String, Map<Object, Object>> relationDim1V1Map = this.getRelationDim1V1Map(qContext, builder, isLinkedQuery);
            for (DataRow row : tableDataSet) {
                if (groupFlagIndex > 0) {
                    Object groupingValue = row.getValue(groupFlagIndex);
                    groupingFlag = Convert.toInt((Object)groupingValue);
                }
                DimensionValueSet rowKey = null;
                rowKey = floatLoopDimensions != null ? this.buildFloatJoinRowKey(qContext, builder, floatLoopDimensions, rowKeyStartIndex, row, groupingFlag, isLinkedQuery, relationDim1V1Map) : this.buildRowKey(qContext, builder, loopDimensions, rowKeyStartIndex, row, groupingFlag, isLinkedQuery, relationDim1V1Map);
                if (hasSurplusDim) {
                    for (int i = rowKey.size() - 1; i >= 0; --i) {
                        String dim = rowKey.getName(i);
                        if (this.loopDimensions.contains(dim)) continue;
                        rowKey.clearValue(dim);
                    }
                    this.readRow(builder, row, rowKey, groupingFlag, false);
                    continue;
                }
                boolean merged = false;
                if (!this.checkRowKey(qContext, rowKey, groupingFlag)) continue;
                List dimensionValueSets = hashJoinMap.getOrDefault(rowKey, Collections.emptyList());
                for (DimensionValueSet mainRowKey : dimensionValueSets) {
                    this.readRow(builder, row, mainRowKey, groupingFlag, false);
                    merged = true;
                }
                if (merged || !asFullJoin) continue;
                this.readRow(builder, row, rowKey, groupingFlag, false);
            }
        }
    }

    protected DimensionValueSet buildRowKey(QueryContext qContext, QuerySqlBuilder builder, DimensionSet loopDimensions, int rowKeyStartIndex, DataRow row, int groupingFlag, boolean isLinkedQuery, Map<String, Map<Object, Object>> relationDim1V1Map) {
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
            if (qContext.needStatLeaf() || value == null || value instanceof List) {
                value = row.getValue(i + rowKeyStartIndex);
            }
            if (value instanceof byte[]) {
                value = Convert.toUUID((Object)value);
            } else if (value instanceof BlobValue) {
                value = Convert.toUUID((byte[])((BlobValue)value)._getBytes());
            }
            if (isLinkedQuery) {
                String originalDim = builder.getUnitDimensionMap().get(dimName);
                if (originalDim != null) {
                    Object originalValue = builder.getUnitKeyMap().get(value.toString());
                    if (originalValue != null) {
                        rowKey.setValue(originalDim, originalValue);
                    } else {
                        rowKey.setValue(originalDim, value);
                    }
                } else {
                    rowKey.setValue(dimName, value);
                }
            } else {
                rowKey.setValue(dimName, value);
            }
            if (groupingFlag < 0) continue;
            rowKey.setValue("GroupingDeep", (groupingFlag + 1) / 2);
        }
        if (isLinkedQuery) {
            this.reSetRowKeyByDimRelation(qContext.getExeContext().getUnitDimension(), rowKey, relationDim1V1Map);
        }
        return rowKey;
    }

    protected boolean checkRowKey(QueryContext qContext, DimensionValueSet rowKey, int groupingFlag) {
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
        HashSet<DimensionValueSet> existDims = null;
        if (this.rowMap.size() > 0) {
            DimensionValueSet firstDestRowKey = this.rowMap.keySet().iterator().next();
            DimensionValueSet firstSrcRowKey = dims.iterator().next();
            if (firstDestRowKey.size() > firstSrcRowKey.size()) {
                existDims = new HashSet<DimensionValueSet>();
                for (DimensionValueSet destRowKey : this.rowMap.keySet()) {
                    DimensionValueSet newRowKey = new DimensionValueSet();
                    for (int i = 0; i < destRowKey.size(); ++i) {
                        String dimName = destRowKey.getName(i);
                        if (!firstSrcRowKey.hasValue(dimName)) continue;
                        newRowKey.setValue(dimName, destRowKey.getValue(i));
                    }
                    existDims.add(newRowKey);
                }
            }
        }
        if (existDims != null) {
            for (DimensionValueSet dim : dims) {
                if (existDims.contains(dim)) continue;
                this.getRowDatas(dim, -1);
            }
        } else {
            for (DimensionValueSet dim : dims) {
                this.getRowDatas(dim, -1);
            }
        }
    }

    public int getColumnCount() {
        return this.columns.size();
    }

    public DimensionValueSet getCurrentRowKey() {
        return this.rowKey;
    }

    public void setLoopDimensions(DimensionSet loopDimensions) {
        this.loopDimensions = loopDimensions;
    }

    public int size() {
        return this.rowMap.size();
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("rowCount:" + this.rowMap.size() + "    columnCount:" + this.columns.size() + "\n");
        buff.append(this.columns).append("\n");
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

    public void print(StringBuilder buff) {
        buff.append(this.columns).append("\n");
        int index = 0;
        int maxCount = 10;
        for (MemoryRowData rowData : this.rowMap.values()) {
            buff.append(rowData).append("\n");
            if (++index < maxCount) continue;
            break;
        }
        if (maxCount < this.rowMap.size()) {
            buff.append("\u66f4\u591a...\n");
        }
        int count = this.rowMap.size();
        if (this.loader != null) {
            count = this.currentIndex + 1;
        }
        buff.append("\u8bb0\u5f55\u6570:" + count + "    \u5217\u6570:" + this.columns.size() + "\n");
    }

    public List<String> getMainInnerDimensions() {
        return this.mainInnerDimensions;
    }

    protected DimensionValueSet buildFloatJoinRowKey(QueryContext qContext, QuerySqlBuilder builder, List<String> loopDimensions, int rowKeyStartIndex, DataRow row, int groupingFlag, boolean isLinkedQuery, Map<String, Map<Object, Object>> relationDim1V1Map) {
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
            if (isLinkedQuery) {
                String originalDim = builder.getUnitDimensionMap().get(dimName);
                if (originalDim != null) {
                    Object originalValue = builder.getUnitKeyMap().get(value.toString());
                    if (originalValue != null) {
                        rowKey.setValue(originalDim, originalValue);
                    } else {
                        rowKey.setValue(originalDim, value);
                    }
                } else {
                    rowKey.setValue(dimName, value);
                }
            } else {
                rowKey.setValue(dimName, value);
            }
            if (groupingFlag < 0) continue;
            rowKey.setValue("GroupingDeep", (groupingFlag + 1) / 2);
        }
        if (isLinkedQuery) {
            this.reSetRowKeyByDimRelation(qContext.getExeContext().getUnitDimension(), rowKey, relationDim1V1Map);
        }
        return rowKey;
    }

    public void reSetRowKeyByDimRelation(String unitDimension, DimensionValueSet rowKey, Map<String, Map<Object, Object>> relationDim1V1Map) {
        if (relationDim1V1Map != null && relationDim1V1Map.size() > 0) {
            for (Map.Entry<String, Map<Object, Object>> entry : relationDim1V1Map.entrySet()) {
                String dimName = entry.getKey();
                Map<Object, Object> dimValueMap = relationDim1V1Map.get(dimName);
                if (dimValueMap == null) continue;
                if (dimValueMap.isEmpty()) {
                    rowKey.clearValue(dimName);
                    continue;
                }
                Object unitKey = rowKey.getValue(unitDimension);
                Object dimValue = dimValueMap.get(unitKey);
                if (dimValue != null) {
                    rowKey.setValue(dimName, dimValue);
                    continue;
                }
                rowKey.clearValue(dimName);
            }
        }
    }

    public void setStreamLoader(MemorySteamLoader loader) throws Exception {
        this.loader = loader;
    }
}

