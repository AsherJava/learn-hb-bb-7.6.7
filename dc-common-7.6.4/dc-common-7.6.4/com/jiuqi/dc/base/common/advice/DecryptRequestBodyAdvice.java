/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpInputMessage
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
 */
package com.jiuqi.dc.base.common.advice;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.dc.base.common.annotation.DecryptRequest;
import com.jiuqi.dc.base.common.enums.EncryptType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

@ControllerAdvice(basePackages={"com.jiuqi.dc"})
public class DecryptRequestBodyAdvice
extends RequestBodyAdviceAdapter {
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(DecryptRequest.class);
    }

    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            String encryptedBody = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
            DecryptRequest annotation = parameter.getParameterAnnotation(DecryptRequest.class);
            EncryptType encryptType = annotation.encryptType();
            String decryptStr = encryptType.decrypt(encryptedBody);
            final ByteArrayInputStream decryptedInputStream = new ByteArrayInputStream(decryptStr.getBytes());
            return new HttpInputMessage(){

                public InputStream getBody() {
                    return decryptedInputStream;
                }

                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u89e3\u5bc6\u5931\u8d25", (Throwable)e);
        }
    }
}

