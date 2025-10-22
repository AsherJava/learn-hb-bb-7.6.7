/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.output;

import com.jiuqi.nr.attachment.output.FailedFileInfo;
import java.util.List;

public class FileImportResult {
    private boolean success;
    private String errorMsg;
    private List<FailedFileInfo> failedFileInfoList;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<FailedFileInfo> getFailedFileInfoList() {
        return this.failedFileInfoList;
    }

    public void setFailedFileInfoList(List<FailedFileInfo> failedFileInfoList) {
        this.failedFileInfoList = failedFileInfoList;
    }
}

