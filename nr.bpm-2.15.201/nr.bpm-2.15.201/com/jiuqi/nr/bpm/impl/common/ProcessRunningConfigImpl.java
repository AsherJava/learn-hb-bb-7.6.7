/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.nr.bpm.common.ProcessRunningConfig;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class ProcessRunningConfigImpl
implements ProcessRunningConfig {
    private String id;
    private String name;
    private String processDefinitionId;
    private UUID taskId;
    private List<String> periods;
    private Map<UUID, List<UUID>> entities;
    private List<UUID> forms;
    private UUID startUserId;

    ProcessRunningConfigImpl() {
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public UUID getTaskId() {
        return this.taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    @Override
    public List<String> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }

    @Override
    public Map<UUID, List<UUID>> getEntities() {
        return this.entities;
    }

    public void setEntities(Map<UUID, List<UUID>> entities) {
        this.entities = entities;
    }

    @Override
    public List<UUID> getForms() {
        return this.forms;
    }

    public void setForms(List<UUID> forms) {
        this.forms = forms;
    }

    @Override
    public UUID getStartUserId() {
        return this.startUserId;
    }

    public void setStartIdentityId(UUID startIdentityId) {
        this.startUserId = startIdentityId;
    }
}

