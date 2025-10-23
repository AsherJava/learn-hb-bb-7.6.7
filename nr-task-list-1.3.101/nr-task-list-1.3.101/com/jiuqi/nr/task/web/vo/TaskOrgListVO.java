/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;

public class TaskOrgListVO {
    private String key;
    private String taskKey;
    private String orgName;
    private String entityId;
    private String entityAlias;
    private String order;

    public TaskOrgListVO() {
    }

    public TaskOrgListVO(TaskOrgLinkDefine define, IEntityMetaService entityMetaService) {
        IEntityDefine entityDefine = entityMetaService.queryEntity(define.getEntity());
        if (entityDefine != null) {
            this.key = define.getKey();
            this.taskKey = define.getTask();
            this.orgName = entityDefine.getTitle();
            this.entityId = define.getEntity();
            this.entityAlias = define.getEntityAlias();
            this.order = define.getOrder();
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
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

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}

