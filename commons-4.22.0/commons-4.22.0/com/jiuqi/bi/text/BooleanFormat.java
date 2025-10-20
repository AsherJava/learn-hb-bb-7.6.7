/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class BooleanFormat
extends Format {
    private static final long serialVersionUID = -1079427146559230210L;
    private final String showPattern;

    public BooleanFormat(String pattern) {
        this.showPattern = pattern;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj == null) {
            return toAppendTo;
        }
        boolean b = (Boolean)obj;
        if (this.showPattern == null || this.showPattern.length() == 0) {
            toAppendTo.append(b ? "\u662f" : "\u5426");
            return toAppendTo;
        }
        int index = this.showPattern.indexOf(47);
        if (index == -1) {
            toAppendTo.append(b ? "\u662f" : "\u5426");
            return toAppendTo;
        }
        String trueValue = this.showPattern.substring(0, index);
        String falseValue = this.showPattern.substring(index + 1);
        toAppendTo.append(b ? trueValue : falseValue);
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return null;
    }
}

