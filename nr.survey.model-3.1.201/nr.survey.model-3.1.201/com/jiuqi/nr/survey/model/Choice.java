/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 */
package com.jiuqi.nr.survey.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Choice {
    private String value;
    private String text;
    private String visibleIf;
    private String enableIf;
    private boolean readOnly;

    @JsonCreator
    public static Choice fromText(String text) {
        Choice choice = new Choice();
        choice.setText(text);
        choice.setValue(text);
        return choice;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVisibleIf() {
        return this.visibleIf;
    }

    public void setVisibleIf(String visibleIf) {
        this.visibleIf = visibleIf;
    }

    public String getEnableIf() {
        return this.enableIf;
    }

    public void setEnableIf(String enableIf) {
        this.enableIf = enableIf;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}

