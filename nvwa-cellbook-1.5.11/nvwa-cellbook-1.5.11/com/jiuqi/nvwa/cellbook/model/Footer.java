/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;

public class Footer
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rowFooter = -1;
    private int columnFooter = -1;

    public int getRowFooter() {
        return this.rowFooter;
    }

    public int getColumnFooter() {
        return this.columnFooter;
    }

    public void setRowFooter(int rowFooter) {
        this.rowFooter = rowFooter;
    }

    public void setColumnFooter(int columnFooter) {
        this.columnFooter = columnFooter;
    }
}

