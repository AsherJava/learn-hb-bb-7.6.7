/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.webserviceclient.vo;

import org.json.JSONObject;

public class DataIntegrationtkExecuteParams {
    private String etlTaskKey;
    private String url;
    private String userName;
    private String pw;
    private JSONObject param = new JSONObject();

    public String getEtlTaskKey() {
        return this.etlTaskKey;
    }

    public void setEtlTaskKey(String etlTaskKey) {
        this.etlTaskKey = etlTaskKey;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPw() {
        return this.pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public JSONObject getParam() {
        return this.param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }
}

