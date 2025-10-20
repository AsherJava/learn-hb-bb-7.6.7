/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.datatrace.vo;

import com.jiuqi.gcreport.datatrace.vo.OffsetExpressionExtendInfoVO;

public class OffsetAmtTraceItemVO {
    private String expression;
    private String description;
    private String expressionType;
    private Object value;
    private boolean offsetAmtTraceItemShow = true;
    private boolean expressionExtendInfoShow = false;
    private OffsetExpressionExtendInfoVO offsetExpressionExtendInfo;

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpressionType() {
        return this.expressionType;
    }

    public void setExpressionType(String expressionType) {
        this.expressionType = expressionType;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isOffsetAmtTraceItemShow() {
        return this.offsetAmtTraceItemShow;
    }

    public void setOffsetAmtTraceItemShow(boolean offsetAmtTraceItemShow) {
        this.offsetAmtTraceItemShow = offsetAmtTraceItemShow;
    }

    public OffsetExpressionExtendInfoVO getOffsetExpressionExtendInfo() {
        return this.offsetExpressionExtendInfo;
    }

    public void setOffsetExpressionExtendInfo(OffsetExpressionExtendInfoVO offsetExpressionExtendInfo) {
        this.offsetExpressionExtendInfo = offsetExpressionExtendInfo;
    }

    public boolean isExpressionExtendInfoShow() {
        return this.expressionExtendInfoShow;
    }

    public void setExpressionExtendInfoShow(boolean expressionExtendInfoShow) {
        this.expressionExtendInfoShow = expressionExtendInfoShow;
    }

    public String toString() {
        return "OffsetAmtTraceItemVO{expression='" + this.expression + '\'' + ", description='" + this.description + '\'' + ", expressionType='" + this.expressionType + '\'' + ", value=" + this.value + ", offsetAmtTraceItemShow=" + this.offsetAmtTraceItemShow + ", expressionExtendInfoShow=" + this.expressionExtendInfoShow + ", offsetExpressionExtendInfo=" + this.offsetExpressionExtendInfo + '}';
    }
}

