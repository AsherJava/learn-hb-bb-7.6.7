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
import com.jiuqi.nr.single.core.para.util.Util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.InflaterInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReadUtil.class);

    public static int readIntValue(Stream mask0) throws StreamException {
        return mask0.readInt();
    }

    public static short readShortValue(Stream mask0) throws StreamException {
        return mask0.readShort();
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

    public static long readLongValue(Stream mask0) throws StreamException {
        return mask0.readLong();
    }

    public static long readFlagValue(Stream mask0) throws StreamException {
        return mask0.readInt();
    }

    public static long readLongValue2(Stream mask0) throws StreamException {
        return mask0.readLong();
    }

    public static int readSmallIntValue(Stream mask0) throws StreamException {
        return mask0.readShort();
    }

    public static String readStringValue(Stream mask0, int size) throws StreamException {
        return mask0.readString(size);
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

    public static byte[] getStringBytes(String result) {
        byte[] s = Util.getBytes(result);
        return s;
    }

    public static byte readByteValue(Stream mask0) throws StreamException {
        return mask0.read();
    }

    public static int[] readArrayValue(Stream mask0, int size) throws StreamException {
        int[] ret = new int[size];
        byte[] b = null;
        for (int i = 0; i < size; ++i) {
            b = new byte[4];
            mask0.read(b, 0, 4);
            ret[i] = Integer.parseInt(ReadUtil.bytesToHexString(ReadUtil.getResverBytearr(b)), 16);
            ret[i] = ReadUtil.getInt(b);
        }
        return ret;
    }

    public static int[] readArrayValue2(Stream mask0, int size) throws StreamException {
        int[] ret = new int[size];
        for (int i = 0; i < size; ++i) {
            ret[i] = mask0.readInt();
        }
        return ret;
    }

    public static void writeArrayValue(Stream mask0, int[] nums) throws StreamException {
        Object b = null;
        for (int i = 0; i < nums.length; ++i) {
            int a = nums[i];
            byte[] buf = ReadUtil.getBuf(a);
            byte[] buf2 = ReadUtil.getResverBytearr(buf);
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
                eax = v = ReadUtil.getInt(b);
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

    public static void skipStream(Stream mask0, int skipLength) throws StreamException {
        if (mask0 == null) {
            return;
        }
        byte[] b = new byte[skipLength];
        mask0.read(b, 0, b.length);
    }

    public static void skipStream(Stream mask0, long skipLength) throws StreamException {
        ReadUtil.skipStream(mask0, (int)skipLength);
    }

    public static void skipStream(Stream mask0) throws StreamException {
        int length = ReadUtil.readIntValue(mask0);
        if (length > 0) {
            ReadUtil.skipStream(mask0, length);
        }
    }

    public static void skipStreamFromBegin(Stream mask0, int skipLength) throws StreamException {
        mask0.seek(0L, 0);
        ReadUtil.skipStream(mask0, skipLength);
    }

    public static String readStreams(Stream mask0) throws StreamException {
        String ret = null;
        long size = ReadUtil.readIntValue(mask0);
        if (size > 0L && size < 0x1000000L) {
            ret = ReadUtil.readStringValue(mask0, (int)size);
        }
        return ret;
    }

    public static String StreamReadString(Stream mask0) throws StreamException {
        String ret = null;
        byte b = mask0.read();
        short size = b;
        if (size < 0) {
            size = (short)(size + 256);
        }
        if (size > 0 && size < 0x1000000) {
            ret = ReadUtil.readStringValue(mask0, size);
        }
        return ret;
    }

    public static Stream decompressData(Stream mask0) throws StreamException {
        int size = ReadUtil.readIntValue(mask0);
        byte[] b = new byte[size];
        mask0.read(b, 0, size);
        MemStream bos = new MemStream();
        bos.write(b, 0, b.length);
        return bos;
    }

    public static void decompress2(Stream mask0, MemStream bos) throws StreamException {
        ByteArrayInputStream aaa = new ByteArrayInputStream(mask0.getBytes());
        ByteArrayOutputStream bbb = new ByteArrayOutputStream();
        ReadUtil.decompress(aaa, bbb);
        bos.write(bbb.toByteArray(), 0, (int)bos.getSize());
    }

    public static byte[] decompress(InputStream is, OutputStream os) {
        try (InflaterInputStream iis = new InflaterInputStream(is);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(iis);
             BufferedOutputStream o = new BufferedOutputStream(os);){
            int i = 1024;
            byte[] buf = new byte[i];
            while ((i = bufferedInputStream.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static byte decodeLoad1(Stream Source, int[] Denst, int IntCount, int SecretKey) throws IOException, StreamException {
        int i;
        int Key11 = 1012811172;
        long Key12 = -2058972074L;
        long Key13 = 1603099228L;
        byte Result = 0;
        if (IntCount <= 0 || Denst == null || Denst.length != IntCount || Source == null) {
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
                SecretKey2 += -981757112;
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

    public static byte decodeLoad2(Stream Source, int[] Denst, int IntCount, int SecretKey) throws IOException, StreamException {
        int i;
        long Key11 = 0x3C3C3C3CL;
        long Key12 = 0x3B3B3B3BL;
        long Key13 = 0x67676767L;
        byte Result = 0;
        if (IntCount <= 0 || Denst == null || Denst.length != IntCount || Source == null) {
            return Result;
        }
        long SecretKey2 = SecretKey;
        int DataLen = IntCount * 4 * 3;
        long[] Data = new long[6];
        long[] dData = new long[6];
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
        KeyList.add(KeyList.size(), Key11);
        KeyList.add(KeyList.size(), Key12);
        KeyList.add(KeyList.size(), Key13);
        for (i = 0; i < KeyList.size(); ++i) {
            long akey = (Long)KeyList.get(i);
            for (int j = 0; j < 2; ++j) {
                long aValue = Data[i * 2 + j];
                aValue ^= SecretKey2;
                dData[i * 2 + j] = aValue = (long)((int)(aValue ^ akey));
                SecretKey2 += 337850945L;
            }
        }
        for (i = 0; i < IntCount; ++i) {
            long I1 = dData[i];
            long I2 = dData[IntCount + i];
            long I3 = dData[2 * IntCount + i];
            if (I1 != I2 || I2 != I3) {
                Result = (byte)(Result | 1);
            }
            if (I1 == I2) {
                Denst[i] = (int)I1;
                continue;
            }
            if (I1 == I3) {
                Denst[i] = (int)I2;
                continue;
            }
            if (I2 == I3) {
                Denst[i] = (int)I3;
                continue;
            }
            Result = (byte)(Result | 2);
        }
        return Result;
    }
}

