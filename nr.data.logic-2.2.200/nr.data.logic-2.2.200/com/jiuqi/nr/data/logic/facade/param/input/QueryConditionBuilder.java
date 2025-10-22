/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.DefaultQueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCol;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConditionImpl;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterGroup;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator;
import com.jiuqi.nr.data.logic.facade.param.input.QueryLogicOperator;

public class QueryConditionBuilder {
    private final QueryConditionImpl queryCondition = new QueryConditionImpl();

    public QueryConditionBuilder(QueryCol col, QueryFilterOperator filterOperator, Object filterValue) {
        QueryFilter queryFilter = new QueryFilter(col, filterOperator, filterValue, null);
        this.queryCondition.addQueryCondition(queryFilter);
        this.queryCondition.addQueryFilter(queryFilter);
    }

    public QueryConditionBuilder(QueryCondition queryCondition) {
        QueryFilterGroup queryFilterGroup = new QueryFilterGroup(queryCondition, null);
        this.queryCondition.addQueryCondition(queryFilterGroup);
        if (queryCondition instanceof QueryConditionImpl) {
            QueryConditionImpl queryConditionImpl = (QueryConditionImpl)queryCondition;
            this.queryCondition.addQueryFilter(queryConditionImpl.getQueryFilters());
        } else if (queryCondition instanceof DefaultQueryFilter) {
            DefaultQueryFilter defaultQueryFilter = (DefaultQueryFilter)queryCondition;
            this.queryCondition.addQueryFilter(defaultQueryFilter.getQueryFilters());
        }
    }

    public QueryConditionBuilder and(QueryCol col, QueryFilterOperator filterOperator, Object filterValue) {
        QueryFilter queryFilter = new QueryFilter(col, filterOperator, filterValue, QueryLogicOperator.AND);
        this.queryCondition.addQueryCondition(queryFilter);
        this.queryCondition.addQueryFilter(queryFilter);
        return this;
    }

    public QueryConditionBuilder andIsNull(QueryCol col) {
        QueryFilter queryFilter = new QueryFilter(col, QueryFilterOperator.ISNULL, null, QueryLogicOperator.AND);
        this.queryCondition.addQueryCondition(queryFilter);
        this.queryCondition.addQueryFilter(queryFilter);
        return this;
    }

    public QueryConditionBuilder andIsNotNull(QueryCol col) {
        QueryFilter queryFilter = new QueryFilter(col, QueryFilterOperator.IS_NOTNULL, null, QueryLogicOperator.AND);
        this.queryCondition.addQueryCondition(queryFilter);
        this.queryCondition.addQueryFilter(queryFilter);
        return this;
    }

    public QueryConditionBuilder or(QueryCol col, QueryFilterOperator filterOperator, Object filterValue) {
        QueryFilter queryFilter = new QueryFilter(col, filterOperator, filterValue, QueryLogicOperator.OR);
        this.queryCondition.addQueryCondition(queryFilter);
        this.queryCondition.addQueryFilter(queryFilter);
        return this;
    }

    public QueryConditionBuilder orIsNull(QueryCol col) {
        QueryFilter queryFilter = new QueryFilter(col, QueryFilterOperator.ISNULL, null, QueryLogicOperator.OR);
        this.queryCondition.addQueryCondition(queryFilter);
        this.queryCondition.addQueryFilter(queryFilter);
        return this;
    }

    public QueryConditionBuilder orIsNotNull(QueryCol col) {
        QueryFilter queryFilter = new QueryFilter(col, QueryFilterOperator.IS_NOTNULL, null, QueryLogicOperator.OR);
        this.queryCondition.addQueryCondition(queryFilter);
        this.queryCondition.addQueryFilter(queryFilter);
        return this;
    }

    public QueryConditionBuilder andSubQuery(QueryCondition queryCondition) {
        QueryFilterGroup queryFilterGroup = new QueryFilterGroup(queryCondition, QueryLogicOperator.AND);
        this.queryCondition.addQueryCondition(queryFilterGroup);
        if (queryCondition instanceof QueryConditionImpl) {
            QueryConditionImpl queryConditionImpl = (QueryConditionImpl)queryCondition;
            this.queryCondition.addQueryFilter(queryConditionImpl.getQueryFilters());
        } else if (queryCondition instanceof DefaultQueryFilter) {
            DefaultQueryFilter defaultQueryFilter = (DefaultQueryFilter)queryCondition;
            this.queryCondition.addQueryFilter(defaultQueryFilter.getQueryFilters());
        }
        return this;
    }

    public QueryConditionBuilder orSubQuery(QueryCondition queryCondition) {
        QueryFilterGroup queryFilterGroup = new QueryFilterGroup(queryCondition, QueryLogicOperator.OR);
        this.queryCondition.addQueryCondition(queryFilterGroup);
        if (queryCondition instanceof QueryConditionImpl) {
            QueryConditionImpl queryConditionImpl = (QueryConditionImpl)queryCondition;
            this.queryCondition.addQueryFilter(queryConditionImpl.getQueryFilters());
        } else if (queryCondition instanceof DefaultQueryFilter) {
            DefaultQueryFilter defaultQueryFilter = (DefaultQueryFilter)queryCondition;
            this.queryCondition.addQueryFilter(defaultQueryFilter.getQueryFilters());
        }
        return this;
    }

    public QueryCondition build() {
        return this.queryCondition;
    }
}

