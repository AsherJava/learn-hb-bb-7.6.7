/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class BooleanFormat
extends Format {
    private final String missingString;
    private String trueString = "true";
    private String falseString = "false";
    private static final long serialVersionUID = 783974429208667489L;

    public BooleanFormat() {
        this(Boolean.TRUE.toString(), Boolean.FALSE.toString());
    }

    public BooleanFormat(String trueString, String falseString) {
        this(trueString, falseString, "");
    }

    public BooleanFormat(String trueString, String falseString, String missingString) {
        this.missingString = "";
        this.trueString = trueString;
        this.falseString = falseString;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj == null || obj instanceof Boolean) {
            return this.format((Boolean)obj, toAppendTo);
        }
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        if (source != null) {
            source = source.trim();
        }
        return null;
    }

    public StringBuffer format(Boolean value, StringBuffer toAppendTo) {
        if (value == null) {
            toAppendTo.append(this.getMissingString());
        } else if (value.booleanValue()) {
            toAppendTo.append(this.trueString);
        } else {
            toAppendTo.append(this.falseString);
        }
        return toAppendTo;
    }

    public String getMissingString() {
        return this.missingString;
    }
}

