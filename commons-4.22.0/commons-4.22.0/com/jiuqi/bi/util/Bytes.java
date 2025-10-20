/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

public class Bytes {
    private Bytes() {
    }

    public static String bytesToHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder result = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; ++i) {
            int b = data[i];
            if (b < 0) {
                b += 256;
            }
            if (b < 16) {
                result.append('0');
            }
            result.append(Integer.toHexString(b));
        }
        return result.toString().toUpperCase();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null) {
            return null;
        }
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < result.length; ++i) {
            result[i] = Integer.decode("0x" + hexString.substring(2 * i, 2 * i + 2)).byteValue();
        }
        return result;
    }
}

