/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.cmd.NeedsActiveTaskCmd
 *  org.activiti.engine.impl.interceptor.CommandContext
 *  org.activiti.engine.impl.persistence.entity.ExecutionEntity
 *  org.activiti.engine.impl.persistence.entity.TaskEntity
 *  org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;

public class DeleteTaskCmd
extends NeedsActiveTaskCmd<String> {
    private static final long serialVersionUID = 424659763004253659L;

    public DeleteTaskCmd(String taskId) {
        super(taskId);
    }

    public String getSuspendedTaskException() {
        return "can not delete suspended task.";
    }

    protected String execute(CommandContext commandContext, TaskEntity task) {
        TaskEntityManagerImpl taskEntityManager = (TaskEntityManagerImpl)commandContext.getTaskEntityManager();
        ExecutionEntity executionEntity = task.getExecution();
        taskEntityManager.deleteTask(task, "jump", false, false);
        return executionEntity.getId();
    }
}

