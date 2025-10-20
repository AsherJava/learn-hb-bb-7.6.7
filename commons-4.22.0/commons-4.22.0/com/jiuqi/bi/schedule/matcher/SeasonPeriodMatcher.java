/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.TaskContext;
import com.jiuqi.bi.schedule.matcher.IPeriodMatcher;
import java.util.Calendar;

public class SeasonPeriodMatcher
implements IPeriodMatcher {
    @Override
    public boolean match(Calendar now, Task task) {
        int minute = now.get(12);
        int hour = now.get(11);
        int day = now.get(5);
        int mouth = now.get(2);
        int mouthInSeason = mouth % 3 + 1;
        if (mouthInSeason == task.getExecMouth()) {
            int maxDay;
            int execMonthDay = task.getExecMouthDay();
            if (execMonthDay > (maxDay = now.getActualMaximum(5)) && day == maxDay) {
                return hour == task.getExecHour() && minute == task.getExecMinute();
            }
            return execMonthDay == day && hour == task.getExecHour() && minute == task.getExecMinute();
        }
        return false;
    }

    public static void main(String[] args) {
        Task t = new Task(){

            @Override
            public long getLastPeriodTime() {
                return 0L;
            }

            @Override
            public void execute(TaskContext context) {
            }

            @Override
            public int getExecHour() {
                return 14;
            }

            @Override
            public int getExecMinute() {
                return 26;
            }

            @Override
            public int getExecMouth() {
                return 3;
            }

            @Override
            public int getExecMouthDay() {
                return 32;
            }
        };
        Calendar c = Calendar.getInstance();
        c.set(5, 30);
        SeasonPeriodMatcher matcher = new SeasonPeriodMatcher();
        System.out.println(matcher.match(c, t));
    }
}

