/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

public class TaskDimensionVO {
    private String entityID;
    private String entityName;
    private Boolean allUnit;
    private boolean isFilterExpression;
    private String filterMessage;

    public TaskDimensionVO() {
    }

    public TaskDimensionVO(boolean isFilterExpression, String entityID, String entityName, String filterExpression) {
        this.isFilterExpression = isFilterExpression;
        this.entityID = entityID;
        this.entityName = entityName;
        this.filterMessage = filterExpression;
    }

    public TaskDimensionVO(String entityID, String entityName, Boolean allUnit, String filterExpression) {
        this.entityID = entityID;
        this.entityName = entityName;
        this.filterMessage = filterExpression;
        this.allUnit = allUnit;
    }

    public String getFilterMessage() {
        return this.filterMessage;
    }

    public void setFilterMessage(String filterMessage) {
        this.filterMessage = filterMessage;
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Boolean getAllUnit() {
        return this.allUnit;
    }

    public void setAllUnit(Boolean allUnit) {
        this.allUnit = allUnit;
    }

    public boolean isFilterExpression() {
        return this.isFilterExpression;
    }

    public void setFilterExpression(boolean filterExpression) {
        this.isFilterExpression = filterExpression;
    }
}

