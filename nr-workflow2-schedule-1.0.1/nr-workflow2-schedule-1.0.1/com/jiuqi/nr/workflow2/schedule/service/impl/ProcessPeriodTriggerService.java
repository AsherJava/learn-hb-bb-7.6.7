/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType
 *  org.quartz.ScheduleBuilder
 *  org.quartz.SimpleScheduleBuilder
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 */
package com.jiuqi.nr.workflow2.schedule.service.impl;

import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType;
import com.jiuqi.nr.workflow2.schedule.common.ProcessScheduleDateUtil;
import com.jiuqi.nr.workflow2.schedule.enumeration.WFSOffsetType;
import com.jiuqi.nr.workflow2.schedule.service.IProcessPeriodTriggerService;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.Date;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessPeriodTriggerService
implements IProcessPeriodTriggerService {
    public static final String ATTR_KEY_TASK = "taskKey";
    public static final String ATTR_KEY_PERIOD = "period";
    @Autowired
    protected PeriodEngineService periodEngineService;
    @Autowired
    protected WorkflowSettingsService workflowSettingsService;

    @Override
    public Trigger buildTrigger(TaskDefine taskDefine, IPeriodEntity periodEntity, String periodValue, LocalTime offsetTime) throws ParseException {
        Date[] periodDateRegion = this.getTaskPeriodOffsetDateRange(taskDefine, periodEntity, periodValue);
        periodDateRegion[0] = ProcessScheduleDateUtil.dateModifyWithTime(periodDateRegion[0], offsetTime);
        periodDateRegion[1] = ProcessScheduleDateUtil.dateModifyWithTime(periodDateRegion[1], offsetTime);
        return this.buildTrigger(taskDefine, periodValue, periodDateRegion);
    }

    @Override
    public Trigger buildTrigger(TaskDefine taskDefine, IPeriodEntity periodEntity, String periodValue, WFSOffsetType offsetType, int offsetDays, LocalTime offsetTime) throws ParseException {
        Date[] periodDateRegion = this.getTaskPeriodOffsetDateRange(taskDefine, periodEntity, periodValue);
        return this.buildTrigger(taskDefine, periodValue, periodDateRegion, offsetType, offsetDays, offsetTime);
    }

    @Override
    public Trigger buildStartupTrigger(TaskDefine taskDefine, String period) throws ParseException {
        WorkflowOtherSettings workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(taskDefine.getKey());
        WorkflowSelfControl startupConfig = workflowOtherSettings.getWorkflowSelfControl();
        FillInStartTimeConfig fillInStartTimeConfig = workflowOtherSettings.getFillInStartTimeConfig();
        StartTimeStrategy type = startupConfig.getType();
        Trigger trigger = null;
        if (StartTimeStrategy.IDENTICAL_TO_START_TIME == type) {
            trigger = this.createWithFillStartTimeConfigTrigger(taskDefine, period, fillInStartTimeConfig, startupConfig.getBootTime());
        } else if (StartTimeStrategy.IDENTICAL_TO_TASK == type) {
            trigger = this.createWithTaskDefineOffsetTrigger(taskDefine, period, startupConfig.getBootTime());
        } else if (StartTimeStrategy.CUSTOM == type) {
            trigger = this.createWithCustomDefineTrigger(taskDefine, period, startupConfig, fillInStartTimeConfig);
        }
        return trigger;
    }

    public Trigger buildTrigger(TaskDefine taskDefine, String periodValue, Date[] periodDateRegion, WFSOffsetType offsetType, int offsetDays, LocalTime offsetTime) {
        if (WFSOffsetType.NATURAL_DAY == offsetType) {
            periodDateRegion[0] = ProcessScheduleDateUtil.dateOffsetNaturalDaysWithTime(periodDateRegion[0], offsetDays, offsetTime);
            periodDateRegion[1] = ProcessScheduleDateUtil.dateOffsetNaturalDaysWithTime(periodDateRegion[1], offsetDays, offsetTime);
        }
        if (WFSOffsetType.BUSINESS_DAY == offsetType) {
            periodDateRegion[0] = ProcessScheduleDateUtil.dateOffsetBusinessDaysWithTime(periodDateRegion[0], offsetDays, offsetTime);
            periodDateRegion[1] = ProcessScheduleDateUtil.dateOffsetBusinessDaysWithTime(periodDateRegion[1], offsetDays, offsetTime);
        }
        return this.buildTrigger(taskDefine, periodValue, periodDateRegion);
    }

    protected Trigger buildTrigger(TaskDefine taskDefine, String periodValue, Date[] periodDateRegion) {
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilder.startAt(periodDateRegion[0]);
        triggerBuilder.endAt(periodDateRegion[1]);
        triggerBuilder.withIdentity(this.getTriggerKey(taskDefine, periodValue), "workflow2-startup-schedule-trigger-group");
        triggerBuilder.withSchedule((ScheduleBuilder)SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0));
        triggerBuilder.usingJobData(ATTR_KEY_TASK, taskDefine.getKey());
        triggerBuilder.usingJobData(ATTR_KEY_PERIOD, periodValue);
        return triggerBuilder.build();
    }

    protected String getTriggerKey(TaskDefine taskDefine, String periodValue) {
        return taskDefine.getTaskCode() + "@" + periodValue;
    }

    protected Trigger createWithTaskDefineOffsetTrigger(TaskDefine taskDefine, String period, LocalTime offsetTime) throws ParseException {
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
        return this.buildTrigger(taskDefine, periodEntity, period, offsetTime);
    }

    protected Trigger createWithCustomDefineTrigger(TaskDefine taskDefine, String period, WorkflowSelfControl startupConfig, FillInStartTimeConfig fillInStartTimeConfig) throws ParseException {
        if (fillInStartTimeConfig.isEnable()) {
            Trigger trigger = this.createWithFillStartTimeConfigTrigger(taskDefine, period, fillInStartTimeConfig, startupConfig.getBootTime());
            Date[] periodDateRegion = new Date[]{trigger.getStartTime(), trigger.getEndTime()};
            WFSOffsetType offsetType = null;
            FillInDaysConfig fillInDaysConfig = startupConfig.getFillInDaysConfig();
            if (TimeControlType.WEEKDAY == fillInDaysConfig.getType()) {
                offsetType = WFSOffsetType.BUSINESS_DAY;
            }
            if (TimeControlType.NATURAL_DAY == fillInDaysConfig.getType()) {
                offsetType = WFSOffsetType.NATURAL_DAY;
            }
            int offsetDays = Math.abs(fillInDaysConfig.getDayNum());
            return this.buildTrigger(taskDefine, period, periodDateRegion, offsetType, offsetDays, startupConfig.getBootTime());
        }
        return this.createWithCustomDefineTrigger(taskDefine, period, startupConfig.getFillInDaysConfig(), startupConfig.getBootTime());
    }

    protected Trigger createWithCustomDefineTrigger(TaskDefine taskDefine, String period, FillInDaysConfig fillInDaysConfig, LocalTime offsetTime) throws ParseException {
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
        WFSOffsetType offsetType = null;
        if (TimeControlType.WEEKDAY == fillInDaysConfig.getType()) {
            offsetType = WFSOffsetType.BUSINESS_DAY;
        }
        if (TimeControlType.NATURAL_DAY == fillInDaysConfig.getType()) {
            offsetType = WFSOffsetType.NATURAL_DAY;
        }
        return this.buildTrigger(taskDefine, periodEntity, period, offsetType, fillInDaysConfig.getDayNum(), offsetTime);
    }

    protected Trigger createWithFillStartTimeConfigTrigger(TaskDefine taskDefine, String period, FillInStartTimeConfig fillInStartTimeConfig, LocalTime offsetTime) throws ParseException {
        if (fillInStartTimeConfig == null || !fillInStartTimeConfig.isEnable()) {
            throw new RuntimeException("\u586b\u62a5\u8ba1\u5212-\u65e0\u6548\u7684\u586b\u62a5\u5f00\u59cb\u65f6\u95f4\u914d\u7f6e\uff0c\u542f\u52a8\u8ba1\u5212\u521b\u5efa\u5931\u8d25\uff01\uff01");
        }
        if (StartTimeStrategy.CUSTOM == fillInStartTimeConfig.getType()) {
            return this.createWithCustomDefineTrigger(taskDefine, period, fillInStartTimeConfig.getFillInDaysConfig(), offsetTime);
        }
        return this.createWithTaskDefineOffsetTrigger(taskDefine, period, offsetTime);
    }

    protected Date[] getTaskPeriodOffsetDateRange(TaskDefine taskDefine, IPeriodEntity periodEntity, String periodValue) throws ParseException {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
        int taskPeriodOffset = taskDefine.getTaskPeriodOffset();
        if (taskPeriodOffset != 0) {
            PeriodModifier periodModifier = new PeriodModifier();
            periodModifier.setPeriodModifier(Math.abs(taskPeriodOffset));
            periodValue = periodProvider.modify(periodValue, periodModifier);
        }
        Date[] periodDateRegion = periodProvider.getPeriodDateRegion(periodValue);
        if (periodEntity.getPeriodType().equals((Object)PeriodType.DAY)) {
            return ProcessScheduleDateUtil.fixTimeRange(periodDateRegion[0], periodDateRegion[1]);
        }
        return periodDateRegion;
    }
}

