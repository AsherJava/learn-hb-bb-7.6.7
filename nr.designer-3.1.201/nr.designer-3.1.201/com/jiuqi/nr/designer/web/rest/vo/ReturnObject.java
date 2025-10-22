/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class ReturnObject {
    private String message;
    private String code;
    private boolean success = true;
    private Object obj;

    public ReturnObject() {
    }

    public ReturnObject(boolean success) {
        this.setSuccess(success);
    }

    public ReturnObject(boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean successs) {
        this.success = successs;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}

