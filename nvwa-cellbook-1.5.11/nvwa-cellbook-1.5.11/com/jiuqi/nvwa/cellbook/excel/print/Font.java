/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import java.io.Serializable;

public final class Font
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -5898896434762165325L;
    private String family;
    private float size;
    private String color;
    private boolean bold;
    private boolean italic;
    private boolean underline;

    public String getFamily() {
        return this.family;
    }

    public float getSize() {
        return this.size;
    }

    public String getColor() {
        return this.color;
    }

    public boolean isBold() {
        return this.bold;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

