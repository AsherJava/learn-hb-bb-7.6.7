/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.chinese;

import com.jiuqi.bi.util.chinese.NotValidChineseDigitUnitException;
import java.io.Serializable;

class ChineseDigitUnit
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 989644502834223640L;
    private static final char[] SMALL_UNIT = new char[]{'\u5341', '\u767e', '\u5343'};
    private static final char[] BIG_UNIT = new char[]{'\u62fe', '\u4f70', '\u4edf'};
    private static final char[] LARGE_UNIT = new char[]{'\u4e07', '\u4ebf'};
    private static String bufferedUnit = null;
    private static String bufferedPower = null;
    private String power;

    public ChineseDigitUnit() {
        this.power = "1";
    }

    public ChineseDigitUnit(long power) {
        if (power < 0L) {
            power = -power;
        }
        long value = 1L;
        while (power >= 10L) {
            power /= 10L;
            value *= 10L;
        }
        this.power = String.valueOf(value);
    }

    public ChineseDigitUnit(String unit) {
        if (unit != null && unit.equals("")) {
            boolean digitString = ChineseDigitUnit.isDigitString(unit);
            if (digitString) {
                StringBuffer sb = new StringBuffer("1");
                for (int i = 0; i < unit.length() - 1; ++i) {
                    sb.append("0");
                }
                this.power = sb.toString();
            } else {
                try {
                    this.power = ChineseDigitUnit.valueOf(unit);
                }
                catch (NotValidChineseDigitUnitException ex) {
                    throw new IllegalArgumentException("\u5355\u4f4d\"" + unit + "\"\u65e2\u4e0d\u662f\u4e00\u4e2a\u6709\u6548\u7684\u963f\u62c9\u4f2f\u6570\u5b57\uff0c\u4e5f\u4e0d\u662f\u6709\u6548\u7684\u4e2d\u6587\u6570\u5b57\u5355\u4f4d\uff01");
                }
            }
        } else {
            throw new IllegalArgumentException("\u5b57\u7b26\u4e32\u4e3anull\u6216\u8005\u7a7a\u5b57\u7b26\u4e32\uff01");
        }
    }

    public long longValue() {
        return Long.parseLong(this.power);
    }

    public String chineseValue(boolean useBigChar) {
        return ChineseDigitUnit.toChineseUnit(this.power, useBigChar);
    }

    public static String valueOf(String value) throws NotValidChineseDigitUnitException {
        if (value.equals(bufferedUnit)) {
            return bufferedPower;
        }
        long result = 0L;
        for (int i = 0; i < value.length(); ++i) {
            int j;
            char thisUnit = value.charAt(i);
            int position = -1;
            for (j = 0; j < SMALL_UNIT.length; ++j) {
                if (thisUnit != SMALL_UNIT[j]) continue;
                position = j;
                break;
            }
            if (position != -1) {
                result += (long)(position + 1);
                position = -1;
                continue;
            }
            for (j = 0; j < BIG_UNIT.length; ++j) {
                if (thisUnit != BIG_UNIT[j]) continue;
                position = j;
                break;
            }
            if (position != -1) {
                result += (long)(position + 1);
                position = -1;
                continue;
            }
            for (j = 0; j < LARGE_UNIT.length; ++j) {
                if (thisUnit != LARGE_UNIT[j]) continue;
                position = j;
                break;
            }
            if (position != -1) {
                result += (long)(4 * (position + 1));
                position = -1;
                continue;
            }
            throw new NotValidChineseDigitUnitException("\u5b57\u7b26" + thisUnit + "\u4e0d\u662f\u4e00\u4e2a\u5408\u6cd5\u7684\u4e2d\u6587\u6570\u5b57\u5355\u4f4d\uff01");
        }
        StringBuffer sb = new StringBuffer("1");
        int i = 0;
        while ((long)i < result) {
            sb.append("0");
            ++i;
        }
        bufferedUnit = value;
        bufferedPower = sb.toString();
        return sb.toString();
    }

    public static String toChineseUnit(String power, boolean useBigChar) {
        if (ChineseDigitUnit.isDigitString(power)) {
            int pow;
            StringBuffer sb = new StringBuffer();
            for (pow = power.length(); pow > 8; pow -= 8) {
                sb.insert(0, LARGE_UNIT[1]);
            }
            while (pow > 4) {
                sb.insert(0, LARGE_UNIT[0]);
                pow -= 4;
            }
            if (pow > 1) {
                if (!useBigChar) {
                    sb.insert(0, SMALL_UNIT[pow - 2]);
                } else {
                    sb.insert(0, BIG_UNIT[pow - 2]);
                }
            }
            return sb.toString();
        }
        throw new IllegalArgumentException("\u5b57\u7b26\u4e32\"" + power + "\"\u4e0d\u662f\u4e00\u4e2a\u963f\u62c9\u4f2f\u6570\u5b57\u5b57\u7b26\u4e32\uff01");
    }

    static boolean isDigitString(String value) {
        for (int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (Character.isDigit(c)) continue;
            return false;
        }
        return true;
    }

    public static boolean isValidChineseDigitUnit(String value) {
        try {
            ChineseDigitUnit.valueOf(value);
            return true;
        }
        catch (NotValidChineseDigitUnitException ex) {
            return false;
        }
    }

    public static boolean isLargeUnit(char value) {
        for (int i = 0; i < LARGE_UNIT.length; ++i) {
            if (value != LARGE_UNIT[i]) continue;
            return true;
        }
        return false;
    }

    public Object clone() {
        return new ChineseDigitUnit(this.power);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChineseDigitUnit)) {
            return false;
        }
        ChineseDigitUnit unit = (ChineseDigitUnit)obj;
        return this.power.equals(unit.power);
    }

    public int hashCode() {
        int value = super.hashCode();
        if (this.power != null) {
            value ^= this.power.hashCode();
        }
        return value;
    }

    public String toString() {
        return this.chineseValue(false);
    }
}

