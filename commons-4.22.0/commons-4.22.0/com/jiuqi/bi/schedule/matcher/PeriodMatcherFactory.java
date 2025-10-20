/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import com.jiuqi.bi.schedule.PeriodType;
import com.jiuqi.bi.schedule.matcher.DayPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.HourPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.IPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.MinutePeriodMatcher;
import com.jiuqi.bi.schedule.matcher.MonthPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.OncePeriodMatcher;
import com.jiuqi.bi.schedule.matcher.SeasonPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.WeekPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.YearPeriodMatcher;

public class PeriodMatcherFactory {
    public static IPeriodMatcher getMatcher(PeriodType periodType) {
        switch (periodType) {
            case ONCE: {
                return new OncePeriodMatcher();
            }
            case HOUR: {
                return new HourPeriodMatcher();
            }
            case DAY: {
                return new DayPeriodMatcher();
            }
            case WEEK: {
                return new WeekPeriodMatcher();
            }
            case MONTH: {
                return new MonthPeriodMatcher();
            }
            case MINUTE: {
                return new MinutePeriodMatcher();
            }
            case SEASON: {
                return new SeasonPeriodMatcher();
            }
            case YEAR: {
                return new YearPeriodMatcher();
            }
        }
        return null;
    }
}

