/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.office.excel2.CacheSXSSFWorkbook
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.common.utils.DataCommonUtils
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.office.excel2.CacheSXSSFWorkbook;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.common.utils.DataCommonUtils;
import com.jiuqi.nr.data.excel.exception.ExcelException;
import com.jiuqi.nr.data.excel.export.ExportAsyncTaskExecutor;
import com.jiuqi.nr.data.excel.extend.AppendedFileDownload;
import com.jiuqi.nr.data.excel.extend.ISheetNameProviderFactory;
import com.jiuqi.nr.data.excel.extend.param.SheetNameParam;
import com.jiuqi.nr.data.excel.obj.AppendedFileDownloadParam;
import com.jiuqi.nr.data.excel.obj.BatchExportOps;
import com.jiuqi.nr.data.excel.obj.ExcelInfo;
import com.jiuqi.nr.data.excel.obj.ExcelSettings;
import com.jiuqi.nr.data.excel.obj.ExcelWriteInfo;
import com.jiuqi.nr.data.excel.obj.ExpExcelSyncResult;
import com.jiuqi.nr.data.excel.obj.ExpSingleFileResult;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.data.excel.param.BaseExpPar;
import com.jiuqi.nr.data.excel.param.BatchExpPar;
import com.jiuqi.nr.data.excel.param.BatchExpParSer;
import com.jiuqi.nr.data.excel.param.Directory;
import com.jiuqi.nr.data.excel.param.Excel;
import com.jiuqi.nr.data.excel.param.ExcelRule;
import com.jiuqi.nr.data.excel.param.ExportAsyncPar;
import com.jiuqi.nr.data.excel.param.FormExpPar;
import com.jiuqi.nr.data.excel.param.GenerateParam;
import com.jiuqi.nr.data.excel.param.Sheet;
import com.jiuqi.nr.data.excel.param.SingleExpPar;
import com.jiuqi.nr.data.excel.param.TitleShowSetting;
import com.jiuqi.nr.data.excel.service.IDataExportService;
import com.jiuqi.nr.data.excel.service.impl.ExportCacheImpl;
import com.jiuqi.nr.data.excel.service.internal.IExportOptionsService;
import com.jiuqi.nr.data.excel.service.internal.IFormDataService;
import com.jiuqi.nr.data.excel.utils.ExportUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DataExportServiceImpl
implements IDataExportService {
    private static final Logger logger = LoggerFactory.getLogger(DataExportServiceImpl.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IFormDataService formDataService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    @Qualifier(value="defaultExcelRule")
    private ExcelRule defaultRule;
    @Autowired(required=false)
    private AppendedFileDownload appendedFileDownload;
    @Autowired
    private IExportOptionsService exportOptionsService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired(required=false)
    private ISheetNameProviderFactory sheetNameProviderFactory;

    @Override
    public ExpSingleFileResult expSingleFile(SingleExpPar param, OutputStream outputStream, CommonParams context) throws Exception {
        ExcelWriteInfo excelWriteInfo;
        HashMap<String, Object> versionDim;
        HashSet<String> expFormCodes;
        CacheSXSSFWorkbook sxWorkbook;
        ExpSingleFileResult expSingleFileResult;
        block11: {
            expSingleFileResult = new ExpSingleFileResult();
            this.progress(context.getMonitor(), 0.05, "export_in_progress_info", null);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
            Date entityQueryVersionDate = ExportUtil.getEntityQueryVersionDate(formScheme.getDateTime(), (String)param.getDimensionCombination().getValue("DATATIME"));
            ExportCacheImpl exportCache = new ExportCacheImpl(entityQueryVersionDate, formScheme, param);
            exportCache.setDataSnapshotId(param.getDataSnapshotId());
            exportCache.setSheetNameProvider(this.sheetNameProviderFactory, param, this.defaultRule.getSheetNameType());
            DimensionCollection dimensionCollection = ExportUtil.toDimensionCollection(param.getDimensionCombination());
            IBatchAccessResult batchAccessResult = this.getBatchAccessResult(dimensionCollection, formScheme, param.getForms());
            ArrayList<Map<String, DimensionValue>> dimensionList = new ArrayList<Map<String, DimensionValue>>();
            double progressPerAccess = 0.94;
            double lastProgress = 0.05;
            GenerateParam genPar = new GenerateParam();
            genPar.setDimensionCollection(dimensionCollection);
            genPar.setFormDefines(ExportUtil.getExpForms(param.getForms(), param.getFormSchemeKey()));
            genPar.setFormSchemeDefine(formScheme);
            genPar.setBatchAccessResult(batchAccessResult);
            genPar.setTitleShowSetting(param.getOps().getTitleShowSetting());
            List<Directory> directories = this.defaultRule.generateExportInfo(genPar);
            ArrayList allExcel = new ArrayList();
            directories.forEach(o -> allExcel.addAll(o.getExcels()));
            if (allExcel.size() != 1) {
                throw new ExcelException("\u5bfc\u51fa\u7684excel\u6587\u4ef6\u4e0d\u552f\u4e00");
            }
            sxWorkbook = null;
            DimensionValueSet dimensionValueSet = ExportUtil.mergeDim(dimensionList);
            expFormCodes = new HashSet<String>();
            versionDim = new HashMap<String, Object>();
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                versionDim.put(dimensionValueSet.getName(i), dimensionValueSet.getValue(i));
            }
            Excel excel = (Excel)allExcel.get(0);
            int sheetNum = excel.getSheets().size();
            double minProgressUnit = progressPerAccess / (double)sheetNum;
            excelWriteInfo = this.writeExcel(param, context, exportCache, lastProgress, minProgressUnit, excel, sxWorkbook = new CacheSXSSFWorkbook(2000));
            if (2 != excelWriteInfo.getState()) break block11;
            if (context.getMonitor() != null) {
                context.getMonitor().canceled(null, null);
            }
            ExpSingleFileResult expSingleFileResult2 = expSingleFileResult;
            DataExportServiceImpl.releaseWb((SXSSFWorkbook)sxWorkbook);
            return expSingleFileResult2;
        }
        try {
            this.writeInfo((BaseExpPar)param, expFormCodes, versionDim, (SXSSFWorkbook)sxWorkbook);
            if (excelWriteInfo.isHaveData()) {
                if (param.getOps().isExpExcelDirSheet()) {
                    ExportUtil.createExcelDirSheet((SXSSFWorkbook)sxWorkbook);
                }
                sxWorkbook.write(outputStream);
            } else {
                expSingleFileResult.setNoDataNoExp(true);
            }
            this.progress(context.getMonitor(), 1.0, "export_finish_info", null);
        }
        catch (Exception e) {
            try {
                logger.error("\u5bfc\u51faExcel\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw e;
            }
            catch (Throwable throwable) {
                DataExportServiceImpl.releaseWb(sxWorkbook);
                throw throwable;
            }
        }
        DataExportServiceImpl.releaseWb((SXSSFWorkbook)sxWorkbook);
        return expSingleFileResult;
    }

    private void writeInfo(BaseExpPar param, Set<String> expFormCodes, Map<String, Object> versionDim, SXSSFWorkbook sxWorkbook) {
        POIXMLProperties.CustomProperties customProperties = sxWorkbook.getXSSFWorkbook().getProperties().getCustomProperties();
        customProperties.addProperty("version", "v1");
        customProperties.addProperty("createTime", String.valueOf(LocalDateTime.now(ZoneId.of("Z")).atZone(ZoneId.of("Z"))));
        customProperties.addProperty("form", String.valueOf(expFormCodes));
        customProperties.addProperty("dimensions", String.valueOf(versionDim));
        ExcelSettings excelSettings = this.getExcelSettings(param.getOps().getTitleShowSetting());
        customProperties.addProperty("splitChar", excelSettings.getSplitChar());
        customProperties.addProperty("formShow", excelSettings.getSheetName());
        customProperties.addProperty("dwShow", excelSettings.getExcelName());
        customProperties.addProperty("simplifyExpFileHierarchy", excelSettings.getFileHierarchy());
    }

    private ExcelSettings getExcelSettings(TitleShowSetting titleShowSetting) {
        String splitChar = null;
        String sheetName = null;
        String excelName = null;
        String fileHierarchy = null;
        if (titleShowSetting != null) {
            if (titleShowSetting.getSplitCharSetting() != null) {
                splitChar = titleShowSetting.getSplitCharSetting();
            }
            if (titleShowSetting.getFormShowSetting() != null) {
                sheetName = titleShowSetting.getFormShowSetting();
            }
            if (titleShowSetting.getDwShowSetting() != null) {
                excelName = titleShowSetting.getDwShowSetting();
            }
            if (titleShowSetting.getSimplifyExpFileHierarchy() != null) {
                String string = fileHierarchy = titleShowSetting.getSimplifyExpFileHierarchy() != false ? "1" : "0";
            }
        }
        if (splitChar == null) {
            splitChar = this.systemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
        }
        if (sheetName == null) {
            sheetName = this.systemOptionService.get("nr-data-entry-export", "SHEET_NAME");
        }
        if (excelName == null) {
            excelName = this.systemOptionService.get("nr-data-entry-export", "EXCEL_NAME");
        }
        if (fileHierarchy == null) {
            fileHierarchy = this.systemOptionService.get("nr-data-entry-export", "SIMPLIFY_EXPORT_FILE_HIERARCHY");
        }
        ExcelSettings excelSettings = new ExcelSettings();
        excelSettings.setSplitChar(splitChar);
        excelSettings.setSheetName(sheetName);
        excelSettings.setExcelName(excelName);
        excelSettings.setFileHierarchy(fileHierarchy);
        return excelSettings;
    }

    @Override
    public ExpExcelSyncResult expExcelSync(BatchExpPar param, String expPath, CommonParams context) throws Exception {
        ExpExcelSyncResult expExcelSyncResult = new ExpExcelSyncResult();
        boolean allHaveData = false;
        if (!StringUtils.hasText(expPath)) {
            expPath = ExportUtil.getExpTempPath();
        }
        this.progress(context.getMonitor(), 0.05, "build_export_dimension_info", null);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        Date entityQueryVersionDate = ExportUtil.getEntityQueryVersionDate(formScheme.getDateTime(), (String)((DimensionCombination)param.getDimensionCollection().getDimensionCombinations().get(0)).getValue("DATATIME"));
        ExportCacheImpl exportCache = new ExportCacheImpl(entityQueryVersionDate, formScheme, param);
        double lastProgress = 0.05;
        String fileExtend = param.getOps().isEt() ? ".et" : ".xlsx";
        IBatchAccessResult batchAccessResult = this.getBatchAccessResult(param.getDimensionCollection(), formScheme, param.getForms());
        GenerateParam genPar = new GenerateParam();
        genPar.setDimensionCollection(param.getDimensionCollection());
        List<FormDefine> expForms = ExportUtil.getExpForms(param.getForms(), param.getFormSchemeKey());
        genPar.setFormDefines(expForms);
        exportCache.initBatchQueryDims(formScheme, expForms, param.getDimensionCollection());
        genPar.setFormSchemeDefine(formScheme);
        genPar.setBatchAccessResult(batchAccessResult);
        genPar.setTitleShowSetting(param.getOps().getTitleShowSetting());
        ExcelRule rule = param.getRule();
        List<Directory> directories = rule.generateExportInfo(genPar);
        exportCache.setSheetNameProvider(this.sheetNameProviderFactory, param, rule.getSheetNameType());
        if (this.exportOptionsService.simplifyExpFileHierarchy(param.getOps().getTitleShowSetting())) {
            ExportUtil.simplifyDirs(directories, "_");
        }
        int sheetNum = 0;
        for (Directory directory : directories) {
            for (Excel excel : directory.getExcels()) {
                sheetNum += excel.getSheets().size();
            }
        }
        double minProgressUnit = 0.0;
        if (sheetNum != 0) {
            minProgressUnit = 0.94 / (double)sheetNum;
        }
        HashSet<String> filePaths = new HashSet<String>();
        List<Directory> mergedDir = this.mergeDir(directories);
        boolean initPath = true;
        for (Directory directory : mergedDir) {
            if (initPath) {
                this.checkPeriodPath(expPath, directory);
            }
            String directoryPath = expPath + directory.getDirectory();
            List<Excel> excels = directory.getExcels();
            for (Excel excel : excels) {
                CacheSXSSFWorkbook sxWorkbook;
                block16: {
                    ExcelWriteInfo excelWriteInfo;
                    String filePath;
                    block15: {
                        String fileName = excel.getFileName() + fileExtend;
                        filePath = directoryPath + fileName;
                        if (!filePaths.add(filePath = filePath.replace("\\\\", "/"))) {
                            throw new ExcelException("excel\u5bfc\u51fa\u6587\u4ef6\u540d\u91cd\u590d:" + directory.getDirectory() + fileName);
                        }
                        initPath = false;
                        sxWorkbook = null;
                        sxWorkbook = new CacheSXSSFWorkbook(2000);
                        excelWriteInfo = this.writeExcel(param, context, exportCache, lastProgress, minProgressUnit, excel, sxWorkbook);
                        if (2 != excelWriteInfo.getState()) break block15;
                        if (context.getMonitor() != null) {
                            context.getMonitor().canceled(null, null);
                        }
                        ExpExcelSyncResult expExcelSyncResult2 = expExcelSyncResult;
                        DataExportServiceImpl.releaseWb((SXSSFWorkbook)sxWorkbook);
                        return expExcelSyncResult2;
                    }
                    try {
                        lastProgress = excelWriteInfo.getProgress();
                        if (!excelWriteInfo.isHaveData()) break block16;
                        DataExportServiceImpl.expDirSheetAndWriteFile(param, filePath, sxWorkbook);
                        allHaveData = true;
                        expExcelSyncResult.getExpDws().addAll(excelWriteInfo.getExpDws());
                    }
                    catch (Exception e) {
                        try {
                            logger.error("\u5bfc\u51faExcel\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            throw e;
                        }
                        catch (Throwable throwable) {
                            DataExportServiceImpl.releaseWb(sxWorkbook);
                            throw throwable;
                        }
                    }
                }
                DataExportServiceImpl.releaseWb((SXSSFWorkbook)sxWorkbook);
            }
            this.expAppendedFile(param.getBatchExportOps(), formScheme, taskDefine, exportCache, directoryPath);
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        HashSet<String> expFormCodes = new HashSet<String>();
        this.writeInfo((BaseExpPar)param, expPath, expFormCodes, dimensionValueSet);
        expExcelSyncResult.setExpPath(expPath);
        expExcelSyncResult.setNoDataNoExp(!allHaveData);
        this.progress(context.getMonitor(), 1.0, "export_finish_info", expExcelSyncResult);
        return expExcelSyncResult;
    }

    private static void expDirSheetAndWriteFile(BatchExpPar param, String filePath, CacheSXSSFWorkbook sxWorkbook) throws IOException {
        File file = FileUtil.createIfNotExists((String)filePath);
        logger.info("\u6587\u4ef6\u521b\u5efa\u6210\u529f\uff1a{}", (Object)filePath);
        try (FileOutputStream fos = new FileOutputStream(file);){
            if (param.getOps().isExpExcelDirSheet()) {
                ExportUtil.createExcelDirSheet((SXSSFWorkbook)sxWorkbook);
            }
            sxWorkbook.write((OutputStream)fos);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private static void releaseWb(SXSSFWorkbook sxWorkbook) {
        if (sxWorkbook != null) {
            try {
                sxWorkbook.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            sxWorkbook.dispose();
        }
    }

    private void checkPeriodPath(String expPath, Directory directory) {
        String[] split = directory.getDirectory().split("[/\\\\]");
        String period = split[0];
        String periodPath = expPath + period;
        Path path = Paths.get(FilenameUtils.normalize(periodPath), new String[0]);
        if (Files.exists(path, new LinkOption[0])) {
            throw new ExcelException("excel\u5bfc\u51fa\u8def\u5f84\u4e2d\u65f6\u671f\u6807\u9898\u91cd\u590d\uff1a" + period + "\uff0c\u8bf7\u68c0\u67e5\u65f6\u671f\u914d\u7f6e\uff01");
        }
    }

    private List<Directory> mergeDir(List<Directory> directories) {
        ArrayList<Directory> result = new ArrayList<Directory>();
        Map<String, List<Directory>> collect = directories.stream().collect(Collectors.groupingBy(Directory::getDirectory));
        for (Map.Entry<String, List<Directory>> entry : collect.entrySet()) {
            Directory e = new Directory(entry.getKey());
            ArrayList<Excel> excels = new ArrayList<Excel>();
            entry.getValue().forEach(o -> excels.addAll(o.getExcels()));
            e.setExcels(excels);
            result.add(e);
        }
        return result;
    }

    private void expAppendedFile(BatchExportOps batchExportOps, FormSchemeDefine formScheme, TaskDefine taskDefine, ExportCache exportCache, String directoryPath) throws Exception {
        if (batchExportOps != null && batchExportOps.isExpAppendedFile() && this.appendedFileDownload != null) {
            AppendedFileDownloadParam appendedFileDownloadParam = new AppendedFileDownloadParam();
            appendedFileDownloadParam.setDataSchemeKey(taskDefine.getDataScheme());
            appendedFileDownloadParam.setFormSchemeKey(formScheme.getKey());
            appendedFileDownloadParam.setFileGroupKeys(exportCache.getFileGroupKeys());
            try {
                this.appendedFileDownload.download(directoryPath, appendedFileDownloadParam);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            exportCache.getFileGroupKeys().clear();
        }
    }

    private void writeInfo(BaseExpPar param, String expPath, Set<String> expFormCodes, DimensionValueSet dimensionValueSet) throws IOException {
        HashMap<String, String> extraAttributes = new HashMap<String, String>();
        TitleShowSetting titleShowSetting = param.getOps().getTitleShowSetting();
        ExcelSettings excelSettings = this.getExcelSettings(titleShowSetting);
        extraAttributes.put("splitChar", excelSettings.getSplitChar());
        extraAttributes.put("formShow", excelSettings.getSheetName());
        extraAttributes.put("dwShow", excelSettings.getExcelName());
        extraAttributes.put("simplifyExpFileHierarchy", excelSettings.getFileHierarchy());
        DataCommonUtils.writeVersionInfoExtra((DimensionValueSet)dimensionValueSet, new ArrayList<String>(expFormCodes), (String)expPath, extraAttributes);
    }

    private ExcelWriteInfo writeExcel(BaseExpPar param, CommonParams context, ExportCache exportCache, double lastProgress, double minProgressUnit, Excel excel, CacheSXSSFWorkbook sxWorkbook) {
        FormSchemeDefine formScheme = exportCache.getCurFormScheme();
        LinkedHashMap<String, String> illegalSheet = new LinkedHashMap<String, String>();
        List<Sheet> sheets = excel.getSheets();
        DimensionCollection dimensionCollection = null;
        if (param instanceof BatchExpPar) {
            dimensionCollection = ((BatchExpPar)param).getDimensionCollection();
        } else if (param instanceof SingleExpPar) {
            dimensionCollection = ExportUtil.toDimensionCollection(((SingleExpPar)param).getDimensionCombination());
        }
        boolean allHaveData = false;
        ExcelWriteInfo excelWriteInfo = new ExcelWriteInfo();
        ExcelInfo excelInfo = this.formDataService.getExcelInfo(excel, exportCache);
        for (Sheet sheet : sheets) {
            if (context.getMonitor() != null && context.getMonitor().isCancel()) {
                excelWriteInfo.setState(2);
                return excelWriteInfo;
            }
            if (excelInfo.getFormKeys().contains(sheet.getFormKey())) {
                SheetInfo sheetInfo = new SheetInfo();
                sheetInfo.setExcelInfo(excelInfo);
                sheetInfo.setDimensionCollection(dimensionCollection);
                sheetInfo.setFormSchemeKey(param.getFormSchemeKey());
                sheetInfo.setTaskKey(formScheme.getTaskKey());
                sheetInfo.setDimensionCombination(sheet.getDimensionCombination());
                SheetNameParam sheetNameParam = new SheetNameParam(sheet.getDimensionCombination(), sheet.getFormKey(), 0, excelInfo.getExcelName());
                ExportUtil.SheetNameHandleResult sheetNameHandleResult = ExportUtil.handleSheetName(sheet.getSheetName(), exportCache.getSheetNameProvider(), sheetNameParam);
                String sheetNameValidate = sheetNameHandleResult.getSheetName();
                if (!sheetNameHandleResult.isProvided() && !sheetNameValidate.equals(sheet.getSheetName())) {
                    illegalSheet.put(sheetNameValidate, sheet.getSheetName());
                }
                sheetInfo.setSheetName(sheetNameValidate);
                sheetInfo.setOriginalSheetName(sheetNameValidate);
                sheetInfo.setFormKey(sheet.getFormKey());
                sheetInfo.setExportOps(param.getOps());
                sheetInfo.setCustomCellStyles(exportCache.getCustomGridCellStyle(sheet.getDimensionCombination(), sheet.getFormKey()));
                boolean haveData = this.formDataService.writeSheet((SXSSFWorkbook)sxWorkbook, sheetInfo, exportCache);
                if (haveData) {
                    allHaveData = true;
                    excelWriteInfo.getExpDws().add(String.valueOf(sheet.getDimensionCombination().getValue(exportCache.getEntityDefine(this.dataAccesslUtil.contextEntityId(formScheme.getDw())).getDimensionName())));
                } else {
                    illegalSheet.remove(sheetNameValidate);
                }
            }
            this.progress(context.getMonitor(), lastProgress += minProgressUnit, "export_in_progress_info", null);
        }
        SXSSFSheet hiddenSheet = sxWorkbook.getSheet("HIDDENSHEETNAME");
        if (hiddenSheet != null) {
            sxWorkbook.setSheetOrder("HIDDENSHEETNAME", sxWorkbook.getNumberOfSheets() - 1);
        }
        if (!illegalSheet.isEmpty()) {
            ExportUtil.createMapper((SXSSFWorkbook)sxWorkbook, illegalSheet);
        }
        excelWriteInfo.setProgress(lastProgress);
        excelWriteInfo.setHaveData(allHaveData);
        return excelWriteInfo;
    }

    @Override
    public String expExcelAsync(BatchExpPar param, String expPath, CommonParams context) throws Exception {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        if (formScheme == null) {
            throw new IllegalArgumentException(String.format("incorrect formSchemeKey %s!", param.getFormSchemeKey()));
        }
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(formScheme.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(formScheme.getKey());
        ExportAsyncPar exportAsyncPar = new ExportAsyncPar();
        exportAsyncPar.setBatchExpParSer(new BatchExpParSer(param));
        exportAsyncPar.setFilePath(expPath);
        exportAsyncPar.setParamsMapping(context.getMapping());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)exportAsyncPar));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ExportAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNCTASK_DATAEXPORT");
    }

    @Override
    public Grid2Data expGrid2Data(FormExpPar param) throws Exception {
        Grid2Data formStyle = this.formDataService.getFormStyle(param.getFormKey());
        return this.expGrid2Data(param, formStyle);
    }

    @Override
    public Grid2Data expGrid2Data(FormExpPar param, Grid2Data grid2Data) throws Exception {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        Date entityQueryVersionDate = ExportUtil.getEntityQueryVersionDate(formScheme.getDateTime(), (String)param.getDimensionCombination().getValue("DATATIME"));
        ExportCacheImpl exportCache = new ExportCacheImpl(entityQueryVersionDate, formScheme, param);
        exportCache.setDataSnapshotId(param.getDataSnapshotId());
        exportCache.setGridDataFormatted(param.isGridDataFormatted());
        Excel excel = new Excel(null, null);
        Sheet sheet = new Sheet(param.getDimensionCombination(), param.getFormKey(), excel, null, null);
        excel.setSheets(Collections.singletonList(sheet));
        ExcelInfo excelInfo = this.formDataService.getExcelInfo(excel, exportCache);
        if (!excelInfo.getFormKeys().contains(param.getFormKey())) {
            return null;
        }
        SheetInfo sheetInfo = new SheetInfo();
        sheetInfo.setFormSchemeKey(param.getFormSchemeKey());
        sheetInfo.setTaskKey(exportCache.getFormScheme(param.getFormSchemeKey()).getTaskKey());
        sheetInfo.setDimensionCombination(param.getDimensionCombination());
        sheetInfo.setDimensionCollection(ExportUtil.toDimensionCollection(param.getDimensionCombination()));
        sheetInfo.setFormKey(param.getFormKey());
        sheetInfo.setExportOps(param.getOps());
        sheetInfo.setCustomCellStyles(exportCache.getCustomGridCellStyle(param.getDimensionCombination(), param.getFormKey()));
        return this.formDataService.fillFormData(grid2Data, sheetInfo, exportCache);
    }

    private void progress(AsyncTaskMonitor monitor, double progress, String pMessage, Object result) {
        if (monitor != null) {
            monitor.progressAndMessage(progress, pMessage);
            if (progress >= 1.0) {
                monitor.finish(pMessage, result);
            }
        }
    }

    private IBatchAccessResult getBatchAccessResult(DimensionCollection dimensionCollection, FormSchemeDefine formSchemeDefine, List<String> formKeys) {
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(formSchemeDefine.getTaskKey(), formSchemeDefine.getKey());
        return dataAccessService.getReadAccess(dimensionCollection, formKeys);
    }
}

