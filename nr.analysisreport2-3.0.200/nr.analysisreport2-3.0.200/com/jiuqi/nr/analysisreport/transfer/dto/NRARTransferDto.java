/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.analysisreport.transfer.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportDefineImpl;
import java.io.Serializable;
import java.util.List;

public class NRARTransferDto
implements Serializable {
    List<ReportChapterDefine> reportChapterDefineList;
    @JsonDeserialize(as=AnalysisReportDefineImpl.class)
    AnalysisReportDefine analysisReportDefine;

    public List<ReportChapterDefine> getReportChapterDefineList() {
        return this.reportChapterDefineList;
    }

    public void setReportChapterDefineList(List<ReportChapterDefine> reportChapterDefineList) {
        this.reportChapterDefineList = reportChapterDefineList;
    }

    public AnalysisReportDefine getAnalysisReportDefine() {
        return this.analysisReportDefine;
    }

    public void setAnalysisReportDefine(AnalysisReportDefine analysisReportDefine) {
        this.analysisReportDefine = analysisReportDefine;
    }
}

