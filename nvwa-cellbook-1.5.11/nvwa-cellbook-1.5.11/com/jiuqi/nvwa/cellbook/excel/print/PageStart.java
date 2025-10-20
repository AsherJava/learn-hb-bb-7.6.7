/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import java.io.Serializable;

public final class PageStart
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -4430136536180089950L;
    private boolean enable;
    private int pageNum;

    public boolean isEnable() {
        return this.enable;
    }

    public int getPageNum() {
        return this.pageNum;
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

