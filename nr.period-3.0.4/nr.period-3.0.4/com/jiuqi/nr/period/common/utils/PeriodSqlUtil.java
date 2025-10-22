/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 */
package com.jiuqi.nr.period.common.utils;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PeriodSqlUtil {
    public static List<PeriodDataDefineImpl> getInitData(String t, int r) {
        ArrayList<PeriodDataDefineImpl> dataList = new ArrayList<PeriodDataDefineImpl>();
        int nowYear = LocalDate.now().getYear();
        switch (PeriodUtils.periodOfType(t)) {
            case YEAR: {
                for (int x = nowYear - r; x < nowYear + r; ++x) {
                    Calendar calendar = PeriodUtils.getCalendar(x, 0, 1);
                    Date date = calendar.getTime();
                    PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
                    periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.YEAR.type(), date));
                    periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
                    periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
                    periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
                    periodDataDefine.setYear(x);
                    periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
                    periodDataDefine.setDay(0);
                    int dayOfYear = PeriodUtils.getCalendar(x, 11, Month.DECEMBER.maxLength()).get(6);
                    periodDataDefine.setDays(dayOfYear);
                    dataList.add(periodDataDefine);
                }
                break;
            }
            case HALFYEAR: {
                for (int x = nowYear - r; x < nowYear + r; ++x) {
                    for (int y = 0; y < 2; ++y) {
                        Calendar calendar = PeriodUtils.getCalendar(x, 6 * y, 1);
                        Date date = calendar.getTime();
                        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
                        periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.HALFYEAR.type(), date));
                        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
                        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
                        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
                        periodDataDefine.setYear(x);
                        periodDataDefine.setDay(0);
                        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
                        periodDataDefine.setDays(PeriodUtils.getNumberOfDays(periodDataDefine));
                        dataList.add(periodDataDefine);
                    }
                }
                break;
            }
            case SEASON: {
                for (int x = nowYear - r; x < nowYear + r; ++x) {
                    for (int y = 0; y < 4; ++y) {
                        Calendar calendar = PeriodUtils.getCalendar(x, 3 * y, 1);
                        Date date = calendar.getTime();
                        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
                        periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.SEASON.type(), date));
                        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
                        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
                        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
                        periodDataDefine.setYear(x);
                        periodDataDefine.setQuarter(y + 1);
                        periodDataDefine.setDay(0);
                        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
                        periodDataDefine.setDays(PeriodUtils.getNumberOfDays(periodDataDefine));
                        dataList.add(periodDataDefine);
                    }
                }
                break;
            }
            case MONTH: {
                for (int x = nowYear - r; x < nowYear + r; ++x) {
                    for (int y = 0; y < 12; ++y) {
                        Calendar calendar = PeriodUtils.getCalendar(x, y, 1);
                        Date date = calendar.getTime();
                        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
                        periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.MONTH.type(), date));
                        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
                        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
                        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
                        periodDataDefine.setYear(x);
                        periodDataDefine.setQuarter(PeriodUtils.getMonthOfQuarter(y + 1));
                        periodDataDefine.setMonth(y + 1);
                        periodDataDefine.setDay(0);
                        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
                        periodDataDefine.setDays(PeriodUtils.getNumberOfDays(periodDataDefine));
                        dataList.add(periodDataDefine);
                    }
                }
                break;
            }
            case TENDAY: {
                for (int x = nowYear - r; x < nowYear + r; ++x) {
                    for (int y = 0; y < 12; ++y) {
                        for (int z = 0; z < 3; ++z) {
                            Calendar calendar = PeriodUtils.getCalendar(x, y, 1 + 10 * z);
                            Date date = calendar.getTime();
                            PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
                            periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.TENDAY.type(), date));
                            periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
                            periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
                            periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
                            periodDataDefine.setYear(x);
                            periodDataDefine.setQuarter(PeriodUtils.getMonthOfQuarter(y + 1));
                            periodDataDefine.setMonth(y + 1);
                            periodDataDefine.setDay(calendar.get(5));
                            periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
                            periodDataDefine.setDays(PeriodUtils.getNumberOfDaysWeek(periodDataDefine));
                            dataList.add(periodDataDefine);
                        }
                    }
                }
                break;
            }
            case WEEK: {
                Calendar calendar = PeriodUtils.getCalendar(nowYear - r, 0, 1);
                calendar.setFirstDayOfWeek(2);
                calendar.set(7, 2);
                if (calendar.get(1) != nowYear) {
                    calendar.set(5, calendar.get(5) + 7);
                }
                while (calendar.get(1) < nowYear + r) {
                    Date date = calendar.getTime();
                    PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
                    periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.WEEK.type(), date));
                    periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
                    periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
                    periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
                    periodDataDefine.setYear(calendar.get(1));
                    periodDataDefine.setQuarter(PeriodUtils.getMonthOfQuarter(calendar.get(2) + 1));
                    periodDataDefine.setMonth(calendar.get(2) + 1);
                    periodDataDefine.setDays(7);
                    periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
                    periodDataDefine.setDay(calendar.get(5));
                    dataList.add(periodDataDefine);
                    calendar.set(5, calendar.get(5) + 7);
                }
                break;
            }
            case DAY: {
                Calendar calendarDay = PeriodUtils.getCalendar(nowYear - r, 0, 1);
                while (calendarDay.get(1) < nowYear + r) {
                    Date date = calendarDay.getTime();
                    PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
                    periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.DAY.type(), date));
                    periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
                    periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
                    periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
                    periodDataDefine.setYear(calendarDay.get(1));
                    periodDataDefine.setQuarter(PeriodUtils.getMonthOfQuarter(calendarDay.get(2) + 1));
                    periodDataDefine.setMonth(calendarDay.get(2) + 1);
                    periodDataDefine.setDays(1);
                    periodDataDefine.setDay(calendarDay.get(5));
                    periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
                    dataList.add(periodDataDefine);
                    calendarDay.set(5, calendarDay.get(5) + 1);
                }
                break;
            }
            case CUSTOM: {
                break;
            }
        }
        return dataList;
    }

    public static void main(String[] args) {
        Calendar calendar = PeriodUtils.getCalendar(2022, 11, 31);
        calendar.add(2, 6);
        System.out.println(calendar.getTime());
    }

    public static PeriodDataDefineImpl createPeriodDataDefine() {
        PeriodDataDefineImpl periodDataDefine = new PeriodDataDefineImpl();
        periodDataDefine.setKey(UUIDUtils.getKey());
        Date nowDate = new Date();
        periodDataDefine.setCreateTime(nowDate);
        periodDataDefine.setCreateUser("admin");
        periodDataDefine.setUpdateTime(nowDate);
        periodDataDefine.setUpdateUser("admin");
        periodDataDefine.setYear(0);
        periodDataDefine.setQuarter(0);
        periodDataDefine.setMonth(0);
        periodDataDefine.setDays(0);
        periodDataDefine.setDay(0);
        return periodDataDefine;
    }

    public static PeriodDefineImpl createPeriodDefine() {
        PeriodDefineImpl defineImpl = new PeriodDefineImpl();
        defineImpl.setKey(UUIDUtils.getKey());
        Date nowDate = new Date();
        defineImpl.setCreateTime(nowDate);
        defineImpl.setCreateUser("admin");
        defineImpl.setUpdateTime(nowDate);
        defineImpl.setUpdateUser("admin");
        return defineImpl;
    }
}

