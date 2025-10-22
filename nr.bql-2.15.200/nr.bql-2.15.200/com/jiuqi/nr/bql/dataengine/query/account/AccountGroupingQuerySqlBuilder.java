/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.sql.SummarizingMethod
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.nr.bql.dataengine.query.account;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nr.bql.dataengine.query.account.AccountQuerySqlBuilder;
import com.jiuqi.nr.bql.dataengine.reader.GroupingJdbcResultSetReader;
import com.jiuqi.nr.bql.dataengine.reader.GroupingMemoryDataSetReader;
import com.jiuqi.nvwa.definition.common.AggrType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountGroupingQuerySqlBuilder
extends AccountQuerySqlBuilder {
    protected boolean wantDetail;
    protected boolean sortGroupingAndDetailRows;
    protected SummarizingMethod summarizingMethod;
    protected HashMap<String, AggrType> uidGatherTypes;
    protected List<Integer> groupColumns;
    protected HashMap<Integer, ASTNode> grpByColIndex2Node;
    protected ArrayList<Integer> grpByColIndexEffectiveInGroupingId;
    protected ArrayList<String> groupFields = new ArrayList();

    public void setWantDetail(boolean wantDetail) {
        this.wantDetail = wantDetail;
    }

    public void setSortGroupingAndDetailRows(boolean sortGroupingAndDetailRows) {
        this.sortGroupingAndDetailRows = sortGroupingAndDetailRows;
    }

    public void setSummarizingMethod(SummarizingMethod summarizingMethod) {
        this.summarizingMethod = summarizingMethod;
    }

    public void setGroupColumns(List<Integer> groupColumns) {
        this.groupColumns = groupColumns;
    }

    public void setUIDGatherTypes(HashMap<String, AggrType> uidGatherTypes) {
        this.uidGatherTypes = uidGatherTypes;
    }

    public void setGrpByColIndex2Node(HashMap<Integer, ASTNode> grpByColIndex2Node) {
        this.grpByColIndex2Node = grpByColIndex2Node;
    }

    public void setGrpByColIndexEffectiveInGroupingId(ArrayList<Integer> grpByColIndexEffectiveInGroupingId) {
        this.grpByColIndexEffectiveInGroupingId = grpByColIndexEffectiveInGroupingId;
    }

    @Override
    protected String getFieldGatherSql(String uid, AggrType gatherType, int dataType) {
        if (gatherType != null) {
            switch (gatherType) {
                case SUM: {
                    if (dataType == 4 || dataType == 3 || dataType == 10) {
                        return "sum(";
                    }
                    return "min(";
                }
                case AVERAGE: {
                    return "avg(";
                }
                case COUNT: {
                    return "count(";
                }
                case DISTINCT_COUNT: {
                    return "count(distinct ";
                }
                case MIN: {
                    return "min(";
                }
                case MAX: {
                    return "max(";
                }
                case NONE: {
                    return "none(";
                }
            }
        }
        if (dataType == 4 || dataType == 3 || dataType == 10) {
            return "sum(";
        }
        return "min(";
    }

    @Override
    protected AggrType getAggrType(String uid) {
        AggrType gatherType = this.uidGatherTypes.get(uid);
        return gatherType;
    }

    @Override
    protected void buildGroupBy(QueryContext context) throws Exception {
        if (this.grpByColIndex2Node == null || this.grpByColIndex2Node.size() <= 0) {
            return;
        }
        boolean hasDot = false;
        for (Integer columnIndex : this.groupColumns) {
            if (hasDot) {
                this.groupByClause.append(",");
            }
            hasDot = true;
            this.getGroupByField(context, columnIndex);
        }
    }

    @Override
    protected void buildOrderBy(QueryContext context) throws Exception {
        if (this.sortGroupingAndDetailRows) {
            boolean hasDot = false;
            if (this.groupColumns != null && this.groupColumns.size() > 0) {
                for (Integer columnIndex : this.groupColumns) {
                    if (hasDot) {
                        this.orderByClause.append(",");
                    }
                    hasDot = true;
                    this.getOrderByField(columnIndex);
                }
            }
            if (hasDot) {
                this.orderByClause.append(",");
            }
            if (this.orderByItems == null || this.orderByItems.isEmpty()) {
                this.orderByClause.setLength(this.orderByClause.length() - 1);
            }
        }
        super.buildOrderBy(context);
    }

    @Override
    protected boolean isGroupingQuery() {
        return true;
    }

    @Override
    protected void buildSelectSql(QueryContext context, StringBuilder sqlBuilder) throws Exception {
        if (this.selectFields.charAt(this.selectFields.length() - 1) == ',') {
            this.selectFields.setLength(this.selectFields.length() - 1);
        }
        sqlBuilder.append("select ").append((CharSequence)this.selectFields).append(" from ");
        if (this.isTrackHistory.booleanValue()) {
            sqlBuilder.append("(").append((CharSequence)this.fromJoinsTables).append(")");
        } else {
            sqlBuilder.append((CharSequence)this.fromJoinsTables);
        }
        this.appendSpace(sqlBuilder);
        sqlBuilder.append("tt");
        this.appendSpace(sqlBuilder);
        if (this.joinTableAndCondition.length() > 0) {
            sqlBuilder.append("left join");
            this.appendSpace(sqlBuilder);
            sqlBuilder.append((CharSequence)this.joinTableAndCondition);
        }
        this.leftJoinDimTables(context, sqlBuilder);
        if (this.groupByClause != null && this.groupByClause.length() > 0) {
            this.appendGroupBySql(this.queryParam.getDatabase(), sqlBuilder, this.groupByClause, this.summarizingMethod);
        }
        if (this.orderByClause != null && this.orderByClause.length() > 0) {
            sqlBuilder.insert(0, "select * from (");
            sqlBuilder.append(") ttg");
            if (this.isDoPage() && this.queryParam.getDatabase().isDatabase("GaussDB")) {
                String[] orderyFields;
                sqlBuilder.append(" ${OrderBy(");
                for (String orderyField : orderyFields = this.orderByClause.toString().split(",")) {
                    sqlBuilder.append("\"").append(orderyField).append("\"").append(",");
                }
                sqlBuilder.setLength(sqlBuilder.length() - 1);
                sqlBuilder.append(")}");
            } else {
                sqlBuilder.append(" order by ").append((CharSequence)this.orderByClause);
            }
        }
    }

    protected void appendGroupBySql(IDatabase database, StringBuilder sqlBuilder, StringBuilder groupByClause, SummarizingMethod summarizingMethod) throws SQLInterpretException {
        if (database.isDatabase("MYSQL")) {
            sqlBuilder.append(" group by ");
            sqlBuilder.append((CharSequence)groupByClause);
            if (summarizingMethod == SummarizingMethod.RollUp) {
                sqlBuilder.append(" with rollup ");
            } else if (summarizingMethod == SummarizingMethod.Cube) {
                throw new SQLInterpretException("Mysql\u4e0d\u652f\u6301Cube\u67e5\u8be2");
            }
        } else if (database.isDatabase("ORACLE") || database.isDatabase("DM") || database.isDatabase("HANA") || database.isDatabase("KINGBASE") || database.isDatabase("KINGBASE8")) {
            sqlBuilder.append(" group by ");
            if (summarizingMethod == SummarizingMethod.RollUp) {
                sqlBuilder.append("rollup(");
            } else if (summarizingMethod == SummarizingMethod.Cube) {
                sqlBuilder.append("cube(");
            }
            sqlBuilder.append((CharSequence)groupByClause);
            if (summarizingMethod == SummarizingMethod.RollUp || summarizingMethod == SummarizingMethod.Cube) {
                sqlBuilder.append(")");
            }
        } else {
            sqlBuilder.append(" group by ");
            sqlBuilder.append((CharSequence)groupByClause);
        }
    }

    private void getOrderByField(Integer columnIndex) throws Exception {
        ASTNode node = this.grpByColIndex2Node.get(columnIndex);
        if (node == null) {
            throw new IncorrectQueryException("\u4e0d\u652f\u6301\u7684\u5206\u7ec4\u8868\u8fbe\u5f0f");
        }
        QueryField queryField = ExpressionUtils.extractQueryField((IASTNode)node);
        if (queryField != null) {
            if (this.fieldAliases.containsKey(queryField.getUID())) {
                this.orderByClause.append((String)this.fieldAliases.get(queryField.getUID()));
            } else {
                this.orderByClause.append(queryField.getFieldName());
            }
            if (this.queryParam.getDatabase().isDatabase("ORACLE")) {
                this.orderByClause.append(" nulls first");
            }
        } else {
            throw new IncorrectQueryException("\u4e0d\u652f\u6301\u7684\u5206\u7ec4\u8868\u8fbe\u5f0f");
        }
    }

    protected void getGroupByField(QueryContext qContext, Integer columnIndex) throws Exception {
        ASTNode node = this.grpByColIndex2Node.get(columnIndex);
        if (node == null) {
            throw new IncorrectQueryException("\u4e0d\u652f\u6301\u7684\u5206\u7ec4\u8868\u8fbe\u5f0f");
        }
        QueryField queryField = ExpressionUtils.extractQueryField((IASTNode)node);
        if (queryField == null) {
            throw new IncorrectQueryException("\u4e0d\u652f\u6301\u7684\u5206\u7ec4\u8868\u8fbe\u5f0f");
        }
        String groupField = "tt." + queryField.getFieldName();
        this.groupByClause.append(groupField);
        this.groupFields.add(groupField);
    }

    @Override
    protected IQueryFieldDataReader getDataReader(QueryContext queryContext) {
        IQueryFieldDataReader dataReader = queryContext.getDataReader();
        if (dataReader == null) {
            dataReader = this.queryRegion.getType() == 0 ? new GroupingJdbcResultSetReader(queryContext) : new GroupingMemoryDataSetReader(queryContext);
            queryContext.setDataReader(dataReader);
        }
        return dataReader;
    }

    public boolean isWantDetail() {
        return this.wantDetail;
    }
}

