/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.internal.dto.SummaryReportDTO;
import java.util.List;

public interface IRuntimeSummaryReportService {
    public String insertSummaryReport(SummaryReportDTO var1) throws SummaryCommonException;

    public void batchInsertSummaryReports(List<SummaryReportDTO> var1);

    public void deleteSummaryReportByKey(String var1) throws SummaryCommonException;

    public void deleteSummaryReportByKeys(List<String> var1);

    public void deleteSummaryReportBySolution(String var1);

    public void updateSummaryReport(SummaryReportDTO var1, boolean var2) throws SummaryCommonException;

    public SummaryReportDTO getSummaryReportByKey(String var1, boolean var2);

    public List<SummaryReportDTO> getSummaryReportsByKeys(List<String> var1, boolean var2);

    public List<SummaryReportDTO> getSummaryReportsBySolution(String var1, boolean var2);

    public List<SummaryReportDTO> getSummaryReportsBySolutions(List<String> var1, boolean var2);
}

