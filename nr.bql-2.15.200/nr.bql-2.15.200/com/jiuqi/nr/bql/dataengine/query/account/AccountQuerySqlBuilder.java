/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$QueryTableType
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.OrderByItem
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.query.QueryTableColFilterValues
 *  com.jiuqi.np.dataengine.query.account.IAccountColumnModelFinder
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.dataengine.util.FieldSqlConditionUtil
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.query.account;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QueryTableColFilterValues;
import com.jiuqi.np.dataengine.query.account.IAccountColumnModelFinder;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.util.FieldSqlConditionUtil;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;
import com.jiuqi.nr.bql.util.TempAssistantTableUtils;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class AccountQuerySqlBuilder
extends QuerySqlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AccountQuerySqlBuilder.class);
    public static final String TN_ACCOUNT_RPT_SUFFIX = "_RPT";
    public static final String TN_ACCOUNT_HIS_SUFFIX = "_HIS";
    public static final String UNIONTABLEALIAS = "tt";
    public static final String TN_ACCOUNT_COL_CODE_VALIDDATATIME = "VALIDDATATIME";
    public static final String TN_ACCOUNT_COL_CODE_INVALIDDATATIME = "INVALIDDATATIME";
    public static final String DF_ACCOUNTID_CODE = "SBID";
    private static final String MD_ORG_START = "MD_ORG";
    private StringBuilder unionSelectFields;
    private StringBuilder accountCondition;
    private StringBuilder accountHiCondition;
    private StringBuilder joinTempTable;
    private StringBuilder hisJoinTempTable;
    private QueryTable accountTable;
    private QueryTable accountHiTable;
    private QueryTable accountRptTable;
    private String accountTableName;
    private String accountHiTableName;
    protected Boolean isTrackHistory;
    private Boolean containTimeField;
    protected StringBuilder joinTableAndCondition;
    private final Map<String, Integer> queryFieldsIndex = new HashMap<String, Integer>();
    private QueryField bizqueryField;
    private int accountFieldIndex = -1;
    private String filterFieldName;
    private List<String> filterKeyColumns;
    private StringBuilder accTableColFilter;
    private StringBuilder accRptTableColFilter;

    @Override
    protected boolean isGroupingQuery() {
        return false;
    }

    @Override
    public String getTableAlias(QueryContext qContext, QueryTable table) {
        String tableAlias = (String)qContext.getQueryTableAliaMap().get(table);
        if (tableAlias == null) {
            tableAlias = table.getAlias();
        }
        return tableAlias;
    }

    @Override
    public QueryTable getPrimaryTable() {
        return this.accountTable;
    }

    @Override
    public String buildSql(QueryContext context) throws Exception {
        this.doInit(context);
        return this.buildQuerySql(context);
    }

    @Override
    public void doInit(QueryContext context) throws ParseException {
        if (this.inited) {
            return;
        }
        int index = 0;
        this.accTableColFilter = new StringBuilder();
        this.accRptTableColFilter = new StringBuilder();
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            ++index;
            String tableName = table.getTableName().toUpperCase();
            if (table.isAccountTable()) {
                this.accountTable = table;
                this.primaryTable = table;
                this.accountTableName = tableName;
                String tableAlias = this.createTableAlias(index);
                context.getQueryTableAliaMap().put(table, tableAlias);
                if (this.isTrackHistory.booleanValue()) {
                    this.accountHiTableName = tableName + TN_ACCOUNT_HIS_SUFFIX;
                    String alias = this.createTableAlias(++index);
                    this.accountHiTable = new QueryTable(alias, this.accountHiTableName);
                    this.accountHiTable.setTableType(DataEngineConsts.QueryTableType.ACCOUNT_HIS);
                    context.getQueryTableAliaMap().put(this.accountHiTable, alias);
                }
                String columnKey = this.queryRegion.getTableFields(table).getItem(0).getUID();
                this.buildDimensionFields(context, columnKey, tableName);
                this.parseFilterToSQL(context, this.rowFilterNode, tableName, tableAlias);
                continue;
            }
            if (table.isAccountRptTable() && this.containTimeField.booleanValue()) {
                QueryFields queryFields = this.queryRegion.getTableFields(table);
                for (QueryField queryField : queryFields) {
                    if (!queryField.getFieldName().equals("BIZKEYORDER")) continue;
                    this.bizqueryField = queryField;
                    break;
                }
                String tableAlias = this.createTableAlias(index);
                this.accountRptTable = table;
                context.getQueryTableAliaMap().put(table, tableAlias);
                this.parseFilterToSQL(context, this.rowFilterNode, tableName, tableAlias);
                ++index;
                continue;
            }
            this.leftJoinTables.add(table);
        }
        this.checkAccTableInit(context, index);
        this.splitFilterSql(context);
        this.inited = true;
    }

    private void splitFilterSql(QueryContext context) {
        if (this.rowFilterNode != null) {
            SQLInfoDescr conditionSqlInfo = new SQLInfoDescr(context.getQueryParam().getDatabase(), true, 0, 0);
            StringBuilder filterSql = new StringBuilder();
            try {
                this.rowFilterNode.interpret((IContext)context, filterSql, Language.SQL, (Object)conditionSqlInfo);
                if (filterSql.length() > 0) {
                    String[] splitSql;
                    for (String s : splitSql = filterSql.toString().split("AND")) {
                        this.appendFilterSql(context, s);
                    }
                }
            }
            catch (InterpretException e) {
                logger.error("\u89e3\u6790\u7b5b\u9009\u6761\u4ef6\u51fa\u9519\uff01\u68c0\u67e5\u6761\u4ef6\u662f\u5426\u80fd\u8f6c\u6362\u4e3aSQL\uff01", e);
            }
        }
    }

    private void checkAccTableInit(QueryContext context, int index) throws ParseException {
        if (Objects.isNull(this.accountTable) && this.accountRptTable != null) {
            this.accountTableName = this.accountRptTable.getTableName().replace(TN_ACCOUNT_RPT_SUFFIX, "");
            String alias = this.createTableAlias(index);
            this.primaryTable = this.accountTable = new QueryTable(alias, this.accountTableName);
            this.accountTable.setTableType(DataEngineConsts.QueryTableType.ACCOUNT);
            context.getQueryTableAliaMap().put(this.accountTable, alias);
            this.accountHiTableName = this.accountTableName + TN_ACCOUNT_HIS_SUFFIX;
            alias = this.createTableAlias(++index);
            this.accountHiTable = new QueryTable(alias, this.accountHiTableName);
            this.accountHiTable.setTableType(DataEngineConsts.QueryTableType.ACCOUNT_HIS);
            context.getQueryTableAliaMap().put(this.accountHiTable, alias);
            if (CollectionUtils.isEmpty(this.dimensionFields)) {
                String columnKey = this.queryRegion.getTableFields(this.accountRptTable).getItem(0).getUID();
                this.buildDimensionFields(context, columnKey, this.accountRptTable.getTableName());
            }
        }
    }

    @Override
    public String buildQuerySql(QueryContext context) throws Exception {
        this.selectFields = new StringBuilder();
        this.unionSelectFields = new StringBuilder();
        this.accountCondition = new StringBuilder();
        this.accountHiCondition = new StringBuilder();
        this.fromJoinsTables = new StringBuilder();
        this.whereCondition = new StringBuilder();
        this.groupByClause = new StringBuilder();
        this.orderByClause = new StringBuilder();
        this.joinTempTable = new StringBuilder();
        this.hisJoinTempTable = new StringBuilder();
        this.joinTableAndCondition = new StringBuilder();
        this.fieldIndex = 1;
        this.dimensionConditions.clear();
        this.fieldAliases.clear();
        this.whereNeedAnd = false;
        if (this.sqlSoftParse) {
            if (this.argValues == null) {
                this.argValues = new ArrayList();
                this.argDataTypes = new ArrayList();
            } else {
                this.argValues.clear();
                this.argDataTypes.clear();
            }
        }
        StringBuilder sqlBuilder = new StringBuilder();
        this.buildSourceSql(context);
        this.buildRptSelectFields(context);
        this.leftJoinRptTable(context);
        this.appendRowFilter(context);
        this.buildGroupBy(context);
        this.buildOrderBy(context);
        this.buildSelectSql(context, sqlBuilder);
        this.sql = sqlBuilder.toString();
        return this.sql;
    }

    private void buildRptSelectFields(QueryContext context) throws ParseException {
        if (this.selectFields.length() < 0 || this.accountRptTable == null) {
            return;
        }
        QueryFields leftFields = this.queryRegion.getTableFields(this.accountRptTable);
        String alias = this.accountRptTable.getAlias();
        for (QueryField leftField : leftFields) {
            String fieldName = leftField.getFieldName();
            if ("BIZKEYORDER".equals(fieldName)) continue;
            this.selectFields.append(",");
            AggrType aggrType = null;
            String gather = this.getFieldGatherSql(leftField.getUID(), aggrType, leftField.getDataType());
            if (gather != null) {
                this.addGatherField(gather, aggrType, alias, fieldName, leftField.getDataType());
            } else {
                this.selectFields.append(alias).append(".").append(leftField.getFieldName());
            }
            String fieldAlias = "c_".concat(String.valueOf(this.fieldIndex));
            this.selectFields.append(" as ").append(fieldAlias);
            this.fieldAliases.put(leftField.getUID(), fieldAlias);
            this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), leftField, this.fieldIndex);
            if (!this.filterKeyColumns.contains(fieldName)) {
                this.queryFieldsIndex.put(leftField.getFieldName(), this.fieldIndex);
            }
            ++this.fieldIndex;
            if (this.filterFieldName == null || this.filterFieldName.contains(".") || !this.filterFieldName.equals(fieldName)) continue;
            this.filterFieldName = alias.concat(".").concat(this.filterFieldName);
        }
    }

    private void appendLeftJoinSelectFields(QueryContext context) throws ParseException {
        for (QueryTable table : this.leftJoinTables) {
            OrderTempAssistantTable dimOrderTempTable = null;
            if (table.isDimensionTable()) {
                String dimensionName = table.getTableDimensions().get(0);
                dimOrderTempTable = TempAssistantTableUtils.getContextTempAssistantTables(context).get(dimensionName);
            }
            QueryFields leftFields = this.queryRegion.getTableFields(table);
            String alias = this.getTableAlias(context, table);
            for (QueryField leftField : leftFields) {
                this.selectFields.append(",");
                AggrType aggrType = null;
                String gather = this.getFieldGatherSql(leftField.getUID(), aggrType, leftField.getDataType());
                if (dimOrderTempTable != null && leftField.getFieldName().equalsIgnoreCase("ORDINAL")) {
                    if (gather != null) {
                        this.addGatherField(gather, aggrType, UNIONTABLEALIAS, "TEMP_ORDER", leftField.getDataType());
                    } else {
                        this.selectFields.append(UNIONTABLEALIAS).append(".").append("TEMP_ORDER");
                    }
                    this.unionSelectFields.append(",").append(dimOrderTempTable.getOrderColumnSql());
                } else if (gather != null) {
                    this.addGatherField(gather, aggrType, alias, leftField.getFieldName(), leftField.getDataType());
                } else {
                    this.selectFields.append(alias).append(".").append(leftField.getFieldName());
                }
                String fieldAlias = "c_".concat(String.valueOf(this.fieldIndex));
                this.selectFields.append(" as ").append(fieldAlias);
                this.fieldAliases.put(leftField.getUID(), fieldAlias);
                this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), leftField, this.fieldIndex);
                ++this.fieldIndex;
            }
        }
    }

    @Override
    protected void buildSelectSql(QueryContext context, StringBuilder sqlBuilder) throws Exception {
        sqlBuilder.append("select ").append((CharSequence)this.selectFields).append(" from ");
        if (this.isTrackHistory.booleanValue()) {
            sqlBuilder.append("(").append((CharSequence)this.fromJoinsTables).append(")");
        } else {
            sqlBuilder.append((CharSequence)this.fromJoinsTables);
        }
        this.appendSpace(sqlBuilder);
        sqlBuilder.append(UNIONTABLEALIAS);
        this.appendSpace(sqlBuilder);
        if (this.joinTableAndCondition.length() > 0) {
            sqlBuilder.append("left join");
            this.appendSpace(sqlBuilder);
            sqlBuilder.append((CharSequence)this.joinTableAndCondition);
        }
        this.leftJoinDimTables(context, sqlBuilder);
        if (this.orderByClause.length() > 0) {
            if (this.isDoPage() && this.queryParam.getDatabase().isDatabase("GaussDB")) {
                String[] orderyFields;
                sqlBuilder.append(" ${OrderBy(");
                for (String orderyField : orderyFields = this.orderByClause.toString().split(",")) {
                    sqlBuilder.append("\"").append(orderyField).append("\"").append(",");
                }
                sqlBuilder.setLength(sqlBuilder.length() - 1);
                sqlBuilder.append(")}");
            } else {
                this.appendSpace(sqlBuilder);
                sqlBuilder.append("order by");
                this.appendSpace(sqlBuilder);
                sqlBuilder.append((CharSequence)this.orderByClause);
            }
        }
    }

    @Override
    public void buildRowKeys(DimensionValueSet rowKeys, DimensionValueSet masterKeys, IQueryFieldDataReader reader) throws Exception {
        HashMap<String, ColumnModelDefine> dimensionFields = this.queryDimensionFields();
        for (Map.Entry<String, ColumnModelDefine> entry : dimensionFields.entrySet()) {
            Integer index;
            String dimesion = entry.getKey();
            ColumnModelDefine dimensionField = entry.getValue();
            Object masterValue = null;
            if (masterKeys.hasValue(dimesion)) {
                Object value = masterKeys.getValue(dimesion);
                if (!(value instanceof List)) {
                    masterValue = value;
                }
            } else {
                if (!this.queryFieldsIndex.containsKey(dimensionField.getName()) || DF_ACCOUNTID_CODE.equals(dimesion)) continue;
                index = this.queryFieldsIndex.get(dimensionField.getName());
                masterValue = reader.readData(index.intValue());
            }
            if (masterValue == null && this.queryFieldsIndex.containsKey(dimensionField.getName())) {
                index = this.queryFieldsIndex.get(dimensionField.getName());
                masterValue = reader.readData(index.intValue());
            }
            if (!Objects.nonNull(masterValue)) continue;
            rowKeys.setValue(dimesion, masterValue);
        }
        if (!dimensionFields.containsKey("RECORDKEY") && this.queryFieldsIndex.containsKey("BIZKEYORDER")) {
            Integer index = this.queryFieldsIndex.get("BIZKEYORDER");
            Object masterValue = reader.readData(index.intValue());
            rowKeys.setValue("RECORDKEY", masterValue);
        }
    }

    private HashMap<String, ColumnModelDefine> queryDimensionFields() {
        return this.dimensionFields;
    }

    private void buildDimensionFields(QueryContext context, String columnKey, String tableName) throws ParseException {
        IAccountColumnModelFinder accountFinder = context.getAccountColumnModelFinder();
        List columns = accountFinder.getAllBizkColumnByColumnId(context.getExeContext(), columnKey);
        DataModelDefinitionsCache tableCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        TableModelRunInfo tableInfo = tableCache.getTableInfo(tableName);
        for (ColumnModelDefine column : columns) {
            String dimension = tableInfo.getDimensionName(column.getCode());
            String fieldName = column.getName();
            if (Objects.isNull(dimension)) {
                dimension = "BIZKEYORDER".equals(fieldName) ? "RECORDKEY" : column.getCode();
            }
            this.dimensionFields.put(dimension, column);
        }
    }

    @Override
    protected void buildOrderBy(QueryContext context) throws Exception {
        if (this.orderByItems != null && !this.orderByItems.isEmpty()) {
            for (OrderByItem orderByItem : this.orderByItems) {
                QueryField orderByField = orderByItem.field;
                if (orderByField != null) {
                    if (orderByField.getFieldCode().equals(this.bizqueryField.getFieldCode())) {
                        orderByField = this.bizqueryField;
                    }
                    if (this.fieldAliases.containsKey(orderByField.getUID())) {
                        this.orderByClause.append((String)this.fieldAliases.get(orderByField.getUID()));
                    } else {
                        QueryTable orderTable = orderByField.getTable();
                        Object tableAlias = (String)context.getQueryTableAliaMap().get(orderTable);
                        if (tableAlias == null && this.primaryTable != null && this.primaryTable.getTableName().equals(orderTable.getTableName())) {
                            tableAlias = (String)context.getQueryTableAliaMap().get(this.primaryTable);
                        }
                        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)tableAlias)) continue;
                        this.selectFields.append((String)tableAlias).append(".").append(orderByField.getFieldName());
                        String fieldAlias = String.format("%s%s", "c_", this.fieldIndex);
                        this.selectFields.append(" as ").append(fieldAlias).append(",");
                        this.fieldAliases.put(orderByField.getUID(), fieldAlias);
                        this.orderByClause.append(fieldAlias);
                        ++this.fieldIndex;
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
    }

    private void buildSourceSql(QueryContext context) throws ParseException, InterpretException {
        if (Objects.isNull(this.accountTable)) {
            logger.info("\u6ca1\u6709\u627e\u5230\u53f0\u8d26\u8868");
            return;
        }
        this.appendSourceFields(context);
        this.appendLeftJoinSelectFields(context);
        this.appendAccountCondition(context);
        this.appendAccountHiCondition(context);
        this.build(context);
    }

    private void appendAccountHiCondition(QueryContext context) throws InterpretException, ParseException {
        if (!this.isTrackHistory.booleanValue()) {
            return;
        }
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.accountTableName);
        DimensionValueSet masterKeys = context.getMasterKeys();
        DimensionSet dimensionSet = masterKeys.getDimensionSet();
        boolean addFlag = false;
        String tableAlias = this.getTableAlias(context, this.accountHiTable);
        for (int index = 0; index < dimensionSet.size(); ++index) {
            Object value;
            String dimension = dimensionSet.get(index);
            if (dimension.equals("VERSIONID") || !Objects.nonNull(value = masterKeys.getValue(dimension))) continue;
            OrderTempAssistantTable orderTempAssistantTable = TempAssistantTableUtils.getContextTempAssistantTables(context).get(dimension);
            if (orderTempAssistantTable != null) {
                String dimFieldName = this.parseDimField(dimension);
                this.hisJoinTempTable.append(orderTempAssistantTable.getJoinSql(tableAlias + "." + dimFieldName));
                continue;
            }
            if (addFlag) {
                this.appendSpace(this.accountHiCondition);
                this.accountHiCondition.append("and");
                this.appendSpace(this.accountHiCondition);
            }
            int dataType = 6;
            if (dimension.equals("DATATIME")) {
                this.accountHiCondition.append(tableAlias).append(".").append(TN_ACCOUNT_COL_CODE_VALIDDATATIME).append("<=");
                PeriodWrapper pw = context.getPeriodWrapper();
                if (pw != null) {
                    value = pw.toString();
                }
                this.appendValue(this.accountHiCondition, value, dataType);
                this.accountHiCondition.append(" and ").append(TN_ACCOUNT_COL_CODE_INVALIDDATATIME).append(">");
                this.appendArgValue(this.accountHiCondition, value, dataType);
            } else {
                String dimFieldName = this.parseDimField(dimension);
                this.appendToCondition(context, null, this.accountHiCondition, tableAlias, dimFieldName, value, dataType, false);
            }
            addFlag = true;
        }
        this.appendRowFilterSql(this.accountHiCondition, tableAlias, this.accountHiTable);
        this.appendColFilterByTable(context, tableAlias, this.accountTable, tableInfo, this.accountHiCondition, true);
    }

    private void appendAccountCondition(QueryContext context) throws ParseException, InterpretException {
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.accountTableName);
        DimensionValueSet masterKeys = context.getMasterKeys();
        DimensionSet dimensionSet = masterKeys.getDimensionSet();
        this.loopDimensions = new DimensionSet(dimensionSet);
        boolean addFlag = false;
        String tableAlias = this.getTableAlias(context, this.accountTable);
        for (int index = 0; index < dimensionSet.size(); ++index) {
            Object value;
            String dimension = dimensionSet.get(index);
            if (dimension.equals("VERSIONID")) continue;
            ColumnModelDefine dimensionField = tableInfo.parseSearchField(this.parseDimField(dimension));
            if (dimensionField != null) {
                this.dimensionFields.put(dimension, dimensionField);
            }
            if (!Objects.nonNull(value = masterKeys.getValue(dimension))) continue;
            OrderTempAssistantTable orderTempAssistantTable = TempAssistantTableUtils.getContextTempAssistantTables(context).get(dimension);
            if (orderTempAssistantTable != null) {
                this.joinTempTable.append(orderTempAssistantTable.getJoinSql(tableAlias + "." + dimensionField.getName()));
                continue;
            }
            if (addFlag) {
                this.appendSpace(this.accountCondition);
                this.accountCondition.append(" and ");
                this.appendSpace(this.accountCondition);
            }
            int dataType = 6;
            if (dimension.equals("DATATIME")) {
                PeriodWrapper pw = context.getPeriodWrapper();
                if (pw != null) {
                    value = pw.toString();
                }
                this.accountCondition.append(tableAlias).append(".").append(TN_ACCOUNT_COL_CODE_VALIDDATATIME).append("<=");
                this.appendArgValue(this.accountCondition, value, dataType);
            } else {
                if (dimensionField == null || this.dimensionConditions.contains(dimension)) continue;
                this.appendToCondition(context, null, this.accountCondition, tableAlias, dimensionField.getName(), value, dataType, false);
            }
            addFlag = true;
        }
        this.appendRowFilterSql(this.accountCondition, tableAlias, this.accountTable);
        this.appendColFilterByTable(context, tableAlias, this.accountTable, tableInfo, this.accountCondition, true);
    }

    private void build(QueryContext context) {
        this.fromJoinsTables.append("(");
        this.fromJoinsTables.append(" select ").append((CharSequence)this.unionSelectFields);
        this.fromJoinsTables.append(" from ");
        this.fromJoinsTables.append(this.accountTableName).append(" ");
        this.fromJoinsTables.append(this.getTableAlias(context, this.accountTable));
        this.fromJoinsTables.append(" ").append((CharSequence)this.joinTempTable);
        this.fromJoinsTables.append(" where ").append((CharSequence)this.accountCondition);
        this.fromJoinsTables.append(")");
        if (!this.isTrackHistory.booleanValue()) {
            return;
        }
        this.fromJoinsTables.append(" union all ");
        this.fromJoinsTables.append("(");
        String accountHiFields = this.unionSelectFields.toString().replaceAll(this.getTableAlias(context, this.accountTable), this.getTableAlias(context, this.accountHiTable));
        this.fromJoinsTables.append(" select ").append(accountHiFields);
        this.fromJoinsTables.append(" from ");
        this.fromJoinsTables.append(this.accountHiTableName).append(" ");
        this.fromJoinsTables.append(this.getTableAlias(context, this.accountHiTable));
        this.fromJoinsTables.append(" ").append((CharSequence)this.hisJoinTempTable);
        this.fromJoinsTables.append(" where ").append((CharSequence)this.accountHiCondition);
        this.fromJoinsTables.append(")");
    }

    protected void leftJoinDimTables(QueryContext context, StringBuilder sqlBuilder) throws SQLException, ParseException, InterpretException {
        DataModelDefinitionsCache dataModelDefinitionsCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        TableModelRunInfo primaryTableInfo = dataModelDefinitionsCache.getTableInfo(this.primaryTable.getTableName());
        for (QueryTable table : this.leftJoinTables) {
            String tableAlias = this.getTableAlias(context, table);
            if (table.getTableType() == DataEngineConsts.QueryTableType.PERIOD) {
                sqlBuilder.append(" left join ").append(table.getTableName()).append(" ").append(tableAlias);
                sqlBuilder.append(" on ");
                Object period = context.getMasterKeys().getValue("DATATIME");
                this.appendToCondition(context, null, sqlBuilder, tableAlias, PeriodTableColumn.CODE.getCode(), period, 6, false);
                continue;
            }
            sqlBuilder.append(" left join ");
            this.appendQueryTable(context, sqlBuilder, table, null);
            sqlBuilder.append(" on ");
            boolean needAnd = false;
            TableModelRunInfo tableInfo = dataModelDefinitionsCache.getTableInfo(table.getTableName());
            for (int i = 0; i < table.getTableDimensions().size(); ++i) {
                String dimension;
                String keyfieldName = dimension = table.getTableDimensions().get(i);
                ColumnModelDefine primaryDimensionField = primaryTableInfo.getDimensionField(dimension);
                if (primaryDimensionField == null) continue;
                if (needAnd) {
                    sqlBuilder.append(" and ");
                }
                sqlBuilder.append(UNIONTABLEALIAS).append(".");
                keyfieldName = primaryDimensionField.getName();
                sqlBuilder.append(keyfieldName);
                sqlBuilder.append("=");
                sqlBuilder.append(tableAlias).append(".");
                String dimensionFieldName = DataEngineUtil.getDimensionFieldName((TableModelRunInfo)tableInfo, (String)dimension);
                sqlBuilder.append(dimensionFieldName);
                needAnd = true;
            }
            needAnd = AccountQuerySqlBuilder.appendVersionFilter(this.queryParam, tableAlias, tableInfo, sqlBuilder, this.queryVersionDate, this.queryVersionStartDate, needAnd);
        }
    }

    @Override
    protected boolean appendColFilterByTable(QueryContext queryContext, String tableAlias, QueryTable table, TableModelRunInfo tableRunInfo, StringBuilder condition, boolean needAnd) {
        if (this.colValueFilters != null) {
            QueryTableColFilterValues filterValues = this.colValueFilters.getSqlColFilterValues(table);
            if (filterValues == null) {
                return needAnd;
            }
            for (QueryField queryField : this.queryRegion.getTableFields(table)) {
                List valueList = filterValues.getColFilterValues(queryField);
                if (valueList == null) continue;
                if (valueList.size() <= 0) {
                    if (needAnd) {
                        condition.append(" and ");
                    }
                    condition.append(" 1=0 ");
                    needAnd = true;
                    continue;
                }
                boolean hasNullValue = false;
                ArrayList notNullValueList = new ArrayList(valueList.size());
                for (Object value : valueList) {
                    if (value == null) {
                        hasNullValue = true;
                        continue;
                    }
                    notNullValueList.add(value);
                }
                if (needAnd) {
                    condition.append(" and ");
                }
                needAnd = true;
                String fieldName = tableAlias + "." + queryField.getFieldName();
                if (hasNullValue) {
                    condition.append("(").append(fieldName).append(" is null ");
                    if (!notNullValueList.isEmpty()) {
                        condition.append(" or ");
                        condition.append(fieldName).append(" in ");
                        this.appendArgValue(condition, notNullValueList, queryField.getDataType());
                    }
                    condition.append(")");
                    continue;
                }
                if (notNullValueList.isEmpty()) continue;
                condition.append(fieldName).append(" in ");
                this.appendArgValue(condition, notNullValueList, queryField.getDataType());
            }
        }
        return needAnd;
    }

    private void appendRowFilterSql(StringBuilder sqlBuilder, String tableAlias, QueryTable table) {
        if (this.rowFilterNode != null) {
            if (table.isAccountTable() && this.accTableColFilter.length() > 0) {
                sqlBuilder.append(" and (").append((CharSequence)this.accTableColFilter).append(") ");
            }
            if (table.isAccountHisTable() && this.accTableColFilter.length() > 0) {
                sqlBuilder.append(" and (").append(this.accTableColFilter.toString().replaceAll(this.accountTable.getAlias(), tableAlias)).append(") ");
            }
            if (table.isAccountRptTable() && this.accRptTableColFilter.length() > 0) {
                sqlBuilder.append(" and (").append((CharSequence)this.accRptTableColFilter).append(") ");
            }
        }
    }

    private void appendArgValue(StringBuilder sqlBuffer, Object value, int dataType) {
        if (value == null || "null".equals(value)) {
            return;
        }
        if (value instanceof List) {
            List values = (List)value;
            sqlBuffer.append("(");
            boolean addFlag = false;
            for (Object o : values) {
                if (addFlag) {
                    sqlBuffer.append(",");
                }
                FieldSqlConditionUtil.appendConstValue((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)sqlBuffer, (int)dataType, o);
                addFlag = true;
            }
            sqlBuffer.append(")");
            this.appendSpace(sqlBuffer);
        } else {
            this.appendValue(sqlBuffer, value, dataType);
        }
    }

    private void appendRowColFields(QueryContext context) throws ParseException {
        HashMap<String, ColumnModelDefine> fields = this.getDimensionFields();
        String acctAlias = this.getTableAlias(context, this.accountTable);
        for (Map.Entry<String, ColumnModelDefine> entry : fields.entrySet()) {
            String dimension = entry.getKey();
            ColumnModelDefine field = entry.getValue();
            if (dimension.equals("RECORDKEY") || field != null && !this.filterKeyColumns.contains(field.getName())) continue;
            String fieldName = field.getName();
            if (dimension.equals("DATATIME")) {
                fieldName = TN_ACCOUNT_COL_CODE_VALIDDATATIME;
            }
            if (field == null) continue;
            if (this.unionSelectFields.length() > 0) {
                this.selectFields.append(",");
                this.unionSelectFields.append(",");
            }
            QueryField queryField = new QueryField(field.getID(), fieldName, this.accountTable);
            String fieldAlias = "c_".concat(String.valueOf(this.fieldIndex));
            this.unionSelectFields.append(acctAlias).append(".").append(fieldName);
            if (this.isGroupingQuery()) {
                this.selectFields.append("min(");
                this.selectFields.append(UNIONTABLEALIAS).append(".").append(fieldName);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(UNIONTABLEALIAS).append(".").append(fieldName);
            }
            this.selectFields.append(" as ").append(fieldAlias);
            this.fieldAliases.put(queryField.getUID(), fieldAlias);
            this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, this.fieldIndex);
            this.queryFieldsIndex.put(queryField.getFieldName(), this.fieldIndex);
            ++this.fieldIndex;
            this.queryRegion.addQueryField(queryField);
        }
    }

    private void appendSourceFields(QueryContext context) throws ParseException {
        String prefix = "c_";
        QueryFields tableFields = this.queryRegion.getTableFields(this.accountTable);
        boolean addFalg = false;
        boolean isExistAccId = false;
        String acctAlias = this.getTableAlias(context, this.accountTable);
        HashMap<String, ColumnModelDefine> fields = this.getDimensionFields();
        Collection<ColumnModelDefine> keyColumns = fields.values();
        this.filterKeyColumns = keyColumns.stream().map(e -> e.getName()).collect(Collectors.toList());
        if (tableFields != null) {
            for (QueryField queryField : tableFields) {
                AggrType aggrType = null;
                String gather = this.getFieldGatherSql(queryField.getUID(), aggrType, queryField.getDataType());
                String fieldName = queryField.getFieldName();
                String fieldAlias = "c_".concat(String.valueOf(this.fieldIndex));
                if (addFalg) {
                    this.unionSelectFields.append(",");
                    this.selectFields.append(",");
                }
                if ("BIZKEYORDER".equals(fieldName)) {
                    isExistAccId = true;
                    this.unionSelectFields.append(acctAlias).append(".").append(DF_ACCOUNTID_CODE);
                    if (gather != null) {
                        this.addGatherField(gather, aggrType, UNIONTABLEALIAS, DF_ACCOUNTID_CODE, queryField.getDataType());
                    } else {
                        this.selectFields.append(UNIONTABLEALIAS).append(".").append(DF_ACCOUNTID_CODE);
                    }
                    this.selectFields.append(" as ").append(fieldAlias);
                    this.bizqueryField = this.initAcctIdFied(context);
                    this.fieldAliases.put(this.bizqueryField.getUID(), fieldAlias);
                    this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, this.fieldIndex);
                    this.queryFieldsIndex.put("BIZKEYORDER", this.fieldIndex);
                    ++this.fieldIndex;
                } else {
                    this.unionSelectFields.append(acctAlias).append(".").append(fieldName);
                    if (gather != null) {
                        this.addGatherField(gather, aggrType, UNIONTABLEALIAS, fieldName, queryField.getDataType());
                    } else {
                        this.selectFields.append(UNIONTABLEALIAS).append(".").append(fieldName);
                    }
                    this.selectFields.append(" as ").append(fieldAlias);
                    this.fieldAliases.put(queryField.getUID(), fieldAlias);
                    this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, this.fieldIndex);
                    this.queryFieldsIndex.put(queryField.getFieldName(), this.fieldIndex);
                    ++this.fieldIndex;
                }
                addFalg = true;
                if (this.filterFieldName != null && this.filterFieldName.equals(fieldName)) {
                    this.filterFieldName = acctAlias.concat(".").concat(this.filterFieldName);
                }
                this.filterKeyColumns.remove(fieldName);
            }
        }
        if (!isExistAccId) {
            String fieldAlias = "c_".concat(String.valueOf(this.fieldIndex));
            if (Objects.nonNull(tableFields) && tableFields.getCount() > 0) {
                this.unionSelectFields.append(",");
            }
            this.unionSelectFields.append(acctAlias).append(".").append(DF_ACCOUNTID_CODE);
            if (Objects.nonNull(tableFields) && tableFields.getCount() > 0) {
                this.selectFields.append(",");
            }
            if (this.isGroupingQuery()) {
                this.selectFields.append("min(").append(UNIONTABLEALIAS).append(".").append(DF_ACCOUNTID_CODE).append(")");
            } else {
                this.selectFields.append(UNIONTABLEALIAS).append(".").append(DF_ACCOUNTID_CODE);
            }
            this.selectFields.append(" as ").append(fieldAlias);
            if (this.bizqueryField == null) {
                this.bizqueryField = this.initAcctIdFied(context);
            }
            this.fieldAliases.put(this.bizqueryField.getUID(), fieldAlias);
            this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), this.bizqueryField, this.fieldIndex);
            this.queryFieldsIndex.put("BIZKEYORDER", this.fieldIndex);
            ++this.fieldIndex;
        }
        this.appendRowColFields(context);
    }

    private void leftJoinRptTable(QueryContext context) throws ParseException, InterpretException {
        if (this.accountRptTable == null) {
            return;
        }
        String alias = this.accountRptTable.getAlias();
        Object dataTime = context.getMasterKeys().getValue("DATATIME");
        this.joinTableAndCondition.append(this.accountRptTable.getTableName());
        this.appendSpace(this.joinTableAndCondition);
        this.joinTableAndCondition.append(alias).append(" on ").append(UNIONTABLEALIAS).append(".").append(DF_ACCOUNTID_CODE);
        this.joinTableAndCondition.append("=").append(this.accountRptTable.getAlias()).append(".").append(DF_ACCOUNTID_CODE);
        if (Objects.nonNull(dataTime)) {
            PeriodWrapper pw = context.getPeriodWrapper();
            if (pw != null) {
                dataTime = pw.toString();
            }
            this.joinTableAndCondition.append(" and ").append(alias).append(".").append("DATATIME");
            this.joinTableAndCondition.append("=");
            this.appendValue(this.joinTableAndCondition, dataTime, this.getDataType(context, "DATATIME"));
        }
        this.appendRowFilterSql(this.joinTableAndCondition, alias, this.accountRptTable);
    }

    private QueryField initAcctIdFied(QueryContext context) throws ParseException {
        QueryField field = null;
        TableModelRunInfo tableModelRunInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.accountTableName);
        ColumnModelDefine accountIdField = tableModelRunInfo.getAccountIdField();
        field = accountIdField != null ? new QueryField(accountIdField.getID(), "BIZKEYORDER", this.accountTable) : new QueryField(DF_ACCOUNTID_CODE, "BIZKEYORDER", this.accountTable);
        return field;
    }

    private String parseDimField(String dimension) {
        if (StringUtils.isEmpty((String)dimension)) {
            return null;
        }
        if (dimension.startsWith(MD_ORG_START)) {
            return "MDCODE";
        }
        if (dimension.equals("DATATIME")) {
            return "DATATIME";
        }
        if (dimension.equals("RECORDKEY")) {
            return "BIZKEYORDER";
        }
        return dimension;
    }

    private int getDataType(QueryContext context, String dimension) throws ParseException {
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.accountTableName);
        ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimension);
        int dataType = dimensionField != null ? DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType()) : 6;
        return dataType;
    }

    public AccountQuerySqlBuilder setTrackHistory(Boolean trackHistory) {
        this.isTrackHistory = trackHistory;
        return this;
    }

    public void setContainTimeField(Boolean containTimeField) {
        this.containTimeField = containTimeField;
    }

    protected void appendSpace(StringBuilder stringBuilder) {
        stringBuilder.append(" ");
    }

    public int getAccountFieldIndex() {
        return this.accountFieldIndex;
    }

    private void parseFilterToSQL(QueryContext context, IASTNode rowFilterNode, String tableName, String aliasTableName) {
        try {
            if (rowFilterNode == null) {
                return;
            }
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)aliasTableName)) {
                for (int index = 0; index < rowFilterNode.childrenSize(); ++index) {
                    IASTNode child = rowFilterNode.getChild(index);
                    if (child instanceof DynamicDataNode) {
                        DynamicDataNode dataNode = (DynamicDataNode)child;
                        if (!tableName.equals(dataNode.getQueryField().getTableName())) continue;
                        dataNode.setTableAlias(aliasTableName);
                        continue;
                    }
                    this.internalSetTableAlias(context, child, tableName, aliasTableName);
                }
            }
        }
        catch (Exception e) {
            logger.warn("\u7b5b\u9009\u8fc7\u6ee4\u6761\u4ef6\u8f6c\u5316SQL\u5931\u8d25\uff0c\u65e0\u6cd5\u6b63\u5e38\u8fc7\u6ee4\uff0c\u8bf7\u68c0\u67e5\uff01", e);
        }
    }

    private void internalSetTableAlias(QueryContext context, IASTNode parentNode, String tableName, String aliasTableName) throws InterpretException {
        for (int index = 0; index < parentNode.childrenSize(); ++index) {
            IASTNode child = parentNode.getChild(index);
            if (child instanceof DynamicDataNode) {
                DynamicDataNode dataNode = (DynamicDataNode)child;
                if (!tableName.equals(dataNode.getQueryField().getTableName())) continue;
                dataNode.setTableAlias(aliasTableName);
                continue;
            }
            this.internalSetTableAlias(context, child, tableName, aliasTableName);
        }
    }

    private void appendFilterSql(QueryContext context, String filterSql) throws InterpretException {
        if (filterSql.contains(this.getTableAlias(context, this.accountTable))) {
            if (this.accTableColFilter.length() > 0) {
                this.accTableColFilter.append(" and ");
            }
            this.accTableColFilter.append(filterSql);
        } else if (this.accountRptTable != null && filterSql.contains(this.getTableAlias(context, this.accountRptTable))) {
            if (this.accRptTableColFilter.length() > 0) {
                this.accRptTableColFilter.append(" and ");
            }
            this.accRptTableColFilter.append(filterSql);
        }
    }
}

