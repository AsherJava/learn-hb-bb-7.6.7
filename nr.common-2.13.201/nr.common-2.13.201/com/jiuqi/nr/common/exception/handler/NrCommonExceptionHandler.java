/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.RestControllerAdvice
 */
package com.jiuqi.nr.common.exception.handler;

import com.jiuqi.nr.common.exception.ExceptionResult;
import com.jiuqi.nr.common.exception.NrCommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value=1)
public class NrCommonExceptionHandler {
    static final Logger LOGGER = LoggerFactory.getLogger(NrCommonExceptionHandler.class);

    @ExceptionHandler(value={NrCommonException.class})
    public ExceptionResult handlerException(NrCommonException e) {
        LOGGER.debug("\u7cfb\u7edf\u51fa\u73b0NrCommonException\u5f02\u5e38\u3002", e);
        return new ExceptionResult(e.getErrorCode(), e.getDatas());
    }
}

