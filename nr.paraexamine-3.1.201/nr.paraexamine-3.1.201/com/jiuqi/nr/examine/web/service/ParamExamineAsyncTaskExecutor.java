/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.nr.examine.web.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.examine.common.ExamineEnvironment;
import com.jiuqi.nr.examine.distributor.ExamineTaskDispatcher;
import com.jiuqi.nr.examine.factory.DistributorFactory;
import com.jiuqi.nr.examine.task.ExamineTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_PARAMEXAMINE", groupTitle="\u53c2\u6570\u68c0\u67e5")
public class ParamExamineAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ParamExamineAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) throws JobExecutionException {
        ExamineEnvironment env = (ExamineEnvironment)SpringBeanUtils.getBean(ExamineEnvironment.class);
        String params = (String)jobContext.getRealTimeJob().getParams().get("NR_ARGS");
        ExamineTask task = (ExamineTask)SimpleParamConverter.SerializationUtils.deserialize((String)params);
        try {
            task.setEnv(env);
            ExamineTaskDispatcher distributor = DistributorFactory.getDistributor(task);
            try {
                distributor.dispatch();
                env.getService().finish(task.getCheckInfoKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            throw new JobExecutionException((Throwable)e1);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_PARAMEXAMIE.getName();
    }
}

