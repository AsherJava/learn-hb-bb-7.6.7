/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.domain;

import java.util.List;

public class FieldGrowthDataQueryParam {
    private String formSchemeKey;
    private String mdCode;
    private String datatime;
    private List<String> queryFields;
    private String entityID;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getDatatime() {
        return this.datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public List<String> getQueryFields() {
        return this.queryFields;
    }

    public void setQueryFields(List<String> queryFields) {
        this.queryFields = queryFields;
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }
}

