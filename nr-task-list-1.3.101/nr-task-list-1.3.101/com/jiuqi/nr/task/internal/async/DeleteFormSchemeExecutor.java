/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController
 */
package com.jiuqi.nr.task.internal.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController;
import com.jiuqi.nr.task.dto.FormSchemePublishDTO;
import com.jiuqi.nr.task.service.IFormSchemeReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNC_FORMSCHEME_DELETE", groupTitle="\u8868\u6837\u5bfc\u51fa")
public class DeleteFormSchemeExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(DeleteFormSchemeExecutor.class);
    public static final String TYPE = "ASYNC_FORMSCHEME_DELETE";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        DesignTimeViewController designTimeViewController = (DesignTimeViewController)SpringBeanUtils.getBean(DesignTimeViewController.class);
        IFormSchemeReleaseService releaseService = (IFormSchemeReleaseService)SpringBeanUtils.getBean(IFormSchemeReleaseService.class);
        String formSchemeKey = jobContext.getRealTimeJob().getQueryField2();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), TYPE, jobContext);
        try {
            asyncTaskMonitor.progressAndMessage(0.1, "\u5220\u9664\u62a5\u8868\u65b9\u6848");
            designTimeViewController.deleteFormScheme(new String[]{formSchemeKey});
            asyncTaskMonitor.progressAndMessage(0.7, "\u5220\u9664\u751f\u6548\u65f6\u671f");
            designTimeViewController.deleteSchemePeriodLinkByFormScheme(formSchemeKey);
            asyncTaskMonitor.progressAndMessage(0.9, "\u5220\u9664\u5176\u4ed6\u53c2\u6570");
            releaseService.publish(new FormSchemePublishDTO(formSchemeKey, false));
            asyncTaskMonitor.finish("\u6267\u884c\u6210\u529f", (Object)"\u5220\u9664\u5b8c\u6210");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            asyncTaskMonitor.error("\u5220\u9664\u62a5\u8868\u65b9\u6848\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
    }
}

