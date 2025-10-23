/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 */
package com.jiuqi.nr.task.i18n.workshop;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormSchemeDTO;
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

public class I18nFormSchemeWorkShop
extends I18nWorkShop {
    private static final Logger logger = LoggerFactory.getLogger(I18nFormSchemeWorkShop.class);
    private static final I18nResourceType currentResourceType = I18nResourceType.FORM_SCHEME_TITLE;
    private IDesignTimeViewController designTimeViewController;
    private DesParamLanguageDao dao;
    private final String I18N_FORM_SCHEME_CODE_COL_KEY = "formSchemeCode";

    public I18nFormSchemeWorkShop(I18nServiceProvider serviceProvider) {
        this.designTimeViewController = serviceProvider.getDesignTimeViewController();
        this.dao = serviceProvider.getDesParamLanguageDao();
    }

    @Override
    public List<? extends I18nBaseDTO> produce(I18nQueryVO queryVO) throws I18nException {
        List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(queryVO.getTask());
        return this.convert(formSchemeDefines);
    }

    private List<I18nFormSchemeDTO> convert(List<DesignFormSchemeDefine> formSchemes) throws I18nException {
        ArrayList<I18nFormSchemeDTO> result = new ArrayList<I18nFormSchemeDTO>();
        Map<String, DesParamLanguage> languageMap = this.getLanguageMap(formSchemes, this.dao, I18nResourceType.FORM_SCHEME_TITLE);
        for (DesignFormSchemeDefine formScheme : formSchemes) {
            DesParamLanguage language = languageMap.get(formScheme.getKey());
            I18nFormSchemeDTO formSchemeDTO = new I18nFormSchemeDTO(this.initDTO(language));
            formSchemeDTO.setTaskKey(formScheme.getTaskKey());
            formSchemeDTO.setFormSchemeKey(formScheme.getKey());
            formSchemeDTO.setFormSchemeCode(formScheme.getFormSchemeCode());
            formSchemeDTO.setDefaultLanguageInfo(formScheme.getTitle());
            result.add(formSchemeDTO);
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
        cols.add(this.buildColsObj("formSchemeCode", "\u62a5\u8868\u65b9\u6848\u6807\u8bc6", false, false, 200));
        cols.add(this.buildColsObj("defaultLanguageInfo", "\u4e2d\u6587\u6807\u9898", false, false, 400));
        cols.add(this.buildColsObj("otherLanguageInfo", "\u82f1\u6587\u6807\u9898", true, false, 600));
        return cols;
    }

    @Override
    public void save(I18nResultVO resultVO) {
        ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
        ArrayList<String> deleteLanguages = new ArrayList<String>();
        try {
            for (I18nBaseDTO i18nBaseDTO : resultVO.getDatas()) {
                if (!(i18nBaseDTO instanceof I18nFormSchemeDTO)) continue;
                if (this.needDelete(i18nBaseDTO)) {
                    deleteLanguages.add(i18nBaseDTO.getLanguageKey());
                }
                if (!this.needAdd(i18nBaseDTO)) continue;
                I18nFormSchemeDTO formSchemeTruly = (I18nFormSchemeDTO)i18nBaseDTO;
                DesParamLanguage paramLanguage = this.buildDesParamLanguage(i18nBaseDTO, resultVO);
                paramLanguage.setResourceKey(formSchemeTruly.getFormSchemeKey());
                updateLanguages.add(paramLanguage);
            }
            this.dao.batchDelete(deleteLanguages.toArray(new String[deleteLanguages.size()]));
            this.dao.batchInsert(updateLanguages.toArray(new DesParamLanguage[updateLanguages.size()]));
        }
        catch (Exception e) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u914d\u7f6e\u4fdd\u5b58\u5931\u8d25", e);
        }
    }

    @Override
    public void dataImport(Workbook workbook, DesignTaskDefine task) throws I18nException {
        logger.info("\u5f00\u59cb\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u62a5\u8868\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u8bbe\u7f6e", (Object)task.getTitle());
        try {
            ArrayList<String> needDeleteFormSchemeWithLanguages = new ArrayList<String>();
            ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
            Sheet sheet = workbook.getSheetAt(0);
            if (!this.checkSheet(sheet)) {
                logger.error("\u9875\u7b7e[{}]\u5185\u5bb9\u6821\u9a8c\u5931\u8d25\uff0c\u8bf7\u6309\u7167\u5bfc\u51fa\u65f6\u5185\u5bb9\u5bfc\u5165", (Object)sheet.getSheetName());
                return;
            }
            String taskCode = this.getResourceCodeFromSheet(sheet);
            if (!task.getTaskCode().equals(taskCode)) {
                logger.error("\u9875\u7b7e\u89e3\u6790\u5931\u8d25");
                return;
            }
            List formSchemes = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            Map<String, DesignFormSchemeDefine> formSchemeCodeMap = formSchemes.stream().collect(Collectors.toMap(FormSchemeDefine::getFormSchemeCode, v -> v));
            List<I18nColsObj> cols = this.buildCols();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String formSchemeCode = this.getCellValueWithImport(row, 0);
                String languageInfo = this.getCellValueWithImport(row, cols.size() - 1);
                if (!formSchemeCodeMap.containsKey(formSchemeCode)) continue;
                needDeleteFormSchemeWithLanguages.add(formSchemeCodeMap.get(formSchemeCode).getKey());
                updateLanguages.add(this.buildLanguageObj(languageInfo, I18nResourceType.FORM_SCHEME_TITLE.getValue(), I18nLanguageType.ENGLISH.getValue(), formSchemeCodeMap.get(formSchemeCode).getKey()));
            }
            logger.info("\u5220\u9664\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doDelete(this.dao, needDeleteFormSchemeWithLanguages));
            logger.info("\u65b0\u589e\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doInsert(this.dao, updateLanguages));
            logger.info("\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u62a5\u8868\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u8bbe\u7f6e\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
            throw new I18nException("\u62a5\u8868\u65b9\u6848\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
        }
    }

    private boolean checkSheet(Sheet sheet) {
        Row row = sheet.getRow(0);
        String schemeCodeCell = this.getCellValueWithImport(row, 0);
        String otherLanguageInfoCell = this.getCellValueWithImport(row, this.buildCols().size() - 1);
        return "\u62a5\u8868\u65b9\u6848\u6807\u8bc6".equals(schemeCodeCell) && "\u82f1\u6587\u6807\u9898".equals(otherLanguageInfoCell);
    }

    @Override
    public void dataExport(I18nExportParam exportParam) throws I18nException {
        DesignTaskDefine task = exportParam.getTask();
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u62a5\u8868\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00", (Object)task.getTitle());
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            Map<String, DesParamLanguage> languageMap = this.getLanguageMap(formSchemeDefines, this.dao, I18nResourceType.FORM_SCHEME_TITLE);
            String sheetTitle = task.getTitle() + '_' + task.getTaskCode();
            List<I18nColsObj> cols = this.buildCols();
            Sheet sheet = this.createSheet(workbook, sheetTitle, cols);
            int colNumber = cols.size();
            int rowNumber = formSchemeDefines.size() + 1;
            for (int row = 1; row < rowNumber; ++row) {
                Row rowData = sheet.createRow(row);
                rowData.setHeightInPoints(25.0f);
                for (int col = 0; col < colNumber; ++col) {
                    Cell cell = rowData.createCell(col);
                    cell.setCellValue(this.getCellValue(row - 1, col, formSchemeDefines, languageMap, cols));
                }
            }
            this.writeToZip(exportParam, this.getFileName(currentResourceType), workbook);
            logger.info("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u62a5\u8868\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u62a5\u8868\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u5931\u8d25", (Object)task.getTitle());
            throw new I18nException("\u62a5\u8868\u65b9\u6848\u6807\u9898\u591a\u8bed\u8a00\u5bfc\u51fa\u5931\u8d25", e);
        }
    }

    private String getCellValue(int row, int col, List<DesignFormSchemeDefine> formSchemes, Map<String, DesParamLanguage> languageMap, List<I18nColsObj> cols) {
        String cellValue = "";
        I18nBaseObj colsObj = cols.get(col);
        DesignFormSchemeDefine formScheme = formSchemes.get(row);
        switch (colsObj.getKey()) {
            case "defaultLanguageInfo": {
                cellValue = formScheme.getTitle();
                break;
            }
            case "otherLanguageInfo": {
                cellValue = this.getOtherLanguageInfo(formScheme.getKey(), languageMap);
                break;
            }
            case "formSchemeCode": {
                cellValue = formScheme.getFormSchemeCode();
                break;
            }
        }
        return cellValue;
    }
}

