/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 */
package com.jiuqi.nr.task.i18n.bean.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;

public class I18nFormDTO
extends I18nBaseDTO {
    private String formSchemeKey;
    private String formKey;
    private String formCode;
    private String formType;
    private ComponentDefine designer;

    public I18nFormDTO() {
    }

    public I18nFormDTO(I18nBaseDTO baseDTO) {
        super.setOtherLanguageInfo(baseDTO.getOtherLanguageInfo());
        super.setLanguageKey(baseDTO.getLanguageKey());
    }

    public I18nFormDTO(JsonNode node) {
        super(node);
        this.formSchemeKey = node.get("formSchemeKey").textValue();
        this.formKey = node.get("formKey").textValue();
        this.formCode = node.get("formCode").textValue();
        this.formType = node.get("formType").textValue();
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public ComponentDefine getDesigner() {
        return this.designer;
    }

    public void setDesign(ComponentDefine designer) {
        this.designer = designer;
    }
}

