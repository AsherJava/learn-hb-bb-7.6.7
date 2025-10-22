/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataQueryColumn;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryRegion;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.intf.impl.StreamJoinLoader;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.StringValueNodeConcat;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryBuilderBase;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QueryFilterValueClassify;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.SteamJoinDataSetReader;
import com.jiuqi.np.dataengine.setting.FieldsInfoImpl;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataQueryBuilder
extends QueryBuilderBase {
    private static final long serialVersionUID = -7832234618989763806L;
    private static final Logger logger = LoggerFactory.getLogger(DataQueryBuilder.class);
    protected QueryRegion queryRegion;
    protected String mainTableName;
    protected ArrayList<OrderByItem> orderByItems;
    protected QueryFilterValueClassify colValueFilters;
    protected boolean openUpdateOnly = false;
    protected QueryParam queryParam;
    protected IQuerySqlUpdater sqlUpdater;
    private boolean isStatic = false;
    protected int currentIndex = 0;
    protected QuerySqlBuilder querySqlBuilder;
    private boolean ignoreDataVersion;
    protected Map<String, TableModelDefine> versionTables = new HashMap<String, TableModelDefine>();
    protected boolean supportFullJoin = true;
    protected boolean errorLog = false;
    private boolean supportMemoryJoin = true;
    protected boolean ignoreDefaultOrderBy = false;
    protected boolean queryModule;

    public void buildQuery(QueryContext qContext, CommonQueryImpl queryInfo, boolean resultReadOnly) throws ExpressionException, ParseException, InterpretException, SQLException {
        this.sqlUpdater = queryInfo.getQuerySqlUpdater();
        this.ignoreDataVersion = queryInfo.getIgnoreDataVersion();
        this.queryModule = qContext.isQueryModule();
        IDatabase database = this.queryParam.getDatabase();
        if (StringUtils.isNotEmpty((String)this.mainTableName) && !database.isDatabase("MYSQL") && database.getDescriptor().supportFullJoin()) {
            this.supportMemoryJoin = false;
        }
        this.table = resultReadOnly ? new ReadonlyTableImpl(qContext, queryInfo.getMasterKeys(), queryInfo.getColumns().size()) : this.createDataTable(qContext, queryInfo);
        this.doParseQuery(qContext, queryInfo);
        this.setTableParams(qContext.getExeContext().getCache(), queryInfo);
        this.initSystemFields();
        this.initVersionFields(qContext);
        this.buildQueryRegion(qContext);
        if (this.queryRegion.getAllTableFields().isEmpty()) {
            return;
        }
        this.queryRegion.getSqlBuilder().setSqlJoinProvider(queryInfo.getSqlJoinProvider());
        this.buildQuerySql(qContext);
    }

    protected DataTableImpl createDataTable(QueryContext qContext, CommonQueryImpl queryInfo) {
        return new DataTableImpl(qContext, queryInfo.getMasterKeys(), queryInfo.getColumns().size());
    }

    protected void initSystemFields() {
        int fieldCount = this.table.getFieldsInfo().getFieldCount();
        FieldsInfoImpl fieldsInfoImpl = this.table.fieldsInfoImpl;
        FieldsInfoImpl systemFields = new FieldsInfoImpl(this.table.getFieldsInfo().getFieldCount());
        for (int fieldIndex = 0; fieldIndex < fieldCount; ++fieldIndex) {
            FieldDefine fieldDefine = fieldsInfoImpl.getFieldDefine(fieldIndex);
            if (fieldDefine != null) {
                systemFields.setupField(fieldIndex, fieldDefine);
                continue;
            }
            FieldType fieldType = fieldsInfoImpl.getDataType(fieldIndex);
            systemFields.setupField(fieldIndex, fieldType);
        }
        this.table.systemFieldsInfo = systemFields;
    }

    protected void initVersionFields(QueryContext qContext) throws ParseException, ExpressionException {
        if (this.versionTables.size() <= 0) {
            return;
        }
        ExecutorContext executorContext = qContext.getExeContext();
        DataModelDefinitionsCache dataDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
        FieldsInfoImpl fieldsInfoImpl = this.table.systemFieldsInfo;
        ArrayList<FieldDefine> insertFields = new ArrayList<FieldDefine>(8);
        for (Map.Entry<String, TableModelDefine> versionTable : this.versionTables.entrySet()) {
            ColumnModelDefine beginVersion = dataDefinitionsCache.getFieldByCodeInTable("VALIDTIME", versionTable.getKey());
            FieldDefine beginField = dataDefinitionsCache.getFieldDefine(beginVersion);
            if (beginVersion != null && fieldsInfoImpl.indexOf(beginField) < 0) {
                insertFields.add(beginField);
            }
            ColumnModelDefine endVersion = dataDefinitionsCache.getFieldByCodeInTable("INVALIDTIME", versionTable.getKey());
            FieldDefine versionField = dataDefinitionsCache.getFieldDefine(endVersion);
            if (endVersion == null || fieldsInfoImpl.indexOf(versionField) >= 0) continue;
            insertFields.add(versionField);
        }
        if (insertFields.size() <= 0) {
            return;
        }
        int fieldIndex = this.orderByIndex - (this.rowFilterNode != null ? 1 : 0);
        fieldsInfoImpl.appendFields(insertFields.size());
        for (FieldDefine insertField : insertFields) {
            QueryField queryField = executorContext.getCache().extractQueryField(executorContext, insertField, null, null);
            DynamicDataNode fieldNode = new DynamicDataNode(queryField);
            fieldsInfoImpl.setupField(fieldIndex, insertField);
            this.table.queryfields.add(fieldIndex, queryField);
            this.queryNodes.add(fieldIndex, fieldNode);
            ++fieldIndex;
        }
    }

    protected void setTableParams(DefinitionsCache cache, CommonQueryImpl queryImpl) {
        this.table.setDataQueryBuilder(this);
        this.table.setDefinitionsCache(cache);
        this.table.setDesignTimeData(queryImpl.getDesignTimeData());
        this.table.setDataValidateProvider(queryImpl.getDataValidateProvider());
        this.table.setRowFilterNode(this.rowFilterNode, queryImpl.getRowFilter());
        this.table.setColFilterValues(queryImpl.getColFilterValues());
        if (queryImpl.getQueryVersionDate() != null) {
            this.table.setQueryVersionDate(queryImpl.getQueryVersionDate());
        }
        if (queryImpl.getQueryVersionStartDate() != null) {
            this.table.setQueryVersionStartDate(queryImpl.getQueryVersionStartDate());
        }
        this.recKeys = queryImpl.getRecKeys();
    }

    protected void buildQueryRegion(QueryContext qContext) {
        QueryFields queryFields = this.getQueryFields();
        DimensionSet masterDimensions = this.table.getMasterDimensions();
        QueryTable mainTable = null;
        if (this.mainTableName == null) {
            for (QueryField queryField : queryFields) {
                if (mainTable != null && queryField.getTable().getTableDimensions().size() <= mainTable.getTableDimensions().size()) continue;
                mainTable = queryField.getTable();
            }
        }
        if (mainTable != null) {
            this.mainTableName = mainTable.getTableName();
            masterDimensions = mainTable.getTableDimensions();
        }
        this.queryRegion = new QueryRegion(masterDimensions);
        this.queryRegion.addQueryFields(queryFields);
        this.queryRegion.setLookupItems(this.lookupItems);
        QuerySqlBuilder querySqlBuilder = this.getSqlBuilder(qContext);
        this.queryRegion.setSqlBuilder(querySqlBuilder);
        querySqlBuilder.setUseDefaultOrderBy(true);
        DimensionSet rowDimensions = queryFields.getExecRegion(masterDimensions).getDimensions();
        this.table.setRowDimensions(rowDimensions);
    }

    protected void buildQuerySql(QueryContext qContext) {
        QuerySqlBuilder mainSqlBuilder = this.queryRegion.getSqlBuilder();
        mainSqlBuilder.setQueryParam(this.queryParam);
        mainSqlBuilder.setQueryRegion(this.queryRegion);
        mainSqlBuilder.setMasterDimensions(this.table.getMasterDimensions());
        mainSqlBuilder.setQueryVersionStartDate(this.table.getQueryVersionStartDate());
        mainSqlBuilder.setQueryVersionDate(this.table.getQueryVersionDate());
        mainSqlBuilder.setDesignTimeData(this.table.getDesignTimeData());
        mainSqlBuilder.setPrimaryTableName(this.mainTableName);
        mainSqlBuilder.setIgnoreDataVersion(this.ignoreDataVersion);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runQuery(QueryContext qContext, int rowCount, int rowIndex) throws Exception {
        String mainQuerySql;
        TableModelRunInfo tableInfo;
        this.currentIndex = 0;
        if (this.queryRegion.getAllTableFields().isEmpty()) {
            DataRowImpl dataRow = this.createRowImpl(qContext.getMasterKeys());
            for (int i = 0; i < this.queryNodes.size(); ++i) {
                IASTNode node = (IASTNode)this.queryNodes.get(i);
                Object value = this.nodeEvaluate(qContext, node);
                dataRow.getRowDatas().add(value);
            }
            return;
        }
        QuerySqlBuilder sqlBuilder = this.getSqlBuilder(qContext);
        boolean useDNASql = qContext.getExeContext().isUseDnaSql() && !sqlBuilder.isGroupingQuery() && !sqlBuilder.getDesignTimeData();
        this.table.setContext(qContext);
        sqlBuilder.setResultTable(this.table);
        sqlBuilder.setUseDNASql(useDNASql);
        sqlBuilder.setRowFilterNode(this.rowFilterNode);
        sqlBuilder.setOrderByItems(this.orderByItems);
        sqlBuilder.setColValueFilters(this.colValueFilters);
        sqlBuilder.setForUpdateOnly(this.openUpdateOnly);
        sqlBuilder.setRecKeys(this.recKeys);
        sqlBuilder.setQueryParam(this.queryParam);
        sqlBuilder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy && rowCount < 0);
        this.hasOrderJoin();
        sqlBuilder.doInit(qContext);
        QueryTable primaryTable = sqlBuilder.primaryTable;
        if (rowCount < 0) {
            qContext.setQueryRowStart(0);
        } else {
            qContext.setQueryRowStart(rowIndex);
            sqlBuilder.setDoPage(true);
        }
        boolean sqlSoftParse = !qContext.isDebug() && (this.sqlUpdater == null || this.sqlUpdater.supportSoftParse(primaryTable, sqlBuilder.getTableAlias(qContext, primaryTable)));
        sqlBuilder.setSqlSoftParse(sqlSoftParse);
        IUnitLeafFinder unitLeafFinder = qContext.getUnitLeafFinder();
        if (unitLeafFinder != null && (tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(primaryTable.getTableName())).getBizOrderField() != null) {
            unitLeafFinder = null;
        }
        if (qContext.isQueryModule()) {
            if (this.queryRegion.getAllTableFields().size() > 1 && !this.queryRegion.justLeftJoin(qContext) || qContext.needRightJoinDimTables() || unitLeafFinder != null) {
                this.doQueryAndMemeryPageJoin(qContext, rowCount, rowIndex);
            } else {
                mainQuerySql = sqlBuilder.buildSql(qContext);
                try {
                    this.queryData(qContext, sqlBuilder, mainQuerySql, rowCount, rowIndex);
                }
                finally {
                    this.queryParam.closeConnection();
                }
            }
        } else {
            if (unitLeafFinder != null) {
                this.doQueryAndMemeryJoin(qContext);
                return;
            }
            if (this.queryRegion.getAllTableFields().size() > 1) {
                this.checkSupportFullJoin(qContext);
                if (!this.supportFullJoin || this.queryRegion.hasDimensionTable() || this.queryRegion.getFieldCount() > 990 || this.canUseMemoryJoin(rowCount)) {
                    this.doQueryAndMemeryJoin(qContext);
                    return;
                }
            }
            mainQuerySql = sqlBuilder.buildSql(qContext);
            if (this.sqlUpdater != null) {
                mainQuerySql = this.sqlUpdater.updateQuerySql(primaryTable, sqlBuilder.getTableAlias(qContext, primaryTable), mainQuerySql);
                sqlBuilder.setSql(mainQuerySql);
            }
            try {
                this.queryData(qContext, sqlBuilder, mainQuerySql, rowCount, rowIndex);
            }
            finally {
                this.queryParam.closeConnection();
            }
        }
    }

    public void loadToDataRowReader(QueryContext qContext, IDataRowReader dataRowReader) throws UnsupportedOperationException, Exception {
        this.currentIndex = 0;
        if (this.queryRegion.getAllTableFields().isEmpty()) {
            throw new UnsupportedOperationException("\u6d41\u5f0f\u8bfb\u53d6\u63a5\u53e3\u4e0d\u652f\u6301\u9759\u6001\u67e5\u8be2");
        }
        QuerySqlBuilder sqlBuilder = this.getSqlBuilder(qContext);
        this.table.setContext(qContext);
        sqlBuilder.setResultTable(this.table);
        sqlBuilder.setRowFilterNode(this.rowFilterNode);
        sqlBuilder.setOrderByItems(this.orderByItems);
        sqlBuilder.setColValueFilters(this.colValueFilters);
        sqlBuilder.setForUpdateOnly(this.openUpdateOnly);
        sqlBuilder.setRecKeys(this.recKeys);
        sqlBuilder.setQueryParam(this.queryParam);
        sqlBuilder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
        sqlBuilder.doInit(qContext);
        QueryTable primaryTable = sqlBuilder.primaryTable;
        qContext.setQueryRowStart(0);
        boolean sqlSoftParse = !qContext.isDebug() && (this.sqlUpdater == null || this.sqlUpdater.supportSoftParse(primaryTable, sqlBuilder.getTableAlias(qContext, primaryTable)));
        sqlBuilder.setSqlSoftParse(sqlSoftParse);
        if (this.queryRegion.getAllTableFields().size() > 1) {
            throw new UnsupportedOperationException("\u6d41\u5f0f\u8bfb\u53d6\u63a5\u53e3\u4e0d\u652f\u6301\u901a\u7528\u8054\u8868\u67e5\u8be2");
        }
        if (sqlBuilder.getPrimaryTable().isDimensionTable()) {
            throw new UnsupportedOperationException("\u6d41\u5f0f\u8bfb\u53d6\u63a5\u53e3\u4e0d\u652f\u6301\u5b9e\u4f53\u6570\u636e\u67e5\u8be2");
        }
        String mainQuerySql = sqlBuilder.buildSql(qContext);
        if (this.sqlUpdater != null) {
            mainQuerySql = this.sqlUpdater.updateQuerySql(primaryTable, sqlBuilder.getTableAlias(qContext, primaryTable), mainQuerySql);
            sqlBuilder.setSql(mainQuerySql);
        }
        sqlBuilder.queryToDataRowReader(qContext, this, dataRowReader);
    }

    public void loadToDataRowReaderByStreamJoin(QueryContext qContext, IDataRowReader dataRowReader) throws UnsupportedOperationException, Exception {
        this.currentIndex = 0;
        if (this.queryRegion.getAllTableFields().isEmpty()) {
            DataRowImpl dataRow = this.createRowImpl(qContext.getMasterKeys());
            for (int i = 0; i < this.queryNodes.size(); ++i) {
                IASTNode node = (IASTNode)this.queryNodes.get(i);
                Object value = this.nodeEvaluate(qContext, node);
                dataRow.getRowDatas().add(value);
            }
            return;
        }
        QuerySqlBuilder sqlBuilder = this.getSqlBuilder(qContext);
        boolean useDNASql = qContext.getExeContext().isUseDnaSql() && !sqlBuilder.isGroupingQuery() && !sqlBuilder.getDesignTimeData();
        this.table.setContext(qContext);
        sqlBuilder.setResultTable(this.table);
        sqlBuilder.setUseDNASql(useDNASql);
        sqlBuilder.setColValueFilters(this.colValueFilters);
        sqlBuilder.setForUpdateOnly(this.openUpdateOnly);
        sqlBuilder.setRecKeys(this.recKeys);
        sqlBuilder.setQueryParam(this.queryParam);
        sqlBuilder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
        sqlBuilder.doInit(qContext);
        QueryTable primaryTable = sqlBuilder.primaryTable;
        qContext.setQueryRowStart(0);
        boolean sqlSoftParse = !qContext.isDebug() && (this.sqlUpdater == null || this.sqlUpdater.supportSoftParse(primaryTable, sqlBuilder.getTableAlias(qContext, primaryTable)));
        sqlBuilder.setSqlSoftParse(sqlSoftParse);
        this.queryRegion.getSqlBuilder().setDoPage(false);
        this.queryRegion.getSqlBuilder().setNeedMemoryFilter(true);
        this.queryRegion.setType(2);
        this.queryRegion.doInit(qContext);
        SteamJoinDataSetReader reader = new SteamJoinDataSetReader(qContext);
        qContext.setDataReader(reader);
        try (StreamJoinLoader loader = new StreamJoinLoader(qContext, this);){
            loader.loadToReader(dataRowReader, reader);
        }
    }

    private void checkSupportFullJoin(QueryContext qContext) {
        IDatabase database;
        IDatabase iDatabase = database = qContext == null ? null : qContext.getQueryParam().getDatabase();
        if (database != null && (database.isDatabase("MYSQL") || !database.getDescriptor().supportFullJoin())) {
            this.supportFullJoin = false;
        }
    }

    private void doQueryAndMemeryPageJoin(QueryContext qContext, int rowCount, int rowIndex) throws Exception {
        int endRowIndex = rowCount + rowIndex;
        this.queryRegion.getSqlBuilder().setDoPage(false);
        this.queryRegion.getSqlBuilder().setNeedMemoryFilter(true);
        this.queryRegion.setType(1);
        this.queryRegion.doInit(qContext);
        MemoryDataSetReader reader = this.queryRegion.runQuery(qContext, this.sqlUpdater);
        if (qContext.needRightJoinDimTables()) {
            HashSet<DimensionValueSet> dims = new HashSet<DimensionValueSet>();
            DimensionValueSet dimValues = new DimensionValueSet(qContext.getMasterKeys());
            ExpressionUtils.expandDims(dimValues, dims);
            reader.expandByDims(dims);
        }
        this.queryRegion.loadLeftJoinDatas(qContext, !this.queryModule);
        while (reader.next()) {
            this.rowKeys = reader.getCurrentRowKey();
            DimensionValueSet currentMarsterKeys = qContext.getCurrentMasterKey();
            for (int i = 0; i < this.rowKeys.size(); ++i) {
                String dimName = this.rowKeys.getName(i);
                if (!currentMarsterKeys.hasValue(dimName)) continue;
                currentMarsterKeys.setValue(dimName, this.rowKeys.getValue(i));
            }
            try {
                if (this.rowFilterNode != null && !this.rowFilterNode.judge((IContext)qContext)) {
                    continue;
                }
            }
            catch (Exception e) {
                if (!this.errorLog) {
                    logger.warn("rowFilter\u6267\u884c\u51fa\u9519", e);
                }
                this.errorLog = true;
            }
            DataRowImpl dataRow = this.createRowImpl(reader.getCurrentRowKey());
            ArrayList<Object> rowDatas = dataRow.getRowDatas();
            for (int i = 0; i < this.queryNodes.size(); ++i) {
                IASTNode node = (IASTNode)this.queryNodes.get(i);
                Object value = this.nodeEvaluate(qContext, node);
                rowDatas.add(value);
            }
            dataRow.buildRow();
        }
        this.doMemorySort(qContext);
        int totalCount = reader.size();
        this.table.setTotalCount(totalCount);
        if (rowIndex >= 0 && endRowIndex > 0) {
            if (endRowIndex > totalCount) {
                endRowIndex = totalCount;
            }
            if (endRowIndex > rowIndex) {
                this.table.setAllDataRows(this.table.getAllDataRows().subList(rowIndex, endRowIndex));
            }
        }
    }

    protected void doQueryAndMemeryJoin(QueryContext qContext) throws Exception {
        this.queryRegion.setType(1);
        this.queryRegion.doInit(qContext);
        MemoryDataSetReader reader = this.queryRegion.runQuery(qContext, this.sqlUpdater);
        this.queryRegion.loadLeftJoinDatas(qContext, !this.queryModule);
        while (reader.next()) {
            this.rowKeys = reader.getCurrentRowKey();
            DimensionValueSet currentMarsterKeys = qContext.getCurrentMasterKey();
            for (int i = 0; i < this.rowKeys.size(); ++i) {
                String dimName = this.rowKeys.getName(i);
                if (!currentMarsterKeys.hasValue(dimName)) continue;
                currentMarsterKeys.setValue(dimName, this.rowKeys.getValue(i));
            }
            if (this.rowFilterNode != null) {
                try {
                    if (!this.rowFilterNode.judge((IContext)qContext)) {
                        continue;
                    }
                }
                catch (Exception e) {
                    if (!this.errorLog) {
                        logger.warn("rowFilter\u6267\u884c\u51fa\u9519", e);
                    }
                    this.errorLog = true;
                }
            }
            if (!this.checkAuth(qContext, this.rowKeys)) continue;
            DataRowImpl dataRow = this.createRowImpl(this.rowKeys);
            ArrayList<Object> rowDatas = dataRow.getRowDatas();
            for (int i = 0; i < this.queryNodes.size(); ++i) {
                IASTNode node = (IASTNode)this.queryNodes.get(i);
                Object value = this.nodeEvaluate(qContext, node);
                rowDatas.add(value);
            }
            if (this.table.groupingFlagColIndex >= 0) {
                int groupingFlag = reader.getRowDatas().getGroup_flag();
                dataRow.setGroupingFlag(groupingFlag);
                dataRow.getRowKeys().setValue("GroupingDeep", null);
                this.initNoneGatherValues(rowDatas, groupingFlag);
            }
            dataRow.buildRow();
        }
        int totalCount = reader.size();
        this.table.setTotalCount(totalCount);
        this.doMemorySort(qContext);
        this.addStaticRow(qContext, totalCount > 0);
    }

    protected void doMemorySort(QueryContext qContext) {
        if (qContext.isQueryModule()) {
            return;
        }
        try {
            final ArrayList<Integer> orderByIndex = new ArrayList<Integer>();
            final ArrayList<Boolean> orderDesc = new ArrayList<Boolean>();
            if (this.orderByItems != null) {
                for (OrderByItem item : this.orderByItems) {
                    int index;
                    if (item.field == null || (index = this.table.queryfields.indexOf(item.field)) < 0) continue;
                    orderByIndex.add(index);
                    orderDesc.add(item.descending);
                }
            } else {
                IFieldsInfo fieldsInfo = this.table.getFieldsInfo();
                for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
                    ColumnModelDefine columnModelDefine;
                    TableModelDefine tableModel;
                    DataModelDefinitionsCache dataDefinitionsCache;
                    TableModelRunInfo tableRunInfo;
                    FieldDefine fieldDefine = fieldsInfo.getFieldDefine(i);
                    if (fieldDefine == null || !(tableRunInfo = (dataDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache()).getTableInfo((tableModel = dataDefinitionsCache.getTableModel(columnModelDefine = dataDefinitionsCache.getColumnModel(fieldDefine))).getName())).isKeyField(columnModelDefine.getCode())) continue;
                    orderByIndex.add(i);
                    orderDesc.add(false);
                }
            }
            Collections.sort(this.table.getAllDataRows(), new Comparator<DataRowImpl>(){

                @Override
                public int compare(DataRowImpl o1, DataRowImpl o2) {
                    int result = o1.getGroupingFlag() - o2.getGroupingFlag();
                    if (result != 0) {
                        return 0 - result;
                    }
                    for (int i = 0; i < orderByIndex.size(); ++i) {
                        try {
                            int index = (Integer)orderByIndex.get(i);
                            boolean desc = (Boolean)orderDesc.get(i);
                            result = o1.getValue(index).compareTo(o2.getValue(index));
                            if (result == 0) continue;
                            if (desc) {
                                result = 0 - result;
                            }
                            return result;
                        }
                        catch (DataTypeException dataTypeException) {
                            // empty catch block
                        }
                    }
                    return 0;
                }
            });
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean canUseMemoryJoin(int rowCount) {
        boolean hasOrderJoin = this.queryRegion.isNeedOrderJoin();
        return (this.queryRegion.getLookupItems() == null || this.queryRegion.getLookupItems().isEmpty()) && this.queryRegion.getSqlBuilder().getSqlJoinProvider() == null && this.supportMemeryJoin() && this.queryRegion.getAllTableFields().size() > 1 && (rowCount < 0 && this.orderByItems == null || hasOrderJoin) && this.rowFilterNode == null;
    }

    public void hasOrderJoin() {
        int orderTableCount = 0;
        for (Map.Entry<QueryTable, QueryFields> queryTable : this.queryRegion.getAllTableFields().entrySet()) {
            if (!queryTable.getKey().getTableDimensions().contains("RECORDKEY")) continue;
            ++orderTableCount;
        }
        this.queryRegion.setNeedOrderJoin(orderTableCount >= 2);
    }

    protected boolean needCheckAuth(Set<String> authIds, Object dimValue) {
        if (dimValue != null) {
            if (dimValue instanceof List) {
                List dimObjects = (List)dimValue;
                ArrayList<String> dimList = new ArrayList<String>();
                for (Object currentValue : dimObjects) {
                    dimList.add(currentValue.toString());
                }
                return !authIds.containsAll(dimList);
            }
            return !authIds.contains(dimValue.toString());
        }
        return true;
    }

    public QuerySqlBuilder getSqlBuilder(QueryContext qContext) {
        if (this.querySqlBuilder == null) {
            this.querySqlBuilder = new QuerySqlBuilder();
        }
        return this.querySqlBuilder;
    }

    protected Object nodeEvaluate(QueryContext qContext, IASTNode node) {
        Object value = null;
        try {
            value = node.evaluate((IContext)qContext);
        }
        catch (SyntaxException e) {
            if (!e.isCanIgnore()) {
                logger.error(e.getMessage(), e);
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return value;
    }

    protected void queryData(QueryContext qContext, QuerySqlBuilder sqlBuilder, String mainQuerySql, int rowCount, int rowIndex) throws Exception {
        int endRowIndex = rowCount + rowIndex;
        if (sqlBuilder.getPrimaryTable().isDimensionTable()) {
            MemoryDataSetReader reader = this.queryRegion.runQuery(qContext, this.sqlUpdater);
            while (reader.next()) {
                this.loadRowData(qContext, sqlBuilder, reader, rowIndex, endRowIndex, null);
            }
            this.addStaticRow(qContext, this.currentIndex > 0);
            this.table.setTotalCount(this.currentIndex);
            return;
        }
        IQueryFieldDataReader reader = qContext.getDataReader();
        if (!sqlBuilder.isDoPage()) {
            if (!this.openUpdateOnly) {
                sqlBuilder.queryToDataReader(qContext, reader);
            }
            while (reader.next()) {
                this.loadRowData(qContext, sqlBuilder, reader, rowIndex, endRowIndex, null);
            }
            this.addStaticRow(qContext, this.currentIndex > 0);
            this.table.setTotalCount(this.currentIndex);
        } else if (!sqlBuilder.isNeedMemoryFilter()) {
            int totalCount = sqlBuilder.queryToDataReader(qContext, reader, rowIndex, rowCount);
            while (reader.next()) {
                this.loadRowData(qContext, sqlBuilder, reader, -1, Integer.MAX_VALUE, null);
            }
            this.table.setTotalCount(totalCount);
            this.addStaticRow(qContext, totalCount > 0);
        } else {
            sqlBuilder.queryToDataReader(qContext, reader, -1, -1);
            while (reader.next()) {
                this.loadRowData(qContext, sqlBuilder, reader, rowIndex, endRowIndex, null);
            }
            this.table.setTotalCount(this.currentIndex);
            this.addStaticRow(qContext, this.currentIndex > 0);
        }
    }

    protected void addStaticRow(QueryContext qContext, boolean hasRecord) {
        if (this.isStatic && this.table.getCount() == 0) {
            this.rowKeys = new DimensionValueSet(qContext.getMasterKeys());
            DataRowImpl dataRow = this.createRowImpl(this.rowKeys);
            ArrayList<Object> rowDatas = dataRow.getRowDatas();
            for (int i = 0; i < this.queryNodes.size(); ++i) {
                IASTNode node = (IASTNode)this.queryNodes.get(i);
                Object value = this.nodeEvaluate(qContext, node);
                rowDatas.add(value);
            }
            if (!hasRecord) {
                this.table.setTotalCount(1);
            }
        }
    }

    public DataRowImpl loadRowData(QueryContext qContext, QuerySqlBuilder sqlBuilder, IQueryFieldDataReader reader, int beginRowIndex, int endRowIndex, IDataRowReader dataRowReader) throws Exception {
        Object recObject;
        if (dataRowReader != null) {
            return this.loadRowDataForReader(qContext, sqlBuilder, reader, dataRowReader);
        }
        if (sqlBuilder.getPrimaryTable().isDimensionTable()) {
            MemoryDataSetReader memoryDataSetReader = (MemoryDataSetReader)reader;
            this.rowKeys = memoryDataSetReader.getCurrentRowKey();
        } else {
            this.rowKeys = sqlBuilder.buildRowKeys(qContext.getMasterKeys(), reader);
        }
        if (!this.checkRowKey(qContext, this.rowKeys)) {
            return null;
        }
        DimensionValueSet currentMarsterKeys = qContext.getCurrentMasterKey();
        for (int i = 0; i < this.rowKeys.size(); ++i) {
            String dimName = this.rowKeys.getName(i);
            if (!currentMarsterKeys.hasValue(dimName)) continue;
            currentMarsterKeys.setValue(dimName, this.rowKeys.getValue(i));
        }
        if (sqlBuilder.isNeedMemoryFilter()) {
            try {
                if (!this.rowFilterNode.judge((IContext)qContext)) {
                    return null;
                }
            }
            catch (Exception e) {
                if (!this.errorLog) {
                    logger.warn("rowFilter\u6267\u884c\u51fa\u9519", e);
                }
                this.errorLog = true;
            }
        }
        if (!this.checkAuth(qContext, this.rowKeys)) {
            return null;
        }
        ++this.currentIndex;
        if (sqlBuilder.isNeedMemoryFilter() && endRowIndex - beginRowIndex > 0 && (this.currentIndex < beginRowIndex + 1 || this.currentIndex > endRowIndex)) {
            return null;
        }
        DataRowImpl dataRow = this.createRowImpl(this.rowKeys);
        ArrayList<Object> rowDatas = dataRow.getRowDatas();
        for (int i = 0; i < dataRow.getSystemFields().getFieldCount(); ++i) {
            IASTNode node = (IASTNode)this.queryNodes.get(i);
            Object value = this.nodeEvaluate(qContext, node);
            rowDatas.add(value);
        }
        if (this.table.groupingFlagColIndex >= 0) {
            int groupingFlag = Convert.toInt((Object)reader.readData(this.table.groupingFlagColIndex));
            dataRow.setGroupingFlag(groupingFlag);
            if (groupingFlag == -1) {
                dataRow.setGroupTreeDeep(-1);
            } else {
                dataRow.setGroupTreeDeep((groupingFlag + 1) / 2);
            }
            this.initNoneGatherValues(rowDatas, groupingFlag);
        }
        if (this.table.getRecKeyIndex() >= 0 && (recObject = reader.readData(this.table.getRecKeyIndex())) != null) {
            if (this.table.getRecType() == 33) {
                UUID recKey = Convert.toUUID((Object)recObject);
                dataRow.setRecKey(recKey.toString());
            } else {
                dataRow.setRecKey(recObject.toString());
            }
        }
        dataRow.buildRow();
        return dataRow;
    }

    public DataRowImpl loadRowDataForReader(QueryContext qContext, QuerySqlBuilder sqlBuilder, IQueryFieldDataReader reader, IDataRowReader dataRowReader) throws Exception {
        if (sqlBuilder.getPrimaryTable().isDimensionTable()) {
            MemoryDataSetReader memoryDataSetReader = (MemoryDataSetReader)reader;
            this.rowKeys = memoryDataSetReader.getCurrentRowKey();
        } else if (dataRowReader.needRowKey()) {
            this.rowKeys = sqlBuilder.buildRowKeys(qContext.getMasterKeys(), reader);
            DimensionValueSet currentMarsterKeys = qContext.getCurrentMasterKey();
            for (int i = 0; i < this.rowKeys.size(); ++i) {
                String dimName = this.rowKeys.getName(i);
                if (!currentMarsterKeys.hasValue(dimName)) continue;
                currentMarsterKeys.setValue(dimName, this.rowKeys.getValue(i));
            }
        } else {
            this.rowKeys = null;
            sqlBuilder.resetCurrentMasterKey(qContext, reader);
        }
        if (sqlBuilder.isNeedMemoryFilter()) {
            try {
                if (this.rowFilterNode != null && !this.rowFilterNode.judge((IContext)qContext)) {
                    return null;
                }
            }
            catch (Exception e) {
                if (!this.errorLog) {
                    logger.warn("rowFilter\u6267\u884c\u51fa\u9519", e);
                }
                this.errorLog = true;
            }
        }
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        DataRowImpl dataRow = new DataRowImpl(this.rowKeys, rowDatas);
        for (int i = 0; i < this.table.getSystemFields().getFieldCount(); ++i) {
            IASTNode node = (IASTNode)this.queryNodes.get(i);
            Object value = this.nodeEvaluate(qContext, node);
            rowDatas.add(value);
        }
        dataRow.buildRow();
        return dataRow;
    }

    protected boolean checkAuth(QueryContext qContext, DimensionValueSet rowKeys) {
        return true;
    }

    protected boolean checkRowKey(QueryContext qContext, DimensionValueSet rowKey) {
        for (int i = 0; i < rowKey.size(); ++i) {
            if (rowKey.getValue(i) != null) continue;
            StringBuilder msg = new StringBuilder();
            msg.append("\u884c\u7ef4\u5ea6\u503c\u7f3a\u5931:rowKey=").append(rowKey).append(",\u7f3a\u5931\u4e86").append(rowKey.getName(i));
            qContext.getMonitor().exception(new DataValidateException(msg.toString()));
            return false;
        }
        return true;
    }

    protected DataRowImpl createRowImpl(DimensionValueSet rowKeys) {
        DataRowImpl dataRow = this.table.addDataRow(rowKeys);
        return dataRow;
    }

    protected static String getQueryItemDesc(DataQueryColumn column) {
        String result = "";
        if (column.getField() != null) {
            result = column.getField().getTitle();
        } else if (column.getExpression() != null) {
            result = String.format("\u8868\u8fbe\u5f0f\u201c%1$s\u201d", column.getExpression());
        }
        return result;
    }

    protected final void ResetParseInfo() {
        this.queryNodes.clear();
        this.rowFilterNode = null;
        this.orderByIndex = -1;
        this.singleOrderByFieldIndex = -1;
        this.orderByIsDescending.clear();
        this.colValueFilters = new QueryFilterValueClassify();
        this.table.getFieldsInfo().reset();
    }

    protected final void doParseQuery(QueryContext qContext, CommonQueryImpl queryInfo) throws ParseException, ExpressionException, SQLException, InterpretException {
        ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(qContext.getExeContext());
        if (queryInfo.getFormName() != null) {
            qContext.setDefaultGroupName(queryInfo.getFormName());
        }
        for (int i = 0; i < queryInfo.getColumns().size(); ++i) {
            DataQueryColumn column = queryInfo.getColumns().get(i);
            ArrayList<Object> filterValues = queryInfo.getColFilterValues().get(i);
            this.parseColumn(qContext, parser, column, filterValues);
        }
        String rowFilter = queryInfo.getRowFilter();
        if (StringUtils.isEmpty((String)rowFilter)) {
            this.rowFilterNode = null;
        } else {
            try {
                this.rowFilterNode = parser.parseCond(rowFilter, (IContext)qContext);
                this.queryNodes.add(this.rowFilterNode);
            }
            catch (Exception e) {
                logger.error("rowFilter\u89e3\u6790\u51fa\u9519: " + rowFilter, e);
                throw e;
            }
        }
        this.orderByIndex = this.queryNodes.size();
        this.orderByIsDescending = new ArrayList();
        for (int i = 0; i < queryInfo.getOrderColumns().size(); ++i) {
            DataQueryColumn orderColumn = queryInfo.getOrderColumns().get(i);
            orderColumn.setIndex(queryInfo.getColumns().size() + i);
            this.orderByIsDescending.add(orderColumn.isDescending());
            IASTNode colNode = this.parseColumn(qContext, parser, orderColumn, null);
            if (colNode == null) continue;
            if (!(colNode instanceof DynamicDataNode) && !colNode.support(Language.SQL)) {
                throw new ExpressionException(DataQueryBuilder.getQueryItemDesc(orderColumn) + "\u6ca1\u6709\u5b9e\u9645\u5b58\u50a8\u5217\uff0c\u4e5f\u4e0d\u80fd\u8f6c\u6362\u6210SQL\uff0c\u4e0d\u80fd\u8bbe\u7f6e\u4e3a\u6392\u5e8f\u5217");
            }
            if (this.orderByItems == null) {
                this.orderByItems = new ArrayList();
            }
            OrderByItem orderByItem = new OrderByItem();
            orderByItem.descending = orderColumn.isDescending();
            orderByItem.specified = orderColumn.isSpecified();
            if (colNode instanceof DynamicDataNode) {
                orderByItem.field = ((DynamicDataNode)colNode).getQueryField();
            } else if (colNode.getChild(0) instanceof DynamicDataNode) {
                orderByItem.field = ((DynamicDataNode)colNode.getChild(0)).getQueryField();
            } else {
                orderByItem.expr = colNode;
            }
            this.orderByItems.add(orderByItem);
        }
    }

    private IASTNode parseColumn(QueryContext qContext, ReportFormulaParser parser, DataQueryColumn column, ArrayList<Object> filterValues) throws ExpressionException {
        Object colNode = null;
        ExecutorContext executorContext = qContext.getExeContext();
        try {
            DynamicDataNode fieldNode;
            FieldDefine field = column.getField();
            boolean lookUpNode = false;
            QueryField queryField = null;
            if (field != null) {
                queryField = executorContext.getCache().extractQueryField(executorContext, field, column.getPeriodModifier(), column.getDimensionRestriction());
                fieldNode = new DynamicDataNode(queryField);
                int type = column.getType();
                if (type == 3) {
                    fieldNode = this.buildLookupNode(executorContext, field, fieldNode, column);
                    lookUpNode = true;
                }
                colNode = fieldNode;
            } else if (StringUtils.isNotEmpty((String)column.getExpression())) {
                colNode = parser.parseEval(column.getExpression(), (IContext)qContext);
                fieldNode = null;
                IASTNode root = colNode.getChild(0);
                if (root instanceof CellDataNode) {
                    fieldNode = (DynamicDataNode)root.getChild(0);
                } else if (root instanceof DynamicDataNode) {
                    fieldNode = (DynamicDataNode)root;
                }
                if (fieldNode != null && fieldNode.getStatisticInfo() == null && !fieldNode.isShowDictTitle()) {
                    queryField = fieldNode.getQueryField();
                    if (queryField.getIsLj()) {
                        queryField = null;
                    }
                    if (queryField != null && queryField.getPeriodModifier() == null && queryField.getDimensionRestriction() == null) {
                        field = fieldNode.getDataLink() != null ? fieldNode.getDataLink().getField() : qContext.getExeContext().getCache().getDataModelDefinitionsCache().getColumnModelFinder().findFieldDefineByColumnId(queryField.getUID());
                    }
                }
            }
            if (!column.isOrderBy()) {
                if (field != null && !lookUpNode) {
                    TableModelDefine tableDefine;
                    DataModelDefinitionsCache dataModelCache = executorContext.getCache().getDataModelDefinitionsCache();
                    ColumnModelDefine columnModel = dataModelCache.getColumnModel(field);
                    this.table.fieldsInfoImpl.setupField(column.getIndex(), field);
                    if (!this.versionTables.containsKey(columnModel.getTableID()) && TableDictType.ZIPPER == (tableDefine = dataModelCache.findTable(columnModel.getTableID())).getDictType()) {
                        this.versionTables.put(columnModel.getTableID(), tableDefine);
                    }
                } else {
                    this.table.fieldsInfoImpl.setupField(column.getIndex(), DataTypesConvert.DataTypeToFieldType(colNode.getType((IContext)qContext)));
                }
            }
            this.table.queryfields.add(queryField);
            column.setParsedNode((IASTNode)colNode);
            this.queryNodes.add(colNode);
            if (filterValues != null) {
                if (this.colValueFilters == null) {
                    this.colValueFilters = new QueryFilterValueClassify();
                }
                if (queryField != null) {
                    this.colValueFilters.addSqlColFilterValues(queryField, filterValues);
                }
            }
        }
        catch (Exception e) {
            throw new ExpressionException("\u8868\u8fbe\u5f0f:" + column.getExpression(), e);
        }
        return colNode;
    }

    private DynamicDataNode buildLookupNode(ExecutorContext executorContext, FieldDefine keyField, DynamicDataNode fieldNode, DataQueryColumn column) throws ExpressionException, ParseException {
        FieldDefine valueField = column.getValueField();
        DataModelDefinitionsCache dataDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
        if (valueField != null) {
            ColumnModelDefine referKeyColumn = dataDefinitionsCache.getColumnModel(keyField);
            if (referKeyColumn.getReferColumnID() == null) {
                return fieldNode;
            }
            boolean isError = this.checkRelatedTable(dataDefinitionsCache, valueField.getOwnerTableKey(), referKeyColumn);
            if (isError) {
                return fieldNode;
            }
            QueryField queryField = executorContext.getCache().extractLookUpQueryField(executorContext, valueField, keyField.getKey(), null);
            DynamicDataNode relatedNode = new DynamicDataNode(queryField);
            fieldNode.setRelatedNode(relatedNode);
        } else {
            ColumnModelDefine referKeyColumn;
            EntityViewDefine entityViewDefine = column.getEntityView();
            if (entityViewDefine == null) {
                return fieldNode;
            }
            String tableKey = EntityUtils.getId((String)entityViewDefine.getEntityId());
            boolean isError = this.checkRelatedTable(dataDefinitionsCache, tableKey, referKeyColumn = dataDefinitionsCache.getColumnModel(keyField));
            if (isError) {
                return fieldNode;
            }
            ArrayList<ASTNode> concatNodes = new ArrayList<ASTNode>();
            if (concatNodes.size() <= 0) {
                return fieldNode;
            }
            StringValueNodeConcat concateNode = new StringValueNodeConcat(concatNodes);
            fieldNode.setRelatedNode(concateNode);
        }
        return fieldNode;
    }

    private boolean checkRelatedTable(DataModelDefinitionsCache dataDefinitionsCache, String tableKey, ColumnModelDefine keyField) {
        String referFieldKey = keyField.getReferColumnID();
        if (referFieldKey == null) {
            return true;
        }
        TableModelDefine tableModel = dataDefinitionsCache.getTableModel(keyField);
        TableModelRunInfo relatedTable = dataDefinitionsCache.getTableInfo(tableModel.getCode());
        if (relatedTable == null) {
            return true;
        }
        if (tableKey.equals(relatedTable.getTableModelDefine().getID())) {
            return false;
        }
        List<ColumnModelDefine> dimFields = relatedTable.getDimFields();
        referFieldKey = dimFields.get(0).getReferColumnID();
        if (dimFields.size() != 1 || referFieldKey == null) {
            return true;
        }
        return this.checkRelatedTable(dataDefinitionsCache, tableKey, dimFields.get(0));
    }

    protected void applyRowAuthority(QueryContext qContext, CommonQueryImpl queryInfo) throws ParseException, SQLException {
        if (!queryInfo.getFilterDataByAuthority()) {
            return;
        }
        Set<QueryTable> queryTables = this.queryRegion.getAllTableFields().keySet();
        HashSet<String> authTables = new HashSet<String>();
        DataModelDefinitionsCache dataDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        for (QueryTable queryTable : queryTables) {
            TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(queryTable.getTableName());
            List<ColumnModelDefine> dimFields = tableRunInfo.getDimFields();
            for (ColumnModelDefine dimField : dimFields) {
                TableModelDefine dimTable;
                ColumnModelDefine refField = this.getRefField(dataDefinitionsCache, dimField);
                if (refField == null || (dimTable = dataDefinitionsCache.findTable(refField.getTableID())) == null || authTables.contains(dimTable.getID())) continue;
                authTables.add(dimTable.getID());
            }
        }
    }

    private ColumnModelDefine getRefField(DataModelDefinitionsCache dataDefinitionsCache, ColumnModelDefine dimField) {
        String referFieldKey = dimField.getReferColumnID();
        if (referFieldKey == null) {
            return null;
        }
        ColumnModelDefine refField = dataDefinitionsCache.findField(referFieldKey);
        if (refField == null) {
            return null;
        }
        if (refField.getReferColumnID() == null) {
            return refField;
        }
        return this.getRefField(dataDefinitionsCache, refField);
    }

    @Override
    public QueryFields getQueryFields() {
        QueryFields queryFields = super.getQueryFields();
        if (this.orderByItems != null) {
            for (OrderByItem orderItem : this.orderByItems) {
                if (orderItem.field == null) continue;
                queryFields.add(orderItem.field);
            }
        }
        return queryFields;
    }

    public String getMainTableName() {
        return this.mainTableName;
    }

    public void setMainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }

    public void setOpenUpdateOnly(boolean openUpdateOnly) {
        this.openUpdateOnly = openUpdateOnly;
    }

    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    protected void initNoneGatherValues(ArrayList<Object> rowDatas, int groupingFlag) {
        HashSet<Integer> noneGatherCols;
        if (groupingFlag >= 0 && (noneGatherCols = this.table.getNoneGatherCols()) != null && noneGatherCols.size() > 0) {
            for (Integer colIndex : noneGatherCols) {
                rowDatas.set(colIndex, null);
            }
        }
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    protected boolean supportMemeryJoin() {
        return this.supportMemoryJoin;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dynamicQuery(QueryContext qContext, String mainSql, int rowCount, int rowIndex) throws Exception {
        QuerySqlBuilder sqlBuilder = this.getSqlBuilder(qContext);
        try {
            this.table.reset();
            this.queryData(qContext, sqlBuilder, mainSql, rowCount, rowIndex);
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    public void closeConnection() throws SQLException {
        this.queryParam.closeConnection();
    }

    public QueryParam getQueryParam() {
        return this.queryParam;
    }

    /*
     * Exception decompiling
     */
    public IndexItem queryRowIndex(DimensionValueSet rowKeys, QueryContext qContext) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 56[SIMPLE_IF_TAKEN]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
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

    public List<QueryTable> getAllTables() {
        return new ArrayList<QueryTable>(this.queryRegion.getAllTableFields().keySet());
    }

    public List<QuerySqlBuilder> getAllFullJoinSqlBuilders(QueryContext qContext) throws ParseException {
        return this.queryRegion.getAllFullJoinSqlBuilders();
    }

    public void setIgnoreDefaultOrderBy(boolean ignoreDefaultOrderBy) {
        this.ignoreDefaultOrderBy = ignoreDefaultOrderBy;
    }

    public IQuerySqlUpdater getSqlUpdater() {
        return this.sqlUpdater;
    }
}

