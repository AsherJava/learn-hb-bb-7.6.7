/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 */
package com.jiuqi.nr.summary.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.nr.summary.job.SummaryReportJobParam;
import com.jiuqi.nr.summary.utils.SumParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SummaryReportJobExecutor
extends JobExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SummaryReportJobExecutor.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private SumParamUtil sumParamUtil;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private NvwaSystemUserClient nvwaSystemUserClient;

    public void execute(JobContext jobContext) throws JobExecutionException {
        logger.info("\u5f00\u59cb\u6267\u884c\u81ea\u5b9a\u4e49\u6c47\u603b\u8ba1\u5212\u4efb\u52a1");
        ObjectMapper objectMapper = new ObjectMapper();
        String extendedConfig = jobContext.getJob().getExtendedConfig();
        SystemUserDTO systemUserDTO = (SystemUserDTO)this.nvwaSystemUserClient.getUsers().get(0);
        NpContext tempContext = this.npApplication.getTempContext(systemUserDTO.getName());
        this.npApplication.runAsContext(tempContext, () -> {
            try {
                SummaryReportJobParam jobParam = (SummaryReportJobParam)objectMapper.readValue(extendedConfig, SummaryReportJobParam.class);
                if (!jobContext.getMonitor().isCanceled()) {
                    String asynTaskID = this.asyncTaskManager.publishAndExecuteTask((Object)jobParam, "ASYNCSUMMARY_SUM");
                    this.querySumExeInfo(jobContext, asynTaskID);
                } else {
                    logger.error("\u53c2\u6570\u5f02\u5e38\uff0c\u81ea\u5b9a\u4e49\u6c47\u603b\u8ba1\u5212\u4efb\u52a1\u88ab\u4e2d\u6b62");
                }
            }
            catch (Exception e) {
                this.cancel(jobContext, e.getMessage(), e);
            }
        });
    }

    private void querySumExeInfo(JobContext jobContext, String asynTaskID) {
        boolean flag = false;
        while (!flag) {
            TaskState taskState = this.asyncTaskManager.queryTaskState(asynTaskID);
            if (TaskState.FINISHED.equals((Object)taskState) || TaskState.ERROR.equals((Object)taskState)) {
                flag = true;
                if (TaskState.ERROR.equals((Object)taskState)) {
                    String detail = (String)this.asyncTaskManager.queryDetail(asynTaskID);
                    this.cancel(jobContext, detail, null);
                }
            }
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                break;
            }
        }
    }

    private void cancel(JobContext jobContext, String message, Throwable t) {
        jobContext.getDefaultLogger().error(message, new Object[]{t.toString()});
        jobContext.getMonitor().cancel();
    }
}

