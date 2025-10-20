/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.penetratebill.client.vo;

public class PenetrateSchemeVO {
    private String schemeName;
    private String scopeType;
    private String scopeValue;
    private String handlerCode;
    private String customParam;
    private String openWay;

    public PenetrateSchemeVO() {
    }

    public PenetrateSchemeVO(String schemeName, String scopeType, String scopeValue, String handlerCode, String customParam, String openWay) {
        this.schemeName = schemeName;
        this.scopeType = scopeType;
        this.scopeValue = scopeValue;
        this.handlerCode = handlerCode;
        this.customParam = customParam;
        this.openWay = openWay;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getScopeType() {
        return this.scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getScopeValue() {
        return this.scopeValue;
    }

    public void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue;
    }

    public String getHandlerCode() {
        return this.handlerCode;
    }

    public void setHandlerCode(String handlerCode) {
        this.handlerCode = handlerCode;
    }

    public String getCustomParam() {
        return this.customParam;
    }

    public void setCustomParam(String customParam) {
        this.customParam = customParam;
    }

    public String getOpenWay() {
        return this.openWay;
    }

    public void setOpenWay(String openWay) {
        this.openWay = openWay;
    }
}

