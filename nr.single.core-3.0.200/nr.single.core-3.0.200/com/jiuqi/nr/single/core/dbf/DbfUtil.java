/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.dbf;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.util.BitConverter;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DbfUtil {
    public static final Date NULL_DATE = new Date(1899, 12, 30);
    public static final Date INITDATE = new Date(-1898, -11, -28);
    public static final Date INITLASTDATE = new Date(0, 0, -1);

    public static Date toDate(byte[] buf, String encoding) throws DbfException {
        if (buf.length != 8) {
            throw new DbfException("date array length must be 8.");
        }
        try {
            String dateStr = new String(buf, encoding);
            if (dateStr.length() < 8) {
                return NULL_DATE;
            }
            int year = Integer.parseInt(dateStr.substring(0, 4));
            int month = Integer.parseInt(dateStr.substring(4, 6));
            int day = Integer.parseInt(dateStr.substring(6, 8));
            return new Date(year, month, day);
        }
        catch (UnsupportedEncodingException e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    public static byte[] formDate(Date date, int Length, String encoding) throws DbfException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String datestr = sdf.format(date);
            return datestr.getBytes(encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    public static Date ToTime(byte[] buf) throws DbfException {
        if (buf.length != 8) {
            throw new DbfException("time array length must be 8.");
        }
        try {
            byte[] tmp = DbfUtil.copySubBytes(buf, 0, 4L);
            int days = BitConverter.toInt32(tmp, 0);
            tmp = DbfUtil.copySubBytes(buf, 4, 4L);
            int milliSeconds = BitConverter.toInt32(tmp, 0);
            if (days == 0 && milliSeconds == 0) {
                return NULL_DATE;
            }
            int seconds = milliSeconds / 1000;
            int milli = milliSeconds % 1000;
            if (milli > 0) {
                ++seconds;
            }
            Date date = INITDATE;
            date = new Date(date.getTime() + (long)(days - 1721426) * 24L * 60L * 60L * 1000L);
            date = new Date(date.getTime() + (long)(seconds * 1000));
            return date;
        }
        catch (Exception e) {
            return new Date(0L);
        }
    }

    public static byte[] formTime(Date time, int length) {
        byte[] ra = new byte[length];
        try {
            TimeZone shZone = TimeZone.getTimeZone("Asia/Shanghai");
            boolean hasXiaLingShi = shZone != null && shZone.inDaylightTime(time);
            Date date = INITDATE;
            long ts = time.getTime() - date.getTime();
            int days = (int)(ts / 86400000L) + 1721426;
            date = new Date(date.getTime() + (long)(days - 1721426) * 24L * 60L * 60L * 1000L);
            long ts2 = time.getTime() - date.getTime();
            int seconds = (int)(ts2 / 1000L);
            if (hasXiaLingShi && seconds == 82800) {
                ++days;
                seconds = 0;
            }
            byte[] tmp = BitConverter.getBytes(days);
            DbfUtil.wirteSubBytes(ra, 0, 4L, tmp);
            int milli = 0;
            int milliSeconds = seconds * 1000 + milli;
            byte[] tmp2 = BitConverter.getBytes(milliSeconds);
            DbfUtil.wirteSubBytes(ra, 4, 4L, tmp2);
            return ra;
        }
        catch (Exception e) {
            return ra;
        }
    }

    public static byte[] copySubBytes(byte[] buf, int startIndex, long length) throws DbfException {
        return DbfUtil.copySubBytes(buf, startIndex, length, null);
    }

    public static byte[] copySubBytes(byte[] buf, int startIndex, long length, byte[] target) throws DbfException {
        if (startIndex >= buf.length) {
            throw new DbfException("startIndex");
        }
        if (length == 0L) {
            throw new DbfException("length must be great than 0.");
        }
        if (length > (long)(buf.length - startIndex)) {
            length = (long)(buf.length - startIndex) + 0L;
        }
        if (target == null) {
            target = new byte[(int)length];
        }
        System.arraycopy(buf, startIndex, target, 0, (int)length);
        return target;
    }

    public static void wirteSubBytes(byte[] buf, int startIndex, long length, byte[] sbuf) throws DbfException {
        if (startIndex >= buf.length) {
            throw new DbfException("startIndex");
        }
        if (length == 0L) {
            throw new DbfException("length must be great than 0.");
        }
        if (length > (long)(buf.length - startIndex)) {
            length = (long)(buf.length - startIndex) + 0L;
        }
        if (length > (long)sbuf.length) {
            length = sbuf.length;
        }
        System.arraycopy(sbuf, 0, buf, startIndex, (int)length);
    }

    public static String getEncoding(String encodingName) {
        if (StringUtils.isEmpty((String)encodingName)) {
            return "GB2312";
        }
        if (encodingName.equalsIgnoreCase("GB2313")) {
            return "GB2312";
        }
        if (encodingName.equalsIgnoreCase("UNICODE")) {
            return "UNICODE";
        }
        if (encodingName.equalsIgnoreCase("UTF8")) {
            return "UTF8";
        }
        if (encodingName.equalsIgnoreCase("UTF7")) {
            return "UTF7";
        }
        if (encodingName.equalsIgnoreCase("UTF32")) {
            return "UTF32";
        }
        if (encodingName.equalsIgnoreCase("ASCII")) {
            return "ASCII";
        }
        return "GB2312";
    }

    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(SinglePathUtil.normalize(strFile));
            if (!f.exists()) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}

