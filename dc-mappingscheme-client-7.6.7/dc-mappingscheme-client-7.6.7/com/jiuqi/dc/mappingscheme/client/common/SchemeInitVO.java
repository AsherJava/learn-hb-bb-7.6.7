/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.common;

public class SchemeInitVO {
    private String odsFieldName;
    private String odsFieldTitle;
    private String savedSql;
    private Boolean enable;

    public String getOdsFieldName() {
        return this.odsFieldName;
    }

    public void setOdsFieldName(String odsFieldName) {
        this.odsFieldName = odsFieldName;
    }

    public String getOdsFieldTitle() {
        return this.odsFieldTitle;
    }

    public void setOdsFieldTitle(String odsFieldTitle) {
        this.odsFieldTitle = odsFieldTitle;
    }

    public Boolean getEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getSavedSql() {
        return this.savedSql;
    }

    public void setSavedSql(String savedSql) {
        this.savedSql = savedSql;
    }
}

