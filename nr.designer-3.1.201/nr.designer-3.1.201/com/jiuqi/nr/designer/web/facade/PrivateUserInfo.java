/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

public class PrivateUserInfo {
    private String name;
    private String org;

    public PrivateUserInfo(String name, String org) {
        this.name = name;
        this.org = org;
    }

    public PrivateUserInfo() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}

