/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.task.api.file.IFileAreaService
 *  com.jiuqi.nr.task.api.file.dto.FileAreaDTO
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  com.jiuqi.nvwa.cellbook.excel.ExcelToCellSheet
 *  com.jiuqi.nvwa.cellbook.excel.IExcelToCellSheetProvider
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.task.form.formio.service.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.task.api.file.IFileAreaService;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import com.jiuqi.nr.task.form.dto.FormImportAnalysisDTO;
import com.jiuqi.nr.task.form.dto.FormImportDTO;
import com.jiuqi.nr.task.form.exception.FormRuntimeException;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.formio.IFormImportService;
import com.jiuqi.nr.task.form.formio.common.ImportCellType;
import com.jiuqi.nr.task.form.formio.context.FormImportContext;
import com.jiuqi.nr.task.form.formio.dto.FormImportResult;
import com.jiuqi.nr.task.form.formio.dto.FormulaParseResult;
import com.jiuqi.nr.task.form.formio.dto.ImportBaseDataDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportFormulaDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportReverseResultDTO;
import com.jiuqi.nr.task.form.formio.dto.ProgressItemDTO;
import com.jiuqi.nr.task.form.formio.formula.ReportContext;
import com.jiuqi.nr.task.form.formio.formula.ReportFormulaParserBuilder;
import com.jiuqi.nr.task.form.formio.service.impl.ExcelReverseModelDataProvider;
import com.jiuqi.nr.task.form.formio.service.impl.ExcelToCellSheetProviderExtend;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.service.IReverseModelingService;
import com.jiuqi.nr.task.form.table.IDataTableService;
import com.jiuqi.nr.task.form.util.FieldBeanUtils;
import com.jiuqi.nr.task.form.util.FormImportUtils;
import com.jiuqi.nr.task.form.util.LinkBeanUtils;
import com.jiuqi.nvwa.cellbook.excel.ExcelToCellSheet;
import com.jiuqi.nvwa.cellbook.excel.IExcelToCellSheetProvider;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FormImportServiceImpl
implements IFormImportService {
    private static Logger logger = LoggerFactory.getLogger(FormImportServiceImpl.class);
    public static final String FORM_TITLE_KEY = "FORM_TITLE";
    public static final String FORM_CODE_KEY = "FORM_CODE";
    private static final String ENUM_NAME = "\u679a\u4e3e\u5b57\u5178";
    private static final String CODE_CHECK_CHAR = "^[A-Z][0-9a-zA-Z_]*$";
    private static final String CACHE_HASH_PROGRESS = "PROGRESS";
    private static final String CACHE_HASH_DATA = "DATA";
    @Autowired
    private IFileAreaService fileAreaService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IReverseModelingService reverseModelingService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataTableService dataTableService;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private IDesignTimeFormulaController designTimeFormulaController;
    private static final String CACHE_NAME = "EXCEL_IMPORT";
    private NedisCache nedisCache;
    private FormImportUtils formImportUtils;

    @Autowired
    private void initCache(NedisCacheProvider cacheProvider) {
        NedisCacheManager manager = cacheProvider.getCacheManager("NR:FORM:IMPORT");
        this.nedisCache = manager.getCache(CACHE_NAME);
    }

    private FormImportUtils getFormImportUtils() {
        if (this.formImportUtils == null) {
            this.formImportUtils = new FormImportUtils(this.designTimeViewController, this.reverseModelingService);
        }
        return this.formImportUtils;
    }

    @Override
    public String excelUpload(MultipartFile file) {
        FileInfoDTO fileInfoDTO;
        try {
            this.createWorkBook(file.getInputStream());
            fileInfoDTO = this.fileAreaService.fileUpload(file.getOriginalFilename(), file.getInputStream(), new FileAreaDTO(true));
        }
        catch (IOException e) {
            throw new FormRuntimeException("\u6587\u4ef6\u4e0a\u4f20\u5931\u8d25:" + e.getMessage());
        }
        return fileInfoDTO == null ? null : fileInfoDTO.getKey();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void formImport(FormImportDTO formImportDTO) {
        if (CollectionUtils.isEmpty(formImportDTO.getFileKeys())) {
            return;
        }
        ArrayList<FormImportAnalysisDTO> analysis = new ArrayList<FormImportAnalysisDTO>();
        logger.debug("=====================\u5f00\u59cb\u5bfc\u5165excel\u6587\u4ef6=====================");
        this.checkBeforeImport(formImportDTO);
        Workbook workbook = null;
        try {
            logger.debug("\u6b63\u5728\u89e3\u6790excel\u6587\u4ef6\uff0c\u5171\u53d1\u73b0[{}]\u4e2a\u6587\u4ef6", (Object)formImportDTO.getFileKeys().size());
            ProgressItem progressItem = this.getFormImportUtils().getProgressItem(formImportDTO.getTaskKey());
            for (String fileKey : formImportDTO.getFileKeys()) {
                this.closeWorkBook(workbook);
                workbook = this.getWorkBook(fileKey);
                FormImportAnalysisDTO fileAnalysisDTO = this.fileAnalysis(progressItem, workbook, formImportDTO.getFormSchemeKey(), formImportDTO.isGenerateField());
                fileAnalysisDTO.setFileKey(fileKey);
                analysis.add(fileAnalysisDTO);
            }
            boolean isSuccess = this.doImport(progressItem, analysis, formImportDTO);
            progressItem.setFinished(true);
            if (isSuccess) {
                progressItem.setMessage("\u8868\u6837\u5bfc\u5165\u5b8c\u6210");
                logger.debug("=====================\u672c\u6b21\u8868\u6837\u5bfc\u5165\u5b8c\u6210=====================");
            } else {
                progressItem.setMessage("\u8868\u6837\u5bfc\u5165\u53d1\u73b0\u9519\u8bef\u516c\u5f0f");
                logger.debug("=====================\u53d1\u73b0\u9519\u8bef\u516c\u5f0f=====================");
                progressItem.setFailed(true);
                ((ProgressItemDTO)progressItem).setFormulaError(true);
            }
            this.setProgress(progressItem);
        }
        catch (Exception e) {
            ProgressItem importProgress = this.getProgress(formImportDTO.getTaskKey());
            if (importProgress == null) {
                importProgress = new ProgressItem();
            }
            importProgress.setFailed(true);
            importProgress.setFinished(true);
            importProgress.setMessage(String.format("\u8868\u6837\u5bfc\u5165\u5931\u8d25" + e.getMessage(), new Object[0]));
            logger.error("\u8868\u6837\u5bfc\u5165\u5931\u8d25{}", (Object)e.getMessage());
            logger.error(e.getMessage(), e);
            importProgress.setProgressId(formImportDTO.getTaskKey());
            this.setProgress(importProgress);
            throw new FormRuntimeException(e);
        }
    }

    private void closeWorkBook(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Workbook getWorkBook(String fileKey) {
        byte[] file = this.fileAreaService.download(fileKey, new FileAreaDTO(true));
        try (ByteArrayInputStream fileStream = new ByteArrayInputStream(file);){
            Workbook workbook = this.createWorkBook(fileStream);
            return workbook;
        }
        catch (Exception e) {
            throw new FormRuntimeException("\u83b7\u53d6\u5de5\u4f5c\u8868\u5931\u8d25:" + e.getMessage());
        }
    }

    private FormImportAnalysisDTO fileAnalysis(ProgressItem progressItem, Workbook workbook, String formSchemeKey, boolean generateField) {
        FormImportAnalysisDTO formImportAnalysis = new FormImportAnalysisDTO();
        ExcelToCellSheetProviderExtend excelToCellSheetProviderExtend = new ExcelToCellSheetProviderExtend(workbook);
        if (generateField) {
            excelToCellSheetProviderExtend = new ExcelToCellSheetProviderExtend(workbook, true);
        }
        ExcelToCellSheet excelImportUtil = new ExcelToCellSheet(workbook, (IExcelToCellSheetProvider)excelToCellSheetProviderExtend);
        formImportAnalysis.setExcelImportUtil(excelImportUtil);
        formImportAnalysis.setExcelToCellSheetProviderExtend(excelToCellSheetProviderExtend);
        formImportAnalysis.setWorkbook(workbook);
        LinkedHashMap<Map<String, String>, Sheet> sheetMap = new LinkedHashMap<Map<String, String>, Sheet>();
        int sheetNumber = workbook.getNumberOfSheets();
        for (int sheetIndex = 0; sheetIndex < sheetNumber; ++sheetIndex) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            boolean sheetHidden = workbook.isSheetHidden(sheetIndex);
            if (sheetHidden) {
                logger.debug("\u8868\u3010{}\u3011\u88ab\u9690\u85cf\uff0c\u5c06\u5ffd\u7565", (Object)sheet.getSheetName());
                continue;
            }
            if (sheet == null) {
                throw new FormRuntimeException("\u6587\u4ef6\u89e3\u6790\u5931\u8d25\uff0c\u9875\u7b7e\u4e3a\u7a7a");
            }
            Map<String, String> formInfoMap = this.analysisFormInfo(sheet, formSchemeKey);
            logger.debug("\u6b63\u5728\u89e3\u6790\u8868\u3010{}\u3011", (Object)formInfoMap.get(FORM_TITLE_KEY));
            this.updateProgressItem(progressItem, String.format("\u6b63\u5728\u89e3\u6790\u8868[%s]", formInfoMap.get(FORM_TITLE_KEY)), 30);
            if (formInfoMap.isEmpty()) continue;
            sheetMap.put(formInfoMap, sheet);
        }
        formImportAnalysis.setSheetMap(sheetMap);
        return formImportAnalysis;
    }

    private Map<String, String> analysisFormInfo(Sheet sheet, String formSchemeKey) {
        HashMap<String, String> formTitleAndCode = new HashMap<String, String>();
        String sheetName = sheet.getSheetName();
        if (!sheetName.startsWith(ENUM_NAME)) {
            String formTilte = sheetName;
            String formCode = "";
            if (sheetName.contains("|")) {
                int index = sheetName.indexOf("|");
                formTilte = sheetName.substring(index + 1).trim();
                formCode = sheetName.substring(0, index).trim().toUpperCase();
            }
            formTilte = this.getFormImportUtils().getFormTitle(formTilte, formSchemeKey);
            formCode = this.getFormImportUtils().getFormCode(formCode, formTilte, formSchemeKey);
            this.checkCode(formTilte, formCode);
            formCode = formCode.toUpperCase();
            formTitleAndCode.put(FORM_TITLE_KEY, formTilte);
            formTitleAndCode.put(FORM_CODE_KEY, formCode);
            logger.debug("\u89e3\u6790\u8868\u5355\u4fe1\u606f\uff1a\u6807\u9898\u3010{}\u3011\uff0c\u6807\u8bc6\u3010{}\u3011", (Object)formTilte, (Object)formCode);
        }
        return formTitleAndCode;
    }

    public boolean doImport(ProgressItem progressItem, List<FormImportAnalysisDTO> analysisResults, FormImportDTO formImportDTO) {
        boolean isSuccess = true;
        FormImportResult formImportResult = new FormImportResult();
        if (!CollectionUtils.isEmpty(analysisResults)) {
            logger.debug("\u6587\u4ef6\u89e3\u6790\u5b8c\u6210\uff0c\u6b63\u5728\u6267\u884c\u5bfc\u5165");
            ExcelReverseModelDataProvider reverseModelDataProvider = new ExcelReverseModelDataProvider(this.designDataSchemeService);
            for (FormImportAnalysisDTO analysisResult : analysisResults) {
                reverseModelDataProvider.setCurrentFile(analysisResult.getFileKey());
                for (Map.Entry<Map<String, String>, Sheet> entry : analysisResult.getSheetMap().entrySet()) {
                    String formTitle = entry.getKey().get(FORM_TITLE_KEY);
                    if ((entry.getValue().getLastRowNum() == 0 || entry.getValue().getLastRowNum() == -1) && entry.getValue().getRow(0) == null) continue;
                    logger.debug("\u6b63\u5728\u5bfc\u5165\u8868[{}]", (Object)formTitle);
                    this.updateProgressItem(progressItem, String.format("\u6b63\u5728\u5bfc\u5165\u8868[%s]", formTitle), 80);
                    DesignFormDefine formDefine = this.getFormImportUtils().buildFormDefine(entry.getKey(), formImportDTO.getFormSchemeKey());
                    logger.debug("\u6784\u5efa\u8868[{}]\u62a5\u8868\u5b9a\u4e49\u5b8c\u6210", (Object)formTitle);
                    DesignFormGroupLink designFormGroupLink = this.designTimeViewController.initFormGroupLink();
                    designFormGroupLink.setFormKey(formDefine.getKey());
                    designFormGroupLink.setGroupKey(formImportDTO.getFormGroupKey());
                    designFormGroupLink.setFormOrder(formDefine.getOrder());
                    Grid2Data grid2Data = this.buildGridData(false, formDefine, analysisResult, entry.getValue());
                    formDefine.setBinaryData(Grid2Data.gridToBytes((Grid2Data)grid2Data));
                    logger.debug("\u6784\u5efa\u8868[{}]\u62a5\u8868\u8868\u6837\u5b8c\u6210", (Object)formTitle);
                    DesignDataRegionDefine dataRegionDefine = this.getFormImportUtils().buildDataRegion(formDefine, grid2Data);
                    logger.debug("\u6784\u5efa\u8868[{}]\u62a5\u8868\u533a\u57df\u5b8c\u6210", (Object)formTitle);
                    formImportResult.getDesignFormDefines().add(formDefine);
                    formImportResult.getDataRegionDefines().add(dataRegionDefine);
                    formImportResult.getDesignFormGroupLinks().add(designFormGroupLink);
                    FormImportContext formImportContext = analysisResult.getExcelToCellSheetProviderExtend().getFormImportContext();
                    this.buildFormImportContext(formImportDTO, formImportContext);
                    formImportContext.setCurrentForm(formDefine);
                    formImportContext.setDataRegion(dataRegionDefine);
                    formImportContext.setReverseModelDataProvider(reverseModelDataProvider);
                    this.doGenerateField(formImportDTO, reverseModelDataProvider);
                }
                if (formImportDTO.isGenerateField()) {
                    FormulaSyntaxStyle syntaxStyle = this.getFormulaSyntaxStyle(formImportDTO);
                    logger.debug("\u751f\u6210\u516c\u5f0f\u7684\u7b56\u7565\u4e3a\uff1a{}", (Object)syntaxStyle);
                    if (syntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION) {
                        this.parseNRFormulas(formImportDTO, analysisResult, reverseModelDataProvider);
                    } else {
                        this.parseFormulas(formImportDTO, reverseModelDataProvider);
                    }
                }
                isSuccess = isSuccess && analysisResult.getCheckResult().isEmpty();
            }
            this.collectFormImportResult(formImportResult, reverseModelDataProvider);
            if (isSuccess) {
                this.saveImportData(formImportResult);
            } else {
                this.nedisCache.hSet(formImportDTO.getTaskKey(), (Object)CACHE_HASH_DATA, (Object)formImportResult);
            }
        }
        return isSuccess;
    }

    private Grid2Data buildGridData(boolean isEmpty, DesignFormDefine formDefine, FormImportAnalysisDTO analysisResult, Sheet value) {
        if (isEmpty) {
            logger.debug("\u68c0\u6d4b\u5230\u7a7a\u8868\u6837\uff1a{}\uff0c\u6784\u5efa\u9ed8\u8ba4\u8868\u6837", (Object)formDefine.getTitle());
            return this.createGrid2Data(19, 14, 28, 70);
        }
        return this.getFormImportUtils().buildGridData(analysisResult, formDefine.getFormCode(), value);
    }

    private FormulaSyntaxStyle getFormulaSyntaxStyle(FormImportDTO formImportDTO) {
        Integer formulaSyntaxStyle = formImportDTO.getFormulaSyntaxStyle();
        formulaSyntaxStyle = formulaSyntaxStyle == null ? 0 : formulaSyntaxStyle;
        FormulaSyntaxStyle syntaxStyle = FormulaSyntaxStyle.forValue((int)formulaSyntaxStyle);
        return syntaxStyle == null ? FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION : syntaxStyle;
    }

    private void saveImportData(FormImportResult formImportResult) {
        long start = System.currentTimeMillis();
        try {
            List<ImportBaseDataDTO> baseDatas = formImportResult.getBaseDatas();
            for (ImportBaseDataDTO baseData : baseDatas) {
                BaseDataDefineDTO baseDataDefine = baseData.getBaseDataDefine();
                this.createBaseData(baseDataDefine.getName(), baseDataDefine.getTitle());
                this.mergeBaseData(baseDataDefine.getName(), baseData.getBaseData());
            }
            logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u4fdd\u5b58\u57fa\u7840\u6570\u636e\uff0c\u6570\u91cf\u4e3a\uff1a{}", (Object)baseDatas.size());
            List<DesignFormDefine> designFormDefines = formImportResult.getDesignFormDefines();
            this.designTimeViewController.insertForms(designFormDefines.toArray(new DesignFormDefine[0]));
            logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u4fdd\u5b58\u8868\u5355\u6570\u636e\uff0c\u6570\u91cf\u4e3a\uff1a{}", (Object)designFormDefines.size());
            List<DesignFormGroupLink> designFormGroupLinks = formImportResult.getDesignFormGroupLinks();
            this.designTimeViewController.insertFormGroupLink(designFormGroupLinks.toArray(new DesignFormGroupLink[0]));
            logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u4fdd\u5b58\u8868\u5355\u5206\u7ec4\u5173\u7cfb\uff0c\u6570\u91cf\u4e3a\uff1a{}", (Object)designFormGroupLinks.size());
            List<DesignDataRegionDefine> regionDefines = formImportResult.getDataRegionDefines();
            this.designTimeViewController.insertDataRegion(regionDefines.toArray(new DesignDataRegionDefine[0]));
            logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u4fdd\u5b58\u6570\u636e\u533a\u57df\uff0c\u6570\u91cf\u4e3a\uff1a{}", (Object)regionDefines.size());
            List<DataLinkSettingDTO> linkSettings = formImportResult.getLinkSettings();
            ArrayList<DesignDataLinkDefine> dataLinkDefines = new ArrayList<DesignDataLinkDefine>(linkSettings.size());
            for (DataLinkSettingDTO link : linkSettings) {
                DesignDataLinkDefine linkDefine = this.designTimeViewController.initDataLink();
                dataLinkDefines.add(linkDefine);
                LinkBeanUtils.toDefine(link, linkDefine);
                if (linkDefine.getUniqueCode() != null) continue;
                linkDefine.setUniqueCode(OrderGenerator.newOrder());
            }
            this.designTimeViewController.insertDataLink(dataLinkDefines.toArray(new DesignDataLinkDefine[0]));
            logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u4fdd\u5b58\u6570\u636e\u94fe\u63a5\uff0c\u6570\u91cf\u4e3a\uff1a{}", (Object)linkSettings.size());
            this.dataTableService.insertReverseModeTables(formImportResult.getDataTables());
            logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u4fdd\u5b58\u6570\u636e\u8868\uff0c\u6570\u91cf\u4e3a\uff1a{}", (Object)formImportResult.getDataTables().size());
            List<DataFieldSettingDTO> fieldSettings = formImportResult.getFieldSettings();
            ArrayList<DesignDataField> fieldDefines = new ArrayList<DesignDataField>(fieldSettings.size());
            for (DataFieldSettingDTO fieldSetting : fieldSettings) {
                DesignDataField designDataField = this.designDataSchemeService.initDataField();
                FieldBeanUtils.toDefine(fieldSetting, designDataField);
                fieldDefines.add(designDataField);
            }
            this.designDataSchemeService.insertDataFields(fieldDefines);
            logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u4fdd\u5b58\u6307\u6807\uff0c\u6570\u91cf\u4e3a\uff1a{}", (Object)fieldSettings.size());
            List<DesignFormulaDefine> formulaDefines = formImportResult.getDesignFormulaDefines();
            this.designTimeFormulaController.insertFormula(formulaDefines.toArray(new DesignFormulaDefine[0]));
            logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u4fdd\u5b58\u516c\u5f0f\uff0c\u6570\u91cf\u4e3a\uff1a{}", (Object)formulaDefines.size());
        }
        catch (SchemeDataException e) {
            throw new FormRuntimeException(e);
        }
        long end = System.currentTimeMillis();
        logger.debug("\u3010\u5bfc\u5165\u8868\u6837\u3011\u8017\u65f6\uff1a{}ms", (Object)(end - start));
    }

    private void collectFormImportResult(FormImportResult formImportResult, ExcelReverseModelDataProvider reverseModelDataProvider) {
        formImportResult.getBaseDatas().addAll(reverseModelDataProvider.getMergeBaseData().getResult());
        Map<String, Map<String, ImportReverseResultDTO>> importReverseResultDTOMap = reverseModelDataProvider.getImportReverseResultDTOMap();
        for (Map.Entry<String, Map<String, ImportReverseResultDTO>> resultEntry : importReverseResultDTOMap.entrySet()) {
            Map<String, ImportReverseResultDTO> importReverseResultMap = resultEntry.getValue();
            for (Map.Entry<String, ImportReverseResultDTO> entry : importReverseResultMap.entrySet()) {
                ImportReverseResultDTO importReverseResultDTO = entry.getValue();
                if (importReverseResultDTO.getDataTableDTO() != null) {
                    formImportResult.getDataTables().add(importReverseResultDTO.getDataTableDTO());
                }
                if (!CollectionUtils.isEmpty(importReverseResultDTO.getLinks())) {
                    formImportResult.getLinkSettings().addAll(importReverseResultDTO.getLinks().values());
                }
                if (!CollectionUtils.isEmpty(importReverseResultDTO.getFieldPosMap())) {
                    formImportResult.getFieldSettings().addAll(importReverseResultDTO.getFieldPosMap().values());
                }
                if (CollectionUtils.isEmpty(importReverseResultDTO.getParsedFormulas())) continue;
                formImportResult.getDesignFormulaDefines().addAll(importReverseResultDTO.getParsedFormulas());
            }
        }
    }

    private void doGenerateField(FormImportDTO formImportDTO, ExcelReverseModelDataProvider reverseModelDataProvider) {
        if (!formImportDTO.isGenerateField()) {
            return;
        }
        logger.debug("\u5f00\u59cb\u751f\u6210\u6307\u6807\u548c\u6570\u636e\u8868\u6a21\u578b");
        FormImportContext formImportContext = reverseModelDataProvider.getCurrentContext();
        ImportReverseResultDTO importReverseResultDTO = this.getFormImportUtils().generateField(formImportContext);
        reverseModelDataProvider.setImportReverseResultDTO(importReverseResultDTO);
        logger.debug("\u5171\u751f\u6210\u3010{}\u3011\u4e2a\u6307\u6807\uff0c\u6570\u636e\u8868\u3010{}\u3011", (Object)importReverseResultDTO.getFieldPosMap().size(), (Object)(importReverseResultDTO.getDataTableDTO() != null ? 1 : 0));
        logger.debug("\u5f00\u59cb\u751f\u6210\u94fe\u63a5\u6a21\u578b");
        this.getFormImportUtils().generateLinks(importReverseResultDTO, formImportContext.getDataRegion());
        logger.debug("\u5171\u751f\u6210\u3010{}\u3011\u4e2a\u94fe\u63a5", (Object)importReverseResultDTO.getLinks().size());
        logger.debug("\u5f00\u59cb\u751f\u6210Excel\u516c\u5f0f\u6a21\u578b");
        DesignFormulaSchemeDefine defaultFormulaSchemeByFormScheme = this.designTimeFormulaController.getDefaultFormulaSchemeByFormScheme(reverseModelDataProvider.getFormSchemeDefine().getKey());
        this.getFormImportUtils().generateFormula(formImportContext, defaultFormulaSchemeByFormScheme, () -> this.designTimeFormulaController.initFormula(), importReverseResultDTO);
        logger.debug("\u5171\u751f\u6210\u3010{}\u3011\u4e2a\u516c\u5f0f", (Object)importReverseResultDTO.getFormulaPosMap().size());
    }

    private void parseFormulas(FormImportDTO formImportDTO, ExcelReverseModelDataProvider reverseModelDataProvider) {
        Map<String, ImportReverseResultDTO> resultDTOMap = reverseModelDataProvider.getCurrentImportReverseResultDTOMap();
        if (CollectionUtils.isEmpty(resultDTOMap)) {
            return;
        }
        logger.debug("\u5f00\u59cb\u89e3\u6790Excel\u98ce\u683c\u7684\u516c\u5f0f");
        for (Map.Entry<String, ImportReverseResultDTO> entry : resultDTOMap.entrySet()) {
            String sheet = entry.getKey();
            ImportReverseResultDTO reverseResultDTO = entry.getValue();
            Map<String, DesignFormulaDefine> formulaPosMap = reverseResultDTO.getFormulaPosMap();
            if (CollectionUtils.isEmpty(formulaPosMap)) continue;
            Map<String, ImportFormulaDTO> importFormulasMap = reverseResultDTO.getImportFormulaDTOMap();
            ArrayList<ImportFormulaDTO> needGenFieldImportFormulas = new ArrayList<ImportFormulaDTO>();
            for (Map.Entry<String, DesignFormulaDefine> formulaEntry : formulaPosMap.entrySet()) {
                String formulaPos = formulaEntry.getKey();
                ImportFormulaDTO importFormulaDTO = importFormulasMap.get(formulaPos);
                DesignFormulaDefine designFormulaDefine = formulaEntry.getValue();
                if (importFormulaDTO == null) continue;
                if (importFormulaDTO.getCellType() == null || importFormulaDTO.getCellType() == ImportCellType.FORM_STYLE) {
                    importFormulaDTO.setCellType(ImportCellType.BIG_DECIMAL);
                }
                importFormulaDTO.getCellAttr().setTitle("\u516c\u5f0f" + designFormulaDefine.getCode());
                needGenFieldImportFormulas.add(importFormulaDTO);
                logger.debug("\u6807\u8bb0\u516c\u5f0f\u6307\u6807\u7c7b\u578b\uff1a{}", (Object)importFormulaDTO.getCellType().getTitle());
                designFormulaDefine.setExpression(String.format("%s=%s", importFormulaDTO.getAddress(), designFormulaDefine.getExpression()));
                designFormulaDefine.setSyntax(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION.getValue());
                reverseResultDTO.getParsedFormulas().add(designFormulaDefine);
            }
            this.doGenerateFieldAfterFormula(needGenFieldImportFormulas, reverseModelDataProvider, sheet, formImportDTO);
        }
    }

    private void parseNRFormulas(FormImportDTO formImportDTO, FormImportAnalysisDTO analysisResult, ExcelReverseModelDataProvider reverseModelDataProvider) {
        Map<String, ImportReverseResultDTO> resultDTOMap = reverseModelDataProvider.getCurrentImportReverseResultDTOMap();
        if (CollectionUtils.isEmpty(resultDTOMap)) {
            return;
        }
        logger.debug("\u5f00\u59cb\u89e3\u6790\u516c\u5f0f");
        ReportFormulaParser parser = new ReportFormulaParserBuilder().addResult(resultDTOMap).build();
        ReportContext parseFormulaContext = new ReportContext(parser);
        for (Map.Entry<String, ImportReverseResultDTO> entry : resultDTOMap.entrySet()) {
            String sheet = entry.getKey();
            parseFormulaContext.setSheet(sheet);
            logger.debug("\u5f00\u59cb\u89e3\u6790\u3010{}\u3011\u7684\u516c\u5f0f", (Object)sheet);
            ImportReverseResultDTO reverseResultDTO = entry.getValue();
            Map<String, DesignFormulaDefine> formulaPosMap = reverseResultDTO.getFormulaPosMap();
            if (CollectionUtils.isEmpty(formulaPosMap)) continue;
            Map<String, ImportFormulaDTO> importFormulasMap = reverseResultDTO.getImportFormulaDTOMap();
            ArrayList<ImportFormulaDTO> needGenFieldImportFormulas = new ArrayList<ImportFormulaDTO>();
            for (Map.Entry<String, DesignFormulaDefine> formulaEntry : formulaPosMap.entrySet()) {
                String formulaPos = formulaEntry.getKey();
                ImportFormulaDTO importFormulaDTO = importFormulasMap.get(formulaPos);
                DesignFormulaDefine designFormulaDefine = formulaEntry.getValue();
                try {
                    parseFormulaContext.setFormulaKey(designFormulaDefine.getKey());
                    IExpression expression = parser.parseEval(designFormulaDefine.getExpression(), (IContext)parseFormulaContext);
                    int type = expression.getType((IContext)parseFormulaContext);
                    parseFormulaContext.getExpressionMap().put(designFormulaDefine.getKey(), expression);
                    if (importFormulaDTO != null && Boolean.TRUE.equals(importFormulaDTO.getNeedReBuild())) {
                        if (importFormulaDTO.getCellType() == null || importFormulaDTO.getCellType() == ImportCellType.FORM_STYLE) {
                            importFormulaDTO.setCellType(ImportCellType.getByFormulaType(type));
                        }
                        importFormulaDTO.getCellAttr().setTitle("\u516c\u5f0f" + designFormulaDefine.getCode());
                        needGenFieldImportFormulas.add(importFormulaDTO);
                        logger.debug("\u6807\u8bb0\u516c\u5f0f\u6307\u6807\u7c7b\u578b\uff1a{}", (Object)importFormulaDTO.getCellType().getTitle());
                    }
                    String formula = expression.interpret((IContext)parseFormulaContext, Language.FORMULA, null);
                    logger.debug("\u516c\u5f0f\u3010{}\u3011\u89e3\u6790\u6210\u529f\uff0c\u7ed3\u679c\u3010{}\u3011\uff0c\u7c7b\u578b\u3010{}\u3011\uff0c\u5750\u6807\u3010{}\u3011", designFormulaDefine.getExpression(), formula, expression.getType((IContext)parseFormulaContext), formulaEntry.getKey());
                    designFormulaDefine.setExpression(String.format("[%s]=%s", formulaEntry.getKey(), formula));
                    designFormulaDefine.setSyntax(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION.getValue());
                    reverseResultDTO.getParsedFormulas().add(designFormulaDefine);
                }
                catch (Exception e) {
                    logger.error("\u516c\u5f0f\u3010{}\u3011\u89e3\u6790\u5931\u8d25\uff0c\u5750\u6807\u3010{}\u3011\uff0c\u539f\u56e0\uff1a{}", formulaEntry.getValue().getExpression(), formulaEntry.getKey(), e.getMessage());
                    if (importFormulaDTO != null) {
                        designFormulaDefine.setExpression(String.format("%s=%s", importFormulaDTO.getAddress(), designFormulaDefine.getExpression()));
                        designFormulaDefine.setSyntax(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION.getValue());
                        if (Boolean.TRUE.equals(importFormulaDTO.getNeedReBuild())) {
                            if (importFormulaDTO.getCellType() == null || importFormulaDTO.getCellType() == ImportCellType.FORM_STYLE) {
                                importFormulaDTO.setCellType(ImportCellType.BIG_DECIMAL);
                            }
                            needGenFieldImportFormulas.add(importFormulasMap.get(formulaPos));
                            logger.debug("\u6807\u8bb0\u516c\u5f0f\u6307\u6807\u7c7b\u578b\uff1a{}", (Object)importFormulaDTO.getCellType().getTitle());
                        }
                    }
                    reverseResultDTO.getParsedFormulas().add(designFormulaDefine);
                    List<FormulaParseResult> checkResult = analysisResult.getCheckResult(sheet);
                    checkResult.add(new FormulaParseResult(formulaEntry.getKey(), designFormulaDefine.getExpression(), e.getMessage()));
                }
            }
            logger.debug("\u3010{}\u3011\u4e2d\u7684\u516c\u5f0f\u89e3\u6790\u5b8c\u6210", (Object)sheet);
            this.doGenerateFieldAfterFormula(needGenFieldImportFormulas, reverseModelDataProvider, sheet, formImportDTO);
        }
    }

    private void doGenerateFieldAfterFormula(List<ImportFormulaDTO> needGenFieldImportFormulas, ExcelReverseModelDataProvider reverseModelDataProvider, String sheet, FormImportDTO formImportDTO) {
        if (!CollectionUtils.isEmpty(needGenFieldImportFormulas)) {
            logger.debug("\u68c0\u6d4b\u5230\u3010{}\u3011\u4e2d{}\u4e2a\u6307\u6807\u56e0\u516c\u5f0f\u7c7b\u578b\u786e\u5b9a\uff0c\u9700\u8981\u4fee\u6539\u6307\u6807\u7c7b\u578b", (Object)sheet, (Object)needGenFieldImportFormulas.size());
            reverseModelDataProvider.changeImportContext(sheet);
            FormImportContext currentContext = reverseModelDataProvider.getCurrentContext();
            currentContext.setLinkCells(Collections.emptyList());
            currentContext.setDropDownCells(Collections.emptyList());
            currentContext.setFormulaCells(needGenFieldImportFormulas);
            this.doGenerateField(formImportDTO, reverseModelDataProvider);
            logger.debug("\u6307\u6807\u7c7b\u578b\u4fee\u6539\u5b8c\u6210");
        }
    }

    private void buildFormImportContext(FormImportDTO formImportDTO, FormImportContext formImportContext) {
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formImportDTO.getFormSchemeKey());
        DesignTaskDefine task = this.designTimeViewController.getTask(formSchemeDefine.getTaskKey());
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(task.getDataScheme());
        DesignFormGroupDefine formGroup = this.designTimeViewController.getFormGroup(formImportDTO.getFormGroupKey());
        formImportContext.setDataScheme(dataScheme);
        formImportContext.setFormSchemeDefine(formSchemeDefine);
        formImportContext.setGroupDefine(formGroup);
    }

    private Workbook createWorkBook(InputStream ins) {
        try {
            ZipSecureFile.setMinInflateRatio(0.002);
            return WorkbookFactory.create(ins);
        }
        catch (IOException e) {
            throw new FormRuntimeException("Excel\u89e3\u6790\u5931\u8d25", e);
        }
    }

    private void checkCode(String title, String code) {
        if (StringUtils.isNotEmpty((String)code) && !Pattern.compile(CODE_CHECK_CHAR).matcher(code).matches()) {
            throw new FormRuntimeException(title + " \u8868 \u7684 code \u4e0d\u7b26\u5408\u89c4\u8303");
        }
    }

    private void checkBeforeImport(FormImportDTO formImportInfo) {
        if (CollectionUtils.isEmpty(formImportInfo.getFileKeys())) {
            throw new FormRuntimeException("\u9009\u62e9\u5bfc\u5165\u7684\u6587\u4ef6\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9");
        }
        ProgressItem progress = this.getProgress(formImportInfo.getTaskKey());
        if (progress != null && !progress.isFinished()) {
            throw new RuntimeException("\u8be5\u4efb\u52a1\u6709\u6b63\u5728\u6267\u884c\u7684\u5bfc\u5165\u4efb\u52a1\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5");
        }
        this.removeProgress(formImportInfo.getTaskKey());
        this.clearCache(formImportInfo);
    }

    private void checkAnalysisResult(List<FormImportAnalysisDTO> analysisList) {
        HashSet<String> titleSet = new HashSet<String>();
        for (FormImportAnalysisDTO analysis : analysisList) {
            Set<Map<String, String>> formCodeMap = analysis.getSheetMap().keySet();
            for (Map<String, String> map : formCodeMap) {
                if (titleSet.add(map.get(FORM_TITLE_KEY))) continue;
                throw new FormRuntimeException(String.format("\u9875\u7b7e[%s]\u540d\u79f0\u91cd\u590d", map.get(FORM_TITLE_KEY)));
            }
        }
    }

    private void updateProgressItem(ProgressItem progressItem, String message, int currentProgress) {
        if (progressItem == null) {
            return;
        }
        progressItem.setCurrentProgess(currentProgress);
        progressItem.setMessage(message);
        this.setProgress(progressItem);
    }

    private void createBaseData(String name, String title) {
        BaseDataDefineDTO baseDataDefine = new BaseDataDefineDTO();
        baseDataDefine.setName(name);
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(baseDataDefine);
        if (baseDataDefineDO == null) {
            baseDataDefine.setStructtype(Integer.valueOf(0));
            baseDataDefine.setSharetype(Integer.valueOf(0));
            baseDataDefine.setTitle(title);
            R result = this.baseDataDefineClient.add(baseDataDefine);
            if (result != null && !result.isEmpty() && result.getCode() != 0) {
                logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49:{}[{}] \u521b\u5efa\u5931\u8d25", (Object)title, (Object)name);
            }
        }
    }

    private void mergeBaseData(String name, List<BaseDataDTO> baseDataList) {
        BaseDataDTO baseDataDto = new BaseDataDTO();
        baseDataDto.setTableName(name);
        baseDataDto.setBaseDataCodes(null);
        baseDataDto.setRecoveryflag(Integer.valueOf(0));
        BaseDataBatchOptDTO batchOption = new BaseDataBatchOptDTO();
        batchOption.setQueryParam(baseDataDto);
        batchOption.setDataList(new ArrayList<BaseDataDTO>(baseDataList));
        batchOption.setHighTrustability(true);
        R sync = this.baseDataClient.sync(batchOption);
        if (sync.getCode() != 0) {
            logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49[{}]\u4e0b\u57fa\u7840\u6570\u636e\u65b0\u589e\u5931\u8d25", (Object)name);
        }
    }

    @Override
    public void saveImportData(FormImportDTO formImportDTO) {
        Cache.ValueWrapper valueWrapper = this.nedisCache.hGet(formImportDTO.getTaskKey(), (Object)CACHE_HASH_DATA);
        FormImportResult formImportResult = (FormImportResult)valueWrapper.get();
        if (formImportResult == null) {
            return;
        }
        this.saveImportData(formImportResult);
        this.clearCache(formImportDTO);
    }

    private void clearCache(FormImportDTO formImportDTO) {
        this.nedisCache.evict(formImportDTO.getTaskKey());
    }

    @Override
    public ProgressItem getProgress(String progressId) {
        Cache.ValueWrapper valueWrapper = this.nedisCache.hGet(progressId, (Object)CACHE_HASH_PROGRESS);
        if (valueWrapper != null) {
            return (ProgressItem)valueWrapper.get();
        }
        return null;
    }

    @Override
    public void setProgress(ProgressItem progressItem) {
        this.nedisCache.hSet(progressItem.getProgressId(), (Object)CACHE_HASH_PROGRESS, (Object)progressItem);
    }

    @Override
    public void removeProgress(String progressId) {
        this.nedisCache.evict(progressId);
    }

    private Grid2Data createGrid2Data(int row, int col, int rowHeight, int colWidth) {
        int i;
        Grid2Data grid2Data = new Grid2Data();
        grid2Data.setRowCount(row);
        grid2Data.setColumnCount(col);
        for (i = 0; i < row; ++i) {
            grid2Data.setRowHeight(i, rowHeight);
        }
        for (i = 1; i < col; ++i) {
            grid2Data.setColumnWidth(i, colWidth);
        }
        grid2Data.setColumnWidth(0, 36);
        for (int i2 = 0; i2 < row; ++i2) {
            for (int j = 0; j < col; ++j) {
                GridCellData cellData = grid2Data.getGridCellData(j, i2);
                cellData.setFontSize(14);
                cellData.setFontName("\u5b8b\u4f53");
                cellData.setForeGroundColor(Integer.parseInt("494949", 16));
            }
        }
        return grid2Data;
    }
}

