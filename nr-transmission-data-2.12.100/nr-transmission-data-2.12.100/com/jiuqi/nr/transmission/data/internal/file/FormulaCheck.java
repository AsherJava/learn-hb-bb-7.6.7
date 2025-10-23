/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.checkdes.api.ICKDExportService
 *  com.jiuqi.nr.data.checkdes.api.ICKDImpService
 *  com.jiuqi.nr.data.checkdes.api.ICKDParamMapping
 *  com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes
 *  com.jiuqi.nr.data.checkdes.facade.obj.ExpInfo
 *  com.jiuqi.nr.data.checkdes.facade.obj.ImpSuccessInfo
 *  com.jiuqi.nr.data.checkdes.obj.CKDExpPar
 *  com.jiuqi.nr.data.checkdes.obj.CKDImpPar
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.checkdes.api.ICKDExportService;
import com.jiuqi.nr.data.checkdes.api.ICKDImpService;
import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.facade.obj.ExpInfo;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpSuccessInfo;
import com.jiuqi.nr.data.checkdes.obj.CKDExpPar;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.common.FileHelper;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.TransmissionExportType;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.internal.file.CKDParamExportMappingNrdImpl;
import com.jiuqi.nr.transmission.data.internal.file.CKDParamImportMappingNrdImpl;
import com.jiuqi.nr.transmission.data.internal.format.FormulaCheckFormat;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FormulaCheck
implements ITransmissionDataGather {
    private static final Logger logger = LoggerFactory.getLogger(FormulaCheck.class);
    @Autowired
    private IFormulaCheckDesService iFormulaCheckDesService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private ICKDExportService iCKDExportService;
    @Autowired
    private ICKDImpService iCKDImpService;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;
    @Autowired(required=false)
    private MultistageUnitReplace multistageUnitReplaceImpl;
    private final FormulaCheckFormat formulaCheckFormat = new FormulaCheckFormat();
    private static final String TEMP_FILE = "temp.data";

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public String getCode() {
        return "FORMULA_CHECK";
    }

    @Override
    public String getTitle() {
        return "\u516c\u5f0f\u51fa\u9519\u8bf4\u660e";
    }

    @Override
    public DataImportResult dataImport(InputStream inputStream, ITransmissionContext context) throws Exception {
        boolean isNrd = context.getContextExpendParam().getIsNrd();
        if (isNrd) {
            return this.newDataImport(inputStream, context);
        }
        return this.oldDataImport(inputStream, context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DataImportResult newDataImport(InputStream inputStream, ITransmissionContext context) throws Exception {
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        monitor.progressAndMessage(0.0, "\u6b63\u5728\u51c6\u5907\u540c\u6b65\u516c\u5f0f\u8bf4\u660e");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u5f00\u59cb\u88c5\u5165\u516c\u5f0f\u51fa\u9519\u8bf4\u660e");
        IExecuteParam executeParam = context.getExecuteParam();
        List formulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(executeParam.getFormSchemeKey());
        DataImportResult importFormulaCheckResult = new DataImportResult();
        Map<String, ZipUtils.ZipSubFile> zipFiles = ZipUtils.unZip((InputStream)inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFilePath, f -> f));
        String tempDir = ZipUtils.newTempDir();
        Message ckdImpMesMessage = null;
        try {
            for (Map.Entry<String, ZipUtils.ZipSubFile> stringZipSubFileEntry : zipFiles.entrySet()) {
                ZipUtils.ZipSubFile subFile = stringZipSubFileEntry.getValue();
                File file = FileHelper.getTempFile(subFile, tempDir);
            }
            CKDImpPar ckdImpPar = new CKDImpPar();
            ckdImpPar.setFilePath(tempDir);
            ckdImpPar.setFormSchemeKey(executeParam.getFormSchemeKey());
            ckdImpPar.setFormKeys(executeParam.getForms());
            ckdImpPar.setFormulaSchemeKeys(formulaSchemeDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
            DimensionCollection collection = this.dimensionBuildUtil.getDimensionCollection(context.getContextExpendParam().getDimensionValueSetWithAllDim(), executeParam.getFormSchemeKey());
            ckdImpPar.setDimensionCollection(collection);
            if (context.getMappingImportParam() != null) {
                CKDParamImportMappingNrdImpl ickdParamMapping = new CKDParamImportMappingNrdImpl(context.getMappingImportParam());
                ckdImpPar.setCkdParamMapping((ICKDParamMapping)ickdParamMapping);
            }
            ckdImpMesMessage = this.iCKDImpService.importSync(ckdImpPar);
        }
        catch (Exception ckdImpPar) {
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempDir);
        }
        List successInfos = new ArrayList();
        if (ckdImpMesMessage != null) {
            CKDImpMes formulaCheckResult = (CKDImpMes)ckdImpMesMessage.getMessage();
            successInfos = formulaCheckResult.getSuccessInfos();
        }
        Map<String, List<ImpSuccessInfo>> collect = successInfos.stream().collect(Collectors.groupingBy(ImpSuccessInfo::getFormulaSchemeKey));
        HashMap<String, String> errorInfo = new HashMap<String, String>();
        HashMap<String, Integer> descInfo = new HashMap<String, Integer>();
        for (Map.Entry<String, List<ImpSuccessInfo>> stringListEntry : collect.entrySet()) {
            descInfo.put(stringListEntry.getKey(), stringListEntry.getValue().size());
        }
        this.logFormulaInfo(context.getLogHelper(), formulaSchemeDefines, errorInfo, descInfo, importFormulaCheckResult);
        monitor.finish(importFormulaCheckResult.getLog() + "  \u516c\u5f0f\u8bf4\u660e\u88c5\u5165\u6210\u529f", (Object)importFormulaCheckResult);
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u88c5\u5165\u516c\u5f0f\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
        Utils.addSyncResult(context.getDataImportResult(), importFormulaCheckResult);
        return importFormulaCheckResult;
    }

    public DataImportResult oldDataImport(InputStream inputStream, ITransmissionContext context) throws Exception {
        DataImportResult importFormulaCheckResult = new DataImportResult();
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        ContextExpendParam contextExpendParam = context.getContextExpendParam();
        monitor.progressAndMessage(0.0, "\u6b63\u5728\u51c6\u5907\u540c\u6b65\u516c\u5f0f\u8bf4\u660e");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u5f00\u59cb\u88c5\u5165\u516c\u5f0f\u51fa\u9519\u8bf4\u660e");
        IExecuteParam executeParam = context.getExecuteParam();
        ILogHelper logHelper = context.getLogHelper();
        Map<String, ZipUtils.ZipSubFile> zipFiles = ZipUtils.unZip((InputStream)inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFilePath, f -> f));
        String tempDir = ZipUtils.newTempDir();
        String formSchemeKey = executeParam.getFormSchemeKey();
        WorkFlowType startType = contextExpendParam.getWorkFlowType();
        String dimensionName = contextExpendParam.getDimensionName();
        List formulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        int formulaSchemeDefineNum = formulaSchemeDefines.size();
        HashSet<String> srcFormulaSchemeKey = new HashSet<String>();
        for (String string : zipFiles.keySet()) {
            if (!StringUtils.hasText(string) || !StringUtils.hasText(string.substring(0, 36))) continue;
            srcFormulaSchemeKey.add(string.substring(0, 36));
        }
        HashMap<String, FormulaSchemeDefine> noExistFormulaScheme = new HashMap<String, FormulaSchemeDefine>();
        for (Object formulaSchemeDefine : formulaSchemeDefines) {
            if (srcFormulaSchemeKey.contains(formulaSchemeDefine.getKey())) continue;
            noExistFormulaScheme.put(formulaSchemeDefine.getKey(), (FormulaSchemeDefine)formulaSchemeDefine);
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (noExistFormulaScheme.size() > 0) {
            formulaSchemeDefineNum -= noExistFormulaScheme.size();
            for (Map.Entry entry : noExistFormulaScheme.entrySet()) {
                hashMap.put((String)entry.getKey(), MultilingualLog.formulaCheckImportMessage(1, ((FormulaSchemeDefine)entry.getValue()).getTitle()));
            }
        }
        HashMap<String, Integer> descInfo = new HashMap<String, Integer>();
        Set set = noExistFormulaScheme.keySet();
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            if (set.contains(formulaSchemeDefine.getKey())) continue;
            FormulaCheckDesQueryInfo queryInfo = this.buildQueryParam(context, formulaSchemeDefine.getKey());
            TransmissionMonitor formulaSchemeSubMonitor = new TransmissionMonitor(formulaSchemeDefine.getKey(), this.cacheObjectResourceRemote, monitor, 1.0 / (double)formulaSchemeDefineNum);
            List<String> adjustPeriod = executeParam.getAdjustPeriod();
            boolean hasAdjustPeriod = !CollectionUtils.isEmpty(adjustPeriod);
            DimensionValueSet dimensionSets = this.getDimensionValueSet(context);
            try {
                String allCheckInfos = this.getCheckInfosByFileInFormulaSchema(formulaSchemeDefine, tempDir, zipFiles);
                if (!StringUtils.hasText(allCheckInfos)) {
                    String info = MultilingualLog.formulaCheckImportMessage(2, formulaSchemeDefine.getTitle());
                    hashMap.put(formulaSchemeDefine.getKey(), info);
                    logger.error(info);
                    continue;
                }
                Map<String, List<DimensionValueSet>> notNeedImportFormMaps = contextExpendParam.getNotNeedImportFormMaps();
                List<FormulaCheckDesInfo> allCheckDesInfoForFormulaScheme = this.formulaCheckFormat.deserialize(allCheckInfos, notNeedImportFormMaps, startType, dimensionName, hasAdjustPeriod, formSchemeKey);
                logger.info(String.format("\u591a\u7ea7\u90e8\u7f72\u516c\u5f0f\u65b9\u6848%s[%s]\u89e3\u6790\u51fa\u6765\u5165%d\u6761\u51fa\u9519\u8bf4\u660e\u3002", formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey(), allCheckDesInfoForFormulaScheme.size()));
                queryInfo.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionSets));
                if (!CollectionUtils.isEmpty(allCheckDesInfoForFormulaScheme)) {
                    FormulaCheckDesBatchSaveInfo saveInfo = new FormulaCheckDesBatchSaveInfo();
                    saveInfo.setDesInfos(allCheckDesInfoForFormulaScheme);
                    saveInfo.setQueryInfo(queryInfo);
                    int successNum = this.iFormulaCheckDesService.batchSaveFormulaCheckDes(saveInfo);
                    descInfo.put(formulaSchemeDefine.getKey(), successNum);
                    logger.info(String.format("\u591a\u7ea7\u90e8\u7f72\u516c\u5f0f\u65b9\u6848%s[%s]\u5171\u5bfc\u5165%d\u6761\u51fa\u9519\u8bf4\u660e\u3002", formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey(), successNum));
                }
            }
            catch (Exception e) {
                hashMap.put(formulaSchemeDefine.getKey(), (String)hashMap.get(formulaSchemeDefine.getKey()) + "\u3002 " + e.getMessage());
                importFormulaCheckResult.setSyncErrorNumInc();
                String message = String.format("\u5bfc\u5165\u516c\u5f0f\u65b9\u6848%s[%s]\u7684\u51fa\u9519\u8bf4\u660e\u65f6\u53d1\u751f\u5f02\u5e38: %s\u3002", formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey(), e.getMessage());
                logger.error(message, e);
            }
            formulaSchemeSubMonitor.finish(String.format("\u5bfc\u5165\u516c\u5f0f\u65b9\u6848%s[%s]\u7684\u51fa\u9519\u8bf4\u660e\u5b8c\u6210", formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey()), String.format("\u5bfc\u5165\u516c\u5f0f\u65b9\u6848%s[%s]\u7684\u51fa\u9519\u8bf4\u660e\u5b8c\u6210", formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey()));
        }
        this.logFormulaInfo(logHelper, formulaSchemeDefines, hashMap, descInfo, importFormulaCheckResult);
        monitor.finish(importFormulaCheckResult.getLog() + "  \u516c\u5f0f\u8bf4\u660e\u88c5\u5165\u6210\u529f", (Object)importFormulaCheckResult);
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f53\u524d\u670d\u52a1\u88c5\u5165\u516c\u5f0f\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
        Utils.deleteAllFilesOfDirByPath(tempDir);
        Utils.addSyncResult(context.getDataImportResult(), importFormulaCheckResult);
        return importFormulaCheckResult;
    }

    private void logFormulaInfo(ILogHelper logHelper, List<FormulaSchemeDefine> formulaSchemeDefines, Map<String, String> errorInfo, Map<String, Integer> descInfo, DataImportResult importFormulaCheckResult) throws Exception {
        StringBuilder logInfo = new StringBuilder();
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            StringBuilder thisFormulaSchemeMessage = new StringBuilder();
            String errorMessage = errorInfo.get(formulaSchemeDefine.getKey());
            if (errorMessage == null) {
                Integer count = 0;
                if (descInfo.size() > 0 && (count = descInfo.get(formulaSchemeDefine.getKey())) == null) {
                    count = 0;
                }
                thisFormulaSchemeMessage.append(MultilingualLog.formulaCheckLogFormulaInfoMessage(1, formulaSchemeDefine.getTitle(), count.toString()));
                logInfo.append((CharSequence)thisFormulaSchemeMessage);
                importFormulaCheckResult.addSuccessFormulaCheckScheme(formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey(), formulaSchemeDefine.getKey(), thisFormulaSchemeMessage.toString());
                continue;
            }
            thisFormulaSchemeMessage.append(MultilingualLog.formulaCheckLogFormulaInfoMessage(2, formulaSchemeDefine.getTitle(), errorMessage));
            logInfo.append((CharSequence)thisFormulaSchemeMessage);
            importFormulaCheckResult.getFailFormulaCheckSchemes().computeIfAbsent(formulaSchemeDefine.getKey(), key -> new ArrayList()).add(new DataImportMessage(formulaSchemeDefine.getTitle(), formulaSchemeDefine.getKey(), formulaSchemeDefine.getKey(), thisFormulaSchemeMessage.toString()));
        }
        String logInfoString = logInfo.toString();
        importFormulaCheckResult.setLog(logInfoString);
        logHelper.appendLog(logInfoString);
    }

    private String getCheckInfosByFileInFormulaSchema(FormulaSchemeDefine formulaSchemeDefine, String tempDir, Map<String, ZipUtils.ZipSubFile> zipFiles) throws Exception {
        ZipUtils.ZipSubFile subFile = zipFiles.get(this.getFilePathInZip(formulaSchemeDefine));
        String checkInfos = null;
        try {
            File tempFile = FileHelper.getTempFile(subFile, tempDir);
            checkInfos = FileUtils.readFileToString(tempFile, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return checkInfos;
    }

    private File getCheckInfoByFormulaScheme(FormulaCheckDesQueryInfo queryInfo, String path, String exportNumMessage, ITransmissionContext context) throws Exception {
        List formulaCheckDesInfos = this.iFormulaCheckDesService.queryFormulaCheckDes(queryInfo);
        int formulaCheckNum = formulaCheckDesInfos.size();
        String dimensionName = context.getContextExpendParam().getDimensionName();
        if (this.multistageUnitReplaceImpl != null) {
            for (FormulaCheckDesInfo formulaCheckDesInfo : formulaCheckDesInfos) {
                String superiorCode;
                DimensionValue dimensionValue = (DimensionValue)formulaCheckDesInfo.getDimensionSet().get(dimensionName);
                String unit = dimensionValue.getValue();
                if (unit.equals(superiorCode = this.multistageUnitReplaceImpl.getSuperiorCode(unit))) continue;
                dimensionValue.setValue(superiorCode);
                formulaCheckDesInfo.setDesKey(null);
            }
        }
        String serialize = this.formulaCheckFormat.serialize(formulaCheckDesInfos);
        logger.info(exportNumMessage + formulaCheckNum + "\u6761\u51fa\u9519\u8bf4\u660e!");
        String filePath = path + "/" + TEMP_FILE;
        PathUtils.validatePathManipulation((String)path);
        File file = new File(filePath);
        if (!file.exists()) {
            FileUtils.forceMkdirParent(file);
        }
        FileUtils.writeStringToFile(file, serialize, StandardCharsets.UTF_8);
        return file;
    }

    private void oldDataExport(OutputStream outputStream, ITransmissionContext context) {
        logger.info("\u591a\u7ea7\u90e8\u7f72\u6253\u5f00\u59cb\u6253\u5305\u51fa\u9519\u8bf4\u660e\uff01");
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        monitor.progressAndMessage(0.0, "\u5f00\u59cb\u6267\u884c\u51fa\u9519\u8bf4\u660e\u5bfc\u51fa");
        IExecuteParam executeParam = context.getExecuteParam();
        ZipOutputStream zipOut = new ZipOutputStream(outputStream);
        String tempPath = ZipUtils.newTempDir();
        String formSchemeKey = executeParam.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        List formulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        double stepSize = 1.0 / (double)formulaSchemeDefines.size();
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            FormulaCheckDesQueryInfo queryInfo = this.buildQueryParam(context, formulaSchemeDefine.getKey());
            TransmissionMonitor formulaSchemeSubMonitor = new TransmissionMonitor(formulaSchemeDefine.getKey(), this.cacheObjectResourceRemote, monitor, stepSize);
            DimensionValueSet dimensionSet = this.getDimensionValueSet(context);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(executeParam.getTaskKey());
            queryInfo.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionSet));
            String exportNumMessage = String.format("\u4efb\u52a1\uff1a%s\uff0c\u62a5\u8868\u65b9\u6848\uff1a%s\uff0c\u516c\u5f0f\u65b9\u6848\uff1a%s \u5171\u5bfc\u51fa", taskDefine.getTitle(), formScheme.getTitle(), formulaSchemeDefine.getTitle());
            try {
                File checkInfoFile = this.getCheckInfoByFormulaScheme(queryInfo, tempPath, exportNumMessage, context);
                zipOut.putNextEntry(new ZipEntry(this.getFilePathInZip(formulaSchemeDefine)));
                FileUtils.copyFile(checkInfoFile, zipOut);
                zipOut.closeEntry();
            }
            catch (Exception e) {
                ILogHelper logHelper = context.getLogHelper();
                String errorMessage = MultilingualLog.exportFormulaCheckError(taskDefine.getTitle(), formScheme.getTitle(), formulaSchemeDefine.getTitle(), e.getMessage());
                DataImportResult dataImportResult = context.getDataImportResult();
                dataImportResult.setSyncErrorNumInc();
                logHelper.appendLog(errorMessage + "\r\n");
                logger.error(errorMessage, e);
            }
            formulaSchemeSubMonitor.finish("\u62a5\u8868\u65b9\u6848" + formulaSchemeDefine.getTitle() + "\u7684\u5ba1\u6838\u8bf4\u660e\u6587\u4ef6\u5bfc\u51fa\u5b8c\u6210", "\u62a5\u8868\u65b9\u6848" + formulaSchemeDefine.getTitle() + "\u7684\u5ba1\u6838\u8bf4\u660e\u6587\u4ef6\u5bfc\u51fa\u5b8c\u6210");
        }
        try {
            zipOut.finish();
        }
        catch (IOException e) {
            logger.error("\u7ed3\u675f\u5199\u5165\u65f6\u53d1\u751f\u5f02\u5e38", e);
        }
        monitor.finish("\u51fa\u9519\u8bf4\u660e\u5bfc\u51fa\u5b8c\u6210", (Object)"\u51fa\u9519\u8bf4\u660e\u5bfc\u51fa\u5b8c\u6210");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u6253\u5305\u51fa\u9519\u8bf4\u660e\u5b8c\u6210\uff01");
        Utils.deleteAllFilesOfDirByPath(tempPath);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dataExport(OutputStream outputStream, ITransmissionContext context) throws Exception {
        if (context.getTransmissionExportType() == TransmissionExportType.FIRST_VERSION) {
            this.oldDataExport(outputStream, context);
            return;
        }
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        monitor.progressAndMessage(0.0, "\u5f00\u59cb\u6267\u884c\u51fa\u9519\u8bf4\u660e\u5bfc\u51fa");
        IExecuteParam executeParam = context.getExecuteParam();
        CKDExpPar cCKDExpPar = new CKDExpPar();
        cCKDExpPar.setFormSchemeKey(executeParam.getFormSchemeKey());
        cCKDExpPar.setFormKeys(executeParam.getForms());
        List formulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(executeParam.getFormSchemeKey());
        cCKDExpPar.setFormulaSchemeKeys(formulaSchemeDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        DimensionCollection collection = this.dimensionBuildUtil.getDimensionCollection(context.getContextExpendParam().getDimensionValueSetWithAllDim(), executeParam.getFormSchemeKey());
        cCKDExpPar.setDimensionCollection(collection);
        if (context.getMappingParam() != null) {
            CKDParamExportMappingNrdImpl ickdParamMapping = new CKDParamExportMappingNrdImpl(context.getMappingParam());
            cCKDExpPar.setCkdParamMapping((ICKDParamMapping)ickdParamMapping);
        }
        ExpInfo export = this.iCKDExportService.export(cCKDExpPar);
        ZipOutputStream zipOut = new ZipOutputStream(outputStream);
        String extFile = export.getFilePath();
        Path sourcePath = Paths.get(extFile, new String[0]);
        String extLastFile = sourcePath.getFileName().toString();
        try (Stream<Path> walked = Files.walk(sourcePath, new FileVisitOption[0]);){
            walked.filter(path -> !Files.isDirectory(path, new LinkOption[0])).forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(sourcePath.relativize((Path)path).toString());
                try {
                    zipOut.putNextEntry(zipEntry);
                    Files.copy(path, zipOut);
                    zipOut.closeEntry();
                }
                catch (IOException e) {
                    logger.error(e.getMessage());
                }
            });
        }
        try {
            zipOut.finish();
        }
        catch (IOException e) {
            logger.error("\u7ed3\u675f\u5199\u5165\u65f6\u53d1\u751f\u5f02\u5e38", e);
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(extFile);
        }
        monitor.finish("\u51fa\u9519\u8bf4\u660e\u5bfc\u51fa\u5b8c\u6210", (Object)"\u51fa\u9519\u8bf4\u660e\u5bfc\u51fa\u5b8c\u6210");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u6253\u5305\u51fa\u9519\u8bf4\u660e\u5b8c\u6210\uff01");
    }

    private DimensionValueSet getDimensionValueSet(ITransmissionContext context) {
        IExecuteParam executeParam = context.getExecuteParam();
        String dimensionName = context.getContextExpendParam().getDimensionName();
        DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
        DimensionValueSet newDimensionValueSet = new DimensionValueSet(dimensionValueSet);
        Map<String, String> uploadDimMap = executeParam.getUploadDimMap();
        for (Map.Entry<String, String> stringStringEntry : uploadDimMap.entrySet()) {
            if (StringUtils.hasText(stringStringEntry.getValue()) || newDimensionValueSet.getValue(stringStringEntry.getKey()) != null) continue;
            newDimensionValueSet.setValue(stringStringEntry.getKey(), (Object)"");
        }
        return newDimensionValueSet;
    }

    private String getFilePathInZip(FormulaSchemeDefine formulaSchemeDefine) {
        return formulaSchemeDefine.getKey() + ".json";
    }

    private FormulaCheckDesQueryInfo buildQueryParam(ITransmissionContext context, String formulaSchemeKey) {
        FormulaCheckDesQueryInfo queryInfo = new FormulaCheckDesQueryInfo();
        IExecuteParam executeParam = context.getExecuteParam();
        queryInfo.setFormSchemeKey(executeParam.getFormSchemeKey());
        ArrayList<String> forms = new ArrayList<String>(executeParam.getForms());
        if (StringUtils.hasText(context.getFmdmForm()) && !forms.contains(context.getFmdmForm())) {
            forms.add(context.getFmdmForm());
        }
        forms.add("00000000-0000-0000-0000-000000000000");
        String formKeys = String.join((CharSequence)";", forms);
        queryInfo.setFormKey(formKeys);
        queryInfo.setDimensionSet(this.getDimensionSet(executeParam.getDimensionValueSet()));
        queryInfo.setFormulaSchemeKey(formulaSchemeKey);
        return queryInfo;
    }

    private Map<String, DimensionValue> getDimensionSet(DimensionValueSet dimensionValueSet) {
        return DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
    }
}

