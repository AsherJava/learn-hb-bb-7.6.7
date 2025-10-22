/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.impl.DataSetExprEvaluator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.Measure
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.SortMode
 *  com.jiuqi.nr.datacrud.common.DataTypeConvert
 *  com.jiuqi.nr.datacrud.impl.DataValue
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datacrud.impl.RegionDataSet
 *  com.jiuqi.nr.datacrud.impl.RegionDataSetFactory
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RowData
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureData
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureService
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureView
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.datacrud.spi.filter.InValuesFilter
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.snapshot.service.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.impl.DataSetExprEvaluator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.datacrud.impl.RegionDataSetFactory;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.measure.MeasureView;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datacrud.spi.filter.InValuesFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.snapshot.consts.HistoryPeriodType;
import com.jiuqi.nr.snapshot.exception.DataRangeException;
import com.jiuqi.nr.snapshot.exception.FormNotWriteException;
import com.jiuqi.nr.snapshot.exception.SnapshotException;
import com.jiuqi.nr.snapshot.input.BatchComparisonContext;
import com.jiuqi.nr.snapshot.input.ComparisonContext;
import com.jiuqi.nr.snapshot.input.QueryPeriodDataSourceContext;
import com.jiuqi.nr.snapshot.input.QuerySnapshotDataSourceContext;
import com.jiuqi.nr.snapshot.input.ReversionByPeriodContext;
import com.jiuqi.nr.snapshot.input.ReversionBySnapshotContext;
import com.jiuqi.nr.snapshot.message.DataInfo;
import com.jiuqi.nr.snapshot.message.DataRange;
import com.jiuqi.nr.snapshot.message.DataRegionAndFields;
import com.jiuqi.nr.snapshot.message.DataRegionRange;
import com.jiuqi.nr.snapshot.message.FieldData;
import com.jiuqi.nr.snapshot.message.FixRegionData;
import com.jiuqi.nr.snapshot.message.FloatRegionData;
import com.jiuqi.nr.snapshot.output.BatchComparisonResult;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import com.jiuqi.nr.snapshot.service.DataOperationService;
import com.jiuqi.nr.snapshot.service.DataSource;
import com.jiuqi.nr.snapshot.service.DataSourceBuilder;
import com.jiuqi.nr.snapshot.service.DataSourceOperationService;
import com.jiuqi.nr.snapshot.utils.LogHellperUtil;
import com.jiuqi.nr.snapshot.utils.SnapshotJoinProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataOperationServiceImpl
implements DataOperationService {
    private static final Logger logger = LoggerFactory.getLogger(DataOperationServiceImpl.class);
    @Autowired
    private DataSourceBuilder dataSourceBuilder;
    @Autowired
    private DataSourceOperationService dataSourceOperationService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private RegionDataSetFactory regionDataSetFactory;
    @Autowired
    private MeasureService measureService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private DesensitizedEncryptor encryptor;
    private static final String LOG_TITLE = "\u5feb\u7167\u64cd\u4f5c";

    @Override
    public List<ComparisonResult> comparison(ComparisonContext comparisonContext) throws SnapshotException {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(comparisonContext.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String DWdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        DimensionValueSet dim = comparisonContext.getCurrentDim().toDimensionValueSet();
        String targetKey = String.valueOf(dim.getValue(DWdimensionName));
        String periodCode = String.valueOf(dim.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        try {
            if (null == comparisonContext.getDataRange() || null == comparisonContext.getDataRange().getFormAndFields() || comparisonContext.getDataRange().getFormAndFields().isEmpty()) {
                DataRange dataRange = this.checkDataRange(comparisonContext.getFormSchemeKey(), comparisonContext.getCurrentDim(), false);
                comparisonContext.setDataRange(dataRange);
            } else {
                this.judgePermissions(comparisonContext.getCurrentDim(), comparisonContext.getFormSchemeKey(), comparisonContext.getDataRange(), false);
            }
            QueryPeriodDataSourceContext initialQueryContext = new QueryPeriodDataSourceContext();
            initialQueryContext.setDimensionCombination(comparisonContext.getCurrentDim());
            initialQueryContext.setFormSchemeKey(comparisonContext.getFormSchemeKey());
            initialQueryContext.setDataRange(comparisonContext.getDataRange());
            initialQueryContext.setDataName(comparisonContext.getCurrentDataName());
            DataSource initialData = this.dataSourceBuilder.queryPeriodDataSource(initialQueryContext);
            Map<String, HistoryPeriodType> historyPeriodMap = comparisonContext.getHistoryPeriodMap();
            List<String> snapshotIds = comparisonContext.getSnapshotIds();
            if ((null == historyPeriodMap || historyPeriodMap.isEmpty()) && (null == snapshotIds || snapshotIds.isEmpty())) {
                return null;
            }
            ArrayList<DataSource> compareDatas = new ArrayList<DataSource>();
            if (null != historyPeriodMap && !historyPeriodMap.isEmpty()) {
                HashMap historyDimsMap = new HashMap();
                DimensionValueSet dimensionValueSet = comparisonContext.getCurrentDim().toDimensionValueSet();
                for (String historyDataName : historyPeriodMap.keySet()) {
                    Object currenPeriod;
                    HistoryPeriodType historyPeriodType = historyPeriodMap.get(historyDataName);
                    if (historyPeriodType == HistoryPeriodType.LAST_PERIOD) {
                        DimensionValueSet lastPeriodDimensionValueSet = new DimensionValueSet(dimensionValueSet);
                        currenPeriod = dimensionValueSet.getValue("DATATIME");
                        String lastPeriod = PeriodUtils.priorPeriod((String)((String)currenPeriod));
                        lastPeriodDimensionValueSet.setValue("DATATIME", (Object)lastPeriod);
                        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(lastPeriodDimensionValueSet);
                        DimensionCombination lastPeriodCombination = dimensionCombinationBuilder.getCombination();
                        historyDimsMap.put(historyDataName, lastPeriodCombination);
                        continue;
                    }
                    if (historyPeriodType == HistoryPeriodType.LASTYEAR_SAMEPERIOD) {
                        DimensionValueSet lastyearDimensionValueSet = new DimensionValueSet(dimensionValueSet);
                        currenPeriod = dimensionValueSet.getValue("DATATIME");
                        String currenPeriodCode = (String)currenPeriod;
                        String currenYear = currenPeriodCode.substring(0, 4);
                        int currenYearInt = Integer.parseInt(currenYear);
                        int lastYearInt = currenYearInt - 1;
                        String lastYearPeriod = Integer.toString(lastYearInt) + currenPeriodCode.substring(4);
                        lastyearDimensionValueSet.setValue("DATATIME", (Object)lastYearPeriod);
                        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(lastyearDimensionValueSet);
                        DimensionCombination lastYearPeriodCombination = dimensionCombinationBuilder.getCombination();
                        historyDimsMap.put(historyDataName, lastYearPeriodCombination);
                        continue;
                    }
                    if (historyPeriodType == HistoryPeriodType.LATEST_ROLLBACK_SNAPSHOT) {
                        String latestRollbackSnapshotId = this.queryLatestRollbackSnapshot(taskDefine.getKey(), comparisonContext.getCurrentDim());
                        if (!org.springframework.util.StringUtils.hasText(latestRollbackSnapshotId)) continue;
                        if (null == snapshotIds) {
                            snapshotIds = new ArrayList<String>();
                        }
                        snapshotIds.add(latestRollbackSnapshotId);
                        continue;
                    }
                    throw new SnapshotException("\u4e0d\u652f\u6301\u7684HistoryPeriodType\u7c7b\u578b");
                }
                if (!historyDimsMap.isEmpty()) {
                    for (String historyDataName : historyDimsMap.keySet()) {
                        DimensionCombination dimensionCombination = (DimensionCombination)historyDimsMap.get(historyDataName);
                        QueryPeriodDataSourceContext queryContext = new QueryPeriodDataSourceContext();
                        queryContext.setDimensionCombination(dimensionCombination);
                        queryContext.setFormSchemeKey(comparisonContext.getFormSchemeKey());
                        queryContext.setDataRange(comparisonContext.getDataRange());
                        queryContext.setDataName(historyDataName);
                        DataSource compareData = this.dataSourceBuilder.queryPeriodDataSource(queryContext);
                        compareDatas.add(compareData);
                    }
                }
            }
            if (null != snapshotIds && !snapshotIds.isEmpty()) {
                for (String snapshotId : snapshotIds) {
                    QuerySnapshotDataSourceContext queryContext = new QuerySnapshotDataSourceContext();
                    queryContext.setFormSchemeKey(comparisonContext.getFormSchemeKey());
                    queryContext.setDimensionCombination(comparisonContext.getCurrentDim());
                    queryContext.setSnapshotId(snapshotId);
                    queryContext.setDataRange(comparisonContext.getDataRange());
                    DataSource compareData = this.dataSourceBuilder.querySnapshotDataSource(queryContext);
                    compareDatas.add(compareData);
                }
            }
            if (!compareDatas.isEmpty()) {
                DataRange dataRange = comparisonContext.getDataRange();
                List<String> formKeys = dataRange.getFormAndFields().stream().map(DataRegionRange::getFormKey).collect(Collectors.toList());
                List<ComparisonResult> comparison = this.dataSourceOperationService.comparison(initialData, compareDatas, formKeys);
                logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u5bf9\u6bd4\u6210\u529f", "\u5feb\u7167\u5bf9\u6bd4\u6210\u529f");
                return comparison;
            }
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u5bf9\u6bd4\u5931\u8d25", "\u65e0\u5feb\u7167");
            return null;
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u5bf9\u6bd4\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a" + e.getMessage());
            throw e;
        }
    }

    private String queryLatestRollbackSnapshot(String taskKey, DimensionCombination dimensionCombination) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(snapshotRelTable.getName());
        List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
        for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
            if (snapshotRelField.getCode().equals("SNAPSHOTID")) {
                queryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
                continue;
            }
            String dimensionName = dimensionChanger.getDimensionName(snapshotRelField);
            if (!StringUtils.isNotEmpty((String)dimensionName) || null == dimensionValueSet.getValue(dimensionName)) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
            queryModel.getColumnFilters().put(snapshotRelField, dimensionValueSet.getValue(dimensionName));
        }
        ColumnModelDefine idColumnModelDefine = null;
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        for (ColumnModelDefine snapshotField : snapshotFields) {
            switch (snapshotField.getCode()) {
                case "CREATTIME": {
                    queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
                    queryModel.getOrderByItems().add(new OrderByItem(snapshotField, true));
                    break;
                }
                case "TASKKEY": {
                    queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
                    queryModel.getColumnFilters().put(snapshotField, taskKey);
                    break;
                }
                case "ISAUTOCREATED": {
                    queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
                    queryModel.getColumnFilters().put(snapshotField, "1");
                    break;
                }
                case "ID": {
                    idColumnModelDefine = snapshotField;
                    queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
                    break;
                }
            }
        }
        SnapshotJoinProvider provider = new SnapshotJoinProvider();
        context.setSqlJoinProvider((ISqlJoinProvider)provider);
        queryModel.setMainTableName(snapshotRelTable.getName());
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            INvwaDataSet nvwaDataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
            if (null != nvwaDataRows && nvwaDataRows.size() > 0) {
                INvwaDataRow row = nvwaDataRows.getRow(0);
                Object value = row.getValue(idColumnModelDefine);
                return (String)value;
            }
            return null;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<BatchComparisonResult> batchComparison(BatchComparisonContext context) throws SnapshotException {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        String DWdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        DimensionValueSet dims = context.getCurrentDims().combineWithoutVarDim();
        Object dwObject = dims.getValue(DWdimensionName);
        if (dwObject instanceof String) {
            String targetKey = (String)dwObject;
            logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        } else if (dwObject instanceof List) {
            ArrayList<String> targetKeys = new ArrayList<String>();
            for (Object o : (List)dwObject) {
                targetKeys.add((String)o);
            }
            logDimensionCollection.setDw(taskDefine.getDw(), targetKeys.toArray(new String[0]));
        }
        String periodCode = String.valueOf(dims.getValue(periodDimensionName));
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        try {
            ArrayList<BatchComparisonResult> batchComparisonResults = new ArrayList<BatchComparisonResult>();
            DataRange dataRange = new DataRange();
            ArrayList<DataRegionRange> formAndFields = new ArrayList<DataRegionRange>();
            List formDefines = this.runTimeViewController.queryFormsById(context.getFormKeys());
            for (FormDefine formDefine : formDefines) {
                if (null == formDefine) {
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u91cf\u5feb\u7167\u5bf9\u6bd4\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a\u8868\u5355\u4e0d\u5b58\u5728");
                    throw new SnapshotException("\u8868\u5355\u4e0d\u5b58\u5728");
                }
                if (!FormType.FORM_TYPE_FMDM.equals((Object)formDefine.getFormType()) && !FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType()) && !FormType.FORM_TYPE_ANALYSISREPORT.equals((Object)formDefine.getFormType()) && !FormType.FORM_TYPE_ACCOUNT.equals((Object)formDefine.getFormType()) && !FormType.FORM_TYPE_INSERTANALYSIS.equals((Object)formDefine.getFormType())) continue;
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u91cf\u5feb\u7167\u5bf9\u6bd4\u5931\u8d25", String.format("\u4e1a\u52a1\u9519\u8bef\uff1a%s\uff0c\u62a5\u8868\u7c7b\u578b\u4e0d\u652f\u6301", formDefine.getKey()));
                throw new SnapshotException(String.format("%s\uff1a\u62a5\u8868\u7c7b\u578b\u4e0d\u652f\u6301", formDefine.getKey()));
            }
            for (String formKey : context.getFormKeys()) {
                DataRegionRange dataRegionRange = new DataRegionRange();
                dataRegionRange.setFormKey(formKey);
                formAndFields.add(dataRegionRange);
            }
            dataRange.setFormAndFields(formAndFields);
            List dimensionCombinations = context.getCurrentDims().getDimensionCombinations();
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                QueryPeriodDataSourceContext initialQueryContext = new QueryPeriodDataSourceContext();
                initialQueryContext.setDimensionCombination(dimensionCombination);
                initialQueryContext.setFormSchemeKey(context.getFormSchemeKey());
                initialQueryContext.setDataRange(dataRange);
                initialQueryContext.setDataName("\u5f53\u524d\u6570\u636e");
                DataSource initialData = this.dataSourceBuilder.queryPeriodDataSource(initialQueryContext);
                ArrayList<DataSource> compareDatas = new ArrayList<DataSource>();
                QuerySnapshotDataSourceContext queryContext = new QuerySnapshotDataSourceContext();
                queryContext.setFormSchemeKey(context.getFormSchemeKey());
                queryContext.setDimensionCombination(dimensionCombination);
                String dw = (String)dimensionCombination.getValue(DWdimensionName);
                queryContext.setSnapshotId(context.getDwSnapshotId().get(dw));
                queryContext.setDataRange(dataRange);
                DataSource compareData = this.dataSourceBuilder.querySnapshotDataSource(queryContext);
                compareDatas.add(compareData);
                List<ComparisonResult> comparison = this.dataSourceOperationService.comparison(initialData, compareDatas, context.getFormKeys());
                BatchComparisonResult batchComparisonResult = new BatchComparisonResult();
                batchComparisonResult.setDim(dimensionCombination);
                batchComparisonResult.setResults(comparison);
                batchComparisonResults.add(batchComparisonResult);
            }
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u6279\u91cf\u5feb\u7167\u5bf9\u6bd4\u6210\u529f", "\u6279\u91cf\u5feb\u7167\u5bf9\u6bd4\u6210\u529f");
            return batchComparisonResults;
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u91cf\u5feb\u7167\u5bf9\u6bd4\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a" + e.getMessage());
            throw e;
        }
    }

    private DataRange checkDataRange(String formSchemeKey, DimensionCombination dimensionCombination, boolean isWrite) throws DataRangeException {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionCombination.toDimensionValueSet(), (String)formSchemeKey);
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskDefine.getKey(), formSchemeKey);
        ArrayList<String> formKeys = new ArrayList<String>();
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (FormDefine formDefine : formDefines) {
            if (FormType.FORM_TYPE_FMDM.equals((Object)formDefine.getFormType()) || FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType()) || FormType.FORM_TYPE_ANALYSISREPORT.equals((Object)formDefine.getFormType()) || FormType.FORM_TYPE_ACCOUNT.equals((Object)formDefine.getFormType()) || FormType.FORM_TYPE_INSERTANALYSIS.equals((Object)formDefine.getFormType())) continue;
            formKeys.add(formDefine.getKey());
        }
        if (formKeys.isEmpty()) {
            throw new DataRangeException("User does not have permission on the forms.");
        }
        IBatchAccessResult access = null;
        access = isWrite ? dataAccessService.getWriteAccess(dimensionCollection, formKeys) : dataAccessService.getReadAccess(dimensionCollection, formKeys);
        ArrayList<String> accessFormKeys = new ArrayList<String>();
        try {
            for (String formKey : formKeys) {
                IAccessResult accessResult = access.getAccess(dimensionCombination, formKey);
                if (accessResult.haveAccess()) {
                    accessFormKeys.add(formKey);
                    continue;
                }
                FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                if (!isWrite) {
                    logger.error("\u62a5\u8868 " + formDefine.getFormCode() + " \u65e0\u6cd5\u5bf9\u6bd4\uff0c\u539f\u56e0\uff1a" + accessResult.getMessage());
                    continue;
                }
                logger.error("\u62a5\u8868 " + formDefine.getFormCode() + " \u65e0\u6cd5\u8fd8\u539f\uff0c\u539f\u56e0\uff1a" + accessResult.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (accessFormKeys.isEmpty()) {
            throw new DataRangeException("User does not have permission on the forms.");
        }
        DataRange newDataRange = new DataRange();
        ArrayList<DataRegionRange> formAndFields = new ArrayList<DataRegionRange>();
        for (String accessFormKey : accessFormKeys) {
            DataRegionRange dataRegionRange = new DataRegionRange();
            dataRegionRange.setFormKey(accessFormKey);
            formAndFields.add(dataRegionRange);
        }
        newDataRange.setFormAndFields(formAndFields);
        return newDataRange;
    }

    @Override
    public void reversionByPeriod(ReversionByPeriodContext revertContext) throws SnapshotException {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(revertContext.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String DWdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        DimensionValueSet dim = revertContext.getCurrentDim().toDimensionValueSet();
        String targetKey = String.valueOf(dim.getValue(DWdimensionName));
        String periodCode = String.valueOf(dim.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        try {
            Object currenPeriod;
            DataRange dataRange;
            if (null == revertContext.getDataRange() || null == revertContext.getDataRange().getFormAndFields() || revertContext.getDataRange().getFormAndFields().isEmpty()) {
                dataRange = this.checkDataRange(revertContext.getFormSchemeKey(), revertContext.getCurrentDim(), true);
                revertContext.setDataRange(dataRange);
            } else {
                dataRange = this.judgePermissions(revertContext.getCurrentDim(), revertContext.getFormSchemeKey(), revertContext.getDataRange());
                revertContext.setDataRange(dataRange);
            }
            QueryPeriodDataSourceContext queryPeriodContext = null;
            QuerySnapshotDataSourceContext querySnapshotContext = null;
            DimensionValueSet dimensionValueSet = revertContext.getCurrentDim().toDimensionValueSet();
            if (revertContext.getHistoryPeriodType() == HistoryPeriodType.LAST_PERIOD) {
                queryPeriodContext = new QueryPeriodDataSourceContext();
                DimensionValueSet lastPeriodDimensionValueSet = new DimensionValueSet(dimensionValueSet);
                currenPeriod = dimensionValueSet.getValue("DATATIME");
                String lastPeriod = PeriodUtils.priorPeriod((String)((String)currenPeriod));
                lastPeriodDimensionValueSet.setValue("DATATIME", (Object)lastPeriod);
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(lastPeriodDimensionValueSet);
                DimensionCombination lastPeriodCombination = dimensionCombinationBuilder.getCombination();
                queryPeriodContext.setDimensionCombination(lastPeriodCombination);
            } else if (revertContext.getHistoryPeriodType() == HistoryPeriodType.LASTYEAR_SAMEPERIOD) {
                queryPeriodContext = new QueryPeriodDataSourceContext();
                DimensionValueSet lastyearDimensionValueSet = new DimensionValueSet(dimensionValueSet);
                currenPeriod = dimensionValueSet.getValue("DATATIME");
                String currenPeriodCode = (String)currenPeriod;
                String currenYear = currenPeriodCode.substring(0, 4);
                int currenYearInt = Integer.parseInt(currenYear);
                int lastYearInt = currenYearInt - 1;
                String lastYearPeriod = Integer.toString(lastYearInt) + currenPeriodCode.substring(4);
                lastyearDimensionValueSet.setValue("DATATIME", (Object)lastYearPeriod);
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(lastyearDimensionValueSet);
                DimensionCombination lastYearPeriodCombination = dimensionCombinationBuilder.getCombination();
                queryPeriodContext.setDimensionCombination(lastYearPeriodCombination);
            } else if (revertContext.getHistoryPeriodType() == HistoryPeriodType.LATEST_ROLLBACK_SNAPSHOT) {
                String latestRollbackSnapshotId = this.queryLatestRollbackSnapshot(taskDefine.getKey(), revertContext.getCurrentDim());
                if (org.springframework.util.StringUtils.hasText(latestRollbackSnapshotId)) {
                    querySnapshotContext = new QuerySnapshotDataSourceContext();
                    querySnapshotContext.setSnapshotId(latestRollbackSnapshotId);
                }
            } else {
                throw new SnapshotException("\u4e0d\u652f\u6301\u7684HistoryPeriodType\u7c7b\u578b");
            }
            DataSource dataSource = null;
            if (null != queryPeriodContext) {
                queryPeriodContext.setFormSchemeKey(revertContext.getFormSchemeKey());
                queryPeriodContext.setDataRange(revertContext.getDataRange());
                queryPeriodContext.setDataName(revertContext.getHistoryDataName());
                dataSource = this.dataSourceBuilder.queryPeriodDataSource(queryPeriodContext);
            } else if (null != querySnapshotContext) {
                querySnapshotContext.setFormSchemeKey(revertContext.getFormSchemeKey());
                querySnapshotContext.setDimensionCombination(revertContext.getCurrentDim());
                querySnapshotContext.setDataRange(revertContext.getDataRange());
                dataSource = this.dataSourceBuilder.querySnapshotDataSource(querySnapshotContext);
            }
            if (null != dataSource) {
                List<String> formKeys = revertContext.getDataRange().getFormAndFields().stream().map(DataRegionRange::getFormKey).collect(Collectors.toList());
                this.dataSourceOperationService.reversion(revertContext.getFormSchemeKey(), revertContext.getCurrentDim(), dataSource, formKeys);
            }
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u6309\u65f6\u671f\u8fd8\u539f\u6210\u529f", "\u5feb\u7167\u6309\u65f6\u671f\u8fd8\u539f\u6210\u529f");
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u6309\u65f6\u671f\u8fd8\u539f\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a" + e.getMessage());
            throw e;
        }
    }

    private void judgePermissions(DimensionCombination dimensionCombination, String formSchemeKey, DataRange dataRange, boolean isWrite) throws FormNotWriteException {
        List<DataRegionRange> formAndFields = dataRange.getFormAndFields();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionCombination.toDimensionValueSet(), (String)formSchemeKey);
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskDefine.getKey(), formSchemeKey);
        List formKeys = formAndFields.stream().map(t -> t.getFormKey()).collect(Collectors.toList());
        IBatchAccessResult access = null;
        access = isWrite ? dataAccessService.getWriteAccess(dimensionCollection, formKeys) : dataAccessService.getReadAccess(dimensionCollection, formKeys);
        boolean canWrite = true;
        try {
            for (String formKey : formKeys) {
                IAccessResult accessResult = access.getAccess(dimensionCombination, formKey);
                if (accessResult.haveAccess()) continue;
                canWrite = false;
                FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                logger.error("\u62a5\u8868 " + formDefine.getFormCode() + " \u65e0\u6cd5\u5bf9\u6bd4\uff0c\u539f\u56e0\uff1a" + accessResult.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (!canWrite) {
            throw new FormNotWriteException("Form no permission.");
        }
    }

    private DataRange judgePermissions(DimensionCombination dimensionCombination, String formSchemeKey, DataRange dataRange) throws FormNotWriteException {
        List<DataRegionRange> formAndFields = dataRange.getFormAndFields();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionCombination.toDimensionValueSet(), (String)formSchemeKey);
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskDefine.getKey(), formSchemeKey);
        List formKeys = formAndFields.stream().map(t -> t.getFormKey()).collect(Collectors.toList());
        IBatchAccessResult access = dataAccessService.getWriteAccess(dimensionCollection, formKeys);
        DataRange result = new DataRange();
        ArrayList<DataRegionRange> resultFormAndFields = new ArrayList<DataRegionRange>();
        result.setFormAndFields(resultFormAndFields);
        try {
            block2: for (String formKey : formKeys) {
                IAccessResult accessResult = access.getAccess(dimensionCombination, formKey);
                if (accessResult.haveAccess()) {
                    for (DataRegionRange formAndField : formAndFields) {
                        if (!formAndField.getFormKey().equals(formKey)) continue;
                        resultFormAndFields.add(formAndField);
                        continue block2;
                    }
                    continue;
                }
                FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                logger.error("\u62a5\u8868 " + formDefine.getFormCode() + " \u65e0\u6cd5\u8fd8\u539f\uff0c\u539f\u56e0\uff1a" + accessResult.getMessage());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (resultFormAndFields.isEmpty()) {
            throw new FormNotWriteException("Form no permission.");
        }
        return result;
    }

    @Override
    public void reversionBySnapshot(ReversionBySnapshotContext revertContext) throws SnapshotException {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(revertContext.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String DWdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        DimensionValueSet dimensionValueSet = revertContext.getCurrentDim().toDimensionValueSet();
        String targetKey = String.valueOf(dimensionValueSet.getValue(DWdimensionName));
        String periodCode = String.valueOf(dimensionValueSet.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        try {
            DataRange dataRange;
            if (null == revertContext.getDataRange() || null == revertContext.getDataRange().getFormAndFields() || revertContext.getDataRange().getFormAndFields().isEmpty()) {
                dataRange = this.checkDataRange(revertContext.getFormSchemeKey(), revertContext.getCurrentDim(), true);
                revertContext.setDataRange(dataRange);
            } else {
                dataRange = this.judgePermissions(revertContext.getCurrentDim(), revertContext.getFormSchemeKey(), revertContext.getDataRange());
                revertContext.setDataRange(dataRange);
            }
            QuerySnapshotDataSourceContext queryContext = new QuerySnapshotDataSourceContext();
            queryContext.setFormSchemeKey(revertContext.getFormSchemeKey());
            queryContext.setDimensionCombination(revertContext.getCurrentDim());
            queryContext.setSnapshotId(revertContext.getSnapshotId());
            queryContext.setDataRange(revertContext.getDataRange());
            DataSource dataSource = this.dataSourceBuilder.querySnapshotDataSource(queryContext);
            List<String> formKeys = revertContext.getDataRange().getFormAndFields().stream().map(t -> t.getFormKey()).collect(Collectors.toList());
            this.dataSourceOperationService.reversion(revertContext.getFormSchemeKey(), revertContext.getCurrentDim(), dataSource, formKeys);
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u6309\u5feb\u7167\u8fd8\u539f\u6210\u529f", "\u5feb\u7167\u6309\u5feb\u7167\u8fd8\u539f\u6210\u529f");
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u6309\u5feb\u7167\u8fd8\u539f\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a" + e.getMessage());
        }
    }

    @Override
    public IRegionDataSet querySanpshotData(IQueryInfo queryInfo, String snapshotId) {
        RegionRelation regionRelation = queryInfo.getRegionRelation();
        FormSchemeDefine formSchemeDefine = regionRelation.getFormSchemeDefine();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String DWdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        DimensionValueSet dimensionValueSet = queryInfo.getDimensionCombination().toDimensionValueSet();
        String targetKey = String.valueOf(dimensionValueSet.getValue(DWdimensionName));
        String periodCode = String.valueOf(dimensionValueSet.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        QuerySnapshotDataSourceContext context = new QuerySnapshotDataSourceContext();
        context.setFormSchemeKey(formSchemeDefine.getKey());
        context.setDimensionCombination(queryInfo.getDimensionCombination());
        DataRange dataRange = new DataRange();
        ArrayList<DataRegionRange> formAndFields = new ArrayList<DataRegionRange>();
        DataRegionRange dataRegionRange = new DataRegionRange();
        FormDefine formDefine = regionRelation.getFormDefine();
        dataRegionRange.setFormKey(formDefine.getKey());
        ArrayList<DataRegionAndFields> dataRegionAndFieldsList = new ArrayList<DataRegionAndFields>();
        DataRegionAndFields dataRegionAndFields = new DataRegionAndFields();
        dataRegionAndFields.setDataRegionKey(queryInfo.getRegionKey());
        dataRegionAndFieldsList.add(dataRegionAndFields);
        dataRegionRange.setDataRegionAndFieldsList(dataRegionAndFieldsList);
        formAndFields.add(dataRegionRange);
        dataRange.setFormAndFields(formAndFields);
        context.setDataRange(dataRange);
        context.setSnapshotId(snapshotId);
        DataSource dataSource = this.dataSourceBuilder.querySnapshotDataSource(context);
        RegionDataSet regionDataSet = null;
        DataInfo data = dataSource.getData(formDefine.getKey());
        if (null == data || null == data.getFixData() && (null == data.getFloatDatas() || data.getFloatDatas().isEmpty())) {
            DataRegionKind regionKind = regionRelation.getRegionDefine().getRegionKind();
            List metaDatas = null;
            metaDatas = DataRegionKind.DATA_REGION_SIMPLE == regionKind ? regionRelation.getMetaData(null, false) : regionRelation.getMetaData(null, true);
            regionDataSet = this.regionDataSetFactory.getRegionRelation(metaDatas, regionRelation);
            regionDataSet.setMasterDimension(queryInfo.getDimensionCombination());
            ArrayList rows = new ArrayList();
            regionDataSet.setRows(rows);
            return regionDataSet;
        }
        FixRegionData fixData = data.getFixData();
        List<FloatRegionData> floatRegionDatas = data.getFloatDatas();
        try {
            if (null != fixData) {
                List metaDatas = regionRelation.getMetaData(null, false);
                regionDataSet = this.regionDataSetFactory.getRegionRelation(metaDatas, regionRelation);
                regionDataSet.setMasterDimension(queryInfo.getDimensionCombination());
                List<FieldData> fixDatas = fixData.getFixDatas();
                if (null == fixDatas || fixDatas.isEmpty()) {
                    ArrayList rows = new ArrayList();
                    regionDataSet.setRows(rows);
                } else {
                    ArrayList<List<FieldData>> fixDatass = new ArrayList<List<FieldData>>();
                    fixDatass.add(fixDatas);
                    this.processValue(queryInfo, fixDatass);
                    HashMap<String, FieldData> fieldKeyFieldDataMap = new HashMap<String, FieldData>();
                    for (FieldData fieldData : fixDatas) {
                        fieldKeyFieldDataMap.put(fieldData.getFieldKey(), fieldData);
                    }
                    RowData rowData = regionDataSet.appendRow();
                    for (MetaData metaData : metaDatas) {
                        DataValue dataValue;
                        FieldData fieldData = (FieldData)fieldKeyFieldDataMap.get(metaData.getFieldKey());
                        if (null != fieldData) {
                            dataValue = rowData.appendDataValue((IMetaData)metaData);
                            AbstractData fieldDataValue = fieldData.getData();
                            if (org.springframework.util.StringUtils.hasText(fieldData.getMaskCode()) && null != fieldDataValue && !fieldDataValue.isNull) {
                                String encipheredData = this.encryptor.desensitize(fieldData.getMaskCode(), fieldDataValue.getAsString());
                                fieldDataValue = AbstractData.valueOf((String)encipheredData);
                            }
                            dataValue.setAbstractData(fieldDataValue);
                            dataValue.setRowData(rowData);
                            continue;
                        }
                        dataValue = rowData.appendDataValue((IMetaData)metaData);
                        dataValue.setAbstractData(AbstractData.valueOf(null));
                        dataValue.setRowData(rowData);
                    }
                }
            } else if (null != floatRegionDatas && !floatRegionDatas.isEmpty()) {
                List metaDatas = regionRelation.getMetaData(null, true);
                regionDataSet = this.regionDataSetFactory.getRegionRelation(metaDatas, regionRelation);
                regionDataSet.setMasterDimension(queryInfo.getDimensionCombination());
                FloatRegionData floatRegionData = floatRegionDatas.get(0);
                List<List<FieldData>> floatDatass = floatRegionData.getFloatDatass();
                if (null != floatDatass && !floatDatass.isEmpty()) {
                    regionDataSet.setTotalCount(floatDatass.size());
                    floatDatass = this.filter(queryInfo, floatDatass);
                    if (!regionRelation.getGradeInfo().isGrade()) {
                        this.sort(queryInfo, floatDatass);
                    }
                    floatDatass = this.page(queryInfo, floatDatass);
                    this.processValue(queryInfo, floatDatass);
                }
                if (null == floatDatass || floatDatass.isEmpty()) {
                    ArrayList rows = new ArrayList();
                    regionDataSet.setRows(rows);
                    regionDataSet.setTotalCount(0);
                } else {
                    for (List<FieldData> floatDatas : floatDatass) {
                        HashMap<String, FieldData> hashMap = new HashMap<String, FieldData>();
                        HashMap<String, FieldData> fieldCodeFieldDataMap = new HashMap<String, FieldData>();
                        for (FieldData fieldData : floatDatas) {
                            hashMap.put(fieldData.getFieldKey(), fieldData);
                            fieldCodeFieldDataMap.put(fieldData.getFieldCode(), fieldData);
                        }
                        RowData rowData = regionDataSet.appendRow();
                        for (MetaData metaData : metaDatas) {
                            DataValue dataValue;
                            FieldData floatData = (FieldData)fieldCodeFieldDataMap.get("SnapshotGatherFlag");
                            if (null != floatData) {
                                rowData.setGroupTreeDeep(Integer.parseInt(floatData.getData().getAsString()));
                                if (Integer.parseInt(floatData.getData().getAsString()) > -1) {
                                    String groupKey = "";
                                    FieldData floatDat = (FieldData)fieldCodeFieldDataMap.get("SnapshotGatherGroupKey");
                                    if (null != floatDat) {
                                        groupKey = floatDat.getData().getAsString();
                                    }
                                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
                                    for (FixedDimensionValue fixedDimensionValue : queryInfo.getDimensionCombination()) {
                                        builder.setValue(fixedDimensionValue);
                                    }
                                    builder.setValue("GROUP_KEY", "", (Object)groupKey);
                                    builder.setValue("GroupingDeep", "", (Object)Integer.parseInt(floatData.getData().getAsString()));
                                    rowData.setDimension(builder.getCombination());
                                }
                            }
                            if (null != (floatData = (FieldData)fieldCodeFieldDataMap.get("isFilledRow"))) {
                                String isFilledRow = floatData.getData().getAsString();
                                rowData.setFilledRow(!"0".equals(isFilledRow));
                            }
                            if (null != (floatData = (FieldData)fieldCodeFieldDataMap.get("BIZKEYORDER"))) {
                                rowData.setRecKey(floatData.getData().getAsString());
                            }
                            if (null != (floatData = (FieldData)hashMap.get(metaData.getFieldKey()))) {
                                dataValue = rowData.appendDataValue((IMetaData)metaData);
                                AbstractData floatDataValue = floatData.getData();
                                if (org.springframework.util.StringUtils.hasText(floatData.getMaskCode()) && null != floatDataValue && !floatDataValue.isNull) {
                                    String encipheredData = this.encryptor.desensitize(floatData.getMaskCode(), floatDataValue.getAsString());
                                    floatDataValue = AbstractData.valueOf((String)encipheredData);
                                }
                                dataValue.setAbstractData(floatDataValue);
                                dataValue.setRowData(rowData);
                                continue;
                            }
                            dataValue = rowData.appendDataValue((IMetaData)metaData);
                            dataValue.setAbstractData(AbstractData.valueOf(null));
                            dataValue.setRowData(rowData);
                        }
                    }
                }
            }
            if (null != regionDataSet) {
                regionDataSet.setSupportTreeGroup(true);
            }
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u67e5\u770b\u6210\u529f", "\u5feb\u7167\u67e5\u770b\u6210\u529f");
            return regionDataSet;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u67e5\u770b\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a" + e.getMessage());
            return regionDataSet;
        }
    }

    private List<List<FieldData>> filter(IQueryInfo queryInfo, List<List<FieldData>> floatDatass) {
        Iterator filterItr = queryInfo.rowFilterItr();
        if (filterItr != null) {
            while (filterItr.hasNext()) {
                RowFilter filter = (RowFilter)filterItr.next();
                if (filter instanceof InValuesFilter) {
                    InValuesFilter inFilter = (InValuesFilter)filter;
                    String link = inFilter.getLink();
                    List values = inFilter.getValues();
                    DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(link);
                    String fieldKey = dataLinkDefine.getLinkExpression();
                    ArrayList<List<FieldData>> floatDatassFilter = new ArrayList<List<FieldData>>();
                    block3: for (List<FieldData> datass : floatDatass) {
                        for (FieldData fieldData : datass) {
                            if (null == fieldData.getFieldKey() || "".equals(fieldData.getFieldKey()) || !fieldData.getFieldKey().equals(fieldKey)) continue;
                            String dataStr = fieldData.getData().getAsString();
                            if (!values.contains(dataStr)) continue block3;
                            floatDatassFilter.add(datass);
                            continue block3;
                        }
                    }
                    floatDatass = floatDatassFilter;
                    continue;
                }
                if (!(filter instanceof FormulaFilter)) continue;
                FormulaFilter formulaFilter = (FormulaFilter)filter;
                String formula = formulaFilter.toFormula();
                try {
                    DimensionValueSet dimensionValueSet = queryInfo.getDimensionCollection().combineWithoutVarDim();
                    floatDatass = this.formulaFilter(floatDatass, formula, dimensionValueSet);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        return floatDatass;
    }

    private List<List<FieldData>> formulaFilter(List<List<FieldData>> floatDatass, String formula, DimensionValueSet dimensionValueSet) throws Exception {
        ArrayList<List<FieldData>> filterFloatDatass = new ArrayList<List<FieldData>>();
        MemoryDataSet dataSet = new MemoryDataSet();
        HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        int index = 0;
        for (int i = 0; i < floatDatass.get(0).size(); ++i) {
            FieldDefine fieldDefine;
            String fieldKey = floatDatass.get(0).get(i).getFieldKey();
            String fieldCode = floatDatass.get(0).get(i).getFieldCode();
            String fieldTitle = floatDatass.get(0).get(i).getFieldTitle();
            if (!StringUtils.isNotEmpty((String)fieldKey) || !StringUtils.isNotEmpty((String)fieldCode) || !StringUtils.isNotEmpty((String)fieldTitle) || null == (fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey))) continue;
            DataField dataField = (DataField)fieldDefine;
            dataSet.getMetadata().addColumn(new Column(dataField.getCode(), dataField.getDataFieldType().toColumnModelType().getValue()));
            indexMap.put(i, index);
            ++index;
        }
        for (List<FieldData> fieldDatas : floatDatass) {
            DataRow row = dataSet.add();
            for (int i = 0; i < fieldDatas.size(); ++i) {
                boolean nullData;
                String fieldKey = fieldDatas.get(i).getFieldKey();
                String fieldCode = fieldDatas.get(i).getFieldCode();
                String fieldTitle = fieldDatas.get(i).getFieldTitle();
                if (!StringUtils.isNotEmpty((String)fieldKey) || !StringUtils.isNotEmpty((String)fieldCode) || !StringUtils.isNotEmpty((String)fieldTitle)) continue;
                Integer j = (Integer)indexMap.get(i);
                int dataType = dataSet.getMetadata().getColumn(j.intValue()).getDataType();
                AbstractData data = fieldDatas.get(i).getData();
                boolean bl = nullData = null == data || data.isNull;
                if (dataType == 6) {
                    row.setValue(j.intValue(), (Object)(nullData ? null : data.getAsString()));
                    continue;
                }
                if (dataType == 10) {
                    row.setValue(j.intValue(), nullData ? null : Double.valueOf(data.getAsFloat()));
                    continue;
                }
                if (dataType == 5) {
                    row.setValue(j.intValue(), nullData ? null : Integer.valueOf(data.getAsInt()));
                    continue;
                }
                if (dataType == 2) {
                    row.setValue(j.intValue(), (Object)(nullData ? null : data.getAsDateTimeObj()));
                    continue;
                }
                if (dataType != 1) continue;
                row.setValue(j.intValue(), nullData ? null : Boolean.valueOf(data.getAsBool()));
            }
        }
        DataSetExprEvaluator evaluator = new DataSetExprEvaluator((DataSet)dataSet);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        evaluator.prepare(executorContext, dimensionValueSet, formula);
        for (int i = 0; i < dataSet.size(); ++i) {
            boolean result = evaluator.judge(dataSet.get(i));
            if (!result) continue;
            filterFloatDatass.add(floatDatass.get(i));
        }
        return filterFloatDatass;
    }

    private void processValue(IQueryInfo queryInfo, List<List<FieldData>> datass) throws Exception {
        Measure measure = queryInfo.getMeasure();
        if (measure == null) {
            return;
        }
        MeasureData byMeasure = this.measureService.getByMeasure(measure.getKey(), measure.getCode());
        RegionRelation regionRelation = queryInfo.getRegionRelation();
        if (null == byMeasure || null == regionRelation.getMeasureData()) {
            return;
        }
        BigDecimal measureRate = byMeasure.getRateValue().divide(regionRelation.getMeasureData().getRateValue(), 20, RoundingMode.HALF_UP);
        HashMap<String, DataField> fieldCatch = new HashMap<String, DataField>();
        HashMap<String, MeasureData> measureDataCache = new HashMap<String, MeasureData>();
        for (List<FieldData> datas : datass) {
            block4: for (FieldData data : datas) {
                Integer fractionDigits;
                if (!org.springframework.util.StringUtils.hasLength(data.getFieldKey()) || null == data.getData() || data.getData().isNull) continue;
                DataField dataField = (DataField)fieldCatch.get(data.getFieldKey());
                if (null == dataField) {
                    FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(data.getFieldKey());
                    if (null == fieldDefine) continue;
                    dataField = (DataField)fieldDefine;
                    fieldCatch.put(data.getFieldKey(), dataField);
                }
                int dataType = DataTypeConvert.dataFieldType2DataType((int)dataField.getDataFieldType().getValue());
                switch (dataType) {
                    case 3: 
                    case 4: 
                    case 10: {
                        break;
                    }
                    default: {
                        continue block4;
                    }
                }
                if (dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                String fieldMeasureUnit = dataField.getMeasureUnit();
                MeasureData fieldMeasureData = null;
                if (org.springframework.util.StringUtils.hasLength(fieldMeasureUnit)) {
                    if (fieldMeasureUnit.endsWith("NotDimession")) continue;
                    fieldMeasureData = (MeasureData)measureDataCache.get(fieldMeasureUnit);
                    if (fieldMeasureData == null && !measureDataCache.containsKey(fieldMeasureUnit)) {
                        String fieldMeasureCode;
                        String[] measures = fieldMeasureUnit.split(";");
                        MeasureView measureView = regionRelation.getMeasureView();
                        MeasureData measureData = regionRelation.getMeasureData();
                        if (measures.length == 2 && measures[0].equals(measureView.getKey()) && !(fieldMeasureCode = measures[1]).equals(measureData.getCode())) {
                            fieldMeasureData = this.measureService.getByMeasure(measures[0], fieldMeasureCode);
                            measureDataCache.put(fieldMeasureUnit, fieldMeasureData);
                        }
                    }
                }
                if ((fractionDigits = dataField.getDecimal()) == null) {
                    fractionDigits = 0;
                }
                BigDecimal bigDecimal = new BigDecimal(data.getData().getAsString());
                if (fieldMeasureData == null) {
                    bigDecimal = bigDecimal.divide(measureRate, (int)fractionDigits, RoundingMode.HALF_UP);
                } else {
                    BigDecimal measureRateNew = byMeasure.getRateValue().divide(fieldMeasureData.getRateValue(), 20, RoundingMode.HALF_UP);
                    bigDecimal = bigDecimal.divide(measureRateNew, (int)fractionDigits, RoundingMode.HALF_UP);
                }
                AbstractData abstractData = AbstractData.valueOf((Object)bigDecimal, (int)10);
                data.setData(abstractData);
            }
        }
    }

    @NotNull
    private List<List<FieldData>> page(IQueryInfo queryInfo, List<List<FieldData>> floatDatass) {
        PageInfo pageInfo = queryInfo.getPageInfo();
        if (null == pageInfo) {
            return floatDatass;
        }
        int rowSize = floatDatass.size();
        if (rowSize > pageInfo.getRowsPerPage() * pageInfo.getPageIndex() && rowSize <= pageInfo.getRowsPerPage() * (pageInfo.getPageIndex() + 1)) {
            ArrayList<List<FieldData>> floatDatassPage = new ArrayList<List<FieldData>>();
            for (int i = pageInfo.getRowsPerPage() * pageInfo.getPageIndex(); i < rowSize; ++i) {
                floatDatassPage.add(floatDatass.get(i));
            }
            floatDatass = floatDatassPage;
        } else if (rowSize > pageInfo.getRowsPerPage() * pageInfo.getPageIndex()) {
            ArrayList<List<FieldData>> floatDatassPage = new ArrayList<List<FieldData>>();
            for (int i = pageInfo.getRowsPerPage() * pageInfo.getPageIndex(); i < pageInfo.getRowsPerPage() * (pageInfo.getPageIndex() + 1); ++i) {
                floatDatassPage.add(floatDatass.get(i));
            }
            floatDatass = floatDatassPage;
        }
        return floatDatass;
    }

    private void sort(IQueryInfo queryInfo, List<List<FieldData>> floatDatass) throws Exception {
        Iterator linkSortIterator = queryInfo.linkSortItr();
        if (null == linkSortIterator) {
            return;
        }
        block0: while (linkSortIterator.hasNext()) {
            LinkSort linkSort = (LinkSort)linkSortIterator.next();
            if (linkSortIterator.hasNext()) continue;
            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(linkSort.getLinkKey());
            String fieldKey = dataLinkDefine.getLinkExpression();
            List<FieldData> fieldDatas = floatDatass.get(0);
            for (int i = 0; i < fieldDatas.size(); ++i) {
                String dataFieldKey = fieldDatas.get(i).getFieldKey();
                if (null == dataFieldKey || !dataFieldKey.equals(fieldKey)) continue;
                FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(dataFieldKey);
                int dataType = ((DataField)fieldDefine).getDataFieldType().getValue();
                final int type = DataTypeConvert.dataFieldType2DataType((int)dataType);
                final int dataIndex = i;
                if (linkSort.getMode().equals((Object)SortMode.ASC)) {
                    Collections.sort(floatDatass, new Comparator<List<FieldData>>(){

                        @Override
                        public int compare(List<FieldData> o1, List<FieldData> o2) {
                            if (type == 4) {
                                int data2;
                                int data1 = o1.get(dataIndex).getData().getAsInt();
                                if (data1 < (data2 = o2.get(dataIndex).getData().getAsInt())) {
                                    return -1;
                                }
                                if (data1 > data2) {
                                    return 1;
                                }
                                return 0;
                            }
                            if (type == 5) {
                                long data2;
                                long data1 = o1.get(dataIndex).getData().getAsDate();
                                if (data1 < (data2 = o2.get(dataIndex).getData().getAsDate())) {
                                    return -1;
                                }
                                if (data1 > data2) {
                                    return 1;
                                }
                                return 0;
                            }
                            if (type == 10) {
                                BigDecimal data1 = o1.get(dataIndex).getData().getAsCurrency();
                                BigDecimal data2 = o2.get(dataIndex).getData().getAsCurrency();
                                if (null == data1 && null != data2) {
                                    return -1;
                                }
                                if (null != data1 && null == data2) {
                                    return 1;
                                }
                                if (null == data1 && null == data2) {
                                    return 0;
                                }
                                return data1.compareTo(data2);
                            }
                            String data1 = o1.get(dataIndex).getData().getAsString();
                            String data2 = o2.get(dataIndex).getData().getAsString();
                            if (null == data1 && null != data2) {
                                return -1;
                            }
                            if (null != data1 && null == data2) {
                                return 1;
                            }
                            if (null == data1 && null == data2) {
                                return 0;
                            }
                            return data1.compareTo(data2);
                        }
                    });
                    continue block0;
                }
                Collections.sort(floatDatass, new Comparator<List<FieldData>>(){

                    @Override
                    public int compare(List<FieldData> o1, List<FieldData> o2) {
                        if (type == 4) {
                            int data2;
                            int data1 = o1.get(dataIndex).getData().getAsInt();
                            if (data1 < (data2 = o2.get(dataIndex).getData().getAsInt())) {
                                return 1;
                            }
                            if (data1 > data2) {
                                return -1;
                            }
                            return 0;
                        }
                        if (type == 5) {
                            long data2;
                            long data1 = o1.get(dataIndex).getData().getAsDate();
                            if (data1 < (data2 = o2.get(dataIndex).getData().getAsDate())) {
                                return 1;
                            }
                            if (data1 > data2) {
                                return -1;
                            }
                            return 0;
                        }
                        if (type == 10) {
                            BigDecimal data1 = o1.get(dataIndex).getData().getAsCurrency();
                            BigDecimal data2 = o2.get(dataIndex).getData().getAsCurrency();
                            if (null == data1 && null != data2) {
                                return 1;
                            }
                            if (null != data1 && null == data2) {
                                return -1;
                            }
                            if (null == data1 && null == data2) {
                                return 0;
                            }
                            return data2.compareTo(data1);
                        }
                        String data1 = o1.get(dataIndex).getData().getAsString();
                        String data2 = o2.get(dataIndex).getData().getAsString();
                        if (null == data1 && null != data2) {
                            return 1;
                        }
                        if (null != data1 && null == data2) {
                            return -1;
                        }
                        if (null == data1 && null == data2) {
                            return 0;
                        }
                        return data2.compareTo(data1);
                    }
                });
                continue block0;
            }
        }
    }

    @Override
    public IRegionDataSet querySanpshotCellData(IQueryInfo queryInfo, String snapshotId) {
        RegionRelation regionRelation = queryInfo.getRegionRelation();
        FormSchemeDefine formSchemeDefine = regionRelation.getFormSchemeDefine();
        QuerySnapshotDataSourceContext context = new QuerySnapshotDataSourceContext();
        context.setFormSchemeKey(formSchemeDefine.getKey());
        context.setDimensionCombination(queryInfo.getDimensionCombination());
        DataRange dataRange = new DataRange();
        ArrayList<DataRegionRange> formAndFields = new ArrayList<DataRegionRange>();
        DataRegionRange dataRegionRange = new DataRegionRange();
        FormDefine formDefine = regionRelation.getFormDefine();
        dataRegionRange.setFormKey(formDefine.getKey());
        ArrayList<DataRegionAndFields> dataRegionAndFieldsList = new ArrayList<DataRegionAndFields>();
        DataRegionAndFields dataRegionAndFields = new DataRegionAndFields();
        dataRegionAndFields.setDataRegionKey(queryInfo.getRegionKey());
        dataRegionAndFieldsList.add(dataRegionAndFields);
        dataRegionRange.setDataRegionAndFieldsList(dataRegionAndFieldsList);
        formAndFields.add(dataRegionRange);
        dataRange.setFormAndFields(formAndFields);
        context.setDataRange(dataRange);
        context.setSnapshotId(snapshotId);
        DataSource dataSource = this.dataSourceBuilder.querySnapshotDataSource(context);
        RegionDataSet regionDataSet = null;
        DataInfo data = dataSource.getData(formDefine.getKey());
        if (null == data || null == data.getFixData() && (null == data.getFloatDatas() || data.getFloatDatas().isEmpty())) {
            DataRegionKind regionKind = regionRelation.getRegionDefine().getRegionKind();
            List metaDatas = null;
            metaDatas = DataRegionKind.DATA_REGION_SIMPLE == regionKind ? regionRelation.getMetaData(null, false) : regionRelation.getMetaData(null, true);
            regionDataSet = this.regionDataSetFactory.getRegionRelation(metaDatas, regionRelation);
            regionDataSet.setMasterDimension(queryInfo.getDimensionCombination());
            ArrayList rows = new ArrayList();
            regionDataSet.setRows(rows);
            return regionDataSet;
        }
        List<FloatRegionData> floatRegionDatas = data.getFloatDatas();
        try {
            if (null != floatRegionDatas && !floatRegionDatas.isEmpty()) {
                List<MetaData> metaDatas = regionRelation.getMetaData(null, true);
                metaDatas = this.filterMeta(queryInfo, metaDatas);
                regionDataSet = this.regionDataSetFactory.getRegionRelation(metaDatas, regionRelation);
                regionDataSet.setMasterDimension(queryInfo.getDimensionCombination());
                FloatRegionData floatRegionData = floatRegionDatas.get(0);
                List<List<FieldData>> floatDatass = floatRegionData.getFloatDatass();
                if (null != floatDatass && !floatDatass.isEmpty()) {
                    regionDataSet.setTotalCount(floatDatass.size());
                    floatDatass = this.filter(queryInfo, floatDatass);
                }
                if (null == floatDatass || floatDatass.isEmpty()) {
                    ArrayList rows = new ArrayList();
                    regionDataSet.setRows(rows);
                    regionDataSet.setTotalCount(0);
                } else {
                    for (List<FieldData> floatDatas : floatDatass) {
                        RowData rowData = regionDataSet.appendRow();
                        block3: for (MetaData metaData : metaDatas) {
                            for (FieldData floatData : floatDatas) {
                                if ("SnapshotGatherFlag".equals(floatData.getFieldCode())) {
                                    rowData.setGroupTreeDeep(Integer.parseInt(floatData.getData().getAsString()));
                                    if (Integer.parseInt(floatData.getData().getAsString()) > -1) {
                                        String groupKey = "";
                                        for (FieldData fieldData : floatDatas) {
                                            if (!"SnapshotGatherGroupKey".equals(fieldData.getFieldCode())) continue;
                                            groupKey = fieldData.getData().getAsString();
                                            break;
                                        }
                                        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
                                        for (FixedDimensionValue fixedDimensionValue : queryInfo.getDimensionCombination()) {
                                            builder.setValue(fixedDimensionValue);
                                        }
                                        builder.setValue("GROUP_KEY", "", (Object)groupKey);
                                        builder.setValue("GroupingDeep", "", (Object)Integer.parseInt(floatData.getData().getAsString()));
                                        rowData.setDimension(builder.getCombination());
                                    }
                                } else if ("isFilledRow".equals(floatData.getFieldCode())) {
                                    String isFilledRow = floatData.getData().getAsString();
                                    if ("0".equals(isFilledRow)) {
                                        rowData.setFilledRow(false);
                                    } else {
                                        rowData.setFilledRow(true);
                                    }
                                } else if ("BIZKEYORDER".equals(floatData.getFieldCode())) {
                                    rowData.setRecKey(floatData.getData().getAsString());
                                }
                                if (!metaData.getFieldKey().equals(floatData.getFieldKey())) continue;
                                boolean sameValue = false;
                                List rowDatas = regionDataSet.getRowData();
                                for (IRowData iRowData : rowDatas) {
                                    List linkDataValues = iRowData.getLinkDataValues();
                                    if (null == linkDataValues || linkDataValues.isEmpty() || null == ((IDataValue)linkDataValues.get(0)).getAbstractData() || 0 != ((IDataValue)linkDataValues.get(0)).getAbstractData().compareTo(floatData.getData())) continue;
                                    sameValue = true;
                                    break;
                                }
                                if (!sameValue) {
                                    DataValue dataValue = rowData.appendDataValue((IMetaData)metaData);
                                    AbstractData floatDataValue = floatData.getData();
                                    if (org.springframework.util.StringUtils.hasText(floatData.getMaskCode()) && null != floatDataValue && !floatDataValue.isNull) {
                                        String encipheredData = this.encryptor.desensitize(floatData.getMaskCode(), floatDataValue.getAsString());
                                        floatDataValue = AbstractData.valueOf((String)encipheredData);
                                    }
                                    dataValue.setAbstractData(floatDataValue);
                                    continue block3;
                                }
                                rowDatas.remove(rowDatas.size() - 1);
                                continue block3;
                            }
                        }
                    }
                }
            }
            if (null != regionDataSet) {
                regionDataSet.setSupportTreeGroup(true);
            }
            return regionDataSet;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return regionDataSet;
        }
    }

    private List<MetaData> filterMeta(IQueryInfo queryInfo, List<MetaData> metaDatas) {
        Iterator selectItr = queryInfo.selectLinkItr();
        if (null != selectItr) {
            ArrayList<String> fieldKeys = new ArrayList<String>();
            while (selectItr.hasNext()) {
                String linkKey = (String)selectItr.next();
                DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(linkKey);
                String fieldKey = dataLinkDefine.getLinkExpression();
                fieldKeys.add(fieldKey);
            }
            ArrayList<MetaData> filterMetaDatas = new ArrayList<MetaData>();
            for (MetaData metaData : metaDatas) {
                String fieldKey;
                DataField dataField = metaData.getDataField();
                if (null == dataField || !fieldKeys.contains(fieldKey = dataField.getKey())) continue;
                filterMetaDatas.add(metaData);
            }
            metaDatas = filterMetaDatas;
        }
        return metaDatas;
    }
}

