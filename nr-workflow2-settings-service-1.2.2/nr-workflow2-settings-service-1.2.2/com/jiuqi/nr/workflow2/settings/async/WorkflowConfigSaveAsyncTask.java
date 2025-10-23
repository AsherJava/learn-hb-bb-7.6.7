/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.workflow2.settings.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.settings.dto.WorkflowSettingsManipulationContext;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsPersistService;
import java.util.Map;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

@RealTimeJob(group="WORKFLOW_ASYNC_TASK", groupTitle="\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u4fdd\u5b58\u540e\u4efb\u52a1\u6d41\u7a0b\u6267\u884c\u4efb\u52a1", isolate=true, subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class WorkflowConfigSaveAsyncTask
extends NpRealTimeTaskExecutor {
    @Transactional
    public void execute(JobContext jobContext) throws JobExecutionException {
        WorkflowSettingsPersistService workflowSettingsPersistService = (WorkflowSettingsPersistService)SpringBeanUtils.getBean(WorkflowSettingsPersistService.class);
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        IProgressMonitor monitor = jobContext.getMonitor();
        if (Objects.nonNull(params) && Objects.nonNull(this.getArgs())) {
            WorkflowSettingsManipulationContext context = (WorkflowSettingsManipulationContext)SimpleParamConverter.SerializationUtils.deserialize((String)this.getArgs());
            workflowSettingsPersistService.persistConfig(context, monitor);
        }
    }
}

