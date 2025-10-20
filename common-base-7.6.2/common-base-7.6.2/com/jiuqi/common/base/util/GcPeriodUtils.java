/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GcPeriodUtils {
    public static Date[] getDateArr(String periodStr) {
        return GcPeriodUtils.getDateArr(new PeriodWrapper(periodStr));
    }

    public static Date[] getDateArr(PeriodWrapper pWrapper) {
        try {
            String[] periodList = GcPeriodUtils.getTimesArr(pWrapper);
            if (periodList == null) {
                return null;
            }
            Date[] dateRegion = new Date[]{DateUtils.parse(periodList[0]), DateUtils.parse(periodList[1])};
            return dateRegion;
        }
        catch (Exception e) {
            throw new RuntimeException("\u89e3\u6790\u671f\u95f4\u5931\u8d25:" + pWrapper, e);
        }
    }

    public static String[] getTimesArr(String periodStr) {
        return GcPeriodUtils.getTimesArr(new PeriodWrapper(periodStr));
    }

    public static String[] getTimesArr(PeriodWrapper pWrapper) {
        return GcPeriodUtils.getTimesArr(pWrapper, pWrapper);
    }

    public static String[] getTimesArr(PeriodWrapper startPeriodWrapper, PeriodWrapper endPeriodWrapper) {
        if (PeriodConsts.codeToType((int)startPeriodWrapper.toString().charAt(4)) == 4) {
            startPeriodWrapper = GcPeriodUtils.convertSpecialPeriod(startPeriodWrapper);
            endPeriodWrapper = GcPeriodUtils.convertSpecialPeriod(endPeriodWrapper);
        }
        if (startPeriodWrapper.getType() == 7) {
            return GcPeriodUtils.weekDate(startPeriodWrapper, endPeriodWrapper);
        }
        PeriodRange periodRange = new PeriodRange(startPeriodWrapper, endPeriodWrapper);
        return GcPeriodUtils.getTimes(periodRange);
    }

    private static PeriodWrapper convertSpecialPeriod(PeriodWrapper periodWrapper) {
        int period = periodWrapper.getPeriod();
        if (period == 0) {
            return new PeriodWrapper(periodWrapper.getYear(), 4, 1);
        }
        if (period > 12) {
            return new PeriodWrapper(periodWrapper.getYear(), 4, 12);
        }
        return periodWrapper;
    }

    private static String[] getTimes(PeriodRange periodRange) {
        String[] ret;
        switch (periodRange.getType()) {
            case 1: {
                ret = GcPeriodUtils.yearDate(periodRange);
                break;
            }
            case 2: {
                ret = GcPeriodUtils.halfYearDate(periodRange);
                break;
            }
            case 3: {
                ret = GcPeriodUtils.seasonDate(periodRange);
                break;
            }
            case 4: {
                ret = GcPeriodUtils.monthDate(periodRange);
                break;
            }
            case 5: {
                ret = GcPeriodUtils.tendayDate(periodRange);
                break;
            }
            case 7: {
                ret = GcPeriodUtils.weekDate(periodRange);
                break;
            }
            case 6: {
                ret = GcPeriodUtils.dayDate(periodRange);
                break;
            }
            default: {
                ret = null;
            }
        }
        return ret;
    }

    private static String[] yearDate(PeriodRange periodRange) {
        String[] ret = new String[2];
        String sp = periodRange.getSplit();
        boolean isFrom = sp.charAt(0) == '+';
        int y1 = periodRange.getStartYear();
        int m1 = Integer.parseInt(sp.substring(1, 3));
        int d1 = Integer.parseInt(sp.substring(3, 5));
        if (isFrom) {
            Calendar calendar = GcPeriodUtils.getCalendar(y1, m1 - 1, d1);
            ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
            int y2 = periodRange.getEndYear();
            calendar = GcPeriodUtils.getCalendar(y2 + 1, m1 - 1, d1);
            calendar.add(5, -1);
            ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
        } else {
            int y2 = periodRange.getEndYear();
            Calendar calendar = GcPeriodUtils.getCalendar(y2, m1 - 1, d1);
            ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
            calendar = GcPeriodUtils.getCalendar(y1 - 1, m1 - 1, d1);
            calendar.add(5, 1);
            ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
        }
        return ret;
    }

    private static String[] halfYearDate(PeriodRange periodRange) {
        String[] ret = new String[2];
        String sp = periodRange.getSplit();
        boolean isFrom = sp.charAt(0) == '+';
        int m1 = Integer.parseInt(sp.substring(1, 3));
        int d1 = Integer.parseInt(sp.substring(3, 5));
        int m2 = Integer.parseInt(sp.substring(6, 8));
        int d2 = Integer.parseInt(sp.substring(8, 10));
        int y1 = periodRange.getStartYear();
        int y2 = periodRange.getEndYear();
        if (isFrom) {
            Calendar calendar;
            if (1 == periodRange.getStartPeriod()) {
                calendar = GcPeriodUtils.getCalendar(y1, m1 - 1, d1);
                ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
            } else {
                calendar = GcPeriodUtils.getCalendar(y1, m2 - 1, d2);
                ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
            }
            if (1 == periodRange.getEndPeriod()) {
                calendar = GcPeriodUtils.getCalendar(y2, m2 - 1, d2);
                calendar.add(5, -1);
                ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
            } else {
                calendar = GcPeriodUtils.getCalendar(y2 + 1, m1 - 1, d1);
                calendar.add(5, -1);
                ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
            }
        } else {
            Calendar calendar;
            if (1 == periodRange.getStartPeriod()) {
                calendar = GcPeriodUtils.getCalendar(y1 - 1, m2 - 1, d2);
                calendar.add(5, 1);
                ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
            } else {
                calendar = GcPeriodUtils.getCalendar(y2 + 1, m1 - 1, d1);
                calendar.add(5, 1);
                ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
            }
            if (1 == periodRange.getEndPeriod()) {
                calendar = GcPeriodUtils.getCalendar(y2, m1 - 1, d1);
                ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
            } else {
                calendar = GcPeriodUtils.getCalendar(y2, m2 - 1, d2);
                ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
            }
        }
        return ret;
    }

    private static String[] seasonDate(PeriodRange periodRange) {
        String[] ret = new String[2];
        String sp = periodRange.getSplit();
        boolean isFrom = sp.charAt(0) == '+';
        int[] m = new int[]{0, 0, 0, 0};
        int[] d = new int[]{0, 0, 0, 0};
        m[0] = Integer.parseInt(sp.substring(1, 3));
        d[0] = Integer.parseInt(sp.substring(3, 5));
        m[1] = Integer.parseInt(sp.substring(6, 8));
        d[1] = Integer.parseInt(sp.substring(8, 10));
        m[2] = Integer.parseInt(sp.substring(11, 13));
        d[2] = Integer.parseInt(sp.substring(13, 15));
        m[3] = Integer.parseInt(sp.substring(16, 18));
        d[3] = Integer.parseInt(sp.substring(18, 20));
        int y1 = periodRange.getStartYear();
        int y2 = periodRange.getEndYear();
        int i = periodRange.getStartPeriod() - 1;
        int j = periodRange.getEndPeriod() - 1;
        if (isFrom) {
            Calendar calendar = GcPeriodUtils.getCalendar(y1, m[i] - 1, d[i]);
            ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
            int other = (j + 1) % 4;
            int jj = j == 3 ? 1 : 0;
            calendar = GcPeriodUtils.getCalendar(y2 + jj, m[other] - 1, d[other]);
            calendar.add(5, -1);
            ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
        } else {
            Calendar calendar = GcPeriodUtils.getCalendar(y2, m[j] - 1, d[j]);
            ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
            int other = (i - 1 + 4) % 4;
            int ii = i == 0 ? 1 : 0;
            calendar = GcPeriodUtils.getCalendar(y1 - ii, m[other] - 1, d[other]);
            calendar.add(5, 1);
            ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
        }
        return ret;
    }

    private static String[] monthDate(PeriodRange periodRange) {
        String[] ret = new String[2];
        String sp = periodRange.getSplit();
        boolean isFrom = sp.charAt(0) == '+';
        int d1 = Integer.parseInt(sp.substring(1, 3));
        int y1 = periodRange.getStartYear();
        int y2 = periodRange.getEndYear();
        int t1 = periodRange.getStartPeriod();
        int t2 = periodRange.getEndPeriod();
        if (isFrom) {
            Calendar calendar = GcPeriodUtils.getCalendar(y1, t1 - 1, d1);
            ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
            int balance = t2 == 12 ? 1 : 0;
            t2 = t2 == 12 ? 1 : t2 + 1;
            calendar = GcPeriodUtils.getCalendar(y2 + balance, t2 - 1, d1);
            calendar.add(5, -1);
            ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
        } else {
            Calendar calendar = GcPeriodUtils.getCalendar(y2, t2 - 1, d1);
            ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
            int balance = t1 == 1 ? 1 : 0;
            t1 = t1 == 1 ? 12 : t1 - 1;
            calendar = GcPeriodUtils.getCalendar(y1 - balance, t1 - 1, d1);
            calendar.add(5, 1);
            ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
        }
        return ret;
    }

    private static String[] tendayDate(PeriodRange periodRange) {
        String[] ret = new String[2];
        String sp = periodRange.getSplit();
        int[] d = new int[]{0, 0, 0, 0};
        d[1] = Integer.parseInt(sp.substring(1, 3));
        d[2] = Integer.parseInt(sp.substring(4, 6));
        d[3] = Integer.parseInt(sp.substring(7, 9));
        boolean isFrom = sp.charAt(0) == '+';
        int y1 = periodRange.getStartYear();
        int m1 = periodRange.getStartPeriod() / 3 + 1;
        int t1 = periodRange.getStartPeriod() % 3;
        if (m1 == 13) {
            m1 = 12;
        }
        if (t1 == 0) {
            t1 = 3;
        }
        int y2 = periodRange.getEndYear();
        int m2 = periodRange.getEndPeriod() / 3 + 1;
        int t2 = periodRange.getEndPeriod() % 3;
        if (m2 == 13) {
            m2 = 12;
        }
        if (t2 == 0) {
            t2 = 3;
        }
        if (isFrom) {
            Calendar calendar = GcPeriodUtils.getCalendar(y1, m1 - 1, d[t1]);
            ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
            int balanceY = m2 == 12 && t2 == 3 ? 1 : 0;
            m2 = t2 == 3 ? m2 % 12 + 1 : m2;
            t2 = t2 == 3 ? 1 : t2 + 1;
            calendar = GcPeriodUtils.getCalendar(y2 + balanceY, m2 - 1, d[t2]);
            calendar.add(5, -1);
            ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
        } else {
            int balanceY;
            Calendar calendar = GcPeriodUtils.getCalendar(y2, m2 - 1, d[t2]);
            ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
            int n = balanceY = m1 == 1 && t1 == 1 ? 1 : 0;
            if (m1 == 1 && t1 == 1) {
                m1 = 12;
            } else if (t1 == 1) {
                --m1;
            }
            t1 = t1 == 1 ? 3 : t1 - 1;
            calendar = GcPeriodUtils.getCalendar(y1 - balanceY, m1 - 1, d[t1]);
            calendar.add(5, 1);
            ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
        }
        return ret;
    }

    private static String[] weekDate(PeriodRange periodRange) {
        String[] ret = new String[2];
        String sp = periodRange.getSplit();
        int offset = Integer.parseInt(sp.substring(2));
        boolean isFrom = sp.charAt(0) == '+';
        int y1 = periodRange.getStartYear();
        int y2 = periodRange.getEndYear();
        int t1 = periodRange.getStartPeriod();
        int t2 = periodRange.getEndPeriod();
        offset = isFrom ? (offset == 7 ? 1 : ++offset) : (offset == 7 ? 2 : (offset += 2));
        ret[0] = GcPeriodUtils.getDayOfWeek(y1, t1, offset, true);
        ret[1] = GcPeriodUtils.getDayOfWeek(y2, t2, offset, false);
        return ret;
    }

    private static String getDayOfWeek(int year, int weekIndex, int firstDay, boolean isFirst) {
        Calendar calendar = GcPeriodUtils.getCalendar(year, 0, 1);
        calendar.setMinimalDaysInFirstWeek(1);
        calendar.setFirstDayOfWeek(firstDay);
        calendar.add(5, weekIndex * 7 - 1);
        int weekOfYear = calendar.get(3);
        if (weekOfYear != weekIndex) {
            calendar.add(5, -7);
        }
        int dayOfWeek = calendar.get(7);
        if (isFirst) {
            calendar.add(5, 1 - dayOfWeek);
            int currYear = calendar.get(1);
            if (currYear != year) {
                calendar.set(year, 0, 1);
            }
        } else {
            calendar.add(5, 7 - dayOfWeek);
            int currYear = calendar.get(1);
            if (currYear != year) {
                calendar.set(year + 1, 0, 1);
                calendar.add(5, -1);
            }
        }
        return GcPeriodUtils.getTimeOfCalendar(calendar);
    }

    private static String[] weekDate(PeriodWrapper startPeriodWrapper, PeriodWrapper endPeriodWrapper) {
        String[] arr = new String[2];
        PeriodType pt = PeriodType.fromType((int)startPeriodWrapper.getType());
        Calendar c = pt.toCalendar(startPeriodWrapper);
        arr[0] = GcPeriodUtils.getTimeOfCalendar(c);
        if (startPeriodWrapper == endPeriodWrapper) {
            c.add(6, 6);
        } else {
            PeriodWrapper endNext = new PeriodWrapper(endPeriodWrapper);
            endNext.nextPeriod();
            c = pt.toCalendar(endNext);
            c.add(6, -1);
        }
        arr[1] = GcPeriodUtils.getTimeOfCalendar(c);
        return arr;
    }

    private static String[] dayDate(PeriodRange periodRange) {
        String[] ret = new String[2];
        Calendar calendar = GcPeriodUtils.getCalendar(periodRange.getStartYear(), 0, 1);
        calendar.add(5, periodRange.getStartPeriod() - 1);
        ret[0] = GcPeriodUtils.getTimeOfCalendar(calendar);
        calendar = GcPeriodUtils.getCalendar(periodRange.getStartYear(), 0, 1);
        calendar.add(5, periodRange.getEndPeriod() - 1);
        ret[1] = GcPeriodUtils.getTimeOfCalendar(calendar);
        return ret;
    }

    private static Calendar getCalendar(int y, int m, int d) {
        GregorianCalendar calendar = new GregorianCalendar(y, m, d);
        int month = calendar.get(2);
        while (month > m) {
            ((Calendar)calendar).add(5, -1);
            month = calendar.get(2);
        }
        return calendar;
    }

    private static String getTimeOfCalendar(Calendar calendar) {
        int year = calendar.get(1);
        int month = calendar.get(2);
        int day = calendar.get(5);
        return year + "-" + (month + 1) + "-" + day;
    }

    static class PeriodRange {
        private final int type;
        private final int startYear;
        private final int startPeriod;
        private final int endYear;
        private final int endPeriod;
        private String split;

        public PeriodRange(PeriodWrapper startPeriodWrapper, PeriodWrapper endPeriodWrapper) {
            this.type = startPeriodWrapper.getType();
            this.startYear = startPeriodWrapper.getYear();
            this.startPeriod = startPeriodWrapper.getPeriod();
            this.endYear = endPeriodWrapper.getYear();
            this.endPeriod = endPeriodWrapper.getPeriod();
        }

        public String getSplit() {
            if (this.split == null || "".equals(this.split)) {
                this.split = this.createDefaultSplit(this.type);
            }
            return this.split;
        }

        private String createDefaultSplit(int periodType) {
            String result = "";
            switch (periodType) {
                case 1: {
                    result = "+0101";
                    break;
                }
                case 2: {
                    result = "+0101,0701";
                    break;
                }
                case 3: {
                    result = "+0101,0401,0701,1001";
                    break;
                }
                case 4: {
                    result = "+01";
                    break;
                }
                case 5: {
                    result = "+01,11,21";
                    break;
                }
                case 7: {
                    result = "+010101";
                    break;
                }
                case 6: {
                    result = "+0101";
                    break;
                }
                case 8: {
                    result = "";
                }
            }
            return result;
        }

        public int getType() {
            return this.type;
        }

        public int getStartYear() {
            return this.startYear;
        }

        public int getStartPeriod() {
            return this.startPeriod;
        }

        public int getEndYear() {
            return this.endYear;
        }

        public int getEndPeriod() {
            return this.endPeriod;
        }
    }
}

