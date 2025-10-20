/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.CronExpression
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 *  org.quartz.spi.OperableTrigger
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.CronScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.MonthlyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.WeeklyScheduleMethod;
import com.jiuqi.bi.util.StringUtils;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.CronExpression;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.spi.OperableTrigger;

public class ScheduleInfoCheckUtil {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AbstractScheduleMethod buildScheduleMethod(String scheduleType, String scheduleInfo) throws JobsException {
        JSONObject scheduleJson;
        AbstractScheduleMethod scheduleMethod;
        block23: {
            scheduleMethod = AbstractScheduleMethod.createMethod(scheduleType);
            if (scheduleMethod == null) {
                throw new JobsException("\u4e0d\u652f\u6301\u7684\u8c03\u5ea6\u7c7b\u578bscheduleType");
            }
            scheduleJson = null;
            try {
                scheduleJson = new JSONObject(scheduleInfo);
            }
            catch (JSONException e) {
                throw new JobsException("\u8c03\u5ea6\u4fe1\u606f\u683c\u5f0f\u9519\u8bef\uff0c\u53ea\u80fd\u4e3aJSON\u5b57\u7b26\u4e32");
            }
            if (scheduleMethod instanceof SimpleScheduleMethod) {
                long exetime;
                long curMillis = System.currentTimeMillis();
                if (scheduleJson.has("exetime") && (exetime = scheduleJson.optLong("exetime")) < curMillis) {
                    scheduleJson.put("exetime", curMillis);
                }
                if (scheduleJson.has("isRepeat") && scheduleJson.optBoolean("isRepeat")) {
                    if (!scheduleJson.has("interval")) {
                        throw new JobsException("\u95f4\u9694\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a");
                    }
                    try {
                        long interval = scheduleJson.getLong("interval");
                        if (interval <= 0L) {
                            throw new JobsException("\u95f4\u9694\u65f6\u95f4\u9519\u8bef\uff0c\u8bf7\u8f93\u5165\u5408\u7406\u7684\u6b63\u6574\u6570");
                        }
                        String periodType = scheduleJson.getString("periodType");
                        if ("SECOND".equals(periodType)) {
                            if (interval > 999999999L) {
                                throw new JobsException("\u95f4\u9694\u65f6\u95f4\u9519\u8bef\uff0c\u95f4\u9694\u5468\u671f\u4e3a\u201c\u79d2\u201d\u65f6\uff0c\u6700\u5927\u5141\u8bb8999999999");
                            }
                            break block23;
                        }
                        if ("MINUTE".equals(periodType)) {
                            if (interval > 16666666L) {
                                throw new JobsException("\u95f4\u9694\u65f6\u95f4\u9519\u8bef\uff0c\u95f4\u9694\u5468\u671f\u4e3a\u201c\u5206\u949f\u201d\u65f6\uff0c\u6700\u5927\u5141\u8bb816666666");
                            }
                        } else if ("HOUR".equals(periodType) && interval > 277777L) {
                            throw new JobsException("\u95f4\u9694\u65f6\u95f4\u9519\u8bef\uff0c\u95f4\u9694\u5468\u671f\u4e3a\u201c\u5c0f\u65f6\u201d\u65f6\uff0c\u6700\u5927\u5141\u8bb8277777");
                        }
                    }
                    catch (Exception e) {
                        throw new JobsException("\u95f4\u9694\u65f6\u95f4\u9519\u8bef\uff0c\u8bf7\u8f93\u5165\u5408\u7406\u7684\u6b63\u6574\u6570");
                    }
                }
            } else if (!(scheduleMethod instanceof DailyScheduleMethod) && scheduleMethod instanceof CronScheduleMethod && scheduleJson.has("cron")) {
                String cron = scheduleJson.optString("cron");
                if (StringUtils.isEmpty((String)cron)) {
                    throw new JobsException("\u6267\u884c\u8868\u8fbe\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
                }
                if (!CronExpression.isValidExpression((String)cron)) {
                    throw new JobsException("\u6267\u884c\u8868\u8fbe\u5f0f\u9519\u8bef");
                }
            }
        }
        try {
            scheduleMethod.fromJson(new JSONObject().put("content", (Object)scheduleJson));
        }
        catch (JSONException e) {
            throw new JobsException("\u8c03\u5ea6\u4fe1\u606fJSON\u8f6c\u65b9\u6cd5\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        if (scheduleMethod instanceof WeeklyScheduleMethod ? ((WeeklyScheduleMethod)scheduleMethod).getEnableInWeek() == 0 : scheduleMethod instanceof MonthlyScheduleMethod && !((MonthlyScheduleMethod)scheduleMethod).days().hasNext()) {
            throw new JobsException("\u672a\u914d\u7f6e\u6309\u5468\u6267\u884c\u533a\u95f4");
        }
        return scheduleMethod;
    }

    public static void validateScheduleInfo(long startTime, long endTime, String scheduleType, String scheduleInfo) throws JobsException {
        Date ft;
        if (startTime > endTime) {
            throw new JobsException("\u8c03\u5ea6\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4");
        }
        AbstractScheduleMethod scheduleMethod = ScheduleInfoCheckUtil.buildScheduleMethod(scheduleType, scheduleInfo);
        ScheduleBuilder<? extends Trigger> builder = scheduleMethod.createQuartzScheduleBuilder();
        TriggerBuilder tb = TriggerBuilder.newTrigger();
        if (startTime > System.currentTimeMillis()) {
            tb.startAt(new Date(startTime));
        } else {
            tb.startNow();
        }
        if (endTime > 0L) {
            tb.endAt(new Date(endTime));
        }
        tb.withSchedule(builder);
        try {
            Trigger trigger = tb.build();
            scheduleMethod.afterScheduleBuilder(trigger);
            Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
            OperableTrigger trig = (OperableTrigger)trigger;
            ft = trig.computeFirstFireTime(scheduler.getCalendar(trigger.getCalendarName()));
        }
        catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg == null) {
                errorMsg = "";
            }
            if (errorMsg.contains("will never fire.")) {
                errorMsg = "\u914d\u7f6e\u9519\u8bef\uff0c\u4efb\u52a1\u6c38\u8fdc\u4e0d\u4f1a\u88ab\u8c03\u5ea6";
            }
            if (errorMsg.contains("cannot be before start time")) {
                errorMsg = "\u914d\u7f6e\u9519\u8bef\uff0c\u4efb\u52a1\u6c38\u8fdc\u4e0d\u4f1a\u88ab\u8c03\u5ea6";
            }
            throw new JobsException(errorMsg, e);
        }
        if (ft == null) {
            throw new JobsException("\u914d\u7f6e\u9519\u8bef\uff0c\u4efb\u52a1\u6c38\u8fdc\u4e0d\u4f1a\u88ab\u8c03\u5ea6");
        }
    }
}

