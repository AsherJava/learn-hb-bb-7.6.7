/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

import com.jiuqi.nr.workflow2.todo.entity.TodoFilterCondition;
import com.jiuqi.nr.workflow2.todo.entityimpl.EntityCaliberItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.ReportDimensionItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.UploadState;
import com.jiuqi.nr.workflow2.todo.entityimpl.WorkflowButton;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoType;
import java.util.List;

public class TodoFilterConditionImpl
implements TodoFilterCondition {
    private List<PeriodItem> periodParam;
    private List<WorkflowButton> WorkflowButtons;
    private List<UploadState> uploadStates;
    private List<TodoType> todoType;
    private List<ReportDimensionItem> reportDimensionParam;
    private List<EntityCaliberItem> entityCaliberParam;

    @Override
    public List<PeriodItem> getPeriodParam() {
        return this.periodParam;
    }

    public void setPeriodParam(List<PeriodItem> periodParam) {
        this.periodParam = periodParam;
    }

    @Override
    public List<WorkflowButton> getWorkflowButtons() {
        return this.WorkflowButtons;
    }

    public void setWorkflowButtons(List<WorkflowButton> WorkflowButtons) {
        this.WorkflowButtons = WorkflowButtons;
    }

    @Override
    public List<UploadState> getUploadStates() {
        return this.uploadStates;
    }

    public void setUploadStates(List<UploadState> uploadStates) {
        this.uploadStates = uploadStates;
    }

    @Override
    public List<TodoType> getTodoType() {
        return this.todoType;
    }

    public void setTodoType(List<TodoType> todoType) {
        this.todoType = todoType;
    }

    @Override
    public List<ReportDimensionItem> getReportDimensionParam() {
        return this.reportDimensionParam;
    }

    public void setReportDimensionParam(List<ReportDimensionItem> reportDimensionParam) {
        this.reportDimensionParam = reportDimensionParam;
    }

    @Override
    public List<EntityCaliberItem> getEntityCaliberParam() {
        return this.entityCaliberParam;
    }

    public void setEntityCaliberParam(List<EntityCaliberItem> entityCaliberParam) {
        this.entityCaliberParam = entityCaliberParam;
    }
}

