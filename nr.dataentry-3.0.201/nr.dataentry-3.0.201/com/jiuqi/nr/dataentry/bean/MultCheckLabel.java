/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

public class MultCheckLabel {
    private String code;
    private String title;
    private String orgCode;

    public MultCheckLabel() {
    }

    public MultCheckLabel(String code, String title, String orgCode) {
        this.code = code;
        this.title = title;
        this.orgCode = orgCode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}

