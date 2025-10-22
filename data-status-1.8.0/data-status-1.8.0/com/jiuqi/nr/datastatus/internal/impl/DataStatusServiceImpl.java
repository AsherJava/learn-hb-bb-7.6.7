/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataChangeListener
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.dataengine.update.UpdateDataColumn
 *  com.jiuqi.np.dataengine.update.UpdateDataRecord
 *  com.jiuqi.np.dataengine.update.UpdateDataSet
 *  com.jiuqi.np.dataengine.update.UpdateDataTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.gather.bean.event.GatherCompleteEvent
 *  com.jiuqi.nr.data.gather.bean.event.GatherCompleteSource
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor
 *  com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionContext
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormFieldInfoDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.paramcheck.ParamClearNotice
 *  com.jiuqi.nr.definition.service.impl.FormSchemeService
 *  com.jiuqi.nr.definition.util.FormDimensionValue
 *  com.jiuqi.nr.definition.util.ParamClearObject
 *  com.jiuqi.nr.definition.util.ParamClearType
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.examine.facade.DataClearParamObj
 *  com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService
 *  com.jiuqi.nr.io.tz.listener.ChangeInfo
 *  com.jiuqi.nr.io.tz.listener.ColumnData
 *  com.jiuqi.nr.io.tz.listener.DataRecord
 *  com.jiuqi.nr.io.tz.listener.TzDataChangeListener
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 */
package com.jiuqi.nr.datastatus.internal.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.dataengine.update.UpdateDataColumn;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.np.dataengine.update.UpdateDataTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.gather.bean.event.GatherCompleteEvent;
import com.jiuqi.nr.data.gather.bean.event.GatherCompleteSource;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.datastatus.facade.obj.BatchRefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.DimensionInfo;
import com.jiuqi.nr.datastatus.facade.obj.ICopySetting;
import com.jiuqi.nr.datastatus.facade.obj.RefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.RollbackStatusPar;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.obj.DataDimensionSplit;
import com.jiuqi.nr.datastatus.internal.obj.FormDimsCollection;
import com.jiuqi.nr.datastatus.internal.util.CommonUtil;
import com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.datastatus.internal.util.EntityUtil;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFieldInfoDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.paramcheck.ParamClearNotice;
import com.jiuqi.nr.definition.service.impl.FormSchemeService;
import com.jiuqi.nr.definition.util.FormDimensionValue;
import com.jiuqi.nr.definition.util.ParamClearObject;
import com.jiuqi.nr.definition.util.ParamClearType;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService;
import com.jiuqi.nr.io.tz.listener.ChangeInfo;
import com.jiuqi.nr.io.tz.listener.ColumnData;
import com.jiuqi.nr.io.tz.listener.DataRecord;
import com.jiuqi.nr.io.tz.listener.TzDataChangeListener;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class DataStatusServiceImpl
implements IDataStatusService,
IDataChangeListener,
TzDataChangeListener,
ApplicationListener<GatherCompleteEvent>,
IDataSchemeDataClearExtendService,
ParamClearNotice {
    private static final Logger logger = LoggerFactory.getLogger(DataStatusServiceImpl.class);
    @Autowired
    protected IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected DataModelService dataModelService;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected FormSchemeService formSchemeService;
    @Autowired
    protected IEntityMetaService entityMetaService;
    @Autowired
    protected PeriodEngineService periodEngineService;
    @Autowired
    protected INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    protected DataEngineAdapter dataEngineAdapter;
    @Autowired
    protected ITaskOptionController taskOptionController;
    @Autowired
    protected DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    protected EntityUtil entityUtil;
    @Autowired
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    protected IDataAccessProvider nrDataAccessProvider;
    @Autowired
    protected IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    protected ITempTableManager tempTableManager;
    @Autowired
    protected IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    protected DimensionProviderFactory dimensionProviderFactory;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onDataChange(IMonitor monitor, UpdateDataSet updateDatas) {
        DataStatusPresetInfo presetInfo;
        IDataStatusMonitor dataStatusMonitor = monitor instanceof IDataStatusMonitor ? (IDataStatusMonitor)monitor : null;
        List presetFullForms = null;
        if (dataStatusMonitor != null && (presetInfo = dataStatusMonitor.getPresetInfo()) != null) {
            presetFullForms = presetInfo.getFullForms();
        }
        HashMap<String, Map<String, Set<DimensionValueSet>>> tzDataFsFmDimInfo = new HashMap<String, Map<String, Set<DimensionValueSet>>>();
        HashMap<String, Map<String, Set<DimensionValueSet>>> ftzDataFsFmDimInfo = new HashMap<String, Map<String, Set<DimensionValueSet>>>();
        HashMap<String, FormDimsCollection> formDimsCollectionMap = new HashMap<String, FormDimsCollection>();
        HashMap<String, List<DimensionInfo>> dataSchemeDimInfoMap = new HashMap<String, List<DimensionInfo>>();
        Connection connection = null;
        try {
            String formSchemeKey;
            for (Map.Entry entry : updateDatas.getTables().entrySet()) {
                DataTable dataTable;
                String dataTableKey;
                String tableName = (String)entry.getKey();
                TableModelDefine table = this.dataModelService.getTableModelDefineByName(tableName);
                if (table == null || !StringUtils.hasText(dataTableKey = this.dataSchemeService.getDataTableByTableModel(table.getID())) || (dataTable = this.dataSchemeService.getDataTable(dataTableKey)) == null) continue;
                List<DimensionInfo> dimInfos = this.getDimInfos(dataTable.getDataSchemeKey(), dataSchemeDimInfoMap);
                UpdateDataTable updateDataTable = (UpdateDataTable)entry.getValue();
                Map<String, ColumnModelDefine> colMapByName = this.dataModelService.getColumnModelDefinesByTable(table.getID()).stream().collect(Collectors.toMap(ColumnModelDefine::getName, Function.identity(), (o1, o2) -> o1));
                if (DataTableType.ACCOUNT == dataTable.getDataTableType()) {
                    this.handleData(tzDataFsFmDimInfo, colMapByName, dimInfos, updateDataTable, formDimsCollectionMap, presetFullForms);
                    continue;
                }
                if (DataTableType.TABLE == dataTable.getDataTableType()) {
                    this.handleGdData(ftzDataFsFmDimInfo, colMapByName, dimInfos, updateDataTable, formDimsCollectionMap, presetFullForms);
                    this.handleGdUpdate(ftzDataFsFmDimInfo, colMapByName, dimInfos, updateDataTable, formDimsCollectionMap, presetFullForms);
                    continue;
                }
                this.handleData(ftzDataFsFmDimInfo, colMapByName, dimInfos, updateDataTable, formDimsCollectionMap, presetFullForms);
            }
            Map<String, Map<String, DataDimensionSplit>> merged = this.merge(tzDataFsFmDimInfo, ftzDataFsFmDimInfo);
            if (CollectionUtils.isEmpty(merged)) {
                return;
            }
            String curPeriod = this.getCurPeriod(merged);
            for (Map.Entry<String, Map<String, DataDimensionSplit>> entry : merged.entrySet()) {
                formSchemeKey = entry.getKey();
                Map<String, DataDimensionSplit> fmDims = entry.getValue();
                FormDefine formDefine = this.runTimeViewController.queryFmdmFormDefineByFormScheme(formSchemeKey);
                if (formDefine == null) continue;
                fmDims.remove(formDefine.getKey());
            }
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            for (Map.Entry<String, Map<String, DataDimensionSplit>> e : merged.entrySet()) {
                formSchemeKey = e.getKey();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                List<String> yearRestPeriods = this.getYearRestPeriods(curPeriod, formScheme);
                if (CollectionUtils.isEmpty(yearRestPeriods)) continue;
                String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                List dimensionInfos = (List)dataSchemeDimInfoMap.get(this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme());
                Map<String, Set<DimensionValueSet>> dbFmDims = this.getDbFmDims((FormDimsCollection)formDimsCollectionMap.get(formSchemeKey), statusTableName, dimensionInfos);
                Map<String, Set<DimensionValueSet>> presetFmDims = DataStatusServiceImpl.getPresetFmDims(e, dbFmDims);
                if (!CollectionUtils.isEmpty(presetFmDims)) {
                    this.presetStatusRec(connection, formScheme, yearRestPeriods, statusTableName, dimensionInfos, presetFmDims);
                }
                this.updateStatus(connection, statusTableName, dimensionInfos, e.getValue());
            }
            if (connection == null) return;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        return;
    }

    private void presetStatusRec(Connection connection, FormSchemeDefine formScheme, List<String> yearRestPeriods, String statusTableName, List<DimensionInfo> dimensionInfos, Map<String, Set<DimensionValueSet>> presetFmDims) throws SQLException {
        String uuid = UUID.randomUUID().toString();
        String insertSql = this.buildInsertSql(statusTableName, dimensionInfos.stream().map(DimensionInfo::getStatusTableCol).collect(Collectors.toList()));
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (Map.Entry<String, Set<DimensionValueSet>> entry : presetFmDims.entrySet()) {
            String formKey = entry.getKey();
            for (DimensionValueSet dimensionValueSet : entry.getValue()) {
                for (String period : yearRestPeriods) {
                    ArrayList<Object> args = new ArrayList<Object>();
                    for (DimensionInfo dimensionInfo : dimensionInfos) {
                        if ("DATATIME".equals(dimensionInfo.getColName())) {
                            args.add(period);
                            continue;
                        }
                        args.add(dimensionValueSet.getValue(dimensionInfo.getDimensionName()));
                    }
                    args.add(uuid);
                    args.add(formScheme.getKey());
                    args.add(formKey);
                    args.add(0);
                    batchValues.add(args.toArray(new Object[0]));
                }
            }
        }
        if (!CollectionUtils.isEmpty(batchValues)) {
            try {
                DataEngineUtil.batchUpdate((Connection)connection, (String)insertSql, batchValues);
            }
            catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }

    @NotNull
    protected static Map<String, Set<DimensionValueSet>> getPresetFmDims(Map.Entry<String, Map<String, DataDimensionSplit>> e, Map<String, Set<DimensionValueSet>> dbFmDims) {
        HashMap<String, Set<DimensionValueSet>> presetFmDims = new HashMap<String, Set<DimensionValueSet>>();
        for (Map.Entry<String, DataDimensionSplit> entry : e.getValue().entrySet()) {
            Set<DimensionValueSet> dbDims;
            String formKey = entry.getKey();
            DataDimensionSplit dataDimensionSplit = entry.getValue();
            if (!dbFmDims.containsKey(formKey)) {
                if (!presetFmDims.containsKey(formKey)) {
                    presetFmDims.put(formKey, new HashSet());
                }
                ((Set)presetFmDims.get(formKey)).addAll(dataDimensionSplit.getFtzDimensionValueSet());
                ((Set)presetFmDims.get(formKey)).addAll(dataDimensionSplit.getTzDimensionValueSet());
                continue;
            }
            for (DimensionValueSet dimensionValueSet : dataDimensionSplit.getTzDimensionValueSet()) {
                dbDims = dbFmDims.get(formKey);
                if (dbDims.contains(dimensionValueSet)) continue;
                if (!presetFmDims.containsKey(formKey)) {
                    presetFmDims.put(formKey, new HashSet());
                }
                ((Set)presetFmDims.get(formKey)).add(dimensionValueSet);
            }
            for (DimensionValueSet dimensionValueSet : dataDimensionSplit.getFtzDimensionValueSet()) {
                dbDims = dbFmDims.get(formKey);
                if (dbDims.contains(dimensionValueSet)) continue;
                if (!presetFmDims.containsKey(formKey)) {
                    presetFmDims.put(formKey, new HashSet());
                }
                ((Set)presetFmDims.get(formKey)).add(dimensionValueSet);
            }
        }
        return presetFmDims;
    }

    @NotNull
    protected Map<String, Set<DimensionValueSet>> getDbFmDims(FormDimsCollection formDimsCollection, String statusTableName, List<DimensionInfo> dimensionInfos) throws Exception {
        DimensionValueSet mergeDim = CommonUtil.merge(formDimsCollection.getDimensionValueSets());
        TableModelDefine statusTable = this.dataModelService.getTableModelDefineByName(statusTableName);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List columns = this.dataModelService.getColumnModelDefinesByTable(statusTable.getID());
        Map columnMapByCode = columns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity()));
        HashMap<String, Integer> columnIndexMap = new HashMap<String, Integer>();
        int index = 0;
        for (ColumnModelDefine o : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(o));
            columnIndexMap.put(o.getCode(), index++);
        }
        queryModel.getColumnFilters().put(columnMapByCode.get("DAST_FORMKEY"), new ArrayList<String>(formDimsCollection.getFormKeys()));
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(statusTableName);
        for (int i = 0; i < mergeDim.size(); ++i) {
            String name = mergeDim.getName(i);
            ColumnModelDefine column = dimensionChanger.getColumn(name);
            if (column == null) continue;
            queryModel.getColumnFilters().put(column, mergeDim.getValue(i));
        }
        INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        HashMap<String, Set<DimensionValueSet>> dbFmDims = new HashMap<String, Set<DimensionValueSet>>();
        MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(new DataAccessContext(this.dataModelService));
        for (DataRow dataRow : dataRows) {
            String formKey = dataRow.getString(((Integer)columnIndexMap.get("DAST_FORMKEY")).intValue());
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            for (DimensionInfo dimensionInfo : dimensionInfos) {
                dimensionValueSet.setValue(dimensionInfo.getDimensionName(), (Object)dataRow.getString(((Integer)columnIndexMap.get(dimensionInfo.getStatusTableCol())).intValue()));
            }
            if (!dbFmDims.containsKey(formKey)) {
                dbFmDims.put(formKey, new HashSet());
            }
            ((Set)dbFmDims.get(formKey)).add(dimensionValueSet);
        }
        return dbFmDims;
    }

    protected void handleGdUpdate(Map<String, Map<String, Set<DimensionValueSet>>> ftzDataFsFmDimInfo, Map<String, ColumnModelDefine> colMapByName, List<DimensionInfo> dimInfos, UpdateDataTable updateDataTable, Map<String, FormDimsCollection> formDimsCollectionMap, List<String> presetFullForms) {
        HashMap<String, Collection<FormFieldInfoDefine>> columnFormsMap = new HashMap<String, Collection<FormFieldInfoDefine>>();
        Optional any = updateDataTable.getUpdateRecords().values().stream().findAny();
        if (any.isPresent()) {
            UpdateDataRecord gdUpdateRecord = (UpdateDataRecord)any.get();
            for (UpdateDataColumn updateColumn : gdUpdateRecord.getUpdateColumns()) {
                Collection formKeysByField;
                String colName = updateColumn.getName();
                ColumnModelDefine column = colMapByName.get(colName);
                DataField dataField = this.dataSchemeService.getDataFieldByColumnKey(column.getID());
                if (dataField == null || CollectionUtils.isEmpty(formKeysByField = this.runTimeViewController.getFormInfosByField(dataField.getKey()))) continue;
                ArrayList<FormFieldInfoDefine> forms = new ArrayList<FormFieldInfoDefine>(formKeysByField);
                if (!CollectionUtils.isEmpty(presetFullForms)) {
                    forms.removeIf(o -> presetFullForms.contains(o.getFormKey()));
                }
                if (CollectionUtils.isEmpty(forms)) continue;
                columnFormsMap.put(colName, forms);
            }
            if (CollectionUtils.isEmpty(columnFormsMap)) {
                return;
            }
            HashMap<String, Set<DimensionValueSet>> colDimsMap = new HashMap<String, Set<DimensionValueSet>>();
            for (Map.Entry e : updateDataTable.getUpdateRecords().entrySet()) {
                DimensionValueSet rowKey = (DimensionValueSet)e.getKey();
                UpdateDataRecord updateDataRecord = (UpdateDataRecord)e.getValue();
                for (UpdateDataColumn updateColumn : updateDataRecord.getUpdateColumns()) {
                    if (updateColumn.getValue() == null || !StringUtils.hasText(updateColumn.getValue().toString())) continue;
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    for (DimensionInfo dimInfo : dimInfos) {
                        dimensionValueSet.setValue(dimInfo.getDimensionName(), rowKey.getValue(dimInfo.getDimensionName()));
                    }
                    if (!colDimsMap.containsKey(updateColumn.getName())) {
                        colDimsMap.put(updateColumn.getName(), new HashSet());
                    }
                    ((Set)colDimsMap.get(updateColumn.getName())).add(dimensionValueSet);
                }
            }
            this.transferColData2Form(colDimsMap, ftzDataFsFmDimInfo, columnFormsMap, formDimsCollectionMap);
        }
    }

    protected String getCurPeriod(Map<String, Map<String, DataDimensionSplit>> merged) {
        Optional<DataDimensionSplit> any = merged.values().stream().findAny().get().values().stream().findAny();
        if (any.isPresent()) {
            Optional any1 = any.get().getTzDimensionValueSet().stream().findAny();
            if (any1.isPresent()) {
                return ((DimensionValueSet)any1.get()).getValue(this.getPeriodDimName()).toString();
            }
            Optional any2 = any.get().getFtzDimensionValueSet().stream().findAny();
            if (any2.isPresent()) {
                return ((DimensionValueSet)any2.get()).getValue(this.getPeriodDimName()).toString();
            }
        }
        return "";
    }

    private void updateStatus(Connection connection, String statusTableName, List<DimensionInfo> dimensionInfos, Map<String, DataDimensionSplit> fmDims) throws SQLException {
        StringBuilder tzSql = new StringBuilder("update ").append(statusTableName).append(" set ").append("DAST_ISENTRY").append("=? ");
        tzSql.append(" where ").append("DAST_FORMKEY").append("=? ");
        for (DimensionInfo dimInfo : dimensionInfos) {
            if ("PERIOD".equals(dimInfo.getStatusTableCol())) continue;
            tzSql.append(" and ").append(dimInfo.getStatusTableCol());
            tzSql.append("=? ");
        }
        StringBuilder ftzSql = new StringBuilder(tzSql);
        tzSql.append(" and ").append("PERIOD").append(">=?");
        ftzSql.append(" and ").append("PERIOD").append("=?");
        ArrayList<Object[]> tzBatchValues = new ArrayList<Object[]>();
        ArrayList<Object[]> ftzBatchValues = new ArrayList<Object[]>();
        for (Map.Entry<String, DataDimensionSplit> e : fmDims.entrySet()) {
            String formKey = e.getKey();
            DataDimensionSplit dataDimensionSplit = e.getValue();
            for (DimensionValueSet tzDim : dataDimensionSplit.getTzDimensionValueSet()) {
                this.fillBatchArgs(dimensionInfos, tzBatchValues, formKey, tzDim);
            }
            for (DimensionValueSet ftzDim : dataDimensionSplit.getFtzDimensionValueSet()) {
                this.fillBatchArgs(dimensionInfos, ftzBatchValues, formKey, ftzDim);
            }
        }
        if (!tzBatchValues.isEmpty()) {
            DataEngineUtil.batchUpdate((Connection)connection, (String)tzSql.toString(), tzBatchValues);
        }
        if (!ftzBatchValues.isEmpty()) {
            DataEngineUtil.batchUpdate((Connection)connection, (String)ftzSql.toString(), ftzBatchValues);
        }
    }

    private void fillBatchArgs(List<DimensionInfo> dimensionInfos, List<Object[]> tzBatchValues, String formKey, DimensionValueSet tzDim) {
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(1);
        args.add(formKey);
        Object period = null;
        for (DimensionInfo dimensionInfo : dimensionInfos) {
            if ("PERIOD".equals(dimensionInfo.getStatusTableCol())) {
                period = tzDim.getValue(dimensionInfo.getDimensionName());
                continue;
            }
            args.add(tzDim.getValue(dimensionInfo.getDimensionName()));
        }
        args.add(period);
        tzBatchValues.add(args.toArray());
    }

    protected List<String> getYearRestPeriods(String period, FormSchemeDefine formSchemeDefine) {
        Set<Object> formSchemePeriodSet = new HashSet();
        try {
            List schemePeriodLinkDefines = this.runTimeViewController.querySchemePeriodLinkByScheme(formSchemeDefine.getKey());
            formSchemePeriodSet = schemePeriodLinkDefines.stream().map(SchemePeriodLinkDefine::getPeriodKey).collect(Collectors.toSet());
            if (!formSchemePeriodSet.contains(period)) {
                return Collections.emptyList();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        ArrayList<String> result = new ArrayList<String>();
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formSchemeDefine.getDateTime());
        boolean action = false;
        int year = 0;
        for (IPeriodRow periodItem : periodProvider.getPeriodItems()) {
            if (periodItem.getCode().equals(period)) {
                action = true;
                year = periodItem.getYear();
            }
            if (!action) continue;
            if (year != periodItem.getYear()) break;
            result.add(periodItem.getCode());
        }
        if (result.size() > 0) {
            result.retainAll(formSchemePeriodSet);
        }
        return result;
    }

    protected Map<String, Map<String, DataDimensionSplit>> merge(Map<String, Map<String, Set<DimensionValueSet>>> tz, Map<String, Map<String, Set<DimensionValueSet>>> ftz) {
        Set<DimensionValueSet> dims;
        String formKey;
        Map map;
        Map<String, Set<DimensionValueSet>> fmDimsMap;
        String formSchemeKey;
        HashMap<String, Map<String, DataDimensionSplit>> result = new HashMap<String, Map<String, DataDimensionSplit>>();
        for (Map.Entry<String, Map<String, Set<DimensionValueSet>>> e : ftz.entrySet()) {
            formSchemeKey = e.getKey();
            fmDimsMap = e.getValue();
            if (!result.containsKey(formSchemeKey)) {
                result.put(formSchemeKey, new HashMap());
            }
            map = (Map)result.get(formSchemeKey);
            for (Map.Entry<String, Set<DimensionValueSet>> e2 : fmDimsMap.entrySet()) {
                formKey = e2.getKey();
                dims = e2.getValue();
                if (!map.containsKey(formKey)) {
                    map.put(formKey, new DataDimensionSplit());
                }
                if (this.judgeFormData(tz, formSchemeKey, formKey)) {
                    Set<DimensionValueSet> tzDims = tz.get(formSchemeKey).get(formKey);
                    ((DataDimensionSplit)map.get(formKey)).getTzDimensionValueSet().addAll(tzDims);
                    dims.removeAll(tzDims);
                    ((DataDimensionSplit)map.get(formKey)).getFtzDimensionValueSet().addAll(dims);
                    continue;
                }
                ((DataDimensionSplit)map.get(formKey)).getFtzDimensionValueSet().addAll(dims);
            }
        }
        for (Map.Entry<String, Map<String, Set<DimensionValueSet>>> e : tz.entrySet()) {
            formSchemeKey = e.getKey();
            fmDimsMap = e.getValue();
            if (!result.containsKey(formSchemeKey)) {
                result.put(formSchemeKey, new HashMap());
            }
            map = (Map)result.get(formSchemeKey);
            for (Map.Entry<String, Set<DimensionValueSet>> e2 : fmDimsMap.entrySet()) {
                formKey = e2.getKey();
                dims = e2.getValue();
                if (!map.containsKey(formKey)) {
                    map.put(formKey, new DataDimensionSplit());
                }
                ((DataDimensionSplit)map.get(formKey)).getTzDimensionValueSet().addAll(dims);
            }
        }
        return result;
    }

    private boolean judgeFormData(Map<String, Map<String, Set<DimensionValueSet>>> fsFmDims, String formSchemeKey, String formKey) {
        return fsFmDims.containsKey(formSchemeKey) && fsFmDims.get(formSchemeKey).containsKey(formKey);
    }

    protected void handleData(Map<String, Map<String, Set<DimensionValueSet>>> fsFmDimsMap, Map<String, ColumnModelDefine> colMapByName, List<DimensionInfo> dimInfos, UpdateDataTable updateDataTable, Map<String, FormDimsCollection> formDimsCollectionMap, List<String> presetFullForms) {
        ArrayList<FormFieldInfoDefine> columnForms = null;
        Optional any = updateDataTable.getInsertRecords().stream().findAny();
        if (any.isPresent()) {
            UpdateDataRecord updateDataRecord = (UpdateDataRecord)any.get();
            for (UpdateDataColumn updateColumn : updateDataRecord.getUpdateColumns()) {
                Collection formKeysByField;
                DataField dataField;
                String colName = updateColumn.getName();
                ColumnModelDefine column = colMapByName.get(colName);
                if (column == null || (dataField = this.dataSchemeService.getDataFieldByColumnKey(column.getID())) == null || CollectionUtils.isEmpty(formKeysByField = this.runTimeViewController.getFormInfosByField(dataField.getKey()))) continue;
                columnForms = new ArrayList<FormFieldInfoDefine>(formKeysByField);
                break;
            }
            if (columnForms == null) {
                return;
            }
            if (!CollectionUtils.isEmpty(presetFullForms)) {
                columnForms.removeIf(o -> presetFullForms.contains(o.getFormKey()));
            }
            if (CollectionUtils.isEmpty(columnForms)) {
                return;
            }
            HashSet<DimensionValueSet> dataDims = new HashSet<DimensionValueSet>();
            Set dimCols = dimInfos.stream().map(DimensionInfo::getColName).collect(Collectors.toSet());
            block1: for (UpdateDataRecord insertRecord : updateDataTable.getInsertRecords()) {
                for (UpdateDataColumn updateColumn : insertRecord.getUpdateColumns()) {
                    if (dimCols.contains(updateColumn.getName()) || updateColumn.getValue() == null || !StringUtils.hasText(updateColumn.getValue().toString())) continue;
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    DimensionValueSet rowKeys = insertRecord.getRowkeys();
                    for (DimensionInfo dimInfo : dimInfos) {
                        Optional<UpdateDataColumn> first;
                        Object value = rowKeys.getValue(dimInfo.getDimensionName());
                        if ("DATATIME".equals(dimInfo.getDimensionName()) && value == null && (first = insertRecord.getUpdateColumns().stream().filter(o -> o.getName().equals("VALIDDATATIME")).findFirst()).isPresent()) {
                            value = first.get().getValue();
                        }
                        dimensionValueSet.setValue(dimInfo.getDimensionName(), value);
                    }
                    dataDims.add(dimensionValueSet);
                    continue block1;
                }
            }
            this.buildFsFmDimsByDataForms(fsFmDimsMap, (Collection<FormFieldInfoDefine>)columnForms, dataDims, formDimsCollectionMap);
        }
    }

    protected void handleGdData(Map<String, Map<String, Set<DimensionValueSet>>> fsFmDimsMap, Map<String, ColumnModelDefine> colMapByName, List<DimensionInfo> dimInfos, UpdateDataTable updateDataTable, Map<String, FormDimsCollection> formDimsCollectionMap, List<String> presetFullForms) {
        HashMap<String, Collection<FormFieldInfoDefine>> columnFormsMap = new HashMap<String, Collection<FormFieldInfoDefine>>();
        Optional any = updateDataTable.getInsertRecords().stream().findAny();
        if (any.isPresent()) {
            UpdateDataRecord updateDataRecord = (UpdateDataRecord)any.get();
            for (UpdateDataColumn updateColumn : updateDataRecord.getUpdateColumns()) {
                Collection formKeysByField;
                String colName = updateColumn.getName();
                ColumnModelDefine column = colMapByName.get(colName);
                DataField dataField = this.dataSchemeService.getDataFieldByColumnKey(column.getID());
                if (dataField == null || CollectionUtils.isEmpty(formKeysByField = this.runTimeViewController.getFormInfosByField(dataField.getKey()))) continue;
                ArrayList<FormFieldInfoDefine> forms = new ArrayList<FormFieldInfoDefine>(formKeysByField);
                if (!CollectionUtils.isEmpty(presetFullForms)) {
                    forms.removeIf(o -> presetFullForms.contains(o.getFormKey()));
                }
                if (CollectionUtils.isEmpty(forms)) continue;
                columnFormsMap.put(colName, forms);
            }
            if (CollectionUtils.isEmpty(columnFormsMap)) {
                return;
            }
            HashMap<String, Set<DimensionValueSet>> colDimsMap = new HashMap<String, Set<DimensionValueSet>>();
            Set dimCols = dimInfos.stream().map(DimensionInfo::getColName).collect(Collectors.toSet());
            for (UpdateDataRecord insertRecord : updateDataTable.getInsertRecords()) {
                for (UpdateDataColumn updateColumn : insertRecord.getUpdateColumns()) {
                    if (dimCols.contains(updateColumn.getName()) || updateColumn.getValue() == null || !StringUtils.hasText(updateColumn.getValue().toString())) continue;
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    DimensionValueSet rowKeys = insertRecord.getRowkeys();
                    for (DimensionInfo dimInfo : dimInfos) {
                        Optional<UpdateDataColumn> first;
                        Object value = rowKeys.getValue(dimInfo.getDimensionName());
                        if ("DATATIME".equals(dimInfo.getDimensionName()) && value == null && (first = insertRecord.getUpdateColumns().stream().filter(o -> o.getName().equals("VALIDDATATIME")).findFirst()).isPresent()) {
                            value = first.get().getValue();
                        }
                        dimensionValueSet.setValue(dimInfo.getDimensionName(), value);
                    }
                    if (!colDimsMap.containsKey(updateColumn.getName())) {
                        colDimsMap.put(updateColumn.getName(), new HashSet());
                    }
                    ((Set)colDimsMap.get(updateColumn.getName())).add(dimensionValueSet);
                }
            }
            this.transferColData2Form(colDimsMap, fsFmDimsMap, columnFormsMap, formDimsCollectionMap);
        }
    }

    private void transferColData2Form(Map<String, Set<DimensionValueSet>> colDimsMap, Map<String, Map<String, Set<DimensionValueSet>>> fsFmDimsMap, Map<String, Collection<FormFieldInfoDefine>> columnFormsMap, Map<String, FormDimsCollection> formDimsCollectionMap) {
        for (Map.Entry<String, Set<DimensionValueSet>> e : colDimsMap.entrySet()) {
            String colName = e.getKey();
            Set<DimensionValueSet> dataDims = e.getValue();
            Collection<FormFieldInfoDefine> formKeys = columnFormsMap.get(colName);
            if (CollectionUtils.isEmpty(formKeys)) continue;
            this.buildFsFmDimsByDataForms(fsFmDimsMap, formKeys, dataDims, formDimsCollectionMap);
        }
    }

    private boolean ifOpen(String taskKey, Map<String, Boolean> tkOptionMap) {
        if (!tkOptionMap.containsKey(taskKey)) {
            tkOptionMap.put(taskKey, this.openStatus(taskKey));
        }
        return tkOptionMap.get(taskKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onDataChange(ChangeInfo changeInfo) {
        if (CollectionUtils.isEmpty(changeInfo.getInsertRecords())) {
            return;
        }
        HashMap<String, Map<String, Set<DimensionValueSet>>> fsFmDims = new HashMap<String, Map<String, Set<DimensionValueSet>>>();
        HashMap<String, FormDimsCollection> formDimsCollectionMap = new HashMap<String, FormDimsCollection>();
        DataTable dataTable = changeInfo.getTable();
        Connection connection = null;
        try {
            String formSchemeKey;
            List<DimensionInfo> dimInfos = this.getDimInfos(dataTable.getDataSchemeKey(), new HashMap<String, List<DimensionInfo>>());
            this.handleData(fsFmDims, dimInfos, changeInfo, formDimsCollectionMap);
            for (Map.Entry entry : fsFmDims.entrySet()) {
                formSchemeKey = (String)entry.getKey();
                Map fmDims = (Map)entry.getValue();
                FormDefine formDefine = this.runTimeViewController.queryFmdmFormDefineByFormScheme(formSchemeKey);
                if (formDefine == null) continue;
                fmDims.remove(formDefine.getKey());
            }
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            for (Map.Entry<String, Map<String, Set<DimensionValueSet>>> entry : fsFmDims.entrySet()) {
                formSchemeKey = (String)entry.getKey();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                List<String> yearRestPeriods = this.getYearRestPeriods(changeInfo.getDatatime(), formScheme);
                if (CollectionUtils.isEmpty(yearRestPeriods)) continue;
                String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                Map<String, Set<DimensionValueSet>> dbFmDims = this.getDbFmDims((FormDimsCollection)formDimsCollectionMap.get(formSchemeKey), statusTableName, dimInfos);
                Map<String, Set<DimensionValueSet>> presetFmDims = this.getTzImpPresetFmDims(entry, dbFmDims);
                if (!CollectionUtils.isEmpty(presetFmDims)) {
                    this.presetStatusRec(connection, formScheme, yearRestPeriods, statusTableName, dimInfos, presetFmDims);
                }
                this.updateTZImpStatus(connection, statusTableName, dimInfos, entry.getValue());
            }
            if (connection == null) return;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        return;
    }

    private Map<String, Set<DimensionValueSet>> getTzImpPresetFmDims(Map.Entry<String, Map<String, Set<DimensionValueSet>>> e, Map<String, Set<DimensionValueSet>> dbFmDims) {
        HashMap<String, Set<DimensionValueSet>> presetFmDims = new HashMap<String, Set<DimensionValueSet>>();
        for (Map.Entry<String, Set<DimensionValueSet>> entry : e.getValue().entrySet()) {
            String formKey = entry.getKey();
            if (!dbFmDims.containsKey(formKey)) {
                if (!presetFmDims.containsKey(formKey)) {
                    presetFmDims.put(formKey, new HashSet());
                }
                ((Set)presetFmDims.get(formKey)).addAll((Collection)entry.getValue());
                continue;
            }
            for (DimensionValueSet dimensionValueSet : entry.getValue()) {
                Set<DimensionValueSet> dbDims = dbFmDims.get(formKey);
                if (dbDims.contains(dimensionValueSet)) continue;
                if (!presetFmDims.containsKey(formKey)) {
                    presetFmDims.put(formKey, new HashSet());
                }
                ((Set)presetFmDims.get(formKey)).add(dimensionValueSet);
            }
        }
        return presetFmDims;
    }

    private void updateTZImpStatus(Connection connection, String statusTableName, List<DimensionInfo> dimInfos, Map<String, Set<DimensionValueSet>> fmDims) throws SQLException {
        StringBuilder sql = new StringBuilder("update ").append(statusTableName).append(" set ").append("DAST_ISENTRY").append("=? ");
        sql.append(" where ").append("DAST_FORMKEY").append("=? ");
        for (DimensionInfo dimInfo : dimInfos) {
            sql.append(" and ").append(dimInfo.getStatusTableCol());
            if ("PERIOD".equals(dimInfo.getStatusTableCol())) {
                sql.append(">=? ");
                continue;
            }
            sql.append("=? ");
        }
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (Map.Entry<String, Set<DimensionValueSet>> e : fmDims.entrySet()) {
            String formKey = e.getKey();
            for (DimensionValueSet dimensionValueSet : e.getValue()) {
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(1);
                args.add(formKey);
                dimInfos.forEach(o -> args.add(dimensionValueSet.getValue(o.getDimensionName())));
                batchValues.add(args.toArray());
            }
        }
        if (batchValues.isEmpty()) {
            return;
        }
        DataEngineUtil.batchUpdate((Connection)connection, (String)sql.toString(), batchValues);
    }

    private void handleData(Map<String, Map<String, Set<DimensionValueSet>>> fsFmDims, List<DimensionInfo> dimInfos, ChangeInfo changeInfo, Map<String, FormDimsCollection> formDimsCollectionMap) {
        List insertRecords = changeInfo.getInsertRecords();
        Map dataFieldMap = changeInfo.getAllFields().stream().collect(Collectors.toMap(Basic::getCode, Function.identity(), (o1, o2) -> o1));
        Collection fieldFormKeys = null;
        for (String dataFieldCode : ((DataRecord)insertRecords.get(0)).getColumnData().keySet()) {
            DataField dataField = (DataField)dataFieldMap.get(dataFieldCode);
            Collection formKeysByField = this.runTimeViewController.getFormInfosByField(dataField.getKey());
            if (CollectionUtils.isEmpty(formKeysByField)) continue;
            fieldFormKeys = formKeysByField;
            break;
        }
        if (fieldFormKeys == null) {
            return;
        }
        HashSet<DimensionValueSet> dataDimMap = new HashSet<DimensionValueSet>();
        for (DataRecord insertRecord : insertRecords) {
            if (insertRecord == null || CollectionUtils.isEmpty(insertRecord.getColumnData())) continue;
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            boolean valid = true;
            for (DimensionInfo dimInfo : dimInfos) {
                Object dimValue;
                if ("DATATIME".equals(dimInfo.getDimensionName())) {
                    dimValue = changeInfo.getDatatime();
                } else {
                    String colName = dimInfo.getColName();
                    ColumnData columnData = (ColumnData)insertRecord.getColumnData().get(colName);
                    if (columnData == null) {
                        valid = false;
                        break;
                    }
                    dimValue = columnData.getValue();
                }
                dimensionValueSet.setValue(dimInfo.getDimensionName(), dimValue);
            }
            if (!valid) continue;
            dataDimMap.add(dimensionValueSet);
        }
        this.buildFsFmDimsByDataForms(fsFmDims, fieldFormKeys, dataDimMap, formDimsCollectionMap);
    }

    private void buildFsFmDimsByDataForms(Map<String, Map<String, Set<DimensionValueSet>>> fsFmDims, Collection<FormFieldInfoDefine> fieldFormKeys, Set<DimensionValueSet> dataDims, Map<String, FormDimsCollection> formDimsCollectionMap) {
        Optional any = dataDims.stream().findAny();
        if (!any.isPresent()) {
            return;
        }
        HashMap<String, Boolean> tkOptionMap = new HashMap<String, Boolean>();
        HashMap<String, String> taskFormShcemeMap = new HashMap<String, String>();
        String period = String.valueOf(((DimensionValueSet)any.get()).getValue("DATATIME"));
        for (FormFieldInfoDefine formFieldInfo : fieldFormKeys) {
            Map<String, Set<DimensionValueSet>> fmDimsMap;
            if (formFieldInfo == null || !StringUtils.hasText(formFieldInfo.getFormKey()) || !StringUtils.hasText(formFieldInfo.getFormSchemeKey()) || !StringUtils.hasText(formFieldInfo.getTaskKey()) || !this.isFormSchemeUsed(period, taskFormShcemeMap, formFieldInfo) || !this.ifOpen(formFieldInfo.getTaskKey(), tkOptionMap)) continue;
            if (!fsFmDims.containsKey(formFieldInfo.getFormSchemeKey())) {
                fsFmDims.put(formFieldInfo.getFormSchemeKey(), new HashMap());
            }
            if (!(fmDimsMap = fsFmDims.get(formFieldInfo.getFormSchemeKey())).containsKey(formFieldInfo.getFormKey())) {
                fmDimsMap.put(formFieldInfo.getFormKey(), new HashSet());
            }
            fmDimsMap.get(formFieldInfo.getFormKey()).addAll(dataDims);
            if (!formDimsCollectionMap.containsKey(formFieldInfo.getFormSchemeKey())) {
                formDimsCollectionMap.put(formFieldInfo.getFormSchemeKey(), new FormDimsCollection());
            }
            FormDimsCollection formDimsCollection = formDimsCollectionMap.get(formFieldInfo.getFormSchemeKey());
            formDimsCollection.getFormKeys().add(formFieldInfo.getFormKey());
            formDimsCollection.getDimensionValueSets().addAll(dataDims);
        }
    }

    @Override
    public List<String> getFilledPeriod(String formSchemeKey, DimensionCollection filterDim) {
        DimensionValueSet dimensionValueSet = CommonUtil.getMergeDimensionValueSet(filterDim);
        return this.groupByCol(formSchemeKey, dimensionValueSet, "PERIOD");
    }

    @Override
    public List<String> getFilledAdjust(String formSchemeKey, String period) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        dimensionCollectionBuilder.setEntityValue(this.getPeriodDimName(), formScheme.getDateTime(), new Object[]{period});
        String dwDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", new DimensionProviderData(null, taskDefine.getDataScheme()));
        dimensionCollectionBuilder.addVariableDW(dwDimName, formScheme.getDw(), dimensionProvider);
        dimensionCollectionBuilder.setContext(new DimensionContext(formScheme.getTaskKey()));
        DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
        DimensionValueSet dimensionValueSet = CommonUtil.getMergeDimensionValueSet(dimensionCollection);
        return this.groupByCol(formSchemeKey, dimensionValueSet, "ADJUST");
    }

    @Override
    public List<String> getFilledUnit(String formSchemeKey, DimensionCollection filterDim) {
        DimensionValueSet dimensionValueSet = CommonUtil.getMergeDimensionValueSet(filterDim);
        return this.groupByCol(formSchemeKey, dimensionValueSet, "MDCODE");
    }

    @Override
    public List<String> getFilledFormKey(String formSchemeKey, DimensionCombination filterDim) {
        return this.query(formSchemeKey, filterDim.toDimensionValueSet(), "DAST_FORMKEY", false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void refreshDataStatus(RefreshStatusPar par) throws Exception {
        String taskKey = par.getTaskKey();
        List<String> periods = par.getPeriods();
        HashMap<String, List<DimensionInfo>> fsDimInfos = new HashMap<String, List<DimensionInfo>>();
        String uuid = UUID.randomUUID().toString();
        Map periodLinkMap = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey).stream().collect(Collectors.toMap(SchemePeriodLinkDefine::getPeriodKey, Function.identity(), (o1, o2) -> o1));
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            for (String period : periods) {
                SchemePeriodLinkDefine schemePeriodLinkDefine = (SchemePeriodLinkDefine)periodLinkMap.get(period);
                if (schemePeriodLinkDefine == null) continue;
                String schemeKey = schemePeriodLinkDefine.getSchemeKey();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemeKey);
                List<DimensionInfo> dimInfos = this.getDimInfos(formScheme, fsDimInfos);
                Map dimInfoByColName = dimInfos.stream().collect(Collectors.toMap(DimensionInfo::getColName, Function.identity(), (o1, o2) -> o1));
                List<DimensionValueSet> allDimensionValueSets = this.getAllDimensionsInFS(formScheme, dimInfos.stream().map(DimensionInfo::getDimensionName).collect(Collectors.toList()), period);
                String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                List<String> statusTableDimCols = dimInfos.stream().map(DimensionInfo::getStatusTableCol).collect(Collectors.toList());
                String delSql = "delete from " + statusTableName + " where " + "PERIOD" + "=?";
                this.jdbcTemplate.update(delSql, new Object[]{period});
                List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(schemeKey);
                for (FormDefine formDefine : formDefines) {
                    if (FormType.FORM_TYPE_NEWFMDM == formDefine.getFormType()) continue;
                    HashSet<DimensionValueSet> havaDataDims = new HashSet<DimensionValueSet>();
                    List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
                    for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                        DimensionValueSet dimensionValueSet;
                        Object sql;
                        Object tableNameByRegion;
                        if (this.judgeTzRegion(dataRegionDefine.getKey())) {
                            tableNameByRegion = this.getTableNameByRegion(dataRegionDefine.getKey(), true);
                            sql = new StringBuilder("(select ");
                            for (DimensionInfo dimensionInfo : dimInfos) {
                                if ("DATATIME".equals(dimensionInfo.getColName())) continue;
                                ((StringBuilder)sql).append("min(").append(dimensionInfo.getColName()).append(") as ").append(dimensionInfo.getColName()).append(",");
                            }
                            ((StringBuilder)sql).setLength(((StringBuilder)sql).length() - 1);
                            ((StringBuilder)sql).append(" from ").append((String)tableNameByRegion).append(" where VALIDDATATIME<=? group by ");
                            for (DimensionInfo dimensionInfo : dimInfos) {
                                ((StringBuilder)sql).append(dimensionInfo.getTZTableCol()).append(",");
                            }
                            ((StringBuilder)sql).setLength(((StringBuilder)sql).length() - 1);
                            ((StringBuilder)sql).append(")");
                            ((StringBuilder)sql).append(" union all ");
                            ((StringBuilder)sql).append("(select ");
                            for (DimensionInfo dimensionInfo : dimInfos) {
                                if ("DATATIME".equals(dimensionInfo.getColName())) continue;
                                ((StringBuilder)sql).append("min(").append(dimensionInfo.getColName()).append(") as ").append(dimensionInfo.getColName()).append(",");
                            }
                            ((StringBuilder)sql).setLength(((StringBuilder)sql).length() - 1);
                            ((StringBuilder)sql).append(" from ").append((String)tableNameByRegion).append("_HIS").append(" where VALIDDATATIME<=? and INVALIDDATATIME>? group by ");
                            for (DimensionInfo dimensionInfo : dimInfos) {
                                ((StringBuilder)sql).append(dimensionInfo.getTZTableCol()).append(",");
                            }
                            ((StringBuilder)sql).setLength(((StringBuilder)sql).length() - 1);
                            ((StringBuilder)sql).append(")");
                            List list = this.jdbcTemplate.queryForList(((StringBuilder)sql).toString(), new Object[]{period, period, period});
                            for (Map map : list) {
                                dimensionValueSet = new DimensionValueSet();
                                for (Map.Entry entry : map.entrySet()) {
                                    dimensionValueSet.setValue(((DimensionInfo)dimInfoByColName.get(((String)entry.getKey()).toUpperCase(Locale.ROOT))).getDimensionName(), (Object)String.valueOf(entry.getValue()));
                                }
                                dimensionValueSet.setValue(this.getPeriodDimName(), (Object)period);
                                havaDataDims.add(dimensionValueSet);
                            }
                            continue;
                        }
                        if (DataRegionKind.DATA_REGION_SIMPLE == dataRegionDefine.getRegionKind()) {
                            Map<String, List<String>> gdTableFields = this.getGdTableFields(dataRegionDefine.getKey());
                            if (CollectionUtils.isEmpty(gdTableFields)) continue;
                            for (Map.Entry entry : gdTableFields.entrySet()) {
                                List list = (List)entry.getValue();
                                if (CollectionUtils.isEmpty(list)) continue;
                                String tableName = (String)entry.getKey();
                                StringBuilder sql2 = new StringBuilder("select ");
                                for (DimensionInfo dimensionInfo : dimInfos) {
                                    if ("DATATIME".equals(dimensionInfo.getColName())) continue;
                                    sql2.append(dimensionInfo.getColName()).append(" as ").append(dimensionInfo.getColName()).append(",");
                                }
                                list.forEach(o -> sql2.append((String)o).append(" as ").append((String)o).append(","));
                                sql2.setLength(sql2.length() - 1);
                                sql2.append(" from ").append(tableName).append(" where DATATIME=?");
                                List maps2 = this.jdbcTemplate.queryForList(sql2.toString(), new Object[]{period});
                                for (Map map : maps2) {
                                    if (!this.gdHaveData(list, map)) continue;
                                    DimensionValueSet dimensionValueSet2 = new DimensionValueSet();
                                    for (DimensionInfo dimInfo4 : dimInfos) {
                                        if (!map.containsKey(dimInfo4.getColName())) continue;
                                        dimensionValueSet2.setValue(dimInfo4.getDimensionName(), (Object)String.valueOf(map.get(dimInfo4.getColName())));
                                    }
                                    dimensionValueSet2.setValue(this.getPeriodDimName(), (Object)period);
                                    havaDataDims.add(dimensionValueSet2);
                                }
                            }
                            continue;
                        }
                        tableNameByRegion = this.getTableNameByRegion(dataRegionDefine.getKey(), false);
                        sql = new StringBuilder("(select ");
                        for (DimensionInfo dimensionInfo : dimInfos) {
                            if ("DATATIME".equals(dimensionInfo.getColName())) continue;
                            ((StringBuilder)sql).append("min(").append(dimensionInfo.getColName()).append(") as ").append(dimensionInfo.getColName()).append(",");
                        }
                        ((StringBuilder)sql).setLength(((StringBuilder)sql).length() - 1);
                        ((StringBuilder)sql).append(" from ").append((String)tableNameByRegion).append(" where DATATIME=?");
                        ((StringBuilder)sql).append(" group by ");
                        for (DimensionInfo dimensionInfo : dimInfos) {
                            ((StringBuilder)sql).append(dimensionInfo.getColName()).append(",");
                        }
                        ((StringBuilder)sql).setLength(((StringBuilder)sql).length() - 1);
                        ((StringBuilder)sql).append(")");
                        List list = this.jdbcTemplate.queryForList(((StringBuilder)sql).toString(), new Object[]{period});
                        for (Map map : list) {
                            dimensionValueSet = new DimensionValueSet();
                            for (Map.Entry entry : map.entrySet()) {
                                dimensionValueSet.setValue(((DimensionInfo)dimInfoByColName.get(((String)entry.getKey()).toUpperCase(Locale.ROOT))).getDimensionName(), (Object)String.valueOf(entry.getValue()));
                            }
                            dimensionValueSet.setValue(this.getPeriodDimName(), (Object)period);
                            havaDataDims.add(dimensionValueSet);
                        }
                    }
                    String insertSql = this.buildInsertSql(statusTableName, statusTableDimCols);
                    ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
                    for (DimensionValueSet dimensionValueSet : allDimensionValueSets) {
                        ArrayList<Object> arrayList = new ArrayList<Object>();
                        for (String statusTableDimCol : statusTableDimCols) {
                            if ("PERIOD".equals(statusTableDimCol)) {
                                arrayList.add(period);
                                continue;
                            }
                            String dimensionName = ((DimensionInfo)dimInfoByColName.get(statusTableDimCol)).getDimensionName();
                            arrayList.add(dimensionValueSet.getValue(dimensionName));
                        }
                        arrayList.add(uuid);
                        arrayList.add(formScheme.getKey());
                        arrayList.add(formDefine.getKey());
                        if (havaDataDims.contains(dimensionValueSet)) {
                            arrayList.add(1);
                        } else {
                            arrayList.add(0);
                        }
                        batchValues.add(arrayList.toArray(new Object[0]));
                    }
                    try {
                        DataEngineUtil.batchUpdate((Connection)connection, (String)insertSql, batchValues);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    protected boolean gdHaveData(List<String> fieldNames, Map<String, Object> dataMap) {
        for (String fieldName : fieldNames) {
            Object fieldData = dataMap.get(fieldName);
            if (fieldData == null || !StringUtils.hasText(fieldData.toString())) continue;
            return true;
        }
        return false;
    }

    protected List<DataFieldDeployInfo> getLinkFieldInfo(String dataRegionKey) {
        List deployInfo = null;
        List linkDefines = this.runTimeViewController.getAllLinksInRegion(dataRegionKey).stream().filter(o -> o.getType() == DataLinkType.DATA_LINK_TYPE_FIELD).collect(Collectors.toList());
        ArrayList<DataField> dataFields = new ArrayList<DataField>();
        for (DataLinkDefine linkDefine : linkDefines) {
            DataField dataField = this.dataSchemeService.getDataField(linkDefine.getLinkExpression());
            if (dataField == null) continue;
            dataFields.add(dataField);
        }
        if (dataFields.size() > 0) {
            deployInfo = this.dataSchemeService.getDeployInfoByDataFieldKeys((String[])dataFields.stream().map(Basic::getKey).toArray(String[]::new));
        }
        return deployInfo;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public void rollbackDataStatus(RollbackStatusPar par) throws Exception {
        List allRegionsInForm;
        Optional<DataRegionDefine> tzRegion;
        FormDefine formDefine;
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(par.getFormSchemeKey());
        if (this.openStatus(formScheme.getTaskKey()) && FormType.FORM_TYPE_ACCOUNT == (formDefine = this.runTimeViewController.queryFormById(par.getFormKey())).getFormType() && (tzRegion = (allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(par.getFormKey())).stream().filter(o -> DataRegionKind.DATA_REGION_COLUMN_LIST == o.getRegionKind() || DataRegionKind.DATA_REGION_ROW_LIST == o.getRegionKind() || DataRegionKind.DATA_REGION_COLUMN_AND_ROW_LIST == o.getRegionKind()).findFirst()).isPresent()) {
            String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
            DimensionCombination rollbackDim = par.getRollbackDim();
            Object period = rollbackDim.getValue(this.getPeriodDimName());
            List<DimensionInfo> dimInfos = this.getDimInfos(formScheme, new HashMap<String, List<DimensionInfo>>());
            String tzTableName = this.getTableNameByRegion(tzRegion.get().getKey(), true);
            StringBuilder sql = new StringBuilder("select count(1) from ").append(tzTableName).append(" where ");
            ArrayList<Object> args = new ArrayList<Object>();
            boolean addAnd = false;
            for (DimensionInfo dimInfo : dimInfos) {
                if (addAnd) {
                    sql.append(" and ");
                }
                args.add(rollbackDim.getValue(dimInfo.getDimensionName()));
                if ("DATATIME".equals(dimInfo.getColName())) {
                    sql.append("validdatatime<=? ");
                } else {
                    sql.append(dimInfo.getColName()).append("=? ");
                }
                addAnd = true;
            }
            Integer count = (Integer)this.jdbcTemplate.queryForObject(sql.toString(), Integer.class, args.toArray());
            if (count != null && count > 0) {
                StringBuilder updateSql = new StringBuilder("update ").append(statusTableName);
                updateSql.append(" set ").append("DAST_ISENTRY").append("=?");
                updateSql.append(" where ").append("DAST_FORMKEY").append("=?");
                ArrayList<Object> updateArgs = new ArrayList<Object>();
                updateArgs.add(1);
                updateArgs.add(formDefine.getKey());
                for (DimensionInfo dimInfo : dimInfos) {
                    updateSql.append(" and ");
                    updateArgs.add(rollbackDim.getValue(dimInfo.getDimensionName()));
                    if ("DATATIME".equals(dimInfo.getColName())) {
                        updateSql.append("PERIOD").append(">=? ");
                        continue;
                    }
                    updateSql.append(dimInfo.getColName()).append("=? ");
                }
                this.jdbcTemplate.update(updateSql.toString(), updateArgs.toArray());
            } else {
                Optional<DataRegionDefine> gdRegion = allRegionsInForm.stream().filter(o -> o.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE).findFirst();
                if (gdRegion.isPresent()) {
                    List<DataFieldDeployInfo> deployInfo = this.getLinkFieldInfo(gdRegion.get().getKey());
                    if (!CollectionUtils.isEmpty(deployInfo)) {
                        List<String> fieldNames = deployInfo.stream().map(DataFieldDeployInfo::getFieldName).collect(Collectors.toList());
                        String maxPSql = "select max(period) from " + statusTableName;
                        String maxPeriod = (String)this.jdbcTemplate.queryForObject(maxPSql, String.class);
                        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
                        if (periodProvider.comparePeriod(maxPeriod, String.valueOf(period)) >= 0) {
                            String gdTableName = this.getTableNameByRegion(gdRegion.get().getKey(), false);
                            StringBuilder sql2 = new StringBuilder("select datatime as DATATIME,");
                            fieldNames.forEach(o -> sql2.append((String)o).append(" as ").append((String)o).append(","));
                            sql2.setLength(sql2.length() - 1);
                            sql2.append(" from ").append(gdTableName).append(" where ");
                            boolean and = false;
                            ArrayList<Object> args2 = new ArrayList<Object>();
                            for (DimensionInfo dimInfo : dimInfos) {
                                if ("DATATIME".equals(dimInfo.getColName())) continue;
                                if (and) {
                                    sql2.append(" and ");
                                }
                                sql2.append(dimInfo.getColName()).append("=? ");
                                args2.add(rollbackDim.getValue(dimInfo.getDimensionName()));
                                and = true;
                            }
                            List maps = this.jdbcTemplate.queryForList(sql2.toString(), args2.toArray());
                            List<String> havaDataPeriods = this.getGdHaveDataPeriods(maps, fieldNames);
                            if (CollectionUtils.isEmpty(havaDataPeriods)) {
                                this.rollBackUpNoStatus(statusTableName, rollbackDim, dimInfos, null, formDefine.getKey());
                            } else {
                                List periodItems = periodProvider.getPeriodItems();
                                List collect = periodItems.stream().map(IPeriodRow::getCode).collect(Collectors.toList());
                                List<String> noDataPeriods = collect.subList(collect.indexOf(String.valueOf(period)), collect.indexOf(maxPeriod) + 1).stream().filter(o -> !havaDataPeriods.contains(o)).collect(Collectors.toList());
                                this.rollBackUpNoStatus(statusTableName, rollbackDim, dimInfos, noDataPeriods, formDefine.getKey());
                            }
                        }
                    } else {
                        this.rollBackUpNoStatus(statusTableName, rollbackDim, dimInfos, null, formDefine.getKey());
                    }
                } else {
                    this.rollBackUpNoStatus(statusTableName, rollbackDim, dimInfos, null, formDefine.getKey());
                }
            }
        }
    }

    private List<String> getGdHaveDataPeriods(List<Map<String, Object>> dataMaps, List<String> fieldNames) {
        ArrayList<String> result = new ArrayList<String>();
        block0: for (Map<String, Object> dataMap : dataMaps) {
            for (String fieldName : fieldNames) {
                Object fieldData = dataMap.get(fieldName);
                if (fieldData == null || !StringUtils.hasText(fieldData.toString())) continue;
                result.add(dataMap.get("DATATIME").toString());
                continue block0;
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.SUPPORTS)
    public void clearDataStatusByForm(final ClearStatusPar par) throws Exception {
        TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

            public void afterCommit() {
                try {
                    DataStatusServiceImpl.this.doClearByForm(par);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doClearByForm(ClearStatusPar par) throws Exception {
        FormDefine formDefine = this.runTimeViewController.queryFormById(par.getFormKey());
        if (FormType.FORM_TYPE_NEWFMDM == formDefine.getFormType()) {
            return;
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(par.getFormSchemeKey());
        Map<String, ITempTable> tempTableMap = null;
        Map<String, ITempTable> tempTableMap2 = null;
        DimensionValueSet dimensionValueSet = CommonUtil.getMergeDimensionValueSet(par.getDimensionCollection());
        Connection connection = null;
        boolean originalAutoCommit = false;
        try {
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            if (this.openStatus(formScheme.getTaskKey())) {
                String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                List<DimensionInfo> dimInfos = this.getDimInfos(formScheme, new HashMap<String, List<DimensionInfo>>());
                IDatabase dataBase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                int n = DataEngineUtil.getMaxInSize((IDatabase)dataBase);
                tempTableMap = CommonUtil.getTempTable(this.tempTableManager, n, dimensionValueSet, dimInfos);
                HashMap<String, DimensionValue> havaDataDims = new HashMap<String, DimensionValue>();
                StringBuilder updateSql = new StringBuilder("update ").append(statusTableName).append(" set ").append("DAST_ISENTRY").append("=? ");
                updateSql.append(" where ").append("DAST_FORMKEY").append("=? ");
                ArrayList<Object> updateArgs = new ArrayList<Object>();
                updateArgs.add(0);
                updateArgs.add(formDefine.getKey());
                if (FormType.FORM_TYPE_ACCOUNT == formDefine.getFormType()) {
                    Object deployInfo;
                    List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
                    Optional<DataRegionDefine> gdRegion = allRegionsInForm.stream().filter(o -> o.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE).findFirst();
                    if (gdRegion.isPresent() && !CollectionUtils.isEmpty(deployInfo = this.getLinkFieldInfo(gdRegion.get().getKey()))) {
                        List<String> fieldNames = deployInfo.stream().map(DataFieldDeployInfo::getFieldName).collect(Collectors.toList());
                        ArrayList<Object> args = new ArrayList<Object>();
                        StringBuilder gdSelectSql = new StringBuilder("select ");
                        for (DimensionInfo dimensionInfo : dimInfos) {
                            gdSelectSql.append(dimensionInfo.getColName()).append(" as ").append(dimensionInfo.getColName()).append(",");
                        }
                        fieldNames.forEach(o -> gdSelectSql.append((String)o).append(" as ").append((String)o).append(","));
                        gdSelectSql.setLength(gdSelectSql.length() - 1);
                        gdSelectSql.append(" from ").append(this.getTableNameByRegion(gdRegion.get().getKey(), false));
                        gdSelectSql.append(" where ");
                        boolean addAnd = false;
                        for (DimensionInfo dimensionInfo : dimInfos) {
                            if (addAnd) {
                                gdSelectSql.append(" and ");
                            }
                            if ("DATATIME".equals(dimensionInfo.getColName())) {
                                gdSelectSql.append(dimensionInfo.getColName()).append(">=? ");
                                args.add(dimensionValueSet.getValue(dimensionInfo.getDimensionName()));
                            } else if (tempTableMap.containsKey(dimensionInfo.getColName())) {
                                gdSelectSql.append(" exists ").append(CommonUtil.getExistsSelectSql(tempTableMap.get(dimensionInfo.getColName()), dimensionInfo.getColName()));
                            } else {
                                List stringList;
                                Object value = dimensionValueSet.getValue(dimensionInfo.getDimensionName());
                                if (value instanceof String && StringUtils.hasText(value.toString())) {
                                    gdSelectSql.append(dimensionInfo.getColName()).append("=? ");
                                    args.add(value);
                                } else if (value instanceof List && (stringList = (List)value).size() > 0) {
                                    gdSelectSql.append(dimensionInfo.getColName()).append(" in (");
                                    for (String s : stringList) {
                                        gdSelectSql.append("?,");
                                        args.add(s);
                                    }
                                    gdSelectSql.setLength(gdSelectSql.length() - 1);
                                    gdSelectSql.append(")");
                                }
                            }
                            addAnd = true;
                        }
                        List list = args.size() > 0 ? this.jdbcTemplate.queryForList(gdSelectSql.toString(), args.toArray()) : this.jdbcTemplate.queryForList(gdSelectSql.toString());
                        for (Map map : list) {
                            if (!this.gdHaveData(fieldNames, map)) continue;
                            for (DimensionInfo dimInfo : dimInfos) {
                                if (havaDataDims.containsKey(dimInfo.getDimensionName())) {
                                    String value = ((DimensionValue)havaDataDims.get(dimInfo.getDimensionName())).getValue();
                                    ((DimensionValue)havaDataDims.get(dimInfo.getDimensionName())).setValue(value + ";" + map.get(dimInfo.getColName()).toString());
                                    continue;
                                }
                                DimensionValue dimensionValue = new DimensionValue();
                                dimensionValue.setName(dimInfo.getDimensionName());
                                dimensionValue.setValue(map.get(dimInfo.getColName()).toString());
                                havaDataDims.put(dimInfo.getDimensionName(), dimensionValue);
                            }
                        }
                    }
                    for (DimensionInfo dimInfo : dimInfos) {
                        List stringList;
                        updateSql.append(" and ");
                        if ("DATATIME".equals(dimInfo.getColName())) {
                            updateSql.append(dimInfo.getStatusTableCol()).append(">=? ");
                            updateArgs.add(dimensionValueSet.getValue(dimInfo.getDimensionName()));
                            continue;
                        }
                        if (tempTableMap.containsKey(dimInfo.getColName())) {
                            updateSql.append(" exists ").append(CommonUtil.getExistsSelectSql(tempTableMap.get(dimInfo.getColName()), dimInfo.getStatusTableCol()));
                            continue;
                        }
                        Iterator value = dimensionValueSet.getValue(dimInfo.getDimensionName());
                        if (value instanceof String && StringUtils.hasText(value.toString())) {
                            updateSql.append(dimInfo.getStatusTableCol()).append("=? ");
                            updateArgs.add(value);
                            continue;
                        }
                        if (!(value instanceof List) || (stringList = (List)((Object)value)).size() <= 0) continue;
                        updateSql.append(dimInfo.getStatusTableCol()).append(" in (");
                        for (String string : stringList) {
                            updateSql.append("?,");
                            updateArgs.add(string);
                        }
                        updateSql.setLength(updateSql.length() - 1);
                        updateSql.append(")");
                    }
                } else {
                    for (DimensionInfo dimInfo : dimInfos) {
                        List stringList;
                        updateSql.append(" and ");
                        if (tempTableMap.containsKey(dimInfo.getColName())) {
                            updateSql.append(" exists ").append(CommonUtil.getExistsSelectSql(tempTableMap.get(dimInfo.getColName()), dimInfo.getStatusTableCol()));
                            continue;
                        }
                        Object value = dimensionValueSet.getValue(dimInfo.getDimensionName());
                        if (value instanceof String && StringUtils.hasText(value.toString())) {
                            updateSql.append(dimInfo.getStatusTableCol()).append("=? ");
                            updateArgs.add(value);
                            continue;
                        }
                        if (!(value instanceof List) || (stringList = (List)value).size() <= 0) continue;
                        updateSql.append(dimInfo.getStatusTableCol()).append(" in (");
                        for (String s : stringList) {
                            updateSql.append("?,");
                            updateArgs.add(s);
                        }
                        updateSql.setLength(updateSql.length() - 1);
                        updateSql.append(")");
                    }
                }
                this.jdbcTemplate.update(updateSql.toString(), updateArgs.toArray());
                if (!CollectionUtils.isEmpty(havaDataDims)) {
                    StringBuilder updateSql2 = new StringBuilder("update ").append(statusTableName).append(" set ").append("DAST_ISENTRY").append("=? ");
                    updateSql2.append(" where ").append("DAST_FORMKEY").append("=? ");
                    ArrayList<Object> updateArgs2 = new ArrayList<Object>();
                    updateArgs2.add(1);
                    updateArgs2.add(formDefine.getKey());
                    tempTableMap2 = CommonUtil.getTempTable(this.tempTableManager, n, DimensionValueSetUtil.getDimensionValueSet(havaDataDims), dimInfos);
                    for (DimensionInfo dimInfo : dimInfos) {
                        List stringList;
                        updateSql2.append(" and ");
                        String statusTableCol = dimInfo.getStatusTableCol();
                        if (tempTableMap.containsKey(dimInfo.getColName())) {
                            updateSql2.append(" exists ").append(CommonUtil.getExistsSelectSql(tempTableMap.get(dimInfo.getColName()), statusTableCol));
                            continue;
                        }
                        Object value = dimensionValueSet.getValue(dimInfo.getDimensionName());
                        if (value instanceof String && StringUtils.hasText(value.toString())) {
                            updateSql2.append(statusTableCol).append("=? ");
                            updateArgs2.add(value);
                            continue;
                        }
                        if (!(value instanceof List) || (stringList = (List)value).size() <= 0) continue;
                        updateSql2.append(statusTableCol).append(" in (");
                        for (String string : stringList) {
                            updateSql2.append("?,");
                            updateArgs2.add(string);
                        }
                        updateSql2.setLength(updateSql2.length() - 1);
                        updateSql2.append(")");
                    }
                    this.jdbcTemplate.update(updateSql2.toString(), updateArgs2.toArray());
                }
            }
            Iterator period = String.valueOf(dimensionValueSet.getValue("DATATIME"));
            Map<String, List<FormFieldInfoDefine>> relatedForms = this.getRelatedForms(Collections.singletonList(formDefine.getKey()), (String)((Object)period));
            for (Map.Entry entry : relatedForms.entrySet()) {
                List formKeys = (List)entry.getValue();
                if (formScheme.getKey().equals(entry.getKey())) {
                    formKeys.removeIf(o -> par.getFormKey().equals(o.getFormKey()));
                }
                if (CollectionUtils.isEmpty(formKeys) || !this.openStatus(((FormFieldInfoDefine)formKeys.get(0)).getTaskKey())) continue;
                this.refreshStatusByFormula(connection, (String)entry.getKey(), dimensionValueSet, formKeys.stream().map(FormFieldInfoDefine::getFormKey).collect(Collectors.toList()));
            }
            connection.commit();
        }
        catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        }
        finally {
            if (!CollectionUtils.isEmpty(tempTableMap)) {
                for (ITempTable table : tempTableMap.values()) {
                    try {
                        table.close();
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(tempTableMap2)) {
                for (ITempTable table : tempTableMap2.values()) {
                    try {
                        table.close();
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
            if (connection != null) {
                if (originalAutoCommit) {
                    connection.setAutoCommit(true);
                }
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void copyDataStatus(ICopySetting par) throws Exception {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(par.getFormSchemeKey());
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            if (this.openStatus(formScheme.getTaskKey())) {
                String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                List<DimensionInfo> dimInfos = this.getDimInfos(formScheme, new HashMap<String, List<DimensionInfo>>());
                StringBuilder copySql = new StringBuilder("update ").append(statusTableName).append(" set ").append("DAST_ISENTRY").append("=(").append(" select src.").append("DAST_ISENTRY").append(" from ").append("(").append("select ").append("t1.").append("DAST_ISENTRY").append(" as ").append("DAST_ISENTRY").append(" from ").append(statusTableName).append(" t1").append(" where ").append("t1.").append("DAST_FORMKEY").append("=?");
                dimInfos.forEach(o -> copySql.append(" and ").append("t1.").append(o.getStatusTableCol()).append("=?"));
                copySql.append(") src").append(")").append(" where ").append("DAST_FORMKEY").append("=?");
                dimInfos.forEach(o -> copySql.append(" and ").append(o.getStatusTableCol()).append("=?"));
                ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
                List dimensionCombinations = par.getTargetDimension().getDimensionCombinations();
                for (String formKey : par.getFormKeys()) {
                    for (DimensionCombination dimensionCombination : dimensionCombinations) {
                        ArrayList<Object> srcArgs = new ArrayList<Object>();
                        srcArgs.add(formKey);
                        ArrayList<Object> tarArgs = new ArrayList<Object>();
                        tarArgs.add(formKey);
                        DimensionCombination sourceDimension = par.getSourceDimension(dimensionCombination);
                        for (DimensionInfo dimInfo : dimInfos) {
                            srcArgs.add(sourceDimension.getValue(dimInfo.getDimensionName()));
                            tarArgs.add(dimensionCombination.getValue(dimInfo.getDimensionName()));
                        }
                        srcArgs.addAll(tarArgs);
                        batchValues.add(srcArgs.toArray());
                    }
                }
                DataEngineUtil.batchUpdate((Connection)connection, (String)copySql.toString(), batchValues);
            }
            DimensionValueSet mergeDimensionValueSet = CommonUtil.getMergeDimensionValueSet(par.getTargetDimension());
            String period = String.valueOf(mergeDimensionValueSet.getValue("DATATIME"));
            Map<String, List<FormFieldInfoDefine>> relatedForms = this.getRelatedForms(par.getFormKeys(), period);
            for (Map.Entry<String, List<FormFieldInfoDefine>> entry : relatedForms.entrySet()) {
                List<FormFieldInfoDefine> formKeys = entry.getValue();
                if (formScheme.getKey().equals(entry.getKey())) {
                    formKeys.removeIf(o -> par.getFormKeys().contains(o.getFormKey()));
                }
                if (CollectionUtils.isEmpty(formKeys) || !this.openStatus(formKeys.get(0).getTaskKey())) continue;
                this.refreshStatusByFormula(connection, entry.getKey(), mergeDimensionValueSet, formKeys.stream().map(FormFieldInfoDefine::getFormKey).collect(Collectors.toList()));
            }
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchRefreshDataStatus(BatchRefreshStatusPar par) throws Exception {
        if (this.openStatus(par.getTaskKey()) && !CollectionUtils.isEmpty(par.getFormKeys())) {
            DimensionValueSet dimensionValueSet = CommonUtil.getMergeDimensionValueSet(par.getDimensionCollection());
            Connection connection = null;
            try {
                connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
                this.refreshStatusByFormula(connection, par.getFormSchemeKey(), dimensionValueSet, par.getFormKeys());
            }
            finally {
                if (connection != null) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
                }
            }
        }
    }

    private void rollBackUpNoStatus(String statusTableName, DimensionCombination rollbackDim, List<DimensionInfo> dimInfos, List<String> noDataPeriods, String formKey) {
        StringBuilder updateSql = new StringBuilder("update ").append(statusTableName);
        updateSql.append(" set ").append("DAST_ISENTRY").append("=?");
        updateSql.append(" where ").append("DAST_FORMKEY").append("=?");
        ArrayList<Object> updateArgs = new ArrayList<Object>();
        updateArgs.add(0);
        updateArgs.add(formKey);
        for (DimensionInfo dimInfo : dimInfos) {
            updateSql.append(" and ");
            if ("DATATIME".equals(dimInfo.getColName())) {
                if (CollectionUtils.isEmpty(noDataPeriods)) {
                    updateArgs.add(rollbackDim.getValue(dimInfo.getDimensionName()));
                    updateSql.append("PERIOD").append(">=? ");
                    continue;
                }
                updateSql.append("PERIOD").append(" in( ");
                for (String noDataPeriod : noDataPeriods) {
                    updateSql.append("?,");
                    updateArgs.add(noDataPeriod);
                }
                updateSql.setLength(updateSql.length() - 1);
                updateSql.append(") ");
                continue;
            }
            updateArgs.add(rollbackDim.getValue(dimInfo.getDimensionName()));
            updateSql.append(dimInfo.getColName()).append("=? ");
        }
        this.jdbcTemplate.update(updateSql.toString(), updateArgs.toArray());
    }

    public void onDataChange(UpdateDataSet updateDatas) {
    }

    public void beforeDelete(List<DimensionValueSet> delRowKeys, IFieldsInfo fieldsInfo) {
    }

    public void beforeUpdate(List<IDataRow> updateRows) {
    }

    public void finish() {
    }

    protected List<DimensionInfo> getDimInfos(FormSchemeDefine formSchemeDefine, Map<String, List<DimensionInfo>> cacheFormSchemeDims) {
        if (cacheFormSchemeDims.containsKey(formSchemeDefine.getKey())) {
            return cacheFormSchemeDims.get(formSchemeDefine.getKey());
        }
        ArrayList<DimensionInfo> result = new ArrayList<DimensionInfo>();
        if (this.formSchemeService.enableAdjustPeriod(formSchemeDefine.getKey())) {
            DimensionInfo dimensionInfo = new DimensionInfo();
            dimensionInfo.setDimensionName("ADJUST");
            dimensionInfo.setColName("ADJUST");
            dimensionInfo.setEntityId("ADJUST");
            result.add(dimensionInfo);
        }
        String periodDimName = this.getPeriodDimName();
        DimensionInfo dimensionInfo = new DimensionInfo();
        dimensionInfo.setDimensionName(periodDimName);
        dimensionInfo.setColName("DATATIME");
        dimensionInfo.setEntityId(formSchemeDefine.getDateTime());
        result.add(dimensionInfo);
        String dwDimensionName = this.entityMetaService.getDimensionName(formSchemeDefine.getDw());
        DimensionInfo dwDimensionInfo = new DimensionInfo();
        dwDimensionInfo.setDimensionName(dwDimensionName);
        dwDimensionInfo.setColName("MDCODE");
        dwDimensionInfo.setEntityId(formSchemeDefine.getDw());
        result.add(dwDimensionInfo);
        if (StringUtils.hasText(formSchemeDefine.getDims())) {
            String[] dims;
            for (String dim : dims = formSchemeDefine.getDims().split(";")) {
                String dimensionName = this.entityMetaService.getDimensionName(dim);
                DimensionInfo dimensionInfo1 = new DimensionInfo();
                dimensionInfo1.setDimensionName(dimensionName);
                dimensionInfo1.setColName(dimensionName);
                dimensionInfo1.setEntityId(dim);
                result.add(dimensionInfo1);
            }
        }
        cacheFormSchemeDims.put(formSchemeDefine.getKey(), result);
        return result;
    }

    protected List<DimensionInfo> getDimInfos(String dataSchemeKey, Map<String, List<DimensionInfo>> cacheDataSchemeDims) {
        if (cacheDataSchemeDims.containsKey(dataSchemeKey)) {
            return cacheDataSchemeDims.get(dataSchemeKey);
        }
        ArrayList<DimensionInfo> result = new ArrayList<DimensionInfo>();
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey);
        for (DataDimension dataDimension : dataSchemeDimension) {
            if (DimensionType.PERIOD == dataDimension.getDimensionType()) {
                String periodDimName = this.getPeriodDimName();
                DimensionInfo dimensionInfo = new DimensionInfo();
                dimensionInfo.setDimensionName(periodDimName);
                dimensionInfo.setColName("DATATIME");
                dimensionInfo.setEntityId(dataDimension.getDimKey());
                result.add(dimensionInfo);
                continue;
            }
            if (DimensionType.UNIT == dataDimension.getDimensionType()) {
                String dwDimensionName = this.entityMetaService.getDimensionName(dataDimension.getDimKey());
                DimensionInfo dwDimensionInfo = new DimensionInfo();
                dwDimensionInfo.setDimensionName(dwDimensionName);
                dwDimensionInfo.setColName("MDCODE");
                dwDimensionInfo.setEntityId(dataDimension.getDimKey());
                result.add(dwDimensionInfo);
                continue;
            }
            if (DimensionType.DIMENSION != dataDimension.getDimensionType()) continue;
            if ("ADJUST".equals(dataDimension.getDimKey())) {
                DimensionInfo dimensionInfo = new DimensionInfo();
                dimensionInfo.setDimensionName("ADJUST");
                dimensionInfo.setColName("ADJUST");
                dimensionInfo.setEntityId("ADJUST");
                result.add(dimensionInfo);
                continue;
            }
            String dimensionName = this.entityMetaService.getDimensionName(dataDimension.getDimKey());
            DimensionInfo dimensionInfo1 = new DimensionInfo();
            dimensionInfo1.setDimensionName(dimensionName);
            dimensionInfo1.setColName(dimensionName);
            dimensionInfo1.setEntityId(dataDimension.getDimKey());
            result.add(dimensionInfo1);
        }
        cacheDataSchemeDims.put(dataSchemeKey, result);
        return result;
    }

    private String buildInsertSql(String tableName, List<String> dimCols) {
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(tableName).append("( ");
        for (String dimCol : dimCols) {
            sql.append(dimCol).append(",");
        }
        sql.append("DAST_RECID").append(",").append("DAST_FORMSCHEMEKEY").append(",").append("DAST_FORMKEY").append(",").append("DAST_ISENTRY").append(") ");
        sql.append(" values(");
        for (int i = 0; i < dimCols.size() + 4; ++i) {
            sql.append("?,");
        }
        sql.setLength(sql.length() - 1);
        sql.append(")");
        return sql.toString();
    }

    protected String getPeriodDimName() {
        return this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
    }

    private List<String> groupByCol(String formSchemeKey, DimensionValueSet filterDim, String colName) {
        return this.query(formSchemeKey, filterDim, colName, true);
    }

    private List<String> query(String formSchemeKey, DimensionValueSet filterDim, String colName, boolean groupQuery) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
        TableModelDefine statusTable = this.dataModelService.getTableModelDefineByName(statusTableName);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.getColumns().add(new NvwaQueryColumn(this.dataModelService.getColumnModelDefineByCode(statusTable.getID(), colName)));
        if (groupQuery) {
            queryModel.getGroupByColumns().add(0);
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(statusTableName);
        for (int i = 0; i < filterDim.size(); ++i) {
            String name = filterDim.getName(i);
            ColumnModelDefine column = dimensionChanger.getColumn(name);
            if (column == null) continue;
            Object value = filterDim.getValue(i);
            if (value instanceof String) {
                queryModel.getColumnFilters().put(column, value.toString());
                continue;
            }
            if (!(value instanceof List)) continue;
            List stringList = (List)value;
            queryModel.getColumnFilters().put(column, stringList);
        }
        queryModel.getColumnFilters().put(this.dataModelService.getColumnModelDefineByCode(statusTable.getID(), "DAST_ISENTRY"), 1);
        INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            ArrayList<String> result = new ArrayList<String>();
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(new DataAccessContext(this.dataModelService));
            for (DataRow dataRow : dataRows) {
                result.add(dataRow.getString(0));
            }
            return result;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    protected boolean judgeTzRegion(String regionKey) {
        Optional<DataLinkDefine> field = this.runTimeViewController.getAllLinksInRegion(regionKey).stream().filter(o -> DataLinkType.DATA_LINK_TYPE_FIELD == o.getType()).findFirst();
        if (!field.isPresent()) {
            return false;
        }
        try {
            DataField dataField = this.dataSchemeService.getDataField(field.get().getLinkExpression());
            String dataTableKey = ((DataFieldDeployInfo)this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()}).get(0)).getDataTableKey();
            DataTable dataTable = this.dataSchemeService.getDataTable(dataTableKey);
            return dataTable.getDataTableType() == DataTableType.ACCOUNT;
        }
        catch (Exception e) {
            return false;
        }
    }

    protected List<DimensionValueSet> getAllDimensionsInFS(FormSchemeDefine formSchemeDefine, List<String> dimNames, String period) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (String dimName : dimNames) {
            if ("DATATIME".equals(dimName)) {
                dimensionValueSet.setValue(dimName, (Object)period);
                continue;
            }
            if ("ADJUST".equals(dimName)) {
                List adjustCodes = this.formSchemeService.queryAdjustPeriods(formSchemeDefine.getKey(), period).stream().map(AdjustPeriod::getCode).collect(Collectors.toList());
                dimensionValueSet.setValue(dimName, adjustCodes);
                continue;
            }
            dimensionValueSet.setValue(dimName, (Object)"");
        }
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, formSchemeDefine.getKey());
        return dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
    }

    protected String getTableNameByRegion(String regionKey, boolean tz) {
        Optional<DataLinkDefine> fieldLink = this.runTimeViewController.getAllLinksInRegion(regionKey).stream().filter(o -> DataLinkType.DATA_LINK_TYPE_FIELD == o.getType()).findFirst();
        if (!fieldLink.isPresent()) {
            return null;
        }
        DataField dataField = this.dataSchemeService.getDataField(fieldLink.get().getLinkExpression());
        if (dataField == null) {
            return null;
        }
        String tableName = ((DataFieldDeployInfo)this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()}).get(0)).getTableName();
        if (tz) {
            String tzTableName = dataField.isChangeWithPeriod() ? tableName.substring(0, tableName.length() - "_RPT".length()) : tableName;
            return tzTableName;
        }
        return tableName;
    }

    protected Map<String, List<String>> getGdTableFields(String dataRegionKey) {
        List allLinksInRegion = this.runTimeViewController.getAllLinksInRegion(dataRegionKey).stream().filter(o -> DataLinkType.DATA_LINK_TYPE_FIELD == o.getType()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allLinksInRegion)) {
            return null;
        }
        List dataFields = this.dataSchemeService.getDataFields(allLinksInRegion.stream().map(DataLinkDefine::getLinkExpression).collect(Collectors.toList()));
        List deployInfoByDataFieldKeys = this.dataSchemeService.getDeployInfoByDataFieldKeys((String[])dataFields.stream().filter(Objects::nonNull).map(Basic::getKey).toArray(String[]::new));
        return deployInfoByDataFieldKeys.stream().collect(Collectors.groupingBy(DataFieldDeployInfo::getTableName, Collectors.mapping(DataFieldDeployInfo::getFieldName, Collectors.toList())));
    }

    public boolean openStatus(String taskKey) {
        String checkStatusMessage = this.taskOptionController.getValue(taskKey, "DATAENTRY_STATUS");
        return checkStatusMessage != null && (checkStatusMessage.contains("0") || checkStatusMessage.contains("1") || checkStatusMessage.contains("2"));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onApplicationEvent(GatherCompleteEvent event) {
        GatherCompleteSource source = event.getSource();
        String formSchemeKey = source.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionCollection dimensionCollection = source.getDimensionCollection();
        DimensionCombination dimensionCombination = (DimensionCombination)dimensionCollection.getDimensionCombinations().get(0);
        boolean recursive = source.isRecursive();
        List formKeys = source.getFormKeys();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        String dwDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
        if (recursive) {
            Date entityQueryVersionDate = this.entityUtil.getEntityQueryVersionDate(formScheme.getDateTime(), dimensionValueSet);
            String curDw = dimensionValueSet.getValue(dwDimName).toString();
            List<String> noLeafChildNode = this.getNoLeafChildNode(curDw, formScheme.getDw(), entityQueryVersionDate);
            if (!CollectionUtils.isEmpty(noLeafChildNode)) {
                ArrayList allDw = new ArrayList();
                allDw.add(curDw);
                allDw.addAll(noLeafChildNode);
                dimensionValueSet.clearValue(dwDimName);
                dimensionValueSet.setValue(dwDimName, (Object)allDw);
            }
        }
        String period = String.valueOf(dimensionValueSet.getValue("DATATIME"));
        Map<String, List<FormFieldInfoDefine>> relatedForms = this.getRelatedForms(formKeys, period);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            for (Map.Entry entry : relatedForms.entrySet()) {
                List formInfos = (List)entry.getValue();
                if (CollectionUtils.isEmpty(formInfos) || !this.openStatus(((FormFieldInfoDefine)formInfos.get(0)).getTaskKey())) continue;
                this.refreshStatusByFormula(connection, (String)entry.getKey(), dimensionValueSet, formInfos.stream().map(FormFieldInfoDefine::getFormKey).collect(Collectors.toList()));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    @NotNull
    protected static Map<String, Set<DimensionValueSet>> getPresetFmDims(FormDimsCollection formDimsCollection, Map<String, Set<DimensionValueSet>> dbFmDims) {
        HashMap<String, Set<DimensionValueSet>> presetFmDims = new HashMap<String, Set<DimensionValueSet>>();
        for (String formKey : formDimsCollection.getFormKeys()) {
            if (!dbFmDims.containsKey(formKey)) {
                presetFmDims.put(formKey, formDimsCollection.getDimensionValueSets());
                continue;
            }
            Set<DimensionValueSet> dbDims = dbFmDims.get(formKey);
            for (DimensionValueSet valueSet : formDimsCollection.getDimensionValueSets()) {
                if (dbDims.contains(valueSet)) continue;
                if (!presetFmDims.containsKey(formKey)) {
                    presetFmDims.put(formKey, new HashSet());
                }
                ((Set)presetFmDims.get(formKey)).add(valueSet);
            }
        }
        return presetFmDims;
    }

    protected List<String> getNoLeafChildNode(String mdCode, String entityId, Date queryDate) {
        IEntityQuery entityQuery = this.entityUtil.getEntityQuery(entityId, queryDate, null, null);
        TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
        treeRangeQuery.setParentKey(Collections.singletonList(mdCode));
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        try {
            IEntityTable entityTable = entityQuery.executeRangeBuild((IContext)executorContext, (RangeQuery)treeRangeQuery);
            ArrayList<IEntityRow> noLeafChildRows = new ArrayList<IEntityRow>();
            IEntityRow entityRow = entityTable.findByEntityKey(mdCode);
            this.getNoLeafChildRows(entityTable, Collections.singletonList(entityRow), noLeafChildRows);
            return noLeafChildRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private void getNoLeafChildRows(IEntityTable entityTable, List<IEntityRow> curRows, List<IEntityRow> result) {
        ArrayList<IEntityRow> curChild = new ArrayList<IEntityRow>();
        for (IEntityRow childRow : curRows) {
            List childRows = entityTable.getChildRows(childRow.getEntityKeyData());
            if (CollectionUtils.isEmpty(childRows)) continue;
            for (IEntityRow row : childRows) {
                if (CollectionUtils.isEmpty(entityTable.getChildRows(row.getEntityKeyData()))) continue;
                curChild.add(row);
            }
        }
        if (!CollectionUtils.isEmpty(curChild)) {
            result.addAll(curChild);
            this.getNoLeafChildRows(entityTable, curChild, result);
        }
    }

    protected ExecutorContext getExecutorContext(FormSchemeDefine formSchemeDefine, DimensionValueSet dimensionValueSet) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeDefine.getKey(), null, null);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setVarDimensionValueSet(dimensionValueSet);
        return executorContext;
    }

    public void clearData(ParamClearObject paramClearObject) {
        block11: {
            String formSchemeKey;
            ParamClearType paramClearType;
            block12: {
                DimensionValueSet dimensionValueSet;
                block10: {
                    dimensionValueSet = paramClearObject.getDimensionValueSet();
                    paramClearType = paramClearObject.getParamClearType();
                    formSchemeKey = paramClearObject.getFormScheme();
                    if (ParamClearType.TASK != paramClearType) break block10;
                    List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
                    for (FormDefine formDefine : formDefines) {
                        ClearStatusPar clearStatusPar = new ClearStatusPar();
                        clearStatusPar.setFormKey(formDefine.getKey());
                        clearStatusPar.setFormSchemeKey(formSchemeKey);
                        clearStatusPar.setDimensionCollection(this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, formSchemeKey));
                        try {
                            this.clearDataStatusByForm(clearStatusPar);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    break block11;
                }
                if (ParamClearType.OPTIONAL != paramClearType) break block12;
                DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, formSchemeKey);
                AccessFormParam accessFormParam = new AccessFormParam();
                accessFormParam.setFormKeys(paramClearObject.getFormKeys());
                accessFormParam.setCollectionMasterKey(dimensionCollection);
                accessFormParam.setTaskKey(paramClearObject.getTask());
                accessFormParam.setFormSchemeKey(paramClearObject.getFormScheme());
                accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
                DimensionAccessFormInfo batchAccessForms = this.dataAccessServiceProvider.getDataAccessFormService().getBatchAccessForms(accessFormParam);
                for (DimensionAccessFormInfo.AccessFormInfo accessForm : batchAccessForms.getAccessForms()) {
                    DimensionCollection clearDim = this.dimensionCollectionUtil.getDimensionCollection(accessForm.getDimensions(), formSchemeKey);
                    for (String formKey : accessForm.getFormKeys()) {
                        ClearStatusPar clearStatusPar = new ClearStatusPar();
                        clearStatusPar.setFormKey(formKey);
                        clearStatusPar.setFormSchemeKey(formSchemeKey);
                        clearStatusPar.setDimensionCollection(clearDim);
                        try {
                            this.clearDataStatusByForm(clearStatusPar);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                break block11;
            }
            if (ParamClearType.CONDITIONAL != paramClearType) break block11;
            List formDimensionValues = paramClearObject.getFormDimensionValues();
            for (FormDimensionValue formDimensionValue : formDimensionValues) {
                DimensionValueSet formDim = formDimensionValue.getDimensionValueSet();
                String formKey = formDimensionValue.getFormKey();
                ClearStatusPar clearStatusPar = new ClearStatusPar();
                clearStatusPar.setFormKey(formKey);
                clearStatusPar.setFormSchemeKey(formSchemeKey);
                clearStatusPar.setDimensionCollection(this.dimensionCollectionUtil.getDimensionCollection(formDim, formSchemeKey));
                try {
                    this.clearDataStatusByForm(clearStatusPar);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    public void doClear(DataClearParamObj clearParam) {
        if (!CollectionUtils.isEmpty(clearParam.getTaskKey())) {
            for (String taskKey : clearParam.getTaskKey()) {
                try {
                    List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
                    if (CollectionUtils.isEmpty(formSchemeDefines)) continue;
                    for (FormSchemeDefine formScheme : formSchemeDefines) {
                        String table = "DE_DAST_" + formScheme.getFormSchemeCode();
                        try {
                            this.jdbcTemplate.execute("TRUNCATE TABLE " + table);
                            logger.info("{}\u8868\u6570\u636e\u6e05\u9664\u6210\u529f", (Object)table);
                        }
                        catch (Exception e) {
                            logger.error(table + "\u8868\u6570\u636e\u6e05\u9664\u5931\u8d25:" + e.getMessage(), e);
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("\u4efb\u52a1-" + taskKey + "-\u6e05\u9664\u6570\u636e\u65b9\u6848\u4e0b\u7684\u6570\u636e\u540e\u6e05\u9664\u62a5\u8868\u5f55\u6570\u72b6\u6001\u8bb0\u5f55\u5f02\u5e38-" + e.getMessage());
                }
            }
        }
    }

    protected Map<String, List<FormFieldInfoDefine>> getRelatedForms(List<String> formKeys, String period) {
        List formDefines = this.runTimeViewController.queryFormsById(formKeys);
        List forms = formDefines.stream().filter(o -> o != null && FormType.FORM_TYPE_NEWFMDM != o.getFormType()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(forms)) {
            return Collections.emptyMap();
        }
        ArrayList relatedFormKeys = new ArrayList();
        HashMap<String, List<FormFieldInfoDefine>> result = new HashMap<String, List<FormFieldInfoDefine>>();
        HashMap taskFormSchemeMap = new HashMap();
        for (FormDefine form : forms) {
            List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(form.getKey());
            for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                List fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                if (DataRegionKind.DATA_REGION_SIMPLE == dataRegionDefine.getRegionKind()) {
                    for (String fieldKey : fieldKeysInRegion) {
                        Collection formKeysByField = this.runTimeViewController.getFormInfosByField(fieldKey);
                        if (CollectionUtils.isEmpty(formKeysByField)) {
                            logger.error("\u53c2\u6570\u5f02\u5e38\uff1a\u533a\u57df{}\u4e0a\u94fe\u63a5\u6307\u6807{}\u672a\u627e\u5230\u6620\u5c04\u62a5\u8868", (Object)dataRegionDefine.getKey(), (Object)fieldKey);
                            continue;
                        }
                        relatedFormKeys.addAll(formKeysByField.stream().filter(o -> o != null && this.isFormSchemeUsed(period, taskFormSchemeMap, (FormFieldInfoDefine)o)).collect(Collectors.toList()));
                    }
                    continue;
                }
                if (fieldKeysInRegion == null || fieldKeysInRegion.isEmpty()) continue;
                Collection formKeysByField = this.runTimeViewController.getFormInfosByField((String)fieldKeysInRegion.get(0));
                if (CollectionUtils.isEmpty(formKeysByField)) {
                    logger.error("\u53c2\u6570\u5f02\u5e38\uff1a\u533a\u57df{}\u4e0a\u94fe\u63a5\u6307\u6807{}\u672a\u627e\u5230\u6620\u5c04\u62a5\u8868", (Object)dataRegionDefine.getKey(), fieldKeysInRegion.get(0));
                    continue;
                }
                relatedFormKeys.addAll(formKeysByField.stream().filter(o -> o != null && this.isFormSchemeUsed(period, taskFormSchemeMap, (FormFieldInfoDefine)o)).collect(Collectors.toList()));
            }
        }
        for (FormFieldInfoDefine relatedForm : relatedFormKeys) {
            if (!result.containsKey(relatedForm.getFormSchemeKey())) {
                result.put(relatedForm.getFormSchemeKey(), new ArrayList());
            }
            ((List)result.get(relatedForm.getFormSchemeKey())).add(relatedForm);
        }
        return result;
    }

    private boolean isFormSchemeUsed(String period, Map<String, String> taskFormSchemeMap, FormFieldInfoDefine formFieldInfoDefine) {
        String taskKey = formFieldInfoDefine.getTaskKey();
        String formSchemeKey = formFieldInfoDefine.getFormSchemeKey();
        if (!taskFormSchemeMap.containsKey(taskKey)) {
            String fsKey = "";
            try {
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
                if (schemePeriodLinkDefine != null) {
                    fsKey = schemePeriodLinkDefine.getSchemeKey();
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            taskFormSchemeMap.put(taskKey, fsKey);
        }
        return taskFormSchemeMap.get(taskKey).equals(formSchemeKey);
    }

    public void refreshStatusByFormula(Connection connection, String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> formKeys) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String period = String.valueOf(dimensionValueSet.getValue(this.getPeriodDimName()));
        List<String> yearRestPeriods = this.getYearRestPeriods(period, formScheme);
        if (CollectionUtils.isEmpty(yearRestPeriods)) {
            logger.warn("\u5f53\u524d\u62a5\u8868\u65b9\u6848{}\u4e0d\u5b58\u5728\u65f6\u671f{}", (Object)formScheme.getFormSchemeCode(), (Object)period);
            return;
        }
        List splitDims = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimensionValueSet);
        FormDimsCollection formDimsCollection = new FormDimsCollection();
        formDimsCollection.getFormKeys().addAll(formKeys);
        formDimsCollection.getDimensionValueSets().addAll(splitDims);
        List formDefines = this.runTimeViewController.queryFormsById(new ArrayList<String>(formKeys));
        ArrayList<String> expressions = new ArrayList<String>();
        ArrayList<String> expFormKeys = new ArrayList<String>();
        for (FormDefine formDefine : formDefines) {
            if (formDefine == null) continue;
            expressions.add("exist(" + formDefine.getFormCode() + ")");
            expFormKeys.add(formDefine.getKey());
        }
        IExpressionEvaluator evaluator = this.nrDataAccessProvider.newExpressionEvaluator();
        ExecutorContext executorContext = this.getExecutorContext(formScheme, dimensionValueSet);
        String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
        List<DimensionInfo> dimInfos = this.getDimInfos(formScheme, new HashMap<String, List<DimensionInfo>>());
        try {
            Map existFormResult = evaluator.evalBatch(expressions, executorContext, dimensionValueSet);
            Map<String, Set<DimensionValueSet>> dbFmDims = this.getDbFmDims(formDimsCollection, statusTableName, dimInfos);
            Map<String, Set<DimensionValueSet>> presetFmDims = DataStatusServiceImpl.getPresetFmDims(formDimsCollection, dbFmDims);
            if (!CollectionUtils.isEmpty(presetFmDims)) {
                this.presetStatusRec(connection, formScheme, yearRestPeriods, statusTableName, dimInfos, presetFmDims);
            }
            StringBuilder sql = new StringBuilder("update ").append(statusTableName).append(" set ").append("DAST_ISENTRY").append("=?").append(" where ").append("DAST_FORMKEY").append("=?");
            dimInfos.forEach(o -> sql.append(" and ").append(o.getStatusTableCol()).append("=?"));
            ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
            String dwDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
            for (Map.Entry entry : existFormResult.entrySet()) {
                String dw = (String)entry.getKey();
                Object[] formExist = (Object[])entry.getValue();
                for (int i = 0; i < formExist.length; ++i) {
                    String formKey = (String)expFormKeys.get(i);
                    ArrayList<Object> args = new ArrayList<Object>();
                    boolean isEntry = formExist[i] != null && (Boolean)formExist[i] != false;
                    args.add(isEntry ? 1 : 0);
                    args.add(formKey);
                    for (DimensionInfo dimInfo : dimInfos) {
                        if (dwDimName.equals(dimInfo.getDimensionName())) {
                            args.add(dw);
                            continue;
                        }
                        args.add(dimensionValueSet.getValue(dimInfo.getDimensionName()));
                    }
                    batchValues.add(args.toArray());
                }
            }
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql.toString(), batchValues);
        }
        catch (Exception e) {
            logger.error(String.format("\u62a5\u8868\u65b9\u6848%s-\u62a5\u8868%s-\u7ef4\u5ea6%s-\u5237\u65b0\u5f55\u6570\u72b6\u6001\u5931\u8d25-%s", formSchemeKey, formKeys, dimensionValueSet, e.getMessage()), e);
        }
    }
}

