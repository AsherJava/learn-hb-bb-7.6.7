/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definitionext.taskExtConfig.model;

import java.io.BufferedInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskExtTransUtils {
    private static final Logger logger = LoggerFactory.getLogger(TaskExtTransUtils.class);

    public String transClob(Clob c) {
        try {
            return c.getSubString(1L, (int)c.length());
        }
        catch (Exception e) {
            return null;
        }
    }

    public Clob transClob(String s) {
        try {
            return new SerialClob(s.toCharArray());
        }
        catch (Exception e) {
            return null;
        }
    }

    public String transUUID(UUID u) {
        return u.toString();
    }

    public UUID transUUID(String u) {
        return UUID.fromString(u);
    }

    public Date transTimeStamp(Timestamp time) {
        return new Date(time.getTime());
    }

    public Timestamp transTimeStamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] transBlob(Blob blob) {
        try (BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());){
            byte[] bytes = new byte[(int)blob.length()];
            int len = bytes.length;
            int read = 0;
            for (int offset = 0; offset < len && (read = is.read(bytes, offset, len - offset)) >= 0; offset += read) {
            }
            is.close();
            byte[] byArray = bytes;
            return byArray;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Blob transBlob(byte[] bytes) {
        SerialBlob value = null;
        try {
            value = new SerialBlob(bytes);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return value;
    }

    public static Blob transBlobToStr(String strValue) {
        SerialBlob value = null;
        try {
            value = new SerialBlob(strValue.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return value;
    }

    public static String transBytes(byte[] bytes) {
        try {
            return new String(bytes, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return "";
        }
    }

    public static byte[] transBytes(String value) {
        try {
            return value.getBytes(StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return new byte[0];
        }
    }

    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();
        for (int i = 0; i < bs.length; ++i) {
            int bit = (bs[i] & 0xF0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0xF;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String transBlobToStr(Blob blob) {
        try (BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());){
            byte[] bytes = new byte[(int)blob.length()];
            int len = bytes.length;
            int read = 0;
            for (int offset = 0; offset < len && (read = is.read(bytes, offset, len - offset)) >= 0; offset += read) {
            }
            is.close();
            String string = new String(bytes, StandardCharsets.UTF_8);
            return string;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < bytes.length; ++i) {
            int n = str.indexOf(hexs[2 * i]) * 16;
            bytes[i] = (byte)((n += str.indexOf(hexs[2 * i + 1])) & 0xFF);
        }
        return new String(bytes);
    }
}

