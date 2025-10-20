/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.chinese;

import com.jiuqi.bi.util.chinese.NotValidChineseDigitException;
import java.io.Serializable;

public class ChineseDigit
implements Comparable<ChineseDigit>,
Cloneable,
Serializable {
    private static final long serialVersionUID = -1122267906808924973L;
    private static final char[] SMALL_DIGITS = new char[]{'\u3007', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d', '\u4e03', '\u516b', '\u4e5d'};
    private static final char[] BIG_DIGITS = new char[]{'\u96f6', '\u58f9', '\u8d30', '\u53c1', '\u8086', '\u4f0d', '\u9646', '\u67d2', '\u634c', '\u7396'};
    private static char bufferedChar = '\u0000';
    private static int bufferedValue = -1;
    private int digit;

    public ChineseDigit() {
        this.digit = 0;
    }

    public ChineseDigit(int dig) {
        if (dig < 0 || dig > 9) {
            throw new IllegalArgumentException("\u53ea\u80fd\u4ece0-9\u7684\u963f\u62c9\u4f2f\u6570\u5b57\u6765\u5efa\u7acb\u5bf9\u8c61\uff01");
        }
        this.digit = dig;
    }

    public ChineseDigit(char digChar) throws NotValidChineseDigitException {
        int value = digChar - 48;
        this.digit = value >= 0 && value <= 9 ? value : ChineseDigit.valueOf(digChar);
    }

    public int intValue() {
        return this.digit;
    }

    public char chineseValue(boolean useBigChar) {
        return ChineseDigit.toChineseChar(this.digit, useBigChar);
    }

    public static int valueOf(char chDig) throws NotValidChineseDigitException {
        int i;
        if (bufferedChar == chDig) {
            return bufferedValue;
        }
        int position = -1;
        for (i = 0; i < SMALL_DIGITS.length; ++i) {
            if (chDig != SMALL_DIGITS[i]) continue;
            position = i;
            break;
        }
        if (position != -1) {
            bufferedChar = chDig;
            bufferedValue = position;
            return position;
        }
        for (i = 0; i < BIG_DIGITS.length; ++i) {
            if (chDig != BIG_DIGITS[i]) continue;
            position = i;
            break;
        }
        if (position != -1) {
            bufferedChar = chDig;
            bufferedValue = position;
            return position;
        }
        throw new NotValidChineseDigitException("\u5b57\u7b26\"" + chDig + "\"\u4e0d\u662f\u4e00\u4e2a\u4e2d\u6587\u7684\u6570\u5b57\u5b57\u7b26\uff01");
    }

    public static char toChineseChar(int value, boolean useBigChar) {
        if (value >= 0 && value <= 9) {
            if (!useBigChar) {
                return SMALL_DIGITS[value];
            }
            return BIG_DIGITS[value];
        }
        throw new IllegalArgumentException("\u6574\u578b\u53c2\u6570\u503c\"" + value + "\"\u5fc5\u987b\u57280-9\u4e4b\u95f4\uff01");
    }

    public static boolean isValidChineseDigit(char value) {
        try {
            ChineseDigit.valueOf(value);
            return true;
        }
        catch (NotValidChineseDigitException ex) {
            return false;
        }
    }

    public Object clone() {
        return new ChineseDigit(this.digit);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChineseDigit)) {
            return false;
        }
        ChineseDigit value = (ChineseDigit)o;
        return this.digit == value.digit;
    }

    public int hashCode() {
        int result = super.hashCode();
        int hc1 = this.digit;
        result = 37 * result + hc1;
        return result;
    }

    public String toString() {
        return String.valueOf(this.chineseValue(false));
    }

    @Override
    public int compareTo(ChineseDigit value) {
        return this.digit - value.digit;
    }
}

