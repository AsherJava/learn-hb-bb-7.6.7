/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.writeback.workbook;

import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackWorksheet;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.HashSet;
import java.util.Set;

public final class WritebackWorkbook
implements ICellProvider {
    private ICellProvider workbook;
    private Set<String> writebackSheets;

    public WritebackWorkbook(ICellProvider workbook, QuickReport report) {
        this.workbook = workbook;
        this.writebackSheets = new HashSet<String>();
        for (WritebackModel sheet : report.getWritebackSheets()) {
            this.writebackSheets.add(sheet.getSheetName().toUpperCase());
        }
    }

    public IWorksheet activeWorksheet(IContext context) throws CellExcpetion {
        throw new CellExcpetion("\u56de\u5199\u9875\u7b7e\u4e2d\u65e0\u6cd5\u5f15\u7528\u5f53\u524d\u6216\u5176\u5b83\u56de\u5199\u9875\u7b7e\u4e2d\u7684\u5355\u5143\u683c\u3002");
    }

    public IWorksheet find(IContext context, String sheetName) throws CellExcpetion {
        if (this.writebackSheets.contains(sheetName)) {
            throw new CellExcpetion("\u56de\u5199\u9875\u7b7e\u4e2d\u65e0\u6cd5\u5f15\u7528\u5f53\u524d\u6216\u5176\u5b83\u56de\u5199\u9875\u7b7e\u4e2d\u7684\u5355\u5143\u683c\u3002");
        }
        IWorksheet sheet = this.workbook.find(context, sheetName);
        return sheet == null ? null : new WritebackWorksheet(sheet);
    }
}

