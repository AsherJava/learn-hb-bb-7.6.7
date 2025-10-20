/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.format;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class BooleanFormat
extends Format {
    private static final long serialVersionUID = -1079427146559230210L;
    private final String showPattern;

    public BooleanFormat(BIDataSetFieldInfo info) {
        this(info.getShowPattern());
    }

    public BooleanFormat(String pattern) {
        this.showPattern = pattern;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj == null) {
            return toAppendTo;
        }
        boolean b = (Boolean)obj;
        if (this.showPattern != null && this.showPattern.length() != 0) {
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
        toAppendTo.append(b ? "\u662f" : "\u5426");
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return null;
    }
}

