/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.survey.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.define.INumberQuestion;

public class NumberQuestion
extends Element
implements INumberQuestion {
    private String showType;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private int decimal = 2;
    private String defaultValue;

    @Override
    public String getShowType() {
        return this.showType;
    }

    @Override
    public void setShowType(String showType) {
        this.showType = showType;
    }

    @Override
    public int getDecimal() {
        return this.decimal;
    }

    @Override
    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

