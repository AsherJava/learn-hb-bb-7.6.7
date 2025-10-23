/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.task.i18n.bean.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;

public class I18nFormSchemeDTO
extends I18nBaseDTO {
    private String taskKey;
    private String formSchemeKey;
    private String formSchemeCode;

    public I18nFormSchemeDTO() {
    }

    public I18nFormSchemeDTO(I18nBaseDTO baseDTO) {
        super.setOtherLanguageInfo(baseDTO.getOtherLanguageInfo());
        super.setLanguageKey(baseDTO.getLanguageKey());
    }

    public I18nFormSchemeDTO(JsonNode node) {
        super(node);
        this.taskKey = node.get("taskKey").textValue();
        this.formSchemeKey = node.get("formSchemeKey").textValue();
        this.formSchemeCode = node.get("formSchemeCode").textValue();
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }
}

