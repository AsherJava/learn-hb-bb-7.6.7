/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.crypto.hash.SimpleHash
 *  org.apache.shiro.util.ByteSource$Util
 */
package com.jiuqi.va.domain.common;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Util {
    private static final String SALT = new String(new byte[]{108, 105, 117, 121, 97, 110, 104, 117, 105});
    private static final String ALGORITH_NAME = new String(new byte[]{109, 100, 53});
    private static final int HASH_ITERATIONS = 2;

    public static String encrypt(String text) {
        return new SimpleHash(ALGORITH_NAME, (Object)text, (Object)ByteSource.Util.bytes((String)(text + SALT)), 2).toHex();
    }

    public static String encrypt(String username, String pswd) {
        return new SimpleHash(ALGORITH_NAME, (Object)pswd, (Object)ByteSource.Util.bytes((String)(username + SALT)), 2).toHex();
    }
}

