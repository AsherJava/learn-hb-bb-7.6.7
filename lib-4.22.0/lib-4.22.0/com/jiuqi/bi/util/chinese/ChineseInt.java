/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.chinese;

import com.jiuqi.bi.util.chinese.ChineseDigit;
import com.jiuqi.bi.util.chinese.ChineseDigitUnit;
import com.jiuqi.bi.util.chinese.NoValidCharException;
import com.jiuqi.bi.util.chinese.NotValidChineseDigitException;
import com.jiuqi.bi.util.chinese.NotValidChineseDigitUnitException;
import com.jiuqi.bi.util.chinese.NotValidChineseNumberException;
import com.jiuqi.bi.util.chinese.StringSplitor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChineseInt
implements Cloneable,
Serializable {
    private static final long serialVersionUID = -6851679365398683859L;
    private static final char[] INVALID_CHARS = new char[]{' ', '\u3000'};
    private String intValue;

    public ChineseInt() {
        this.intValue = "0";
    }

    public ChineseInt(long value) {
        this.intValue = String.valueOf(value);
    }

    public ChineseInt(String value) {
        if (ChineseInt.isIntString(value)) {
            this.intValue = value;
        } else {
            try {
                this.intValue = ChineseInt.valueOf(value);
            }
            catch (Exception e) {
                this.intValue = "0";
            }
        }
    }

    public long longValue() {
        return Long.parseLong(this.intValue);
    }

    public String chineseValue(boolean showUnit, boolean useBigChar) {
        return ChineseInt.parseToChinese(this.intValue, showUnit, useBigChar);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String valueOf(String value) throws NotValidChineseNumberException, NoValidCharException {
        boolean negtive = false;
        StringSplitor chars = new StringSplitor(value, INVALID_CHARS);
        char thisChar = chars.getNextValidChar();
        if (thisChar == '(' || thisChar == '\uff08') {
            thisChar = chars.getNextValidChar();
            if (thisChar != '\u8d1f') throw new NotValidChineseNumberException("\u5b57\u7b26'('\u540e\u9762\u7684\u5b57\u7b26\u65e0\u6548\uff0c\u5fc5\u987b\u662f'\u8d1f'\uff01");
            thisChar = chars.getNextValidChar();
            if (thisChar != ')' && thisChar != '\uff09') throw new NotValidChineseNumberException("\u5b57\u7b26'\u8d1f'\u540e\u9762\u7684\u5b57\u7b26\u65e0\u6548\uff0c\u5fc5\u987b\u662f')'\uff01");
            negtive = true;
        }
        if (!negtive) {
            chars.rollBack();
        }
        boolean hasUnit = false;
        thisChar = chars.getNextValidChar();
        if (!ChineseDigit.isValidChineseDigit(thisChar)) throw new NotValidChineseNumberException("\u5f00\u5934\u7684\u5b57\u7b26\"" + thisChar + "\"\u4e0d\u662f\u6570\u5b57\uff01");
        thisChar = chars.getNextValidChar();
        if (ChineseDigitUnit.isValidChineseDigitUnit(String.valueOf(thisChar))) {
            hasUnit = true;
        }
        chars.rollBack();
        chars.rollBack();
        long longValue = 0L;
        if (hasUnit) {
            int tempValue = 0;
            while (chars.hasValidChar()) {
                thisChar = chars.getNextValidChar();
                if (ChineseDigit.isValidChineseDigit(thisChar)) {
                    try {
                        tempValue = ChineseDigit.valueOf(thisChar);
                    }
                    catch (NotValidChineseDigitException ex) {
                        throw new NotValidChineseNumberException("\u5b58\u5728\u975e\u6cd5\u5b57\u7b26\"" + thisChar + "\"\uff01");
                    }
                    if (chars.hasValidChar()) continue;
                    longValue += (long)tempValue;
                    continue;
                }
                if (!ChineseDigitUnit.isValidChineseDigitUnit(String.valueOf(thisChar))) throw new NotValidChineseNumberException("\u5b58\u5728\u975e\u6cd5\u7684\u5b57\u7b26\"" + thisChar + "\"\uff01");
                if (ChineseDigitUnit.isLargeUnit(thisChar)) {
                    longValue += (long)tempValue;
                    tempValue = 0;
                    try {
                        longValue *= Long.parseLong(ChineseDigitUnit.valueOf(String.valueOf(thisChar)));
                        continue;
                    }
                    catch (NotValidChineseDigitUnitException ex) {
                        throw new NotValidChineseNumberException("\u5b58\u5728\u975e\u6cd5\u5b57\u7b26\"" + thisChar + "\"\uff01");
                    }
                }
                try {
                    longValue += (long)tempValue * Long.parseLong(ChineseDigitUnit.valueOf(String.valueOf(thisChar)));
                }
                catch (NotValidChineseDigitUnitException ex) {
                    throw new NotValidChineseNumberException("\u5b58\u5728\u975e\u6cd5\u5b57\u7b26\"" + thisChar + "\"\uff01");
                }
                tempValue = 0;
            }
        } else {
            StringBuffer stringValue = new StringBuffer();
            while (chars.hasValidChar()) {
                thisChar = chars.getNextValidChar();
                if (!ChineseDigit.isValidChineseDigit(thisChar)) continue;
                try {
                    stringValue.append(ChineseDigit.valueOf(thisChar));
                }
                catch (NotValidChineseDigitException ex4) {
                    throw new NotValidChineseNumberException("\u5b58\u5728\u975e\u6cd5\u5b57\u7b26\"" + thisChar + "\"\uff01");
                }
            }
            longValue = Long.parseLong(stringValue.toString());
        }
        if (!negtive) return String.valueOf(longValue);
        longValue = -longValue;
        return String.valueOf(longValue);
    }

    public static String parseToChinese(String value, boolean showUnit, boolean useBigChar) {
        if (value != null && !value.equals("") && ChineseInt.isIntString(value)) {
            boolean negative = false;
            if (value.startsWith("-")) {
                negative = true;
                value = value.substring(1);
            }
            ArrayList<Integer> digits = new ArrayList<Integer>();
            for (int i = 0; i < value.length(); ++i) {
                int digit = value.charAt(i) - 48;
                if (digit < 0 || digit > 9) continue;
                digits.add(new Integer(digit));
            }
            StringBuffer result = new StringBuffer();
            if (!showUnit) {
                for (int i = 0; i < digits.size(); ++i) {
                    int digit = (Integer)digits.get(i);
                    result.append(ChineseDigit.toChineseChar(digit, useBigChar));
                }
            } else {
                int cursor = digits.size();
                int smallInt = 0;
                int i = 0;
                while (cursor >= 0) {
                    smallInt = ChineseInt.getSmallInt(digits, i);
                    if (smallInt != 0) {
                        result.insert(0, ChineseInt.smallIntToChineseString(smallInt, useBigChar) + ChineseDigitUnit.toChineseUnit(ChineseInt.getUnit(i * 4), useBigChar));
                    }
                    if (smallInt < 1000 && smallInt > 0 && (cursor -= 4) >= 0) {
                        result.insert(0, ChineseDigit.toChineseChar(0, useBigChar));
                    }
                    ++i;
                }
            }
            if (negative) {
                result.insert(0, "(\u8d1f)");
            }
            return result.toString();
        }
        throw new IllegalArgumentException("\u5b57\u7b26\u4e32\"" + value + "\"\u4e0d\u662f\u5408\u6cd5\u7684\u963f\u62c9\u4f2f\u6570\u5b57\u5b57\u7b26\u4e32\uff01");
    }

    private static int getSmallInt(List<Integer> digits, int i) {
        int value = 0;
        int j = 0;
        for (int cursor = digits.size() - i * 4 - 1; j < 4 && cursor >= 0; --cursor, ++j) {
            int digit = digits.get(cursor);
            value = (int)((double)value + (double)digit * Math.pow(10.0, j));
        }
        return value;
    }

    private static String smallIntToChineseString(int value, boolean useBigChar) {
        if (value >= 0 && value < 10000) {
            int[] digits = new int[]{0, 0, 0, 0};
            int count = 3;
            while (value != 0) {
                digits[count] = value % 10;
                value /= 10;
                --count;
            }
            StringBuffer result = new StringBuffer();
            boolean allZeroBefore = true;
            boolean zeroExist = false;
            for (int i = 0; i < digits.length; ++i) {
                if (digits[i] != 0) {
                    result.append(ChineseDigit.toChineseChar(digits[i], useBigChar) + ChineseDigitUnit.toChineseUnit(ChineseInt.getUnit(3 - i), useBigChar));
                    allZeroBefore = false;
                    zeroExist = false;
                    continue;
                }
                if (allZeroBefore) continue;
                boolean allZeroAfter = true;
                for (int j = i + 1; j < 4; ++j) {
                    if (digits[j] == 0) continue;
                    allZeroAfter = false;
                    break;
                }
                if (allZeroAfter || zeroExist) continue;
                result.append(ChineseDigit.toChineseChar(0, useBigChar));
                zeroExist = true;
            }
            return result.toString();
        }
        throw new IllegalArgumentException("\u53ea\u80fd\u89e3\u67900-9999\u7684\u6574\u6570");
    }

    static boolean isIntString(String value) {
        if (value.startsWith("-")) {
            value = value.substring(1);
        }
        return ChineseDigitUnit.isDigitString(value);
    }

    private static String getUnit(int i) {
        StringBuffer sb = new StringBuffer("1");
        for (int j = 0; j < i; ++j) {
            sb.append("0");
        }
        return sb.toString();
    }

    public Object clone() {
        return new ChineseInt(this.intValue);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChineseInt)) {
            return false;
        }
        ChineseInt value = (ChineseInt)o;
        return this.intValue.equals(value.intValue);
    }

    public int hashCode() {
        int result = super.hashCode();
        if (this.intValue != null) {
            result ^= this.intValue.hashCode();
        }
        return result;
    }

    public String toString() {
        return this.chineseValue(true, false);
    }
}

