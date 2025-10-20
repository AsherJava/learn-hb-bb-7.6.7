/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.financialstatus.vo;

public class FinancialStatusManageVo {
    String code;
    String appName;
    String prodLine;
    String name;
    Integer order;

    public FinancialStatusManageVo(String code, String name, String appName, String prodLine, Integer order) {
        this.code = code;
        this.name = name;
        this.appName = appName;
        this.prodLine = prodLine;
        this.order = order;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getProdLine() {
        return this.prodLine;
    }

    public void setProdLine(String prodLine) {
        this.prodLine = prodLine;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}

