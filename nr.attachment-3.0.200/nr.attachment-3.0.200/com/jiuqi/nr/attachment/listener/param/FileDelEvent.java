/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.listener.param;

import java.util.List;

public class FileDelEvent {
    private String dataSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    private String groupKey;
    private List<String> fileKeys;

    public FileDelEvent() {
    }

    public FileDelEvent(String dataSchemeKey, String taskKey, String formSchemeKey, String groupKey, List<String> fileKeys) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.groupKey = groupKey;
        this.fileKeys = fileKeys;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
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

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(List<String> fileKeys) {
        this.fileKeys = fileKeys;
    }
}

