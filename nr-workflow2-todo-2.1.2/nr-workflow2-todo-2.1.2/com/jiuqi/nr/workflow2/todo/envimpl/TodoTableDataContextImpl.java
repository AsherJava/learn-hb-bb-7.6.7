/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.envimpl;

import com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext;
import com.jiuqi.nr.workflow2.todo.envimpl.PageInfo;
import java.util.List;

public class TodoTableDataContextImpl
implements TodoTableDataContext {
    private String taskId;
    private String entityCaliber;
    private String period;
    private String workflowNode;
    private List<String> rangeUnits;
    private List<String> rangeForms;
    private List<String> uploadState;
    private String todoType;
    private PageInfo pageInfo;

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getEntityCaliber() {
        return this.entityCaliber;
    }

    public void setEntityCaliber(String entityCaliber) {
        this.entityCaliber = entityCaliber;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getWorkflowNode() {
        return this.workflowNode;
    }

    public void setWorkflowNode(String workflowNode) {
        this.workflowNode = workflowNode;
    }

    @Override
    public List<String> getRangeUnits() {
        return this.rangeUnits;
    }

    public void setRangeUnits(List<String> rangeUnits) {
        this.rangeUnits = rangeUnits;
    }

    @Override
    public List<String> getRangeForms() {
        return this.rangeForms;
    }

    public void setRangeForms(List<String> rangeForms) {
        this.rangeForms = rangeForms;
    }

    @Override
    public List<String> getUploadState() {
        return this.uploadState;
    }

    public void setUploadState(List<String> uploadState) {
        this.uploadState = uploadState;
    }

    @Override
    public String getTodoType() {
        return this.todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    @Override
    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}

