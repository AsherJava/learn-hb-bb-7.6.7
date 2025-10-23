/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.task.i18n.bean.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class I18nBaseDTO {
    private String defaultLanguageInfo;
    private String otherLanguageInfo;
    private String languageKey;

    public I18nBaseDTO() {
    }

    public I18nBaseDTO(JsonNode node) {
        this.defaultLanguageInfo = node.get("defaultLanguageInfo").textValue();
        this.otherLanguageInfo = node.get("otherLanguageInfo").textValue();
        this.languageKey = node.get("languageKey").textValue();
    }

    public String getDefaultLanguageInfo() {
        return this.defaultLanguageInfo;
    }

    public void setDefaultLanguageInfo(String defaultLanguageInfo) {
        this.defaultLanguageInfo = defaultLanguageInfo;
    }

    public String getOtherLanguageInfo() {
        return this.otherLanguageInfo;
    }

    public void setOtherLanguageInfo(String otherLanguageInfo) {
        this.otherLanguageInfo = otherLanguageInfo;
    }

    public String getLanguageKey() {
        return this.languageKey;
    }

    public void setLanguageKey(String languageKey) {
        this.languageKey = languageKey;
    }
}

