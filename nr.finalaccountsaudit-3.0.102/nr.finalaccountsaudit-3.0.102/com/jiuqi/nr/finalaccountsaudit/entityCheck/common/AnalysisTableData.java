/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

public class AnalysisTableData {
    private String key;
    private String value;
    private String dwzdm;
    private String orgCode;

    public AnalysisTableData(String key, String value, String dwzdm) {
        this.key = key;
        this.value = value;
        this.dwzdm = dwzdm;
    }

    public AnalysisTableData(String key, String value, String dwzdm, String orgCode) {
        this.key = key;
        this.value = value;
        this.dwzdm = dwzdm;
        this.orgCode = orgCode;
    }

    public AnalysisTableData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDwzdm() {
        return this.dwzdm;
    }

    public void setDwzdm(String dwzdm) {
        this.dwzdm = dwzdm;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}

