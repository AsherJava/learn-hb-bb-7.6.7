/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.quartz.Trigger
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs.executor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.schedule.bean.utils.ProcessScheduleBeanUtils;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.executor.ProcessJobExecutor;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.ProcessStartupAsyncMonitor;
import com.jiuqi.nr.workflow2.schedule.common.ProcessScheduleDateUtil;
import com.jiuqi.nr.workflow2.schedule.dao.WFSTriggerEntity;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.quartz.Trigger;

public class CheckInstancesJobExecutor
extends ProcessJobExecutor {
    @Override
    void execute(JobContext context, ProcessStartupAsyncMonitor monitor) throws JobExecutionException {
        monitor.info("\u5f00\u59cb\u6267\u884c\u68c0\u67e5...");
        List taskDefines = ProcessScheduleBeanUtils.getRunTimeViewController().listAllTask();
        for (TaskDefine taskDefine : taskDefines) {
            monitor.info("\u68c0\u67e5\u4efb\u52a1\uff1a\u3010" + taskDefine.getTitle() + "\uff08" + taskDefine.getTaskCode() + "\uff09\u3011");
            this.executeCheckTaskStartup(taskDefine, monitor);
            monitor.info("\u68c0\u67e5\u7ed3\u675f......");
        }
    }

    protected void executeCheckTaskStartup(TaskDefine taskDefine, ProcessStartupAsyncMonitor monitor) {
        List<WFSTriggerEntity> hiTriggerEntities = ProcessScheduleBeanUtils.getWfsTriggerPlanDao().queryRowsByTask(taskDefine.getKey());
        List periodRangeList = ProcessScheduleBeanUtils.getRunTimeViewController().listSchemePeriodLinkByTask(taskDefine.getKey());
        List sortedRangePeriods = periodRangeList.stream().map(SchemePeriodLinkDefine::getPeriodKey).sorted().collect(Collectors.toList());
        for (String periodValue : sortedRangePeriods) {
            if (hiTriggerEntities.stream().noneMatch(e -> e.getPeriod().equals(periodValue))) {
                Trigger trigger = this.createExecuteTrigger(taskDefine, periodValue, monitor);
                if (trigger == null) {
                    monitor.info("\u300c" + periodValue + "\u300d\uff1a\u9519\u8bef\u7684\u65f6\u671f\u504f\u79fb\uff01\uff01\u8df3\u8fc7\u68c0\u67e5");
                    continue;
                }
                if (!ProcessScheduleDateUtil.isNowInRange(trigger.getStartTime(), trigger.getEndTime()) || !ProcessScheduleDateUtil.isNowAfter(trigger.getStartTime())) continue;
                this.immediateStartupSchedule(taskDefine, periodValue, monitor);
                break;
            }
            monitor.info("\u300c" + periodValue + "\u300d\uff1a\u5df2\u7ecf\u88ab\u542f\u52a8\u8fc7\uff01\uff01\u8df3\u8fc7\u68c0\u67e5");
        }
    }

    protected void immediateStartupSchedule(TaskDefine taskDefine, String periodValue, ProcessStartupAsyncMonitor monitor) {
        try {
            ProcessScheduleBeanUtils.getProcessStartupScheduleManager().immediateStartupSchedule(taskDefine, periodValue);
        }
        catch (JobsException | ParseException e) {
            monitor.error("\u7acb\u5373\u542f\u52a8\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }
}

