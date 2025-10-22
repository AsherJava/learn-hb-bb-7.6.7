/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.entity;

public class BillFetchCondiEO {
    public static final String TABLENAME = "BDE_BILL_FETCH_CONDI";
    private String id;
    private String fetchSchemeId;
    private String fetchCondiType;
    private String fetchCondiCode;
    private String fetchCondiValue;

    public BillFetchCondiEO() {
    }

    public BillFetchCondiEO(String id, String fetchSchemeId, String fetchCondiType, String fetchCondiCode, String fetchCondiValue) {
        this.id = id;
        this.fetchSchemeId = fetchSchemeId;
        this.fetchCondiType = fetchCondiType;
        this.fetchCondiCode = fetchCondiCode;
        this.fetchCondiValue = fetchCondiValue;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFetchCondiType() {
        return this.fetchCondiType;
    }

    public void setFetchCondiType(String fetchCondiType) {
        this.fetchCondiType = fetchCondiType;
    }

    public String getFetchCondiCode() {
        return this.fetchCondiCode;
    }

    public void setFetchCondiCode(String fetchCondiCode) {
        this.fetchCondiCode = fetchCondiCode;
    }

    public String getFetchCondiValue() {
        return this.fetchCondiValue;
    }

    public void setFetchCondiValue(String fetchCondiValue) {
        this.fetchCondiValue = fetchCondiValue;
    }
}

