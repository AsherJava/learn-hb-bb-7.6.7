/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public final class MD5Util {
    private static ThreadLocal<MessageDigest> digestThredLocal = new ThreadLocal<MessageDigest>(){

        @Override
        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private MD5Util() {
    }

    private static byte[] encrypt(String messageStr) {
        MessageDigest digest = digestThredLocal.get();
        if (digest == null) {
            return null;
        }
        return digest.digest(messageStr.getBytes());
    }

    public static UUID md5(String messageStr) {
        if (messageStr == null) {
            return null;
        }
        return MD5Util.fromGuid(MD5Util.encrypt(messageStr));
    }

    public static UUID fromGuid(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        if (byteArray.length != 16) {
            throw new IllegalArgumentException("data must be 16 byte in length.");
        }
        long most = byteArray[0] & 0xFF;
        for (int i = 1; i < 8; ++i) {
            most = most << 8 | (long)(byteArray[i] & 0xFF);
        }
        long least = byteArray[8] & 0xFF;
        for (int i = 9; i < 16; ++i) {
            least = least << 8 | (long)(byteArray[i] & 0xFF);
        }
        return new UUID(most, least);
    }
}

