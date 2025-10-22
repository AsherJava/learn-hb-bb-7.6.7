/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.message;

public class CellAnnotationRelation {
    private String formKey;
    private String dataLinkKey;
    private String rowId;
    private String regionKey;
    private String show;

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getShow() {
        return this.show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

