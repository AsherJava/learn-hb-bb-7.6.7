/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.update.UpdateDataSet
 *  com.jiuqi.np.dataengine.update.UpdateDataTable
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.gather.bean.event.GatherCompleteEvent
 *  com.jiuqi.nr.data.gather.bean.event.GatherCompleteSource
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor
 *  com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormFieldInfoDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.examine.facade.DataClearParamObj
 *  com.jiuqi.nr.io.tz.listener.ChangeInfo
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datastatus.internal.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.np.dataengine.update.UpdateDataTable;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.gather.bean.event.GatherCompleteEvent;
import com.jiuqi.nr.data.gather.bean.event.GatherCompleteSource;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.datastatus.facade.obj.BatchRefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.DimensionInfo;
import com.jiuqi.nr.datastatus.facade.obj.ICopySetting;
import com.jiuqi.nr.datastatus.facade.obj.RefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.RollbackStatusPar;
import com.jiuqi.nr.datastatus.internal.impl.DataStatusServiceImpl;
import com.jiuqi.nr.datastatus.internal.obj.DataDimensionSplit;
import com.jiuqi.nr.datastatus.internal.obj.FormDimsCollection;
import com.jiuqi.nr.datastatus.internal.util.CommonUtil;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFieldInfoDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.io.tz.listener.ChangeInfo;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class DataStatusServiceImpl2
extends DataStatusServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(DataStatusServiceImpl2.class);
    @Autowired
    private NrdbHelper nrdbHelper;

    @Override
    public void onDataChange(IMonitor monitor, UpdateDataSet updateDatas) {
        if (!this.nrdbHelper.isEnableNrdb()) {
            super.onDataChange(monitor, updateDatas);
        } else {
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
            try {
                String formSchemeKey;
                for (Map.Entry entry : updateDatas.getTables().entrySet()) {
                    DataTable dataTable;
                    String dataTableKey;
                    String tableName = (String)entry.getKey();
                    TableModelDefine table = this.dataModelService.getTableModelDefineByName(tableName);
                    if (table == null || !StringUtils.hasText(dataTableKey = this.dataSchemeService.getDataTableByTableModel(table.getID())) || (dataTable = this.dataSchemeService.getDataTable(dataTableKey)) == null) continue;
                    if (DataTableType.ACCOUNT == dataTable.getDataTableType()) {
                        logger.warn("\u6587\u4ef6\u5b58\u50a8\u4e0d\u652f\u6301\u53f0\u8d26\u8868{}", (Object)dataTable.getCode());
                        continue;
                    }
                    List<DimensionInfo> dimInfos = this.getDimInfos(dataTable.getDataSchemeKey(), dataSchemeDimInfoMap);
                    UpdateDataTable updateDataTable = (UpdateDataTable)entry.getValue();
                    Map<String, ColumnModelDefine> colMapByName = this.dataModelService.getColumnModelDefinesByTable(table.getID()).stream().collect(Collectors.toMap(ColumnModelDefine::getName, Function.identity(), (o1, o2) -> o1));
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
                for (Map.Entry<String, Map<String, DataDimensionSplit>> e : merged.entrySet()) {
                    formSchemeKey = e.getKey();
                    FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                    List<String> yearRestPeriods = this.getYearRestPeriods(curPeriod, formScheme);
                    if (CollectionUtils.isEmpty(yearRestPeriods)) continue;
                    String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                    List dimensionInfos = (List)dataSchemeDimInfoMap.get(this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme());
                    Map<String, Set<DimensionValueSet>> dbFmDims = this.getDbFmDims((FormDimsCollection)formDimsCollectionMap.get(formSchemeKey), statusTableName, dimensionInfos);
                    Map<String, Set<DimensionValueSet>> presetFmDims = DataStatusServiceImpl2.getPresetFmDims(e, dbFmDims);
                    if (!CollectionUtils.isEmpty(presetFmDims)) {
                        this.presetStatusRec(formScheme, yearRestPeriods, statusTableName, dimensionInfos, presetFmDims);
                    }
                    this.updateStatus(statusTableName, dimensionInfos, e.getValue());
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void updateStatus(String statusTableName, List<DimensionInfo> dimensionInfos, Map<String, DataDimensionSplit> fmDims) throws Exception {
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(statusTableName);
        if (table == null) {
            logger.warn("{}\u8868\u6a21\u578b\u4e0d\u5b58\u5728", (Object)statusTableName);
            return;
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        HashMap<String, ColumnModelDefine> colMap = new HashMap<String, ColumnModelDefine>();
        int entryIndex = -1;
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
            if ("DAST_ISENTRY".equals(columnModelDefine.getCode())) {
                entryIndex = i;
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            colMap.put(columnModelDefine.getCode(), columnModelDefine);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(dataAccessContext);
        for (Map.Entry<String, DataDimensionSplit> e : fmDims.entrySet()) {
            String formKey = e.getKey();
            DataDimensionSplit dataDimensionSplit = e.getValue();
            Set<DimensionValueSet> ftzDimensionValueSet = dataDimensionSplit.getFtzDimensionValueSet();
            for (DimensionValueSet dimensionValueSet : ftzDimensionValueSet) {
                INvwaDataRow dataRow = dataUpdator.addUpdateRow();
                for (DimensionInfo dimensionInfo : dimensionInfos) {
                    Object value = dimensionValueSet.getValue(dimensionInfo.getDimensionName());
                    if (value == null) continue;
                    dataRow.setKeyValue((ColumnModelDefine)colMap.get(dimensionInfo.getStatusTableCol()), value);
                }
                dataRow.setKeyValue((ColumnModelDefine)colMap.get("DAST_FORMKEY"), (Object)formKey);
                dataRow.setValue(entryIndex, (Object)1);
            }
        }
        dataUpdator.commitChanges(dataAccessContext);
    }

    @Override
    public void onDataChange(ChangeInfo changeInfo) {
        if (this.nrdbHelper.isEnableNrdb()) {
            logger.warn(Thread.currentThread().getStackTrace()[1].getMethodName() + "\u4e0d\u652f\u6301\u6587\u4ef6\u5b58\u50a8");
        } else {
            super.onDataChange(changeInfo);
        }
    }

    @Override
    public void onApplicationEvent(GatherCompleteEvent event) {
        if (!this.nrdbHelper.isEnableNrdb()) {
            super.onApplicationEvent(event);
        } else {
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
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(curDw);
                    arrayList.addAll(noLeafChildNode);
                    dimensionValueSet.clearValue(dwDimName);
                    dimensionValueSet.setValue(dwDimName, arrayList);
                }
            }
            String period = String.valueOf(dimensionValueSet.getValue("DATATIME"));
            Map<String, List<FormFieldInfoDefine>> relatedForms = this.getRelatedForms(formKeys, period);
            try {
                for (Map.Entry entry : relatedForms.entrySet()) {
                    List formInfos = (List)entry.getValue();
                    if (CollectionUtils.isEmpty(formInfos) || !this.openStatus(((FormFieldInfoDefine)formInfos.get(0)).getTaskKey())) continue;
                    this.refreshStatusByFormula((String)entry.getKey(), dimensionValueSet, formInfos.stream().map(FormFieldInfoDefine::getFormKey).collect(Collectors.toList()));
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void doClear(DataClearParamObj clearParam) {
        if (!this.nrdbHelper.isEnableNrdb()) {
            super.doClear(clearParam);
        } else if (!CollectionUtils.isEmpty(clearParam.getTaskKey())) {
            for (String taskKey : clearParam.getTaskKey()) {
                try {
                    List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
                    if (CollectionUtils.isEmpty(formSchemeDefines)) continue;
                    for (FormSchemeDefine formScheme : formSchemeDefines) {
                        String table = "DE_DAST_" + formScheme.getFormSchemeCode();
                        try {
                            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(table);
                            ColumnModelDefine recidCol = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), "DAST_RECID");
                            NvwaQueryModel queryModel = new NvwaQueryModel();
                            queryModel.getColumns().add(new NvwaQueryColumn(recidCol));
                            INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
                            DataAccessContext context = new DataAccessContext(this.dataModelService);
                            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
                            dataUpdator.deleteAll();
                            dataUpdator.commitChanges(context);
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

    @Override
    public void refreshDataStatus(RefreshStatusPar par) throws Exception {
        if (!this.nrdbHelper.isEnableNrdb()) {
            super.refreshDataStatus(par);
        } else {
            String taskKey = par.getTaskKey();
            List<String> periods = par.getPeriods();
            HashMap<String, List<DimensionInfo>> fsDimInfos = new HashMap<String, List<DimensionInfo>>();
            String uuid = UUID.randomUUID().toString();
            Map periodLinkMap = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey).stream().collect(Collectors.toMap(SchemePeriodLinkDefine::getPeriodKey, Function.identity(), (o1, o2) -> o1));
            for (String period : periods) {
                SchemePeriodLinkDefine schemePeriodLinkDefine = (SchemePeriodLinkDefine)periodLinkMap.get(period);
                if (schemePeriodLinkDefine == null) continue;
                String schemeKey = schemePeriodLinkDefine.getSchemeKey();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemeKey);
                List<DimensionInfo> dimInfos = this.getDimInfos(formScheme, fsDimInfos);
                Map dimInfoByColName = dimInfos.stream().collect(Collectors.toMap(DimensionInfo::getColName, Function.identity(), (o1, o2) -> o1));
                List<DimensionValueSet> allDimensionValueSets = this.getAllDimensionsInFS(formScheme, dimInfos.stream().map(DimensionInfo::getDimensionName).collect(Collectors.toList()), period);
                String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                List statusTableDimCols = dimInfos.stream().map(DimensionInfo::getStatusTableCol).collect(Collectors.toList());
                TableModelDefine table = this.dataModelService.getTableModelDefineByName(statusTableName);
                List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
                NvwaQueryModel queryModel = new NvwaQueryModel();
                HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
                HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
                for (int i = 0; i < columns.size(); ++i) {
                    ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
                    queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
                    colIndexMap.put(columnModelDefine.getCode(), i);
                    colModelMap.put(columnModelDefine.getCode(), columnModelDefine);
                }
                queryModel.getColumnFilters().put(colModelMap.get("PERIOD"), period);
                INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
                dataUpdator.deleteAll();
                dataUpdator.commitChanges(context);
                List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(schemeKey);
                for (FormDefine formDefine : formDefines) {
                    if (FormType.FORM_TYPE_NEWFMDM == formDefine.getFormType()) continue;
                    HashSet<DimensionValueSet> havaDataDims = new HashSet<DimensionValueSet>();
                    List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
                    for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                        if (this.judgeTzRegion(dataRegionDefine.getKey())) continue;
                        if (DataRegionKind.DATA_REGION_SIMPLE == dataRegionDefine.getRegionKind()) {
                            Map<String, List<String>> gdTableFields = this.getGdTableFields(dataRegionDefine.getKey());
                            if (CollectionUtils.isEmpty(gdTableFields)) continue;
                            for (Map.Entry entry : gdTableFields.entrySet()) {
                                DataAccessContext gdContext;
                                List colNames = (List)entry.getValue();
                                if (CollectionUtils.isEmpty(colNames)) continue;
                                String tableName = (String)entry.getKey();
                                TableModelDefine gdTable = this.dataModelService.getTableModelDefineByName(tableName);
                                List gdColumns = this.dataModelService.getColumnModelDefinesByTable(gdTable.getID());
                                NvwaQueryModel gdQueryModel = new NvwaQueryModel();
                                HashMap<String, Integer> colNameIndexMap = new HashMap<String, Integer>();
                                for (int i = 0; i < gdColumns.size(); ++i) {
                                    ColumnModelDefine columnModelDefine = (ColumnModelDefine)gdColumns.get(i);
                                    colNameIndexMap.put(columnModelDefine.getName(), i);
                                    gdQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
                                }
                                gdQueryModel.getColumnFilters().put(this.dataModelService.getColumnModelDefineByCode(gdTable.getID(), "DATATIME"), period);
                                INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(gdQueryModel);
                                MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(gdContext = new DataAccessContext(this.dataModelService));
                                if (dataRows == null || dataRows.size() <= 0) continue;
                                for (DataRow dataRow : dataRows) {
                                    if (!this.gdHaveData(dataRow, colNames, colNameIndexMap)) continue;
                                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                                    for (DimensionInfo dimInfo : dimInfos) {
                                        if (!colNameIndexMap.containsKey(dimInfo.getColName())) continue;
                                        dimensionValueSet.setValue(dimInfo.getDimensionName(), (Object)dataRow.getString(((Integer)colNameIndexMap.get(dimInfo.getColName())).intValue()));
                                    }
                                    dimensionValueSet.setValue(this.getPeriodDimName(), (Object)period);
                                    havaDataDims.add(dimensionValueSet);
                                }
                            }
                            continue;
                        }
                        String tableNameByRegion = this.getTableNameByRegion(dataRegionDefine.getKey(), false);
                        TableModelDefine fdTable = this.dataModelService.getTableModelDefineByName(tableNameByRegion);
                        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
                        int index = -1;
                        HashMap<String, Integer> dimColIndexMap = new HashMap<String, Integer>();
                        for (DimensionInfo dimInfo : dimInfos) {
                            if ("DATATIME".equals(dimInfo.getColName())) continue;
                            ColumnModelDefine dimCol = this.dataModelService.getColumnModelDefineByCode(fdTable.getID(), dimInfo.getColName());
                            NvwaQueryColumn e = new NvwaQueryColumn(dimCol);
                            e.setAggrType(AggrType.MIN);
                            nvwaQueryModel.getColumns().add(e);
                            nvwaQueryModel.getGroupByColumns().add(++index);
                            dimColIndexMap.put(dimInfo.getColName(), index);
                        }
                        ColumnModelDefine dataTime = this.dataModelService.getColumnModelDefineByCode(fdTable.getID(), "DATATIME");
                        nvwaQueryModel.getColumnFilters().put(dataTime, period);
                        INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
                        MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(new DataAccessContext(this.dataModelService));
                        for (DataRow dataRow : dataRows) {
                            DimensionValueSet dimensionValueSet = new DimensionValueSet();
                            for (DimensionInfo dimInfo : dimInfos) {
                                if (!dimColIndexMap.containsKey(dimInfo.getColName())) continue;
                                dimensionValueSet.setValue(dimInfo.getDimensionName(), (Object)dataRow.getString(((Integer)dimColIndexMap.get(dimInfo.getColName())).intValue()));
                            }
                            dimensionValueSet.setValue(this.getPeriodDimName(), (Object)period);
                            havaDataDims.add(dimensionValueSet);
                        }
                    }
                    try {
                        INvwaUpdatableDataAccess insertUpdate = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
                        DataAccessContext insertContext = new DataAccessContext(this.dataModelService);
                        INvwaDataUpdator insDataUpdator = insertUpdate.openForUpdate(insertContext);
                        for (DimensionValueSet dimensionValueSet : allDimensionValueSets) {
                            INvwaDataRow dataRow = insDataUpdator.addInsertRow();
                            for (String statusTableDimCol : statusTableDimCols) {
                                if ("PERIOD".equals(statusTableDimCol)) {
                                    dataRow.setKeyValue((ColumnModelDefine)colModelMap.get(statusTableDimCol), (Object)period);
                                    continue;
                                }
                                String dimensionName = ((DimensionInfo)dimInfoByColName.get(statusTableDimCol)).getDimensionName();
                                dataRow.setKeyValue((ColumnModelDefine)colModelMap.get(statusTableDimCol), dimensionValueSet.getValue(dimensionName));
                            }
                            dataRow.setKeyValue((ColumnModelDefine)colModelMap.get("DAST_FORMKEY"), (Object)formDefine.getKey());
                            dataRow.setValue(((Integer)colIndexMap.get("DAST_RECID")).intValue(), (Object)uuid);
                            dataRow.setValue(((Integer)colIndexMap.get("DAST_FORMSCHEMEKEY")).intValue(), (Object)formScheme.getKey());
                            if (havaDataDims.contains(dimensionValueSet)) {
                                dataRow.setValue(((Integer)colIndexMap.get("DAST_ISENTRY")).intValue(), (Object)1);
                                continue;
                            }
                            dataRow.setValue(((Integer)colIndexMap.get("DAST_ISENTRY")).intValue(), (Object)0);
                        }
                        insDataUpdator.commitChanges(insertContext);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    private boolean gdHaveData(DataRow dataRow, List<String> fieldNames, Map<String, Integer> colNameIndexMap) {
        for (String fieldName : fieldNames) {
            Object value = dataRow.getValue(colNameIndexMap.get(fieldName).intValue());
            if (value == null || !StringUtils.hasText(value.toString())) continue;
            return true;
        }
        return false;
    }

    @Override
    public void rollbackDataStatus(RollbackStatusPar par) throws Exception {
        if (this.nrdbHelper.isEnableNrdb()) {
            logger.warn(Thread.currentThread().getStackTrace()[1].getMethodName() + "\u4e0d\u652f\u6301\u6587\u4ef6\u5b58\u50a8");
        } else {
            super.rollbackDataStatus(par);
        }
    }

    @Override
    public void doClearByForm(ClearStatusPar par) throws Exception {
        if (!this.nrdbHelper.isEnableNrdb()) {
            super.doClearByForm(par);
        } else {
            FormDefine formDefine = this.runTimeViewController.queryFormById(par.getFormKey());
            if (FormType.FORM_TYPE_NEWFMDM == formDefine.getFormType() || FormType.FORM_TYPE_ACCOUNT == formDefine.getFormType()) {
                return;
            }
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(par.getFormSchemeKey());
            DimensionValueSet dimensionValueSet = CommonUtil.getMergeDimensionValueSet(par.getDimensionCollection());
            try {
                if (this.openStatus(formScheme.getTaskKey())) {
                    int i;
                    String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                    DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(statusTableName);
                    TableModelDefine table = this.dataModelService.getTableModelDefineByName(statusTableName);
                    List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
                    NvwaQueryModel queryModel = new NvwaQueryModel();
                    int entryIndex = -1;
                    for (i = 0; i < columns.size(); ++i) {
                        ColumnModelDefine o2 = (ColumnModelDefine)columns.get(i);
                        if ("DAST_ISENTRY".equals(o2.getCode())) {
                            entryIndex = i;
                        }
                        queryModel.getColumns().add(new NvwaQueryColumn(o2));
                    }
                    for (i = 0; i < dimensionValueSet.size(); ++i) {
                        Object value = dimensionValueSet.getValue(i);
                        String name = dimensionValueSet.getName(i);
                        ColumnModelDefine column = dimensionChanger.getColumn(name);
                        if (column == null) continue;
                        queryModel.getColumnFilters().put(column, value);
                    }
                    queryModel.getColumnFilters().put(this.dataModelService.getColumnModelDefineByCode(table.getID(), "DAST_FORMKEY"), formDefine.getKey());
                    INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
                    DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
                    INvwaUpdatableDataSet nvwaDataRows = updatableDataAccess.executeQueryForUpdate(dataAccessContext);
                    for (INvwaDataRow nvwaDataRow : nvwaDataRows) {
                        nvwaDataRow.setValue(entryIndex, (Object)0);
                    }
                    nvwaDataRows.commitChanges(dataAccessContext);
                }
                String period = String.valueOf(dimensionValueSet.getValue("DATATIME"));
                Map<String, List<FormFieldInfoDefine>> relatedForms = this.getRelatedForms(Collections.singletonList(formDefine.getKey()), period);
                for (Map.Entry<String, List<FormFieldInfoDefine>> entry : relatedForms.entrySet()) {
                    List<FormFieldInfoDefine> formKeys = entry.getValue();
                    if (formScheme.getKey().equals(entry.getKey())) {
                        formKeys.removeIf(o -> par.getFormKey().equals(o.getFormKey()));
                    }
                    if (CollectionUtils.isEmpty(formKeys) || !this.openStatus(formKeys.get(0).getTaskKey())) continue;
                    this.refreshStatusByFormula(entry.getKey(), dimensionValueSet, formKeys.stream().map(FormFieldInfoDefine::getFormKey).collect(Collectors.toList()));
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void refreshStatusByFormula(String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> formKeys) {
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
            Map<String, Set<DimensionValueSet>> presetFmDims = DataStatusServiceImpl2.getPresetFmDims(formDimsCollection, dbFmDims);
            if (!CollectionUtils.isEmpty(presetFmDims)) {
                this.presetStatusRec(formScheme, yearRestPeriods, statusTableName, dimInfos, presetFmDims);
            }
            TableModelDefine table = this.dataModelService.getTableModelDefineByName(statusTableName);
            List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            NvwaQueryModel queryModel = new NvwaQueryModel();
            HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
            HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
            for (int i = 0; i < columns.size(); ++i) {
                ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
                queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
                colIndexMap.put(columnModelDefine.getCode(), i);
                colModelMap.put(columnModelDefine.getCode(), columnModelDefine);
            }
            INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
            DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(dataAccessContext);
            String dwDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
            for (Map.Entry entry : existFormResult.entrySet()) {
                String dw = (String)entry.getKey();
                Object[] formExist = (Object[])entry.getValue();
                for (int i = 0; i < formExist.length; ++i) {
                    INvwaDataRow dataRow = dataUpdator.addUpdateRow();
                    String formKey = (String)expFormKeys.get(i);
                    dataRow.setKeyValue((ColumnModelDefine)colModelMap.get("DAST_FORMKEY"), (Object)formKey);
                    boolean isEntry = formExist[i] != null && (Boolean)formExist[i] != false;
                    dataRow.setValue(((Integer)colIndexMap.get("DAST_ISENTRY")).intValue(), (Object)(isEntry ? 1 : 0));
                    for (DimensionInfo dimInfo : dimInfos) {
                        String dimColCode = dimInfo.getStatusTableCol();
                        ColumnModelDefine dimCol = (ColumnModelDefine)colModelMap.get(dimColCode);
                        if (dwDimName.equals(dimInfo.getDimensionName())) {
                            dataRow.setKeyValue(dimCol, (Object)dw);
                            continue;
                        }
                        dataRow.setKeyValue(dimCol, dimensionValueSet.getValue(dimInfo.getDimensionName()));
                    }
                }
            }
            dataUpdator.commitChanges(dataAccessContext);
        }
        catch (Exception e) {
            logger.error(String.format("\u62a5\u8868\u65b9\u6848%s-\u62a5\u8868%s-\u7ef4\u5ea6%s-\u5237\u65b0\u5f55\u6570\u72b6\u6001\u5931\u8d25-%s", formSchemeKey, formKeys, dimensionValueSet, e.getMessage()), e);
        }
    }

    private void presetStatusRec(FormSchemeDefine formScheme, List<String> yearRestPeriods, String statusTableName, List<DimensionInfo> dimensionInfos, Map<String, Set<DimensionValueSet>> presetFmDims) throws SQLException {
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(statusTableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            colIndexMap.put(columnModelDefine.getCode(), i);
            colModelMap.put(columnModelDefine.getCode(), columnModelDefine);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        Map<String, String> dimNameCodeMap = dimensionInfos.stream().collect(Collectors.toMap(DimensionInfo::getDimensionName, DimensionInfo::getStatusTableCol));
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(dataAccessContext);
            for (Map.Entry<String, Set<DimensionValueSet>> entry : presetFmDims.entrySet()) {
                String formKey = entry.getKey();
                for (DimensionValueSet dimensionValueSet : entry.getValue()) {
                    for (String period : yearRestPeriods) {
                        INvwaDataRow dataRow = dataUpdator.addInsertRow();
                        for (int i = 0; i < dimensionValueSet.size(); ++i) {
                            String name = dimensionValueSet.getName(i);
                            Object value = dimensionValueSet.getValue(i);
                            String dimCode = dimNameCodeMap.get(name);
                            if (dimCode.equals("PERIOD")) {
                                dataRow.setKeyValue((ColumnModelDefine)colModelMap.get(dimCode), (Object)period);
                                continue;
                            }
                            dataRow.setKeyValue((ColumnModelDefine)colModelMap.get(dimCode), value);
                        }
                        dataRow.setKeyValue((ColumnModelDefine)colModelMap.get("DAST_FORMKEY"), (Object)formKey);
                        dataRow.setValue(((Integer)colIndexMap.get("DAST_RECID")).intValue(), (Object)UUID.randomUUID().toString());
                        dataRow.setValue(((Integer)colIndexMap.get("DAST_FORMSCHEMEKEY")).intValue(), (Object)formScheme.getKey());
                        dataRow.setValue(((Integer)colIndexMap.get("DAST_ISENTRY")).intValue(), (Object)0);
                    }
                }
            }
            dataUpdator.commitChanges(dataAccessContext);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void copyDataStatus(ICopySetting par) throws Exception {
        if (!this.nrdbHelper.isEnableNrdb()) {
            super.copyDataStatus(par);
        } else {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(par.getFormSchemeKey());
            if (this.openStatus(formScheme.getTaskKey())) {
                String statusTableName = "DE_DAST_" + formScheme.getFormSchemeCode();
                List<DimensionInfo> dimInfos = this.getDimInfos(formScheme, new HashMap<String, List<DimensionInfo>>());
                TableModelDefine table = this.dataModelService.getTableModelDefineByName(statusTableName);
                List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
                HashMap<String, ColumnModelDefine> colMap = new HashMap<String, ColumnModelDefine>();
                HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
                for (int i = 0; i < columns.size(); ++i) {
                    ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
                    colMap.put(columnModelDefine.getCode(), columnModelDefine);
                    colIndexMap.put(columnModelDefine.getCode(), i);
                }
                List dimensionCombinations = par.getTargetDimension().getDimensionCombinations();
                HashMap srcDims = new HashMap();
                for (DimensionInfo dimInfo : dimInfos) {
                    srcDims.put(dimInfo.getDimensionName(), new HashSet());
                }
                for (Object dimensionCombination : dimensionCombinations) {
                    DimensionCombination sourceDimension = par.getSourceDimension((DimensionCombination)dimensionCombination);
                    for (FixedDimensionValue fixedDimensionValue : sourceDimension) {
                        ((Set)srcDims.get(fixedDimensionValue.getName())).add(fixedDimensionValue.getValue());
                    }
                }
                NvwaQueryModel queryModel = new NvwaQueryModel();
                columns.forEach(o -> queryModel.getColumns().add(new NvwaQueryColumn(o)));
                for (DimensionInfo dimInfo : dimInfos) {
                    if (!colMap.containsKey(dimInfo.getStatusTableCol())) continue;
                    queryModel.getColumnFilters().put(colMap.get(dimInfo.getStatusTableCol()), new ArrayList((Collection)srcDims.get(dimInfo.getDimensionName())));
                }
                queryModel.getColumnFilters().put(colMap.get("DAST_FORMKEY"), par.getFormKeys());
                INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
                MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(new DataAccessContext(this.dataModelService));
                if (dataRows != null && !dataRows.isEmpty()) {
                    HashMap<DimensionValueSet, DataRow> rowsByBizKeys = new HashMap<DimensionValueSet, DataRow>();
                    Integer formIndex = (Integer)colIndexMap.get("DAST_FORMKEY");
                    for (DataRow dataRow : dataRows) {
                        DimensionValueSet dimensionValueSet = new DimensionValueSet();
                        for (DimensionInfo dimInfo : dimInfos) {
                            Integer colIndex = (Integer)colIndexMap.get(dimInfo.getStatusTableCol());
                            dimensionValueSet.setValue(dimInfo.getDimensionName(), dataRow.getValue(colIndex.intValue()));
                        }
                        dimensionValueSet.setValue("DAST_FORMKEY", dataRow.getValue(formIndex.intValue()));
                        rowsByBizKeys.put(dimensionValueSet, dataRow);
                    }
                    NvwaQueryModel updateModel = new NvwaQueryModel();
                    columns.forEach(o -> updateModel.getColumns().add(new NvwaQueryColumn(o)));
                    INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(updateModel);
                    DataAccessContext updateCon = new DataAccessContext(this.dataModelService);
                    INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(updateCon);
                    Integer entryIndex = (Integer)colIndexMap.get("DAST_ISENTRY");
                    for (String formKey : par.getFormKeys()) {
                        for (DimensionCombination dimensionCombination : dimensionCombinations) {
                            DimensionCombination sourceDimension = par.getSourceDimension(dimensionCombination);
                            DimensionValueSet dimensionValueSet = new DimensionValueSet(sourceDimension.toDimensionValueSet());
                            dimensionValueSet.setValue("DAST_FORMKEY", (Object)formKey);
                            DataRow dataRow = (DataRow)rowsByBizKeys.get(dimensionValueSet);
                            if (dataRow == null) continue;
                            INvwaDataRow updateRow = dataUpdator.addUpdateRow();
                            for (DimensionInfo dimInfo : dimInfos) {
                                updateRow.setKeyValue((ColumnModelDefine)colMap.get(dimInfo.getStatusTableCol()), dimensionCombination.getValue(dimInfo.getDimensionName()));
                            }
                            updateRow.setKeyValue((ColumnModelDefine)colMap.get("DAST_FORMKEY"), (Object)formKey);
                            updateRow.setValue(entryIndex.intValue(), dataRow.getValue(entryIndex.intValue()));
                        }
                    }
                    dataUpdator.commitChanges(updateCon);
                }
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
                this.refreshStatusByFormula(entry.getKey(), mergeDimensionValueSet, formKeys.stream().map(FormFieldInfoDefine::getFormKey).collect(Collectors.toList()));
            }
        }
    }

    @Override
    public void batchRefreshDataStatus(BatchRefreshStatusPar par) throws Exception {
        if (!this.nrdbHelper.isEnableNrdb()) {
            super.batchRefreshDataStatus(par);
        } else if (this.openStatus(par.getTaskKey()) && !CollectionUtils.isEmpty(par.getFormKeys())) {
            DimensionValueSet dimensionValueSet = CommonUtil.getMergeDimensionValueSet(par.getDimensionCollection());
            try {
                this.refreshStatusByFormula(par.getFormSchemeKey(), dimensionValueSet, par.getFormKeys());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

