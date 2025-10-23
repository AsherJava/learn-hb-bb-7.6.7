/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

public class taskOrderMoveDTO {
    private String sourceKey;
    private String targetKey;
    private String taskGroupKey;
    private Integer way;

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getTargetKey() {
        return this.targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public String getTaskGroupKey() {
        return this.taskGroupKey;
    }

    public void setTaskGroupKey(String taskGroupKey) {
        this.taskGroupKey = taskGroupKey;
    }

    public Integer getWay() {
        return this.way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }
}

