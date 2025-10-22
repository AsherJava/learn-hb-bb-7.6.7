/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="FormulaData", description="\u516c\u5f0f\u4fe1\u606f")
public class FormulaData
implements Comparable,
Serializable {
    private static final long serialVersionUID = 8556866801737495298L;
    @ApiModelProperty(value="\u516c\u5f0f\u89e3\u6790\u540ekey", name="key")
    private String parsedExpressionKey;
    @ApiModelProperty(value="\u516c\u5f0f\u89e3\u6790\u524did", name="id")
    private String key;
    @ApiModelProperty(value="\u516c\u5f0fcode", name="code")
    private String code;
    @ApiModelProperty(value="\u516c\u5f0f\u8868\u8fbe\u5f0f", name="formula")
    private String formula;
    @ApiModelProperty(value="\u516c\u5f0f\u6240\u5728\u62a5\u8868key", name="formKey")
    private String formKey;
    @ApiModelProperty(value="\u516c\u5f0f\u6240\u5728\u516c\u5f0f\u65b9\u6848key", name="formKey")
    private String formulaSchemeKey;
    @ApiModelProperty(value="\u516c\u5f0f\u6240\u5728\u62a5\u8868\u6807\u9898", name="formTitle")
    private String formTitle;
    @ApiModelProperty(value="\u516c\u5f0f\u542b\u4e49", name="meanning")
    private String meaning;
    @ApiModelProperty(value="\u516c\u5f0f\u901a\u914d\u884c", name="globRow")
    private int globRow;
    @ApiModelProperty(value="\u516c\u5f0f\u901a\u914d\u5217", name="globCol")
    private int globCol;
    @ApiModelProperty(value="\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b", name="checktype")
    private int checkType;
    @ApiModelProperty(value="\u516c\u5f0f\u6392\u5e8f\u5b57\u6bb5", name="order")
    private String order;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParsedExpressionKey() {
        return this.parsedExpressionKey;
    }

    public void setParsedExpressionKey(String parsedExpressionKey) {
        this.parsedExpressionKey = parsedExpressionKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public int getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(int globRow) {
        this.globRow = globRow;
    }

    public int getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(int globCol) {
        this.globCol = globCol;
    }

    public String getMeaning() {
        return this.meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int compareTo(Object arg0) {
        if (arg0 instanceof FormulaData) {
            return this.order.compareTo(((FormulaData)arg0).getOrder());
        }
        return 0;
    }
}

