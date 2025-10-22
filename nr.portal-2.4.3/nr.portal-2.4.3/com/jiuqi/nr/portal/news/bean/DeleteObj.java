/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteObj {
    @JsonProperty(value="Success")
    private boolean success;
    @JsonProperty(value="Message")
    private String message;
    @JsonProperty(value="Code")
    private String code;

    public boolean getSuccess() {
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

