/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.common;

public class BizAssTable {
    private String tableName;
    private String fieldName;
    private String dbFieldName;
    private boolean isMainDim;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public String getDbFieldName() {
        return this.dbFieldName;
    }

    public boolean isMainDim() {
        return this.isMainDim;
    }

    public void setMainDim(boolean mainDim) {
        this.isMainDim = mainDim;
    }
}

