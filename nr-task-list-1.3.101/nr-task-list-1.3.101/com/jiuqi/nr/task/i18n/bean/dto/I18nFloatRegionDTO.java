/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.task.i18n.bean.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;

public class I18nFloatRegionDTO
extends I18nBaseDTO {
    private String formGroupKey;
    private String formGroupTitle;
    private String formKey;
    private String formTitle;
    private String floatRegionTitle;
    private String floatRegionKey;

    public I18nFloatRegionDTO() {
    }

    public I18nFloatRegionDTO(I18nBaseDTO baseDTO) {
        super.setDefaultLanguageInfo(baseDTO.getDefaultLanguageInfo());
        super.setOtherLanguageInfo(baseDTO.getOtherLanguageInfo());
        super.setLanguageKey(baseDTO.getLanguageKey());
    }

    public I18nFloatRegionDTO(JsonNode node) {
        super(node);
        this.formGroupKey = node.get("formGroupKey").textValue();
        this.formGroupTitle = node.get("formGroupTitle").textValue();
        this.formKey = node.get("formKey").textValue();
        this.formTitle = node.get("formTitle").textValue();
        this.floatRegionKey = node.get("floatRegionKey").textValue();
        this.floatRegionTitle = node.get("floatRegionTitle").textValue();
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormGroupTitle() {
        return this.formGroupTitle;
    }

    public void setFormGroupTitle(String formGroupTitle) {
        this.formGroupTitle = formGroupTitle;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFloatRegionTitle() {
        return this.floatRegionTitle;
    }

    public void setFloatRegionTitle(String floatRegionTitle) {
        this.floatRegionTitle = floatRegionTitle;
    }

    public String getFloatRegionKey() {
        return this.floatRegionKey;
    }

    public void setFloatRegionKey(String floatRegionKey) {
        this.floatRegionKey = floatRegionKey;
    }
}

