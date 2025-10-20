/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.certification;

import org.json.JSONObject;

public class CertificationInfo {
    private String username;
    private String authorization;
    private String sign;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.putOpt("username", (Object)this.username);
        object.putOpt("authorization", (Object)this.authorization);
        object.putOpt("sign", (Object)this.sign);
        return object;
    }

    public CertificationInfo fromJson(JSONObject object) {
        this.username = object.optString("username");
        this.authorization = object.optString("authorization");
        this.sign = object.optString("sign");
        return this;
    }
}

