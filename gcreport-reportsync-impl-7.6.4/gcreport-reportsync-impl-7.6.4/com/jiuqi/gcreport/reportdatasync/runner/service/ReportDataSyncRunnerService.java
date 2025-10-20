/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 */
package com.jiuqi.gcreport.reportdatasync.runner.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.gcreport.reportdatasync.runner.param.ReportDataSyncRunnerParam;
import com.jiuqi.gcreport.reportdatasync.runner.systemhook.ReportDataSyncRunnerSystemHook;
import java.util.List;

public interface ReportDataSyncRunnerService {
    public List<ReportDataSyncRunnerSystemHook> getRunnerSystemHooks();

    public void runner(JobContext var1, ReportDataSyncRunnerParam var2, List<String> var3) throws Exception;
}

