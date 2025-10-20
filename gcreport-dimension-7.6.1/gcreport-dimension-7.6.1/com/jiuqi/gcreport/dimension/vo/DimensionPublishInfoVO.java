/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

public class DimensionPublishInfoVO {
    private String sysCode;
    private String systitle;
    private String scope;
    private String scopeTitle;
    private String tableName;
    private String tableTitle;
    private String errorInfo;
    private Boolean success;

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getScopeTitle() {
        return this.scopeTitle;
    }

    public void setScopeTitle(String scopeTitle) {
        this.scopeTitle = scopeTitle;
    }

    public String getTableTitle() {
        return this.tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSystitle() {
        return this.systitle;
    }

    public void setSystitle(String systitle) {
        this.systitle = systitle;
    }
}

