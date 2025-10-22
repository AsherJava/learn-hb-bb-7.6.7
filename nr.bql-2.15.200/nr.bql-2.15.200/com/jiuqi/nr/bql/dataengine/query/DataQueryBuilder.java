/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.AdHocEngineException
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IUnitLeafFinder
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.query.OrderByItem
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.query.QueryFilterValueClassify
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.bi.adhoc.engine.AdHocEngineException;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QueryFilterValueClassify;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bql.dataengine.IFieldsInfo;
import com.jiuqi.nr.bql.dataengine.impl.CommonQueryImpl;
import com.jiuqi.nr.bql.dataengine.impl.DataQueryColumn;
import com.jiuqi.nr.bql.dataengine.impl.DataRowImpl;
import com.jiuqi.nr.bql.dataengine.impl.ReadonlyTableImpl;
import com.jiuqi.nr.bql.dataengine.query.DimQueryInfo;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import com.jiuqi.nr.bql.dataengine.query.QueryBuilderBase;
import com.jiuqi.nr.bql.dataengine.query.QueryRegion;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;
import com.jiuqi.nr.bql.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.nr.bql.datasource.QueryDataReader;
import com.jiuqi.nr.bql.util.TempAssistantTableUtils;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    protected QueryParam queryParam;
    protected int currentIndex = 0;
    protected QuerySqlBuilder querySqlBuilder;
    protected Date queryVersionDate;
    protected boolean supportFullJoin = true;
    protected boolean errorLog = false;
    private boolean supportMemoryJoin = true;
    protected boolean ignoreDefaultOrderBy = false;
    protected QueryDataReader queryDataReader;
    protected DataRowImpl currentDataRow;
    protected DimensionValueSet expandDimValues = null;

    public DataQueryBuilder(QueryDataReader queryDataReader) {
        this.queryDataReader = queryDataReader;
    }

    public void buildQuery(QueryContext qContext, CommonQueryImpl queryInfo, boolean resultReadOnly) throws ExpressionException, ParseException, InterpretException, SQLException {
        this.queryVersionDate = queryInfo.getQueryVersionDate();
        IDatabase database = this.queryParam.getDatabase();
        if (StringUtils.isNotEmpty((String)this.mainTableName) && !database.isDatabase("MYSQL") && database.getDescriptor().supportFullJoin()) {
            this.supportMemoryJoin = false;
        }
        this.table = resultReadOnly ? new ReadonlyTableImpl(qContext, queryInfo.getMasterKeys(), queryInfo.getColumns().size()) : this.createDataTable(qContext, queryInfo);
        this.doParseQuery(qContext, queryInfo);
        this.setTableParams(qContext.getExeContext().getCache(), queryInfo);
        this.buildQueryRegion(qContext);
        if (this.queryRegion.getAllTableFields().isEmpty()) {
            return;
        }
        this.queryRegion.sqlBuilder.setSqlConditionProcesser(queryInfo.getSqlConditionProcesser());
        this.buildQuerySql(qContext);
        this.expandDimValues = queryInfo.getExpandDimValues();
    }

    protected ReadonlyTableImpl createDataTable(QueryContext qContext, CommonQueryImpl queryInfo) {
        return new ReadonlyTableImpl(qContext, queryInfo.getMasterKeys(), queryInfo.getColumns().size());
    }

    protected void setTableParams(DefinitionsCache cache, CommonQueryImpl queryImpl) {
        this.table.setDataQueryBuilder(this);
        this.table.setRowFilterNode(this.rowFilterNode);
        this.table.setColFilterValues(queryImpl.getColFilterValues());
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
        mainSqlBuilder.setPrimaryTableName(this.mainTableName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runQuery(QueryContext qContext, int rowCount, int rowIndex) throws Exception {
        TableModelRunInfo tableInfo;
        this.currentIndex = 0;
        if (this.queryRegion.getAllTableFields().isEmpty()) {
            return;
        }
        QuerySqlBuilder sqlBuilder = this.getSqlBuilder(qContext);
        this.table.setContext(qContext);
        sqlBuilder.setResultTable(this.table);
        sqlBuilder.setRowFilterNode(this.rowFilterNode);
        sqlBuilder.setOrderByItems(this.orderByItems);
        sqlBuilder.setColValueFilters(this.colValueFilters);
        sqlBuilder.setQueryParam(this.queryParam);
        sqlBuilder.setQueryVersionDate(this.queryVersionDate);
        sqlBuilder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy && rowCount < 0);
        sqlBuilder.doInit(qContext);
        if (rowCount < 0) {
            qContext.setQueryRowStart(0);
        } else {
            qContext.setQueryRowStart(rowIndex);
            sqlBuilder.setDoPage(true);
        }
        boolean sqlSoftParse = !DataEngineConsts.DATA_ENGINE_DEBUG;
        sqlBuilder.setSqlSoftParse(sqlSoftParse);
        IDataTableQueryExecutor queryExecuter = (IDataTableQueryExecutor)qContext.getOption("DataTableQueryExecutor");
        IUnitLeafFinder unitLeafFinder = qContext.getUnitLeafFinder();
        if (unitLeafFinder != null && (tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(sqlBuilder.primaryTable.getTableName())).getBizOrderField() != null) {
            unitLeafFinder = null;
        }
        if (this.queryRegion.getAllTableFields().size() > 1 && (!this.queryRegion.justLeftJoin(qContext) || sqlBuilder.getDbQueryExecutor() != null) || qContext.needRightJoinDimTables() || queryExecuter != null || unitLeafFinder != null) {
            sqlBuilder.setNeedMemoryFilter(true);
            sqlBuilder.setQueryExecuter(queryExecuter);
            this.doQueryAndMemeryPageJoin(qContext, rowCount, rowIndex);
        } else {
            String mainQuerySql = sqlBuilder.buildSql(qContext);
            try {
                this.queryData(qContext, sqlBuilder, mainQuerySql, rowCount, rowIndex);
            }
            finally {
                this.queryParam.closeConnection();
            }
        }
    }

    private void doQueryAndMemeryPageJoin(QueryContext qContext, int rowCount, int rowIndex) throws Exception {
        int endRowIndex = rowCount + rowIndex;
        this.queryRegion.getSqlBuilder().setDoPage(false);
        this.queryRegion.getSqlBuilder().setNeedMemoryFilter(true);
        this.queryRegion.setType(1);
        this.queryRegion.doInit(qContext);
        MemoryDataSetReader reader = this.queryRegion.runQuery(qContext);
        if (qContext.needRightJoinDimTables()) {
            if (this.expandDimValues == null) {
                this.expandDimValues = qContext.getMasterKeys();
            }
            Set<DimensionValueSet> dims = new HashSet<DimensionValueSet>();
            DimensionValueSet dimValues = new DimensionValueSet();
            DimensionSet groupDims = this.queryRegion.getSqlBuilder().getGroupDims();
            boolean dynamicExpand = "true".equals(qContext.getCache().get("dynamicExpand"));
            if (groupDims == null) {
                dimValues.assign(this.expandDimValues);
            } else {
                for (int i = 0; i < groupDims.size(); ++i) {
                    String dimName = groupDims.get(i);
                    Object dimValue = this.expandDimValues.getValue(dimName);
                    if (dimValue == null) continue;
                    dimValues.setValue(dimName, dimValue);
                }
            }
            boolean canExpand = true;
            if (!dynamicExpand && (groupDims == null || groupDims.contains("DATATIME"))) {
                String startPeriod = (String)qContext.getCache().get("startTimeKey");
                String endPeriod = (String)qContext.getCache().get("endTimeKey");
                if (!dimValues.hasValue("DATATIME") || startPeriod != null || endPeriod != null) {
                    if (reader.size() > 0) {
                        this.fixPeriodDim(qContext, reader, dimValues, startPeriod, endPeriod);
                    } else {
                        canExpand = false;
                    }
                }
            }
            if (canExpand) {
                DimensionSet expandDims = new DimensionSet(dimValues.getDimensionSet());
                if (dynamicExpand && this.queryRegion.isFloat()) {
                    if (reader.size() > 0) {
                        List<DimensionValueSet> rowKeys = reader.getRowKeys();
                        HashSet<DimensionValueSet> publicDims = new HashSet<DimensionValueSet>();
                        for (DimensionValueSet rowKey : rowKeys) {
                            DimensionValueSet newKey = new DimensionValueSet(rowKey);
                            for (int i = 0; i < expandDims.size(); ++i) {
                                newKey.clearValue(expandDims.get(i));
                            }
                            publicDims.add(newKey);
                        }
                        for (DimensionValueSet publicDim : publicDims) {
                            publicDim.combine(dimValues);
                            HashSet oneExpandDims = new HashSet();
                            ExpressionUtils.expandDims((DimensionValueSet)publicDim, oneExpandDims);
                            dims.addAll(oneExpandDims);
                        }
                        reader.expandByDims(expandDims, this.queryRegion.getSqlBuilder().loopDimensions, dims);
                    }
                } else {
                    ExpressionUtils.expandDims((DimensionValueSet)dimValues, dims);
                    dims = this.fixByDimensionQueryInfo(qContext, expandDims, dims);
                    dims = this.fixByAdjust(qContext, dims);
                    reader.expandByDims(expandDims, this.queryRegion.getSqlBuilder().loopDimensions, dims);
                }
            }
        }
        this.queryRegion.loadLeftJoinDatas(qContext, false);
        while (reader.next()) {
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
            DataRowImpl dataRow = this.table.addDataRow(reader.getCurrentRowKey());
            Object[] rowDatas = dataRow.getRowDatas();
            qContext.getExeContext().setVarDimensionValueSet(reader.getCurrentRowKey());
            for (int i = 0; i < this.table.getFieldsInfo().getFieldCount(); ++i) {
                Object value;
                IASTNode node = (IASTNode)this.queryNodes.get(i);
                rowDatas[i] = value = this.nodeEvaluate(qContext, node);
            }
        }
        for (int index = 0; index < this.table.queryfields.size(); ++index) {
            QueryField queryField = this.table.queryfields.get(index);
            if (queryField == null || !queryField.getFieldCode().equals("ORDINAL") || !queryField.getTable().isDimensionTable()) continue;
            String orderDimension = queryField.getTable().getTableDimensions().get(0);
            OrderTempAssistantTable orderTempAssistantTable = TempAssistantTableUtils.getContextTempAssistantTables(qContext).get(orderDimension);
            if (orderTempAssistantTable == null) continue;
            Map<String, Integer> orderFilterValues = orderTempAssistantTable.getOrderFilterValues();
            for (DataRowImpl dataRow : this.table.getAllDataRows()) {
                Object keyValue = dataRow.getRowKeys().getValue(orderDimension);
                Integer order = orderFilterValues.get(keyValue);
                dataRow.getRowDatas()[index] = order == null ? Integer.MAX_VALUE : order;
            }
        }
        this.doMemorySort(qContext);
        int totalCount = this.table.getAllDataRows().size();
        if (rowIndex >= 0 && endRowIndex > 0) {
            if (endRowIndex > totalCount) {
                endRowIndex = totalCount;
            }
            if (endRowIndex > rowIndex) {
                this.table.setAllDataRows(this.table.getAllDataRows().subList(rowIndex, endRowIndex));
            }
            this.queryDataReader.setTotalCount(totalCount);
        }
        this.queryDataReader.start();
        for (int i = 0; i < this.table.getCount(); ++i) {
            this.queryDataReader.readRowData(this.table.getItem(i));
        }
        this.queryDataReader.finish();
    }

    private Set<DimensionValueSet> fixByAdjust(QueryContext qContext, Set<DimensionValueSet> dims) {
        Map adjustMap = (Map)qContext.getCache().get("adjustMap");
        if (adjustMap != null) {
            HashSet<DimensionValueSet> newDims = new HashSet<DimensionValueSet>();
            for (DimensionValueSet dim : dims) {
                String period = (String)dim.getValue("DATATIME");
                Set adjustSet = (Set)adjustMap.get(period);
                if (adjustSet == null || adjustSet.isEmpty()) {
                    DimensionValueSet newDimRow = new DimensionValueSet(dim);
                    newDimRow.setValue("ADJUST", (Object)"0");
                    newDims.add(newDimRow);
                    continue;
                }
                for (String adjustValue : adjustSet) {
                    DimensionValueSet newDimRow = new DimensionValueSet(dim);
                    newDimRow.setValue("ADJUST", (Object)adjustValue);
                    newDims.add(newDimRow);
                }
            }
            dims = newDims;
        }
        return dims;
    }

    protected Set<DimensionValueSet> fixByDimensionQueryInfo(QueryContext qContext, DimensionSet expandDims, Set<DimensionValueSet> dims) throws Exception {
        List dimQueryInfos = (List)qContext.getCache().get("DimQueryInfos");
        if (dimQueryInfos != null) {
            for (DimQueryInfo dimQueryInfo : dimQueryInfos) {
                if (dimQueryInfo.getVariableValue() == null) continue;
                List<Object> values = dimQueryInfo.getValues();
                if (!expandDims.contains(dimQueryInfo.getDimension())) {
                    expandDims.addDimension(dimQueryInfo.getDimension());
                }
                HashSet<DimensionValueSet> newDims = new HashSet<DimensionValueSet>();
                DimensionTable dimensionTable = qContext.getDimTable(dimQueryInfo.getRefTableName(), qContext.getPeriodWrapper());
                for (DimensionValueSet dim : dims) {
                    String keyValue = (String)dim.getValue(dimensionTable.getDimensionName());
                    Object refDimValue = dimQueryInfo.getVariableValue();
                    DimensionRow row = dimensionTable.findRowByKey(keyValue);
                    if (row != null) {
                        refDimValue = row.getValue(dimQueryInfo.getRefFieldName());
                    }
                    DimensionValueSet newDim = new DimensionValueSet(dim);
                    newDim.setValue(dimQueryInfo.getDimension(), refDimValue);
                    newDims.add(newDim);
                    if (values == null) continue;
                    for (Object dimValue : values) {
                        DimensionValueSet newDimRow = new DimensionValueSet(dim);
                        newDimRow.setValue(dimQueryInfo.getDimension(), dimValue);
                        newDims.add(newDimRow);
                    }
                }
                dims = newDims;
            }
        }
        return dims;
    }

    private void fixPeriodDim(QueryContext qContext, MemoryDataSetReader reader, DimensionValueSet dimValues, String minPeriod, String maxPeriod) {
        String startPeriod = minPeriod;
        String endPeriod = maxPeriod;
        List<DimensionValueSet> rowKeys = reader.getRowKeys();
        for (DimensionValueSet rowKey : rowKeys) {
            String period = (String)rowKey.getValue("DATATIME");
            if (period == null) continue;
            if (minPeriod == null && (startPeriod == null || startPeriod.compareTo(period) > 0)) {
                startPeriod = period;
            }
            if (maxPeriod != null || endPeriod != null && endPeriod.compareTo(period) >= 0) continue;
            endPeriod = period;
        }
        ArrayList<String> periods = new ArrayList<String>();
        if (startPeriod != null) {
            periods.add(startPeriod);
        }
        if (startPeriod != null && endPeriod != null && endPeriod.compareTo(startPeriod) > 0) {
            IPeriodAdapter periodAdapter = qContext.getExeContext().getPeriodAdapter();
            PeriodWrapper periodWrapper = new PeriodWrapper(startPeriod);
            while (periodAdapter.nextPeriod(periodWrapper)) {
                String period = periodWrapper.toString();
                periods.add(period);
                if (period.compareTo(endPeriod) < 0) continue;
                break;
            }
        }
        if (periods.size() > 0) {
            dimValues.setValue("DATATIME", periods);
        }
    }

    protected void doMemorySort(QueryContext qContext) {
        try {
            final ArrayList<Integer> orderByIndex = new ArrayList<Integer>();
            final ArrayList<Boolean> orderDesc = new ArrayList<Boolean>();
            if (this.orderByItems != null) {
                for (OrderByItem item : this.orderByItems) {
                    int index;
                    QueryField field = item.field;
                    if (field == null || (index = this.table.queryfields.indexOf(field)) < 0) continue;
                    orderByIndex.add(index);
                    orderDesc.add(item.descending);
                }
            }
            if (orderByIndex.size() > 0) {
                Collections.sort(this.table.getAllDataRows(), new Comparator<DataRowImpl>(){

                    @Override
                    public int compare(DataRowImpl o1, DataRowImpl o2) {
                        for (int i = 0; i < orderByIndex.size(); ++i) {
                            try {
                                int index = (Integer)orderByIndex.get(i);
                                boolean desc = (Boolean)orderDesc.get(i);
                                int result = DataType.compareObject((Object)o1.getRowDatas()[index], (Object)o2.getRowDatas()[index]);
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
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean canUseMemoryJoin(int rowCount) {
        return this.supportMemeryJoin() && this.queryRegion.getAllTableFields().size() > 1 && rowCount < 0 && this.orderByItems == null && this.rowFilterNode == null;
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
        this.currentDataRow = new DataRowImpl(new DimensionValueSet());
        int endRowIndex = rowCount + rowIndex;
        this.queryDataReader.start();
        if (sqlBuilder.getPrimaryTable().isDimensionTable()) {
            MemoryDataSetReader reader = this.queryRegion.runQuery(qContext);
            while (reader.next()) {
                this.loadRowData(qContext, sqlBuilder, reader, rowIndex, endRowIndex);
            }
            this.queryDataReader.setTotalCount(this.currentIndex);
            this.queryDataReader.finish();
            return;
        }
        IQueryFieldDataReader reader = qContext.getDataReader();
        sqlBuilder.queryToDataReader(qContext, reader, this, rowIndex, rowCount);
        this.queryDataReader.finish();
    }

    public boolean loadRowData(QueryContext qContext, QuerySqlBuilder sqlBuilder, IQueryFieldDataReader reader, int beginRowIndex, int endRowIndex) throws Exception {
        DimensionValueSet rowKeys = this.currentDataRow.getRowKeys();
        sqlBuilder.buildRowKeys(rowKeys, qContext.getMasterKeys(), reader);
        qContext.getExeContext().setVarDimensionValueSet(rowKeys);
        DimensionValueSet currentMarsterKeys = qContext.getCurrentMasterKey();
        for (int i = 0; i < rowKeys.size(); ++i) {
            String dimName = rowKeys.getName(i);
            if (!currentMarsterKeys.hasValue(dimName)) continue;
            currentMarsterKeys.setValue(dimName, rowKeys.getValue(i));
        }
        if (sqlBuilder.isNeedMemoryFilter()) {
            try {
                if (!this.rowFilterNode.judge((IContext)qContext)) {
                    return false;
                }
            }
            catch (Exception e) {
                if (!this.errorLog) {
                    logger.warn("rowFilter\u6267\u884c\u51fa\u9519", e);
                }
                this.errorLog = true;
            }
        }
        ++this.currentIndex;
        if (sqlBuilder.isNeedMemoryFilter() && endRowIndex - beginRowIndex > 0 && (this.currentIndex < beginRowIndex + 1 || this.currentIndex > endRowIndex)) {
            return false;
        }
        IFieldsInfo fieldInfo = this.table.getFieldsInfo();
        Object[] rowDatas = new Object[this.table.getFieldsInfo().getFieldCount()];
        for (int i = 0; i < fieldInfo.getFieldCount(); ++i) {
            Object value;
            IASTNode node = (IASTNode)this.queryNodes.get(i);
            rowDatas[i] = value = this.nodeEvaluate(qContext, node);
        }
        this.currentDataRow.setRowDatas(rowDatas);
        this.queryDataReader.readRowData(this.currentDataRow);
        return true;
    }

    protected static String getQueryItemDesc(DataQueryColumn column) {
        String result = "";
        if (column.getColumnModel() != null) {
            result = column.getColumnModel().getTitle();
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
                logger.error("rowFilter\u89e3\u6790\u51fa\u9519", e);
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
        IExpression colNode = null;
        ExecutorContext executorContext = qContext.getExeContext();
        try {
            ColumnModelDefine columnModel = column.getColumnModel();
            QueryField queryField = null;
            if (columnModel != null) {
                queryField = executorContext.getCache().extractQueryField(executorContext, columnModel, column.getPeriodModifier(), column.getDimensionRestriction());
                DynamicDataNode fieldNode = new DynamicDataNode(queryField);
                colNode = fieldNode;
            } else if (StringUtils.isNotEmpty((String)column.getExpression())) {
                colNode = parser.parseEval(column.getExpression(), (IContext)qContext);
                DynamicDataNode fieldNode = null;
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
                        columnModel = fieldNode.getDataModelLink() != null ? fieldNode.getDataModelLink().getColumModel() : qContext.getExeContext().getCache().getDataModelDefinitionsCache().findField(queryField.getUID());
                    }
                }
            }
            if (!column.isOrderBy()) {
                if (columnModel != null) {
                    this.table.fieldsInfoImpl.setupField(column.getIndex(), columnModel);
                } else {
                    this.table.fieldsInfoImpl.setupField(column.getIndex(), colNode.getType((IContext)qContext));
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
            throw new ExpressionException(e);
        }
        return colNode;
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

    public void setIgnoreDefaultOrderBy(boolean ignoreDefaultOrderBy) {
        this.ignoreDefaultOrderBy = ignoreDefaultOrderBy;
    }

    public void setTotalCount(int totalCount) {
        this.queryDataReader.setTotalCount(totalCount);
    }

    public void finishRead() throws AdHocEngineException {
        this.queryDataReader.finish();
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }
}

