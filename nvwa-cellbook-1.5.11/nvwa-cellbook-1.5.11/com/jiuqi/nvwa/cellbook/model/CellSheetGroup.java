/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import java.io.Serializable;

public class CellSheetGroup
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String name;
    private CellBook cellBook;

    protected CellSheetGroup(String title, String name, CellBook cellBook) {
        this.title = title;
        this.name = name;
        this.cellBook = cellBook;
    }

    public CellSheet createSheet(String title) {
        return this.createSheet(title, title, 5, 5);
    }

    public CellSheet createSheet(String title, String code) {
        return this.createSheet(title, code, 5, 5);
    }

    public CellSheet createSheet(String title, String name, int rowNum, int colNum) {
        return this.cellBook.createSheet(title, name, rowNum, colNum, this.name);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }

    public void setCellBook(CellBook cellBook) {
        this.cellBook = cellBook;
    }
}

