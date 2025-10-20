/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class CheckFileTypeUtil {
    public static final HashMap<String, String> mFileTypes = new HashMap();

    public static String getFileType(String filePath) {
        System.out.println(CheckFileTypeUtil.getFileHeader(filePath));
        return mFileTypes.get(CheckFileTypeUtil.getFileHeader(filePath));
    }

    public static String getFileTypeByFileInputStream(FileInputStream is) {
        return mFileTypes.get(CheckFileTypeUtil.getFileHeaderByFileInputStream(is));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = CheckFileTypeUtil.bytesToHexString(b);
        }
        catch (Exception exception) {
        }
        finally {
            if (null != is) {
                try {
                    is.close();
                }
                catch (IOException iOException) {}
            }
        }
        return value;
    }

    public static String getFileHeaderByFileInputStream(InputStream is) {
        String value = null;
        try {
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = CheckFileTypeUtil.bytesToHexString(b);
        }
        catch (Exception exception) {
        }
        finally {
            if (null != is) {
                try {
                    is.close();
                }
                catch (IOException iOException) {}
            }
        }
        return value;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            String hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    static {
        mFileTypes.put("504B0304", "zip");
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("49492A00", "tif");
        mFileTypes.put("424D", "bmp");
        mFileTypes.put("41433130", "dwg");
        mFileTypes.put("38425053", "psd");
        mFileTypes.put("7B5C727466", "rtf");
        mFileTypes.put("3C3F786D6C", "xml");
        mFileTypes.put("68746D6C3E", "html");
        mFileTypes.put("44656C69766572792D646174653A", "eml");
        mFileTypes.put("D0CF11E0", "doc");
        mFileTypes.put("D0CF11E0", "xls");
        mFileTypes.put("5374616E64617264204A", "mdb");
        mFileTypes.put("252150532D41646F6265", "ps");
        mFileTypes.put("255044462D312E", "pdf");
        mFileTypes.put("504B0304", "docx");
        mFileTypes.put("504B0304", "xlsx");
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("4D546864", "mid");
        mFileTypes.put("1F8B08", "gz");
    }
}

