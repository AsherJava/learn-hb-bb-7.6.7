/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.common;

public class DataFieldMap {
    private String fieldCode;
    private String dataTableCode;

    public DataFieldMap() {
    }

    public DataFieldMap(String fieldCode, String dataTableCode) {
        this.fieldCode = fieldCode;
        this.dataTableCode = dataTableCode;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getDataTableCode() {
        return this.dataTableCode;
    }

    public void setDataTableCode(String dataTableCode) {
        this.dataTableCode = dataTableCode;
    }
}

