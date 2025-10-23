/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.dto;

import com.jiuqi.nr.workflow2.todo.envimpl.PageInfo;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import java.util.List;

public class TodoQueryDTO {
    private String formSchemeKey;
    private String taskId;
    private List<String> period;
    private String workflowNode;
    private List<String> rangeUnits;
    private List<String> rangeForms;
    private List<String> uploadState;
    private String todoType;
    private WorkFlowStateTableModel stateTableModel;
    private List<String> workflowInstanceRange;
    private PageInfo pageInfo;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getPeriod() {
        return this.period;
    }

    public void setPeriod(List<String> period) {
        this.period = period;
    }

    public String getWorkflowNode() {
        return this.workflowNode;
    }

    public void setWorkflowNode(String workflowNode) {
        this.workflowNode = workflowNode;
    }

    public List<String> getRangeUnits() {
        return this.rangeUnits;
    }

    public void setRangeUnits(List<String> rangeUnits) {
        this.rangeUnits = rangeUnits;
    }

    public List<String> getRangeForms() {
        return this.rangeForms;
    }

    public void setRangeForms(List<String> rangeForms) {
        this.rangeForms = rangeForms;
    }

    public List<String> getUploadState() {
        return this.uploadState;
    }

    public void setUploadState(List<String> uploadState) {
        this.uploadState = uploadState;
    }

    public String getTodoType() {
        return this.todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public WorkFlowStateTableModel getStateTableModel() {
        return this.stateTableModel;
    }

    public void setStateTableModel(WorkFlowStateTableModel stateTableModel) {
        this.stateTableModel = stateTableModel;
    }

    public List<String> getWorkflowInstanceRange() {
        return this.workflowInstanceRange;
    }

    public void setWorkflowInstanceRange(List<String> workflowInstanceRange) {
        this.workflowInstanceRange = workflowInstanceRange;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}

