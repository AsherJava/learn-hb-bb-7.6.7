/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.utils;

import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class R<T> {
    private static final String ERROR_MESSAGE = "\u7cfb\u7edf\u5f02\u5e38";
    private static final Logger logger = LoggerFactory.getLogger(R.class);
    int code = 500;
    String msg = "\u7cfb\u7edf\u5f02\u5e38";
    String exception;
    T data;

    @Deprecated
    public R() {
    }

    public R(Throwable e, int code, T data) {
        this(e, e.getMessage() == null ? ERROR_MESSAGE : e.getMessage(), code, data);
    }

    public R(Throwable e, String msg, int code, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if (e != null) {
            if (Env.isDEBUG()) {
                this.exception = Utils.toString(e);
                logger.error(msg, e);
            } else {
                logger.error(msg, e);
            }
        }
    }

    public R(int code, String msg, String exception, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if (exception != null) {
            if (Env.isDEBUG()) {
                this.exception = exception;
                logger.error(exception);
            } else {
                logger.error(exception);
            }
        }
    }

    public void setError(String error) {
        this.exception = error;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public void setStatus(int status) {
        this.code = status == 200 ? 0 : status;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getException() {
        return this.exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> R<T> ok() {
        return new R<Object>(0, null, null, null);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(0, null, null, data);
    }

    public static <T> R<T> error(String message) {
        return new R<Object>(500, message, null, null);
    }

    public static <T> R<T> error(Throwable e) {
        return new R<Object>(e, 500, null);
    }

    public static <T> R<T> error(String message, Throwable e) {
        return new R<Object>(e, message, 500, null);
    }
}

