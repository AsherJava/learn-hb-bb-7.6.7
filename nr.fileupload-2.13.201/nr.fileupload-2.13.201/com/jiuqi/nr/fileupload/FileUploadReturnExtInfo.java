/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

public class FileUploadReturnExtInfo {
    private boolean checkSuccess = true;
    private String errorMessage;

    public boolean isCheckSuccess() {
        return this.checkSuccess;
    }

    public void setCheckSuccess(boolean checkSuccess) {
        this.checkSuccess = checkSuccess;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

