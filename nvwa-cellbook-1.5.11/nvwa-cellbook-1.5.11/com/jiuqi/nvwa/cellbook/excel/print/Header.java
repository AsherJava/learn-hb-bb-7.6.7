/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.excel.print.FontText;
import java.io.Serializable;

public class Header
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 5372198681840949838L;
    private FontText left;
    private FontText center;
    private FontText right;

    public FontText getLeft() {
        return this.left;
    }

    public FontText getCenter() {
        return this.center;
    }

    public FontText getRight() {
        return this.right;
    }

    protected Object clone() {
        try {
            Header cloned = (Header)super.clone();
            cloned.left = this.left == null ? null : (FontText)this.left.clone();
            cloned.center = this.center == null ? null : (FontText)this.center.clone();
            cloned.right = this.right == null ? null : (FontText)this.right.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

