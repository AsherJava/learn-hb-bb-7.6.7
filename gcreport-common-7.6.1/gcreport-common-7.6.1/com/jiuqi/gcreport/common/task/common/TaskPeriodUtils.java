/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.gcreport.common.task.common;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.task.vo.OptionVO;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskPeriodUtils {
    public static void setSchemeTimeByFormSchemeDefine(Scheme scheme, FormSchemeDefine schemeDefine) {
        int i;
        ArrayList<OptionVO> acctYearList = new ArrayList<OptionVO>();
        ArrayList<OptionVO> acctPeriodList = new ArrayList<OptionVO>();
        Integer fromAcctYear = null;
        Integer fromAcctPeriod = null;
        Integer toAcctYear = null;
        Integer toAcctPeriod = null;
        String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey(schemeDefine.getKey());
        String fromPeriod = fromToPeriodByFormSchemeKey[0];
        String toPeriod = fromToPeriodByFormSchemeKey[1];
        if (!StringUtils.isEmpty((String)fromPeriod)) {
            fromAcctYear = Integer.valueOf(fromPeriod.substring(0, 4));
        }
        if (!StringUtils.isEmpty((String)toPeriod)) {
            toAcctYear = Integer.valueOf(toPeriod.substring(0, 4));
        }
        int periodType = schemeDefine.getPeriodType().type();
        char periodTypeChar = (char)schemeDefine.getPeriodType().code();
        fromAcctPeriod = 1;
        toAcctPeriod = TaskPeriodUtils.getPeriodNum(periodTypeChar);
        Calendar date = Calendar.getInstance();
        int year = date.get(1);
        fromAcctYear = fromAcctYear == null ? year - 5 : fromAcctYear;
        toAcctYear = toAcctYear == null ? year + 5 : toAcctYear;
        for (i = fromAcctYear.intValue(); i <= toAcctYear; ++i) {
            acctYearList.add(new OptionVO(i, i + ""));
        }
        for (i = fromAcctPeriod.intValue(); i <= toAcctPeriod; ++i) {
            acctPeriodList.add(new OptionVO(i, i + ""));
        }
        scheme.setScheme(new OptionVO(schemeDefine.getKey(), schemeDefine.getTitle()));
        scheme.setSchemeTitle(schemeDefine.getTitle());
        scheme.setPeriodType(periodType);
        scheme.setPeriodTypeChar(String.valueOf(periodTypeChar));
        scheme.setAcctYear(acctYearList);
        scheme.setAcctPeriod(acctPeriodList);
    }

    public static void setDefaultTime(Scheme scheme, FormSchemeDefine schemeDefine) {
        int interval = schemeDefine.getPeriodOffset();
        PeriodWrapper currentPeriod = TaskPeriodUtils.getCurrentPeriod(scheme.getPeriodType());
        currentPeriod.modifyPeriod(interval);
        scheme.setDflYear(currentPeriod.getYear());
        scheme.setDflPeriod(currentPeriod.getPeriod());
    }

    public static PeriodWrapper getCurrentPeriod(int periodType) {
        Calendar date = Calendar.getInstance();
        int year = date.get(1);
        int month = date.get(2);
        int week = date.get(3);
        int day = date.get(6);
        int dayOfMonth = date.get(5);
        date.get(7);
        int acctYear = year;
        int acctPriod = 1;
        if (1 == periodType) {
            acctPriod = 1;
        } else if (2 == periodType) {
            acctPriod = (month + 1) / 7 + 1;
        } else if (3 == periodType) {
            acctPriod = (month + 1) / 4 + 1;
        } else if (4 == periodType) {
            acctPriod = month + 1;
        } else if (5 == periodType) {
            acctPriod = month * 3 + (dayOfMonth - 1) / 10 + 1;
        } else if (6 == periodType) {
            acctPriod = day;
        } else if (7 == periodType) {
            acctPriod = week;
        } else if (8 == periodType) {
            // empty if block
        }
        return new PeriodWrapper(acctYear, periodType, acctPriod);
    }

    public static int getPeriodNum(char periodTypeChar) {
        int periodNum = 1;
        if ('N' == periodTypeChar) {
            periodNum = 1;
        } else if ('H' == periodTypeChar) {
            periodNum = 2;
        } else if ('J' == periodTypeChar) {
            periodNum = 4;
        } else if ('Y' == periodTypeChar) {
            periodNum = 12;
        } else if ('X' == periodTypeChar) {
            periodNum = 36;
        } else if ('R' == periodTypeChar) {
            periodNum = 366;
        } else if ('Z' == periodTypeChar) {
            periodNum = 53;
        } else if ('B' == periodTypeChar) {
            periodNum = 1;
        }
        return periodNum;
    }

    public static int getPeriodNum(int periodType) {
        int periodNum = 1;
        if (1 == periodType) {
            periodNum = 1;
        } else if (2 == periodType) {
            periodNum = 2;
        } else if (3 == periodType) {
            periodNum = 4;
        } else if (4 == periodType) {
            periodNum = 12;
        } else if (5 == periodType) {
            periodNum = 36;
        } else if (6 == periodType) {
            periodNum = 366;
        } else if (7 == periodType) {
            periodNum = 53;
        } else if (8 == periodType) {
            periodNum = 1;
        }
        return periodNum;
    }
}

