/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static int readIntValue(BufferedInputStream is) throws IOException {
        int ret = 0;
        byte[] b = new byte[4];
        is.read(b, 0, 4);
        ret = Integer.parseInt(StreamUtil.bytesToHexString(StreamUtil.getResverBytearr(b)), 16);
        return ret;
    }

    public static String readStringValue(BufferedInputStream is, int size) throws IOException {
        String ret = null;
        byte[] b = new byte[size];
        is.read(b, 0, size);
        ret = new String(b);
        return ret;
    }

    public static int[] readArrayValue(BufferedInputStream is, int size) throws IOException {
        int[] ret = new int[size];
        byte[] b = null;
        for (int i = 0; i < size; ++i) {
            b = new byte[4];
            is.read(b, 0, 4);
            ret[i] = Integer.parseInt(StreamUtil.bytesToHexString(StreamUtil.getResverBytearr(b)), 16);
        }
        return ret;
    }

    public static byte[] getResverBytearr(byte[] source) {
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

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static int[] decodeLoad(BufferedInputStream is, int count, int intKey) {
        int i;
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
        for (i = 0; i < 3; ++i) {
            edx = key[i];
            for (int s = 0; s < count; ++s) {
                try {
                    is.read(b, 0, 4);
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                eax = Integer.parseInt(StreamUtil.bytesToHexString(StreamUtil.getResverBytearr(b)), 16);
                eax ^= ebx;
                ebx += 337850945;
                data[idx++] = eax ^= edx;
            }
        }
        for (i = 0; i < count; ++i) {
            int i1 = data[i];
            int i2 = data[count + i];
            int i3 = data[2 * count + i];
            if (i1 == i2) {
                ret[i] = i1;
                continue;
            }
            if (i1 == i3) {
                ret[i] = i2;
                continue;
            }
            if (i2 != i3) continue;
            ret[i] = i3;
        }
        return ret;
    }

    public static boolean isMergeTop(long data) {
        return (data & 0x20000000L) != 0L;
    }

    public static boolean isMergeBottom(long data) {
        return (data & 0x10000000L) != 0L;
    }

    public static boolean isMergeLeft(long data) {
        return (data & Integer.MIN_VALUE) != 0L;
    }

    public static boolean isMergeRight(long data) {
        return (data & 0x40000000L) != 0L;
    }

    public static String getParaDir(String dirName) {
        return dirName + "PARA" + File.separator;
    }
}

