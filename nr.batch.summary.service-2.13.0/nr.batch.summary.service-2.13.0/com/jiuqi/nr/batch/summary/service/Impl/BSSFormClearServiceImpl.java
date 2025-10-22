/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl
 *  com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.subdatabase.controller.SubDataBaseController
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.Impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.batch.summary.service.BSSFormClearService;
import com.jiuqi.nr.batch.summary.service.targetform.BSFormClearTableInfo;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseController;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BSSFormClearServiceImpl
implements BSSFormClearService {
    private static final Logger logger = LoggerFactory.getLogger(BSSFormClearServiceImpl.class);
    private static final String DEFAULT_DB_CODE = "default";
    private static final String DIM_DATATIME = "DATATIME";
    @Resource
    private IRunTimeViewController rtViewService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Resource
    public DataFieldDeployInfoService dataFieldDeployInfoService;
    @Autowired
    private SubDataBaseController subDataBaseController;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    @Override
    public void clearUselessData(SummaryScheme oldScheme, SummarySchemeImpl newScheme) throws Exception {
        if (RangeFormType.ALL == newScheme.getRangeForm().getRangeFormType()) {
            return;
        }
        Set<String> allDataBaseCodes = this.getAllSubDataBaseCodes(oldScheme.getTask());
        List schemePeriodLinkDefines = this.rtViewService.querySchemePeriodLinkByTask(oldScheme.getTask());
        if (CollectionUtils.isEmpty(schemePeriodLinkDefines)) {
            return;
        }
        for (int i = 0; i < schemePeriodLinkDefines.size(); ++i) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = (SchemePeriodLinkDefine)schemePeriodLinkDefines.get(i);
            List<FormDefine> oldRangeForms = this.getRangeForms(oldScheme.getRangeForm(), oldScheme.getTask(), schemePeriodLinkDefine.getSchemeKey());
            List<FormDefine> newRangeForms = this.getRangeForms(newScheme.getRangeForm(), oldScheme.getTask(), schemePeriodLinkDefine.getSchemeKey());
            oldRangeForms.removeAll(newRangeForms);
            if (CollectionUtils.isEmpty(oldRangeForms)) continue;
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("GATHER_SCHEME_CODE", (Object)newScheme.getKey());
            dimensionValueSet.setValue(DIM_DATATIME, (Object)schemePeriodLinkDefine.getPeriodKey());
            for (String dabaseCode : allDataBaseCodes) {
                List<BSFormClearTableInfo> tableModelDefines = this.getTableModelDefines(oldRangeForms, oldScheme.getTask(), schemePeriodLinkDefine.getPeriodKey(), dabaseCode);
                for (int j = 0; j < tableModelDefines.size(); ++j) {
                    BSFormClearTableInfo tableInfo = tableModelDefines.get(j);
                    if (tableInfo.isFloatForm()) {
                        this.doClearFloatForm(tableInfo, dimensionValueSet);
                        continue;
                    }
                    this.doClearFixForm(tableInfo, dimensionValueSet);
                }
            }
        }
    }

    private Set<String> getAllSubDataBaseCodes(String task) {
        HashSet<String> allDataBaseCodes = new HashSet<String>();
        allDataBaseCodes.add(DEFAULT_DB_CODE);
        TaskDefine taskDefine = this.rtViewService.queryTaskDefine(task);
        List allSubDataBase = this.subDataBaseController.getSubDataBaseObjByDataScheme(taskDefine.getDataScheme());
        if (!CollectionUtils.isEmpty(allSubDataBase)) {
            allDataBaseCodes.addAll(allSubDataBase.stream().map(e -> e.getCode()).collect(Collectors.toList()));
        }
        return allDataBaseCodes;
    }

    private String getSDBSummaryTableName(String dataBaseCode, String defaultTableName) {
        if (!StringUtils.isNotEmpty((String)dataBaseCode) || DEFAULT_DB_CODE.equals(dataBaseCode)) {
            return defaultTableName + "_G_";
        }
        return dataBaseCode + defaultTableName + "_" + dataBaseCode + "_G_";
    }

    @Override
    public void doClearFixForm(BSFormClearTableInfo tableInfo, DimensionValueSet dimensionValueSet) throws Exception {
        String period = (String)dimensionValueSet.getValue(DIM_DATATIME);
        String colNameOfScheme = tableInfo.getSchemeColumnCode();
        String tableName = tableInfo.getCurSummaryDBTableName();
        String colNameOfPeriod = tableInfo.getPeriodColumnCode();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(tableName);
        HashMap<ColumnModelDefine, String> keyValue = new HashMap<ColumnModelDefine, String>();
        List<ColumnModelDefine> bizColumnModelDefines = tableInfo.getBizColumnModelDefines();
        for (int i = 0; i < bizColumnModelDefines.size(); ++i) {
            ColumnModelDefine columnModelDefine = bizColumnModelDefines.get(i);
            if (!colNameOfPeriod.equals(columnModelDefine.getCode())) continue;
            keyValue.put(columnModelDefine, period);
        }
        List<ColumnModelDefine> dataColumnModelDefines = tableInfo.getDataColumnModelDefines();
        for (int i = 0; i < dataColumnModelDefines.size(); ++i) {
            ColumnModelDefine columnModelDefine = dataColumnModelDefines.get(i);
            if (colNameOfScheme.equals(columnModelDefine.getCode())) {
                keyValue.put(columnModelDefine, dimensionValueSet.getValue("GATHER_SCHEME_CODE").toString());
                continue;
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
            INvwaDataUpdator updaor = dataAccess.openForUpdate(dataAccessContext);
            INvwaDataRow updateRow = updaor.addUpdateRow();
            if (keyValue.size() > 0) {
                int size = queryModel.getColumns().size();
                for (int i = 0; i < size; ++i) {
                    updateRow.setValue(i, null);
                }
                keyValue.forEach((k, v) -> updateRow.setKeyValue(k, v));
                updaor.commitChanges(dataAccessContext);
            }
        }
        catch (Exception e) {
            logger.error(queryModel.getMainTableName() + "\u4e0d\u5b58\u5728\u6216\u5220\u9664\u6570\u636e\u5931\u8d25", e);
        }
    }

    @Override
    public void doClearFloatForm(BSFormClearTableInfo tableInfo, DimensionValueSet dimensionValueSet) throws Exception {
        String period = (String)dimensionValueSet.getValue(DIM_DATATIME);
        String colNameOfScheme = tableInfo.getSchemeColumnCode();
        String tableName = tableInfo.getCurSummaryDBTableName();
        String colNameOfPeriod = tableInfo.getPeriodColumnCode();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(tableName);
        HashMap<ColumnModelDefine, String> keyValue = new HashMap<ColumnModelDefine, String>();
        List<ColumnModelDefine> bizColumnModelDefines = tableInfo.getBizColumnModelDefines();
        for (int i = 0; i < bizColumnModelDefines.size(); ++i) {
            ColumnModelDefine columnModelDefine = bizColumnModelDefines.get(i);
            if (!colNameOfPeriod.equals(columnModelDefine.getCode())) continue;
            keyValue.put(columnModelDefine, period);
        }
        List<ColumnModelDefine> dataColumnModelDefines = tableInfo.getDataColumnModelDefines();
        for (int i = 0; i < dataColumnModelDefines.size(); ++i) {
            ColumnModelDefine columnModelDefine = dataColumnModelDefines.get(i);
            if (colNameOfScheme.equals(columnModelDefine.getCode())) {
                keyValue.put(columnModelDefine, dimensionValueSet.getValue("GATHER_SCHEME_CODE").toString());
                continue;
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        try {
            INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
            INvwaDataUpdator updaor = dataAccess.openForUpdate(dataAccessContext);
            INvwaDataRow deleteRow = updaor.addDeleteRow();
            if (keyValue.size() > 0) {
                keyValue.forEach((k, v) -> deleteRow.setKeyValue(k, v));
                updaor.commitChanges(dataAccessContext);
            }
        }
        catch (Exception e) {
            logger.error(queryModel.getMainTableName() + "\u4e0d\u5b58\u5728\u6216\u66f4\u65b0\u6570\u636e\u5931\u8d25", e);
        }
    }

    @Override
    public List<BSFormClearTableInfo> getTableModelDefines(List<FormDefine> formDefines, String taskKey, String period, String dbCode) {
        ArrayList<BSFormClearTableInfo> fieldsList = new ArrayList<BSFormClearTableInfo>();
        HashSet<String> checkList = new HashSet<String>();
        for (FormDefine formDefine : formDefines) {
            List allRegionsInForm = this.rtViewService.getAllRegionsInForm(formDefine.getKey());
            for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                List allLinksInRegion = this.rtViewService.getAllLinksInRegion(dataRegionDefine.getKey());
                List deployInfo = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys((String[])allLinksInRegion.stream().map(DataLinkDefine::getLinkExpression).toArray(String[]::new));
                for (DataFieldDeployInfo dataFieldDeployInfo : deployInfo) {
                    String summaryDBTableName = this.getSDBSummaryTableName(dbCode, dataFieldDeployInfo.getTableName());
                    TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(summaryDBTableName);
                    if (tableModelDefine == null || checkList.contains(tableModelDefine.getName()) || !this.isSummaryTableModel(dataRegionDefine, tableModelDefine)) continue;
                    List<String> bizKeys = Arrays.asList(tableModelDefine.getBizKeys().split(";"));
                    List bizColumnModelDefines = this.dataModelService.getColumnModelDefinesByIDs(bizKeys);
                    String dwColumnCode = this.getDWColumnCode(taskKey, period, bizColumnModelDefines);
                    List dataColumnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
                    dataColumnModelDefines.removeAll(bizColumnModelDefines);
                    TaskDefine taskDefine = this.rtViewService.queryTaskDefine(taskKey);
                    BSFormClearTableInfo tableInfo = new BSFormClearTableInfo();
                    tableInfo.setTableModelDefine(tableModelDefine);
                    tableInfo.setBizColumnModelDefines(bizColumnModelDefines);
                    tableInfo.setDataColumnModelDefines(dataColumnModelDefines);
                    tableInfo.setFloatForm(dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE);
                    tableInfo.setTaskDefine(taskDefine);
                    tableInfo.setDWColumnCode(dwColumnCode);
                    tableInfo.setDefaultTableName(dataFieldDeployInfo.getTableName());
                    fieldsList.add(tableInfo);
                    checkList.add(tableModelDefine.getName());
                }
            }
        }
        return fieldsList;
    }

    public String getDWColumnCode(String taskKey, String period, List<ColumnModelDefine> bizColumnModelDefines) {
        String mainDimName = this.getMainDimName(taskKey, period);
        for (ColumnModelDefine columnModelDefine : bizColumnModelDefines) {
            TableModelDefine tableModelDefine;
            IEntityDefine entityDefine;
            String referTableID = columnModelDefine.getReferTableID();
            if (null == referTableID || !mainDimName.equals((entityDefine = this.iEntityMetaService.queryEntityByCode((tableModelDefine = this.dataModelService.getTableModelDefineById(referTableID)).getCode())).getDimensionName())) continue;
            return columnModelDefine.getCode();
        }
        return null;
    }

    public String getMainDimName(String taskId, String period) {
        FormSchemeDefine formSchemeDefine = this.queryFormSchemeDefine(taskId, period);
        assert (formSchemeDefine != null);
        IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(this.dataAccesslUtil.contextEntityId(formSchemeDefine.getDw()));
        return iEntityDefine.getDimensionName();
    }

    public FormSchemeDefine queryFormSchemeDefine(String taskId, String period) {
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.rtViewService.querySchemePeriodLinkByPeriodAndTask(period, taskId);
            if (schemePeriodLinkDefine != null) {
                return this.rtViewService.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private boolean isSummaryTableModel(DataRegionDefine dataRegionDefine, TableModelDefine tableModelDefine) {
        String dataTableKey;
        if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && StringUtils.isNotEmpty((String)(dataTableKey = this.dataFieldDeployInfoService.getDataTableByTableModel(tableModelDefine.getID())))) {
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
            return dataTable != null && !dataTable.getDataTableGatherType().equals((Object)DataTableGatherType.LIST);
        }
        return true;
    }

    public List<FormDefine> getRangeForms(SchemeRangeForm rangeFormConf, String taskKey, String formSchemeKey) {
        List defRangeForms = null;
        switch (rangeFormConf.getRangeFormType()) {
            case ALL: {
                defRangeForms = this.rtViewService.queryAllFormDefinesByTask(taskKey);
                break;
            }
            case CUSTOM: {
                defRangeForms = this.rtViewService.queryFormsById(rangeFormConf.getFormList());
            }
        }
        List formDefines = this.rtViewService.queryAllFormDefinesByFormScheme(formSchemeKey);
        List formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        return defRangeForms.stream().filter(formDefine -> formKeys.contains(formDefine.getKey())).collect(Collectors.toList());
    }
}

