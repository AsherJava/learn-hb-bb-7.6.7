/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.http.server.ServerHttpRequest
 *  org.springframework.http.server.ServerHttpResponse
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
 */
package com.jiuqi.dc.base.common.advice;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.annotation.EncryptResponse;
import com.jiuqi.dc.base.common.enums.EncryptType;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(basePackages={"com.jiuqi.dc"})
public class EncryptResponseBodyAdvice
implements ResponseBodyAdvice<Object> {
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(EncryptResponse.class);
    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(body instanceof BusinessResponseEntity)) {
            return body;
        }
        BusinessResponseEntity responseEntity = (BusinessResponseEntity)body;
        Object data = responseEntity.getData();
        if (Objects.isNull(data)) {
            return body;
        }
        try {
            EncryptResponse annotation = returnType.getMethodAnnotation(EncryptResponse.class);
            EncryptType encryptType = annotation.encryptType();
            String encryptStr = encryptType.encrypt(data);
            BusinessResponseEntity newResponseEntity = BusinessResponseEntity.ok();
            responseEntity.data(null);
            BeanUtils.copyProperties(responseEntity, newResponseEntity);
            return newResponseEntity.data((Object)encryptStr);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u52a0\u5bc6\u5931\u8d25", (Throwable)e);
        }
    }
}

