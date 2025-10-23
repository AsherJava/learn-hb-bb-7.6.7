/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl
 */
package com.jiuqi.nr.param.transfer.definition.dto.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TaskOrgLinkDTO {
    private String key;
    private String task;
    private String entity;
    private String entityAlias;
    private String order;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityAlias() {
        return this.entityAlias;
    }

    public void setEntityAlias(String entityAlias) {
        this.entityAlias = entityAlias;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public static TaskOrgLinkDTO valueOf(TaskOrgLinkDefine taskOrgLinkDefine) {
        TaskOrgLinkDTO taskOrgLinkDTO = new TaskOrgLinkDTO();
        taskOrgLinkDTO.setKey(taskOrgLinkDefine.getKey());
        taskOrgLinkDTO.setTask(taskOrgLinkDefine.getTask());
        taskOrgLinkDTO.setEntity(taskOrgLinkDefine.getEntity());
        taskOrgLinkDTO.setEntityAlias(taskOrgLinkDefine.getEntityAlias());
        taskOrgLinkDTO.setOrder(taskOrgLinkDefine.getOrder());
        return taskOrgLinkDTO;
    }

    public void valueToDefine(TaskOrgLinkDefineImpl taskOrgLinkDefineImpl) {
        taskOrgLinkDefineImpl.setKey(this.key);
        taskOrgLinkDefineImpl.setTask(this.task);
        taskOrgLinkDefineImpl.setEntity(this.entity);
        taskOrgLinkDefineImpl.setEntityAlias(this.entityAlias);
        taskOrgLinkDefineImpl.setOrder(this.order);
    }
}

