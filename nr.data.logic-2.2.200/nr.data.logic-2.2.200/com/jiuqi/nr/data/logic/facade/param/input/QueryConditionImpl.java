/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.CheckResultObj;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConSql;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterGroup;
import com.jiuqi.nr.data.logic.facade.param.input.QueryLogicOperator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class QueryConditionImpl
implements QueryCondition {
    private final List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
    private final List<QueryFilter> queryFilters = new ArrayList<QueryFilter>();

    public void addQueryCondition(QueryCondition queryCondition) {
        this.queryConditions.add(queryCondition);
    }

    public void addQueryFilter(QueryFilter queryFilter) {
        this.queryFilters.add(queryFilter);
    }

    public void addQueryFilter(List<QueryFilter> queryFilters) {
        this.queryFilters.addAll(queryFilters);
    }

    @Override
    public QueryConSql buildSql(QueryContext queryContext) {
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sqlCon = new StringBuilder();
        for (QueryCondition queryCondition : this.queryConditions) {
            QueryConSql subQuery = queryCondition.buildSql(queryContext);
            if (subQuery == null) continue;
            sqlCon.append(subQuery.getConSql());
            args.addAll(subQuery.getConArgs());
        }
        return new QueryConSql(sqlCon.toString(), args);
    }

    @Override
    public String buildFml(QueryContext queryContext) {
        StringBuilder fml = new StringBuilder();
        for (QueryCondition queryCondition : this.queryConditions) {
            fml.append(queryCondition.buildFml(queryContext));
        }
        return fml.toString();
    }

    @Override
    public boolean filter(CheckResultObj data) {
        if (CollectionUtils.isEmpty(this.queryConditions)) {
            return true;
        }
        ArrayDeque<Boolean> values = new ArrayDeque<Boolean>();
        ArrayDeque<QueryLogicOperator> logicOperators = new ArrayDeque<QueryLogicOperator>();
        for (QueryCondition queryCondition : this.queryConditions) {
            QueryLogicOperator operator = null;
            if (queryCondition instanceof QueryFilter) {
                operator = ((QueryFilter)queryCondition).getPreLogicOperator();
            } else if (queryCondition instanceof QueryFilterGroup) {
                operator = ((QueryFilterGroup)queryCondition).getPreLogicOperator();
            }
            if (!values.isEmpty() && operator == QueryLogicOperator.AND) {
                boolean prevValue = (Boolean)values.pop();
                values.push(prevValue && queryCondition.filter(data));
                continue;
            }
            values.push(queryCondition.filter(data));
            if (operator == null) continue;
            logicOperators.push(operator);
        }
        while (!logicOperators.isEmpty()) {
            QueryLogicOperator operator = (QueryLogicOperator)logicOperators.pop();
            boolean value1 = (Boolean)values.pop();
            boolean value2 = (Boolean)values.pop();
            if (operator == QueryLogicOperator.OR) {
                values.push(value1 || value2);
                continue;
            }
            values.push(value1 && value2);
        }
        return (Boolean)values.pop();
    }

    public List<QueryFilter> getQueryFilters() {
        return this.queryFilters;
    }
}

