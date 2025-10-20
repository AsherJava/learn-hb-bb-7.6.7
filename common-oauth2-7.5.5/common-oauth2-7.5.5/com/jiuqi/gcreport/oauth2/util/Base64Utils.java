/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.codec.binary.Base64
 */
package com.jiuqi.gcreport.oauth2.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64Utils {
    private static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);

    public static String base64Encode(String plainText) {
        try {
            return Base64.encodeBase64String((byte[])plainText.getBytes("UTF-8"));
        }
        catch (Exception e) {
            logger.error("base64\u52a0\u5bc6\u9519\u8bef", e);
            return null;
        }
    }

    public static String base64Decode(String encode) {
        try {
            return new String(Base64.decodeBase64((byte[])encode.getBytes("UTF-8")));
        }
        catch (Exception e) {
            logger.error("base64\u89e3\u5bc6\u9519\u8bef", e);
            return null;
        }
    }
}

