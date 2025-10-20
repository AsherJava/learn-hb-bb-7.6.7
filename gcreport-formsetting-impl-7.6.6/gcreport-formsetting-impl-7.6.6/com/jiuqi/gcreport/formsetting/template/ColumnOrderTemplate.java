/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid2.GridCellData
 *  com.jiuqi.np.grid2.GridCellStyleData
 */
package com.jiuqi.gcreport.formsetting.template;

import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.grid2.GridCellStyleData;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColumnOrderTemplate {
    private static Map<String, Integer> fixedColumns = new LinkedHashMap<String, Integer>(1);
    private static GridCellStyleData cellStyle;
    private static GridCellData cell;

    public static Map<String, Integer> getFixedColumns() {
        LinkedHashMap<String, Integer> re = new LinkedHashMap<String, Integer>(1);
        re.putAll(fixedColumns);
        return re;
    }

    public static GridCellStyleData getGridCellStyleData() {
        GridCellData reCall = new GridCellData(1, 1);
        reCall.copyCellData(cell);
        return reCall.getCellStyleData();
    }

    public static String getShowText(int linkIndex) {
        return String.valueOf(linkIndex + 1);
    }

    static {
        fixedColumns.put("\u680f\u6b21", 1);
        cellStyle = new GridCellStyleData();
        cell = new GridCellData(1, 1);
        cellStyle.setBottomBorderStyle(1);
        cellStyle.setRightBorderStyle(1);
        cellStyle.setFontSize(12);
        cellStyle.setHorzAlign(3);
        cellStyle.setEditable(false);
        cellStyle.setSelectable(false);
        cell.setCellStyleData(cellStyle);
    }
}

