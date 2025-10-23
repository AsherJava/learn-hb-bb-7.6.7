/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.data.dto;

import com.jiuqi.nr.nrdx.data.dto.FileType;

public class FileDTO {
    private String downloadKey;
    private FileType type;
    private int encryptType;

    public String getDownloadKey() {
        return this.downloadKey;
    }

    public void setDownloadKey(String downloadKey) {
        this.downloadKey = downloadKey;
    }

    public FileType getType() {
        return this.type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public int getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(int encryptType) {
        this.encryptType = encryptType;
    }
}

