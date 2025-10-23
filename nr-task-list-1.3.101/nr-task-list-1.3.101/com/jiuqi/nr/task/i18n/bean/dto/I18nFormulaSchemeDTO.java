/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.task.i18n.bean.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;

public class I18nFormulaSchemeDTO
extends I18nBaseDTO {
    private String formSchemeKey;
    private String formulaSchemeKey;

    public I18nFormulaSchemeDTO() {
    }

    public I18nFormulaSchemeDTO(I18nBaseDTO baseDTO) {
        super.setOtherLanguageInfo(baseDTO.getOtherLanguageInfo());
        super.setLanguageKey(baseDTO.getLanguageKey());
    }

    public I18nFormulaSchemeDTO(JsonNode node) {
        super(node);
        this.formSchemeKey = node.get("formSchemeKey").textValue();
        this.formulaSchemeKey = node.get("formulaSchemeKey").textValue();
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }
}

