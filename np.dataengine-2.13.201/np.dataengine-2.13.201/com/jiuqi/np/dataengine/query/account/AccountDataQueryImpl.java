/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.DataQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.intf.impl.DataUpdatorImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.account.AccountDataQueryBuilder;
import com.jiuqi.np.dataengine.query.account.AccountDataTableImpl;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;

public class AccountDataQueryImpl
extends DataQueryImpl {
    protected boolean isReadOnly;
    protected AccountDataQueryBuilder builder;
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
                ReadonlyTableImpl result;
                QueryContext qContext = this.getQueryContext(context, tempResource, true);
                this.adjustQueryVersionDate(qContext);
                this.builder = new AccountDataQueryBuilder();
                this.builder.setStatic(this.isStatic);
                this.builder.setQueryParam(this.queryParam);
                this.builder.setMainTableName(this.tableName);
                this.builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
                this.builder.buildQuery(qContext, this, this.isReadOnly);
                if (this.rowIndex < 0) {
                    this.rowIndex = this.rowsPerPage * this.pageIndex;
                }
                this.builder.runQuery(qContext, this.rowsPerPage, this.rowIndex);
                ReadonlyTableImpl readonlyTableImpl = result = this.builder.getResultTable();
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
                this.builder = new AccountDataQueryBuilder();
                this.builder.setStatic(this.isStatic);
                this.builder.setQueryParam(this.queryParam);
                this.builder.setMainTableName(this.tableName);
                this.builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
                this.builder.buildQuery(qContext, this, this.isReadOnly);
                if (this.rowIndex < 0) {
                    this.rowIndex = this.rowsPerPage * this.pageIndex;
                }
                this.builder.runQuery(qContext, this.rowsPerPage, this.rowIndex);
                AccountDataTableImpl result = (AccountDataTableImpl)this.builder.getResultTable();
                result.setQueryParam(this.queryParam);
                AccountDataTableImpl accountDataTableImpl = result;
                return accountDataTableImpl;
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
                this.builder = new AccountDataQueryBuilder();
                this.builder.setStatic(this.isStatic);
                this.builder.setQueryParam(this.queryParam);
                this.builder.setMainTableName(this.tableName);
                this.builder.setOpenUpdateOnly(true);
                this.builder.buildQuery(qContext, this, this.isReadOnly);
                if (this.rowIndex < 0) {
                    this.rowIndex = this.rowsPerPage * this.pageIndex;
                }
                this.builder.runQuery(qContext, this.rowsPerPage, this.rowIndex);
                DataTableImpl result = (DataTableImpl)this.builder.getResultTable();
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
                this.builder = new AccountDataQueryBuilder();
                this.builder.setStatic(this.isStatic);
                this.builder.setQueryParam(this.queryParam);
                this.builder.setMainTableName(this.tableName);
                this.builder.buildQuery(qContext, this, this.isReadOnly);
                IndexItem indexItem = result = this.builder.queryRowIndex(rowKeys, qContext);
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

    @Override
    public boolean isQueryModule() {
        return this.queryModule;
    }

    @Override
    public void setFilledEnumLinks(List<FieldDefine> enumFields, List<List<String>> enumObjects) {
        this.enumFields = enumFields;
        this.enumObjects = enumObjects;
    }

    @Override
    public boolean isIgnoreDefaultOrderBy() {
        return this.ignoreDefaultOrderBy;
    }

    @Override
    public void setIgnoreDefaultOrderBy(boolean ignoreDefaultOrderBy) {
        this.ignoreDefaultOrderBy = ignoreDefaultOrderBy;
    }
}

