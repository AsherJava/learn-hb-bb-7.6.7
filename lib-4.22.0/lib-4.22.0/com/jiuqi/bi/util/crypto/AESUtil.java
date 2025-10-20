/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Base64
 *  com.jiuqi.bi.util.JqLib
 */
package com.jiuqi.bi.util.crypto;

import com.jiuqi.bi.util.Base64;
import com.jiuqi.bi.util.JqLib;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final String AES_KEY = "D0310E4A016D11EC9ECD6C4B9029E8A4";

    private AESUtil() {
    }

    public static String encrypt(String content) throws Exception {
        return AESUtil.encrypt(content, AES_KEY, StandardCharsets.UTF_8);
    }

    public static String decrypt(String ciphertext) throws Exception {
        return AESUtil.decrypt(ciphertext, AES_KEY, StandardCharsets.UTF_8);
    }

    public static byte[] encrypt(byte[] data) throws Exception {
        Key key = AESUtil.getDefAESKey(AES_KEY);
        return AESUtil.encrypt(data, key);
    }

    public static byte[] decrypt(byte[] data) throws Exception {
        Key key = AESUtil.getDefAESKey(AES_KEY);
        return AESUtil.decrypt(data, key);
    }

    private static Key getDefAESKey(String keyString) {
        byte[] keyBytes = JqLib.hexStringToBytes((String)keyString);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static byte[] encrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, key);
        byte[] result = cipher.doFinal(data);
        return result;
    }

    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, key);
        return cipher.doFinal(data);
    }

    public static String encrypt(String content, String keyString, Charset encoding) throws Exception {
        Key key = AESUtil.getDefAESKey(keyString);
        byte[] data = content.getBytes(encoding);
        byte[] result = AESUtil.encrypt(data, key);
        return Base64.byteArrayToBase64((byte[])result);
    }

    public static String decrypt(String ciphertext, String keyString, Charset encoding) throws Exception {
        byte[] data = Base64.base64ToByteArray((String)ciphertext);
        Key key = AESUtil.getDefAESKey(keyString);
        byte[] result = AESUtil.decrypt(data, key);
        return new String(result, encoding);
    }

    public static String createAESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            return JqLib.bytesToHexString((byte[])keyBytes);
        }
        catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
            return null;
        }
    }
}

