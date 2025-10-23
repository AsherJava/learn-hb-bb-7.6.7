/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.param.transfer;

import com.jiuqi.nr.param.transfer.ChangeObj;
import java.util.ArrayList;
import java.util.List;

public class FormulaChangeObj {
    private String formulaSchemeKey;
    private String formulaSchemeTitle;
    private String formKey;
    private String formTitle;
    List<ChangeObj> updateFormulas = new ArrayList<ChangeObj>();
    List<ChangeObj> addFormulas = new ArrayList<ChangeObj>();
    List<ChangeObj> deleteFormulas = new ArrayList<ChangeObj>();

    public FormulaChangeObj() {
    }

    public FormulaChangeObj(String formulaSchemeKey, String formKey, List<ChangeObj> updateFormulas, List<ChangeObj> addFormulas, List<ChangeObj> deleteFormulas) {
        this.formulaSchemeKey = formulaSchemeKey;
        this.formKey = formKey;
        this.updateFormulas = updateFormulas;
        this.addFormulas = addFormulas;
        this.deleteFormulas = deleteFormulas;
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

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public List<ChangeObj> getUpdateFormulas() {
        return this.updateFormulas;
    }

    public void setUpdateFormulas(List<ChangeObj> updateFormulas) {
        this.updateFormulas = updateFormulas;
    }

    public List<ChangeObj> getAddFormulas() {
        return this.addFormulas;
    }

    public void setAddFormulas(List<ChangeObj> addFormulas) {
        this.addFormulas = addFormulas;
    }

    public List<ChangeObj> getDeleteFormulas() {
        return this.deleteFormulas;
    }

    public void setDeleteFormulas(List<ChangeObj> deleteFormulas) {
        this.deleteFormulas = deleteFormulas;
    }
}

