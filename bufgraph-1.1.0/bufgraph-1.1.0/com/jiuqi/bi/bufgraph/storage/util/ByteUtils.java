/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage.util;

public final class ByteUtils {
    public static byte[] int2Bytes(int value) {
        byte[] bytes = new byte[4];
        bytes[3] = (byte)(value >> 24);
        bytes[2] = (byte)(value >> 16);
        bytes[1] = (byte)(value >> 8);
        bytes[0] = (byte)value;
        return bytes;
    }

    public static void int2Bytes(int value, byte[] dest, int pos) {
        dest[pos] = (byte)value;
        dest[pos + 1] = (byte)(value >> 8);
        dest[pos + 2] = (byte)(value >> 16);
        dest[pos + 3] = (byte)(value >> 24);
    }

    public static int bytes2Int(byte[] bytes) {
        return ByteUtils.bytes2Int(bytes, 0);
    }

    public static int bytes2Int(byte[] bytes, int start) {
        int bit1 = bytes[start] & 0xFF;
        int bit2 = (bytes[start + 1] & 0xFF) << 8;
        int bit3 = (bytes[start + 2] & 0xFF) << 16;
        int bit4 = (bytes[start + 3] & 0xFF) << 24;
        return bit1 | bit2 | bit3 | bit4;
    }

    public static byte[] long2Bytes(long value) {
        long temp = value;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; ++i) {
            b[i] = Long.valueOf(temp & 0xFFL).byteValue();
            temp >>= 8;
        }
        return b;
    }

    public static void long2Bytes(long value, byte[] dest, int pos) {
        byte[] bytes = ByteUtils.long2Bytes(value);
        System.arraycopy(bytes, 0, dest, pos, 8);
    }

    public static long bytes2Long(byte[] bytes, int start) {
        long s = 0L;
        long s0 = bytes[start] & 0xFF;
        long s1 = bytes[start + 1] & 0xFF;
        long s2 = bytes[start + 2] & 0xFF;
        long s3 = bytes[start + 3] & 0xFF;
        long s4 = bytes[start + 4] & 0xFF;
        long s5 = bytes[start + 5] & 0xFF;
        long s6 = bytes[start + 6] & 0xFF;
        long s7 = bytes[start + 7] & 0xFF;
        s = s0 | (s1 <<= 8) | (s2 <<= 16) | (s3 <<= 24) | (s4 <<= 32) | (s5 <<= 40) | (s6 <<= 48) | (s7 <<= 56);
        return s;
    }

    public static long bytes2Long(byte[] bytes) {
        return ByteUtils.bytes2Long(bytes, 0);
    }
}

