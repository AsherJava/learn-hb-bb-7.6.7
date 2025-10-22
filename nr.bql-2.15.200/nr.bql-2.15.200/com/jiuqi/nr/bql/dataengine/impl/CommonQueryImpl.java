/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionRelationProvider
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.IDataValidateProvider
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bql.dataengine.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionRelationProvider;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IDataValidateProvider;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bql.dataengine.ICommonQuery;
import com.jiuqi.nr.bql.dataengine.impl.BqlDimensionRelationProvider;
import com.jiuqi.nr.bql.dataengine.impl.DataQueryColumn;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import com.jiuqi.nr.bql.dataengine.query.QueryMonitor;
import com.jiuqi.nr.bql.datasource.QueryDataReader;
import com.jiuqi.nr.bql.intf.ISqlConditionProcesser;
import com.jiuqi.nr.bql.util.TempAssistantTableUtils;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonQueryImpl
implements ICommonQuery {
    protected DimensionValueSet masterKeys;
    protected String rowFilter;
    protected boolean filterByAuth = false;
    protected String formName;
    protected HashMap<Integer, ArrayList<Object>> colFilterValues = new HashMap();
    protected Date queryVersionStartDate;
    protected Date queryVersionDate;
    protected boolean designTimeData;
    protected List<String> recKeys;
    protected IDynamicNodeProvider dynamicNodeProvider;
    protected List<DataQueryColumn> columns = new ArrayList<DataQueryColumn>();
    protected List<DataQueryColumn> orderColumns = new ArrayList<DataQueryColumn>();
    protected int rowsPerPage = -1;
    protected int pageIndex = -1;
    protected int rowIndex = -1;
    protected String tableName;
    protected QueryParam queryParam;
    protected IDataValidateProvider validateProvider;
    protected boolean ignoreDataVersion;
    protected List<String> periodCodes;
    protected IMonitor monitor;
    protected boolean ignoreDefaultOrderBy;
    protected Map<String, OrderTempAssistantTable> tempAssistantTables;
    protected Set<String> rightJoinDimTables;
    protected Map<String, Object> options;
    protected ISqlConditionProcesser sqlConditionProcesser;
    protected QueryDataReader reader;
    protected DBQueryExecutorProvider dbQueryExecutorProvider;
    protected DimensionValueSet expandDimValues = new DimensionValueSet();

    public CommonQueryImpl() {
        this.queryVersionDate = Consts.DATE_VERSION_INVALID_VALUE;
        this.queryVersionStartDate = Consts.DATE_VERSION_INVALID_VALUE;
    }

    @Override
    public void queryToReader(ExecutorContext context) throws Exception {
    }

    @Override
    public void setReader(QueryDataReader reader) {
        this.reader = reader;
    }

    @Override
    public QueryDataReader getReader() {
        return this.reader;
    }

    public String getFormName() {
        return this.formName;
    }

    public HashMap<Integer, ArrayList<Object>> getColFilterValues() {
        return this.colFilterValues;
    }

    public IDynamicNodeProvider getDynamicNodeProvider() {
        return this.dynamicNodeProvider;
    }

    public List<DataQueryColumn> getColumns() {
        return this.columns;
    }

    public List<DataQueryColumn> getOrderColumns() {
        return this.orderColumns;
    }

    public int getRowsPerPage() {
        return this.rowsPerPage;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public String getTableName() {
        return this.tableName;
    }

    @Override
    public void setMasterKeys(DimensionValueSet masterKeys) {
        this.masterKeys = masterKeys;
    }

    @Override
    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }

    @Override
    public void setFilterDataByAuthority(boolean filterByAuth) {
        this.filterByAuth = filterByAuth;
    }

    @Override
    public void setDefaultGroupName(String formName) {
        this.formName = formName;
    }

    @Override
    public int addColumn(ColumnModelDefine fieldDefine) {
        DataQueryColumn column = new DataQueryColumn(fieldDefine);
        return this.addColumn(column);
    }

    @Override
    public int addColumn(ColumnModelDefine columnModel, PeriodModifier periodModifier, DimensionValueSet dimensionRestriction) {
        DataQueryColumn column = new DataQueryColumn(columnModel, periodModifier, dimensionRestriction);
        return this.addColumn(column);
    }

    @Override
    public int addExpressionColumn(String expression) {
        DataQueryColumn column = new DataQueryColumn(expression);
        return this.addColumn(column);
    }

    private int addColumn(DataQueryColumn column) {
        column.setIndex(this.columns.size());
        this.columns.add(column);
        return column.getIndex();
    }

    @Override
    public int addCustomSqlColumn(String sqlString) {
        return 0;
    }

    @Override
    public void addOrderByItem(ColumnModelDefine columnModel, boolean descending) {
        if (this.containsOrderBy(columnModel)) {
            return;
        }
        DataQueryColumn column = new DataQueryColumn(columnModel);
        this.addOrderByItem(descending, column);
    }

    @Override
    public void addOrderByItem(String expression, boolean descending) {
        DataQueryColumn column = new DataQueryColumn(expression);
        this.addOrderByItem(descending, column);
    }

    private void addOrderByItem(boolean descending, DataQueryColumn column) {
        column.setOrderBy(true);
        column.setDescending(descending);
        this.orderColumns.add(column);
    }

    @Override
    public void addSpecifiedOrderByItem(ColumnModelDefine columnModel) {
        DataQueryColumn column = new DataQueryColumn(columnModel);
        column.setSpecified(true);
        this.addOrderByItem(false, column);
    }

    private boolean containsOrderBy(ColumnModelDefine columnModel) {
        if (this.orderColumns.size() <= 0 || columnModel == null) {
            return false;
        }
        for (DataQueryColumn orderColumn : this.orderColumns) {
            if (orderColumn.getColumnModel() == null || !orderColumn.getColumnModel().getID().equals(columnModel.getID())) continue;
            return true;
        }
        return false;
    }

    @Override
    public void clearOrderByItems() {
        this.orderColumns.clear();
    }

    @Override
    public void setColumnFilterValueList(int colIndex, ArrayList<Object> filteredValues) {
        this.colFilterValues.put(colIndex, filteredValues);
    }

    @Override
    public void setMainTable(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void setPagingInfo(int rowsPerPage, int pageIndex) {
        this.rowsPerPage = rowsPerPage;
        this.pageIndex = pageIndex;
    }

    @Override
    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public Date getQueryVersionDate() {
        return this.queryVersionDate;
    }

    public Date getQueryVersionStartDate() {
        return this.queryVersionStartDate;
    }

    public String getRowFilter() {
        return this.rowFilter;
    }

    public boolean getFilterDataByAuthority() {
        return this.filterByAuth;
    }

    @Override
    public void setDesignTimeData(boolean designTimeData) {
        this.designTimeData = designTimeData;
    }

    public boolean getDesignTimeData() {
        return this.designTimeData;
    }

    protected DefinitionsCache getDefinitionsCache(ExecutorContext eContext) throws ParseException {
        eContext.setDesignTimeData(this.designTimeData, this.queryParam.getDesignTimeController());
        DefinitionsCache cache = eContext.getCache();
        return cache;
    }

    protected void adjustQueryVersionDate(QueryContext qContext) throws ParseException {
        if (this.masterKeys == null) {
            this.masterKeys = new DimensionValueSet();
        }
        if (this.queryVersionStartDate != null && this.queryVersionStartDate.compareTo(Consts.DATE_VERSION_MIN_VALUE) < 0) {
            this.queryVersionStartDate = Consts.DATE_VERSION_INVALID_VALUE;
        }
        if (!this.masterKeys.hasValue("DATATIME") && this.periodCodes != null && this.periodCodes.size() > 0) {
            this.masterKeys = new DimensionValueSet(this.masterKeys);
            this.masterKeys.setValue("DATATIME", this.periodCodes);
            qContext.setMasterKeys(this.masterKeys);
        }
        PeriodWrapper periodWrapper = null;
        if (this.masterKeys.hasValue("DATATIME")) {
            periodWrapper = qContext.getPeriodWrapper();
        }
        if (periodWrapper != null) {
            try {
                Date[] dateRegion = qContext.getExeContext().getPeriodAdapter().getPeriodDateRegion(periodWrapper);
                if (dateRegion != null && dateRegion.length == 2 && dateRegion[1] != null) {
                    this.queryVersionDate = dateRegion[1];
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.queryVersionDate == null || this.queryVersionDate.equals(Consts.DATE_VERSION_INVALID_VALUE)) {
            this.queryVersionDate = new Date(System.currentTimeMillis());
        }
    }

    protected QueryContext getQueryContext(ExecutorContext context, boolean createTempTable) throws Exception {
        IDataTableQueryExecutor queryExecuter;
        List dimQueryInfos;
        QueryContext qContext = new QueryContext(context, this.queryParam, this.monitor);
        qContext.setQueryModule(true);
        if (this.rightJoinDimTables != null) {
            qContext.getRightJoinDimTables().addAll(this.rightJoinDimTables);
        }
        if (this.tempAssistantTables != null) {
            TempAssistantTableUtils.getContextTempAssistantTables(qContext).putAll(this.tempAssistantTables);
        }
        qContext.setMasterKeys(this.masterKeys);
        DataModelDefinitionsCache dataDefinitionsCache = context.getCache().getDataModelDefinitionsCache();
        for (int i = 0; i < this.masterKeys.size(); ++i) {
            List values;
            Object dimValue = this.masterKeys.getValue(i);
            if (!(dimValue instanceof List) || (values = (List)dimValue).size() != 1) continue;
            this.masterKeys.setValue(this.masterKeys.getName(i), values.get(0));
        }
        if (this.colFilterValues != null && this.colFilterValues.size() > 0) {
            for (Map.Entry<Integer, ArrayList<Object>> filterValue : this.colFilterValues.entrySet()) {
                ArrayList<Object> filterList = filterValue.getValue();
                DataQueryColumn column = this.columns.get(filterValue.getKey());
                ColumnModelDefine columnModel = column.getColumnModel();
                if (columnModel == null) continue;
                TableModelDefine table = dataDefinitionsCache.getTableModel(columnModel);
                String filterName = table.getName().concat("_").concat(columnModel.getName());
                int dataType = columnModel.getColumnType().getValue();
                qContext.getTempAssistantTable(filterName, filterList, dataType);
            }
        }
        if (qContext.isEnableNrdb() && (dimQueryInfos = (List)this.options.get("DimQueryInfos")) != null && dimQueryInfos.size() > 0) {
            BqlDimensionRelationProvider provider = new BqlDimensionRelationProvider();
            provider.doInit(qContext, dimQueryInfos);
            qContext.setDimensionRelationProvider((IDimensionRelationProvider)provider);
        }
        if ((queryExecuter = (IDataTableQueryExecutor)this.options.get("DataTableQueryExecutor")) != null && queryExecuter.getConnectionProvider() != null) {
            this.queryParam.setConnectionProvider(queryExecuter.getConnectionProvider());
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(this.queryParam.getConnection());
            this.queryParam.setDatabase(database);
            this.options.remove("DataTableQueryExecutor");
        }
        qContext.setQueryExecutorProvider(this.dbQueryExecutorProvider);
        if (this.options != null) {
            qContext.getCache().putAll(this.options);
        }
        return qContext;
    }

    protected void ininMonitor(DataEngineConsts.DataEngineRunType runType) {
        if (this.monitor == null) {
            this.monitor = new QueryMonitor(runType);
        }
    }

    @Override
    public void setPagingInfoByRowIndex(int rowCount, int rowIndex) {
        this.rowsPerPage = rowCount;
        this.rowIndex = rowIndex;
    }

    @Override
    public void setRecKeys(List<String> recKeys) {
        this.recKeys = recKeys;
    }

    public List<String> getRecKeys() {
        return this.recKeys;
    }

    public QueryParam getQueryParam() {
        return this.queryParam;
    }

    @Override
    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam.clone();
    }

    @Override
    public void setDataValidateProvider(IDataValidateProvider validateProvider) {
        this.validateProvider = validateProvider;
    }

    public IDataValidateProvider getDataValidateProvider() {
        return this.validateProvider;
    }

    @Override
    public void setIgnoreDataVersion(boolean ignoreDataVersion) {
        this.ignoreDataVersion = ignoreDataVersion;
    }

    public boolean getIgnoreDataVersion() {
        return this.ignoreDataVersion;
    }

    @Override
    public void setQueryPeriod(String startDate, String endDate, PeriodType periodType) {
        this.periodCodes = PeriodUtil.getPeriodCodes((String)startDate, (String)endDate, (PeriodType)periodType);
    }

    @Override
    public void setQueryVersionStartDate(Date queryVersionStartDate) {
        this.queryVersionStartDate = queryVersionStartDate;
    }

    @Override
    public void setQueryVersionDate(Date queryVersionDate) {
        this.queryVersionDate = queryVersionDate;
    }

    @Override
    public void setTempAssistantTable(String dimension, OrderTempAssistantTable tempTable) {
        if (this.tempAssistantTables == null) {
            this.tempAssistantTables = new HashMap<String, OrderTempAssistantTable>();
        }
        this.tempAssistantTables.put(dimension, tempTable);
    }

    @Override
    public void setIgnoreDefaultOrderBy(boolean ignoreDefaultOrderBy) {
        this.ignoreDefaultOrderBy = ignoreDefaultOrderBy;
    }

    @Override
    public void addRightJoinDimTable(String rightJoinDimTable) {
        if (this.rightJoinDimTables == null) {
            this.rightJoinDimTables = new HashSet<String>();
        }
        this.rightJoinDimTables.add(rightJoinDimTable);
    }

    @Override
    public int getColumnSize() {
        return this.columns.size();
    }

    @Override
    public void setOption(String key, Object value) {
        if (this.options == null) {
            this.options = new HashMap<String, Object>();
        }
        this.options.put(key, value);
    }

    public ISqlConditionProcesser getSqlConditionProcesser() {
        return this.sqlConditionProcesser;
    }

    public void setSqlConditionProcesser(ISqlConditionProcesser sqlConditionProcesser) {
        this.sqlConditionProcesser = sqlConditionProcesser;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("query ").append(this.columns).append("\n");
        buff.append(" where ");
        this.printMasterKeys(buff);
        if (this.rowFilter != null) {
            buff.append(" and ").append(this.rowFilter).append("\n");
        }
        if (this.orderColumns.size() > 0) {
            buff.append(" order by ");
            this.orderColumns.forEach(o -> {
                buff.append(o.toString()).append(" ").append(o.isDescending() ? "desc" : "asc");
                if (o.isSpecified()) {
                    buff.append("specified");
                }
                buff.append(",");
            });
            buff.setLength(buff.length() - 1);
            buff.append("\n");
        }
        return buff.toString();
    }

    protected void printMasterKeys(StringBuilder buff) {
        boolean needAnd = false;
        for (int i = 0; i < this.masterKeys.size(); ++i) {
            String dim = this.masterKeys.getName(i);
            Object value = this.masterKeys.getValue(i);
            if (needAnd) {
                buff.append(" and ");
            }
            if (value instanceof List) {
                List values = (List)value;
                buff.append(dim).append(" in {");
                if (values.size() > 0) {
                    for (int n = 0; n < values.size(); ++n) {
                        if (n >= 10) {
                            buff.append("...,");
                            break;
                        }
                        buff.append(values.get(n)).append(",");
                    }
                    buff.setLength(buff.length() - 1);
                }
                buff.append("}");
            } else {
                buff.append(dim).append("=").append(value);
            }
            needAnd = true;
        }
        buff.append("\n");
    }

    @Override
    public void setDBQueryExecutorProvider(DBQueryExecutorProvider dbQueryExecutorProvider) {
        this.dbQueryExecutorProvider = dbQueryExecutorProvider;
    }

    @Override
    public void addExpandDimValues(String dimName, Object dimValues) {
        this.expandDimValues.setValue(dimName, dimValues);
    }

    public DimensionValueSet getExpandDimValues() {
        return this.expandDimValues;
    }
}

