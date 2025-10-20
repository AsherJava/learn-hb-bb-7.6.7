/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.util;

import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.Calendar;
import java.util.Date;

public class AgeCalendar {
    private String periodTypeCode;
    private int year;
    private int month;

    public AgeCalendar(String periodStr) {
        YearPeriodDO periodUtil = new YearPeriodObject(null, periodStr).formatYP();
        this.periodTypeCode = String.valueOf(periodUtil.getTypeCode());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(periodUtil.getEndDate());
        this.year = calendar.get(1);
        this.month = calendar.get(2);
    }

    public String getPeriodTypeCode() {
        return this.periodTypeCode;
    }

    public int compareMonth(Date date) {
        Calendar paramCalendar = Calendar.getInstance();
        paramCalendar.setTime(date);
        int yearDiff = paramCalendar.get(1) - this.year;
        int monthDiff = paramCalendar.get(2) - this.month;
        int totalMonthDiff = yearDiff * 12 + monthDiff;
        return totalMonthDiff;
    }

    public int compareYear(Date date) {
        Calendar paramCalendar = Calendar.getInstance();
        paramCalendar.setTime(date);
        int yearDiff = paramCalendar.get(1) - this.year;
        return yearDiff;
    }

    public int compareTo(Date date, String periodType) {
        if ("N".equals(periodType)) {
            return this.compareYear(date);
        }
        return this.compareMonth(date);
    }

    public static boolean match(Integer beginPeriod, int periodNum, Integer endPeriod) {
        if (null != beginPeriod && beginPeriod > periodNum) {
            return false;
        }
        return null == endPeriod || periodNum <= endPeriod;
    }
}

