/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.penetratebill.client.common;

public class CustomParam {
    private String code;
    private String name;
    private String value = "";
    private boolean required = true;
    private String type = "string";

    public CustomParam() {
    }

    public CustomParam(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public CustomParam(String code, String name, String value, boolean required, String type) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.required = required;
        this.type = type;
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

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

