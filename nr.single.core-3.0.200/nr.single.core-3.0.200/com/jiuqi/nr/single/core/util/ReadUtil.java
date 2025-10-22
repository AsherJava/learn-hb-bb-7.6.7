/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReadUtil.class);

    public static int readIntValue(InputStream is) throws IOException {
        int ret = 0;
        byte[] b = new byte[4];
        is.read(b, 0, 4);
        ret = Integer.parseInt(ReadUtil.bytesToHexString(ReadUtil.getResverBytearr(b)), 16);
        return ret;
    }

    public static long readLongValue(InputStream is) throws IOException {
        long ret = 0L;
        byte[] b = new byte[4];
        is.read(b, 0, 4);
        ret = Long.parseLong(ReadUtil.bytesToHexString(ReadUtil.getResverBytearr(b)), 16);
        if (ret > Integer.MAX_VALUE) {
            ret -= Integer.MAX_VALUE;
            ret -= Integer.MAX_VALUE;
        }
        return ret;
    }

    public static long readLongValue2(InputStream is) throws IOException {
        long ret = 0L;
        byte[] b = new byte[8];
        is.read(b, 0, 8);
        ret = Long.parseLong(ReadUtil.bytesToHexString(ReadUtil.getResverBytearr(b)), 16);
        return ret;
    }

    public static int readSmallIntValue(InputStream is) throws IOException {
        int ret = 0;
        byte[] b = new byte[2];
        is.read(b, 0, 2);
        ret = Integer.parseInt(ReadUtil.bytesToHexString(ReadUtil.getResverBytearr(b)), 16);
        return ret;
    }

    public static String readStringValue(InputStream is, int size) throws IOException {
        String ret = null;
        byte[] b = new byte[size];
        is.read(b, 0, size);
        ret = new String(b);
        return ret;
    }

    public static Byte readByteValue(InputStream is) throws IOException {
        byte[] b = new byte[1];
        is.read(b, 0, 1);
        return b[0];
    }

    public static boolean readBoolValue(InputStream is) throws IOException {
        byte[] b = new byte[1];
        is.read(b, 0, 1);
        return b[0] == 1;
    }

    public static int[] readArrayValue(InputStream is, int size) throws IOException {
        int[] ret = new int[size];
        byte[] b = null;
        for (int i = 0; i < size; ++i) {
            b = new byte[4];
            is.read(b, 0, 4);
            ret[i] = Integer.parseInt(ReadUtil.bytesToHexString(ReadUtil.getResverBytearr(b)), 16);
        }
        return ret;
    }

    public static int[] decodeLoad(InputStream is, int count, int intKey) {
        int i;
        int[] ret = new int[count];
        int ebx = intKey;
        int edx = 0;
        int eax = 0;
        int[] key = new int[]{0x3C3C3C3C, 0x3B3B3B3B, 0x67676767};
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
                eax = Integer.parseInt(ReadUtil.bytesToHexString(ReadUtil.getResverBytearr(b)), 16);
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

    private static byte[] getResverBytearr(byte[] source) {
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

    private static String bytesToHexString(byte[] src) {
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

    public static void skipStream(InputStream is, int skipLength) throws IOException {
        if (is == null) {
            return;
        }
        byte[] b = new byte[skipLength];
        is.read(b);
    }

    public static String readStreams(InputStream is) throws IOException {
        String ret = null;
        long size = ReadUtil.readLongValue(is);
        if (size > 0L && size < 0x1000000L) {
            ret = ReadUtil.readStringValue(is, (int)size);
        }
        return ret;
    }

    public static ByteArrayInputStream decompressData(InputStream is) {
        try {
            int size = ReadUtil.readIntValue(is);
            byte[] b = new byte[size];
            is.read(b, 0, size);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InflaterOutputStream zos = new InflaterOutputStream(bos);
            zos.write(b);
            zos.close();
            return new ByteArrayInputStream(bos.toByteArray());
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static void skipStream(InputStream is) throws IOException {
        int length = ReadUtil.readIntValue(is);
        if (length > 0) {
            ReadUtil.skipStream(is, length);
        }
    }
}

