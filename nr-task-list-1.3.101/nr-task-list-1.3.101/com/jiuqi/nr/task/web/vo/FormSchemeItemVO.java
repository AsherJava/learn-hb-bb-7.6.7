/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;

public class FormSchemeItemVO {
    private String key;
    private String code;
    private String title;
    private String task;
    private FormSchemeStatusDTO status;
    private String updateTime;
    private String updateUser;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public FormSchemeStatusDTO getStatus() {
        return this.status;
    }

    public void setStatus(FormSchemeStatusDTO status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}

