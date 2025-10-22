/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import com.jiuqi.nr.definition.common.FormulaConditionDTO;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import java.util.ArrayList;
import java.util.List;

public class DesignFormulaDTO {
    private DesignFormulaDefine designFormulaDefine;
    private String dlExpression;
    private String formulaSchemeTitle;
    private String formulaSchemeOrder;
    private String oldExpression;
    private String newExpression;
    private String uniqueCode;
    private String oldBalanceExp;
    private String newBalanceExp;
    private String resultMes;
    private boolean success;
    private String formKey;
    private List<FormulaConditionDTO> conditions;

    public DesignFormulaDTO() {
    }

    public DesignFormulaDTO(DesignFormulaDefine designFormulaDefine, String dlExpression, String resultMes, boolean success) {
        this.designFormulaDefine = designFormulaDefine;
        this.dlExpression = dlExpression;
        this.resultMes = resultMes;
        this.success = success;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getFormulaSchemeOrder() {
        return this.formulaSchemeOrder;
    }

    public void setFormulaSchemeOrder(String formulaSchemeOrder) {
        this.formulaSchemeOrder = formulaSchemeOrder;
    }

    public String getResultMes() {
        return this.resultMes;
    }

    public void setResultMes(String resultMes) {
        this.resultMes = resultMes;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DesignFormulaDefine getDesignFormulaDefine() {
        return this.designFormulaDefine;
    }

    public void setDesignFormulaDefine(DesignFormulaDefine designFormulaDefine) {
        this.designFormulaDefine = designFormulaDefine;
    }

    public String getDlExpression() {
        return this.dlExpression;
    }

    public void setDlExpression(String dlExpression) {
        this.dlExpression = dlExpression;
    }

    public String getOldExpression() {
        return this.oldExpression;
    }

    public void setOldExpression(String oldExpression) {
        this.oldExpression = oldExpression;
    }

    public String getNewExpression() {
        return this.newExpression;
    }

    public void setNewExpression(String newExpression) {
        this.newExpression = newExpression;
    }

    public String getOldBalanceExp() {
        return this.oldBalanceExp;
    }

    public void setOldBalanceExp(String oldBalanceExp) {
        this.oldBalanceExp = oldBalanceExp;
    }

    public String getNewBalanceExp() {
        return this.newBalanceExp;
    }

    public void setNewBalanceExp(String newBalanceExp) {
        this.newBalanceExp = newBalanceExp;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public List<FormulaConditionDTO> getConditions() {
        if (this.conditions == null) {
            this.conditions = new ArrayList<FormulaConditionDTO>();
        }
        return this.conditions;
    }

    public void setConditions(List<FormulaConditionDTO> conditions) {
        this.conditions = conditions;
    }
}

