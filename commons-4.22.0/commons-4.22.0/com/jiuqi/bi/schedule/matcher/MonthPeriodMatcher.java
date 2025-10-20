/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.matcher.IPeriodMatcher;
import java.util.Calendar;

public class MonthPeriodMatcher
implements IPeriodMatcher {
    @Override
    public boolean match(Calendar now, Task task) {
        int maxDay = now.getActualMaximum(5);
        if (task.getExecMouthDay() > maxDay && now.get(5) == maxDay) {
            int minute = now.get(12);
            int hour = now.get(11);
            return hour == task.getExecHour() && minute == task.getExecMinute();
        }
        int minute = now.get(12);
        int hour = now.get(11);
        int day = now.get(5);
        return day == task.getExecMouthDay() && hour == task.getExecHour() && minute == task.getExecMinute();
    }
}

