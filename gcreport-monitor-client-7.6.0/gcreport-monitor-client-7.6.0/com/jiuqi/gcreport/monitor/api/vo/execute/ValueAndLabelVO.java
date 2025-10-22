/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.execute;

import java.util.List;

public class ValueAndLabelVO {
    private String taskId;
    private String value;
    private String label;
    private String periodChar;
    private String orgType;
    private List<ValueAndLabelVO> children;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ValueAndLabelVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<ValueAndLabelVO> children) {
        this.children = children;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getPeriodChar() {
        return this.periodChar;
    }

    public void setPeriodChar(String periodChar) {
        this.periodChar = periodChar;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

