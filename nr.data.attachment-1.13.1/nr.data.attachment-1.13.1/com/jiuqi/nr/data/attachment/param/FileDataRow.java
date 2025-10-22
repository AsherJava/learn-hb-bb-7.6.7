/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.attachment.param;

public class FileDataRow {
    private String dimNameList;
    private String dimValueList;
    private String formKey;
    private String formCode;
    private String fieldKey;
    private String fieldCode;
    private String fileGroup;
    private String fileKey;

    public String getDimNameList() {
        return this.dimNameList;
    }

    public void setDimNameList(String dimNameList) {
        this.dimNameList = dimNameList;
    }

    public String getDimValueList() {
        return this.dimValueList;
    }

    public void setDimValueList(String dimValueList) {
        this.dimValueList = dimValueList;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFileGroup() {
        return this.fileGroup;
    }

    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public FileDataRow(String dimNameList, String dimValueList, String formKey, String formCode, String fieldKey, String fieldCode, String fileGroup, String fileKey) {
        this.dimNameList = dimNameList;
        this.dimValueList = dimValueList;
        this.formKey = formKey;
        this.formCode = formCode;
        this.fieldKey = fieldKey;
        this.fieldCode = fieldCode;
        this.fileGroup = fileGroup;
        this.fileKey = fileKey;
    }

    public FileDataRow() {
    }
}

