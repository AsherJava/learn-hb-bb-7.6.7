/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.event.bean;

import com.jiuqi.nr.configuration.common.SystemOptionType;

@Deprecated
public class EventBO {
    private String key;
    private Object value;
    private Object oldValue;
    private String taskKey;
    private String formSchemeKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public SystemOptionType getSystemOptionType() {
        if (this.getTaskKey() != null && this.getFormSchemeKey() != null) {
            return SystemOptionType.FORMSCHEME_OPTION;
        }
        if (this.getTaskKey() != null && this.getFormSchemeKey() == null) {
            return SystemOptionType.TASK_OPTION;
        }
        return SystemOptionType.SYSTEM_OPTION;
    }

    public EventBO() {
    }

    public EventBO(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public EventBO(String key, Object value, Object oldValue) {
        this.key = key;
        this.value = value;
        this.oldValue = oldValue;
    }

    public EventBO(String key, Object value, Object oldValue, String taskKey, String formSchemeKey) {
        this.key = key;
        this.value = value;
        this.oldValue = oldValue;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
    }

    public String toString() {
        return "EventBO{key='" + this.key + '\'' + ", value=" + this.value + ", oldValue=" + this.oldValue + ", taskKey='" + this.taskKey + '\'' + ", formSchemeKey='" + this.formSchemeKey + '\'' + ", systemOptionType=" + (Object)((Object)this.getSystemOptionType()) + '}';
    }
}

