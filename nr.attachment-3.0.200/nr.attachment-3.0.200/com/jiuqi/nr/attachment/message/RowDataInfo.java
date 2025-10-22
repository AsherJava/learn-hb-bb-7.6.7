/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.message;

import java.util.Date;

public class RowDataInfo {
    private String fileKey;
    private String name;
    private Date createtime;
    private long size;
    private String creator;
    private String confidential;
    private String category;
    private boolean writeable;
    private String groupKey;
    private String dw;
    private String formKey;
    private int index;
    private boolean isFilepool = false;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isWriteable() {
        return this.writeable;
    }

    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
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

