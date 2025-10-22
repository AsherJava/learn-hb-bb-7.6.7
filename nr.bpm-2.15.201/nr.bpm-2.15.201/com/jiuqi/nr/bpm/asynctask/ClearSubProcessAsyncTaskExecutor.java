/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.bpm.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.bpm.asynctask.StartProcessAsyncTaskExecutor;
import com.jiuqi.nr.bpm.setting.bean.SubProcessBean;
import com.jiuqi.nr.bpm.setting.service.IDeleteProcess;
import com.jiuqi.nr.common.exception.NrCommonException;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_SUB_STOPPROCESS", groupTitle="\u6e05\u9664\u6d41\u7a0b", subject="\u62a5\u8868")
public class ClearSubProcessAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(StartProcessAsyncTaskExecutor.class);
    public static final String ASYNCTASK_SUB_STOPPROCESS = "ASYNCTASK_SUB_STOPPROCESS";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        IDeleteProcess deleteProcess = (IDeleteProcess)BeanUtils.getBean(IDeleteProcess.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, ASYNCTASK_SUB_STOPPROCESS, jobContext);
        try {
            String args = (String)params.get("NR_ARGS");
            if (Objects.nonNull(params) && Objects.nonNull(args)) {
                SubProcessBean subProcessBean = (SubProcessBean)SimpleParamConverter.SerializationUtils.deserialize((String)args);
                deleteProcess.deleteProcess(subProcessBean.getFormSchemeKey(), subProcessBean.getBusinessKeyMap(), subProcessBean.getFlowType(), subProcessBean.isBindFlag(), subProcessBean.isSelectAll(), subProcessBean.isSelectReportAll(), subProcessBean.getAdjust());
                if (asyncTaskMonitor.isCancel()) {
                    String retStr = "\u4efb\u52a1\u53d6\u6d88";
                    asyncTaskMonitor.canceled(retStr, (Object)retStr);
                } else {
                    asyncTaskMonitor.finish("", (Object)"\u6267\u884c\u5b8c\u6210");
                }
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)nrCommonException);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return ASYNCTASK_SUB_STOPPROCESS;
    }
}

