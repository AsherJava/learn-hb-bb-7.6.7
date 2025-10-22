/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.FormulaCondition
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.util.StringUtils
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.Grid2DataSeralizeToGeGe;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.IFormFormulaService;
import com.jiuqi.nr.designer.util.CycleUtil;
import com.jiuqi.nr.designer.web.facade.FormulaCheckObj;
import com.jiuqi.nr.designer.web.facade.FormulaConditionLinkObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.service.TaskDesignerService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.util.StringUtils;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FormFormulaService
implements IFormFormulaService {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private TaskDesignerService taskDesignerService;
    @Autowired
    private ServeCodeService serveCodeService;
    private Logger logger = LogFactory.getLogger(FormFormulaService.class);
    private static final String XLS = "XLS";
    private static final String XLSX = "XLSX";
    private static final String REPEATCODEINFO = "\u91cd\u590d\u7f16\u53f7\u4fe1\u606f";
    private static final String NUMBER = "\u7f16\u53f7";
    private static final String EXPRESSION = "\u8868\u8fbe\u5f0f";
    private static final String DESCRIPTION = "\u8bf4\u660e";
    private static final String TYPE = "\u7c7b\u578b";
    private static final String AUDITTYPE = "\u5ba1\u6838\u7c7b\u578b";
    private static final String BJFORMULA = "\u8868\u95f4\u516c\u5f0f";
    private static final String QUOTETYPE = "\u5f15\u7528\u7c7b\u578b";
    private static final String BALANCEZBEXP = "\u8c03\u6574\u6307\u6807";
    private static final String CONDITION = "\u9002\u7528\u6761\u4ef6";
    private static final String OWNERLEVELANDID = "ownerLevelAndIds";
    private String serverCode = "SERVERCODE";

    @Override
    public void paraExcelFormulas(MultipartFile file, String[] formId, Map<String, List> formulasMap, String formulaScheme, boolean isFullImport) throws JQException {
        this.checkFile(file);
        try {
            Workbook workBook = this.getWorkBook(file);
            this.paraExcel(workBook, formId, formulasMap, formulaScheme, isFullImport);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_022);
        }
    }

    private void checkFile(MultipartFile file) throws JQException {
        if (null == file) {
            this.logger.error("\u6587\u4ef6\u4e0d\u5b58\u5728\uff01");
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_020);
        }
        String fileName = file.getOriginalFilename().toUpperCase();
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            this.logger.error(fileName + "\u4e0d\u662fexcel\u6587\u4ef6");
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_021, "[" + fileName + "]" + NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_021);
        }
    }

    private Workbook getWorkBook(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toUpperCase();
        Workbook workbook = null;
        try (InputStream is = file.getInputStream();){
            if (fileName.endsWith(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(is);
            }
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw e;
        }
        return workbook;
    }

    private void paraExcel(Workbook workbook, String[] formId, Map<String, List> formulasMap, String formulaScheme, boolean isFullImport) throws IOException, JQException {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        int sheetCount = workbook.getNumberOfSheets();
        List formulas = this.nrDesignTimeController.getAllSoftFormulasInScheme(formulaScheme);
        HashMap<String, String> formulaCodeKeyMap = new HashMap<String, String>();
        HashMap<String, List<String>> form_formulasMap = new HashMap<String, List<String>>();
        HashSet<String> formulaCodeSet = new HashSet<String>();
        if (isFullImport) {
            HashSet<String> excelFormCodeSet = new HashSet<String>();
            for (int i = 0; i < sheetCount; ++i) {
                excelFormCodeSet.add(workbook.getSheetAt(i).getSheetName().split(" ")[0]);
            }
            this.getAllFormulaCodeSet(formulas, excelFormCodeSet, formulaCodeSet, formulaCodeKeyMap);
        } else {
            this.getForm_formulasMap(form_formulasMap, formulas, formulaCodeKeyMap);
        }
        Map<Object, Object> formCodeDefineMap = new HashMap();
        if (formId != null) {
            formCodeDefineMap = Arrays.stream(formId).map(formKey -> this.nrDesignTimeController.querySoftFormDefine(formKey)).filter(f -> f != null).collect(Collectors.toMap(form -> form.getFormCode(), form -> form, (oldValue, newValue) -> newValue));
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        Map<String, Integer> auditTypeMap = this.getAuditTypeMap();
        HashSet<String> excelFormulaCodeSet = new HashSet<String>();
        ArrayList<String> excelFormulaLength = new ArrayList<String>();
        ArrayList<String> formulaRepeatCode = new ArrayList<String>();
        for (int s = 0; s < sheetCount; ++s) {
            String sheetName;
            Sheet sheet = workbook.getSheetAt(s);
            if (sheet == null || (sheetName = sheet.getSheetName()).equals(REPEATCODEINFO)) continue;
            this.validateHeader(sheet);
            String sheetCode = sheetName.split(" ")[0];
            DesignFormDefine form2 = (DesignFormDefine)formCodeDefineMap.get(sheetCode);
            if (null == form2 && !sheetCode.equals(BJFORMULA)) continue;
            int rowCount = sheet.getLastRowNum();
            ArrayList<FormulaCheckObj> formulaList = new ArrayList<FormulaCheckObj>();
            int headerColCount = sheet.getRow(0).getLastCellNum();
            ArrayList<String> headerList = new ArrayList<String>();
            for (int c = 0; c < headerColCount; ++c) {
                headerList.add(FormFormulaService.getCellValue(sheet.getRow(0).getCell(c)));
            }
            for (int r = 1; r <= rowCount; ++r) {
                boolean hasExpression = true;
                Row row = sheet.getRow(r);
                int colCount = row.getLastCellNum();
                FormulaCheckObj formula = new FormulaCheckObj();
                block23: for (int c = 0; c < colCount && hasExpression; ++c) {
                    if (c > headerList.size() - 1) continue;
                    String cellValue = (String)headerList.get(c);
                    String cValue = FormFormulaService.getCellValue(row.getCell(c));
                    switch (cellValue) {
                        case "\u7f16\u53f7": {
                            formula.setCode(com.jiuqi.bi.util.StringUtils.isEmpty((String)cValue) ? this.paraFormulaCode(form2, r) : cValue);
                            continue block23;
                        }
                        case "\u8868\u8fbe\u5f0f": {
                            hasExpression = com.jiuqi.bi.util.StringUtils.isNotEmpty((String)cValue);
                            formula.setFormula(cValue);
                            continue block23;
                        }
                        case "\u8bf4\u660e": {
                            if (cValue.length() > 1000) {
                                excelFormulaLength.add("excel\u516c\u5f0f\u8bf4\u660e\u957f\u5ea6\u8d85\u957f:sheet\u540d\u79f0:" + sheetName + ";\u957f\u5ea6\u9650\u5236\u4e3a:1000;\u8d85\u957f\u4f4d\u7f6e:C" + (r + 1));
                            }
                            formula.setDescription(cValue);
                            continue block23;
                        }
                        case "\u7c7b\u578b": {
                            if (isEfdc) continue block23;
                            if (cValue.indexOf("\u8fd0\u7b97\u516c\u5f0f") > -1) {
                                formula.setUseCalculate(true);
                            }
                            if (cValue.indexOf("\u5ba1\u6838\u516c\u5f0f") > -1) {
                                formula.setUseCheck(true);
                            }
                            if (cValue.indexOf("\u5e73\u8861\u516c\u5f0f") <= -1) continue block23;
                            formula.setUseBalance(true);
                            continue block23;
                        }
                        case "\u5ba1\u6838\u7c7b\u578b": {
                            if (isEfdc || !formula.isUseCheck()) continue block23;
                            Integer code = auditTypeMap.get(cValue);
                            formula.setChecktype(code);
                            continue block23;
                        }
                        case "\u8c03\u6574\u6307\u6807": {
                            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)cValue)) {
                                formula.setBalanceZBExp(cValue);
                                continue block23;
                            }
                            formula.setBalanceZBExp("");
                            continue block23;
                        }
                        case "\u9002\u7528\u6761\u4ef6": {
                            if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)cValue)) continue block23;
                            formula.setConditionCodes(Arrays.asList(cValue.split(";")));
                            continue block23;
                        }
                    }
                }
                if (!hasExpression) continue;
                formula.setOrder(OrderGenerator.newOrder());
                formula.setSchemeKey(formulaScheme);
                formula.setFormKey(sheetName.equals(BJFORMULA) ? null : form2.getKey());
                formula.setReportName(form2 == null ? "" : form2.getFormCode());
                formula.setId(formulaCodeKeyMap.get(formula.getCode()) == null ? UUIDUtils.getKey() : (String)formulaCodeKeyMap.get(formula.getCode()));
                formulaList.add(formula);
                this.checkFormulaCode(formula, excelFormulaCodeSet, formulaCodeSet, formulaRepeatCode, isFullImport, r, sheetName, form_formulasMap);
            }
            formulasMap.put(sheetName, formulaList);
        }
        formulasMap.put("repeatCode", formulaRepeatCode);
        formulasMap.get("repeatCode").addAll(excelFormulaLength);
    }

    private void validateHeader(Sheet sheet) throws JQException {
        Row firstRow = sheet.getRow(0);
        if (!NUMBER.equals(FormFormulaService.getCellValue(firstRow.getCell(0)))) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_035);
        }
    }

    private Map<String, Integer> getAuditTypeMap() throws JQException {
        HashMap<String, Integer> auditTypeMap = new HashMap<String, Integer>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            for (int i = 0; i < auditTypes.size(); ++i) {
                auditTypeMap.put(((AuditType)auditTypes.get(i)).getTitle(), ((AuditType)auditTypes.get(i)).getCode());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_025, (Throwable)e);
        }
        return auditTypeMap;
    }

    private void checkFormulaCode(FormulaCheckObj excelFormula, Set<String> excelFormulaCodeSet, Set<String> formulaCodeSet, List<String> formulaRepeatCode, boolean isFullImport, int r, String sheetName, Map<String, List<String>> form_formulasMap) {
        if (isFullImport) {
            String formulaCode = excelFormula.getCode();
            if (!excelFormulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("excel\u5185\u90e8\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
            if (formulaCodeSet.contains(formulaCode)) {
                formulaRepeatCode.add("\u4e0e\u5df2\u6709\u5176\u4ed6\u8868\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
        } else {
            Boolean bjFlag = sheetName.equals(BJFORMULA);
            String formKey = excelFormula.getFormKey();
            formulaCodeSet.clear();
            for (Map.Entry<String, List<String>> formulasFormKey : form_formulasMap.entrySet()) {
                List<String> formulaCodes;
                String formKeyByFormula = formulasFormKey.getKey();
                if (bjFlag.booleanValue() && formKeyByFormula == null || formKeyByFormula != null && formKeyByFormula.equals(formKey) || (formulaCodes = formulasFormKey.getValue()) == null) continue;
                formulaCodeSet.addAll(formulaCodes);
            }
            String formulaCode = excelFormula.getCode();
            if (!excelFormulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("excel\u5185\u90e8\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
            if (!formulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("\u4e0e\u5176\u4ed6\u8868\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
        }
    }

    private void getForm_formulasMap(Map<String, List<String>> form_formulasMap, List<DesignFormulaDefine> formulas, Map<String, String> formulasCodeMap) {
        for (DesignFormulaDefine formula : formulas) {
            formulasCodeMap.put(formula.getCode(), formula.getKey());
            List<String> formulaCodeList = form_formulasMap.get(formula.getFormKey());
            if (null == formulaCodeList) {
                formulaCodeList = new ArrayList<String>();
                form_formulasMap.put(formula.getFormKey(), formulaCodeList);
            }
            formulaCodeList.add(formula.getCode());
        }
    }

    private void getAllFormulaCodeSet(List<DesignFormulaDefine> formulas, Set<String> excelFormCodeSet, Set<String> formulaCodeSet, Map<String, String> formulasCodeMap) {
        if (formulas.size() == 0) {
            return;
        }
        HashMap<String, Boolean> importFormMap = new HashMap<String, Boolean>();
        boolean hasBJ = excelFormCodeSet.contains(BJFORMULA);
        for (int i = 0; i < formulas.size(); ++i) {
            DesignFormulaDefine formula = formulas.get(i);
            formulasCodeMap.put(formula.getCode(), formula.getKey());
            String formKey = formula.getFormKey();
            String formulaCode = formula.getCode();
            if (com.jiuqi.bi.util.StringUtils.isEmpty((String)formKey)) {
                if (hasBJ) continue;
                formulaCodeSet.add(formulaCode);
                continue;
            }
            Boolean isImportForm = (Boolean)importFormMap.get(formKey);
            if (isImportForm == null) {
                DesignFormDefine formDefine = this.nrDesignTimeController.querySoftFormDefine(formKey);
                if (formDefine == null) {
                    isImportForm = true;
                    importFormMap.put(formKey, isImportForm);
                } else {
                    isImportForm = excelFormCodeSet.contains(formDefine.getFormCode());
                    importFormMap.put(formKey, isImportForm);
                }
            }
            if (isImportForm.booleanValue()) continue;
            formulaCodeSet.add(formulaCode);
        }
    }

    @Override
    public void allImportFormula(String[] formId, String formulaScheme, Map<String, List> formulasSheetMap) throws JQException, ParseException {
        if (formulasSheetMap.get("repeatCode").size() > 0) {
            return;
        }
        int count = 0;
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        ArrayList<FormulaObj> formulaList = new ArrayList<FormulaObj>();
        ArrayList deleteFormulas = new ArrayList();
        HashSet formCodeSet = new HashSet();
        HashMap<String, String> formCodeMap = new HashMap<String, String>();
        HashSet<String> needDeleteFormulaByForm = new HashSet<String>();
        List formDefineList = this.nrDesignTimeController.queryAllFormDefinesByFormScheme(formulaSchemeDefine.getFormSchemeKey());
        for (DesignFormDefine designFormDefine : formDefineList) {
            formCodeMap.put(designFormDefine.getFormCode(), designFormDefine.getKey());
        }
        if (!isEfdc) {
            formCodeMap.put(BJFORMULA, null);
        }
        boolean isNeedDelete = false;
        Map conditionMap = this.nrDesignTimeController.queryFormulaConditionBySchemeAndCode(formulaScheme, new String[0]);
        for (Map.Entry<String, List> entry : formulasSheetMap.entrySet()) {
            String formulasMapKey = entry.getKey();
            List formulasMapValue = entry.getValue();
            String sheetCode = formulasMapKey.split(" ")[0];
            if (formulasMapKey.equals("repeatCode") || !formCodeMap.containsKey(sheetCode)) continue;
            List allSoftFormulasInForm = this.nrDesignTimeController.getAllSoftFormulasInForm(formulaScheme, (String)formCodeMap.get(sheetCode));
            List canDeleteFormulas = allSoftFormulasInForm.stream().filter(item -> this.SameServeCode(item.getOwnerLevelAndId())).collect(Collectors.toList());
            count += allSoftFormulasInForm.size() - canDeleteFormulas.size();
            deleteFormulas.addAll(canDeleteFormulas);
            for (Object formula : formulasMapValue) {
                String formulaCode = ((FormulaCheckObj)((Object)formula)).getCode();
                List needUpdateFromulas = allSoftFormulasInForm.stream().filter(item -> item.getCode().equals(formulaCode)).collect(Collectors.toList());
                if (needUpdateFromulas.size() > 0 && !this.SameServeCode(((DesignFormulaDefine)needUpdateFromulas.get(0)).getOwnerLevelAndId()).booleanValue()) continue;
                FormulaObj formulaObj = new FormulaObj();
                Boolean isDirty = false;
                FormulaObj addFormulaObj = this.objectToFormulaObj(formula, formulaObj, null, isDirty);
                addFormulaObj.setIsNew(true);
                addFormulaObj.setIsDirty(false);
                addFormulaObj.setIsDeleted(false);
                needDeleteFormulaByForm.add(addFormulaObj.getFormKey());
                this.setFormulaCondition(addFormulaObj, ((FormulaCheckObj)((Object)formula)).getConditionCodes(), conditionMap);
                formulaList.add(addFormulaObj);
            }
            isNeedDelete = true;
        }
        if (!isNeedDelete) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_036);
        }
        if (deleteFormulas.size() > 0) {
            this.nrDesignTimeController.deleteFormulaDefines(deleteFormulas.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()).toArray(new String[0]));
        }
        if (count > 0) {
            LinkedList<Integer> ownerLevelAndIds = new LinkedList<Integer>();
            ownerLevelAndIds.add(count);
            int allCount = formulaList.size();
            ownerLevelAndIds.add(allCount);
            formulasSheetMap.put(OWNERLEVELANDID, ownerLevelAndIds);
        }
        FormulaObj[] formulaArray = new FormulaObj[formulaList.size()];
        this.taskDesignerService.saveFormulas(formulaList.toArray(formulaArray));
    }

    private void setFormulaCondition(FormulaObj addFormulaObj, List<String> conditionCodes, Map<String, DesignFormulaCondition> conditionMap) {
        for (String code : conditionCodes) {
            if (!conditionMap.containsKey(code)) continue;
            FormulaConditionLinkObj formulaConditionLinkObj = new FormulaConditionLinkObj();
            formulaConditionLinkObj.setBinding(true);
            formulaConditionLinkObj.setFormulaKey(addFormulaObj.getId());
            formulaConditionLinkObj.setSchemeKey(addFormulaObj.getSchemeKey());
            formulaConditionLinkObj.setKey(conditionMap.get(code).getKey());
            addFormulaObj.getFormulaConditions().add(formulaConditionLinkObj);
        }
    }

    @Override
    public synchronized void addImportFormula(String[] formIds, String formulaScheme, Map<String, List> formulasSheetMap) throws ParseException, JQException {
        if (formulasSheetMap.get("repeatCode").size() > 0) {
            return;
        }
        int count = 0;
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        List formulas = this.nrDesignTimeController.getAllSoftFormulasInScheme(formulaScheme);
        Map conditionMap = this.nrDesignTimeController.queryFormulaConditionBySchemeAndCode(formulaScheme, new String[0]);
        ArrayList<FormulaObj> formulaList = new ArrayList<FormulaObj>();
        HashSet<String> formCodeSet = new HashSet<String>();
        for (int i = 0; i < formIds.length; ++i) {
            DesignFormDefine formDefine = this.nrDesignTimeController.querySoftFormDefine(formIds[i]);
            formCodeSet.add(formDefine.getFormCode());
        }
        if (!isEfdc) {
            formCodeSet.add(BJFORMULA);
        }
        int formCodeSetSize = formCodeSet.size();
        for (Map.Entry entry : formulasSheetMap.entrySet()) {
            String formulasMapKey = (String)entry.getKey();
            List formulasMapValue = (List)entry.getValue();
            String sheetCode = formulasMapKey.split(" ")[0];
            if (formulasMapKey.equals("repeatCode")) continue;
            formCodeSet.add(sheetCode);
            for (Object formula : formulasMapValue) {
                FormulaObj addFormulaObj;
                Boolean isDirty;
                String formulaCode = ((FormulaCheckObj)((Object)formula)).getCode();
                FormulaObj formulaObj = new FormulaObj();
                List needAddFormulas = formulas.stream().filter(item -> item.getCode().equals(formulaCode)).collect(Collectors.toList());
                if (needAddFormulas.size() == 1) {
                    if (!this.SameServeCode(((DesignFormulaDefine)needAddFormulas.get(0)).getOwnerLevelAndId()).booleanValue()) {
                        ++count;
                        continue;
                    }
                    isDirty = true;
                    addFormulaObj = this.objectToFormulaObj(formula, formulaObj, (DesignFormulaDefine)needAddFormulas.get(0), isDirty);
                    addFormulaObj.setIsNew(false);
                    addFormulaObj.setIsDirty(true);
                    addFormulaObj.setIsDeleted(false);
                    this.setFormulaCondition(addFormulaObj, ((FormulaCheckObj)((Object)formula)).getConditionCodes(), conditionMap);
                    formulaList.add(addFormulaObj);
                    continue;
                }
                if (needAddFormulas.size() == 0) {
                    isDirty = false;
                    addFormulaObj = this.objectToFormulaObj(formula, formulaObj, null, isDirty);
                    addFormulaObj.setIsNew(true);
                    addFormulaObj.setIsDirty(false);
                    addFormulaObj.setIsDeleted(false);
                    this.setFormulaCondition(addFormulaObj, ((FormulaCheckObj)((Object)formula)).getConditionCodes(), conditionMap);
                    formulaList.add(addFormulaObj);
                    continue;
                }
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_034);
            }
        }
        int formCodeSetSizeNew = formCodeSet.size();
        if (formCodeSetSizeNew - formCodeSetSize == formulasSheetMap.size() - 1) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_036);
        }
        if (count > 0) {
            LinkedList<Integer> linkedList = new LinkedList<Integer>();
            linkedList.add(count);
            int allCount = formulaList.size();
            linkedList.add(allCount);
            formulasSheetMap.put(OWNERLEVELANDID, linkedList);
        }
        FormulaObj[] formulaObjArray = new FormulaObj[formulaList.size()];
        this.taskDesignerService.saveFormulas(formulaList.toArray(formulaObjArray));
    }

    @Override
    public void exportAllFormulas(String formulaScheme, ArrayList<String> formKeyList, HttpServletResponse res, boolean isEfdc) throws JQException {
        try {
            String[] summary;
            List<String[]> data;
            int sheetCount = 0;
            HashSet<String> sheetTitleSet = new HashSet<String>();
            String fileName = "\u5168\u90e8\u516c\u5f0f";
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            ServletOutputStream out = res.getOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            if (!isEfdc) {
                data = this.getExcelData(formulaScheme, null);
                summary = new String[]{NUMBER, EXPRESSION, DESCRIPTION, TYPE, AUDITTYPE, BALANCEZBEXP};
                String BJSheetTitle = BJFORMULA;
                this.exportAll(workbook, sheetCount, BJSheetTitle, summary, data, (OutputStream)out, isEfdc);
            }
            summary = null;
            if (!isEfdc) {
                ++sheetCount;
                summary = new String[]{NUMBER, EXPRESSION, DESCRIPTION, TYPE, AUDITTYPE, BALANCEZBEXP};
            } else {
                summary = new String[]{NUMBER, EXPRESSION, DESCRIPTION};
            }
            for (String formKey : formKeyList) {
                data = this.getExcelData(formulaScheme, formKey);
                DesignFormDefine formDefine = this.nrDesignTimeController.querySoftFormDefine(formKey);
                String sheetTitle = formDefine.getFormCode() + " " + formDefine.getTitle();
                if (!sheetTitleSet.add(sheetTitle)) continue;
                this.exportAll(workbook, sheetCount, sheetTitle, summary, data, (OutputStream)out, isEfdc);
                ++sheetCount;
            }
            workbook.write((OutputStream)out);
            out.flush();
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage(), (Throwable)e);
                }
            }
        }
        catch (JQException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_101);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
    }

    private List<String[]> getExcelData(String formulaScheme, String form) throws Exception {
        List formulas = this.nrDesignTimeController.getAllSoftFormulasInForm(formulaScheme, form);
        if (CollectionUtils.isEmpty(formulas)) {
            return Collections.emptyList();
        }
        Map conditions = this.nrDesignTimeController.queryFormulaConditions(formulaScheme);
        ArrayList<String[]> list = new ArrayList<String[]>();
        List queryAllAuditType = this.auditTypeDefineService.queryAllAuditType();
        String audit = "";
        String balanceZB = "";
        for (DesignFormulaDefine formula : formulas) {
            if (formula == null) continue;
            String checkType = this.getCheckType(formula.getUseCalculate(), formula.getUseCheck(), formula.getUseBalance());
            for (AuditType auditType : queryAllAuditType) {
                if (auditType.getCode() == null || !auditType.getCode().equals(formula.getCheckType())) continue;
                audit = auditType.getTitle();
            }
            String condition = this.getConditionStr(formula.getKey(), conditions);
            list.add(new String[]{formula.getCode(), formula.getExpression(), formula.getDescription(), checkType, audit, balanceZB, condition});
        }
        return list;
    }

    private String getConditionStr(String key, Map<String, List<DesignFormulaCondition>> conditions) {
        if (conditions.containsKey(key)) {
            List<DesignFormulaCondition> list = conditions.get(key);
            return list.stream().map(FormulaCondition::getCode).collect(Collectors.joining(";"));
        }
        return "";
    }

    private String getCheckType(boolean calculate, boolean check, boolean balance) {
        String typeCal = "\u8fd0\u7b97\u516c\u5f0f";
        String typeCheck = "\u5ba1\u6838\u516c\u5f0f";
        String typeBalance = "\u5e73\u8861\u516c\u5f0f";
        String cellValue = calculate && !check && !balance ? typeCal : (!calculate && !check && balance ? typeBalance : (!calculate && check && !balance ? typeCheck : (calculate && !check ? typeCal + ";" + typeBalance : (!calculate && check ? typeCheck + ";" + typeBalance : (calculate && !balance ? typeCal + ";" + typeCheck : (calculate ? typeCal + ";" + typeCheck + ";" + typeBalance : ""))))));
        return cellValue;
    }

    public void exportAll(XSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers, List<String[]> excelData, OutputStream out, boolean isEfdc) throws Exception {
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        sheet.setDefaultColumnWidth(15);
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setFont(font);
        style.setWrapText(true);
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            XSSFCell cell = row.createCell((short)i);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        if (excelData != null) {
            int index = 1;
            for (String[] data : excelData) {
                row = sheet.createRow(index++);
                for (int i = 0; i < data.length; ++i) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellValue(data[i]);
                }
            }
        }
    }

    @Override
    public void exportRepeatCodeExcel(Map<String, List> formulasMap, HttpServletResponse res, boolean isEfdc) throws IOException {
        List repeatValue = formulasMap.get("repeatCode");
        String fileName = "\u7f16\u53f7\u91cd\u590d";
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        ServletOutputStream out = res.getOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(60);
        workbook.setSheetName(0, REPEATCODEINFO);
        for (int c = 0; c < repeatValue.size(); ++c) {
            String repeatCodeInfo = (String)repeatValue.get(c);
            String[] CodeInfo = repeatCodeInfo.split(":");
            String sheetName = CodeInfo[2].split(";")[0];
            String string = CodeInfo[4];
            XSSFRow row = sheet.createRow((short)c);
            XSSFCell likeCell = row.createCell(0);
            XSSFCreationHelper createHelper = workbook.getCreationHelper();
            XSSFHyperlink hyperlink = (XSSFHyperlink)createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            hyperlink.setAddress("'" + (String)sheetName + "'!" + string);
            likeCell.setHyperlink(hyperlink);
            likeCell.setCellValue(repeatCodeInfo);
            XSSFCellStyle linkStyle = workbook.createCellStyle();
            XSSFFont cellFont = workbook.createFont();
            cellFont.setUnderline((byte)1);
            cellFont.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
            linkStyle.setFont(cellFont);
            likeCell.setCellStyle(linkStyle);
        }
        String[] summary = null;
        summary = !isEfdc ? new String[]{NUMBER, EXPRESSION, DESCRIPTION, TYPE, AUDITTYPE} : new String[]{NUMBER, EXPRESSION, DESCRIPTION};
        try {
            ArrayList<List<String>> summaryData = new ArrayList<List<String>>();
            int sheetCount = 1;
            for (Map.Entry entry : formulasMap.entrySet()) {
                String formulasMapKey = (String)entry.getKey();
                List formulasMapValue = (List)entry.getValue();
                if (formulasMapKey == "repeatCode") continue;
                summaryData.add(formulasMapValue);
                this.exportExcel(workbook, sheetCount, formulasMapKey, summary, summaryData, (OutputStream)out);
                ++sheetCount;
                summaryData.clear();
            }
            workbook.write((OutputStream)out);
            out.flush();
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage(), (Throwable)e);
                }
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public void exportExcel(XSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers, List<List<String>> result, OutputStream out) throws Exception {
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        sheet.setDefaultColumnWidth(15);
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setFont(font);
        style.setWrapText(true);
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            XSSFCell cell = row.createCell((short)i);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        if (result != null) {
            int index = 1;
            ArrayList resultData = (ArrayList)result.get(0);
            for (int r = 0; r < resultData.size(); ++r) {
                row = sheet.createRow(index);
                HashMap resultMap = (HashMap)resultData.get(r);
                for (String key : resultMap.keySet()) {
                    XSSFCell cell;
                    int cellIndex;
                    String cellValue = null;
                    if (key.equals("code")) {
                        cellIndex = 0;
                        cellValue = (String)resultMap.get(key);
                        cell = row.createCell(cellIndex);
                        cell.setCellValue(cellValue.toString());
                    }
                    if (key.equals("formula")) {
                        cellIndex = 1;
                        cellValue = (String)resultMap.get(key);
                        cell = row.createCell(cellIndex);
                        cell.setCellValue(cellValue.toString());
                    }
                    if (key.equals("description")) {
                        cellIndex = 2;
                        cellValue = (String)resultMap.get(key);
                        cell = row.createCell(cellIndex);
                        cell.setCellValue(cellValue.toString());
                    }
                    if (key.equals("useCalculate")) {
                        cellIndex = 3;
                        String typeCal = "\u8fd0\u7b97\u516c\u5f0f";
                        String typeCheck = "\u5ba1\u6838\u516c\u5f0f";
                        String calculate = String.valueOf(resultMap.get("useCalculate"));
                        String check = String.valueOf(resultMap.get("useCheck"));
                        if (calculate == "true" && check == "false") {
                            cellValue = typeCal;
                        }
                        if (calculate == "true" && check == "true") {
                            cellValue = typeCal + ";" + typeCheck;
                        }
                        if (calculate == "false" && check == "false") {
                            cellValue = "";
                        }
                        if (calculate == "false" && check == "true") {
                            cellValue = typeCheck;
                        }
                        XSSFCell cell2 = row.createCell(cellIndex);
                        cell2.setCellValue(cellValue.toString());
                    }
                    if (!key.equals("checktype")) continue;
                    cellIndex = 4;
                    cellValue = resultMap.get(key) == null ? (String)resultMap.get(key) : String.valueOf(resultMap.get(key));
                    String cellVal = null;
                    List queryAllAuditType = this.auditTypeDefineService.queryAllAuditType();
                    if (cellValue != null) {
                        for (AuditType auditType : queryAllAuditType) {
                            if (!auditType.getCode().toString().equals(cellValue)) continue;
                            cellVal = auditType.getTitle();
                        }
                    } else {
                        cellVal = "";
                    }
                    XSSFCell cell3 = row.createCell(cellIndex);
                    cell3.setCellValue(cellVal.toString());
                }
                ++index;
            }
        }
    }

    private FormulaObj objectToFormulaObj(Object formula, FormulaObj formulaObj, DesignFormulaDefine needAddFormulas, Boolean isDirty) {
        FormulaCheckObj formulaCheckObj = (FormulaCheckObj)((Object)formula);
        formulaObj.setCode(formulaCheckObj.getCode());
        String formulaId = null;
        String formulaOrder = null;
        if (isDirty.booleanValue()) {
            formulaId = needAddFormulas.getKey().toString();
            formulaOrder = needAddFormulas.getOrder();
        } else {
            formulaId = ((FormulaCheckObj)((Object)formula)).getId();
            formulaOrder = ((FormulaCheckObj)((Object)formula)).getOrder();
        }
        formulaObj.setId(formulaId);
        formulaObj.setExpression(formulaCheckObj.getFormula());
        if (!com.jiuqi.bi.util.StringUtils.isEmpty((String)formulaOrder)) {
            formulaObj.setOrder(formulaOrder);
        }
        int checkType = formulaCheckObj.getChecktype() == null ? 0 : formulaCheckObj.getChecktype();
        formulaObj.setCheckType(checkType);
        formulaObj.setDescription(formulaCheckObj.getDescription());
        formulaObj.setUseCalculate(formulaCheckObj.isUseCalculate());
        formulaObj.setUseCheck(formulaCheckObj.isUseCheck());
        formulaObj.setUseBalance(formulaCheckObj.isUseBalance());
        formulaObj.setBalanceZBExp(formulaCheckObj.getBalanceZBExp());
        if (!com.jiuqi.bi.util.StringUtils.isEmpty((String)formulaCheckObj.getSchemeKey())) {
            formulaObj.setSchemeKey(formulaCheckObj.getSchemeKey());
        }
        formulaObj.setFormKey(formulaCheckObj.getFormKey() == null ? null : formulaCheckObj.getFormKey().toString());
        return formulaObj;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        cellValue = cell.getCellType() == CellType.NUMERIC ? String.valueOf(cell.getNumericCellValue()) : (cell.getCellType() == CellType.STRING ? String.valueOf(cell.getStringCellValue()) : (cell.getCellType() == CellType.BOOLEAN ? String.valueOf(cell.getBooleanCellValue()) : (cell.getCellType() == CellType.FORMULA ? String.valueOf(cell.getCellFormula()) : (cell.getCellType() == CellType.BLANK ? "" : (cell.getCellType() == CellType.ERROR ? "\u975e\u6cd5\u5b57\u7b26" : "\u672a\u77e5\u7c7b\u578b")))));
        return cellValue;
    }

    private String paraFormulaCode(DesignFormDefine form, int row) {
        String formulaNum = "";
        for (int i = String.valueOf(row).length(); i < 4; ++i) {
            formulaNum = formulaNum + "0";
        }
        String cValue = form == null ? "BJ" + formulaNum + row : form.getFormCode() + formulaNum + row;
        return cValue;
    }

    @Override
    public void exportEfdcWithStyle(List<String> jsonFormKeys, String efdcScheme, HttpServletResponse res) throws JQException, IOException {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(efdcScheme.toString());
        String fileName = "\u8d22\u52a1\u516c\u5f0f";
        if (formulaSchemeDefine != null) {
            fileName = formulaSchemeDefine.getTitle() + "_\u8d22\u52a1\u516c\u5f0f";
        }
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        ServletOutputStream out = res.getOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (int i = 0; i < jsonFormKeys.size(); ++i) {
            String formId = jsonFormKeys.get(i).toString();
            DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formId);
            String formTitle = formDefine.getTitle();
            Grid2Data styleData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
            List FormulaDefineList = this.nrDesignTimeController.getAllFormulasInForm(efdcScheme, formId);
            try {
                this.exportExcelForEfdc(workbook, out, styleData, FormulaDefineList, i, formTitle, formId);
                continue;
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), (Throwable)e);
            }
        }
        workbook.write((OutputStream)out);
        out.flush();
        if (out != null) {
            try {
                out.close();
            }
            catch (IOException e) {
                this.logger.error(e.getMessage(), (Throwable)e);
            }
        }
    }

    public void exportExcelForEfdc(XSSFWorkbook workbook, ServletOutputStream out, Grid2Data styleData, List<DesignFormulaDefine> formulaDefineList, int sheetCount, String sheetName, String formId) {
        HashMap<String, String> efdcFormulaPositionMap = new HashMap<String, String>();
        this.getefdcFormulaPosition(formId, efdcFormulaPositionMap, formulaDefineList);
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetCount, sheetName);
        sheet.setDefaultColumnWidth(15);
        int colCount = styleData.getColumnCount();
        int rowCount = styleData.getRowCount();
        for (int h = 1; h < rowCount; ++h) {
            XSSFRow row = sheet.createRow(h - 1);
            int rowHeight = styleData.getRowHeight(h);
            row.setHeightInPoints(rowHeight * 72 / 96);
            for (int l = 1; l < colCount; ++l) {
                GridCellData cellData = styleData.getGridCellData(l, h);
                this.setCellRange(cellData, sheet, h, l);
                sheet.setColumnWidth(l - 1, (int)((double)styleData.getColumnWidth(l) / 8.0 * 256.0));
                XSSFCell cell = row.createCell(l - 1);
                XSSFCellStyle style = workbook.createCellStyle();
                this.setBorderStyle(style, cellData);
                XSSFFont font = workbook.createFont();
                this.setFontStyle(font, cellData);
                style.setFont(font);
                cell.setCellStyle(style);
                cell.setCellValue(cellData.getShowText());
                String excelPositionString = h + "_" + l;
                for (Map.Entry formula : efdcFormulaPositionMap.entrySet()) {
                    String formulaPosition = (String)formula.getKey();
                    String formulaExpression = (String)formula.getValue();
                    if (!excelPositionString.equals(formulaPosition)) continue;
                    cell.setCellValue(formulaExpression);
                }
            }
        }
    }

    private void getefdcFormulaPosition(String formId, Map<String, String> efdcFormulaPositionMap, List<DesignFormulaDefine> formulaDefineList) {
        for (DesignFormulaDefine designFormulaDefine : formulaDefineList) {
            String formula = designFormulaDefine.getExpression();
            String pos = formula.trim().split("]")[0].substring(1);
            int x = Integer.parseInt(pos.split(",")[0]);
            int y = Integer.parseInt(pos.split(",")[1]);
            DesignDataLinkDefine dataLinkDefine = this.nrDesignTimeController.queryDataLinkDefineByColRow(formId, y, x);
            if (dataLinkDefine == null) continue;
            int form2ExcelX = dataLinkDefine.getPosX();
            int form2ExcelY = dataLinkDefine.getPosY();
            String mapKey = form2ExcelY + "_" + form2ExcelX;
            efdcFormulaPositionMap.put(mapKey, formula);
        }
    }

    private void setCellRange(GridCellData cellData, XSSFSheet sheet, int row, int col) {
        int colSpan = cellData.getColSpan();
        int rowSpan = cellData.getRowSpan();
        CellRangeAddress region = null;
        if (colSpan > 1 && rowSpan == 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1 + colSpan - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        } else if (rowSpan > 1 && colSpan == 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1 + rowSpan - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        } else if (colSpan > 1 && rowSpan > 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1 + rowSpan - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1 + colSpan - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        }
        if (region != null) {
            sheet.addMergedRegion(region);
        }
    }

    private void setFontStyle(XSSFFont font, GridCellData cellData) {
        XSSFColor fontColor = new XSSFColor(FormFormulaService.hex2Rgb(Grid2DataSeralizeToGeGe.intToHtmlColor(cellData.getForeGroundColor(), "#000000")), (IndexedColorMap)new DefaultIndexedColorMap());
        byte[] wRgb = new byte[]{-1, -1, -1};
        String savedRgbStr = new String(fontColor.getRGB());
        String wRgbStr = new String(wRgb);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        if (!savedRgbStr.equals(wRgbStr)) {
            font.setColor(fontColor);
        }
        int fontStyle = cellData.getFontStyle();
        font.setFontName(cellData.getFontName());
        font.setFontHeightInPoints((short)(cellData.getFontSize() * 72 / 96));
        if ((2 & fontStyle) == 2) {
            font.setBold(true);
        } else if ((4 & fontStyle) == 4) {
            font.setItalic(true);
        } else if ((8 & fontStyle) == 8) {
            font.setUnderline((byte)1);
        } else if ((0x10 & fontStyle) == 16) {
            font.setStrikeout(true);
        }
    }

    private void setBorderStyle(XSSFCellStyle style, GridCellData cellData) {
        int vertAlign = cellData.getVertAlign();
        int horzAlign = cellData.getHorzAlign();
        int backGroundStyle = cellData.getBackGroundStyle();
        XSSFColor backStyleColor = new XSSFColor(FormFormulaService.hex2Rgb("#C8CBCC"), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor backGroundColor = new XSSFColor(FormFormulaService.hex2Rgb(Grid2DataSeralizeToGeGe.intToHtmlColor(cellData.getBackGroundColor(), "#ffffff")), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor bottomBorderColor = new XSSFColor(new Color(cellData.getBottomBorderColor()), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor rightBorderColor = new XSSFColor(new Color(cellData.getRightBorderColor()), (IndexedColorMap)new DefaultIndexedColorMap());
        BorderStyle excelBottomBorderStyle = this.formBorder2ExcelBorder(cellData.getBottomBorderStyle());
        BorderStyle excelRightBorderStyle = this.formBorder2ExcelBorder(cellData.getRightBorderStyle());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(excelBottomBorderStyle);
        style.setBorderRight(excelRightBorderStyle);
        XSSFColor deaultBorderColor = new XSSFColor(new Color(217, 217, 217), (IndexedColorMap)new DefaultIndexedColorMap());
        if (cellData.getBottomBorderColor() < 0) {
            style.setBottomBorderColor(deaultBorderColor);
        } else {
            style.setBottomBorderColor(bottomBorderColor);
        }
        if (cellData.getRightBorderColor() < 0) {
            style.setRightBorderColor(deaultBorderColor);
        } else {
            style.setRightBorderColor(rightBorderColor);
        }
        if (cellData.isVertText()) {
            style.setRotation((short)255);
        }
        if (cellData.isWrapLine()) {
            style.setWrapText(true);
        }
        style.setIndention((short)cellData.getIndent());
        if (cellData.isEditable()) {
            style.setLocked(true);
        }
        if (horzAlign == 1) {
            style.setAlignment(HorizontalAlignment.forInt(1));
        } else if (horzAlign == 3) {
            style.setAlignment(HorizontalAlignment.forInt(2));
        } else if (horzAlign == 2) {
            style.setAlignment(HorizontalAlignment.forInt(3));
        }
        if (vertAlign == 1) {
            style.setVerticalAlignment(VerticalAlignment.forInt(0));
        } else if (vertAlign == 3) {
            style.setVerticalAlignment(VerticalAlignment.forInt(1));
        } else if (vertAlign == 2) {
            style.setVerticalAlignment(VerticalAlignment.forInt(2));
        }
        if (backGroundStyle == 0) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            XSSFColor silverBackGroundColor = new XSSFColor(new Color(232, 232, 232), (IndexedColorMap)new DefaultIndexedColorMap());
            byte[] wRgb = new byte[]{0, 0, 0};
            String savedRgbStr = new String(backGroundColor.getRGB());
            String wRgbStr = new String(wRgb);
            boolean silverHead = cellData.isSilverHead();
            if (savedRgbStr.equals(wRgbStr) && silverHead) {
                style.setFillForegroundColor(silverBackGroundColor);
            } else {
                style.setFillForegroundColor(backGroundColor);
            }
        } else if (backGroundStyle == 11) {
            style.setFillPattern(FillPatternType.THIN_HORZ_BANDS);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 8) {
            style.setFillPattern(FillPatternType.THIN_VERT_BANDS);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 10) {
            style.setFillPattern(FillPatternType.THIN_BACKWARD_DIAG);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 9) {
            style.setFillPattern(FillPatternType.THIN_FORWARD_DIAG);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 4) {
            style.setFillPattern(FillPatternType.SQUARES);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 5) {
            style.setFillPattern(FillPatternType.DIAMONDS);
            style.setFillForegroundColor(backStyleColor);
        }
    }

    private BorderStyle formBorder2ExcelBorder(int formBottomBorderStyle) {
        switch (formBottomBorderStyle) {
            case -1: {
                return BorderStyle.THIN;
            }
            case 0: {
                return BorderStyle.NONE;
            }
            case 1: {
                return BorderStyle.THIN;
            }
            case 2: {
                return BorderStyle.DOTTED;
            }
            case 4: {
                return BorderStyle.MEDIUM;
            }
            case 8: {
                return BorderStyle.DOUBLE;
            }
        }
        return BorderStyle.THIN;
    }

    private static Color hex2Rgb(String colorStr) {
        return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    private Boolean SameServeCode(String ownerLevelAndId) {
        try {
            boolean sameServeCode = this.serveCodeService.isSameServeCode(ownerLevelAndId);
            return sameServeCode;
        }
        catch (JQException e) {
            this.logger.error("\u53c2\u6570\u7ea7\u6b21\u5224\u65ad\u5931\u8d25\uff01");
            return true;
        }
    }

    @Override
    public void exportCycleFormulaExcel(Map<String, List<Formula>> cycleData, Map<String, String> formTitleMap, HttpServletResponse res) throws JQException {
        try {
            String fileName = "\u5faa\u73af\u516c\u5f0f";
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            ServletOutputStream out = res.getOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            String[] summary = new String[]{"\u62a5\u8868", "\u516c\u5f0f\u7f16\u53f7", "\u516c\u5f0f\u5185\u5bb9"};
            int index = 0;
            for (String order : cycleData.keySet()) {
                XSSFSheet sheet = workbook.createSheet();
                workbook.setSheetName(index, "\u5faa\u73af" + (index + 1));
                ++index;
                sheet.setDefaultColumnWidth(15);
                XSSFCellStyle style = workbook.createCellStyle();
                XSSFFont font = workbook.createFont();
                font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
                style.setFont(font);
                style.setWrapText(true);
                XSSFRow row = sheet.createRow(0);
                for (int i = 0; i < summary.length; ++i) {
                    XSSFCell cell = row.createCell((short)i);
                    cell.setCellStyle(style);
                    XSSFRichTextString text = new XSSFRichTextString(summary[i]);
                    cell.setCellValue(text.toString());
                }
                List<Formula> formulas = CycleUtil.distinctCycle(cycleData.get(order));
                if (formulas == null) continue;
                for (int i = 0; i < formulas.size(); ++i) {
                    row = sheet.createRow(i + 1);
                    XSSFCell cell0 = row.createCell(0);
                    String shwoTitle = "";
                    if (StringUtils.isNotEmpty((String)formulas.get(i).getReportName())) {
                        String formTitle = formTitleMap.get(formulas.get(i).getReportName());
                        shwoTitle = formTitle.concat("(").concat(formulas.get(i).getReportName()).concat(")");
                    } else {
                        shwoTitle = BJFORMULA;
                    }
                    cell0.setCellValue(shwoTitle);
                    XSSFCell cell1 = row.createCell(1);
                    cell1.setCellValue(formulas.get(i).getCode());
                    XSSFCell cell2 = row.createCell(2);
                    cell2.setCellValue(formulas.get(i).getFormula());
                }
            }
            workbook.write((OutputStream)out);
            out.flush();
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage(), (Throwable)e);
                }
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_230);
        }
    }
}

