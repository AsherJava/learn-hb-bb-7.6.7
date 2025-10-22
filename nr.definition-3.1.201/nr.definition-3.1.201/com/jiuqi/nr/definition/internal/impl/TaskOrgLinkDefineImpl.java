/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_TASK_ORG_LINK")
public class TaskOrgLinkDefineImpl
implements TaskOrgLinkDefine {
    @DBAnno.DBField(dbField="TO_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="TO_TASK")
    private String task;
    @DBAnno.DBField(dbField="TO_ENTITY")
    private String entity;
    @DBAnno.DBField(dbField="TO_ENTITY_ALIAS")
    private String entityAlias;
    @DBAnno.DBField(dbField="TO_ORDER")
    private String order;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return null;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String getTask() {
        return this.task;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String getEntity() {
        return this.entity;
    }

    public void setEntityAlias(String entityAlias) {
        this.entityAlias = entityAlias;
    }

    @Override
    public String getEntityAlias() {
        return this.entityAlias;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public Date getUpdateTime() {
        return null;
    }
}

