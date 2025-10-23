/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.task.i18n.workshop;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.dto.I18nTaskOrgAliasDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class I18nTaskOrgAliasWorkShop
extends I18nWorkShop {
    private static final Logger logger = LoggerFactory.getLogger(I18nTaskOrgAliasWorkShop.class);
    private static final I18nResourceType currentResourceType = I18nResourceType.TASK_ORG_ALIAS;
    private IDesignTimeViewController designTimeViewController;
    private DesParamLanguageDao dao;
    private IEntityMetaService entityMetaService;
    private final String I18N_TASK_ORG_COL_KEY = "entityName";

    public I18nTaskOrgAliasWorkShop(I18nServiceProvider serviceProvider) {
        this.designTimeViewController = serviceProvider.getDesignTimeViewController();
        this.dao = serviceProvider.getDesParamLanguageDao();
        this.entityMetaService = serviceProvider.getEntityMetaService();
    }

    @Override
    public I18nInitExtendResultVO buildCondition(I18nInitExtendQueryVO conditionQueryVO) {
        return this.buildCommonResultVO(conditionQueryVO, this.designTimeViewController);
    }

    @Override
    public List<I18nColsObj> buildCols() {
        List<I18nColsObj> cols = this.initCols();
        cols.add(this.buildColsObj("entityName", "\u673a\u6784", false, false, 300));
        cols.add(this.buildColsObj("defaultLanguageInfo", "\u4e2d\u6587\u522b\u540d", false, false, 400));
        cols.add(this.buildColsObj("otherLanguageInfo", "\u82f1\u6587\u522b\u540d", true, false, 600));
        return cols;
    }

    @Override
    public List<? extends I18nBaseDTO> produce(I18nQueryVO queryVO) throws I18nException {
        List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(queryVO.getTask());
        return this.convert(taskOrgLinkDefines);
    }

    private List<I18nTaskOrgAliasDTO> convert(List<TaskOrgLinkDefine> taskOrgLinkDefines) throws I18nException {
        ArrayList<I18nTaskOrgAliasDTO> result = new ArrayList<I18nTaskOrgAliasDTO>();
        Map<String, DesParamLanguage> languageMap = this.getLanguageMap(taskOrgLinkDefines, this.dao, I18nResourceType.TASK_ORG_ALIAS);
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskOrgLinkDefine.getEntity());
            if (entityDefine == null) continue;
            DesParamLanguage language = languageMap.get(taskOrgLinkDefine.getKey());
            I18nTaskOrgAliasDTO taskOrgAliasDTO = new I18nTaskOrgAliasDTO(this.initDTO(language));
            taskOrgAliasDTO.setTaskOrgLinkKey(taskOrgLinkDefine.getKey());
            taskOrgAliasDTO.setEntityName(entityDefine.getTitle() + '[' + entityDefine.getCode() + ']');
            taskOrgAliasDTO.setDefaultLanguageInfo(taskOrgLinkDefine.getEntityAlias());
            result.add(taskOrgAliasDTO);
        }
        return result;
    }

    @Override
    public void save(I18nResultVO resultVO) throws I18nException {
        ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
        ArrayList<String> deleteLanguages = new ArrayList<String>();
        try {
            for (I18nBaseDTO i18nBaseDTO : resultVO.getDatas()) {
                if (!(i18nBaseDTO instanceof I18nTaskOrgAliasDTO)) continue;
                if (this.needDelete(i18nBaseDTO)) {
                    deleteLanguages.add(i18nBaseDTO.getLanguageKey());
                }
                if (!this.needAdd(i18nBaseDTO)) continue;
                I18nTaskOrgAliasDTO taskOrgTruly = (I18nTaskOrgAliasDTO)i18nBaseDTO;
                DesParamLanguage desParamLanguage = this.buildDesParamLanguage(i18nBaseDTO, resultVO);
                desParamLanguage.setResourceKey(taskOrgTruly.getTaskOrgLinkKey());
                updateLanguages.add(desParamLanguage);
            }
            this.dao.batchDelete(deleteLanguages.toArray(new String[deleteLanguages.size()]));
            this.dao.batchInsert(updateLanguages.toArray(new DesParamLanguage[updateLanguages.size()]));
        }
        catch (Exception e) {
            throw new I18nException("\u5b9e\u4f53\u522b\u540d\u591a\u8bed\u8a00\u914d\u7f6e\u4fdd\u5b58\u5931\u8d25", e);
        }
    }

    @Override
    public void dataImport(Workbook workbook, DesignTaskDefine task) throws I18nException {
        logger.info("\u5f00\u59cb\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u5b9e\u4f53\u522b\u540d\u591a\u8bed\u8a00\u8bbe\u7f6e", (Object)task.getTitle());
        try {
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
            List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(task.getKey());
            HashMap<String, TaskOrgLinkDefine> taskOrgLinkDefineMap = new HashMap<String, TaskOrgLinkDefine>();
            for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
                String code = this.entityMetaService.getEntityCode(taskOrgLinkDefine.getEntity());
                taskOrgLinkDefineMap.put(code, taskOrgLinkDefine);
            }
            List<I18nColsObj> cols = this.buildCols();
            ArrayList<String> needDeleteTaskOrgLinkKeys = new ArrayList<String>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String entityName = this.getCellValueWithImport(row, 0);
                int startIndex = entityName.indexOf("[");
                int endIndex = entityName.indexOf("]");
                String entityCode = entityName.substring(startIndex + 1, endIndex);
                String entityAlias = this.getCellValueWithImport(row, 1);
                if (!StringUtils.hasText(entityAlias)) continue;
                String languageInfo = this.getCellValueWithImport(row, cols.size() - 1);
                if (!taskOrgLinkDefineMap.containsKey(entityCode)) continue;
                needDeleteTaskOrgLinkKeys.add(((TaskOrgLinkDefine)taskOrgLinkDefineMap.get(entityCode)).getKey());
                updateLanguages.add(this.buildLanguageObj(languageInfo, I18nResourceType.FORM_SCHEME_TITLE.getValue(), I18nLanguageType.ENGLISH.getValue(), ((TaskOrgLinkDefine)taskOrgLinkDefineMap.get(entityCode)).getKey()));
            }
            logger.info("\u5220\u9664\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doDelete(this.dao, needDeleteTaskOrgLinkKeys));
            logger.info("\u65b0\u589e\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doInsert(this.dao, updateLanguages));
            logger.info("\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u5b9e\u4f53\u522b\u540d\u591a\u8bed\u8a00\u8bbe\u7f6e\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u5b9e\u4f53\u522b\u540d\u5bfc\u5165\u5931\u8d25", e);
            throw new I18nException("\u5b9e\u4f53\u522b\u540d\u5bfc\u5165\u5931\u8d25", e);
        }
    }

    private boolean checkSheet(Sheet sheet) {
        Row row = sheet.getRow(0);
        String codeCell = this.getCellValueWithImport(row, 0);
        String otherLanguageInfoCell = this.getCellValueWithImport(row, this.buildCols().size() - 1);
        return "\u673a\u6784".equals(codeCell) && "\u82f1\u6587\u522b\u540d".equals(otherLanguageInfoCell);
    }

    @Override
    public void dataExport(I18nExportParam exportParam) throws I18nException {
        DesignTaskDefine task = exportParam.getTask();
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u5b9e\u4f53\u522b\u540d\u591a\u8bed\u8a00", (Object)task.getTitle());
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(task.getKey());
            Map<String, DesParamLanguage> languageMap = this.getLanguageMap(taskOrgLinkDefines, this.dao, I18nResourceType.TASK_ORG_ALIAS);
            String sheetTitle = task.getTitle() + '_' + task.getTaskCode();
            List<I18nColsObj> cols = this.buildCols();
            Sheet sheet = this.createSheet(workbook, sheetTitle, cols);
            int colNumber = cols.size();
            int rowNumber = taskOrgLinkDefines.size() + 1;
            for (int row = 1; row < rowNumber; ++row) {
                Row rowData = sheet.createRow(row);
                rowData.setHeightInPoints(25.0f);
                for (int col = 0; col < colNumber; ++col) {
                    Cell cell = rowData.createCell(col);
                    cell.setCellValue(this.getCellValue(row - 1, col, taskOrgLinkDefines, languageMap, cols));
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

    private String getCellValue(int row, int col, List<TaskOrgLinkDefine> taskOrgLinkDefines, Map<String, DesParamLanguage> languageMap, List<I18nColsObj> cols) {
        String cellValue = "";
        I18nBaseObj colsObj = cols.get(col);
        TaskOrgLinkDefine taskOrgLinkDefine = taskOrgLinkDefines.get(row);
        switch (colsObj.getKey()) {
            case "defaultLanguageInfo": {
                cellValue = taskOrgLinkDefine.getEntityAlias();
                break;
            }
            case "otherLanguageInfo": {
                cellValue = this.getOtherLanguageInfo(taskOrgLinkDefine.getKey(), languageMap);
                break;
            }
            case "entityName": {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskOrgLinkDefine.getEntity());
                if (entityDefine == null) {
                    cellValue = "\u5173\u8054\u9519\u8bef\u7ec4\u7ec7\u673a\u6784\uff0c\u8bf7\u68c0\u67e5[" + this.entityMetaService.getEntityCode(taskOrgLinkDefine.getEntity()) + ']';
                    break;
                }
                cellValue = entityDefine.getTitle() + '[' + entityDefine.getCode() + ']';
                break;
            }
        }
        return cellValue;
    }
}

