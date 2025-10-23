/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format.impl;

import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.IFormatParser;

public abstract class AbstractFormatParser
implements IFormatParser {
    @Override
    public boolean supportFormatParts() {
        return true;
    }

    @Override
    public FormatDTO parse(String format) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

