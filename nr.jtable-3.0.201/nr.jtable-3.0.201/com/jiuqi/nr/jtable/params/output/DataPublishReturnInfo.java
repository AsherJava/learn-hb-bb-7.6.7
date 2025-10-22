/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.EntityData;

public class DataPublishReturnInfo {
    private int status;
    private EntityData entity;
    private String message;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public EntityData getEntity() {
        return this.entity;
    }

    public void setEntity(EntityData entity) {
        this.entity = entity;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

