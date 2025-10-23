/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.param.transfer;

import com.jiuqi.nr.param.transfer.ChangeObj;
import java.util.ArrayList;
import java.util.List;

public class FormChangeObj {
    private String formSchemeKey;
    private String formSchemeTitle;
    List<ChangeObj> addForms = new ArrayList<ChangeObj>();
    List<ChangeObj> updateForms = new ArrayList<ChangeObj>();

    public FormChangeObj() {
    }

    public FormChangeObj(String formSchemeKey, String formSchemeTitle) {
        this.formSchemeKey = formSchemeKey;
        this.formSchemeTitle = formSchemeTitle;
    }

    public FormChangeObj(String formSchemeKey, String formSchemeTitle, List<ChangeObj> addForms, List<ChangeObj> updateForms) {
        this.formSchemeKey = formSchemeKey;
        this.formSchemeTitle = formSchemeTitle;
        this.addForms = addForms;
        this.updateForms = updateForms;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public List<ChangeObj> getAddForms() {
        return this.addForms;
    }

    public void setAddForms(List<ChangeObj> addForms) {
        this.addForms = addForms;
    }

    public void addInsertForms(ChangeObj addForm) {
        this.addForms.add(addForm);
    }

    public List<ChangeObj> getUpdateForms() {
        return this.updateForms;
    }

    public void setUpdateForms(List<ChangeObj> updateForms) {
        this.updateForms = updateForms;
    }

    public void addUpdateForms(ChangeObj updateForm) {
        this.updateForms.add(updateForm);
    }
}

