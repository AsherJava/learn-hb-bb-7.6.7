/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.navigation.common;

import com.jiuqi.navigation.common.BusinessResponseErrorCodeEnum;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import org.springframework.util.ObjectUtils;

public final class BusinessResponseEntity<T> {
    private String timestamp = LocalDateTime.now().toString();
    private boolean success;
    private String errorCode;
    private String errorMessage;
    private String errorDetail;
    private T data;

    private BusinessResponseEntity() {
    }

    public static <T> BusinessResponseEntity<T> ok() {
        return BusinessResponseEntity.ok(null);
    }

    public static <T> BusinessResponseEntity<T> ok(T data) {
        return new BusinessResponseEntity<T>().success(true).data(data);
    }

    public static <T> BusinessResponseEntity<T> error() {
        return BusinessResponseEntity.error(null, null);
    }

    public static <T> BusinessResponseEntity<T> error(String errorMessage) {
        return BusinessResponseEntity.error(null, errorMessage, null);
    }

    public static <T> BusinessResponseEntity<T> error(String errorCode, String errorMessage) {
        return BusinessResponseEntity.error(errorCode, errorMessage, null);
    }

    public static <T> BusinessResponseEntity<T> error(String errorCode, String errorMessage, String errorDetail) {
        return new BusinessResponseEntity<T>().success(false).errorCode(errorCode).errorMessage(errorMessage).errorDetail(errorDetail);
    }

    public static <T> BusinessResponseEntity<T> error(BusinessResponseErrorCodeEnum errorCodeEnum) {
        return BusinessResponseEntity.error(errorCodeEnum.getErrorCode(), errorCodeEnum.getErrorTitle());
    }

    public static <T> BusinessResponseEntity<T> error(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        e.printStackTrace(printWriter);
        printWriter.flush();
        stringWriter.flush();
        String errorDetail = stringWriter.toString();
        String errorMessage = ObjectUtils.isEmpty(e.getMessage()) ? "\u7cfb\u7edf\u5904\u7406\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\u9519\u8bef\u3002" : e.getMessage();
        return BusinessResponseEntity.error(null, errorMessage, errorDetail);
    }

    public BusinessResponseEntity<T> success(boolean isSuccess) {
        this.success = isSuccess;
        return this;
    }

    public BusinessResponseEntity<T> errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public BusinessResponseEntity<T> errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public BusinessResponseEntity<T> errorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
        return this;
    }

    public BusinessResponseEntity<T> data(T data) {
        this.data = data;
        return this;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public T getData() {
        return this.data;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}

