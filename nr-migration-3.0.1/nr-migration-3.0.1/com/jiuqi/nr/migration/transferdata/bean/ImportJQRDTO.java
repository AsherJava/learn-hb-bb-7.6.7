/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

public class ImportJQRDTO {
    private String ossKey;
    private String fileName;
    private String taskId;
    private String formSchemeId;
    private String mappingSchemeId;

    public String getOssKey() {
        return this.ossKey;
    }

    public void setOssKey(String ossKey) {
        this.ossKey = ossKey;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getMappingSchemeId() {
        return this.mappingSchemeId;
    }

    public void setMappingSchemeId(String mappingSchemeId) {
        this.mappingSchemeId = mappingSchemeId;
    }
}

