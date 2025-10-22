/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.service;

public class IHBcheckReuslt {
    private String appCode;
    private boolean result;
    private String detail;

    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}

