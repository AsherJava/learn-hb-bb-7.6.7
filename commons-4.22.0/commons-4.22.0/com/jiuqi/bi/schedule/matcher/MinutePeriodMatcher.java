/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.matcher.IPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.PeriodMatcherUtil;
import java.util.Calendar;

public class MinutePeriodMatcher
implements IPeriodMatcher {
    @Override
    public boolean match(Calendar now, Task task) {
        long lastTime = task.getLastPeriodTime();
        Calendar lastExecution = Calendar.getInstance();
        lastExecution.setTimeInMillis(lastTime);
        lastExecution.set(13, 0);
        lastExecution.set(14, 0);
        lastTime = lastExecution.getTimeInMillis();
        long spaceTime = now.getTimeInMillis() - lastTime;
        int spaceMinute = (int)(spaceTime / 60000L);
        if (spaceMinute >= task.getExecMinute()) {
            return PeriodMatcherUtil.enableInDay(now, task.getEnableInDayStartHour(), task.getEnableInDayEndHour());
        }
        return false;
    }
}

