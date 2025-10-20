/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class DigestUtils {
    private static final int STREAM_BUFFER_LENGTH = 1024;
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String md5Hex(InputStream data) throws IOException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return DigestUtils.encodeHexString(DigestUtils.digest(digest, data));
    }

    public static byte[] digest(MessageDigest messageDigest, InputStream data) throws IOException {
        return DigestUtils.updateDigest(messageDigest, data).digest();
    }

    private static MessageDigest updateDigest(MessageDigest digest, InputStream data) throws IOException {
        byte[] buffer = new byte[1024];
        int read = data.read(buffer, 0, 1024);
        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, 1024);
        }
        return digest;
    }

    public static String encodeHexString(byte[] data) {
        return new String(DigestUtils.encodeHex(data));
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; ++i) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0xF & data[i]];
        }
        return out;
    }
}

