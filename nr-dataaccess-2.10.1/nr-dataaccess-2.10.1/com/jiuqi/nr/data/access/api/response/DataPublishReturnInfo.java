/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.api.response;

public class DataPublishReturnInfo {
    private int status;
    private String entity;
    private String message;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

