/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.excel.print.HeaderFooter;
import java.io.Serializable;

public final class DifferentOddEven
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 4176456371324523650L;
    private boolean enable;
    private HeaderFooter odd;
    private HeaderFooter even;

    public boolean isEnable() {
        return this.enable;
    }

    public HeaderFooter getOdd() {
        return this.odd;
    }

    public HeaderFooter getEven() {
        return this.even;
    }

    protected Object clone() {
        try {
            DifferentOddEven cloned = (DifferentOddEven)super.clone();
            cloned.odd = this.odd == null ? null : (HeaderFooter)this.odd.clone();
            cloned.even = this.even == null ? null : (HeaderFooter)this.even.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

