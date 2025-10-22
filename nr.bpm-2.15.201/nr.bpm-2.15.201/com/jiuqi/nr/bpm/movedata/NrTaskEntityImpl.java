/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class NrTaskEntityImpl {
    private String id;
    private BigDecimal rev;
    private String executionId;
    private String procInstId;
    private String procDefId;
    private String name;
    private String parentTaskId;
    private String description;
    private String taskDefKey;
    private String owner;
    private String assignee;
    private String delegation;
    private BigDecimal priority;
    private Timestamp createTime;
    private Timestamp dueDate;
    private String category;
    private BigDecimal suspensionState;
    private String tenantId;
    private String formKey;
    private Timestamp claimTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getRev() {
        return this.rev;
    }

    public void setRev(BigDecimal rev) {
        this.rev = rev;
    }

    public String getExecutionId() {
        return this.executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcInstId() {
        return this.procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefId() {
        return this.procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentTaskId() {
        return this.parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskDefKey() {
        return this.taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return this.assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDelegation() {
        return this.delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public BigDecimal getPriority() {
        return this.priority;
    }

    public void setPriority(BigDecimal priority) {
        this.priority = priority;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getSuspensionState() {
        return this.suspensionState;
    }

    public void setSuspensionState(BigDecimal suspensionState) {
        this.suspensionState = suspensionState;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Timestamp getClaimTime() {
        return this.claimTime;
    }

    public void setClaimTime(Timestamp claimTime) {
        this.claimTime = claimTime;
    }

    public String toString() {
        return "NrTaskEntityImpl{id='" + this.id + '\'' + ", rev=" + this.rev + ", executionId='" + this.executionId + '\'' + ", procInstId='" + this.procInstId + '\'' + ", procDefId='" + this.procDefId + '\'' + ", name='" + this.name + '\'' + ", parentTaskId='" + this.parentTaskId + '\'' + ", description='" + this.description + '\'' + ", taskDefKey='" + this.taskDefKey + '\'' + ", owner='" + this.owner + '\'' + ", assignee='" + this.assignee + '\'' + ", delegation='" + this.delegation + '\'' + ", priority=" + this.priority + ", createTime=" + this.createTime + ", dueDate=" + this.dueDate + ", category='" + this.category + '\'' + ", suspensionState=" + this.suspensionState + ", tenantId='" + this.tenantId + '\'' + ", formKey='" + this.formKey + '\'' + ", claimTime=" + this.claimTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NrTaskEntityImpl that = (NrTaskEntityImpl)o;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}

