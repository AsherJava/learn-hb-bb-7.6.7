/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn
 *  com.jiuqi.util.StringUtils
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs.executor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.schedule.bean.utils.ProcessScheduleBeanUtils;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.executor.ProcessJobExecutor;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.ProcessStartupAsyncMonitor;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.ProcessStartupOperateResult;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.param.ProcessStartupRunPara;
import com.jiuqi.nr.workflow2.schedule.dao.IWFSTriggerPlanDao;
import com.jiuqi.nr.workflow2.schedule.dao.WFSTriggerEntity;
import com.jiuqi.nr.workflow2.schedule.dao.impl.WFSTriggerEntityImpl;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import com.jiuqi.util.StringUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public class StartInstancesJobExecutor
extends ProcessJobExecutor {
    @Override
    public void execute(JobContext context, ProcessStartupAsyncMonitor monitor) {
        String extendedConfig = context.getJob().getExtendedConfig();
        ProcessStartupRunPara startupRunPara = (ProcessStartupRunPara)JavaBeanUtils.toJavaBean((String)extendedConfig, ProcessStartupRunPara.class);
        TaskDefine taskDefine = ProcessScheduleBeanUtils.getRunTimeViewController().getTask(startupRunPara.getTaskKey());
        this.insertOrUpdateTriggerEntity(taskDefine, startupRunPara.getPeriod(), monitor);
        WorkflowSettingsDO flowSettings = ProcessScheduleBeanUtils.getWorkflowSettingsService().queryWorkflowSettings(taskDefine.getKey());
        monitor.info("\u4efb\u52a1\uff1a\u3010" + taskDefine.getTitle() + "\uff08" + taskDefine.getTaskCode() + "\uff09\u3011");
        monitor.info("\u65f6\u671f\uff1a\u3010" + startupRunPara.getPeriod() + "\u3011");
        monitor.info("\u6d41\u7a0b\u7c7b\u578b\uff1a\u3010" + flowSettings.getWorkflowEngine() + "\u3011");
        monitor.info("\u62a5\u9001\u6a21\u5f0f\uff1a\u3010" + flowSettings.getWorkflowObjectType().title + "\u3011");
        try {
            TaskOrgLinkListStream orgLinkListStream = ProcessScheduleBeanUtils.getRunTimeViewController().listTaskOrgLinkStreamByTask(taskDefine.getKey());
            List orgLinkDefines = orgLinkListStream.getList();
            for (TaskOrgLinkDefine orgLinkDefine : orgLinkDefines) {
                monitor.info("\u5355\u4f4d\u53e3\u5f84\uff1a\u3010" + orgLinkDefine.getEntity() + "\u3011");
                DsContextHolder.setDsContext((DsContext)this.getDsContext(taskDefine, orgLinkDefine));
                ProcessStartupOperateResult operateResultManager = new ProcessStartupOperateResult();
                IOperateResultSet operateResultSet = operateResultManager.getOperateResultSet(IEventOperateColumn.DEF_OPT_COLUMN);
                ProcessScheduleBeanUtils.getStartupInstanceService().startInstances(startupRunPara, operateResultSet, monitor);
                operateResultManager.toResultMessage(monitor);
                DsContextHolder.clearContext();
                monitor.info("================================================");
            }
            this.startNextPeriod(taskDefine, startupRunPara.getPeriod(), monitor);
            monitor.setJobResult(AsyncJobResult.SUCCESS, "\u6267\u884c\u5b8c\u6210\uff01\uff01");
        }
        catch (Exception e) {
            this.startNextPeriod(taskDefine, startupRunPara.getPeriod(), monitor);
            monitor.error(e.getMessage(), e);
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u6267\u884c\u8fc7\u7a0b\u51fa\u73b0\u5f02\u5e38\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
        }
    }

    protected DsContext getDsContext(TaskDefine taskDefine, TaskOrgLinkDefine orgLinkDefine) {
        DsContextImpl dsContext = new DsContextImpl();
        dsContext.setTaskKey(taskDefine.getKey());
        dsContext.setEntityId(orgLinkDefine.getEntity());
        return dsContext;
    }

    protected void insertOrUpdateTriggerEntity(TaskDefine taskDefine, String period, ProcessStartupAsyncMonitor monitor) {
        monitor.info("\u5f00\u59cb\u63d2\u5165\u5386\u53f2\u8bb0\u5f55...");
        Trigger trigger = this.createExecuteTrigger(taskDefine, period, monitor);
        WFSTriggerEntityImpl triggerEntity = new WFSTriggerEntityImpl();
        triggerEntity.setTaskKey(taskDefine.getKey());
        triggerEntity.setPeriod(period);
        triggerEntity.setPlanedStartTime(trigger.getStartTime());
        triggerEntity.setPlanedEndTime(trigger.getEndTime());
        triggerEntity.setActualTime(new Date());
        triggerEntity.setStatus(monitor.getStatus());
        IWFSTriggerPlanDao triggerPlanDao = ProcessScheduleBeanUtils.getWfsTriggerPlanDao();
        ArrayList<WFSTriggerEntity> insertEntities = new ArrayList<WFSTriggerEntity>();
        ArrayList<WFSTriggerEntity> updateEntities = new ArrayList<WFSTriggerEntity>();
        WFSTriggerEntity findByPeriod = triggerPlanDao.queryRowByTaskAndPeriod(taskDefine.getKey(), period);
        if (findByPeriod != null) {
            triggerEntity.setExecCount(findByPeriod.getExecCount() + 1);
            updateEntities.add(triggerEntity);
        } else {
            insertEntities.add(triggerEntity);
        }
        triggerPlanDao.updateRow(updateEntities);
        triggerPlanDao.insertRows(insertEntities);
        monitor.info("\u5386\u53f2\u8bb0\u5f55\u63d2\u5165\u5b8c\u6210\uff01\uff01\uff01");
    }

    protected void startNextPeriod(TaskDefine taskDefine, String currentPeriod, IProcessAsyncMonitor monitor) {
        try {
            IPeriodEntity periodEntity = ProcessScheduleBeanUtils.getPeriodEngineService().getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
            IPeriodProvider periodProvider = ProcessScheduleBeanUtils.getPeriodEngineService().getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
            String nextPeriod = periodProvider.nextPeriod(currentPeriod);
            if (StringUtils.isEmpty((String)nextPeriod)) {
                monitor.info("\u3010" + currentPeriod + "\u3011\u5df2\u7ecf\u662f\u6700\u540e\u4e00\u671f\u4e86\uff0c\u6240\u6709\u53ef\u7528\u65f6\u671f\u5df2\u7ecf\u5168\u90e8\u542f\u52a8\u5b8c\u6bd5\uff01\uff01\uff01\u4efb\u52a1\u5c06\u6682\u505c\u6267\u884c\uff01\uff01");
                ProcessScheduleBeanUtils.getProcessStartupScheduleManager().deleteStartupSchedule(taskDefine);
                return;
            }
            monitor.info("\u4e0b\u4e00\u65f6\u671f\u3010" + nextPeriod + "\u3011\u5c06\u88ab\u542f\u52a8\uff01\uff01");
            ProcessScheduleBeanUtils.getProcessStartupScheduleManager().createOrUpdateStartupSchedule(taskDefine, nextPeriod);
        }
        catch (JobsException | ParseException | SchedulerException e) {
            monitor.error("\u4e0b\u4e00\u671f\u542f\u52a8\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }
}

