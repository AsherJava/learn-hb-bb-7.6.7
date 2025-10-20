/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nrextracteditctrl.dto;

public class FormSchemeParamDTO {
    private String key;
    private String title;
    private String orgTypeCode;
    private String vaildPeriod;

    public String getOrgTypeCode() {
        return this.orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVaildPeriod() {
        return this.vaildPeriod;
    }

    public void setVaildPeriod(String vaildPeriod) {
        this.vaildPeriod = vaildPeriod;
    }
}

