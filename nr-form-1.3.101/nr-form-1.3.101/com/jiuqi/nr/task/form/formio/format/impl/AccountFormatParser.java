/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format.impl;

import com.jiuqi.nr.task.form.formio.format.Currency;
import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.FormatType;
import com.jiuqi.nr.task.form.formio.format.NegativeStyle;
import com.jiuqi.nr.task.form.formio.format.impl.AbstractFormatParser;
import java.util.Arrays;
import java.util.List;

class AccountFormatParser
extends AbstractFormatParser {
    private final List<String> startWith = Arrays.asList("_ \"\uffe5\"* ", "_-* ", "_-\\$* ");
    private final List<String> endWith = Arrays.asList("_ ", "\\ \"\u20ac\"_-", "_ ");

    AccountFormatParser() {
    }

    @Override
    public boolean supports(String[] formats) {
        String formatString = formats[0];
        if (!formatString.startsWith("_")) {
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            if (!formatString.startsWith(this.startWith.get(i)) || !formatString.endsWith(this.endWith.get(i))) continue;
            return true;
        }
        return false;
    }

    @Override
    public FormatDTO parse(String[] formats) {
        int i;
        String format = formats[0];
        FormatDTO formatDTO = new FormatDTO();
        formatDTO.setFormatType(FormatType.ACCOUNTING);
        formatDTO.setNegativeStyle(NegativeStyle.NS_0);
        formatDTO.setDecimal(0);
        if (format.contains("\uffe5")) {
            formatDTO.setCurrency(Currency.CNY);
        } else if (format.contains("\u20ac")) {
            formatDTO.setCurrency(Currency.EUR);
        } else if (format.contains("$")) {
            formatDTO.setCurrency(Currency.USD);
        }
        if (format.contains("#,#")) {
            formatDTO.setHasThousand(true);
        }
        if ((i = format.indexOf(".")) > 0) {
            int decimal = 0;
            for (int j = i + 1; j < format.length() && format.charAt(j) == '0'; ++j) {
                ++decimal;
            }
            formatDTO.setDecimal(decimal);
        }
        if (this.check(formatDTO, formats)) {
            return formatDTO;
        }
        return null;
    }

    private boolean check(FormatDTO formatDTO, String[] formats) {
        StringBuilder sb1 = new StringBuilder();
        if (formatDTO.getCurrency() == Currency.CNY) {
            sb1.append("_ \"\uffe5\"* ");
            this.appendThousand(formatDTO, sb1);
            this.appendDecimal(formatDTO, sb1);
            sb1.append("_ ");
        } else if (formatDTO.getCurrency() == Currency.USD) {
            sb1.append("_-\\$* ");
            this.appendThousand(formatDTO, sb1);
            this.appendDecimal(formatDTO, sb1);
            sb1.append("_ ");
        } else if (formatDTO.getCurrency() == Currency.EUR) {
            sb1.append("_-* ");
            this.appendThousand(formatDTO, sb1);
            this.appendDecimal(formatDTO, sb1);
            sb1.append("\\ \"\u20ac\"_-");
        }
        return sb1.toString().equals(formats[0]);
    }

    private void appendDecimal(FormatDTO parse, StringBuilder sb1) {
        if (parse.getDecimal() > 0) {
            sb1.append(".");
            for (int i = 0; i < parse.getDecimal(); ++i) {
                sb1.append("0");
            }
        }
    }

    private void appendThousand(FormatDTO parse, StringBuilder sb1) {
        if (parse.isHasThousand()) {
            sb1.append("#,##0");
        }
    }
}

