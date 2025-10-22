/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
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
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.query.old;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
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
import com.jiuqi.np.dataengine.common.LookupItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QueryFilterValueClassify;
import com.jiuqi.np.dataengine.query.QueryTableColFilterValues;
import com.jiuqi.np.dataengine.query.old.MemoryDataSetReader;
import com.jiuqi.np.dataengine.query.old.QueryRegion;
import com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.reader.DataSetReader;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.dataengine.setting.ISqlJoinProvider;
import com.jiuqi.np.dataengine.setting.SqlJoinItem;
import com.jiuqi.np.dataengine.setting.SqlJoinOneItem;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuerySqlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QuerySqlBuilder.class);
    protected boolean useDNASql;
    protected DimensionSet masterDimensions;
    protected QueryRegion queryRegion;
    protected String primaryTableName;
    protected ArrayList<OrderByItem> orderByItems;
    protected QueryFilterValueClassify colValueFilters;
    protected Date queryVersionDate = Consts.DATE_VERSION_FOR_ALL;
    protected Date queryVersionStartDate = Consts.DATE_VERSION_INVALID_VALUE;
    protected boolean designTimeData;
    protected ReadonlyTableImpl resultTable;
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
    protected HashMap<String, String> lookupAliases = new HashMap();
    protected HashMap<String, Integer> dimIndexes = new HashMap();
    protected QueryParam queryParam;
    protected HashSet<String> dimensionConditions = new HashSet();
    protected boolean forUpdateOnly;
    protected List<String> recKeys;
    protected String sql = null;
    protected int memoryStartIndex = 0;
    protected boolean needMemoryFilter = false;
    protected boolean needAdjustJoinTables = false;
    protected boolean useDefaultOrderBy = true;
    protected boolean isUnionQuery;
    protected ISqlJoinProvider sqlJoinProvider;
    protected boolean allVersionQuery;
    protected boolean ignoreDataVersion;
    protected HashMap<String, Object> unitKeyMap = new HashMap();
    protected HashMap<String, String> unitDimensionMap = new HashMap();
    protected List<Object> argValues;
    protected boolean sqlSoftParse = false;
    protected boolean inited = false;
    public static final String ROWINDEX = "rowindex";
    public static final String TABLE_ALIAS_PREFIX = "c_";
    private boolean ignoreDefaultOrderBy = false;

    public void setOrderByItems(ArrayList<OrderByItem> orderByItems) {
        this.orderByItems = orderByItems;
    }

    public void setUseDefaultOrderBy(boolean useDefaultOrderBy) {
        this.useDefaultOrderBy = useDefaultOrderBy;
    }

    public void setForUpdateOnly(boolean openUpdateOnly) {
        this.forUpdateOnly = openUpdateOnly;
    }

    public void setQueryVersionDate(Date queryVersionDate) {
        if (queryVersionDate != null) {
            this.queryVersionDate = queryVersionDate;
        }
    }

    public void setQueryVersionStartDate(Date queryVersionStartDate) {
        if (queryVersionStartDate != null) {
            this.queryVersionStartDate = queryVersionStartDate;
        }
    }

    public void setColValueFilters(QueryFilterValueClassify colValueFilters) {
        this.colValueFilters = colValueFilters;
    }

    public void setGetRowNum(boolean getRowNum) {
    }

    public void setRowKeys(DimensionValueSet rowKeys) {
    }

    public boolean trySetRowFilterNode(IASTNode rowFilterNode) {
        return false;
    }

    public void setQueryRegion(QueryRegion queryRegion) {
        this.queryRegion = queryRegion;
    }

    public ReadonlyTableImpl getResultTable() {
        return this.resultTable;
    }

    public void setResultTable(ReadonlyTableImpl readonlyTableImpl) {
        this.resultTable = readonlyTableImpl;
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

    public String buildSql(QueryContext context) throws Exception {
        this.doInit(context);
        return this.buildQuerySql(context);
    }

    public String buildQuerySql(QueryContext context) throws Exception {
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
        boolean sqlSoftParse = this.sqlSoftParse;
        this.sqlSoftParse = false;
        this.appendJoinTables(context);
        this.sqlSoftParse = sqlSoftParse;
        this.buildLookupItems(context);
        this.buildWhereCondition(context);
        this.buildGroupBy(context);
        this.buildOrderBy(context);
        this.buildSelectSql(context, sqlBuilder);
        this.sql = sqlBuilder.toString();
        return this.sql;
    }

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

    public void doInit(QueryContext context) throws ParseException {
        if (this.inited) {
            return;
        }
        this.initPrimaryTable();
        int index = 0;
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            if (table.equals(this.primaryTable)) {
                context.getQueryTableAliaMap().put(this.primaryTable, this.createTableAlias(0));
                continue;
            }
            context.getQueryTableAliaMap().put(table, this.createTableAlias(++index));
            if (this.primaryTable.getDimensionRestriction() != null || table.getDimensionRestriction() != null) {
                DimensionSet primaryLoopDims = new DimensionSet();
                for (int i = 0; i < this.primaryTable.getTableDimensions().size(); ++i) {
                    String dimName = this.primaryTable.getTableDimensions().get(i);
                    if (this.primaryTable.getDimensionRestriction() != null && this.primaryTable.getDimensionRestriction().hasValue(dimName)) continue;
                    primaryLoopDims.addDimension(dimName);
                }
                DimensionSet tableLoopDims = new DimensionSet();
                for (int i = 0; i < table.getTableDimensions().size(); ++i) {
                    String dimName = table.getTableDimensions().get(i);
                    if (table.getDimensionRestriction() != null && table.getDimensionRestriction().hasValue(dimName)) continue;
                    tableLoopDims.addDimension(dimName);
                }
                if (primaryLoopDims.size() > tableLoopDims.size()) {
                    this.leftJoinTables.add(table);
                    continue;
                }
                this.fullJoinTables.add(table);
                continue;
            }
            if (table.getTableDimensions().equals(this.primaryTable.getTableDimensions())) {
                this.fullJoinTables.add(table);
                continue;
            }
            this.leftJoinTables.add(table);
        }
        if (this.isNeedAdjustJoinTables()) {
            this.adjustJoinTables(context);
        }
        this.inited = true;
    }

    protected void initPrimaryTable() {
        if (this.primaryTable == null) {
            for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
                if (table.getTableName().equals(this.primaryTableName)) {
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
    }

    protected String createTableAlias(int index) {
        return "t" + index;
    }

    public List<QuerySqlBuilder> divideFullJoins(QueryContext context) throws ParseException {
        this.orderByItems = null;
        this.setUseDefaultOrderBy(false);
        return this.dividTables(this.fullJoinTables, context);
    }

    protected List<QuerySqlBuilder> dividTables(List<QueryTable> tables, QueryContext context) throws ParseException {
        ArrayList<QuerySqlBuilder> builders = null;
        if (!tables.isEmpty()) {
            builders = new ArrayList<QuerySqlBuilder>();
            for (QueryTable table : tables) {
                QuerySqlBuilder subSqlBuilder = new QuerySqlBuilder();
                QueryRegion subRegion = new QueryRegion(this.queryRegion.getDimensions(), subSqlBuilder);
                QueryFields queryFields = this.queryRegion.getTableFields(table);
                subRegion.addQueryFields(queryFields);
                subSqlBuilder.setUseDNASql(this.useDNASql);
                subSqlBuilder.setPrimaryTable(table);
                subSqlBuilder.setQueryParam(this.queryParam);
                subSqlBuilder.setResultTable(this.resultTable);
                subSqlBuilder.setUseDNASql(this.useDNASql);
                subSqlBuilder.setRowFilterNode(this.rowFilterNode);
                subSqlBuilder.setColValueFilters(this.colValueFilters);
                subSqlBuilder.setForUpdateOnly(this.forUpdateOnly);
                subSqlBuilder.setRecKeys(this.recKeys);
                subSqlBuilder.setSqlSoftParse(this.sqlSoftParse);
                subSqlBuilder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
                builders.add(subSqlBuilder);
                this.queryRegion.getAllTableFields().remove(table);
            }
            tables.clear();
        }
        return builders;
    }

    public ArrayList<OrderByItem> getOrderItems(ArrayList<OrderByItem> orderByItems, QueryFields queryFields, QueryTable table, QueryContext context) {
        List<QueryField> orderFields;
        ArrayList<OrderByItem> subOrders = new ArrayList<OrderByItem>();
        if (orderByItems != null) {
            for (OrderByItem orderByItem : orderByItems) {
                if (orderByItem.field == null || queryFields.indexOfFieldName(orderByItem.field.getFieldName()) < 0) continue;
                subOrders.add(orderByItem);
            }
        }
        if (subOrders.size() <= 0 && this.queryRegion.isNeedOrderJoin() && (orderFields = this.getFloatOrderFields(context, table)).size() > 0) {
            for (QueryField orderField : orderFields) {
                OrderByItem orderByItem = new OrderByItem();
                orderByItem.field = orderField;
                subOrders.add(orderByItem);
            }
        }
        return subOrders;
    }

    private List<QueryField> getFloatOrderFields(QueryContext context, QueryTable table) {
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        try {
            DefinitionsCache cache = context.getExeContext().getCache();
            DataModelDefinitionsCache dataDefinitionsCache = cache.getDataModelDefinitionsCache();
            TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(table.getTableName());
            if (tableRunInfo != null) {
                List<ColumnModelDefine> dimFields = tableRunInfo.getDimFields();
                for (ColumnModelDefine fieldDefine : dimFields) {
                    if (fieldDefine.getCode().equals("BIZKEYORDER")) continue;
                    QueryField queryField = cache.extractQueryField(context.getExeContext(), fieldDefine, null, null);
                    queryFields.add(queryField);
                }
                if (tableRunInfo.getOrderField() != null) {
                    QueryField queryField = cache.extractQueryField(context.getExeContext(), tableRunInfo.getOrderField(), null, null);
                    queryFields.add(queryField);
                }
                return queryFields;
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return queryFields;
        }
    }

    public List<QuerySqlBuilder> divideLeftJoins(QueryContext context) throws ParseException {
        this.orderByItems = null;
        this.setUseDefaultOrderBy(false);
        return this.dividTables(this.leftJoinTables, context);
    }

    private void adjustJoinTables(QueryContext context) throws ParseException {
        if (this.primaryTable != null) {
            this.fullJoinTables.add(this.primaryTable);
        }
        if (this.fullJoinTables.size() <= 1) {
            this.fullJoinTables.remove(this.primaryTable);
            return;
        }
        ArrayList<QueryTable> leftTables = new ArrayList<QueryTable>();
        DataModelDefinitionsCache dataDefinitionsCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        for (int i = 0; i < this.fullJoinTables.size(); ++i) {
            QueryTable outerTable = this.fullJoinTables.get(i);
            TableModelRunInfo outerTableInfo = dataDefinitionsCache.getTableInfo(outerTable.getTableName());
            ColumnModelDefine outerField = outerTableInfo.getDimFields().get(0);
            if (outerField.getReferColumnID() == null) continue;
            for (int j = 0; j < this.fullJoinTables.size(); ++j) {
                QueryTable innerTable;
                TableModelRunInfo innerTableInfo;
                TableModelDefine innerTableDefine;
                List<String> bizKeyFields;
                boolean isRefField;
                if (i == j || !(isRefField = this.checkIfRefField(dataDefinitionsCache, bizKeyFields = Arrays.asList((innerTableDefine = (innerTableInfo = dataDefinitionsCache.getTableInfo((innerTable = this.fullJoinTables.get(j)).getTableName())).getTableModelDefine()).getBizKeys().split(";")), outerField.getReferColumnID()))) continue;
                leftTables.add(innerTable);
            }
        }
        for (QueryTable queryTable : leftTables) {
            this.leftJoinTables.add(queryTable);
            this.fullJoinTables.remove(queryTable);
        }
        if (this.fullJoinTables.size() >= 1 && !this.fullJoinTables.contains(this.primaryTable)) {
            this.primaryTable = this.fullJoinTables.get(0);
        }
        this.fullJoinTables.remove(this.primaryTable);
    }

    private boolean checkIfRefField(DataModelDefinitionsCache dataDefinitionsCache, List<String> bizKeyFieldsID, String referFieldKey) {
        if (bizKeyFieldsID.contains(referFieldKey)) {
            return true;
        }
        ColumnModelDefine refField = dataDefinitionsCache.findField(referFieldKey);
        if (refField.getReferColumnID() != null) {
            return this.checkIfRefField(dataDefinitionsCache, bizKeyFieldsID, refField.getReferColumnID());
        }
        return false;
    }

    private void buildLookupItems(QueryContext context) throws ParseException, SQLException {
        List<LookupItem> lookupItems = this.queryRegion.getLookupItems();
        if (lookupItems == null || lookupItems.size() <= 0) {
            return;
        }
        for (LookupItem lookupItem : lookupItems) {
            String alias;
            QueryField keyField = lookupItem.getKeyField();
            QueryField valueField = lookupItem.getValueField();
            QueryTable valueTable = valueField.getTable();
            ColumnModelDefine joinField = this.getJoinField(context, keyField.getUID(), valueTable.getTableName());
            if (joinField == null) continue;
            QueryTable keyTable = lookupItem.getKeyField().getTable();
            String lookupKey = keyField.getUID().concat("_").concat(joinField.getID());
            if (!this.lookupAliases.containsKey(lookupKey)) {
                TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(valueTable.getTableName());
                alias = this.getTableAlias(context, valueTable);
                this.lookupAliases.put(lookupKey, alias);
                this.fromJoinsTables.append(" left join ");
                this.fromJoinsTables.append(this.getTableName(context, valueTable));
                if (this.useDNASql) {
                    this.fromJoinsTables.append(" AS ");
                } else {
                    this.fromJoinsTables.append(" ");
                }
                this.fromJoinsTables.append(alias);
                this.fromJoinsTables.append(" on ");
                this.fromJoinsTables.append(alias).append(".").append(joinField.getName());
                this.fromJoinsTables.append("=");
                this.fromJoinsTables.append(this.getTableAlias(context, keyTable)).append(".").append(keyField.getFieldName());
                this.appendDimensionFilterByTable(context, alias, valueTable, tableInfo, this.fromJoinsTables, true);
                this.appendVersionFilter(context, alias, valueTable, tableInfo, this.fromJoinsTables, true);
            } else {
                alias = this.lookupAliases.get(lookupKey);
            }
            if (this.getDataReader(context).findIndex(valueField) >= 0) continue;
            String gather = this.getFieldGatherSql(valueField.getUID(), valueField.getDataType());
            if (gather != null) {
                this.addGatherField(gather, alias, valueField.getFieldName(), valueField.getDataType());
            } else {
                this.selectFields.append(alias).append(".").append(valueField.getFieldName());
            }
            this.selectFields.append(" as ").append("lf_").append(this.fieldIndex).append(",");
            this.getDataReader(context).putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), valueField, this.fieldIndex);
            ++this.fieldIndex;
        }
    }

    private void addGatherField(String gather, String alias, String fieldName, int dataType) {
        if (gather.startsWith("none")) {
            if (dataType == 37 || dataType == 36 || dataType == 35 || dataType == 34) {
                this.selectFields.append(" null ");
            } else {
                this.selectFields.append("min(");
                this.selectFields.append(alias).append(".").append(fieldName);
                this.selectFields.append(")");
            }
        } else {
            this.selectFields.append(gather);
            this.selectFields.append(alias).append(".").append(fieldName);
            this.selectFields.append(")");
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
        if (referField.getName().equals(tableName)) {
            return referField;
        }
        return this.getJoinField(context, referFieldId, tableName);
    }

    protected void buildOrderBy(QueryContext context) throws Exception {
        List<ColumnModelDefine> dimFields;
        if (this.orderByItems != null && !this.orderByItems.isEmpty()) {
            for (OrderByItem orderByItem : this.orderByItems) {
                if (orderByItem.field != null) {
                    if (this.fieldAliases.containsKey(orderByItem.field.getUID())) {
                        this.orderByClause.append(this.fieldAliases.get(orderByItem.field.getUID()));
                    } else {
                        QueryTable orderTable = orderByItem.field.getTable();
                        Object tableAlias = context.getQueryTableAliaMap().get(orderTable);
                        if (tableAlias == null && this.primaryTable != null && this.primaryTable.getTableName().equals(orderTable.getTableName())) {
                            tableAlias = context.getQueryTableAliaMap().get(this.primaryTable);
                        }
                        if (StringUtils.isEmpty((String)tableAlias)) continue;
                        String gather = this.getFieldGatherSql(orderByItem.field.getUID(), orderByItem.field.getDataType());
                        if (gather != null) {
                            this.addGatherField(gather, (String)tableAlias, orderByItem.field.getFieldName(), orderByItem.field.getDataType());
                        } else {
                            this.selectFields.append((String)tableAlias).append(".").append(orderByItem.field.getFieldName());
                        }
                        String fieldAlias = String.format("%s%s", TABLE_ALIAS_PREFIX, this.fieldIndex);
                        this.selectFields.append(" as ").append(fieldAlias).append(",");
                        this.fieldAliases.put(orderByItem.field.getUID(), fieldAlias);
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
        DataModelDefinitionsCache dataDefinitionsCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(this.primaryTableName);
        if (this.orderByClause.length() == 0 && this.useDefaultOrderBy && !this.ignoreDefaultOrderBy && (dimFields = tableRunInfo.getDimFields()) != null && dimFields.size() > 0) {
            String primaryTableAlias = this.getTableAlias(context, this.primaryTable);
            for (ColumnModelDefine dimField : dimFields) {
                if (tableRunInfo.isBizOrderField(dimField.getName())) continue;
                if (this.fieldAliases.containsKey(dimField.getID())) {
                    this.orderByClause.append(this.fieldAliases.get(dimField.getID()));
                } else {
                    this.orderByClause.append(primaryTableAlias).append(".").append(dimField.getName());
                }
                this.orderByClause.append(",");
            }
            this.orderByClause.setLength(this.orderByClause.length() - 1);
        }
        if (tableRunInfo.getOrderField() != null && !this.ignoreDefaultOrderBy) {
            if (this.orderByClause.length() > 0) {
                this.orderByClause.append(",");
            }
            this.orderByClause.append(this.fieldAliases.get(tableRunInfo.getOrderField().getID()));
        }
        if (tableRunInfo.getBizOrderField() != null && !this.ignoreDefaultOrderBy) {
            if (this.orderByClause.length() > 0) {
                this.orderByClause.append(",");
            }
            this.orderByClause.append(this.fieldAliases.get(tableRunInfo.getBizOrderField().getID()));
        }
    }

    protected void buildSelectSql(QueryContext context, StringBuilder sqlBuilder) throws Exception {
        if (this.selectFields.charAt(this.selectFields.length() - 1) == ',') {
            this.selectFields.setLength(this.selectFields.length() - 1);
        }
        if (this.useDNASql) {
            sqlBuilder.append(" define query queryData()\n begin \n");
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
        if (this.useDNASql) {
            sqlBuilder.append("\nend");
        }
    }

    public void setRowFilterNode(IASTNode rowFilterNode) {
        this.rowFilterNode = rowFilterNode;
    }

    private void appendJoinTables(QueryContext context) throws SQLException, ParseException, InterpretException {
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

    public void setLoopDimensions(QueryContext context, DimensionSet loopDimensions) {
        IDataModelLinkFinder dataLinkFinder;
        this.loopDimensions = new DimensionSet(loopDimensions);
        ExecutorContext exeContext = context.getExeContext();
        String linkAlias = context.getTableLinkAliaMap().get(this.primaryTable);
        IDataModelLinkFinder iDataModelLinkFinder = dataLinkFinder = exeContext.getEnv() == null ? null : exeContext.getEnv().getDataModelLinkFinder();
        if (linkAlias != null && dataLinkFinder != null && exeContext.getEnv() != null) {
            String unitDimesion = exeContext.getEnv().getUnitDimesion(exeContext);
            if (!loopDimensions.contains(unitDimesion)) {
                String relatedUnitDimName = dataLinkFinder.getRelatedUnitDimName(context.getExeContext(), linkAlias, unitDimesion);
                if (loopDimensions.contains(relatedUnitDimName)) {
                    this.unitDimensionMap.put(relatedUnitDimName, unitDimesion);
                }
            } else {
                this.unitDimensionMap.put(unitDimesion, unitDimesion);
            }
        }
        for (int i = this.loopDimensions.size() - 1; i >= 0; --i) {
            String dimension = this.loopDimensions.get(i);
            if (this.primaryTable.getTableDimensions().contains(dimension)) continue;
            this.loopDimensions.removeAt(i);
        }
    }

    public int getRowKeyFieldStartIndex() {
        return this.rowKeyFieldStartIndex;
    }

    private void joinTable(QueryContext context, QueryTable table, String type) throws SQLException, ParseException {
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
                TempAssistantTable tempAssistantTable = context.getTempAssistantTables().get(dimension);
                PeriodModifier periodModifier = table.getPeriodModifier();
                if (periodModifier != null && dimension.equals("DATATIME")) {
                    PeriodWrapper oldPeriodWrapper = context.getPeriodWrapper();
                    PeriodWrapper periodWrapper = new PeriodWrapper(oldPeriodWrapper);
                    IPeriodAdapter periodAdapter = exeContext.getPeriodAdapter();
                    periodAdapter.modify(periodWrapper, periodModifier);
                    String dimValue2 = periodWrapper.toString();
                    String dimensionFieldName2 = dimension;
                    dimensionFieldName2 = dimensionField.getName();
                    int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                    if (needAnd) {
                        this.fromJoinsTables.append(" and ");
                    }
                    this.fromJoinsTables.append(tableAlias).append(".");
                    this.fromJoinsTables.append(dimensionFieldName2);
                    this.fromJoinsTables.append("=");
                    this.appendConstValue(this.fromJoinsTables, dimValue2, dimDataType);
                    needAnd = true;
                    continue;
                }
                if (table.getDimensionRestriction() != null && table.getDimensionRestriction().hasValue(dimension)) {
                    dimValue = table.getDimensionRestriction().getValue(dimension);
                    dimensionFieldName = dimension;
                    dimensionFieldName = dimensionField.getName();
                    int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                    if (needAnd) {
                        this.fromJoinsTables.append(" and ");
                    }
                    this.fromJoinsTables.append(tableAlias).append(".");
                    this.fromJoinsTables.append(dimensionFieldName);
                    this.fromJoinsTables.append("=");
                    this.appendConstValue(this.fromJoinsTables, dimValue, dimDataType);
                    needAnd = true;
                    continue;
                }
                if (this.primaryTable.getTableDimensions().contains(dimension)) {
                    if (needAnd) {
                        this.fromJoinsTables.append(" and ");
                    }
                    this.fromJoinsTables.append(primaryTableAlias).append(".").append(DataEngineUtil.getDimensionFieldName(primaryTableInfo, dimension));
                    this.fromJoinsTables.append("=");
                    this.fromJoinsTables.append(tableAlias).append(".").append(DataEngineUtil.getDimensionFieldName(tableInfo, dimension));
                    needAnd = true;
                    continue;
                }
                if (!context.getMasterKeys().hasValue(dimension)) continue;
                dimValue = context.getMasterKeys().getValue(dimension);
                dimensionFieldName = dimension;
                dimensionFieldName = dimensionField.getName();
                int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                this.appendToCondition(context, tempAssistantTable, this.fromJoinsTables, tableAlias, dimensionFieldName, dimValue, dimDataType, needAnd);
                needAnd = true;
            }
            needAnd = this.appendJoinVersionCondition(tableInfo, context.getMasterKeys(), context, tableAlias, needAnd);
        }
        if (!needAnd) {
            this.fromJoinsTables.append(" 1=1 ");
            needAnd = true;
        }
        needAnd = this.appendDimensionFilterByTable(context, tableAlias, table, tableInfo, this.fromJoinsTables, needAnd);
        needAnd = this.appendVersionFilter(context, tableAlias, table, tableInfo, this.fromJoinsTables, needAnd);
    }

    private boolean appendJoinVersionCondition(TableModelRunInfo tableInfo, DimensionValueSet masterKeys, QueryContext context, String tableAlias, boolean needAnd) {
        String keyName = "VERSIONID";
        Object keyValue = masterKeys.getValue(keyName);
        ColumnModelDefine versionField = tableInfo.getDimensionField(keyName);
        if (tableInfo.getVersionField() != null && versionField == null) {
            if (keyValue == null) {
                keyValue = "00000000-0000-0000-0000-000000000000";
            }
            ColumnModelDefine dimensionField = tableInfo.getVersionField();
            int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
            TempAssistantTable tempAssistantTable = context.getTempAssistantTable(keyName, keyValue, dimDataType);
            this.appendToCondition(context, tempAssistantTable, this.fromJoinsTables, tableAlias, dimensionField.getName(), keyValue, dimDataType, needAnd);
            needAnd = true;
        }
        return needAnd;
    }

    private void leftJoinLjTable(QueryContext context, QueryTable table) {
        this.fromJoinsTables.append(" left join ");
        this.fromJoinsTables.append(" (select ");
        for (QueryField queryField : this.queryRegion.getTableFields(table)) {
            this.fromJoinsTables.append("sum(").append(table.getTableName()).append(".").append(queryField.getFieldName()).append(") as ").append(queryField.getFieldName());
            this.fromJoinsTables.append(",");
        }
        this.fromJoinsTables.setLength(this.fromJoinsTables.length() - 1);
        this.fromJoinsTables.append(" from ");
        this.fromJoinsTables.append(" ").append(this.getTableName(context, table));
        if (this.useDNASql) {
            this.fromJoinsTables.append(" AS ");
        } else {
            this.fromJoinsTables.append(" ");
        }
        this.fromJoinsTables.append(table.getTableName());
        this.fromJoinsTables.append(" where ");
        this.fromJoinsTables.append(") ");
        if (this.useDNASql) {
            this.fromJoinsTables.append(" AS ");
        } else {
            this.fromJoinsTables.append(" ");
        }
        this.fromJoinsTables.append(this.getTableAlias(context, table));
        this.fromJoinsTables.append(" on ");
    }

    private void buildSqlByMainTable(QueryContext context) throws SQLException, ParseException {
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        this.appendQueryFields(context, tableInfo);
        this.appendRowKeyFields(context, tableInfo);
        this.appendQueryTable(context, this.fromJoinsTables, this.primaryTable);
        this.buildWhereConditionByTable(context, tableInfo, this.primaryTable);
    }

    protected boolean isGroupingQuery() {
        return false;
    }

    private void appendRowKeyFields(QueryContext qContext, TableModelRunInfo tableInfo) {
        ColumnModelDefine recField;
        ColumnModelDefine bizOrderField;
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
            String fieldAlias = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.dimIndexes.put(keyField.getID(), this.fieldIndex);
            this.fieldAliases.putIfAbsent(keyField.getID(), fieldAlias);
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
            String fieldAlias = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.fieldAliases.putIfAbsent(inputOrderField.getID(), fieldAlias);
            ++this.fieldIndex;
        }
        if ((bizOrderField = tableInfo.getBizOrderField()) != null) {
            String fieldName = bizOrderField.getName();
            if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(fieldName);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(fieldName);
            }
            String fieldAlias = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.fieldAliases.putIfAbsent(bizOrderField.getID(), fieldAlias);
            ++this.fieldIndex;
        }
        if ((recField = tableInfo.getRecField()) != null) {
            ReadonlyTableImpl tableImpl;
            int recType = DataTypesConvert.fieldTypeToDataType(tableInfo.getRecField().getColumnType());
            if (this.dimIndexes.containsKey(recField.getID())) {
                tableImpl = this.getResultTable();
                if (tableImpl != null) {
                    tableImpl.setRecKeyIndex(this.dimIndexes.get(recField.getID()));
                    tableImpl.setRecType(recType);
                }
                return;
            }
            if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(recField.getName());
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(recField.getName());
            }
            this.selectFields.append(" as ").append(TABLE_ALIAS_PREFIX).append(this.fieldIndex).append(",");
            tableImpl = this.getResultTable();
            if (tableImpl != null) {
                tableImpl.setRecKeyIndex(this.fieldIndex);
                tableImpl.setRecType(recType);
            }
            ++this.fieldIndex;
        }
    }

    private void appendQueryFields(QueryContext context, TableModelRunInfo tableInfo) throws ParseException {
        String prefix = TABLE_ALIAS_PREFIX;
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            String tableAlias = this.getTableAlias(context, table);
            for (QueryField queryField : this.queryRegion.getTableFields(table)) {
                String gather = this.getFieldGatherSql(queryField.getUID(), queryField.getDataType());
                if (gather != null) {
                    this.addGatherField(gather, tableAlias, queryField.getFieldName(), queryField.getDataType());
                } else if (table.getIsLj()) {
                    this.selectFields.append("sum(");
                    this.selectFields.append(tableAlias).append(".").append(queryField.getFieldName());
                    this.selectFields.append(")");
                } else {
                    this.selectFields.append(tableAlias).append(".").append(queryField.getFieldName());
                }
                String fieldAlias = TABLE_ALIAS_PREFIX.concat(String.valueOf(this.fieldIndex));
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

    protected IQueryFieldDataReader getDataReader(QueryContext queryContext) {
        IQueryFieldDataReader dataReader = queryContext.getDataReader();
        if (dataReader == null) {
            dataReader = this.queryRegion.getType() == 0 ? new DataSetReader(queryContext) : new MemoryDataSetReader(queryContext);
            queryContext.setDataReader(dataReader);
        }
        return dataReader;
    }

    protected String getFieldGatherSql(String uid, int dataType) {
        return null;
    }

    protected void buildGroupBy(QueryContext qContext) throws Exception {
    }

    private void buildWhereConditionByTable(QueryContext qContext, TableModelRunInfo tableInfo, QueryTable queryTable) throws SQLException, ParseException {
        Object keyValue;
        String keyName;
        int i;
        String tableAlias = this.getTableAlias(qContext, queryTable);
        DimensionValueSet masterkeys = new DimensionValueSet(qContext.getMasterKeys());
        this.initDefaultVersionKey(masterkeys, tableInfo);
        DimensionValueSet tableRestriction = queryTable.getDimensionRestriction();
        ExecutorContext exeContext = qContext.getExeContext();
        for (i = 0; i < masterkeys.size(); ++i) {
            keyName = masterkeys.getName(i);
            keyValue = masterkeys.getValue(i);
            if (tableRestriction != null && tableRestriction.hasValue(keyName)) continue;
            String dimensionFieldName = keyName;
            ColumnModelDefine dimensionField = tableInfo.getDimensionField(keyName);
            String linkAlias = qContext.getTableLinkAliaMap().get(queryTable);
            if (linkAlias != null) {
                String relatedUnitDimName = null;
                if (dimensionField == null) {
                    IDataModelLinkFinder dataLinkFinder;
                    if (exeContext.getEnv() != null && keyName.equals(exeContext.getEnv().getUnitDimesion(exeContext)) && (relatedUnitDimName = (dataLinkFinder = exeContext.getEnv().getDataModelLinkFinder()).getRelatedUnitDimName(exeContext, linkAlias, keyName)) != null) {
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
            int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
            TempAssistantTable tempAssistantTable = qContext.getTempAssistantTable(keyName, keyValue, dimDataType);
            if (StringUtils.isEmpty((String)dimensionFieldName) || this.dimensionConditions.contains(keyName)) continue;
            this.dimensionConditions.add(keyName);
            if (keyName.equals("DATATIME")) {
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
                if (tempAssistantTable != null && DataEngineUtil.needJoinTempTable(this.queryParam.getDatabase())) {
                    this.fromJoinsTables.append(tempAssistantTable.getJoinSql(tableAlias + "." + dimensionFieldName));
                    continue;
                }
                this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, dimensionFieldName, keyValue, dimDataType, this.whereNeedAnd);
            }
            this.whereNeedAnd = true;
        }
        this.appendDefaultVersionCondition(tableInfo, masterkeys, qContext, tableAlias);
        if (this.recKeys != null && this.recKeys.size() > 0 && tableInfo.getRecField() != null) {
            this.whereNeedAnd = false;
            if (this.whereCondition.length() > 0) {
                this.whereCondition.insert(0, "(").append(")").append(" or ");
            }
            TempAssistantTable tempAssistantTable = qContext.getTempAssistantTables().get(tableInfo.getRecField().getName());
            this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, this.getTableAlias(qContext, this.primaryTable), tableInfo.getRecField().getName(), this.recKeys, DataTypesConvert.fieldTypeToDataType(tableInfo.getRecField().getColumnType()), this.whereNeedAnd);
            this.whereCondition.insert(0, "(").append(")");
            this.whereNeedAnd = true;
        }
        if (tableRestriction != null) {
            for (i = 0; i < tableRestriction.size(); ++i) {
                keyName = tableRestriction.getName(i);
                keyValue = tableRestriction.getValue(i);
                TempAssistantTable tempAssistantTable = qContext.getTempAssistantTables().get(keyName);
                if (keyValue == null) continue;
                String dimensionFieldName = keyName;
                ColumnModelDefine dimensionField = tableInfo.getDimensionField(keyName);
                if (dimensionField != null) {
                    dimensionFieldName = dimensionField.getName();
                }
                int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                if (StringUtils.isEmpty((String)dimensionFieldName)) continue;
                this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, dimensionFieldName, keyValue, dimDataType, this.whereNeedAnd);
                this.whereNeedAnd = true;
            }
        }
        this.whereNeedAnd = this.appendColFilter(qContext, exeContext.getCache().getDataModelDefinitionsCache(), this.whereCondition, this.whereNeedAnd);
        this.whereNeedAnd = this.appendVersionFilter(qContext, tableAlias, queryTable, tableInfo, this.whereCondition, this.whereNeedAnd);
    }

    private void appendDefaultVersionCondition(TableModelRunInfo tableInfo, DimensionValueSet masterkeys, QueryContext qContext, String tableAlias) {
        String keyName = "VERSIONID";
        Object keyValue = masterkeys.getValue(keyName);
        ColumnModelDefine versionField = tableInfo.getDimensionField(keyName);
        if (tableInfo.getVersionField() != null && versionField == null) {
            if (keyValue == null) {
                keyValue = "00000000-0000-0000-0000-000000000000";
            }
            ColumnModelDefine dimensionField = tableInfo.getVersionField();
            String dimensionFieldName = dimensionField.getName();
            int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
            TempAssistantTable tempAssistantTable = qContext.getTempAssistantTable(keyName, keyValue, dimDataType);
            this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, dimensionFieldName, keyValue, dimDataType, this.whereNeedAnd);
            this.whereNeedAnd = true;
        }
    }

    private Object processLinkedUnit(QueryContext qContext, QueryTable queryTable, ExecutorContext exeContext, String keyName, Object keyValue) {
        IDataModelLinkFinder dataLinkFinder;
        String linkAlias = qContext.getTableLinkAliaMap().get(queryTable);
        IDataModelLinkFinder iDataModelLinkFinder = dataLinkFinder = exeContext.getEnv() == null ? null : exeContext.getEnv().getDataModelLinkFinder();
        if (linkAlias != null && dataLinkFinder != null) {
            if (keyValue instanceof List) {
                ArrayList<Object> unitKeys = new ArrayList<Object>();
                List valueList = keyValue;
                Map<Object, List<Object>> keyValueMap = dataLinkFinder.findRelatedUnitKeyMap(exeContext, linkAlias, keyName, valueList);
                if (keyValueMap != null) {
                    for (Object v : valueList) {
                        List<Object> mapedValues = keyValueMap.get(v);
                        if (mapedValues != null && mapedValues.size() > 0) {
                            for (Object mv : mapedValues) {
                                this.unitKeyMap.put(mv.toString(), v);
                                unitKeys.add(mv);
                            }
                            continue;
                        }
                        unitKeys.add(v);
                        qContext.getUnKnownLinkUnitSet(linkAlias).add(v.toString());
                    }
                    keyValue = unitKeys;
                }
            } else {
                List<Object> mapedKeyValues = dataLinkFinder.findRelatedUnitKey(exeContext, linkAlias, keyName, keyValue);
                if (mapedKeyValues != null && mapedKeyValues.size() > 0) {
                    for (Object mv : mapedKeyValues) {
                        this.unitKeyMap.put(mv.toString(), mv);
                    }
                    keyValue = mapedKeyValues;
                } else {
                    qContext.getUnKnownLinkUnitSet(linkAlias).add(keyValue.toString());
                }
            }
        }
        return keyValue;
    }

    private void initDefaultVersionKey(DimensionValueSet masterkeys, TableModelRunInfo tableRunInfo) {
        List<Object> versionObjects;
        if (tableRunInfo.getVersionField() == null || this.ignoreDataVersion) {
            return;
        }
        if (masterkeys.hasValue("VERSIONID")) {
            Object versionValue = masterkeys.getValue("VERSIONID");
            if (tableRunInfo.getVersionField().getColumnType() == ColumnModelType.UUID) {
                masterkeys.setValue("VERSIONID", UUID.fromString(versionValue.toString()));
            } else {
                masterkeys.setValue("VERSIONID", versionValue.toString());
            }
            return;
        }
        List<Object> list = versionObjects = this.colValueFilters != null ? this.colValueFilters.getDimensionColFilterValues("VERSIONID") : null;
        if (versionObjects != null && versionObjects.size() > 0) {
            return;
        }
        if (tableRunInfo.getVersionField().getColumnType() == ColumnModelType.UUID) {
            masterkeys.setValue("VERSIONID", UUID.fromString("00000000-0000-0000-0000-000000000000"));
        } else {
            masterkeys.setValue("VERSIONID", "00000000-0000-0000-0000-000000000000");
        }
    }

    protected void buildWhereCondition(QueryContext qContext) throws InterpretException {
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

    private boolean appendVersionFilter(QueryContext qContext, String tableAlias, QueryTable queryTable, TableModelRunInfo tableRunInfo, StringBuilder condition, boolean needAnd) throws SQLException {
        if (tableRunInfo.getTableModelDefine().getDictType() != TableDictType.ZIPPER) {
            return needAnd;
        }
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
                String aliasPrefix = StringUtils.isEmpty((String)tableAlias) ? "" : String.format("%s.", tableAlias);
                condition.append(aliasPrefix).append("INVALIDTIME").append(">").append(aliasPrefix).append("VALIDTIME");
                condition.append(" and ");
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
                condition.append(" and ");
                String aliasPrefix = StringUtils.isEmpty((String)tableAlias) ? "" : String.format("%s.", tableAlias);
                condition.append(aliasPrefix).append("INVALIDTIME").append(">").append(aliasPrefix).append("VALIDTIME");
                condition.append(" and ");
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
            condition.append(aliasValue).append(".").append("INVALIDTIME").append(">").append(aliasValue).append("VALIDTIME");
        } else {
            condition.append("INVALIDTIME").append(">").append("VALIDTIME");
        }
        condition.append(" and ");
        if (this.useDNASql) {
            condition.append(aliasValue).append(".").append("VALIDTIME");
        } else {
            condition.append("VALIDTIME");
        }
        condition.append('>');
        condition.append(tableAlias).append(".").append("VALIDTIME");
        condition.append(") ");
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
        }
        return sql.toString();
    }

    protected String getTableName(QueryContext qContext, QueryTable queryTable) {
        SplitTableHelper splitTableHelper = this.queryParam.getSplitTableHelper();
        if (splitTableHelper != null) {
            return splitTableHelper.getCurrentSplitTableName(qContext.getExeContext(), queryTable.getTableName());
        }
        if (this.designTimeData) {
            return String.format("%s%s", "DES_", queryTable.getTableName());
        }
        return queryTable.getTableName();
    }

    private boolean appendColFilter(QueryContext qContext, DataModelDefinitionsCache cache, StringBuilder condition, boolean needAnd) {
        String tableAlias;
        TableModelRunInfo tableRunInfo = cache.getTableInfo(this.primaryTable.getTableName());
        needAnd = this.appendDimensionFilterByTable(qContext, this.getTableAlias(qContext, this.primaryTable), this.primaryTable, tableRunInfo, condition, needAnd);
        String primaryTableAlias = this.getTableAlias(qContext, this.primaryTable);
        needAnd = this.appendColFilterByTable(qContext, primaryTableAlias, this.primaryTable, tableRunInfo, condition, needAnd);
        for (QueryTable table : this.leftJoinTables) {
            tableRunInfo = cache.getTableInfo(table.getTableName());
            tableAlias = this.getTableAlias(qContext, table);
            needAnd = this.appendColFilterByTable(qContext, tableAlias, table, tableRunInfo, condition, needAnd);
        }
        for (QueryTable table : this.fullJoinTables) {
            tableRunInfo = cache.getTableInfo(table.getTableName());
            tableAlias = this.getTableAlias(qContext, table);
            needAnd = this.appendColFilterByTable(qContext, tableAlias, table, tableRunInfo, condition, needAnd);
        }
        return needAnd;
    }

    protected boolean appendDimensionFilterByTable(QueryContext qContext, String tableAlias, QueryTable table, TableModelRunInfo tableRunInfo, StringBuilder condition, boolean needAnd) {
        if (this.colValueFilters != null && this.colValueFilters.dimensionFilterValues != null) {
            DimensionSet dimensionSet = table.getTableDimensions();
            for (int index = 0; index < dimensionSet.size(); ++index) {
                ColumnModelDefine fieldDefine;
                String dimName = dimensionSet.get(index);
                List<Object> dimValues = this.colValueFilters.getDimensionColFilterValues(dimName);
                if (dimValues == null || (fieldDefine = tableRunInfo.getDimensionField(dimName)) == null) continue;
                int dataType = DataTypesConvert.fieldTypeToDataType(fieldDefine.getColumnType());
                this.appendToCondition(qContext, null, condition, tableAlias, fieldDefine.getName(), dimValues, dataType, needAnd);
                needAnd = true;
            }
        }
        return needAnd;
    }

    protected boolean appendColFilterByTable(QueryContext queryContext, String tableAlias, QueryTable table, TableModelRunInfo tableRunInfo, StringBuilder condition, boolean needAnd) {
        if (this.colValueFilters != null) {
            QueryTableColFilterValues filterValues = this.colValueFilters.getSqlColFilterValues(table);
            if (filterValues == null) {
                return needAnd;
            }
            Map<String, TempAssistantTable> tempAssistantTableMap = queryContext.getTempAssistantTables();
            for (QueryField queryField : this.queryRegion.getTableFields(table)) {
                List<Object> valueList = filterValues.getColFilterValues(queryField);
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
                ArrayList<Object> notNullValueList = new ArrayList<Object>(valueList.size());
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
                TempAssistantTable tempAssistantTable = tempAssistantTableMap.get(queryField.getTableName().concat("_").concat(queryField.getFieldName()));
                if (hasNullValue) {
                    condition.append("(").append(fieldName).append(" is null ");
                    if (!notNullValueList.isEmpty()) {
                        condition.append(" or ");
                        this.appendTempTableCondition(queryContext, condition, queryField, valueList, fieldName, tempAssistantTable);
                    }
                    condition.append(")");
                    continue;
                }
                this.appendTempTableCondition(queryContext, condition, queryField, valueList, fieldName, tempAssistantTable);
            }
        }
        return needAnd;
    }

    private void appendTempTableCondition(QueryContext qContext, StringBuilder condition, QueryField queryField, List<Object> valueList, String fieldName, TempAssistantTable tempAssistantTable) {
        if (valueList.size() >= DataEngineUtil.getMaxInSize(qContext.getQueryParam().getDatabase())) {
            if (tempAssistantTable != null) {
                condition.append(" exists ").append(tempAssistantTable.getExistsSelectSql(fieldName));
            } else {
                this.printSplitedInSQL(qContext, condition, fieldName, valueList, queryField.getDataType());
            }
        } else {
            this.printInSQL(condition, fieldName, valueList, queryField.getDataType());
        }
    }

    private void printSplitedInSQL(QueryContext qContext, StringBuilder buffer, String fieldName, List<?> valueList, int dataType) {
        ArrayList subValues = new ArrayList();
        buffer.append('(');
        boolean started = false;
        for (Object val : valueList) {
            subValues.add(val);
            if (subValues.size() < DataEngineUtil.getMaxInSize(qContext.getQueryParam().getDatabase())) continue;
            if (started) {
                buffer.append(" OR ");
            } else {
                started = true;
            }
            this.printInSQL(buffer, fieldName, subValues, dataType);
            subValues.clear();
        }
        if (!subValues.isEmpty()) {
            if (started) {
                buffer.append(" OR ");
            } else {
                started = true;
            }
            this.printInSQL(buffer, fieldName, subValues, dataType);
            subValues.clear();
        }
        buffer.append(')');
    }

    private void printInSQL(StringBuilder buffer, String fieldName, List<?> valueList, int dataType) {
        buffer.append(fieldName);
        buffer.append(" in (");
        for (Object value : valueList) {
            this.appendValue(buffer, value, dataType);
            buffer.append(",");
        }
        buffer.setLength(buffer.length() - 1);
        buffer.append(")");
    }

    protected void appendToCondition(QueryContext qContext, TempAssistantTable tempAssistantTable, StringBuilder sql, String tableAlias, String name, Object value, int dataType, boolean needAnd) {
        if (needAnd) {
            sql.append(" and ");
        }
        String fieldName = tableAlias + "." + name;
        if (value instanceof List) {
            List valueList = (List)value;
            if (valueList.size() <= 0) {
                sql.append(" 1=0 ");
            } else if (valueList.size() >= DataEngineUtil.getMaxInSize(qContext.getQueryParam().getDatabase())) {
                if (tempAssistantTable != null) {
                    sql.append(" exists ").append(tempAssistantTable.getExistsSelectSql(fieldName));
                } else {
                    this.printSplitedInSQL(qContext, sql, fieldName, valueList, dataType);
                }
            } else {
                this.printInSQL(sql, fieldName, valueList, dataType);
            }
        } else {
            sql.append(fieldName);
            sql.append("=");
            this.appendValue(sql, value, dataType);
        }
    }

    protected void appendValue(StringBuilder sql, Object value, int dataType) {
        if (this.sqlSoftParse) {
            this.appendArgValue(sql, value, dataType);
        } else {
            this.appendConstValue(sql, value, dataType);
        }
    }

    private void appendArgValue(StringBuilder sql, Object value, int dataType) {
        Object argValue = value;
        if (dataType == 6) {
            argValue = value.toString();
        } else if (dataType == 33) {
            argValue = Convert.toUUID((Object)value);
        } else if (dataType == 5) {
            argValue = new Date(Convert.toDate((Object)value));
        } else if (dataType == 2) {
            argValue = new Timestamp(Convert.toDate((Object)value));
        } else if (dataType == 4) {
            argValue = Convert.toInt((Object)value);
        }
        sql.append("?");
        this.argValues.add(argValue);
    }

    private void appendConstValue(StringBuilder sql, Object value, int dataType) {
        if (dataType == 6) {
            sql.append("'").append(value).append("'");
        } else if (dataType == 33) {
            UUID uuid = Convert.toUUID((Object)value);
            sql.append(DataEngineUtil.buildGUIDValueSql(this.queryParam.getDatabase(), uuid));
        } else if (dataType == 5 || dataType == 2) {
            sql.append(DataEngineUtil.buildDateValueSql(this.queryParam.getDatabase(), value, this.queryParam.getConnection()));
        } else {
            sql.append(value);
        }
    }

    protected void appendQueryTable(QueryContext qContext, StringBuilder sql, QueryTable table) {
        sql.append(" ").append(this.getTableName(qContext, table));
        if (this.useDNASql) {
            sql.append(" AS ");
        } else {
            sql.append(" ");
        }
        sql.append(this.getTableAlias(qContext, table));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DataSet<QueryField> runQuery(QueryContext qContext) throws Exception {
        try {
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
            DataSet<QueryField> dataSet = this.queryByJDBC(qContext, this.sql);
            return dataSet;
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    private DataSet<QueryField> queryByJDBC(QueryContext qContext, String mainQuerySql) throws SQLException, DataSetException {
        Connection conn = this.queryParam.getConnection();
        Object[] args = this.argValues == null ? null : this.argValues.toArray();
        MemoryDataSet<QueryField> dataSet = DataEngineUtil.queryMemoryDataSet(conn, this.primaryTableName, mainQuerySql, args, qContext.getMonitor());
        this.getDataReader(qContext).setDataSet(dataSet);
        return dataSet;
    }

    public DimensionValueSet buildRowKeys(DimensionValueSet masterKeys, IQueryFieldDataReader reader) throws Exception {
        DimensionValueSet rowKeys = new DimensionValueSet();
        DimensionSet loopDimensions = this.getLoopDimensions();
        HashMap<String, ColumnModelDefine> dimensionFields = this.getDimensionFields();
        for (int i = 0; i < loopDimensions.size(); ++i) {
            Object value;
            String dimensionName = loopDimensions.get(i);
            Object masterValue = null;
            if (masterKeys.hasValue(dimensionName) && !((value = masterKeys.getValue(dimensionName)) instanceof List)) {
                masterValue = value;
            }
            if (masterValue == null) {
                masterValue = reader.readData(i + this.getRowKeyFieldStartIndex());
            }
            if (dimensionFields.containsKey(dimensionName)) {
                masterValue = DataEngineConsts.formatData(dimensionFields.get(dimensionName), masterValue, null);
            }
            if (masterValue == null) continue;
            rowKeys.setValue(dimensionName, masterValue);
        }
        return rowKeys;
    }

    protected String getTableAlias(QueryContext qContext, QueryTable table) {
        String tableAlias = qContext.getQueryTableAliaMap().get(table);
        if (tableAlias == null) {
            tableAlias = table.getAlias();
        }
        return tableAlias;
    }

    public void setMasterDimensions(DimensionSet masterDimensions) {
        this.masterDimensions = masterDimensions;
    }

    public void setUseDNASql(boolean useDNASql) {
        this.useDNASql = useDNASql;
    }

    public void setDesignTimeData(boolean designTimeData) {
        this.designTimeData = designTimeData;
    }

    public boolean getDesignTimeData() {
        return this.designTimeData;
    }

    public void setRecKeys(List<String> recKeys) {
        this.recKeys = recKeys;
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

    public QueryRegion getQueryRegion() {
        return this.queryRegion;
    }

    public boolean isNeedMemoryFilter() {
        return this.needMemoryFilter;
    }

    public boolean isNeedAdjustJoinTables() {
        return this.needAdjustJoinTables;
    }

    public void setNeedAdjustJoinTables(boolean needAdjustJoinTables) {
        this.needAdjustJoinTables = needAdjustJoinTables;
    }

    public void resetOrderItems(QueryContext queryContext) {
        if (this.primaryTable != null) {
            QueryFields queryFields = this.queryRegion.getTableFields(this.primaryTable);
            ArrayList<OrderByItem> subOrders = this.getOrderItems(this.orderByItems, queryFields, this.primaryTable, queryContext);
            this.setOrderByItems(subOrders);
        }
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

    public void setAllVersionQuery(boolean allVersionQuery) {
        this.allVersionQuery = allVersionQuery;
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

    public String getIndexSql(QueryContext queryContext, String mainQuerySql, DimensionValueSet rowKeys, DimensionValueSet masterKeys) throws ParseException, ExpressionException {
        StringBuilder rowIndexSql = new StringBuilder(mainQuerySql.length());
        StringBuilder leftSql = new StringBuilder();
        leftSql.append("select tt.").append(ROWINDEX);
        rowIndexSql.append(" from (");
        rowIndexSql.append("select rownum as ").append(ROWINDEX).append(", o.* from (");
        rowIndexSql.append(mainQuerySql);
        rowIndexSql.append(") o");
        rowIndexSql.append(") tt where ");
        TableModelRunInfo tableInfo = queryContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
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
            int dataType = DataTypesConvert.fieldTypeToDataType(dimField.getColumnType());
            if (!this.fieldAliases.containsKey(dimField.getID())) {
                hasError = true;
                break;
            }
            this.appendToCondition(queryContext, null, rowIndexSql, "tt", this.fieldAliases.get(dimField.getID()), rowKeys.getValue(dimName), dataType, addDot);
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

    public void setIgnoreDefaultOrderBy(boolean ignoreDefaultOrderBy) {
        this.ignoreDefaultOrderBy = ignoreDefaultOrderBy;
    }
}

