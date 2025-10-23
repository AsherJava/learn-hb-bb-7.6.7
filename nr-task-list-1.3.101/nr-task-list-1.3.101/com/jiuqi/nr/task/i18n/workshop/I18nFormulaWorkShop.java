/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.i18n.workshop;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormulaDTO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.common.I18nLanguageType;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import com.jiuqi.nr.task.i18n.provider.I18nServiceProvider;
import com.jiuqi.nr.task.i18n.service.I18nTreeService;
import com.jiuqi.nr.task.i18n.workshop.I18nWorkShop;
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

public class I18nFormulaWorkShop
extends I18nWorkShop {
    private static final Logger logger = LoggerFactory.getLogger(I18nFormulaWorkShop.class);
    private static final I18nResourceType currentResourceType = I18nResourceType.FORMULA_DESC;
    private IDesignTimeViewController designTimeViewController;
    private IDesignTimeFormulaController designTimeFormulaController;
    private DesParamLanguageDao dao;
    private I18nTreeService treeService;
    private final String I18N_FORMULA_COL_KEY = "formula";
    private final String I18N_FORMULA_TYPE_COL_KEY = "formulaType";
    private final String I18N_FORMULA_CODE_COL_KEY = "formulaCode";
    private final String I18N_FORMULA_SCHEME_COL_KEY = "formulaSchemeTitle";
    private final String I18N_FORM_COL_KEY = "formTitle";

    public I18nFormulaWorkShop(I18nServiceProvider serviceProvider) {
        this.designTimeFormulaController = serviceProvider.getDesignTimeFormulaController();
        this.designTimeViewController = serviceProvider.getDesignTimeViewController();
        this.dao = serviceProvider.getDesParamLanguageDao();
        this.treeService = serviceProvider.getTreeService();
    }

    @Override
    public List<? extends I18nBaseDTO> produce(I18nQueryVO queryVO) throws I18nException {
        List formulas = "I18N_FORM_TREE_ROOT".equals(queryVO.getFormKey()) ? this.designTimeFormulaController.listFormulaByScheme(queryVO.getFormulaSchemeKey()) : ("I18N_FORMULA_BETWEEN_FORM_NODE".equals(queryVO.getFormKey()) ? this.designTimeFormulaController.listFormulaBySchemeAndForm(queryVO.getFormulaSchemeKey(), null) : this.designTimeFormulaController.listFormulaBySchemeAndForm(queryVO.getFormulaSchemeKey(), queryVO.getFormKey()));
        DesignFormulaSchemeDefine scheme = this.designTimeFormulaController.getFormulaScheme(queryVO.getFormulaSchemeKey());
        return this.convert(formulas, scheme);
    }

    private List<I18nFormulaDTO> convert(List<DesignFormulaDefine> formulas, DesignFormulaSchemeDefine scheme) throws I18nException {
        ArrayList<I18nFormulaDTO> result = new ArrayList<I18nFormulaDTO>();
        Map<String, DesParamLanguage> languageMap = this.getLanguageMap(formulas, this.dao, I18nResourceType.FORMULA_DESC);
        for (DesignFormulaDefine formula : formulas) {
            DesParamLanguage language = languageMap.get(formula.getKey());
            I18nFormulaDTO formulaDTO = new I18nFormulaDTO(this.initDTO(language));
            formulaDTO.setFormulaSchemeKey(scheme.getKey());
            formulaDTO.setFormulaSchemeTitle(scheme.getTitle());
            formulaDTO.setFormulaKey(formula.getKey());
            formulaDTO.setFormulaCode(formula.getCode());
            formulaDTO.setFormula(formula.getExpression());
            formulaDTO.setFormulaType(this.getFormulaTye(formula));
            formulaDTO.setDefaultLanguageInfo(formula.getDescription());
            result.add(formulaDTO);
        }
        return result;
    }

    @Override
    public I18nInitExtendResultVO buildCondition(I18nInitExtendQueryVO conditionQueryVO) {
        I18nInitExtendResultVO resultVO = this.buildCommonResultVO(conditionQueryVO, this.designTimeViewController);
        if (resultVO != null) {
            return resultVO;
        }
        if (StringUtils.hasText(conditionQueryVO.getFormSchemeKey())) {
            List formulaSchemeDefines = this.designTimeFormulaController.listFormulaSchemeByFormScheme(conditionQueryVO.getFormSchemeKey());
            ArrayList<I18nBaseObj> formulaSchemeObjs = new ArrayList<I18nBaseObj>();
            formulaSchemeDefines.forEach(formulaSchemeDefine -> {
                I18nBaseObj formulaSchemeObj = new I18nBaseObj(formulaSchemeDefine.getKey(), formulaSchemeDefine.getTitle());
                formulaSchemeObjs.add(formulaSchemeObj);
            });
            List<UITreeNode<TreeData>> treeDatas = this.treeService.formTreeLoad(conditionQueryVO.getFormSchemeKey());
            return new I18nInitExtendResultVO(formulaSchemeObjs, treeDatas);
        }
        return null;
    }

    @Override
    public List<I18nColsObj> buildCols() {
        List<I18nColsObj> cols = this.initCols();
        cols.add(this.buildColsObj("formulaCode", "\u7f16\u53f7", false, false, 150));
        cols.add(this.buildColsObj("formula", "\u516c\u5f0f", false, false, 300));
        cols.add(this.buildColsObj("formulaType", "\u7c7b\u578b", false, false, 200));
        cols.add(this.buildColsObj("defaultLanguageInfo", "\u4e2d\u6587\u8bf4\u660e", false, false, 450));
        cols.add(this.buildColsObj("otherLanguageInfo", "\u82f1\u6587\u8bf4\u660e", true, false, 450));
        return cols;
    }

    private List<I18nColsObj> buildIOCols() {
        List<I18nColsObj> cols = this.buildCols();
        cols.add(0, this.buildColsObj("formulaSchemeTitle", "\u516c\u5f0f\u65b9\u6848", false, false, null));
        cols.add(1, this.buildColsObj("formTitle", "\u62a5\u8868\u6807\u9898", false, false, null));
        return cols;
    }

    @Override
    public void save(I18nResultVO resultVO) throws I18nException {
        ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
        ArrayList<String> deleteLanguages = new ArrayList<String>();
        try {
            for (I18nBaseDTO i18nBaseDTO : resultVO.getDatas()) {
                if (!(i18nBaseDTO instanceof I18nFormulaDTO)) continue;
                if (this.needDelete(i18nBaseDTO)) {
                    deleteLanguages.add(i18nBaseDTO.getLanguageKey());
                }
                if (!this.needAdd(i18nBaseDTO)) continue;
                I18nFormulaDTO formulaTruly = (I18nFormulaDTO)i18nBaseDTO;
                DesParamLanguage language = this.buildDesParamLanguage(i18nBaseDTO, resultVO);
                language.setResourceKey(formulaTruly.getFormulaKey());
                updateLanguages.add(language);
            }
            this.dao.batchDelete(deleteLanguages.toArray(new String[deleteLanguages.size()]));
            this.dao.batchInsert(updateLanguages.toArray(new DesParamLanguage[updateLanguages.size()]));
        }
        catch (Exception e) {
            throw new I18nException("\u516c\u5f0f\u8bf4\u660e\u591a\u8bed\u8a00\u914d\u7f6e\u4fdd\u5b58\u5931\u8d25", e);
        }
    }

    private String getFormulaTye(DesignFormulaDefine formula) {
        StringBuffer formulaType = new StringBuffer();
        if (formula.getUseCheck()) {
            formulaType.append("\u5ba1\u6838\u516c\u5f0f");
        }
        if (formula.getUseBalance()) {
            if (StringUtils.hasText(formulaType)) {
                formulaType.append(";");
            }
            formulaType.append("\u5e73\u8861\u516c\u5f0f");
        }
        if (formula.getUseCalculate()) {
            if (StringUtils.hasText(formulaType)) {
                formulaType.append(";");
            }
            formulaType.append("\u8fd0\u7b97\u516c\u5f0f");
        }
        return formulaType.toString();
    }

    @Override
    public void dataImport(Workbook workbook, DesignTaskDefine task) throws I18nException {
        logger.info("\u5f00\u59cb\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u8bf4\u660e\u591a\u8bed\u8a00\u8bbe\u7f6e", (Object)task.getTitle());
        try {
            List formSchemes = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            Map<String, DesignFormSchemeDefine> schemeCodeMap = formSchemes.stream().collect(Collectors.toMap(FormSchemeDefine::getFormSchemeCode, v -> v));
            List<I18nColsObj> cols = this.buildIOCols();
            ArrayList<DesParamLanguage> updateLanguages = new ArrayList<DesParamLanguage>();
            ArrayList<String> needDeleteFormulaWithLanguages = new ArrayList<String>();
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
                HashMap<String, Map<String, DesignFormulaDefine>> schemeAndFormulaCodeMap = new HashMap<String, Map<String, DesignFormulaDefine>>();
                List formulaSchemes = this.designTimeFormulaController.listFormulaSchemeByFormScheme(formScheme.getKey());
                for (DesignFormulaSchemeDefine formulaScheme : formulaSchemes) {
                    List formulas = this.designTimeFormulaController.listFormulaByScheme(formulaScheme.getKey());
                    Map<String, DesignFormulaDefine> formulaCodeMap = formulas.stream().collect(Collectors.toMap(FormulaDefine::getCode, v -> v));
                    schemeAndFormulaCodeMap.put(formulaScheme.getTitle(), formulaCodeMap);
                }
                for (Row row : sheet) {
                    Map formulaCodeMap;
                    if (row.getRowNum() == 0) continue;
                    String formulaSchemeTitle = this.getCellValueWithImport(row, 0);
                    String formulaCode = this.getCellValueWithImport(row, 2);
                    String languageInfo = this.getCellValueWithImport(row, cols.size() - 1);
                    if (!schemeAndFormulaCodeMap.containsKey(formulaSchemeTitle) || !(formulaCodeMap = (Map)schemeAndFormulaCodeMap.get(formulaSchemeTitle)).containsKey(formulaCode)) continue;
                    needDeleteFormulaWithLanguages.add(((DesignFormulaDefine)formulaCodeMap.get(formulaCode)).getKey());
                    updateLanguages.add(this.buildLanguageObj(languageInfo, I18nResourceType.FORMULA_DESC.getValue(), I18nLanguageType.ENGLISH.getValue(), ((DesignFormulaDefine)formulaCodeMap.get(formulaCode)).getKey()));
                }
            }
            logger.info("\u5220\u9664\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doDelete(this.dao, needDeleteFormulaWithLanguages));
            logger.info("\u65b0\u589e\u591a\u8bed\u8a00\u8bb0\u5f55{}\u6761", (Object)this.doInsert(this.dao, updateLanguages));
            logger.info("\u5bfc\u5165\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u8bf4\u660e\u591a\u8bed\u8a00\u8bbe\u7f6e\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u516c\u5f0f\u8bf4\u660e\u5bfc\u5165\u5931\u8d25", e);
            throw new I18nException("\u516c\u5f0f\u8bf4\u660e\u5bfc\u5165\u5931\u8d25", e);
        }
    }

    private boolean checkSheet(Sheet sheet) {
        Row row = sheet.getRow(0);
        String schemeTitleCell = this.getCellValueWithImport(row, 0);
        String codeCell = this.getCellValueWithImport(row, 2);
        String otherLanguageInfoCell = this.getCellValueWithImport(row, this.buildIOCols().size() - 1);
        return "\u516c\u5f0f\u65b9\u6848".equals(schemeTitleCell) && "\u7f16\u53f7".equals(codeCell) && "\u82f1\u6587\u8bf4\u660e".equals(otherLanguageInfoCell);
    }

    @Override
    public void dataExport(I18nExportParam exportParam) throws I18nException {
        DesignTaskDefine task = exportParam.getTask();
        logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u8bf4\u660e\u591a\u8bed\u8a00", (Object)task.getTitle());
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(task.getKey());
            List<I18nColsObj> cols = this.buildIOCols();
            logger.info("\u5171\u6709{}\u4e2a\u62a5\u8868\u65b9\u6848", (Object)formSchemeDefines.size());
            for (DesignFormSchemeDefine formScheme : formSchemeDefines) {
                List formulaSchemes = this.designTimeFormulaController.listFormulaSchemeByFormScheme(formScheme.getKey());
                List forms = this.designTimeViewController.listFormByFormScheme(formScheme.getKey());
                Map<String, DesignFormDefine> formMap = forms.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
                Map<String, DesignFormulaSchemeDefine> schemeMap = formulaSchemes.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
                ArrayList<DesignFormulaDefine> formulas = new ArrayList<DesignFormulaDefine>();
                for (DesignFormulaSchemeDefine formulaScheme : formulaSchemes) {
                    formulas.addAll(this.designTimeFormulaController.listFormulaByScheme(formulaScheme.getKey()));
                }
                Map<String, DesParamLanguage> languageMap = this.getLanguageMap(formulas, this.dao, I18nResourceType.FORMULA_DESC);
                Sheet sheet = this.createSheet(workbook, this.getFormSchemeSheetName(formScheme), cols);
                int colNumber = cols.size();
                int rowNumber = formulas.size() + 1;
                for (int row = 1; row < rowNumber; ++row) {
                    Row rowData = sheet.createRow(row);
                    rowData.setHeightInPoints(25.0f);
                    for (int col = 0; col < colNumber; ++col) {
                        Cell cell = rowData.createCell(col);
                        cell.setCellValue(this.getCellValue(row - 1, col, formulas, schemeMap, formMap, languageMap, cols));
                    }
                }
            }
            this.writeToZip(exportParam, this.getFileName(currentResourceType), workbook);
            logger.info("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u8bf4\u660e\u591a\u8bed\u8a00\u5b8c\u6210", (Object)task.getTitle());
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u4efb\u52a1[{}]\u7684\u516c\u5f0f\u8bf4\u660e\u591a\u8bed\u8a00\u5931\u8d25", (Object)task.getTitle());
            throw new I18nException("\u516c\u5f0f\u8bf4\u660e\u591a\u8bed\u8a00\u5bfc\u51fa\u5931\u8d25", e);
        }
    }

    private String getCellValue(int row, int col, List<DesignFormulaDefine> formulas, Map<String, DesignFormulaSchemeDefine> schemeMap, Map<String, DesignFormDefine> formMap, Map<String, DesParamLanguage> languageMap, List<I18nColsObj> cols) {
        String cellValue = "";
        I18nColsObj colsObj = cols.get(col);
        DesignFormulaDefine formula = formulas.get(row);
        switch (colsObj.getKey()) {
            case "defaultLanguageInfo": {
                cellValue = formula.getDescription();
                break;
            }
            case "otherLanguageInfo": {
                cellValue = this.getOtherLanguageInfo(formula.getKey(), languageMap);
                break;
            }
            case "formulaCode": {
                cellValue = formula.getCode();
                break;
            }
            case "formulaType": {
                cellValue = this.getFormulaTye(formula);
                break;
            }
            case "formula": {
                cellValue = formula.getExpression();
                break;
            }
            case "formulaSchemeTitle": {
                cellValue = schemeMap.get(formula.getFormulaSchemeKey()) == null ? "" : schemeMap.get(formula.getFormulaSchemeKey()).getTitle();
                break;
            }
            case "formTitle": {
                cellValue = formMap.get(formula.getFormKey()) == null ? "\u8868\u95f4\u516c\u5f0f" : formMap.get(formula.getFormKey()).getTitle();
                break;
            }
        }
        return cellValue;
    }
}

