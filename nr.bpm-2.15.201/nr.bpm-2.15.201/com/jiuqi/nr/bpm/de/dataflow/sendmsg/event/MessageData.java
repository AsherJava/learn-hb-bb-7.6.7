/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  org.activiti.engine.task.IdentityLink
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.bpm.common.Task;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.activiti.engine.task.IdentityLink;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MessageData
implements Task,
Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String processInstanceId;
    private String processDefinitionId;
    private String userTaskId;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    @Override
    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }

    @Override
    public String getUserTaskId() {
        return this.userTaskId;
    }

    @Override
    public String getExecutionId() {
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public void setUserTaskId(String userTaskId) {
        this.userTaskId = userTaskId;
    }

    @Override
    @JsonIgnore
    public Date getStartTime() {
        return null;
    }

    @Override
    @JsonIgnore
    public Date getEndTime() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isSuspended() {
        return false;
    }

    @Override
    @JsonIgnore
    public String getAssignee() {
        return null;
    }

    @Override
    @JsonIgnore
    public Set<IdentityLink> getIdentityLink() {
        return null;
    }
}

