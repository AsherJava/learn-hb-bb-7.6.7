/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.facade.FormulaSchemeObj;
import java.util.ArrayList;
import java.util.List;

public class FlowCalcScheme {
    String formScheme;
    String[] checkBeforeCheckValue;
    String[] checkBeforeFormulaValue;
    String[] submitAfterFormulaValue;
    List<FormulaSchemeObj> list = new ArrayList<FormulaSchemeObj>();

    public List<FormulaSchemeObj> getList() {
        return this.list;
    }

    public void setList(List<FormulaSchemeObj> list) {
        this.list = list;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String[] getCheckBeforeCheckValue() {
        return this.checkBeforeCheckValue;
    }

    public void setCheckBeforeCheckValue(String[] checkBeforeCheckValue) {
        this.checkBeforeCheckValue = checkBeforeCheckValue;
    }

    public String[] getCheckBeforeFormulaValue() {
        return this.checkBeforeFormulaValue;
    }

    public void setCheckBeforeFormulaValue(String[] checkBeforeFormulaValue) {
        this.checkBeforeFormulaValue = checkBeforeFormulaValue;
    }

    public String[] getSubmitAfterFormulaValue() {
        return this.submitAfterFormulaValue;
    }

    public void setSubmitAfterFormulaValue(String[] submitAfterFormulaValue) {
        this.submitAfterFormulaValue = submitAfterFormulaValue;
    }
}

