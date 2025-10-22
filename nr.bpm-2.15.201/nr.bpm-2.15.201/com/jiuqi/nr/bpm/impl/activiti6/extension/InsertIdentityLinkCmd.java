/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.context.Context
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.interceptor.CommandContext
 *  org.activiti.engine.impl.persistence.entity.TaskEntity
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import java.util.Set;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

public class InsertIdentityLinkCmd
implements Command<Integer> {
    private String deploymentId;
    private String taskId;
    private Set<String> candidateUserIds;

    public InsertIdentityLinkCmd(String deploymentId, String taskId, Set<String> candidateUserIds) {
        this.deploymentId = deploymentId;
        this.taskId = taskId;
        this.candidateUserIds = candidateUserIds;
    }

    public InsertIdentityLinkCmd() {
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setCandidateUserIds(Set<String> candidateUserIds) {
        this.candidateUserIds = candidateUserIds;
    }

    public Integer execute(CommandContext commandContext) {
        TaskEntity taskEntity = (TaskEntity)Context.getCommandContext().getTaskEntityManager().findById(this.taskId);
        for (String candidateUser : this.candidateUserIds) {
            Context.getCommandContext().getIdentityLinkEntityManager().addIdentityLink(taskEntity, candidateUser, null, "candidate");
        }
        return null;
    }
}

