/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.excel.print.DifferentFirst;
import com.jiuqi.nvwa.cellbook.excel.print.DifferentOddEven;
import com.jiuqi.nvwa.cellbook.excel.print.HeaderFooter;
import java.io.Serializable;

public final class HeaderFooterSetting
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -8944876135699349611L;
    private HeaderFooter headerFooter;
    private DifferentOddEven differentOddEven;
    private DifferentFirst differentFirst;
    private boolean scaleWithDoc;
    private boolean alignWithMargins;

    public HeaderFooter getHeaderFooter() {
        return this.headerFooter;
    }

    public DifferentOddEven getDifferentOddEven() {
        return this.differentOddEven;
    }

    public DifferentFirst getDifferentFirst() {
        return this.differentFirst;
    }

    public boolean isScaleWithDoc() {
        return this.scaleWithDoc;
    }

    public boolean isAlignWithMargins() {
        return this.alignWithMargins;
    }

    protected Object clone() {
        try {
            HeaderFooterSetting cloned = (HeaderFooterSetting)super.clone();
            cloned.headerFooter = this.headerFooter == null ? null : (HeaderFooter)this.headerFooter.clone();
            cloned.differentOddEven = this.differentOddEven == null ? null : (DifferentOddEven)this.differentOddEven.clone();
            cloned.differentFirst = this.differentFirst == null ? null : (DifferentFirst)this.differentFirst.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

