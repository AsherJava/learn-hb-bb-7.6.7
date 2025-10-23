/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.nr.io.record.bean.ImportHistory
 *  com.jiuqi.nr.io.record.service.ImportHistoryService
 */
package com.jiuqi.nr.nrdx.data;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import org.springframework.lang.NonNull;

public class JobUtil {
    @NonNull
    public static ImportHistory getImportHistoryWhenJobExec(JobContext jobContext, ImportHistoryService importHistoryService) throws JobExecutionException {
        ImportHistory importHistory;
        long queryTime = 0L;
        long sleepTime = 2000L;
        while ((importHistory = importHistoryService.queryByRecKey(jobContext.getInstanceId())) == null) {
            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e) {
                throw new JobExecutionException("\u67e5\u8be2\u6267\u884c\u8bb0\u5f55\u5f02\u5e38", (Throwable)e);
            }
            if ((queryTime += sleepTime) < 60000L) continue;
            throw new JobExecutionException("\u6267\u884c\u8d85\u65f6");
        }
        return importHistory;
    }
}

