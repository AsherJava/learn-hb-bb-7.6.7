/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.migration.syncscheme.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckParam {
    private boolean isUpdate;
    private String key;
    private String code;
    private String title;
    private String group;

    public boolean isUpdate() {
        return this.isUpdate;
    }

    @JsonProperty(value="isUpdate")
    public void setUpdate(boolean update) {
        this.isUpdate = update;
    }

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

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

