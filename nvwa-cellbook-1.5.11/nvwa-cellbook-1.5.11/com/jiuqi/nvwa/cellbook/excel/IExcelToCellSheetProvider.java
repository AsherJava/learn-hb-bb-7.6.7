/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 */
package com.jiuqi.nvwa.cellbook.excel;

import com.jiuqi.nvwa.cellbook.model.Cell;

public interface IExcelToCellSheetProvider {
    public void readCellDataAfter(org.apache.poi.ss.usermodel.Cell var1, Cell var2);

    public void readCellStyleAfter(org.apache.poi.ss.usermodel.Cell var1, Cell var2);
}

