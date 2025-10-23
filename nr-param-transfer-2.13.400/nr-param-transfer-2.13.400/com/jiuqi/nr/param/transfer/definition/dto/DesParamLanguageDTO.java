/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 */
package com.jiuqi.nr.param.transfer.definition.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DesParamLanguageDTO {
    private String key;
    private LanguageResourceType resourceType;
    private String resourceKey;
    private String languageType;
    private String languageInfo;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LanguageResourceType getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(LanguageResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getLanguageType() {
        return this.languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public String getLanguageInfo() {
        return this.languageInfo;
    }

    public void setLanguageInfo(String languageInfo) {
        this.languageInfo = languageInfo;
    }

    public static DesParamLanguageDTO valueOf(DesParamLanguage desParamLanguage) {
        DesParamLanguageDTO desParamLanguageDTO = new DesParamLanguageDTO();
        desParamLanguageDTO.setKey(desParamLanguage.getKey());
        desParamLanguageDTO.setResourceType(desParamLanguage.getResourceType());
        desParamLanguageDTO.setResourceKey(desParamLanguage.getResourceKey());
        desParamLanguageDTO.setLanguageType(desParamLanguage.getLanguageType());
        desParamLanguageDTO.setLanguageInfo(desParamLanguage.getLanguageInfo());
        return desParamLanguageDTO;
    }

    public void value2Define(DesParamLanguage formDefine) {
        formDefine.setKey(this.getKey());
        formDefine.setResourceType(this.getResourceType());
        formDefine.setResourceKey(this.getResourceKey());
        formDefine.setLanguageType(this.getLanguageType());
        formDefine.setLanguageInfo(this.getLanguageInfo());
    }
}

