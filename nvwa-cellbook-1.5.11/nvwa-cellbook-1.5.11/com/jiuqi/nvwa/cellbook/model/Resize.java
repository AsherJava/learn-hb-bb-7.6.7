/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;

public class Resize
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean row;
    private boolean column;

    public boolean isRow() {
        return this.row;
    }

    public void setRow(boolean row) {
        this.row = row;
    }

    public boolean isColumn() {
        return this.column;
    }

    public void setColumn(boolean column) {
        this.column = column;
    }
}

