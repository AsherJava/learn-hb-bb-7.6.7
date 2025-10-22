/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treecommon.utils;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UUID5Formatter {
    public static final UUID POLYGENEA_NAMESPACE = UUID.fromString("954aac7d-47b2-5975-9a80-37eeed186527");

    private UUID5Formatter() {
    }

    public static UUID fromBytes(byte[] name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return UUID5Formatter.makeUUID(md.digest(name), 5);
        }
        catch (NoSuchAlgorithmException e) {
            throw new AssertionError((Object)e);
        }
    }

    public static UUID fromBytes(UUID namespace, byte[] name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            if (namespace == null) {
                md.update(new byte[16]);
            } else {
                md.update(UUID5Formatter.asBytes(namespace.getMostSignificantBits(), ByteOrder.BIG_ENDIAN));
                md.update(UUID5Formatter.asBytes(namespace.getLeastSignificantBits(), ByteOrder.BIG_ENDIAN));
            }
            return UUID5Formatter.makeUUID(md.digest(name), 5);
        }
        catch (NoSuchAlgorithmException e) {
            throw new AssertionError((Object)e);
        }
    }

    public static UUID fromUTF8(String name) {
        return UUID5Formatter.fromBytes(name.getBytes(Charset.forName("UTF-8")));
    }

    public static UUID fromUTF8(UUID namespace, String name) {
        return UUID5Formatter.fromBytes(namespace, name.getBytes(Charset.forName("UTF-8")));
    }

    static long peekLong(byte[] src, int offset, ByteOrder order) {
        long ans = 0L;
        if (order == ByteOrder.BIG_ENDIAN) {
            for (int i = offset; i < offset + 8; ++i) {
                ans <<= 8;
                ans |= (long)src[i] & 0xFFL;
            }
        } else {
            for (int i = offset + 7; i >= offset; --i) {
                ans <<= 8;
                ans |= (long)src[i] & 0xFFL;
            }
        }
        return ans;
    }

    static void putLong(long data, byte[] dest, int offset, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            for (int i = offset + 7; i >= offset; --i) {
                dest[i] = (byte)(data & 0xFFL);
                data >>= 8;
            }
        } else {
            for (int i = offset; i < offset + 8; ++i) {
                dest[i] = (byte)(data & 0xFFL);
                data >>= 8;
            }
        }
    }

    static byte[] asBytes(long data, ByteOrder order) {
        byte[] ans = new byte[8];
        UUID5Formatter.putLong(data, ans, 0, order);
        return ans;
    }

    static UUID makeUUID(byte[] hash, int version) {
        long msb = UUID5Formatter.peekLong(hash, 0, ByteOrder.BIG_ENDIAN);
        long lsb = UUID5Formatter.peekLong(hash, 8, ByteOrder.BIG_ENDIAN);
        msb &= 0xFFFFFFFFFFFF0FFFL;
        lsb &= 0x3FFFFFFFFFFFFFFFL;
        return new UUID(msb |= (long)version << 12, lsb |= Long.MIN_VALUE);
    }
}

