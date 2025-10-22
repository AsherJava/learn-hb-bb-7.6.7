/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.query.account;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.bql.dataengine.IGroupingQuery;
import com.jiuqi.nr.bql.dataengine.impl.CommonQueryImpl;
import com.jiuqi.nr.bql.dataengine.query.account.AccountGroupingQueryBuilder;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountGroupingQueryImpl
extends CommonQueryImpl
implements IGroupingQuery {
    private List<Integer> groupColumns = new ArrayList<Integer>();
    private boolean wantDetail;
    private boolean sortGroupingAndDetailRows;
    private ArrayList<AggrType> gatherTypes = new ArrayList();
    private HashMap<Integer, Boolean> setTypes = new HashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void queryToReader(ExecutorContext context) throws Exception {
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_GROUP);
        QueryContext queryContext = null;
        try {
            this.assureGatherTypesCount();
            queryContext = this.getQueryContext(context, true);
            this.adjustQueryVersionDate(queryContext);
            AccountGroupingQueryBuilder builder = new AccountGroupingQueryBuilder(this.reader);
            builder.setQueryParam(this.queryParam);
            builder.setMainTableName(this.tableName);
            builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
            builder.buildGroupingQuery(queryContext, this);
            builder.runQuery(queryContext, this.rowsPerPage, this.pageIndex * this.rowsPerPage);
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    @Override
    public void addGroupColumn(int columnIndex) {
        this.groupColumns.add(columnIndex);
    }

    @Override
    public int addGroupColumn(ColumnModelDefine fieldDefine) {
        int columnIndex = this.addColumn(fieldDefine);
        this.groupColumns.add(columnIndex);
        return columnIndex;
    }

    @Override
    public void setWantDetail(boolean value) {
        this.wantDetail = value;
    }

    public boolean getWantDetail() {
        return this.wantDetail;
    }

    public boolean getSortGroupingAndDetailRows() {
        return this.sortGroupingAndDetailRows;
    }

    @Override
    public AggrType getGatherType(int columnIndex) {
        this.assureGatherTypesCount();
        return this.gatherTypes.get(columnIndex);
    }

    @Override
    public void setGatherType(int columnIndex, AggrType gatherType) {
        this.assureGatherTypesCount();
        this.gatherTypes.set(columnIndex, gatherType);
        this.setTypes.put(columnIndex, gatherType == AggrType.NONE);
    }

    private void assureGatherTypesCount() {
        if (this.gatherTypes.size() >= this.columns.size()) {
            return;
        }
        int c = this.columns.size();
        for (int i = this.gatherTypes.size(); i < c; ++i) {
            this.gatherTypes.add(AggrType.NONE);
        }
    }

    public ArrayList<AggrType> getGatherTypes() {
        return this.gatherTypes;
    }

    public List<Integer> getGroupColumns() {
        return this.groupColumns;
    }

    public HashMap<Integer, Boolean> getSetTypes() {
        return this.setTypes;
    }
}

