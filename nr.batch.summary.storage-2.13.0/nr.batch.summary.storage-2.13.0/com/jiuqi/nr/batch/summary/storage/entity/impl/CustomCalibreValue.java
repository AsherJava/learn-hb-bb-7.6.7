/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.util.StringUtils;
import java.util.List;

public class CustomCalibreValue {
    private String expression;
    private List<String> checkList;
    private ConditionValueType valueType;

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<String> getCheckList() {
        return this.checkList;
    }

    public void setCheckList(List<String> checkList) {
        this.checkList = checkList;
    }

    public ConditionValueType getValueType() {
        return this.valueType;
    }

    public void setValueType(ConditionValueType valueType) {
        this.valueType = valueType;
    }

    public String valueToClob() {
        switch (this.valueType) {
            case UNITS: {
                return BatchSummaryUtils.toJSONStr(this.checkList);
            }
            case EXPRESSION: {
                return this.expression;
            }
        }
        return null;
    }

    public void transformAndSetCheckList(String jsonStr) {
        switch (this.valueType) {
            case UNITS: {
                this.checkList = BatchSummaryUtils.toJavaArray(jsonStr, String.class);
                break;
            }
            case EXPRESSION: {
                this.expression = jsonStr;
            }
        }
    }

    public boolean isValidValue() {
        switch (this.valueType) {
            case UNITS: {
                return this.checkList != null && !this.checkList.isEmpty();
            }
            case EXPRESSION: {
                return StringUtils.isNotEmpty((String)this.expression);
            }
        }
        return false;
    }
}

