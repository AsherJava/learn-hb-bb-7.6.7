/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.bql.dataengine.query.account;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.bql.dataengine.impl.DataQueryImpl;
import com.jiuqi.nr.bql.dataengine.query.account.AccountDataQueryBuilder;

public class AccountDataQueryImpl
extends DataQueryImpl {
    protected boolean isReadOnly;
    protected AccountDataQueryBuilder builder;

    @Override
    public void queryToReader(ExecutorContext context) throws Exception {
        this.isReadOnly = true;
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_READONLY);
        QueryContext qContext = null;
        try {
            qContext = this.getQueryContext(context, true);
            this.adjustQueryVersionDate(qContext);
            this.builder = new AccountDataQueryBuilder(this.reader);
            this.builder.setQueryParam(this.queryParam);
            this.builder.setMainTableName(this.tableName);
            this.builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
            this.builder.buildQuery(qContext, this, this.isReadOnly);
            if (this.rowIndex < 0) {
                this.rowIndex = this.rowsPerPage * this.pageIndex;
            }
            this.builder.runQuery(qContext, this.rowsPerPage, this.rowIndex);
        }
        finally {
            this.queryParam.closeConnection();
        }
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

