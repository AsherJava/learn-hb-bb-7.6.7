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
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.logic.web.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.data.logic.internal.service.IReviseCKDRECIDService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@RealTimeJob(group="ASYNC_TASK_REFRESH_CKD", groupTitle="\u6570\u636e\u670d\u52a1\u5237\u65b0\u51fa\u9519\u8bf4\u660e")
public class RefreshCkdAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(RefreshCkdAsyncTaskExecutor.class);
    private static final long serialVersionUID = -2844792399153490270L;

    public String getTaskPoolType() {
        return "ASYNC_TASK_REFRESH_CKD";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IReviseCKDRECIDService reviseCKDRECIDService = (IReviseCKDRECIDService)BeanUtil.getBean(IReviseCKDRECIDService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, this.getTaskPoolType(), jobContext);
        try {
            monitor.progressAndMessage(0.07, "\u5f00\u59cb\u5237\u65b0\u51fa\u9519\u8bf4\u660e");
            if (!Objects.nonNull(params) || !Objects.nonNull(params.get("NR_ARGS"))) throw new IllegalArgumentException(" REALTIME_TASK_PARAMS_KEY_ARGS is null");
            Object args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            if (!(args instanceof String)) throw new IllegalArgumentException(" args not instanceof String");
            String taskKey = (String)args;
            List formSchemeDefines = runTimeViewController.queryFormSchemeByTask(taskKey);
            if (CollectionUtils.isEmpty(formSchemeDefines)) {
                monitor.error("\u62a5\u8868\u65b9\u6848\u53c2\u6570\u5f02\u5e38", null);
                return;
            }
            monitor.progressAndMessage(0.09, "\u67e5\u8be2\u53c2\u6570");
            int count = 1;
            double p = 0.9 / (double)(formSchemeDefines.size() * 2);
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                monitor.progressAndMessage(0.09 + p * (double)count++, "\u5f00\u59cb\u5237\u65b0\u62a5\u8868\u65b9\u6848" + formSchemeDefine.getFormSchemeCode() + "\u4e0b\u7684\u51fa\u9519\u8bf4\u660e");
                reviseCKDRECIDService.revise(formSchemeDefine);
                monitor.progressAndMessage(0.09 + p * (double)count++, "\u62a5\u8868\u65b9\u6848" + formSchemeDefine.getFormSchemeCode() + "\u4e0b\u7684\u51fa\u9519\u8bf4\u660e\u5237\u65b0\u5b8c\u6210");
                if (!monitor.isCancel()) continue;
                String retStr = "\u4efb\u52a1\u53d6\u6d88";
                monitor.canceled(retStr, (Object)retStr);
                return;
            }
            monitor.finish("\u51fa\u9519\u8bf4\u660e\u5237\u65b0\u5b8c\u6210", args);
            return;
        }
        catch (Exception e) {
            monitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }
}

