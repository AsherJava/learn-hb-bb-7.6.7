/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.bean;

import java.util.UUID;

public class MultcheckSchemeOrg {
    private String key;
    private String scheme;
    private String org;

    public MultcheckSchemeOrg() {
    }

    public MultcheckSchemeOrg(String scheme, String org) {
        this.key = UUID.randomUUID().toString();
        this.scheme = scheme;
        this.org = org;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}

