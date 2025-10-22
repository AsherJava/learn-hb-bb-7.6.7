/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.owner.entity;

import com.jiuqi.nr.dafafill.model.enums.ModelType;
import java.sql.Timestamp;

public class DataFillDefinitionPrivate {
    private String key;
    private String title;
    private String groupId;
    private ModelType sourceType;
    private String task;
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

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ModelType getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(ModelType sourceType) {
        this.sourceType = sourceType;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
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

