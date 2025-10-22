/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.exception.ExceptionResult
 *  com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.RestControllerAdvice
 *  org.springframework.web.multipart.MultipartException
 */
package com.jiuqi.nr.dataentry.exception.handler;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.exception.ExceptionResult;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
@Order(value=1)
public class DataEntryExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(DataEntryExceptionHandler.class);
    @Value(value="${spring.servlet.multipart.max-request-size:#{null}}")
    private String maxFile;

    @ExceptionHandler(value={MultipartException.class})
    public ExceptionResult handlerMultipartException(MultipartException e) {
        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage() + this.maxFile, (Throwable)e);
        if (StringUtils.isEmpty((String)this.maxFile)) {
            this.maxFile = "10M";
        }
        return new ExceptionResult(JtableExceptionCodeCost.EXCEPTION_FILE_BIG, new String[]{this.maxFile});
    }
}

