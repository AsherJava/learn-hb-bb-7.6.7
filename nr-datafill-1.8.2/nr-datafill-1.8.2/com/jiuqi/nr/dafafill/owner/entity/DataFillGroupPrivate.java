/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.owner.entity;

import java.sql.Timestamp;

public class DataFillGroupPrivate {
    private String key;
    private String title;
    private String parentId;
    private Timestamp modifyTime;
    private String createUser;

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

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Timestamp getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}

