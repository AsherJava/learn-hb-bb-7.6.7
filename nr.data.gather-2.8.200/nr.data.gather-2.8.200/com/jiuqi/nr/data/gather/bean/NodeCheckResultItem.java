/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.fasterxml.jackson.databind.ser.std.ToStringSerializer
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.gather.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.gather.bean.NodeCheckFieldMessage;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class NodeCheckResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fieldTitle;
    private String fieldCode;
    @JsonSerialize(using=ToStringSerializer.class)
    private BigDecimal parentValue;
    @JsonSerialize(using=ToStringSerializer.class)
    private BigDecimal childValue;
    @JsonSerialize(using=ToStringSerializer.class)
    private BigDecimal minusValue;
    private NodeCheckFieldMessage nodeCheckFieldMessage;
    private String unitTitle;
    private String unitKey;
    private int dimensionIndex;
    private Map<String, String> dimensionTitle = new HashMap<String, String>();
    private Map<String, DimensionValue> rowKeys = new HashMap<String, DimensionValue>();
    private String bizKeyOrder;

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
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

    public NodeCheckFieldMessage getNodeCheckFieldMessage() {
        return this.nodeCheckFieldMessage;
    }

    public void setNodeCheckFieldMessage(NodeCheckFieldMessage nodeCheckFieldMessage) {
        this.nodeCheckFieldMessage = nodeCheckFieldMessage;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public Map<String, String> getDimensionTitle() {
        return this.dimensionTitle;
    }

    public void setDimensionTitle(Map<String, String> dimensionTitle) {
        this.dimensionTitle = dimensionTitle;
    }

    public int getDimensionIndex() {
        return this.dimensionIndex;
    }

    public void setDimensionIndex(int dimensionIndex) {
        this.dimensionIndex = dimensionIndex;
    }

    public Map<String, DimensionValue> getRowKeys() {
        return this.rowKeys;
    }

    public void setRowKeys(Map<String, DimensionValue> rowKeys) {
        this.rowKeys = rowKeys;
    }

    public String getBizKeyOrder() {
        return this.bizKeyOrder;
    }

    public void setBizKeyOrder(String bizKeyOrder) {
        this.bizKeyOrder = bizKeyOrder;
    }
}

