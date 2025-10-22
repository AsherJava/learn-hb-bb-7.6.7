/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.task.IdentityLink
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.process.consts.TaskEnum;
import java.util.Date;
import java.util.Set;
import org.activiti.engine.task.IdentityLink;

public class ProcessTask
implements Task {
    private TaskEnum task;
    private BusinessKey businessKey;

    public ProcessTask(TaskEnum task, BusinessKey businessKey) {
        this.task = task;
        this.businessKey = businessKey;
    }

    public ProcessTask(WorkFlowNodeSet workFlowNodeSet, BusinessKey businessKey) {
        this.task = TaskEnum.TASK_DEFAULT;
        this.task.setName(workFlowNodeSet.getTitle());
        this.task.setTaskId(workFlowNodeSet.getId());
        this.businessKey = businessKey;
    }

    @Override
    public String getId() {
        return this.task.getTaskId();
    }

    @Override
    public String getName() {
        return this.task.getName();
    }

    @Override
    public String getProcessInstanceId() {
        return BusinessKeyFormatter.formatToString(this.businessKey);
    }

    @Override
    public String getProcessDefinitionId() {
        return null;
    }

    @Override
    public String getUserTaskId() {
        return this.task.getTaskId();
    }

    @Override
    public Date getStartTime() {
        return new Date();
    }

    @Override
    public Date getEndTime() {
        return null;
    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public String getAssignee() {
        return null;
    }

    @Override
    public Set<IdentityLink> getIdentityLink() {
        return null;
    }

    @Override
    public String getExecutionId() {
        return null;
    }
}

