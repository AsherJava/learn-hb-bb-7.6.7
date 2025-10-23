/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

import com.jiuqi.nr.workflow2.todo.entity.TodoTask;

public class TodoTaskImpl
implements TodoTask {
    private String period;
    private String unit;
    private String workflowNodeTask;
    private String workflowObject;
    private String workflowInstance;
    private String uploadState;
    private String comment;
    private String time;
    private String adjust;
    private String reportDimension;

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String getWorkflowNodeTask() {
        return this.workflowNodeTask;
    }

    public void setWorkflowNodeTask(String workflowNodeTask) {
        this.workflowNodeTask = workflowNodeTask;
    }

    @Override
    public String getWorkflowObject() {
        return this.workflowObject;
    }

    public void setWorkflowObject(String workflowObject) {
        this.workflowObject = workflowObject;
    }

    @Override
    public String getWorkflowInstance() {
        return this.workflowInstance;
    }

    public void setWorkflowInstance(String workflowInstance) {
        this.workflowInstance = workflowInstance;
    }

    @Override
    public String getUploadState() {
        return this.uploadState;
    }

    public void setUploadState(String uploadState) {
        this.uploadState = uploadState;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    @Override
    public String getReportDimension() {
        return this.reportDimension;
    }

    public void setReportDimension(String reportDimension) {
        this.reportDimension = reportDimension;
    }
}

