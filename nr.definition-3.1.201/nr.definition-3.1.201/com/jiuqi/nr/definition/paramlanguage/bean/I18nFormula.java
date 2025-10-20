/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.np.dataengine.definitions.Formula;

public class I18nFormula
extends Formula {
    private Formula formula;
    private String meaning;

    public I18nFormula(Formula formula) {
        this.formula = formula;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMeanning() {
        return this.meaning;
    }

    public String getId() {
        return this.formula.getId();
    }

    public String getCode() {
        return this.formula.getCode();
    }

    public String getFormula() {
        return this.formula.getFormula();
    }

    public String getOrder() {
        return this.formula.getOrder();
    }

    public String getReportName() {
        return this.formula.getReportName();
    }

    public String getFormKey() {
        return this.formula.getFormKey();
    }

    public Integer getChecktype() {
        return this.formula.getChecktype();
    }

    public boolean isAutoCalc() {
        return this.formula.isAutoCalc();
    }

    public String getBalanceZBExp() {
        return this.formula.getBalanceZBExp();
    }
}

