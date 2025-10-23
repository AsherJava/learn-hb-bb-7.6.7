/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dto;

import com.jiuqi.nr.zb.scheme.core.ZbValidationRule;
import java.util.List;

public class ZbValidationRuleImpl
implements ZbValidationRule {
    private String message;
    private Integer compareType;
    private String min;
    private String max;
    private String value;
    private List<String> inValues;

    public ZbValidationRuleImpl() {
    }

    public ZbValidationRuleImpl(ZbValidationRule validationRule) {
        if (validationRule != null) {
            this.compareType = validationRule.getCompareType();
            this.min = validationRule.getMin();
            this.max = validationRule.getMax();
            this.value = validationRule.getValue();
            this.inValues = validationRule.getInValues();
            this.message = validationRule.getMessage();
        }
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getCompareType() {
        return this.compareType;
    }

    public void setCompareType(Integer compareType) {
        this.compareType = compareType;
    }

    @Override
    public String getMin() {
        return this.min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    @Override
    public String getMax() {
        return this.max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public List<String> getInValues() {
        return this.inValues;
    }

    public void setInValues(List<String> inValues) {
        this.inValues = inValues;
    }
}

