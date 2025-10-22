/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.hshd.vo;

public class TaskOrgLinkVO {
    private String entity;
    private String entityTitle;

    public TaskOrgLinkVO(String entity, String entityTitle) {
        this.entity = entity;
        this.entityTitle = entityTitle;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }
}

