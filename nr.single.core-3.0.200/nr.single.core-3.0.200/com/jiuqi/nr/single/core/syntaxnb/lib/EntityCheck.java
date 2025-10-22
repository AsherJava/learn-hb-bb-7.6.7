/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.syntaxnb.lib;

import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;

public class EntityCheck {
    public static final String ADDRESS = "00x11x12x13x14x15x21x22x23x31x32x33x34x35x36x37x41x42x43x44x45x46x50x51x52x53x54x61x62x63x64x65x71x81x82";
    public static final char[] DENGJI_GUANLI_CODE_CHARS = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'Y'};
    public static final String[] DENGJI_GUANLI_NAME_CHARS = new String[]{"\u673a\u6784\u7f16\u5236", "\u5916\u4ea4", "\u6559\u80b2", "\u516c\u5b89", "\u6c11\u653f", "\u53f8\u6cd5", "\u4ea4\u901a\u8fd0\u8f93", "\u6587\u5316", "\u5de5\u5546", "\u65c5\u6e38\u5c40", "\u5b97\u6559\u4e8b\u52a1\u7ba1\u7406", "\u5168\u56fd\u603b\u5de5\u4f1a", "\u4eba\u6c11\u89e3\u653e\u519b\u603b\u540e\u52e4\u90e8", "\u7701\u7ea7\u4eba\u6c11\u653f\u5e9c", "\u5730\u3001\u5e02\uff08\u793e\u533a\uff09\u7ea7\u4eba\u6c11\u653f\u5e9c", "\u533a\u3001\u53bf\u7ea7\u4eba\u6c11\u653f\u5e9c", "\u5176\u4ed6"};
    public static final int[] WI = new int[]{1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};

    public static int getNum(char c) {
        int result = 0;
        if (c >= '0' && c <= '9') {
            result = c - 48;
        } else if (c == '#') {
            result = 36;
        } else {
            char c2 = Character.toUpperCase(c);
            result = c2 - 55;
        }
        return result;
    }

    public static char checkIdc(String idStr) {
        char result = '\u0000';
        if (StringUtils.isEmpty((String)idStr)) {
            return result;
        }
        if (idStr.length() != 8) {
            return result;
        }
        for (int i = 0; i < idStr.length(); ++i) {
            char c = idStr.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '#') continue;
            return result;
        }
        int v = 11 - (EntityCheck.getNum(idStr.charAt(0)) * 3 + EntityCheck.getNum(idStr.charAt(1)) * 7 + EntityCheck.getNum(idStr.charAt(2)) * 9 + EntityCheck.getNum(idStr.charAt(3)) * 10 + EntityCheck.getNum(idStr.charAt(4)) * 5 + EntityCheck.getNum(idStr.charAt(5)) * 8 + EntityCheck.getNum(idStr.charAt(6)) * 4 + EntityCheck.getNum(idStr.charAt(7)) * 2) % 11;
        switch (v) {
            case 10: {
                result = 'X';
                break;
            }
            case 11: {
                result = '0';
                break;
            }
            default: {
                result = (char)(v + 48);
            }
        }
        return result;
    }

    public static boolean checkCode(String unitCode) {
        boolean result = false;
        if (StringUtils.isEmpty((String)unitCode)) {
            return result;
        }
        if (StringUtils.isEmpty((String)(unitCode = unitCode.trim()))) {
            return result;
        }
        if (unitCode.length() == 6) {
            result = true;
            return result;
        }
        if (unitCode.length() == 9) {
            result = EntityCheck.checkIdc(unitCode) == unitCode.charAt(8);
        } else if (unitCode.length() == 18) {
            boolean ifHas = false;
            for (int i = 0; i < DENGJI_GUANLI_CODE_CHARS.length; ++i) {
                if (unitCode.charAt(0) != DENGJI_GUANLI_CODE_CHARS[i]) continue;
                ifHas = true;
                break;
            }
            if (!ifHas) {
                return result;
            }
            String regionCode = unitCode.substring(2, 8);
            String subCode = unitCode.substring(8, 16);
            char code = EntityCheck.checkIdc(subCode);
            if (code != unitCode.charAt(16)) {
                return result;
            }
            subCode = unitCode.substring(0, 17);
            code = EntityCheck.getBusinessCode(subCode);
            if (code != unitCode.charAt(17)) {
                return result;
            }
            result = true;
        }
        return result;
    }

    public static char getBusinessCode(String businessCode) {
        char result = '\u0000';
        if (StringUtils.isEmpty((String)businessCode)) {
            return result;
        }
        if (businessCode.length() != 17) {
            return result;
        }
        String baseCode = "0123456789ABCDEFGHJKLMNPQRTUWXYIOZSV#";
        HashMap<String, Integer> aList = new HashMap<String, Integer>();
        for (int i = 0; i < baseCode.length(); ++i) {
            aList.put(baseCode.substring(i, i + 1), i);
        }
        int sum = 0;
        for (int i = 0; i < 17; ++i) {
            String key = String.valueOf(businessCode.charAt(i));
            if (!baseCode.contains(key)) {
                return result;
            }
            int num = 0;
            if (aList.containsKey(key)) {
                num = (Integer)aList.get(key);
            }
            sum += num * WI[i];
        }
        int value = 31 - sum % 31;
        if (value >= 31) {
            value = 0;
        }
        result = baseCode.charAt(value);
        return result;
    }

    public static boolean isBusinessNo(String businessCode) {
        boolean result = false;
        if (StringUtils.isEmpty((String)businessCode)) {
            return result;
        }
        if (businessCode.length() != 18) {
            return result;
        }
        String unitCode = businessCode.trim();
        String subCode = unitCode.substring(0, 17);
        char code = EntityCheck.getBusinessCode(subCode);
        result = code == unitCode.charAt(17);
        return result;
    }
}

