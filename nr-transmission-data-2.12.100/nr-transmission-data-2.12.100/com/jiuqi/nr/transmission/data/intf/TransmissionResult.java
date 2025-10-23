/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

import java.io.Serializable;

public class TransmissionResult<T extends Serializable>
implements Serializable {
    private String executeKey;
    private T data;
    private Boolean success = true;
    private String message;
    private String instanceId;

    public String getExecuteKey() {
        return this.executeKey;
    }

    public void setExecuteKey(String executeKey) {
        this.executeKey = executeKey;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}

