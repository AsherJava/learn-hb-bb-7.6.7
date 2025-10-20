/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.model;

import com.jiuqi.np.core.model.CodeEnum;
import com.jiuqi.np.core.model.PageResult;
import java.io.Serializable;

public class Result<T>
implements Serializable {
    private static final long serialVersionUID = -7949528445312825133L;
    private String code;
    private String message;
    private T datas;

    public Result() {
    }

    public Result(String code, String message, T datas) {
        this.code = code;
        this.message = message;
        this.datas = datas;
    }

    public static <V> Result<PageResult<V>> OK(PageResult<V> data) {
        return Result.succeedWith(CodeEnum.SUCCESS.getCode(), "\u64cd\u4f5c\u6210\u529f\uff01", data);
    }

    public static <T> Result<T> succeed(String msg) {
        return Result.succeedWith(CodeEnum.SUCCESS.getCode(), msg, null);
    }

    public static <T> Result<T> succeed(String msg, T model) {
        return Result.succeedWith(CodeEnum.SUCCESS.getCode(), msg, model);
    }

    public static <T> Result<T> succeed(T model) {
        return Result.succeedWith(CodeEnum.SUCCESS.getCode(), "", model);
    }

    public static <T> Result<T> succeedWith(String code, String msg, T datas) {
        return new Result<T>(code, msg, datas);
    }

    public static <T> Result<T> failed(String msg) {
        return Result.failedWith(CodeEnum.FAIL.getCode(), msg, null);
    }

    public static <T> Result<T> failed(T model, String msg) {
        return Result.failedWith(CodeEnum.FAIL.getCode(), msg, model);
    }

    public static <T> Result<T> failedWith(String code, String msg, T datas) {
        return new Result<T>(code, msg, datas);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDatas() {
        return this.datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }
}

