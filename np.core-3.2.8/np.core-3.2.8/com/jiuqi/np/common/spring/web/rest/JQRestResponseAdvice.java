/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.http.server.ServerHttpRequest
 *  org.springframework.http.server.ServerHttpResponse
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
 */
package com.jiuqi.np.common.spring.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.common.spring.web.rest.JQRestResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Order(value=9797)
@ControllerAdvice(annotations={JQRestController.class})
public class JQRestResponseAdvice
implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof JQRestResponseBody) {
            return body;
        }
        JQRestResponseBody newBody = JQRestResponseBody.success(body);
        if (body instanceof String) {
            try {
                return this.objectMapper.writeValueAsString((Object)newBody);
            }
            catch (JsonProcessingException e) {
                return JQRestResponseBody.error((Exception)((Object)e));
            }
        }
        return newBody;
    }
}

