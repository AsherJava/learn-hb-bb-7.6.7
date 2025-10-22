/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SingleConvert {
    private static final String BASE64_CHARACTER = "Illegal base64 character";
    private static final char[] intToBase64 = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] base64ToInt = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
    public static final Charset utf8 = StandardCharsets.UTF_8;

    private SingleConvert() {
        throw new IllegalStateException("Utility class");
    }

    public static int toInt(String value) {
        return value != null && value.length() != 0 ? Integer.parseInt(value) : 0;
    }

    public static String fastString(char[] chars) {
        int charCount = chars.length;
        if (charCount == 0) {
            return "";
        }
        return new String(chars);
    }

    public static String bytesToBase64(byte[] a) {
        int aLen = a.length;
        if (aLen == 0) {
            return "";
        }
        int numFullGroups = aLen / 3;
        int numBytesInPartialGroup = aLen - 3 * numFullGroups;
        int resultLen = 4 * ((aLen + 2) / 3);
        char[] result = new char[resultLen];
        int inCursor = 0;
        int outCursor = 0;
        for (int i = 0; i < numFullGroups; ++i) {
            int byte0 = a[inCursor++] & 0xFF;
            int byte1 = a[inCursor++] & 0xFF;
            int byte2 = a[inCursor++] & 0xFF;
            result[outCursor++] = intToBase64[byte0 >>> 2];
            result[outCursor++] = intToBase64[byte0 << 4 & 0x3F | byte1 >>> 4];
            result[outCursor++] = intToBase64[byte1 << 2 & 0x3F | byte2 >>> 6];
            result[outCursor++] = intToBase64[byte2 & 0x3F];
        }
        if (numBytesInPartialGroup != 0) {
            int byte0 = a[inCursor++] & 0xFF;
            result[outCursor++] = intToBase64[byte0 >>> 2];
            if (numBytesInPartialGroup == 1) {
                result[outCursor++] = intToBase64[byte0 << 4 & 0x3F];
                result[outCursor++] = 61;
                result[outCursor] = 61;
            } else {
                int byte1 = a[inCursor] & 0xFF;
                result[outCursor++] = intToBase64[byte0 << 4 & 0x3F | byte1 >>> 4];
                result[outCursor++] = intToBase64[byte1 << 2 & 0x3F];
                result[outCursor] = 61;
            }
        }
        return SingleConvert.fastString(result);
    }

    public static int bytesToBase64(byte[] a, StringBuilder str) {
        int byte1;
        int byte0;
        int aLen = a.length;
        if (aLen == 0) {
            return 0;
        }
        int numFullGroups = aLen / 3;
        int resultLen = 4 * ((aLen + 2) / 3);
        str.ensureCapacity(str.length() + resultLen);
        int inCursor = 0;
        for (int i = 0; i < numFullGroups; ++i) {
            byte0 = a[inCursor++] & 0xFF;
            byte1 = a[inCursor++] & 0xFF;
            int byte2 = a[inCursor++] & 0xFF;
            str.append(intToBase64[byte0 >>> 2]);
            str.append(intToBase64[byte0 << 4 & 0x3F | byte1 >>> 4]);
            str.append(intToBase64[byte1 << 2 & 0x3F | byte2 >>> 6]);
            str.append(intToBase64[byte2 & 0x3F]);
        }
        int numBytesInPartialGroup = aLen - 3 * numFullGroups;
        if (numBytesInPartialGroup != 0) {
            byte0 = a[inCursor++] & 0xFF;
            str.append(intToBase64[byte0 >>> 2]);
            if (numBytesInPartialGroup == 1) {
                str.append(intToBase64[byte0 << 4 & 0x3F]);
                str.append('=');
                str.append('=');
            } else {
                byte1 = a[inCursor] & 0xFF;
                str.append(intToBase64[byte0 << 4 & 0x3F | byte1 >>> 4]);
                str.append(intToBase64[byte1 << 2 & 0x3F]);
                str.append('=');
            }
        }
        return resultLen;
    }

    public static byte[] base64ToBytes(CharSequence s) {
        int sLen = s.length();
        int numGroups = sLen / 4;
        if (4 * numGroups != sLen) {
            throw new IllegalArgumentException("String length must be a multiple of four.");
        }
        int missingBytesInLastGroup = 0;
        int numFullGroups = numGroups;
        if (sLen != 0) {
            if (s.charAt(sLen - 1) == '=') {
                ++missingBytesInLastGroup;
                --numFullGroups;
            }
            if (s.charAt(sLen - 2) == '=') {
                ++missingBytesInLastGroup;
            }
        }
        byte[] result = new byte[3 * numGroups - missingBytesInLastGroup];
        int inCursor = 0;
        int outCursor = 0;
        for (int i = 0; i < numFullGroups; ++i) {
            byte ch0 = base64ToInt[s.charAt(inCursor++)];
            byte ch1 = base64ToInt[s.charAt(inCursor++)];
            byte ch2 = base64ToInt[s.charAt(inCursor++)];
            byte ch3 = base64ToInt[s.charAt(inCursor++)];
            if (ch0 < 0 || ch1 < 0 || ch2 < 0 || ch3 < 0) {
                throw new IllegalArgumentException(BASE64_CHARACTER);
            }
            result[outCursor++] = (byte)(ch0 << 2 | ch1 >>> 4);
            result[outCursor++] = (byte)(ch1 << 4 | ch2 >>> 2);
            result[outCursor++] = (byte)(ch2 << 6 | ch3);
        }
        if (missingBytesInLastGroup != 0) {
            byte ch0 = base64ToInt[s.charAt(inCursor++)];
            byte ch1 = base64ToInt[s.charAt(inCursor++)];
            if (ch0 < 0 || ch1 < 0) {
                throw new IllegalArgumentException(BASE64_CHARACTER);
            }
            result[outCursor++] = (byte)(ch0 << 2 | ch1 >>> 4);
            if (missingBytesInLastGroup == 1) {
                byte ch2 = base64ToInt[s.charAt(inCursor)];
                if (ch2 < 0) {
                    throw new IllegalArgumentException(BASE64_CHARACTER);
                }
                result[outCursor] = (byte)(ch1 << 4 | ch2 >>> 2);
            }
        }
        return result;
    }
}

