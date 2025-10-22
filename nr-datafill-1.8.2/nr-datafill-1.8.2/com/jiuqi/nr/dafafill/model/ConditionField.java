/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.enums.DefaultValueType;
import com.jiuqi.nr.dafafill.model.enums.SelectType;
import java.io.Serializable;
import java.util.List;

public class ConditionField
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fullCode;
    private SelectType selectType;
    private DefaultValueType defaultValueType;
    private List<String> defaultValues;
    private String defaultBinding;
    private DefaultValueType defaultMaxValueType;
    private String defaultMaxValue;
    private boolean quickCondition = true;

    public String getFullCode() {
        return this.fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public SelectType getSelectType() {
        return this.selectType;
    }

    public void setSelectType(SelectType selectType) {
        this.selectType = selectType;
    }

    public DefaultValueType getDefaultValueType() {
        return this.defaultValueType;
    }

    public void setDefaultValueType(DefaultValueType defaultValueType) {
        this.defaultValueType = defaultValueType;
    }

    public List<String> getDefaultValues() {
        return this.defaultValues;
    }

    public void setDefaultValues(List<String> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public String getDefaultBinding() {
        return this.defaultBinding;
    }

    public void setDefaultBinding(String defaultBinding) {
        this.defaultBinding = defaultBinding;
    }

    public DefaultValueType getDefaultMaxValueType() {
        return this.defaultMaxValueType;
    }

    public void setDefaultMaxValueType(DefaultValueType defaultMaxValueType) {
        this.defaultMaxValueType = defaultMaxValueType;
    }

    public String getDefaultMaxValue() {
        return this.defaultMaxValue;
    }

    public void setDefaultMaxValue(String defaultMaxValue) {
        this.defaultMaxValue = defaultMaxValue;
    }

    public boolean isQuickCondition() {
        return this.quickCondition;
    }

    public void setQuickCondition(boolean quickCondition) {
        this.quickCondition = quickCondition;
    }
}

