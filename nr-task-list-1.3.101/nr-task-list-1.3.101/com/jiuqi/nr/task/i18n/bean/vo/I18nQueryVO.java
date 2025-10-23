/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.i18n.bean.vo;

import com.jiuqi.nr.task.i18n.bean.vo.I18nVO;

public class I18nQueryVO
extends I18nVO {
    private String task;
    private String formSchemeKey;
    private String formGroupKey;
    private String formulaSchemeKey;
    private String formKey;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
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

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }
}

