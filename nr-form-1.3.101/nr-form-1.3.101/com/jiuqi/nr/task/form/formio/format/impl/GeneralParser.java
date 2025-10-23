/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format.impl;

import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.FormatType;
import com.jiuqi.nr.task.form.formio.format.impl.AbstractFormatParser;

class GeneralParser
extends AbstractFormatParser {
    GeneralParser() {
    }

    @Override
    public boolean supports(String[] formats) {
        return formats[0] != null && formats[0].equals("General");
    }

    @Override
    public FormatDTO parse(String[] formats) {
        FormatDTO format = new FormatDTO();
        format.setFormatType(FormatType.GENERAL);
        return format;
    }
}

