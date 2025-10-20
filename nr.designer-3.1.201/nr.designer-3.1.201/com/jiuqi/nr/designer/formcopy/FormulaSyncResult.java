/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 */
package com.jiuqi.nr.designer.formcopy;

import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;

public class FormulaSyncResult {
    private String formulaSchemeKey;
    private String formulaSchemeTitle;
    private FormulaSchemeType formulaSchemeType;
    private String formulaCode;
    private String formKey;
    private String type = "\u516c\u5f0f";
    private String message = "\u6807\u8bc6\u51b2\u7a81\uff0c\u8df3\u8fc7";

    public FormulaSyncResult() {
    }

    public FormulaSyncResult(String type, String name, String message) {
        this.type = type;
        this.formulaCode = name;
        this.message = message;
    }

    public FormulaSyncResult(DesignFormulaDefine formula, FormulaSchemeType formulaSchemeType) {
        this.formulaSchemeKey = formula.getFormulaSchemeKey();
        this.formulaSchemeType = formulaSchemeType;
        this.formulaCode = formula.getCode();
        this.formKey = formula.getFormKey();
    }

    public FormulaSyncResult(DesignFormulaDefine formula, FormulaSchemeType formulaSchemeType, String formulaSchemeTitle) {
        this.formulaSchemeKey = formula.getFormulaSchemeKey();
        this.formulaSchemeTitle = formulaSchemeTitle;
        this.formulaSchemeType = formulaSchemeType;
        this.formulaCode = formula.getCode();
        this.formKey = formula.getFormKey();
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public FormulaSchemeType getFormulaSchemeType() {
        return this.formulaSchemeType;
    }

    public void setFormulaSchemeType(FormulaSchemeType formulaSchemeType) {
        this.formulaSchemeType = formulaSchemeType;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

