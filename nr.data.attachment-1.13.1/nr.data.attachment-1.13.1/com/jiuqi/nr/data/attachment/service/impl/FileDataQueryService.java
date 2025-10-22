/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext
 *  com.jiuqi.nr.attachment.input.FileUploadInfo
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.exception.DataCommonException
 *  com.jiuqi.nr.data.common.exception.ErrorCode
 *  com.jiuqi.nr.data.common.param.CommonImportDetails
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.attachment.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.data.attachment.exception.AttachmentErrorCode;
import com.jiuqi.nr.data.attachment.param.FileDataRow;
import com.jiuqi.nr.data.attachment.param.FileFilter;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.exception.DataCommonException;
import com.jiuqi.nr.data.common.exception.ErrorCode;
import com.jiuqi.nr.data.common.param.CommonImportDetails;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileDataQueryService {
    private static final Logger log = LoggerFactory.getLogger(FileDataQueryService.class);
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private AttachmentIOService attachmentIOService;
    @Autowired
    private IEntityMetaService entityMetaService;

    public List<FileDataRow> queryFileData(List<DataRegionDefine> regions, DimensionCollection dimensions, FormSchemeDefine formScheme, FileFilter filter, DataServiceLogHelper logHelper) {
        List<String> fieldsInForm1;
        if (regions == null || regions.isEmpty() || dimensions == null || formScheme == null) {
            throw new IllegalArgumentException("\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ArrayList<String> fieldsInForm = new ArrayList<String>();
        if (filter != null && (fieldsInForm1 = filter.getFieldsInForm(regions.get(0).getFormKey())) != null) {
            fieldsInForm.addAll(fieldsInForm1);
        }
        ArrayList<FileDataRow> fileDataRows = new ArrayList<FileDataRow>();
        FormDefine formDefine = this.runTimeViewController.queryFormById(regions.get(0).getFormKey());
        for (DataRegionDefine dataRegionDefine : regions) {
            List<FieldDefine> filesDefine = this.queryFilesDefine(fieldsInForm, dataRegionDefine);
            if (filesDefine == null || filesDefine.isEmpty()) continue;
            QueryEnvironment environment = new QueryEnvironment();
            environment.setRegionKey(dataRegionDefine.getKey());
            environment.setFormCode(formDefine.getFormCode());
            environment.setFormKey(dataRegionDefine.getFormKey());
            environment.setFormSchemeKey(formScheme.getKey());
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(environment);
            for (FieldDefine it : filesDefine) {
                dataQuery.addColumn(it);
            }
            dataQuery.setDefaultGroupName(formDefine.getFormCode());
            DimensionValueSet varDim = dimensions.combineWithoutVarDim();
            dataQuery.setMasterKeys(varDim);
            String filterCondition = dataRegionDefine.getFilterCondition();
            if (filterCondition != null && filterCondition.length() > 0) {
                dataQuery.setRowFilter(filterCondition);
            }
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment1 = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formScheme.getKey());
            executorContext.setEnv((IFmlExecEnvironment)environment1);
            executorContext.setJQReportModel(true);
            try {
                IReadonlyTable dataTable = dataQuery.executeReader(executorContext);
                int count = dataTable.getCount();
                for (int i = 0; i < count; ++i) {
                    IDataRow item = dataTable.getItem(i);
                    DimensionValueSet rowKeys = item.getRowKeys();
                    StringBuilder dimNameList = new StringBuilder();
                    StringBuilder dimValueList = new StringBuilder();
                    for (int j = 0; j < rowKeys.size(); ++j) {
                        dimNameList.append(rowKeys.getName(j)).append(";");
                        dimValueList.append(rowKeys.getValue(j)).append(";");
                    }
                    for (FieldDefine colum : filesDefine) {
                        String fileGroup = item.getAsString(colum);
                        FileDataRow fileDataRow = new FileDataRow(dimNameList.toString(), dimValueList.toString(), formDefine.getKey(), formDefine.getFormCode(), colum.getKey(), colum.getCode(), fileGroup, null);
                        fileDataRows.add(fileDataRow);
                    }
                }
            }
            catch (Exception e) {
                log.error("\u67e5\u8be2\u9644\u4ef6\u6570\u636e\u5f02\u5e38\uff1a{}", (Object)e.getMessage());
                logHelper.error(formScheme.getTaskKey(), null, "\u6570\u636e\u670d\u52a1-\u9644\u4ef6\u5bfc\u51fa", e.getMessage());
            }
        }
        return fileDataRows;
    }

    private List<FieldDefine> queryFilesDefine(List<String> fieldsInForm, DataRegionDefine dataRegionDefine) {
        List fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
        if (fieldsInForm != null && !fieldsInForm.isEmpty()) {
            fieldKeysInRegion = fieldKeysInRegion.stream().filter(key -> fieldsInForm.contains(key)).collect(Collectors.toList());
        }
        List fieldDefinesInRange = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)fieldKeysInRegion);
        ArrayList<FieldDefine> filesFieldDefine = new ArrayList<FieldDefine>();
        if (fieldDefinesInRange != null && !fieldDefinesInRange.isEmpty()) {
            for (FieldDefine fieldDefine : fieldDefinesInRange) {
                if (!fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE) && !fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) continue;
                filesFieldDefine.add(fieldDefine);
            }
        }
        return filesFieldDefine;
    }

    private String queryDataSchemeKey(String formKey) {
        List fieldDefinesInRange = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInForm(formKey));
        try {
            List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(((FieldDefine)fieldDefinesInRange.get(0)).getOwnerTableKey());
            if (deployInfoByDataTableKey != null && !deployInfoByDataTableKey.isEmpty() && deployInfoByDataTableKey.get(0) != null) {
                return ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getDataSchemeKey();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public void updateGroupKeys(List<FileDataRow> list, FormDefine formDefine, String formSchemeKey, List<DataRegionDefine> regions, String filePath, CommonParams commonParams, CommonMessage message) {
        List details = (List)message.getDetail();
        HashMap fieldRowMap = new HashMap();
        for (FileDataRow row : list) {
            if (fieldRowMap.containsKey(row.getFieldCode())) {
                ((List)fieldRowMap.get(row.getFieldCode())).add(row);
                continue;
            }
            ArrayList<FileDataRow> fieldRow = new ArrayList<FileDataRow>();
            fieldRow.add(row);
            fieldRowMap.put(row.getFieldCode(), fieldRow);
        }
        String queryDataSchemeKey = this.queryDataSchemeKey(formDefine.getKey());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String taskKey = formScheme.getTaskKey();
        String dw = formScheme.getDw();
        String dwDimName = this.entityMetaService.getDimensionName(dw);
        for (DataRegionDefine dataRegionDefine : regions) {
            List<FieldDefine> allFields = this.queryFilesDefine(null, dataRegionDefine);
            TableDefine tableDef = null;
            try {
                tableDef = this.dataDefinitionRuntimeController.queryTableDefine(allFields.get(0).getOwnerTableKey());
            }
            catch (Exception e1) {
                log.error(e1.getMessage());
            }
            if (tableDef == null) {
                CommonImportDetails cid = new CommonImportDetails(null, formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u533a\u57df\u5b58\u50a8\u8868\u5b9a\u4e49\u672a\u627e\u5230 ,\u8df3\u8fc7\u5bfc\u5165\u8be5\u533a\u57df:" + dataRegionDefine.getTitle() + formDefine.getFormCode() + dataRegionDefine.getRegionTop());
                details.add(cid);
                continue;
            }
            String bizKeyFields = tableDef.getBizKeyFieldsStr();
            ArrayList<FieldDefine> bizKeyFieldDef = new ArrayList<FieldDefine>();
            try {
                List allFieldList = this.dataDefinitionRuntimeController.getAllFieldsInTable(tableDef.getKey());
                for (FieldDefine fieldDefine : allFieldList) {
                    if (!bizKeyFields.contains(fieldDefine.getKey())) continue;
                    bizKeyFieldDef.add(fieldDefine);
                }
            }
            catch (Exception e1) {
                log.error(e1.getMessage());
            }
            ArrayList filesDefine = new ArrayList();
            Map dimMap = allFields.stream().filter(item -> fieldRowMap.containsKey(item.getCode())).peek(filesDefine::add).flatMap(item -> ((List)fieldRowMap.get(item.getCode())).stream()).collect(Collectors.groupingBy(FileDataRow::getDimNameList, Collectors.mapping(FileDataRow::getDimValueList, Collectors.toSet())));
            if (filesDefine == null || filesDefine.isEmpty()) continue;
            ParamsMapping mapping = null;
            if (commonParams != null && commonParams.getMapping() != null) {
                mapping = commonParams.getMapping();
            }
            if (dimMap.size() > 1) {
                CommonImportDetails cid = new CommonImportDetails(null, formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u533a\u57df\u7ef4\u5ea6\u53c2\u6570\u4e0d\u4e00\u81f4,\u8df3\u8fc7\u5bfc\u5165\u8be5\u533a\u57df" + dataRegionDefine.getTitle());
                details.add(cid);
                continue;
            }
            Iterator<String> iterator = dimMap.keySet().iterator();
            String dimInCsvNames = iterator.next();
            String[] csvDims = dimInCsvNames.split(";");
            ArrayList dimValus = new ArrayList();
            Set dimValueSet = dimMap.get(dimInCsvNames);
            for (String value : dimValueSet) {
                String[] csvValues = value.split(";");
                for (int i = 0; i < csvValues.length; ++i) {
                    if (csvValues[i] == null) continue;
                    if (dimValus.size() > i) {
                        ((Set)dimValus.get(i)).add(csvValues[i]);
                        continue;
                    }
                    HashSet<String> set = new HashSet<String>();
                    set.add(csvValues[i]);
                    dimValus.add(set);
                }
            }
            DimensionValueSet varDim = new DimensionValueSet();
            for (int i = 0; i < csvDims.length; ++i) {
                if (mapping != null && csvDims[i].equals(dwDimName)) {
                    ArrayList arrayList = new ArrayList((Collection)dimValus.get(i));
                    Map originOrgCode = mapping.getOriginOrgCode(arrayList);
                    ArrayList org = new ArrayList();
                    for (String string : arrayList) {
                        org.add(originOrgCode.get(string));
                    }
                    varDim.setValue(csvDims[i], org);
                    continue;
                }
                varDim.setValue(csvDims[i], new ArrayList((Collection)dimValus.get(i)));
            }
            QueryEnvironment environment = new QueryEnvironment();
            environment.setRegionKey(dataRegionDefine.getKey());
            environment.setFormCode(formDefine.getFormCode());
            environment.setFormKey(dataRegionDefine.getFormKey());
            environment.setFormSchemeKey(formSchemeKey);
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(environment);
            for (FieldDefine it : filesDefine) {
                dataQuery.addColumn(it);
            }
            dataQuery.setDefaultGroupName(formDefine.getFormCode());
            dataQuery.setMasterKeys(varDim);
            String filterCondition = dataRegionDefine.getFilterCondition();
            if (filterCondition != null && filterCondition.length() > 0) {
                dataQuery.setRowFilter(filterCondition);
            }
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment1 = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
            executorContext.setEnv((IFmlExecEnvironment)environment1);
            executorContext.setJQReportModel(true);
            try {
                IReadonlyTable dataTable = dataQuery.executeReader(executorContext);
                int count = dataTable.getCount();
                for (int i = 0; i < count; ++i) {
                    IDataRow item2 = dataTable.getItem(i);
                    DimensionValueSet rowKeys = item2.getRowKeys();
                    for (FieldDefine colum : filesDefine) {
                        String fileGroup = item2.getAsString(colum);
                        List fieldRows = (List)fieldRowMap.get(colum.getCode());
                        ArrayList<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
                        for (FileDataRow fileDataRow : fieldRows) {
                            String fileKey;
                            String[] split = fileDataRow.getDimValueList().split(";");
                            boolean currentRow = false;
                            if (!(currentRow = this.matchDims(csvDims, rowKeys, split, currentRow, mapping, dwDimName))) continue;
                            fileDataRow.setFileGroup(fileGroup);
                            log.info("Find . \u66ff\u6362groupKey:{}", (Object)fileGroup);
                            if (fileGroup == null || (fileKey = fileDataRow.getFileKey()) == null) continue;
                            File dir = new File(FilenameUtils.normalize(filePath.endsWith("/") || filePath.endsWith("\\") ? filePath + DigestUtils.md5Hex(fileKey) : filePath + "/" + DigestUtils.md5Hex(fileKey)));
                            if (dir.exists()) {
                                byte[] buffer;
                                String[] files = dir.list();
                                File file = null;
                                if (files.length > 0) {
                                    file = new File(dir.getAbsolutePath() + "/" + files[0]);
                                }
                                if ((buffer = this.getBytes(file)) != null) {
                                    this.buildFileUpload(fileUploadInfos, fileKey, file, buffer);
                                    continue;
                                }
                                DimensionCombinationBuilder builder = new DimensionCombinationBuilder(rowKeys);
                                CommonImportDetails cid = new CommonImportDetails(builder.getCombination(), formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u4e0d\u5bfc\u5165");
                                details.add(cid);
                                continue;
                            }
                            DimensionCombinationBuilder builder = new DimensionCombinationBuilder(rowKeys);
                            CommonImportDetails cid = new CommonImportDetails(builder.getCombination(), formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u4e0d\u5bfc\u5165");
                            details.add(cid);
                        }
                        this.uploadColumsFile(formDefine, formSchemeKey, queryDataSchemeKey, taskKey, rowKeys, colum, fileGroup, fileUploadInfos);
                    }
                }
            }
            catch (Exception e) {
                log.error("\u67e5\u8be2\u9644\u4ef6\u6570\u636e\u5f02\u5e38\uff1a{}", (Object)e.getMessage());
                throw new DataCommonException((ErrorCode)AttachmentErrorCode.ATTA_QUERY_ERROR);
            }
        }
    }

    private boolean matchDims(String[] csvDims, DimensionValueSet rowKeys, String[] split, boolean currentRow, ParamsMapping mapping, String dwDimName) {
        for (int j = 0; j < split.length; ++j) {
            if (split[j] == null) continue;
            if (mapping != null && rowKeys.getName(j).equals(dwDimName)) {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(split[j]);
                Map originOrgCode = mapping.getOriginOrgCode(arrayList);
                if (rowKeys.getValue(csvDims[j]).equals(originOrgCode.get(split[j]))) {
                    currentRow = true;
                    continue;
                }
                currentRow = false;
                continue;
            }
            currentRow = rowKeys.getValue(csvDims[j]).equals(split[j]);
        }
        return currentRow;
    }

    private void buildFileUpload(List<FileUploadInfo> fileUploadInfos, String fileKey, File file, byte[] buffer) {
        FileUploadInfo fui = new FileUploadInfo();
        fui.setCovered(false);
        fui.setFile((InputStream)new ByteArrayInputStream(buffer));
        fui.setName(file.getName());
        fui.setFileKey(fileKey);
        fileUploadInfos.add(fui);
    }

    private void uploadColumsFile(FormDefine formDefine, String formSchemeKey, String queryDataSchemeKey, String taskKey, DimensionValueSet rowKeys, FieldDefine colum, String fileGroup, List<FileUploadInfo> fileUploadInfos) {
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(rowKeys);
        DimensionCombination dimensionCombination = builder.getCombination();
        FileUploadByGroupKeyContext fileUploadByGroupKeyContext = new FileUploadByGroupKeyContext(colum.getKey(), fileGroup, fileUploadInfos, queryDataSchemeKey, taskKey, dimensionCombination, formSchemeKey, formDefine.getKey());
        this.attachmentIOService.uploadByGroup(fileUploadByGroupKeyContext);
    }

    private byte[] getBytes(File file) {
        byte[] buffer = null;
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);){
            int n;
            byte[] b = new byte[1000];
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e) {
            log.info("\u8bfb\u53d6\u7684\u9644\u4ef6\u6ca1\u6709\u6587\u4ef6\u4fe1\u606f,\u8df3\u8fc7\u8be5\u9644\u4ef6\u5bfc\u5165:{}", (Object)e.getMessage());
        }
        catch (IOException e) {
            log.info("\u8bfb\u53d6\u7684\u9644\u4ef6\u6ca1\u6709\u6587\u4ef6\u4fe1\u606f,\u8df3\u8fc7\u8be5\u9644\u4ef6\u5bfc\u5165:{}", (Object)e.getMessage());
        }
        return buffer;
    }

    private FieldDefine getUnitFieldDefine(List<FieldDefine> bizKeyFieldDef) {
        FieldDefine unitFieldDefine = null;
        for (int i = 0; i < bizKeyFieldDef.size(); ++i) {
            if (!bizKeyFieldDef.get(i).getCode().equals("MDCODE")) continue;
            unitFieldDefine = bizKeyFieldDef.get(i);
        }
        if (unitFieldDefine != null) {
            return unitFieldDefine;
        }
        return null;
    }
}

