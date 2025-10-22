/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 */
package com.jiuqi.nr.bpm.instance.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.instance.bean.StartStateParam;
import com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService;
import com.jiuqi.nr.common.log.LogModuleEnum;
import java.util.Map;

@RealTimeJob(group="ASYNC_TASK_REFRESH_PARTICIPANT", groupTitle="\u6d41\u7a0b\u5b9e\u4f8b\u5237\u65b0\u53c2\u4e0e\u8005", subject="\u62a5\u8868")
public class RefreshParticipantTaskExecutor
extends NpRealTimeTaskExecutor {
    public void executeWithNpContext(JobContext jobContext) {
        WorkflowInstanceService workflowInstanceService = (WorkflowInstanceService)BeanUtils.getBean(WorkflowInstanceService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNC_TASK_REFRESH_PARTICIPANT", jobContext);
        try {
            String args = (String)params.get("NR_ARGS");
            StartStateParam startStateParam = (StartStateParam)SimpleParamConverter.SerializationUtils.deserialize((String)args);
            workflowInstanceService.refreshStrategicPartici(startStateParam, (AsyncTaskMonitor)asyncTaskMonitor);
            if (asyncTaskMonitor.isCancel()) {
                String retStr = "\u4efb\u52a1\u53d6\u6d88";
                asyncTaskMonitor.canceled(retStr, (Object)retStr);
            } else {
                asyncTaskMonitor.finish("", (Object)"\u6267\u884c\u5b8c\u6210");
            }
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5237\u65b0\u53c2\u4e0e\u8005", (String)"\u5237\u65b0\u53c2\u4e0e\u8005\u5931\u8d25");
            throw new RuntimeException(e);
        }
    }
}

