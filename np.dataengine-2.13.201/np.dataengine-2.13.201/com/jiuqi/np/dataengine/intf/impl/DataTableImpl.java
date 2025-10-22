/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.memdb.api.DBCursor
 *  com.jiuqi.nvwa.memdb.api.DBMetadata
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 *  com.jiuqi.nvwa.memdb.api.DBTable
 *  com.jiuqi.nvwa.memdb.api.query.DBFilter
 *  com.jiuqi.nvwa.memdb.api.query.DBQuery
 *  com.jiuqi.nvwa.memdb.api.query.DBQueryBuilder
 *  com.jiuqi.nvwa.nrdb.NrdbStorageManager
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DefaultExpression;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.FieldValidateResult;
import com.jiuqi.np.dataengine.common.OperateRowType;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.RowExpressionValidResult;
import com.jiuqi.np.dataengine.common.RowState;
import com.jiuqi.np.dataengine.common.RowValidateResult;
import com.jiuqi.np.dataengine.common.ValidateResult;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.dataengine.event.RevokeRowEvent;
import com.jiuqi.np.dataengine.event.RowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.OperateRowBreakException;
import com.jiuqi.np.dataengine.exception.OperateRowException;
import com.jiuqi.np.dataengine.exception.ValueValidateException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IValueValidateHandler;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.nrdb.NrdbAccessUtils;
import com.jiuqi.np.dataengine.nrdb.NrdbTableChangesCommitter;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.TableChangesCommitter;
import com.jiuqi.np.dataengine.query.account.AccountDataTableImpl;
import com.jiuqi.np.dataengine.reader.DataRowReader;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.np.dataengine.util.FieldSqlConditionUtil;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.memdb.api.DBCursor;
import com.jiuqi.nvwa.memdb.api.DBMetadata;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import com.jiuqi.nvwa.memdb.api.DBTable;
import com.jiuqi.nvwa.memdb.api.query.DBFilter;
import com.jiuqi.nvwa.memdb.api.query.DBQuery;
import com.jiuqi.nvwa.memdb.api.query.DBQueryBuilder;
import com.jiuqi.nvwa.nrdb.NrdbStorageManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataTableImpl
extends ReadonlyTableImpl
implements IDataTable {
    private static final Logger logger = LoggerFactory.getLogger(DataTableImpl.class);
    protected ArrayList<DataRowImpl> emptyDataRows = new ArrayList();
    private IMonitor monitor;
    protected boolean needCheckKeys;
    protected boolean onlyUpdate = false;
    private boolean ignoreEvent = false;
    private boolean onlySaveData = false;
    private HashMap<String, ColumnModelDefine> dimFields = null;
    private boolean initContext = false;
    private List<InsertRowEvent> insertRowEvents = null;
    private List<UpdateRowEvent> updateRowEvents = null;
    private List<DeleteRowEvent> deleteRowEvents = null;
    private List<DeleteAllRowEvent> deleteAllRowEvents = null;
    private final Set<String> currentPeriodSet = new HashSet<String>();
    private boolean ignoreResetCache = false;
    private final List<IParsedExpression> validExpressions = new ArrayList<IParsedExpression>();

    public DataTableImpl(QueryContext qContext, DimensionValueSet masterKeys, int columnCount) {
        super(qContext, masterKeys, columnCount);
        AbstractMonitor abstractMonitor;
        IMonitor contextMonitor;
        IMonitor iMonitor = contextMonitor = qContext == null ? null : qContext.getMonitor();
        if (contextMonitor != null && contextMonitor instanceof AbstractMonitor && (abstractMonitor = (AbstractMonitor)contextMonitor).getDataChangeListeners().size() > 0) {
            this.monitor = abstractMonitor;
        }
    }

    @Override
    public boolean getIsReadOnly() {
        return false;
    }

    @Override
    public IDataRow appendRow(DimensionValueSet rowKeys) throws IncorrectQueryException {
        this.checkNewRowKeys(rowKeys);
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        DataRowImpl dataRowImpl = new DataRowImpl(this, rowKeys, rowDatas);
        dataRowImpl.setRowState(RowState.ADD);
        this.initDataRow(dataRowImpl, true);
        if (rowKeys.isAllNull()) {
            dataRowImpl.rowKeysEmpty = true;
        }
        this.dataRows.add(dataRowImpl);
        this.addedRowToCache(dataRowImpl);
        return dataRowImpl;
    }

    @Override
    public IDataRow newEmptyRow(boolean initDefaultValues) {
        DimensionSet rowDimensions = this.getRowDimensions();
        DimensionValueSet rowKeys = rowDimensions == null ? new DimensionValueSet() : new DimensionValueSet(this.getRowDimensions());
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        DataRowImpl dataRowImpl = new DataRowImpl(this, rowKeys, rowDatas);
        dataRowImpl.setRowState(RowState.ADD);
        dataRowImpl.notInitialized = true;
        dataRowImpl.rowKeysEmpty = true;
        this.emptyDataRows.add(dataRowImpl);
        this.initDataRow(dataRowImpl, initDefaultValues);
        return dataRowImpl;
    }

    @Override
    public int deleteRow(DimensionValueSet rowKeys) throws IncorrectQueryException {
        DataRowImpl dataRowImpl;
        if (this.getMasterKeys().size() == 0 && rowKeys.isAllNull()) {
            throw new IncorrectQueryException("\u4e0d\u652f\u6301\u5220\u9664\u6240\u6709\u8bb0\u5f55");
        }
        if (!rowKeys.hasAnyNull() && (dataRowImpl = (DataRowImpl)this.findRow(rowKeys)) != null) {
            this.deleteRow(dataRowImpl);
            return 1;
        }
        int deleteCount = 0;
        for (DataRowImpl dataRowImpl2 : this.dataRows) {
            if (!rowKeys.isSubsetOf(dataRowImpl2.getRowKeys())) continue;
            this.deleteRow(dataRowImpl2);
            ++deleteCount;
        }
        return deleteCount;
    }

    @Override
    public void deleteByIndex(int rowIndex) {
        DataRowImpl dataRowImpl = (DataRowImpl)this.dataRows.get(rowIndex);
        if (dataRowImpl == null) {
            return;
        }
        this.deleteRow(dataRowImpl);
    }

    protected void deleteRow(DataRowImpl deleteRow) {
        deleteRow.delete();
        String rowKeyData = deleteRow.getRowKeys().toString();
        if (this.rowKeySearch != null) {
            this.rowKeySearch.remove(rowKeyData);
        }
    }

    @Override
    public void deleteAll() {
        for (DataRowImpl dataRowImpl : this.dataRows) {
            this.deleteRow(dataRowImpl);
        }
    }

    @Override
    public boolean commitChanges(boolean abandonDataTable) throws Exception {
        return this.commitChanges(false, abandonDataTable, true);
    }

    @Override
    public boolean commitChanges(boolean ignoreLogicCheck, boolean abandonDataTable) throws Exception {
        return this.commitChanges(ignoreLogicCheck, abandonDataTable, true);
    }

    @Override
    public boolean commitChanges(boolean ignoreLogicCheck, boolean abandonDataTable, boolean cascadeDeletion) throws Exception {
        ArrayList<DataRowImpl> revokeRows;
        ArrayList<DataRowImpl> updateRows;
        ArrayList<DataRowImpl> deleteRows;
        ArrayList<DataRowImpl> insertRows;
        boolean hasChange;
        if (this.qContext != null) {
            this.setCalcRowContext(this.qContext);
        }
        if (!(hasChange = this.checkRowValid(insertRows = new ArrayList<DataRowImpl>(), deleteRows = new ArrayList<DataRowImpl>(), updateRows = new ArrayList<DataRowImpl>(), revokeRows = new ArrayList<DataRowImpl>(), ignoreLogicCheck))) {
            return false;
        }
        this.emptyDataRows.clear();
        this.wholeTableCheck(insertRows, updateRows, deleteRows);
        this.commitChanges(this.getMasterKeys(), false, insertRows, updateRows, deleteRows, revokeRows, this.getQueryVersionStartDate(), this.getQueryVersionDate(), cascadeDeletion);
        if (abandonDataTable) {
            return true;
        }
        this.refreshTableRows(insertRows, updateRows, deleteRows);
        return true;
    }

    private boolean checkRowValid(List<DataRowImpl> insertRows, List<DataRowImpl> deleteRows, List<DataRowImpl> updateRows, List<DataRowImpl> revokeRows, boolean ignoreLogicCheck) throws Exception {
        boolean hasChange = false;
        ValueValidateException valueMergeResult = new ValueValidateException("\u5f53\u524d\u884c\u6570\u636e\u6821\u9a8c\u4e0d\u901a\u8fc7\u3002");
        ExpressionValidateException expMergeResult = new ExpressionValidateException("\u5f53\u524d\u6570\u636e\u6821\u9a8c\u516c\u5f0f\u6267\u884c\u4e0d\u901a\u8fc7\u3002");
        boolean validError = false;
        boolean expValidError = false;
        for (DataRowImpl dataRowImpl : this.dataRows) {
            try {
                switch (dataRowImpl.rowState) {
                    case NONE: {
                        break;
                    }
                    case ADD: {
                        hasChange = true;
                        dataRowImpl.validateExpression(this.validExpressions);
                        dataRowImpl.validateAll(ignoreLogicCheck);
                        insertRows.add(dataRowImpl);
                        break;
                    }
                    case MODIFIED: {
                        hasChange = true;
                        dataRowImpl.validateExpression(this.validExpressions);
                        dataRowImpl.validateAll(ignoreLogicCheck);
                        updateRows.add(dataRowImpl);
                        break;
                    }
                    case DELETE: {
                        hasChange = true;
                        deleteRows.add(dataRowImpl);
                        break;
                    }
                    case REVOKE: {
                        hasChange = true;
                        revokeRows.add(dataRowImpl);
                        break;
                    }
                }
            }
            catch (ValueValidateException e) {
                valueMergeResult.merge(e);
                validError = true;
            }
            catch (ExpressionValidateException ex) {
                expMergeResult.merge(ex);
                expValidError = true;
            }
            catch (Exception e) {
                throw e;
            }
        }
        if (expValidError) {
            throw expMergeResult;
        }
        if (validError) {
            throw valueMergeResult;
        }
        return hasChange;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void commitChanges(DimensionValueSet masterKeys, boolean deleteAllRows, List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> deleteRows, List<DataRowImpl> revokeRows, Date queryStartVersionDate, Date queryVersionDate, boolean cascadeDeletion) throws Exception {
        if (this.monitor instanceof AbstractMonitor) {
            AbstractMonitor abstractMonitor = (AbstractMonitor)this.monitor;
            abstractMonitor.setFieldsInfo(this.getFieldsInfo());
        }
        ArrayList<TableChangesCommitter> committers = new ArrayList<TableChangesCommitter>();
        HashMap<QueryTable, HashMap<QueryField, List<Integer>>> queryRegion = new HashMap<QueryTable, HashMap<QueryField, List<Integer>>>();
        for (int index = 0; index < this.queryfields.size(); ++index) {
            List<Integer> indexCols;
            HashMap fields;
            QueryField queryField = (QueryField)this.queryfields.get(index);
            if (queryField == null) continue;
            QueryTable queryTable = queryField.getTable();
            if (!queryRegion.containsKey(queryTable)) {
                fields = new HashMap();
                indexCols = new LinkedList<Integer>();
                indexCols.add(index);
                fields.put(queryField, indexCols);
                queryRegion.put(queryTable, fields);
                continue;
            }
            fields = (HashMap)queryRegion.get(queryTable);
            if (!fields.containsKey(queryField)) {
                indexCols = new LinkedList();
                indexCols.add(index);
                fields.put(queryField, indexCols);
                continue;
            }
            indexCols = (List)fields.get(queryField);
            indexCols.add(index);
        }
        UpdateDataSet updateDataSet = null;
        if (this.monitor != null) {
            updateDataSet = new UpdateDataSet();
        }
        Connection connection = this.queryParam.getConnection();
        try {
            committers.addAll(this.createCommitters(connection, queryRegion, updateDataSet, queryStartVersionDate, queryVersionDate));
            this.beforeOperateEvent(committers, deleteAllRows, deleteRows, insertRows, updateRows);
            try {
                TableChangesCommitter committer;
                int commitCount = committers.size();
                if (deleteAllRows) {
                    boolean deleteAll = true;
                    DimensionValueSet deleteKeys = new DimensionValueSet();
                    if (this.monitor != null) {
                        deleteAll = this.monitor.beforeDeleteAll(masterKeys, deleteKeys);
                    }
                    if (deleteAll) {
                        String rowFilter = this.getRowFilter();
                        HashMap<QueryField, ArrayList<Object>> colFilterValues = this.getFilterValuesByField();
                        for (int index = 0; index < commitCount; ++index) {
                            TableChangesCommitter committer2 = committers.get(index);
                            committer2.deleteRows(masterKeys, rowFilter, colFilterValues, this.monitor, deleteKeys);
                            this.publishDeleteEventToAuthService(null, masterKeys, committer2, true);
                        }
                        if (this.monitor != null) {
                            this.monitor.afterDeleteAll(masterKeys, deleteKeys, this.rowFilterNode);
                        }
                    } else {
                        logger.info("deleteAll cancled by monitor.");
                    }
                }
                if (this.needCheckKeys && !(this instanceof AccountDataTableImpl)) {
                    this.doCheckRowKeys(insertRows, updateRows, deleteRows);
                }
                if (deleteRows.size() > 0) {
                    ArrayList<DimensionValueSet> delRowKeys = new ArrayList<DimensionValueSet>(deleteRows.size());
                    if (this.monitor != null) {
                        for (DataRowImpl deleteRow : deleteRows) {
                            delRowKeys.add(deleteRow.getRowKeys());
                        }
                        this.monitor.beforeDelete(delRowKeys);
                    }
                    for (int index = 0; index < commitCount; ++index) {
                        committer = committers.get(index);
                        committer.deleteRows(deleteRows, this.currentPeriodSet, this.monitor);
                        this.publishDeleteEventToAuthService(deleteRows, null, committer, false);
                    }
                    if (this.monitor != null) {
                        this.monitor.afterDelete(delRowKeys);
                    }
                }
                if (revokeRows.size() > 0) {
                    for (int index = 0; index < commitCount; ++index) {
                        TableChangesCommitter committer3 = committers.get(index);
                        committer3.revokeRows(revokeRows, this.monitor);
                    }
                }
                if (updateRows.size() > 0) {
                    ArrayList<IDataRow> rows = new ArrayList<IDataRow>(updateRows.size());
                    if (this.monitor != null) {
                        for (DataRowImpl updateRow : updateRows) {
                            rows.add(updateRow);
                        }
                        this.monitor.beforeUpdate(rows);
                    }
                    for (int index = 0; index < commitCount; ++index) {
                        committer = committers.get(index);
                        if (commitCount <= 1) {
                            committer.updateRows(updateRows, this.monitor);
                            continue;
                        }
                        committer.upsertRows(updateRows, this.monitor);
                    }
                    if (this.monitor != null) {
                        this.monitor.afterUpdate(rows);
                    }
                }
                if (insertRows.size() > 0) {
                    for (int index = 0; index < commitCount; ++index) {
                        TableChangesCommitter committer4 = committers.get(index);
                        committer4.insertRows(insertRows, this.monitor);
                    }
                }
            }
            catch (Exception ex) {
                logger.error("\u6570\u636e\u63d0\u4ea4\u51fa\u9519\u3002", ex);
                try {
                    this.monitor.onCommitException(ex, this.insertRowEvents, this.updateRowEvents, this.deleteRowEvents, this.deleteAllRowEvents);
                    this.exceptionOperateEvent(ex, committers, deleteRows, insertRows, updateRows, revokeRows);
                }
                catch (Exception e) {
                    logger.error("\u6570\u636e\u63d0\u4ea4\u5f02\u5e38\u540e\u7f6e\u5904\u7406\u51fa\u9519\u3002", ex);
                }
                throw ex;
            }
            this.afterOperateEvent(committers, deleteRows, insertRows, updateRows, revokeRows);
            if (!this.ignoreResetCache) {
                for (TableChangesCommitter committer : committers) {
                    TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                    this.resetEntityCache(tableRunInfo);
                    this.resetDataCache(tableRunInfo);
                    this.publishUpdateEventToAuthService(tableRunInfo);
                }
            }
            if (this.monitor != null) {
                this.monitor.onDataChange(updateDataSet);
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    public ArrayList<TableChangesCommitter> createCommitters(Connection connection, HashMap<QueryTable, HashMap<QueryField, List<Integer>>> queryRegion, UpdateDataSet updateDataSet, Date queryStartVersionDate, Date queryVersionDate) {
        ArrayList<TableChangesCommitter> committers = new ArrayList<TableChangesCommitter>();
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        for (Map.Entry<QueryTable, HashMap<QueryField, List<Integer>>> regionPair : queryRegion.entrySet()) {
            TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(regionPair.getKey().getTableName());
            TableChangesCommitter commiter = this.qContext.isEnableNrdb() && tableRunInfo.getTableModelDefine().isSupportNrdb() ? new NrdbTableChangesCommitter(this.qContext, regionPair.getKey(), regionPair.getValue(), tableRunInfo, this.fieldsInfoImpl, this.systemFieldsInfo, connection, this.designTimeData, this.masterKeys, this.needCheckKeys, this.queryParam) : new TableChangesCommitter(this.qContext, regionPair.getKey(), regionPair.getValue(), tableRunInfo, this.fieldsInfoImpl, this.systemFieldsInfo, connection, this.designTimeData, this.masterKeys, this.needCheckKeys, this.queryParam);
            commiter.setOnlySaveData(this.onlySaveData);
            commiter.setUpdateDataTable(updateDataSet);
            committers.add(commiter);
        }
        return committers;
    }

    private void resetDataCache(TableModelRunInfo tableRunInfo) {
        if (this.queryParam == null || this.queryParam.getEntityResetCacheService() == null) {
            return;
        }
        boolean isEntityTable = this.isEntityTable(tableRunInfo);
        if (!isEntityTable) {
            return;
        }
        this.queryParam.getEntityResetCacheService().resetDataCache(tableRunInfo.getTableModelDefine().getID());
    }

    private HashMap<QueryField, ArrayList<Object>> getFilterValuesByField() {
        HashMap<QueryField, ArrayList<Object>> filterValueMap = new HashMap<QueryField, ArrayList<Object>>();
        if (this.colFilterValues == null || this.colFilterValues.size() <= 0) {
            return filterValueMap;
        }
        for (Map.Entry colFilterValue : this.colFilterValues.entrySet()) {
            QueryField queryField;
            int fieldIndex = (Integer)colFilterValue.getKey();
            if (fieldIndex >= this.queryfields.size() || (queryField = (QueryField)this.queryfields.get(fieldIndex)) == null) continue;
            filterValueMap.put(queryField, (ArrayList<Object>)colFilterValue.getValue());
        }
        return filterValueMap;
    }

    private void exceptionOperateEvent(Exception ex, ArrayList<TableChangesCommitter> committers, List<DataRowImpl> deleteRows, List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> revokeRows) {
        if (this.ignoreEvent) {
            return;
        }
        OperateRowEventListener eventListener = this.queryParam.getEventListener();
        if (eventListener == null || !eventListener.hasListerner()) {
            return;
        }
        if (this.insertRowEvents == null && this.updateRowEvents == null && this.deleteRowEvents == null && this.deleteAllRowEvents == null) {
            return;
        }
        for (int index = 0; index < committers.size(); ++index) {
            InsertRowEvent insertRowEvent = this.insertRowEvents == null || this.insertRowEvents.size() <= index ? null : this.insertRowEvents.get(index);
            UpdateRowEvent updateRowEvent = this.updateRowEvents == null || this.updateRowEvents.size() <= index ? null : this.updateRowEvents.get(index);
            DeleteRowEvent deleteRowEvent = this.deleteRowEvents == null || this.deleteRowEvents.size() <= index ? null : this.deleteRowEvents.get(index);
            DeleteAllRowEvent deleteAllRowEvent = this.deleteAllRowEvents == null || this.deleteAllRowEvents.size() <= index ? null : this.deleteAllRowEvents.get(index);
            eventListener.exceptionHandler(ex, insertRowEvent, updateRowEvent, deleteRowEvent, deleteAllRowEvent);
        }
    }

    private void afterOperateEvent(ArrayList<TableChangesCommitter> committers, List<DataRowImpl> deleteRows, List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> revokeRows) throws OperateRowException {
        if (this.ignoreEvent) {
            return;
        }
        OperateRowEventListener eventListener = this.queryParam.getEventListener();
        if (eventListener == null || !eventListener.hasListerner()) {
            return;
        }
        this.afterDeleteEvent(committers, deleteRows, eventListener);
        this.afterRevokeEvent(committers, revokeRows, eventListener);
        this.afterUpdateEvent(committers, updateRows, eventListener);
        this.afterInsertEvent(committers, insertRows, eventListener);
    }

    private void afterInsertEvent(ArrayList<TableChangesCommitter> committers, List<DataRowImpl> insertRows, OperateRowEventListener eventListener) throws OperateRowException {
        if (insertRows.size() > 0) {
            if (this.insertRowEvents != null && this.insertRowEvents.size() > 0) {
                for (InsertRowEvent insertRowEvent : this.insertRowEvents) {
                    eventListener.afterInsert(insertRowEvent);
                }
                return;
            }
            ArrayList<IDataRow> eventRows = new ArrayList<IDataRow>(insertRows);
            for (TableChangesCommitter committer : committers) {
                TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                InsertRowEvent insertRowEvent = new InsertRowEvent();
                insertRowEvent.setInsertRows(eventRows);
                this.initRowEvent(insertRowEvent, tableRunInfo.getTableModelDefine().getID());
                eventListener.afterInsert(insertRowEvent);
            }
        }
    }

    private void afterUpdateEvent(ArrayList<TableChangesCommitter> committers, List<DataRowImpl> updateRows, OperateRowEventListener eventListener) throws OperateRowException {
        if (updateRows.size() > 0) {
            if (this.updateRowEvents != null && this.updateRowEvents.size() > 0) {
                for (UpdateRowEvent updateRowEvent : this.updateRowEvents) {
                    eventListener.afterUpdate(updateRowEvent);
                }
                return;
            }
            ArrayList<IDataRow> eventRows = new ArrayList<IDataRow>(updateRows);
            for (TableChangesCommitter committer : committers) {
                TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                UpdateRowEvent updateRowEvent = new UpdateRowEvent();
                updateRowEvent.setUpdateRows(eventRows);
                this.initRowEvent(updateRowEvent, tableRunInfo.getTableModelDefine().getID());
                eventListener.afterUpdate(updateRowEvent);
            }
        }
    }

    private void afterRevokeEvent(ArrayList<TableChangesCommitter> committers, List<DataRowImpl> revokeRows, OperateRowEventListener eventListener) {
        ArrayList<DimensionValueSet> revokeItems = new ArrayList<DimensionValueSet>();
        for (DataRowImpl revokeRow : revokeRows) {
            revokeItems.add(revokeRow.getRowKeys());
        }
        if (revokeItems.size() > 0) {
            for (TableChangesCommitter committer : committers) {
                TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                RevokeRowEvent revokeRowEvent = new RevokeRowEvent();
                revokeRowEvent.setRevokeRows(revokeItems);
                revokeRowEvent.setTableKey(tableRunInfo.getTableModelDefine().getID());
                eventListener.afterRevoke(revokeRowEvent);
            }
        }
    }

    private void afterDeleteEvent(ArrayList<TableChangesCommitter> committers, List<DataRowImpl> deleteRows, OperateRowEventListener eventListener) throws OperateRowException {
        if (this.deleteRowEvents != null && this.deleteRowEvents.size() > 0) {
            for (DeleteRowEvent deleteRowEvent : this.deleteRowEvents) {
                eventListener.afterDelete(deleteRowEvent);
            }
            return;
        }
        ArrayList<DimensionValueSet> deleteItems = new ArrayList<DimensionValueSet>();
        for (DataRowImpl deleteRow : deleteRows) {
            deleteItems.add(deleteRow.getRowKeys());
        }
        if (deleteItems.size() > 0) {
            for (TableChangesCommitter committer : committers) {
                TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                DeleteRowEvent deleteRowEvent = new DeleteRowEvent();
                deleteRowEvent.setDeleteRows(deleteItems);
                deleteRowEvent.setTableKey(tableRunInfo.getTableModelDefine().getID());
                eventListener.afterDelete(deleteRowEvent);
            }
        }
    }

    private void initRowEvent(RowEvent rowEvent, String tableKey) {
        Object periodValue;
        rowEvent.setTableKey(tableKey);
        rowEvent.setQueryStartVersionDate(this.getQueryVersionStartDate());
        rowEvent.setQueryVersionDate(this.getQueryVersionDate());
        rowEvent.setOnlySaveData(this.onlySaveData);
        Object object = periodValue = this.masterKeys == null ? null : this.masterKeys.getValue("DATATIME");
        if (periodValue != null && periodValue instanceof String) {
            rowEvent.setPeriodCode(periodValue.toString());
        }
    }

    private void beforeOperateEvent(ArrayList<TableChangesCommitter> committers, Boolean deleteAllRows, List<DataRowImpl> deleteRows, List<DataRowImpl> insertRows, List<DataRowImpl> updateRows) throws OperateRowBreakException {
        if (this.ignoreEvent) {
            return;
        }
        OperateRowEventListener eventListener = this.queryParam.getEventListener();
        if (eventListener == null || !eventListener.hasListerner()) {
            return;
        }
        this.beforeDeleteAllEvent(committers, deleteAllRows, eventListener);
        this.beforeDeleteEvent(committers, deleteRows, eventListener);
        this.beforeUpdateEvent(committers, updateRows, eventListener);
        this.beforeInsertEvent(committers, insertRows, eventListener);
    }

    private void beforeDeleteAllEvent(ArrayList<TableChangesCommitter> committers, Boolean deleteAllRows, OperateRowEventListener eventListener) throws OperateRowBreakException {
        if (deleteAllRows.booleanValue()) {
            ArrayList<DeleteAllRowEvent> deleteAllRowEvents = new ArrayList<DeleteAllRowEvent>(committers.size());
            for (TableChangesCommitter committer : committers) {
                TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                DeleteAllRowEvent deleteAllRowEvent = new DeleteAllRowEvent();
                deleteAllRowEvent.setTableKey(tableRunInfo.getTableModelDefine().getID());
                try {
                    deleteAllRowEvent.setRowFilter(this.getRowFilter());
                }
                catch (Exception e) {
                    throw new OperateRowBreakException(e.getMessage(), e);
                }
                deleteAllRowEvent.setAllFields(tableRunInfo.getAllFields());
                deleteAllRowEvent.setMasterKeys(committer.getMasterKeys());
                deleteAllRowEvents.add(deleteAllRowEvent);
                eventListener.beforeAllDelete(deleteAllRowEvent);
                if (!deleteAllRowEvent.isBreak()) continue;
                throw new OperateRowBreakException(this.getExceptionMessage(OperateRowType.DELETEALL, deleteAllRowEvent.getMessage()));
            }
            this.deleteAllRowEvents = deleteAllRowEvents;
        }
    }

    private void beforeInsertEvent(ArrayList<TableChangesCommitter> committers, List<DataRowImpl> insertRows, OperateRowEventListener eventListener) throws OperateRowBreakException {
        if (insertRows.size() > 0) {
            ArrayList<InsertRowEvent> insertRowEvents = new ArrayList<InsertRowEvent>(committers.size());
            ArrayList<IDataRow> eventRows = new ArrayList<IDataRow>(insertRows);
            for (TableChangesCommitter committer : committers) {
                TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                InsertRowEvent insertRowEvent = new InsertRowEvent();
                insertRowEvent.setInsertRows(eventRows);
                this.initRowEvent(insertRowEvent, tableRunInfo.getTableModelDefine().getID());
                insertRowEvents.add(insertRowEvent);
                eventListener.beforeInsert(insertRowEvent);
                if (!insertRowEvent.isBreak()) continue;
                throw new OperateRowBreakException(this.getExceptionMessage(OperateRowType.INSERT, insertRowEvent.getMessage()));
            }
            this.insertRowEvents = insertRowEvents;
        }
    }

    private void beforeUpdateEvent(ArrayList<TableChangesCommitter> committers, List<DataRowImpl> updateRows, OperateRowEventListener eventListener) throws OperateRowBreakException {
        if (updateRows.size() > 0) {
            ArrayList<UpdateRowEvent> updateRowEvents = new ArrayList<UpdateRowEvent>(committers.size());
            ArrayList<IDataRow> eventRows = new ArrayList<IDataRow>(updateRows);
            for (TableChangesCommitter committer : committers) {
                TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                UpdateRowEvent updateRowEvent = new UpdateRowEvent();
                updateRowEvent.setUpdateRows(eventRows);
                this.initRowEvent(updateRowEvent, tableRunInfo.getTableModelDefine().getID());
                updateRowEvents.add(updateRowEvent);
                eventListener.beforeUpdate(updateRowEvent);
                if (!updateRowEvent.isBreak()) continue;
                throw new OperateRowBreakException(this.getExceptionMessage(OperateRowType.UPDATE, updateRowEvent.getMessage()));
            }
            this.updateRowEvents = updateRowEvents;
        }
    }

    private void beforeDeleteEvent(ArrayList<TableChangesCommitter> committers, List<DataRowImpl> deleteRows, OperateRowEventListener eventListener) throws OperateRowBreakException {
        ArrayList<DimensionValueSet> deleteItems = new ArrayList<DimensionValueSet>();
        for (DataRowImpl deleteRow : deleteRows) {
            deleteItems.add(deleteRow.getRowKeys());
        }
        if (deleteItems.size() > 0) {
            ArrayList<DeleteRowEvent> deleteRowEvents = new ArrayList<DeleteRowEvent>(committers.size());
            for (TableChangesCommitter committer : committers) {
                TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
                DeleteRowEvent deleteRowEvent = new DeleteRowEvent();
                deleteRowEvent.setDeleteRows(deleteItems);
                deleteRowEvent.setTableKey(tableRunInfo.getTableModelDefine().getID());
                deleteRowEvent.setAllFields(tableRunInfo.getAllFields());
                deleteRowEvents.add(deleteRowEvent);
                eventListener.beforeDelete(deleteRowEvent);
                if (!deleteRowEvent.isBreak()) continue;
                throw new OperateRowBreakException(this.getExceptionMessage(OperateRowType.DELETE, deleteRowEvent.getMessage()));
            }
            this.deleteRowEvents = deleteRowEvents;
        }
    }

    private String getExceptionMessage(OperateRowType rowType, String message) {
        String resultMsg = message;
        switch (rowType) {
            case INSERT: {
                resultMsg = "\u65b0\u589e\u6570\u636e\u884c\u5df2\u88ab\u4e2d\u65ad\uff0c\u4e2d\u65ad\u539f\u56e0\uff1a" + message;
                break;
            }
            case UPDATE: {
                resultMsg = "\u66f4\u65b0\u6570\u636e\u884c\u5df2\u88ab\u4e2d\u65ad\uff0c\u4e2d\u65ad\u539f\u56e0\uff1a" + message;
                break;
            }
            case DELETE: {
                resultMsg = "\u5220\u9664\u6570\u636e\u884c\u5df2\u88ab\u4e2d\u65ad\uff0c\u4e2d\u65ad\u539f\u56e0\uff1a" + message;
                break;
            }
            case DELETEALL: {
                resultMsg = "\u5168\u90e8\u5220\u9664\u6570\u636e\u884c\u5df2\u88ab\u4e2d\u65ad\uff0c\u4e2d\u65ad\u539f\u56e0\uff1a" + message;
                break;
            }
        }
        return resultMsg;
    }

    private void publishUpdateEventToAuthService(TableModelRunInfo tableRunInfo) {
        boolean isEntityTable = this.isEntityTable(tableRunInfo);
        if (!isEntityTable) {
            return;
        }
    }

    private void publishDeleteEventToAuthService(List<DataRowImpl> deleteRows, DimensionValueSet masterKeys, TableChangesCommitter committer, boolean deleteAll) {
        TableModelRunInfo tableRunInfo = committer.getTableRunInfo();
        boolean isEntityTable = this.isEntityTable(tableRunInfo);
        if (!isEntityTable) {
            return;
        }
        String entityTableKey = tableRunInfo.getTableModelDefine().getID();
        EntityIdentityService entityLinkService = this.queryParam.getEntityLinkService();
        if (entityLinkService == null) {
            return;
        }
        DimensionSet dimensionSet = tableRunInfo.getDimensions();
        if (deleteAll) {
            if (dimensionSet.size() == 1) {
                if (masterKeys.size() > 0) {
                    Object dimValue = masterKeys.getValue(dimensionSet.get(0));
                    if (dimValue == null) {
                        return;
                    }
                    if (dimValue instanceof List) {
                        List dimList = (List)dimValue;
                        for (Object currentDim : dimList) {
                            entityLinkService.revokeEntity(entityTableKey, currentDim.toString());
                        }
                    } else {
                        entityLinkService.revokeEntity(entityTableKey, dimValue.toString());
                    }
                } else {
                    entityLinkService.revokeEntityTable(entityTableKey);
                }
            } else {
                logger.warn("\u5b9e\u4f53\u8868\u5b58\u5728\u591a\u4e2a\u7ef4\u5ea6\uff0c\u76ee\u524d\u6682\u4e0d\u652f\u6301\u3002");
            }
            return;
        }
        if (dimensionSet.size() == 1) {
            String dimName = dimensionSet.get(0);
            for (DataRowImpl dataRowImpl : deleteRows) {
                Object dimValue = committer.getKeyValue(dataRowImpl, dimName);
                if (dimValue == null) continue;
                entityLinkService.revokeEntity(entityTableKey, dimValue.toString());
            }
        } else {
            logger.warn("\u5b9e\u4f53\u8868\u5b58\u5728\u591a\u4e2a\u7ef4\u5ea6\uff0c\u76ee\u524d\u6682\u4e0d\u652f\u6301\u3002");
        }
    }

    private String getRowFilter() throws Exception {
        if (this.rowFilterNode == null) {
            return null;
        }
        try {
            if (!this.qContext.isEnableNrdb()) {
                if (!this.rowFilterNode.support(Language.SQL)) {
                    throw new Exception("\u6574\u8868\u5220\u9664\u64cd\u4f5c\u5bf9\u4e8e\u5f53\u524d\u8fc7\u6ee4\u6761\u4ef6 " + this.rowFilterNode.interpret((IContext)this.qContext, Language.FORMULA, null) + " \u4e0d\u9002\u7528");
                }
                SQLInfoDescr conditionSqlINfo = new SQLInfoDescr(this.queryParam.getDatabase(), true, 0, 0, true);
                String rowFilter = this.rowFilterNode.interpret((IContext)this.qContext, Language.SQL, (Object)conditionSqlINfo);
                return rowFilter;
            }
            String rowFilter = this.rowFilterNode.interpret((IContext)this.qContext, Language.FORMULA, (Object)new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA, false));
            return rowFilter;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private void resetEntityCache(TableModelRunInfo tableRunInfo) {
        boolean isEntityTable = this.isEntityTable(tableRunInfo);
        if (!isEntityTable) {
            return;
        }
        if (this.queryParam == null || this.queryParam.getEntityResetCacheService() == null) {
            return;
        }
        this.queryParam.getEntityResetCacheService().resetCache(tableRunInfo.getTableModelDefine().getID());
    }

    private boolean isEntityTable(TableModelRunInfo tableRunInfo) {
        return false;
    }

    public void setCalcRowContext(QueryContext queryContext) throws ParseException {
        if (this.initContext) {
            return;
        }
        this.initContext = true;
        IQueryFieldDataReader reader = this.getDataReader(queryContext);
        reader.reset();
        Integer index = 0;
        while (index < this.queryfields.size()) {
            QueryField queryField = (QueryField)this.queryfields.get(index);
            if (queryField != null && reader.findIndex(queryField) < 0) {
                reader.putIndex(queryContext.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, index);
            }
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
        }
    }

    protected void refreshTableRows(List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> deleteRows) {
        for (DataRowImpl dataRowImpl : updateRows) {
            for (Map.Entry<Integer, Object> valuePair : dataRowImpl.modifiedDatas.entrySet()) {
                dataRowImpl.rowDatas.set(valuePair.getKey(), valuePair.getValue());
            }
            dataRowImpl.modifiedDatas.clear();
            dataRowImpl.setRowState(RowState.NONE);
        }
        for (DataRowImpl dataRowImpl : insertRows) {
            if (dataRowImpl.rowDatas.size() <= 0) {
                for (int i = 0; i < this.queryfields.size(); ++i) {
                    dataRowImpl.rowDatas.add(null);
                }
            }
            for (Map.Entry<Integer, Object> valuePair : dataRowImpl.modifiedDatas.entrySet()) {
                dataRowImpl.rowDatas.set(valuePair.getKey(), valuePair.getValue());
            }
            dataRowImpl.modifiedDatas.clear();
            dataRowImpl.setRowState(RowState.NONE);
        }
        for (DataRowImpl dataRowImpl : deleteRows) {
            this.dataRows.remove(dataRowImpl);
        }
    }

    protected void wholeTableCheck(List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> deleteRows) {
    }

    public void delayInitRow(DimensionValueSet rowKeys, DataRowImpl dataRowImpl) {
        this.emptyDataRows.remove(dataRowImpl);
        this.dataRows.add(dataRowImpl);
        this.addedRowToCache(dataRowImpl);
    }

    public void recalculate(DataRowImpl dataRowImpl) {
        try {
            if (this.calcExprs == null) {
                this.calcExprs = this.getCalcExprs(this.qContext);
            }
            if (this.calcExprs.size() <= 0) {
                return;
            }
            IQueryFieldDataReader dataReader = this.getDataReader(this.qContext);
            dataReader.setDataSet(dataRowImpl);
            for (Map.Entry calcExpr : this.calcExprs.entrySet()) {
                IExpression calcExp = (IExpression)calcExpr.getValue();
                QueryFields queryFields = ExpressionUtils.getAllQueryFields((IASTNode)calcExp);
                boolean rowCalc = true;
                for (QueryField queryField : queryFields) {
                    int fieldIndex = dataReader.findIndex(queryField);
                    if (fieldIndex >= 0) continue;
                    rowCalc = false;
                    break;
                }
                if (rowCalc) {
                    Object calcResult = ((IExpression)calcExpr.getValue()).evaluate((IContext)this.qContext);
                    dataRowImpl.setValue((Integer)calcExpr.getKey(), calcResult);
                    continue;
                }
                this.evalCalcExpression((String)this.calcFormulas.get(calcExpr.getKey()), (Integer)calcExpr.getKey(), this.qContext, dataRowImpl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void evalCalcExpression(String expression, Integer fieldIndex, QueryContext qContext, DataRowImpl dataRowImpl) throws ExpressionException, DataTypeException {
        this.queryParam.setSameConnection(true);
        try {
            ExpressionEvaluatorImpl evaluatorImpl = new ExpressionEvaluatorImpl(this.queryParam);
            AbstractData calcData = evaluatorImpl.eval(expression, qContext.getExeContext(), dataRowImpl.getRowKeys());
            dataRowImpl.setValue(fieldIndex, calcData.getAsObject());
        }
        finally {
            this.queryParam.setSameConnection(false);
        }
    }

    private IQueryFieldDataReader getDataReader(QueryContext queryContext) {
        IQueryFieldDataReader dataReader = queryContext.getDataReader();
        if (dataReader == null || !(dataReader instanceof DataRowReader)) {
            dataReader = new DataRowReader(queryContext);
            queryContext.setDataReader(dataReader);
        }
        return dataReader;
    }

    public void verifyDataRow(DataRowImpl dataRowImpl) throws Exception {
        if (this.validExprs == null) {
            this.validExprs = this.getValidExprs(this.qContext);
        }
        if (this.validExprs.size() <= 0) {
            return;
        }
        this.getDataReader(this.qContext).setDataSet(dataRowImpl);
        for (Map.Entry validExpr : this.validExprs.entrySet()) {
            Object validResult = ((IExpression)validExpr.getValue()).evaluate((IContext)this.qContext);
            if (!(validResult instanceof Boolean) || ((Boolean)validResult).booleanValue()) continue;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine((Integer)validExpr.getKey());
            String filedValue = dataRowImpl.getAsString((Integer)validExpr.getKey());
            throw new Exception(String.format("\u6307\u6807%s\u7684\u503c%s\u4e0d\u7b26\u5408\u6821\u9a8c\u89c4\u5219%s", fieldDefine.getTitle(), filedValue, fieldDefine.getDescription()));
        }
    }

    protected void initDataRow(DataRowImpl dataRowImpl, boolean initDefaultValues) {
        try {
            dataRowImpl.buildRow();
            this.initDefaultDimValues(dataRowImpl);
            if (!initDefaultValues) {
                return;
            }
            if (this.defExprs == null) {
                this.defExprs = this.getDefExprs(this.qContext);
            }
            if (this.defExprs.size() <= 0) {
                return;
            }
            this.getDataReader(this.qContext).setDataSet(dataRowImpl);
            for (Map.Entry defExpr : this.defExprs.entrySet()) {
                try {
                    Object calcResult = ((IExpression)defExpr.getValue()).evaluate((IContext)this.qContext);
                    dataRowImpl.directSet((Integer)defExpr.getKey(), calcResult);
                }
                catch (SyntaxException e) {
                    FieldDefine field = this.getFieldsInfo().getFieldDefine((Integer)defExpr.getKey());
                    StringBuilder msg = new StringBuilder();
                    msg.append("\u5b57\u6bb5 [").append(field.getCode()).append("]\u8bbe\u7f6e\u8868\u8fbe\u5f0f\u9ed8\u8ba4\u503c\u51fa\u9519:").append(field.getDefaultValue());
                    logger.error(msg.toString());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void initDefaultDimValues(DataRowImpl dataRowImpl) {
        try {
            if (this.dimDefaultExprs == null) {
                this.dimDefaultExprs = this.getDimDefaultExprs(this.qContext);
            }
            if (this.dimDefaultExprs.size() <= 0) {
                return;
            }
            this.getDataReader(this.qContext).setDataSet(dataRowImpl);
            for (DefaultExpression defaultExpression : this.dimDefaultExprs) {
                boolean hasValue = dataRowImpl.hasKeyValue(defaultExpression.getFieldDefine());
                if (hasValue) continue;
                Object calcResult = defaultExpression.getExpression().evaluate((IContext)this.qContext);
                dataRowImpl.setKeyValue(defaultExpression.getFieldDefine(), calcResult);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected void checkNewRowKeys(DimensionValueSet rowKeys) throws IncorrectQueryException {
        if (this.rowKeySearch == null) {
            this.buildRowKeySearch();
        }
        if (this.rowKeySearch.containsKey(rowKeys.toString())) {
            ArrayList<DimensionValueSet> rowKeysArray = new ArrayList<DimensionValueSet>();
            rowKeysArray.add(rowKeys);
            throw new DuplicateRowKeysException(rowKeysArray);
        }
    }

    private HashMap<Integer, IExpression> getCalcExprs(QueryContext qContext) {
        if (this.calcFormulas == null) {
            this.calcFormulas = new HashMap();
        }
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        HashMap<Integer, IExpression> calcExprs = new HashMap<Integer, IExpression>();
        for (int index = 0; index < this.fieldsInfoImpl.getFieldCount(); ++index) {
            ColumnModelDefine column;
            IExpression expression;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(index);
            if (fieldDefine == null || (expression = dataDefinitionsCache.getFieldCalculation(qContext, column = dataDefinitionsCache.getColumnModel(fieldDefine))) == null) continue;
            calcExprs.put(index, expression);
            this.calcFormulas.put(index, null);
        }
        return calcExprs;
    }

    private HashMap<Integer, IExpression> getValidExprs(QueryContext qContext) {
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        HashMap<Integer, IExpression> validExprs = new HashMap<Integer, IExpression>();
        for (int index = 0; index < this.fieldsInfoImpl.getFieldCount(); ++index) {
            ColumnModelDefine column;
            IExpression expression;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(index);
            if (fieldDefine == null || (expression = dataDefinitionsCache.getFieldVerification(qContext, column = dataDefinitionsCache.getColumnModel(fieldDefine))) == null) continue;
            validExprs.put(index, expression);
        }
        return validExprs;
    }

    private HashMap<Integer, IExpression> getDefExprs(QueryContext qContext) {
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        HashMap<Integer, IExpression> defExprs = new HashMap<Integer, IExpression>();
        for (int index = 0; index < this.fieldsInfoImpl.getFieldCount(); ++index) {
            IExpression expression;
            ColumnModelDefine column;
            TableModelDefine tableModel;
            TableModelRunInfo tableRunInfo;
            boolean isKeyField;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(index);
            if (fieldDefine == null || StringUtils.isEmpty((String)fieldDefine.getDefaultValue()) || (isKeyField = (tableRunInfo = dataDefinitionsCache.getTableInfoByCode((tableModel = dataDefinitionsCache.getTableModel(column = dataDefinitionsCache.getColumnModel(fieldDefine))).getCode())).isKeyField(column.getCode())) || (expression = dataDefinitionsCache.getFieldDefaultValue(qContext, fieldDefine)) == null) continue;
            defExprs.put(index, expression);
        }
        return defExprs;
    }

    private List<DefaultExpression> getDimDefaultExprs(QueryContext qContext) {
        ArrayList<DefaultExpression> defExprs = new ArrayList<DefaultExpression>();
        QueryField queryField = this.getQueryField();
        if (queryField == null) {
            return defExprs;
        }
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(queryField.getTableName());
        if (tableRunInfo == null) {
            return defExprs;
        }
        List<ColumnModelDefine> dimFields = tableRunInfo.getDimFields();
        for (int index = 0; index < dimFields.size(); ++index) {
            IExpression expression;
            ColumnModelDefine fieldDefine = dimFields.get(index);
            FieldDefine column = dataDefinitionsCache.getFieldDefine(fieldDefine);
            if (fieldDefine == null || StringUtils.isEmpty((String)column.getDefaultValue()) || (expression = dataDefinitionsCache.getFieldDefaultValue(qContext, column)) == null) continue;
            DefaultExpression defaultExpression = new DefaultExpression(column, expression);
            defExprs.add(defaultExpression);
        }
        return defExprs;
    }

    private QueryField getQueryField() {
        if (this.queryfields == null || this.queryfields.size() <= 0) {
            return null;
        }
        for (int index = 0; index < this.queryfields.size(); ++index) {
            QueryField queryField = (QueryField)this.queryfields.get(index);
            if (queryField == null) continue;
            return queryField;
        }
        return null;
    }

    @Override
    public void setDataChangeMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }

    public void dataValidate(DataRowImpl dataRowImpl) throws DataValidateException {
        boolean resultValue;
        if (this.validateProvider != null && !(resultValue = this.validateProvider.checkRow(dataRowImpl))) {
            throw new DataValidateException("\u6570\u636e\u884c\u6821\u9a8c\u4e0d\u901a\u8fc7\uff0c\u65e0\u6cd5\u63d0\u4ea4\u6570\u636e\u3002");
        }
    }

    @Override
    public void needCheckDuplicateKeys(boolean needCheckKeys) {
        this.needCheckKeys = needCheckKeys;
    }

    @Override
    public IDataRow revokeRow(DimensionValueSet rowKeys) {
        DataRowImpl dataRowImpl = (DataRowImpl)this.findRow(rowKeys);
        if (dataRowImpl == null) {
            return null;
        }
        dataRowImpl.setRowState(RowState.REVOKE);
        return dataRowImpl;
    }

    @Override
    public void setIgnoreEvent(boolean value) {
        this.ignoreEvent = value;
    }

    public boolean isIgnoreEvent() {
        return this.ignoreEvent;
    }

    @Override
    public void setOnlySaveData(boolean value) {
        this.onlySaveData = value;
    }

    public void resetRowKeys(DataRowImpl dataRowImpl) {
        if (this.dimFields == null) {
            this.dimFields = this.getDimensionFields();
        }
        if (this.dimFields.isEmpty()) {
            return;
        }
        DimensionValueSet rowKeys = new DimensionValueSet();
        DimensionSet dimensionSet = this.getRowDimensions();
        int count = dimensionSet.size();
        if (count <= 0) {
            dimensionSet = new DimensionSet();
            for (Map.Entry<String, ColumnModelDefine> dimField : this.dimFields.entrySet()) {
                dimensionSet.addDimension(dimField.getKey());
            }
            count = dimensionSet.size();
        }
        boolean hasNull = false;
        for (int index = 0; index < count; ++index) {
            String dimension = dimensionSet.get(index);
            if (this.dimFields.containsKey(dimension)) {
                ColumnModelDefine fieldDefine = this.dimFields.get(dimension);
                DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
                FieldDefine column = dataDefinitionsCache.getFieldDefine(fieldDefine);
                int fieldIndex = this.fieldsInfoImpl.indexOf(column);
                if (fieldIndex < 0) {
                    if (dataRowImpl.getRowKeys().hasValue(dimension)) {
                        Object value = dataRowImpl.getRowKeys().getValue(dimension);
                        if (value == null) {
                            hasNull = true;
                            break;
                        }
                        rowKeys.setValue(dimension, value);
                        continue;
                    }
                    hasNull = true;
                    break;
                }
                Object dimValue = dataRowImpl.internalGetValue(fieldIndex);
                if (dimValue == null) {
                    hasNull = true;
                    break;
                }
                rowKeys.setValue(dimension, dimValue);
                continue;
            }
            hasNull = true;
            break;
        }
        if (hasNull) {
            return;
        }
        dataRowImpl.setRowKeys(rowKeys);
    }

    private HashMap<String, ColumnModelDefine> getDimensionFields() {
        HashMap<String, ColumnModelDefine> dimMap = new HashMap<String, ColumnModelDefine>();
        QueryField queryField = this.getQueryField();
        if (queryField == null) {
            return dimMap;
        }
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(queryField.getTableName());
        if (tableRunInfo == null) {
            return dimMap;
        }
        List<ColumnModelDefine> dimFields = tableRunInfo.getDimFields();
        for (ColumnModelDefine fieldDefine : dimFields) {
            String dimension = tableRunInfo.getDimensionName(fieldDefine.getCode());
            dimMap.put(dimension, fieldDefine);
        }
        return dimMap;
    }

    public boolean validataDataRow(DataRowImpl dataRowImpl, List<IParsedExpression> expressions) throws ExpressionValidateException {
        if (expressions.size() <= 0) {
            return true;
        }
        try {
            this.setCalcRowContext(this.qContext);
        }
        catch (ParseException ex) {
            logger.error("\u6570\u636e\u884c\u516c\u5f0f\u6267\u884c\u73af\u5883\u521d\u59cb\u5316\u5931\u8d25", ex);
        }
        this.getDataReader(this.qContext).setDataSet(dataRowImpl);
        boolean resultValue = true;
        ArrayList<IParsedExpression> errorExps = new ArrayList<IParsedExpression>();
        for (IParsedExpression iExpression : expressions) {
            try {
                Object validResult = iExpression.getRealExpression().evaluate((IContext)this.qContext);
                if (!(validResult instanceof Boolean) || ((Boolean)validResult).booleanValue()) continue;
                resultValue = false;
                errorExps.add(iExpression);
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u884c\uff1a".concat(dataRowImpl.getRowKeys().toString()).concat("\u6821\u9a8c\u516c\u5f0f\u6267\u884c\u51fa\u9519\uff0c\u5177\u4f53\u516c\u5f0f\u4e3a\uff1a").concat(iExpression.toString()), e);
            }
        }
        if (!resultValue) {
            ExpressionValidateException exception = new ExpressionValidateException("\u5f53\u524d\u6570\u636e\u6821\u9a8c\u516c\u5f0f\u6267\u884c\u4e0d\u901a\u8fc7\u3002");
            RowExpressionValidResult rowExpressionValidResult = new RowExpressionValidResult();
            rowExpressionValidResult.setErrorExpressions(errorExps);
            rowExpressionValidResult.setRowKeys(dataRowImpl.getRowKeys());
            rowExpressionValidResult.setRecordKey(dataRowImpl.getRecordKey());
            ArrayList<RowExpressionValidResult> rowExpressionValidResults = new ArrayList<RowExpressionValidResult>();
            rowExpressionValidResults.add(rowExpressionValidResult);
            exception.setRowExpressionValidResults(rowExpressionValidResults);
            throw exception;
        }
        return true;
    }

    public boolean judge(DataRowImpl dataRowImpl, IExpression expression, QueryContext context) {
        try {
            this.setCalcRowContext(this.qContext);
        }
        catch (ParseException ex) {
            logger.error("\u6570\u636e\u884c\u516c\u5f0f\u6267\u884c\u73af\u5883\u521d\u59cb\u5316\u5931\u8d25", ex);
            return true;
        }
        this.getDataReader(this.qContext).setDataSet(dataRowImpl);
        boolean result = true;
        try {
            result = expression.judge((IContext)this.qContext);
        }
        catch (Exception e) {
            return true;
        }
        return result;
    }

    protected void deleteRow(DataRowImpl deleteRow, boolean onlyCurrent) {
        deleteRow.delete();
        String rowKeyData = deleteRow.getRowKeys().toString();
        if (this.rowKeySearch != null) {
            this.rowKeySearch.remove(rowKeyData);
        }
        if (onlyCurrent) {
            this.currentPeriodSet.add(rowKeyData);
        }
    }

    @Override
    public void setIgnoreResetCache(boolean value) {
        this.ignoreResetCache = value;
    }

    public void valueValidate(DataRowImpl dataRowImpl) throws ValueValidateException {
        if (this.queryParam.getValueValidateHandlers() == null || this.queryParam.getValueValidateHandlers().size() <= 0) {
            return;
        }
        List<IValueValidateHandler> validateHandlers = this.queryParam.getValueValidateHandlers();
        HashMap<Integer, Object> modifiedDatas = dataRowImpl.getModifiedDatas();
        RowValidateResult rowValidateResult = new RowValidateResult();
        ArrayList<FieldValidateResult> fieldValidateResults = new ArrayList<FieldValidateResult>();
        for (Map.Entry<Integer, Object> modifyData : modifiedDatas.entrySet()) {
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(modifyData.getKey());
            if (fieldDefine == null) continue;
            int dataType = DataTypesConvert.fieldTypeToDataType(fieldDefine.getType());
            ArrayList<ValidateResult> validateResults = new ArrayList<ValidateResult>();
            for (IValueValidateHandler validateHandler : validateHandlers) {
                ValidateResult validateResult = validateHandler.checkValue(fieldDefine.getCode(), dataType, modifyData.getValue());
                if (validateResult == null || validateResult.isResultValue()) continue;
                validateResults.add(validateResult);
            }
            if (validateResults.size() <= 0) continue;
            FieldValidateResult fieldValidateResult = new FieldValidateResult();
            fieldValidateResult.setFieldDefine(fieldDefine);
            fieldValidateResult.setValidateResult(validateResults);
            fieldValidateResults.add(fieldValidateResult);
        }
        if (fieldValidateResults.size() > 0) {
            DimensionValueSet rowKeys = dataRowImpl.getRowKeys();
            rowValidateResult.setFieldValidateResults(fieldValidateResults);
            rowValidateResult.setRowKeys(rowKeys);
            rowValidateResult.setRecordKey(dataRowImpl.getRecordKey());
            ValueValidateException exception = new ValueValidateException("\u5f53\u524d\u884c\u6570\u636e\u6821\u9a8c\u4e0d\u901a\u8fc7\u3002");
            ArrayList<RowValidateResult> rowValidateResults = new ArrayList<RowValidateResult>();
            rowValidateResults.add(rowValidateResult);
            exception.setRowValidateResults(rowValidateResults);
            throw exception;
        }
    }

    @Override
    public void setValidExpression(List<IParsedExpression> expressions) {
        if (expressions == null) {
            return;
        }
        this.validExpressions.addAll(expressions);
    }

    private void doCheckRowKeys(List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> deleteRows) throws Exception {
        List<DimensionValueSet> duplicateKeys;
        QueryTable queryTable = this.getDataQueryBuilder().getSqlBuilder(this.qContext).getPrimaryTable();
        DataModelDefinitionsCache dataModelDefinitionsCache = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = dataModelDefinitionsCache.getTableInfo(queryTable.getTableName());
        if (tableRunInfo.getDimensions().contains("RECORDKEY")) {
            return;
        }
        HashMap<DimensionValueSet, Integer> rowCountMap = new HashMap<DimensionValueSet, Integer>();
        for (DataRowImpl row : deleteRows) {
            rowCountMap.put(row.getRowKeys(), -1);
        }
        for (DataRowImpl row : insertRows) {
            this.putRowCount(rowCountMap, row.getRowKeys(), 1);
        }
        if (updateRows.size() > 0) {
            this.checkModiedRowKeys(tableRunInfo, updateRows, insertRows, deleteRows, rowCountMap);
        }
        if (insertRows.size() > 0) {
            if (this.qContext.isEnableNrdb() && tableRunInfo.getTableModelDefine().isSupportNrdb()) {
                this.checkRowKeyCountFromNrdb(rowCountMap, queryTable, tableRunInfo);
            } else {
                this.checkRowKeyCountFromDB(rowCountMap, queryTable, tableRunInfo);
            }
        }
        if ((duplicateKeys = this.findDuplicateKeys(rowCountMap)).size() > 0) {
            throw new DuplicateRowKeysException(duplicateKeys);
        }
    }

    private List<DimensionValueSet> findDuplicateKeys(Map<DimensionValueSet, Integer> rowCountMap) {
        ArrayList<DimensionValueSet> duplicateKeys = new ArrayList<DimensionValueSet>();
        for (Map.Entry<DimensionValueSet, Integer> entry : rowCountMap.entrySet()) {
            int count = entry.getValue();
            if (count <= 1) continue;
            duplicateKeys.add(entry.getKey());
        }
        return duplicateKeys;
    }

    private void checkRowKeyCountFromNrdb(Map<DimensionValueSet, Integer> rowCountMap, QueryTable queryTable, TableModelRunInfo tableRunInfo) throws Exception {
        String dimName;
        int i;
        DBQueryBuilder dbQueryBuilder = new DBQueryBuilder();
        String physicalTableName = this.qContext.getPhysicalTableName(queryTable);
        TableModelRunInfo physicalTableInfo = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(physicalTableName);
        DimensionSet tableDim = physicalTableInfo.getDimensions();
        for (i = 0; i < tableDim.size(); ++i) {
            dimName = tableDim.get(i);
            String fieldName = physicalTableInfo.getDimensionField(dimName).getName();
            dbQueryBuilder.select(fieldName);
        }
        for (i = 0; i < this.masterKeys.size(); ++i) {
            dimName = this.masterKeys.getName(i);
            Object dimValue = this.masterKeys.getValue(i);
            ColumnModelDefine dimensionField = tableRunInfo.getDimensionField(dimName);
            dbQueryBuilder.filterBy(new DBFilter[]{NrdbAccessUtils.getColumnDBFilter(dimensionField, dimValue)});
        }
        try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(physicalTableInfo.getTableModelDefine());){
            DBQuery dbQuery = dbQueryBuilder.build();
            try (DBCursor cursor = dbTable.query(dbQuery);){
                MemoryDataSet dataSet = new MemoryDataSet();
                DBMetadata metadata = cursor.getMetadata();
                for (int i2 = 0; i2 < metadata.size(); ++i2) {
                    dataSet.getMetadata().addColumn(new Column(metadata.getName(i2), metadata.getType(i2)));
                }
                while (cursor.hasNext()) {
                    DBRecord record = (DBRecord)cursor.next();
                    DataRow row = dataSet.add();
                    for (int index = 0; index < metadata.size(); ++index) {
                        row.setValue(index, record.getValue(index));
                    }
                }
                for (DataRow row : dataSet) {
                    DimensionValueSet rowKeys = new DimensionValueSet();
                    for (int i3 = 0; i3 < tableDim.size(); ++i3) {
                        String dimName2 = tableDim.get(i3);
                        Object dimValue = row.getValue(i3);
                        rowKeys.setValue(dimName2, dimValue);
                    }
                    this.putRowCount(rowCountMap, rowKeys, 1);
                }
            }
        }
    }

    private void checkRowKeyCountFromDB(Map<DimensionValueSet, Integer> rowCountMap, QueryTable queryTable, TableModelRunInfo tableRunInfo) throws SQLException, DataSetException {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        DimensionSet tableDim = tableRunInfo.getDimensions();
        for (int i = 0; i < tableDim.size(); ++i) {
            String dimName = tableDim.get(i);
            String fieldName = tableRunInfo.getDimensionField(dimName).getName();
            sql.append(fieldName).append(",");
        }
        sql.setLength(sql.length() - 1);
        SplitTableHelper splitTableHelper = this.queryParam.getSplitTableHelper();
        String tableName = queryTable.getTableName();
        if (splitTableHelper != null) {
            tableName = splitTableHelper.getCurrentSplitTableName(this.qContext.getExeContext(), tableName);
        }
        sql.append(" from ").append(tableName).append(" where ");
        boolean needAnd = false;
        ArrayList<Object> argValues = new ArrayList<Object>(this.masterKeys.size());
        for (int i = 0; i < this.masterKeys.size(); ++i) {
            String dimName = this.masterKeys.getName(i);
            Object dimValue = this.masterKeys.getValue(i);
            String fieldName = tableRunInfo.getDimensionField(dimName).getName();
            FieldSqlConditionUtil.appendFieldCondition(this.qContext.getQueryParam().getDatabase(), this.qContext.getQueryParam().getConnection(), sql, fieldName, 6, dimValue, needAnd, null, argValues, null, false);
            needAnd = true;
        }
        MemoryDataSet<QueryField> rs = DataEngineUtil.queryMemoryDataSet(this.qContext.getQueryParam().getConnection(), tableName, sql.toString(), argValues.toArray(), this.qContext.getMonitor());
        for (DataRow row : rs) {
            DimensionValueSet rowKeys = new DimensionValueSet();
            for (int i = 0; i < tableDim.size(); ++i) {
                String dimName = tableDim.get(i);
                Object dimValue = row.getValue(i);
                rowKeys.setValue(dimName, dimValue);
            }
            this.putRowCount(rowCountMap, rowKeys, 1);
        }
    }

    private void checkModiedRowKeys(TableModelRunInfo tableRunInfo, List<DataRowImpl> updateRows, List<DataRowImpl> insertRows, List<DataRowImpl> deleteRows, Map<DimensionValueSet, Integer> rowCountMap) throws ParseException {
        DataModelDefinitionsCache dataModelDefinitionsCache = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        DimensionSet rowDims = tableRunInfo.getDimensions();
        int[] dimFieldIndexes = new int[rowDims.size()];
        for (int index = 0; index < rowDims.size(); ++index) {
            String dimName = rowDims.get(index);
            ColumnModelDefine dimField = tableRunInfo.getDimensionField(dimName);
            FieldDefine field = dataModelDefinitionsCache.getFieldDefine(dimField);
            dimFieldIndexes[index] = this.fieldsInfoImpl.indexOf(field);
        }
        for (int i = updateRows.size() - 1; i >= 0; --i) {
            DataRowImpl dataRowImpl = updateRows.get(i);
            DimensionValueSet oldRowKeys = new DimensionValueSet();
            DimensionValueSet newRowKeys = new DimensionValueSet();
            for (int index = 0; index < rowDims.size(); ++index) {
                String dimName = rowDims.get(index);
                if (dimFieldIndexes[index] >= 0) {
                    Object oldValue = dataRowImpl.internalGetOldValue(dimFieldIndexes[index]);
                    if (oldValue == null) {
                        oldValue = dataRowImpl.getRowKeys().getValue(dimName);
                    }
                    oldRowKeys.setValue(dimName, oldValue);
                    Object newValue = dataRowImpl.internalGetValue(dimFieldIndexes[index]);
                    if (newValue == null) {
                        newValue = dataRowImpl.getRowKeys().getValue(dimName);
                    }
                    newRowKeys.setValue(dimName, newValue);
                    continue;
                }
                Object value = dataRowImpl.getRowKeys().getValue(dimName);
                oldRowKeys.setValue(dimName, value);
                newRowKeys.setValue(dimName, value);
            }
            if (newRowKeys.equals(oldRowKeys)) continue;
            this.putRowCount(rowCountMap, oldRowKeys, -1);
            this.putRowCount(rowCountMap, newRowKeys, 1);
            updateRows.remove(i);
            dataRowImpl.setRowState(RowState.ADD);
            insertRows.add(dataRowImpl);
            DataRowImpl deleteRow = new DataRowImpl(this, oldRowKeys, dataRowImpl.rowDatas);
            deleteRow.delete();
            deleteRows.add(deleteRow);
        }
    }

    private void putRowCount(Map<DimensionValueSet, Integer> rowCountMap, DimensionValueSet rowKeys, int addCount) {
        Integer count = rowCountMap.get(rowKeys);
        count = count != null ? Integer.valueOf(count + addCount) : Integer.valueOf(addCount);
        rowCountMap.put(rowKeys, count);
    }
}

