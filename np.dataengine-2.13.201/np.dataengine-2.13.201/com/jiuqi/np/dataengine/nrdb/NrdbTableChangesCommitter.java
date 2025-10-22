/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 *  com.jiuqi.nvwa.memdb.api.DBTable
 *  com.jiuqi.nvwa.memdb.api.DBTransaction
 *  com.jiuqi.nvwa.memdb.api.query.DBFilterByExpression
 *  com.jiuqi.nvwa.memdb.api.record.ArrayRecord
 *  com.jiuqi.nvwa.nrdb.NrdbStorageManager
 */
package com.jiuqi.np.dataengine.nrdb;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.RowState;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.UnsafeOperationException;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.ITableNameFinder;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.nrdb.NrdbAccessUtils;
import com.jiuqi.np.dataengine.nrdb.NrdbWriteColumn;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.TableChangesCommitter;
import com.jiuqi.np.dataengine.setting.FieldsInfoImpl;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import com.jiuqi.nvwa.memdb.api.DBTable;
import com.jiuqi.nvwa.memdb.api.DBTransaction;
import com.jiuqi.nvwa.memdb.api.query.DBFilterByExpression;
import com.jiuqi.nvwa.memdb.api.record.ArrayRecord;
import com.jiuqi.nvwa.nrdb.NrdbStorageManager;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class NrdbTableChangesCommitter
extends TableChangesCommitter {
    public NrdbTableChangesCommitter(QueryContext qContext, QueryTable queryTable, HashMap<QueryField, List<Integer>> queryFields, TableModelRunInfo tableRunInfo, FieldsInfoImpl fieldsInfoImpl, FieldsInfoImpl systemFields, Connection connection, boolean designTimeData, DimensionValueSet masterKeys, boolean needCheckKeys, QueryParam queryParam) {
        super(qContext, queryTable, queryFields, tableRunInfo, fieldsInfoImpl, systemFields, connection, designTimeData, masterKeys, needCheckKeys, queryParam);
        this.canModifyKey = false;
    }

    @Override
    public void deleteRows(DimensionValueSet masterKeys, String rowFilter, HashMap<QueryField, ArrayList<Object>> colFilterValues, IMonitor monitor, DimensionValueSet deleteKeys) throws Exception {
        String physicalTableName = this.getTableName();
        TableModelRunInfo physicalTableInfo = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(physicalTableName);
        DimensionSet openDimensions = this.queryTable.getOpenDimensions();
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        DimensionValueSet delMasterkeys = new DimensionValueSet();
        List<ColumnModelDefine> dimFields = physicalTableInfo.getDimFields();
        ArrayList<Object> dbFilters = new ArrayList<Object>();
        try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(physicalTableInfo.getTableModelDefine());
             DBTransaction transaction = dbTable.newTransaction();){
            Map<String, String> tableCondition;
            for (ColumnModelDefine columnModelDefine : dimFields) {
                Object rv;
                String string = physicalTableInfo.getDimensionName(columnModelDefine.getCode());
                Object argValue = masterKeys.getValue(string);
                if (argValue == null && deleteKeys.hasValue(string)) {
                    argValue = deleteKeys.getValue(string);
                }
                if (dimensionRestriction != null && (rv = dimensionRestriction.getValue(string)) != null) {
                    argValue = rv;
                }
                if (openDimensions.contains(string) && argValue == null) {
                    String fieldCode = columnModelDefine.getCode();
                    if (!fieldCode.equals("MDCODE") && !fieldCode.equals("DATATIME") && !fieldCode.equals("DW")) continue;
                    throw new UnsafeOperationException("\u7f3a\u5931\u4e86\u4e3b\u7ef4\u5ea6\u6216\u65f6\u671f\u6761\u4ef6,\u4e0d\u5141\u8bb8\u6267\u884c\u5220\u9664\uff01");
                }
                if (this.updateDataTable != null) {
                    delMasterkeys.setValue(string, argValue);
                }
                dbFilters.add(NrdbAccessUtils.getColumnDBFilter(columnModelDefine, argValue));
            }
            if (!StringUtils.isEmpty((String)rowFilter)) {
                dbFilters.add(new DBFilterByExpression(rowFilter));
            }
            if (colFilterValues != null && colFilterValues.size() > 0) {
                for (Map.Entry entry : colFilterValues.entrySet()) {
                    QueryField queryField = (QueryField)entry.getKey();
                    ArrayList arrayList = (ArrayList)entry.getValue();
                    if (!this.queryFields.containsKey(queryField) || arrayList.size() <= 0) continue;
                    dbFilters.add(NrdbAccessUtils.getColumnDBFilter(queryField.getFieldName(), queryField.getDataType(), arrayList));
                }
            }
            if ((tableCondition = this.getTableCondition(physicalTableName)) != null) {
                for (Map.Entry<String, String> entry : tableCondition.entrySet()) {
                    dbFilters.add(NrdbAccessUtils.getColumnDBFilter(entry.getKey(), 6, entry.getValue()));
                }
            }
            if (delMasterkeys.size() > 0) {
                UpdateDataRecord updateDataRecord = new UpdateDataRecord();
                updateDataRecord.setRowkeys(delMasterkeys);
                this.updateDataTable.getDeleteRecords().add(updateDataRecord);
            }
            transaction.deleteBy(dbFilters);
            transaction.commit();
        }
    }

    @Override
    public void deleteRows(List<DataRowImpl> deleteRows, Set<String> currentPeriodSet, IMonitor monitor) throws Exception {
        String physicalTableName = this.getTableName();
        TableModelRunInfo physicalTableInfo = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(physicalTableName);
        List<ColumnModelDefine> dimFields = physicalTableInfo.getDimFields();
        try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(physicalTableInfo.getTableModelDefine());
             DBTransaction transaction = dbTable.newTransaction();){
            Map<String, String> tableCondition = this.getTableCondition(physicalTableName);
            for (DataRowImpl deleteRow : deleteRows) {
                UpdateDataRecord deleteRecord = this.createUpdateRecord();
                ArrayList<Object> keyValues = new ArrayList<Object>();
                for (ColumnModelDefine columnModelDefine : dimFields) {
                    String dimension = physicalTableInfo.getDimensionName(columnModelDefine.getCode());
                    Object keyValue = this.getKeyValue(tableCondition, deleteRow, columnModelDefine.getName(), dimension, RowState.DELETE);
                    keyValues.add(keyValue);
                    if (deleteRecord == null) continue;
                    deleteRecord.getRowkeys().setValue(dimension, keyValue);
                }
                if (deleteRecord != null) {
                    for (Map.Entry entry : this.queryFields.entrySet()) {
                        QueryField item = (QueryField)entry.getKey();
                        List columnIndexs = (List)entry.getValue();
                        Object dataValue = deleteRow.internalGetValue(columnIndexs);
                        deleteRecord.addData(item.getFieldName(), item.getDataType(), item.getFractionDigits(), null, dataValue);
                    }
                    this.addToUpdateDataTable(deleteRecord, RowState.DELETE);
                }
                transaction.delete(keyValues);
            }
            transaction.commit();
        }
    }

    @Override
    public void insertRows(List<DataRowImpl> insertRows, IMonitor monitor) throws Exception {
        this.writeRows(insertRows, RowState.ADD);
    }

    @Override
    public void updateRows(List<DataRowImpl> updateRows, IMonitor monitor) throws Exception {
        this.writeRows(updateRows, RowState.MODIFIED);
    }

    @Override
    public void upsertRows(List<DataRowImpl> updateRows, IMonitor monitor) throws Exception {
        this.writeRows(updateRows, RowState.UPSERT);
    }

    @Override
    public void updateRow(DataRowImpl updateRow, IMonitor monitor) throws Exception {
        this.writeRows(Collections.singletonList(updateRow), RowState.UPSERT);
    }

    private void writeRows(List<DataRowImpl> updateRows, RowState rowState) throws Exception {
        ColumnModelDefine bizOrderField;
        String physicalTableName = this.getTableName();
        TableModelRunInfo physicalTableInfo = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(physicalTableName);
        List<NrdbWriteColumn> columns = this.buildWriteColumns(physicalTableName);
        Map<String, String> tableCondition = this.getTableCondition(physicalTableName);
        int bizOrderIndex = -1;
        if (rowState == RowState.ADD && (bizOrderField = physicalTableInfo.getBizOrderField()) != null && !this.bizOrderIsDim) {
            bizOrderIndex = this.addBizOrderField(bizOrderField, columns);
        }
        try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(physicalTableInfo.getTableModelDefine());
             DBTransaction transaction = dbTable.newTransaction((Collection)columns.stream().map(column -> column.getName()).collect(Collectors.toList()));){
            for (DataRowImpl updateRow : updateRows) {
                UpdateDataRecord updateRecord = this.createUpdateRecord();
                ArrayRecord record = new ArrayRecord(columns.size());
                for (NrdbWriteColumn column2 : columns) {
                    String dimension = column2.getDimensionName();
                    if (dimension != null) {
                        Object keyValue = this.getKeyValue(tableCondition, updateRow, column2.getName(), dimension, rowState);
                        column2.writerValue(this.qContext, record, keyValue);
                        if (updateRecord == null) continue;
                        updateRecord.getRowkeys().setValue(dimension, keyValue);
                        continue;
                    }
                    if (column2.getMetaIndex() == bizOrderIndex) {
                        column2.writerValue(this.qContext, record, UUID.randomUUID().toString());
                        continue;
                    }
                    Object dataValue = updateRow.internalGetValue(column2.getIndexes());
                    column2.writerValue(this.qContext, record, dataValue);
                    if (updateRecord == null) continue;
                    updateRecord.addData(column2.getName(), column2.getDataType(), dataValue, updateRow.internalGetOldValue(column2.getIndexes()));
                }
                if (updateRecord != null) {
                    this.addToUpdateDataTable(updateRecord, rowState);
                }
                if (rowState == RowState.MODIFIED) {
                    transaction.update((DBRecord)record);
                    continue;
                }
                if (rowState == RowState.UPSERT) {
                    transaction.upsert((DBRecord)record);
                    continue;
                }
                if (rowState != RowState.ADD) continue;
                transaction.insert((DBRecord)record);
            }
            transaction.commit();
        }
    }

    private List<NrdbWriteColumn> buildWriteColumns(String physicalTableName) throws ParseException {
        TableModelRunInfo tableInfo = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(physicalTableName);
        List<ColumnModelDefine> dimFields = tableInfo.getDimFields();
        ArrayList<NrdbWriteColumn> columns = new ArrayList<NrdbWriteColumn>();
        for (int i = 0; i < dimFields.size(); ++i) {
            ColumnModelDefine dimField = dimFields.get(i);
            String dimension = tableInfo.getDimensionName(dimField.getCode());
            NrdbWriteColumn column = new NrdbWriteColumn(dimension, dimField.getName(), DataTypesConvert.fieldTypeToDataType(dimField.getColumnType()), columns.size(), dimField.getSceneId());
            columns.add(column);
        }
        for (Map.Entry queryItem : this.queryFields.entrySet()) {
            QueryField queryField = (QueryField)queryItem.getKey();
            List indexes = (List)queryItem.getValue();
            if (this.tableRunInfo.isKeyField(queryField.getFieldCode())) continue;
            NrdbWriteColumn column = new NrdbWriteColumn(queryField.getFieldName(), indexes, queryField.getDataType(), columns.size(), queryField.getSceneId());
            columns.add(column);
        }
        return columns;
    }

    private Object getKeyValue(Map<String, String> tableCondition, DataRowImpl dataRow, String columnName, String dimension, RowState rowState) {
        String coditionValue;
        Object keyValue = this.getOldKeyValue(dataRow, dimension);
        if (tableCondition != null && (coditionValue = tableCondition.get(columnName)) != null) {
            keyValue = coditionValue;
        }
        if (keyValue == null && rowState == RowState.ADD && dimension.equals("RECORDKEY")) {
            keyValue = UUID.randomUUID().toString();
        }
        return keyValue;
    }

    private Map<String, String> getTableCondition(String physicalTableName) {
        Map<String, String> tableCondition = null;
        ITableNameFinder tableNameFinder = this.qContext.getTableNameFinder();
        if (tableNameFinder != null) {
            tableCondition = tableNameFinder.getTableCondition(this.qContext.getExeContext(), physicalTableName);
        }
        return tableCondition;
    }

    private void addToUpdateDataTable(UpdateDataRecord updateRecord, RowState rowState) {
        if (this.updateDataTable != null) {
            if (rowState == RowState.MODIFIED || rowState == RowState.UPSERT) {
                this.updateDataTable.getUpdateRecords().put(updateRecord.getRowkeys(), updateRecord);
            } else if (rowState == RowState.ADD) {
                this.updateDataTable.getInsertRecords().add(updateRecord);
            } else if (rowState == RowState.DELETE) {
                this.updateDataTable.getDeleteRecords().add(updateRecord);
            }
        }
    }

    private UpdateDataRecord createUpdateRecord() {
        UpdateDataRecord updateRecord = null;
        if (this.updateDataTable != null) {
            updateRecord = new UpdateDataRecord();
            DimensionValueSet rowkeys = new DimensionValueSet();
            updateRecord.setRowkeys(rowkeys);
        }
        return updateRecord;
    }

    private int addBizOrderField(ColumnModelDefine bizOrderField, List<NrdbWriteColumn> columns) {
        NrdbWriteColumn column = new NrdbWriteColumn(null, bizOrderField.getName(), DataTypesConvert.fieldTypeToDataType(bizOrderField.getColumnType()), columns.size(), bizOrderField.getSceneId());
        columns.add(column);
        return column.getMetaIndex();
    }
}

