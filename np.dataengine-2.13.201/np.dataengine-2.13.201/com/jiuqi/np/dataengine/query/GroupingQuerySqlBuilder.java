/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.sql.SummarizingMethod
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryRegion;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.nrdb.query.GroupQueryInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.GroupingJdbcResultSetReader;
import com.jiuqi.np.dataengine.reader.GroupingMemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class GroupingQuerySqlBuilder
extends QuerySqlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(GroupingQuerySqlBuilder.class);
    protected boolean wantDetail;
    protected boolean sortGroupingAndDetailRows;
    protected SummarizingMethod summarizingMethod;
    protected HashMap<String, FieldGatherType> uidGatherTypes;
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

    public void setUIDGatherTypes(HashMap<String, FieldGatherType> uidGatherTypes) {
        this.uidGatherTypes = uidGatherTypes;
    }

    public void setGrpByColIndex2Node(HashMap<Integer, ASTNode> grpByColIndex2Node) {
        this.grpByColIndex2Node = grpByColIndex2Node;
    }

    public void setGrpByColIndexEffectiveInGroupingId(ArrayList<Integer> grpByColIndexEffectiveInGroupingId) {
        this.grpByColIndexEffectiveInGroupingId = grpByColIndexEffectiveInGroupingId;
    }

    @Override
    protected void initDBQueryExecutor(QueryContext context) throws ParseException {
        super.initDBQueryExecutor(context);
        if (this.dbQueryExecutor != null) {
            GroupQueryInfo groupInfo = new GroupQueryInfo();
            groupInfo.groupColumns = this.groupColumns;
            groupInfo.grpByColIndex2Node = this.grpByColIndex2Node;
            groupInfo.uidGatherTypes = this.uidGatherTypes;
            this.dbQueryExecutor.getQueryInfo().groupQueryInfo = groupInfo;
        }
    }

    @Override
    protected String getFieldGatherSql(String uid, int dataType) {
        FieldGatherType gatherType = this.uidGatherTypes.get(uid);
        if (gatherType != null) {
            switch (gatherType) {
                case FIELD_GATHER_SUM: {
                    if (dataType == 4 || dataType == 3 || dataType == 10) {
                        return "sum(";
                    }
                    return "min(";
                }
                case FIELD_GATHER_AVG: {
                    return "avg(";
                }
                case FIELD_GATHER_COUNT: {
                    return "count(";
                }
                case FIELD_GATHER_MIN: {
                    return "min(";
                }
                case FIELD_GATHER_MAX: {
                    return "max(";
                }
                case FIELD_GATHER_NONE: {
                    return "none(";
                }
            }
        }
        for (ASTNode grpNode : this.grpByColIndex2Node.values()) {
            QueryField queryField = ExpressionUtils.extractQueryField((IASTNode)grpNode);
            if (queryField == null || queryField.getUID() != uid) continue;
            return null;
        }
        if (dataType == 4 || dataType == 3 || dataType == 10) {
            return "sum(";
        }
        return "min(";
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
            this.orderByClause.append("f__gf desc,");
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
        if (this.needMemoryFilter) {
            this.buildDetailsSql(context, sqlBuilder);
            return;
        }
        this.appendGroupingIdColumn(this.selectFields);
        sqlBuilder.append(" select ").append((CharSequence)this.selectFields).append(" from ").append((CharSequence)this.fromJoinsTables);
        if (this.whereCondition.length() > 0) {
            sqlBuilder.append(" where ").append((CharSequence)this.whereCondition);
        }
        if (this.groupByClause != null && this.groupByClause.length() > 0) {
            this.appendGroupBySql(this.queryParam.getDatabase(), sqlBuilder, this.groupByClause, this.summarizingMethod);
        }
        if (this.wantDetail) {
            sqlBuilder.append(" union all ");
            QuerySqlBuilder detailsSql = this.buildDetailsSql(context, sqlBuilder);
            if (!CollectionUtils.isEmpty(detailsSql.getArgValues())) {
                if (this.argValues != null) {
                    this.argValues.addAll(detailsSql.getArgValues());
                } else {
                    this.argValues = detailsSql.getArgValues();
                }
            }
            if (!CollectionUtils.isEmpty(detailsSql.getArgDataTypes())) {
                if (this.argDataTypes != null) {
                    this.argDataTypes.addAll(detailsSql.getArgDataTypes());
                } else {
                    this.argDataTypes = detailsSql.getArgDataTypes();
                }
            }
        }
        if (this.orderByClause != null && this.orderByClause.length() > 0) {
            sqlBuilder.insert(0, "select * from (");
            sqlBuilder.append(") tt");
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private QuerySqlBuilder buildDetailsSql(QueryContext context, StringBuilder sqlBuilder) throws Exception {
        IQueryFieldDataReader dataReader = context.getDataReader();
        try {
            context.setDataReader(null);
            QuerySqlBuilder builder = this.getDetailQueryBuilder(context);
            builder.buildSql(context);
            sqlBuilder.append("select ").append((CharSequence)builder.selectFields).append(this.getResultTable().groupingFlagColIndex >= 0 ? ", -1 as f__gf" : "").append(this.getResultTable().groupingFlagColIndex >= 0 ? ", " + this.getNullStringColumn() + " as f__gid" : "").append(" from ").append((CharSequence)builder.fromJoinsTables);
            if (builder.whereCondition.length() > 0) {
                sqlBuilder.append(" where ").append((CharSequence)builder.whereCondition);
            }
            QuerySqlBuilder querySqlBuilder = builder;
            return querySqlBuilder;
        }
        finally {
            context.setDataReader(dataReader);
        }
    }

    private QuerySqlBuilder getDetailQueryBuilder(QueryContext context) {
        QuerySqlBuilder builder = new QuerySqlBuilder();
        builder.setQueryParam(this.queryParam);
        builder.setQueryRegion(this.queryRegion);
        builder.setMasterDimensions(this.masterDimensions);
        builder.setPrimaryTable(this.primaryTable);
        builder.setPrimaryTableName(this.primaryTableName);
        builder.setRowFilterNode(this.rowFilterNode);
        builder.setColValueFilters(this.colValueFilters);
        builder.setOrderByItems(this.orderByItems);
        builder.setResultTable(this.getResultTable());
        builder.setUseDNASql(false);
        builder.setUnionQuery(true);
        builder.setSqlSoftParse(this.sqlSoftParse);
        builder.setLoopDimensions(context, this.loopDimensions);
        return builder;
    }

    protected void appendGroupingIdColumn(StringBuilder selectFields) throws SQLInterpretException {
        if (this.summarizingMethod != null && this.grpByColIndexEffectiveInGroupingId != null && this.grpByColIndexEffectiveInGroupingId.size() > 0 && this.groupByClause != null && this.groupByClause.length() > 0) {
            this.appendGroupingIdColumnByDataBase(this.queryParam.getDatabase(), selectFields, this.groupByClause, this.groupFields);
            ReadonlyTableImpl readonlyTableImpl = this.getResultTable();
            readonlyTableImpl.groupingFlagColIndex = this.fieldIndex++;
            selectFields.append(",");
            if (this.groupByClause.length() > 1) {
                this.appendGroupKeyColumnByDataBase(this.queryParam.getDatabase(), selectFields, this.groupByClause, this.groupFields);
            } else {
                selectFields.append((CharSequence)this.groupByClause);
            }
            readonlyTableImpl.groupingIdColIndex = this.fieldIndex++;
            selectFields.append(" as f__gid ");
        } else {
            selectFields.append(" 0 as f__gf ");
            ReadonlyTableImpl readonlyTableImpl = this.getResultTable();
            readonlyTableImpl.groupingFlagColIndex = this.fieldIndex++;
            selectFields.append(", " + this.getNullStringColumn() + " as f__gid ");
            readonlyTableImpl.groupingIdColIndex = this.fieldIndex++;
        }
    }

    private void appendGroupKeyColumnByDataBase(IDatabase database, StringBuilder selectFields, StringBuilder groupByClause, ArrayList<String> groupFields) {
        if (database.isDatabase("MYSQL") || database.isDatabase("MSSQL") || database.isDatabase("TDSQL")) {
            selectFields.append(" concat(");
            selectFields.append((CharSequence)groupByClause);
            selectFields.append(") ");
            return;
        }
        if (database.isDatabase("POSTGRESQL") || database.isDatabase("POSTGRE")) {
            selectFields.append(String.join((CharSequence)"+", groupFields));
            return;
        }
        selectFields.append(String.join((CharSequence)"||", groupFields));
    }

    private void appendGroupingIdColumnByDataBase(IDatabase database, StringBuilder selectFields, StringBuilder groupByClause, ArrayList<String> groupFields) throws SQLInterpretException {
        if (database.isDatabase("ORACLE") || database.isDatabase("DM") || database.isDatabase("GAUSSDB100") || database.isDatabase("HANA") && this.summarizingMethod == SummarizingMethod.RollUp) {
            selectFields.append(" grouping_id(");
            selectFields.append((CharSequence)groupByClause);
            selectFields.append(") as f__gf ");
        } else if (database.isDatabase("MSSQL")) {
            selectFields.append(" grouping_id(");
            selectFields.append((CharSequence)groupByClause);
            selectFields.append(") as f__gf ");
        } else if (database.isDatabase("Informix")) {
            selectFields.append(" (");
            boolean addDot = false;
            int size = groupFields.size() - 1;
            for (String groupField : groupFields) {
                if (addDot) {
                    selectFields.append("+");
                }
                addDot = true;
                selectFields.append("(case NVL(").append(groupField).append(",null) WHEN null THEN 1 ELSE 0 END)*power(2," + size + ")");
                size = size--;
            }
            selectFields.append(") as f__gf ");
        } else if (database.isDatabase("MYSQL")) {
            selectFields.append(" conv(concat(");
            boolean addDot = false;
            for (String groupField : groupFields) {
                if (addDot) {
                    selectFields.append(",");
                }
                addDot = true;
                selectFields.append("if(").append(groupField).append(" is null,1,0)");
            }
            selectFields.append("),2,10) as f__gf ");
        } else if (database.isDatabase("TDSQL")) {
            selectFields.append(" conv(concat(");
            boolean addDot = false;
            for (String groupField : groupFields) {
                if (addDot) {
                    selectFields.append(",");
                }
                addDot = true;
                selectFields.append("case ifnull(").append(groupField).append(",'1') WHEN '1' THEN 1 ELSE 0 END");
            }
            selectFields.append("),2,10) as f__gf ");
        } else if (database.isDatabase("OSCAR")) {
            selectFields.append(" (");
            boolean addDot = false;
            int size = groupFields.size() - 1;
            for (String groupField : groupFields) {
                if (addDot) {
                    selectFields.append("+");
                }
                addDot = true;
                selectFields.append("(case ifnull(").append(groupField).append(",null) WHEN null THEN 1 ELSE 0 END)*power(2," + size + ")");
                size = size--;
            }
            selectFields.append(") as f__gf ");
        } else {
            selectFields.append(" 0 as f__gf ");
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
        String groupField = this.getTableAlias(qContext, queryField.getTable()) + "." + queryField.getFieldName();
        this.groupByClause.append(groupField);
        this.groupFields.add(groupField);
    }

    @Override
    protected List<QuerySqlBuilder> dividTables(List<QueryTable> tables, QueryContext context) throws ParseException {
        ArrayList<GroupingQuerySqlBuilder> builders = null;
        if (!tables.isEmpty()) {
            QueryFields groupQueryFields = this.getGroupFields();
            builders = new ArrayList<GroupingQuerySqlBuilder>();
            for (QueryTable table : tables) {
                HashMap<Integer, ASTNode> grpNodes = new HashMap<Integer, ASTNode>(this.grpByColIndex2Node);
                GroupingQuerySqlBuilder subSqlBuilder = new GroupingQuerySqlBuilder();
                QueryRegion subRegion = new QueryRegion(this.queryRegion.getDimensions(), subSqlBuilder);
                QueryFields queryFields = this.queryRegion.getTableFields(table);
                QueryFields currentGroupFields = this.getCurrentGroupingFields(queryFields, groupQueryFields, table, context, grpNodes);
                subRegion.addQueryFields(queryFields);
                subRegion.addQueryFields(currentGroupFields);
                subSqlBuilder.setUseDNASql(this.useDNASql);
                subSqlBuilder.setPrimaryTable(table);
                subSqlBuilder.setQueryParam(this.queryParam);
                subSqlBuilder.setResultTable(this.resultTable);
                subSqlBuilder.setUseDNASql(this.useDNASql);
                subSqlBuilder.setRowFilterNode(this.rowFilterNode);
                subSqlBuilder.setColValueFilters(this.colValueFilters);
                subSqlBuilder.setForUpdateOnly(this.forUpdateOnly);
                subSqlBuilder.setRecKeys(this.recKeys);
                subSqlBuilder.setWantDetail(this.wantDetail);
                subSqlBuilder.setSortGroupingAndDetailRows(this.sortGroupingAndDetailRows);
                subSqlBuilder.setSummarizingMethod(this.summarizingMethod);
                subSqlBuilder.setGroupColumns(this.groupColumns);
                subSqlBuilder.setUIDGatherTypes(this.uidGatherTypes);
                subSqlBuilder.setGrpByColIndex2Node(grpNodes);
                subSqlBuilder.setGrpByColIndexEffectiveInGroupingId(this.grpByColIndexEffectiveInGroupingId);
                builders.add(subSqlBuilder);
                this.queryRegion.getAllTableFields().remove(table);
            }
            tables.clear();
            this.addToMainBuilder(groupQueryFields, context);
        }
        return builders;
    }

    private QueryFields getCurrentGroupingFields(QueryFields queryFields, QueryFields groupQueryFields, QueryTable table, QueryContext qContext, HashMap<Integer, ASTNode> grpNodes) throws ParseException {
        IDatabase database;
        IDatabase iDatabase = database = qContext == null ? null : qContext.getQueryParam().getDatabase();
        if (database != null && (database.isDatabase("MYSQL") || !database.getDescriptor().supportFullJoin())) {
            DefinitionsCache cache = qContext.getExeContext().getCache();
            QueryFields grpingFields = new QueryFields();
            for (Map.Entry<Integer, ASTNode> queryField : grpNodes.entrySet()) {
                TableModelRunInfo tableRunInfo;
                QueryField qField;
                ASTNode astNode = queryField.getValue();
                QueryFields qFields = ExpressionUtils.getAllQueryFields((IASTNode)astNode);
                boolean hasAdd = false;
                if (qFields.getCount() == 1 && !queryFields.hasField(qField = qFields.getItem(0)) && !table.getTableName().equalsIgnoreCase(qField.getTableName()) && (tableRunInfo = cache.getDataModelDefinitionsCache().getTableInfo(qField.getTableName())).isKeyField(qField.getFieldCode())) {
                    String dimensionName = tableRunInfo.getDimensionName(qField.getFieldCode());
                    TableModelRunInfo currentTable = cache.getDataModelDefinitionsCache().getTableInfo(table.getTableName());
                    ColumnModelDefine fieldDefine = currentTable.getDimensionField(dimensionName);
                    if (fieldDefine != null) {
                        QueryField currentField = null;
                        try {
                            currentField = cache.extractQueryField(qContext.getExeContext(), fieldDefine, null, null);
                        }
                        catch (ExpressionException expressionException) {
                            // empty catch block
                        }
                        if (currentField != null) {
                            DynamicDataNode currentNode = new DynamicDataNode(currentField);
                            grpNodes.put(queryField.getKey(), currentNode);
                            grpingFields.add(currentField);
                            hasAdd = true;
                        }
                    }
                }
                if (hasAdd) continue;
                grpingFields.addAll(qFields);
            }
            return grpingFields;
        }
        return groupQueryFields;
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

    private void addToMainBuilder(QueryFields groupQueryFields, QueryContext context) throws ParseException {
        for (QueryField queryField : groupQueryFields) {
            DefinitionsCache cache;
            TableModelRunInfo tableInfo;
            ColumnModelDefine fieldDefine;
            if (queryField.getTable().equals(this.primaryTable) || (fieldDefine = (tableInfo = (cache = context.getExeContext().getCache()).getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName())).getFieldByName(queryField.getFieldName())) == null) continue;
            try {
                QueryField ownGroupField = cache.extractQueryField(context.getExeContext(), fieldDefine, null, null);
                this.queryRegion.addQueryField(ownGroupField);
            }
            catch (ExpressionException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.doInit(context);
    }

    private QueryFields getGroupFields() {
        QueryFields groupQueryFields = new QueryFields();
        if (this.grpByColIndex2Node == null || this.grpByColIndex2Node.size() <= 0) {
            return groupQueryFields;
        }
        for (Map.Entry<Integer, ASTNode> groupNode : this.grpByColIndex2Node.entrySet()) {
            ASTNode node = groupNode.getValue();
            QueryFields queryFields = ExpressionUtils.getAllQueryFields((IASTNode)node);
            groupQueryFields.addAll(queryFields);
        }
        return groupQueryFields;
    }

    public boolean isWantDetail() {
        return this.wantDetail;
    }

    @Override
    public DimensionValueSet buildRowKeys(DimensionValueSet masterKeys, IQueryFieldDataReader reader) throws Exception {
        int groupingFlag = -1;
        if (this.getResultTable().groupingIdColIndex != -1 && this.getResultTable().groupingFlagColIndex != -1) {
            groupingFlag = Convert.toInt((Object)reader.readData(this.getResultTable().groupingFlagColIndex));
        }
        DimensionValueSet rowKeys = new DimensionValueSet();
        DimensionSet loopDimensions = this.getLoopDimensions();
        HashMap<String, ColumnModelDefine> dimensionFields = this.getDimensionFields();
        for (int i = 0; i < loopDimensions.size(); ++i) {
            String dimensionName = loopDimensions.get(i);
            Object masterValue = null;
            if (masterKeys.hasValue(dimensionName)) {
                Object value = masterKeys.getValue(dimensionName);
                if (!(value instanceof List)) {
                    masterValue = value;
                }
            } else if (this.summarizingMethod != null && groupingFlag >= 0) continue;
            if (masterValue == null) {
                masterValue = reader.readData(i + this.getRowKeyFieldStartIndex());
            }
            if (dimensionFields.containsKey(dimensionName)) {
                masterValue = DataEngineConsts.formatData(dimensionFields.get(dimensionName), masterValue, null);
            }
            if (masterValue == null) continue;
            rowKeys.setValue(dimensionName, masterValue);
        }
        if (this.getResultTable().groupingIdColIndex != -1 && this.getResultTable().groupingFlagColIndex != -1 && groupingFlag >= 0) {
            Object groupId = reader.readData(this.getResultTable().groupingIdColIndex);
            if (groupId == null) {
                groupId = "";
            }
            rowKeys.setValue("GROUP_KEY", groupId);
            rowKeys.setValue("GroupingDeep", (groupingFlag + 1) / 2);
        }
        return rowKeys;
    }

    private String getNullStringColumn() {
        IDatabase database = this.queryParam.getDatabase();
        if (database.isDatabase("DERBY") || database.isDatabase("Informix")) {
            return "''";
        }
        return "null";
    }
}

