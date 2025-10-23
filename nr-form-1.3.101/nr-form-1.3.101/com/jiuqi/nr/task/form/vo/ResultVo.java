/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.vo;

import java.io.Serializable;

public class ResultVo<T>
implements Serializable {
    private T data;
    private boolean success;
    private String message;

    public ResultVo() {
    }

    public ResultVo(boolean success, T data, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public ResultVo(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResultVo(boolean success, T data) {
        this.data = data;
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return this.success;
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

