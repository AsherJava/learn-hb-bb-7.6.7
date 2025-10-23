/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format.impl;

import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.FormatType;
import com.jiuqi.nr.task.form.formio.format.impl.AbstractFormatParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PercentageParser
extends AbstractFormatParser {
    private static final Pattern pattern = Pattern.compile("^([0#]+)(?:\\.([0#]+))?%$");
    private Matcher matcher;

    PercentageParser() {
    }

    @Override
    public boolean supports(String[] format) {
        String formatString = format[0];
        this.matcher = pattern.matcher(formatString);
        return this.matcher.find();
    }

    @Override
    public FormatDTO parse(String[] formats) {
        int decimalPlaces = 0;
        if (this.matcher.group(2) != null) {
            decimalPlaces = this.matcher.group(2).length();
        }
        FormatDTO format = new FormatDTO();
        format.setFormatType(FormatType.PERCENTAGE);
        format.setDecimal(decimalPlaces);
        this.matcher = null;
        return format;
    }
}

