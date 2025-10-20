/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.impl.dto;

public class GcTaskDataCopyDTO {
    private String formCode;
    private String tableName;
    private String tarTaskCode;
    private String srcTaskCode;
    private Boolean isFloat;
    private String floatTableName;

    public GcTaskDataCopyDTO(String formCode, String tableName, String tarTaskCode, String srcTaskCode, String floatTableName, boolean isFloat) {
        this.formCode = formCode;
        this.tableName = tableName;
        this.tarTaskCode = tarTaskCode;
        this.srcTaskCode = srcTaskCode;
        this.floatTableName = floatTableName;
        this.isFloat = isFloat;
    }

    public GcTaskDataCopyDTO() {
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTarTaskCode() {
        return this.tarTaskCode;
    }

    public void setTarTaskCode(String tarTaskCode) {
        this.tarTaskCode = tarTaskCode;
    }

    public String getSrcTaskCode() {
        return this.srcTaskCode;
    }

    public void setSrcTaskCode(String srcTaskCode) {
        this.srcTaskCode = srcTaskCode;
    }

    public Boolean getFloat() {
        return this.isFloat;
    }

    public void setFloat(Boolean aFloat) {
        this.isFloat = aFloat;
    }

    public String getFloatTableName() {
        return this.floatTableName;
    }

    public void setFloatTableName(String floatTableName) {
        this.floatTableName = floatTableName;
    }
}

