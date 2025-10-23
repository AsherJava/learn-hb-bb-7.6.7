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
import java.util.function.Predicate;

class CurrencyFormatParser
extends AbstractFormatParser {
    public List<Predicate<String[]>> predicates = Arrays.asList(formats -> {
        String format0 = formats[0];
        String format1 = formats[1];
        return format0.contains("\uffe5") && format1.contains("\uffe5");
    }, formats -> {
        String format0 = formats[0];
        String format1 = formats[1];
        return format0.contains("\\$") && format1.contains("\\$");
    }, formats -> {
        String format0 = formats[0];
        String format1 = formats[1];
        return format0.contains("\u20ac") && format1.contains("\u20ac");
    });

    @Override
    public boolean supports(String[] formats) {
        if (formats[0].startsWith("_")) {
            return false;
        }
        return this.predicates.stream().anyMatch(predicate -> predicate.test(formats));
    }

    @Override
    public FormatDTO parse(String format) {
        FormatDTO formatDTO = new FormatDTO();
        formatDTO.setFormatType(FormatType.CURRENCY);
        formatDTO.setCurrency(Currency.CNY);
        formatDTO.setNegativeStyle(NegativeStyle.NS_0);
        formatDTO.setHasThousand(true);
        char[] chars = format.toCharArray();
        boolean hasPoint = false;
        int len = chars.length;
        int decimal = 0;
        block9: for (int i = 0; i < chars.length; ++i) {
            switch (chars[i]) {
                case '\uffe5': {
                    if (i != 1) {
                        return null;
                    }
                    formatDTO.setCurrency(Currency.CNY);
                    continue block9;
                }
                case '$': {
                    if (i != 1) {
                        return null;
                    }
                    formatDTO.setCurrency(Currency.USD);
                    continue block9;
                }
                case '\u20ac': {
                    if (i != len - 2 && i != len - 4) {
                        return null;
                    }
                    formatDTO.setCurrency(Currency.EUR);
                    continue block9;
                }
                case ',': {
                    formatDTO.setHasThousand(true);
                    continue block9;
                }
                case ')': {
                    if (chars[i - 1] != '_') {
                        return null;
                    }
                    formatDTO.setNegativeStyle(NegativeStyle.NS_1);
                    continue block9;
                }
                case '.': {
                    if (chars[i - 1] != '0') {
                        return null;
                    }
                    hasPoint = true;
                    continue block9;
                }
                case '0': {
                    if (!hasPoint) continue block9;
                    ++decimal;
                    continue block9;
                }
            }
        }
        formatDTO.setDecimal(decimal);
        return formatDTO;
    }

    @Override
    public FormatDTO parse(String[] formats) {
        String format0 = formats[0];
        FormatDTO parse = this.parse(format0);
        if (parse != null && this.check(parse, formats)) {
            return parse;
        }
        return null;
    }

    private boolean check(FormatDTO parse, String[] formats) {
        StringBuilder sb1 = new StringBuilder();
        switch (parse.getCurrency()) {
            case CNY: {
                sb1.append("\"\uffe5\"");
                this.appendThousand(parse, sb1);
                this.appendDecimal(parse, sb1);
                this.appendNegativeStyle(parse, sb1);
                break;
            }
            case USD: {
                sb1.append("\\$");
                this.appendThousand(parse, sb1);
                this.appendDecimal(parse, sb1);
                this.appendNegativeStyle(parse, sb1);
                break;
            }
            case EUR: {
                this.appendThousand(parse, sb1);
                this.appendDecimal(parse, sb1);
                sb1.append("\\ \"\u20ac\"");
                this.appendNegativeStyle(parse, sb1);
            }
        }
        return sb1.toString().equals(formats[0]);
    }

    private void appendNegativeStyle(FormatDTO parse, StringBuilder sb1) {
        if (parse.getNegativeStyle() == NegativeStyle.NS_1) {
            sb1.append("_)");
        }
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

