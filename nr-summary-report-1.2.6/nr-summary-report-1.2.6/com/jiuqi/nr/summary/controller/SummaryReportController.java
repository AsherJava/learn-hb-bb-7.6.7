/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.controller;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.executor.save.SummaryReportSaveExecutor;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.vo.SummaryReportModelVO;
import com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_report"})
public class SummaryReportController {
    @Autowired
    private IDesignSummarySolutionService designSolutionService;
    @Autowired
    private IDesignSummaryReportService designSummaryReportService;
    @Autowired
    private SummaryReportSaveExecutor reportSaveExecutor;

    @PostMapping(value={"/report/save"})
    public void updateSummaryReportModel(@RequestBody SummaryReportModelVO reportModelVO) throws SummaryCommonException {
        this.reportSaveExecutor.saveReport(reportModelVO);
    }

    @GetMapping(value={"/report/{reportKey}"})
    public SummaryReportModelVO getSummaryReportModel(@PathVariable String reportKey) throws SummaryCommonException {
        SummaryReportModel reportModel = this.designSolutionService.getSummaryReportModel(reportKey);
        if (!ObjectUtils.isEmpty(reportModel)) {
            SummaryReportModelVO reportModelVO = new SummaryReportModelVO();
            if (!ObjectUtils.isEmpty(reportModel.getGridData())) {
                GridData gridData = reportModel.getGridData();
                CellBook cellBook = new CellBook();
                CellBookGriddataConverter.gridDataToCellBook((GridData)gridData, (CellBook)cellBook, (String)"result", (String)"result");
                reportModelVO.setCellBook(cellBook);
            }
            reportModel.setReportData(null);
            reportModelVO.setReportModel(reportModel);
            reportModelVO.setDeploy(this.designSolutionService.isReportDeployed(reportKey));
            return reportModelVO;
        }
        return null;
    }
}

