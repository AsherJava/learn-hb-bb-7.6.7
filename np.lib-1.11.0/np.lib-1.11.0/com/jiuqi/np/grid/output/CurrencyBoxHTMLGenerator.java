/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.JqLib
 */
package com.jiuqi.np.grid.output;

import com.jiuqi.bi.util.JqLib;
import java.text.DecimalFormat;

public class CurrencyBoxHTMLGenerator {
    private int decimal;
    private int precision;
    private int type;
    private String value;
    private String moneySign;

    public CurrencyBoxHTMLGenerator(int decimal, int precision, String value, int type, String moneySign) {
        this.decimal = decimal;
        this.precision = precision;
        this.type = type;
        this.value = value;
        this.moneySign = moneySign;
    }

    public String generate() {
        this.decimal = this.getDecimal(this.decimal);
        this.precision = this.getPrecision(this.decimal, this.precision);
        if (this.value.equals("|")) {
            return this.getTitleHTML(this.decimal, this.precision);
        }
        if (this.moneySign == null) {
            this.moneySign = "";
        }
        return this.getDataHTML(this.decimal, this.precision, this.value, this.type, this.moneySign);
    }

    private int getDecimal(int dec) {
        if (dec == 0) {
            return 0;
        }
        return 2;
    }

    private int getPrecision(int dec, int pre) {
        if (dec == 0) {
            if (pre >= 2 && pre <= 13) {
                return pre;
            }
            return 13;
        }
        if (pre >= 4 && pre <= 15) {
            return pre;
        }
        return 15;
    }

    private char formatNumber(char c, int type) {
        if (type == 0) {
            return c;
        }
        if (type == 1) {
            switch (c) {
                case '1': {
                    return '\u4e00';
                }
                case '2': {
                    return '\u4e8c';
                }
                case '3': {
                    return '\u4e09';
                }
                case '4': {
                    return '\u56db';
                }
                case '5': {
                    return '\u4e94';
                }
                case '6': {
                    return '\u516d';
                }
                case '7': {
                    return '\u4e03';
                }
                case '8': {
                    return '\u516b';
                }
                case '9': {
                    return '\u4e5d';
                }
                case '0': {
                    return 'O';
                }
                case '-': {
                    return '\u8d1f';
                }
            }
            return '\u9519';
        }
        switch (c) {
            case '1': {
                return '\u58f9';
            }
            case '2': {
                return '\u8d30';
            }
            case '3': {
                return '\u53c1';
            }
            case '4': {
                return '\u8086';
            }
            case '5': {
                return '\u4f0d';
            }
            case '6': {
                return '\u9646';
            }
            case '7': {
                return '\u67d2';
            }
            case '8': {
                return '\u634c';
            }
            case '9': {
                return '\u7396';
            }
            case '0': {
                return '\u96f6';
            }
            case '-': {
                return '\u8d1f';
            }
        }
        return '\u9519';
    }

    private boolean isFlagNumber(int num, boolean bool) {
        switch (num) {
            case 1: {
                return bool;
            }
            case 4: 
            case 7: 
            case 10: 
            case 13: {
                return true;
            }
        }
        return false;
    }

