/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static String encodeToUrlSafeString(byte[] src) {
        return new String(Base64Utils.encodeUrlSafe(src), DEFAULT_CHARSET);
    }

    public static byte[] encodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlEncoder().encode(src);
    }
}

