/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;

public class Header
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rowHeader = -1;
    private int columnHeader = -1;

    public int getRowHeader() {
        return this.rowHeader;
    }

    public void setRowHeader(int rowHeader) {
        this.rowHeader = rowHeader;
    }

    public int getColumnHeader() {
        return this.columnHeader;
    }

    public void setColumnHeader(int columnHeader) {
        this.columnHeader = columnHeader;
    }
}

