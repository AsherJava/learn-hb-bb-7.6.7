/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.utils;

import com.jiuqi.va.biz.intf.encrypt.VaSymmetricEncryptService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class E
implements InitializingBean {
    @Autowired
    private VaSymmetricEncryptService vaSymmetricEncryptService;
    public static E e;
    private static final Logger log;

    @Override
    public void afterPropertiesSet() throws Exception {
        e = this;
    }

    public static String e(String s) {
        try {
            List<String> plaintexts = Collections.singletonList(s);
            return new String(E.encodeUrlSafe(E.e.vaSymmetricEncryptService.doEncrypt(plaintexts).get(0).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static String d(String s) {
        try {
            List<String> ciphertexts = Collections.singletonList(new String(E.decodeUrlSafe(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
            return E.e.vaSymmetricEncryptService.doDecrypt(ciphertexts).get(0);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static byte[] encodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlEncoder().encode(src);
    }

    public static byte[] decodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlDecoder().decode(src);
    }

    static {
        log = LoggerFactory.getLogger(E.class);
    }
}

