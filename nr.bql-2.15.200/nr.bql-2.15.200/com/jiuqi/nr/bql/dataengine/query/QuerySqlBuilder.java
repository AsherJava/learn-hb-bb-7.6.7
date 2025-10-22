/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.sql.SQLQueryContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$QueryTableType
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.common.SqlQueryHelper
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.IUnitLeafFinder
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutor
 *  com.jiuqi.np.dataengine.nrdb.query.DBQueryInfo
 *  com.jiuqi.np.dataengine.nrdb.query.NRDBQueryExecutor
 *  com.jiuqi.np.dataengine.query.DBResultSet
 *  com.jiuqi.np.dataengine.query.OrderByItem
 *  com.jiuqi.np.dataengine.query.PageSQLQueryExecutor
 *  com.jiuqi.np.dataengine.query.PageSQLQueryListener
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.query.QueryFilterValueClassify
 *  com.jiuqi.np.dataengine.query.QueryTableColFilterValues
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.dataengine.reader.QueryFieldInfo
 *  com.jiuqi.np.dataengine.setting.ISqlJoinProvider
 *  com.jiuqi.np.dataengine.setting.SqlJoinItem
 *  com.jiuqi.np.dataengine.setting.SqlJoinOneItem
 *  com.jiuqi.np.dataengine.util.FieldSqlConditionUtil
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.provider.DimensionColumn
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.query.datascheme.extend.DataQueryParam
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor
 *  com.jiuqi.nvwa.dataengine.util.DataEngineUtil
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.sql.SQLQueryContext;
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
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutor;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryInfo;
import com.jiuqi.np.dataengine.nrdb.query.NRDBQueryExecutor;
import com.jiuqi.np.dataengine.query.DBResultSet;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.PageSQLQueryExecutor;
import com.jiuqi.np.dataengine.query.PageSQLQueryListener;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QueryFilterValueClassify;
import com.jiuqi.np.dataengine.query.QueryTableColFilterValues;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.dataengine.setting.ISqlJoinProvider;
import com.jiuqi.np.dataengine.setting.SqlJoinItem;
import com.jiuqi.np.dataengine.setting.SqlJoinOneItem;
import com.jiuqi.np.dataengine.util.FieldSqlConditionUtil;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.provider.DimensionColumn;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bql.dataengine.impl.ReadonlyTableImpl;
import com.jiuqi.nr.bql.dataengine.query.DBQueryDataListener;
import com.jiuqi.nr.bql.dataengine.query.DataQueryBuilder;
import com.jiuqi.nr.bql.dataengine.query.DimQueryInfo;
import com.jiuqi.nr.bql.dataengine.query.MainSubQueryAdapter;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import com.jiuqi.nr.bql.dataengine.query.QueryRegion;
import com.jiuqi.nr.bql.dataengine.query.SubQueryBuilder;
import com.jiuqi.nr.bql.dataengine.reader.JdbcResultSetDataReader;
import com.jiuqi.nr.bql.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.nr.bql.datasource.MappingMainDimTable;
import com.jiuqi.nr.bql.intf.ISqlConditionProcesser;
import com.jiuqi.nr.bql.intf.TableRelation;
import com.jiuqi.nr.bql.util.TempAssistantTableUtils;
import com.jiuqi.nr.query.datascheme.extend.DataQueryParam;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuerySqlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QuerySqlBuilder.class);
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
    public List<QueryTable> fullJoinTables = new ArrayList<QueryTable>();
    protected List<QueryTable> emptyJoinTables = new ArrayList<QueryTable>();
    protected List<QueryField> allQueryFields = new ArrayList<QueryField>();
    protected int fieldIndex = 1;
    protected int rowKeyFieldStartIndex;
    protected HashMap<String, String> fieldAliases = new HashMap();
    protected HashMap<String, String> lookupAliases = new HashMap();
    protected HashMap<String, Integer> dimIndexes = new HashMap();
    protected QueryParam queryParam;
    protected HashSet<String> dimensionConditions = new HashSet();
    protected String sql = null;
    protected int memoryStartIndex = 0;
    protected boolean needMemoryFilter = false;
    protected boolean needAdjustJoinTables = false;
    protected boolean useDefaultOrderBy = true;
    protected boolean isUnionQuery;
    protected ISqlJoinProvider sqlJoinProvider;
    protected HashMap<String, Object> unitKeyMap = new HashMap();
    protected HashMap<String, String> unitDimensionMap = new HashMap();
    protected List<Object> argValues;
    protected List<Integer> argDataTypes;
    protected boolean sqlSoftParse = false;
    protected boolean inited = false;
    public static final String ROWINDEX = "rowindex";
    public static final String TABLE_ALIAS_PREFIX = "c_";
    private boolean ignoreDefaultOrderBy = false;
    private boolean doPage;
    private DimensionSet groupDims;
    protected ISqlConditionProcesser sqlConditionProcesser;
    private Boolean isMdInfoQuery;
    private IDataTableQueryExecutor queryExecuter;
    protected DBQueryExecutor dbQueryExecutor = null;
    private boolean filterBeforeJoin = false;
    private MainSubQueryAdapter mainSubQueryAdapter;

    public void doInit(QueryContext context) throws ParseException {
        if (this.inited) {
            return;
        }
        this.initPrimaryTable();
        TableRelation parentTableRelation = (TableRelation)context.getOption("parnetTableRelation");
        if (parentTableRelation != null) {
            this.filterBeforeJoin = true;
            this.mainSubQueryAdapter = new MainSubQueryAdapter(context, parentTableRelation, this.primaryTable);
        }
        int index = 0;
        Map queryTableAliaMap = context.getQueryTableAliaMap();
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            if (table.equals((Object)this.primaryTable)) {
                if (queryTableAliaMap.containsKey(table)) continue;
                queryTableAliaMap.put(this.primaryTable, this.createTableAlias(0));
                continue;
            }
            if (this.isParentTable(table)) continue;
            if (table.isDimensionTable() && this.isUnitTable(context.getExeContext(), table.getTableDimensions())) {
                this.filterBeforeJoin = true;
            }
            ++index;
            if (!queryTableAliaMap.containsKey(table)) {
                queryTableAliaMap.put(table, this.createTableAlias(index));
            }
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
            if (table.getTableDimensions().equals((Object)this.primaryTable.getTableDimensions()) && !this.isMdInfoTable(context, table.getTableName())) {
                this.fullJoinTables.add(table);
                continue;
            }
            this.leftJoinTables.add(table);
        }
        this.initDBQueryExecutor(context);
        this.inited = true;
    }

    private boolean isParentTable(QueryTable table) {
        return this.mainSubQueryAdapter != null && this.primaryTable.isCommonDataTable() && this.mainSubQueryAdapter.isParentTable(table);
    }

    public String buildSql(QueryContext context) throws Exception {
        this.doInit(context);
        if (this.primaryTable.isDimensionTable() && this.queryRegion.getAllTableFields().size() == 1) {
            return null;
        }
        return this.buildQuerySql(context);
    }

    public String buildQuerySql(QueryContext context) throws Exception {
        if (this.primaryTable.isDimensionTable() || this.queryExecuter != null && !this.primaryTable.getTableName().startsWith("NR_PERIOD")) {
            IQueryFieldDataReader dataReader = context.getDataReader();
            if (dataReader != null) {
                for (QueryField queryField : this.queryRegion.getTableFields(this.primaryTable)) {
                    dataReader.putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, this.fieldIndex);
                    ++this.fieldIndex;
                }
                this.rowKeyFieldStartIndex = this.fieldIndex;
            }
            return null;
        }
        if (this.dbQueryExecutor != null) {
            this.needMemoryFilter = this.dbQueryExecutor.buildQuery(context);
            return null;
        }
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
                this.argDataTypes = new ArrayList<Integer>();
            } else {
                this.argValues.clear();
                this.argDataTypes.clear();
            }
        }
        StringBuilder sqlBuilder = new StringBuilder();
        this.buildSqlByMainTable(context);
        boolean sqlSoftParse = this.sqlSoftParse;
        this.sqlSoftParse = false;
        this.appendJoinTables(context);
        this.sqlSoftParse = sqlSoftParse;
        this.appendRowFilter(context);
        this.buildGroupBy(context);
        this.buildOrderBy(context);
        this.buildSelectSql(context, sqlBuilder);
        this.sql = sqlBuilder.toString();
        return this.sql;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DataSet<QueryField> runQuery(QueryContext qContext) throws Exception {
        try {
            ArrayList<String> fieldKeys;
            IFmlExecEnvironment env = qContext.getExeContext().getEnv();
            if (env != null && env.JudgeZBAuth()) {
                fieldKeys = new ArrayList<String>();
                for (QueryFields queryFields : this.queryRegion.getAllTableFields().values()) {
                    for (QueryField field : queryFields) {
                        fieldKeys.add(field.getUID());
                    }
                }
                qContext.setAulthJuger(env.getZBAuthJudger(fieldKeys));
            }
            if (this.primaryTable.isDimensionTable() && this.queryRegion.getAllTableFields().size() == 1) {
                fieldKeys = this.getDimensionTableDataSet(qContext);
                return fieldKeys;
            }
            if (this.queryExecuter != null && !this.primaryTable.getTableName().startsWith("NR_PERIOD")) {
                fieldKeys = this.getMapedTableDataSet(qContext);
                return fieldKeys;
            }
            if (this.dbQueryExecutor != null) {
                MemoryDataSet result = new MemoryDataSet();
                this.dbQueryExecutor.runQuery(qContext, result);
                MemoryDataSet memoryDataSet = result;
                return memoryDataSet;
            }
            DataSet<QueryField> dataSet = this.queryByJDBC(qContext, this.sql);
            return dataSet;
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    public void queryToDataReader(QueryContext qContext, IQueryFieldDataReader reader, DataQueryBuilder dataQueryBuilder, int rowIndex, int rowSize) throws Exception {
        if (!this.doPage) {
            this.readDataNoPage(qContext, reader, dataQueryBuilder);
        } else if (this.needMemoryFilter) {
            this.readDataByMemoryPage(qContext, reader, dataQueryBuilder, rowIndex, rowSize);
        } else {
            this.readDataByDBPage(qContext, reader, dataQueryBuilder, rowIndex, rowSize);
        }
    }

    private void readDataNoPage(QueryContext qContext, IQueryFieldDataReader reader, DataQueryBuilder dataQueryBuilder) throws Exception {
        if (this.dbQueryExecutor != null) {
            this.dbQueryExecutor.readData(qContext, (IDataListener)new DBQueryDataListener(qContext, dataQueryBuilder, this));
            dataQueryBuilder.setTotalCount(dataQueryBuilder.getCurrentIndex());
        } else {
            Object[] args = this.getArgValues() == null ? null : this.getArgValues().toArray();
            try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                 DBResultSet dataSet = sqlHelper.queryDBResultSet(qContext.getQueryParam().getConnection(), this.sql, args, qContext.getMonitor());){
                reader.setDataSet((Object)dataSet);
                while (reader.next()) {
                    dataQueryBuilder.loadRowData(qContext, this, reader, -1, -1);
                }
                dataQueryBuilder.setTotalCount(dataQueryBuilder.getCurrentIndex());
            }
        }
    }

    private void readDataByDBPage(QueryContext qContext, IQueryFieldDataReader reader, DataQueryBuilder dataQueryBuilder, int rowIndex, int rowSize) throws Exception {
        if (this.dbQueryExecutor != null) {
            int totalCount = this.dbQueryExecutor.readData(qContext, (IDataListener)new DBQueryDataListener(qContext, dataQueryBuilder, this), rowIndex, rowSize);
            dataQueryBuilder.setTotalCount(totalCount);
        } else {
            int endRowIndex = rowSize + rowIndex;
            SQLQueryContext sqlQueryContext = new SQLQueryContext();
            PageSQLQueryListener listener = new PageSQLQueryListener(this.argValues, this.argDataTypes);
            sqlQueryContext.getListeners().add(listener);
            try (PageSQLQueryExecutor sqlExecutor = new PageSQLQueryExecutor(qContext.getQueryParam().getConnection(), qContext.getMonitor(), sqlQueryContext);){
                sqlExecutor.open(this.sql);
                try (ResultSet rs = sqlExecutor.executeQuery(rowIndex, endRowIndex);){
                    DBResultSet dataSet = new DBResultSet(rs);
                    reader.setDataSet((Object)dataSet);
                    while (reader.next()) {
                        dataQueryBuilder.loadRowData(qContext, this, reader, -1, -1);
                    }
                    int totalCount = sqlExecutor.getRecordCount();
                    dataQueryBuilder.setTotalCount(totalCount);
                }
            }
        }
    }

    private void readDataByMemoryPage(QueryContext qContext, IQueryFieldDataReader reader, DataQueryBuilder dataQueryBuilder, int rowIndex, int rowSize) throws Exception, SQLException {
        int endRowIndex = rowSize + rowIndex;
        if (this.dbQueryExecutor != null) {
            MemoryDataSet result = new MemoryDataSet();
            this.dbQueryExecutor.runQuery(qContext, result);
            reader.setDataSet((Object)result);
            while (reader.next()) {
                dataQueryBuilder.loadRowData(qContext, this, reader, rowIndex, endRowIndex);
            }
            dataQueryBuilder.setTotalCount(dataQueryBuilder.getCurrentIndex());
        } else {
            SQLQueryContext sqlQueryContext = new SQLQueryContext();
            PageSQLQueryListener listener = new PageSQLQueryListener(this.argValues, this.argDataTypes);
            sqlQueryContext.getListeners().add(listener);
            try (PageSQLQueryExecutor sqlExecutor = new PageSQLQueryExecutor(qContext.getQueryParam().getConnection(), qContext.getMonitor(), sqlQueryContext);){
                sqlExecutor.open(this.sql);
                try (ResultSet rs = sqlExecutor.executeQuery();){
                    DBResultSet dataSet = new DBResultSet(rs);
                    reader.setDataSet((Object)dataSet);
                    while (reader.next()) {
                        dataQueryBuilder.loadRowData(qContext, this, reader, rowIndex, endRowIndex);
                    }
                    int totalCount = sqlExecutor.getRecordCount();
                    dataQueryBuilder.setTotalCount(totalCount);
                }
            }
        }
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

    protected void initDBQueryExecutor(QueryContext context) throws ParseException {
        TableModelRunInfo tableInfo;
        DataModelDefinitionsCache dataModelDefinitionsCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        if (context.getQueryExecutorProvider() != null) {
            String tableDefineCode = dataModelDefinitionsCache.getTableDefineCode(this.primaryTable.getTableName());
            this.dbQueryExecutor = context.getQueryExecutorProvider().getDBQueryExecutor(tableDefineCode);
        } else if (context.isEnableNrdb() && this.primaryTable.isCommonDataTable() && (tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName())).getTableModelDefine().isSupportNrdb()) {
            this.dbQueryExecutor = new NRDBQueryExecutor();
        }
        if (this.dbQueryExecutor != null) {
            try {
                this.dbQueryExecutor.init(context, this.createDBQueryInfo());
            }
            catch (Exception e) {
                throw new ParseException(e.getMessage(), (Throwable)e);
            }
        }
    }

    protected DBQueryInfo createDBQueryInfo() {
        DBQueryInfo queryInfo = new DBQueryInfo();
        queryInfo.dimensionFields = this.dimensionFields;
        queryInfo.loopDimensions = this.loopDimensions;
        queryInfo.QueryFields = this.queryRegion.getTableFields(this.primaryTable);
        queryInfo.primaryTable = this.primaryTable;
        queryInfo.rowFilterNode = this.rowFilterNode;
        queryInfo.unitKeyMap = this.unitKeyMap;
        queryInfo.colValueFilters = this.colValueFilters;
        queryInfo.needMemoryFilter = this.needMemoryFilter;
        queryInfo.orderByItems = this.orderByItems;
        queryInfo.useDefaultOrderBy = this.useDefaultOrderBy;
        queryInfo.ignoreDefaultOrderBy = this.ignoreDefaultOrderBy;
        queryInfo.queryMode = true;
        return queryInfo;
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
            QueryTable periodQueryTable = null;
            for (QueryTable table : tables) {
                if (context.isQueryModule() && this.queryExecuter == null && !context.isRightJoinTable(table.getTableName()) && table.getTableName().startsWith("NR_PERIOD")) {
                    periodQueryTable = table;
                    continue;
                }
                QuerySqlBuilder subSqlBuilder = new QuerySqlBuilder();
                QueryRegion subRegion = new QueryRegion(this.queryRegion.getDimensions(), subSqlBuilder);
                QueryFields queryFields = this.queryRegion.getTableFields(table);
                subRegion.addQueryFields(queryFields);
                subSqlBuilder.setPrimaryTable(table);
                subSqlBuilder.setQueryParam(this.queryParam);
                subSqlBuilder.setResultTable(this.resultTable);
                if (!this.isNeedMemoryFilter()) {
                    subSqlBuilder.setRowFilterNode(this.rowFilterNode);
                }
                subSqlBuilder.setColValueFilters(this.colValueFilters);
                subSqlBuilder.setSqlSoftParse(this.sqlSoftParse);
                subSqlBuilder.setSqlConditionProcesser(this.sqlConditionProcesser);
                subSqlBuilder.setQueryExecuter(this.queryExecuter);
                subSqlBuilder.setQueryVersionDate(this.queryVersionDate);
                subSqlBuilder.setQueryVersionStartDate(this.queryVersionStartDate);
                builders.add(subSqlBuilder);
                this.queryRegion.getAllTableFields().remove(table);
            }
            tables.clear();
            if (periodQueryTable != null) {
                tables.add(periodQueryTable);
            }
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
        if (subOrders.size() <= 0 && (orderFields = this.getFloatOrderFields(context, table)).size() > 0) {
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
                List dimFields = tableRunInfo.getDimFields();
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
            logger.error(e.getMessage(), e);
            return queryFields;
        }
    }

    public List<QuerySqlBuilder> divideLeftJoins(QueryContext context) throws ParseException {
        this.orderByItems = null;
        this.setUseDefaultOrderBy(false);
        return this.dividTables(this.leftJoinTables, context);
    }

    protected void addGatherField(String gather, AggrType aggrType, String alias, String fieldName, int dataType) {
        if (aggrType == AggrType.NONE) {
            if (dataType == 37 || dataType == 36 || dataType == 35 || dataType == 34) {
                this.selectFields.append(" null ");
            } else {
                this.selectFields.append("min(");
                this.selectFields.append(alias).append(".").append(fieldName);
                this.selectFields.append(")");
            }
        } else if (aggrType == AggrType.COUNT) {
            this.selectFields.append(gather).append("1)");
        } else {
            this.selectFields.append(gather);
            this.selectFields.append(alias).append(".").append(fieldName);
            this.selectFields.append(")");
        }
    }

    protected AggrType getAggrType(String uid) {
        return null;
    }

    protected void buildOrderBy(QueryContext context) throws Exception {
        String orderAlias;
        List dimFields;
        if (this.orderByItems != null && !this.orderByItems.isEmpty()) {
            for (OrderByItem orderByItem : this.orderByItems) {
                if (orderByItem.field != null) {
                    if (this.fieldAliases.containsKey(orderByItem.field.getUID())) {
                        this.orderByClause.append(this.fieldAliases.get(orderByItem.field.getUID()));
                    } else {
                        QueryTable orderTable = orderByItem.field.getTable();
                        Object tableAlias = (String)context.getQueryTableAliaMap().get(orderTable);
                        if (tableAlias == null && this.primaryTable != null && this.primaryTable.getTableName().equals(orderTable.getTableName())) {
                            tableAlias = (String)context.getQueryTableAliaMap().get(this.primaryTable);
                        }
                        if (StringUtils.isEmpty((String)tableAlias)) continue;
                        AggrType aggrType = null;
                        String gather = this.getFieldGatherSql(orderByItem.field.getUID(), aggrType, orderByItem.field.getDataType());
                        if (gather != null) {
                            this.addGatherField(gather, aggrType, (String)tableAlias, orderByItem.field.getFieldName(), orderByItem.field.getDataType());
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
            orderAlias = this.fieldAliases.get(tableRunInfo.getOrderField().getID());
            if (!this.orderByClause.toString().contains(orderAlias)) {
                if (this.orderByClause.length() > 0) {
                    this.orderByClause.append(",");
                }
                this.orderByClause.append(orderAlias);
            }
        }
        if (tableRunInfo.getBizOrderField() != null && !this.ignoreDefaultOrderBy) {
            orderAlias = this.fieldAliases.get(tableRunInfo.getBizOrderField().getID());
            if (!this.orderByClause.toString().contains(orderAlias)) {
                if (this.orderByClause.length() > 0) {
                    this.orderByClause.append(",");
                }
                this.orderByClause.append(orderAlias);
            }
        }
    }

    protected void buildSelectSql(QueryContext context, StringBuilder sqlBuilder) throws Exception {
        Object orgType;
        if (this.selectFields.charAt(this.selectFields.length() - 1) == ',') {
            this.selectFields.setLength(this.selectFields.length() - 1);
        }
        sqlBuilder.append(" select ").append((CharSequence)this.selectFields).append(" from ").append((CharSequence)this.fromJoinsTables);
        if (this.sqlConditionProcesser != null && (orgType = context.getCache().get("orgType")) != null) {
            PeriodWrapper periodWrapper = context.getPeriodWrapper();
            this.sqlConditionProcesser.beforeCondition(this.primaryTable, this.getTableAlias(context, this.primaryTable), sqlBuilder, periodWrapper == null ? null : periodWrapper.toString(), orgType.toString());
        }
        if (this.whereCondition.length() > 0) {
            sqlBuilder.append(" where ").append((CharSequence)this.whereCondition);
        }
        if (this.orderByClause != null && this.orderByClause.length() > 0) {
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

    public void setRowFilterNode(IASTNode rowFilterNode) {
        this.rowFilterNode = rowFilterNode;
    }

    protected void appendJoinTables(QueryContext context) throws SQLException, ParseException, InterpretException {
        List<DimQueryInfo> dimQueryInfos = this.getDimQueryInfos(context);
        boolean needJoinDimTable = dimQueryInfos != null && !this.isMdInfoQuery(context);
        boolean dimQueryInfosJoinSucc = false;
        for (QueryTable table : this.leftJoinTables) {
            ArrayList<DimQueryInfo> refDimQueryInfos = null;
            if (needJoinDimTable && !dimQueryInfosJoinSucc && table.getTableType() == DataEngineConsts.QueryTableType.DIMENSION) {
                refDimQueryInfos = new ArrayList<DimQueryInfo>();
                for (DimQueryInfo info : dimQueryInfos) {
                    if (!info.getRefTableName().equals(table.getTableName())) continue;
                    refDimQueryInfos.add(info);
                    dimQueryInfosJoinSucc = true;
                }
            }
            this.joinTable(context, table, refDimQueryInfos == null ? "left" : "", refDimQueryInfos);
        }
        if (needJoinDimTable && !dimQueryInfosJoinSucc) {
            IExpression exp = context.getExeContext().getCache().getFormulaParser(true).parseEval(dimQueryInfos.get(0).getRefTableName() + "[CODE]", (IContext)context);
            QueryField queryField = ExpressionUtils.extractQueryField((IASTNode)exp);
            QueryTable table = queryField.getTable();
            ArrayList<DimQueryInfo> refDimQueryInfos = new ArrayList<DimQueryInfo>();
            for (DimQueryInfo info : dimQueryInfos) {
                if (!info.getRefTableName().equals(table.getTableName())) continue;
                refDimQueryInfos.add(info);
                dimQueryInfosJoinSucc = true;
            }
            this.filterBeforeJoin = true;
            this.joinTable(context, table, refDimQueryInfos == null ? "left" : "", refDimQueryInfos);
        }
        for (QueryTable table : this.fullJoinTables) {
            this.joinTable(context, table, "full", null);
        }
        if (this.primaryTable.getTableType() == DataEngineConsts.QueryTableType.COMMON && needJoinDimTable && !dimQueryInfosJoinSucc) {
            for (QueryTable queryTable : context.getQueryTableAliaMap().keySet()) {
                DimQueryInfo dimQueryInfo = dimQueryInfos.get(0);
                if (!queryTable.getTableName().equals(dimQueryInfo.getRefTableName())) continue;
                this.joinTable(context, queryTable, "", dimQueryInfos);
            }
        }
    }

    public DimensionSet getLoopDimensions() {
        if (this.dbQueryExecutor != null) {
            return this.dbQueryExecutor.getQueryInfo().loopDimensions;
        }
        return this.loopDimensions;
    }

    public void setLoopDimensions(QueryContext context, DimensionSet loopDimensions) {
        IDataModelLinkFinder dataLinkFinder;
        this.loopDimensions = new DimensionSet(loopDimensions);
        ExecutorContext exeContext = context.getExeContext();
        String linkAlias = (String)context.getTableLinkAliaMap().get(this.primaryTable);
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
        if (this.isMdInfoQuery(context)) {
            this.loopDimensions.clear();
            this.loopDimensions.addDimension(context.getExeContext().getUnitDimension());
            this.loopDimensions.addDimension("DATATIME");
        }
        if (this.dbQueryExecutor != null) {
            this.dbQueryExecutor.getQueryInfo().loopDimensions = loopDimensions;
        }
    }

    public int getRowKeyFieldStartIndex() {
        if (this.dbQueryExecutor != null) {
            return this.dbQueryExecutor.getRowKeyFieldStartIndex();
        }
        return this.rowKeyFieldStartIndex;
    }

    protected void joinTable(QueryContext context, QueryTable table, String type, List<DimQueryInfo> refDimQueryInfos) throws SQLException, ParseException {
        String tableAlias = this.getTableAlias(context, table);
        String primaryTableAlias = this.getTableAlias(context, this.primaryTable);
        ExecutorContext exeContext = context.getExeContext();
        TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
        TableModelRunInfo primaryTableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        MappingMainDimTable mappingMainDimTable = null;
        if (table.isDimensionTable()) {
            mappingMainDimTable = this.getMappingMainDimTable(context, table.getTableName());
        }
        SqlJoinItem joinItem = null;
        if (this.sqlJoinProvider != null) {
            joinItem = this.sqlJoinProvider.getSqlJoinItem(this.primaryTable.getTableName(), table.getTableName());
        }
        if (joinItem != null) {
            type = joinItem.getJoinType().name();
        }
        boolean needAnd = false;
        if (this.filterBeforeJoin && mappingMainDimTable == null && this.isUnitTable(exeContext, tableInfo.getDimensions()) && this.primaryTable.isCommonDataTable()) {
            SubQueryBuilder subQueryBuilder = new SubQueryBuilder(context, this, tableInfo, table);
            subQueryBuilder.joinTable(this.fromJoinsTables, primaryTableInfo, primaryTableAlias, "", refDimQueryInfos);
            needAnd = true;
            this.appendDimQueryInfos(context, tableAlias, tableInfo, this.fromJoinsTables, refDimQueryInfos, needAnd);
            return;
        }
        if (this.isUnitTable(exeContext, tableInfo.getDimensions())) {
            this.fromJoinsTables.append(" ").append(" join ");
        } else {
            this.fromJoinsTables.append(" ").append(type).append(" join ");
        }
        this.appendQueryTable(context, this.fromJoinsTables, table, mappingMainDimTable);
        this.fromJoinsTables.append(" on ");
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
                ColumnModelDefine primaryDimensionField;
                String keyfieldName;
                String dimensionFieldName;
                Object dimValue;
                String dimension = table.getTableDimensions().get(i);
                ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimension);
                if (dimensionField == null) continue;
                TempAssistantTable tempAssistantTable = context.findTempAssistantTable(dimension);
                PeriodModifier periodModifier = table.getPeriodModifier();
                if (periodModifier != null && dimension.equals("DATATIME")) {
                    PeriodWrapper oldPeriodWrapper = context.getPeriodWrapper();
                    PeriodWrapper periodWrapper = new PeriodWrapper(oldPeriodWrapper);
                    IPeriodAdapter periodAdapter = exeContext.getPeriodAdapter();
                    periodAdapter.modify(periodWrapper, periodModifier);
                    String dimValue2 = periodWrapper.toString();
                    String dimensionFieldName2 = dimension;
                    dimensionFieldName2 = dimensionField.getName();
                    int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
                    if (needAnd) {
                        this.fromJoinsTables.append(" and ");
                    }
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
                    if (needAnd) {
                        this.fromJoinsTables.append(" and ");
                    }
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
                    keyfieldName = dimension;
                    primaryDimensionField = primaryTableInfo.getDimensionField(dimension);
                    keyfieldName = primaryDimensionField.getName();
                    if (this.mainSubQueryAdapter != null) {
                        keyfieldName = this.mainSubQueryAdapter.getColumnName(keyfieldName, false);
                    }
                    this.fromJoinsTables.append(primaryTableAlias).append(".");
                    this.fromJoinsTables.append(keyfieldName);
                    this.fromJoinsTables.append("=");
                    this.fromJoinsTables.append(tableAlias).append(".");
                    if (primaryDimensionField.getColumnType() == ColumnModelType.DATETIME && tableInfo.getTableType() == DataEngineConsts.QueryTableType.PERIOD) {
                        this.fromJoinsTables.append("P_STARTDATE");
                    } else {
                        String dimensionFieldName3 = DataEngineUtil.getDimensionFieldName((TableModelRunInfo)tableInfo, (String)dimension);
                        if (mappingMainDimTable != null) {
                            dimensionFieldName3 = mappingMainDimTable.getFieldMappings().get(dimensionFieldName3);
                        }
                        this.fromJoinsTables.append(dimensionFieldName3);
                    }
                    needAnd = true;
                    continue;
                }
                if (this.mainSubQueryAdapter != null && this.mainSubQueryAdapter.getParentDimensionField(dimension) != null) {
                    if (needAnd) {
                        this.fromJoinsTables.append(" and ");
                    }
                    keyfieldName = dimension;
                    primaryDimensionField = this.mainSubQueryAdapter.getParentDimensionField(dimension);
                    keyfieldName = this.mainSubQueryAdapter.getColumnName(primaryDimensionField.getName(), true);
                    this.fromJoinsTables.append(primaryTableAlias).append(".");
                    this.fromJoinsTables.append(keyfieldName);
                    this.fromJoinsTables.append("=");
                    this.fromJoinsTables.append(tableAlias).append(".");
                    if (primaryDimensionField.getColumnType() == ColumnModelType.DATETIME && tableInfo.getTableType() == DataEngineConsts.QueryTableType.PERIOD) {
                        this.fromJoinsTables.append("P_STARTDATE");
                    } else {
                        String dimensionFieldName4 = DataEngineUtil.getDimensionFieldName((TableModelRunInfo)tableInfo, (String)dimension);
                        if (mappingMainDimTable != null) {
                            dimensionFieldName4 = mappingMainDimTable.getFieldMappings().get(dimensionFieldName4);
                        }
                        this.fromJoinsTables.append(dimensionFieldName4);
                    }
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
        if (mappingMainDimTable == null) {
            needAnd = this.appendDimensionFilterByTable(context, tableAlias, table, tableInfo, this.fromJoinsTables, needAnd);
            needAnd = QuerySqlBuilder.appendVersionFilter(this.queryParam, tableAlias, tableInfo, this.fromJoinsTables, this.queryVersionDate, this.queryVersionStartDate, needAnd);
            needAnd = this.appendDimQueryInfos(context, tableAlias, tableInfo, this.fromJoinsTables, refDimQueryInfos, needAnd);
        } else if (mappingMainDimTable.hasArgs()) {
            for (Map.Entry<String, String> argEntry : mappingMainDimTable.getArgs().entrySet()) {
                this.appendToCondition(context, null, this.fromJoinsTables, tableAlias, argEntry.getKey(), argEntry.getValue(), 6, needAnd);
            }
        }
    }

    private boolean isUnitTable(ExecutorContext exeContext, DimensionSet tableDimensions) {
        return tableDimensions.size() == 1 && tableDimensions.get(0).equals(exeContext.getUnitDimension());
    }

    private void buildSqlByMainTable(QueryContext context) throws SQLException, ParseException {
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        this.appendQueryFields(context, tableInfo);
        this.appendRowKeyFields(context, tableInfo);
        if (this.filterBeforeJoin) {
            SubQueryBuilder subQueryBuilder = new SubQueryBuilder(context, this, tableInfo, this.primaryTable);
            subQueryBuilder.setMainSubQueryAdapter(this.mainSubQueryAdapter);
            subQueryBuilder.appendSubQueryTable(this.fromJoinsTables);
        } else {
            this.appendQueryTable(context, this.fromJoinsTables, this.primaryTable, null);
            this.buildWhereConditionByTable(context, tableInfo, this.primaryTable);
        }
    }

    protected boolean isGroupingQuery() {
        return false;
    }

    private void appendRowKeyFields(QueryContext qContext, TableModelRunInfo tableInfo) {
        ColumnModelDefine recField;
        ColumnModelDefine bizOrderField;
        this.rowKeyFieldStartIndex = this.fieldIndex;
        if (this.mainSubQueryAdapter != null) {
            this.loopDimensions = this.mainSubQueryAdapter.getQueryDimension();
        } else if (this.loopDimensions == null) {
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
            String keyfieldName;
            String keyName = this.loopDimensions.get(i);
            ColumnModelDefine keyField = tableInfo.getDimensionField(keyName);
            String keyTableName = tableInfo.getTableModelDefine().getName();
            if (this.mainSubQueryAdapter != null && keyField == null && (keyField = this.mainSubQueryAdapter.getParentDimensionField(keyName)) != null) {
                keyTableName = this.mainSubQueryAdapter.getParentTableInfo().getTableModelDefine().getName();
            }
            if (keyField != null) {
                this.dimensionFields.put(keyName, keyField);
            }
            String keySqlColumn = keyfieldName = keyField.getName();
            if (keyfieldName.equals("VIRTUAL_BIZKEYORDER")) {
                this.selectFields.append(DataEngineUtil.buildcreateUUIDSql((IDatabase)this.queryParam.getDatabase(), (boolean)true));
            } else if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                if (this.mainSubQueryAdapter != null) {
                    keySqlColumn = this.mainSubQueryAdapter.renameColumn(keyfieldName, keyTableName);
                }
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(keySqlColumn);
                this.selectFields.append(")");
            } else {
                if (this.mainSubQueryAdapter != null) {
                    keySqlColumn = this.mainSubQueryAdapter.renameColumn(keyfieldName, keyTableName);
                }
                this.selectFields.append(primaryTableAlias).append(".").append(keySqlColumn);
            }
            String fieldAlias = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.dimIndexes.put(keyField.getID(), this.fieldIndex);
            this.fieldAliases.putIfAbsent(keyField.getID(), fieldAlias);
            ++this.fieldIndex;
        }
        ColumnModelDefine inputOrderField = tableInfo.getOrderField();
        if (inputOrderField != null) {
            String fieldName;
            String sqlColumn = fieldName = inputOrderField.getName();
            if (this.mainSubQueryAdapter != null) {
                sqlColumn = this.mainSubQueryAdapter.renameColumn(fieldName, tableInfo.getTableModelDefine().getName());
            }
            if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(sqlColumn);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(sqlColumn);
            }
            String fieldAlias = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.fieldAliases.putIfAbsent(inputOrderField.getID(), fieldAlias);
            ++this.fieldIndex;
        }
        if ((bizOrderField = tableInfo.getBizOrderField()) != null) {
            String fieldName;
            String sqlColumn = fieldName = bizOrderField.getName();
            if (fieldName.equals("VIRTUAL_BIZKEYORDER")) {
                this.selectFields.append(DataEngineUtil.buildcreateUUIDSql((IDatabase)this.queryParam.getDatabase(), (boolean)true));
            } else if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                if (this.mainSubQueryAdapter != null) {
                    sqlColumn = this.mainSubQueryAdapter.renameColumn(fieldName, tableInfo.getTableModelDefine().getName());
                }
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(sqlColumn);
                this.selectFields.append(")");
            } else {
                if (this.mainSubQueryAdapter != null) {
                    sqlColumn = this.mainSubQueryAdapter.renameColumn(fieldName, tableInfo.getTableModelDefine().getName());
                }
                this.selectFields.append(primaryTableAlias).append(".").append(sqlColumn);
            }
            String fieldAlias = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.fieldAliases.putIfAbsent(bizOrderField.getID(), fieldAlias);
            ++this.fieldIndex;
        }
        if ((recField = tableInfo.getRecField()) != null) {
            String fieldName;
            String sqlColumn = fieldName = recField.getName();
            if (this.mainSubQueryAdapter != null) {
                sqlColumn = this.mainSubQueryAdapter.renameColumn(fieldName, tableInfo.getTableModelDefine().getName());
            }
            if (this.isGroupingQuery() || this.primaryTable.getIsLj()) {
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(sqlColumn);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(sqlColumn);
            }
            this.selectFields.append(" as ").append(TABLE_ALIAS_PREFIX).append(this.fieldIndex).append(",");
            ++this.fieldIndex;
        }
    }

    private void appendQueryFields(QueryContext context, TableModelRunInfo tableInfo) throws ParseException {
        String prefix = TABLE_ALIAS_PREFIX;
        String primaryTableAlias = this.getTableAlias(context, this.primaryTable);
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            String tableAlias = this.getTableAlias(context, table);
            OrderTempAssistantTable dimOrderTempTable = null;
            String dimensionName = null;
            if (table.isDimensionTable()) {
                dimensionName = table.getTableDimensions().get(0);
                dimOrderTempTable = TempAssistantTableUtils.getContextTempAssistantTables(context).get(dimensionName);
            }
            MappingMainDimTable mappingMainDimTable = null;
            if (table.isDimensionTable()) {
                mappingMainDimTable = this.getMappingMainDimTable(context, table.getTableName());
            }
            for (QueryField queryField : this.queryRegion.getTableFields(table)) {
                AggrType aggrType = null;
                String gather = this.getFieldGatherSql(queryField.getUID(), aggrType, queryField.getDataType());
                String fieldName = queryField.getFieldName();
                if (fieldName.equals("VIRTUAL_BIZKEYORDER")) {
                    if (gather != null && aggrType == AggrType.DISTINCT_COUNT) {
                        this.selectFields.append("count(1)");
                    } else {
                        this.selectFields.append(DataEngineUtil.buildcreateUUIDSql((IDatabase)this.queryParam.getDatabase(), (boolean)true));
                    }
                } else if (dimOrderTempTable != null && fieldName.equalsIgnoreCase("ORDINAL")) {
                    if (gather != null) {
                        if (this.filterBeforeJoin) {
                            this.addGatherField(gather, aggrType, primaryTableAlias, dimensionName + "_" + "TEMP_ORDER", queryField.getDataType());
                        } else {
                            this.addGatherField(gather, aggrType, dimOrderTempTable.getTableName(), "TEMP_ORDER", queryField.getDataType());
                        }
                    } else if (this.filterBeforeJoin) {
                        this.selectFields.append(primaryTableAlias + "." + dimensionName + "_" + "TEMP_ORDER");
                    } else {
                        this.selectFields.append(dimOrderTempTable.getOrderColumnSql());
                    }
                } else {
                    if (mappingMainDimTable != null) {
                        fieldName = mappingMainDimTable.getFieldMappings().get(fieldName);
                    }
                    String fieldSqlColumn = fieldName;
                    if (this.mainSubQueryAdapter != null) {
                        fieldSqlColumn = this.mainSubQueryAdapter.renameColumn(fieldName, queryField.getTableName());
                        tableAlias = primaryTableAlias;
                    }
                    if (gather != null) {
                        this.addGatherField(gather, aggrType, tableAlias, fieldSqlColumn, queryField.getDataType());
                    } else {
                        this.selectFields.append(tableAlias).append(".").append(fieldSqlColumn);
                    }
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
            dataReader = this.queryRegion.getType() == 0 ? new JdbcResultSetDataReader(queryContext) : new MemoryDataSetReader(queryContext);
            queryContext.setDataReader(dataReader);
        }
        return dataReader;
    }

    protected String getFieldGatherSql(String uid, AggrType gatherType, int dataType) {
        return null;
    }

    protected void buildGroupBy(QueryContext qContext) throws Exception {
    }

    private void buildWhereConditionByTable(QueryContext qContext, TableModelRunInfo tableInfo, QueryTable queryTable) throws SQLException, ParseException {
        String tableAlias = this.getTableAlias(qContext, queryTable);
        DimensionValueSet masterkeys = new DimensionValueSet(qContext.getMasterKeys());
        ExecutorContext exeContext = qContext.getExeContext();
        for (int i = 0; i < masterkeys.size(); ++i) {
            IUnitLeafFinder unitLeafFinder;
            String keyName = masterkeys.getName(i);
            Object keyValue = masterkeys.getValue(i);
            String dimensionFieldName = keyName;
            ColumnModelDefine dimensionField = tableInfo.getDimensionField(keyName);
            if (this.sqlConditionProcesser != null && !this.sqlConditionProcesser.acceptFieldCondition(dimensionField) || dimensionField == null) continue;
            dimensionFieldName = dimensionField.getName();
            int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
            if (dimDataType == 2 && keyName.equals("DATATIME")) {
                keyValue = this.convertPeriodToDate(keyValue);
            }
            if (keyName.equals(qContext.getExeContext().getUnitDimension()) && tableInfo.getBizOrderField() == null && (unitLeafFinder = qContext.getUnitLeafFinder()) != null) {
                keyValue = qContext.getStatLeafHelper().processUnitLeafs(qContext, unitLeafFinder, keyValue);
            }
            OrderTempAssistantTable orderTempAssistantTable = TempAssistantTableUtils.getContextTempAssistantTables(qContext).get(keyName);
            if (StringUtils.isEmpty((String)dimensionFieldName) || this.dimensionConditions.contains(keyName)) continue;
            this.dimensionConditions.add(keyName);
            if (orderTempAssistantTable != null) {
                this.fromJoinsTables.append(orderTempAssistantTable.getJoinSql(tableAlias + "." + dimensionFieldName));
                continue;
            }
            this.appendToCondition(qContext, null, this.whereCondition, tableAlias, dimensionFieldName, keyValue, dimDataType, this.whereNeedAnd);
            this.whereNeedAnd = true;
        }
        SplitTableHelper splitTableHelper = this.queryParam.getSplitTableHelper();
        if (splitTableHelper != null && queryTable.getTableType() != DataEngineConsts.QueryTableType.PERIOD) {
            String splitTableName = splitTableHelper.getCurrentSplitTableName(qContext.getExeContext(), queryTable.getTableName());
            Map argMap = splitTableHelper.getSumSchemeKey(qContext.getExeContext(), splitTableName);
            if (argMap != null) {
                Set entrySet = argMap.entrySet();
                for (Map.Entry entry : entrySet) {
                    String fieldName = (String)entry.getKey();
                    String value = (String)entry.getValue();
                    this.appendToCondition(qContext, null, this.whereCondition, tableAlias, fieldName, value, 6, this.whereNeedAnd);
                }
            }
        }
        this.whereNeedAnd = this.appendColFilter(qContext, exeContext.getCache().getDataModelDefinitionsCache(), this.whereCondition, this.whereNeedAnd);
        this.whereNeedAnd = QuerySqlBuilder.appendVersionFilter(this.queryParam, tableAlias, tableInfo, this.whereCondition, this.queryVersionDate, this.queryVersionStartDate, this.whereNeedAnd);
    }

    protected Object convertPeriodToDate(Object keyValue) {
        PeriodType pt = PeriodType.DAY;
        if (keyValue instanceof String) {
            PeriodWrapper pw = new PeriodWrapper((String)((Object)keyValue));
            keyValue = pt.toCalendar(pw);
        } else if (keyValue instanceof List) {
            List values = keyValue;
            ArrayList<Date> dataValues = new ArrayList<Date>(values.size());
            for (Object value : values) {
                PeriodWrapper pw = new PeriodWrapper((String)value);
                dataValues.add(pt.toCalendar(pw).getTime());
            }
            keyValue = dataValues;
        }
        return keyValue;
    }

    protected void appendRowFilter(QueryContext qContext) throws InterpretException {
        if (!this.needMemoryFilter && this.rowFilterNode != null) {
            if (this.rowFilterNode.support(Language.SQL)) {
                if (this.whereNeedAnd) {
                    this.whereCondition.append(" and ");
                }
                if (this.mainSubQueryAdapter != null) {
                    for (IASTNode node : this.rowFilterNode) {
                        if (!(node instanceof DynamicDataNode)) continue;
                        DynamicDataNode dataNode = (DynamicDataNode)node;
                        QueryField queryField = dataNode.getQueryField();
                        String sqlColumn = this.mainSubQueryAdapter.getColumnName(queryField.getFieldName(), queryField);
                        dataNode.setSqlColumn(sqlColumn);
                    }
                }
                SQLInfoDescr conditionSqlINfo = new SQLInfoDescr(this.queryParam.getDatabase(), true, 0, 0);
                this.whereCondition.append("(").append(this.rowFilterNode.interpret((IContext)qContext, Language.SQL, (Object)conditionSqlINfo)).append(")");
                this.whereNeedAnd = true;
            } else {
                this.needMemoryFilter = true;
            }
        }
    }

    protected boolean appendDimQueryInfos(QueryContext qContext, String tableAlias, TableModelRunInfo tableRunInfo, StringBuilder condition, List<DimQueryInfo> refDimQueryInfos, boolean needAnd) throws SQLException, ParseException {
        if (refDimQueryInfos == null) {
            return needAnd;
        }
        if (!this.primaryTable.isCommonDataTable()) {
            return needAnd;
        }
        if (this.isMdInfoQuery(qContext)) {
            return needAnd;
        }
        for (DimQueryInfo dimQueryInfo : refDimQueryInfos) {
            if (needAnd) {
                condition.append(" and ");
            }
            needAnd = true;
            if (dimQueryInfo.getValues() != null) {
                condition.append("(");
            }
            String primmaryTableAlias = this.getTableAlias(qContext, this.primaryTable);
            TableModelRunInfo primaryTableRunInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
            ColumnModelDefine dimField = primaryTableRunInfo.getDimensionField(dimQueryInfo.getDimension());
            condition.append(primmaryTableAlias).append(".").append(dimField.getName());
            condition.append("=");
            condition.append(tableAlias).append(".").append(dimQueryInfo.getRefFieldName());
            if (dimQueryInfo.getValues() != null) {
                condition.append(" or ");
                this.appendToCondition(qContext, null, condition, primmaryTableAlias, dimField.getName(), dimQueryInfo.getValues(), dimField.getColumnType().getValue(), false);
                condition.append(")");
            }
            condition.append(" ");
        }
        return needAnd;
    }

    public static boolean appendVersionFilter(QueryParam queryParam, String tableAlias, TableModelRunInfo tableRunInfo, StringBuilder condition, Date queryVersionDate, Date queryVersionStartDate, boolean needAnd) throws SQLException {
        if (tableRunInfo.getTableModelDefine().getDictType() != TableDictType.ZIPPER) {
            return needAnd;
        }
        if (queryVersionDate.equals(Consts.ALL_VERSIONS_DATE) && queryVersionStartDate.equals(Consts.DATE_VERSION_INVALID_VALUE)) {
            return needAnd;
        }
        if (needAnd) {
            condition.append(" and ");
        }
        needAnd = true;
        Connection conn = queryParam.getConnection();
        IDatabase database = queryParam.getDatabase();
        condition.append(com.jiuqi.nvwa.dataengine.util.DataEngineUtil.getDateCompareSql((Connection)conn, (IDatabase)database, (String)tableAlias, (String)"VALIDTIME", (String)"<=", (Date)queryVersionDate));
        condition.append(" and ");
        condition.append(com.jiuqi.nvwa.dataengine.util.DataEngineUtil.getDateCompareSql((Connection)conn, (IDatabase)database, (String)tableAlias, (String)"INVALIDTIME", (String)">", (Date)queryVersionDate));
        condition.append(" and ").append(tableAlias).append(".").append("RECOVERYFLAG").append("<>1");
        return needAnd;
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

    protected String getTableName(QueryTable queryTable) {
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
                List dimValues = this.colValueFilters.getDimensionColFilterValues(dimName);
                if (dimValues == null || (fieldDefine = tableRunInfo.getDimensionField(dimName)) == null) continue;
                int dataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)fieldDefine.getColumnType());
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
                        FieldSqlConditionUtil.appendFieldValuesCondition((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)condition, (String)fieldName, (int)queryField.getDataType(), (List)valueList, null);
                    }
                    condition.append(")");
                    continue;
                }
                FieldSqlConditionUtil.appendFieldValuesCondition((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)condition, (String)fieldName, (int)queryField.getDataType(), (List)valueList, null);
            }
        }
        return needAnd;
    }

    protected void appendToCondition(QueryContext qContext, TempAssistantTable tempAssistantTable, StringBuilder sql, String tableAlias, String name, Object value, int dataType, boolean needAnd) {
        List<Object> args;
        String fieldName = tableAlias + "." + name;
        List<Object> list = args = this.sqlSoftParse ? this.argValues : null;
        if (value instanceof TableRelation) {
            TableRelation relation = (TableRelation)value;
            sql.append(" exists (select 1 from ").append(relation.getDestTableName()).append(" tr ").append(" where ");
            boolean and = false;
            for (Map.Entry<String, String> entry : relation.getFieldMap().entrySet()) {
                if (and) {
                    sql.append(" and ");
                }
                sql.append(tableAlias).append(".").append(entry.getKey()).append("=").append("tr.").append(entry.getValue());
                and = true;
            }
            sql.append(")");
        } else {
            FieldSqlConditionUtil.appendFieldCondition((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)sql, (String)fieldName, (int)dataType, (Object)value, (boolean)needAnd, (TempAssistantTable)tempAssistantTable, args, this.argDataTypes, (boolean)this.doPage);
        }
    }

    protected void appendValue(StringBuilder sql, Object value, int dataType) {
        List<Object> args = this.sqlSoftParse ? this.argValues : null;
        FieldSqlConditionUtil.appendValue((IDatabase)this.queryParam.getDatabase(), (Connection)this.queryParam.getConnection(), (StringBuilder)sql, (int)dataType, (Object)value, args, this.argDataTypes, (boolean)this.doPage);
    }

    protected void appendQueryTable(QueryContext qContext, StringBuilder sql, QueryTable table, MappingMainDimTable mappingMainDimTable) {
        if (mappingMainDimTable == null) {
            sql.append(" ").append(this.getTableName(qContext, table));
        } else {
            sql.append(" ").append(mappingMainDimTable.getTableName());
        }
        sql.append(" ");
        sql.append(this.getTableAlias(qContext, table));
    }

    private DataSet<QueryField> getMapedTableDataSet(QueryContext qContext) throws Exception {
        TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        if (this.loopDimensions == null) {
            this.loopDimensions = new DimensionSet(this.primaryTable.getTableDimensions());
        }
        DimensionValueSet unMatchedMasterKeys = new DimensionValueSet();
        DataQueryParam param = this.buildDataQueryParam(qContext, tableInfo, unMatchedMasterKeys);
        MemoryDataSet allColResult = this.queryExecuter.execute(param);
        MemoryDataSet<QueryField> result = this.createDataTableResultSet(tableInfo);
        List<Column<QueryField>> queryColumns = this.getDataTableQueryColumns((MemoryDataSet<QueryField>)allColResult, result);
        Map<Integer, Object> fieldValueFilter = this.getFieldValueFilter(unMatchedMasterKeys, (MemoryDataSet<QueryField>)allColResult);
        String unitDim = qContext.getExeContext().getUnitDimension();
        ColumnModelDefine unitColumn = tableInfo.getDimensionField(unitDim);
        int unitColumnIndex = allColResult.getMetadata().indexOf(unitColumn.getName());
        for (DataRow allColRow : allColResult) {
            if (!this.judge(unitColumnIndex, fieldValueFilter, allColRow)) continue;
            DataRow row = result.add();
            for (int index = 0; index < queryColumns.size(); ++index) {
                Column<QueryField> queryColumn = queryColumns.get(index);
                Object value = allColRow.getValue(queryColumn.getIndex());
                row.setValue(index, value);
            }
            row.commit();
        }
        return result;
    }

    protected List<Column<QueryField>> getDataTableQueryColumns(MemoryDataSet<QueryField> allColResult, MemoryDataSet<QueryField> result) {
        ArrayList<Column<QueryField>> queryColumns = new ArrayList<Column<QueryField>>();
        for (int n = 0; n < result.getMetadata().getColumnCount(); ++n) {
            Column column = result.getMetadata().getColumn(n);
            String columnName = ((QueryField)column.getInfo()).getFieldName();
            Column queryColumn = allColResult.getMetadata().find(columnName);
            if (queryColumn == null) continue;
            queryColumns.add((Column<QueryField>)queryColumn);
        }
        return queryColumns;
    }

    protected Map<Integer, Object> getFieldValueFilter(DimensionValueSet unMatchedMasterKeys, MemoryDataSet<QueryField> allColResult) {
        HashMap<Integer, Object> fieldValueFilter = null;
        if (unMatchedMasterKeys.size() > 0) {
            fieldValueFilter = new HashMap<Integer, Object>();
            for (int i = 0; i < unMatchedMasterKeys.size(); ++i) {
                String fieldName = unMatchedMasterKeys.getName(i);
                int index = allColResult.getMetadata().indexOf(fieldName);
                if (index < 0) continue;
                fieldValueFilter.put(index, unMatchedMasterKeys.getValue(i));
            }
        }
        return fieldValueFilter;
    }

    protected MemoryDataSet<QueryField> createDataTableResultSet(TableModelRunInfo tableInfo) {
        MemoryDataSet result = new MemoryDataSet();
        for (QueryField queryField : this.queryRegion.getTableFields(this.primaryTable)) {
            result.getMetadata().addColumn(new Column(queryField.getFieldCode(), queryField.getDataType(), (Object)queryField));
        }
        for (int i = 0; i < this.loopDimensions.size(); ++i) {
            ColumnModelDefine keyField = tableInfo.getDimensionField(this.loopDimensions.get(i));
            QueryField queryField = new QueryField(keyField.getID(), keyField.getCode(), keyField.getName(), this.primaryTable);
            queryField.setDataType(DataTypesConvert.fieldTypeToDataType((ColumnModelType)keyField.getColumnType()));
            result.getMetadata().addColumn(new Column("key_" + keyField.getName(), queryField.getDataType(), (Object)queryField));
        }
        return result;
    }

    protected DataQueryParam buildDataQueryParam(QueryContext qContext, TableModelRunInfo tableInfo, DimensionValueSet unMatchedMasterKeys) throws Exception {
        DataQueryParam param = new DataQueryParam();
        DimensionValueSet queryMasterKeys = qContext.getMasterKeys();
        List paramNames = this.queryExecuter.getParamNames();
        for (int i = 0; i < queryMasterKeys.size(); ++i) {
            String dimName = queryMasterKeys.getName(i);
            ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimName);
            String fieldParamName = "P_" + dimensionField.getName();
            HashSet dimValue = queryMasterKeys.getValue(i);
            if (paramNames.contains(fieldParamName)) {
                param.getParamValues().put(fieldParamName, dimValue);
                continue;
            }
            if (paramNames.contains(dimensionField.getName())) {
                param.getParamValues().put(dimensionField.getName(), dimValue);
                continue;
            }
            if (dimValue instanceof List) {
                dimValue = new HashSet((List)((Object)dimValue));
            }
            unMatchedMasterKeys.setValue(dimensionField.getName(), (Object)dimValue);
        }
        List<DimQueryInfo> dimQueryInfos = this.getDimQueryInfos(qContext);
        if (dimQueryInfos != null) {
            for (DimQueryInfo dimQueryInfo : dimQueryInfos) {
                DimensionTable dimensionTable = qContext.getDimTable(dimQueryInfo.getRefTableName(), qContext.getPeriodWrapper());
                dimQueryInfo.initForJudge(dimensionTable);
                ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimQueryInfo.getDimension());
                unMatchedMasterKeys.setValue(dimensionField.getName(), (Object)dimQueryInfo);
            }
        }
        return param;
    }

    protected boolean judge(int unitColumnIndex, Map<Integer, Object> fieldValueFilter, DataRow allColRow) {
        boolean accept = true;
        if (fieldValueFilter != null) {
            for (Map.Entry<Integer, Object> entry : fieldValueFilter.entrySet()) {
                Object value = allColRow.getValue(entry.getKey().intValue());
                String unitKey = allColRow.getString(unitColumnIndex);
                Object filterObj = entry.getValue();
                if (filterObj instanceof HashSet) {
                    HashSet set = (HashSet)filterObj;
                    if (set.contains(value)) continue;
                    accept = false;
                    break;
                }
                if (filterObj instanceof DimQueryInfo) {
                    DimQueryInfo dimQueryInfo = (DimQueryInfo)filterObj;
                    dimQueryInfo.judge(unitKey, value);
                    continue;
                }
                if (filterObj.equals(value)) continue;
                accept = false;
                break;
            }
        }
        return accept;
    }

    public DataSet<QueryField> getDimensionTableDataSet(QueryContext qContext) throws Exception {
        MemoryDataSet dataSet = new MemoryDataSet();
        TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        if (this.loopDimensions == null) {
            this.loopDimensions = new DimensionSet(this.primaryTable.getTableDimensions());
        }
        for (QueryField queryField : this.queryRegion.getTableFields(this.primaryTable)) {
            dataSet.getMetadata().addColumn(new Column(queryField.getFieldCode(), queryField.getDataType(), (Object)queryField));
        }
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

    private DataSet<QueryField> queryByJDBC(QueryContext qContext, String mainQuerySql) throws SQLException, DataSetException {
        Connection conn = this.queryParam.getConnection();
        Object[] args = this.argValues == null ? null : this.argValues.toArray();
        MemoryDataSet dataSet = DataEngineUtil.queryMemoryDataSet((Connection)conn, (String)mainQuerySql, (Object[])args, (IMonitor)qContext.getMonitor());
        this.getDataReader(qContext).setDataSet((Object)dataSet);
        return dataSet;
    }

    public void buildRowKeys(DimensionValueSet rowKeys, DimensionValueSet masterKeys, IQueryFieldDataReader reader) throws Exception {
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
                if (reader instanceof MemoryDataSetReader) {
                    MemoryDataSetReader memoryDataSetReader = (MemoryDataSetReader)reader;
                    masterValue = memoryDataSetReader.getCurrentRowKey().getValue(dimensionName);
                } else {
                    masterValue = reader.readData(i + this.getRowKeyFieldStartIndex());
                }
            }
            if (dimensionFields.containsKey(dimensionName)) {
                if (masterValue instanceof Date && dimensionName.equals("DATATIME")) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime((Date)masterValue);
                    masterValue = PeriodType.DAY.fromCalendar(calendar);
                } else {
                    masterValue = DataEngineConsts.formatData((ColumnModelDefine)dimensionFields.get(dimensionName), (Object)masterValue, null);
                }
            }
            if (masterValue == null) continue;
            rowKeys.setValue(dimensionName, masterValue);
        }
    }

    public String getTableAlias(QueryContext qContext, QueryTable table) {
        String tableAlias = (String)qContext.getQueryTableAliaMap().get(table);
        if (tableAlias == null) {
            tableAlias = table.getAlias();
        }
        return tableAlias;
    }

    protected String getTableAlias(QueryContext qContext, String tableName) {
        for (Map.Entry entry : qContext.getQueryTableAliaMap().entrySet()) {
            if (!((QueryTable)entry.getKey()).getTableName().equals(tableName)) continue;
            return (String)entry.getValue();
        }
        return tableName;
    }

    private MappingMainDimTable getMappingMainDimTable(QueryContext qContext, String tableName) {
        MappingMainDimTable mappingMainDimTable = (MappingMainDimTable)qContext.getCache().get("MappingMainDimTable_" + tableName);
        return mappingMainDimTable;
    }

    private List<DimQueryInfo> getDimQueryInfos(QueryContext qContext) {
        List dimQueryInfos = (List)qContext.getCache().get("DimQueryInfos");
        return dimQueryInfos;
    }

    private boolean isMdInfoQuery(QueryContext qContext) {
        if (this.isMdInfoQuery == null) {
            TableModelRunInfo mdTableInfo = (TableModelRunInfo)qContext.getOption("MDInfo_Table");
            this.isMdInfoQuery = mdTableInfo != null && mdTableInfo.getTableModelDefine().getName().equals(this.primaryTable.getTableName());
        }
        return this.isMdInfoQuery;
    }

    public boolean isMdInfoTable(QueryContext qContext, String tableName) {
        TableModelRunInfo mdTableInfo = (TableModelRunInfo)qContext.getOption("MDInfo_Table");
        return mdTableInfo != null && mdTableInfo.getTableModelDefine().getName().equals(tableName);
    }

    protected String createTableAlias(int index) {
        return "t" + index;
    }

    public void setMasterDimensions(DimensionSet masterDimensions) {
        this.masterDimensions = masterDimensions;
    }

    public void setDesignTimeData(boolean designTimeData) {
        this.designTimeData = designTimeData;
    }

    public boolean getDesignTimeData() {
        return this.designTimeData;
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

    public List<Integer> getArgDataTypes() {
        return this.argDataTypes;
    }

    public void setIgnoreDefaultOrderBy(boolean ignoreDefaultOrderBy) {
        this.ignoreDefaultOrderBy = ignoreDefaultOrderBy;
    }

    public void setDoPage(boolean doPage) {
        this.doPage = doPage;
    }

    public boolean isDoPage() {
        return this.doPage;
    }

    public void setNeedMemoryFilter(boolean needMemoryFilter) {
        this.needMemoryFilter = needMemoryFilter;
    }

    public DimensionSet getGroupDims() {
        return this.groupDims;
    }

    public void setGroupDims(DimensionSet groupDims) {
        this.groupDims = groupDims;
    }

    public ISqlConditionProcesser getSqlConditionProcesser() {
        return this.sqlConditionProcesser;
    }

    public void setSqlConditionProcesser(ISqlConditionProcesser sqlConditionProcesser) {
        this.sqlConditionProcesser = sqlConditionProcesser;
    }

    public void setQueryExecuter(IDataTableQueryExecutor queryExecuter) {
        this.queryExecuter = queryExecuter;
    }

    public void setOrderByItems(ArrayList<OrderByItem> orderByItems) {
        this.orderByItems = orderByItems;
    }

    public void setUseDefaultOrderBy(boolean useDefaultOrderBy) {
        this.useDefaultOrderBy = useDefaultOrderBy;
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

    public DBQueryExecutor getDbQueryExecutor() {
        return this.dbQueryExecutor;
    }

    public Date getQueryVersionDate() {
        return this.queryVersionDate;
    }

    public Date getQueryVersionStartDate() {
        return this.queryVersionStartDate;
    }
}

