/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.entity;

import com.jiuqi.nr.dafafill.model.enums.ModelType;
import java.sql.Timestamp;

public class DataFillDefinition {
    private String id;
    private String title;
    private String code;
    private ModelType sourceType;
    private String parentId;
    private String taskKey;
    private String description;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ModelType getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(ModelType sourceType) {
        this.sourceType = sourceType;
    }

    public void setSourceType1(String sourceType) {
        if (sourceType.equals(ModelType.TASK.title())) {
            this.sourceType = ModelType.TASK;
        }
        if (sourceType.equals(ModelType.SCHEME.title())) {
            this.sourceType = ModelType.SCHEME;
        }
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }
}

