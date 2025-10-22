/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.DataVersionService
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.snapshot.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.DataVersionService;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.snapshot.DataVersion;
import com.jiuqi.nr.snapshot.SnapshotOpt;
import com.jiuqi.nr.snapshot.SnapshotService;
import com.jiuqi.nr.snapshot.impl.DataInitUtil;
import com.jiuqi.nr.snapshot.impl.DataVersionCommentJoinProvider;
import com.jiuqi.nr.snapshot.impl.DataVersionImpl;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotServiceImpl
implements SnapshotService {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IDataDefinitionRuntimeController dataRunTimeController;
    @Resource
    private DataVersionService dataVersionService;
    @Autowired
    private SnapshotOpt dataVersionOpt;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private static final Double START_PROGRESS = 0.05;
    private static final Double DELETE_PROGRESS = 0.5;
    private static final Double COPY_PROGRESS = 0.8;
    private static final Double END_PROGRESS = 1.0;
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";

    @Override
    public void createVersion(DataVersion dataVersion) throws Exception {
        String formSchemeKey = dataVersion.getFormSchemeKey();
        this.createVersion(this.getTableKeysByFormScheme(formSchemeKey), formSchemeKey, dataVersion, null);
    }

    public void createVersionByForm(List<String> formKeys, String formSchemeKey, DataVersion dataVersion, IMonitor iMonitor) throws Exception {
        List<String> formKeysByFormScheme = this.getFormKeysByFormScheme(formSchemeKey);
        List<String> tableKeys = this.getTableKeysByForm(formKeys.stream().filter(t -> formKeysByFormScheme.contains(t)).collect(Collectors.toList()));
        this.createVersion(tableKeys, formSchemeKey, dataVersion, iMonitor);
    }

    private List<String> getFormKeysByFormScheme(String formSchemeKey) {
        return this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
    }

    private List<String> getTableKeysByForm(List<String> formKeys) {
        HashSet fieldSet = new HashSet();
        for (String formKey : formKeys) {
            List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
            fieldSet.addAll(fieldKeys);
        }
        return this.dataRunTimeController.queryTableDefinesByFields(fieldSet).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
    }

    private List<String> getTableKeysByFormScheme(String formSchemeKey) {
        List<String> formKeys = this.getFormKeysByFormScheme(formSchemeKey);
        return this.getTableKeysByForm(formKeys);
    }

    @Override
    public void overwriteDefaultVersion(DataVersion dataVersion) throws Exception {
        String versionId = dataVersion.getVersionId();
        if (versionId.equals("00000000-0000-0000-0000-000000000000")) {
            return;
        }
        List<String> tableKeys = this.getTableKeysByFormScheme(dataVersion.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = dataVersion.getDimensionValueSet().toDimensionValueSet();
        dimensionValueSet.setValue("VERSIONID", (Object)versionId);
        this.dataVersionOpt.overwriteDefaultVersion(tableKeys, dimensionValueSet, versionId, "00000000-0000-0000-0000-000000000000", null, true, dataVersion.getFormSchemeKey());
    }

    @Override
    public void overwriteDefaultVersionOfFormList(List<String> formKeys, DataVersion dataVersion) throws Exception {
        this.overwriteDefaultVersionOfFormList(formKeys, dataVersion, null);
    }

    @Override
    public void overwriteDefaultVersionOfFormList(List<String> formKeys, DataVersion dataVersion, IMonitor iMonitor) throws Exception {
        String versionId = dataVersion.getVersionId();
        if (versionId.equals("00000000-0000-0000-0000-000000000000")) {
            return;
        }
        List<String> tableKeys = this.getTableKeysByForm(formKeys);
        Double deleteProcess = DELETE_PROGRESS / (double)tableKeys.size();
        DimensionValueSet dimensionValueSet = dataVersion.getDimensionValueSet().toDimensionValueSet();
        dimensionValueSet.setValue("VERSIONID", (Object)versionId);
        this.dataVersionOpt.overwriteDefaultVersionOfFormList(tableKeys, dimensionValueSet, versionId, "00000000-0000-0000-0000-000000000000", iMonitor, true, formKeys, dataVersion.getFormSchemeKey());
    }

    private void createVersion(List<String> tableKeys, String formSchemeKey, DataVersion dataVersion, IMonitor iMonitor) throws Exception {
        this.copyVersion(tableKeys, formSchemeKey, dataVersion, "00000000-0000-0000-0000-000000000000", iMonitor);
    }

    private void copyVersion(List<String> tableKeys, String formSchemeKey, DataVersion dataVersion, String oldVersionId, IMonitor iMonitor) throws Exception {
        FormSchemeDefine formScheme;
        String newVersion;
        HashMap<String, String> mapObject = new HashMap<String, String>();
        DimensionValueSet dimensionValueSet = dataVersion.getDimensionValueSet().toDimensionValueSet();
        if (dimensionValueSet != null && dimensionValueSet.size() != 0) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String name = dimensionValueSet.getName(i);
                Object value = dimensionValueSet.getValue(i);
                if (name == null || value == null) continue;
                mapObject.put(name, value.toString());
            }
            mapObject.put("formSchemeKey", formSchemeKey);
        }
        LogHelper.info((String)"copyVersion", (String)"\u521b\u5efa\u7248\u672c", (String)"\u5f00\u59cb\u521b\u5efa\u7248\u672c", mapObject);
        if (null != iMonitor) {
            iMonitor.onProgress(START_PROGRESS.doubleValue());
            iMonitor.message("\u5f00\u59cb\u521b\u5efa\u7248\u672c", (Object)this);
            iMonitor.start();
        }
        if (null == (newVersion = dataVersion.getVersionId())) {
            return;
        }
        if (!newVersion.equals("00000000-0000-0000-0000-000000000000") && !oldVersionId.equals(newVersion)) {
            DimensionValueSet dimensionValueSet2 = this.getReportDims(formSchemeKey, dataVersion);
            this.dataVersionOpt.createDataVersion(tableKeys, dimensionValueSet2, oldVersionId, dataVersion.getVersionId(), iMonitor, false, formSchemeKey);
        }
        if ((formScheme = this.runTimeViewController.getFormScheme(formSchemeKey)) == null) {
            if (null != iMonitor) {
                iMonitor.onProgress(END_PROGRESS.doubleValue());
                iMonitor.message("\u521b\u5efa\u5b8c\u6210!", (Object)this);
                iMonitor.finish();
            }
            return;
        }
        ExecutorContext context = new ExecutorContext(this.dataRunTimeController);
        this.insertVersion(context, formScheme, dataVersion);
        if (null != iMonitor) {
            iMonitor.onProgress(COPY_PROGRESS + 0.1);
            iMonitor.message("\u63d2\u5165\u65b0\u7248\u672c, \u603b\u8fdb\u5ea6" + (COPY_PROGRESS + 0.1) * 100.0 + "%", (Object)this);
        }
        this.insertVersionRelation(context, formScheme, dataVersion);
        if (null != iMonitor) {
            iMonitor.onProgress(COPY_PROGRESS + 0.19);
            iMonitor.message("\u63d2\u5165\u65b0\u7248\u672c\u5173\u8054\u8868, \u603b\u8fdb\u5ea6" + (COPY_PROGRESS + 0.19) * 100.0 + "%", (Object)this);
        }
        if (null != iMonitor) {
            iMonitor.onProgress(END_PROGRESS.doubleValue());
            iMonitor.message("\u521b\u5efa\u5b8c\u6210!", (Object)this);
            iMonitor.finish();
        }
        LogHelper.info((String)"copyVersion", (String)"\u521b\u5efa\u7248\u672c", (String)"\u521b\u5efa\u7248\u672c\u5b8c\u6210", mapObject);
    }

    private DimensionValueSet getReportDims(String formSchemeKey, DataVersion dataVersion) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine queryTaskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String dataScheme = queryTaskDefine.getDataScheme();
        List reportDimension = this.runtimeDataSchemeService.getReportDimension(dataScheme);
        DimensionValueSet dimensionValueSet2 = dataVersion.getDimensionValueSet().toDimensionValueSet();
        String dims = queryTaskDefine.getDims();
        if (dims != null && !dims.isEmpty()) {
            String[] dimArr = dims.split(";");
            ArrayList<String> removeDimKeys = new ArrayList<String>(Arrays.asList(dimArr));
            for (DataDimension dataDimDTO : reportDimension) {
                removeDimKeys.remove(dataDimDTO.getDimKey());
            }
            if (!removeDimKeys.isEmpty()) {
                for (String entityKey : removeDimKeys) {
                    if (entityKey.equals("ADJUST")) {
                        dimensionValueSet2.clearValue("ADJUST");
                        continue;
                    }
                    IEntityDefine entity = this.iEntityMetaService.queryEntity(entityKey);
                    String dimensionName = entity.getDimensionName();
                    dimensionValueSet2.clearValue(dimensionName);
                }
            }
        }
        return dimensionValueSet2;
    }

    private DimensionValueSet getReportDims(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine queryTaskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String dataScheme = queryTaskDefine.getDataScheme();
        List reportDimension = this.runtimeDataSchemeService.getReportDimension(dataScheme);
        String dims = queryTaskDefine.getDims();
        if (dims != null && !dims.isEmpty()) {
            String[] dimArr = dims.split(";");
            ArrayList<String> removeDimKeys = new ArrayList<String>(Arrays.asList(dimArr));
            for (DataDimension dataDimDTO : reportDimension) {
                removeDimKeys.remove(dataDimDTO.getDimKey());
            }
            if (!removeDimKeys.isEmpty()) {
                for (String entityKey : removeDimKeys) {
                    if (entityKey.equals("ADJUST")) {
                        dimensionValueSet.clearValue("ADJUST");
                        continue;
                    }
                    IEntityDefine entity = this.iEntityMetaService.queryEntity(entityKey);
                    String dimensionName = entity.getDimensionName();
                    dimensionValueSet.clearValue(dimensionName);
                }
            }
        }
        return dimensionValueSet;
    }

    private void insertVersionRelation(ExecutorContext context, FormSchemeDefine formScheme, DataVersion dataVersion) throws Exception {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(DataInitUtil.getSysVersionRelationTableName(formScheme));
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        for (ColumnModelDefine columnModelDefine : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator updaor = dataAccess.openForUpdate(dataAccessContext);
        INvwaDataRow nvwaDataRow = updaor.addInsertRow();
        tableModelDefine.getKeys();
        DimensionValueSet dimensionValueSet = dataVersion.getDimensionValueSet().toDimensionValueSet();
        block11: for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
            switch (columnModelDefine.getCode()) {
                case "VERSIONID": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getVersionId());
                    continue block11;
                }
                case "MDCODE": {
                    String referTableID = ((ColumnModelDefine)columns.get(i)).getReferTableID();
                    if (referTableID == null) {
                        nvwaDataRow.setValue(i, dimensionValueSet.getValue(((ColumnModelDefine)columns.get(i)).getCode().equals(PERIOD_FIELD) ? "DATATIME" : ((ColumnModelDefine)columns.get(i)).getCode()));
                        continue block11;
                    }
                    TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(((ColumnModelDefine)columns.get(i)).getReferTableID());
                    IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
                    nvwaDataRow.setValue(i, dimensionValueSet.getValue(entityByCode.getDimensionName()));
                    continue block11;
                }
                case "PERIOD": {
                    nvwaDataRow.setValue(i, dimensionValueSet.getValue("DATATIME"));
                    continue block11;
                }
            }
        }
        updaor.commitChanges(dataAccessContext);
    }

    private void insertVersion(ExecutorContext context, FormSchemeDefine formScheme, DataVersion dataVersion) throws Exception {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(DataInitUtil.getSysVersionTableName(formScheme));
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        for (ColumnModelDefine columnModelDefine : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator updaor = dataAccess.openForUpdate(dataAccessContext);
        INvwaDataRow nvwaDataRow = updaor.addInsertRow();
        block17: for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
            switch (columnModelDefine.getCode()) {
                case "VERSIONID": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getVersionId());
                    continue block17;
                }
                case "TITLE": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getTitle());
                    continue block17;
                }
                case "DESCRIBE_": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getDescribe());
                    continue block17;
                }
                case "CREATUSER": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getCreatUser());
                    continue block17;
                }
                case "ISAUTOCREATED": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.isAutoCreated());
                    continue block17;
                }
                case "CREATTIME": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getCreatTime());
                    continue block17;
                }
            }
        }
        updaor.commitChanges(dataAccessContext);
    }

    @Override
    public void modifyVersion(DataVersion dataVersion) throws Exception {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(dataVersion.getFormSchemeKey());
        if (formScheme == null) {
            return;
        }
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(DataInitUtil.getSysVersionTableName(formScheme));
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        ArrayList<ColumnModelDefine> queryColumns = new ArrayList<ColumnModelDefine>();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        for (ColumnModelDefine column : columns) {
            queryColumns.add(column);
        }
        for (ColumnModelDefine columnModelDefine : queryColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator updaor = dataAccess.openForUpdate(context);
        ArrayKey arrayKey = new ArrayKey(new Object[]{dataVersion.getVersionId()});
        INvwaDataRow nvwaDataRow = updaor.addUpdateRow(arrayKey);
        block12: for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
            switch (columnModelDefine.getCode()) {
                case "TITLE": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getTitle());
                    continue block12;
                }
                case "DESCRIBE_": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getDescribe());
                    continue block12;
                }
                case "CREATUSER": {
                    nvwaDataRow.setValue(i, (Object)dataVersion.getCreatUser());
                    continue block12;
                }
            }
        }
        updaor.commitChanges(context);
    }

    @Override
    public void deleteVersion(String formSchemeKey, String versionId) throws Exception {
        LogHelper.info((String)"deleteVersion", (String)("\u5220\u9664\u7248\u672c,\u7248\u672cversionID\uff1a" + versionId + "\u62a5\u8868\u65b9\u6848\uff1a" + formSchemeKey), (String)"\u5f00\u59cb\u521b\u5efa\u7248\u672c");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return;
        }
        ArrayList<String> tableKeys = new ArrayList<String>();
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(DataInitUtil.getSysVersionTableName(formScheme));
        TableModelDefine tableModelDefineRelation = this.dataModelService.getTableModelDefineByName(DataInitUtil.getSysVersionRelationTableName(formScheme));
        tableKeys.add(tableModelDefine.getID());
        tableKeys.add(tableModelDefineRelation.getID());
        for (String tableKey : tableKeys) {
            this.deleteVersion(tableKey, versionId, null, formSchemeKey);
        }
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (FormDefine formDefine : formDefines) {
            List fileInfos = this.fileInfoService.getFileInfoByGroup(formDefine.getKey(), "DataVer", FileStatus.AVAILABLE);
            if (null == fileInfos) continue;
            for (FileInfo fileInfo : fileInfos) {
                if (!fileInfo.getName().equals(versionId)) continue;
                this.fileInfoService.deleteFile(fileInfo, Boolean.valueOf(false));
            }
        }
    }

    private void deleteVersion(String tableKey, String versionId, DimensionCombination dimensionCombination, String formSchemeKey) throws Exception {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(tableKey);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableKey);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        ArrayList<ColumnModelDefine> queryColumns = new ArrayList<ColumnModelDefine>();
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        for (ColumnModelDefine column : columns) {
            queryColumns.add(column);
        }
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("VERSIONID", (Object)versionId);
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dim);
        List<DataVersion> queryVersions = this.queryVersion(builder.getCombination(), formSchemeKey);
        DataVersion dataVersion = null;
        if (queryVersions != null && queryVersions.size() > 0) {
            dataVersion = queryVersions.get(0);
        }
        queryModel.setMainTableName(tableModelDefine.getName());
        HashMap<ColumnModelDefine, String> keyValue = new HashMap<ColumnModelDefine, String>();
        for (ColumnModelDefine columnModelDefine : queryColumns) {
            if (columnModelDefine.getCode().equals("VERSIONID")) {
                keyValue.put(columnModelDefine, versionId);
                queryModel.getColumnFilters().put(columnModelDefine, versionId);
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            if (!columnModelDefine.getCode().equals(DW_FIELD) && !columnModelDefine.getCode().equals(PERIOD_FIELD)) continue;
            String referTableID = columnModelDefine.getReferTableID();
            DimensionValueSet queryDimensionValueSet = dataVersion.getDimensionValueSet().toDimensionValueSet();
            if (queryDimensionValueSet != null && queryDimensionValueSet.isAllNull()) {
                queryDimensionValueSet = null;
            }
            if (referTableID == null && queryDimensionValueSet != null) {
                String dimvalue = dataVersion.getDimensionValueSet().getValue(columnModelDefine.getCode().equals(PERIOD_FIELD) ? "DATATIME" : columnModelDefine.getCode()).toString();
                keyValue.put(columnModelDefine, dimvalue);
                continue;
            }
            if (queryDimensionValueSet == null) continue;
            TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
            IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
            if (queryDimensionValueSet == null) continue;
            String dimvalue = queryDimensionValueSet.getValue(entityByCode.getDimensionName()).toString();
            keyValue.put(columnModelDefine, dimvalue);
        }
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator updaor = dataAccess.openForUpdate(dataAccessContext);
        INvwaDataRow addDeleteRow = updaor.addDeleteRow();
        if (keyValue.size() > 0) {
            keyValue.forEach((k, v) -> addDeleteRow.setKeyValue(k, v));
        }
        updaor.commitChanges(dataAccessContext);
    }

    @Override
    public List<DataVersion> queryVersion(DimensionCombination dimensionCombination, String formSchemeKey) throws Exception {
        DimensionValueSet dimensionValueSet2 = this.getReportDims(formSchemeKey, dimensionCombination.toDimensionValueSet());
        dimensionCombination = new DimensionCombinationBuilder(dimensionValueSet2).getCombination();
        ArrayList<DataVersion> result = new ArrayList<DataVersion>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return new ArrayList<DataVersion>();
        }
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(DataInitUtil.getSysVersionTableName(formScheme));
        TableModelDefine tableModelDefineRelation = this.dataModelService.getTableModelDefineByName(DataInitUtil.getSysVersionRelationTableName(formScheme));
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        List columnsRel = this.dataModelService.getColumnModelDefinesByTable(tableModelDefineRelation.getID());
        columns.addAll(columnsRel);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        ArrayList<Object> queryColumns = new ArrayList<Object>();
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        for (Object column : columns) {
            queryColumns.add(column);
        }
        int index = 0;
        for (ColumnModelDefine columnModelDefine : queryColumns) {
            if (columnModelDefine.getCode().equals("CREATTIME")) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine);
                orderByItem.setDesc(true);
                queryModel.getOrderByItems().add(index, orderByItem);
                ++index;
            }
            if (columnModelDefine.getCode().equals("VERSIONID") && dimensionCombination.getValue("VERSIONID") != null) {
                queryModel.getColumnFilters().put(columnModelDefine, dimensionCombination.getValue("VERSIONID"));
            }
            if (columnModelDefine.getCode().equals(PERIOD_FIELD) && dimensionCombination.getValue("DATATIME") != null) {
                queryModel.getColumnFilters().put(columnModelDefine, dimensionCombination.getValue("DATATIME"));
            }
            if (columnModelDefine.getCode().equals(DW_FIELD)) {
                String referTableID = columnModelDefine.getReferTableID();
                if (referTableID == null) {
                    Object value = dimensionCombination.getValue(columnModelDefine.getCode());
                    if (value != null) {
                        queryModel.getColumnFilters().put(columnModelDefine, value);
                    }
                } else {
                    TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                    IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
                    Object value = dimensionCombination.getValue(entityByCode.getDimensionName());
                    if (value != null) {
                        queryModel.getColumnFilters().put(columnModelDefine, value);
                    }
                }
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        queryModel.setMainTableName(tableModelDefine.getName());
        DataVersionCommentJoinProvider joinProvider = new DataVersionCommentJoinProvider();
        joinProvider.getSqlJoinItem(tableModelDefine.getName(), tableModelDefineRelation.getName());
        dataAccessContext.setSqlJoinProvider((ISqlJoinProvider)joinProvider);
        INvwaDataAccess iNvwaDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataSet = iNvwaDataAccess.executeQuery(dataAccessContext);
        for (int i = 0; i < dataSet.size(); ++i) {
            DataRow dataRow = dataSet.get(i);
            DataVersionImpl version = new DataVersionImpl();
            for (int j = 0; j < queryColumns.size(); ++j) {
                DimensionCombinationBuilder builder;
                DimensionValueSet dimensionValueSet;
                ColumnModelDefine columnModelDefine = (ColumnModelDefine)queryColumns.get(j);
                if (columnModelDefine.getCode().equals("VERSIONID")) {
                    version.setVersionId(dataRow.getString(j));
                    continue;
                }
                if (columnModelDefine.getCode().equals("TITLE")) {
                    version.setTitle(dataRow.getString(j));
                    continue;
                }
                if (columnModelDefine.getCode().equals("DESCRIBE_")) {
                    version.setDescribe(dataRow.getString(j));
                    continue;
                }
                if (columnModelDefine.getCode().equals("CREATTIME")) {
                    Calendar date = dataRow.getDate(j);
                    version.setCreatTime(date != null ? date.getTime() : null);
                    continue;
                }
                if (columnModelDefine.getCode().equals("CREATUSER")) {
                    version.setCreatUser(dataRow.getString(j));
                    continue;
                }
                if (columnModelDefine.getCode().equals("ISAUTOCREATED")) {
                    if (1 == dataRow.getInt(j)) {
                        version.setAutoCreated(true);
                        continue;
                    }
                    version.setAutoCreated(false);
                    continue;
                }
                if (columnModelDefine.getCode().equals(DW_FIELD)) {
                    dimensionValueSet = null;
                    if (version.getDimensionValueSet() == null) {
                        builder = new DimensionCombinationBuilder();
                        version.setDimensionValueSet(builder.getCombination());
                        dimensionValueSet = new DimensionValueSet();
                    } else {
                        dimensionValueSet = version.getDimensionValueSet().toDimensionValueSet();
                    }
                    String referTableID = columnModelDefine.getReferTableID();
                    String dimName = null;
                    if (referTableID == null) {
                        dimName = columnModelDefine.getCode();
                    } else {
                        TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                        IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
                        dimName = entityByCode.getDimensionName();
                    }
                    dimensionValueSet.setValue(dimName, (Object)dataRow.getString(j));
                    continue;
                }
                if (!columnModelDefine.getCode().equals(PERIOD_FIELD)) continue;
                dimensionValueSet = null;
                if (version.getDimensionValueSet() == null) {
                    builder = new DimensionCombinationBuilder();
                    version.setDimensionValueSet(builder.getCombination());
                    dimensionValueSet = new DimensionValueSet();
                } else {
                    dimensionValueSet = version.getDimensionValueSet().toDimensionValueSet();
                }
                dimensionValueSet.setValue("DATATIME", (Object)dataRow.getString(j));
            }
            result.add(version);
        }
        return result;
    }

    @Override
    public void batchDeleteVersion(String formSchemeKey, String ... versionIds) throws Exception {
        for (String versionId : versionIds) {
            this.deleteVersion(formSchemeKey, versionId);
        }
    }
}

