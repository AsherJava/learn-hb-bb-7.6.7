/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.crypto;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crypto {
    private static final Logger logger = LoggerFactory.getLogger(Crypto.class);
    private static String KEY = "JIUQI/NEWREP/AES";
    private static String IV = "JIUQI/NEWREP/AES";

    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength += blockSize - plaintextLength % blockSize;
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(1, (Key)keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new Base64().encodeToString(encrypted);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(2, (Key)keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String encrypt(String data) throws Exception {
        return Crypto.encrypt(data, KEY, IV);
    }

    public static String desEncrypt(String data) throws Exception {
        return Crypto.desEncrypt(data, KEY, IV);
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        for (int i = 0; i < bytes.length; ++i) {
            String sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String test = "admin";
        String data = null;
        data = Crypto.encrypt(test, KEY, IV);
        logger.info("\u52a0\u5bc6\u540e" + data);
        logger.info("\u89e3\u5bc6\u540e" + Crypto.desEncrypt(data, KEY, IV));
        String enmi = "z/7z22C1U3bleGH4r/PYKQ==";
        logger.info("\u89e3\u5bc6\u4e32\uff1a" + enmi);
        logger.info("\u89e3\u5bc6\u540e" + Crypto.desEncrypt(enmi, KEY, IV));
    }
}

