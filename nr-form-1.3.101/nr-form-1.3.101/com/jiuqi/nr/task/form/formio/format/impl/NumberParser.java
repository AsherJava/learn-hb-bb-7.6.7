/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format.impl;

import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.FormatType;
import com.jiuqi.nr.task.form.formio.format.NegativeStyle;
import com.jiuqi.nr.task.form.formio.format.impl.AbstractFormatParser;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NumberParser
extends AbstractFormatParser {
    private static final Pattern pattern = Pattern.compile("^(#,##)?0+(.0+)?_( ?\\)?)?$");
    private Matcher matcher;

    NumberParser() {
    }

    @Override
    public boolean supports(String[] format) {
        String formatString = format[0];
        this.matcher = pattern.matcher(formatString);
        return this.matcher.find();
    }

    @Override
    public FormatDTO parse(String[] formats) {
        String group3;
        String group2;
        int count = this.matcher.groupCount();
        if (count < 3) {
            return null;
        }
        FormatDTO format = this.initFormat();
        format.setFormatType(FormatType.NUMBER);
        if ("#,##".equals(this.matcher.group(1))) {
            format.setHasThousand(true);
        }
        if ((group2 = this.matcher.group(2)) != null) {
            format.setDecimal(group2.length() - 1);
        }
        if (")".equals(group3 = this.matcher.group(3))) {
            format.setNegativeStyle(NegativeStyle.NS_1);
        }
        this.matcher = null;
        List<String> currencySigns = Arrays.asList("\"\uffe5\"", "\"$\"", "\"\u20ac\"");
        boolean anyMatch = currencySigns.stream().anyMatch(s -> formats[0].contains((CharSequence)s));
        if (!anyMatch && this.check(formats[0], format)) {
            return format;
        }
        return null;
    }

    private boolean check(String format0, FormatDTO format) {
        StringBuilder builder = new StringBuilder();
        if (format.isHasThousand()) {
            builder.append("#,##");
        }
        builder.append("0");
        if (format.getDecimal() > 0) {
            builder.append(".");
        }
        for (int i = 0; i < format.getDecimal(); ++i) {
            builder.append("0");
        }
        if (format.getNegativeStyle() == NegativeStyle.NS_1) {
            builder.append("_)");
        } else {
            builder.append("_ ");
        }
        return builder.toString().equals(format0);
    }

    private FormatDTO initFormat() {
        FormatDTO format = new FormatDTO();
        format.setFormatType(FormatType.NUMBER);
        format.setHasThousand(false);
        format.setDecimal(0);
        format.setNegativeStyle(NegativeStyle.NS_0);
        return format;
    }
}

