/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

import com.jiuqi.nr.task.web.vo.TaskDimensionVO;
import java.util.List;

public class TaskDimension {
    private String entityId;
    private Integer filterWay;
    private Integer filterType;
    private String expression;
    private List<String> keys;

    public TaskDimension(TaskDimensionVO taskDimensionVO) {
        this.entityId = taskDimensionVO.getEntityID();
        this.filterWay = taskDimensionVO.isFilterExpression() ? Integer.valueOf(0) : Integer.valueOf(1);
        this.expression = taskDimensionVO.getFilterMessage();
    }

    public TaskDimension(TaskDimensionVO taskDimensionVO, List<String> keys) {
        this.entityId = taskDimensionVO.getEntityID();
        this.filterWay = 1;
        this.filterType = 0;
        this.keys = keys;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Integer getFilterWay() {
        return this.filterWay;
    }

    public void setFilterWay(Integer filterWay) {
        this.filterWay = filterWay;
    }

    public Integer getFilterType() {
        return this.filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }
}

