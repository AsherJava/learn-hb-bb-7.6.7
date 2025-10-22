/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treecommon.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.slf4j.LoggerFactory;

public class UUIDFormatter {
    private static MessageDigest md;
    private static String MD5;
    private static String SHA_256;

    private UUIDFormatter() {
    }

    public static UUID fromString(String str) {
        return UUID.fromString(str);
    }

    public static UUID fromText(String text) {
        int i;
        byte[] name = text.getBytes(StandardCharsets.UTF_8);
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6] = (byte)(md5Bytes[6] & 0xF);
        md5Bytes[6] = (byte)(md5Bytes[6] | 0x30);
        md5Bytes[8] = (byte)(md5Bytes[8] & 0x3F);
        md5Bytes[8] = (byte)(md5Bytes[8] | 0x80);
        long msb = 0L;
        long lsb = 0L;
        assert (md5Bytes.length == 16) : "data must be 16 bytes in length";
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(md5Bytes[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(md5Bytes[i] & 0xFF);
        }
        return new UUID(msb, lsb);
    }

    static {
        MD5 = "MD5";
        SHA_256 = "SHA-256";
        if (md == null) {
            try {
                md = MessageDigest.getInstance(MD5);
            }
            catch (NoSuchAlgorithmException nsae) {
                try {
                    md = MessageDigest.getInstance(SHA_256);
                }
                catch (NoSuchAlgorithmException e) {
                    LoggerFactory.getLogger(UUIDFormatter.class).error(e.getMessage(), e.getCause());
                }
            }
        }
    }
}

