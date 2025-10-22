/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.math.BigDecimal;

public class CheckErrorItem {
    private String unitKey;
    private String unitTitle;
    private String periodCode;
    private String tableKey;
    private String fieldKey;
    private String fieldCode;
    private String fieldTitle;
    private BigDecimal parentValue;
    private BigDecimal childValue;
    private BigDecimal minusValue;
    private String bizOrder;
    private String regionKey;
    private DimensionValueSet rowKeys;
    private DimensionValueSet dimension;

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public BigDecimal getParentValue() {
        return this.parentValue;
    }

    public void setParentValue(BigDecimal parentValue) {
        this.parentValue = parentValue;
    }

    public BigDecimal getChildValue() {
        return this.childValue;
    }

    public void setChildValue(BigDecimal childValue) {
        this.childValue = childValue;
    }

    public BigDecimal getMinusValue() {
        return this.minusValue;
    }

    public void setMinusValue(BigDecimal minusValue) {
        this.minusValue = minusValue;
    }

    public String getBizOrder() {
        return this.bizOrder;
    }

    public void setBizOrder(String bizOrder) {
        this.bizOrder = bizOrder;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public DimensionValueSet getDimension() {
        return this.dimension;
    }

    public void setDimension(DimensionValueSet dimension) {
        this.dimension = dimension;
    }

    public DimensionValueSet getRowKeys() {
        return this.rowKeys;
    }

    public void setRowKeys(DimensionValueSet rowKeys) {
        this.rowKeys = rowKeys;
    }
}

