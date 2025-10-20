/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nrextracteditctrl.entity;

public class NrExtractEditCtrlItemEO {
    public static final String TABLE_NAME = "GC_NR_EXTRACT_EDIT_CTRL_FORM_ITEM";
    public static final String ALL_LINK = "*";
    public static final String ALL_FORM = "*";
    private String id;
    private String editCtrlConfId;
    private String formKey;
    private String LinkKey;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEditCtrlConfId() {
        return this.editCtrlConfId;
    }

    public void setEditCtrlConfId(String editCtrlConfId) {
        this.editCtrlConfId = editCtrlConfId;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getLinkKey() {
        return this.LinkKey;
    }

    public void setLinkKey(String linkKey) {
        this.LinkKey = linkKey;
    }
}

