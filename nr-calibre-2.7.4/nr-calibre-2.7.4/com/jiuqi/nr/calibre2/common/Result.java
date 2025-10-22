/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.common;

public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<Object>(1, null, null);
    }

    public static <T> Result<T> success(T o) {
        return new Result<T>(1, null, o);
    }

    public static <T> Result<T> success(T o, String message) {
        return new Result<T>(1, message, o);
    }

    public static <T> Result<T> fail() {
        return new Result<Object>(0, null, null);
    }

    public static <T> Result<T> fail(T o) {
        return new Result<T>(0, null, o);
    }

    public static <T> Result<T> fail(T o, String message) {
        return new Result<T>(0, message, o);
    }

    public T getData() {
        return this.data;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

