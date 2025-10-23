/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 */
package com.jiuqi.nr.workflow2.service.jobs;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateColumn;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessServiceHelper;
import com.jiuqi.nr.workflow2.service.helper.ProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.jobs.AbstractInstanceTaskExecutor;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;

@RealTimeJob(group="NR-WORKFLOW2-TASK-FOR#refresh_actors", groupTitle="\u5237\u65b0\u53c2\u4e0e\u8005-\u5f02\u6b65\u4efb\u52a1", subject="\u62a5\u8868")
public class RefreshActorsTaskExecutor
extends AbstractInstanceTaskExecutor<ProcessBatchRunPara> {
    public static final String TASK_NAME = "NR-WORKFLOW2-TASK-FOR#refresh_actors";
    public static final String TASK_TITLE = "\u5237\u65b0\u53c2\u4e0e\u8005-\u5f02\u6b65\u4efb\u52a1";

    @Override
    protected void executeProcess(JobContext jobContext, ProcessBatchRunPara runParam, IProcessAsyncMonitor monitor) throws JobExecutionException {
        IProcessDimensionsBuilder dimensionsBuilder = (IProcessDimensionsBuilder)SpringBeanUtils.getBean(ProcessDimensionsBuilder.class);
        IBusinessKeyCollection bizKeyCollection = dimensionsBuilder.buildBusinessKeyCollection(runParam);
        EventOperateResult operateResultManager = new EventOperateResult(bizKeyCollection.getBusinessObjects());
        IProcessServiceHelper instanceServiceHelper = (IProcessServiceHelper)SpringBeanUtils.getBean(IProcessServiceHelper.class);
        IOperateResultSet operateResultSet = operateResultManager.getOperateResultSet(new EventOperateColumn(TASK_NAME, "\u5237\u65b0\u53c2\u4e0e\u8005"));
        instanceServiceHelper.getProcessInstanceService(runParam.getTaskKey()).refreshActors(runParam, bizKeyCollection, monitor, operateResultSet);
    }

    @Override
    protected ProcessBatchRunPara getRunParaImpl(String jsonRunPara) {
        return (ProcessBatchRunPara)JavaBeanUtils.toJavaBean((String)jsonRunPara, ProcessBatchRunPara.class);
    }
}

