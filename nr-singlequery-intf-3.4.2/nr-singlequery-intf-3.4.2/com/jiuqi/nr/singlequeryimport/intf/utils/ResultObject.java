/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.intf.utils;

import java.io.Serializable;

public class ResultObject
implements Serializable {
    private static final long serialVersionUID = 3525659634537742842L;
    private boolean success;
    private String message;
    private transient Object data;

    public ResultObject() {
        this.success = true;
        this.message = "\u64cd\u4f5c\u6210\u529f!";
    }

    public ResultObject(Object data) {
        this();
        this.data = data;
    }

    public ResultObject(boolean success, String message) {
        this.success = success;
        this.message = message;
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

    public static ResultObject ok() {
        return new ResultObject();
    }

    public static ResultObject ok(Object data) {
        return new ResultObject(data);
    }

    public static ResultObject error(String errorMsg) {
        return new ResultObject(false, errorMsg);
    }
}

