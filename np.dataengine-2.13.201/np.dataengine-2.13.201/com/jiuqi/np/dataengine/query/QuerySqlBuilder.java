/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.provider.DimensionColumn
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nvwa.dataengine.util.DataEngineUtil
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.Version;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.KeyOrderTempTable;
import com.jiuqi.np.dataengine.common.LookupItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryRegion;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.common.TwoKeyTempTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ITableConditionProvider;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.PeriodConditionNode;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryDataListener;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutor;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryInfo;
import com.jiuqi.np.dataengine.nrdb.query.NRDBQueryExecutor;
import com.jiuqi.np.dataengine.parse.LJSQLInfo;
import com.jiuqi.np.dataengine.query.DBResultSet;
import com.jiuqi.np.dataengine.query.DataQueryBuilder;
import com.jiuqi.np.dataengine.query.MemorySteamLoader;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QueryFilterValueClassify;
import com.jiuqi.np.dataengine.query.QueryTableColFilterValues;
import com.jiuqi.np.dataengine.reader.DataSetReader;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.dataengine.setting.ISqlJoinProvider;
import com.jiuqi.np.dataengine.setting.SqlJoinItem;
import com.jiuqi.np.dataengine.setting.SqlJoinOneItem;
import com.jiuqi.np.dataengine.util.FieldSqlConditionUtil;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.provider.DimensionColumn;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    protected boolean useDNASql;
    protected DimensionSet masterDimensions;
    protected QueryRegion queryRegion;
    protected String primaryTableName;
    protected ArrayList<OrderByItem> orderByItems;
    protected OrderByItem specifiedOrderByItem;
    protected String specifiedOrderAilas;
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
    protected List<Integer> argDataTypes;
    protected boolean sqlSoftParse = false;
    protected boolean inited = false;
    public static final String ROWINDEX = "rowindex";
    public static final String TABLE_ALIAS_PREFIX = "c_";
    protected boolean ignoreDefaultOrderBy = false;
    private boolean doPage;
    private boolean isMultiFloatCheck = false;
    private int bizkeyOrderFieldIndex = -1;
    protected DBQueryExecutor dbQueryExecutor = null;

    public void doInit(QueryContext context) throws ParseException {
        if (this.inited) {
            return;
        }
        this.initPrimaryTable();
        int index = 0;
        Map<QueryTable, String> queryTableAliaMap = context.getQueryTableAliaMap();
        for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
            if (table.equals(this.primaryTable)) {
                if (queryTableAliaMap.containsKey(table)) continue;
                queryTableAliaMap.put(this.primaryTable, this.createTableAlias(0));
                continue;
            }
            ++index;
            if (!queryTableAliaMap.containsKey(table)) {
                queryTableAliaMap.put(table, this.createTableAlias(index));
            }
            if (this.queryRegion.isFloatStreamCalc()) {
                this.leftJoinTables.add(table);
                continue;
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
            if (table.getTableDimensions().equals(this.primaryTable.getTableDimensions())) {
                this.fullJoinTables.add(table);
                continue;
            }
            this.leftJoinTables.add(table);
        }
        this.initDBQueryExecutor(context);
        this.inited = true;
    }

    public String buildSql(QueryContext context) throws Exception {
        this.doInit(context);
        if (this.getPrimaryTable().isDimensionTable()) {
            return null;
        }
        return this.buildQuerySql(context);
    }

    public String buildQuerySql(QueryContext context) throws Exception {
        this.fieldIndex = 1;
        if (this.getPrimaryTable().isDimensionTable()) {
            IQueryFieldDataReader dataReader = context.getDataReader();
            if (dataReader != null) {
                for (QueryField queryField : this.queryRegion.getTableFields(this.getPrimaryTable())) {
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
            this.sql = sqlUpdater.updateQuerySql(this.getPrimaryTable(), this.getTableAlias(context, this.getPrimaryTable()), this.sql);
        }
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
            if (this.getPrimaryTable().isDimensionTable()) {
                fieldKeys = this.getDimensionTableDataSet(qContext);
                return fieldKeys;
            }
            if (this.dbQueryExecutor != null) {
                MemoryDataSet result = new MemoryDataSet();
                this.dbQueryExecutor.runQuery(qContext, (MemoryDataSet<QueryField>)result);
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

    public void queryToDataReader(QueryContext qContext, IQueryFieldDataReader reader) throws Exception {
        if (this.dbQueryExecutor != null) {
            MemoryDataSet result = new MemoryDataSet();
            this.dbQueryExecutor.runQuery(qContext, (MemoryDataSet<QueryField>)result);
            reader.setDataSet(result);
        } else {
            Object[] args = this.getArgValues() == null ? null : this.getArgValues().toArray();
            MemoryDataSet<QueryField> dataSet = DataEngineUtil.queryMemoryDataSet(qContext.getQueryParam().getConnection(), this.getTableName(this.getPrimaryTable()), this.sql, args, qContext.getMonitor());
            reader.setDataSet(dataSet);
        }
    }

    /*
     * Exception decompiling
     */
    public int queryToDataReader(QueryContext qContext, IQueryFieldDataReader reader, int rowIndex, int rowSize) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void queryToDataRowReader(QueryContext qContext, DataQueryBuilder dataQueryBuilder, IDataRowReader dataRowReader) throws Exception {
        IQueryFieldDataReader reader = qContext.getDataReader();
        if (this.dbQueryExecutor != null) {
            this.dbQueryExecutor.readData(qContext, new DBQueryDataListener(qContext, dataQueryBuilder, dataRowReader, this));
        } else {
            Object[] args = this.getArgValues() == null ? null : this.getArgValues().toArray();
            try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                 DBResultSet<QueryField> dataSet = sqlHelper.queryDBResultSet(qContext.getQueryParam().getConnection(), this.sql, args, qContext.getMonitor());){
                reader.setDataSet(dataSet);
                dataRowReader.start(qContext, dataQueryBuilder.getTable().getSystemFields());
                while (reader.next()) {
                    DataRowImpl dataRow = dataQueryBuilder.loadRowData(qContext, this, reader, -1, -1, dataRowReader);
                    dataRowReader.readRowData(qContext, dataRow);
                }
                dataRowReader.finish(qContext);
            }
        }
    }

    public MemorySteamLoader queryMemorySteamLoader(QueryContext qContext) throws Exception {
        MemorySteamLoader loader = new MemorySteamLoader();
        if (this.dbQueryExecutor != null) {
            loader.doInit(qContext, this);
            this.dbQueryExecutor.readToMemorySteamLoader(qContext, loader);
        } else {
            Object[] args = this.getArgValues() == null ? null : this.getArgValues().toArray();
            SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
            DBResultSet<QueryField> dataSet = sqlHelper.queryDBResultSet(qContext.getQueryParam().getConnection(), this.sql, args, qContext.getMonitor());
            loader.doInit(qContext, this, sqlHelper, dataSet);
        }
        return loader;
    }

    public DataSet<QueryField> getDimensionTableDataSet(QueryContext qContext) throws Exception {
        boolean isUnitDimension;
        MemoryDataSet dataSet = new MemoryDataSet();
        ExecutorContext exeContext = qContext.getExeContext();
        TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(this.getPrimaryTable().getTableName());
        if (this.loopDimensions == null) {
            this.loopDimensions = new DimensionSet(this.getPrimaryTable().getTableDimensions());
        }
        for (Object queryField : this.queryRegion.getTableFields(this.getPrimaryTable())) {
            dataSet.getMetadata().addColumn(new Column(((QueryField)queryField).getFieldCode(), ((QueryField)queryField).getDataType(), queryField));
        }
        String dimensionName = null;
        for (ColumnModelDefine keyField : tableInfo.getDimFields()) {
            QueryField queryField = new QueryField(keyField, this.getPrimaryTable());
            queryField.setDataType(DataTypesConvert.fieldTypeToDataType(keyField.getColumnType()));
            dataSet.getMetadata().addColumn(new Column("key_" + keyField.getName(), queryField.getDataType(), (Object)queryField));
            dimensionName = tableInfo.getDimensionName(keyField.getCode());
        }
        ArrayList<Integer> dimColumnIndexes = new ArrayList<Integer>();
        PeriodWrapper offSetPeriod = null;
        if (this.getPrimaryTable().getPeriodModifier() != null) {
            offSetPeriod = new PeriodWrapper(qContext.getPeriodWrapper());
            exeContext.getPeriodAdapter().modify(offSetPeriod, this.getPrimaryTable().getPeriodModifier());
        } else if (this.getPrimaryTable().getDimensionRestriction() != null && this.getPrimaryTable().getDimensionRestriction().hasValue("DATATIME")) {
            offSetPeriod = new PeriodWrapper(this.getPrimaryTable().getDimensionRestriction().getValue("DATATIME").toString());
        }
        DimensionTable dimTable = null;
        String linkAlias = qContext.getTableLinkAliaMap().get(this.getPrimaryTable());
        IFmlExecEnvironment env = exeContext.getEnv();
        boolean bl = isUnitDimension = env != null && dimensionName.equals(env.getUnitDimesion(exeContext));
        if (linkAlias != null && isUnitDimension) {
            VariableManager variableManager = qContext.getExeContext().getVariableManager();
            variableManager.remove("VAR_LINK_ALIAS");
            variableManager.add(new Variable("VAR_LINK_ALIAS", "VAR_LINK_ALIAS", 6, linkAlias));
            Object dimValue = qContext.getMasterKeys().getValue(dimensionName);
            dimValue = DataEngineUtil.processLinkedUnit(qContext, this.unitKeyMap, this.getPrimaryTable(), exeContext, dimensionName, dimValue);
            dimTable = qContext.getDimTableByKeyValue(this.getPrimaryTable().getTableName(), dimValue, offSetPeriod, linkAlias);
        } else {
            dimTable = offSetPeriod != null ? qContext.getDimTable(this.getPrimaryTable().getTableName(), offSetPeriod) : qContext.getDimTable(this.getPrimaryTable().getTableName());
        }
        for (int n = 0; n < dataSet.getMetadata().getColumnCount(); ++n) {
            Column column = dataSet.getMetadata().getColumn(n);
            String dimColumnName = ((QueryField)column.getInfo()).getFieldCode();
            DimensionColumn dimColumn = dimTable.getMetaData().findColumn(dimColumnName);
            if (dimColumn != null) {
                dimColumnIndexes.add(dimColumn.getIndex());
                continue;
            }
            dimColumnIndexes.add(-1);
        }
        for (int i = 0; i < dimTable.rowCount(); ++i) {
            DimensionRow dimensionRow = dimTable.getRow(i);
            DataRow row = dataSet.add();
            for (int index = 0; index < dimColumnIndexes.size(); ++index) {
                int dimColumnIndex = (Integer)dimColumnIndexes.get(index);
                if (dimColumnIndex < 0) continue;
                Object value = dimensionRow.getValue(dimColumnIndex);
                row.setValue(index, value);
            }
            row.commit();
        }
        return dataSet;
    }

    public List<QuerySqlBuilder> divideFullJoins(QueryContext context) throws ParseException {
        this.orderByItems = null;
        this.setUseDefaultOrderBy(false);
        return this.dividTables(this.fullJoinTables, context);
    }

    public List<QuerySqlBuilder> divideLeftJoins(QueryContext context) throws ParseException {
        this.orderByItems = null;
        this.setUseDefaultOrderBy(false);
        List<QuerySqlBuilder> builders = this.dividTables(this.leftJoinTables, context);
        if (builders != null && builders.size() > 1) {
            Collections.sort(builders, new Comparator<QuerySqlBuilder>(){

                @Override
                public int compare(QuerySqlBuilder o1, QuerySqlBuilder o2) {
                    return o1.getPrimaryTable().getTableDimensions().size() - o2.getPrimaryTable().getTableDimensions().size();
                }
            });
            Collections.reverse(builders);
        }
        return builders;
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

    public void resetCurrentMasterKey(QueryContext qContext, IQueryFieldDataReader reader) throws Exception {
        DimensionValueSet masterKeys = qContext.getMasterKeys();
        DimensionSet loopDimensions = this.getLoopDimensions();
        HashMap<String, ColumnModelDefine> dimensionFields = this.getDimensionFields();
        DimensionValueSet currentMarsterKeys = qContext.getCurrentMasterKey();
        for (int i = 0; i < loopDimensions.size(); ++i) {
            Object value;
            String dimensionName = loopDimensions.get(i);
            if (!currentMarsterKeys.hasValue(dimensionName)) continue;
            Object dimensionValue = null;
            if (masterKeys.hasValue(dimensionName) && !((value = masterKeys.getValue(dimensionName)) instanceof List)) {
                dimensionValue = value;
            }
            if (dimensionValue == null) {
                dimensionValue = reader.readData(i + this.getRowKeyFieldStartIndex());
            }
            if (dimensionFields.containsKey(dimensionName)) {
                dimensionValue = DataEngineConsts.formatData(dimensionFields.get(dimensionName), dimensionValue, null);
            }
            if (dimensionValue == null) continue;
            currentMarsterKeys.setValue(dimensionName, dimensionValue);
        }
    }

    public String getIndexSql(QueryContext queryContext, String mainQuerySql, DimensionValueSet rowKeys, DimensionValueSet masterKeys) throws ParseException, ExpressionException {
        StringBuilder leftSql = new StringBuilder();
        leftSql.append("select tt.").append(ROWINDEX);
        StringBuilder rowIndexSql = new StringBuilder();
        IDatabase database = DatabaseInstance.getDatabase();
        if (database.isDatabase("ORACLE") || database.isDatabase("Informix") || database.isDatabase("DM") || database.isDatabase("KINGBASE") || database.isDatabase("KINGBASE8")) {
            rowIndexSql.append(" from (");
            rowIndexSql.append("select rownum as ").append(ROWINDEX).append(", o.* from (");
            rowIndexSql.append(mainQuerySql);
            rowIndexSql.append(") o");
            rowIndexSql.append(") tt where ");
        } else if (database.isDatabase("MYSQL")) {
            Version version = DatabaseInstance.getVersion();
            if (version != null && version.getMajor() >= 8) {
                rowIndexSql.append(" from (");
                rowIndexSql.append(" select ROW_NUMBER() OVER (ORDER BY ").append((CharSequence)this.orderByClause).append(") AS ").append(ROWINDEX).append(", o.*");
                rowIndexSql.append(" from ");
                rowIndexSql.append("( ").append(mainQuerySql).append(" ) o");
                rowIndexSql.append(") tt where ");
            } else {
                rowIndexSql.append(" from (");
                rowIndexSql.append("select (@rownum := @rownum + 1) as ").append(ROWINDEX).append(",o.* from ");
                rowIndexSql.append("( ").append(mainQuerySql).append(" ) o,").append(" (select @rownum := 0) r ");
                rowIndexSql.append(") tt where ");
            }
        } else {
            return null;
        }
        TableModelRunInfo tableInfo = queryContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.getPrimaryTable().getTableName());
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

    protected DBQueryInfo createDBQueryInfo() {
        DBQueryInfo queryInfo = new DBQueryInfo();
        queryInfo.dimensionFields = this.dimensionFields;
        queryInfo.loopDimensions = this.loopDimensions;
        queryInfo.QueryFields = this.queryRegion.getTableFields(this.getPrimaryTable());
        queryInfo.primaryTable = this.getPrimaryTable();
        queryInfo.rowFilterNode = this.rowFilterNode;
        queryInfo.unitKeyMap = this.unitKeyMap;
        queryInfo.colValueFilters = this.colValueFilters;
        queryInfo.needMemoryFilter = this.needMemoryFilter;
        queryInfo.orderByItems = this.orderByItems;
        queryInfo.useDefaultOrderBy = this.useDefaultOrderBy;
        queryInfo.ignoreDefaultOrderBy = this.ignoreDefaultOrderBy;
        return queryInfo;
    }

    protected void initPrimaryTable() {
        if (this.primaryTable == null) {
            for (QueryTable table : this.queryRegion.getAllTableFields().keySet()) {
                if (table.getTableName().equals(this.primaryTableName) && table.getIsSimple()) {
                    this.primaryTable = table;
                    break;
                }
                if (this.primaryTable == null) {
                    this.primaryTable = table;
                    continue;
                }
                if (table.getTableDimensions().size() > this.primaryTable.getTableDimensions().size()) {
                    this.primaryTable = table;
                    continue;
                }
                if (table.getTableDimensions().size() != this.primaryTable.getTableDimensions().size() || !table.getIsSimple() || this.primaryTable.getIsSimple()) continue;
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
                this.appendVersionFilter(alias, valueTable, tableInfo, this.fromJoinsTables, true);
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

    protected List<QuerySqlBuilder> dividTables(List<QueryTable> tables, QueryContext context) throws ParseException {
        ArrayList<QuerySqlBuilder> builders = null;
        if (!tables.isEmpty()) {
            builders = new ArrayList<QuerySqlBuilder>();
            QueryTable periodQueryTable = null;
            for (QueryTable table : tables) {
                if (context.isQueryModule() && !context.isRightJoinTable(table.getTableName()) && table.getTableName().startsWith("NR_PERIOD")) {
                    periodQueryTable = table;
                    continue;
                }
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
            logger.error(e.getMessage(), e);
            return queryFields;
        }
    }

    private void addGatherField(String gather, String alias, String fieldName, int dataType) {
        if (gather.startsWith("none")) {
            if (dataType == 37 || dataType == 36 || dataType == 35 || dataType == 34) {
                IDatabase database = this.queryParam.getDatabase();
                if (database.isDatabase("DERBY") || database.isDatabase("Informix")) {
                    this.selectFields.append(" '' ");
                }
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
        TableModelDefine tableModel = dataDefinitionsCache.getTableModel(referField);
        if (tableModel.getName().equals(tableName)) {
            return referField;
        }
        return this.getJoinField(context, referFieldId, tableName);
    }

    protected void buildOrderBy(QueryContext context) throws Exception {
        String orderAlias;
        List<ColumnModelDefine> dimFields;
        if (this.orderByItems != null && !this.orderByItems.isEmpty()) {
            for (OrderByItem orderByItem : this.orderByItems) {
                if (orderByItem.field != null) {
                    if (this.specifiedOrderByItem != null && this.specifiedOrderByItem.field.equals(orderByItem.field) && this.specifiedOrderAilas != null) {
                        this.orderByClause.append(this.specifiedOrderAilas);
                    } else if (this.fieldAliases.containsKey(orderByItem.field.getUID())) {
                        this.orderByClause.append(this.fieldAliases.get(orderByItem.field.getUID()));
                    } else {
                        QueryTable orderTable = orderByItem.field.getTable();
                        Object tableAlias = context.getQueryTableAliaMap().get(orderTable);
                        if (tableAlias == null && this.getPrimaryTable() != null && this.getPrimaryTable().getTableName().equals(orderTable.getTableName())) {
                            tableAlias = context.getQueryTableAliaMap().get(this.getPrimaryTable());
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
            String primaryTableAlias = this.getTableAlias(context, this.getPrimaryTable());
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
        if (this.groupByClause != null && this.groupByClause.length() > 0) {
            sqlBuilder.append(" group by ");
            sqlBuilder.append((CharSequence)this.groupByClause);
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
        if (this.useDNASql) {
            sqlBuilder.append("\nend");
        }
    }

    public void setRowFilterNode(IASTNode rowFilterNode) {
        this.rowFilterNode = rowFilterNode;
    }

    private void appendJoinTables(QueryContext context) throws Exception {
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
        if (this.dbQueryExecutor != null) {
            return this.dbQueryExecutor.getQueryInfo().loopDimensions;
        }
        return this.loopDimensions;
    }

    public void setLoopDimensions(QueryContext context, DimensionSet loopDimensions) {
        IDataModelLinkFinder dataLinkFinder;
        this.loopDimensions = new DimensionSet(loopDimensions);
        ExecutorContext exeContext = context.getExeContext();
        String linkAlias = context.getTableLinkAliaMap().get(this.getPrimaryTable());
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
            if (this.getPrimaryTable() == null || this.getPrimaryTable().getTableDimensions().contains(dimension)) continue;
            this.loopDimensions.removeAt(i);
        }
        if (this.dbQueryExecutor != null) {
            this.dbQueryExecutor.getQueryInfo().loopDimensions = this.loopDimensions;
        }
    }

    public int getRowKeyFieldStartIndex() {
        if (this.dbQueryExecutor != null) {
            return this.dbQueryExecutor.getRowKeyFieldStartIndex();
        }
        return this.rowKeyFieldStartIndex;
    }

    private void joinTable(QueryContext context, QueryTable table, String type) throws Exception {
        if (this.queryRegion.isFloatStreamCalc() && table.isDimensionTable()) {
            DimensionTable dimTable = context.getDimTable(table.getTableName());
            IQueryFieldDataReader dataReader = context.getDataReader();
            if (dataReader != null) {
                for (QueryField queryField : this.queryRegion.getTableFields(table)) {
                    DimensionColumn dimColumn = dimTable.getMetaData().findColumn(queryField.getFieldCode());
                    QueryFieldInfo info = dataReader.putIndex(context.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, dimColumn.getIndex());
                    info.dimTable = dimTable;
                }
            }
            return;
        }
        String tableAlias = this.getTableAlias(context, table);
        String primaryTableAlias = this.getTableAlias(context, this.getPrimaryTable());
        ExecutorContext exeContext = context.getExeContext();
        TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
        TableModelRunInfo primaryTableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(this.primaryTable.getTableName());
        SqlJoinItem joinItem = null;
        if (this.sqlJoinProvider != null) {
            joinItem = this.sqlJoinProvider.getSqlJoinItem(this.getPrimaryTable().getTableName(), table.getTableName());
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
                    FieldSqlConditionUtil.appendConstValue(this.queryParam.getDatabase(), this.queryParam.getConnection(), this.fromJoinsTables, dimDataType, dimValue2);
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
                    FieldSqlConditionUtil.appendConstValue(this.queryParam.getDatabase(), this.queryParam.getConnection(), this.fromJoinsTables, dimDataType, dimValue);
                    needAnd = true;
                    continue;
                }
                if (this.getPrimaryTable().getTableDimensions().contains(dimension)) {
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
        }
        if (!needAnd) {
            this.fromJoinsTables.append(" 1=1 ");
            needAnd = true;
        }
        needAnd = this.appendDimensionFilterByTable(context, tableAlias, table, tableInfo, this.fromJoinsTables, needAnd);
        needAnd = this.appendVersionFilter(tableAlias, table, tableInfo, this.fromJoinsTables, needAnd);
    }

    private void leftJoinLjTable(QueryContext context, QueryTable table) {
        this.fromJoinsTables.append(" left join ");
        this.fromJoinsTables.append(" (select ");
        for (QueryField queryField : this.queryRegion.getTableFields(table)) {
            this.fromJoinsTables.append("sum(").append(this.getTableName(context, table)).append(".").append(queryField.getFieldName()).append(") as ").append(queryField.getFieldName());
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
        this.fromJoinsTables.append(this.getTableName(context, table));
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
        TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.getPrimaryTable().getTableName());
        this.appendQueryFields(context, tableInfo);
        this.appendRowKeyFields(context, tableInfo);
        this.appendQueryTable(context, this.fromJoinsTables, this.getPrimaryTable());
        this.buildWhereConditionByTable(context, tableInfo, this.getPrimaryTable());
    }

    protected boolean isGroupingQuery() {
        return false;
    }

    private void appendRowKeyFields(QueryContext qContext, TableModelRunInfo tableInfo) {
        ColumnModelDefine recField;
        ColumnModelDefine bizOrderField;
        ColumnModelDefine inputOrderField;
        QueryField specifiedOrderField;
        String dimensionName;
        TempAssistantTable tempAssistantTable;
        String fieldAlias;
        this.rowKeyFieldStartIndex = this.fieldIndex;
        if (this.loopDimensions == null) {
            this.loopDimensions = new DimensionSet(this.getPrimaryTable().getTableDimensions());
            for (int i = 0; i < this.getPrimaryTable().getTableDimensions().size(); ++i) {
                String dimName = this.getPrimaryTable().getTableDimensions().get(i);
                if (this.getPrimaryTable().getDimensionRestriction() != null && !this.getPrimaryTable().getDimensionRestriction().hasValue(dimName)) {
                    this.loopDimensions.removeDimension(dimName);
                }
                if (this.getPrimaryTable().getPeriodModifier() == null) continue;
                this.loopDimensions.removeDimension("DATATIME");
            }
        }
        String primaryTableAlias = this.getTableAlias(qContext, this.getPrimaryTable());
        for (int i = 0; i < this.loopDimensions.size(); ++i) {
            String keyName = this.loopDimensions.get(i);
            ColumnModelDefine keyField = tableInfo.getDimensionField(keyName);
            this.dimensionFields.put(keyName, keyField);
            String keyfieldName = keyField.getName();
            if (this.isGroupingQuery() || this.getPrimaryTable().getIsLj()) {
                if (keyfieldName.equals("DATATIME")) {
                    this.selectFields.append("max(");
                } else {
                    this.selectFields.append("min(");
                }
                this.selectFields.append(primaryTableAlias).append(".").append(keyfieldName);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(keyfieldName);
            }
            fieldAlias = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.dimIndexes.put(keyField.getID(), this.fieldIndex);
            this.fieldAliases.putIfAbsent(keyField.getID(), fieldAlias);
            ++this.fieldIndex;
        }
        if (this.specifiedOrderByItem != null && (tempAssistantTable = qContext.findTempAssistantTable(dimensionName = tableInfo.getDimensionName((specifiedOrderField = this.specifiedOrderByItem.field).getFieldCode()))) != null && tempAssistantTable instanceof KeyOrderTempTable) {
            KeyOrderTempTable keyOrderTempTable = (KeyOrderTempTable)tempAssistantTable;
            this.selectFields.append(keyOrderTempTable.getOrderColumnSql());
            fieldAlias = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias).append(",");
            this.fieldAliases.putIfAbsent(specifiedOrderField.getUID(), fieldAlias);
            this.specifiedOrderAilas = fieldAlias;
            ++this.fieldIndex;
        }
        if ((inputOrderField = tableInfo.getOrderField()) != null) {
            String fieldName = inputOrderField.getName();
            if (this.isGroupingQuery() || this.getPrimaryTable().getIsLj()) {
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(fieldName);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(fieldName);
            }
            String fieldAlias2 = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias2).append(",");
            this.fieldAliases.putIfAbsent(inputOrderField.getID(), fieldAlias2);
            ++this.fieldIndex;
        }
        if ((bizOrderField = tableInfo.getBizOrderField()) != null) {
            String fieldName = bizOrderField.getName();
            if (this.isGroupingQuery() || this.getPrimaryTable().getIsLj()) {
                this.selectFields.append("min(");
                this.selectFields.append(primaryTableAlias).append(".").append(fieldName);
                this.selectFields.append(")");
            } else {
                this.selectFields.append(primaryTableAlias).append(".").append(fieldName);
            }
            String fieldAlias3 = TABLE_ALIAS_PREFIX + this.fieldIndex;
            this.selectFields.append(" as ").append(fieldAlias3).append(",");
            this.fieldAliases.putIfAbsent(bizOrderField.getID(), fieldAlias3);
            this.bizkeyOrderFieldIndex = this.fieldIndex++;
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
            if (this.isGroupingQuery() || this.getPrimaryTable().getIsLj()) {
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
            if (this.queryRegion.isFloatStreamCalc() && table.isDimensionTable()) continue;
            String tableAlias = this.getTableAlias(context, table);
            for (QueryField queryField : this.queryRegion.getTableFields(table)) {
                String gather = this.getFieldGatherSql(queryField.getUID(), queryField.getDataType());
                String fieldName = queryField.getFieldName();
                if (gather != null) {
                    this.addGatherField(gather, tableAlias, fieldName, queryField.getDataType());
                } else if (table.getIsLj()) {
                    this.selectFields.append("sum(");
                    this.selectFields.append(tableAlias).append(".").append(fieldName);
                    this.selectFields.append(")");
                } else {
                    this.selectFields.append(tableAlias).append(".").append(fieldName);
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
        if (this.getPrimaryTable().getIsLj()) {
            TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.getPrimaryTable().getTableName());
            String tableAlias = this.getTableAlias(qContext, this.getPrimaryTable());
            for (ColumnModelDefine dimensionField : tableInfo.getDimFields()) {
                if (dimensionField.getCode().equals("DATATIME")) continue;
                this.groupByClause.append(tableAlias).append(".").append(dimensionField.getName()).append(",");
            }
            if (this.groupByClause.length() > 0) {
                this.groupByClause.setLength(this.groupByClause.length() - 1);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private void buildWhereConditionByTable(QueryContext qContext, TableModelRunInfo tableInfo, QueryTable queryTable) throws SQLException, ParseException {
        String tableAlias = this.getTableAlias(qContext, queryTable);
        DimensionValueSet masterkeys = new DimensionValueSet(qContext.getMasterKeys());
        DimensionValueSet tableRestriction = queryTable.getDimensionRestriction();
        ExecutorContext exeContext = qContext.getExeContext();
        IFmlExecEnvironment env = exeContext.getEnv();
        String linkAlias = qContext.getTableLinkAliaMap().get(queryTable);
        for (int i = 0; i < masterkeys.size(); ++i) {
            void var15_19;
            boolean isUnitDimension;
            String keyName = masterkeys.getName(i);
            Object keyValue = masterkeys.getValue(i);
            if (qContext.is1v1RelationDim(keyName, linkAlias)) continue;
            boolean bl = isUnitDimension = env != null && keyName.equals(env.getUnitDimesion(exeContext));
            if (tableRestriction != null && tableRestriction.hasValue(keyName)) continue;
            Object dimensionFieldName = keyName;
            ColumnModelDefine columnModelDefine = tableInfo.getDimensionField(keyName);
            if (isUnitDimension) {
                IUnitLeafFinder unitLeafFinder;
                if (linkAlias != null) {
                    String relatedUnitDimName = null;
                    if (columnModelDefine == null) {
                        IDataModelLinkFinder dataLinkFinder = env.getDataModelLinkFinder();
                        relatedUnitDimName = dataLinkFinder.getRelatedUnitDimName(exeContext, linkAlias, keyName);
                        if (relatedUnitDimName != null) {
                            keyName = relatedUnitDimName;
                            ColumnModelDefine columnModelDefine2 = tableInfo.getDimensionField(keyName);
                            keyValue = DataEngineUtil.processLinkedUnit(qContext, this.unitKeyMap, queryTable, exeContext, keyName, keyValue);
                        }
                    } else {
                        relatedUnitDimName = keyName;
                        keyValue = DataEngineUtil.processLinkedUnit(qContext, this.unitKeyMap, queryTable, exeContext, keyName, keyValue);
                    }
                }
                if ((unitLeafFinder = qContext.getUnitLeafFinder()) != null && tableInfo.getBizOrderField() == null) {
                    keyValue = qContext.getStatLeafHelper().processUnitLeafs(qContext, unitLeafFinder, keyValue);
                }
            }
            if (var15_19 == null) continue;
            dimensionFieldName = var15_19.getName();
            int dimDataType = DataTypesConvert.fieldTypeToDataType(var15_19.getColumnType());
            if (StringUtils.isEmpty((String)dimensionFieldName) || this.dimensionConditions.contains(keyName)) continue;
            this.dimensionConditions.add(keyName);
            if (keyValue == null) {
                if (this.whereNeedAnd) {
                    this.whereCondition.append(" and ");
                }
                this.whereCondition.append(" 1=0 ");
                this.whereNeedAnd = true;
                continue;
            }
            if (keyName.equals("DATATIME")) {
                keyValue = DataEngineUtil.getPeriodValue(qContext, tableInfo, this.getPrimaryTable(), env, linkAlias, keyValue);
                if (queryTable.getIsLj()) {
                    if (this.whereNeedAnd) {
                        this.whereCondition.append(" and ");
                    }
                    this.whereCondition.append(tableAlias).append(".").append((String)dimensionFieldName);
                    this.whereCondition.append(" like '").append((String)keyValue, 0, 5).append("%'");
                    this.whereCondition.append(" and ");
                    this.whereCondition.append(tableAlias).append(".").append((String)dimensionFieldName);
                    this.whereCondition.append("<='").append((String)keyValue).append("'");
                } else {
                    this.appendKeyCondition(qContext, queryTable, tableInfo, keyName, isUnitDimension, tableAlias, (String)dimensionFieldName, keyValue, dimDataType, linkAlias);
                }
                this.whereNeedAnd = true;
                continue;
            }
            this.appendKeyCondition(qContext, queryTable, tableInfo, keyName, isUnitDimension, tableAlias, (String)dimensionFieldName, keyValue, dimDataType, linkAlias);
        }
        ITableConditionProvider conditionProvider = qContext.getTableConditionProvider();
        if (conditionProvider != null && queryTable.getTableType() != DataEngineConsts.QueryTableType.PERIOD) {
            String realTableName = this.getTableName(qContext, queryTable);
            Map<String, String> argMap = conditionProvider.getTableCondition(qContext.getExeContext(), realTableName);
            if (argMap != null) {
                Set<Map.Entry<String, String>> entrySet = argMap.entrySet();
                for (Map.Entry entry : entrySet) {
                    String fieldName = (String)entry.getKey();
                    String value = (String)entry.getValue();
                    this.appendToCondition(qContext, null, this.whereCondition, tableAlias, fieldName, value, 6, this.whereNeedAnd);
                }
            }
        }
        if (this.recKeys != null && this.recKeys.size() > 0 && tableInfo.getRecField() != null) {
            this.whereNeedAnd = false;
            if (this.whereCondition.length() > 0) {
                this.whereCondition.insert(0, "(").append(")").append(" or ");
            }
            TempAssistantTable tempAssistantTable = qContext.getTempAssistantTables().get(tableInfo.getRecField().getName());
            this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, this.getTableAlias(qContext, this.getPrimaryTable()), tableInfo.getRecField().getName(), this.recKeys, DataTypesConvert.fieldTypeToDataType(tableInfo.getRecField().getColumnType()), this.whereNeedAnd);
            this.whereCondition.insert(0, "(").append(")");
            this.whereNeedAnd = true;
        }
        if (tableRestriction != null) {
            for (int i = 0; i < tableRestriction.size(); ++i) {
                ColumnModelDefine dimensionField;
                String keyName = tableRestriction.getName(i);
                Object keyValue = tableRestriction.getValue(i);
                if (keyValue == null) continue;
                if (!queryTable.getIsLj() && keyValue instanceof PeriodConditionNode) {
                    String periodValue;
                    PeriodConditionNode conditionNode = (PeriodConditionNode)keyValue;
                    IASTNode iASTNode = conditionNode.getFilterNode();
                    try {
                        keyValue = iASTNode.evaluate((IContext)qContext);
                    }
                    catch (SyntaxException e) {
                        qContext.getMonitor().exception((Exception)((Object)e));
                    }
                    if (keyName.equals("DATATIME") && (periodValue = (String)keyValue).length() == 4) {
                        keyValue = String.valueOf(qContext.getPeriodWrapper().getYear()) + conditionNode.getTypeCode() + periodValue;
                    }
                }
                if ((dimensionField = tableInfo.getDimensionField(keyName)) == null) continue;
                String string = dimensionField.getName();
                int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                if (StringUtils.isEmpty((String)string)) continue;
                if (queryTable.getIsLj() && keyValue instanceof PeriodConditionNode) {
                    PeriodConditionNode conditionNode = (PeriodConditionNode)keyValue;
                    if (this.whereNeedAnd) {
                        this.whereCondition.append(" and ");
                    }
                    PeriodConditionNode filterNode = conditionNode;
                    this.whereCondition.append(tableAlias).append(".").append(string);
                    this.whereCondition.append(" like '").append(qContext.getPeriodWrapper().getYear()).append(filterNode.getTypeCode()).append("%'");
                    this.whereCondition.append(" and ");
                    qContext.getCache().put("option.periodSqlField", tableAlias + "." + string);
                    try {
                        this.whereCondition.append(filterNode.getFilterNode().interpret((IContext)qContext, Language.SQL, (Object)new LJSQLInfo()));
                    }
                    catch (Exception e) {
                        qContext.getMonitor().exception(e);
                    }
                } else {
                    TempAssistantTable tempAssistantTable = qContext.findTempAssistantTable(keyName);
                    this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, string, keyValue, dimDataType, this.whereNeedAnd);
                }
                this.whereNeedAnd = true;
            }
        }
        this.whereNeedAnd = this.appendColFilter(qContext, exeContext.getCache().getDataModelDefinitionsCache(), this.whereCondition, this.whereNeedAnd);
        this.whereNeedAnd = this.appendVersionFilter(tableAlias, queryTable, tableInfo, this.whereCondition, this.whereNeedAnd);
    }

    protected void buildWhereCondition(QueryContext qContext) throws InterpretException {
        if (!this.needMemoryFilter && this.rowFilterNode != null) {
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

    private boolean appendVersionFilter(String tableAlias, QueryTable queryTable, TableModelRunInfo tableRunInfo, StringBuilder condition, boolean needAnd) throws SQLException {
        if (tableRunInfo.getTableModelDefine().getDictType() != TableDictType.ZIPPER) {
            return needAnd;
        }
        if (this.queryVersionDate.equals(Consts.ALL_VERSIONS_DATE) && this.queryVersionStartDate.equals(Consts.DATE_VERSION_INVALID_VALUE)) {
            return needAnd;
        }
        if (needAnd) {
            condition.append(" and ");
        }
        needAnd = true;
        com.jiuqi.nvwa.dataengine.util.DataEngineUtil.appendVersionFilter((Connection)this.queryParam.getConnection(), (IDatabase)this.queryParam.getDatabase(), (StringBuilder)condition, (Date)this.queryVersionDate, (String)tableAlias);
        return needAnd;
    }

    protected String getDateCompareSql(String alias, String fieldName, String compareOper, Date date) {
        return com.jiuqi.nvwa.dataengine.util.DataEngineUtil.getDateCompareSql((Connection)this.queryParam.getConnection(), (IDatabase)this.queryParam.getDatabase(), (String)alias, (String)fieldName, (String)compareOper, (Date)date);
    }

    protected String getTableName(QueryContext qContext, QueryTable queryTable) {
        return qContext.getPhysicalTableName(queryTable);
    }

    protected String getTableName(QueryTable queryTable) {
        if (this.designTimeData) {
            return String.format("%s%s", "DES_", queryTable.getTableName());
        }
        return queryTable.getTableName();
    }

    private boolean appendColFilter(QueryContext qContext, DataModelDefinitionsCache cache, StringBuilder condition, boolean needAnd) {
        String tableAlias;
        TableModelRunInfo tableRunInfo = cache.getTableInfo(this.getPrimaryTable().getTableName());
        needAnd = this.appendDimensionFilterByTable(qContext, this.getTableAlias(qContext, this.getPrimaryTable()), this.getPrimaryTable(), tableRunInfo, condition, needAnd);
        String primaryTableAlias = this.getTableAlias(qContext, this.getPrimaryTable());
        needAnd = this.appendColFilterByTable(qContext, primaryTableAlias, this.getPrimaryTable(), tableRunInfo, condition, needAnd);
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
                        FieldSqlConditionUtil.appendFieldValuesCondition(this.queryParam.getDatabase(), this.queryParam.getConnection(), condition, fieldName, queryField.getDataType(), valueList, tempAssistantTable);
                    }
                    condition.append(")");
                    continue;
                }
                FieldSqlConditionUtil.appendFieldValuesCondition(this.queryParam.getDatabase(), this.queryParam.getConnection(), condition, fieldName, queryField.getDataType(), valueList, tempAssistantTable);
            }
        }
        return needAnd;
    }

    protected void appendToCondition(QueryContext qContext, TempAssistantTable tempAssistantTable, StringBuilder sql, String tableAlias, String name, Object value, int dataType, boolean needAnd) {
        String fieldName = tableAlias + "." + name;
        List<Object> args = this.sqlSoftParse ? this.argValues : null;
        FieldSqlConditionUtil.appendFieldCondition(this.queryParam.getDatabase(), this.queryParam.getConnection(), sql, fieldName, dataType, value, needAnd, tempAssistantTable, args, this.argDataTypes, this.doPage);
    }

    protected void appendKeyCondition(QueryContext qContext, QueryTable queryTable, TableModelRunInfo tableInfo, String keyName, boolean isUnitDimension, String tableAlias, String keyFieldName, Object keyValue, int dataType, String linkAlias) {
        if (isUnitDimension) {
            List<String> dims = qContext.getDimsToBindRelation(tableInfo, queryTable, keyName, keyFieldName);
            if (keyValue instanceof List) {
                List mainDimValues = (List)keyValue;
                Map<String, Map<String, List<String>>> allEffectiveRelationDimValues = qContext.getAllEffectiveRelationDimValues(dims, mainDimValues, linkAlias, this.unitKeyMap);
                if (allEffectiveRelationDimValues != null && allEffectiveRelationDimValues.size() > 0) {
                    for (Map.Entry<String, Map<String, List<String>>> entry : allEffectiveRelationDimValues.entrySet()) {
                        String dim = entry.getKey();
                        String cacheKey = keyName + "_" + dim;
                        if (StringUtils.isNotEmpty((String)linkAlias)) {
                            cacheKey = cacheKey + "@" + linkAlias;
                        }
                        TwoKeyTempTable tempTable = qContext.getTwoKeyTempTable(cacheKey, entry.getValue());
                        String dimensionFieldName = tableInfo.getDimensionField(dim).getName();
                        this.fromJoinsTables.append(tempTable.getJoinSql(tableAlias + "." + keyFieldName, tableAlias + "." + dimensionFieldName));
                    }
                    TempAssistantTable tempAssistantTable = qContext.getTempAssistantTable(keyName, keyValue, dataType);
                    if (tempAssistantTable != null && tempAssistantTable instanceof KeyOrderTempTable) {
                        this.fromJoinsTables.append(tempAssistantTable.getJoinSql(tableAlias + "." + keyFieldName));
                    }
                } else {
                    TempAssistantTable tempAssistantTable = qContext.getTempAssistantTable(keyName, keyValue, dataType);
                    if (tempAssistantTable != null && (tempAssistantTable instanceof KeyOrderTempTable || DataEngineUtil.needJoinTempTable(this.queryParam.getDatabase()))) {
                        this.fromJoinsTables.append(tempAssistantTable.getJoinSql(tableAlias + "." + keyFieldName));
                    } else {
                        this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, keyFieldName, keyValue, dataType, this.whereNeedAnd);
                        this.whereNeedAnd = true;
                    }
                }
            } else {
                this.appendToCondition(qContext, null, this.whereCondition, tableAlias, keyFieldName, keyValue, dataType, this.whereNeedAnd);
                this.whereNeedAnd = true;
                Map<String, List<String>> relationDimValues = qContext.getEffectiveRelationDimValues(dims, keyValue.toString(), linkAlias, this.unitKeyMap);
                if (relationDimValues != null) {
                    for (Map.Entry<String, List<String>> entry : relationDimValues.entrySet()) {
                        String dimension = entry.getKey();
                        List<String> dimValues = entry.getValue();
                        String cacheKey = StringUtils.isEmpty((String)linkAlias) ? dimension : dimension + "@" + linkAlias;
                        TempAssistantTable tempAssistantTable = qContext.getTempAssistantTable(cacheKey, dimValues, dataType);
                        String dimensionFieldName = tableInfo.getDimensionField(dimension).getName();
                        if (tempAssistantTable != null && DataEngineUtil.needJoinTempTable(this.queryParam.getDatabase())) {
                            this.fromJoinsTables.append(tempAssistantTable.getJoinSql(tableAlias + "." + dimensionFieldName));
                            continue;
                        }
                        this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, dimensionFieldName, dimValues, dataType, this.whereNeedAnd);
                        this.whereNeedAnd = true;
                    }
                }
            }
        } else {
            TempAssistantTable tempAssistantTable = qContext.getTempAssistantTable(keyName, keyValue, dataType);
            if (tempAssistantTable != null && DataEngineUtil.needJoinTempTable(this.queryParam.getDatabase())) {
                this.fromJoinsTables.append(tempAssistantTable.getJoinSql(tableAlias + "." + keyFieldName));
            } else {
                this.appendToCondition(qContext, tempAssistantTable, this.whereCondition, tableAlias, keyFieldName, keyValue, dataType, this.whereNeedAnd);
                this.whereNeedAnd = true;
            }
        }
    }

    protected void appendValue(StringBuilder sql, Object value, int dataType) {
        List<Object> args = this.sqlSoftParse ? this.argValues : null;
        FieldSqlConditionUtil.appendValue(this.queryParam.getDatabase(), this.queryParam.getConnection(), sql, dataType, value, args, this.argDataTypes, this.doPage);
    }

    protected void appendQueryTable(QueryContext qContext, StringBuilder sql, QueryTable table) {
        sql.append(" ").append(this.getTableName(qContext, table));
        sql.append(" ");
        sql.append(this.getTableAlias(qContext, table));
    }

    private DataSet<QueryField> queryByJDBC(QueryContext qContext, String mainQuerySql) throws SQLException, DataSetException {
        Connection conn = this.queryParam.getConnection();
        Object[] args = this.argValues == null ? null : this.argValues.toArray();
        MemoryDataSet<QueryField> dataSet = DataEngineUtil.queryMemoryDataSet(conn, this.getTableName(this.getPrimaryTable()), mainQuerySql, args, qContext.getMonitor());
        this.getDataReader(qContext).setDataSet(dataSet);
        return dataSet;
    }

    protected String getTableAlias(QueryContext qContext, QueryTable table) {
        if (this.queryRegion.isFloatStreamCalc()) {
            if (table.equals(this.getPrimaryTable())) {
                return "t0";
            }
            return "t" + String.valueOf(this.leftJoinTables.indexOf(table) + 1);
        }
        String tableAlias = qContext.getQueryTableAliaMap().get(table);
        if (tableAlias == null) {
            tableAlias = table.getAlias();
        }
        return tableAlias;
    }

    protected void initDBQueryExecutor(QueryContext context) throws ParseException {
        TableModelRunInfo tableInfo;
        DataModelDefinitionsCache dataModelDefinitionsCache = context.getExeContext().getCache().getDataModelDefinitionsCache();
        if (context.getQueryExecutorProvider() != null) {
            String tableDefineCode = dataModelDefinitionsCache.getTableDefineCode(this.getPrimaryTable().getTableName());
            this.dbQueryExecutor = context.getQueryExecutorProvider().getDBQueryExecutor(tableDefineCode);
        } else if (context.isEnableNrdb() && this.getPrimaryTable() != null && this.getPrimaryTable().isCommonDataTable() && (tableInfo = dataModelDefinitionsCache.getTableInfo(this.getPrimaryTable().getTableName())).getTableModelDefine().isSupportNrdb()) {
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

    public void setSql(String sql) {
        this.sql = sql;
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
        if (this.getPrimaryTable() != null) {
            QueryFields queryFields = this.queryRegion.getTableFields(this.getPrimaryTable());
            ArrayList<OrderByItem> subOrders = this.getOrderItems(this.orderByItems, queryFields, this.getPrimaryTable(), queryContext);
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

    public boolean isMultiFloatCheck() {
        return this.isMultiFloatCheck;
    }

    public void setMultiFloatCheck(boolean isMultiFloatCheck) {
        this.isMultiFloatCheck = isMultiFloatCheck;
    }

    public boolean sumDatas() {
        return this.isMultiFloatCheck && this.primaryTable.getTableDimensions().contains("RECORDKEY");
    }

    public void setNeedMemoryFilter(boolean needMemoryFilter) {
        if (this.dbQueryExecutor != null) {
            this.dbQueryExecutor.getQueryInfo().needMemoryFilter = needMemoryFilter;
        }
        this.needMemoryFilter = needMemoryFilter;
    }

    public int getBizkeyOrderFieldIndex() {
        if (this.dbQueryExecutor != null) {
            return this.dbQueryExecutor.getBizkeyOrderFieldIndex();
        }
        return this.bizkeyOrderFieldIndex;
    }

    public IASTNode getRowFilterNode() {
        return this.rowFilterNode;
    }

    public DBQueryExecutor getDbQueryExecutor() {
        return this.dbQueryExecutor;
    }

    public void setOrderByItems(ArrayList<OrderByItem> orderByItems) {
        if (orderByItems != null) {
            for (OrderByItem item : orderByItems) {
                if (!item.specified) continue;
                this.specifiedOrderByItem = item;
                break;
            }
        }
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

    public OrderByItem getSpecifiedOrderByItem() {
        return this.specifiedOrderByItem;
    }

    public void setSpecifiedOrderByItem(OrderByItem specifiedOrderByItem) {
        this.specifiedOrderByItem = specifiedOrderByItem;
    }
}

