/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 */
package com.jiuqi.gcreport.formsetting.template;

import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;

public class FloatTemplate {
    private static GridCellStyleData cellCenterStyle;
    private static GridCellStyleData cellRightStyle;
    private static GridCellStyleData cellStyle;
    private static GridCellData cell;
    private static GridCellData cellRight;
    private static GridCellData cellCenter;

    public static GridCellStyleData getGridCellStyleData() {
        GridCellData reCall = new GridCellData(1, 1);
        reCall.copyCellData(cell);
        return reCall.getCellStyleData();
    }

    public static GridCellStyleData getGridCellRightStyleData() {
        GridCellData reCall = new GridCellData(1, 1);
        reCall.copyCellData(cellRight);
        return reCall.getCellStyleData();
    }

    public static GridCellStyleData getGridCellCenterStyleData() {
        GridCellData reCall = new GridCellData(1, 1);
        reCall.copyCellData(cellCenter);
        return reCall.getCellStyleData();
    }

    static {
        cellStyle = new GridCellStyleData();
        cell = new GridCellData(1, 1);
        cellStyle.setFontSize(12);
        cellStyle.setHorzAlign(1);
        cellStyle.setBottomBorderStyle(1);
        cellStyle.setRightBorderStyle(1);
        cell.setCellStyleData(cellStyle);
        cellCenterStyle = new GridCellStyleData();
        cellCenter = new GridCellData(1, 1);
        cellCenterStyle.setFontSize(12);
        cellCenterStyle.setHorzAlign(3);
        cellCenterStyle.setBottomBorderStyle(1);
        cellCenterStyle.setRightBorderStyle(1);
        cellCenter.setCellStyleData(cellCenterStyle);
        cellRightStyle = new GridCellStyleData();
        cellRight = new GridCellData(1, 1);
        cellRightStyle.setFontSize(12);
        cellRightStyle.setHorzAlign(2);
        cellRightStyle.setBottomBorderStyle(1);
        cellRightStyle.setRightBorderStyle(1);
        cellRight.setCellStyleData(cellRightStyle);
    }
}

