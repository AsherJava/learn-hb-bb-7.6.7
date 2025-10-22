/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl
 *  com.jiuqi.nr.data.engine.grouping.GroupingQueryImpl
 */
package com.jiuqi.gcreport.inputdata.query;

import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.query.updater.GcQuerySqlUpdater;
import com.jiuqi.gcreport.inputdata.query.updater.GcQuerySqlUpdaterFactory;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl;
import com.jiuqi.nr.data.engine.grouping.GroupingQueryImpl;

public class GcGroupingQueryImpl
extends GroupingQueryImpl {
    private GcDataEntryContext gcContext;

    public GcGroupingQueryImpl(GcDataEntryContext gcContext) {
        this.gcContext = gcContext;
        this.setQuerySqlUpdater(GcQuerySqlUpdaterFactory.createSqlUpdater(this.getGcContext()));
    }

    public GcDataEntryContext getGcContext() {
        return this.gcContext;
    }

    public GcQuerySqlUpdater getQuerySqlUpdater() {
        return (GcQuerySqlUpdater)super.getQuerySqlUpdater();
    }

    public IGroupingTable executeReader(ExecutorContext eContext) throws Exception {
        IGroupingTable executeReader;
        try {
            this.getQuerySqlUpdater().beforeQuery((CommonQueryImpl)this, eContext.isUseDnaSql());
            executeReader = super.executeReader(eContext);
            this.getQuerySqlUpdater().afterQuery((CommonQueryImpl)this);
        }
        finally {
            this.queryParam.closeConnection();
        }
        return executeReader;
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        super.setMasterKeys(this.getGcContext().initMasterDimension(masterKeys));
    }

    public void setRowFilter(String rowFilter) {
        super.setRowFilter(this.getGcContext().initRowFilter(rowFilter));
    }
}

