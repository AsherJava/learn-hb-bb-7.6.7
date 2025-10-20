/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class AES128EncryptUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(AES128EncryptUtil.class);
    private static final String CHARSET_NAME = "utf-8";
    private static String KEY = "";
    private static String IV = "";
    private static final String AES_TYPE = "AES/CBC/ISO10126Padding";

    public static String getPublicKey() {
        return KEY;
    }

    public static String getType() {
        return "AES128";
    }

    public static String getAlias() {
        return "1";
    }

    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_TYPE);
        byte[] dataBytes = data.getBytes(CHARSET_NAME);
        SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes(CHARSET_NAME));
        cipher.init(1, (Key)keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(dataBytes);
        return new String(Base64.getEncoder().encode(encrypted), CHARSET_NAME);
    }

    public static String decrypt(String data) throws Exception {
        byte[] encrypted = data.getBytes(CHARSET_NAME);
        encrypted = Base64.getDecoder().decode(encrypted);
        Cipher cipher = Cipher.getInstance(AES_TYPE);
        SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes(CHARSET_NAME));
        cipher.init(2, (Key)keyspec, ivspec);
        byte[] original = cipher.doFinal(encrypted);
        String originalString = new String(original, CHARSET_NAME);
        return originalString.trim();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        InputStream inputStream = null;
        try {
            ClassPathResource templateResource = new ClassPathResource("keys/AES128.key");
            inputStream = templateResource.getInputStream();
            String defaultKey = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            int indexOf = defaultKey.indexOf("----------");
            KEY = defaultKey.substring(0, indexOf);
            IV = defaultKey.substring(indexOf + "----------".length(), defaultKey.length());
        }
        catch (IOException e) {
            LOGGER.error("\u521d\u59cb\u5316\uff1aAES128\u5bc6\u94a5\u62a5\u9519\uff01", e);
        }
        finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    LOGGER.error("\u521d\u59cb\u5316\uff1aAES128\u5bc6\u94a5\u62a5\u9519\uff01", e);
                }
            }
        }
    }
}

