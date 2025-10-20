/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.treebean;

public class TaskOrderObject {
    private String preTaskKey;
    private String preTaskOrder;
    private String sufTaskKey;
    private String sufTaskOrder;
    private String taskGroupKey;

    public String getTaskGroupKey() {
        return this.taskGroupKey;
    }

    public void setTaskGroupKey(String taskGroupKey) {
        this.taskGroupKey = taskGroupKey;
    }

    public String getPreTaskKey() {
        return this.preTaskKey;
    }

    public void setPreTaskKey(String preTaskKey) {
        this.preTaskKey = preTaskKey;
    }

    public String getPreTaskOrder() {
        return this.preTaskOrder;
    }

    public void setPreTaskOrder(String preTaskOrder) {
        this.preTaskOrder = preTaskOrder;
    }

    public String getSufTaskKey() {
        return this.sufTaskKey;
    }

    public void setSufTaskKey(String sufTaskKey) {
        this.sufTaskKey = sufTaskKey;
    }

    public String getSufTaskOrder() {
        return this.sufTaskOrder;
    }

    public void setSufTaskOrder(String sufTaskOrder) {
        this.sufTaskOrder = sufTaskOrder;
    }
}

