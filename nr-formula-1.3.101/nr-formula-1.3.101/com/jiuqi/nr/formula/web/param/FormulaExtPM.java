/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.param;

import com.jiuqi.nr.formula.dto.FormulaExtDTO;
import java.util.List;

public class FormulaExtPM {
    private String formulaSchemeKey;
    private boolean efdcCheck;
    private List<FormulaExtDTO> itemList;
    private List<String> deleted;
    private String formKey;
    private String unit;

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public boolean isEfdcCheck() {
        return this.efdcCheck;
    }

    public void setEfdcCheck(boolean efdcCheck) {
        this.efdcCheck = efdcCheck;
    }

    public List<FormulaExtDTO> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<FormulaExtDTO> itemList) {
        this.itemList = itemList;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getDeleted() {
        return this.deleted;
    }

    public void setDeleted(List<String> deleted) {
        this.deleted = deleted;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

