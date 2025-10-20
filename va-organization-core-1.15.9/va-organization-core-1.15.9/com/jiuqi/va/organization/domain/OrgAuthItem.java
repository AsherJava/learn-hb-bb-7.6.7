/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.organization.domain;

public class OrgAuthItem {
    private int biztype;
    private String bizname;
    private int authtype;
    private String orgname;
    private int authflag;

    public int getBiztype() {
        return this.biztype;
    }

    public void setBiztype(int biztype) {
        this.biztype = biztype;
    }

    public String getBizname() {
        return this.bizname;
    }

    public void setBizname(String bizname) {
        this.bizname = bizname;
    }

    public int getAuthtype() {
        return this.authtype;
    }

    public void setAuthtype(int authtype) {
        this.authtype = authtype;
    }

    public String getOrgname() {
        return this.orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public int getAuthflag() {
        return this.authflag;
    }

    public void setAuthflag(int authflag) {
        this.authflag = authflag;
    }
}

