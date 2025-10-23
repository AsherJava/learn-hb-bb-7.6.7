/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 */
package com.jiuqi.nr.task.i18n.workshop;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormulaSchemeDTO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.common.I18nLanguageType;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import com.jiuqi.nr.task.i18n.provider.I18nServiceProvider;
import com.jiuqi.nr.task.i18n.workshop.I18nWorkShop;
import java.util.ArrayList;
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

public class I18nFormulaSchemeWorkShop
extends I18nWorkShop {
    private static final Logger logger = LoggerFactory.getLogger(I18nFormulaSchemeWorkShop.class);
    private static final I18nResourceType currentResourceType = I18nResourceType.FORMULA_SCHEME_TITLE;
    private IDesignTimeViewController designTimeViewController;
    private IDesignTimeFormulaController designTimeFormulaController;
    private DesParamLanguageDao dao;

    public I18nFormulaSchemeWorkShop(I18nServiceProvider serviceProvider) {
        this.designTimeViewController = serviceProvider.getDesignTimeViewController();
        this.designTimeFormulaController = serviceProvider.getDesignTimeFormulaController();
        this.dao = serviceProvider.getDesParamLanguageDao();
    }

    @Override
    public List<? extends I18nBaseDTO> produce(I18nQueryVO queryVO) throws I18nException {
        List formulaSchemes = this.designTimeFormulaController.listFormulaSchemeByFormScheme(queryVO.getFormSchemeKey());
        return this.convert(formulaSchemes);
    }

    private List<I18nFormulaSchemeDTO> convert(List<DesignFormulaSchemeDefine> formulaSchemes) throws I18nException {
        ArrayList<I18nFormulaSchemeDTO> result = new ArrayList<I18nFormulaSchemeDTO>();
        Map<String, DesParamLanguage> languageMap = this.getLanguageMap(formulaSchemes, this.dao, I18nResourceType.FORMULA_SCHEME_TITLE);
        for (DesignFormulaSchemeDefine formulaScheme : formulaSchemes) {
            DesParamLanguage language = languageMap.get(formulaScheme.getKey());
            I18nFormulaSchemeDTO formulaSchemeDTO = new I18nFormulaSchemeDTO(this.initDTO(language));
            formulaSchemeDTO.setFormSchemeKey(formulaScheme.getFormSchemeKey());
            formulaSchemeDTO.setFormulaSchemeKey(formulaScheme.getKey());
            formulaSchemeDTO.setDefaultLanguageInfo(formulaScheme.getTitle());
            result.add(formulaSchemeDTO);
        }
        return result;
    }

    @Override
    public I18nInitExtendResultVO buildCondition(I18nInitExtendQueryVO conditionQueryVO) {
        return this.buildCommonResultVO(conditionQueryVO, this.designTimeViewController);
    }

    @Override
    public List<I18nColsObj> buildCols() {
        List<I18nColsObj> cols = this.initCols();
        cols.add(this.buildColsObj("defaultLanguageInfo", "\u4e2d\u6587\u6807\u9898", false, false, 400));
        cols.add(this.buildColsObj("otherLanguageInfo", "\u82f1\u6587\u6807\u9898", true, false, 500));
        return cols;
    }

    @Override
    public void save(I18nResultVO resultVO) {
        ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
        ArrayList<String> deleteLanguages = new ArrayList<String>();
        try {
            for (I18nBaseDTO i18nBaseDTO : resultVO.getDatas()) {
                if (!(i18nBaseDTO instanceof I18nFormulaSchemeDTO)) continue;
                if (this.needDelete(i18nBaseDTO)) {
                    deleteLanguages.add(i18nBaseDTO.getLanguageKey());
                }
                if (!this.needAdd(i18nBaseDTO)) continue;
                I18nFormulaSchemeDTO formulaSchemeTruly = (I18nFormulaSchemeDTO)i18nBaseDTO;
                DesParamLanguage language = this.buildDesParamLanguage(i18nBaseDTO, resultVO);
                language.setResourceKey(formulaSchemeTruly.getFormulaSchemeKey());
                updateLanguages.add(language);
            }
            this.dao.batchDelete(deleteLanguages.toArray(new String[deleteLanguages.size()]));
            this.dao.batchInsert(updateLanguages.toArray(new DesParamLanguage[updateLanguages.size()]));
        }
        catch (Exception e) {
            throw new RuntimeException("\u516c\u5f0f\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u914d\u7f6e\u4fdd\u5b58\u5931\u8d25", e);
        }
    }

    @Override
    public void dataImport(Workbook workbook, DesignTaskDefine task) throws I18nException {
        logger.info("\u5f00\u59cb\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u8bbe\u7f6e", (Object)task.getTitle());
        try {
            ArrayList<String> needDeleteFormulaSchemeWithLanguages = new ArrayList<String>();
            ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
            List formSchemes = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            Map<String, DesignFormSchemeDefine> schemeCodeMap = formSchemes.stream().collect(Collectors.toMap(FormSchemeDefine::getFormSchemeCode, v -> v));
            List<I18nColsObj> cols = this.buildCols();
            for (int i = 0; i < workbook.getNumberOfSheets(); ++i) {
                Sheet sheet = workbook.getSheetAt(i);
                if (!this.checkSheet(sheet)) {
                    logger.error("\u9875\u7b7e[{}]\u5185\u5bb9\u6821\u9a8c\u5931\u8d25\uff0c\u8bf7\u6309\u7167\u5bfc\u51fa\u65f6\u5185\u5bb9\u5bfc\u5165", (Object)sheet.getSheetName());
                    continue;
                }
                String formSchemeCode = this.getResourceCodeFromSheet(sheet);
                DesignFormSchemeDefine formScheme = schemeCodeMap.get(formSchemeCode);
                if (formScheme == null) {
                    logger.error("{}\u9875\u7b7e\u4e0b\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230", (Object)sheet.getSheetName());
                    continue;
                }
                List formulaSchemes = this.designTimeFormulaController.listFormulaSchemeByFormScheme(schemeCodeMap.get(formSchemeCode).getKey());
                Map<String, DesignFormulaSchemeDefine> formulaSchemeTitleMap = formulaSchemes.stream().collect(Collectors.toMap(IBaseMetaItem::getTitle, v -> v));
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue;
                    String formulaSchemeTitle = this.getCellValueWithImport(row, 0);
                    String languageInfo = this.getCellValueWithImport(row, cols.size() - 1);
                    if (!formulaSchemeTitleMap.containsKey(formulaSchemeTitle)) continue;
                    needDeleteFormulaSchemeWithLanguages.add(formulaSchemeTitleMap.get(formulaSchemeTitle).getKey());
                    updateLanguages.add(this.buildLanguageObj(languageInfo, I18nResourceType.FORMULA_SCHEME_TITLE.getValue(), I18nLanguageType.ENGLISH.getValue(), formulaSchemeTitleMap.get(formulaSchemeTitle).getKey()));
                }
            }
            logger.info("\u5220\u9664\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doDelete(this.dao, needDeleteFormulaSchemeWithLanguages));
            logger.info("\u65b0\u589e\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doInsert(this.dao, updateLanguages));
            logger.info("\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u8bbe\u7f6e\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u516c\u5f0f\u65b9\u6848\u6807\u9898\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
            throw new I18nException("\u516c\u5f0f\u65b9\u6848\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
        }
    }

    private boolean checkSheet(Sheet sheet) {
        Row row = sheet.getRow(0);
        String schemeTitleCell = this.getCellValueWithImport(row, 0);
        String otherLanguageInfoCell = this.getCellValueWithImport(row, this.buildCols().size() - 1);
        return "\u4e2d\u6587\u6807\u9898".equals(schemeTitleCell) && "\u82f1\u6587\u6807\u9898".equals(otherLanguageInfoCell);
    }

    @Override
    public void dataExport(I18nExportParam exportParam) throws I18nException {
        DesignTaskDefine task = exportParam.getTask();
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00", (Object)task.getTitle());
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            logger.info("\u5171\u6709{}\u4e2a\u62a5\u8868\u65b9\u6848", (Object)formSchemeDefines.size());
            for (DesignFormSchemeDefine formScheme : formSchemeDefines) {
                List formulaSchemes = this.designTimeFormulaController.listFormulaSchemeByFormScheme(formScheme.getKey());
                Map<String, DesParamLanguage> languageMap = this.getLanguageMap(formulaSchemes, this.dao, I18nResourceType.FORMULA_SCHEME_TITLE);
                List<I18nColsObj> cols = this.buildCols();
                Sheet sheet = this.createSheet(workbook, this.getFormSchemeSheetName(formScheme), cols);
                int colNumber = cols.size();
                int rowNumber = formulaSchemes.size() + 1;
                for (int row = 1; row < rowNumber; ++row) {
                    Row rowData = sheet.createRow(row);
                    rowData.setHeightInPoints(25.0f);
                    for (int col = 0; col < colNumber; ++col) {
                        Cell cell = rowData.createCell(col);
                        cell.setCellValue(this.getCellValue(row - 1, col, formulaSchemes, languageMap, cols));
                    }
                }
            }
            this.writeToZip(exportParam, this.getFileName(currentResourceType), workbook);
            logger.info("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u5931\u8d25", (Object)task.getTitle());
            throw new I18nException("\u516c\u5f0f\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u5bfc\u51fa\u5931\u8d25", e);
        }
    }

    private String getCellValue(int row, int col, List<DesignFormulaSchemeDefine> formulaSchemes, Map<String, DesParamLanguage> languageMap, List<I18nColsObj> cols) {
        String cellValue = "";
        I18nBaseObj colsObj = cols.get(col);
        DesignFormulaSchemeDefine formulaScheme = formulaSchemes.get(row);
        switch (colsObj.getKey()) {
            case "defaultLanguageInfo": {
                cellValue = formulaScheme.getTitle();
                break;
            }
            case "otherLanguageInfo": {
                cellValue = this.getOtherLanguageInfo(formulaScheme.getKey(), languageMap);
                break;
            }
        }
        return cellValue;
    }
}

