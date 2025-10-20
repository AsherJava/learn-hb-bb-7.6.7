/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.matcher.IPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.PeriodMatcherUtil;
import java.util.Calendar;

public class DayPeriodMatcher
implements IPeriodMatcher {
    @Override
    public boolean match(Calendar now, Task task) {
        int hour = now.get(11);
        int minute = now.get(12);
        if (hour == task.getExecHour() && minute == task.getExecMinute()) {
            return PeriodMatcherUtil.enableInWeek(now, task.getEnableInWeek());
        }
        return false;
    }
}

