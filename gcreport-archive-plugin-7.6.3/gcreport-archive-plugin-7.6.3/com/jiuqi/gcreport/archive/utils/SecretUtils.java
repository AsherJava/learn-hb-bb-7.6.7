/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.archive.utils;

import com.jiuqi.gcreport.archive.utils.SecretCommon;
import org.apache.commons.lang3.StringUtils;

public class SecretUtils {
    public static String encrypt(String plainText) {
        return SecretCommon.encrypt(plainText);
    }

    public static String encryptByKey(String keyText, String plainText) {
        return SecretCommon.encryptByKey(keyText, plainText);
    }

    public static boolean verify(String key, String src, String sm3HexStr) {
        String newcode = SecretUtils.encryptByKey(key, src);
        if (StringUtils.isEmpty((CharSequence)newcode)) {
            return false;
        }
        return newcode.equals(sm3HexStr);
    }

    public static void main(String[] args) {
        String s = SecretUtils.encryptByKey("&37j2Q$92*v@dhCQ", "GJPTSQ-17DM202403260003");
        System.out.println("s=" + s);
    }
}

