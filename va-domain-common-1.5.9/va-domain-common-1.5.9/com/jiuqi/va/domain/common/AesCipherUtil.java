/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.codec.Base64
 *  org.apache.shiro.codec.Hex
 *  org.apache.shiro.crypto.AesCipherService
 */
package com.jiuqi.va.domain.common;

import java.security.Key;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;

public class AesCipherUtil {
    public static String encode(String text) {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        Key key = aesCipherService.generateNewKey();
        byte[] keyByte = key.getEncoded();
        String encodeText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        String keyText = Base64.encodeToString((byte[])keyByte);
        return Base64.encodeToString((byte[])(encodeText + "." + keyText).getBytes());
    }

    public static String decode(String text) {
        String dataText = Base64.decodeToString((String)text);
        int flag = dataText.lastIndexOf(".");
        String encodeText = dataText.substring(0, flag);
        String keyText = dataText.substring(flag + 1);
        byte[] keyByte = Base64.decode((String)keyText);
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        return new String(aesCipherService.decrypt(Hex.decode((String)encodeText), keyByte).getBytes());
    }

    public static byte[] reverse(byte[] a) {
        if (a == null || a.length <= 1) {
            return a;
        }
        int left = 0;
        for (int right = a.length - 1; left < right; ++left, --right) {
            byte temp = a[left];
            a[left] = a[right];
            a[right] = temp;
        }
        return a;
    }

    public static byte[] exchange(byte[] a) {
        if (a == null || a.length <= 1) {
            return a;
        }
        for (int i = 0; i < a.length - 1; i += 2) {
            byte temp = a[i];
            a[i] = a[i + 1];
            a[i + 1] = temp;
        }
        return a;
    }
}

