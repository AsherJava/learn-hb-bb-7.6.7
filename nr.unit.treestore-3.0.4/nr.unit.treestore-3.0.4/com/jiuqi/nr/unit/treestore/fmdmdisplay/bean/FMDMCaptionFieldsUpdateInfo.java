/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.fmdmdisplay.bean;

import java.util.ArrayList;
import java.util.List;

public class FMDMCaptionFieldsUpdateInfo {
    private String formSchemeKey;
    private List<String> addCaptionFields = new ArrayList<String>();
    private List<String> delCaptionFields = new ArrayList<String>();

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getAddCaptionFields() {
        return this.addCaptionFields;
    }

    public void setAddCaptionFields(List<String> addCaptionFields) {
        this.addCaptionFields = addCaptionFields;
    }

    public List<String> getDelCaptionFields() {
        return this.delCaptionFields;
    }

    public void setDelCaptionFields(List<String> delCaptionFields) {
        this.delCaptionFields = delCaptionFields;
    }
}

