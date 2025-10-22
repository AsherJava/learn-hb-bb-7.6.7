/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

public class FileUploadReturnInfo {
    private String fileInfoKey;
    private String message;
    private String configInfo;
    private boolean success = true;
    private String errorType;

    public String getFileInfoKey() {
        return this.fileInfoKey;
    }

    public void setFileInfoKey(String fileInfoKey) {
        this.fileInfoKey = fileInfoKey;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getConfigInfo() {
        return this.configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }
}

