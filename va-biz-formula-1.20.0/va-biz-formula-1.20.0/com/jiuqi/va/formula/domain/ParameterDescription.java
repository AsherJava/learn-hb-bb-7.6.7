/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.domain;

public class ParameterDescription {
    private String name;
    private String type;
    private String description;
    private Boolean required;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public ParameterDescription(String name, String type, String description, Boolean required) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.required = required;
    }

    public ParameterDescription() {
    }
}

