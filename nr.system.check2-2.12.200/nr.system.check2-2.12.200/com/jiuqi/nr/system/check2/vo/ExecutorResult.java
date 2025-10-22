/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check2.vo;

public class ExecutorResult {
    private String resourceKey;
    private boolean success;
    private String message;
    private Object data;

    public String getResourceKey() {
        return this.resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
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

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ExecutorResult() {
    }

    public ExecutorResult(boolean success) {
        this.success = success;
    }

    public ExecutorResult(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public ExecutorResult(String resourceKey, boolean success) {
        this.resourceKey = resourceKey;
        this.success = success;
    }

    public ExecutorResult(String resourceKey, boolean success, String message) {
        this.resourceKey = resourceKey;
        this.success = success;
        this.message = message;
    }

    public ExecutorResult(String resourceKey, boolean success, String message, Object data) {
        this.resourceKey = resourceKey;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}

