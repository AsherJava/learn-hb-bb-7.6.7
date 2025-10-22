/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util;

public class BitConverter {
    public static short toInt16(byte[] bytes, int offset) {
        short result = (short)(bytes[offset] & 0xFF);
        result = (short)(result | (bytes[offset + 1] & 0xFF) << 8);
        return (short)(result & 0xFFFF);
    }

    public static int toUInt16(byte[] bytes, int offset) {
        int result = bytes[offset + 1] & 0xFF;
        return (result |= (bytes[offset] & 0xFF) << 8) & 0xFFFF;
    }

    public static int toInt32(byte[] bytes, int offset) {
        int result = bytes[offset] & 0xFF;
        result |= (bytes[offset + 1] & 0xFF) << 8;
        result |= (bytes[offset + 2] & 0xFF) << 16;
        return result |= (bytes[offset + 3] & 0xFF) << 24;
    }

    public static long toUInt32(byte[] bytes, int offset) {
        long result = bytes[offset] & 0xFF;
        result |= (long)((bytes[offset + 1] & 0xFF) << 8);
        result |= (long)((bytes[offset + 2] & 0xFF) << 16);
        return (result |= (long)((bytes[offset + 3] & 0xFF) << 24)) & 0xFFFFFFFFL;
    }

    public static long toInt64(byte[] buffer, int offset) {
        long values = 0L;
        for (int i = 0; i < 8; ++i) {
            values <<= 8;
            values |= (long)(buffer[offset + i] & 0xFF);
        }
        return values;
    }

    public static long toUInt64(byte[] bytes, int offset) {
        long result = 0L;
        int offs = offset;
        for (int i = 0; i <= 56; i += 8) {
            long a = bytes[offs] & 0xFF;
            result |= (a <<= i);
            ++offs;
        }
        return result;
    }

    public static float toFloat(byte[] bs, int index) {
        return Float.intBitsToFloat(BitConverter.toInt32(bs, index));
    }

    public static double toDouble(byte[] arr, int offset) {
        long value = BitConverter.toUInt64(arr, offset);
        return Double.longBitsToDouble(value);
    }

    public static boolean toBoolean(byte[] bytes, int offset) {
        return bytes[offset] != 0;
    }

    public static byte[] getBytes(byte value) {
        byte[] bytes = new byte[]{value};
        return bytes;
    }

    public static byte[] getBytes(short value) {
        byte[] bytes = new byte[]{(byte)(value & 0xFF), (byte)((value & 0xFF00) >> 8)};
        return bytes;
    }

    public static byte[] getBytes2(int value) {
        byte[] bytes = new byte[]{(byte)(value & 0xFF), (byte)((value & 0xFF00) >> 8)};
        return bytes;
    }

    public static byte[] getBytes(int value) {
        byte[] bytes = new byte[]{(byte)(value & 0xFF), (byte)(value >> 8 & 0xFF), (byte)(value >> 16 & 0xFF), (byte)(value >>> 24)};
        return bytes;
    }

    public static byte[] getBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; ++i) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte)(values >> offset & 0xFFL);
        }
        return buffer;
    }

    public static byte[] getBytes2(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; ++i) {
            int offset = i * 8;
            buffer[i] = (byte)(values >> offset & 0xFFL);
        }
        return buffer;
    }

    public static byte[] getBytes(float value) {
        return BitConverter.getBytes(Float.floatToIntBits(value));
    }

    public static byte[] getBytes(double val) {
        long value = Double.doubleToLongBits(val);
        return BitConverter.getBytes(value);
    }

    public static byte[] getBytes2(double val) {
        long value = Double.doubleToLongBits(val);
        return BitConverter.getBytes2(value);
    }

    public static byte[] getBytes(boolean value) {
        return new byte[]{(byte)(value ? 1 : 0)};
    }

    public static byte intToByte(int x) {
        return (byte)x;
    }

    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    public static char toChar(byte[] bs, int offset) {
        return (char)((bs[offset] & 0xFF) << 8 | bs[offset + 1] & 0xFF);
    }

    public static byte[] getBytes(char value) {
        byte[] b = new byte[]{(byte)((value & 0xFF00) >> 8), (byte)(value & 0xFF)};
        return b;
    }

    public static byte[] concat(byte[] ... bs) {
        int len = 0;
        int idx = 0;
        for (byte[] b : bs) {
            len += b.length;
        }
        byte[] buffer = new byte[len];
        for (byte[] b : bs) {
            System.arraycopy(b, 0, buffer, idx, b.length);
            idx += b.length;
        }
        return buffer;
    }

    public static void main(String[] args) {
        long a = 123456L;
        byte[] b1 = BitConverter.getBytes(a);
        long b = BitConverter.toInt64(b1, 0);
    }
}

