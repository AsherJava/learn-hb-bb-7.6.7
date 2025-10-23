/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.save;

public class FormSaveContext {
    private String formKey;
    private Boolean formulaChanged;
    private Boolean formStyleChanged;

    public FormSaveContext() {
    }

    public FormSaveContext(String formKey, Boolean formStyleChanged) {
        this.formKey = formKey;
        this.formStyleChanged = formStyleChanged;
    }

    public FormSaveContext(String formKey, Boolean formulaChanged, Boolean formStyleChanged) {
        this.formKey = formKey;
        this.formulaChanged = formulaChanged;
        this.formStyleChanged = formStyleChanged;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Boolean isFormulaChanged() {
        return this.formulaChanged;
    }

    public void setFormulaChanged(Boolean formulaChanged) {
        this.formulaChanged = formulaChanged;
    }

    public Boolean isFormStyleChanged() {
        return this.formStyleChanged;
    }

    public void setFormStyleChanged(Boolean formStyleChanged) {
        this.formStyleChanged = formStyleChanged;
    }
}

