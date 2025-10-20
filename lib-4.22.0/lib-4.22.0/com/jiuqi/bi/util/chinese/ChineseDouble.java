/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.chinese;

import com.jiuqi.bi.util.chinese.ChineseDigitUnit;
import com.jiuqi.bi.util.chinese.ChineseInt;
import com.jiuqi.bi.util.chinese.NoValidCharException;
import com.jiuqi.bi.util.chinese.NotValidChineseNumberException;
import com.jiuqi.bi.util.chinese.StringSplitor;
import java.io.Serializable;

public class ChineseDouble
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 6603671444611834282L;
    private static final char[] INVALID_CHARS = new char[]{' ', '\u3000'};
    private String dbValue;

    public ChineseDouble() {
        this.dbValue = "0";
    }

    public ChineseDouble(double value) {
        this.dbValue = String.valueOf(value);
    }

    public ChineseDouble(String value) {
        if (ChineseDouble.isDoubleString(value)) {
            this.dbValue = value;
        } else {
            try {
                this.dbValue = ChineseDouble.valueOf(value);
            }
            catch (Exception ex) {
                this.dbValue = "0";
            }
        }
    }

    public double doubleValue() {
        return Double.parseDouble(this.dbValue);
    }

    public String chineseValue(int frcLength, boolean showUnit, boolean useBigChar) {
        return ChineseDouble.parseToChinese(this.dbValue, frcLength, showUnit, useBigChar);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String valueOf(String chineseNumber) throws NotValidChineseNumberException, NoValidCharException {
        boolean isNegtive = false;
        boolean isPercent = false;
        boolean isPermillage = false;
        StringSplitor chars = new StringSplitor(chineseNumber, INVALID_CHARS);
        char thisChar = chars.getNextValidChar();
        if (thisChar == '(' || thisChar == '\uff08') {
            thisChar = chars.getNextValidChar();
            if (thisChar != '\u8d1f') throw new NotValidChineseNumberException("\u5b57\u7b26'('\u540e\u9762\u7684\u5b57\u7b26\u65e0\u6548\uff0c\u5fc5\u987b\u662f'\u8d1f'\u8fd9\u4e2a\u5b57\u7b26\uff01");
            thisChar = chars.getNextValidChar();
            if (thisChar != ')' && thisChar != '\uff09') throw new NotValidChineseNumberException("\u5b57\u7b26'\u8d1f'\u540e\u9762\u7684\u5b57\u7b26\u65e0\u6548\uff0c\u5fc5\u987b\u662f')'\u8fd9\u4e2a\u5b57\u7b26\uff01");
            isNegtive = true;
        }
        if (!isNegtive) {
            chars.rollBack();
        }
        if ((thisChar = chars.getNextValidChar()) == '\u767e' || thisChar == '\u4f70') {
            thisChar = chars.getNextValidChar();
            if (thisChar != '\u5206') throw new NotValidChineseNumberException("\u5b57\u7b26'\u767e'('\u4f70')\u540e\u9762\u7684\u5b57\u7b26\u65e0\u6548\uff0c\u5fc5\u987b\u662f'\u5206'\u8fd9\u4e2a\u5b57\u7b26\uff01");
            thisChar = chars.getNextValidChar();
            if (thisChar != '\u4e4b') throw new NotValidChineseNumberException("\u5b57\u7b26'\u5206'\u540e\u9762\u7684\u5b57\u7b26\u65e0\u6548\uff0c\u5fc5\u987b\u662f'\u4e4b'\u8fd9\u4e2a\u5b57\u7b26\uff01");
            isPercent = true;
        } else if (thisChar == '\u5343' || thisChar == '\u4edf') {
            thisChar = chars.getNextValidChar();
            if (thisChar != '\u5206') throw new NotValidChineseNumberException("\u5b57\u7b26'\u5343'('\u4edf')\u540e\u9762\u7684\u5b57\u7b26\u65e0\u6548\uff0c\u5fc5\u987b\u662f'\u5206'\u8fd9\u4e2a\u5b57\u7b26\uff01");
            thisChar = chars.getNextValidChar();
            if (thisChar != '\u4e4b') throw new NotValidChineseNumberException("\u5b57\u7b26'\u5206'\u540e\u9762\u7684\u5b57\u7b26\u65e0\u6548\uff0c\u5fc5\u987b\u662f'\u4e4b'\u8fd9\u4e2a\u5b57\u7b26\uff01");
            isPermillage = true;
        }
        if (!isPercent && !isPermillage) {
            chars.rollBack();
        }
        StringBuffer result = new StringBuffer();
        String left = chars.getLeft();
        int position = left.indexOf(28857);
        if (position != -1) {
            String intPart = left.substring(0, position);
            String frcPart = left.substring(position + 1);
            if (intPart.equals("")) {
                result.append("0");
            } else {
                result.append(ChineseInt.valueOf(intPart));
            }
            result.append(".");
            result.append(ChineseInt.valueOf(frcPart));
        } else {
            result.append(ChineseInt.valueOf(left));
        }
        if (isPercent) {
            result = new StringBuffer(ChineseDouble.leftMoveDot(result.toString(), 2));
        }
        if (isPermillage) {
            result = new StringBuffer(ChineseDouble.leftMoveDot(result.toString(), 3));
        }
        if (!isNegtive) return result.toString();
        result.insert(0, "-");
        return result.toString();
    }

    private static String leftMoveDot(String value, int count) {
        StringBuffer number = new StringBuffer(value);
        int position = number.indexOf(".");
        if (position == -1) {
            number.append(".");
            position = number.length() - 1;
        }
        if (count >= position) {
            int loopCount = count + 1 - position;
            for (int i = 0; i < loopCount; ++i) {
                number.insert(0, "0");
                ++position;
            }
        }
        number.deleteCharAt(position);
        number.insert(position - count, ".");
        ChineseDouble.removeExtraZero(number);
        return number.toString();
    }

    public static String parseToChinese(String value, int frcLength, boolean showUnit, boolean useBigChar) {
        if (ChineseDouble.isDoubleString(value)) {
            boolean isNegtive = false;
            if (value.startsWith("-")) {
                isNegtive = true;
                value = value.substring(1);
            }
            StringBuffer result = new StringBuffer();
            int dotPos = value.indexOf(46);
            String intPart = null;
            String frcPart = null;
            if (dotPos != -1) {
                intPart = value.substring(0, dotPos);
                frcPart = value.substring(dotPos + 1);
            } else {
                intPart = value;
                frcPart = "";
            }
            if (frcLength >= 0) {
                frcPart = ChineseDouble.getFrcPart(frcPart, frcLength);
            }
            result.append(ChineseInt.parseToChinese(intPart, showUnit, useBigChar));
            if (!frcPart.equals("")) {
                result.append("\u70b9");
                result.append(ChineseInt.parseToChinese(frcPart, false, useBigChar));
            }
            if (isNegtive) {
                result.insert(0, "\uff08\u8d1f\uff09");
            }
            return result.toString();
        }
        throw new IllegalArgumentException("\u5b57\u7b26\u4e32\"" + value + "\"\u4e0d\u662f\u4e00\u4e2a\u963f\u62c9\u4f2f\u6570\u5b57\u5b57\u7b26\u4e32\uff01");
    }

    private static String getFrcPart(String frcPart, int frcLength) {
        int length = frcPart.length();
        if (length >= frcLength) {
            return frcPart.substring(0, frcLength);
        }
        StringBuffer sb = new StringBuffer(frcPart);
        for (int i = 0; i < frcLength - length; ++i) {
            sb.append("0");
        }
        return sb.toString();
    }

    public String getPercentForm(int frcLength, boolean showUnit, boolean useBigChar) {
        String value = this.dbValue;
        boolean negative = value.startsWith("-");
        if (negative) {
            value = value.substring(1);
        }
        String result = ChineseDigitUnit.toChineseUnit("100", useBigChar) + "\u5206\u4e4b" + ChineseDouble.parseToChinese(ChineseDouble.rightMoveDot(value, 2), frcLength, showUnit, useBigChar);
        if (negative) {
            result = "\uff08\u8d1f\uff09" + result;
        }
        return result;
    }

    public String getPermillageForm(int frcLength, boolean showUnit, boolean useBigChar) {
        String value = this.dbValue;
        boolean negative = value.startsWith("-");
        if (negative) {
            value = value.substring(1);
        }
        String result = ChineseDigitUnit.toChineseUnit("1000", useBigChar) + "\u5206\u4e4b" + ChineseDouble.parseToChinese(ChineseDouble.rightMoveDot(value, 3), frcLength, showUnit, useBigChar);
        if (negative) {
            result = "\uff08\u8d1f\uff09" + result;
        }
        return result;
    }

    private static String rightMoveDot(String value, int count) {
        StringBuffer number = new StringBuffer(value);
        int position = number.indexOf(".");
        if (position == -1) {
            number.append(".");
            position = number.length() - 1;
        }
        if (position + count > number.length() - 1) {
            int loopCount = position + count + 1 - number.length();
            for (int i = 0; i < loopCount; ++i) {
                number.append("0");
            }
        }
        number.deleteCharAt(position);
        number.insert(position + count, ".");
        ChineseDouble.removeExtraZero(number);
        return number.toString();
    }

    private static void removeExtraZero(StringBuffer number) {
        char c;
        int i;
        int dotPos = number.indexOf(".");
        for (i = 0; i < dotPos - 1 && (c = number.charAt(0)) == '0'; ++i) {
            number.deleteCharAt(0);
        }
        dotPos = number.indexOf(".");
        for (i = number.length() - 1; i >= dotPos && ((c = number.charAt(i)) == '0' || c == '.'); --i) {
            number.deleteCharAt(number.length() - 1);
        }
    }

    static boolean isDoubleString(String value) {
        int dotPos = value.indexOf(46);
        if (dotPos != -1) {
            String part1 = value.substring(0, dotPos);
            String part2 = value.substring(dotPos + 1);
            return ChineseInt.isIntString(part1) && ChineseInt.isIntString(part2);
        }
        return ChineseInt.isIntString(value);
    }

    public Object clone() {
        return new ChineseDouble(this.dbValue);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChineseDouble)) {
            return false;
        }
        ChineseDouble value = (ChineseDouble)o;
        return this.dbValue.equals(value.dbValue);
    }

    public int hashCode() {
        int result = super.hashCode();
        if (this.dbValue != null) {
            result ^= this.dbValue.hashCode();
        }
        return result;
    }

    public String toString() {
        return this.chineseValue(-1, true, false);
    }
}

