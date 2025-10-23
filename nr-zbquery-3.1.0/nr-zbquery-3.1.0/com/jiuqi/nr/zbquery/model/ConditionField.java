/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.zbquery.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.zbquery.model.ConditionType;
import com.jiuqi.nr.zbquery.model.DefaultValueMode;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.serialize.ConditionFieldConverter;

@JsonDeserialize(converter=ConditionFieldConverter.class)
public class ConditionField {
    private String fullName;
    private QueryObjectType objectType;
    private ConditionType conditionType;
    private DefaultValueMode defaultValueMode = DefaultValueMode.NONE;
    private String[] defaultValues;
    private int defaultPreviousN = 2;
    private String defaultBinding;
    private DefaultValueMode defaultMaxValueMode;
    private String defaultMaxValue;
    private int defaultMaxPreviousN = 2;
    private String minValue;
    private String maxValue;
    private boolean quickCondition;
    private boolean onlyLeafSelectable;
    private boolean visible = true;
    private String calibreName;
    private String calibreTitle;
    private String candidateValueFilter;
    private String properties;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public QueryObjectType getObjectType() {
        return this.objectType;
    }

    public void setObjectType(QueryObjectType objectType) {
        this.objectType = objectType;
    }

    public ConditionType getConditionType() {
        return this.conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public DefaultValueMode getDefaultValueMode() {
        return this.defaultValueMode;
    }

    public void setDefaultValueMode(DefaultValueMode defaultValueMode) {
        this.defaultValueMode = defaultValueMode;
    }

    public String[] getDefaultValues() {
        return this.defaultValues;
    }

    public void setDefaultValues(String[] defaultValues) {
        this.defaultValues = defaultValues;
    }

    public String getDefaultBinding() {
        return this.defaultBinding;
    }

    public void setDefaultBinding(String defaultBinding) {
        this.defaultBinding = defaultBinding;
    }

    public DefaultValueMode getDefaultMaxValueMode() {
        return this.defaultMaxValueMode;
    }

    public void setDefaultMaxValueMode(DefaultValueMode defaultMaxValueMode) {
        this.defaultMaxValueMode = defaultMaxValueMode;
    }

    public String getDefaultMaxValue() {
        return this.defaultMaxValue;
    }

    public void setDefaultMaxValue(String defaultMaxValue) {
        this.defaultMaxValue = defaultMaxValue;
    }

    public String getMinValue() {
        return this.minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isQuickCondition() {
        return this.quickCondition;
    }

    public void setQuickCondition(boolean quickCondition) {
        this.quickCondition = quickCondition;
    }

    public boolean isOnlyLeafSelectable() {
        return this.onlyLeafSelectable;
    }

    public void setOnlyLeafSelectable(boolean onlyLeafSelectable) {
        this.onlyLeafSelectable = onlyLeafSelectable;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getCalibreName() {
        return this.calibreName;
    }

    public void setCalibreName(String calibreName) {
        this.calibreName = calibreName;
    }

    public String getCalibreTitle() {
        return this.calibreTitle;
    }

    public void setCalibreTitle(String calibreTitle) {
        this.calibreTitle = calibreTitle;
    }

    public boolean isCalibreCondition() {
        return StringUtils.isNotEmpty((String)this.calibreName);
    }

    public String getProperties() {
        return this.properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public int getDefaultPreviousN() {
        return this.defaultPreviousN;
    }

    public void setDefaultPreviousN(int defaultPreviousN) {
        this.defaultPreviousN = defaultPreviousN;
    }

    public int getDefaultMaxPreviousN() {
        return this.defaultMaxPreviousN;
    }

    public void setDefaultMaxPreviousN(int defaultMaxPreviousN) {
        this.defaultMaxPreviousN = defaultMaxPreviousN;
    }

    public String getCandidateValueFilter() {
        return this.candidateValueFilter;
    }

    public void setCandidateValueFilter(String candidateValueFilter) {
        this.candidateValueFilter = candidateValueFilter;
    }

    public String toString() {
        return "{fullName=" + this.fullName + ", objectType=" + (Object)((Object)this.objectType) + "}";
    }
}

