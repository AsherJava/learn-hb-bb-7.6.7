/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.pool2.impl.GenericObjectPoolConfig
 */
package com.jiuqi.gcreport.archive.common;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value={"classpath:/config/archiveConfig.properties"}, ignoreResourceNotFound=true)
public class ArchiveProperties
extends GenericObjectPoolConfig {
    @Value(value="${archive.path-prefix:}")
    private String ftpPathPrefix;
    @Value(value="${archive.host:}")
    private String host;
    @Value(value="${archive.port:0}")
    private int port;
    @Value(value="${archive.user:}")
    private String userName;
    @Value(value="${archive.pwd:}")
    private String pwd;
    @Value(value="${archive.appID:}")
    private String appID;
    @Value(value="${archive.keySecret:}")
    private String keySecret;
    @Value(value="${archive.retryCount:3}")
    private int retryCount;
    @Value(value="${archive.efsAddress:}")
    private String efsAddress;
    @Value(value="${archive.sendItemMaxSize:50}")
    private int sendItemMaxSize;
    @Value(value="${archive.privateKey:}")
    private String privateKey;
    @Value(value="${archive.isSFTP:false}")
    private Boolean isSFTP = false;
    @Value(value="${archive.hashAlgorithm:SHA-256}")
    private String hashAlgorithm;
    @Value(value="${archive.isInspurSingleForm:}")
    private Boolean isInspurSingleForm = false;
    @Value(value="${archive.inspurSourceSys:}")
    private String inspurSourceSys;
    @Value(value="${archive.inspurCheckCode:}")
    private String inspurCheckCode;

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getEfsAddress() {
        return this.efsAddress;
    }

    public void setEfsAddress(String efsAddress) {
        this.efsAddress = efsAddress;
    }

    public String getAppID() {
        return this.appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getKeySecret() {
        return this.keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    public int getSendItemMaxSize() {
        return this.sendItemMaxSize;
    }

    public void setSendItemMaxSize(int sendItemMaxSize) {
        this.sendItemMaxSize = sendItemMaxSize;
    }

    public String getFtpPathPrefix() {
        return this.ftpPathPrefix == null ? "" : this.ftpPathPrefix;
    }

    public void setFtpPathPrefix(String ftpPathPrefix) {
        this.ftpPathPrefix = ftpPathPrefix;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Boolean isSFTP() {
        return this.isSFTP;
    }

    public void setSFTP(Boolean SFTP) {
        this.isSFTP = SFTP;
    }

    public Boolean getSFTP() {
        return this.isSFTP;
    }

    public String getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public Boolean getInspurSingleForm() {
        return this.isInspurSingleForm;
    }

    public void setInspurSingleForm(Boolean inspurSingleForm) {
        this.isInspurSingleForm = inspurSingleForm;
    }

    public String getInspurSourceSys() {
        return this.inspurSourceSys;
    }

    public void setInspurSourceSys(String inspurSourceSys) {
        this.inspurSourceSys = inspurSourceSys;
    }

    public String getInspurCheckCode() {
        return this.inspurCheckCode;
    }

    public void setInspurCheckCode(String inspurCheckCode) {
        this.inspurCheckCode = inspurCheckCode;
    }
}

