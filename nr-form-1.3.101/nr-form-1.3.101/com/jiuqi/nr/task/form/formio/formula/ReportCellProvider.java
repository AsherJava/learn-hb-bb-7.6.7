/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportCellProvider
 */
package com.jiuqi.nr.task.form.formio.formula;

import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportCellProvider;
import com.jiuqi.nr.task.form.formio.dto.ImportReverseResultDTO;
import com.jiuqi.nr.task.form.formio.formula.ReportContext;
import com.jiuqi.nr.task.form.formio.formula.ReportWorkSheet;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportCellProvider
implements IReportCellProvider {
    private final Map<String, ReportWorkSheet> sheets = new LinkedHashMap<String, ReportWorkSheet>();

    public ReportCellProvider(Map<String, ImportReverseResultDTO> reverseResultDTOMap) {
        for (Map.Entry<String, ImportReverseResultDTO> entry : reverseResultDTOMap.entrySet()) {
            this.sheets.put(entry.getKey(), new ReportWorkSheet(entry.getKey(), entry.getValue()));
        }
    }

    public IWorksheet activeWorksheet(IContext context) throws CellExcpetion {
        ReportContext tContext = (ReportContext)context;
        String sheet = tContext.getSheet();
        return this.sheets.get(sheet);
    }

    public IWorksheet find(IContext context, String sheetName) throws CellExcpetion {
        return this.sheets.get(sheetName);
    }

    public IWorksheet activeWorksheet(IContext context, String groupKey) throws CellExcpetion {
        return this.activeWorksheet(context);
    }

    public IWorksheet find(IContext context, String groupKey, String sheetName) throws CellExcpetion {
        return this.find(context, sheetName);
    }
}

