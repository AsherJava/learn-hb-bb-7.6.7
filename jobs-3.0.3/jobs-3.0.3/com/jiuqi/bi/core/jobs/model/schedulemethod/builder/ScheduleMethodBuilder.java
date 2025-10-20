/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod.builder;

import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.IScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import com.jiuqi.bi.core.jobs.model.schedulemethod.MonthlyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.QuarterlyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SchedulePeroid;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.YearlyScheduleMethod;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleMethodBuilder {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private SchedulePeroid period = SchedulePeroid.YEAR;
    private boolean oneOff = false;
    private long oneOffExecTime = System.currentTimeMillis();
    private SortedSet<Integer> month = new TreeSet<Integer>();
    private SortedSet<Integer> day = new TreeSet<Integer>();
    private int hour = 0;
    private int minite = 0;

    private ScheduleMethodBuilder() {
    }

    public static ScheduleMethodBuilder create() {
        return new ScheduleMethodBuilder();
    }

    public IScheduleMethod build() {
        AbstractScheduleMethod scheduleMethod = null;
        if (this.oneOff) {
            SimpleScheduleMethod simpleScheduleMethod = new SimpleScheduleMethod();
            simpleScheduleMethod.setExecuteTime(this.oneOffExecTime);
            scheduleMethod = simpleScheduleMethod;
        } else {
            Iterator monthIterator = this.month.iterator();
            Iterator dayIterator = this.day.iterator();
            if (this.period == SchedulePeroid.YEAR) {
                YearlyScheduleMethod yearlyScheduleMethod = new YearlyScheduleMethod();
                while (monthIterator.hasNext()) {
                    yearlyScheduleMethod.addMonth((Integer)monthIterator.next());
                }
                while (dayIterator.hasNext()) {
                    yearlyScheduleMethod.addDay((Integer)dayIterator.next());
                }
                yearlyScheduleMethod.setDayHour(this.getDayHour());
                scheduleMethod = yearlyScheduleMethod;
            } else if (this.period == SchedulePeroid.SEASON) {
                QuarterlyScheduleMethod quarterlyScheduleMethod = new QuarterlyScheduleMethod();
                while (monthIterator.hasNext()) {
                    int curMonth = (Integer)monthIterator.next();
                    if (curMonth > 3) {
                        this.logger.info("\u8c03\u5ea6\u4fe1\u606f\u4e2d\uff1a\u5b63\u5ea6\u6708\u975e\u6cd5\u3010{}\u3011\uff0c\u6bcf\u5b63\u5ea6\u6700\u5927\u6708\u6570\u4e3a3\u4e2a\u6708\u3002\u8be5\u6708\u4efd\u5c06\u88ab\u5ffd\u7565", (Object)curMonth);
                    }
                    quarterlyScheduleMethod.addMonth(curMonth);
                }
                while (dayIterator.hasNext()) {
                    quarterlyScheduleMethod.addDay((Integer)dayIterator.next());
                }
                quarterlyScheduleMethod.setDayHour(this.getDayHour());
                scheduleMethod = quarterlyScheduleMethod;
            } else if (this.period == SchedulePeroid.MONTH) {
                MonthlyScheduleMethod monthlyScheduleMethod = new MonthlyScheduleMethod();
                while (dayIterator.hasNext()) {
                    monthlyScheduleMethod.addDay((Integer)dayIterator.next());
                }
                monthlyScheduleMethod.setDayHour(this.getDayHour());
                scheduleMethod = monthlyScheduleMethod;
            } else if (this.period == SchedulePeroid.DAY) {
                DailyScheduleMethod dailyScheduleMethod = new DailyScheduleMethod();
                dailyScheduleMethod.setExecuteTimeInDay(this.getDayHour());
                scheduleMethod = dailyScheduleMethod;
            }
        }
        return scheduleMethod;
    }

    private DayHour getDayHour() {
        DayHour executeTime = new DayHour();
        if (this.hour < 12) {
            executeTime.setHour(this.hour);
        } else {
            executeTime.setPM(true);
            executeTime.setHour(this.hour - 12);
        }
        executeTime.setMinute(this.minite);
        return executeTime;
    }

    public ScheduleMethodBuilder setPeriod(SchedulePeroid period) {
        this.period = period;
        return this;
    }

    public ScheduleMethodBuilder oneOff(boolean oneOf, long execTime) {
        this.oneOff = oneOf;
        this.oneOffExecTime = execTime;
        return this;
    }

    public ScheduleMethodBuilder addMonth(int month) {
        this.month.add(month);
        return this;
    }

    public ScheduleMethodBuilder addDay(int day) {
        this.day.add(day);
        return this;
    }

    public ScheduleMethodBuilder setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public ScheduleMethodBuilder setMinite(int minite) {
        this.minite = minite;
        return this;
    }
}

