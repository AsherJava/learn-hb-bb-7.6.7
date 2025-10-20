/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.crypto;

import java.io.UnsupportedEncodingException;

class CryptoUtil {
    private CryptoUtil() {
    }

    public static byte[] encodeStr(String s) {
        try {
            return s.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return s.getBytes();
        }
    }
}

