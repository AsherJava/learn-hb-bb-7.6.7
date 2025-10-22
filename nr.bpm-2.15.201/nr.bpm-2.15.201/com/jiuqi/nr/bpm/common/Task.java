/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.task.IdentityLink
 */
package com.jiuqi.nr.bpm.common;

import java.util.Date;
import java.util.Set;
import org.activiti.engine.task.IdentityLink;

public interface Task {
    public String getId();

    public String getName();

    public String getProcessInstanceId();

    public String getProcessDefinitionId();

    public String getUserTaskId();

    public Date getStartTime();

    public Date getEndTime();

    public boolean isSuspended();

    public String getAssignee();

    public Set<IdentityLink> getIdentityLink();

    public String getExecutionId();
}

