/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChineseMoneyUtil {
    private static final String[] chnNumbers = new String[]{"\u96f6", "\u58f9", "\u8d30", "\u53c1", "\u8086", "\u4f0d", "\u9646", "\u67d2", "\u634c", "\u7396"};
    private static final String[] chnUnitChars = new String[]{"", "\u62fe", "\u4f70", "\u4edf"};
    private static final String[] chnUnitSections = new String[]{"\u4e07", "\u4ebf"};
    private static final String[] chnDunitChars = new String[]{"\u89d2", "\u5206", "\u5398"};
    private static final String BLANK = "";
    private static final String ZERO = chnNumbers[0];

    public static String toChineseMoney(BigDecimal money) {
        int num;
        int i;
        boolean isMinus;
        if (money == null || money.compareTo(BigDecimal.ZERO) == 0) {
            return "\u96f6\u5143\u6574";
        }
        boolean bl = isMinus = (money = money.setScale(3, RoundingMode.HALF_UP)).compareTo(BigDecimal.ZERO) < 0;
        if (isMinus) {
            money = money.abs();
        }
        int[] bytes = ChineseMoneyUtil.getEightByteArr(money);
        int maxLength = 8;
        StringBuilder chnStr = new StringBuilder(maxLength * 3);
        String[] numChar = new String[maxLength];
        String[] numUnitChars = new String[maxLength];
        String[] numUnitSections = new String[maxLength];
        boolean allZero = true;
        for (int j = 0; j < bytes.length; ++j) {
            if (bytes[j] == 0) continue;
            allZero = true;
            for (i = maxLength - 1; i >= 0; --i) {
                numChar[i] = BLANK;
                numUnitChars[i] = BLANK;
                numUnitSections[i] = BLANK;
                if (i % 4 == 3) {
                    allZero = true;
                }
                if ((num = ChineseMoneyUtil.getNumberByCoordinates(money, 8 * (bytes.length - j - 1) + i + 1)) != 0) {
                    allZero = false;
                    numChar[i] = chnNumbers[num];
                    if (i < maxLength - 1 && numChar[i + 1] == BLANK && i % 4 != 3) {
                        numChar[i + 1] = ZERO;
                    }
                    numUnitChars[i] = chnUnitChars[i % 4];
                }
                if (allZero || i != 4) continue;
                numUnitSections[i] = chnUnitSections[0];
            }
            for (i = maxLength - 1; i >= 0; --i) {
                chnStr.append(numChar[i]).append(numUnitChars[i]).append(numUnitSections[i]);
            }
            for (int k = bytes.length - j - 1; k > 0; --k) {
                chnStr.append(chnUnitSections[1]);
            }
        }
        if (money.abs().compareTo(BigDecimal.ONE) >= 0) {
            chnStr.append("\u5143");
        }
        if (money.doubleValue() - (double)money.longValue() == 0.0) {
            chnStr.append("\u6574");
        } else {
            boolean addZero = false;
            for (i = 0; i >= -2; --i) {
                num = ChineseMoneyUtil.getNumberByCoordinates(money, i);
                if (num != 0) {
                    if (i < 0 && addZero) {
                        chnStr.append(ZERO);
                        addZero = false;
                    }
                    chnStr.append(chnNumbers[num]).append(chnDunitChars[0 - i]);
                    continue;
                }
                if (i != 0) continue;
                addZero = true;
            }
        }
        if (chnStr.length() > 1 && chnStr.substring(0, 1).equals(ZERO)) {
            chnStr.deleteCharAt(0);
        }
        if (isMinus) {
            chnStr.insert(0, "\u8d1f");
        }
        return chnStr.toString();
    }

    private static int[] getEightByteArr(BigDecimal money) {
        long intNum = money.longValue();
        int length = String.valueOf(intNum).toString().length();
        int i = length / 8 + (length % 8 == 0 ? 0 : 1);
        int[] bytes = new int[i];
        while (i > 0) {
            bytes[--i] = (int)(money.movePointLeft((bytes.length - i - 1) * 8).longValue() % 100000000L);
        }
        return bytes;
    }

    private static int getNumberByCoordinates(BigDecimal number, int i) {
        return (int)(number.movePointLeft(i - 1).longValue() % 10L);
    }
}

