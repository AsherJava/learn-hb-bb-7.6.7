/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.task.i18n.bean.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;

public class I18nFormulaDTO
extends I18nBaseDTO {
    private String formulaSchemeKey;
    private String formulaSchemeTitle;
    private String formulaKey;
    private String formulaCode;
    private String formula;
    private String formulaType;

    public I18nFormulaDTO() {
    }

    public I18nFormulaDTO(I18nBaseDTO baseDTO) {
        this.setOtherLanguageInfo(baseDTO.getOtherLanguageInfo());
        this.setLanguageKey(baseDTO.getLanguageKey());
    }

    public I18nFormulaDTO(JsonNode node) {
        super(node);
        this.formulaSchemeKey = node.get("formulaSchemeKey").textValue();
        this.formulaSchemeTitle = node.get("formulaSchemeTitle").textValue();
        this.formulaCode = node.get("formulaCode").textValue();
        this.formula = node.get("formula").textValue();
        this.formulaType = node.get("formulaType").textValue();
        this.formulaKey = node.get("formulaKey").textValue();
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }
}

