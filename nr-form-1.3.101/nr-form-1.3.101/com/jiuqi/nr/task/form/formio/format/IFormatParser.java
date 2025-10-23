/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format;

import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.impl.ExcelFormatParser;

public interface IFormatParser {
    default public double order() {
        return 99.0;
    }

    public FormatDTO parse(String var1);

    public boolean supportFormatParts();

    default public boolean supports(String[] formats) {
        return true;
    }

    default public FormatDTO parse(String[] formats) {
        return null;
    }

    default public void registerFormatParser(IFormatParser ... formatParsers) {
    }

    public static interface Factory {
        public static IFormatParser newInstance() {
            return new ExcelFormatParser();
        }
    }
}

