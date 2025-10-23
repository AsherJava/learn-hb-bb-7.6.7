/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format.impl;

import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.FormatType;
import com.jiuqi.nr.task.form.formio.format.impl.AbstractFormatParser;

class DateParser
extends AbstractFormatParser {
    private static final String DATE = "yyyy\"\u5e74\"m\"\u6708\"d\"\u65e5\"";
    private static final String DATE_TIME = "h:mm:ss";

    DateParser() {
    }

    @Override
    public boolean supports(String[] format) {
        return DATE.equals(format[0]) || DATE_TIME.equals(format[0]);
    }

    @Override
    public FormatDTO parse(String[] formats) {
        FormatDTO format = new FormatDTO();
        if (DATE.equals(formats[0])) {
            format.setFormatType(FormatType.DATE);
        } else {
            format.setFormatType(FormatType.DATE_TIME);
        }
        return format;
    }
}

