/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.syntax.common;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.syntax.common.SyntaxConsts;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SyntaxLib {
    public static BigDecimal myStrToBigDecimal(String str) {
        Double db = Double.valueOf(str);
        BigDecimal bg = new BigDecimal(db);
        return bg;
    }

    public static double myStrToFloat(String str) {
        double db = Double.valueOf(str);
        return db;
    }

    public static Date SafeStrToDate(String s) {
        Date result = null;
        try {
            if (s == "" || s == null) {
                result = SyntaxConsts.NULL_DATE;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                result = sdf.parse(s);
            }
        }
        catch (Exception e) {
            result = SyntaxConsts.NULL_DATE;
        }
        return result;
    }

    public static int getCharNum(char c) {
        return 0;
    }

    public static char getIdc(String idStr) {
        char c = '\u0000';
        return c;
    }

    public static char getIDCardC(String s) {
        char c = '\u0000';
        return c;
    }

    public static boolean getIsIDCard(String s) {
        boolean r = false;
        return r;
    }

    public static boolean getIsDigit(String str) {
        boolean result = true;
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch >= '0' || ch <= '9') continue;
            result = false;
            break;
        }
        return result;
    }

    public static int getExtendPos(char subStr, String mainStr) {
        return SyntaxLib.getExtendPos(String.valueOf(subStr), mainStr);
    }

    public static int getExtendPos(String subStr, String mainStr) {
        int result = -1;
        boolean strStart = false;
        for (int i = 0; i < mainStr.length() - subStr.length(); ++i) {
            if (mainStr.charAt(i) == '\"') {
                strStart = !strStart;
                continue;
            }
            if (strStart) continue;
            boolean foundOk = true;
            for (int j = 0; j < subStr.length(); ++j) {
                if (subStr.charAt(j) == mainStr.charAt(i + j)) continue;
                foundOk = false;
                break;
            }
            if (!foundOk) continue;
            result = i;
            break;
        }
        return result;
    }

    public static boolean checkInCharSet(char s, char[] cS) {
        return SyntaxLib.checkInCharSet(String.valueOf(s), cS);
    }

    public static boolean checkInCharSet(String s, char[] cS) {
        boolean result = true;
        String str = new String(cS);
        for (int i = 0; i < s.length(); ++i) {
            if (str.indexOf(String.valueOf(s.charAt(i))) >= 0) continue;
            result = false;
            break;
        }
        return result;
    }

    public static boolean checkBetween(char c1, char c2, char c3) {
        return c1 >= c2 && c1 <= c3;
    }

    public static int getSignPos(String signStr, String mainStr) {
        int result = 0;
        if (!SyntaxLib.checkInCharSet(signStr, SyntaxConsts.SIGN_CHARSET)) {
            return result;
        }
        String englishCode = new String(SyntaxConsts.ENGLISH_CHARSET);
        String signCode = new String(SyntaxConsts.SIGN_CHARSET);
        boolean strStart = false;
        int i = 0;
        int startI = 0;
        while (i <= mainStr.length() - signStr.length()) {
            char c = mainStr.charAt(i);
            String cS = String.valueOf(c);
            if (c == '\"') {
                strStart = !strStart;
                ++i;
                continue;
            }
            if (strStart) {
                ++i;
                continue;
            }
            if (englishCode.indexOf(cS) < 0) {
                ++i;
                continue;
            }
            startI = i;
            while (i < mainStr.length() && signCode.indexOf(cS) >= 0) {
                ++i;
            }
            if (signStr != mainStr.substring(startI, i)) continue;
            result = startI;
            break;
        }
        return result;
    }

    public static int getFindPos(String subStr, String mainStr) {
        int result = -1;
        String partStr = "";
        for (int i = 0; i < mainStr.length(); ++i) {
            char c = mainStr.charAt(i);
            if (c == ';') {
                if (partStr.equalsIgnoreCase(subStr)) {
                    result = i - partStr.length();
                    return result;
                }
                partStr = "";
                continue;
            }
            partStr = partStr + c;
        }
        return result;
    }

    public static double getFrac(double value) {
        double value1 = Math.floor(value);
        return value - value1;
    }

    public static String ClearEdgeSpace(String str) {
        String result = "";
        if (StringUtils.isEmpty((String)str)) {
            return result;
        }
        int beginPos = 0;
        int endPos = str.length() - 1;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == ' ') continue;
            beginPos = i;
            break;
        }
        for (int j = str.length() - 1; j >= 0; --j) {
            if (str.charAt(j) == ' ') continue;
            endPos = j;
            break;
        }
        result = str.substring(beginPos, endPos + 1);
        return result;
    }

    public static String UpperCase(char value) {
        String code = String.valueOf(value);
        return code.toUpperCase();
    }
}

