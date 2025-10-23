/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 */
package com.jiuqi.nr.workflow2.service.jobs;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.service.jobs.AbstractInstanceTaskExecutor;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchExecutePara;

public class BatchCompleteTackExecutor
extends AbstractInstanceTaskExecutor<ProcessBatchExecutePara> {
    @Override
    protected void executeProcess(JobContext jobContext, ProcessBatchExecutePara runParam, IProcessAsyncMonitor monitor) throws JobExecutionException {
    }

    @Override
    protected ProcessBatchExecutePara getRunParaImpl(String jsonRunPara) {
        return (ProcessBatchExecutePara)JavaBeanUtils.toJavaBean((String)jsonRunPara, ProcessBatchExecutePara.class);
    }
}

