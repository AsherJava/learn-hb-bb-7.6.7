/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.patterns.Wildcard
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.patterns.Wildcard;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultObj;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCol;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConSql;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator;
import com.jiuqi.nr.data.logic.facade.param.input.QueryLogicOperator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryFilter
implements QueryCondition {
    private final QueryCol queryCol;
    private final QueryFilterOperator filterOperator;
    private final Object filterValue;
    private final QueryLogicOperator preLogicOperator;
    private Wildcard wildcard;

    public QueryFilter(QueryCol queryCol, QueryFilterOperator filterOperator, Object filterValue, QueryLogicOperator preLogicOperator) {
        this.queryCol = queryCol;
        this.filterOperator = filterOperator;
        this.filterValue = filterValue;
        this.preLogicOperator = preLogicOperator;
    }

    @Override
    public QueryConSql buildSql(QueryContext queryContext) {
        StringBuilder conSql = new StringBuilder();
        if (this.preLogicOperator != null) {
            conSql.append(" ").append(this.preLogicOperator.toSql(queryContext)).append(" ");
        }
        ArrayList<Object> conArgs = new ArrayList<Object>();
        switch (this.filterOperator) {
            case EQUALS: {
                conSql.append(this.queryCol.toSql(queryContext)).append("=").append("?");
                conArgs.add(this.filterValue);
                break;
            }
            case IN: {
                if (this.filterValue instanceof List) {
                    List valueList = (List)this.filterValue;
                    conArgs.addAll(valueList);
                    conSql.append(this.queryCol.toSql(queryContext)).append(" IN ").append("(");
                    for (int i = 0; i < valueList.size(); ++i) {
                        conSql.append("?,");
                    }
                    conSql.setLength(conSql.length() - 1);
                    conSql.append(")");
                    break;
                }
                return new QueryConSql(null, null);
            }
            case ISNULL: {
                conSql.append(this.queryCol.toSql(queryContext)).append(" IS NULL");
                break;
            }
            case IS_NOTNULL: {
                conSql.append(this.queryCol.toSql(queryContext)).append(" IS NOT NULL");
                break;
            }
            case LIKE: {
                conSql.append(this.queryCol.toSql(queryContext)).append(" LIKE ").append("?");
                conArgs.add(this.filterValue);
                break;
            }
            default: {
                return new QueryConSql(null, null);
            }
        }
        return new QueryConSql(conSql.toString(), conArgs);
    }

    @Override
    public String buildFml(QueryContext queryContext) {
        StringBuilder fml = this.preLogicOperator == null ? new StringBuilder() : new StringBuilder(" " + this.preLogicOperator.toFml(queryContext) + " ");
        switch (this.filterOperator) {
            case EQUALS: {
                if (this.filterValue instanceof Number) {
                    return fml.append(this.queryCol.toFml(queryContext)).append("=").append(this.filterValue).toString();
                }
                return fml.append(this.queryCol.toFml(queryContext)).append("=").append("'").append(this.filterValue).append("'").toString();
            }
            case IN: {
                if (this.filterValue instanceof List) {
                    List valueList = (List)this.filterValue;
                    if (!valueList.isEmpty()) {
                        fml.append(this.queryCol.toFml(queryContext));
                        List strValueList = valueList.stream().map(String::valueOf).collect(Collectors.toList());
                        Object firstValue = valueList.get(0);
                        if (firstValue instanceof Number) {
                            String join = String.join((CharSequence)",", strValueList);
                            fml.append(" IN{").append(join).append("}");
                        } else {
                            String join = String.join((CharSequence)"','", strValueList);
                            fml.append(" IN{'").append(join).append("'}");
                        }
                        return fml.toString();
                    }
                    return "";
                }
                return "";
            }
            case ISNULL: {
                return fml.append(" ISNULL").append("(").append(this.queryCol.toFml(queryContext)).append(")").toString();
            }
            case IS_NOTNULL: {
                return fml.append(" ISNOTNULL").append("(").append(this.queryCol.toFml(queryContext)).append(")").toString();
            }
            case LIKE: {
                return fml.append(this.queryCol.toFml(queryContext)).append(" LIKE ").append("'").append(this.filterValue).append("'").toString();
            }
        }
        return "";
    }

    @Override
    public boolean filter(CheckResultObj data) {
        Object colData = this.queryCol.getColData(data);
        switch (this.filterOperator) {
            case EQUALS: {
                return this.filterValue.equals(colData);
            }
            case IN: {
                if (this.filterValue instanceof List) {
                    List valueList = (List)this.filterValue;
                    return valueList.contains(colData);
                }
                return false;
            }
            case ISNULL: {
                return colData == null || StringUtils.isEmpty((String)colData.toString());
            }
            case IS_NOTNULL: {
                return colData != null && StringUtils.isNotEmpty((String)colData.toString());
            }
            case LIKE: {
                return colData != null && this.getWildcard().match(colData.toString());
            }
        }
        return false;
    }

    QueryLogicOperator getPreLogicOperator() {
        return this.preLogicOperator;
    }

    public QueryCol getQueryCol() {
        return this.queryCol;
    }

    public QueryFilterOperator getFilterOperator() {
        return this.filterOperator;
    }

    public Object getFilterValue() {
        return this.filterValue;
    }

    private Wildcard getWildcard() {
        if (this.wildcard == null) {
            String pattern = this.filterValue.toString();
            this.wildcard = new Wildcard(pattern, '%', '_');
        }
        return this.wildcard;
    }
}

