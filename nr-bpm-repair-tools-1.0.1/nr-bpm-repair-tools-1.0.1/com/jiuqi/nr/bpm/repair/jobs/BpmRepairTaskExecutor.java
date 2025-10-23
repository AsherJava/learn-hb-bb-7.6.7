/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.repair.jobs;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableUtils;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.bpm.repair.jobs.monitor.AsyncJobResult;
import com.jiuqi.nr.bpm.repair.jobs.monitor.BpmRepairTaskMonitor;
import com.jiuqi.nr.bpm.repair.jobs.monitor.IBpmRepairTaskMonitor;
import com.jiuqi.nr.bpm.repair.service.BpmRepairStateService;
import com.jiuqi.nr.bpm.repair.web.param.BpmRepairToolsParam;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.util.Objects;

public abstract class BpmRepairTaskExecutor
extends NpRealTimeTaskExecutor {
    public static final String PREFIX_TASK_NAME = "bpm-repair-job-with_";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        BpmRepairTaskMonitor monitor = new BpmRepairTaskMonitor(jobContext.getInstanceId(), jobContext);
        String executeParam = (String)this.getParams().get("NR_ARGS");
        BpmRepairToolsParam envParam = (BpmRepairToolsParam)SimpleParamConverter.SerializationUtils.deserialize((String)executeParam);
        IRunTimeViewController defineService = this.getModelDefineService();
        TaskDefine taskDefine = defineService.getTask(envParam.getTaskId());
        if (taskDefine == null) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u4efb\u52a1\u4e3a\u7a7a\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
            return;
        }
        if (StringUtils.isEmpty((String)envParam.getPeriod())) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u65f6\u671f\u4e3a\u7a7a\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
            return;
        }
        FormSchemeDefine formScheme = this.getFormScheme(taskDefine, envParam.getPeriod());
        if (formScheme == null) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u3010" + taskDefine.getTitle() + "\uff08" + taskDefine.getTaskCode() + "\uff09\u3011\u3010" + envParam.getPeriod() + "\u3011\u4e0b\u6ca1\u6709\u62a5\u8868\u65b9\u6848\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
            return;
        }
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        if (flowsSetting == null) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u62a5\u8868\u65b9\u6848\u4e0a\u6ca1\u6709\u6d41\u7a0b\u76f8\u5173\u914d\u7f6e\uff08\u53c2\u6570\u6a21\u578b\u6709\u95ee\u9898\uff09\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
            return;
        }
        DataModelService dataModelService = this.getDataModelService();
        String stateTableName = String.format("%s%s", "SYS_UP_ST_", formScheme.getFormSchemeCode());
        TableModelDefine stateTableDefine = dataModelService.getTableModelDefineByCode(stateTableName);
        if (stateTableDefine == null) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u62a5\u8868\u65b9\u6848\u3010" + formScheme.getTitle() + "\uff08" + formScheme.getFormSchemeCode() + "\uff09\u3011\u4e0b\u7684\u6d41\u7a0b\u72b6\u6001\u8868\u3010" + stateTableName + "\u3011\u4e0d\u5b58\u5728\uff0c\u8bf7\u5c1d\u8bd5\u53d1\u5e03\u62a5\u8868\u65b9\u6848\uff01\u6216\u8005\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return;
        }
        String stateHiTableName = String.format("%s%s", "SYS_UP_HI_", formScheme.getFormSchemeCode());
        TableModelDefine stateHiTableDefine = dataModelService.getTableModelDefineByCode(stateHiTableName);
        if (stateHiTableDefine == null) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u62a5\u8868\u65b9\u6848\u3010" + formScheme.getTitle() + "\uff08" + formScheme.getFormSchemeCode() + "\uff09\u3011\u4e0b\u7684\u6d41\u7a0b\u72b6\u6001\u8868\u3010" + stateTableName + "\u3011\u4e0d\u5b58\u5728\uff0c\u8bf7\u5c1d\u8bd5\u53d1\u5e03\u62a5\u8868\u65b9\u6848\uff01\u6216\u8005\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return;
        }
        if (!this.isOpenWorkFlow(flowsSetting)) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u62a5\u8868\u65b9\u6848\u672a\u542f\u7528\u6d41\u7a0b\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
            return;
        }
        BpmRepairToolsEnv env = new BpmRepairToolsEnv();
        env.setTaskDefine(taskDefine);
        env.setPeriod(envParam.getPeriod());
        env.setFormScheme(formScheme);
        env.setFlowsSetting(flowsSetting);
        env.setStateTableDefine(stateTableDefine);
        env.setStateHiTableDefine(stateHiTableDefine);
        monitor.info("\u5f00\u59cb\u6267\u884c...");
        monitor.info("\u4efb\u52a1\uff1a[" + taskDefine.getTaskCode() + "]" + taskDefine.getTitle());
        monitor.info("\u65f6\u671f\uff1a" + envParam.getPeriod());
        monitor.info("\u62a5\u8868\u65b9\u6848\uff1a[" + formScheme.getFormSchemeCode() + "]" + formScheme.getTitle());
        monitor.info("\u6d41\u7a0b\u72b6\u6001\u8868\u540d\uff1a" + stateTableName);
        monitor.info("\u6d41\u7a0b\u8ddf\u8e2a\u8868\u540d\uff1a" + stateHiTableName);
        monitor.info("\u6309\u3010" + flowsSetting.getWordFlowType() + "\u3011\u4e0a\u62a5\uff01\uff01");
        this.executeRepair(jobContext, monitor, env);
        monitor.setJobResult(AsyncJobResult.SUCCESS, "\u6267\u884c\u7ed3\u675f\uff01\uff01");
    }

    protected abstract void executeRepair(JobContext var1, IBpmRepairTaskMonitor var2, BpmRepairToolsEnv var3);

    protected DBTableUtils getDBTableUtils() {
        return (DBTableUtils)SpringBeanUtils.getBean(DBTableUtils.class);
    }

    protected DataModelService getDataModelService() {
        return (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
    }

    protected IRunTimeViewController getModelDefineService() {
        return (IRunTimeViewController)SpringBeanUtils.getBean(RunTimeViewController.class);
    }

    protected BpmRepairStateService getBpmRepairStateService() {
        return (BpmRepairStateService)SpringBeanUtils.getBean(BpmRepairStateService.class);
    }

    protected FormSchemeDefine getFormScheme(TaskDefine taskDefine, String period) {
        IRunTimeViewController defineService = this.getModelDefineService();
        SchemePeriodLinkDefine schemePeriodLinkDefine = defineService.getSchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
        if (schemePeriodLinkDefine != null) {
            return defineService.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
        }
        return null;
    }

    protected boolean isOpenWorkFlow(TaskFlowsDefine flowsSetting) {
        FlowsType flowsType = flowsSetting.getFlowsType();
        switch (flowsType) {
            case DEFAULT: 
            case WORKFLOW: {
                return true;
            }
        }
        return false;
    }

    protected boolean isDefaultWorkFlow(TaskFlowsDefine flowsSetting) {
        FlowsType flowsType = flowsSetting.getFlowsType();
        return Objects.requireNonNull(flowsType) == FlowsType.DEFAULT;
    }

    protected String printWorkflowDefine(TaskFlowsDefine flowsSetting) {
        if (flowsSetting.isUnitSubmitForCensorship() && flowsSetting.isDataConfirm()) {
            return "\u542f\u7528\u4e86\u3010\u9001\u5ba1\u3011\u548c\u3010\u786e\u8ba4\u3011\uff1a\u9001\u5ba1->\u4e0a\u62a5->\u5ba1\u6279->\u786e\u8ba4";
        }
        if (flowsSetting.isUnitSubmitForCensorship()) {
            return "\u542f\u7528\u4e86\u3010\u9001\u5ba1\u3011\uff1a\u9001\u5ba1->\u4e0a\u62a5->\u5ba1\u6279";
        }
        if (flowsSetting.isDataConfirm()) {
            return "\u542f\u7528\u4e86\u3010\u786e\u8ba4\u3011\uff1a\u4e0a\u62a5->\u5ba1\u6279->\u786e\u8ba4";
        }
        return "\u6ca1\u6709\u542f\u7528\u3010\u9001\u5ba1\u3011\u548c\u3010\u786e\u8ba4\u3011\uff1a\u4e0a\u62a5->\u5ba1\u6279";
    }
}

