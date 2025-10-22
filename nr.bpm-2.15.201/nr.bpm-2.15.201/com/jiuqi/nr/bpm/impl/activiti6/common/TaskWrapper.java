/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.TaskService
 *  org.activiti.engine.task.IdentityLink
 *  org.activiti.engine.task.Task
 */
package com.jiuqi.nr.bpm.impl.activiti6.common;

import com.jiuqi.nr.bpm.common.Task;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.springframework.util.Assert;

class TaskWrapper
implements Task {
    private final org.activiti.engine.task.Task innerTask;
    private TaskService activitiTaskService;

    public TaskWrapper(org.activiti.engine.task.Task innerTask) {
        Assert.notNull((Object)innerTask, "'innerTask' must not be null.");
        this.innerTask = innerTask;
    }

    public TaskWrapper(org.activiti.engine.task.Task innerTask, TaskService activitiTaskService) {
        Assert.notNull((Object)innerTask, "'innerTask' must not be null.");
        this.innerTask = innerTask;
        this.activitiTaskService = activitiTaskService;
    }

    @Override
    public String getId() {
        return this.innerTask.getId();
    }

    @Override
    public String getName() {
        return this.innerTask.getName();
    }

    @Override
    public String getProcessInstanceId() {
        return this.innerTask.getProcessInstanceId();
    }

    @Override
    public String getProcessDefinitionId() {
        return this.innerTask.getProcessDefinitionId();
    }

    @Override
    public String getUserTaskId() {
        return this.innerTask.getTaskDefinitionKey();
    }

    @Override
    public Date getStartTime() {
        return this.innerTask.getCreateTime();
    }

    @Override
    public Date getEndTime() {
        return null;
    }

    @Override
    public boolean isSuspended() {
        return this.innerTask.isSuspended();
    }

    @Override
    public String getAssignee() {
        return this.innerTask.getAssignee();
    }

    @Override
    public Set<IdentityLink> getIdentityLink() {
        HashSet<IdentityLink> identityLinks = new HashSet<IdentityLink>();
        if (this.activitiTaskService != null) {
            identityLinks.addAll(this.activitiTaskService.getIdentityLinksForTask(this.innerTask.getId()));
            return identityLinks;
        }
        return null;
    }

    @Override
    public String getExecutionId() {
        return this.innerTask.getExecutionId();
    }
}

