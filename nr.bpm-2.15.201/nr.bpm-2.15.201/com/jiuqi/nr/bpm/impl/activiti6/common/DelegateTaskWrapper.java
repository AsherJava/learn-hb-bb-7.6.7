/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.delegate.DelegateTask
 *  org.activiti.engine.task.IdentityLink
 */
package com.jiuqi.nr.bpm.impl.activiti6.common;

import com.jiuqi.nr.bpm.common.Task;
import java.util.Date;
import java.util.Set;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.IdentityLink;
import org.springframework.util.Assert;

public class DelegateTaskWrapper
implements Task {
    private final DelegateTask delegateTask;

    public DelegateTaskWrapper(DelegateTask delegateTask) {
        Assert.notNull((Object)delegateTask, "'innerTask' must not be null.");
        this.delegateTask = delegateTask;
    }

    @Override
    public String getId() {
        return this.delegateTask.getId();
    }

    @Override
    public String getName() {
        return this.delegateTask.getName();
    }

    @Override
    public String getProcessInstanceId() {
        return this.delegateTask.getProcessInstanceId();
    }

    @Override
    public String getProcessDefinitionId() {
        return this.delegateTask.getProcessDefinitionId();
    }

    @Override
    public String getUserTaskId() {
        return this.delegateTask.getTaskDefinitionKey();
    }

    @Override
    public Date getStartTime() {
        return this.delegateTask.getCreateTime();
    }

    @Override
    public Date getEndTime() {
        return null;
    }

    @Override
    public boolean isSuspended() {
        return this.delegateTask.isSuspended();
    }

    @Override
    public String getAssignee() {
        return this.delegateTask.getAssignee();
    }

    @Override
    public Set<IdentityLink> getIdentityLink() {
        return this.delegateTask.getCandidates();
    }

    @Override
    public String getExecutionId() {
        return this.delegateTask.getExecutionId();
    }
}

