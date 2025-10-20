/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.schedule;

import com.jiuqi.bi.schedule.PeriodType;
import com.jiuqi.bi.schedule.TaskContext;
import com.jiuqi.bi.util.StringUtils;

public abstract class Task {
    private String id;
    private String name;
    private PeriodType type;
    private int execMinute = -1;
    private int execHour = -1;
    private int execWeekDay = -1;
    private int execMouthDay = -1;
    private int execMouth = -1;
    private int execYear = -1;
    private int enableInDayStartHour = 0;
    private int enableInDayEndHour = 23;
    private int enableInWeek = 127;
    public static final int DAY_OF_WEEK_SUNDAY = 1;
    public static final int DAY_OF_WEEK_MONDAY = 2;
    public static final int DAY_OF_WEEK_TUESDAY = 4;
    public static final int DAY_OF_WEEK_WEDNESDAY = 8;
    public static final int DAY_OF_WEEK_THURSDAY = 16;
    public static final int DAY_OF_WEEK_FRIDAY = 32;
    public static final int DAY_OF_WEEK_SATURDAY = 64;

    public void setEnableInDayStartHour(int enableInDayStartHour) {
        this.enableInDayStartHour = enableInDayStartHour;
    }

    public void setEnableInDayEndHour(int enableInDayEndHour) {
        this.enableInDayEndHour = enableInDayEndHour;
    }

    public void setEnableInWeek(int enableInWeek) {
        this.enableInWeek = enableInWeek;
    }

    public int getEnableInDayStartHour() {
        return this.enableInDayStartHour;
    }

    public int getEnableInDayEndHour() {
        return this.enableInDayEndHour;
    }

    public int getEnableInWeek() {
        return this.enableInWeek;
    }

    public boolean isEnableInWeek(int weekDays) {
        return (this.enableInWeek & weekDays) == weekDays;
    }

    public PeriodType getType() {
        return this.type;
    }

    public void setType(PeriodType type) {
        this.type = type;
    }

    public int getExecMinute() {
        return this.execMinute;
    }

    public void setExecMinute(int execMinute) {
        this.execMinute = execMinute;
    }

    public int getExecHour() {
        return this.execHour;
    }

    public void setExecHour(int execHour) {
        this.execHour = execHour;
    }

    public int getExecWeekDay() {
        return this.execWeekDay;
    }

    public void setExecWeekDay(int execWeekDay) {
        this.execWeekDay = execWeekDay;
    }

    public int getExecMouthDay() {
        return this.execMouthDay;
    }

    public void setExecMouthDay(int execMouthDay) {
        this.execMouthDay = execMouthDay;
    }

    public int getExecMouth() {
        return this.execMouth;
    }

    public void setExecMouth(int execMouth) {
        this.execMouth = execMouth;
    }

    public int getExecYear() {
        return this.execYear;
    }

    public void setExecYear(int execYear) {
        this.execYear = execYear;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (StringUtils.isEmpty((String)this.name)) {
            return "";
        }
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void execute(TaskContext var1);

    public abstract long getLastPeriodTime();
}

