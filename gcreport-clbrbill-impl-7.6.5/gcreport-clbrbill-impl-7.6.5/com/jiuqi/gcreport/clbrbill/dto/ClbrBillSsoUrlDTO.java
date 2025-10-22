/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dto;

public class ClbrBillSsoUrlDTO {
    private String userName;
    private String clbrCode;
    private String clientId;
    private String clientSecret;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }
}