    private String getDataHTML(int dec, int pre, String val, int ty, String mSigh) {
        DecimalFormat doublefmt = new DecimalFormat();
        StringBuffer dataHTML = new StringBuffer();
        int length = 0;
        double percent = 100.0 / (double)pre;
        int b = (int)Math.round(percent * 100.0);
        percent = (double)b / 100.0;
        String value = val;
        doublefmt.setMaximumFractionDigits(dec);
        doublefmt.setMinimumFractionDigits(dec);
        doublefmt.setGroupingSize(0);
        value = JqLib.strExclude((String)value, (char)',');
        value = doublefmt.format(Double.parseDouble(value));
        length = value.length();
        dataHTML.append("<table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"1\" bgcolor=\"#E0E0E0\">\n");
        dataHTML.append("<tr>\n");
        if (dec == 0) {
            int flagNumber = pre;
            if (length < pre) {
                int i;
                for (i = 0; i < pre - length - 1; ++i) {
                    if (this.isFlagNumber(flagNumber, false)) {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\"></td>\n");
                    } else {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\"></td>\n");
                    }
                    --flagNumber;
                }
                if (this.isFlagNumber(flagNumber, false)) {
                    dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">" + mSigh + "</td>\n");
                } else {
                    dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\">" + mSigh + "</td>\n");
                }
                --flagNumber;
                for (i = 0; i < length; ++i) {
                    if (this.isFlagNumber(flagNumber, false)) {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">" + this.formatNumber(value.charAt(i), ty) + "</td>\n");
                    } else {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" >" + this.formatNumber(value.charAt(i), ty) + "</td>\n");
                    }
                    --flagNumber;
                }
            } else {
                for (int i = 0; i < pre; ++i) {
                    if (this.isFlagNumber(flagNumber, false)) {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">" + this.formatNumber(value.charAt(length - pre + i), ty) + "</td>\n");
                    } else {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" >" + this.formatNumber(value.charAt(length - pre + i), ty) + "</td>\n");
                    }
                    --flagNumber;
                }
            }
        } else {
            int flagNumber = pre - 2;
            if (length - 1 < pre) {
                int i;
                for (i = 0; i < pre - length; ++i) {
                    if (this.isFlagNumber(flagNumber, true)) {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\"></td>\n");
                    } else {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\"></td>\n");
                    }
                    --flagNumber;
                }
                if (this.isFlagNumber(flagNumber, true)) {
                    dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">" + mSigh + "</td>\n");
                } else {
                    dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\">" + mSigh + "</td>\n");
                }
                --flagNumber;
                for (i = 0; i < length - 3; ++i) {
                    if (this.isFlagNumber(flagNumber, true)) {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">" + this.formatNumber(value.charAt(i), ty) + "</td>\n");
                    } else {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" >" + this.formatNumber(value.charAt(i), ty) + "</td>\n");
                    }
                    --flagNumber;
                }
            } else {
                for (int i = 0; i < pre - 2; ++i) {
                    if (this.isFlagNumber(flagNumber, true)) {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">" + this.formatNumber(value.charAt(length - 1 - pre + i), ty) + "</td>\n");
                    } else {
                        dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" >" + this.formatNumber(value.charAt(length - 1 - pre + i), ty) + "</td>\n");
                    }
                    --flagNumber;
                }
            }
            dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-LEFT: black 1px solid;\">" + this.formatNumber(value.charAt(length - 2), ty) + "</td>\n");
            dataHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" >" + this.formatNumber(value.charAt(length - 1), ty) + "</td>\n");
        }
        dataHTML.append("</tr>\n");
        dataHTML.append("</table>\n");
        return dataHTML.toString();
    }

    private String getTitleHTML(int dec, int pre) {
        StringBuffer titleHTML = new StringBuffer();
        double percent = 100.0 / (double)pre;
        int b = (int)Math.round(percent * 100.0);
        percent = (double)b / 100.0;
        titleHTML.append("<table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"1\" bgcolor=\"#E0E0E0\">\n");
        titleHTML.append("<tr>\n");
        block8: for (int i = pre - dec; i >= 1; --i) {
            switch (i) {
                case 9: {
                    titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\">\u4ebf</td>\n");
                    continue block8;
                }
                case 5: 
                case 13: {
                    if (i == 13) {
                        titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">\u4e07</td>\n");
                        continue block8;
                    }
                    titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\">\u4e07</td>\n");
                    continue block8;
                }
                case 4: 
                case 8: 
                case 12: {
                    if (i == 4) {
                        titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">\u5343</td>\n");
                        continue block8;
                    }
                    titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\">\u5343</td>\n");
                    continue block8;
                }
                case 3: 
                case 7: 
                case 11: {
                    if (i == 7) {
                        titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">\u767e</td>\n");
                        continue block8;
                    }
                    titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\">\u767e</td>\n");
                    continue block8;
                }
                case 2: 
                case 6: 
                case 10: {
                    if (i == 10) {
                        titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">\u5341</td>\n");
                        continue block8;
                    }
                    titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\">\u5341</td>\n");
                    continue block8;
                }
                case 1: {
                    if (dec == 2) {
                        titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-RIGHT: black 1px solid;\">\u5143</td>\n");
                        continue block8;
                    }
                    titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" >\u5143</td>\n");
                    continue block8;
                }
            }
        }
        if (dec == 2) {
            titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"BORDER-LEFT: black 1px solid;\">\u89d2</td>\n");
            titleHTML.append("<td width=\"" + percent + "%\" align=\"center\" bgcolor=\"#FFFFFF\">\u5206</td>\n");
        }
        titleHTML.append("</tr>\n");
        titleHTML.append("</table>\n");
        return titleHTML.toString();
    }
}

