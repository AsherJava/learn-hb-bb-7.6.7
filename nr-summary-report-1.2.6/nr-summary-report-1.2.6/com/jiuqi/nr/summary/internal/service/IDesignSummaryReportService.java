/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import java.util.List;

public interface IDesignSummaryReportService {
    public String insertSummaryReport(DesignSummaryReportDTO var1) throws SummaryCommonException;

    public void deleteSummaryReportByKey(String var1) throws SummaryCommonException;

    public void deleteSummaryReportByKeys(List<String> var1);

    public void deleteSummaryReportBySolution(String var1);

    public void updateSummaryReport(DesignSummaryReportDTO var1, boolean var2) throws SummaryCommonException;

    public DesignSummaryReportDTO getSummaryReportByKey(String var1, boolean var2);

    public List<DesignSummaryReportDTO> getSummaryReportsBySolution(String var1, boolean var2);

    public void moveSummaryReport(String var1, String var2, int var3) throws SummaryCommonException;
}

