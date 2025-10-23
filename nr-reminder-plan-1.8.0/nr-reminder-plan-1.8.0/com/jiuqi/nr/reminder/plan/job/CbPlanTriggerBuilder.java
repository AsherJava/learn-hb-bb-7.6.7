/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.ScheduleBuilder
 *  org.quartz.SimpleScheduleBuilder
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 */
package com.jiuqi.nr.reminder.plan.job;

import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanTimeDO;
import com.jiuqi.nr.reminder.plan.job.CbTriggerBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

@Component
public class CbPlanTriggerBuilder
implements CbTriggerBuilder {
    @Override
    public List<Trigger> build(CbPlanDTO cbPlanDTO) {
        Date startTime = cbPlanDTO.getEffectiveStartTime();
        Date endTime = cbPlanDTO.getEffectiveEndTime();
        List<CbPlanTimeDO> times = cbPlanDTO.getTimes();
        ArrayList<Trigger> list = new ArrayList<Trigger>();
        block8: for (CbPlanTimeDO time : times) {
            Timestamp execTime = time.getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(execTime);
            calendar.setTimeZone(TimeZone.getDefault());
            int hour = calendar.get(11);
            int minute = calendar.get(12);
            int day = calendar.get(5);
            int month = calendar.get(2) + 1;
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.startAt(startTime);
            triggerBuilder.endAt(endTime);
            triggerBuilder.withIdentity(time.getId(), time.getPlanId());
            int periodType = time.getPeriodType();
            switch (periodType) {
                case 0: {
                    SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
                    simpleScheduleBuilder.withMisfireHandlingInstructionNextWithRemainingCount();
                    triggerBuilder.withSchedule((ScheduleBuilder)simpleScheduleBuilder);
                    break;
                }
                case 2: {
                    SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(time.getPeriodValue()).repeatForever();
                    simpleScheduleBuilder.withMisfireHandlingInstructionNextWithRemainingCount();
                    triggerBuilder.withSchedule((ScheduleBuilder)simpleScheduleBuilder);
                    break;
                }
                case 3: {
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.dailyAtHourAndMinute((int)hour, (int)minute);
                    cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
                    triggerBuilder.withSchedule((ScheduleBuilder)cronScheduleBuilder);
                    break;
                }
                case 4: {
                    int periodValue = time.getPeriodValue();
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule((String)String.format("0 %s %s ? * %s", minute, hour, periodValue));
                    cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
                    triggerBuilder.withSchedule((ScheduleBuilder)cronScheduleBuilder);
                    break;
                }
                case 5: {
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule((String)String.format("0 %s %s %s * ?", minute, hour, day));
                    triggerBuilder.withSchedule((ScheduleBuilder)cronScheduleBuilder.withMisfireHandlingInstructionDoNothing());
                    break;
                }
                case 6: {
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule((String)String.format("0 %s %s %s %s ?", minute, hour, day, month));
                    triggerBuilder.withSchedule((ScheduleBuilder)cronScheduleBuilder.withMisfireHandlingInstructionDoNothing());
                    break;
                }
                default: {
                    continue block8;
                }
            }
            list.add(triggerBuilder.build());
        }
        return list;
    }
}

