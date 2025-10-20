/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.provider;

public class ResourceAppInfo {
    private String appName;
    private String prodLine;
    private String type;
    private String funcName;

    public ResourceAppInfo() {
    }

    public ResourceAppInfo(String prodLine, String appName) {
        this.prodLine = prodLine;
        this.appName = appName;
    }

    public ResourceAppInfo(String prodLine, String appName, String funcName) {
        this.prodLine = prodLine;
        this.appName = appName;
        this.funcName = funcName;
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

    public String getFuncName() {
        return this.funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

