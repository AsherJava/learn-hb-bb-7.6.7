/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.period;

import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import java.util.Calendar;
import java.util.Date;

public class MonthAdapter {
    private YearPeriodDO yearPeriodDO;
    private int beginMonth;
    private int endMonth;

    public static MonthAdapter getInstance(YearPeriodDO yearPeriodDO) {
        return new MonthAdapter(yearPeriodDO);
    }

    private MonthAdapter(YearPeriodDO yearPeriodDO) {
        this.yearPeriodDO = yearPeriodDO;
    }

    public int getYear() {
        return this.yearPeriodDO.getYear();
    }

    public int getBeginMonth() {
        if (this.beginMonth <= 0) {
            this.beginMonth = this.calcMonth(this.yearPeriodDO.getBeginDate());
        }
        return this.beginMonth;
    }

    private int calcMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public int getEndMonth() {
        if (this.endMonth <= 0) {
            this.endMonth = this.calcMonth(this.yearPeriodDO.getEndDate());
        }
        return this.endMonth;
    }
}

