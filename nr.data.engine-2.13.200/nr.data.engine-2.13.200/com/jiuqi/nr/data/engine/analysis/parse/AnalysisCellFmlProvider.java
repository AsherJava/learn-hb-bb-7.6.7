/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportCellProvider
 *  com.jiuqi.bi.syntax.reportparser.IReportWorkSheet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportCellProvider;
import com.jiuqi.bi.syntax.reportparser.IReportWorkSheet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisWorkSheet;

public class AnalysisCellFmlProvider
implements IReportCellProvider {
    public IReportWorkSheet activeWorksheet(IContext context, String groupKey) throws CellExcpetion {
        QueryContext queryContext = (QueryContext)context;
        String defaultGroupName = queryContext.getDefaultGroupName();
        AnalysisWorkSheet workSheet = (AnalysisWorkSheet)this.find(context, groupKey, defaultGroupName);
        if (workSheet == null) {
            throw new CellExcpetion("\u6839\u636e\u516c\u5f0f\u6240\u5c5e\u62a5\u8868\u6807\u8bc6'" + defaultGroupName + "'\u6ca1\u6709\u627e\u5230\u62a5\u8868\u5bf9\u8c61\uff01");
        }
        workSheet.setDefault(true);
        return workSheet;
    }

    public IReportWorkSheet find(IContext context, String groupKey, String sheetName) throws CellExcpetion {
        QueryContext queryContext = (QueryContext)context;
        ReportInfo reportInfo = null;
        reportInfo = groupKey != null ? queryContext.getExeContext().getDataLinkFinder().findReportInfo(groupKey, sheetName) : queryContext.getExeContext().getDataLinkFinder().findReportInfo(sheetName);
        if (reportInfo == null && queryContext.getDefaultLinkAlias() != null) {
            reportInfo = queryContext.getExeContext().getDataLinkFinder().findReportInfo(queryContext.getDefaultLinkAlias(), sheetName);
        }
        if (reportInfo != null) {
            return new AnalysisWorkSheet(reportInfo);
        }
        return null;
    }

    public IWorksheet activeWorksheet(IContext context) throws CellExcpetion {
        return this.activeWorksheet(context, null);
    }

    public IWorksheet find(IContext context, String sheetName) throws CellExcpetion {
        return this.find(context, null, sheetName);
    }
}

