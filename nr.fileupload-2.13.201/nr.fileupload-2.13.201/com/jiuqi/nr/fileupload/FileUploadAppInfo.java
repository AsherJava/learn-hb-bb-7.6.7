/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

import java.io.Serializable;

public class FileUploadAppInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appName;
    private double fileSize;
    private String fileSuffixInfo;

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public double getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSuffixInfo() {
        return this.fileSuffixInfo;
    }

    public void setFileSuffixInfo(String fileSuffixInfo) {
        this.fileSuffixInfo = fileSuffixInfo;
    }
}

