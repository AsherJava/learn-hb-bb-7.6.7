/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.storage.entity.impl;

import java.util.List;

public class DimTableRecord {
    private String dataScheme;
    private String tableName;
    private List<String> filedCodes;

    public DimTableRecord() {
    }

    public DimTableRecord(String dataScheme, String tableName, List<String> filedCodes) {
        this.tableName = tableName;
        this.dataScheme = dataScheme;
        this.filedCodes = filedCodes;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getFiledCodes() {
        return this.filedCodes;
    }

    public void setFiledCodes(List<String> filedCodes) {
        this.filedCodes = filedCodes;
    }
}

