/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.form.selector.entity;

import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class OneFormFormulaOperator {
    private String formulaScheme;
    private String searchInfo;
    private List<Integer> formulaTypeList;
    private List<Integer> checkTypes;

    public OneFormFormulaOperator(String formulaScheme, String searchInfo, List<Integer> formulaTypeList, List<Integer> checkTypes) {
        this.formulaScheme = formulaScheme;
        this.searchInfo = searchInfo;
        this.formulaTypeList = formulaTypeList;
        this.checkTypes = checkTypes;
    }

    public List<FormulaDefine> filterAll(String formKy, IFormulaRunTimeController iFormulaRunTimeController) {
        List<FormulaDefine> formulaDefines = this.queryFormula(formKy, iFormulaRunTimeController);
        ArrayList<FormulaDefine> resultFormulaDefines = new ArrayList<FormulaDefine>();
        for (FormulaDefine formulaDefine : formulaDefines) {
            if (this.OneFormulaFilterBySearchInfo(formulaDefine, this.searchInfo) || this.OneFormulaFilterByCheckedTypes(formulaDefine, this.checkTypes) || this.OneFormulaFilterByTypeList(formulaDefine)) continue;
            resultFormulaDefines.add(formulaDefine);
        }
        return resultFormulaDefines;
    }

    public List<FormulaDefine> notCheckTypesAndSearchInfoFilterAll(String formKy, IFormulaRunTimeController iFormulaRunTimeController) {
        List<FormulaDefine> formulaDefines = this.queryFormula(formKy, iFormulaRunTimeController);
        ArrayList<FormulaDefine> resultFormulaDefines = new ArrayList<FormulaDefine>();
        for (FormulaDefine formulaDefine : formulaDefines) {
            if (this.OneFormulaFilterByTypeList(formulaDefine)) continue;
            resultFormulaDefines.add(formulaDefine);
        }
        return resultFormulaDefines;
    }

    public List<FormulaDefine> queryFormula(String formKey, IFormulaRunTimeController iFormulaRunTimeController) {
        List formulaDefines = iFormulaRunTimeController.getAllFormulasInForm(this.formulaScheme, formKey);
        return formulaDefines;
    }

    public boolean OneFormulaFilterByTypeList(FormulaDefine formulaDefine) {
        if (this.formulaTypeList != null && this.formulaTypeList.size() > 0) {
            return !(formulaDefine.getUseCalculate() && this.formulaTypeList.contains(1) || formulaDefine.getUseBalance() && this.formulaTypeList.contains(2) || formulaDefine.getUseCheck() && this.formulaTypeList.contains(4));
        }
        return true;
    }

    public boolean OneFormulaFilterBySearchInfo(FormulaDefine formulaDefine, String searchInfo) {
        return StringUtils.isNotEmpty((String)searchInfo) && !this.matchFormulaCode(formulaDefine, searchInfo) && !this.matchDescription(formulaDefine, searchInfo) && !this.matchExpression(formulaDefine, searchInfo);
    }

    public boolean OneFormulaFilterByCheckedTypes(FormulaDefine formulaDefine, List<Integer> checkType) {
        return checkType != null && checkType.size() > 0 && !checkType.contains(formulaDefine.getCheckType());
    }

    public boolean matchFormulaCode(FormulaDefine formulaDefine, String searchInfo) {
        String code = formulaDefine.getCode();
        return StringUtils.isNotEmpty((String)code) && code.toLowerCase().contains(searchInfo.toLowerCase());
    }

    public boolean matchDescription(FormulaDefine formulaDefine, String searchInfo) {
        String description = formulaDefine.getDescription();
        return StringUtils.isNotEmpty((String)description) && description.toLowerCase().contains(searchInfo.toLowerCase());
    }

    public boolean matchExpression(FormulaDefine formulaDefine, String searchInfo) {
        String expression = formulaDefine.getExpression();
        return StringUtils.isNotEmpty((String)expression) && expression.toLowerCase().contains(searchInfo.toLowerCase());
    }
}

