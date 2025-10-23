/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.zbquery.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.zbquery.model.ConditionOperationItem;
import com.jiuqi.nr.zbquery.model.ConditionStyleType;
import com.jiuqi.nr.zbquery.model.Logic;
import java.util.List;

public class CellConditionStyle {
    private ConditionStyleType conditionType;
    private List<ConditionOperationItem> conditions;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Logic logic;
    private String expression;
    private int foregroundColor = -1;
    private int backgroundColor = -1;
    private Boolean bold;
    private String desc;
    private int order;

    public ConditionStyleType getConditionType() {
        return this.conditionType;
    }

    public void setConditionType(ConditionStyleType conditionType) {
        this.conditionType = conditionType;
    }

    public List<ConditionOperationItem> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<ConditionOperationItem> conditions) {
        this.conditions = conditions;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getForegroundColor() {
        return this.foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Boolean getBold() {
        return this.bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Logic getLogic() {
        return this.logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }
}

