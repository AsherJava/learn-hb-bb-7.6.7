/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.common.DataFile
 *  com.jiuqi.nr.data.common.service.DescRecorder
 *  com.jiuqi.nr.data.common.service.ExpSettings
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.ImpSettings
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.Result
 *  com.jiuqi.nr.data.common.service.StatisticalRecorder
 *  com.jiuqi.nr.data.common.service.TaskDataFactory
 *  com.jiuqi.nr.data.common.service.TransferContext
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 *  com.jiuqi.nr.fielddatacrud.spi.pp.FormParamProvider
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.data.text.service.impl;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.util.Version;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.common.DataFile;
import com.jiuqi.nr.data.common.service.DescRecorder;
import com.jiuqi.nr.data.common.service.ExpSettings;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.ImpSettings;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.Result;
import com.jiuqi.nr.data.common.service.StatisticalRecorder;
import com.jiuqi.nr.data.common.service.TaskDataFactory;
import com.jiuqi.nr.data.common.service.TransferContext;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import com.jiuqi.nr.data.text.DataFileSaveRes;
import com.jiuqi.nr.data.text.FieldDataSaveParam;
import com.jiuqi.nr.data.text.FieldFileParamBuilder;
import com.jiuqi.nr.data.text.IFieldFileParam;
import com.jiuqi.nr.data.text.api.IFieldDataFileServiceFactory;
import com.jiuqi.nr.data.text.param.FieldDataExpFileWriter;
import com.jiuqi.nr.data.text.param.FieldDataImpFileFinder;
import com.jiuqi.nr.data.text.param.FormDataResult;
import com.jiuqi.nr.data.text.service.ExpFieldDataService;
import com.jiuqi.nr.data.text.service.ImpFieldDataService;
import com.jiuqi.nr.data.text.service.impl.IParamDataFileProviderImpl;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import com.jiuqi.nr.fielddatacrud.spi.pp.FormParamProvider;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Order(value=20)
public class FormDataFactoryImpl
implements TaskDataFactory {
    private static final Logger logger = LoggerFactory.getLogger(FormDataFactoryImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFieldDataFileServiceFactory fieldDataFileServiceFactory;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private static final String FACTORY_CODE = "FORMDATA";
    private static final String FILE_NAME = "_F";

    public String getCode() {
        return FACTORY_CODE;
    }

    public String getName() {
        return "\u62a5\u8868\u6570\u636e";
    }

    public String getDescription() {
        return "";
    }

    public Version getVersion() {
        return new Version("1.0.0");
    }

    public int getWeight() {
        return 6;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exportTaskData(TransferContext context, FileWriter writer) {
        List<String> formKeys = context.getFormKeys();
        formKeys = this.checkFormKeys(context, formKeys);
        Map<String, FormDefine> formKeyToDefine = this.runTimeViewController.queryFormsById(formKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        IParamDataFileProviderImpl paramDataProvider = new IParamDataFileProviderImpl();
        if (context.getParamsMapping() != null) {
            paramDataProvider.setMapping(context.getParamsMapping());
        }
        IProgressMonitor progressMonitor = context.getProgressMonitor();
        try {
            progressMonitor.startTask("\u62a5\u8868\u6570\u636e\u5bfc\u51fa", formKeys.size());
            ExpSettings exportSettings = context.getExportSettings();
            StatisticalRecorder statisticalRecord = context.getStatisticalRecord(FACTORY_CODE);
            for (String formKey : formKeys) {
                List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formKey);
                FormDefine formDefine = formKeyToDefine.get(formKey);
                FormParamProvider formParamProvider = new FormParamProvider(context.getTaskKey(), formDefine);
                paramDataProvider.setParamProvider((ParamProvider)formParamProvider);
                ExpFieldDataService expFieldDataService = this.fieldDataFileServiceFactory.getExpFieldDataService(paramDataProvider);
                FieldDataExpFileWriter fileWriter = new FieldDataExpFileWriter(writer, formDefine.getFormCode());
                int count = 0;
                for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                    FieldFileParamBuilder fieldFileParamBuilder = FieldFileParamBuilder.create(context.getMasterKeys());
                    fieldFileParamBuilder.setExportFile(exportSettings.isExportAttachments());
                    fieldFileParamBuilder.setExpZip(false);
                    if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                        fieldFileParamBuilder.setExpFileName(formDefine.getFormCode());
                    } else {
                        fieldFileParamBuilder.setExpFileName(formDefine.getFormCode() + FILE_NAME + dataRegionDefine.getRegionTop());
                    }
                    fieldFileParamBuilder.setFileWriter(fileWriter);
                    if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST || dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
                        fieldFileParamBuilder.setExpBizKey(true);
                    }
                    List allLinksInForm = this.runTimeViewController.getAllLinksInRegion(dataRegionDefine.getKey());
                    boolean hasField = false;
                    for (DataLinkDefine dataLinkDefine : allLinksInForm) {
                        if (DataLinkType.DATA_LINK_TYPE_FIELD != dataLinkDefine.getType()) continue;
                        fieldFileParamBuilder.select(dataLinkDefine.getLinkExpression());
                        hasField = true;
                    }
                    String filterCondition = dataRegionDefine.getFilterCondition();
                    if (StringUtils.hasLength((String)filterCondition)) {
                        FormulaFilter formulaFilter = new FormulaFilter(filterCondition);
                        fieldFileParamBuilder.where((RowFilter)formulaFilter);
                    }
                    if (!hasField) {
                        logger.info("\u62a5\u8868 {}{} \u7684\u533a\u57df {}\u6ca1\u6709\u6307\u6807\uff0c\u5ffd\u7565\u5bfc\u51fa\u3002", formDefine.getTitle(), formDefine.getFormCode(), dataRegionDefine.getCode());
                        continue;
                    }
                    IFieldFileParam param = fieldFileParamBuilder.build();
                    try {
                        DataFile export = expFieldDataService.export(param);
                        if (export != null) {
                            export.close();
                        }
                        progressMonitor.stepIn();
                    }
                    catch (Exception e) {
                        logger.warn("\u62a5\u8868\uff1a{}\u7684\u533a\u57df\uff1a{} \u5bfc\u51fa\u5931\u8d25,\u8df3\u8fc7\u5bfc\u51fa", formDefine.getTitle(), dataRegionDefine.getCode(), e);
                    }
                    count += param.getExportCount();
                }
                statisticalRecord.formRecord(formDefine.getKey(), "\u5bfc\u51fa" + count + "\u6761\u6570\u636e");
            }
        }
        finally {
            progressMonitor.finishTask("\u62a5\u8868\u6570\u636e\u5bfc\u51fa");
        }
    }

    private List<String> checkFormKeys(TransferContext context, List<String> formKeys) {
        if (formKeys == null || formKeys.isEmpty()) {
            formKeys = new ArrayList<String>(this.runTimeViewController.queryAllFormKeysByFormScheme(context.getFormSchemeKey()));
        }
        return formKeys;
    }

    public void importTaskData(TransferContext context, FileFinder finder) {
        List<String> formKeys = context.getFormKeys();
        formKeys = this.checkFormKeys(context, formKeys);
        List formDefines = this.runTimeViewController.queryFormsById(formKeys);
        Map<String, FormDefine> formCode2Define = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, a -> a));
        IParamDataFileProviderImpl paramDataProvider = new IParamDataFileProviderImpl();
        ParamsMapping paramsMapping = context.getParamsMapping();
        paramDataProvider.setMapping(paramsMapping);
        FieldDataSaveParam saveParam = new FieldDataSaveParam();
        ImpSettings importSettings = context.getImportSettings();
        saveParam.setMode(importSettings.getImpMode(FACTORY_CODE));
        saveParam.setMasterKey(importSettings.getMasterKeys());
        saveParam.setFilterDim(importSettings.getFilterDims());
        saveParam.setCompletionDim(importSettings.getCompletionDims());
        String illegalDataImport = this.taskOptionController.getValue(context.getTaskKey(), "IllegalDataImport_2132");
        saveParam.setAllIllegalDataImport(illegalDataImport.equals("1"));
        IProgressMonitor progressMonitor = context.getProgressMonitor();
        DescRecorder descRecorder = context.getDescRecorder(FACTORY_CODE);
        StatisticalRecorder statisticalRecord = context.getStatisticalRecord(FACTORY_CODE);
        HashSet<String> successDw = new HashSet<String>();
        HashSet<String> failDw = new HashSet<String>();
        try {
            List fileEntries = finder.listFiles("");
            progressMonitor.startTask("\u62a5\u8868\u6570\u636e\u5bfc\u5165", fileEntries.size());
            for (FileEntry fileEntry : fileEntries) {
                String formCode = fileEntry.getFileName();
                FieldDataImpFileFinder fileFinder = new FieldDataImpFileFinder(finder, formCode);
                if (paramsMapping.tryFormCodeMap()) {
                    ArrayList<String> sourceFormCode = new ArrayList<String>();
                    sourceFormCode.add(formCode);
                    Map originFormCode = paramsMapping.getOriginFormCode(sourceFormCode);
                    String aimFormCode = (String)originFormCode.get(formCode);
                    FormDefine formDefine = formCode2Define.get(aimFormCode);
                    if (formDefine == null) continue;
                    FormParamProvider formParamProvider = new FormParamProvider(context.getTaskKey(), formDefine);
                    paramDataProvider.setParamProvider((ParamProvider)formParamProvider);
                    ImpFieldDataService impFieldDataService = this.fieldDataFileServiceFactory.getImpFieldDataService(paramDataProvider);
                    List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
                    HashMap<Integer, DataRegionDefine> regionTop2Define = new HashMap<Integer, DataRegionDefine>();
                    DataRegionDefine fixedRegionDefine = null;
                    for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                        if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                            regionTop2Define.put(dataRegionDefine.getRegionTop(), dataRegionDefine);
                            continue;
                        }
                        fixedRegionDefine = dataRegionDefine;
                    }
                    List regionData = finder.listFiles(formCode);
                    int count = 0;
                    for (FileEntry regionFileEntry : regionData) {
                        if (regionFileEntry.isDirectory()) continue;
                        ++count;
                    }
                    String[] desc = new String[count];
                    int index = 0;
                    Collection<Object> noAccessDw = Collections.emptyList();
                    String title = formDefine.getTitle();
                    for (FileEntry regionFileEntry : regionData) {
                        String regionName = regionFileEntry.getFileName();
                        if (regionName.equals("attachment")) continue;
                        fileFinder.setCurFileName(regionName);
                        StringBuilder regionDesc = new StringBuilder();
                        if (regionName.contains(FILE_NAME)) {
                            int top = Integer.parseInt(regionName.substring(regionName.length() - 1));
                            DataRegionDefine dataRegionDefine = (DataRegionDefine)regionTop2Define.get(top);
                            if (dataRegionDefine == null) {
                                logger.info("\u62a5\u8868{}\u7684\u6570\u636e\u6587\u4ef6{}\u672a\u5339\u914d\u4e0a\uff0c\u5ffd\u7565\u5bfc\u5165\u3002", (Object)formDefine.getTitle(), (Object)regionName);
                                continue;
                            }
                            String filterCondition = dataRegionDefine.getFilterCondition();
                            List<DataField> allFiledInRegion = this.getAllFiledInRegion(dataRegionDefine.getKey());
                            if (allFiledInRegion == null) {
                                regionDesc.append("\u6d6e\u52a8\u533a\u57df\u65e0\u6307\u6807\u6620\u5c04\u8df3\u8fc7\u5bfc\u5165\u3002");
                                logger.info("\u6d6e\u52a8\u533a\u57df{}\u65e0\u6307\u6807\u6620\u5c04\u8df3\u8fc7\u5bfc\u5165\u3002", (Object)regionName);
                                desc[index++] = regionDesc.toString();
                                continue;
                            }
                            saveParam.setDataFieldsInAimRegion(allFiledInRegion);
                            saveParam.setFilterCondition(filterCondition);
                            regionDesc.append("\u6d6e\u52a8\u533a\u57df").append(top).append("\u5bfc\u5165");
                        } else {
                            if (fixedRegionDefine == null) {
                                logger.info("\u62a5\u8868{}\u7684\u6570\u636e\u6587\u4ef6{}\u672a\u5339\u914d\u4e0a\uff0c\u5ffd\u7565\u5bfc\u5165\u3002", (Object)formDefine.getTitle(), (Object)regionName);
                                continue;
                            }
                            List<DataField> allFiledInRegion = this.getAllFiledInRegion(fixedRegionDefine.getKey());
                            if (allFiledInRegion == null) {
                                regionDesc.append("\u56fa\u5b9a\u533a\u57df\u65e0\u6307\u6807\u6620\u5c04\u8df3\u8fc7\u5bfc\u5165\u3002");
                                logger.info("\u56fa\u5b9a\u533a\u57df{}\u65e0\u6307\u6807\u6620\u5c04\u8df3\u8fc7\u5bfc\u5165\u3002", (Object)regionName);
                                desc[index++] = regionDesc.toString();
                                continue;
                            }
                            saveParam.setDataFieldsInAimRegion(allFiledInRegion);
                            regionDesc.append("\u56fa\u5b9a\u533a\u57df\u5bfc\u5165");
                        }
                        DataFileSaveRes dataFileSaveRes = impFieldDataService.importData(saveParam, fileFinder);
                        boolean hasAddRowFail = dataFileSaveRes.isAddFail();
                        if (hasAddRowFail) {
                            regionDesc.append("\u65f6\u6dfb\u52a0\u6570\u636e\u884c\u5931\u8d25\uff01");
                        } else {
                            regionDesc.append(dataFileSaveRes.getImportCount()).append("\u6761\u6570\u636e\u3002");
                        }
                        successDw.addAll(dataFileSaveRes.getSaveDw());
                        failDw.addAll(dataFileSaveRes.getFailDw());
                        failDw.addAll(dataFileSaveRes.getNoPermissionDw());
                        Collection<String> failDwByRegion = dataFileSaveRes.getFailDw();
                        if (CollectionUtils.isEmpty(noAccessDw)) {
                            noAccessDw = dataFileSaveRes.getNoPermissionDw();
                        }
                        if (!CollectionUtils.isEmpty(failDwByRegion)) {
                            regionDesc.append("\u5b58\u5728\u5bfc\u5165\u5931\u8d25\u5355\u4f4d\u3002");
                        }
                        desc[index++] = regionDesc.toString();
                        for (String fail : failDwByRegion) {
                            ReturnRes failMessage = dataFileSaveRes.getFailMessage(fail);
                            List messages = failMessage.getMessages();
                            String message = CollectionUtils.isEmpty(messages) ? failMessage.getMessage() : String.join((CharSequence)",", messages);
                            descRecorder.addDesc(fail, "\u8868\u5355:" + title + " \u5bfc\u5165\u9519\u8bef:" + message);
                        }
                    }
                    for (String noAccess : noAccessDw) {
                        descRecorder.addDesc(noAccess, "\u5bf9\u8868\u5355:" + title + " \u65e0\u6570\u636e\u5199\u6743\u9650");
                    }
                    StringBuilder total = new StringBuilder();
                    for (String s : desc) {
                        total.append(s);
                    }
                    statisticalRecord.formRecord(formDefine.getKey(), total.toString());
                }
                progressMonitor.stepIn();
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u62a5\u8868\u6570\u636e\u5bfc\u5165\u5931\u8d25", e);
        }
        finally {
            progressMonitor.finishTask("\u62a5\u8868\u6570\u636e\u5bfc\u5165");
        }
        successDw.removeAll(failDw);
        int allDw = successDw.size() + failDw.size();
        FormDataResult result = new FormDataResult("\u5171\u5bfc\u5165" + allDw + "\u5bb6\u5355\u4f4d\uff0c\u5176\u4e2d" + failDw.size() + "\u5bb6\u5bfc\u5165\u5931\u8d25\u3002", new ArrayList<String>(failDw));
        context.setResult(FACTORY_CODE, (Result)result);
    }

    private List<DataField> getAllFiledInRegion(String key) {
        List allLinksInRegion = this.runTimeViewController.getAllLinksInRegion(key);
        ArrayList<String> dataFieldKeys = new ArrayList<String>();
        for (DataLinkDefine dataLinkDefine : allLinksInRegion) {
            if (dataLinkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD) continue;
            dataFieldKeys.add(dataLinkDefine.getLinkExpression());
        }
        if (CollectionUtils.isEmpty(dataFieldKeys)) {
            return null;
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(dataFieldKeys);
        List dataFieldByTable = this.runtimeDataSchemeService.getDataFieldByTable(((DataField)dataFields.get(0)).getDataTableKey());
        List collect = dataFieldByTable.stream().filter(a -> a.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD).collect(Collectors.toList());
        dataFields.addAll(collect);
        return dataFields;
    }
}

