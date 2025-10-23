/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.dto;

public class ConfigDTO {
    private String key;
    private String code;
    private String title;
    private String level;
    private Object data;

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

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

