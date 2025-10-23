/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 */
package com.jiuqi.nr.summary.job;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.job.SummaryReportJobExecutor;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SummaryReportJobFactory
extends JobFactory
implements IJobAdvanceConfiguration {
    @Autowired
    private IRuntimeSummarySolutionService runtimeSolutionService;
    @Autowired
    private SummaryParamService summaryParamService;
    @Autowired
    private SummaryReportJobExecutor jobExecutor;

    public String getJobCategoryId() {
        return "summary_report";
    }

    public String getJobCategoryTitle() {
        return "\u81ea\u5b9a\u4e49\u6c47\u603b\u8868";
    }

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return this.jobExecutor;
    }

    public String getModelName() {
        return "nr-summary-report-job";
    }
}

