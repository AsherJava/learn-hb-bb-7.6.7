/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.webserviceclient.vo;

import java.io.Serializable;
import java.util.List;

public class WebserviceClientParam
implements Serializable {
    private List<String> orgCodeList;
    private String periodString;
    private String url;
    private String paramsXmlText;
    private String periodType;
    private int schemePeriodType;
    private String token;
    private String wsClientBaseDataCode;
    private String zjkTableName;
    private String userName;
    private String password;

    public List<String> getOrgCodeList() {
        return this.orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getPeriodString() {
        return this.periodString;
    }

    public void setPeriodString(String periodString) {
        this.periodString = periodString;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParamsXmlText() {
        return this.paramsXmlText;
    }

    public void setParamsXmlText(String paramsXmlText) {
        this.paramsXmlText = paramsXmlText;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public int getSchemePeriodType() {
        return this.schemePeriodType;
    }

    public void setSchemePeriodType(int schemePeriodType) {
        this.schemePeriodType = schemePeriodType;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWsClientBaseDataCode() {
        return this.wsClientBaseDataCode;
    }

    public void setWsClientBaseDataCode(String wsClientBaseDataCode) {
        this.wsClientBaseDataCode = wsClientBaseDataCode;
    }

    public String getZjkTableName() {
        return this.zjkTableName;
    }

    public void setZjkTableName(String zjkTableName) {
        this.zjkTableName = zjkTableName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

