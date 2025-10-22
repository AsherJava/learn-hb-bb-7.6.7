/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

public class DataFdInfoBase {
    private String tableName;
    private String fdName;

    public DataFdInfoBase() {
    }

    public DataFdInfoBase(String filedName, String tableName) {
        this.tableName = tableName;
        this.fdName = filedName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return this.fdName;
    }

    public void setFieldName(String fdName) {
        this.fdName = fdName;
    }
}

