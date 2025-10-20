/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.navigation.common;

import java.util.UUID;
import org.springframework.util.ObjectUtils;

public class UUIDUtils {
    private static final int h2b_A_10 = 55;
    private static final int h2b_a_10 = 87;
    private static final char[] LOWER_HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String EMPTY_UUID_STR = "00000000-0000-0000-0000-000000000000";

    public static String emptyUUIDStr() {
        return EMPTY_UUID_STR;
    }

    public static String newUUIDStr() {
        return UUIDUtils.toString36(UUID.randomUUID());
    }

    public static boolean isEmpty(String uuidStr) {
        return ObjectUtils.isEmpty(uuidStr) || EMPTY_UUID_STR.equals(uuidStr);
    }

    public static String toString36(UUID id) {
        char[] charArray = new char[36];
        long least = id.getLeastSignificantBits();
        int index = 35;
        for (int i = 15; i >= 8; --i) {
            byte b = (byte)least;
            charArray[index--] = UUIDUtils.parseLowerChar(b & 0xF);
            charArray[index--] = UUIDUtils.parseLowerChar(b >>> 4 & 0xF);
            least >>>= 8;
            if (i != 10) continue;
            charArray[index--] = 45;
        }
        charArray[index--] = 45;
        long most = id.getMostSignificantBits();
        for (int i = 7; i >= 0; --i) {
            byte b = (byte)most;
            charArray[index--] = UUIDUtils.parseLowerChar(b & 0xF);
            charArray[index--] = UUIDUtils.parseLowerChar(b >>> 4 & 0xF);
            most >>>= 8;
            if (i != 6 && i != 4) continue;
            charArray[index--] = 45;
        }
        return new String(charArray);
    }

    public static UUID fromString36(String id) {
        try {
            if (id == null || id.trim().length() == 0) {
                return null;
            }
            return UUIDUtils.parseUUIDStr(id);
        }
        catch (Exception e) {
            return null;
        }
    }

    private static char parseLowerChar(int val) {
        if (val < 0 || val > 15) {
            throw new IllegalArgumentException("\u5b58\u5728\u8981\u8f6c\u4e3a\u5341\u516d\u8fdb\u5236\u5b57\u7b26\u7684\u65e0\u6548\u6570\u503c\uff1a" + val);
        }
        return LOWER_HEX_DIGITS[val];
    }

    private static int parseChar(String s, int offset) throws RuntimeException, StringIndexOutOfBoundsException {
        char c = s.charAt(offset);
        if (c >= '0') {
            if (c <= '9') {
                return c - 48;
            }
            if (c >= 'A') {
                if (c <= 'F') {
                    return c - 55;
                }
                if (c >= 'a' && c <= 'f') {
                    return c - 87;
                }
            }
        }
        throw new RuntimeException("\u5728\u504f\u79fb\u91cf" + offset + "\u5904\u51fa\u73b0\u65e0\u6548\u7684\u5341\u516d\u8fdb\u5236\u5b57\u7b26'" + c + "'");
    }

    private static UUID parseUUIDStr(String uuidStr) {
        int i;
        int i2;
        long most = UUIDUtils.parseChar(uuidStr, 0);
        for (i2 = 1; i2 < 8; ++i2) {
            most = most << 4 | (long)UUIDUtils.parseChar(uuidStr, i2);
        }
        for (i2 = 9; i2 < 13; ++i2) {
            most = most << 4 | (long)UUIDUtils.parseChar(uuidStr, i2);
        }
        for (i2 = 14; i2 < 18; ++i2) {
            most = most << 4 | (long)UUIDUtils.parseChar(uuidStr, i2);
        }
        long least = UUIDUtils.parseChar(uuidStr, 19);
        for (i = 20; i < 23; ++i) {
            least = least << 4 | (long)UUIDUtils.parseChar(uuidStr, i);
        }
        for (i = 24; i < 36; ++i) {
            least = least << 4 | (long)UUIDUtils.parseChar(uuidStr, i);
        }
        return new UUID(most, least);
    }
}

