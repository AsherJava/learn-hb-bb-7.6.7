/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 */
package com.jiuqi.nr.workflow2.service.jobs;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.service.IProcessExecuteService;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.ProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.impl.ProcessExecuteService;
import com.jiuqi.nr.workflow2.service.jobs.AbstractInstanceTaskExecutor;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara;

@RealTimeJob(group="NR-WORKFLOW2-TASK-FOR#complete_instance", groupTitle="\u3010\u5355\u4e2a\u3011\u6d41\u7a0b\u5b9e\u4f8b\u6d41\u8f6c-\u5f02\u6b65\u4efb\u52a1", subject="\u62a5\u8868")
public class OneCompleteTaskExecutor
extends AbstractInstanceTaskExecutor<ProcessOneExecutePara> {
    private static final long serialVersionUID = -4049500597637133918L;
    public static final String TASK_TITLE = "\u3010\u5355\u4e2a\u3011\u6d41\u7a0b\u5b9e\u4f8b\u6d41\u8f6c-\u5f02\u6b65\u4efb\u52a1";
    public static final String TASK_NAME = "NR-WORKFLOW2-TASK-FOR#complete_instance";

    @Override
    protected void executeProcess(JobContext jobContext, ProcessOneExecutePara runParam, IProcessAsyncMonitor monitor) throws Exception {
        IProcessDimensionsBuilder dimensionsBuilder = (IProcessDimensionsBuilder)SpringBeanUtils.getBean(ProcessDimensionsBuilder.class);
        IBusinessKey businessKey = dimensionsBuilder.buildBusinessKey(runParam);
        EventOperateResult operateResultManager = new EventOperateResult(businessKey.getBusinessObject());
        IProcessExecuteService completeService = (IProcessExecuteService)SpringBeanUtils.getBean(ProcessExecuteService.class);
        completeService.executeProcess((IProcessExecutePara)runParam, businessKey, monitor, (IEventOperateResult)operateResultManager);
    }

    @Override
    protected ProcessOneExecutePara getRunParaImpl(String jsonRunPara) {
        return (ProcessOneExecutePara)JavaBeanUtils.toJavaBean((String)jsonRunPara, ProcessOneExecutePara.class);
    }
}

