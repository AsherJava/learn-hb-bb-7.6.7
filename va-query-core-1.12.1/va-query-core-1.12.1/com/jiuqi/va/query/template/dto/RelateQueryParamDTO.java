/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.dto;

public class RelateQueryParamDTO {
    private String name;
    private String title;
    private String argumentType;
    private boolean needFlag;
    private String valueType;
    private String value;

    public String getArgumentType() {
        return this.argumentType;
    }

    public void setArgumentType(String argumentType) {
        this.argumentType = argumentType;
    }

    public String getValueType() {
        return this.valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isNeedFlag() {
        return this.needFlag;
    }

    public void setNeedFlag(boolean needFlag) {
        this.needFlag = needFlag;
    }
}

