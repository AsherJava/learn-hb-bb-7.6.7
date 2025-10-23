/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.face.ParamType;

public class ErrorData {
    private ParamType paramType;
    private String message;
    private String key;
    private String code;
    private String title;
    private Object data;

    public ErrorData() {
    }

    public ErrorData(ParamType paramType, String message) {
        this.paramType = paramType;
        this.message = message;
    }

    public ErrorData(ParamType paramType, String message, String key) {
        this.paramType = paramType;
        this.message = message;
        this.key = key;
    }

    public ErrorData(ParamType paramType, String message, DataCore baseData) {
        this.paramType = paramType;
        this.message = message;
        this.key = baseData.getKey();
        this.title = baseData.getTitle();
    }

    public ErrorData(ParamType paramType, String message, String key, String code, String title) {
        this.paramType = paramType;
        this.message = message;
        this.key = key;
        this.code = code;
        this.title = title;
    }

    public ParamType getParamType() {
        return this.paramType;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "ErrorData{paramType=" + this.paramType + ", message='" + this.message + '\'' + ", key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", data=" + this.data.toString() + '}';
    }
}

