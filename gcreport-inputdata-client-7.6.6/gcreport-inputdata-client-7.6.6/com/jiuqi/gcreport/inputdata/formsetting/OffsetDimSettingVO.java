/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.formsetting;

public class OffsetDimSettingVO {
    private String id;
    private String formId;
    private String offsetDims;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getOffsetDims() {
        return this.offsetDims;
    }

    public void setOffsetDims(String offsetDims) {
        this.offsetDims = offsetDims;
    }

    public String toString() {
        return "OffsetDimSettingVO{id='" + this.id + '\'' + ", formId='" + this.formId + '\'' + ", offsetDims='" + this.offsetDims + '\'' + '}';
    }
}

