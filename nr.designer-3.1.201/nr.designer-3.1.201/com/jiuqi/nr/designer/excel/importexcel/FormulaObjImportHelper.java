/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.designer.excel.importexcel;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.excel.importexcel.cache.ExcelImportContext;
import com.jiuqi.nr.designer.excel.importexcel.common.BJFormulaInfo;
import com.jiuqi.nr.designer.excel.importexcel.util.FormHelper;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaObjImportHelper {
    @Autowired
    private FormHelper formHelper;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    private static final String BJ = "BJ-";

    public void createExcelFormulaCache(Cell cell, ExcelImportContext importContext) {
        int col = cell.getColumnIndex() + 1;
        int row = cell.getRowIndex() + 1;
        String formula = cell.getCellFormula();
        if (formula.contains("!")) {
            String colTitle = this.formHelper.getColumnTitle(col);
            StringBuilder expression = new StringBuilder();
            expression.append(importContext.getDesignFormDefine().getTitle()).append("!").append(colTitle).append(row).append(" = ");
            expression.append(formula);
            importContext.getFormulaImportContext().getBJFormulaList().add(expression.toString());
        } else {
            importContext.getFormulaImportContext().getTmpFormulasMap().put(row + "_" + col, formula);
        }
    }

    public void createExcelFormulaDefine(int col, int row, String formula, ExcelImportContext importContext) {
        String colTitle = this.formHelper.getColumnTitle(col);
        StringBuilder expression = new StringBuilder();
        expression.append(colTitle).append(row).append(" = ");
        expression.append(formula);
        DesignFormulaDefine designFormulaDefine = this.nrDesignTimeController.createFormulaDefine();
        designFormulaDefine.setExpression(expression.toString());
        designFormulaDefine.setCode(this.getNewFormulaCode(importContext));
        designFormulaDefine.setFormKey(importContext.getFormKey());
        designFormulaDefine.setFormulaSchemeKey(importContext.getFormulaImportContext().getDesignFormulaSchemeDefine().getKey());
        designFormulaDefine.setUseCalculate(true);
        designFormulaDefine.setOrder(OrderGenerator.newOrder());
        importContext.getFormulaImportContext().getFormulaDefineList().add(designFormulaDefine);
    }

    private String getNewFormulaCode(ExcelImportContext importContext) {
        LinkedList<String> formulaCodeList = importContext.getFormulaImportContext().getFormulaCode();
        String formCode = importContext.getDesignFormDefine().getFormCode();
        if (formulaCodeList.isEmpty()) {
            String formulaCode = formCode + "001";
            formulaCodeList.add(formulaCode);
            return formulaCode;
        }
        String serialNumber = formulaCodeList.getLast().substring(8);
        String newNum = String.valueOf(Integer.parseInt(serialNumber) + 1);
        int newL = newNum.length();
        if (newL < serialNumber.length()) {
            StringBuilder sb = new StringBuilder();
            sb.append(newNum);
            while (newL < serialNumber.length()) {
                sb.insert(0, "0");
                ++newL;
            }
            newNum = sb.toString();
        }
        String formulaCode = formCode + newNum;
        formulaCodeList.add(formulaCode);
        return formulaCode;
    }

    private String getBJNewFormulaCode(BJFormulaInfo bJFormulaInfo, String formCode) {
        if (StringUtils.isEmpty((String)bJFormulaInfo.getLastFormulaCode())) {
            String formulaCode = formCode + "001";
            bJFormulaInfo.setLastFormulaCode(formulaCode);
            return formulaCode;
        }
        String serialNumber = bJFormulaInfo.getLastFormulaCode().substring(8);
        String newNum = String.valueOf(Integer.parseInt(serialNumber) + 1);
        int newL = newNum.length();
        if (newL < serialNumber.length()) {
            StringBuilder sb = new StringBuilder();
            sb.append(newNum);
            while (newL < serialNumber.length()) {
                sb.insert(0, "0");
                ++newL;
            }
            newNum = sb.toString();
        }
        String formulaCode = formCode + newNum;
        bJFormulaInfo.setLastFormulaCode(formulaCode);
        return formulaCode;
    }

    public List<DesignFormulaDefine> createBJFormulas(BJFormulaInfo bJFormulaInfo) {
        ArrayList<DesignFormulaDefine> formulaDefines = new ArrayList<DesignFormulaDefine>();
        Map<String, String> map = bJFormulaInfo.getOldNameAndNewName();
        Map<String, List<String>> bJFormluas = bJFormulaInfo.getbJFormulas();
        if (bJFormluas != null && bJFormluas.size() > 0) {
            for (Map.Entry<String, List<String>> entry : bJFormluas.entrySet()) {
                List<String> bJFormluasList;
                String formKey = entry.getKey();
                DesignFormDefine designFormDefine = this.nrDesignTimeController.queryFormById(formKey);
                if (designFormDefine == null || this.formHelper.listIsEmpty(bJFormluasList = entry.getValue())) continue;
                bJFormulaInfo.setLastFormulaCode("");
                String formCode = designFormDefine.getFormCode();
                int count = this.nrDesignTimeController.getBJFormulaCountByFormulaSchemeKey(bJFormulaInfo.getDesignFormulaSchemeDefine().getKey(), formKey);
                if (count != 0) {
                    if (count > 99) {
                        bJFormulaInfo.setLastFormulaCode(formCode + count);
                    } else {
                        String strCount = String.valueOf(count);
                        StringBuilder sb = new StringBuilder();
                        sb.append(strCount);
                        while (sb.length() < 3) {
                            sb.insert(0, "0");
                        }
                        bJFormulaInfo.setLastFormulaCode(formCode + sb.toString());
                    }
                }
                for (String bJFormlua : bJFormluasList) {
                    for (Map.Entry<String, String> entry2 : map.entrySet()) {
                        String newTitle = entry2.getValue();
                        String oldTitle = entry2.getKey();
                        if (oldTitle.equals(newTitle) || !bJFormlua.contains(oldTitle) || newTitle.equals(designFormDefine.getTitle())) continue;
                        bJFormlua = bJFormlua.replace(oldTitle, newTitle);
                    }
                    DesignFormulaDefine designFormulaDefine = this.nrDesignTimeController.createFormulaDefine();
                    designFormulaDefine.setExpression(bJFormlua);
                    designFormulaDefine.setCode(this.getBJNewFormulaCode(bJFormulaInfo, formCode));
                    designFormulaDefine.setFormKey(formKey);
                    designFormulaDefine.setFormulaSchemeKey(bJFormulaInfo.getDesignFormulaSchemeDefine().getKey());
                    designFormulaDefine.setUseCalculate(true);
                    designFormulaDefine.setOrder(OrderGenerator.newOrder());
                    formulaDefines.add(designFormulaDefine);
                }
            }
        }
        return formulaDefines;
    }
}

