/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.vo;

public class MappingSchemeVO {
    private String mappingSchemeKey;
    private String mappingSchemeTitle;
    private String formSchemeKey;

    public MappingSchemeVO(String mappingSchemeKey, String mappingSchemeTitle) {
        this.mappingSchemeKey = mappingSchemeKey;
        this.mappingSchemeTitle = mappingSchemeTitle;
    }

    public MappingSchemeVO(String mappingSchemeKey, String mappingSchemeTitle, String formSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
        this.mappingSchemeTitle = mappingSchemeTitle;
        this.formSchemeKey = formSchemeKey;
    }

    public MappingSchemeVO() {
    }

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public String getMappingSchemeTitle() {
        return this.mappingSchemeTitle;
    }

    public void setMappingSchemeTitle(String mappingSchemeTitle) {
        this.mappingSchemeTitle = mappingSchemeTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

