/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 */
package com.jiuqi.nr.task.i18n.workshop;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.dto.I18nTaskDTO;
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
import org.springframework.util.CollectionUtils;

public class I18nTaskWorkShop
extends I18nWorkShop {
    private static final Logger logger = LoggerFactory.getLogger(I18nTaskWorkShop.class);
    private static final I18nResourceType currentResourceType = I18nResourceType.TASK_TITLE;
    private IDesignTimeViewController designTimeViewController;
    private DesParamLanguageDao dao;
    private final String I18N_TASK_CODE_COL_KEY = "taskCode";

    public I18nTaskWorkShop(I18nServiceProvider serviceProvider) {
        this.designTimeViewController = serviceProvider.getDesignTimeViewController();
        this.dao = serviceProvider.getDesParamLanguageDao();
    }

    @Override
    public List<? extends I18nBaseDTO> produce(I18nQueryVO queryVO) throws I18nException {
        List taskDefines = this.designTimeViewController.listAllTask();
        return this.convert(taskDefines);
    }

    private List<I18nTaskDTO> convert(List<DesignTaskDefine> taskDefines) throws I18nException {
        ArrayList<I18nTaskDTO> result = new ArrayList<I18nTaskDTO>();
        Map<String, DesParamLanguage> languageMap = this.getLanguageMap(taskDefines, this.dao, I18nResourceType.TASK_TITLE);
        for (DesignTaskDefine taskDefine : taskDefines) {
            DesParamLanguage language = languageMap.get(taskDefine.getKey());
            I18nTaskDTO taskDTO = new I18nTaskDTO(this.initDTO(language));
            taskDTO.setTaskKey(taskDefine.getKey());
            taskDTO.setTaskCode(taskDefine.getTaskCode());
            taskDTO.setDefaultLanguageInfo(taskDefine.getTitle());
            result.add(taskDTO);
        }
        return result;
    }

    @Override
    public I18nInitExtendResultVO buildCondition(I18nInitExtendQueryVO conditionQueryVO) {
        return null;
    }

    @Override
    public List<I18nColsObj> buildCols() {
        List<I18nColsObj> cols = this.initCols();
        cols.add(this.buildColsObj("taskCode", "\u4efb\u52a1\u6807\u8bc6", false, false, 300));
        cols.add(this.buildColsObj("defaultLanguageInfo", "\u4e2d\u6587\u6807\u9898", false, false, 400));
        cols.add(this.buildColsObj("otherLanguageInfo", "\u82f1\u6587\u6807\u9898", true, false, 600));
        return cols;
    }

    @Override
    public void save(I18nResultVO resultVO) throws I18nException {
        ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
        ArrayList<String> deleteLanguages = new ArrayList<String>();
        try {
            for (I18nBaseDTO i18nBaseDTO : resultVO.getDatas()) {
                if (!(i18nBaseDTO instanceof I18nTaskDTO)) continue;
                if (this.needDelete(i18nBaseDTO)) {
                    deleteLanguages.add(i18nBaseDTO.getLanguageKey());
                }
                if (!this.needAdd(i18nBaseDTO)) continue;
                I18nTaskDTO taskTruly = (I18nTaskDTO)i18nBaseDTO;
                DesParamLanguage desParamLanguage = this.buildDesParamLanguage(i18nBaseDTO, resultVO);
                desParamLanguage.setResourceKey(taskTruly.getTaskKey());
                updateLanguages.add(desParamLanguage);
            }
            this.dao.batchDelete(deleteLanguages.toArray(new String[deleteLanguages.size()]));
            this.dao.batchInsert(updateLanguages.toArray(new DesParamLanguage[updateLanguages.size()]));
        }
        catch (Exception e) {
            throw new I18nException("\u4efb\u52a1\u6807\u9898\u591a\u8bed\u8a00\u914d\u7f6e\u4fdd\u5b58\u5931\u8d25", e);
        }
    }

    @Override
    public void dataImport(Workbook workbook, DesignTaskDefine task) throws I18nException {
        logger.info("\u5f00\u59cb\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u4efb\u52a1\u6807\u9898\u591a\u8bed\u8a00\u8bbe\u7f6e", (Object)task.getTitle());
        try {
            ArrayList deleteLanguages = new ArrayList();
            ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
            Sheet sheet = workbook.getSheetAt(0);
            if (!this.checkSheet(sheet)) {
                logger.error("\u9875\u7b7e\u5185\u5bb9\u6821\u9a8c\u5931\u8d25\uff0c\u8bf7\u6309\u7167\u5bfc\u51fa\u65f6\u5185\u5bb9\u5bfc\u5165");
                return;
            }
            String taskCode = this.getResourceCodeFromSheet(sheet);
            if (!task.getTaskCode().equals(taskCode)) {
                logger.error("\u9875\u7b7e\u89e3\u6790\u5931\u8d25");
                return;
            }
            List languages = this.dao.queryLanguageByResKey(task.getKey());
            if (!CollectionUtils.isEmpty(languages)) {
                deleteLanguages.addAll(languages.stream().map(DesParamLanguage::getKey).collect(Collectors.toList()));
            }
            Row row = sheet.getRow(1);
            String CellTaskCode = this.getCellValueWithImport(row, 0);
            if (!task.getTaskCode().equals(CellTaskCode)) {
                logger.error("\u4efb\u52a1\u6807\u9898\u591a\u8bed\u8a00\u5bfc\u5165 \u6807\u8bc6\u5339\u914d\u5931\u8d25");
                return;
            }
            String cellValue = this.getCellValueWithImport(row, this.buildCols().size() - 1);
            updateLanguages.add(this.buildLanguageObj(cellValue, I18nResourceType.TASK_TITLE.getValue(), I18nLanguageType.ENGLISH.getValue(), task.getKey()));
            this.dao.batchDelete(deleteLanguages.toArray(new String[deleteLanguages.size()]));
            logger.info("\u5220\u9664\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)deleteLanguages.size());
            this.dao.batchInsert(updateLanguages.toArray(new DesParamLanguage[updateLanguages.size()]));
            logger.info("\u65b0\u589e\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)updateLanguages.size());
            logger.info("\u4efb\u52a1[{}]\u7684\u4efb\u52a1\u6807\u9898\u591a\u8bed\u8a00\u8bbe\u7f6e\u5bfc\u5165\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u4efb\u52a1\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
            throw new I18nException("\u4efb\u52a1\u6807\u9898\u5bfc\u5165\u5931\u8d25", e);
        }
    }

    private boolean checkSheet(Sheet sheet) {
        Row row = sheet.getRow(0);
        String codeCell = this.getCellValueWithImport(row, 0);
        String otherLanguageInfoCell = this.getCellValueWithImport(row, this.buildCols().size() - 1);
        return "\u4efb\u52a1\u6807\u8bc6".equals(codeCell) && "\u82f1\u6587\u6807\u9898".equals(otherLanguageInfoCell);
    }

    @Override
    public void dataExport(I18nExportParam exportParam) throws I18nException {
        DesignTaskDefine task = exportParam.getTask();
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u4efb\u52a1\u6807\u9898\u591a\u8bed\u8a00", (Object)task.getTitle());
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            List languages = this.dao.queryLanguageByResKey(task.getKey());
            String sheetTitle = task.getTitle() + '_' + task.getTaskCode();
            List<I18nColsObj> cols = this.buildCols();
            Sheet sheet = this.createSheet(workbook, sheetTitle, cols);
            int colNumber = cols.size();
            int rowNumber = 2;
            for (int row = 1; row < rowNumber; ++row) {
                Row rowData = sheet.createRow(row);
                rowData.setHeightInPoints(25.0f);
                for (int col = 0; col < colNumber; ++col) {
                    Cell cell = rowData.createCell(col);
                    cell.setCellValue(this.getCellValue(col, task, languages, cols));
                }
            }
            this.writeToZip(exportParam, this.getFileName(currentResourceType), workbook);
            logger.info("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u4efb\u52a1\u6807\u9898\u591a\u8bed\u8a00\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u4efb\u52a1\u6807\u9898\u591a\u8bed\u8a00\u5931\u8d25", (Object)task.getTitle());
            throw new I18nException("\u4efb\u52a1\u6807\u9898\u591a\u8bed\u8a00\u5bfc\u51fa\u5931\u8d25", e);
        }
    }

    private String getCellValue(int col, DesignTaskDefine task, List<DesParamLanguage> language, List<I18nColsObj> cols) {
        String cellValue = "";
        I18nColsObj colsObj = cols.get(col);
        switch (colsObj.getKey()) {
            case "defaultLanguageInfo": {
                cellValue = task.getTitle();
                break;
            }
            case "otherLanguageInfo": {
                cellValue = CollectionUtils.isEmpty(language) ? "" : language.get(0).getLanguageInfo();
                break;
            }
            case "taskCode": {
                cellValue = task.getTaskCode();
                break;
            }
        }
        return cellValue;
    }
}

