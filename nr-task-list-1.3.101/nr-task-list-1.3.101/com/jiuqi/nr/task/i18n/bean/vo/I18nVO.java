/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.i18n.bean.vo;

public class I18nVO {
    private String languageType;
    private String resourceType;

    public Integer getLanguageType() {
        return Integer.valueOf(this.languageType);
    }

    public String getTrulyLanguageType() {
        return this.languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public Integer getResourceType() {
        return Integer.valueOf(this.resourceType);
    }

    public String getTrulyResourceType() {
        return this.resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}

