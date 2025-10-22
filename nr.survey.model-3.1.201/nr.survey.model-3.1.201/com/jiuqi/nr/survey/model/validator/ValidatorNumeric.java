/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.validator;

import com.jiuqi.nr.survey.model.validator.Validator;

public class ValidatorNumeric
extends Validator {
    private int minValue;
    private int maxValue;

    public int getMinValue() {
        return this.minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}

