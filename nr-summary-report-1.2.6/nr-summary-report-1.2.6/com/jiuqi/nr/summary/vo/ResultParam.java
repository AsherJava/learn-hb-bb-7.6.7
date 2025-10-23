/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.model.report.SummaryReport;
import java.util.ArrayList;
import java.util.List;

public class ResultParam {
    private List<SummaryReport> reports = new ArrayList<SummaryReport>();

    public List<SummaryReport> getReports() {
        return this.reports;
    }

    public void setReports(List<SummaryReport> reports) {
        this.reports = reports;
    }
}

