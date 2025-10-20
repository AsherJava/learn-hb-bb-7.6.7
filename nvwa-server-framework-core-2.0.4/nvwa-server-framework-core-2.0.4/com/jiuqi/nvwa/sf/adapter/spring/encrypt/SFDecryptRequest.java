/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.RSAEncryptUtils
 *  org.json.JSONObject
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpInputMessage
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
 */
package com.jiuqi.nvwa.sf.adapter.spring.encrypt;

import com.jiuqi.np.core.utils.RSAEncryptUtils;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

@ControllerAdvice
public class SFDecryptRequest
extends RequestBodyAdviceAdapter {
    @Value(value="${jiuqi.nvwa.sf.encryption-disable:false}")
    private boolean closeEncryption;

    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(SFDecrypt.class) || methodParameter.hasParameterAnnotation(SFDecrypt.class);
    }

    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        String decrypt;
        block8: {
            if (this.closeEncryption) {
                return inputMessage;
            }
            InputStream bodyIs = inputMessage.getBody();
            byte[] data = new byte[1024];
            StringBuilder bodyStr = new StringBuilder();
            int len = 0;
            while (-1 != (len = bodyIs.read(data))) {
                String str = new String(data, 0, len, StandardCharsets.UTF_8);
                bodyStr.append(str);
            }
            decrypt = "";
            try {
                try {
                    JSONObject object = new JSONObject(bodyStr.toString());
                    if (!object.has("data")) break block8;
                    if (object.has("type")) {
                        if ("b".equals(object.optString("type"))) {
                            Base64.Decoder decoder = Base64.getDecoder();
                            decrypt = new String(decoder.decode(object.optString("data")), "UTF-8");
                        }
                        break block8;
                    }
                    decrypt = RSAEncryptUtils.decryptLong((String)object.optString("data"));
                }
                catch (Exception e) {
                    decrypt = RSAEncryptUtils.decryptLong((String)bodyStr.toString());
                }
            }
            catch (Exception e) {
                throw new RuntimeException("\u52a0\u5bc6\u8bf7\u6c42\u4f53\u89e3\u6790\u5f02\u5e38", e);
            }
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(decrypt.getBytes(StandardCharsets.UTF_8));
        return new HttpInputMessage(){

            public InputStream getBody() throws IOException {
                return bais;
            }

            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }
        };
    }
}

