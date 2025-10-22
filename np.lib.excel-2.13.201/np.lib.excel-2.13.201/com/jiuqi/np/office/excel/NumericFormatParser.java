/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel;

public class NumericFormatParser {
    private int decimal;
    private boolean parcent;
    private boolean thoundsMarks;
    private boolean chineseNumber;
    private boolean bigChineseNumber;
    private boolean bracketNegative;
    private boolean warnNegative;
    private boolean currency;
    private int currencyType;
    private int currencyShowType;
    private boolean dateType;
    private int dateShowType;
    private String source;
    private boolean general;

    public NumericFormatParser(String source) {
        this.source = source != null ? source : "";
        this.init();
    }

    private void init() {
        this.general = false;
        this.DateTypeInit();
        if (this.isDateType()) {
            return;
        }
        if (this.source.indexOf("General") >= 0) {
            this.general = true;
            this.currency = false;
        } else if (this.source.indexOf("($") >= 0 || this.source.indexOf("\"\uffe5\"") >= 0 || this.source.indexOf("[$\uffe5-804]") >= 0) {
            this.currency = true;
            this.currencyType = 1;
            this.currencyShowType = 0;
        } else if (this.source.indexOf("[$CNY]") >= 0) {
            this.currency = true;
            this.currencyType = 1;
            this.currencyShowType = 1;
        } else if (this.source.indexOf("\\$") >= 0 || this.source.indexOf("[$$-409]") >= 0) {
            this.currency = true;
            this.currencyType = 2;
            this.currencyShowType = 0;
        } else if (this.source.indexOf("[$USD]") >= 0) {
            this.currency = true;
            this.currencyType = 2;
            this.currencyShowType = 1;
        } else if (this.source.indexOf("[$EUR]") >= 0) {
            this.currency = true;
            this.currencyType = 3;
            this.currencyShowType = 0;
        } else if (this.source.indexOf("[$JPY]") >= 0) {
            this.currency = true;
            this.currencyType = 4;
            this.currencyShowType = 1;
        } else if (this.source.indexOf("[$HK$-C04]") >= 0) {
            this.currency = true;
            this.currencyType = 5;
            this.currencyShowType = 0;
        } else if (this.source.indexOf("[$HKD]") >= 0) {
            this.currency = true;
            this.currencyType = 5;
            this.currencyShowType = 1;
        } else if (this.source.indexOf("[$\u00a3-809]") >= 0) {
            this.currency = true;
            this.currencyType = 6;
            this.currencyShowType = 0;
        } else if (this.source.indexOf("[$GBP]") >= 0) {
            this.currency = true;
            this.currencyType = 6;
            this.currencyShowType = 1;
        } else if (this.source.indexOf("[$CHF]") >= 0) {
            this.currency = true;
            this.currencyType = 7;
            this.currencyShowType = 1;
        } else if (this.source.indexOf("[$CAD]") >= 0) {
            this.currency = true;
            this.currencyType = 9;
            this.currencyShowType = 1;
        } else {
            this.currency = false;
        }
        this.decimal = 0;
        if (this.source.indexOf(46) >= 0) {
            int length = this.source.length();
            for (int i = this.source.indexOf(46) + 1; i < length && this.source.charAt(i) == '0'; ++i) {
                ++this.decimal;
            }
        }
        this.parcent = false;
        if (this.source.indexOf("%") >= 0) {
            this.parcent = true;
        }
        this.bigChineseNumber = false;
        if (this.source.indexOf("DBNum2") >= 0) {
            this.bigChineseNumber = true;
        }
        this.chineseNumber = false;
        if (this.source.indexOf("DBNum1") >= 0 || this.isBigChineseNumber()) {
            this.chineseNumber = true;
        }
        this.thoundsMarks = false;
        if (this.source.indexOf("#,##") >= 0 || this.isChineseNumber()) {
            this.thoundsMarks = true;
        }
        this.bracketNegative = false;
        if (this.source.indexOf(40) >= 0 && this.source.indexOf(41) >= 0) {
            this.bracketNegative = true;
        }
        this.warnNegative = false;
        if (this.source.indexOf("[Red]") >= 0 && this.source.indexOf("[Red]") > this.source.indexOf(";")) {
            this.warnNegative = true;
        }
    }

