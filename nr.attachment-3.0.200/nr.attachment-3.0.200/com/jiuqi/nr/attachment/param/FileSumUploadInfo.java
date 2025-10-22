/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.param;

import java.io.InputStream;

public class FileSumUploadInfo {
    private InputStream file;
    private String name;
    private long size;
    private String fileSecret;
    private String category;

    public InputStream getFile() {
        return this.file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
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
}

