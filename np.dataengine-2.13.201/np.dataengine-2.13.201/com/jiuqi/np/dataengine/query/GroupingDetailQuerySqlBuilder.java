/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.GroupingQuerySqlBuilder;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.setting.SqlJoinItem;
import com.jiuqi.np.dataengine.setting.SqlJoinOneItem;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupingDetailQuerySqlBuilder
extends GroupingQuerySqlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(GroupingDetailQuerySqlBuilder.class);
    private final List<QueryTable> orderJoinTables = new ArrayList<QueryTable>();
    private StringBuilder gatherFields = null;
    private final Map<QueryTable, StringBuilder> tableSqls = new HashMap<QueryTable, StringBuilder>();
    private final Map<QueryTable, Integer> dimesionFieldIndexes = new HashMap<QueryTable, Integer>();
    private final List<TableModelRunInfo> tableInfoList = new ArrayList<TableModelRunInfo>();
    private final List<QueryTable> queryTableList = new ArrayList<QueryTable>();

    @Override
    public void setPrimaryTable(QueryTable primaryTable) {
        this.primaryTable = primaryTable;
        if (this.primaryTableName == null) {
            this.setPrimaryTableName(primaryTable.getTableName());
        }
    }

    @Override
    public String buildSql(QueryContext context) throws Exception {
        this.doInit(context);
        return this.buildQuerySql(context);
    }

    @Override
    public String buildQuerySql(QueryContext context) throws Exception {
        this.selectFields = new StringBuilder();
        this.gatherFields = new StringBuilder();
        this.fromJoinsTables = new StringBuilder();
        this.whereCondition = new StringBuilder();
        this.groupByClause = new StringBuilder();
        this.orderByClause = new StringBuilder();
        this.tableSqls.clear();
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            StringBuilder tableSql = new StringBuilder("select ");
            this.tableSqls.put(table, tableSql);
        }
        this.fieldIndex = 1;
        if (this.sqlSoftParse) {
            if (this.argValues == null) {
                this.argValues = new ArrayList();
            } else {
                this.argValues.clear();
            }
        }
        StringBuilder sqlBuilder = new StringBuilder();
        this.appendQueryFields(context);
        this.appenddimensionFields(context);
        this.appendRowFilterCondition(this.whereCondition, context);
        this.buildGroupBy(context);
        this.buildOrderBy(context);
        StringBuilder subQuerySql = this.joinSubQueryTables(context);
        this.buildSelectSql(context, sqlBuilder, subQuerySql);
        this.sql = sqlBuilder.toString();
        return this.sql;
    }

    private void appendQueryFields(QueryContext context) throws ParseException {
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            String mainQueryTableAlias = this.getTableAlias(context, table, true);
            String subQueryTableAlias = this.getTableAlias(context, table, false);
            StringBuilder tableSql = this.tableSqls.get(table);
            TableModelRunInfo tableRunInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
            for (QueryField queryField : this.queryRegion.getTableFields(table)) {
                String gather = this.getFieldGatherSql(queryField.getUID(), queryField.getDataType());
                if (gather != null) {
                    this.addGatherField(this.gatherFields, gather, mainQueryTableAlias, queryField.getFieldName(), queryField.getDataType());
                    this.selectFields.append(mainQueryTableAlias).append(".").append(queryField.getFieldName());
                } else if (table.getIsLj()) {
                    this.selectFields.append("sum(");
                    this.selectFields.append(mainQueryTableAlias).append(".").append(queryField.getFieldName());
                    this.selectFields.append(")");
                    this.gatherFields.append("sum(");
                    this.gatherFields.append(mainQueryTableAlias).append(".").append(queryField.getFieldName());
                    this.gatherFields.append(")");
                } else {
                    this.selectFields.append(mainQueryTableAlias).append(".").append(queryField.getFieldName());
                    this.gatherFields.append(mainQueryTableAlias).append(".").append(queryField.getFieldName());
                }
                String fieldAlias = "c_" + this.fieldIndex;
                this.selectFields.append(" as ").append(fieldAlias).append(",");
                this.gatherFields.append(" as ").append(fieldAlias).append(",");
                if (!tableRunInfo.isKeyField(queryField.getFieldCode())) {
                    tableSql.append(subQueryTableAlias).append(".").append(queryField.getFieldName()).append(",");
                }
                this.fieldAliases.put(queryField.getUID(), fieldAlias);
                this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, this.fieldIndex);
                ++this.fieldIndex;
            }
        }
    }

    private String getTableAlias(QueryContext qContext, QueryTable queryTable, boolean mainQuery) {
        if (mainQuery) {
            return "s" + this.getTableAlias(qContext, queryTable);
        }
        return this.getTableAlias(qContext, queryTable);
    }

    private void appenddimensionFields(QueryContext context) throws ParseException {
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            String mainQueryTableAlias = this.getTableAlias(context, table, true);
            String subQueryTableAlias = this.getTableAlias(context, table, false);
            StringBuilder tableSql = this.tableSqls.get(table);
            TableModelRunInfo tableRunInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
            this.dimesionFieldIndexes.put(table, this.fieldIndex);
            for (ColumnModelDefine fieldDefine : tableRunInfo.getDimFields()) {
                int fieldDataType = DataTypesConvert.fieldTypeToDataType(fieldDefine.getColumnType());
                String gather = "none";
                this.addGatherField(this.gatherFields, gather, mainQueryTableAlias, fieldDefine.getName(), fieldDataType);
                this.selectFields.append(mainQueryTableAlias).append(".").append(fieldDefine.getName());
                String fieldAlias = "c_" + this.fieldIndex;
                this.selectFields.append(" as ").append(fieldAlias).append(",");
                this.gatherFields.append(" as ").append(fieldAlias).append(",");
                tableSql.append(subQueryTableAlias).append(".").append(fieldDefine.getName()).append(",");
                this.fieldAliases.putIfAbsent(fieldDefine.getID(), fieldAlias);
                ++this.fieldIndex;
            }
            ColumnModelDefine floatOrderfield = tableRunInfo.getOrderField();
            if (floatOrderfield != null) {
                tableSql.append(subQueryTableAlias).append(".").append(floatOrderfield.getName()).append(",");
                if (table.equals(this.primaryTable)) {
                    String fieldName = floatOrderfield.getName();
                    if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                        this.gatherFields.append("min(");
                        this.gatherFields.append(mainQueryTableAlias).append(".").append(fieldName);
                        this.gatherFields.append(")");
                        this.selectFields.append(mainQueryTableAlias).append(".").append(fieldName);
                    } else {
                        this.gatherFields.append(mainQueryTableAlias).append(".").append(fieldName);
                        this.selectFields.append(mainQueryTableAlias).append(".").append(fieldName);
                    }
                    String fieldAlias = "c_" + this.fieldIndex;
                    this.selectFields.append(" as ").append(fieldAlias).append(",");
                    this.gatherFields.append(" as ").append(fieldAlias).append(",");
                    this.fieldAliases.putIfAbsent(floatOrderfield.getID(), fieldAlias);
                    ++this.fieldIndex;
                }
            }
            tableSql.setLength(tableSql.length() - 1);
        }
    }

    private StringBuilder joinSubQueryTables(QueryContext context) throws ParseException, SQLException {
        boolean addRowNumColumn = false;
        if (!this.orderJoinTables.isEmpty()) {
            addRowNumColumn = true;
        }
        StringBuilder subQuerySql = new StringBuilder();
        StringBuilder tableSql = this.buildSubQueryByTable(context, this.primaryTable, addRowNumColumn);
        subQuerySql.append("(").append((CharSequence)tableSql).append(") ").append(this.getTableAlias(context, this.primaryTable, true));
        DataModelDefinitionsCache dataDefinitionsCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        TableModelRunInfo tableInfo = dataDefinitionsCache.getTableInfo(this.primaryTable.getTableName());
        this.tableInfoList.add(tableInfo);
        this.queryTableList.add(this.primaryTable);
        for (QueryTable table : this.fullJoinTables) {
            this.joinTable(subQuerySql, context, table, false, false);
            this.tableInfoList.add(dataDefinitionsCache.getTableInfo(table.getTableName()));
            this.queryTableList.add(table);
        }
        for (QueryTable table : this.orderJoinTables) {
            this.joinTable(subQuerySql, context, table, true, false);
            this.tableInfoList.add(dataDefinitionsCache.getTableInfo(table.getTableName()));
            this.queryTableList.add(table);
        }
        for (QueryTable table : this.leftJoinTables) {
            this.joinTable(subQuerySql, context, table, false, false);
            this.tableInfoList.add(dataDefinitionsCache.getTableInfo(table.getTableName()));
            this.queryTableList.add(table);
        }
        for (QueryTable table : this.emptyJoinTables) {
            this.joinTable(subQuerySql, context, table, false, false);
            this.tableInfoList.add(dataDefinitionsCache.getTableInfo(table.getTableName()));
            this.queryTableList.add(table);
        }
        return subQuerySql;
    }

    private StringBuilder buildSubQueryByTable(QueryContext context, QueryTable table, boolean addRowNumColumn) throws ParseException, SQLException {
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
        StringBuilder tableSql = this.tableSqls.get(table);
        String tableAlias = this.getTableAlias(context, table, false);
        if (addRowNumColumn) {
            tableSql.append(", Row_Number() OVER (partition by ");
            for (int i = 0; i < table.getTableDimensions().size(); ++i) {
                String dimension = table.getTableDimensions().get(i);
                ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimension);
                if (dimensionField == null || tableInfo.isBizOrderField(dimensionField.getName())) continue;
                tableSql.append(tableAlias).append(".").append(dimensionField.getName()).append(",");
            }
            tableSql.setLength(tableSql.length() - 1);
            tableSql.append(" order by ").append(tableAlias).append(".").append(tableInfo.getOrderField().getName());
            tableSql.append(") rank");
        }
        tableSql.append(" from ").append(this.getTableName(context, table)).append(" ").append(tableAlias);
        StringBuilder whereCondition = new StringBuilder();
        this.buildWhereConditionByTable(context, whereCondition, tableInfo, tableAlias, table);
        if (whereCondition.length() > 0) {
            tableSql.append(" where ").append((CharSequence)whereCondition);
        }
        return tableSql;
    }

    @Override
    public String buildQuerySql(QueryContext context, IQuerySqlUpdater sqlUpdater) throws Exception {
        if (sqlUpdater != null) {
            this.sqlSoftParse = false;
        }
        this.sql = this.buildQuerySql(context);
        if (sqlUpdater != null) {
            this.sql = sqlUpdater.updateQuerySql(this.primaryTable, this.getTableAlias(context, this.primaryTable), this.sql);
        }
        return this.sql;
    }

    @Override
    public void doInit(QueryContext context) throws ParseException {
        if (this.inited) {
            return;
        }
        if (this.primaryTable == null) {
            for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
                if (table.getTableName().equals(this.primaryTableName) && table.getPeriodModifier() == null) {
                    this.primaryTable = table;
                    break;
                }
                if (this.primaryTable == null) {
                    this.primaryTable = table;
                    continue;
                }
                if (table.getTableDimensions().size() <= this.primaryTable.getTableDimensions().size()) continue;
                this.primaryTable = table;
            }
        }
        if (this.primaryTable != null) {
            this.primaryTableName = this.primaryTable.getTableName();
        }
        int index = 0;
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            if (table.equals(this.primaryTable)) {
                context.getQueryTableAliaMap().put(this.primaryTable, this.createTableAlias(0));
                continue;
            }
            context.getQueryTableAliaMap().put(table, this.createTableAlias(++index));
            if (table.getTableDimensions().equals(this.primaryTable.getTableDimensions())) {
                if (this.primaryTable.getTableDimensions().contains("RECORDKEY") || table.getTableDimensions().contains("RECORDKEY")) {
                    IDatabase dataBase = context.getQueryParam().getDatabase();
                    if (dataBase.isDatabase("ORACLE") || dataBase.isDatabase("MSSQL")) {
                        this.orderJoinTables.add(table);
                        continue;
                    }
                    this.fullJoinTables.add(table);
                    continue;
                }
                this.fullJoinTables.add(table);
                continue;
            }
            if (this.primaryTable.getTableDimensions().containsAll(table.getTableDimensions())) {
                this.leftJoinTables.add(table);
                continue;
            }
            this.emptyJoinTables.add(table);
        }
        this.inited = true;
    }

    private void addGatherField(StringBuilder sql, String gather, String alias, String fieldName, int dataType) {
        if (gather.startsWith("none")) {
            if (dataType == 37 || dataType == 36 || dataType == 35 || dataType == 34) {
                sql.append(" null ");
            } else {
                sql.append("min(");
                sql.append(alias).append(".").append(fieldName);
                sql.append(")");
            }
        } else {
            sql.append(gather);
            sql.append(alias).append(".").append(fieldName);
            sql.append(")");
        }
    }

    private ColumnModelDefine getJoinField(QueryContext context, String keyField, String tableName) throws ParseException {
        DataModelDefinitionsCache dataDefinitionsCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        ColumnModelDefine fieldDefine = dataDefinitionsCache.findField(keyField);
        String referFieldId = fieldDefine.getReferColumnID();
        if (referFieldId == null) {
            return null;
        }
        ColumnModelDefine referField = dataDefinitionsCache.findField(referFieldId);
        if (referField == null) {
            return null;
        }
        TableModelDefine table = dataDefinitionsCache.getTableModel(referField);
        if (table.getName().equals(tableName)) {
            return referField;
        }
        return this.getJoinField(context, referFieldId, tableName);
    }

    @Override
    protected void buildOrderBy(QueryContext context) throws Exception {
        List<ColumnModelDefine> dimFields;
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
        if (this.orderByItems != null && !this.orderByItems.isEmpty()) {
            for (OrderByItem orderByItem : this.orderByItems) {
                if (orderByItem.field != null) {
                    if (this.fieldAliases.containsKey(orderByItem.field.getUID())) {
                        this.orderByClause.append((String)this.fieldAliases.get(orderByItem.field.getUID()));
                    }
                } else if (orderByItem.expr != null) {
                    SQLInfoDescr sqlInfo = new SQLInfoDescr(this.queryParam.getDatabase(), false);
                    for (IASTNode node : orderByItem.expr) {
                        if (!(node instanceof DynamicDataNode)) continue;
                        DynamicDataNode dataNode = (DynamicDataNode)node;
                        dataNode.setTableAlias(this.getTableAlias(context, dataNode.getQueryField().getTable()));
                    }
                    String sqlExpr = orderByItem.expr.interpret((IContext)context, Language.SQL, (Object)sqlInfo);
                    this.orderByClause.append(sqlExpr);
                }
                if (orderByItem.descending) {
                    this.orderByClause.append(" desc");
                }
                this.orderByClause.append(",");
            }
            this.orderByClause.setLength(this.orderByClause.length() - 1);
        }
        DataModelDefinitionsCache dataDefinitionsCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(this.primaryTableName);
        if (this.orderByClause.length() == 0 && this.useDefaultOrderBy && (dimFields = tableRunInfo.getDimFields()) != null && dimFields.size() > 0) {
            for (ColumnModelDefine dimField : dimFields) {
                if (tableRunInfo.isBizOrderField(dimField.getName())) continue;
                this.orderByClause.append((String)this.fieldAliases.get(dimField.getID())).append(",");
            }
            this.orderByClause.setLength(this.orderByClause.length() - 1);
        }
        if (tableRunInfo.getOrderField() != null) {
            if (this.orderByClause.length() > 0) {
                this.orderByClause.append(",");
            }
            this.orderByClause.append((String)this.fieldAliases.get(tableRunInfo.getOrderField().getID()));
        }
    }

    private void getOrderByField(Integer columnIndex) throws Exception {
        ASTNode node = (ASTNode)this.grpByColIndex2Node.get(columnIndex);
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

    protected void buildSelectSql(QueryContext context, StringBuilder sqlBuilder, StringBuilder subQuerySql) throws Exception {
        this.appendGroupingIdColumn(this.gatherFields);
        if (this.wantDetail) {
            sqlBuilder.append(" select ").append((CharSequence)this.gatherFields).append(" from (").append((CharSequence)subQuerySql).append(")");
            if (this.whereCondition.length() > 0) {
                sqlBuilder.append(" where ").append((CharSequence)this.whereCondition);
            }
            if (this.groupByClause != null && this.groupByClause.length() > 0) {
                this.appendGroupBySql(this.queryParam.getDatabase(), sqlBuilder, this.groupByClause, this.summarizingMethod);
            }
            sqlBuilder.append(" union all select ").append((CharSequence)this.selectFields).append(this.getResultTable().groupingFlagColIndex >= 0 ? " -1 as f__gf" : "").append(" from (").append((CharSequence)subQuerySql).append(")");
            if (this.whereCondition.length() > 0) {
                sqlBuilder.append(" where ").append((CharSequence)this.whereCondition);
            }
            if (this.orderByClause != null && this.orderByClause.length() > 0) {
                sqlBuilder.insert(0, "select * from (");
                sqlBuilder.append(") tt").append(" order by ").append((CharSequence)this.orderByClause);
            }
            if (this.argValues != null && this.argValues.size() > 0) {
                this.argValues.addAll(this.argValues);
            }
        } else {
            sqlBuilder.append(" select ").append((CharSequence)this.gatherFields).append(" from (").append((CharSequence)subQuerySql).append(")");
            if (this.whereCondition.length() > 0) {
                sqlBuilder.append(" where ").append((CharSequence)this.whereCondition);
            }
            if (this.groupByClause != null && this.groupByClause.length() > 0) {
                this.appendGroupBySql(this.queryParam.getDatabase(), sqlBuilder, this.groupByClause, this.summarizingMethod);
            }
            if (this.orderByClause != null && this.orderByClause.length() > 0) {
                sqlBuilder.append(" order by ").append((CharSequence)this.orderByClause);
            }
        }
    }

    private void joinTable(StringBuilder sql, QueryContext context, QueryTable table, boolean orderJoin, boolean noJoin) throws SQLException, ParseException {
        SqlJoinItem joinItem = null;
        if (this.sqlJoinProvider != null) {
            joinItem = this.sqlJoinProvider.getSqlJoinItem(this.primaryTable.getTableName(), table.getTableName());
        }
        String joinType = "full";
        if (joinItem != null) {
            joinType = joinItem.getJoinType().name();
        }
        String tableAlias = this.getTableAlias(context, table, true);
        String primaryTableAlias = this.getTableAlias(context, this.primaryTable, true);
        sql.append(" ").append(joinType).append(" join ");
        StringBuilder tableSql = this.buildSubQueryByTable(context, table, orderJoin);
        sql.append("(").append((CharSequence)tableSql).append(") ").append(tableAlias);
        sql.append(" on ");
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
        TableModelRunInfo primaryTableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        boolean needAnd = false;
        if (joinItem != null && joinItem.getJoinFields() != null && joinItem.getJoinFields().size() > 0) {
            for (SqlJoinOneItem oneItem : joinItem.getJoinFields()) {
                if (needAnd) {
                    sql.append(" and ");
                }
                sql.append(primaryTableAlias).append(".").append(oneItem.getSrcField());
                sql.append("=");
                sql.append(tableAlias).append(".").append(oneItem.getDesField());
                needAnd = true;
            }
        } else {
            for (int i = 0; i < table.getTableDimensions().size(); ++i) {
                ColumnModelDefine dimensionField;
                String dimension = table.getTableDimensions().get(i);
                if (table.getPeriodModifier() != null && dimension.equals("DATATIME") || (dimensionField = tableInfo.getDimensionField(dimension)) == null) continue;
                if (orderJoin && tableInfo.isBizOrderField(dimensionField.getName())) {
                    if (needAnd) {
                        sql.append(" and ");
                    }
                    sql.append(primaryTableAlias).append(".rank");
                    sql.append("=");
                    sql.append(tableAlias).append(".rank");
                    needAnd = true;
                    continue;
                }
                if (this.primaryTable.getTableDimensions().contains(dimension)) {
                    if (table.getDimensionRestriction() != null && table.getDimensionRestriction().hasValue(dimension)) continue;
                    if (needAnd) {
                        sql.append(" and ");
                    }
                    sql.append(primaryTableAlias).append(".").append(DataEngineUtil.getDimensionFieldName(primaryTableInfo, dimension));
                    sql.append("=");
                    sql.append(tableAlias).append(".").append(dimensionField.getName());
                    needAnd = true;
                    continue;
                }
                if (!noJoin) continue;
                if (needAnd) {
                    sql.append(" and ");
                }
                sql.append(tableAlias).append(".").append(dimensionField.getName()).append(" IS NULL ");
                needAnd = true;
            }
        }
    }

    private void buildWhereConditionByTable(QueryContext qContext, StringBuilder sql, TableModelRunInfo tableInfo, String tableAlias, QueryTable queryTable) throws SQLException {
        int dimDataType;
        ColumnModelDefine dimensionField;
        String dimensionFieldName;
        String keyName;
        int i;
        DimensionValueSet masterkeys = qContext.getMasterKeys();
        DimensionValueSet tableRestriction = queryTable.getDimensionRestriction();
        boolean whereNeedAnd = false;
        for (i = 0; i < masterkeys.size(); ++i) {
            keyName = masterkeys.getName(i);
            TempAssistantTable tempAssistantTable = qContext.getTempAssistantTables().get(keyName);
            if (tableRestriction != null && tableRestriction.hasValue(keyName)) continue;
            Object keyValue = masterkeys.getValue(i);
            dimensionFieldName = keyName;
            dimensionField = tableInfo.getDimensionField(keyName);
            if (dimensionField == null) continue;
            dimensionFieldName = dimensionField.getName();
            dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
            if (StringUtils.isEmpty((String)dimensionFieldName)) continue;
            if (keyName.equals("DATATIME")) {
                String periodStr = null;
                if (keyValue instanceof String) {
                    periodStr = (String)keyValue;
                    PeriodModifier periodModifier = queryTable.getPeriodModifier();
                    if (periodModifier != null) {
                        periodStr = qContext.getExeContext().getPeriodAdapter().modify(periodStr, periodModifier);
                    }
                }
                if (periodStr != null) {
                    keyValue = periodStr;
                }
                if (queryTable.getIsLj()) {
                    if (whereNeedAnd) {
                        sql.append(" and ");
                    }
                    sql.append(tableAlias).append(".").append(dimensionFieldName);
                    sql.append(" like '").append(periodStr, 0, 5).append("%'");
                    sql.append(" and ");
                    sql.append(tableAlias).append(".").append(dimensionFieldName);
                    sql.append("<='").append(periodStr).append("'");
                } else {
                    this.appendToCondition(qContext, tempAssistantTable, sql, tableAlias, dimensionFieldName, keyValue, dimDataType, whereNeedAnd);
                }
            } else {
                this.appendToCondition(qContext, tempAssistantTable, sql, tableAlias, dimensionFieldName, keyValue, dimDataType, whereNeedAnd);
            }
            whereNeedAnd = true;
        }
        if (this.recKeys != null && this.recKeys.size() > 0 && tableInfo.getRecField() != null) {
            whereNeedAnd = false;
            if (sql.length() > 0) {
                sql.insert(0, "(").append(")").append(" or ");
            }
            TempAssistantTable tempAssistantTable = qContext.getTempAssistantTables().get(tableInfo.getRecField().getName());
            this.appendToCondition(qContext, tempAssistantTable, sql, this.getTableAlias(qContext, this.primaryTable, false), tableInfo.getRecField().getName(), this.recKeys, DataTypesConvert.fieldTypeToDataType(tableInfo.getRecField().getColumnType()), whereNeedAnd);
            sql.insert(0, "(").append(")");
            whereNeedAnd = true;
        }
        if (tableRestriction != null) {
            for (i = 0; i < tableRestriction.size(); ++i) {
                keyName = tableRestriction.getName(i);
                Object keyValue = tableRestriction.getValue(i);
                if (keyValue == null) continue;
                TempAssistantTable tempAssistantTable = qContext.getTempAssistantTables().get(keyName);
                dimensionFieldName = keyName;
                dimensionField = tableInfo.getDimensionField(keyName);
                if (dimensionField != null) {
                    dimensionFieldName = dimensionField.getName();
                }
                dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                if (StringUtils.isEmpty((String)dimensionFieldName)) continue;
                this.appendToCondition(qContext, tempAssistantTable, sql, tableAlias, dimensionFieldName, keyValue, dimDataType, whereNeedAnd);
                whereNeedAnd = true;
            }
        }
        whereNeedAnd = this.appendVersionFilter(qContext, tableAlias, queryTable, tableInfo, sql, whereNeedAnd);
    }

    private void appendRowFilterCondition(StringBuilder sql, QueryContext qContext) throws InterpretException {
        if (this.rowFilterNode != null) {
            if (this.rowFilterNode.support(Language.SQL)) {
                for (IASTNode child : this.rowFilterNode) {
                    if (!(child instanceof DynamicDataNode)) continue;
                    DynamicDataNode dataNode = (DynamicDataNode)child;
                    dataNode.setTableAlias(this.getTableAlias(qContext, dataNode.getQueryField().getTable(), true));
                }
                SQLInfoDescr conditionSqlINfo = new SQLInfoDescr(this.queryParam.getDatabase(), true, 0, 0);
                sql.append("(").append(this.rowFilterNode.interpret((IContext)qContext, Language.SQL, (Object)conditionSqlINfo)).append(")");
            } else {
                this.needMemoryFilter = true;
            }
        }
        if (this.colValueFilters != null) {
            boolean temp = this.sqlSoftParse;
            this.sqlSoftParse = false;
            boolean needAnd = this.whereCondition.length() > 0;
            for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
                try {
                    String tableAlias = this.getTableAlias(qContext, table, true);
                    TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
                    needAnd = this.appendColFilterByTable(qContext, tableAlias, table, tableInfo, this.whereCondition, needAnd);
                }
                catch (ParseException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            this.sqlSoftParse = temp;
        }
    }

    private boolean appendVersionFilter(QueryContext qContext, String tableAlias, QueryTable queryTable, TableModelRunInfo tableRunInfo, StringBuilder condition, boolean needAnd) throws SQLException {
        if (this.queryVersionDate.equals(Consts.ALL_VERSIONS_DATE) && this.queryVersionStartDate.equals(Consts.DATE_VERSION_INVALID_VALUE)) {
            return needAnd;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (this.queryVersionDate.equals(Consts.DATE_VERSION_FOR_ALL)) {
            if (!this.queryVersionStartDate.equals(Consts.DATE_VERSION_INVALID_VALUE)) {
                if (needAnd) {
                    condition.append(" and ");
                }
                condition.append(this.getDateCompareSql(tableAlias, "INVALIDTIME", ">=", this.queryVersionStartDate));
                needAnd = true;
            }
            if (!this.allVersionQuery) {
                if (needAnd) {
                    condition.append(" and ");
                }
                needAnd = true;
                this.buildLastestVersionSql(qContext, sdf, tableAlias, queryTable, tableRunInfo, condition);
            }
        } else if (this.queryVersionStartDate.equals(Consts.DATE_VERSION_INVALID_VALUE)) {
            if (needAnd) {
                condition.append(" and ");
            }
            needAnd = true;
            condition.append(this.getDateCompareSql(tableAlias, "VALIDTIME", "<=", this.queryVersionDate));
            condition.append(" and ");
            condition.append(this.getDateCompareSql(tableAlias, "INVALIDTIME", ">", this.queryVersionDate));
        } else {
            if (needAnd) {
                condition.append(" and ");
            }
            needAnd = true;
            condition.append(this.getDateCompareSql(tableAlias, "VALIDTIME", "<=", this.queryVersionDate));
            condition.append(" and ");
            condition.append(this.getDateCompareSql(tableAlias, "INVALIDTIME", ">", this.queryVersionStartDate));
            if (!this.allVersionQuery) {
                if (needAnd) {
                    condition.append(" and ");
                }
                needAnd = true;
                this.buildLastestVersionSql(qContext, sdf, tableAlias, queryTable, tableRunInfo, condition);
            }
        }
        return needAnd;
    }

    private void buildLastestVersionSql(QueryContext qContext, SimpleDateFormat sdf, String tableAlias, QueryTable queryTable, TableModelRunInfo tableRunInfo, StringBuilder condition) throws SQLException {
        String aliasValue = "tss";
        condition.append(" not exists (select 1 from ").append(this.getTableName(qContext, queryTable));
        if (this.useDNASql) {
            condition.append(" as ").append(aliasValue).append(" where ");
        } else {
            condition.append(" where ");
        }
        boolean addAnd = false;
        List<ColumnModelDefine> dimFields = tableRunInfo.getDimFields();
        for (ColumnModelDefine dimField : dimFields) {
            if (addAnd) {
                condition.append(" and ");
            }
            addAnd = true;
            if (this.useDNASql) {
                condition.append(aliasValue).append(".").append(dimField.getName());
            } else {
                condition.append(dimField.getName());
            }
            condition.append('=');
            condition.append(tableAlias).append(".").append(dimField.getName());
        }
        if (addAnd) {
            condition.append(" and ");
        }
        if (!this.queryVersionDate.equals(Consts.DATE_VERSION_FOR_ALL)) {
            condition.append(this.getDateCompareSql(null, "VALIDTIME", "<=", this.queryVersionDate));
            condition.append(" and ");
        }
        if (!this.queryVersionStartDate.equals(Consts.DATE_VERSION_INVALID_VALUE)) {
            condition.append(this.getDateCompareSql(null, "INVALIDTIME", ">", this.queryVersionStartDate));
            condition.append(" and ");
        }
        if (this.useDNASql) {
            condition.append(aliasValue).append(".").append("VALIDTIME");
        } else {
            condition.append("VALIDTIME");
        }
        condition.append('>');
        condition.append(tableAlias).append(".").append("VALIDTIME");
        condition.append(") ");
    }

    @Override
    public DimensionValueSet buildRowKeys(DimensionValueSet masterKeys, IQueryFieldDataReader reader) throws Exception {
        DimensionValueSet rowKeys = new DimensionValueSet();
        for (int index = 0; index < this.tableInfoList.size(); ++index) {
            TableModelRunInfo tableInfo = this.tableInfoList.get(index);
            QueryTable table = this.queryTableList.get(index);
            int dimFieldStartIndex = this.dimesionFieldIndexes.get(table);
            List<ColumnModelDefine> dimfields = tableInfo.getDimFields();
            for (int i = 0; i < dimfields.size(); ++i) {
                Object value;
                ColumnModelDefine dimField = dimfields.get(i);
                String dimensionName = tableInfo.getDimensionName(dimField.getCode());
                Object masterValue = null;
                if (masterKeys.hasValue(dimensionName) && !((value = masterKeys.getValue(dimensionName)) instanceof List)) {
                    masterValue = value;
                }
                if (masterValue == null) {
                    masterValue = reader.readData(i + dimFieldStartIndex);
                }
                if ((masterValue = DataEngineConsts.formatData(dimField, masterValue, null)) == null) continue;
                rowKeys.setValue(dimensionName, masterValue);
            }
            if (rowKeys.size() < this.primaryTable.getTableDimensions().size()) continue;
            return rowKeys;
        }
        return rowKeys;
    }

    @Override
    protected void getGroupByField(QueryContext qContext, Integer columnIndex) throws Exception {
        ASTNode node = (ASTNode)this.grpByColIndex2Node.get(columnIndex);
        if (node == null) {
            throw new IncorrectQueryException("\u4e0d\u652f\u6301\u7684\u5206\u7ec4\u8868\u8fbe\u5f0f");
        }
        QueryField queryField = ExpressionUtils.extractQueryField((IASTNode)node);
        if (queryField == null) {
            throw new IncorrectQueryException("\u4e0d\u652f\u6301\u7684\u5206\u7ec4\u8868\u8fbe\u5f0f");
        }
        String groupField = this.getTableAlias(qContext, queryField.getTable(), true) + "." + queryField.getFieldName();
        this.groupByClause.append(groupField);
        this.groupFields.add(groupField);
    }
}

