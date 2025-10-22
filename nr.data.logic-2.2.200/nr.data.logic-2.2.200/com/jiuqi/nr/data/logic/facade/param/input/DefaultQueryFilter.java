/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.CheckResultObj;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCol;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConSql;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConditionBuilder;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConditionImpl;
import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckState;
import java.util.Collections;
import java.util.List;

public enum DefaultQueryFilter implements QueryCondition
{
    DES_CHECK_PASS{

        @Override
        public QueryCondition getCondition() {
            if (this.queryCondition == null) {
                this.queryCondition = new QueryConditionBuilder(QueryCol.ERROR_DES_STATE, QueryFilterOperator.EQUALS, DesCheckState.PASS.getCode()).build();
            }
            return this.queryCondition;
        }
    }
    ,
    DES_CHECK_FAIL{

        @Override
        public QueryCondition getCondition() {
            if (this.queryCondition == null) {
                this.queryCondition = new QueryConditionBuilder(QueryCol.ERROR_DES_STATE, QueryFilterOperator.EQUALS, DesCheckState.FAIL.getCode()).build();
            }
            return this.queryCondition;
        }
    }
    ,
    DES_IS_NULL{

        @Override
        public QueryCondition getCondition() {
            if (this.queryCondition == null) {
                this.queryCondition = new QueryConditionBuilder(QueryCol.CHECK_ERROR_DES, QueryFilterOperator.ISNULL, null).build();
            }
            return this.queryCondition;
        }
    }
    ,
    DES_IS_NOTNULL{

        @Override
        public QueryCondition getCondition() {
            if (this.queryCondition == null) {
                this.queryCondition = new QueryConditionBuilder(QueryCol.CHECK_ERROR_DES, QueryFilterOperator.IS_NOTNULL, null).build();
            }
            return this.queryCondition;
        }
    }
    ,
    DES_IS_NOTNULL_AND_CHECK_PASS{

        @Override
        public QueryCondition getCondition() {
            if (this.queryCondition == null) {
                this.queryCondition = new QueryConditionBuilder(QueryCol.CHECK_ERROR_DES, QueryFilterOperator.IS_NOTNULL, null).and(QueryCol.ERROR_DES_STATE, QueryFilterOperator.EQUALS, DesCheckState.PASS.getCode()).build();
            }
            return this.queryCondition;
        }
    }
    ,
    DES_IS_NULL_OR_CHECK_FAIL{

        @Override
        public QueryCondition getCondition() {
            if (this.queryCondition == null) {
                this.queryCondition = new QueryConditionBuilder(QueryCol.CHECK_ERROR_DES, QueryFilterOperator.ISNULL, null).or(QueryCol.ERROR_DES_STATE, QueryFilterOperator.EQUALS, DesCheckState.FAIL.getCode()).build();
            }
            return this.queryCondition;
        }
    }
    ,
    NO_FILTER{

        @Override
        public QueryCondition getCondition() {
            return null;
        }

        @Override
        public QueryConSql buildSql(QueryContext queryContext) {
            return null;
        }

        @Override
        public String buildFml(QueryContext queryContext) {
            return "";
        }

        @Override
        public boolean filter(CheckResultObj data) {
            return true;
        }
    };

    QueryCondition queryCondition;

    public abstract QueryCondition getCondition();

    @Override
    public QueryConSql buildSql(QueryContext queryContext) {
        return this.getCondition().buildSql(queryContext);
    }

    @Override
    public String buildFml(QueryContext queryContext) {
        return this.getCondition().buildFml(queryContext);
    }

    @Override
    public boolean filter(CheckResultObj data) {
        return this.getCondition().filter(data);
    }

    public List<QueryFilter> getQueryFilters() {
        QueryCondition condition = this.getCondition();
        if (condition instanceof QueryConditionImpl) {
            return ((QueryConditionImpl)condition).getQueryFilters();
        }
        return Collections.emptyList();
    }
}

