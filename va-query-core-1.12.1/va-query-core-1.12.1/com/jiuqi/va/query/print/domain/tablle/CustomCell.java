/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.layout.element.Cell
 */
package com.jiuqi.va.query.print.domain.tablle;

import com.itextpdf.layout.element.Cell;
import com.jiuqi.va.query.print.BorderDTO;

public class CustomCell
extends Cell {
    private BorderDTO borderDTO;
    private int rowIndex;
    private int columnIndex;

    public CustomCell() {
    }

    public CustomCell(int spanRow, int spanColumn) {
        super(spanRow, spanColumn);
    }

    public BorderDTO getBorderDTO() {
        return this.borderDTO;
    }

    public void setBorderDTO(BorderDTO borderDTO) {
        this.borderDTO = borderDTO;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}

