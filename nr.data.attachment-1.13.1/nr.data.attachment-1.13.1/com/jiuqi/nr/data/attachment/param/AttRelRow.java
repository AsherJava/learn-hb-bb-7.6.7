/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.attachment.param;

import java.util.Map;

public class AttRelRow {
    private String groupKey;
    private String fileKey;
    private String fileName;
    private String fileSize;
    private String fileSecret;
    private String category;
    private String dataTableCode;
    private String fieldCode;
    private Map<String, String> dimNameValue;

    public AttRelRow(String groupKey, String fileKey, String fileName, String fileSize, String fileSecret, String category, String dataTableCode, String fieldCode, Map<String, String> dimNameValue) {
        this.groupKey = groupKey;
        this.fileKey = fileKey;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileSecret = fileSecret;
        this.category = category;
        this.dataTableCode = dataTableCode;
        this.fieldCode = fieldCode;
        this.dimNameValue = dimNameValue;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSecret() {
        return this.fileSecret;
    }

    public void setFileSecret(String fileSecret) {
        this.fileSecret = fileSecret;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDataTableCode() {
        return this.dataTableCode;
    }

    public void setDataTableCode(String dataTableCode) {
        this.dataTableCode = dataTableCode;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Map<String, String> getDimNameValue() {
        return this.dimNameValue;
    }

    public void setDimNameValue(Map<String, String> dimNameValue) {
        this.dimNameValue = dimNameValue;
    }
}

