/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.nr.zb.scheme.common.CompareType;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.internal.dto.ValidationRuleDTO;
import java.util.List;

public class ValidationRuleVO {
    private String message;
    private Integer compareType;
    private String min;
    private String max;
    private String value;
    private List<String> inValues;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCompareType() {
        return this.compareType;
    }

    public void setCompareType(Integer compareType) {
        this.compareType = compareType;
    }

    public String getMin() {
        return this.min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return this.max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getInValues() {
        return this.inValues;
    }

    public void setInValues(List<String> inValues) {
        this.inValues = inValues;
    }

    public ValidationRuleDTO toValidationRuleDTO() {
        if (this.compareType == null) {
            return null;
        }
        CompareType compareType = CompareType.fromType(this.compareType);
        if (compareType == null) {
            return null;
        }
        ValidationRuleDTO dto = new ValidationRuleDTO();
        dto.setMessage(this.message);
        dto.setLeftValue(this.min);
        dto.setRightValue(this.max);
        dto.setValue(this.value);
        dto.setInValues(this.inValues);
        dto.setCompareType(compareType);
        return dto;
    }

    private boolean valueIsNull() {
        return this.value == null;
    }

    public ValidationRuleVO() {
    }

    public ValidationRuleVO(ValidationRule validationRule) {
        this.compareType = validationRule.getCompareType().getValue();
        this.inValues = validationRule.getInValues();
        this.value = validationRule.getValue();
        this.min = validationRule.getLeftValue();
        this.max = validationRule.getRightValue();
        this.message = validationRule.getMessage();
    }

    public String toString() {
        return "ValidationRuleVO{message='" + this.message + '\'' + ", compareType=" + this.compareType + ", min='" + this.min + '\'' + ", max='" + this.max + '\'' + ", value='" + this.value + '\'' + ", inValues=" + this.inValues + '}';
    }
}

