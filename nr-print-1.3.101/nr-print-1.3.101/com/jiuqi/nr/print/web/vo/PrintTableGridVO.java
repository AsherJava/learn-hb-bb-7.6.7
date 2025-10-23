/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookDeserialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookSerialize
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.print.web.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.print.dto.PrintTableGridDTO;
import com.jiuqi.nvwa.cellbook.json.CellBookDeserialize;
import com.jiuqi.nvwa.cellbook.json.CellBookSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;

public class PrintTableGridVO {
    private String designerId;
    private String elementId;
    @JsonSerialize(using=CellBookSerialize.class)
    @JsonDeserialize(using=CellBookDeserialize.class)
    private CellBook cellBook;

    public PrintTableGridVO() {
    }

    public PrintTableGridVO(PrintTableGridDTO grid) {
        this.designerId = grid.getDesignerId();
        this.elementId = grid.getElementId();
        this.cellBook = grid.getCellBook();
    }

    public PrintTableGridDTO toPrintTableGrid() {
        PrintTableGridDTO grid = new PrintTableGridDTO();
        grid.setDesignerId(this.designerId);
        grid.setElementId(this.elementId);
        grid.setCellBook(this.cellBook);
        return grid;
    }

    public String getDesignerId() {
        return this.designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getElementId() {
        return this.elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }

    public void setCellBook(CellBook cellBook) {
        this.cellBook = cellBook;
    }
}

