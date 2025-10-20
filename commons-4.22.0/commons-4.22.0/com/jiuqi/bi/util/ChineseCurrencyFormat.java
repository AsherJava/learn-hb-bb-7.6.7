/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class ChineseCurrencyFormat
extends NumberFormat {
    private static final long serialVersionUID = 3833375913807067467L;
    private static final char[] numberChars = new char[]{'\u96f6', '\u58f9', '\u8d30', '\u53c1', '\u8086', '\u4f0d', '\u9646', '\u67d2', '\u634c', '\u7396'};
    private static final char[] numberUnits = new char[]{'\u62fe', '\u4f70', '\u4edf', '\u4e07'};
    private static final char[] numberLevels = new char[]{'\u4e07', '\u4ebf', '\u5146'};
    private static final int level_number_count = 4;
    private static final int max_int_count = 20;
    private static final char[] moneyUnits = new char[]{'\u5143', '\u89d2', '\u5206'};
    private static final String zero_money_str = "\u96f6\u5143\u6574";
    private String prefix;
    private String suffix;
    private boolean startZero = false;

    public ChineseCurrencyFormat() {
        this.prefix = null;
        this.suffix = null;
    }

    public ChineseCurrencyFormat(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String value) {
        this.prefix = value;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String value) {
        this.suffix = value;
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        return null;
    }

    private String convertSimple(String number) {
        StringBuffer buffer = new StringBuffer();
        boolean prevZero = false;
        boolean started = false;
        int cnt = 0;
        for (int i = number.length() - 1; i >= 0; --i) {
            char ch = number.charAt(i);
            if (ch == '0') {
                prevZero = true;
            } else {
                if (started && prevZero) {
                    buffer.insert(0, numberChars[0]);
                }
                if (cnt > 0) {
                    buffer.insert(0, numberUnits[cnt - 1]);
                }
                buffer.insert(0, numberChars[ch - 48]);
                started = true;
                prevZero = false;
            }
            ++cnt;
        }
        this.startZero = prevZero;
        return buffer.toString();
    }

    protected void format(String number, StringBuffer toAppendTo) {
        String decString;
        StringBuffer buffer;
        String intString;
        if (number == null || number.length() == 0) {
            return;
        }
        if (number.charAt(0) == '-') {
            number = number.substring(1);
        }
        if (number.equals("0")) {
            toAppendTo.append(zero_money_str);
            return;
        }
        int pointPos = number.indexOf(46);
        if (pointPos < 0) {
            pointPos = number.length();
        }
        if (pointPos == 1 && number.charAt(0) == '0') {
            intString = null;
        } else {
            if (pointPos > 20) {
                toAppendTo.append("\u6570\u503c\u8fc7\u5927\uff0c\u8f6c\u5316\u5931\u8d25");
                return;
            }
            int levelCount = (pointPos + 4 - 1) / 4;
            if (levelCount > 0) {
                String[] levelStrs = new String[levelCount];
                boolean[] zeroStarts = new boolean[levelCount];
                for (int i = 0; i < levelCount; ++i) {
                    int end = pointPos - 4 * i;
                    int start = end - 4 < 0 ? 0 : end - 4;
                    String subVal = this.convertSimple(number.substring(start, end));
                    levelStrs[i] = "".equals(subVal) ? null : subVal;
                    zeroStarts[i] = this.startZero;
                }
                buffer = new StringBuffer();
                boolean started = false;
                if (levelStrs[0] != null) {
                    buffer.insert(0, levelStrs[0]);
                    started = true;
                }
                if (levelCount >= 2) {
                    if (levelStrs[1] != null) {
                        if (started && zeroStarts[0]) {
                            buffer.insert(0, numberChars[0]);
                        }
                        buffer.insert(0, numberLevels[0]);
                        buffer.insert(0, levelStrs[1]);
                        started = true;
                    }
                    if (levelCount >= 3) {
                        if (levelStrs[2] != null) {
                            if (started && levelStrs[1] == null) {
                                buffer.insert(0, numberChars[0]);
                            } else if (started && levelStrs[1] != null && zeroStarts[1]) {
                                buffer.insert(0, numberChars[0]);
                            }
                            buffer.insert(0, numberLevels[1]);
                            buffer.insert(0, levelStrs[2]);
                            started = true;
                        }
                        if (levelCount >= 4 && levelStrs[3] != null) {
                            if (levelStrs[2] == null && started && levelStrs[1] == null) {
                                buffer.insert(0, numberChars[0]);
                            }
                            if (levelStrs[2] == null) {
                                buffer.insert(0, numberLevels[1]);
                            }
                            buffer.insert(0, numberLevels[0]);
                            buffer.insert(0, levelStrs[3]);
                            started = true;
                        }
                        if (levelCount >= 5 && levelStrs[4] != null) {
                            if (started && levelStrs[4] == null && levelStrs[3] == null) {
                                buffer.insert(0, numberChars[0]);
                            }
                            if (levelStrs[4] != null) {
                                buffer.insert(0, numberLevels[2]);
                                buffer.insert(0, levelStrs[4]);
                            }
                        }
                    }
                }
                intString = buffer.toString();
            } else {
                intString = null;
            }
        }
        if (pointPos >= number.length() - 1) {
            decString = null;
        } else {
            int p2;
            char p1 = number.charAt(pointPos + 1);
            int n = p2 = number.length() - 1 >= pointPos + 2 ? (int)number.charAt(pointPos + 2) : 48;
            if (p1 == '0' && p2 == 48) {
                decString = null;
            } else {
                buffer = new StringBuffer();
                if (p1 == '0') {
                    buffer.append(numberChars[0]);
                    buffer.append(numberChars[p2 - 48]);
                    buffer.append(moneyUnits[2]);
                } else if (p2 == 48) {
                    buffer.append(numberChars[p1 - 48]);
                    buffer.append(moneyUnits[1]);
                } else {
                    buffer.append(numberChars[p1 - 48]);
                    buffer.append(moneyUnits[1]);
                    buffer.append(numberChars[p2 - 48]);
                    buffer.append(moneyUnits[2]);
                }
                decString = buffer.toString();
            }
        }
        if (intString == null && decString == null) {
            toAppendTo.append(zero_money_str);
        } else if (intString == null) {
            toAppendTo.append(decString);
        } else if (decString == null) {
            toAppendTo.append(intString + "\u5143\u6574");
        } else {
            toAppendTo.append(intString);
            toAppendTo.append("\u5143");
            toAppendTo.append(decString);
        }
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        if (this.prefix != null) {
            toAppendTo.append(this.prefix);
        }
        DecimalFormat fmt = new DecimalFormat("0.##");
        this.format(fmt.format(number), toAppendTo);
        if (this.suffix != null) {
            toAppendTo.append(this.suffix);
        }
        return toAppendTo;
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        if (this.prefix != null) {
            toAppendTo.append(this.prefix);
        }
        this.format(Long.toString(number), toAppendTo);
        if (this.suffix != null) {
            toAppendTo.append(this.suffix);
        }
        return toAppendTo;
    }
}

