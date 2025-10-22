/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.dafafill.parameter;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.dafafill.model.enums.DefaultValueType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.SelectType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.util.List;

public class ParameterInfoVO {
    private FieldType fieldType;
    private String displayTitle;
    private String entityId;
    private DataFieldType dataType;
    private String fullCode;
    private SelectType selectType;
    private DefaultValueType defaultValueType;
    private List<String> defaultValues;
    private DefaultValueType defaultMaxValueType;
    private String defaultMaxValue;
    private String bizKey;
    private PeriodType periodType;

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getFullCode() {
        return this.fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getDisplayTitle() {
        return this.displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public String getBizKey() {
        return this.bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public DataFieldType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataFieldType dataType) {
        this.dataType = dataType;
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

    public DefaultValueType getDefaultMaxValueType() {
        return this.defaultMaxValueType;
    }

    public void setDefaultMaxValue(String defaultMaxValue) {
        this.defaultMaxValue = defaultMaxValue;
    }

    public String getDefaultMaxValue() {
        return this.defaultMaxValue;
    }

    public void setDefaultMaxValueType(DefaultValueType defaultMaxValueType) {
        this.defaultMaxValueType = defaultMaxValueType;
    }
}

