/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.util;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nr.single.core.para.util.Util;
import java.io.IOException;
import java.util.ArrayList;

public class WriteUtil {
    public static void writeIntValue(Stream mask0, int value) throws StreamException {
        mask0.writeInt(value);
    }

    public static void readShortValue(Stream mask0, short value) throws StreamException {
        mask0.writeShort(value);
    }

    public static int getInt(byte[] buf) {
        int result = 0;
        for (int i = buf.length - 1; i >= 0; --i) {
            result <<= 8;
            result |= buf[i] & 0xFF;
        }
        return result;
    }

    public static byte[] getBuf(int value) {
        byte[] buf = new byte[4];
        int result = value;
        for (int i = 0; i <= buf.length - 1; ++i) {
            buf[i] = (byte)(result & 0xFF);
            result >>= 8;
        }
        return buf;
    }

    public static void readLongValue(Stream mask0, long value) throws StreamException {
        mask0.writeLong(value);
    }

    public static void writeFlagValue(Stream mask0, long value) throws StreamException {
        mask0.writeInt((int)value);
    }

    public static void writeLongValue2(Stream mask0, long value) throws StreamException {
        mask0.writeLong(value);
    }

    public static void writeSmallIntValue(Stream mask0, int value) throws StreamException {
        mask0.writeShort((short)value);
    }

    public static void writeStringValue(Stream mask0, int size, String value) throws StreamException {
        mask0.writeString(value);
    }

    public static void writeStringValue(Stream mask0, String value) throws StreamException {
        mask0.writeString(value);
    }

    public static String readEnumStringValue(Stream mask0, int size) throws StreamException {
        String ret = mask0.readString(size);
        int index = ret.indexOf(0);
        if (index > 0) {
            ret = ret.substring(0, index);
        }
        return ret;
    }

    public static String getEnumString(byte[] result) {
        String s = Util.getString(result);
        int index = s.indexOf(0);
        if (index > 0) {
            s = s.substring(0, index);
        }
        return s;
    }

    public static String getString(byte[] result) {
        String s = Util.getString(result);
        s = s.replace("\u0000", "").trim();
        return s;
    }

    public static String getFullString(byte[] result) {
        String s = Util.getString(result);
        return s;
    }

    public static String getFullString(byte[] result, boolean useTrim) {
        String s = Util.getString(result, useTrim);
        return s;
    }

    public static void writeByteValue(Stream mask0, byte value) throws StreamException {
        mask0.write(value);
    }

    public static int[] readArrayValue(Stream mask0, int size) throws StreamException {
        int[] ret = new int[size];
        byte[] b = null;
        for (int i = 0; i < size; ++i) {
            b = new byte[4];
            mask0.read(b, 0, 4);
            ret[i] = Integer.parseInt(WriteUtil.bytesToHexString(WriteUtil.getResverBytearr(b)), 16);
            ret[i] = WriteUtil.getInt(b);
        }
        return ret;
    }

    public static void writeArrayValue(Stream mask0, int[] nums) throws StreamException {
        Object b = null;
        for (int i = 0; i < nums.length; ++i) {
            int a = nums[i];
            byte[] buf = WriteUtil.getBuf(a);
            byte[] buf2 = WriteUtil.getResverBytearr(buf);
            mask0.write(buf2, 0, 4);
        }
    }

    public static void writeArrayValue2(Stream mask0, int[] nums) throws StreamException {
        Object b = null;
        for (int i = 0; i < nums.length; ++i) {
            int a = nums[i];
            mask0.writeInt(a);
        }
    }

    public static int[] decodeLoad(Stream mask0, int count, int intKey) throws StreamException {
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
                int v;
                mask0.read(b, 0, 4);
                eax = v = WriteUtil.getInt(b);
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
            String hv = new Integer(v).toString();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void writeSkipStream(Stream mask0, int skipLength) throws StreamException {
        if (mask0 == null) {
            return;
        }
        byte[] b = new byte[skipLength];
        mask0.write(b, 0, b.length);
    }

    public static void writeSkipStream(Stream mask0, long skipLength) throws StreamException {
        WriteUtil.writeSkipStream(mask0, (int)skipLength);
    }

    public static void writeStreams(Stream mask0, String value) throws StreamException {
        Object ret = null;
        long size = value.length();
        WriteUtil.writeIntValue(mask0, (int)size);
        if (size > 0L && size < 0x1000000L) {
            WriteUtil.writeStringValue(mask0, (int)size, value);
        }
    }

    public static Stream compressData(Stream mask0) throws StreamException {
        int size = ReadUtil.readIntValue(mask0);
        byte[] b = new byte[size];
        mask0.read(b, 0, size);
        MemStream bos = new MemStream();
        bos.write(b, 0, b.length);
        return bos;
    }

    public static byte decodeLoad1(Stream Source, int[] Denst, int IntCount, int SecretKey) throws IOException, StreamException {
        int i;
        int Key11 = 1012811172;
        long Key12 = -2058972074L;
        long Key13 = 1603099228L;
        byte Result = 0;
        if (IntCount <= 0 || Denst == null || Denst.length != 2 || Source == null) {
            return Result;
        }
        int SecretKey2 = SecretKey;
        int DataLen = IntCount * 4 * 3;
        int[] Data = new int[6];
        int[] dData = new int[6];
        byte[] b = new byte[DataLen];
        Source.read(b, 0, DataLen);
        int offset = 0;
        for (int i2 = 0; i2 < 6; ++i2) {
            byte[] b4 = new byte[4];
            for (int j = offset; j < offset + 4; ++j) {
                b4[j - offset] = b[offset];
            }
            Data[i2] = Util.getInt(b4);
            offset += 4;
        }
        ArrayList<Long> KeyList = new ArrayList<Long>();
        KeyList.add(KeyList.size(), Long.valueOf(Key11));
        KeyList.add(KeyList.size(), Key12);
        KeyList.add(KeyList.size(), Key12);
        for (i = 0; i < KeyList.size(); ++i) {
            long akey = (Long)KeyList.get(i);
            for (int j = 0; j < 2; ++j) {
                int aValue = Data[i * 2 + j];
                aValue ^= SecretKey2;
                dData[i * 2 + j] = aValue = (int)((long)aValue ^ akey);
                SecretKey2 += 337850945;
            }
        }
        for (i = 0; i < IntCount; ++i) {
            int I1 = dData[i];
            int I2 = dData[IntCount + i];
            int I3 = dData[2 * IntCount + i];
            if (I1 != I2 || I2 != I3) {
                Result = (byte)(Result | 1);
            }
            if (I1 == I2) {
                Denst[i] = I1;
                continue;
            }
            if (I1 == I3) {
                Denst[i] = I2;
                continue;
            }
            if (I2 == I3) {
                Denst[i] = I3;
                continue;
            }
            Result = (byte)(Result | 2);
        }
        return Result;
    }
}

