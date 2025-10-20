/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.office.excel.print.PrintSetting
 *  com.jiuqi.bi.syntax.excel.SheetDescriptorAdapter
 */
package com.jiuqi.bi.quickreport.engine.export;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.quickreport.engine.util.GridDataHandler;
import com.jiuqi.bi.quickreport.print.ExcelInfo;
import com.jiuqi.bi.syntax.excel.SheetDescriptorAdapter;

final class SheetDescriptor
extends SheetDescriptorAdapter {
    private final String sheetName;
    private final GridData gridData;

    public SheetDescriptor(String sheetName, GridData gridData, ExcelInfo excelInfo, PrintSetting printSetting) {
        this.sheetName = sheetName;
        this.gridData = gridData;
        if (printSetting != null) {
            this.properties().setProperty("printSetting", printSetting.toJson().toString());
        }
        if (excelInfo != null && !excelInfo.isExportHidden()) {
            new GridDataHandler(this.gridData).deleteHiddenColRow();
        }
    }

    public String name() {
        return this.sheetName;
    }

    public GridData grid() {
        return this.gridData;
    }

    public String getExpression(int col, int row) {
        CellResultInfo cellInfo = (CellResultInfo)this.gridData.getObj(col, row);
        return cellInfo == null ? null : cellInfo.getExcelFormula();
    }

    public void setExpression(int col, int row, String expr) {
        throw new UnsupportedOperationException();
    }
}

