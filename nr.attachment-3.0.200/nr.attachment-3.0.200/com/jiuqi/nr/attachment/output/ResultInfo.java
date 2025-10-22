/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.output;

public class ResultInfo {
    private boolean success;
    private String errorMessage;
    private String groupKey;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}