    private void DateTypeInit() {
        this.dateType = true;
        if (this.source.equals("m/d/yy")) {
            this.dateShowType = 0;
        } else if (this.source.equals("[$-F800]dddd\\,\\ mmmm\\ dd\\,\\ yyyy")) {
            this.dateShowType = 7;
        } else if (this.source.equals("[DBNum1][$-804]yyyy\"\u5e74\"m\"\u6708\"d\"\u65e5\";@")) {
            this.dateShowType = 7;
        } else if (this.source.equals("[DBNum1][$-804]yyyy\"\u5e74\"m\"\u6708\";@")) {
            this.dateShowType = 8;
        } else if (this.source.equals("[DBNum1][$-804]m\"\u6708\"d\"\u65e5\";@")) {
            this.dateShowType = 9;
        } else if (this.source.equals("yyyy\"\u5e74\"m\"\u6708\"d\"\u65e5\";@")) {
            this.dateShowType = 7;
        } else if (this.source.equals("yyyy\"\u5e74\"m\"\u6708\";@")) {
            this.dateShowType = 8;
        } else if (this.source.equals("m\"\u6708\"d\"\u65e5\";@")) {
            this.dateShowType = 9;
        } else if (this.source.equals("[$-804]aaaa;@")) {
            this.dateShowType = 0;
        } else if (this.source.equals("[$-804]aaa;@")) {
            this.dateShowType = 0;
        } else if (this.source.equals("yyyy/m/d;@")) {
            this.dateShowType = 0;
        } else if (this.source.equals("[$-409]yyyy/m/d\\ h:mm\\ AM/PM;@")) {
            this.dateShowType = 1;
        } else if (this.source.equals("yyyy/m/d\\ h:mm;@")) {
            this.dateShowType = 2;
        } else if (this.source.equals("yy/m/d;@")) {
            this.dateShowType = 3;
        } else if (this.source.equals("m/d;@")) {
            this.dateShowType = 6;
        } else if (this.source.equals("m/d/yy;@")) {
            this.dateShowType = 4;
        } else if (this.source.equals("mm/dd/yy;@")) {
            this.dateShowType = 4;
        } else if (this.source.equals("[$-409]d/mmm;@")) {
            this.dateShowType = 13;
        } else if (this.source.equals("[$-409]d/mmm/yy;@")) {
            this.dateShowType = 10;
        } else if (this.source.equals("[$-409]dd/mmm/yy;@")) {
            this.dateShowType = 10;
        } else if (this.source.equals("[$-409]mmm/yy;@")) {
            this.dateShowType = 12;
        } else if (this.source.equals("[$-409]mmmm/yy;@")) {
            this.dateShowType = 12;
        } else if (this.source.equals("[$-409]mmmmm;@")) {
            this.dateShowType = 12;
        } else if (this.source.equals("[$-409]mmmmm/yy;@")) {
            this.dateShowType = 12;
        } else if (this.source.equals("[$-F400]h:mm:ss\\ AM/PM")) {
            this.dateShowType = 2;
        } else if (this.source.equals("h:mm;@")) {
            this.dateShowType = 2;
        } else if (this.source.equals("[$-409]h:mm\\ AM/PM;@")) {
            this.dateShowType = 1;
        } else if (this.source.equals("h:mm:ss;@")) {
            this.dateShowType = 2;
        } else if (this.source.equals("[$-409]h:mm:ss\\ AM/PM;@")) {
            this.dateShowType = 1;
        } else if (this.source.equals("h\"\u65f6\"mm\"\u5206\";@")) {
            this.dateShowType = 2;
        } else if (this.source.equals("h\"\u65f6\"mm\"\u5206\"ss\"\u79d2\";@")) {
            this.dateShowType = 2;
        } else if (this.source.equals("\u4e0a\u5348/\u4e0b\u5348h\"\u65f6\"mm\"\u5206\";@")) {
            this.dateShowType = 1;
        } else if (this.source.equals("\u4e0a\u5348/\u4e0b\u5348h\"\u65f6\"mm\"\u5206\"ss\"\u79d2\";@")) {
            this.dateShowType = 1;
        } else if (this.source.equals("[DBNum1][$-804]h\"\u65f6\"mm\"\u5206\";@")) {
            this.dateShowType = 2;
        } else if (this.source.equals("[DBNum1][$-804]\u4e0a\u5348/\u4e0b\u5348h\"\u65f6\"mm\"\u5206\";@")) {
            this.dateShowType = 1;
        } else {
            this.dateType = false;
        }
    }

    public int getDecimal() {
        return this.decimal;
    }

    public boolean isParcent() {
        return this.parcent;
    }

    public boolean isThoundsMarks() {
        return this.thoundsMarks;
    }

    public boolean isChineseNumber() {
        return this.chineseNumber;
    }

    public boolean isBigChineseNumber() {
        return this.bigChineseNumber;
    }

    public boolean isBracketNegative() {
        return this.bracketNegative;
    }

    public boolean isWarnNegative() {
        return this.warnNegative;
    }

    public boolean isCurrency() {
        return this.currency;
    }

    public int getCurrencyType() {
        if (this.currency) {
            return this.currencyType;
        }
        return -1;
    }

    public int getCurrencyShowType() {
        if (this.currency) {
            return this.currencyShowType;
        }
        return -1;
    }

    public boolean isDateType() {
        return this.dateType;
    }

    public int getDateShowType() {
        return this.dateShowType;
    }

    public boolean isGeneral() {
        return this.general;
    }
}

