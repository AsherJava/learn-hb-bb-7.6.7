/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IndexItem
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataRowReader
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl
 *  com.jiuqi.np.dataengine.intf.impl.DataQueryImpl
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.query;

import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.monitor.GcDataQueryMonitor;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.validate.GcDataValidateProvider;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.query.updater.GcQuerySqlUpdater;
import com.jiuqi.gcreport.inputdata.query.updater.GcQuerySqlUpdaterFactory;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.DataQueryImpl;

public class GcDataQueryImpl
extends DataQueryImpl {
    private GcDataEntryContext gcContext;
    private ExecutorContext executorContext;

    public GcDataQueryImpl(GcDataEntryContext gcContext) {
        this.gcContext = gcContext;
        if (gcContext.isGcQuery()) {
            this.monitor = new GcDataQueryMonitor(this);
            this.validateProvider = new GcDataValidateProvider(this);
        }
        this.setQuerySqlUpdater(GcQuerySqlUpdaterFactory.createSqlUpdater(this.getGcContext()));
    }

    public GcDataEntryContext getGcContext() {
        return this.gcContext;
    }

    public GcQuerySqlUpdater getQuerySqlUpdater() {
        return (GcQuerySqlUpdater)super.getQuerySqlUpdater();
    }

    public IReadonlyTable executeReader(ExecutorContext context) throws Exception {
        this.executorContext = context;
        IReadonlyTable executeReader = null;
        try {
            this.getQuerySqlUpdater().beforeQuery((CommonQueryImpl)this, context.isUseDnaSql());
            executeReader = super.executeReader(context);
            this.getQuerySqlUpdater().afterQuery((CommonQueryImpl)this);
        }
        finally {
            this.queryParam.closeConnection();
        }
        return executeReader;
    }

    public void queryToDataRowReader(ExecutorContext context, IDataRowReader reader) throws UnsupportedOperationException, Exception {
        this.executorContext = context;
        try {
            this.getQuerySqlUpdater().beforeQuery((CommonQueryImpl)this, context.isUseDnaSql());
            super.queryToDataRowReader(context, reader);
            this.getQuerySqlUpdater().afterQuery((CommonQueryImpl)this);
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    public IDataTable executeQuery(ExecutorContext context) throws Exception {
        IDataTable executeQuery;
        this.executorContext = context;
        try {
            this.getQuerySqlUpdater().beforeQuery((CommonQueryImpl)this, context.isUseDnaSql());
            executeQuery = super.executeQuery(context);
            executeQuery.setDataChangeMonitor(this.monitor);
            this.getQuerySqlUpdater().afterQuery((CommonQueryImpl)this);
        }
        finally {
            this.queryParam.closeConnection();
        }
        return executeQuery;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IDataUpdator openForUpdate(ExecutorContext context, boolean deleteAllExistingData) throws Exception {
        this.executorContext = context;
        IDataUpdator openForUpdate = null;
        try {
            this.getQuerySqlUpdater().beforeQuery((CommonQueryImpl)this, context.isUseDnaSql());
            openForUpdate = super.openForUpdate(context, deleteAllExistingData);
            openForUpdate.setDataChangeMonitor(this.monitor);
            this.getQuerySqlUpdater().afterQuery((CommonQueryImpl)this);
        }
        finally {
            this.queryParam.closeConnection();
        }
        return openForUpdate;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IndexItem queryRowIndex(DimensionValueSet rowKeys, ExecutorContext context) throws Exception {
        this.executorContext = context;
        IndexItem index = null;
        try {
            this.getQuerySqlUpdater().beforeQuery((CommonQueryImpl)this, context.isUseDnaSql());
            index = super.queryRowIndex(rowKeys, context);
            this.getQuerySqlUpdater().afterQuery((CommonQueryImpl)this);
        }
        finally {
            this.queryParam.closeConnection();
        }
        return index;
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        super.setMasterKeys(this.getGcContext().initMasterDimension(masterKeys));
    }

    public void setRowFilter(String rowFilter) {
        String initRowFilter = this.getGcContext().initRowFilter(rowFilter);
        super.setRowFilter(initRowFilter);
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }
}

