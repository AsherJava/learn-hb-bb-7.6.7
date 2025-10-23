/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.model.DataSetInfo
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 */
package com.jiuqi.nr.summary.executor.query.grid;

import com.jiuqi.bi.quickreport.model.DataSetInfo;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.executor.query.grid.GridBuilder;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import java.util.Map;

public class QuickReportBuilder {
    private final SummaryReportModelHelper reportModelHelper;
    private final Map<String, SummaryDSModel> modelMap;

    public QuickReportBuilder(SummaryReportModelHelper reportModelHelper, Map<String, SummaryDSModel> modelMap) {
        this.reportModelHelper = reportModelHelper;
        this.modelMap = modelMap;
    }

    private void initGridModel(QuickReport report) throws Exception {
        WorksheetModel wsModel = new WorksheetModel();
        wsModel.setName("sheet1");
        wsModel.setGriddata(this.reportModelHelper.getGridData());
        report.getWorksheets().add(wsModel);
        report.setPrimarySheetName(wsModel.getName());
        new GridBuilder(this.modelMap, wsModel, this.reportModelHelper).build();
    }

    public QuickReport build() throws Exception {
        QuickReport report = new QuickReport();
        SummaryReportModel reportModel = this.reportModelHelper.getReportModel();
        report.setName("summaryReport_" + reportModel.getName());
        report.setTitle("\u81ea\u5b9a\u4e49\u6c47\u603b\u8868_" + reportModel.getTitle());
        report.getExcelInfo().setExportHidden(false);
        this.modelMap.forEach((key, value) -> {
            DataSetInfo refDataSet = new DataSetInfo(key, value.getType());
            report.getRefDataSets().add(refDataSet);
        });
        this.initGridModel(report);
        return report;
    }
}

