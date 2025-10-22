/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.common.util;

import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeDimHelper {
    private static final Logger logger = LoggerFactory.getLogger(TimeDimHelper.class);
    static final int[] tenday = new int[]{1, 11, 21};
    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public static String getTimeDimByDate(Calendar c, TimeGranularity timeGan) {
        int year = c.get(1);
        int month = c.get(2) + 1;
        int day = c.get(5);
        return TimeDimHelper.getDateStringByIntValues(year, month, day, timeGan);
    }

    public String getTimeDimByPeriodWrapper(PeriodWrapper pw, TimeGranularity timeGan) {
        return this.periodWrapperToTimeKey(pw, timeGan);
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy;MM;dd");
        return formatter.format(new Date());
    }

    public static String getDataTimeByTimeDim(String timeDimValue, String periodType) {
        String year = timeDimValue.substring(0, 4);
        String datatime = timeDimValue;
        if (timeDimValue != null && timeDimValue.length() == 8) {
            if ("Y".equals(periodType)) {
                datatime = year + "Y00" + timeDimValue.substring(4, 6);
            } else if ("N".equals(periodType)) {
                datatime = year + "N0001";
            } else if ("J".equals(periodType)) {
                int month = Integer.parseInt(timeDimValue.substring(4, 6));
                datatime = year + "J000" + ((month - 1) / 3 + 1);
            } else if ("H".equals(periodType)) {
                int month = Integer.parseInt(timeDimValue.substring(4, 6));
                datatime = year + "H000" + ((month - 1) / 6 + 1);
            } else if ("R".equals(periodType)) {
                int month = Integer.parseInt(timeDimValue.substring(4, 6));
                int day = Integer.parseInt(timeDimValue.substring(6, 8));
                Calendar date = Calendar.getInstance();
                date.set(1, Integer.valueOf(year));
                date.set(2, month - 1);
                date.set(5, day);
                int dayOfYear = date.get(6);
                String dayStr = String.valueOf(dayOfYear);
                for (int i = 0; i < 3 - dayStr.length(); ++i) {
                    dayStr = "0" + dayStr;
                }
                datatime = year + "R0" + dayStr;
            }
        }
        return datatime;
    }

    public String periodWrapperToTimeKey(PeriodWrapper pw) {
        if (pw == null) {
            return null;
        }
        PeriodType pt = PeriodType.fromType((int)pw.getType());
        return this.periodWrapperToTimeKey(pw, TimeDimHelper.periodTypeToTimeGranularity(pt));
    }

    public String periodToTimeKey(String period) {
        if (period == null) {
            return null;
        }
        String timeKey = period;
        if (period.length() == 9) {
            PeriodWrapper pw = new PeriodWrapper(period);
            timeKey = this.periodWrapperToTimeKey(pw);
        }
        return timeKey;
    }

    public String timeKeyToPeriod(String timeKey, TimeGranularity timeGan) {
        String period = timeKey;
        if (timeKey == null || timeGan == null) {
            return period;
        }
        if (timeKey.length() == 8) {
            try {
                PeriodType pt = TimeDimHelper.timeGranularityToPeriodType(timeGan);
                if (pt == null) {
                    return period;
                }
                if (timeGan == TimeGranularity.MONTH) {
                    return this.timeKeyToPeriod(timeKey, pt);
                }
                Date date = this.dateFormat.parse(timeKey);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                PeriodWrapper pw = pt.fromCalendar(calendar);
                period = pw.toString();
            }
            catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return period;
    }

    public String timeKeyToPeriod(String timeKey, PeriodType pt) {
        String period = timeKey;
        if (timeKey == null || pt == null) {
            return period;
        }
        if (timeKey.length() == 8) {
            if (pt == PeriodType.MONTH) {
                String periodTypeCode = String.valueOf((char)pt.code());
                return TimeDimHelper.getDataTimeByTimeDim(timeKey, periodTypeCode);
            }
            if (pt != PeriodType.CUSTOM && pt != PeriodType.WEEK) {
                try {
                    Date date = this.dateFormat.parse(timeKey);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    PeriodWrapper pw = pt.fromCalendar(calendar);
                    period = pw.toString();
                }
                catch (ParseException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return period;
    }

    public static PeriodType timeGranularityToPeriodType(TimeGranularity timeGranularity) {
        if (timeGranularity == null) {
            return null;
        }
        switch (timeGranularity) {
            case YEAR: {
                return PeriodType.YEAR;
            }
            case HALFYEAR: {
                return PeriodType.HALFYEAR;
            }
            case QUARTER: {
                return PeriodType.SEASON;
            }
            case MONTH: {
                return PeriodType.MONTH;
            }
            case XUN: {
                return PeriodType.TENDAY;
            }
            case DAY: {
                return PeriodType.DAY;
            }
            case WEEK: {
                return PeriodType.WEEK;
            }
        }
        return null;
    }

    public static TimeGranularity periodTypeToTimeGranularity(PeriodType periodType) {
        if (periodType == null) {
            return null;
        }
        if (periodType == PeriodType.YEAR) {
            return TimeGranularity.YEAR;
        }
        if (periodType == PeriodType.HALFYEAR) {
            return TimeGranularity.HALFYEAR;
        }
        if (periodType == PeriodType.SEASON) {
            return TimeGranularity.QUARTER;
        }
        if (periodType == PeriodType.MONTH) {
            return TimeGranularity.MONTH;
        }
        if (periodType == PeriodType.TENDAY) {
            return TimeGranularity.XUN;
        }
        if (periodType == PeriodType.DAY) {
            return TimeGranularity.DAY;
        }
        if (periodType == PeriodType.WEEK) {
            return TimeGranularity.WEEK;
        }
        return null;
    }

    private static String getDateStringByIntValues(int year, int month, int day, TimeGranularity timeGan) {
        String monthStr = TimeDimHelper.getMonth(month, timeGan);
        String dayStr = TimeDimHelper.getDay(day, timeGan);
        return year + monthStr + dayStr;
    }

    private static String getDay(int day, TimeGranularity timeGan) {
        String dayStr = String.valueOf(day);
        if (timeGan == TimeGranularity.DAY) {
            if (dayStr.length() == 1) {
                dayStr = "0" + dayStr;
            }
        } else if (timeGan == TimeGranularity.XUN) {
            if (day < 11) {
                return "01";
            }
            if (day > 10 && day < 21) {
                return "11";
            }
            if (day > 20) {
                return "21";
            }
        } else {
            dayStr = "01";
        }
        return dayStr;
    }

    private static String getMonth(int month, TimeGranularity timeGan) {
        String monthStr = String.valueOf(month);
        if (timeGan == TimeGranularity.MONTH || timeGan == TimeGranularity.DAY) {
            if (monthStr.length() == 1) {
                monthStr = "0" + monthStr;
            }
        } else if (timeGan == TimeGranularity.HALFYEAR) {
            monthStr = Integer.valueOf(monthStr) > 6 ? "07" : "01";
        } else if (timeGan == TimeGranularity.YEAR) {
            monthStr = "01";
        } else if (timeGan == TimeGranularity.QUARTER) {
            int m = Integer.valueOf(monthStr);
            if (m < 4) {
                monthStr = "01";
            } else if (m >= 4 && m < 7) {
                monthStr = "04";
            } else if (m >= 7 && m < 10) {
                monthStr = "07";
            } else if (m >= 10 && m < 13) {
                monthStr = "10";
            }
        }
        return monthStr;
    }

    private String periodWrapperToTimeKey(PeriodWrapper pw, TimeGranularity timeGan) {
        if (pw == null) {
            return null;
        }
        String timeKey = pw.toString();
        PeriodType pt = PeriodType.fromType((int)pw.getType());
        if (pt == PeriodType.MONTH && (pw.getPeriod() < 1 || pw.getPeriod() > 12) && pt == PeriodType.MONTH) {
            return String.valueOf(pw.getYear()) + TimeDimHelper.getMonth(pw.getPeriod(), timeGan) + "01";
        }
        if (pt != PeriodType.CUSTOM && pt != PeriodType.WEEK) {
            Calendar calendar = pt.toCalendar(pw);
            timeKey = this.dateFormat.format(calendar.getTime());
        }
        return timeKey;
    }

    public static List<String> getPeiodStrList(IPeriodAdapter periodAdapter, PeriodWrapper minPeriod, PeriodWrapper maxPeriod) {
        ArrayList<String> periodList = new ArrayList<String>();
        List<PeriodWrapper> periodWrapperList = TimeDimHelper.getPeiodWrapperList(periodAdapter, minPeriod, maxPeriod);
        if (null != periodWrapperList) {
            for (PeriodWrapper item : periodWrapperList) {
                periodList.add(item.toString());
            }
        }
        return periodList;
    }

    public static List<PeriodWrapper> getPeiodWrapperList(IPeriodAdapter periodAdapter, PeriodWrapper minPeriod, PeriodWrapper maxPeriod) {
        ArrayList<PeriodWrapper> periods = new ArrayList<PeriodWrapper>();
        if (periodAdapter == null) {
            return PeriodUtil.getPeiodWrapperList((PeriodWrapper)minPeriod, (PeriodWrapper)maxPeriod);
        }
        if (minPeriod != null && maxPeriod != null && minPeriod.getType() == maxPeriod.getType()) {
            while (minPeriod.compareTo((Object)maxPeriod) <= 0 && minPeriod.getYear() != 1900) {
                periods.add(new PeriodWrapper(minPeriod));
                if (periodAdapter.nextPeriod(minPeriod)) continue;
            }
            return periods;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("period to timeKey");
        String period = "2022N0001";
        TimeDimHelper helper = new TimeDimHelper();
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        period = "2022H0002";
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        period = "2022J0002";
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        period = "2022Y0003";
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        period = "2022X0003";
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        period = "2022R0024";
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        period = "2022B0001";
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        period = "2022Z0001";
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        period = "2022Y0000";
        System.out.println(period + "-->" + helper.periodToTimeKey(period));
        System.out.println("timeKey to period ");
        String timeKey = "20220101";
        System.out.println(timeKey + "-->" + helper.timeKeyToPeriod(timeKey, TimeGranularity.YEAR));
        timeKey = "20220701";
        System.out.println(timeKey + "-->" + helper.timeKeyToPeriod(timeKey, TimeGranularity.HALFYEAR));
        System.out.println(timeKey + "-->" + helper.timeKeyToPeriod(timeKey, TimeGranularity.QUARTER));
        System.out.println(timeKey + "-->" + helper.timeKeyToPeriod(timeKey, TimeGranularity.MONTH));
        System.out.println(timeKey + "-->" + helper.timeKeyToPeriod(timeKey, TimeGranularity.XUN));
        System.out.println(timeKey + "-->" + helper.timeKeyToPeriod(timeKey, TimeGranularity.DAY));
        timeKey = "20221301";
        System.out.println(timeKey + "-->" + helper.timeKeyToPeriod(timeKey, TimeGranularity.MONTH));
    }
}

