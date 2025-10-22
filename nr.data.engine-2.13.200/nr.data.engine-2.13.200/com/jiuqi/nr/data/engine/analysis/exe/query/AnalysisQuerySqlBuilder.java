/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.IQuerySqlUpdater
 *  com.jiuqi.np.dataengine.query.OrderByItem
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.dataengine.reader.MemoryDataSetReader
 *  com.jiuqi.np.dataengine.reader.QueryFieldInfo
 *  com.jiuqi.np.dataengine.setting.ISqlJoinProvider
 *  com.jiuqi.np.dataengine.setting.SqlJoinItem
 *  com.jiuqi.np.dataengine.setting.SqlJoinOneItem
 *  com.jiuqi.np.dataengine.util.FieldSqlConditionUtil
 *  com.jiuqi.np.definition.provider.DimensionColumn
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.engine.analysis.exe.query;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.dataengine.setting.ISqlJoinProvider;
import com.jiuqi.np.dataengine.setting.SqlJoinItem;
import com.jiuqi.np.dataengine.setting.SqlJoinOneItem;
import com.jiuqi.np.dataengine.util.FieldSqlConditionUtil;
import com.jiuqi.np.definition.provider.DimensionColumn;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisQueryRegion;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisQuerySqlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisQuerySqlBuilder.class);
    protected DimensionSet masterDimensions;
    protected AnalysisQueryRegion queryRegion;
    protected String primaryTableName;
    protected DimensionSet loopDimensions;
    protected HashMap<String, ColumnModelDefine> dimensionFields = new HashMap();
    protected IASTNode rowFilterNode;
    protected StringBuilder selectFields;
    protected StringBuilder fromJoinsTables;
    protected StringBuilder whereCondition;
    protected boolean whereNeedAnd;
    protected StringBuilder orderByClause;
    protected StringBuilder groupByClause;
    protected QueryTable primaryTable = null;
    protected List<QueryTable> leftJoinTables = new ArrayList<QueryTable>();
    protected List<QueryTable> fullJoinTables = new ArrayList<QueryTable>();
    protected List<QueryTable> emptyJoinTables = new ArrayList<QueryTable>();
    protected List<QueryField> allQueryFields = new ArrayList<QueryField>();
    protected int fieldIndex = 1;
    protected int rowKeyFieldStartIndex;
    protected HashMap<String, String> fieldAliases = new HashMap();
    protected HashMap<String, Integer> dimIndexes = new HashMap();
    protected QueryParam queryParam;
    protected HashSet<String> dimensionConditions = new HashSet();
    protected boolean forUpdateOnly;
    protected String sql = null;
    protected int memoryStartIndex = 0;
    protected boolean needMemoryFilter = false;
    protected boolean isUnionQuery;
    protected ISqlJoinProvider sqlJoinProvider;
    protected boolean ignoreDataVersion;
    protected HashMap<String, Object> unitKeyMap = new HashMap();
    protected HashMap<String, String> unitDimensionMap = new HashMap();
    protected List<Object> argValues;
    protected boolean sqlSoftParse = false;
    protected boolean inited = false;
    public static final String ROWINDEX = "rowindex";

    public void setForUpdateOnly(boolean openUpdateOnly) {
        this.forUpdateOnly = openUpdateOnly;
    }

    public void setQueryRegion(AnalysisQueryRegion queryRegion) {
        this.queryRegion = queryRegion;
    }

    public void setPrimaryTableName(String tableName) {
        this.primaryTableName = tableName;
    }

    public void setPrimaryTable(QueryTable primaryTable) {
        this.primaryTable = primaryTable;
        if (this.primaryTableName == null) {
            this.setPrimaryTableName(primaryTable.getTableName());
        }
    }

    public String buildSql(AnalysisContext context) throws Exception {
        this.doInit(context);
        if (this.primaryTable.isDimensionTable()) {
            return null;
        }
        return this.buildQuerySql(context);
    }

    public String buildQuerySql(AnalysisContext context) throws Exception {
        this.selectFields = new StringBuilder();
        this.fromJoinsTables = new StringBuilder();
        this.whereCondition = new StringBuilder();
        this.groupByClause = new StringBuilder();
        this.orderByClause = new StringBuilder();
        this.fieldIndex = 1;
        this.dimensionConditions.clear();
        this.fieldAliases.clear();
        this.whereNeedAnd = false;
        if (this.sqlSoftParse) {
            if (this.argValues == null) {
                this.argValues = new ArrayList<Object>();
            } else {
                this.argValues.clear();
            }
        }
        StringBuilder sqlBuilder = new StringBuilder();
        this.buildSqlByMainTable(context);
        this.appendJoinTables(context);
        this.buildWhereCondition(context);
        this.buildSelectSql(context, sqlBuilder);
        this.sql = sqlBuilder.toString();
        return this.sql;
    }

    public String buildQuerySql(AnalysisContext context, IQuerySqlUpdater sqlUpdater) throws Exception {
        if (sqlUpdater != null) {
            this.sqlSoftParse = false;
        }
        this.sql = this.buildQuerySql(context);
        if (sqlUpdater != null) {
            this.sql = sqlUpdater.updateQuerySql(this.primaryTable, this.getTableAlias(context, this.primaryTable), this.sql);
        }
        return this.sql;
    }

    public void doInit(AnalysisContext context) throws ParseException {
        if (this.inited) {
            return;
        }
        if (this.primaryTable == null) {
            for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
                if (table.getTableName().equals(this.primaryTableName)) {
                    this.primaryTable = table;
                    break;
                }
                if (context.isDest(table)) continue;
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
            if (table.equals((Object)this.primaryTable)) {
                context.getQueryTableAliaMap().put(this.primaryTable, this.createTableAlias(0));
                continue;
            }
            context.getQueryTableAliaMap().put(table, this.createTableAlias(++index));
            if (this.primaryTable != null && table.getTableDimensions().equals((Object)this.primaryTable.getTableDimensions())) {
                this.fullJoinTables.add(table);
                continue;
            }
            this.leftJoinTables.add(table);
        }
        this.inited = true;
    }

    protected String createTableAlias(int index) {
        return "t" + index;
    }

    public List<AnalysisQuerySqlBuilder> divideFullJoins(AnalysisContext context) throws ParseException {
        return this.dividTables(this.fullJoinTables, context);
    }

    protected List<AnalysisQuerySqlBuilder> dividTables(List<QueryTable> tables, AnalysisContext context) throws ParseException {
        ArrayList<AnalysisQuerySqlBuilder> builders = null;
        if (!tables.isEmpty()) {
            builders = new ArrayList<AnalysisQuerySqlBuilder>();
            for (QueryTable table : tables) {
                AnalysisQuerySqlBuilder subSqlBuilder = new AnalysisQuerySqlBuilder();
                AnalysisQueryRegion subRegion = new AnalysisQueryRegion(this.queryRegion.getDimensions(), subSqlBuilder);
                QueryFields queryFields = this.queryRegion.getTableFields(table);
                subRegion.addQueryFields(queryFields);
                subSqlBuilder.setPrimaryTable(table);
                subSqlBuilder.setQueryParam(this.queryParam);
                subSqlBuilder.setRowFilterNode(this.rowFilterNode);
                subSqlBuilder.setForUpdateOnly(this.forUpdateOnly);
                builders.add(subSqlBuilder);
                this.queryRegion.getAllTableFields().remove(table);
            }
            tables.clear();
        }
        return builders;
    }

    public ArrayList<OrderByItem> getOrderItems(ArrayList<OrderByItem> orderByItems, QueryFields queryFields, QueryTable table, AnalysisContext context) {
        ArrayList<OrderByItem> subOrders = new ArrayList<OrderByItem>();
        if (orderByItems != null) {
            for (OrderByItem orderByItem : orderByItems) {
                if (orderByItem.field == null || queryFields.indexOfFieldName(orderByItem.field.getFieldName()) < 0) continue;
                subOrders.add(orderByItem);
            }
        }
        return subOrders;
    }

    public List<AnalysisQuerySqlBuilder> divideLeftJoins(AnalysisContext context) throws ParseException {
        return this.dividTables(this.leftJoinTables, context);
    }

    protected void buildSelectSql(AnalysisContext context, StringBuilder sqlBuilder) throws Exception {
        if (this.selectFields.charAt(this.selectFields.length() - 1) == ',') {
            this.selectFields.setLength(this.selectFields.length() - 1);
        }
        sqlBuilder.append(" select ").append((CharSequence)this.selectFields).append(" from ").append((CharSequence)this.fromJoinsTables);
        if (this.whereCondition.length() > 0) {
            if (this.forUpdateOnly) {
                sqlBuilder.append(" where ").append("(1=0) and (").append((CharSequence)this.whereCondition).append(")");
            } else {
                sqlBuilder.append(" where ").append((CharSequence)this.whereCondition);
            }
        } else if (this.forUpdateOnly) {
            sqlBuilder.append(" where ").append("(1=0)");
        }
        if (this.orderByClause != null && this.orderByClause.length() > 0) {
            sqlBuilder.append(" order by ").append((CharSequence)this.orderByClause);
        }
    }

    public void setRowFilterNode(IASTNode rowFilterNode) {
        this.rowFilterNode = rowFilterNode;
    }

    private void appendJoinTables(AnalysisContext context) throws SQLException, ParseException, InterpretException {
        for (QueryTable table : this.leftJoinTables) {
            if (table.getIsLj()) {
                this.leftJoinLjTable(context, table);
                continue;
            }
            this.joinTable(context, table, "left");
        }
        for (QueryTable table : this.fullJoinTables) {
            this.joinTable(context, table, "full");
        }
    }

    public DimensionSet getLoopDimensions() {
        return this.loopDimensions;
    }

    public void setLoopDimensions(AnalysisContext context, DimensionSet loopDimensions) {
        String relatedUnitDimName;
        String unitDimesion;
        IDataLinkFinder dataLinkFinder;
        this.loopDimensions = new DimensionSet(loopDimensions);
        ExecutorContext exeContext = context.getExeContext();
        String linkAlias = (String)context.getTableLinkAliaMap().get(this.primaryTable);
        IDataLinkFinder iDataLinkFinder = dataLinkFinder = exeContext.getEnv() == null ? null : exeContext.getEnv().getDataLinkFinder();
        if (linkAlias != null && dataLinkFinder != null && exeContext.getEnv() != null && !loopDimensions.contains(unitDimesion = exeContext.getEnv().getUnitDimesion(exeContext)) && loopDimensions.contains(relatedUnitDimName = dataLinkFinder.getRelatedUnitDimName(context.getExeContext(), linkAlias, unitDimesion))) {
            this.unitDimensionMap.put(relatedUnitDimName, unitDimesion);
        }
        if (this.primaryTable != null) {
            for (int i = this.loopDimensions.size() - 1; i >= 0; --i) {
                String dimension = this.loopDimensions.get(i);
                if (this.primaryTable.getTableDimensions().contains(dimension)) continue;
                this.loopDimensions.removeAt(i);
            }
        }
    }

    public int getRowKeyFieldStartIndex() {
        return this.rowKeyFieldStartIndex;
    }

    private void joinTable(AnalysisContext context, QueryTable table, String type) throws SQLException, ParseException {
        String tableAlias = this.getTableAlias(context, table);
        String primaryTableAlias = this.getTableAlias(context, this.primaryTable);
        ExecutorContext exeContext = context.getExeContext();
        TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
        TableModelRunInfo primaryTableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        SqlJoinItem joinItem = null;
        if (this.sqlJoinProvider != null) {
            joinItem = this.sqlJoinProvider.getSqlJoinItem(this.primaryTable.getTableName(), table.getTableName());
        }
        if (joinItem != null) {
            type = joinItem.getJoinType().name();
        }
        this.fromJoinsTables.append(" ").append(type).append(" join ");
        this.appendQueryTable(context, this.fromJoinsTables, table);
        this.fromJoinsTables.append(" on ");
        boolean needAnd = false;
        if (joinItem != null && joinItem.getJoinFields() != null && joinItem.getJoinFields().size() > 0) {
            for (SqlJoinOneItem oneItem : joinItem.getJoinFields()) {
                if (needAnd) {
                    this.fromJoinsTables.append(" and ");
                }
                this.fromJoinsTables.append(primaryTableAlias).append(".").append(oneItem.getSrcField());
                this.fromJoinsTables.append("=");
                this.fromJoinsTables.append(tableAlias).append(".").append(oneItem.getDesField());
                needAnd = true;
            }
        } else {
            for (int i = 0; i < table.getTableDimensions().size(); ++i) {
                String dimensionFieldName;
                Object dimValue;
                String dimension = table.getTableDimensions().get(i);
                ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimension);
                if (dimensionField == null) continue;
                TempAssistantTable tempAssistantTable = (TempAssistantTable)context.getTempAssistantTables().get(dimension);
                PeriodModifier periodModifier = table.getPeriodModifier();
                if (periodModifier != null && dimension.equals("DATATIME")) {
                    PeriodWrapper periodWrapper = context.getPeriodWrapper();
                    IPeriodAdapter periodAdapter = exeContext.getPeriodAdapter();
                    periodAdapter.modify(periodWrapper, periodModifier);
                    String dimValue2 = periodWrapper.toString();
                    String dimensionFieldName2 = dimension;
                    dimensionFieldName2 = dimensionField.getName();
                    int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
                    this.fromJoinsTables.append(tableAlias).append(".");
                    this.fromJoinsTables.append(dimensionFieldName2);
                    this.fromJoinsTables.append("=");
                    FieldSqlConditionUtil.appendConstValue((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)this.fromJoinsTables, (int)dimDataType, (Object)dimValue2);
                    needAnd = true;
                    continue;
                }
                if (table.getDimensionRestriction() != null && table.getDimensionRestriction().hasValue(dimension)) {
                    dimValue = table.getDimensionRestriction().getValue(dimension);
                    dimensionFieldName = dimension;
                    dimensionFieldName = dimensionField.getName();
                    int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
                    this.fromJoinsTables.append(tableAlias).append(".");
                    this.fromJoinsTables.append(dimensionFieldName);
                    this.fromJoinsTables.append("=");
                    FieldSqlConditionUtil.appendConstValue((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)this.fromJoinsTables, (int)dimDataType, (Object)dimValue);
                    needAnd = true;
                    continue;
                }
                if (this.primaryTable.getTableDimensions().contains(dimension)) {
                    if (needAnd) {
                        this.fromJoinsTables.append(" and ");
                    }
                    this.fromJoinsTables.append(primaryTableAlias).append(".").append(DataEngineUtil.getDimensionFieldName((TableModelRunInfo)primaryTableInfo, (String)dimension));
                    this.fromJoinsTables.append("=");
                    this.fromJoinsTables.append(tableAlias).append(".").append(DataEngineUtil.getDimensionFieldName((TableModelRunInfo)tableInfo, (String)dimension));
                    needAnd = true;
                    continue;
                }
                if (!context.getMasterKeys().hasValue(dimension)) continue;
                dimValue = context.getMasterKeys().getValue(dimension);
                dimensionFieldName = dimension;
                dimensionFieldName = dimensionField.getName();
                int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
                this.appendToCondition(context, tempAssistantTable, this.fromJoinsTables, tableAlias, dimensionFieldName, dimValue, dimDataType, needAnd);
                needAnd = true;
            }
        }
        if (!needAnd) {
            this.fromJoinsTables.append(" 1=1 ");
            needAnd = true;
        }
    }

    protected void appendToCondition(QueryContext qContext, TempAssistantTable tempAssistantTable, StringBuilder sql, String tableAlias, String name, Object value, int dataType, boolean needAnd) {
        String fieldName = tableAlias + "." + name;
        List<Object> args = this.sqlSoftParse ? this.argValues : null;
        FieldSqlConditionUtil.appendFieldCondition((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)sql, (String)fieldName, (int)dataType, (Object)value, (boolean)needAnd, (TempAssistantTable)tempAssistantTable, args);
    }

    protected void appendValue(StringBuilder sql, Object value, int dataType) {
        List<Object> args = this.sqlSoftParse ? this.argValues : null;
        FieldSqlConditionUtil.appendValue((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)sql, (int)dataType, (Object)value, args);
    }

    private void leftJoinLjTable(AnalysisContext context, QueryTable table) {
        this.fromJoinsTables.append(" left join ");
        this.fromJoinsTables.append(" (select ");
        for (QueryField queryField : this.queryRegion.getTableFields(table)) {
            this.fromJoinsTables.append("sum(").append(table.getTableName()).append(".").append(queryField.getFieldName()).append(") as ").append(queryField.getFieldName());
            this.fromJoinsTables.append(",");
        }
        this.fromJoinsTables.setLength(this.fromJoinsTables.length() - 1);
        this.fromJoinsTables.append(" from ");
        this.fromJoinsTables.append(" ").append(this.getTableName(table));
        this.fromJoinsTables.append(" ");
        this.fromJoinsTables.append(table.getTableName());
        this.fromJoinsTables.append(" where ");
        this.fromJoinsTables.append(") ");
        this.fromJoinsTables.append(" ");
        this.fromJoinsTables.append(this.getTableAlias(context, table));
        this.fromJoinsTables.append(" on ");
    }

    private void buildSqlByMainTable(AnalysisContext context) throws SQLException, ParseException {
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        this.appendQueryFields(context, tableInfo);
        this.appendRowKeyFields(context, tableInfo);
        this.appendQueryTable(context, this.fromJoinsTables, this.primaryTable);
        this.buildWhereConditionByTable(context, tableInfo, this.primaryTable);
    }

    protected boolean isGroupingQuery() {
        return false;
    }

    private void appendRowKeyFields(AnalysisContext qContext, TableModelRunInfo tableInfo) {
        this.rowKeyFieldStartIndex = this.fieldIndex;
        if (this.loopDimensions == null) {
            this.loopDimensions = new DimensionSet(this.primaryTable.getTableDimensions());
            for (int i = 0; i < this.primaryTable.getTableDimensions().size(); ++i) {
                String dimName = this.primaryTable.getTableDimensions().get(i);
                if (this.primaryTable.getDimensionRestriction() != null && !this.primaryTable.getDimensionRestriction().hasValue(dimName)) {
                    this.loopDimensions.removeDimension(dimName);
                }
                if (this.primaryTable.getPeriodModifier() == null) continue;
                this.loopDimensions.removeDimension("DATATIME");
            }
        }
        String primaryTableAlias = this.getTableAlias(qContext, this.primaryTable);
        for (int i = 0; i < this.loopDimensions.size(); ++i) {
            String keyName = this.loopDimensions.get(i);
            ColumnModelDefine keyField = tableInfo.getDimensionField(keyName);
            this.dimensionFields.put(keyName, keyField);
            String keyfieldName = keyField.getName();
            if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(keyfieldName);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(keyfieldName);
            }
            String fieldAlias = "c" + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.dimIndexes.put(keyField.getID(), this.fieldIndex);
            this.fieldAliases.put(keyField.getID(), fieldAlias);
            ++this.fieldIndex;
        }
        ColumnModelDefine inputOrderField = tableInfo.getOrderField();
        if (inputOrderField != null) {
            String fieldName = inputOrderField.getName();
            if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(fieldName);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(fieldName);
            }
            String fieldAlias = "c" + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.fieldAliases.put(inputOrderField.getID(), fieldAlias);
            ++this.fieldIndex;
        }
    }

    private void appendQueryFields(AnalysisContext context, TableModelRunInfo tableInfo) throws ParseException {
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            String tableAlias = this.getTableAlias(context, table);
            for (QueryField queryField : this.queryRegion.getTableFields(table)) {
                this.selectFields.append(tableAlias).append(".").append(queryField.getFieldName());
                String fieldAlias = String.format("%s%s", "c", this.fieldIndex);
                this.selectFields.append(" as ").append(fieldAlias).append(",");
                this.fieldAliases.put(queryField.getUID(), fieldAlias);
                QueryFieldInfo fieldInfo = this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, this.fieldIndex);
                if (!this.isUnionQuery) {
                    fieldInfo.dimensionName = tableInfo.getDimensionName(queryField.getFieldCode());
                }
                ++this.fieldIndex;
            }
        }
    }

    protected IQueryFieldDataReader getDataReader(AnalysisContext AnalysisContext2) {
        IQueryFieldDataReader dataReader = AnalysisContext2.getDataReader();
        if (dataReader == null) {
            dataReader = new MemoryDataSetReader((QueryContext)AnalysisContext2);
            AnalysisContext2.setDataReader(dataReader);
        }
        return dataReader;
    }

    private void buildWhereConditionByTable(AnalysisContext qContext, TableModelRunInfo tableInfo, QueryTable queryTable) throws SQLException, ParseException {
        Object keyValue;
        String keyName;
        int i;
        String tableAlias = this.getTableAlias(qContext, queryTable);
        DimensionValueSet masterkeys = qContext.getMasterKeys();
        this.initDefaultVersionKey(masterkeys, tableInfo);
        DimensionValueSet tableRestriction = queryTable.getDimensionRestriction();
        ColumnModelDefine periodField = tableInfo.getPeriodField();
        ExecutorContext exeContext = qContext.getExeContext();
        for (i = 0; i < masterkeys.size(); ++i) {
            List values;
            keyName = masterkeys.getName(i);
            keyValue = masterkeys.getValue(i);
            if (tableRestriction != null && tableRestriction.hasValue(keyName)) continue;
            String dimensionFieldName = keyName;
            ColumnModelDefine dimensionField = tableInfo.getDimensionField(keyName);
            String linkAlias = (String)qContext.getTableLinkAliaMap().get(queryTable);
            if (linkAlias != null) {
                String relatedUnitDimName = null;
                if (dimensionField == null) {
                    IDataLinkFinder dataLinkFinder;
                    if (exeContext.getEnv() != null && keyName.equals(exeContext.getEnv().getUnitDimesion(exeContext)) && (relatedUnitDimName = (dataLinkFinder = exeContext.getEnv().getDataLinkFinder()).getRelatedUnitDimName(exeContext, linkAlias, keyName)) != null) {
                        keyName = relatedUnitDimName;
                        dimensionField = tableInfo.getDimensionField(keyName);
                        keyValue = this.processLinkedUnit(qContext, queryTable, exeContext, keyName, keyValue);
                    }
                } else if (exeContext.getEnv() != null && keyName.equals(exeContext.getEnv().getUnitDimesion(exeContext))) {
                    relatedUnitDimName = keyName;
                    keyValue = this.processLinkedUnit(qContext, queryTable, exeContext, keyName, keyValue);
                }
            }
            if (dimensionField == null) continue;
            dimensionFieldName = dimensionField.getName();
            int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
            TempAssistantTable tempAssistantTable = (TempAssistantTable)qContext.getTempAssistantTables().get(keyName);
            if (keyValue instanceof List && tempAssistantTable == null && (values = (List)keyValue).size() >= DataEngineUtil.getMaxInSize((IDatabase)qContext.getQueryParam().getDatabase())) {
                try {
                    tempAssistantTable = new TempAssistantTable(values, dimDataType);
                    tempAssistantTable.createTempTable(this.queryParam.getConnection());
                    tempAssistantTable.insertIntoTempTable(this.queryParam.getConnection());
                    qContext.getTempAssistantTables().put(keyName, tempAssistantTable);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (StringUtils.isEmpty((String)dimensionFieldName) || this.dimensionConditions.contains(keyName)) continue;
            this.dimensionConditions.add(keyName);
            if (periodField != null && keyName.equals("DATATIME")) {
                String periodStr = null;
                if (keyValue instanceof String) {
                    periodStr = (String)keyValue;
                    PeriodModifier periodModifier = queryTable.getPeriodModifier();
                    if (periodModifier != null) {
                        periodStr = exeContext.getPeriodAdapter().modify(periodStr, periodModifier);
                    }
                }
                if (periodStr != null) {
                    keyValue = periodStr;
                }
                if (queryTable.getIsLj()) {
                    if (this.whereNeedAnd) {
                        this.whereCondition.append(" and ");
                    }
                    this.whereCondition.append(tableAlias).append(".").append(dimensionFieldName);
                    this.whereCondition.append(" like '").append(periodStr, 0, 5).append("%'");
                    this.whereCondition.append(" and ");
                    this.whereCondition.append(tableAlias).append(".").append(dimensionFieldName);
                    this.whereCondition.append("<='").append(periodStr).append("'");
                } else {
                    this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, dimensionFieldName, keyValue, dimDataType, this.whereNeedAnd);
                }
            } else {
                if (tempAssistantTable != null && DataEngineUtil.needJoinTempTable((IDatabase)this.queryParam.getDatabase())) {
                    this.fromJoinsTables.append(tempAssistantTable.getJoinSql(tableAlias + "." + dimensionFieldName));
                    continue;
                }
                this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, dimensionFieldName, keyValue, dimDataType, this.whereNeedAnd);
            }
            this.whereNeedAnd = true;
        }
        if (tableRestriction != null) {
            for (i = 0; i < tableRestriction.size(); ++i) {
                keyName = tableRestriction.getName(i);
                keyValue = tableRestriction.getValue(i);
                TempAssistantTable tempAssistantTable = (TempAssistantTable)qContext.getTempAssistantTables().get(keyName);
                if (keyValue == null) continue;
                String dimensionFieldName = keyName;
                ColumnModelDefine dimensionField = tableInfo.getDimensionField(keyName);
                if (dimensionField != null) {
                    dimensionFieldName = dimensionField.getName();
                }
                int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
                if (StringUtils.isEmpty((String)dimensionFieldName)) continue;
                this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, dimensionFieldName, keyValue, dimDataType, this.whereNeedAnd);
                this.whereNeedAnd = true;
            }
        }
    }

    private Object processLinkedUnit(AnalysisContext qContext, QueryTable queryTable, ExecutorContext exeContext, String keyName, Object keyValue) {
        IDataLinkFinder dataLinkFinder;
        String linkAlias = (String)qContext.getTableLinkAliaMap().get(queryTable);
        IDataLinkFinder iDataLinkFinder = dataLinkFinder = exeContext.getEnv() == null ? null : exeContext.getEnv().getDataLinkFinder();
        if (linkAlias != null && dataLinkFinder != null) {
            if (keyValue instanceof List) {
                ArrayList<Object> unitKeys = new ArrayList<Object>();
                List valueList = keyValue;
                Map keyValueMap = dataLinkFinder.findRelatedUnitKeyMap(exeContext, linkAlias, keyName, valueList);
                if (keyValueMap != null) {
                    for (Object v : valueList) {
                        Object mapedValue = keyValueMap.get(v);
                        if (mapedValue != null) {
                            if (mapedValue instanceof List) {
                                for (Object mv : (List)mapedValue) {
                                    this.unitKeyMap.put(mv.toString(), v);
                                    unitKeys.add(mv);
                                }
                                continue;
                            }
                            this.unitKeyMap.put(mapedValue.toString(), v);
                            unitKeys.add(mapedValue);
                            continue;
                        }
                        unitKeys.add(v);
                    }
                    keyValue = unitKeys;
                }
            } else {
                List mapedKeyValue = dataLinkFinder.findRelatedUnitKey(exeContext, linkAlias, keyName, (Object)keyValue);
                if (mapedKeyValue != null) {
                    this.unitKeyMap.put(mapedKeyValue.toString(), keyValue);
                    keyValue = mapedKeyValue;
                }
            }
        }
        return keyValue;
    }

    private void initDefaultVersionKey(DimensionValueSet masterkeys, TableModelRunInfo tableRunInfo) {
        if (tableRunInfo.getVersionField() == null || this.ignoreDataVersion) {
            return;
        }
        if (masterkeys.hasValue("VERSIONID")) {
            Object versionValue = masterkeys.getValue("VERSIONID");
            if (tableRunInfo.getVersionField().getColumnType() == ColumnModelType.UUID) {
                masterkeys.setValue("VERSIONID", (Object)UUID.fromString(versionValue.toString()));
            } else {
                masterkeys.setValue("VERSIONID", (Object)versionValue.toString());
            }
            return;
        }
        if (tableRunInfo.getVersionField().getColumnType() == ColumnModelType.UUID) {
            masterkeys.setValue("VERSIONID", (Object)UUID.fromString("00000000-0000-0000-0000-000000000000"));
        } else {
            masterkeys.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
        }
    }

    protected void buildWhereCondition(AnalysisContext qContext) throws InterpretException {
        if (this.rowFilterNode != null) {
            if (this.rowFilterNode.support(Language.SQL)) {
                if (this.whereNeedAnd) {
                    this.whereCondition.append(" and ");
                }
                SQLInfoDescr conditionSqlINfo = new SQLInfoDescr(this.queryParam.getDatabase(), true, 0, 0);
                this.whereCondition.append("(").append(this.rowFilterNode.interpret((IContext)qContext, Language.SQL, (Object)conditionSqlINfo)).append(")");
                this.whereNeedAnd = true;
            } else {
                this.needMemoryFilter = true;
            }
        }
    }

    protected String getDateCompareSql(String alias, String fieldName, String compareOper, Date date) {
        IDatabase database = this.queryParam.getDatabase();
        StringBuilder sql = new StringBuilder();
        if (StringUtils.isNotEmpty((String)alias)) {
            sql.append(alias).append(".");
        }
        sql.append(fieldName);
        sql.append(compareOper);
        try {
            sql.append(database.createSQLInterpretor(this.queryParam.getConnection()).formatSQLDate(date));
        }
        catch (SQLInterpretException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return sql.toString();
    }

    protected String getTableName(QueryTable queryTable) {
        return queryTable.getTableName();
    }

    protected void appendQueryTable(AnalysisContext qContext, StringBuilder sql, QueryTable table) {
        sql.append(" ").append(this.getTableName(table));
        sql.append(" ");
        sql.append(this.getTableAlias(qContext, table));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DataSet<QueryField> runQuery(AnalysisContext qContext) throws Exception {
        try {
            DataSet<QueryField> dataSet;
            IFmlExecEnvironment env = qContext.getExeContext().getEnv();
            if (env != null && env.JudgeZBAuth()) {
                ArrayList<String> fieldKeys = new ArrayList<String>();
                for (QueryFields queryFields : this.queryRegion.getAllTableFields().values()) {
                    for (QueryField field : queryFields) {
                        fieldKeys.add(field.getUID());
                    }
                }
                qContext.setAulthJuger(env.getZBAuthJudger(fieldKeys));
            }
            if (this.primaryTable.isDimensionTable()) {
                dataSet = this.getDimensionTableDataSet(qContext);
                return dataSet;
            }
            dataSet = this.queryByJDBC(qContext, this.sql);
            return dataSet;
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    private DataSet<QueryField> getDimensionTableDataSet(AnalysisContext qContext) throws Exception {
        MemoryDataSet dataSet = new MemoryDataSet();
        TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        if (this.loopDimensions == null) {
            this.loopDimensions = new DimensionSet(this.primaryTable.getTableDimensions());
        }
        for (QueryField queryField : this.queryRegion.getTableFields(this.primaryTable)) {
            dataSet.getMetadata().addColumn(new Column(queryField.getFieldCode(), queryField.getDataType(), (Object)queryField));
            ++this.fieldIndex;
        }
        this.rowKeyFieldStartIndex = this.fieldIndex;
        for (ColumnModelDefine keyField : tableInfo.getDimFields()) {
            QueryField queryField = new QueryField(keyField.getID(), keyField.getCode(), keyField.getName(), this.primaryTable);
            queryField.setDataType(DataTypesConvert.fieldTypeToDataType((ColumnModelType)keyField.getColumnType()));
            dataSet.getMetadata().addColumn(new Column("key_" + keyField.getName(), queryField.getDataType(), (Object)queryField));
        }
        ArrayList<DimensionColumn> dimColumns = new ArrayList<DimensionColumn>();
        DimensionTable dimTable = qContext.getDimTable(this.primaryTable.getTableName());
        for (int n = 0; n < dataSet.getMetadata().getColumnCount(); ++n) {
            Column column = dataSet.getMetadata().getColumn(n);
            String dimColumnName = ((QueryField)column.getInfo()).getFieldCode();
            DimensionColumn dimColumn = dimTable.getMetaData().findColumn(dimColumnName);
            dimColumns.add(dimColumn);
        }
        for (int i = 0; i < dimTable.rowCount(); ++i) {
            DimensionRow dimensionRow = dimTable.getRow(i);
            DataRow row = dataSet.add();
            for (int index = 0; index < dimColumns.size(); ++index) {
                DimensionColumn dimColumn = (DimensionColumn)dimColumns.get(index);
                Object value = dimensionRow.getValue(dimColumn.getIndex());
                row.setValue(index, value);
            }
            row.commit();
        }
        return dataSet;
    }

    private DataSet<QueryField> queryByJDBC(AnalysisContext qContext, String mainQuerySql) throws SQLException, DataSetException {
        Connection conn = this.queryParam.getConnection();
        Object[] args = this.argValues == null ? null : this.argValues.toArray();
        MemoryDataSet dataSet = DataEngineUtil.queryMemoryDataSet((Connection)conn, (String)mainQuerySql, (Object[])args, (IMonitor)qContext.getMonitor());
        this.getDataReader(qContext).setDataSet((Object)dataSet);
        return dataSet;
    }

    public DimensionValueSet buildRowKeys(IQueryFieldDataReader reader) throws Exception {
        DimensionValueSet rowKeys = new DimensionValueSet();
        DimensionSet loopDimensions = this.getLoopDimensions();
        HashMap<String, ColumnModelDefine> dimensionFields = this.getDimensionFields();
        for (int i = 0; i < loopDimensions.size(); ++i) {
            String dimensionName = loopDimensions.get(i);
            Object value = reader.readData(i + this.getRowKeyFieldStartIndex());
            if (dimensionFields.containsKey(dimensionName)) {
                value = DataEngineConsts.formatData((ColumnModelDefine)dimensionFields.get(dimensionName), (Object)value, null);
            }
            if (value == null) continue;
            rowKeys.setValue(dimensionName, value);
        }
        return rowKeys;
    }

    protected String getTableAlias(AnalysisContext qContext, QueryTable table) {
        String tableAlias = (String)qContext.getQueryTableAliaMap().get(table);
        if (tableAlias == null) {
            tableAlias = table.getAlias();
        }
        return tableAlias;
    }

    public void setMasterDimensions(DimensionSet masterDimensions) {
        this.masterDimensions = masterDimensions;
    }

    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    public HashMap<String, ColumnModelDefine> getDimensionFields() {
        return this.dimensionFields;
    }

    public String getSql() {
        return this.sql;
    }

    public int getMemoryStartIndex() {
        return this.memoryStartIndex;
    }

    public void setMemoryStartIndex(int memoryStartIndex) {
        this.memoryStartIndex = memoryStartIndex;
    }

    public AnalysisQueryRegion getQueryRegion() {
        return this.queryRegion;
    }

    public boolean isNeedMemoryFilter() {
        return this.needMemoryFilter;
    }

    public void setUnionQuery(boolean isUnionQuery) {
        this.isUnionQuery = isUnionQuery;
    }

    public boolean isUnionQuery() {
        return this.isUnionQuery;
    }

    public ISqlJoinProvider getSqlJoinProvider() {
        return this.sqlJoinProvider;
    }

    public void setSqlJoinProvider(ISqlJoinProvider sqlJoinProvider) {
        this.sqlJoinProvider = sqlJoinProvider;
    }

    public boolean isIgnoreDataVersion() {
        return this.ignoreDataVersion;
    }

    public void setIgnoreDataVersion(boolean ignoreDataVersion) {
        this.ignoreDataVersion = ignoreDataVersion;
    }

    public QueryTable getPrimaryTable() {
        return this.primaryTable;
    }

    public HashMap<String, Object> getUnitKeyMap() {
        return this.unitKeyMap;
    }

    public HashMap<String, String> getUnitDimensionMap() {
        return this.unitDimensionMap;
    }

    public boolean isSqlSoftParse() {
        return this.sqlSoftParse;
    }

    public void setSqlSoftParse(boolean sqlSoftParse) {
        this.sqlSoftParse = sqlSoftParse;
    }

    public List<Object> getArgValues() {
        return this.argValues;
    }

    public String getIndexSql(AnalysisContext AnalysisContext2, String mainQuerySql, DimensionValueSet rowKeys, DimensionValueSet masterKeys) throws ParseException, ExpressionException {
        StringBuilder rowIndexSql = new StringBuilder(mainQuerySql.length());
        StringBuilder leftSql = new StringBuilder();
        leftSql.append("select tt.").append(ROWINDEX);
        rowIndexSql.append(" from (");
        rowIndexSql.append("select rownum as ").append(ROWINDEX).append(", o.* from (");
        rowIndexSql.append(mainQuerySql);
        rowIndexSql.append(") o");
        rowIndexSql.append(") tt where ");
        TableModelRunInfo tableInfo = AnalysisContext2.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        DimensionSet dimensionSet = rowKeys.getDimensionSet();
        int dimCount = dimensionSet.size();
        boolean hasError = false;
        boolean addDot = false;
        boolean hasDim = false;
        for (int index = 0; index < dimCount; ++index) {
            String dimName = dimensionSet.get(index);
            if (masterKeys.hasValue(dimName)) continue;
            ColumnModelDefine dimField = tableInfo.getDimensionField(dimName);
            if (dimField == null) {
                hasError = true;
                break;
            }
            int dataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimField.getColumnType());
            if (!this.fieldAliases.containsKey(dimField.getID())) {
                hasError = true;
                break;
            }
            this.appendToCondition(AnalysisContext2, null, rowIndexSql, "tt", this.fieldAliases.get(dimField.getID()), rowKeys.getValue(dimName), dataType, addDot);
            leftSql.append(", ");
            leftSql.append("tt.").append(this.fieldAliases.get(dimField.getID())).append(" as ").append(dimName);
            hasDim = true;
            addDot = true;
        }
        if (hasError || !hasDim) {
            return "";
        }
        leftSql.append((CharSequence)rowIndexSql);
        return leftSql.toString();
    }
}

