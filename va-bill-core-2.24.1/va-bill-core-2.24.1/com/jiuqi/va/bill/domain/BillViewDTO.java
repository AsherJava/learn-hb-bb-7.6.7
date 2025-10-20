/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain;

import java.util.Map;

public class BillViewDTO {
    private String defineCode;
    private long defineVer;
    private String billCode;
    private String dataId;
    private String verifyCode;
    private String triggerOrigin;
    private String viewName;
    private String reqID;
    private String cacheID;
    private String schemeCode;
    private Map<String, Object> action;
    private Map<String, Object> contextParams;
    private Map<String, Object> initValues;
    private String externalViewName;

    public Map<String, Object> getContextParams() {
        return this.contextParams;
    }

    public void setContextParams(Map<String, Object> contextParams) {
        this.contextParams = contextParams;
    }

    public Map<String, Object> getInitValues() {
        return this.initValues;
    }

    public void setInitValues(Map<String, Object> initValues) {
        this.initValues = initValues;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public long getDefineVer() {
        return this.defineVer;
    }

    public void setDefineVer(long defineVer) {
        this.defineVer = defineVer;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getTriggerOrigin() {
        return this.triggerOrigin;
    }

    public void setTriggerOrigin(String triggerOrigin) {
        this.triggerOrigin = triggerOrigin;
    }

    public String getViewName() {
        return this.viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getAction() {
        return this.action;
    }

    public void setAction(Map<String, Object> action) {
        this.action = action;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getExternalViewName() {
        return this.externalViewName;
    }

    public void setExternalViewName(String externalViewName) {
        this.externalViewName = externalViewName;
    }

    public String getReqID() {
        return this.reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getCacheID() {
        return this.cacheID;
    }

    public void setCacheID(String cacheID) {
        this.cacheID = cacheID;
    }
}

