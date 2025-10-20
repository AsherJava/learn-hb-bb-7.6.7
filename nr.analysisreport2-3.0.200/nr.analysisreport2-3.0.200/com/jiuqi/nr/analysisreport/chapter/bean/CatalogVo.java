/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.chapter.bean;

import java.util.Date;

public class CatalogVo {
    private String catalog;
    private Date catalogUpdateTime;
    private String authorization;
    private String versionKey;
    private String templateKey;

    public String getCatalog() {
        return this.catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Date getCatalogUpdateTime() {
        return this.catalogUpdateTime;
    }

    public void setCatalogUpdateTime(Date catalogUpdateTime) {
        this.catalogUpdateTime = catalogUpdateTime;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getVersionKey() {
        return this.versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public String getTemplateKey() {
        return this.templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }
}

