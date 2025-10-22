/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CipherUtil {
    private static final String DES_KEY = "com.jiuqi.nr.analysisreport.utils.CipherUtil";
    private static final String KEY_GENERATOR_TYPE = "DESede";
    private static final String SECURERANDOM_TYPE = "SHA1PRNG";
    private static final String CIPHER_TYPE = "DESede/ECB/PKCS5Padding";
    private static final String CHARACTER_SET = "UTF-8";
    private static final int BUFF_SIZE = 128;

    public static void encrypt(InputStream is, OutputStream out) throws Exception {
        int r;
        KeyGenerator _generator = KeyGenerator.getInstance(KEY_GENERATOR_TYPE);
        SecureRandom secureRandom = SecureRandom.getInstance(SECURERANDOM_TYPE);
        secureRandom.setSeed(DES_KEY.getBytes(CHARACTER_SET));
        _generator.init(secureRandom);
        SecretKey privateKey = _generator.generateKey();
        Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
        cipher.init(1, privateKey);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[128];
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
    }

    public static void decrypt(InputStream is, OutputStream out) throws Exception {
        int r;
        KeyGenerator _generator = KeyGenerator.getInstance(KEY_GENERATOR_TYPE);
        SecureRandom secureRandom = SecureRandom.getInstance(SECURERANDOM_TYPE);
        secureRandom.setSeed(DES_KEY.getBytes(CHARACTER_SET));
        _generator.init(secureRandom);
        SecretKey privateKey = _generator.generateKey();
        Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
        cipher.init(2, privateKey);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[128];
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
    }
}

