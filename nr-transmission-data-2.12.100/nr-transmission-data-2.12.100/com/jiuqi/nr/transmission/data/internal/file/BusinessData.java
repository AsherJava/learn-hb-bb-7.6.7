/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.data.common.DataFile
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.text.DataFileSaveRes
 *  com.jiuqi.nr.data.text.FieldDataSaveParam
 *  com.jiuqi.nr.data.text.FieldFileParamBuilder
 *  com.jiuqi.nr.data.text.IFieldFileParam
 *  com.jiuqi.nr.data.text.api.IFieldDataFileServiceFactory
 *  com.jiuqi.nr.data.text.service.ExpFieldDataService
 *  com.jiuqi.nr.data.text.service.ImpFieldDataService
 *  com.jiuqi.nr.data.text.service.impl.DataFileImpl
 *  com.jiuqi.nr.data.text.service.impl.IParamDataFileProviderImpl
 *  com.jiuqi.nr.data.text.spi.IParamDataFileProvider
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 *  com.jiuqi.nr.io.params.base.CSVRange
 *  com.jiuqi.nr.io.params.base.CSVRange$FormRange
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ImportInformations
 *  com.jiuqi.nr.io.service.DataFileImportService
 *  com.jiuqi.nr.io.service.FileExportService
 *  com.jiuqi.nr.io.service.IoQualifier
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 *  com.jiuqi.nr.io.service.impl.IoQualifierImpl
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.data.common.DataFile;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.text.DataFileSaveRes;
import com.jiuqi.nr.data.text.FieldDataSaveParam;
import com.jiuqi.nr.data.text.FieldFileParamBuilder;
import com.jiuqi.nr.data.text.IFieldFileParam;
import com.jiuqi.nr.data.text.api.IFieldDataFileServiceFactory;
import com.jiuqi.nr.data.text.service.ExpFieldDataService;
import com.jiuqi.nr.data.text.service.ImpFieldDataService;
import com.jiuqi.nr.data.text.service.impl.DataFileImpl;
import com.jiuqi.nr.data.text.service.impl.IParamDataFileProviderImpl;
import com.jiuqi.nr.data.text.spi.IParamDataFileProvider;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import com.jiuqi.nr.io.params.base.CSVRange;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.service.DataFileImportService;
import com.jiuqi.nr.io.service.FileExportService;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.io.service.impl.IoQualifierImpl;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.common.FileHelper;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.TransmissionExportType;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.internal.file.ParamProviderNrdImpl;
import com.jiuqi.nr.transmission.data.internal.file.ParamsExportMappingNrdImpl;
import com.jiuqi.nr.transmission.data.internal.file.ParamsImportMappingNrdImpl;
import com.jiuqi.nr.transmission.data.intf.ContextExpendParam;
import com.jiuqi.nr.transmission.data.intf.DataImportMessage;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class BusinessData
implements ITransmissionDataGather {
    private static final Logger logger = LoggerFactory.getLogger(BusinessData.class);
    private static final String ADJUST = "ADJUST";
    private static final String DATA_FORM_SCHEME = "FORMSCHEME";
    private static final String FORM_REGION_SEG = ";";
    private static final String FORM_REGION_FILE_ZIP = ".zip";
    @Autowired
    @Qualifier(value="CsvExportServiceImpl")
    private FileExportService fileExportService;
    @Autowired
    private DataFileImportService dataFileImportService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Autowired(required=false)
    private MultistageUnitReplace multistageUnitReplaceImpl;
    @Autowired(required=false)
    private IoQualifier ioQualifier;
    @Autowired
    private IFieldDataFileServiceFactory fieldDataFileServiceFactory;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getCode() {
        return "BUSINESS_DATA";
    }

    @Override
    public String getTitle() {
        return "\u62a5\u8868\u6570\u636e";
    }

    @Override
    public DataImportResult dataImport(InputStream inputStream, ITransmissionContext context) throws Exception {
        List<String> forms;
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        DataImportResult importBusinessDataResult = new DataImportResult();
        if (StringUtils.hasText(context.getFmdmForm()) && (forms = context.getExecuteParam().getForms()).size() == 1 && forms.get(0).equals(context.getFmdmForm())) {
            ILogHelper logHelper = context.getLogHelper();
            logHelper.appendLog(MultilingualLog.businessDataImportMessage(1, ""));
            importBusinessDataResult.setLog("\u6ca1\u6709\u62a5\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165");
            monitor.finish("\u6ca1\u6709\u62a5\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165", (Object)"\u6ca1\u6709\u62a5\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165");
            return importBusinessDataResult;
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u5f00\u59cb\u88c5\u5165\u62a5\u8868\u6570\u636e");
        monitor.progressAndMessage(0.0, "\u5f00\u59cb\u6267\u884c\u62a5\u8868\u6570\u636e\u88c5\u5165");
        Map<String, ZipUtils.ZipSubFile> zipFiles = ZipUtils.unZip((InputStream)inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFilePath, f -> f));
        importBusinessDataResult = this.importBusinessData(context, zipFiles);
        monitor.finish(importBusinessDataResult.getLog() + "  \u62a5\u8868\u6570\u636e\u88c5\u5165\u5b8c\u6210", (Object)importBusinessDataResult);
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u88c5\u5165\u62a5\u8868\u6570\u636e\u5b8c\u6210");
        Utils.addSyncResult(context.getDataImportResult(), importBusinessDataResult);
        return importBusinessDataResult;
    }

    private DataImportResult importBusinessData(ITransmissionContext transmissionContext, Map<String, ZipUtils.ZipSubFile> zipFiles) throws Exception {
        boolean isNrd = transmissionContext.getContextExpendParam().getIsNrd();
        if (isNrd) {
            return this.doImportNrd(transmissionContext, zipFiles);
        }
        return this.oldImportBusinessData(transmissionContext, zipFiles);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataImportResult doImportNrd(ITransmissionContext transmissionContext, Map<String, ZipUtils.ZipSubFile> zipFiles) throws Exception {
        DataImportResult importResult = new DataImportResult();
        AsyncTaskMonitor monitor = transmissionContext.getTransmissionMonitor();
        ContextExpendParam contextExpendParam = transmissionContext.getContextExpendParam();
        IExecuteParam executeParam = transmissionContext.getExecuteParam();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(executeParam.getTaskKey());
        Map<String, FormDefine> formCodeToKeyMap = this.runTimeViewController.queryFormsById(executeParam.getForms()).stream().collect(Collectors.toMap(FormDefine::getFormCode, a -> a, (k1, k2) -> k1));
        HashMap<String, List<ImportInformations>> formWithErrorObj = new HashMap<String, List<ImportInformations>>();
        IParamDataFileProviderImpl paramDataProvider = new IParamDataFileProviderImpl();
        if (transmissionContext.getMappingImportParam() != null) {
            ParamsImportMappingNrdImpl mappingParam = new ParamsImportMappingNrdImpl(transmissionContext.getMappingImportParam());
            paramDataProvider.setMapping((ParamsMapping)mappingParam);
        }
        ParamProviderNrdImpl paramProvider = new ParamProviderNrdImpl(this.runtimeDataSchemeService, this.runTimeViewController, executeParam.getTaskKey(), executeParam.getFormSchemeKey());
        paramDataProvider.setParamProvider((ParamProvider)paramProvider);
        ImpFieldDataService impFieldDataService = this.fieldDataFileServiceFactory.getImpFieldDataService((IParamDataFileProvider)paramDataProvider);
        FieldDataSaveParam param = new FieldDataSaveParam();
        param.setMode(contextExpendParam.getMode());
        DimensionCollection collection = this.dimensionBuildUtil.getDimensionCollection(contextExpendParam.getDimensionValueSetWithAllDim(), executeParam.getFormSchemeKey());
        if (!CollectionUtils.isEmpty(contextExpendParam.getVariables())) {
            param.setVariables(contextExpendParam.getVariables());
        }
        param.setMasterKey(collection);
        String tempPath = ZipUtils.newTempDir();
        try {
            int j = 0;
            for (Map.Entry<String, ZipUtils.ZipSubFile> stringZipSubFileEntry : zipFiles.entrySet()) {
                File file;
                block13: {
                    ZipUtils.ZipSubFile subFile = stringZipSubFileEntry.getValue();
                    file = FileHelper.getTempFile(subFile, tempPath);
                    DataFileImpl dataFileImpl = new DataFileImpl();
                    dataFileImpl.setFile(file);
                    logger.info("\u5f00\u59cb\u5bfc\u5165{}\u7684\u6570\u636e", (Object)file.getName());
                    monitor.progressAndMessage((double)j * (0.9 / (double)zipFiles.size()), "\u62a5\u8868\u6570\u636e\u5f00\u59cb\u88c5\u5165 " + file.getName() + "\u7684\u6570\u636e");
                    String name = file.getName();
                    int segIndex = name.indexOf(FORM_REGION_SEG);
                    String formCode = name.substring(0, segIndex);
                    FormDefine formDefine = formCodeToKeyMap.get(formCode);
                    try {
                        DataFileSaveRes dataFileSaveRes = impFieldDataService.importData(param, (DataFile)dataFileImpl);
                        if (dataFileSaveRes == null || CollectionUtils.isEmpty(dataFileSaveRes.getFailMessages())) break block13;
                        importResult.setSyncErrorNumInc();
                        int i = 0;
                        for (Map.Entry unitError : dataFileSaveRes.getFailMessages().entrySet()) {
                            if (i <= 4) {
                                StringBuilder errorMessage = new StringBuilder("\u5355\u4f4d ").append((String)unitError.getKey()).append("\u7684\u5931\u8d25\u539f\u56e0\uff1a\u533a\u57df ").append(file.getName()).append(((ReturnRes)unitError.getValue()).getMessage());
                                ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), errorMessage.toString(), (String)unitError.getKey());
                                formWithErrorObj.computeIfAbsent(importInformations.getFormKey(), key -> new ArrayList()).add(importInformations);
                                ++i;
                                continue;
                            }
                            break;
                        }
                    }
                    catch (Exception e) {
                        String errorMessage = e.getMessage();
                        if (e instanceof NullPointerException || e.getMessage().contains("NUll") || e.getMessage().contains("Null") || e.getMessage().contains("null")) {
                            errorMessage = "\u672a\u77e5\u5f02\u5e38";
                        }
                        ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), errorMessage, "-");
                        formWithErrorObj.computeIfAbsent(importInformations.getFormKey(), key -> new ArrayList()).add(importInformations);
                        logger.error("\u62a5\u8868" + formDefine.getTitle() + "\u6570\u636e\u88c5\u5165\u533a\u57df" + file.getName() + "\u7684\u6570\u636e\u51fa\u73b0\u5f02\u5e38", e);
                    }
                }
                monitor.progressAndMessage((double)(++j) * (0.9 / (double)zipFiles.size()), "\u62a5\u8868\u6570\u636e\u88c5\u5165" + file.getName() + "\u7684\u6570\u636e\u5b8c\u6210");
            }
        }
        catch (IOException iOException) {
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempPath);
        }
        monitor.progressAndMessage(0.9, "\u62a5\u8868\u6570\u636e\u88c5\u5165\u5b8c\u6210");
        this.logFormInfo(transmissionContext, formWithErrorObj, importResult);
        return importResult;
    }

    private DataImportResult oldImportBusinessData(ITransmissionContext transmissionContext, Map<String, ZipUtils.ZipSubFile> zipFiles) throws Exception {
        DataImportResult importResult = new DataImportResult();
        AsyncTaskMonitor monitor = transmissionContext.getTransmissionMonitor();
        IExecuteParam executeParam = transmissionContext.getExecuteParam();
        TableContext context = this.setContext(transmissionContext);
        String tempPath = ZipUtils.newTempDir();
        HashMap<String, List<ImportInformations>> formWithErrorObj = new HashMap<String, List<ImportInformations>>();
        DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
        List<String> adjustPeriod = executeParam.getAdjustPeriod();
        HashMap<String, CSVRange.FormRange> formToFormRange = new HashMap<String, CSVRange.FormRange>();
        CSVRange csvRange = new CSVRange(formToFormRange);
        this.setCsvRange(transmissionContext, formToFormRange);
        context.setCsvRange(csvRange);
        DimensionValueSet dimensionSets = new DimensionValueSet(dimensionValueSet);
        context.setDimensionSet(dimensionSets);
        List importResults = new ArrayList();
        try {
            ZipUtils.ZipSubFile subFile = zipFiles.get(this.getFormSchemePath(context));
            File file = FileHelper.getTempFile(subFile, tempPath);
            TransmissionMonitor importMonitor = new TransmissionMonitor(executeParam.getFormSchemeKey(), this.cacheObjectResourceRemote, monitor, 0.9);
            importResults = this.dataFileImportService.dataImportFile(context, file, (AsyncTaskMonitor)importMonitor);
        }
        catch (Exception e) {
            importResult.setSyncErrorNumInc();
            logger.error("\u5bfc\u5165\u62a5\u8868\u6570\u636e\u65f6\u53d1\u751f\u4e86\u9519\u8bef\uff1a" + e.getMessage());
            Utils.deleteAllFilesOfDirByPath(tempPath);
            throw new Exception(MultilingualLog.businessDataImportMessage(2, e.getMessage()), e);
        }
        for (Map map : importResults) {
            try {
                List errorInfo;
                if (CollectionUtils.isEmpty(map) || "success".equals(map.get("msg")) && map.get("errorInfo") == null || CollectionUtils.isEmpty(errorInfo = (List)map.get("errorInfo"))) continue;
                for (ImportInformations importInformations : errorInfo) {
                    if (!StringUtils.hasText(importInformations.getFormKey())) {
                        importInformations.setFormKey("00000000-0000-0000-0000-000000000000");
                    }
                    if (!StringUtils.hasText(importInformations.getUnitCode())) {
                        importInformations.setFormKey("-");
                    }
                    if (!CollectionUtils.isEmpty((Collection)formWithErrorObj.get(importInformations.getFormKey())) && ((List)formWithErrorObj.get(importInformations.getFormKey())).size() > 5) continue;
                    formWithErrorObj.computeIfAbsent(importInformations.getFormKey(), key -> new ArrayList()).add(importInformations);
                    importResult.setSyncErrorNumInc();
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u4fe1\u606f\u8bfb\u53d6\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        Utils.deleteAllFilesOfDirByPath(tempPath);
        monitor.progressAndMessage(0.9, "\u62a5\u8868\u6570\u636e\u88c5\u5165\u5b8c\u6210");
        this.logFormInfo(transmissionContext, formWithErrorObj, importResult);
        return importResult;
    }

    private void setCsvRange(ITransmissionContext transmissionContext, Map<String, CSVRange.FormRange> formToFormRange) {
        IExecuteParam executeParam = transmissionContext.getExecuteParam();
        ContextExpendParam contextExpendParam = transmissionContext.getContextExpendParam();
        ArrayList<String> forms = new ArrayList<String>(executeParam.getForms());
        if (StringUtils.hasText(transmissionContext.getFmdmForm())) {
            forms.remove(transmissionContext.getFmdmForm());
        }
        List<String> adjustPeriod = executeParam.getAdjustPeriod();
        String dimensionName = contextExpendParam.getDimensionName();
        List entitys = (List)executeParam.getDimensionValueSet().getValue(dimensionName);
        for (String form : forms) {
            List<DimensionValueSet> notImportDimensionValueSets = contextExpendParam.getNotNeedImportFormMaps().get(form);
            HashMap<String, List<String>> notImportAdjustToEntityForForm = new HashMap();
            if (!CollectionUtils.isEmpty(notImportDimensionValueSets)) {
                notImportAdjustToEntityForForm = this.getAdjustToEntityForThisForm(notImportDimensionValueSets, adjustPeriod, dimensionName);
            }
            CSVRange.FormRange formRange = new CSVRange.FormRange();
            HashMap<String, List<String>> adjustDwList = new HashMap<String, List<String>>();
            if (CollectionUtils.isEmpty(adjustPeriod)) {
                this.setAdJustToUnitForForm(entitys, notImportAdjustToEntityForForm, adjustDwList, CSVRange.FormRange.EmpAdjust);
            } else {
                for (String adjustPeriodValue : adjustPeriod) {
                    this.setAdJustToUnitForForm(entitys, notImportAdjustToEntityForForm, adjustDwList, adjustPeriodValue);
                }
            }
            formRange.setAdjustDwList(adjustDwList);
            if (CollectionUtils.isEmpty(formRange.getAdjustDwList())) continue;
            formToFormRange.put(form, formRange);
        }
        contextExpendParam.getNeedImportForm().addAll(formToFormRange.keySet());
    }

    private void setAdJustToUnitForForm(List<String> entities, Map<String, List<String>> notImportAdjustToEntityForForm, Map<String, List<String>> adjustDwList, String adjustPeriodValue) {
        ArrayList<String> importEntity = new ArrayList<String>(entities);
        List<String> notImportEntitys = notImportAdjustToEntityForForm.get(CSVRange.FormRange.EmpAdjust);
        if (!CollectionUtils.isEmpty(notImportEntitys)) {
            importEntity.removeAll(notImportEntitys);
        }
        if (importEntity.size() > 0) {
            adjustDwList.put(adjustPeriodValue, importEntity);
        }
    }

    private Map<String, List<String>> getAdjustToEntityForThisForm(List<DimensionValueSet> notImportDimensionValueSets, List<String> adjustPeriod, String dimensionName) {
        HashMap<String, List<String>> notImportAdjustToEntityForThisForm = new HashMap<String, List<String>>();
        if (CollectionUtils.isEmpty(adjustPeriod)) {
            ArrayList<String> entity = new ArrayList<String>();
            for (DimensionValueSet notImportValueSet : notImportDimensionValueSets) {
                entity.add(notImportValueSet.getValue(dimensionName).toString());
            }
            notImportAdjustToEntityForThisForm.put(CSVRange.FormRange.EmpAdjust, entity);
        } else {
            for (DimensionValueSet valueSet : notImportDimensionValueSets) {
                String entity = valueSet.getValue(dimensionName).toString();
                notImportAdjustToEntityForThisForm.computeIfAbsent(valueSet.getValue(ADJUST).toString(), key -> new ArrayList()).add(entity);
            }
        }
        return notImportAdjustToEntityForThisForm;
    }

    private void logFormInfo(ITransmissionContext transmissionContext, Map<String, List<ImportInformations>> formWithErrorObj, DataImportResult importResult) throws Exception {
        ILogHelper logHelper = transmissionContext.getLogHelper();
        List<String> forms = transmissionContext.getExecuteParam().getForms();
        if (StringUtils.hasText(transmissionContext.getFmdmForm())) {
            forms = new ArrayList<String>(transmissionContext.getExecuteParam().getForms());
            forms.remove(transmissionContext.getFmdmForm());
        }
        Map<String, List<DataImportMessage>> failForms = importResult.getFailForms();
        Map<String, List<DataImportMessage>> failUnits = importResult.getFailUnits();
        ArrayList<String> importDataErrorForm = new ArrayList<String>(formWithErrorObj.keySet());
        StringBuilder successFormInfo = new StringBuilder(MultilingualLog.businessDataImportMessage(3, String.valueOf(forms.size() - importDataErrorForm.size())));
        StringBuilder errorFormInfo = new StringBuilder(MultilingualLog.businessDataImportMessage(4, String.valueOf(importDataErrorForm.size())));
        List formDefines = this.runTimeViewController.queryFormsById(forms);
        Map<String, FormDefine> formCodeToDefine = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a, (k1, k2) -> k1));
        int i = 0;
        for (String form : forms) {
            FormDefine formDefine = formCodeToDefine.get(form);
            String failMessage = MultilingualLog.businessDataImportMessage(5, "");
            if (importDataErrorForm.contains(form)) {
                errorFormInfo.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]");
                errorFormInfo.append(failMessage);
                List<ImportInformations> importInformations = formWithErrorObj.get(form);
                int k = 0;
                StringBuilder fileMessage = new StringBuilder("");
                for (ImportInformations importInformation : importInformations) {
                    if (!StringUtils.hasLength(importInformation.getUnitCode())) {
                        importInformation.setUnitCode("-");
                    }
                    if (CollectionUtils.isEmpty((Collection)failUnits.get(importInformation.getUnitCode())) || failUnits.get(importInformation.getUnitCode()).size() < 5) {
                        failUnits.computeIfAbsent(importInformation.getUnitCode(), key -> new ArrayList()).add(new DataImportMessage(importInformation.getUnitCode(), importInformation.getUnitCode(), importInformation.getMessage()));
                    }
                    fileMessage.append("\u5355\u4f4d ").append(importInformation.getUnitCode()).append(importInformation.getMessage());
                    if (k < importInformations.size() - 1) {
                        fileMessage.append("\uff0c");
                    }
                    ++k;
                }
                errorFormInfo.append((CharSequence)fileMessage);
                errorFormInfo.append("\u3002  ");
                failForms.computeIfAbsent(formDefine.getKey(), key -> new ArrayList()).add(new DataImportMessage(formDefine.getTitle(), formDefine.getFormCode(), formDefine.getKey(), fileMessage.toString()));
                continue;
            }
            successFormInfo.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]");
            if (i < forms.size() - importDataErrorForm.size() - 1) {
                successFormInfo.append("\uff0c");
            } else {
                successFormInfo.append("\u3002");
            }
            ++i;
        }
        if (!CollectionUtils.isEmpty((Collection)formWithErrorObj.get("00000000-0000-0000-0000-000000000000"))) {
            StringBuilder unKnowFormFailMessage = new StringBuilder(MultilingualLog.businessDataImportMessage(6, ""));
            int j = 0;
            List<ImportInformations> importInformations = formWithErrorObj.get("00000000-0000-0000-0000-000000000000");
            for (ImportInformations importInformation : importInformations) {
                unKnowFormFailMessage.append(importInformation.getMessage());
                if (j < importInformations.size() - 1) {
                    unKnowFormFailMessage.append("\uff0c");
                }
                ++j;
            }
            errorFormInfo.append((CharSequence)unKnowFormFailMessage);
            failForms.computeIfAbsent("00000000-0000-0000-0000-000000000000", key -> new ArrayList()).add(new DataImportMessage(MultilingualLog.businessDataImportMessage(7, ""), "00000000", "00000000-0000-0000-0000-000000000000", unKnowFormFailMessage.toString()));
        }
        if (!CollectionUtils.isEmpty(formWithErrorObj)) {
            successFormInfo.append("\r\n").append((CharSequence)errorFormInfo);
        }
        String successFormMessage = successFormInfo.toString();
        importResult.setLog(successFormMessage);
        logHelper.appendLog(successFormMessage);
    }

    private void exportBusinessDataByFormScheme(ITransmissionContext transmissionContext, ZipOutputStream zipOut) throws Exception {
        AsyncTaskMonitor monitor = transmissionContext.getTransmissionMonitor();
        TableContext context = this.setContext(transmissionContext);
        try {
            String extZipFile = this.fileExportService.getExtZipFile(context, monitor);
            File formSchemeFile = new File(FilenameUtils.normalize(extZipFile));
            zipOut.putNextEntry(new ZipEntry(this.getFormSchemePath(context)));
            FileUtils.copyFile(formSchemeFile, zipOut);
            zipOut.closeEntry();
        }
        catch (Exception e) {
            ILogHelper logHelper = transmissionContext.getLogHelper();
            String errorMessage = MultilingualLog.exportFormDataError(this.getCode()) + e.getMessage();
            logHelper.appendLog(errorMessage + "\r\n");
            logger.error(errorMessage, e);
            throw new Exception(errorMessage, e);
        }
    }

    private void oldDataExport(OutputStream outputStream, ITransmissionContext context) throws Exception {
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f00\u59cb\u6253\u5305\u62a5\u8868\u6570\u636e\uff01");
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        monitor.progressAndMessage(0.0, "\u5f00\u59cb\u6267\u884c\u6240\u9009\u5355\u4f4d\u62a5\u8868\u6570\u636e\u5bfc\u51fa\u3002");
        ZipOutputStream zipOut = new ZipOutputStream(outputStream);
        this.exportBusinessDataByFormScheme(context, zipOut);
        try {
            zipOut.finish();
        }
        catch (IOException e) {
            logger.error("\u7ed3\u675f\u5199\u5165\u65f6\u53d1\u751f\u5f02\u5e38", e);
        }
        monitor.finish("\u6240\u9009\u5355\u4f4d\u62a5\u8868\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\uff01", (Object)"\u6240\u9009\u5355\u4f4d\u62a5\u8868\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\uff01");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u6253\u5305\u62a5\u8868\u6570\u636e\u5b8c\u6210\uff01");
    }

    @Override
    public void dataExport(OutputStream outputStream, ITransmissionContext context) throws Exception {
        if (context.getTransmissionExportType() == TransmissionExportType.FIRST_VERSION) {
            this.oldDataExport(outputStream, context);
            return;
        }
        IExecuteParam executeParam = context.getExecuteParam();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(executeParam.getTaskKey());
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f00\u59cb\u6253\u5305\u62a5\u8868\u6570\u636e\uff01");
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        monitor.progressAndMessage(0.0, "\u5f00\u59cb\u6267\u884c\u6240\u9009\u5355\u4f4d\u62a5\u8868\u6570\u636e\u5bfc\u51fa\u3002");
        IParamDataFileProviderImpl paramDataProvider = new IParamDataFileProviderImpl();
        if (context.getMappingParam() != null) {
            ParamsExportMappingNrdImpl mappingParam = new ParamsExportMappingNrdImpl(context.getMappingParam());
            paramDataProvider.setMapping((ParamsMapping)mappingParam);
        }
        ParamProviderNrdImpl paramProvider = new ParamProviderNrdImpl(this.runtimeDataSchemeService, this.runTimeViewController, executeParam.getTaskKey(), executeParam.getFormSchemeKey());
        paramDataProvider.setParamProvider((ParamProvider)paramProvider);
        ExpFieldDataService expFieldDataService = this.fieldDataFileServiceFactory.getExpFieldDataService((IParamDataFileProvider)paramDataProvider);
        ContextExpendParam contextExpendParam = context.getContextExpendParam();
        DimensionCollection collection = this.dimensionBuildUtil.getDimensionCollection(contextExpendParam.getDimensionValueSetWithAllDim(), executeParam.getFormSchemeKey());
        List<String> formsWithOutFMDM = executeParam.getForms();
        if (StringUtils.hasText(context.getFmdmForm())) {
            formsWithOutFMDM = new ArrayList<String>(executeParam.getForms());
            formsWithOutFMDM.remove(context.getFmdmForm());
        }
        FieldFileParamBuilder fieldFileParamBuilder = null;
        ZipOutputStream zipOut = new ZipOutputStream(outputStream);
        Map<String, FormDefine> formKeyToDefine = this.runTimeViewController.queryFormsById(formsWithOutFMDM).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        for (String fromKey : formsWithOutFMDM) {
            List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(fromKey);
            FormDefine formDefine = formKeyToDefine.get(fromKey);
            for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                fieldFileParamBuilder = FieldFileParamBuilder.create((DimensionCollection)collection);
                if (!CollectionUtils.isEmpty(contextExpendParam.getVariables())) {
                    for (Variable variable : contextExpendParam.getVariables()) {
                        fieldFileParamBuilder.addVariable(variable);
                    }
                }
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
                if (!hasField) {
                    logger.info("\u62a5\u8868 " + formDefine.getTitle() + formDefine.getFormCode() + " \u7684\u533a\u57df " + dataRegionDefine.getCode() + "\u6ca1\u6709\u6307\u6807\uff0c\u5ffd\u7565\u5bfc\u5165\u3002");
                    continue;
                }
                String regionFileName = formDefine.getFormCode() + FORM_REGION_SEG + dataRegionDefine.getCode() + FORM_REGION_FILE_ZIP;
                try {
                    IFieldFileParam param = fieldFileParamBuilder.build();
                    DataFile export = expFieldDataService.export(param);
                    Throwable throwable = null;
                    try {
                        File formRegionFile = export.getFile();
                        zipOut.putNextEntry(new ZipEntry(regionFileName));
                        FileUtils.copyFile(formRegionFile, zipOut);
                        zipOut.closeEntry();
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (export == null) continue;
                        if (throwable != null) {
                            try {
                                export.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        export.close();
                    }
                }
                catch (Exception e) {
                    ILogHelper logHelper = context.getLogHelper();
                    String errorMessage = MultilingualLog.exportFormDataError(this.getCode()) + "\u62a5\u8868 " + formDefine.getFormCode() + " \u7684\u533a\u57df " + dataRegionDefine.getCode() + e.getMessage();
                    logHelper.appendLog(errorMessage + "\r\n");
                    Utils.deleteAllFilesOfDirByPath(regionFileName);
                    logger.error(errorMessage, e);
                }
            }
        }
        try {
            zipOut.finish();
        }
        catch (IOException e) {
            logger.error("\u7ed3\u675f\u5199\u5165\u65f6\u53d1\u751f\u5f02\u5e38", e);
        }
        monitor.finish("\u6240\u9009\u5355\u4f4d\u62a5\u8868\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\uff01", (Object)"\u6240\u9009\u5355\u4f4d\u62a5\u8868\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\uff01");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u6253\u5305\u62a5\u8868\u6570\u636e\u5b8c\u6210\uff01");
    }

    private String getFormSchemePath(TableContext context) {
        return "FORMSCHEME/" + context.getFormSchemeKey() + FORM_REGION_FILE_ZIP;
    }

    private TableContext setContext(ITransmissionContext transmissionContext) {
        IExecuteParam executeParam = transmissionContext.getExecuteParam();
        TableContext context = new TableContext();
        context.setTaskKey(executeParam.getTaskKey());
        context.setFormSchemeKey(executeParam.getFormSchemeKey());
        context.setAttachment(true);
        context.setFileType(".csv");
        context.setAttachmentArea("JTABLEAREA");
        context.setMultistageEliminateBizKey(true);
        context.setOptType(OptTypes.FORM);
        context.setExportBizkeyorder(true);
        context.setImportBizkeyorder(true);
        if (this.ioQualifier == null) {
            this.ioQualifier = new IoQualifierImpl();
        }
        context.setIoQualifier(this.ioQualifier);
        DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
        DimensionValueSet dim = new DimensionValueSet();
        dim.assign(dimensionValueSet);
        String dimensionName = transmissionContext.getContextExpendParam().getDimensionName();
        Object value = dim.getValue(dimensionName);
        if (value == null || "".equals(value)) {
            dim.clearValue(dimensionName);
        }
        if (transmissionContext.getContextExpendParam().getAllEntity()) {
            dim.clearValue(dimensionName);
        }
        context.setDimensionSet(dim);
        List<String> forms = executeParam.getForms();
        if (StringUtils.hasText(transmissionContext.getFmdmForm())) {
            forms = new ArrayList<String>(executeParam.getForms());
            forms.remove(transmissionContext.getFmdmForm());
        }
        context.setFormKey(String.join((CharSequence)FORM_REGION_SEG, forms));
        context.setMultistageUnitReplace(this.multistageUnitReplaceImpl);
        return context;
    }
}

