/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.List;

public class DataSnapshotFormInfo {
    private String formSchemeKey;
    private String formSchemeTitle;
    private List<DataSnapshotForm> dataSnapshotForms;

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

    public List<DataSnapshotForm> getDataSnapshotForms() {
        return this.dataSnapshotForms;
    }

    public void setDataSnapshotForms(List<DataSnapshotForm> dataSnapshotForms) {
        this.dataSnapshotForms = dataSnapshotForms;
    }

    public void setDataSnapshotForms(List<FormDefine> formDefines, int index) {
        this.dataSnapshotForms = new ArrayList<DataSnapshotForm>();
        for (FormDefine formDefine : formDefines) {
            DataSnapshotForm dataSnapshotForm = new DataSnapshotForm();
            dataSnapshotForm.setFormTitle(formDefine.getTitle());
            dataSnapshotForm.setFormKey(formDefine.getFormCode());
            this.dataSnapshotForms.add(dataSnapshotForm);
        }
    }

    class DataSnapshotForm {
        private String formKey;
        private String formTitle;

        DataSnapshotForm() {
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
    }
}

