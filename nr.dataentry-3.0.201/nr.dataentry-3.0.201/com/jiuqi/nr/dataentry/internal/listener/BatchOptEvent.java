/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.internal.listener;

import java.util.List;

public class BatchOptEvent {
    private String taskKey;
    private String fromSchemeKey;
    private String period;
    private List<String> units;
    private String operator;
    private List<String> formKeys;
    private List<String> fromGroupKeys;
    private String content;
    private String actionCode;
    private String adjustPeriod;

    public String getFromSchemeKey() {
        return this.fromSchemeKey;
    }

    public void setFromSchemeKey(String fromSchemeKey) {
        this.fromSchemeKey = fromSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getUnits() {
        return this.units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getFromGroupKeys() {
        return this.fromGroupKeys;
    }

    public void setFromGroupKeys(List<String> fromGroupKeys) {
        this.fromGroupKeys = fromGroupKeys;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdjustPeriod() {
        return this.adjustPeriod;
    }

    public void setAdjustPeriod(String adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }
}

