/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.designer.web.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.designer.web.service.TaskDeleteCondition;
import com.jiuqi.nr.designer.web.service.TaskDeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_DELETETASK", groupTitle="\u8868\u6837\u5bfc\u51fa")
public class TaskDeleteExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(TaskDeleteExecutor.class);
    public static final String TYPE = "ASYNCTASK_DELETETASK";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        TaskDeleteService taskDeleteService = (TaskDeleteService)SpringBeanUtils.getBean(TaskDeleteService.class);
        String params = (String)jobContext.getRealTimeJob().getParams().get("NR_ARGS");
        TaskDeleteCondition deleteCondition = (TaskDeleteCondition)SimpleParamConverter.SerializationUtils.deserialize((String)params);
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), TYPE, jobContext);
        try {
            taskDeleteService.deleteTask((AsyncTaskMonitor)asyncTaskMonitor, deleteCondition);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            asyncTaskMonitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)e);
        }
    }
}

