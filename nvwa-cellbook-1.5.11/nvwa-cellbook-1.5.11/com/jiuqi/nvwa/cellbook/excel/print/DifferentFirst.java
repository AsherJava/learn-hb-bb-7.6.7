/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.excel.print.HeaderFooter;
import java.io.Serializable;

public final class DifferentFirst
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 3208384059926347195L;
    private boolean enable;
    private HeaderFooter first;

    public boolean isEnable() {
        return this.enable;
    }

    public HeaderFooter getFirst() {
        return this.first;
    }

    protected Object clone() {
        try {
            DifferentFirst cloned = (DifferentFirst)super.clone();
            cloned.first = this.first == null ? null : (HeaderFooter)this.first.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

