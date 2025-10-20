/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.RestControllerAdvice
 */
package com.jiuqi.np.common.spring.web.rest;

import com.jiuqi.np.common.exception.AbstractJQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.common.spring.web.rest.JQRestResponseBody;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(value=9797)
@RestControllerAdvice(annotations={JQRestController.class})
public class JQRestControllerExceptionHandler {
    @ExceptionHandler(value={AbstractJQException.class})
    public ResponseEntity<JQRestResponseBody> onException(AbstractJQException e) {
        JQRestResponseBody responseBody = JQRestResponseBody.error(e);
        return new ResponseEntity((Object)responseBody, null, HttpStatus.OK.value());
    }
}

