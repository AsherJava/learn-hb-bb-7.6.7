/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.analysisreport.utils.Col
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.var.common.vo;

import com.jiuqi.nr.analysisreport.utils.Col;
import com.jiuqi.nvwa.grid2.GridCellData;

public class HtmlTableCellInfo {
    private int i;
    private int j;
    private Col col;
    private String showText;
    private GridCellData gridCellData;
    private int colspan;

    public int getI() {
        return this.i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return this.j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public GridCellData getGridCellData() {
        return this.gridCellData;
    }

    public void setGridCellData(GridCellData gridCellData) {
        this.gridCellData = gridCellData;
    }

    public Col getCol() {
        return this.col;
    }

    public void setCol(Col col) {
        this.col = col;
    }

    public String getShowText() {
        return this.showText;
    }

    public int getColspan() {
        return this.colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public HtmlTableCellInfo(int i, int j, Col col, GridCellData gridCellData) {
        this.i = i;
        this.j = j;
        this.gridCellData = gridCellData;
        this.col = col;
    }
}

