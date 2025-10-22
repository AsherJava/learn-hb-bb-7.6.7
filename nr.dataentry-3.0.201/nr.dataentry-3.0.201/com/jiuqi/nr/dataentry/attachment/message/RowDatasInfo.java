/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.message;

public class RowDatasInfo {
    private String id;
    private String title;
    private String createtime;
    private String size;
    private String creator;
    private String confidential;
    private String fileCategory;
    private String field;
    private long sizeLong;
    private String fieldType;
    private boolean writeable;
    private String message;
    private String groupKey;
    private String dw;
    private String formKey;
    private int index;
    private boolean isFilepool = false;

    public RowDatasInfo() {
    }

    public RowDatasInfo(String id, String title, String createtime, String size, String creator, String confidential, String fileCategory, String field, long sizeLong, String fieldType, boolean writeable, String message) {
        this.id = id;
        this.title = title;
        this.createtime = createtime;
        this.size = size;
        this.creator = creator;
        this.confidential = confidential;
        this.fileCategory = fileCategory;
        this.field = field;
        this.sizeLong = sizeLong;
        this.fieldType = fieldType;
        this.writeable = writeable;
        this.message = message;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getConfidential() {
        return this.confidential;
    }

    public void setConfidential(String confidential) {
        this.confidential = confidential;
    }

    public String getFileCategory() {
        return this.fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public long getSizeLong() {
        return this.sizeLong;
    }

    public void setSizeLong(long sizeLong) {
        this.sizeLong = sizeLong;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isWriteable() {
        return this.writeable;
    }

    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isFilepool() {
        return this.isFilepool;
    }

    public void setFilepool(boolean filepool) {
        this.isFilepool = filepool;
    }
}

