/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.util;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static int ReadIntValue(InputStream mask0) throws IOException {
        int ret = 0;
        byte[] b = new byte[4];
        mask0.read(b, 0, 4);
        ret = Integer.parseInt(Utils.BytesToHexString(Utils.GetResverBytearr(b)));
        return ret;
    }

    public static String ReadStringValue(InputStream mask0, int size) throws IOException {
        String ret = null;
        byte[] b = new byte[size];
        mask0.read(b, 0, size);
        ret = Utils.BytesToHexString(Utils.GetResverBytearr(b));
        return ret;
    }

    public static int[] ReadArrayValue(InputStream mask0, int size) throws IOException {
        int[] ret = new int[size];
        byte[] b = null;
        for (int i = 0; i < size; ++i) {
            b = new byte[4];
            mask0.read(b, 0, 4);
            ret[i] = Integer.parseInt(Utils.BytesToHexString(Utils.GetResverBytearr(b)), 16);
        }
        return ret;
    }

    public static byte[] GetResverBytearr(byte[] source) {
        if (source == null) {
            return null;
        }
        byte[] ret = new byte[source.length];
        int i = source.length - 1;
        for (byte b : source) {
            ret[i--] = b;
        }
        return ret;
    }

    public static String BytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 0xFF;
            String hv = new Integer(v).toString();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static int[] DecodeLoad(InputStream mask0, int count, int intKey) throws IOException {
        int[] ret = new int[count];
        int ecx = 3;
        int ebx = intKey;
        int edx = 0;
        int eax = 0;
        int[] key = new int[]{0x3C3C3C3C, 0x3B3B3B3B, 0x67676767};
        ecx = count;
        int size = count * 4 * 3;
        int[] data = new int[size / 4];
        int idx = 0;
        byte[] b = new byte[4];
        for (int i = 0; i < 3; ++i) {
            edx = key[i];
            for (int s = 0; s < count; ++s) {
                mask0.read(b, 0, 4);
                eax = Integer.parseInt(Utils.BytesToHexString(Utils.GetResverBytearr(b)), 16);
                eax ^= ebx;
                ebx += 337850945;
                data[idx++] = eax ^= edx;
            }
        }
        for (int i_0 = 0; i_0 < count; ++i_0) {
            int i1 = data[i_0];
            int i2 = data[count + i_0];
            int i3 = data[2 * count + i_0];
            if (i1 == i2) {
                ret[i_0] = i1;
                continue;
            }
            if (i1 == i3) {
                ret[i_0] = i2;
                continue;
            }
            if (i2 != i3) continue;
            ret[i_0] = i3;
        }
        return ret;
    }

    public static boolean IsMergeTop(long data) {
        return (data & 0x20000000L) != 0L;
    }

    public static boolean IsMergeBottom(long data) {
        return (data & 0x10000000L) != 0L;
    }

    public static boolean IsMergeLeft(long data) {
        return (data & Integer.MIN_VALUE) != 0L;
    }

    public static boolean IsMergeRight(long data) {
        return (data & 0x40000000L) != 0L;
    }
}

