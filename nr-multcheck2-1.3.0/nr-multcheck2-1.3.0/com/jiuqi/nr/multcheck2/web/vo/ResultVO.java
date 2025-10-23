/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.vo;

public class ResultVO {
    private boolean success;
    private String message;
    private String key;

    public ResultVO(String key, boolean success, String message) {
        this.key = key;
        this.success = success;
        this.message = message;
    }

    public static ResultVO error(String message) {
        return new ResultVO(null, false, message);
    }

    public static ResultVO success(String key) {
        return new ResultVO(key, true, null);
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

