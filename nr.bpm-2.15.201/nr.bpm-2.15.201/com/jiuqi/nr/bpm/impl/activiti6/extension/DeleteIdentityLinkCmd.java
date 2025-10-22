/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.context.Context
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.interceptor.CommandContext
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

public class DeleteIdentityLinkCmd
implements Command<Integer> {
    private String deploymentId;
    private String taskId;

    public DeleteIdentityLinkCmd(String deploymentId, String taskId) {
        this.deploymentId = deploymentId;
        this.taskId = taskId;
    }

    public DeleteIdentityLinkCmd() {
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

    public Integer execute(CommandContext commandContext) {
        Context.getCommandContext().getIdentityLinkEntityManager().deleteIdentityLinksByTaskId(this.getTaskId());
        return null;
    }
}

