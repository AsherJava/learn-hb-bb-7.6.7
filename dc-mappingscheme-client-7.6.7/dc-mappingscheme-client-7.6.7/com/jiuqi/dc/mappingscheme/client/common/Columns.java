/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.common;

import java.io.Serializable;

public class Columns
implements Serializable {
    private static final long serialVersionUID = 1745156267878642390L;
    private String fieldName;
    private String fieldTitle;
    private String odsFieldName;

    public Columns() {
    }

    public Columns(String fieldName, String fieldTitle, String odsFieldName) {
        this.fieldName = fieldName;
        this.fieldTitle = fieldTitle;
        this.odsFieldName = odsFieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getOdsFieldName() {
        return this.odsFieldName;
    }

    public void setOdsFieldName(String odsFieldName) {
        this.odsFieldName = odsFieldName;
    }
}

