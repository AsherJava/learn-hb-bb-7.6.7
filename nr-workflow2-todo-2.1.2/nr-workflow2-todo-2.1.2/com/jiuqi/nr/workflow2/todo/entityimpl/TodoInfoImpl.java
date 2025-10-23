/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

import com.jiuqi.nr.workflow2.todo.entity.TodoInfo;
import java.util.List;

public class TodoInfoImpl
implements TodoInfo {
    private String taskId;
    private String businessKey;
    private String businessTitle;
    private String workflowNodeTask;
    private List<String> participants;
    private String workflowNode;
    private String workflowInstance;
    private String formSchemeKey;
    private String period;
    private String remark;

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public String getBusinessTitle() {
        return this.businessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    @Override
    public String getWorkflowNodeTask() {
        return this.workflowNodeTask;
    }

    public void setWorkflowNodeTask(String workflowNodeTask) {
        this.workflowNodeTask = workflowNodeTask;
    }

    @Override
    public List<String> getParticipants() {
        return this.participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public String getWorkflowNode() {
        return this.workflowNode;
    }

    public void setWorkflowNode(String workflowNode) {
        this.workflowNode = workflowNode;
    }

    @Override
    public String getWorkflowInstance() {
        return this.workflowInstance;
    }

    public void setWorkflowInstance(String workflowInstance) {
        this.workflowInstance = workflowInstance;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

