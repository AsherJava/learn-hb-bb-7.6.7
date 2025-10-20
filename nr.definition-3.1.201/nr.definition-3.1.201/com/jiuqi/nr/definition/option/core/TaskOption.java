/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.definition.option.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TaskOption
implements Cloneable {
    private String key;
    private String taskKey;
    private String value;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TaskOption() {
    }

    public TaskOption(String taskKey, String key, String value) {
        this.key = key;
        this.taskKey = taskKey;
        this.value = value;
    }

    public TaskOption clone() {
        TaskOption clone;
        try {
            clone = (TaskOption)super.clone();
        }
        catch (CloneNotSupportedException e) {
            clone = new TaskOption(this.taskKey, this.key, this.value);
        }
        return clone;
    }
}

