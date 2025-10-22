/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.entity;

import java.util.List;

public class FormulaDataInputParam {
    private String taskKey;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private String formKey;
    private String period;
    private List<String> unCheckformulas;
    private List<String> formKeys;
    private String search;
    private int offset;
    private int limit;
    private List<Integer> formulaType;
    private List<Integer> checkType;

    public FormulaDataInputParam() {
    }

    public FormulaDataInputParam(String taskKey, String formSchemeKey, String formulaSchemeKey, String formKey, String period, List<String> unCheckformulas) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.formulaSchemeKey = formulaSchemeKey;
        this.formKey = formKey;
        this.period = period;
        this.unCheckformulas = unCheckformulas;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public List<String> getUnCheckformulas() {
        return this.unCheckformulas;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Integer> getFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(List<Integer> formulaType) {
        this.formulaType = formulaType;
    }

    public List<Integer> getCheckType() {
        return this.checkType;
    }

    public void setCheckType(List<Integer> checkType) {
        this.checkType = checkType;
    }
}

