/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.workbook;

import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EngineWorkbook
extends ReportWorkbook {
    private QuickReport report;
    private List<EngineWorksheet> worksheets;
    private Map<String, EngineWorksheet> sheetFinder;
    private EngineWorksheet activeWorksheet;

    public EngineWorkbook(QuickReport report) {
        this.report = report;
        this.worksheets = new ArrayList<EngineWorksheet>(report.getWorksheets().size());
        this.sheetFinder = new HashMap<String, EngineWorksheet>(report.getWorksheets().size());
        for (WorksheetModel sheetModel : report.getWorksheets()) {
            EngineWorksheet worksheet = new EngineWorksheet(sheetModel);
            this.worksheets.add(worksheet);
            this.sheetFinder.put(sheetModel.getName().toUpperCase(), worksheet);
        }
    }

    @Override
    public IWorksheet activeWorksheet(IContext context) throws CellExcpetion {
        return this.activeWorksheet;
    }

    @Override
    public IWorksheet find(IContext context, String sheetName) throws CellExcpetion {
        return this.sheetFinder.get(sheetName.toUpperCase());
    }

    @Override
    public void setActiveWorksheet(String sheetName) throws ReportExpressionException {
        EngineWorksheet sheet = this.sheetFinder.get(sheetName.toUpperCase());
        if (sheet == null) {
            throw new ReportExpressionException("\u67e5\u627e\u9875\u7b7e\u4e0d\u5b58\u5728\uff1a" + sheetName);
        }
        this.activeWorksheet = sheet;
    }

    @Override
    public int sheetSize() {
        return this.worksheets.size();
    }

    @Override
    public ReportWorksheet getSheet(int index) {
        return this.worksheets.get(index);
    }

    public QuickReport getReport() {
        return this.report;
    }
}

