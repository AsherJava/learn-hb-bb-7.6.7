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
import java.util.LinkedHashMap;
import java.util.Map;

public class SumTemplate {
    private static Map<String, Integer> fixedColumns = new LinkedHashMap<String, Integer>(1);
    private static GridCellStyleData fixedCellStyle;
    private static GridCellData fixedCell;
    private static GridCellStyleData cellStyle;
    private static GridCellData cell;
    private static final String DEFAULT_SHOW_TEXT = "\u2014\u2014\u2014\u2014";

    public static Map<String, Integer> getFixedColumns() {
        LinkedHashMap<String, Integer> re = new LinkedHashMap<String, Integer>(1);
        re.putAll(fixedColumns);
        return re;
    }

    public static GridCellStyleData getFixedGridCellStyleData() {
        GridCellData reCall = new GridCellData(1, 1);
        reCall.copyCellData(fixedCell);
        return reCall.getCellStyleData();
    }

    public static GridCellStyleData getGridCellStyleData() {
        GridCellData reCall = new GridCellData(1, 1);
        reCall.copyCellData(cell);
        return reCall.getCellStyleData();
    }

    public static String getDefaultShowText() {
        return DEFAULT_SHOW_TEXT;
    }

    static {
        fixedColumns.put("\u5408\u8ba1", 2);
        fixedCellStyle = new GridCellStyleData();
        fixedCell = new GridCellData(1, 1);
        fixedCellStyle.setBottomBorderStyle(1);
        fixedCellStyle.setRightBorderStyle(1);
        fixedCellStyle.setFontSize(12);
        fixedCellStyle.setHorzAlign(3);
        fixedCellStyle.setEditable(false);
        fixedCellStyle.setSelectable(false);
        fixedCellStyle.setBackGroundColor(Integer.parseInt("f2f2f2", 16));
        fixedCellStyle.setFontStyle(1);
        fixedCell.setCellStyleData(fixedCellStyle);
        cellStyle = new GridCellStyleData();
        cell = new GridCellData(1, 1);
        cellStyle.setBottomBorderStyle(1);
        cellStyle.setRightBorderStyle(1);
        cellStyle.setFontSize(12);
        cellStyle.setHorzAlign(3);
        cell.setCellStyleData(cellStyle);
    }
}

