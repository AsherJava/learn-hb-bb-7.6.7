/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class ReportDataUploadToFtpVO {
    private String uploadSchemeId;
    private String ftpUserName;
    private String ftpPassword;
    private String ftpHost;
    private Integer ftpPort;
    private String ftpFilePath;
    private String ftpType;

    public String getUploadSchemeId() {
        return this.uploadSchemeId;
    }

    public void setUploadSchemeId(String uploadSchemeId) {
        this.uploadSchemeId = uploadSchemeId;
    }

    public String getFtpUserName() {
        return this.ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return this.ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpHost() {
        return this.ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public Integer getFtpPort() {
        return this.ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpFilePath() {
        return this.ftpFilePath;
    }

    public void setFtpFilePath(String ftpFilePath) {
        this.ftpFilePath = ftpFilePath;
    }

    public String getFtpType() {
        return this.ftpType;
    }

    public void setFtpType(String ftpType) {
        this.ftpType = ftpType;
    }
}

