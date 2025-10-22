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
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
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
package com.jiuqi.nr.data.engine.version.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.DataVersionService;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.engine.version.DataVersion;
import com.jiuqi.nr.data.engine.version.DataVersionManager;
import com.jiuqi.nr.data.engine.version.DataVersionOpt;
import com.jiuqi.nr.data.engine.version.impl.DataInitUtil;
import com.jiuqi.nr.data.engine.version.impl.DataVersionCommentJoinProvider;
import com.jiuqi.nr.data.engine.version.impl.DataVersionImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataVersionManagerImpl
implements DataVersionManager {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IDataDefinitionRuntimeController dataRunTimeController;
    @Resource
    private DataVersionService dataVersionService;
    @Autowired
    private DataVersionOpt dataVersionOpt;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    private static final Double START_PROGRESS = 0.05;
    private static final Double DELETE_PROGRESS = 0.5;
    private static final Double COPY_PROGRESS = 0.8;
    private static final Double END_PROGRESS = 1.0;
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";

    @Override
    public void createVersion(String formSchemeKey, DataVersion dataVersion, IMonitor iMonitor) throws Exception {
        this.createVersion(this.getTableKeysByFormScheme(formSchemeKey), formSchemeKey, dataVersion, iMonitor);
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
    public void overwriteDefaultVersion(String formSchemeKey, DataVersion dataVersion, IMonitor iMonitor) throws Exception {
        String versionId;
        if (null != iMonitor) {
            iMonitor.onProgress(START_PROGRESS.doubleValue());
            iMonitor.message("\u5f00\u59cb\u8986\u76d6\u9ed8\u8ba4\u7248\u672c", (Object)this);
            iMonitor.start();
        }
        if ((versionId = dataVersion.getVersionId()).equals("00000000-0000-0000-0000-000000000000")) {
            if (null != iMonitor) {
                iMonitor.onProgress(END_PROGRESS.doubleValue());
                iMonitor.message("\u9ed8\u8ba4\u7248\u672c\u8986\u76d6\u5b8c\u6210!", (Object)this);
                iMonitor.finish();
            }
            return;
        }
        List<String> tableKeys = this.getTableKeysByFormScheme(formSchemeKey);
        DimensionValueSet dimensionValueSet = dataVersion.getDimensionValueSet();
        dimensionValueSet.setValue("VERSIONID", (Object)versionId);
        this.dataVersionOpt.overwriteDefaultVersion(tableKeys, dimensionValueSet, versionId, "00000000-0000-0000-0000-000000000000", iMonitor, true, formSchemeKey);
        if (null != iMonitor) {
            iMonitor.onProgress(END_PROGRESS.doubleValue());
            iMonitor.message("\u9ed8\u8ba4\u7248\u672c\u8986\u76d6\u5b8c\u6210!", (Object)this);
            iMonitor.finish();
        }
    }

    @Override
    public void overwriteDefaultVersionOfFormList(List<String> formKeys, String formSchemeKey, DataVersion dataVersion, IMonitor iMonitor) throws Exception {
        String versionId;
        if (null != iMonitor) {
            iMonitor.onProgress(START_PROGRESS.doubleValue());
            iMonitor.message("\u5f00\u59cb\u8986\u76d6\u9ed8\u8ba4\u7248\u672c", (Object)this);
            iMonitor.start();
        }
        if ((versionId = dataVersion.getVersionId()).equals("00000000-0000-0000-0000-000000000000")) {
            if (null != iMonitor) {
                iMonitor.onProgress(END_PROGRESS.doubleValue());
                iMonitor.message("\u9ed8\u8ba4\u7248\u672c\u8986\u76d6\u5b8c\u6210!", (Object)this);
                iMonitor.finish();
            }
            return;
        }
        List<String> tableKeys = this.getTableKeysByForm(formKeys);
        Double deleteProcess = DELETE_PROGRESS / (double)tableKeys.size();
        DimensionValueSet dimensionValueSet = dataVersion.getDimensionValueSet();
        dimensionValueSet.setValue("VERSIONID", (Object)versionId);
        this.dataVersionOpt.overwriteDefaultVersionOfFormList(tableKeys, dimensionValueSet, versionId, "00000000-0000-0000-0000-000000000000", iMonitor, true, formKeys, formSchemeKey);
        if (null != iMonitor) {
            iMonitor.onProgress(END_PROGRESS.doubleValue());
            iMonitor.message("\u9ed8\u8ba4\u7248\u672c\u8986\u76d6\u5b8c\u6210!", (Object)this);
            iMonitor.finish();
        }
    }

    private void createVersion(List<String> tableKeys, String formSchemeKey, DataVersion dataVersion, IMonitor iMonitor) throws Exception {
        this.copyVersion(tableKeys, formSchemeKey, dataVersion, "00000000-0000-0000-0000-000000000000", iMonitor);
    }

    private void copyVersion(List<String> tableKeys, String formSchemeKey, DataVersion dataVersion, String oldVersionId, IMonitor iMonitor) throws Exception {
        FormSchemeDefine formScheme;
        String newVersion;
        if (null != iMonitor) {
            iMonitor.onProgress(START_PROGRESS.doubleValue());
            iMonitor.message("\u5f00\u59cb\u521b\u5efa\u7248\u672c", (Object)this);
            iMonitor.start();
        }
        if (null == (newVersion = dataVersion.getVersionId())) {
            return;
        }
        if (!newVersion.equals("00000000-0000-0000-0000-000000000000") && !oldVersionId.equals(newVersion)) {
            this.dataVersionOpt.createDataVersion(tableKeys, dataVersion.getDimensionValueSet(), oldVersionId, dataVersion.getVersionId(), iMonitor, false, formSchemeKey);
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
        DimensionValueSet dimensionValueSet = dataVersion.getDimensionValueSet();
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
    public void modifyVersion(String formSchemeKey, DataVersion dataVersion) throws Exception {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
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

    private void deleteVersion(String tableKey, String versionId, DimensionValueSet dimensionValueSet, String formSchemeKey) throws Exception {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(tableKey);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableKey);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        ArrayList<ColumnModelDefine> queryColumns = new ArrayList<ColumnModelDefine>();
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        for (ColumnModelDefine column : columns) {
            queryColumns.add(column);
        }
        dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("VERSIONID", (Object)versionId);
        List<DataVersion> queryVersions = this.queryVersion(dimensionValueSet, formSchemeKey);
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
            DimensionValueSet queryDimensionValueSet = dataVersion.getDimensionValueSet();
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
    public List<DataVersion> queryVersion(DimensionValueSet dimValueSet, String formSchemeKey) throws Exception {
        ArrayList<DataVersion> result = new ArrayList<DataVersion>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return null;
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
            if (columnModelDefine.getCode().equals("VERSIONID") && dimValueSet.getValue("VERSIONID") != null) {
                queryModel.getColumnFilters().put(columnModelDefine, dimValueSet.getValue("VERSIONID"));
            }
            if (columnModelDefine.getCode().equals(PERIOD_FIELD) && dimValueSet.getValue("DATATIME") != null) {
                queryModel.getColumnFilters().put(columnModelDefine, dimValueSet.getValue("DATATIME"));
            }
            if (columnModelDefine.getCode().equals(DW_FIELD)) {
                String referTableID = columnModelDefine.getReferTableID();
                if (referTableID == null) {
                    Object value = dimValueSet.getValue(columnModelDefine.getCode());
                    if (value != null) {
                        queryModel.getColumnFilters().put(columnModelDefine, value);
                    }
                } else {
                    TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                    IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
                    Object value = dimValueSet.getValue(entityByCode.getDimensionName());
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
                    version.setAutoCreated(Boolean.getBoolean(dataRow.getString(j)));
                    continue;
                }
                if (columnModelDefine.getCode().equals(DW_FIELD)) {
                    dimensionValueSet = version.getDimensionValueSet();
                    if (dimensionValueSet == null) {
                        dimensionValueSet = new DimensionValueSet();
                        version.setDimensionValueSet(dimensionValueSet);
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
                dimensionValueSet = version.getDimensionValueSet();
                if (dimensionValueSet == null) {
                    dimensionValueSet = new DimensionValueSet();
                    version.setDimensionValueSet(dimensionValueSet);
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

