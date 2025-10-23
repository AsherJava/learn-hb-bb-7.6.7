/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 */
package com.jiuqi.nr.workflow2.service.jobs;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.service.exception.OperateStateCode;
import com.jiuqi.nr.workflow2.service.execute.runtime.ProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.service.para.ProcessRunPara;
import org.slf4j.LoggerFactory;

public abstract class AbstractInstanceTaskExecutor<E extends ProcessRunPara>
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 8240620596882178375L;
    protected static final int progressWeight = 100;
    public static final String TASK_PREFIX_NAME = "NR-WORKFLOW2-TASK-FOR#";

    public void executeWithNpContext(JobContext jobContext) {
        ProcessAsyncMonitor monitor = new ProcessAsyncMonitor(jobContext, LoggerFactory.getLogger(((Object)((Object)this)).getClass()), 100);
        try {
            E runParam = this.getRunParaImpl((String)this.getParams().get("NR_ARGS"));
            OperateStateCode operateStateCode = ((ProcessRunPara)runParam).checkPara();
            if (operateStateCode != OperateStateCode.OPT_SUCCESS) {
                monitor.error("[code\uff1a" + operateStateCode.toString() + "] \u4e0d\u5408\u6cd5\u7684\u6267\u884c\u53c2\u6570\uff1a" + operateStateCode.description + "\uff01\uff01\u8bf7\u68c0\u67e5\u6267\u884c\u53c2\u6570\u662f\u5426\u6b63\u786e\uff01!");
                monitor.setJobResult(AsyncJobResult.FAILURE, operateStateCode.description);
                return;
            }
            IRunTimeViewController defineService = this.getRuntTimeDefineService();
            TaskDefine taskDefine = defineService.getTask(((ProcessRunPara)runParam).getTaskKey());
            WorkflowSettingsService settingsService = this.getWorkflowSettingsService();
            WorkflowSettingsDO flowSettings = settingsService.queryWorkflowSettings(((ProcessRunPara)runParam).getTaskKey());
            monitor.info("\u4efb\u52a1\uff1a\u3010" + taskDefine.getTitle() + "\uff08" + taskDefine.getTaskCode() + "\uff09\u3011");
            monitor.info("\u65f6\u671f\uff1a\u3010" + ((ProcessRunPara)runParam).getPeriod() + "\u3011");
            monitor.info("\u6d41\u7a0b\u7c7b\u578b\uff1a\u3010" + flowSettings.getWorkflowEngine() + "\u3011");
            monitor.info("\u62a5\u9001\u6a21\u5f0f\uff1a\u3010" + flowSettings.getWorkflowObjectType().title + "\u3011");
            this.executeProcess(jobContext, runParam, monitor);
        }
        catch (Exception e) {
            monitor.error(e.getMessage(), e);
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u6267\u884c\u8fc7\u7a0b\u51fa\u73b0\u5f02\u5e38\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
            e.printStackTrace();
        }
    }

    protected abstract void executeProcess(JobContext var1, E var2, IProcessAsyncMonitor var3) throws Exception;

    protected abstract E getRunParaImpl(String var1);

    protected WorkflowSettingsService getWorkflowSettingsService() {
        return (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
    }

    protected IRunTimeViewController getRuntTimeDefineService() {
        return (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    }
}

