/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.HttpMediaTypeNotSupportedException
 *  org.springframework.web.HttpRequestMethodNotSupportedException
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.ResponseStatus
 */
package com.jiuqi.np.core.exception;

import com.jiuqi.np.core.exception.BusinessException;
import com.jiuqi.np.core.exception.IdempotencyException;
import com.jiuqi.np.core.model.Result;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
public class DefaultExceptionAdvice {
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value={IllegalArgumentException.class})
    public Result<?> badRequestException(IllegalArgumentException e) {
        return this.defHandler("\u53c2\u6570\u89e3\u6790\u5931\u8d25", e);
    }

    @ResponseStatus(value=HttpStatus.FORBIDDEN)
    @ExceptionHandler(value={AccessDeniedException.class})
    public Result<?> badMethodExpressException(AccessDeniedException e) {
        return this.defHandler("\u6ca1\u6709\u6743\u9650\u8bf7\u6c42\u5f53\u524d\u65b9\u6cd5", e);
    }

    @ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value={HttpRequestMethodNotSupportedException.class})
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return this.defHandler("\u4e0d\u652f\u6301\u5f53\u524d\u8bf7\u6c42\u65b9\u6cd5", (Exception)e);
    }

    @ResponseStatus(value=HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(value={HttpMediaTypeNotSupportedException.class})
    public Result<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return this.defHandler("\u4e0d\u652f\u6301\u5f53\u524d\u5a92\u4f53\u7c7b\u578b", (Exception)e);
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value={SQLException.class})
    public Result<?> handleSQLException(SQLException e) {
        return this.defHandler("\u670d\u52a1\u8fd0\u884cSQLException\u5f02\u5e38", e);
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value={BusinessException.class})
    public Result<?> handleException(BusinessException e) {
        return this.defHandler("\u4e1a\u52a1\u5f02\u5e38", e);
    }

    @ResponseStatus(value=HttpStatus.OK)
    @ExceptionHandler(value={IdempotencyException.class})
    public Result<?> handleException(IdempotencyException e) {
        return Result.failed(e.getMessage());
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value={Exception.class})
    public Result<?> handleException(Exception e) {
        return this.defHandler("\u672a\u77e5\u5f02\u5e38", e);
    }

    private Result<?> defHandler(String msg, Exception e) {
        return Result.failed(msg);
    }
}

