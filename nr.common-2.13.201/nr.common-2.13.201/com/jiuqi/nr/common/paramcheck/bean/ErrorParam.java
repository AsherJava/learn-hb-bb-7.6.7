/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.bean;

public class ErrorParam {
    private String key;
    private String type;
    private String message;
    private Object data;
    private String errorData;
    private String url;

    public ErrorParam() {
    }

    public ErrorParam(String key, String type, String message, Object data, String errorData, String url) {
        this.key = key;
        this.type = type;
        this.message = message;
        this.data = data;
        this.errorData = errorData;
        this.url = url;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErrorData() {
        return this.errorData;
    }

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }
}

