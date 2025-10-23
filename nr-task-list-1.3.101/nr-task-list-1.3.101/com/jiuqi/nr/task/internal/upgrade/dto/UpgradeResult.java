/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.internal.upgrade.dto;

public class UpgradeResult {
    private String moduleName;
    private boolean success;
    private String message;

    public UpgradeResult(String moduleName) {
        this.moduleName = moduleName;
        this.success = true;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

