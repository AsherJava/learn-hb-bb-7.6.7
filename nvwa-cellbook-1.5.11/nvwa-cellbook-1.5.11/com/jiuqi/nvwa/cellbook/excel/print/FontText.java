/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.excel.print.Font;
import java.io.Serializable;

public final class FontText
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -4120883524002168613L;
    private String text;
    private Font font;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return this.font;
    }

    protected Object clone() {
        try {
            FontText cloned = (FontText)super.clone();
            cloned.font = this.font == null ? null : (Font)this.font.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

