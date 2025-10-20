/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class DimensionData {
    private String tableKey;
    private String masterKey;

    public DimensionData() {
    }

    public DimensionData(String tableKey, String masterKey) {
        this.tableKey = tableKey;
        this.masterKey = masterKey;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }
}

