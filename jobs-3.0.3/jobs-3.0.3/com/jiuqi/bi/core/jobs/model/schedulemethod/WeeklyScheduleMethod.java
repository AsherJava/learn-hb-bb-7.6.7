/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Trigger
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;

public class WeeklyScheduleMethod
extends AbstractScheduleMethod {
    public static final String NAME = "weekly";
    public static final String TITLE = "\u6bcf\u5468\u8c03\u5ea6";
    private int enableInWeek = 0;
    private DayHour time = new DayHour();
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 4;
    public static final int WEDNESDAY = 8;
    public static final int THURSDAY = 16;
    public static final int FRIDAY = 32;
    public static final int SATURDAY = 64;
    private static final int WEEKEND_DAYS = 65;
    private static final int WORKING_DAYS = 62;
    private static final String TAG_TIME = "time";
    private static final String TAG_WEEKDAYS = "weekdays";

    public void setWeekDayEnabled(int dayOfWeek) {
        this.enableInWeek |= dayOfWeek;
    }

    public void setWeekDayDisabled(int dayOfWeek) {
        this.enableInWeek &= ~dayOfWeek;
    }

    public boolean isWeekDayEnabled(int dayOfWeek) {
        return (this.enableInWeek & dayOfWeek) == dayOfWeek;
    }

    public void setTime(DayHour time) {
        this.time = time;
    }

    public DayHour getTime() {
        return this.time;
    }

    public int getEnableInWeek() {
        return this.enableInWeek;
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
        if (this.enableInWeek == 0) {
            return "\u672a\u8bbe\u7f6e\u6267\u884c\u5468\u671f";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\u6bcf\u5468");
        if (this.enableInWeek == 65) {
            sb.append("\u5468\u672b");
        } else if (this.enableInWeek == 62) {
            sb.append("\u5de5\u4f5c\u65e5");
        } else {
            if (this.isWeekDayEnabled(2)) {
                sb.append("\u5468\u4e00\u3001");
            }
            if (this.isWeekDayEnabled(4)) {
                sb.append("\u5468\u4e8c\u3001");
            }
            if (this.isWeekDayEnabled(8)) {
                sb.append("\u5468\u4e09\u3001");
            }
            if (this.isWeekDayEnabled(16)) {
                sb.append("\u5468\u56db\u3001");
            }
            if (this.isWeekDayEnabled(32)) {
                sb.append("\u5468\u4e94\u3001");
            }
            if (this.isWeekDayEnabled(64)) {
                sb.append("\u5468\u516d\u3001");
            }
            if (this.isWeekDayEnabled(1)) {
                sb.append("\u5468\u65e5\u3001");
            }
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append(" ").append(this.time).append("\u6267\u884c");
        return sb.toString();
    }

    @Override
    protected void loadFromJson(JSONObject json) throws JSONException {
        this.enableInWeek = json.optInt(TAG_WEEKDAYS);
        JSONObject jo = json.optJSONObject(TAG_TIME);
        DayHour dh = new DayHour();
        dh.fromJson(jo);
        this.time = dh;
    }

    @Override
    protected void saveToJson(JSONObject json) throws JSONException {
        json.putOpt(TAG_WEEKDAYS, (Object)this.enableInWeek);
        JSONObject jo = new JSONObject();
        this.time.toJson(jo);
        json.put(TAG_TIME, (Object)jo);
    }

    @Override
    public WeeklyScheduleMethod clone() {
        WeeklyScheduleMethod cloned = (WeeklyScheduleMethod)super.clone();
        cloned.time = this.time.clone();
        return cloned;
    }

    @Override
    public ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder() {
        StringBuilder cronExpr = new StringBuilder();
        cronExpr.append(this.time.getSecond()).append(" ").append(this.time.getMinute()).append(" ").append(this.time.isPM() ? this.time.getHour() + 12 : this.time.getHour()).append(" ");
        cronExpr.append("? * ");
        boolean weekExist = false;
        if (this.isWeekDayEnabled(1)) {
            cronExpr.append("1");
            weekExist = true;
        }
        if (this.isWeekDayEnabled(2)) {
            if (weekExist) {
                cronExpr.append(",");
            }
            cronExpr.append("2");
            weekExist = true;
        }
        if (this.isWeekDayEnabled(4)) {
            if (weekExist) {
                cronExpr.append(",");
            }
            cronExpr.append("3");
            weekExist = true;
        }
        if (this.isWeekDayEnabled(8)) {
            if (weekExist) {
                cronExpr.append(",");
            }
            cronExpr.append("4");
            weekExist = true;
        }
        if (this.isWeekDayEnabled(16)) {
            if (weekExist) {
                cronExpr.append(",");
            }
            cronExpr.append("5");
            weekExist = true;
        }
        if (this.isWeekDayEnabled(32)) {
            if (weekExist) {
                cronExpr.append(",");
            }
            cronExpr.append("6");
            weekExist = true;
        }
        if (this.isWeekDayEnabled(64)) {
            if (weekExist) {
                cronExpr.append(",");
            }
            cronExpr.append("7");
        }
        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule((String)cronExpr.toString());
        csb.withMisfireHandlingInstructionDoNothing();
        return csb;
    }
}

