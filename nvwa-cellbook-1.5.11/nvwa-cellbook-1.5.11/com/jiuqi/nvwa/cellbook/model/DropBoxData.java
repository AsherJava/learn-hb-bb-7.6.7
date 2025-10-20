/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.util.List;

public class DropBoxData {
    private boolean byCol;
    private Integer rowNum;
    private Integer colNum;
    private String enueName;
    private List<String> values;

    public boolean isByCol() {
        return this.byCol;
    }

    public void setByCol(boolean byCol) {
        this.byCol = byCol;
    }

    public Integer getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColNum() {
        return this.colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public String getEnueName() {
        return this.enueName;
    }

    public void setEnueName(String enueName) {
        this.enueName = enueName;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}

