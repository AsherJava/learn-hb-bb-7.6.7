/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachmentcheck.bean;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ChineseCharToEn {
    private static final int[] li_SecPosValue = new int[]{1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};
    private static final String[] lc_FirstLetter = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "w", "x", "y", "z"};
    private static final Logger logger = LoggerFactory.getLogger(ChineseCharToEn.class);

    public static String getAllFirstLetter(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        String _str = "";
        for (int i = 0; i < str.length(); ++i) {
            _str = _str + ChineseCharToEn.getFirstLetter(str.substring(i, i + 1));
        }
        return _str;
    }

    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        if ((chinese = ChineseCharToEn.conversionStr(chinese, "GB2312", "ISO8859-1")).length() > 1) {
            int li_SectorCode = chinese.charAt(0);
            int li_PositionCode = chinese.charAt(1);
            int li_SecPosCode = (li_SectorCode -= 160) * 100 + (li_PositionCode -= 160);
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; ++i) {
                    if (li_SecPosCode < li_SecPosValue[i] || li_SecPosCode >= li_SecPosValue[i + 1]) continue;
                    chinese = lc_FirstLetter[i];
                    break;
                }
            } else {
                chinese = ChineseCharToEn.conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }
        return chinese;
    }

    private static String conversionStr(String str, String charsetName, String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        }
        catch (UnsupportedEncodingException ex) {
            logger.error("\u5b57\u7b26\u4e32\u7f16\u7801\u8f6c\u6362\u5f02\u5e38\uff1a" + ex.getMessage(), ex);
        }
        return str;
    }
}

