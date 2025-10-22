/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param.bean;

import com.jiuqi.nr.data.excel.param.bean.NodeCheckFieldMessage;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class NodeCheckResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fieldTitle;
    private String fieldCode;
    private BigDecimal parentValue;
    private BigDecimal childValue;
    private BigDecimal minusValue;
    private NodeCheckFieldMessage nodeCheckFieldMessage;
    private String unitTitle;
    private String unitKey;
    private int dimensionIndex;
    private Map<String, String> dimensionTitle = new HashMap<String, String>();

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
}

