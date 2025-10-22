/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);
    public static final long DAYS_BETWEEN_18991230_AND_19700101 = 25569L;
    public static final long MILLISECONDS_PER_DAY = 86400000L;

    public static String ChangeExtension(String sFile, String newSuffix) {
        String fileName = sFile.substring(0, sFile.lastIndexOf("."));
        return fileName + newSuffix;
    }

    public static byte[] getBytes(InputStream is, int size) throws IOException {
        byte[] result = new byte[size];
        is.read(result, 0, size);
        return result;
    }

    public static ByteArrayOutputStream readFile(InputStream in) throws IOException {
        byte[] buf = new byte[1024];
        int pos = 0;
        int len = 0;
        len = in.read(buf, pos, 1024);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (len > 0) {
            bos.write(buf, 0, len);
            pos += len;
            len = in.read(buf, 0, 1024);
        }
        return bos;
    }

    public static void setBytes(OutputStream out, byte[] bStream, int size) throws IOException {
        out.write(bStream, 0, size);
    }

    public static double getDouble(InputStream is, int size) throws IOException {
        return Util.getDouble(Util.getBytes(is, size));
    }

    public static void setDouble(OutputStream out, double d, int size) throws IOException {
        Util.setBytes(out, Util.setDouble(d, size), size);
    }

    public static double getDouble(InputStream is) throws IOException {
        return Util.getDouble(Util.getBytes(is, 8));
    }

    public static void setDouble(OutputStream out, double d) throws IOException {
        Util.setBytes(out, Util.setDouble(d, 8), 8);
    }

    public static double getDouble(byte[] buf) {
        long rl = Util.getLong(buf);
        double result = Double.longBitsToDouble(rl);
        return result;
    }

    public static byte[] setDouble(double d, int size) {
        byte[] result = new byte[size];
        long r1 = Double.doubleToLongBits(d);
        result = Util.setLong(r1, size);
        return result;
    }

    public static long getLong(InputStream is, int size) throws IOException {
        return Util.getLong(Util.getBytes(is, size));
    }

    public static void setLong(OutputStream out, long l, int size) throws IOException {
        Util.setBytes(out, Util.setLong(l, size), size);
    }

    public static long getLong(InputStream is) throws IOException {
        return Util.getLong(Util.getBytes(is, 8));
    }

    public static void setLong(OutputStream out, long l) throws IOException {
        Util.setBytes(out, Util.setLong(l, 8), 8);
    }

    public static long getLong(byte[] buf) {
        long result = 0L;
        for (int i = buf.length - 1; i >= 0; --i) {
            result <<= 8;
            result += (long)(buf[i] & 0xFF);
        }
        return result;
    }

    public static byte[] setLong(long l, int size) {
        byte[] result = new byte[size];
        for (int i = 0; i < size; ++i) {
            result[i] = (byte)(l & 0xFFL);
            l >>= 8;
        }
        return result;
    }

    public static int getInt(InputStream is, int size) throws IOException {
        return Util.getInt(Util.getBytes(is, size));
    }

    public static void setInt(OutputStream out, int i, int size) throws IOException {
        Util.setBytes(out, Util.setInt(i, size), size);
    }

    public static int getInt(InputStream is) throws IOException {
        return Util.getInt(is, 4);
    }

    public static void setInt(OutputStream out, int i) throws IOException {
        Util.setBytes(out, Util.setInt(i, 4), 4);
    }

    public static int getInt(byte[] buf) {
        int result = 0;
        for (int i = buf.length - 1; i >= 0; --i) {
            result <<= 8;
            result |= buf[i] & 0xFF;
        }
        return result;
    }

    public static byte[] setInt(int intager, int size) {
        byte[] result = new byte[size];
        for (int i = 0; i != size; ++i) {
            result[i] = (byte)(intager & 0xFF);
            intager >>= 8;
        }
        return result;
    }

    public static byte[] setInt(long intager, int size) {
        byte[] result = new byte[size];
        for (int i = 0; i != size; ++i) {
            result[i] = (byte)(intager & 0xFFFFL);
            intager >>= 8;
        }
        return result;
    }

    public static boolean getBool(InputStream is, int size) throws IOException {
        return Util.getBool(Util.getBytes(is, size));
    }

    public static void setBool(OutputStream out, boolean b, int size) throws IOException {
        Util.setBytes(out, Util.setBool(b, size), size);
    }

    public static boolean getBool(InputStream is) throws IOException {
        return Util.getBool(Util.getBytes(is, 1));
    }

    public static void getBool(OutputStream out, boolean b) throws IOException {
        Util.setBytes(out, Util.setBool(b, 1), 1);
    }

    public static boolean getBool(byte[] result) {
        return result[0] != 70;
    }

    public static byte[] setBool(boolean b, int size) {
        byte[] res = new byte[size];
        res[0] = b ? 84 : 70;
        return res;
    }

    public static byte[] setBoolBinary(boolean b, int size) {
        byte[] res = new byte[size];
        res[0] = b ? (byte)1 : 0;
        return res;
    }

    public static Object getCurrency(byte[] result) {
        return null;
    }

    public static Object getDateTime(byte[] result) {
        long res = Util.getLong(result);
        long daysSince1899 = 2415019L;
        res = res % daysSince1899 - 25569L;
        long cmpToEmpty = -22091616L;
        if ((cmpToEmpty *= 100000L) == (res *= 86400000L)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dat = new Date(res);
        String etime = format.format(dat);
        return etime;
    }

    public static byte[] setDateTime(Object obj) throws ParseException {
        String str = (String)obj;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (str == null || str.equals("")) {
            str = "1899-12-29";
        }
        Date dat = format.parse(str);
        return Util.setDateFromLong(dat.getTime());
    }

    public static byte[] setDateFromLong(long dateTime) {
        byte[] result = new byte[8];
        dateTime = dateTime / 86400000L + 1L;
        long daysSince1899 = 2415019L;
        result = Util.setLong(dateTime += 25569L + daysSince1899, 8);
        return result;
    }

    public static float getFloat(InputStream is, int size) throws IOException {
        return Util.getFloat(Util.getBytes(is, size));
    }

    public static void setFloat(OutputStream out, float fl, int size) throws IOException {
        Util.setBytes(out, Util.setFolat(fl, size), size);
    }

    public static float getFloat(InputStream is) throws IOException {
        return Util.getFloat(Util.getBytes(is, 4));
    }

    public static void setFloat(OutputStream out, float fl) throws IOException {
        Util.setBytes(out, Util.setFolat(fl, 4), 4);
    }

    public static float getFloat(byte[] result) throws IOException {
        return Float.intBitsToFloat(Util.getInt(result));
    }

    public static byte[] setFolat(float fl, int size) {
        int intager = Float.floatToIntBits(fl);
        return Util.setInt(intager, size);
    }

    public static Object getNumberic(byte[] result) {
        return null;
    }

    public static Object getDate(byte[] result) {
        return Util.getDateTime(result);
    }

    public static byte[] setDate(Object obj) throws ParseException {
        return Util.setDateTime(obj);
    }

    public static String getString(InputStream is, int size) throws IOException {
        return Util.getString(Util.getBytes(is, size));
    }

    public static void setString(OutputStream out, String str, int size) throws IOException {
        Util.setBytes(out, Util.setString(str, size), size);
    }

    public static byte[] getBytes(String result) {
        try {
            return result.getBytes("GBK");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String getString(byte[] result) {
        try {
            return new String(result, "GBK").trim();
        }
        catch (UnsupportedEncodingException e) {
            return new String(result).trim();
        }
    }

    public static String getString(byte[] result, boolean useTrim) {
        String code = "";
        try {
            code = new String(result, "GBK");
            if (useTrim) {
                code = code.trim();
            }
            return code;
        }
        catch (UnsupportedEncodingException e) {
            code = new String(result);
            if (useTrim) {
                code = code.trim();
            }
            return code;
        }
    }

    public static String getText(byte[] result) {
        try {
            return new String(result, "GBK").trim();
        }
        catch (UnsupportedEncodingException e) {
            return new String(result).trim();
        }
    }

    public static byte[] setString(String str, int size) {
        byte[] result = new byte[size];
        byte[] strByte = str.getBytes();
        int len = strByte.length;
        if (strByte.length > size) {
            len = size;
        }
        for (int i = 0; i != len; ++i) {
            result[i] = strByte[i];
        }
        return result;
    }

    public static byte[] setStringFillZero(String str, int size) {
        int i;
        byte[] result = new byte[size];
        byte[] strByte = str.getBytes();
        int len = strByte.length;
        if (strByte.length > size) {
            len = size;
        }
        for (i = 0; i != len; ++i) {
            result[i] = strByte[i];
        }
        for (i = len; i < size; ++i) {
            result[i] = 32;
        }
        return result;
    }

    public static int insertBytes(byte[] total, byte[] buffer, int startPos, int size) {
        int i = 0;
        if (startPos + size > total.length) {
            return 0;
        }
        for (i = 0; i != size; ++i) {
            total[i + startPos] = buffer[i];
        }
        return i;
    }

    public static void fillZero(byte[] buf, int totalLen) {
        for (int i = 0; i != totalLen; ++i) {
            buf[i] = 32;
        }
    }

    public static void fillChar(char[] buf, char defalut) {
        for (int i = 0; i != buf.length; ++i) {
            buf[i] = defalut;
        }
    }

    public static byte[] TranToBytes(byte[] buf) {
        byte[] abc = new byte[buf.length];
        for (int i = 0; i != buf.length; ++i) {
            abc[i] = buf[i];
        }
        return abc;
    }

    public static byte[] TranToBytes2(byte[] buf, byte[] abc) {
        for (int i = 0; i != buf.length; ++i) {
            abc[i] = Util.TranToByte2(buf[i]);
        }
        return abc;
    }

    public static void TranToSBytes2(byte[] abc, int[] buf) {
        for (int i = 0; i != buf.length; ++i) {
            abc[i] = Util.TranToSByte2(buf[i]);
        }
    }

    public static byte TranToByte(byte buf) {
        byte abc = buf;
        return abc;
    }

    public static byte TranToByte2(byte buf) {
        byte abc = 0;
        abc = buf < 0 ? (byte)(256 + buf) : buf;
        return abc;
    }

    public static byte TranToSByte2(int buf) {
        byte abc = 0;
        abc = buf > 127 ? (byte)(buf - 256) : (byte)buf;
        return abc;
    }

    public static int copyBytes(byte[] to, byte[] from, int startPos, int size) {
        int i = 0;
        int fromSize = from.length;
        for (i = 0; i + startPos < fromSize && i != size; ++i) {
            to[i] = from[i + startPos];
        }
        return i;
    }

    public static void putByteToOut(byte[] bytes, ByteArrayOutputStream out) {
        ByteArrayInputStream in = null;
        byte[] buffer = new byte[4096];
        in = new ByteArrayInputStream(bytes);
        try {
            int bytes_read;
            while ((bytes_read = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes_read);
            }
            ((InputStream)in).close();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static File transByteToFile(String name, byte[] data) throws Exception {
        File file;
        if (name.contains("\\")) {
            name = name.substring(name.lastIndexOf("\\") + 1, name.length());
        }
        if (!(file = new File(name)).exists() && !file.createNewFile()) {
            logger.info("\u521b\u5efa\u6587\u4ef6\u5931\u8d25\uff01");
        }
        try (FileOutputStream os = new FileOutputStream(file);){
            os.write(data);
        }
        return file;
    }

    public static int round(double numb) {
        int temp = (int)((numb - (double)((int)numb)) * 10.0);
        int result = (int)numb;
        if (temp > 5) {
            result = (int)(numb + 1.0);
        } else if (temp < 5) {
            result = (int)numb;
        } else {
            if (result < 0) {
                result = -result;
            }
            result = result % 2 == 1 ? (int)(numb + 1.0) : (int)numb;
        }
        return result;
    }

    public static void exportZipFile(String titleName, ByteArrayOutputStream outStream) {
    }

    public static String writeToFS(String folderName, String fileName, byte[] data) {
        return "";
    }
}

