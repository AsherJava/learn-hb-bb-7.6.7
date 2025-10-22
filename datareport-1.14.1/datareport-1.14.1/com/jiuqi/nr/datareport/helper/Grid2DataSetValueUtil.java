/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 */
package com.jiuqi.nr.datareport.helper;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;

public class Grid2DataSetValueUtil {
    public static void converterFieldTypeToGridCellData(DataValueFormatter formatter, GridCellData cell, IDataValue dataValue) {
        cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
        Grid2DataSetValueUtil.setGridCellDataValue(formatter, dataValue, cell);
    }

    private static void setGridCellDataValue(DataValueFormatter formatter, IDataValue dataValue, GridCellData cell) {
        cell.setEditText(dataValue.getAsString());
        cell.setShowText(formatter.format(dataValue));
    }

    public static int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for (int i = 0; i < length; ++i) {
            char ch = colStr.charAt(length - i - 1);
            num = ch - 65 + 1;
            num = (int)((double)num * Math.pow(26.0, i));
            result += num;
        }
        return result;
    }

    public static void converterFieldTypeToGridCellData(GridCellData gridCellData, String indexNum) {
        gridCellData.setEditText(indexNum);
        gridCellData.setShowText(indexNum);
    }
}

