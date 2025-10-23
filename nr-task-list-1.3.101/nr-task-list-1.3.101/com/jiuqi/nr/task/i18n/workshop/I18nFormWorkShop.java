/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.service.DesignBigDataService
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.task.api.cellbook.CellBookInit
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.excel.CellSheetToExcel
 *  com.jiuqi.nvwa.cellbook.excel.ExcelToCellSheet
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.task.i18n.workshop;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.task.api.cellbook.CellBookInit;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormDTO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.common.I18nLanguageType;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import com.jiuqi.nr.task.i18n.factory.I18nDesignFactory;
import com.jiuqi.nr.task.i18n.provider.I18nServiceProvider;
import com.jiuqi.nr.task.i18n.service.I18nTreeService;
import com.jiuqi.nr.task.i18n.workshop.I18nWorkShop;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.excel.CellSheetToExcel;
import com.jiuqi.nvwa.cellbook.excel.ExcelToCellSheet;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class I18nFormWorkShop
extends I18nWorkShop {
    private static final Logger logger = LoggerFactory.getLogger(I18nFormWorkShop.class);
    private static final I18nResourceType currentResourceType = I18nResourceType.FORM;
    private IDesignTimeViewController designTimeViewController;
    private DesParamLanguageDao dao;
    private I18nTreeService treeService;
    private DesignBigDataService bigDataService;
    private final String I18N_FORM_CODE_COL_KEY = "formCode";
    private final String I18N_FORM_TYPE_COL_KEY = "formType";
    private final String I18N_FORM_OPERATE_COL_KEY = "operate";

    public I18nFormWorkShop(I18nServiceProvider serviceProvider) {
        this.designTimeViewController = serviceProvider.getDesignTimeViewController();
        this.dao = serviceProvider.getDesParamLanguageDao();
        this.treeService = serviceProvider.getTreeService();
        this.bigDataService = serviceProvider.getBigDataService();
    }

    @Override
    public List<? extends I18nBaseDTO> produce(I18nQueryVO queryVO) throws I18nException {
        ArrayList<DesignFormDefine> forms = new ArrayList<DesignFormDefine>();
        if ("I18N_FORM_GROUP_TREE_ROOT".equals(queryVO.getFormGroupKey())) {
            forms.addAll(this.designTimeViewController.listFormByFormScheme(queryVO.getFormSchemeKey()));
        } else {
            forms.addAll(this.designTimeViewController.listFormByGroup(queryVO.getFormGroupKey()));
        }
        return this.convert(forms, queryVO.getFormSchemeKey());
    }

    private List<I18nFormDTO> convert(List<DesignFormDefine> forms, String formSchemeKey) throws I18nException {
        ArrayList<I18nFormDTO> result = new ArrayList<I18nFormDTO>();
        Map<String, DesParamLanguage> languageMap = this.getLanguageMap(forms, this.dao, I18nResourceType.FORM);
        for (DesignFormDefine form : forms) {
            DesParamLanguage language = languageMap.get(form.getKey());
            I18nFormDTO formDTO = new I18nFormDTO(this.initDTO(language));
            formDTO.setFormSchemeKey(formSchemeKey);
            formDTO.setFormKey(form.getKey());
            formDTO.setFormCode(form.getFormCode());
            formDTO.setFormType(this.getFormType(form.getFormType().getValue()));
            formDTO.setDefaultLanguageInfo(form.getTitle());
            formDTO.setDesign(I18nDesignFactory.getDesign(form.getFormType().getValue()));
            result.add(formDTO);
        }
        return result;
    }

    private String getFormType(int formTypeValue) {
        String type = "";
        switch (formTypeValue) {
            case 0: {
                type = "\u56fa\u5b9a\u8868";
                break;
            }
            case 1: {
                type = "\u6d6e\u52a8\u8868";
                break;
            }
            case 5: {
                type = "\u95ee\u5377";
                break;
            }
            case 11: {
                type = "\u5c01\u9762\u4ee3\u7801\u8868";
                break;
            }
            case 12: {
                type = "\u5d4c\u5165\u5f0f\u5206\u6790\u8868";
                break;
            }
            case 13: {
                type = "\u53f0\u8d26\u8868";
                break;
            }
        }
        return type;
    }

    @Override
    public I18nInitExtendResultVO buildCondition(I18nInitExtendQueryVO conditionQueryVO) {
        I18nInitExtendResultVO commonResultVO = this.buildCommonResultVO(conditionQueryVO, this.designTimeViewController);
        if (commonResultVO != null) {
            return commonResultVO;
        }
        if (StringUtils.hasText(conditionQueryVO.getFormSchemeKey())) {
            List<UITreeNode<TreeData>> treeDatas = this.treeService.formGroupTreeLoad(conditionQueryVO.getFormSchemeKey());
            I18nInitExtendResultVO resultVO = new I18nInitExtendResultVO();
            resultVO.setTreeDatas(treeDatas);
            return resultVO;
        }
        return null;
    }

    @Override
    public List<I18nColsObj> buildCols() {
        List<I18nColsObj> cols = this.initCols();
        cols.add(this.buildColsObj("defaultLanguageInfo", "\u6807\u9898", false, false, 300));
        cols.add(this.buildColsObj("formCode", "\u6807\u8bc6", false, false, 250));
        cols.add(this.buildColsObj("formType", "\u7c7b\u578b", false, false, 100));
        cols.add(this.buildColsObj("otherLanguageInfo", "\u82f1\u6587\u6807\u9898", true, false, 500));
        cols.add(this.buildColsObj("operate", "\u64cd\u4f5c", true, true, 150));
        return cols;
    }

    private List<I18nColsObj> buildIOCols() {
        List<I18nColsObj> cols = this.buildCols();
        cols.remove(2);
        cols.remove(cols.size() - 1);
        return cols;
    }

    @Override
    public void save(I18nResultVO resultVO) throws I18nException {
        ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
        ArrayList<String> deleteLanguages = new ArrayList<String>();
        try {
            for (I18nBaseDTO i18nBaseDTO : resultVO.getDatas()) {
                if (!(i18nBaseDTO instanceof I18nFormDTO)) continue;
                if (this.needDelete(i18nBaseDTO)) {
                    deleteLanguages.add(i18nBaseDTO.getLanguageKey());
                }
                if (!this.needAdd(i18nBaseDTO)) continue;
                I18nFormDTO formTruly = (I18nFormDTO)i18nBaseDTO;
                DesParamLanguage language = this.buildDesParamLanguage(i18nBaseDTO, resultVO);
                language.setResourceKey(formTruly.getFormKey());
                updateLanguages.add(language);
            }
            this.dao.batchDelete(deleteLanguages.toArray(new String[deleteLanguages.size()]));
            this.dao.batchInsert(updateLanguages.toArray(new DesParamLanguage[updateLanguages.size()]));
        }
        catch (Exception e) {
            throw new I18nException("\u8868\u5355\u6807\u9898\u591a\u8bed\u8a00\u914d\u7f6e\u4fdd\u5b58\u5931\u8d25", e);
        }
    }

    @Override
    public void dataImport(Workbook workbook, DesignTaskDefine task) throws I18nException {
        logger.info("\u5f00\u59cb\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u8868\u5355\u591a\u8bed\u8a00\u8bbe\u7f6e", (Object)task.getTitle());
        try {
            List formSchemes = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            Map<String, DesignFormSchemeDefine> schemeCodeMap = formSchemes.stream().collect(Collectors.toMap(FormSchemeDefine::getFormSchemeCode, V -> V));
            List<I18nColsObj> cols = this.buildIOCols();
            ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
            ArrayList<String> needDeleteFormWithLanguages = new ArrayList<String>();
            for (int i = 0; i < workbook.getNumberOfSheets(); ++i) {
                Sheet sheet = workbook.getSheetAt(i);
                String formSchemeCode = this.getResourceCodeFromSheet(sheet);
                DesignFormSchemeDefine formScheme = schemeCodeMap.get(formSchemeCode);
                if (formScheme == null) {
                    String sheetName = sheet.getSheetName();
                    String formStyleFormSchemeCode = sheetName.split("_")[0];
                    DesignFormSchemeDefine designFormSchemeDefine = schemeCodeMap.get(formStyleFormSchemeCode);
                    if (designFormSchemeDefine == null) {
                        logger.error("{}\u9875\u7b7e\u4e0b\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230", (Object)sheet.getSheetName());
                        continue;
                    }
                    this.importFormStyle(workbook, designFormSchemeDefine);
                    return;
                }
                if (!this.checkSheet(sheet)) {
                    logger.error("\u9875\u7b7e[{}]\u5185\u5bb9\u6821\u9a8c\u5931\u8d25\uff0c\u8bf7\u6309\u7167\u5bfc\u51fa\u65f6\u5185\u5bb9\u5bfc\u5165", (Object)sheet.getSheetName());
                    continue;
                }
                List forms = this.designTimeViewController.listFormByFormScheme(formScheme.getKey());
                Map<String, DesignFormDefine> formCodeMap = forms.stream().collect(Collectors.toMap(FormDefine::getFormCode, v -> v));
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue;
                    String formCode = this.getCellValueWithImport(row, 1);
                    String languageInfo = this.getCellValueWithImport(row, cols.size() - 1);
                    if (!formCodeMap.containsKey(formCode)) continue;
                    needDeleteFormWithLanguages.add(formCodeMap.get(formCode).getKey());
                    updateLanguages.add(this.buildLanguageObj(languageInfo, I18nResourceType.FORM.getValue(), I18nLanguageType.ENGLISH.getValue(), formCodeMap.get(formCode).getKey()));
                }
            }
            logger.info("\u5220\u9664\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doDelete(this.dao, needDeleteFormWithLanguages));
            logger.info("\u65b0\u589e\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doInsert(this.dao, updateLanguages));
            logger.info("\u4efb\u52a1[{}]\u7684\u8868\u5355\u6807\u9898\u591a\u8bed\u8a00\u8bbe\u7f6e\u5bfc\u5165\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u8868\u5355\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
            throw new I18nException("\u8868\u5355\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
        }
    }

    private boolean checkSheet(Sheet sheet) {
        Row row = sheet.getRow(0);
        String codeCell = this.getCellValueWithImport(row, 1);
        String otherLanguageInfoCell = this.getCellValueWithImport(row, this.buildIOCols().size() - 1);
        return "\u6807\u8bc6".equals(codeCell) && "\u82f1\u6587\u6807\u9898".equals(otherLanguageInfoCell);
    }

    @Override
    public void dataExport(I18nExportParam exportParam) throws I18nException {
        DesignTaskDefine task = exportParam.getTask();
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u8868\u5355\u591a\u8bed\u8a00", (Object)task.getTitle());
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            List formSchemes = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            logger.info("\u5171\u6709{}\u4e2a\u62a5\u8868\u65b9\u6848", (Object)formSchemes.size());
            for (DesignFormSchemeDefine formScheme : formSchemes) {
                List forms = this.designTimeViewController.listFormByFormScheme(formScheme.getKey());
                Map<String, DesParamLanguage> languageMap = this.getLanguageMap(forms, this.dao, I18nResourceType.FORM);
                List<I18nColsObj> cols = this.buildIOCols();
                Sheet sheet = this.createSheet(workbook, this.getFormSchemeSheetName(formScheme), cols);
                int colNumber = cols.size();
                int rowNumber = forms.size() + 1;
                for (int row = 1; row < rowNumber; ++row) {
                    Row rowData = sheet.createRow(row);
                    rowData.setHeightInPoints(25.0f);
                    for (int col = 0; col < colNumber; ++col) {
                        Cell cell = rowData.createCell(col);
                        cell.setCellValue(this.getCellValue(row - 1, col, forms, languageMap, cols));
                    }
                }
                this.exportFormStyle(exportParam, forms, formScheme);
            }
            this.writeToZip(exportParam, this.getFileName(I18nResourceType.FORM_TITLE), workbook);
            logger.info("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u8868\u5355\u591a\u8bed\u8a00\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u8868\u5355\u591a\u8bed\u8a00\u5931\u8d25", (Object)task.getTitle());
            throw new I18nException("\u5bfc\u51fa\u8868\u5355\u591a\u8bed\u8a00\u5931\u8d25", e);
        }
    }

    private String getCellValue(int row, int col, List<DesignFormDefine> forms, Map<String, DesParamLanguage> languageMap, List<I18nColsObj> cols) {
        String cellValue = "";
        I18nBaseObj colsObj = cols.get(col);
        DesignFormDefine form = forms.get(row);
        switch (colsObj.getKey()) {
            case "defaultLanguageInfo": {
                cellValue = form.getTitle();
                break;
            }
            case "otherLanguageInfo": {
                cellValue = this.getOtherLanguageInfo(form.getKey(), languageMap);
                break;
            }
            case "formCode": {
                cellValue = form.getFormCode();
                break;
            }
        }
        return cellValue;
    }

    private void exportFormStyle(I18nExportParam exportParam, List<DesignFormDefine> forms, DesignFormSchemeDefine formSchemeDefine) throws I18nException {
        logger.info("\u5bfc\u51fa\u62a5\u8868\u65b9\u6848[{}]\u7684\u8868\u6837\u591a\u8bed\u8a00", (Object)formSchemeDefine.getFormSchemeCode());
        Map<String, DesignFormDefine> formMap = forms.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
        HashMap<String, byte[]> formStyleMap = new HashMap<String, byte[]>();
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            for (String formKey : formMap.keySet()) {
                byte[] bytes = this.bigDataService.queryBigDataDefine(formKey, "FORM_DATA", 2);
                if (bytes == null) continue;
                formStyleMap.put(formKey, bytes);
            }
            if (formStyleMap.isEmpty()) {
                logger.info("\u62a5\u8868\u65b9\u6848[{}]\u4e0d\u5b58\u5728\u8868\u6837\u591a\u8bed\u8a00", (Object)formSchemeDefine.getFormSchemeCode());
                return;
            }
            CellSheetToExcel cellSheetToExcel = null;
            for (String form : formStyleMap.keySet()) {
                byte[] formStyle = (byte[])formStyleMap.get(form);
                Grid2Data styleData = Grid2Data.bytesToGrid((byte[])formStyle);
                String sheetName = formSchemeDefine.getFormSchemeCode() + '_' + formMap.get(form).getTitle();
                styleData.deleteRows(0, 1);
                styleData.deleteColumns(0, 1);
                CellBook cellBook = CellBookInit.init();
                CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)styleData, (CellBook)cellBook, (String)sheetName);
                if (cellSheetToExcel == null) {
                    cellSheetToExcel = new CellSheetToExcel(workbook);
                }
                cellBook.getSheets().forEach(arg_0 -> ((CellSheetToExcel)cellSheetToExcel).writeToExcel(arg_0));
            }
            this.writeToZip(exportParam, I18nResourceType.FORM_STYLE.toString() + "-" + formSchemeDefine.getFormSchemeCode() + ".xlsx", workbook);
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u62a5\u8868\u65b9\u6848[{}]\u7684\u8868\u6837\u591a\u8bed\u8a00\u5931\u8d25", (Object)formSchemeDefine.getFormSchemeCode());
            throw new I18nException("\u5bfc\u51fa\u8868\u6837\u591a\u8bed\u8a00\u5931\u8d25", e);
        }
    }

    private void importFormStyle(Workbook workbook, DesignFormSchemeDefine formSchemeDefine) throws I18nException {
        try {
            logger.info("\u5f00\u59cb\u5bfc\u5165\u62a5\u8868\u65b9\u6848{}\u7684\u591a\u8bed\u8a00\u8868\u6837", (Object)formSchemeDefine.getFormSchemeCode());
            List forms = this.designTimeViewController.listFormByFormScheme(formSchemeDefine.getKey());
            Map<String, DesignFormDefine> formMap = forms.stream().collect(Collectors.toMap(IBaseMetaItem::getTitle, v -> v));
            HashMap<String, byte[]> needUpdateFormStyle = new HashMap<String, byte[]>();
            ExcelToCellSheet excelImportUtil = new ExcelToCellSheet(workbook);
            for (int i = 0; i < workbook.getNumberOfSheets(); ++i) {
                Sheet sheet = workbook.getSheetAt(i);
                String formTitle = sheet.getSheetName().split("_")[1];
                DesignFormDefine form = formMap.get(formTitle);
                if (form == null) continue;
                CellBook cellBook = CellBookInit.init();
                Grid2Data grid2Data = new Grid2Data();
                CellSheet cellSheet = excelImportUtil.addCellSheet(sheet.getSheetName(), cellBook);
                CellBookGrid2dataConverter.cellBookToGrid2Data((CellSheet)cellSheet, (Grid2Data)grid2Data);
                grid2Data.insertRows(0, 1);
                grid2Data.insertColumns(0, 1);
                byte[] bytes = Grid2Data.gridToBytes((Grid2Data)grid2Data);
                needUpdateFormStyle.put(form.getKey(), bytes);
            }
            if (!needUpdateFormStyle.isEmpty()) {
                for (String formKey : needUpdateFormStyle.keySet()) {
                    this.bigDataService.updateBigDataDefine(formKey, "FORM_DATA", 2, (byte[])needUpdateFormStyle.get(formKey));
                }
            }
            logger.info("\u62a5\u8868\u65b9\u6848{}\u7684\u591a\u8bed\u8a00\u8868\u6837\u5bfc\u5165\u5b8c\u6210", (Object)formSchemeDefine.getFormSchemeCode());
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u62a5\u8868\u65b9\u6848{}\u7684\u591a\u8bed\u8a00\u8868\u6837\u5931\u8d25", (Object)formSchemeDefine.getFormSchemeCode());
            throw new I18nException("\u5bfc\u5165\u591a\u8bed\u8a00\u8868\u6837\u5931\u8d25", e);
        }
    }
}

