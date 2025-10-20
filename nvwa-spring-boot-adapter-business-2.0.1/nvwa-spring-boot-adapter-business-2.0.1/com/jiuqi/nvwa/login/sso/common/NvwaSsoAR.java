/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nvwa.login.sso.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Map;

public class NvwaSsoAR {
    private String token;
    private boolean certify = false;
    private String userName;
    private String orgCode;
    private String message;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date loginDate;
    private Map<String, Object> extInfo = null;
    private String failUrl;

    public boolean isCertify() {
        return this.certify;
    }

    public void setCertify(boolean certify) {
        this.certify = certify;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Date getLoginDate() {
        return this.loginDate;
    }

    public Map<String, Object> getExtInfo() {
        return this.extInfo;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFailUrl() {
        return this.failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }
}

