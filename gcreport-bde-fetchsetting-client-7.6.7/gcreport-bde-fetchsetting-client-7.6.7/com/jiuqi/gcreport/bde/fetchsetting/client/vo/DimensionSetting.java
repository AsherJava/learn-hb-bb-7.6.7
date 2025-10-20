/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

public class DimensionSetting {
    private String key;
    private String code;
    private String name;
    private Boolean required;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String toString() {
        return "DimensionSetting{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", name='" + this.name + '\'' + ", required=" + this.required + '}';
    }
}

