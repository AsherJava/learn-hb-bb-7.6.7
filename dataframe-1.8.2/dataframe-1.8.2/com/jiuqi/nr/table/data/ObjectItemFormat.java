/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

import com.jiuqi.nr.table.data.EnumItem;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class ObjectItemFormat
extends Format {
    private static final long serialVersionUID = 2072467923371252858L;
    private boolean showCode;
    private boolean showTitle = true;
    private String separator = "|";
    private int level;

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof EnumItem) {
            return this.format((EnumItem)obj, toAppendTo, pos);
        }
        return null;
    }

    public StringBuffer format(EnumItem item, StringBuffer toAppendTo, FieldPosition pos) {
        if (this.showCode) {
            toAppendTo.append(item.getCode());
        }
        if (this.showCode && this.showTitle) {
            toAppendTo.append(this.separator);
        }
        if (this.showTitle) {
            toAppendTo.append(item.getTitle());
        }
        return toAppendTo;
    }

    @Override
    public EnumItem parseObject(String source, ParsePosition pos) {
        EnumItem it = new EnumItem();
        return it;
    }

    public void setShowCode(boolean showCode) {
        this.showCode = showCode;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

