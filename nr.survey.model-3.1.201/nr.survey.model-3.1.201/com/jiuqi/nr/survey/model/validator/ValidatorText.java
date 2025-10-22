/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.survey.model.validator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.survey.model.validator.Validator;

public class ValidatorText
extends Validator {
    private int minLength;
    private int maxLength;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private boolean allowDigits = true;

    public int getMinLength() {
        return this.minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isAllowDigits() {
        return this.allowDigits;
    }

    public void setAllowDigits(boolean allowDigits) {
        this.allowDigits = allowDigits;
    }
}

