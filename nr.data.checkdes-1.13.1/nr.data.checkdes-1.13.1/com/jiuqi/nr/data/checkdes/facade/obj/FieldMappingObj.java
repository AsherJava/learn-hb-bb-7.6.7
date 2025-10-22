/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.facade.obj;

public class FieldMappingObj {
    private String dataFieldCode;
    private String dataTableCode;

    public FieldMappingObj() {
    }

    public FieldMappingObj(String dataFieldCode, String dataTableCode) {
        this.dataFieldCode = dataFieldCode;
        this.dataTableCode = dataTableCode;
    }

    public String getDataFieldCode() {
        return this.dataFieldCode;
    }

    public void setDataFieldCode(String dataFieldCode) {
        this.dataFieldCode = dataFieldCode;
    }

    public String getDataTableCode() {
        return this.dataTableCode;
    }

    public void setDataTableCode(String dataTableCode) {
        this.dataTableCode = dataTableCode;
    }
}

