/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.zb.scheme.service.async;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckService;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckParam;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNC_FORSCHEME_ZB_CHECK", groupTitle="\u9006\u5411\u751f\u6210\u6307\u6807\u4f53\u7cfb\u6307\u6807\u9884\u68c0\u67e5")
public class FormSchemeZbCheckAsyncExecutor
extends NpRealTimeTaskExecutor {
    public void executeWithNpContext(JobContext jobContext) {
        IFormSchemeZbCheckService formSchemeZbCheckService = (IFormSchemeZbCheckService)SpringBeanUtils.getBean(IFormSchemeZbCheckService.class);
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), "ASYNC_FORSCHEME_ZB_CHECK", jobContext);
        if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
            String paramJsonStr = (String)params.get("NR_ARGS");
            try {
                ZbCheckParam checkParam = (ZbCheckParam)new ObjectMapper().readValue(paramJsonStr, ZbCheckParam.class);
                formSchemeZbCheckService.checkZb(checkParam, (AsyncTaskMonitor)monitor);
            }
            catch (Exception e) {
                monitor.error(e.getMessage(), (Throwable)e);
            }
        }
    }
}

