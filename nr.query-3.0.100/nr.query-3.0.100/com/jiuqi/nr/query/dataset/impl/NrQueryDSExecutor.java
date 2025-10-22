/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.time.ITimeReader
 *  com.jiuqi.bi.util.time.TimeCalculator
 *  com.jiuqi.bi.util.time.TimeFieldInfo
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.query.dataset.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.time.ITimeReader;
import com.jiuqi.bi.util.time.TimeCalculator;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.dataset.QueryDSField;
import com.jiuqi.nr.query.dataset.QueryDSModel;
import com.jiuqi.nr.query.dataset.QueryRowComparator;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNodeFinder;
import com.jiuqi.nr.query.dataset.parse.QueryDataSetFunctionProvider;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NrQueryDSExecutor {
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void runQuery(QueryDSModel dsModel, IDSContext dsContext, int pageSize, int currentPage, MemoryDataSet<BIDataSetFieldInfo> memoryDataSet) throws Exception {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(dsModel.getFormSchemeKey());
        QueryRowComparator comparator = new QueryRowComparator();
        if (dsModel.getBlockId() != null) {
            IReadonlyTable table = this.getDataTable(dsModel, dsContext, pageSize, currentPage);
            int timeKeyIndex = dsModel.getTimeKeyIndex();
            if (timeKeyIndex >= 0) {
                comparator.getOrderIndexes().add(timeKeyIndex);
            }
            for (int i = 0; i < dsModel.getCommonFields().size(); ++i) {
                DSField dsField = (DSField)dsModel.getCommonFields().get(i);
                QueryDSField queryDSField = (QueryDSField)dsField;
                if (!queryDSField.getName().equals(queryDSField.getKeyFieldName())) continue;
                comparator.getOrderIndexes().add(i);
            }
            IFieldsInfo fieldsInfo = table.getFieldsInfo();
            TimeCalculator timeCalculator = new TimeCalculator();
            List dsFields = dsModel.getCommonFields();
            List<Integer> timeDims = dsModel.getTimeDims();
            if (timeDims.size() > 0) {
                ArrayList<TimeFieldInfo> timeFields = new ArrayList<TimeFieldInfo>(timeDims.size() + 1);
                for (int index : timeDims) {
                    DSField dsField = (DSField)dsFields.get(index);
                    TimeFieldInfo timeFieldInfo = new TimeFieldInfo(dsField.getName(), dsField.getTimegranularity().value(), dsField.getDataPattern(), false);
                    timeFields.add(timeFieldInfo);
                }
                DSField timeKeyField = (DSField)dsFields.get(timeKeyIndex);
                TimeFieldInfo timeKeyFieldInfo = new TimeFieldInfo(timeKeyField.getName(), timeKeyField.getTimegranularity().value(), timeKeyField.getDataPattern(), true);
                timeFields.add(timeKeyFieldInfo);
                timeCalculator.init(timeFields);
            }
            for (int r = 0; r < table.getCount(); ++r) {
                try {
                    IDataRow dataRow = table.getItem(r);
                    if (dataRow.getGroupingFlag() >= 0) continue;
                    String periodStr = null;
                    ITimeReader reader = null;
                    if (timeKeyIndex >= 0) {
                        AbstractData dataValue = dataRow.getValue(timeKeyIndex);
                        try {
                            PeriodWrapper pw = new PeriodWrapper(dataValue.getAsString());
                            if (formScheme != null && pw.getType() != formScheme.getPeriodType().type()) continue;
                            periodStr = TimeDimUtils.getTimeDimByPeriodWrapper((PeriodWrapper)pw, (TimeGranularity)dsModel.getTimeGranularity());
                            timeCalculator.setValue(timeCalculator.getFields().size() - 1, (Object)periodStr);
                            reader = timeCalculator.calculate();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    DataRow row = memoryDataSet.add();
                    for (int c = 0; c < fieldsInfo.getFieldCount(); ++c) {
                        AbstractData dataValue = dataRow.getValue(c);
                        if (timeKeyIndex == c) {
                            if (periodStr != null) {
                                row.setString(c, periodStr);
                                for (int i = 0; i < timeDims.size(); ++i) {
                                    int index = timeDims.get(i);
                                    row.setString(index, (String)reader.getValue(i));
                                }
                                continue;
                            }
                            row.setString(c, dataValue.getAsString());
                            continue;
                        }
                        this.setValueToRow(row, dataValue, c);
                    }
                    row.commit();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        memoryDataSet.sort((Comparator)comparator);
    }

    private IReadonlyTable getDataTable(QueryDSModel dsModel, IDSContext dsContext, int pageSize, int currentPage) throws Exception, ParseException, ExpressionException, ExecuteException {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.registerFunctionProvider((IFunctionProvider)new QueryDataSetFunctionProvider());
        QueryDSModel model = dsModel;
        QueryBlockDefine block = model.getBlock();
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, model.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)env);
        ReportFormulaParser formulaParser = executorContext.getCache().getFormulaParser(true);
        formulaParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new DataSetDimensionNodeFinder());
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        Map<Integer, List<Object>> colfilterList = this.buildColFilters(dsModel, dsContext, dims);
        DimensionValueSet masterKeys = this.buildDimensionValueSet(model, dsContext, block.getPeriodTypeInCache());
        return this.queryByDSModel(executorContext, model, masterKeys, colfilterList, pageSize, currentPage);
    }

    protected Map<Integer, List<Object>> buildColFilters(QueryDSModel dsModel, IDSContext dsContext, List<QueryDimensionDefine> dims) {
        HashMap colfilterList = null;
        for (ParameterModel paraModel : dsModel.getParameterModels()) {
            int columnIndex = -1;
            if (!dsModel.getParaColumnIndexMap().containsKey(paraModel.getName())) continue;
            columnIndex = dsModel.getParaColumnIndexMap().get(paraModel.getName());
            try {
                ArrayList<String> paraValues = dsContext.getEnhancedParameterEnv().getValueAsList(paraModel.getName());
                if (paraValues == null) {
                    for (QueryDimensionDefine dim : dims) {
                        if (!dim.getDimensionName().equals(paraModel.getName())) continue;
                        paraValues = new ArrayList<String>();
                        List<QuerySelectItem> selectItems = dim.getSelectItems();
                        for (QuerySelectItem selectItem : selectItems) {
                            paraValues.add(selectItem.getCode());
                        }
                    }
                }
                if (columnIndex < 0 || paraValues.size() <= 0) continue;
                if (colfilterList == null) {
                    colfilterList = new HashMap();
                }
                colfilterList.put(columnIndex, paraValues);
            }
            catch (ParameterException e) {
                e.printStackTrace();
            }
        }
        for (QueryDimensionDefine dim : dims) {
            if (dim.getDimensionType() != QueryDimensionType.QDT_DICTIONARY) continue;
        }
        return colfilterList;
    }

    private IGroupingTable queryByDSModel(ExecutorContext executorContext, QueryDSModel model, DimensionValueSet masterKeys, Map<Integer, List<Object>> colfilterList, int pageSize, int currentPage) throws ParseException, ExpressionException, DataSetException, SQLException, SyntaxException, DataTypeException, Exception {
        IGroupingQuery dataQuery = this.dataAccessProvider.newGroupingQuery();
        dataQuery.setWantDetail(true);
        IDatabase database = null;
        Connection connection = this.jdbcTemplate.getDataSource().getConnection();
        Object object = null;
        try {
            database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            if (database.getDescriptor().supportFullJoin() && !database.isDatabase("MYSQL")) {
                dataQuery.setOldQueryModule(true);
            }
        }
        catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        }
        finally {
            if (connection != null) {
                if (object != null) {
                    try {
                        connection.close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)object).addSuppressed(throwable);
                    }
                } else {
                    connection.close();
                }
            }
        }
        DataModelDefinitionsCache dataDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
        for (Object dsField : model.getCommonFields()) {
            QueryDSField queryDSField = (QueryDSField)((Object)dsField);
            if (queryDSField.getExpresion() != null) {
                dataQuery.addExpressionColumn(queryDSField.getExpresion());
                continue;
            }
            ColumnModelDefine columnModel = queryDSField.getColumnModel();
            if (columnModel == null) continue;
            TableModelDefine table = dataDefinitionsCache.findTable(columnModel.getTableID());
            dataQuery.addExpressionColumn(table.getCode() + "[" + columnModel.getCode() + "]");
        }
        this.checkAuth(executorContext, masterKeys);
        dataQuery.setMasterKeys(masterKeys);
        if (pageSize > 0) {
            dataQuery.setPagingInfo(pageSize, currentPage);
        }
        IGroupingTable result = null;
        if (colfilterList != null) {
            if (database != null && database.getDescriptor().supportFullJoin() && !database.isDatabase("MYSQL")) {
                Object dsField;
                dsField = colfilterList.keySet().iterator();
                while (dsField.hasNext()) {
                    int index = (Integer)dsField.next();
                    dataQuery.setColumnFilterValueList(index, (ArrayList)colfilterList.get(index));
                }
                result = dataQuery.executeReader(executorContext);
            } else {
                ReadonlyTableImpl table = (ReadonlyTableImpl)dataQuery.executeReader(executorContext);
                if (colfilterList != null) {
                    for (int index : colfilterList.keySet()) {
                        ArrayList values = (ArrayList)colfilterList.get(index);
                        HashSet valueSet = new HashSet(values);
                        for (int i = table.getAllDataRows().size() - 1; i >= 0; --i) {
                            DataRowImpl row = (DataRowImpl)table.getAllDataRows().get(i);
                            AbstractData dataValue = row.getValue(index);
                            if (valueSet.contains(dataValue.getAsObject())) continue;
                            table.getAllDataRows().remove(i);
                        }
                    }
                }
                result = (IGroupingTable)table;
            }
        } else {
            result = dataQuery.executeReader(executorContext);
        }
        return result;
    }

    protected void checkAuth(ExecutorContext executorContext, DimensionValueSet masterKeys) throws Exception {
        Object dimValue;
        String unitEntityId;
        EntityViewDefine entityViewDefine;
        String unitDim = executorContext.getUnitDimension();
        Set<String> authIds = this.getAuthSet(unitDim, masterKeys, entityViewDefine = this.entityViewRunTimeController.buildEntityView(unitEntityId = executorContext.getCache().getDataModelDefinitionsCache().getDimensionProvider().getEntityIdByEntityTableCode(executorContext, unitDim)));
        boolean checkAuth = this.needCheckAuth(authIds, dimValue = masterKeys.hasValue(unitDim) ? masterKeys.getValue(unitDim) : null);
        if (!checkAuth) {
            return;
        }
        ArrayList<String> authValues = new ArrayList<String>(authIds);
        masterKeys.setValue(unitDim, authValues);
    }

    public Set<String> getAuthSet(String dimensionName, DimensionValueSet masterKeys, EntityViewDefine entityViewDefine) throws Exception {
        HashSet<String> authList = new HashSet<String>();
        IEntityTable entityTable = this.getEntityTable(dimensionName, entityViewDefine, masterKeys);
        List rows = entityTable.getAllRows();
        for (IEntityRow row : rows) {
            authList.add(row.getEntityKeyData());
        }
        return authList;
    }

    private IEntityTable getEntityTable(String dimensionName, EntityViewDefine entityViewDefine, DimensionValueSet masterKeys) throws Exception {
        DimensionValueSet dim = new DimensionValueSet();
        for (int i = 0; i < masterKeys.size(); ++i) {
            String dimName = masterKeys.getName(i);
            if (!dimName.equals(dimensionName)) continue;
            dim.setValue(dimName, masterKeys.getValue(i));
        }
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(dim);
        return iEntityQuery.executeFullBuild(null);
    }

    private boolean needCheckAuth(Set<String> authIds, Object dimValue) {
        if (dimValue != null) {
            if (dimValue instanceof List) {
                List dimObjects = (List)dimValue;
                ArrayList<String> dimList = new ArrayList<String>();
                for (Object currentValue : dimObjects) {
                    dimList.add(currentValue.toString());
                }
                if (authIds.containsAll(dimList)) {
                    return false;
                }
            } else if (authIds.contains(dimValue.toString())) {
                return false;
            }
        }
        return true;
    }

    private DimensionValueSet buildDimensionValueSet(QueryDSModel model, IDSContext dsContext, String periodType) throws ParameterException {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        List parameterModels = model.getParameterModels();
        if (parameterModels != null && parameterModels.size() > 0) {
            for (ParameterModel parameterModel : parameterModels) {
                String dimName = parameterModel.getName();
                String newDimName = model.getParaNamesMap().get(dimName);
                if (newDimName != null) {
                    dimName = newDimName;
                }
                List values = dsContext.getEnhancedParameterEnv().getValueAsList(parameterModel.getName());
                ArrayList<String> results = new ArrayList<String>(values.size());
                if (dimName.equals("DATATIME")) {
                    for (Object periodStr : values) {
                        String dataTime = TimeDimUtils.getDataTimeByTimeDim((String)((String)periodStr), (String)periodType);
                        results.add(dataTime);
                    }
                } else {
                    results.addAll(values);
                }
                dimensionValueSet.setValue(dimName, results);
            }
        }
        return dimensionValueSet;
    }

    private void setValueToRow(DataRow row, AbstractData dataValue, int index) {
        if (dataValue != null && !dataValue.isNull) {
            Object value = this.convertDataValue(dataValue);
            if (value != null) {
                row.setValue(index, value);
            } else {
                row.setNull(index);
            }
        } else {
            row.setNull(index);
        }
    }

    private Object convertDataValue(AbstractData dataValue) {
        try {
            switch (dataValue.dataType) {
                case 1: {
                    return dataValue.getAsBool();
                }
                case 2: 
                case 5: 
                case 8: {
                    long date = dataValue.getAsDate();
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(date);
                    return c;
                }
                case 6: 
                case 7: 
                case 33: {
                    return dataValue.getAsString();
                }
                case 3: 
                case 10: {
                    return dataValue.getAsFloat();
                }
                case 4: {
                    return dataValue.getAsInt();
                }
            }
        }
        catch (DataTypeException e) {
            e.printStackTrace();
        }
        return null;
    }
}

