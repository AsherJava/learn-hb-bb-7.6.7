/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.listener.param;

public class FileUpdateEvent {
    private String dataSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    private String fileKey;

    public FileUpdateEvent() {
    }

    public FileUpdateEvent(String dataSchemeKey, String taskKey, String formSchemeKey, String fileKey) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.fileKey = fileKey;
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

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}

