/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nrdt.parampacket.manage.bean;

public class ResponseObj {
    private boolean success;
    private Object data;
    private String message;

    public ResponseObj() {
    }

    public ResponseObj(boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static ResponseObj SUCCESS(Object data, String message) {
        return new ResponseObj(true, data, message);
    }

    public static ResponseObj FAIL(Object data, String message) {
        return new ResponseObj(false, data, message);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

