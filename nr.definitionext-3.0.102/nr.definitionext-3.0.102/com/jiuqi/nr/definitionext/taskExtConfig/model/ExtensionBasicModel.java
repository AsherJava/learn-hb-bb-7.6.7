/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definitionext.taskExtConfig.model;

public class ExtensionBasicModel<T> {
    private String extKey;
    private String taskKey;
    private String schemaKey;
    private String extData;
    private String extType;
    private String extCode;
    private T extInfoModel;

    public String getExtKey() {
        return this.extKey;
    }

    public void setExtKey(String extKey) {
        this.extKey = extKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSchemaKey() {
        return this.schemaKey;
    }

    public void setSchemaKey(String schemaKey) {
        this.schemaKey = schemaKey;
    }

    public String getExtData() {
        return this.extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getExtType() {
        return this.extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public String getExtCode() {
        return this.extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public T getExtInfoModel() {
        return this.extInfoModel;
    }

    public void setExtInfoModel(T extInfo) {
        this.extInfoModel = extInfo;
    }
}

