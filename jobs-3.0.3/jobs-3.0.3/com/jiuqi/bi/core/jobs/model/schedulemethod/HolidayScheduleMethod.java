/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Trigger
 *  org.quartz.spi.MutableTrigger
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.spi.MutableTrigger;

public class HolidayScheduleMethod
extends AbstractScheduleMethod {
    public static final String NAME = "holiday";
    public static final String TITLE = "\u8282\u5047\u65e5/\u5de5\u4f5c\u65e5";
    private static final String TAG_CALENDAR = "calendar";
    private static final String TAG_STARTTIME = "startTime";
    private String calendar = null;
    private long startTime = 0L;
    public static final String WORKDAY_KEY = "WORKDAY_CALENDAR";
    public static final String HOLIDAY_KEY = "HOLIDAY_CALENDAR";

    @Override
    public ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder() {
        Calendar cal = Calendar.getInstance();
        if (this.startTime > 0L) {
            cal.setTimeInMillis(this.startTime);
        } else {
            cal.setTime(new Date());
        }
        int hours = cal.get(11);
        int minute = cal.get(12);
        int second = cal.get(13);
        String cronExpr = second + " " + minute + " " + hours + " * * ?";
        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule((String)cronExpr);
        csb.withMisfireHandlingInstructionDoNothing();
        return csb;
    }

    @Override
    public void afterScheduleBuilder(Trigger trigger) {
        if (trigger instanceof MutableTrigger) {
            MutableTrigger mt = (MutableTrigger)trigger;
            if (WORKDAY_KEY.equalsIgnoreCase(this.calendar) || HOLIDAY_KEY.equalsIgnoreCase(this.calendar)) {
                mt.setCalendarName(this.calendar);
            }
        }
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setCalendar(String calendarName) {
        this.calendar = calendarName;
    }

    @Override
    protected void loadFromJson(JSONObject json) throws JSONException {
        this.calendar = json.optString(TAG_CALENDAR);
        this.startTime = json.optLong(TAG_STARTTIME);
    }

    @Override
    protected void saveToJson(JSONObject json) throws JSONException {
        json.putOpt(TAG_CALENDAR, (Object)this.calendar);
        json.putOpt(TAG_STARTTIME, (Object)this.startTime);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public String generateText() {
        Calendar cal = Calendar.getInstance();
        if (this.startTime > 0L) {
            cal.setTimeInMillis(this.startTime);
        } else {
            cal.setTime(new Date());
        }
        if (WORKDAY_KEY.equalsIgnoreCase(this.calendar)) {
            return "\u6309\u7167\u5de5\u4f5c\u65e5\u6bcf\u5929" + cal.get(11) + "\u65f6" + cal.get(12) + "\u5206" + cal.get(13) + "\u79d2\u6267\u884c";
        }
        if (HOLIDAY_KEY.equalsIgnoreCase(this.calendar)) {
            return "\u6309\u7167\u8282\u5047\u65e5\u6bcf\u5929" + cal.get(11) + "\u65f6" + cal.get(12) + "\u5206" + cal.get(13) + "\u79d2\u6267\u884c";
        }
        return "\u4fe1\u606f\u9519\u8bef\uff0c\u65e0\u6cd5\u89e3\u6790";
    }

    public String getCalendar() {
        return this.calendar;
    }

    public long getStartTime() {
        return this.startTime;
    }
}

