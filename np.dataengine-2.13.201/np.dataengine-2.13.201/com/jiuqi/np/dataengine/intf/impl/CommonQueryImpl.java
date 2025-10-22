/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataQueryColumn;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IDataValidateProvider;
import com.jiuqi.np.dataengine.setting.ISqlJoinProvider;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
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
    protected ISqlJoinProvider sqlJoinProvider;
    protected int rowsPerPage = -1;
    protected int pageIndex = -1;
    protected int rowIndex = -1;
    protected String tableName;
    protected QueryParam queryParam;
    protected IQuerySqlUpdater sqlUpdater;
    protected IDataValidateProvider validateProvider;
    protected boolean ignoreDataVersion;
    protected List<String> periodCodes;
    protected IMonitor monitor;
    protected boolean ignoreDefaultOrderBy;
    protected boolean queryModule = false;
    protected Map<String, TempAssistantTable> tempAssistantTables;
    protected Set<String> rightJoinDimTables;

    public CommonQueryImpl() {
        this.queryVersionDate = Consts.DATE_VERSION_INVALID_VALUE;
        this.queryVersionStartDate = Consts.DATE_VERSION_INVALID_VALUE;
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

    public ISqlJoinProvider getSqlJoinProvider() {
        return this.sqlJoinProvider;
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
    public int addColumn(FieldDefine fieldDefine) {
        DataQueryColumn column = new DataQueryColumn(fieldDefine);
        return this.addColumn(column);
    }

    @Override
    public int addColumn(FieldDefine fieldDefine, PeriodModifier periodModifier, DimensionValueSet dimensionRestriction) {
        DataQueryColumn column = new DataQueryColumn(fieldDefine, periodModifier, dimensionRestriction);
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
    public int addLookupColumn(FieldDefine keyFieldDefine, FieldDefine valueFieldDefine) {
        DataQueryColumn column = new DataQueryColumn(keyFieldDefine, valueFieldDefine);
        return this.addColumn(column);
    }

    @Override
    public int addCaptionLookupColumn(FieldDefine keyFieldDefine, EntityViewDefine viewDefine) {
        DataQueryColumn column = new DataQueryColumn(keyFieldDefine, viewDefine);
        return this.addColumn(column);
    }

    @Override
    public void addOrderByItem(FieldDefine fieldDefine, boolean descending) {
        if (this.containsOrderBy(fieldDefine)) {
            return;
        }
        DataQueryColumn column = new DataQueryColumn(fieldDefine);
        this.addOrderByItem(descending, column);
    }

    @Override
    public void addOrderByItem(String expression, boolean descending) {
        DataQueryColumn column = new DataQueryColumn(expression);
        this.addOrderByItem(descending, column);
    }

    @Override
    public void addSpecifiedOrderByItem(FieldDefine fieldDefine) {
        if (this.containsOrderBy(fieldDefine)) {
            return;
        }
        DataQueryColumn column = new DataQueryColumn(fieldDefine);
        column.setSpecified(true);
        this.addOrderByItem(false, column);
    }

    private void addOrderByItem(boolean descending, DataQueryColumn column) {
        column.setOrderBy(true);
        column.setDescending(descending);
        this.orderColumns.add(column);
    }

    private boolean containsOrderBy(FieldDefine fieldDefine) {
        if (this.orderColumns.size() <= 0 || fieldDefine == null) {
            return false;
        }
        for (DataQueryColumn orderColumn : this.orderColumns) {
            if (orderColumn.getField() == null || !orderColumn.getField().getKey().equals(fieldDefine.getKey())) continue;
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
    public void setMainTable(FieldDefine fieldDefine) {
        this.tableName = fieldDefine.getCode();
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
    public void setSqlJoinProvider(ISqlJoinProvider provider) {
        this.sqlJoinProvider = provider;
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
        PeriodWrapper periodWrapper;
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
        if (this.masterKeys.hasValue("DATATIME") && (periodWrapper = qContext.getPeriodWrapper()) != null) {
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

    protected QueryContext getQueryContext(ExecutorContext context, TempResource tempResource, boolean createTempTable) throws Exception {
        QueryContext qContext = new QueryContext(context, this.queryParam, this.monitor);
        tempResource.setConnectionProvider(this.queryParam.getConnectionProvider());
        qContext.setTempResource(tempResource);
        qContext.setQueryModule(this.queryModule);
        if (this.rightJoinDimTables != null) {
            qContext.getRightJoinDimTables().addAll(this.rightJoinDimTables);
        }
        if (this.tempAssistantTables != null) {
            for (Map.Entry<String, TempAssistantTable> entry : this.tempAssistantTables.entrySet()) {
                tempResource.addTempAssistantTable(entry.getKey(), entry.getValue());
            }
        }
        qContext.setMasterKeys(this.masterKeys);
        DataModelDefinitionsCache dataDefinitionsCache = context.getCache().getDataModelDefinitionsCache();
        DataQueryColumn specifiedColumn = null;
        if (this.orderColumns != null && this.orderColumns.size() > 0) {
            for (DataQueryColumn column : this.orderColumns) {
                if (!column.isSpecified()) continue;
                specifiedColumn = column;
                break;
            }
        }
        for (int i = 0; i < this.masterKeys.size(); ++i) {
            Object dimValue = this.masterKeys.getValue(i);
            if (!(dimValue instanceof List)) continue;
            List values = (List)dimValue;
            String dimension = this.masterKeys.getName(i);
            if (createTempTable && (values.size() >= DataEngineUtil.getMaxInSize(qContext.getQueryParam().getDatabase()) || specifiedColumn != null) && !qContext.getTempAssistantTables().containsKey(dimension)) {
                ColumnModelDefine dimensionField = null;
                for (DataQueryColumn column : this.columns) {
                    ColumnModelDefine columnModel;
                    TableModelDefine table;
                    TableModelRunInfo tableInfo;
                    if (column.getField() != null && (dimensionField = (tableInfo = dataDefinitionsCache.getTableInfo((table = dataDefinitionsCache.getTableModel(columnModel = dataDefinitionsCache.getColumnModel(column.getField()))).getName())).getDimensionField(dimension)) != null) break;
                }
                if (dimensionField != null) {
                    int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                    if (specifiedColumn != null) {
                        if (dimensionField.getCode().equals(specifiedColumn.getField().getCode())) {
                            qContext.getKeyOrderTempTable(dimension, values, dimDataType);
                        }
                    } else {
                        qContext.getTempAssistantTable(dimension, values, dimDataType);
                    }
                }
            }
            if (values.size() != 1) continue;
            this.masterKeys.setValue(dimension, values.get(0));
        }
        if (this.colFilterValues != null && this.colFilterValues.size() > 0) {
            for (Map.Entry<Integer, ArrayList<Object>> filterValue : this.colFilterValues.entrySet()) {
                ArrayList<Object> filterList = filterValue.getValue();
                DataQueryColumn column = this.columns.get(filterValue.getKey());
                FieldDefine fieldDefine = column.getField();
                if (fieldDefine == null) continue;
                ColumnModelDefine columnModel = dataDefinitionsCache.getColumnModel(fieldDefine);
                TableModelDefine table = dataDefinitionsCache.getTableModel(columnModel);
                String filterName = table.getName().concat("_").concat(columnModel.getName());
                int dataType = DataTypesConvert.fieldTypeToDataType(fieldDefine.getType());
                qContext.getTempAssistantTable(filterName, filterList, dataType);
            }
        }
        return qContext;
    }

    protected void ininMonitor(DataEngineConsts.DataEngineRunType runType) {
        AbstractMonitor abstractMonitor;
        if (this.monitor == null) {
            this.monitor = new AbstractMonitor(runType);
        } else if (this.monitor instanceof AbstractMonitor && (abstractMonitor = (AbstractMonitor)this.monitor).getRunType() == DataEngineConsts.DataEngineRunType.UNKOWN) {
            abstractMonitor.setRunType(runType);
        }
        if (this.monitor instanceof AbstractMonitor) {
            abstractMonitor = (AbstractMonitor)this.monitor;
            if (this.queryParam.getDataChangeListeners() != null) {
                abstractMonitor.getDataChangeListeners().clear();
                abstractMonitor.getDataChangeListeners().addAll(this.queryParam.getDataChangeListeners());
            }
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
    public void setQuerySqlUpdater(IQuerySqlUpdater updater) {
        this.sqlUpdater = updater;
    }

    public IQuerySqlUpdater getQuerySqlUpdater() {
        return this.sqlUpdater;
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
    public void setTempAssistantTable(String dimension, TempAssistantTable tempTable) {
        if (this.tempAssistantTables == null) {
            this.tempAssistantTables = new HashMap<String, TempAssistantTable>();
        }
        this.tempAssistantTables.put(dimension, tempTable);
    }

    @Override
    public void setQueryModule(boolean queryModule) {
        this.queryModule = queryModule;
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
    public IReadonlyTable executeReader(ExecutorContext context) throws Exception {
        return null;
    }

    @Override
    public int getColumnSize() {
        return this.columns.size();
    }
}

