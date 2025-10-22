/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 */
package com.jiuqi.nr.form.selector.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import java.util.LinkedList;
import java.util.Map;

public class FormulaData {
    private String key;
    private String code;
    private String formula;
    private String meanning;
    private LinkedList<Integer> type = new LinkedList();
    private String checktype;
    private String formKey;
    @JsonProperty(value="_checked")
    boolean checked;

    public FormulaData() {
    }

    public FormulaData(String key, String code, String formula, String meanning, String checktype) {
        this.key = key;
        this.code = code;
        this.formula = formula;
        this.meanning = meanning;
        this.checktype = checktype;
    }

    public FormulaData(String key, String code, String formula, String meanning, LinkedList<Integer> type, String checktype, boolean checked) {
        this.key = key;
        this.code = code;
        this.formula = formula;
        this.meanning = meanning;
        this.type = type;
        this.checktype = checktype;
        this.checked = checked;
    }

    public FormulaData(FormulaDefine formulaDefine, Map<Integer, String> allAuditTypeMap) {
        this.key = formulaDefine.getKey();
        this.code = formulaDefine.getCode();
        this.formula = formulaDefine.getExpression();
        this.meanning = formulaDefine.getDescription();
        if (formulaDefine.getUseCalculate()) {
            this.type.add(1);
        }
        if (formulaDefine.getUseBalance()) {
            this.type.add(2);
        }
        if (formulaDefine.getUseCheck()) {
            this.type.add(4);
        }
        this.checktype = allAuditTypeMap.get(formulaDefine.getCheckType());
        this.checked = false;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setMeanning(String meanning) {
        this.meanning = meanning;
    }

    public void setType(LinkedList<Integer> type) {
        this.type = type;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getFormula() {
        return this.formula;
    }

    public String getMeanning() {
        return this.meanning;
    }

    public LinkedList<Integer> getType() {
        return this.type;
    }

    public String getChecktype() {
        return this.checktype;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

