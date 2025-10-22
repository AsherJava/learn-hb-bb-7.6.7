/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultObj;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConSql;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;
import com.jiuqi.nr.data.logic.facade.param.input.QueryLogicOperator;

public class QueryFilterGroup
implements QueryCondition {
    private final QueryCondition queryCondition;
    private final QueryLogicOperator preLogicOperator;

    public QueryFilterGroup(QueryCondition queryCondition, QueryLogicOperator preLogicOperator) {
        this.queryCondition = queryCondition;
        this.preLogicOperator = preLogicOperator;
    }

    @Override
    public QueryConSql buildSql(QueryContext queryContext) {
        QueryConSql subQueryConSql = this.queryCondition.buildSql(queryContext);
        if (subQueryConSql == null) {
            return null;
        }
        String pre = this.preLogicOperator == null ? "" : " " + this.preLogicOperator.toSql(queryContext) + " ";
        return new QueryConSql(pre + "(" + subQueryConSql.getConSql() + ")", subQueryConSql.getConArgs());
    }

    @Override
    public String buildFml(QueryContext queryContext) {
        String subFml = this.queryCondition.buildFml(queryContext);
        if (StringUtils.isEmpty((String)subFml)) {
            return "";
        }
        String pre = this.preLogicOperator == null ? "" : " " + this.preLogicOperator.toFml(queryContext) + " ";
        return pre + "(" + subFml + ")";
    }

    @Override
    public boolean filter(CheckResultObj data) {
        return this.queryCondition.filter(data);
    }

    QueryLogicOperator getPreLogicOperator() {
        return this.preLogicOperator;
    }
}

