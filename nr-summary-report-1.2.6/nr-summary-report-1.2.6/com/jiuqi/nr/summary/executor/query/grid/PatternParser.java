/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.executor.query.grid;

import org.springframework.util.StringUtils;

final class PatternParser {
    private int decimal = 0;
    private boolean thousandsMark;
    private boolean percent;
    private boolean thousandsPercent;

    public PatternParser(String pattern) {
        String[] strArray;
        if (StringUtils.isEmpty(pattern)) {
            return;
        }
        if (pattern.endsWith("%")) {
            this.percent = true;
            pattern = pattern.substring(0, pattern.length() - 1);
        } else if (pattern.endsWith("\u2030")) {
            this.thousandsPercent = true;
            pattern = pattern.substring(0, pattern.length() - 1);
        }
        if (pattern.contains(",")) {
            this.thousandsMark = true;
        }
        if ((strArray = pattern.split("\\.")).length > 1) {
            this.decimal = strArray[1].length();
        }
    }

    public int getDecimal() {
        return this.decimal;
    }

    public boolean isThousandsMark() {
        return this.thousandsMark;
    }

    public boolean isPercent() {
        return this.percent;
    }

    public boolean isThousandsPercent() {
        return this.thousandsPercent;
    }
}

