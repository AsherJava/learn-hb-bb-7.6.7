/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.dto;

public class PenetrationContextInfo {
    private String dataSchemeTableCode;
    private String dataSchemeKey;

    public String getDataSchemeTableCode() {
        return this.dataSchemeTableCode;
    }

    public void setDataSchemeTableCode(String scheculeTableName) {
        this.dataSchemeTableCode = scheculeTableName;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }
}

