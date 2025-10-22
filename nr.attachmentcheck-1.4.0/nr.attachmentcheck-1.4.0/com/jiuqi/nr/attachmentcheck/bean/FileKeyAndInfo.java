/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachmentcheck.bean;

public class FileKeyAndInfo {
    private String fileKey;
    private String name;
    private String extension;
    private long size;

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

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}

