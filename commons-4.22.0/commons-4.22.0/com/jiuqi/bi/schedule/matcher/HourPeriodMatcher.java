/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.matcher.IPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.PeriodMatcherUtil;
import java.util.Calendar;

public class HourPeriodMatcher
implements IPeriodMatcher {
    @Override
    public boolean match(Calendar now, Task task) {
        if (now.get(12) == task.getExecMinute()) {
            return PeriodMatcherUtil.enableInDay(now, task.getEnableInDayStartHour(), task.getEnableInDayEndHour());
        }
        return false;
    }
}

