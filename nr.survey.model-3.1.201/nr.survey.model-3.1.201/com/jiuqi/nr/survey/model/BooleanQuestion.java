/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Element;

public class BooleanQuestion
extends Element {
    private String labelTrue;
    private String labelFalse;
    private String defaultValue;

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getLabelTrue() {
        return this.labelTrue;
    }

    public void setLabelTrue(String labelTrue) {
        this.labelTrue = labelTrue;
    }

    public String getLabelFalse() {
        return this.labelFalse;
    }

    public void setLabelFalse(String labelFalse) {
        this.labelFalse = labelFalse;
    }
}

