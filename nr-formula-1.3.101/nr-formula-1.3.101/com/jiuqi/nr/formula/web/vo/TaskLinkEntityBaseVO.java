/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

public class TaskLinkEntityBaseVO {
    private String entityId;
    private String entityCode;
    private String entityTitle;

    public TaskLinkEntityBaseVO() {
    }

    public TaskLinkEntityBaseVO(String entityId, String entityName, String entityCode) {
        this.entityId = entityId;
        this.entityTitle = entityName;
        this.entityCode = entityCode;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }
}

