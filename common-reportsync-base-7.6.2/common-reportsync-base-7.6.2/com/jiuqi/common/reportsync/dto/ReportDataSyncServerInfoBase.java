/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.reportsync.dto;

public class ReportDataSyncServerInfoBase {
    private String url;
    private String targetUrl;
    private String targetUserName;
    private String targetPwd;
    private String targetEncryptType;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetUrl() {
        return this.targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTargetUserName() {
        return this.targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getTargetPwd() {
        return this.targetPwd;
    }

    public void setTargetPwd(String targetPwd) {
        this.targetPwd = targetPwd;
    }

    public String getTargetEncryptType() {
        return this.targetEncryptType;
    }

    public void setTargetEncryptType(String targetEncryptType) {
        this.targetEncryptType = targetEncryptType;
    }
}

