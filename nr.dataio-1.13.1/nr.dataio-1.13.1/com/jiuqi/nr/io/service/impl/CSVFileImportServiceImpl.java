/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 *  com.jiuqi.nr.data.common.logger.DataIoLoggerFactory
 *  com.jiuqi.nr.data.common.logger.LoggerPartInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.io.service.impl;

import com.csvreader.CsvReader;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.data.common.logger.DataIoLoggerFactory;
import com.jiuqi.nr.data.common.logger.LoggerPartInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.io.asynctask.bean.CSVFileImportInfo;
import com.jiuqi.nr.io.asynctask.bean.RegionDataSetContext;
import com.jiuqi.nr.io.asynctask.executor.CSVFileImportAsyncTaskExecutor;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.CSVRange;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.params.output.ImportParameters;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import com.jiuqi.nr.io.service.FileImportService;
import com.jiuqi.nr.io.tz.dataset.AbstractRegionDataSet;
import com.jiuqi.nr.io.util.ExtUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="CSVFileImportServiceImpl")
public class CSVFileImportServiceImpl
implements FileImportService {
    private static final Logger log = LoggerFactory.getLogger(CSVFileImportServiceImpl.class);
    public static final String MODULE_CSVFILE_UPLOAD = "\u6570\u636e\u670d\u52a1-\u591a\u7ea7\u90e8\u7f72CSV\u5bfc\u5165";
    @Autowired
    private IRunTimeViewController viewController;
    @Resource
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private NrIoProperties nrIoProperties;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private DataIoLoggerFactory dataIoLoggerFactory;

    @Override
    public Map<String, Object> dealFileData(File file, TableContext tableContext) throws Exception {
        DimensionCombination dimensionCombination = new DimensionCombinationBuilder(tableContext.getDimensionSet()).getCombination();
        DataImportLogger dataImportLogger = this.dataIoLoggerFactory.getDataImportLogger(MODULE_CSVFILE_UPLOAD, tableContext.getFormSchemeKey(), dimensionCombination);
        dataImportLogger.startImport();
        Map<String, Object> result = this.dealFileData(file, tableContext, dataImportLogger);
        dataImportLogger.finishImport();
        return result;
    }

    @Override
    public Map<String, Object> dealFileData(File file, TableContext tableContext, DataImportLogger dataImportLogger) throws Exception {
        boolean multiThread;
        int regionTop;
        String formCode;
        FormSchemeDefine formScheme1 = this.viewController.getFormScheme(tableContext.getFormSchemeKey());
        if (tableContext.getTaskKey() == null) {
            tableContext.setTaskKey(formScheme1.getTaskKey());
        }
        HashMap<String, Object> results = new HashMap<String, Object>();
        tableContext.setPwd(file.getAbsolutePath().replace(file.getName(), ""));
        FormDefine formDefine = null;
        String fileName = ExtUtil.trimEnd(file.getName(), ".csv");
        int lastIndexOfConst = fileName.lastIndexOf("_F");
        formDefine = this.getFormDefine(tableContext, fileName);
        if (lastIndexOfConst == -1 || Objects.nonNull(formDefine)) {
            formCode = fileName;
            regionTop = 1;
        } else {
            formCode = fileName.substring(0, lastIndexOfConst);
            regionTop = Integer.parseInt(fileName.substring(lastIndexOfConst + "_F".length()));
            formDefine = this.getFormDefine(tableContext, formCode);
        }
        CSVRange csvRange = tableContext.getCsvRange();
        String dw = null;
        TaskDefine queryTaskDefine = null;
        if (tableContext.getTaskKey() != null) {
            queryTaskDefine = this.viewController.queryTaskDefine(tableContext.getTaskKey());
        }
        if (queryTaskDefine == null) {
            FormSchemeDefine formScheme = this.viewController.getFormScheme(tableContext.getFormSchemeKey());
            dw = formScheme.getDw();
        } else {
            dw = queryTaskDefine.getDw();
        }
        if (csvRange != null && formDefine != null && !csvRange.contains(formDefine.getFormCode()) && !csvRange.contains(formDefine.getKey())) {
            results.put("msg", "error");
            String formTitle = formDefine == null ? "" : formDefine.getTitle();
            ImportInformations error = new ImportInformations(formDefine.getKey(), formCode, formTitle, String.format("\u5bf9\u5e94\u6587\u4ef6\uff1a%s \u4e0d\u53ef\u5bfc\u5165\u3002", file.getName()), "\u63d0\u793a\u4fe1\u606f");
            ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
            errorInfo.add(error);
            results.put("errorInfo", errorInfo);
            results.put("msg", "error");
            dataImportLogger.importError("CSV\u5bfc\u5165\u5b58\u5728\u5f02\u5e38\uff1a" + error.getMessage());
            return results;
        }
        List<RegionData> formRegions = this.getFormRegions(tableContext, formCode);
        if (null == formRegions) {
            results.put("msg", "error");
            String formTitle = formDefine == null ? "" : formDefine.getTitle();
            ImportInformations error = new ImportInformations(null, formCode, formTitle, String.format("\u672a\u627e\u5230\u5bf9\u5e94\u6587\u4ef6\uff1a%s \u7684\u62a5\u8868\u3002", file.getName()), "\u63d0\u793a\u4fe1\u606f");
            ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
            errorInfo.add(error);
            results.put("errorInfo", errorInfo);
            results.put("msg", "error");
            dataImportLogger.importError("CSV\u5bfc\u5165\u5b58\u5728\u5f02\u5e38\uff1a" + error.getMessage());
            return results;
        }
        RegionData region = null;
        for (RegionData regionData : formRegions) {
            if (regionData.getRegionTop() != regionTop) continue;
            region = regionData;
            break;
        }
        if (null == region) {
            results.put("msg", "error");
            return results;
        }
        int impThreadSize = this.nrIoProperties.getImpThreadSize();
        boolean bl = multiThread = impThreadSize > 1 && tableContext.getTempTable() == null;
        if (!multiThread) {
            Map<String, Object> result = this.doFileDataImport(file, tableContext, formDefine == null ? "" : formDefine.getKey(), region.getKey());
            results.putAll(result);
        } else {
            ObjectInfo objectInfo = null;
            try (InputStream inputStream = Files.newInputStream(file.toPath(), new OpenOption[0]);){
                objectInfo = this.fileUploadOssService.uploadFileStreamToTemp(file.getName(), inputStream);
            }
            catch (Exception e) {
                log.error("\u4e0a\u4f20\u6587\u4ef6\u81f3\u6587\u4ef6\u670d\u52a1\u5668\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
            if (objectInfo != null) {
                String fileKey = objectInfo.getKey();
                if (StringUtils.hasText(fileKey)) {
                    CSVFileImportInfo csvFileImportInfo = new CSVFileImportInfo();
                    csvFileImportInfo.setFileName(file.getName());
                    csvFileImportInfo.setFileKey(fileKey);
                    RegionDataSetContext regionDataSetContext = this.simplifyTableContext(tableContext);
                    regionDataSetContext.setFormKey(formDefine == null ? "" : formDefine.getKey());
                    regionDataSetContext.setRegionKey(region.getKey());
                    csvFileImportInfo.setRegionDataSetContext(regionDataSetContext);
                    NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                    npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)csvFileImportInfo));
                    npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new CSVFileImportAsyncTaskExecutor());
                    String taskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
                    results.put("taskId", taskId);
                }
            } else {
                ImportInformations error = new ImportInformations(null, formCode, null, "\u5411\u6587\u4ef6\u670d\u52a1\u5668\u4e0a\u4f20\u6587\u4ef6\u65f6\u53d1\u751f\u5f02\u5e38\uff0c\u6587\u4ef6\u540d\u79f0\uff1a" + file.getName(), "\u63d0\u793a\u4fe1\u606f");
                ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
                errorInfo.add(error);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
        return results;
    }

    public void doFileDataImport(File file, TableContext tableContext, String formKey, String regionKey, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        asyncTaskMonitor.progressAndMessage(0.0, "\u5f00\u59cb\u6267\u884c\u5bfc\u5165!");
        try {
            Map<String, Object> results = this.doFileDataImport(file, tableContext, formKey, regionKey);
            asyncTaskMonitor.progressAndMessage(99.0, "\u5bfc\u5165\u5b8c\u6210!");
            asyncTaskMonitor.finish("\u5bfc\u5165\u5b8c\u6210\uff01", results);
        }
        catch (Exception e) {
            log.error("\u6587\u4ef6\u3010{}\u3011\u5bfc\u5165\u8fc7\u7a0b\u4e2d\uff0c\u53d1\u751f\u9519\u8bef\uff1a{} ", file.getName(), e.getMessage(), e);
            asyncTaskMonitor.error("\u5bfc\u5165\u5931\u8d25\uff01", (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, Object> doFileDataImport(File file, TableContext tableContext, String formKey, String regionKey) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        LoggerPartInfo loggerPartInfo = new LoggerPartInfo();
        results.put("log_info", loggerPartInfo);
        FormDefine formDefine = this.viewController.queryFormById(formKey);
        DataRegionDefine dataRegion = this.viewController.queryDataRegionDefine(regionKey);
        RegionData region = new RegionData();
        region.initialize(dataRegion);
        String formCode = formDefine.getFormCode();
        CSVRange csvRange = tableContext.getCsvRange();
        ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
        ArrayList<ExportFieldDefine> fields1 = new ArrayList<ExportFieldDefine>();
        CsvReader reader = null;
        int lineNum = 0;
        String unitCode = "";
        int adjustIndex = -1;
        IRegionDataSet unitCodeUtil = null;
        Map<String, Set<String>> unImport = null;
        try {
            AbstractRegionDataSet regionDataSet;
            reader = new CsvReader(file.getAbsolutePath(), tableContext.getSplit().toCharArray()[0], StandardCharsets.UTF_8);
            if (reader.readHeaders()) {
                for (int i = 0; i < reader.getHeaderCount(); ++i) {
                    ExportFieldDefine e2 = new ExportFieldDefine();
                    e2.setCode(reader.getHeader(i));
                    ExportFieldDefine e1 = new ExportFieldDefine();
                    e1.setCode(reader.getHeader(i));
                    fields.add(e2);
                    if (e2.getCode().contains("ADJUST")) {
                        adjustIndex = i;
                    }
                    fields1.add(e1);
                }
            }
            HashSet<String> mdCodeScop = null;
            if (csvRange != null && adjustIndex == -1) {
                List<String> dwScop;
                List<String> list = dwScop = null != formDefine ? csvRange.searchDwInForm(formDefine.getFormCode(), formDefine.getKey()) : null;
                if (dwScop != null && !dwScop.isEmpty()) {
                    mdCodeScop = new HashSet<String>(dwScop);
                }
            }
            if (null != formDefine && formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT && region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                SBRegionDataSet sbRegionDataSet = new SBRegionDataSet(tableContext, region, fields1);
                sbRegionDataSet.setMdCodeScop(mdCodeScop);
                regionDataSet = sbRegionDataSet;
            } else {
                regionDataSet = new RegionDataSet(tableContext, region, fields1);
            }
            unitCodeUtil = regionDataSet;
            List<ExportFieldDefine> importFields = regionDataSet.getFieldDataList();
            importFields.forEach(e -> loggerPartInfo.getReportCodes().add(e.getTableCode()));
            ArrayList<DimensionValueSet> bizFieldValues = null;
            if (tableContext.isReturnBizKeyValue()) {
                bizFieldValues = new ArrayList<DimensionValueSet>();
                ImportParameters importPara = new ImportParameters(formCode, formCode, formCode, null);
                importPara.setBizFields(regionDataSet.getBizFieldDefList());
                importPara.setBizFieldValues(bizFieldValues);
                results.put("impParameters", importPara);
            }
            try {
                HashMap<Integer, Set> map = new HashMap<Integer, Set>();
                int conut = 0;
                while (reader.readRecord()) {
                    ++conut;
                    if (tableContext.getDataLineIndex() != null && reader.getCurrentRecord() + 2L < (long)tableContext.getDataLineIndex().intValue()) {
                        map.put(1, null);
                        continue;
                    }
                    ArrayList listData = new ArrayList();
                    for (ExportFieldDefine field : fields) {
                        listData.add(reader.get(field.getCode()));
                    }
                    if (tableContext.isReturnBizKeyValue()) {
                        unitCode = listData.get(0).toString();
                        if (csvRange != null) {
                            if (adjustIndex != -1) {
                                List<String> list = csvRange.searchAdjustInForm(formDefine.getFormCode(), formDefine.getKey(), listData.get(adjustIndex).toString());
                                if (list != null && !list.isEmpty() && !list.contains(unitCode)) {
                                    Set strings2 = map.computeIfAbsent(2, k -> new HashSet());
                                    strings2.add(unitCode);
                                    continue;
                                }
                            } else if (mdCodeScop != null && !mdCodeScop.contains(unitCode)) {
                                Set set = map.computeIfAbsent(3, k -> new HashSet());
                                set.add(unitCode);
                                continue;
                            }
                        }
                        bizFieldValues.add(regionDataSet.importDatas(listData));
                        loggerPartInfo.getUnitCodes().add(unitCode);
                    } else {
                        unitCode = listData.get(0).toString();
                        if (csvRange != null) {
                            if (adjustIndex != -1) {
                                List<String> list = csvRange.searchAdjustInForm(formDefine.getFormCode(), formDefine.getKey(), listData.get(adjustIndex).toString());
                                if (list != null && !list.isEmpty() && !list.contains(unitCode)) {
                                    Set strings = map.computeIfAbsent(4, k -> new HashSet());
                                    strings.add(unitCode);
                                    continue;
                                }
                            } else if (mdCodeScop != null && !mdCodeScop.contains(unitCode)) {
                                Set set = map.computeIfAbsent(5, k -> new HashSet());
                                set.add(unitCode);
                                continue;
                            }
                        }
                        regionDataSet.importDatas(listData);
                        loggerPartInfo.getUnitCodes().add(unitCode);
                    }
                    ++lineNum;
                }
                for (Map.Entry entry : map.entrySet()) {
                    Integer key = (Integer)entry.getKey();
                    Set value = (Set)entry.getValue();
                    String dwList = null;
                    if (value != null) {
                        dwList = String.join((CharSequence)",", value);
                    }
                    log.info("{} \u8df3\u8fc7 {}", (Object)key, (Object)dwList);
                }
                log.info("\u6570\u636e {} \u6761 , \u8df3\u8fc7 {} \u6761 \u6587\u4ef6 {} , {}", conut, lineNum, file, file.getName());
                regionDataSet.commit();
                unImport = this.getImportInfo(regionDataSet, unImport);
            }
            catch (Exception e3) {
                unImport = this.getImportInfo(regionDataSet, unImport);
                unitCodeUtil = regionDataSet;
                throw e3;
            }
        }
        catch (Exception e4) {
            log.info("\u8bfbcsv\u6587\u4ef6\u51fa\u9519 {}", (Object)e4.getMessage(), (Object)e4);
            results.put("lineNum", lineNum);
            if (unitCodeUtil != null) {
                String title = unitCodeUtil.getCodeTitle(unitCode);
                unitCode = title == null ? unitCode : title;
            }
            results.put("unitCode", unitCode);
            unImport = this.getImportInfo(unitCodeUtil, unImport);
            int dbDataCount = unitCodeUtil.getDbDataCount();
            results.put("dbDataCount", dbDataCount);
            this.buildErrInfoNormal(results, region, unImport);
            this.buildErrInfo(results, region, e4);
            loggerPartInfo.getErrorMessages().add("CSV\u5bfc\u5165\u5b58\u5728\u5f02\u5e38\u3010\u62a5\u8868\uff1a" + formCode + "\u3011:" + e4.getMessage());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
        unImport = this.getImportInfo(unitCodeUtil, unImport);
        this.buildErrInfoNormal(results, region, unImport);
        Set<String> unitSuccess = unImport.get("unit_success");
        if (unitSuccess != null && !unitSuccess.isEmpty()) {
            Set<String> inexistence = unImport.get("unit_inexistence");
            Set<String> noAccess = unImport.get("unit_noaccess");
            if (inexistence != null && !inexistence.isEmpty()) {
                unitSuccess.removeAll(inexistence);
            }
            if (noAccess != null && !noAccess.isEmpty()) {
                unitSuccess.removeAll(noAccess);
            }
            for (String item : unitSuccess) {
                ImportInformations successs = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u5bfc\u5165\u6210\u529f", item);
                List successInfo = null;
                successInfo = results.get("successInfo") == null ? new ArrayList() : (List)results.get("successInfo");
                successInfo.add(successs);
                results.put("successInfo", successInfo);
                results.put("msg", "success");
            }
        }
        return results;
    }

    private Map<String, Set<String>> getImportInfo(IRegionDataSet regionDataSet, Map<String, Set<String>> unImport) throws Exception {
        if (unImport != null) {
            Set<String> inexistence = unImport.get("unit_inexistence");
            Set<String> noaccess = unImport.get("unit_noaccess");
            Set<String> success = unImport.get("unit_success");
            Set<String> regionNoAccess = unImport.get("region_nosuccess");
            Map<String, Set<String>> unImport2 = regionDataSet.getUnImport();
            Set<String> inexistence2 = unImport2.get("unit_inexistence");
            Set<String> noaccess2 = unImport2.get("unit_noaccess");
            Set<String> success2 = unImport2.get("unit_success");
            Set<String> regionNoAccess2 = unImport.get("region_nosuccess");
            if (inexistence != null && inexistence2 != null) {
                inexistence2.addAll(inexistence);
            } else if (inexistence2 == null) {
                inexistence2 = inexistence;
            }
            if (noaccess != null && noaccess2 != null) {
                noaccess2.addAll(noaccess);
            } else if (noaccess2 == null) {
                noaccess2 = noaccess;
            }
            if (success2 != null && success != null) {
                success2.addAll(success);
            } else if (success2 == null) {
                success2 = success;
            }
            if (regionNoAccess2 != null && regionNoAccess != null) {
                regionNoAccess2.addAll(regionNoAccess);
            } else if (regionNoAccess2 == null) {
                regionNoAccess2 = regionNoAccess;
            }
            return unImport2;
        }
        return regionDataSet.getUnImport();
    }

    private void buildErrInfoNormal(Map<String, Object> results, RegionData region, Map<String, Set<String>> unImport) {
        Set<String> set;
        Set<String> noAccess;
        Set<String> inexistence = unImport.get("unit_inexistence");
        if (inexistence != null && !inexistence.isEmpty()) {
            for (String string : inexistence) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u6ca1\u6709\u8be5\u5355\u4f4dcode\u5bf9\u5e94\u7684\u4e3b\u4f53\u6570\u636e\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", string);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorInfo");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
        if ((noAccess = unImport.get("unit_noaccess")) != null && !noAccess.isEmpty()) {
            for (String item : noAccess) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u8be5\u5355\u4f4d\u6ca1\u6709\u5bfc\u5165\u6743\u9650\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", item);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorInfo");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
        if ((set = unImport.get("region_nosuccess")) != null && !set.isEmpty()) {
            for (String item : set) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u8be5\u5355\u4f4d\u5b58\u5728\u4e0e\u9ed8\u8ba4\u7ef4\u5ea6\u503c\u4e0d\u5339\u914d\u7684\u6570\u636e\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", item);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorss");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
    }

    private List<RegionData> getFormRegions(TableContext tableContext, String formCode) {
        FormDefine formDefine;
        block18: {
            formDefine = null;
            String separatorMessage = this.iNvwaSystemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
            String separator = " ";
            if (separatorMessage.equals("1")) {
                separator = "_";
            } else if (separatorMessage.equals("2")) {
                separator = "&";
            }
            String[] formCodeArr = null;
            if (formCode.contains(separator)) {
                formCodeArr = formCode.split(separator);
            }
            try {
                if (null == tableContext.getFormSchemeKey() && null != tableContext.getTaskKey()) {
                    List schemeByTask = this.viewController.queryFormSchemeByTask(tableContext.getTaskKey());
                    Iterator iterator = schemeByTask.iterator();
                    while (iterator.hasNext()) {
                        FormSchemeDefine fsd = (FormSchemeDefine)iterator.next();
                        if (formCodeArr != null && formCodeArr.length > 1) {
                            for (String formCodeInfo : formCodeArr) {
                                formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCodeInfo);
                                if (formDefine == null) {
                                    continue;
                                }
                                break;
                            }
                        } else {
                            formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCode);
                        }
                        if (null == formDefine) continue;
                        tableContext.setFormSchemeKey(fsd.getKey());
                        break block18;
                    }
                    break block18;
                }
                if (formCodeArr != null && formCodeArr.length > 1) {
                    for (String formCodeInfo : formCodeArr) {
                        formDefine = this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCodeInfo);
                        if (formDefine == null) {
                            continue;
                        }
                        break block18;
                    }
                    break block18;
                }
                formDefine = this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCode);
            }
            catch (Exception e) {
                log.info("\u901a\u8fc7\u8868\u5355\u4ee3\u7801\u67e5\u627e\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355\u51fa\u9519 {}", (Object)formCode, (Object)e);
            }
        }
        if (null == formDefine) {
            return null;
        }
        List allRegions = this.viewController.getAllRegionsInForm(formDefine.getKey());
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        for (DataRegionDefine dataRegionDefine : allRegions) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }

    private FormDefine getFormDefine(TableContext tableContext, String formCode) {
        block4: {
            FormDefine formDefine = null;
            try {
                if (null == tableContext.getFormSchemeKey() && null != tableContext.getTaskKey()) {
                    List schemeByTask = this.viewController.queryFormSchemeByTask(tableContext.getTaskKey());
                    for (FormSchemeDefine fsd : schemeByTask) {
                        formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCode);
                        if (null == formDefine) continue;
                        return formDefine;
                    }
                    break block4;
                }
                return this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCode);
            }
            catch (Exception e) {
                log.info("\u901a\u8fc7\u8868\u5355\u4ee3\u7801\u67e5\u627e\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355\u51fa\u9519 {}", (Object)formCode, (Object)e);
            }
        }
        return null;
    }

    private void buildErrInfo(Map<String, Object> results, RegionData regionData, Exception e) {
        int dbDataCount = Integer.parseInt(results.get("dbDataCount").toString());
        int lineNum = Integer.parseInt(results.get("lineNum").toString());
        String errMessage = e.getMessage() + (e.getCause() != null ? e.getCause().getMessage() : "");
        ImportInformations error = new ImportInformations(regionData.getFormKey(), regionData.getFormCode(), regionData.getTitle(), "\u7b2c" + (lineNum - 1000 > 0 ? lineNum - 1000 : 0) + "\u5230" + lineNum + "\u884c," + errMessage + "(\u5efa\u8bae\u5b9a\u4f4d\uff1a" + dbDataCount + "\u884c)", (String)results.get("unitCode"));
        List errorInfo = null;
        errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorInfo");
        errorInfo.add(error);
        results.put("errorInfo", errorInfo);
        results.put("msg", "error");
    }

    private RegionDataSetContext simplifyTableContext(TableContext tableContext) {
        RegionDataSetContext regionDataSetContext = new RegionDataSetContext();
        if (tableContext != null) {
            regionDataSetContext.setTaskKey(tableContext.getTaskKey());
            regionDataSetContext.setFormSchemeKey(tableContext.getFormSchemeKey());
            regionDataSetContext.setFormKey(tableContext.getFormKey());
            regionDataSetContext.setDimensionSetMap(tableContext.getDimensionSet() == null ? null : DimensionValueSetUtil.getDimensionSet((DimensionValueSet)tableContext.getDimensionSet()));
            regionDataSetContext.setDimensionSetRangeMap(tableContext.getDimensionSetRange() == null ? null : DimensionValueSetUtil.getDimensionSet((DimensionValueSet)tableContext.getDimensionSetRange()));
            regionDataSetContext.setOptType(tableContext.getOptType());
            regionDataSetContext.setFileType(tableContext.getFileType());
            regionDataSetContext.setSplit(tableContext.getSplit());
            regionDataSetContext.setAttachment(tableContext.isAttachment());
            regionDataSetContext.setAttachmentArea(tableContext.getAttachmentArea());
            regionDataSetContext.setExpEntryFields(tableContext.getExpEntryFields());
            regionDataSetContext.setExpEnumFields(tableContext.getExpEnumFields());
            regionDataSetContext.setFloatImpOpt(tableContext.getFloatImpOpt());
            regionDataSetContext.setPwd(tableContext.getPwd());
            regionDataSetContext.setExportBizkeyorder(tableContext.isExportBizkeyorder());
            regionDataSetContext.setImportBizkeyorder(tableContext.isImportBizkeyorder());
            regionDataSetContext.setValidEntityExist(tableContext.isValidEntityExist());
            regionDataSetContext.setReturnBizKeyValue(tableContext.isReturnBizKeyValue());
            regionDataSetContext.setDataLineIndex(tableContext.getDataLineIndex());
            regionDataSetContext.setMultistageEliminateBizKey(tableContext.isMultistageEliminateBizKey());
            regionDataSetContext.setVariables(tableContext.getVariables());
            regionDataSetContext.setCsvRange(tableContext.getCsvRange());
            regionDataSetContext.setNewFileGroup(tableContext.isNewFileGroup());
            regionDataSetContext.setMeasure(tableContext.getMeasure());
            regionDataSetContext.setDecimal(tableContext.getDecimal());
            regionDataSetContext.setCheckType(tableContext.getCheckType());
            regionDataSetContext.setSortFields(tableContext.getSortFields());
            regionDataSetContext.setOrdered(tableContext.isOrdered());
        }
        return regionDataSetContext;
    }
}

