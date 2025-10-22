/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.transfer;

import java.io.Serializable;
import java.util.Date;

public class FilterTemplateDTO
implements Serializable {
    private String key;
    private String title;
    private String owner;
    private String entityId;
    private boolean shared;
    private String templateJSON;
    private Date createTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public boolean isShared() {
        return this.shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getTemplateJSON() {
        return this.templateJSON;
    }

    public void setTemplateJSON(String templateJSON) {
        this.templateJSON = templateJSON;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

