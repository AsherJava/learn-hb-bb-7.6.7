/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.engine.version.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.nr.data.engine.version.DataVersionOpt;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataVersionOptImpl
implements DataVersionOpt {
    private static final Logger logger = LoggerFactory.getLogger(DataVersionOptImpl.class);
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private IDataDefinitionRuntimeController controller;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    private static final Double DELETE_PROGRESS = 0.5;
    private static final Double COPY_PROGRESS = 0.8;
    private static final Double END_PROGRESS = 1.0;

    @Override
    public String createDataVersion(List<String> tableKeys, DimensionValueSet dimValueSet, String oldVersion, String newVersion, IMonitor iMonitor, boolean isForOverwrite, String formSchemeKey) throws Exception {
        for (int i = 0; i < dimValueSet.size(); ++i) {
            Object value = dimValueSet.getValue(i);
            if (value != null && !value.equals("")) continue;
            dimValueSet.clearValue(dimValueSet.getName(i));
        }
        Double process = 0.0;
        process = isForOverwrite ? Double.valueOf(END_PROGRESS - DELETE_PROGRESS / (double)tableKeys.size()) : Double.valueOf(COPY_PROGRESS / (double)tableKeys.size());
        List formKeys = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        DimensionValueSet masterKeys = new DimensionValueSet(dimValueSet);
        for (int i = 0; i < masterKeys.size(); ++i) {
            Object value = masterKeys.getValue(i);
            if (value == null || value.equals("")) {
                masterKeys.clearValue(masterKeys.getName(i));
            }
            if (masterKeys.getName(i) == null || !masterKeys.getName(i).equals("PROCESSKEY")) continue;
            masterKeys.clearValue(masterKeys.getName(i));
        }
        masterKeys.clearValue("VERSIONID");
        String fileKeys = "";
        ExecutorContext executorContext = new ExecutorContext(this.controller);
        DataModelDefinitionsCache tableCache = executorContext.getCache().getDataModelDefinitionsCache();
        for (String formKey : formKeys) {
            List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
            HashSet fieldSet = new HashSet();
            fieldSet.addAll(fieldKeys);
            List formTableKeys = this.controller.queryTableDefinesByFields(fieldSet).stream().filter(table -> table.getKind() == TableKind.TABLE_KIND_BIZDATA).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            ArrayList formTableRows = new ArrayList();
            for (int i = 0; i < formTableKeys.size(); ++i) {
                String tableKey = (String)formTableKeys.get(i);
                List allColumn = tableCache.getColumnModelFinder().getAllColumnModelsByTableKey(executorContext, tableKey);
                if (null == allColumn || allColumn.size() == 0) {
                    return null;
                }
                QueryEnvironment queryEnvironment = new QueryEnvironment();
                queryEnvironment.setFormSchemeKey(formSchemeKey);
                IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
                dataQuery.setFilterDataByAuthority(false);
                for (ColumnModelDefine column : allColumn) {
                    FieldDefine fieldDefine = tableCache.getFieldDefine(column);
                    dataQuery.addColumn(fieldDefine);
                }
                TableModelDefine tableModel = tableCache.getTableModel((ColumnModelDefine)allColumn.stream().findAny().get());
                dataQuery.setMainTable(tableModel.getName());
                dataQuery.setMasterKeys(masterKeys);
                IReadonlyTable executeReader = null;
                dataQuery.setIgnoreDefaultOrderBy(true);
                try {
                    executeReader = dataQuery.executeReader(executorContext);
                }
                catch (Exception e) {
                    Log.error((Exception)e);
                }
                List findFuzzyRows = executeReader.findFuzzyRows(masterKeys);
                ArrayList tableRows = new ArrayList();
                for (IDataRow iDataRow : findFuzzyRows) {
                    HashMap<String, String> row = new HashMap<String, String>();
                    for (ColumnModelDefine column : allColumn) {
                        FieldDefine fieldDefine = tableCache.getFieldDefine(column);
                        String value = iDataRow.getAsString(fieldDefine);
                        if (fieldDefine.getCode().equals("VERSION")) {
                            row.put(fieldDefine.getCode(), newVersion);
                            continue;
                        }
                        row.put(fieldDefine.getCode(), value);
                    }
                    tableRows.add(row);
                }
                HashMap map = new HashMap();
                map.put(formTableKeys.get(i), tableRows);
                formTableRows.add(map);
                if (null == iMonitor) continue;
                iMonitor.onProgress(process * (double)(i + 1));
                iMonitor.message("\u521b\u5efa\u7248\u672c, \u603b\u8fdb\u5ea6" + process * (double)(i + 1) * 100.0 + "%", (Object)this);
            }
            if (formTableRows.isEmpty()) continue;
            ObjectMapper mapper = new ObjectMapper();
            byte[] bytes = mapper.writeValueAsBytes(formTableRows);
            this.fileService.area("DataVer").uploadByKey(newVersion, newVersion + formKey, formKey, bytes);
        }
        return fileKeys;
    }

    @Override
    public void overwriteDefaultVersion(List<String> tableKeys, DimensionValueSet dimValueSet, String versionId, String defaultVersionId, IMonitor iMonitor, boolean isForOverwrite, String formSchemeKey) throws Exception {
        Double process = 0.0;
        process = isForOverwrite ? Double.valueOf(END_PROGRESS - DELETE_PROGRESS / (double)tableKeys.size()) : Double.valueOf(COPY_PROGRESS / (double)tableKeys.size());
        List formKeys = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        ExecutorContext executorContext = new ExecutorContext(this.controller);
        DataModelDefinitionsCache tableCache = executorContext.getCache().getDataModelDefinitionsCache();
        for (String formKey : formKeys) {
            List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
            HashSet fieldSet = new HashSet();
            fieldSet.addAll(fieldKeys);
            List fileList = this.fileInfoService.getFileInfoByGroup(formKey, "DataVer", FileStatus.AVAILABLE);
            ArrayList<FileInfo> tableFile = new ArrayList<FileInfo>();
            if (null == fileList || fileList.isEmpty()) continue;
            for (FileInfo item : fileList) {
                if (!item.getName().equals(versionId)) continue;
                tableFile.add(item);
            }
            if (!tableFile.isEmpty()) {
                for (FileInfo fileInfo : tableFile) {
                    byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
                    String result = null;
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream();){
                        out.write(bs);
                        result = new String(out.toByteArray());
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    List formList = (List)mapper.readValue(result, (TypeReference)new TypeReference<List<Object>>(){});
                    int i = 0;
                    for (Object object : formList) {
                        Map o = (Map)object;
                        for (Map.Entry table : o.entrySet()) {
                            TableDefine tableDefine = this.controller.queryTableDefine((String)table.getKey());
                            List allColumn = tableCache.getColumnModelFinder().getAllColumnModelsByTableKey(executorContext, (String)table.getKey());
                            if (null == allColumn || allColumn.size() == 0) continue;
                            QueryEnvironment queryEnvironment = new QueryEnvironment();
                            queryEnvironment.setFormSchemeKey(formSchemeKey);
                            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
                            dataQuery.setFilterDataByAuthority(false);
                            for (ColumnModelDefine column : allColumn) {
                                FieldDefine fieldDefine = tableCache.getFieldDefine(column);
                                dataQuery.addColumn(fieldDefine);
                            }
                            TableModelDefine tableModelDefine = tableCache.getTableModel((ColumnModelDefine)allColumn.stream().findAny().get());
                            dataQuery.setMainTable(tableModelDefine.getName());
                            dataQuery.setMasterKeys(dimValueSet);
                            IDataUpdator openForUpdate = dataQuery.openForUpdate(executorContext, true);
                            for (Map item : (List)table.getValue()) {
                                DimensionValueSet rowDimValSet = new DimensionValueSet(dimValueSet);
                                if (item.get("BIZKEYORDER") != null) {
                                    rowDimValSet.setValue("RECORDKEY", item.get("BIZKEYORDER"));
                                }
                                IDataRow dataRow = openForUpdate.addInsertedRow(rowDimValSet);
                                for (ColumnModelDefine column : allColumn) {
                                    FieldDefine field = tableCache.getFieldDefine(column);
                                    if (field.getValueType() == FieldValueType.FIELD_VALUE_DATE_VERSION) {
                                        dataRow.setValue(field, (Object)"00000000-0000-0000-0000-000000000000");
                                        continue;
                                    }
                                    if (null != item.get(field.getCode())) {
                                        String format;
                                        SimpleDateFormat sdf;
                                        if (field.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
                                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                format = sdf.format(new Date(Long.valueOf(String.valueOf(item.get(field.getCode())))));
                                            }
                                            catch (Exception e) {
                                                format = String.valueOf(item.get(field.getCode()));
                                            }
                                            dataRow.setValue(field, (Object)format);
                                            continue;
                                        }
                                        if (field.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            try {
                                                format = sdf.format(new Date(Long.valueOf(String.valueOf(item.get(field.getCode())))));
                                            }
                                            catch (Exception e) {
                                                format = String.valueOf(item.get(field.getCode()));
                                            }
                                            dataRow.setValue(field, (Object)format);
                                            continue;
                                        }
                                        dataRow.setValue(field, item.get(field.getCode()));
                                        continue;
                                    }
                                    dataRow.setValue(field, item.get(field.getCode()));
                                }
                            }
                            openForUpdate.commitChanges(true);
                            if (null == iMonitor) continue;
                            iMonitor.onProgress(process * (double)(i + 1));
                            iMonitor.message("\u521b\u5efa\u7248\u672c, \u603b\u8fdb\u5ea6" + process * (double)(i + 1) * 100.0 + "%", (Object)this);
                            ++i;
                        }
                    }
                }
            }
            if (null == iMonitor) continue;
            iMonitor.onProgress(1.0);
            iMonitor.message("\u521b\u5efa\u7248\u672c, \u603b\u8fdb\u5ea6100%", (Object)this);
        }
    }

    @Override
    public void overwriteDefaultVersionOfFormList(List<String> tableKeys, DimensionValueSet dimValueSet, String versionId, String defaultVersionId, IMonitor iMonitor, boolean isForOverwrite, List<String> formKeys, String formSchemeKey) throws Exception {
        Double process = 0.0;
        process = isForOverwrite ? Double.valueOf(END_PROGRESS - DELETE_PROGRESS / (double)tableKeys.size()) : Double.valueOf(COPY_PROGRESS / (double)tableKeys.size());
        ExecutorContext executorContext = new ExecutorContext(this.controller);
        DataModelDefinitionsCache tableCache = executorContext.getCache().getDataModelDefinitionsCache();
        for (String formKey : formKeys) {
            List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
            HashSet fieldSet = new HashSet();
            fieldSet.addAll(fieldKeys);
            List fileList = this.fileInfoService.getFileInfoByGroup(formKey, "DataVer", FileStatus.AVAILABLE);
            ArrayList<FileInfo> tableFile = new ArrayList<FileInfo>();
            if (null == fileList || fileList.isEmpty()) continue;
            for (FileInfo item : fileList) {
                if (!item.getName().equals(versionId)) continue;
                tableFile.add(item);
            }
            if (!tableFile.isEmpty()) {
                for (FileInfo fileInfo : tableFile) {
                    byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
                    String result = null;
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream();){
                        out.write(bs);
                        result = new String(out.toByteArray());
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    List formList = (List)mapper.readValue(result, (TypeReference)new TypeReference<List<Object>>(){});
                    int i = 0;
                    for (Object object : formList) {
                        Map o = (Map)object;
                        for (Map.Entry table : o.entrySet()) {
                            TableDefine tableDefine = this.controller.queryTableDefine((String)table.getKey());
                            List columns = tableCache.getColumnModelFinder().getAllColumnModelsByTableKey(executorContext, (String)table.getKey());
                            if (null == columns || columns.size() == 0) continue;
                            QueryEnvironment queryEnvironment = new QueryEnvironment();
                            queryEnvironment.setFormSchemeKey(formSchemeKey);
                            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
                            dataQuery.setFilterDataByAuthority(false);
                            for (ColumnModelDefine column : columns) {
                                FieldDefine fieldDefine = tableCache.getFieldDefine(column);
                                dataQuery.addColumn(fieldDefine);
                            }
                            TableModelDefine tableModel = tableCache.getTableModel((ColumnModelDefine)columns.stream().findAny().get());
                            dataQuery.setMainTable(tableModel.getName());
                            dataQuery.setMasterKeys(dimValueSet);
                            IDataUpdator openForUpdate = dataQuery.openForUpdate(executorContext, true);
                            for (Map item : (List)table.getValue()) {
                                DimensionValueSet rowDimValSet = new DimensionValueSet(dimValueSet);
                                if (item.get("BIZKEYORDER") != null) {
                                    rowDimValSet.setValue("RECORDKEY", item.get("BIZKEYORDER"));
                                }
                                IDataRow dataRow = openForUpdate.addInsertedRow(rowDimValSet);
                                for (ColumnModelDefine column : columns) {
                                    FieldDefine field = tableCache.getFieldDefine(column);
                                    if (field.getValueType() == FieldValueType.FIELD_VALUE_DATE_VERSION) {
                                        dataRow.setValue(field, (Object)"00000000-0000-0000-0000-000000000000");
                                        continue;
                                    }
                                    if (null != item.get(field.getCode())) {
                                        String format;
                                        SimpleDateFormat sdf;
                                        if (field.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
                                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                format = sdf.format(new Date(Long.valueOf(String.valueOf(item.get(field.getCode())))));
                                            }
                                            catch (Exception e) {
                                                format = String.valueOf(item.get(field.getCode()));
                                            }
                                            dataRow.setValue(field, (Object)format);
                                            continue;
                                        }
                                        if (field.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            try {
                                                format = sdf.format(new Date(Long.valueOf(String.valueOf(item.get(field.getCode())))));
                                            }
                                            catch (Exception e) {
                                                format = String.valueOf(item.get(field.getCode()));
                                            }
                                            dataRow.setValue(field, (Object)format);
                                            continue;
                                        }
                                        dataRow.setValue(field, item.get(field.getCode()));
                                        continue;
                                    }
                                    dataRow.setValue(field, item.get(field.getCode()));
                                }
                            }
                            openForUpdate.commitChanges(true);
                            if (null == iMonitor) continue;
                            iMonitor.onProgress(process * (double)(i + 1));
                            iMonitor.message("\u521b\u5efa\u7248\u672c, \u603b\u8fdb\u5ea6" + process * (double)(i + 1) * 100.0 + "%", (Object)this);
                            ++i;
                        }
                    }
                }
            }
            if (null == iMonitor) continue;
            iMonitor.onProgress(1.0);
            iMonitor.message("\u521b\u5efa\u7248\u672c, \u603b\u8fdb\u5ea6100%", (Object)this);
        }
    }
}

