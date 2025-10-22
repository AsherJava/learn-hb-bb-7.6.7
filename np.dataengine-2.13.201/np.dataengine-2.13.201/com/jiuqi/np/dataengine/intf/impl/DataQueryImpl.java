/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataQueryColumn;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.intf.impl.DataUpdatorImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.query.DataQueryBuilder;
import com.jiuqi.np.dataengine.query.FilledEnumLinkHelper;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.account.AccountDataQueryBuilder;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

public class DataQueryImpl
extends CommonQueryImpl
implements IDataQuery {
    protected boolean isReadOnly;
    private List<FieldDefine> enumFields;
    private List<List<String>> enumObjects;
    private boolean isStatic = false;

    /*
     * Loose catch block
     */
    @Override
    public IReadonlyTable executeReader(ExecutorContext context) throws Exception {
        this.isReadOnly = true;
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_READONLY);
        if (!(this.monitor instanceof AbstractMonitor) || ((AbstractMonitor)this.monitor).isDebug()) {
            // empty if block
        }
        try {
            try (TempResource tempResource = new TempResource();){
                QueryContext qContext = this.getQueryContext(context, tempResource, true);
                this.adjustQueryVersionDate(qContext);
                DataQueryBuilder builder = this.isAccountTable(qContext) ? new AccountDataQueryBuilder() : new DataQueryBuilder();
                builder.setStatic(this.isStatic);
                builder.setQueryParam(this.queryParam);
                builder.setMainTableName(this.tableName);
                builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
                builder.buildQuery(qContext, this, this.isReadOnly);
                if (this.rowIndex < 0) {
                    this.rowIndex = this.rowsPerPage * this.pageIndex;
                }
                builder.runQuery(qContext, this.rowsPerPage, this.rowIndex);
                ReadonlyTableImpl result = builder.getResultTable();
                if (!(this.isAccountTable(qContext) || CollectionUtils.isEmpty(this.enumFields) || CollectionUtils.isEmpty(this.enumObjects))) {
                    FilledEnumLinkHelper.filledDetailDataByEnumLinks(result, this.enumFields, this.enumObjects);
                    FilledEnumLinkHelper.filledDetailDataSort(result, this.orderColumns);
                }
                ReadonlyTableImpl readonlyTableImpl = result;
                return readonlyTableImpl;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    /*
     * Loose catch block
     */
    @Override
    public IDataTable executeQuery(ExecutorContext context) throws Exception {
        this.isReadOnly = false;
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_COMMON);
        try {
            try (TempResource tempResource = new TempResource();){
                QueryContext qContext = this.getQueryContext(context, tempResource, true);
                this.adjustQueryVersionDate(qContext);
                DataQueryBuilder builder = this.isAccountTable(qContext) ? new AccountDataQueryBuilder() : new DataQueryBuilder();
                builder.setStatic(this.isStatic);
                builder.setQueryParam(this.queryParam);
                builder.setMainTableName(this.tableName);
                builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
                builder.buildQuery(qContext, this, this.isReadOnly);
                if (this.rowIndex < 0) {
                    this.rowIndex = this.rowsPerPage * this.pageIndex;
                }
                builder.runQuery(qContext, this.rowsPerPage, this.rowIndex);
                DataTableImpl result = (DataTableImpl)builder.getResultTable();
                result.setQueryParam(this.queryParam);
                DataTableImpl dataTableImpl = result;
                return dataTableImpl;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void queryToDataRowReader(ExecutorContext context, IDataRowReader reader) throws UnsupportedOperationException, Exception {
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_READONLY);
        try (TempResource tempResource = new TempResource();){
            QueryContext qContext = this.getQueryContext(context, tempResource, true);
            this.adjustQueryVersionDate(qContext);
            DataQueryBuilder builder = this.isAccountTable(qContext) ? new AccountDataQueryBuilder() : new DataQueryBuilder();
            builder.setStatic(this.isStatic);
            builder.setQueryParam(this.queryParam);
            builder.setMainTableName(this.tableName);
            builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
            builder.buildQuery(qContext, this, this.isReadOnly);
            List<QueryTable> allTables = builder.getAllTables();
            if (allTables.size() <= 1) {
                builder.loadToDataRowReader(qContext, reader);
            } else {
                boolean allMatch = this.acceptStreamJoin(qContext, allTables);
                if (allMatch) {
                    builder.loadToDataRowReaderByStreamJoin(qContext, reader);
                } else {
                    this.readFromMemeryFullJoin(reader, qContext, builder);
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    private void readFromMemeryFullJoin(IDataRowReader reader, QueryContext qContext, DataQueryBuilder builder) throws Exception {
        builder.runQuery(qContext, this.rowsPerPage, this.rowIndex);
        DataTableImpl result = (DataTableImpl)builder.getResultTable();
        reader.start(qContext, result.getFieldsInfo());
        for (int i = 0; i < result.getCount(); ++i) {
            reader.readRowData(qContext, result.getItem(i));
        }
        reader.finish(qContext);
    }

    private boolean acceptStreamJoin(QueryContext qContext, List<QueryTable> allTables) {
        if (this.orderColumns != null && this.orderColumns.size() > 0) {
            return false;
        }
        if (this.pageIndex > 0 || this.rowsPerPage > 0) {
            return false;
        }
        if (qContext.getUnitLeafFinder() != null) {
            return false;
        }
        DimensionSet tableDims = null;
        boolean allMatch = true;
        for (QueryTable table : allTables) {
            if (tableDims == null) {
                tableDims = table.getTableDimensions();
                continue;
            }
            if (tableDims.equals(table.getTableDimensions())) continue;
            allMatch = false;
            break;
        }
        return allMatch;
    }

    @Override
    public IDataUpdator openForUpdate(ExecutorContext context) throws Exception {
        return this.openForUpdate(context, false);
    }

    /*
     * Loose catch block
     */
    @Override
    public IDataUpdator openForUpdate(ExecutorContext context, boolean deleteAllExistingData) throws Exception {
        this.isReadOnly = false;
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_FORUPDATE);
        try {
            try (TempResource tempResource = new TempResource();){
                DataUpdatorImpl dataUpdatorImpl;
                QueryContext qContext = this.getQueryContext(context, tempResource, false);
                this.adjustQueryVersionDate(qContext);
                DataQueryBuilder builder = this.isAccountTable(qContext) ? new AccountDataQueryBuilder() : new DataQueryBuilder();
                builder.setStatic(this.isStatic);
                builder.setQueryParam(this.queryParam);
                builder.setMainTableName(this.tableName);
                builder.setOpenUpdateOnly(true);
                builder.buildQuery(qContext, this, this.isReadOnly);
                if (this.rowIndex < 0) {
                    this.rowIndex = this.rowsPerPage * this.pageIndex;
                }
                builder.runQuery(qContext, this.rowsPerPage, this.rowIndex);
                DataTableImpl result = (DataTableImpl)builder.getResultTable();
                result.setQueryParam(this.queryParam);
                DataUpdatorImpl dataUpdatorImpl2 = dataUpdatorImpl = new DataUpdatorImpl(result, deleteAllExistingData);
                return dataUpdatorImpl2;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    @Override
    public void setMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    /*
     * Loose catch block
     */
    @Override
    public IndexItem queryRowIndex(DimensionValueSet rowKeys, ExecutorContext context) throws Exception {
        try {
            try (TempResource tempResource = new TempResource();){
                IndexItem result;
                QueryContext qContext = this.getQueryContext(context, tempResource, true);
                this.adjustQueryVersionDate(qContext);
                DataQueryBuilder builder = this.isAccountTable(qContext) ? new AccountDataQueryBuilder() : new DataQueryBuilder();
                builder.setStatic(this.isStatic);
                builder.setQueryParam(this.queryParam);
                builder.setMainTableName(this.tableName);
                builder.buildQuery(qContext, this, this.isReadOnly);
                IndexItem indexItem = result = builder.queryRowIndex(rowKeys, qContext);
                return indexItem;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    public boolean isQueryModule() {
        return this.queryModule;
    }

    @Override
    public void setFilledEnumLinks(List<FieldDefine> enumFields, List<List<String>> enumObjects) {
        this.enumFields = enumFields;
        this.enumObjects = enumObjects;
    }

    public boolean isIgnoreDefaultOrderBy() {
        return this.ignoreDefaultOrderBy;
    }

    @Override
    public void setIgnoreDefaultOrderBy(boolean ignoreDefaultOrderBy) {
        this.ignoreDefaultOrderBy = ignoreDefaultOrderBy;
    }

    private boolean isAccountTable(QueryContext qContext) {
        boolean accountTable = false;
        if (this.columns != null && this.columns.size() > 0) {
            FieldDefine field = ((DataQueryColumn)this.columns.get(0)).getField();
            try {
                if (Objects.nonNull(field)) {
                    accountTable = qContext.getAccountColumnModelFinder().isAccountTable(qContext.getExeContext(), field.getOwnerTableKey());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accountTable;
    }

    @Override
    public IMonitor getMonitor() {
        return this.monitor;
    }
}

