/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UUIDUtil {
    private static final Logger logger = LoggerFactory.getLogger(UUIDUtil.class);
    private static final String ALGORITHM = "MD5";
    private static MessageDigest digest = null;
    private static final Pattern HexPattern = Pattern.compile("^[0-9a-fA-F]+$");
    private static final char[] UpperHexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] LowerHexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final UUID emptyID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private UUIDUtil() {
    }

    private static MessageDigest getDigest() {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance(ALGORITHM);
            }
            catch (NoSuchAlgorithmException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return digest;
    }

    public static String encrypt(String messageStr, boolean upper) {
        MessageDigest digest = UUIDUtil.getDigest();
        if (digest == null) {
            return null;
        }
        return UUIDUtil.byteArrayToHex(digest.digest(messageStr.getBytes()), upper);
    }

    public static String byteArrayToHex(byte[] byteArray, boolean upper) {
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            if (upper) {
                resultCharArray[index++] = UpperHexDigits[b >>> 4 & 0xF];
                resultCharArray[index++] = UpperHexDigits[b & 0xF];
                continue;
            }
            resultCharArray[index++] = LowerHexDigits[b >>> 4 & 0xF];
            resultCharArray[index++] = LowerHexDigits[b & 0xF];
        }
        return new String(resultCharArray);
    }

    public static UUID md5(String messageStr) {
        if (messageStr == null) {
            return null;
        }
        String guidStr = UUIDUtil.encrypt(messageStr, false);
        return UUIDUtil.fromGuid(guidStr);
    }

    public static UUID fromGuid(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String guidStr = UUIDUtil.byteArrayToHex(byteArray, false);
        return UUIDUtil.fromGuid(guidStr);
    }

    public static UUID fromString(String uuidStr) {
        return UUID.fromString(uuidStr);
    }

    public static UUID fromGuid(String guidStr) {
        if (guidStr == null) {
            return null;
        }
        StringBuffer uuidStr = new StringBuffer();
        uuidStr.append(guidStr.substring(0, 8)).append("-").append(guidStr.substring(8, 12)).append("-").append(guidStr.substring(12, 16)).append("-").append(guidStr.substring(16, 20)).append("-").append(guidStr.substring(20, guidStr.length()));
        return UUID.fromString(uuidStr.toString());
    }

    public static String toGuid(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return uuid.toString().replace("-", "").toUpperCase();
    }

    public static byte[] toBytes(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return UUIDUtil.hexToBytes(uuid.toString().replace("-", ""));
    }

    public static byte[] hexToBytes(String guidStr) {
        if (guidStr == null || guidStr.length() == 0) {
            return null;
        }
        Matcher matcher = HexPattern.matcher(guidStr);
        if (!matcher.matches()) {
            throw new RuntimeException("\u8f6c\u6362\u5341\u516d\u8fdb\u5236\u5b57\u7b26\u4e32\u683c\u5f0f\u9519\u8bef\uff1a\u3010" + guidStr + "\u3011");
        }
        guidStr = guidStr.toUpperCase();
        int count = guidStr.length() / 2;
        byte[] resultByteArray = new byte[count];
        for (int i = 0; i < count; ++i) {
            int indexStart = i * 2;
            resultByteArray[i] = (byte)(UUIDUtil.charToByte(guidStr.charAt(indexStart)) << 4 | UUIDUtil.charToByte(guidStr.charAt(indexStart + 1)));
        }
        return resultByteArray;
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
}

