/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.matcher.IPeriodMatcher;
import java.util.Calendar;

public class YearPeriodMatcher
implements IPeriodMatcher {
    @Override
    public boolean match(Calendar now, Task task) {
        int minute = now.get(12);
        int hour = now.get(11);
        int day = now.get(5);
        int mouth = now.get(2) + 1;
        return mouth == task.getExecMouth() && day == task.getExecMouthDay() && hour == task.getExecHour() && minute == task.getExecMinute();
    }
}

