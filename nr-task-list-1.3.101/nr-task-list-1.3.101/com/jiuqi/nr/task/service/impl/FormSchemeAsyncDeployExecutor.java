/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.task.service.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.task.dto.FormSchemePublishDTO;
import com.jiuqi.nr.task.exception.PublishException;
import com.jiuqi.nr.task.service.IFormSchemeReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="FORM_SCHEME_DEPLOY", groupTitle="\u62a5\u8868\u65b9\u6848\u5f02\u6b65\u53d1\u5e03")
public class FormSchemeAsyncDeployExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = -981120356914721531L;
    private static final Logger LOGGER = LoggerFactory.getLogger(FormSchemeAsyncDeployExecutor.class);
    public static final String TYPE = "FORM_SCHEME_DEPLOY";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        IFormSchemeReleaseService formSchemeReleaseService = (IFormSchemeReleaseService)BeanUtil.getBean(IFormSchemeReleaseService.class);
        String params = (String)jobContext.getRealTimeJob().getParams().get("NR_ARGS");
        FormSchemePublishDTO publishDTO = (FormSchemePublishDTO)SimpleParamConverter.SerializationUtils.deserialize((String)params);
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), TYPE, jobContext);
        try {
            LOGGER.info("\u5f02\u6b65\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5f00\u59cb");
            asyncTaskMonitor.progressAndMessage(0.0, "\u5f02\u6b65\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5f00\u59cb");
            formSchemeReleaseService.publish(publishDTO);
            LOGGER.info("\u5f02\u6b65\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5b8c\u6210");
            asyncTaskMonitor.progressAndMessage(1.0, "\u5f02\u6b65\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5b8c\u6210");
        }
        catch (PublishException e) {
            LOGGER.error("\u5f02\u6b65\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5f02\u5e38", e);
            asyncTaskMonitor.error("\u5f02\u6b65\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5f02\u5e38", (Throwable)e);
            throw new JobExecutionException((Throwable)e);
        }
    }
}

