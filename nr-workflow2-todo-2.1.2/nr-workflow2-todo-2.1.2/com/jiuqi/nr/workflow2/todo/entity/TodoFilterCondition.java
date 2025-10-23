/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entity;

import com.jiuqi.nr.workflow2.todo.entityimpl.EntityCaliberItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.ReportDimensionItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.UploadState;
import com.jiuqi.nr.workflow2.todo.entityimpl.WorkflowButton;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoType;
import java.util.List;

public interface TodoFilterCondition {
    public List<PeriodItem> getPeriodParam();

    public List<WorkflowButton> getWorkflowButtons();

    public List<UploadState> getUploadStates();

    public List<TodoType> getTodoType();

    public List<ReportDimensionItem> getReportDimensionParam();

    public List<EntityCaliberItem> getEntityCaliberParam();
}

