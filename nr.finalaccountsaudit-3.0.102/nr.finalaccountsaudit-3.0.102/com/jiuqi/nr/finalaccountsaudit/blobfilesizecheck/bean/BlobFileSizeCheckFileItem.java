/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import java.io.Serializable;

public class BlobFileSizeCheckFileItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private double fileSize;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
}

