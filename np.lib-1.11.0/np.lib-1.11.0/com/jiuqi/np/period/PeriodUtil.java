/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period;

import com.jiuqi.np.period.DateAider;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PeriodUtil {
    static final int[] tenday = new int[]{1, 12, 22};

    public static final PeriodWrapper currentPeriod(int periodType) {
        return PeriodUtil.currentPeriod(periodType, 0);
    }

    public static final PeriodWrapper currentPeriod(int periodType, int periodOffset) {
        return PeriodUtil.currentPeriod(1970, 9999, periodType, periodOffset);
    }

    public static final PeriodWrapper getPeriodWrapper(String periodString) {
        return new PeriodWrapper(periodString);
    }

    public static final GregorianCalendar getCurrentCalendar() {
        return new GregorianCalendar();
    }

    public static final GregorianCalendar getCurrentCalendar(Date currDate) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (null != currDate) {
            calendar.setTime(currDate);
        }
        return calendar;
    }

    public static final PeriodWrapper currentPeriod(int startYear, int stopYear, int periodType, int periodOffset) {
        GregorianCalendar calendar = PeriodUtil.getCurrentCalendar();
        return PeriodUtil.currentPeriod(calendar, startYear, stopYear, periodType, periodOffset);
    }

    public static final PeriodWrapper currentPeriod(GregorianCalendar calendar, int periodType, int periodOffset) {
        PeriodWrapper currPeriod = new PeriodWrapper();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int weekOfYear = calendar.get(3);
        int dayOfMonth = calendar.get(5);
        int dayOfYear = calendar.get(6);
        int period = 1;
        switch (periodType) {
            case 1: {
                currPeriod.setAll(year += periodOffset, periodType, period);
                break;
            }
            case 2: {
                period = month < 7 ? 1 : 2;
                year += periodOffset / 2;
                if ((period += periodOffset % 2) <= 0) {
                    --year;
                    period = 2;
                } else if (period > 2) {
                    ++year;
                    period = 1;
                }
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 3: {
                period = month < 4 ? 1 : (month < 7 ? 2 : (month < 10 ? 3 : 4));
                year += periodOffset / 4;
                if ((period += periodOffset % 4) <= 0) {
                    --year;
                    period = 4 + period;
                } else if (period > 4) {
                    ++year;
                    period -= 4;
                }
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 4: {
                period = month;
                year += periodOffset / 12;
                if ((period += periodOffset % 12) <= 0) {
                    --year;
                    period = 12 + period;
                } else if (period > 12) {
                    ++year;
                    period -= 12;
                }
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 5: {
                int tailTenDay = dayOfMonth < 11 ? 1 : (dayOfMonth < 21 ? 2 : 3);
                period = (month - 1) * 3 + tailTenDay;
                year += periodOffset / 36;
                if ((period += periodOffset % 36) <= 0) {
                    --year;
                    period = 36 + period;
                } else if (period > 36) {
                    ++year;
                    period -= 36;
                }
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 7: {
                if (weekOfYear == 1 && month == 12) {
                    weekOfYear = 53;
                }
                period = weekOfYear;
                year += periodOffset / 53;
                if ((period += periodOffset % 53) <= 0) {
                    --year;
                    period = 53 + period;
                } else if (period > 53) {
                    ++year;
                    period -= 53;
                }
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 6: {
                period = dayOfYear;
                GregorianCalendar startCalendar = new GregorianCalendar(year, month - 1, dayOfMonth);
                ((Calendar)startCalendar).add(5, periodOffset);
                year = startCalendar.get(1);
                period = startCalendar.get(6);
                currPeriod.setAll(year, periodType, period);
                break;
            }
        }
        return currPeriod;
    }

    @Deprecated
    public static final PeriodWrapper currentPeriod(GregorianCalendar calendar, int startYear, int stopYear, int periodType, int periodOffset) {
        PeriodWrapper currPeriod = new PeriodWrapper();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int weekOfYear = calendar.get(3);
        int dayOfMonth = calendar.get(5);
        int dayOfYear = calendar.get(6);
        int period = 1;
        switch (periodType) {
            case 1: {
                year += periodOffset;
                year = PeriodUtil.checkYearVaild(year, startYear, stopYear);
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 2: {
                period = month < 7 ? 1 : 2;
                year += periodOffset / 2;
                if ((period += periodOffset % 2) <= 0) {
                    --year;
                    period = 2;
                } else if (period > 2) {
                    ++year;
                    period = 1;
                }
                year = PeriodUtil.checkYearVaild(year, startYear, stopYear);
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 3: {
                period = month < 4 ? 1 : (month < 7 ? 2 : (month < 10 ? 3 : 4));
                year += periodOffset / 4;
                if ((period += periodOffset % 4) <= 0) {
                    --year;
                    period = 4 + period;
                } else if (period > 4) {
                    ++year;
                    period -= 4;
                }
                year = PeriodUtil.checkYearVaild(year, startYear, stopYear);
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 4: {
                period = month;
                year += periodOffset / 12;
                if ((period += periodOffset % 12) <= 0) {
                    --year;
                    period = 12 + period;
                } else if (period > 12) {
                    ++year;
                    period -= 12;
                }
                year = PeriodUtil.checkYearVaild(year, startYear, stopYear);
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 5: {
                int tailTenDay = dayOfMonth < 11 ? 1 : (dayOfMonth < 21 ? 2 : 3);
                period = (month - 1) * 3 + tailTenDay;
                year += periodOffset / 36;
                if ((period += periodOffset % 36) <= 0) {
                    --year;
                    period = 36 + period;
                } else if (period > 36) {
                    ++year;
                    period -= 36;
                }
                year = PeriodUtil.checkYearVaild(year, startYear, stopYear);
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 7: {
                period = weekOfYear;
                year += periodOffset / 53;
                if ((period += periodOffset % 53) <= 0) {
                    --year;
                    period = 53 + period;
                } else if (period > 53) {
                    ++year;
                    period -= 53;
                }
                year = PeriodUtil.checkYearVaild(year, startYear, stopYear);
                currPeriod.setAll(year, periodType, period);
                break;
            }
            case 6: {
                period = dayOfYear;
                GregorianCalendar startCalendar = new GregorianCalendar(year, month - 1, dayOfMonth);
                ((Calendar)startCalendar).add(5, periodOffset);
                year = startCalendar.get(1);
                period = startCalendar.get(6);
                year = PeriodUtil.checkYearVaild(year, startYear, stopYear);
                currPeriod.setAll(year, periodType, period);
                break;
            }
        }
        return currPeriod;
    }

    public static int currentPeriodNumber(int periodType, int periodOffset) {
        GregorianCalendar calendar = PeriodUtil.getCurrentCalendar();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int weekOfYear = calendar.get(3);
        int dayOfMonth = calendar.get(5);
        int dayOfYear = calendar.get(6);
        int period = 0;
        switch (periodType) {
            case 1: {
                period = 1;
                break;
            }
            case 2: {
                int n = period = month < 7 ? 1 : 2;
                if ((period += periodOffset % 2) <= 0) {
                    period = 2;
                    break;
                }
                if (period <= 2) break;
                period = 1;
                break;
            }
            case 3: {
                int n = month < 4 ? 1 : (month < 7 ? 2 : (period = month < 10 ? 3 : 4));
                if ((period += periodOffset % 4) <= 0) {
                    period = 4 + period;
                    break;
                }
                if (period <= 4) break;
                period -= 4;
                break;
            }
            case 4: {
                period = month;
                if ((period += periodOffset % 12) <= 0) {
                    period = 12 + period;
                    break;
                }
                if (period <= 12) break;
                period -= 12;
                break;
            }
            case 5: {
                int tailTenDay = dayOfMonth < 11 ? 1 : (dayOfMonth < 21 ? 2 : 3);
                period = (month - 1) * 3 + tailTenDay;
                if ((period += periodOffset % 36) <= 0) {
                    period = 36 + period;
                    break;
                }
                if (period <= 36) break;
                period -= 36;
                break;
            }
            case 7: {
                period = weekOfYear;
                if ((period += periodOffset % 53) <= 0) {
                    period = 53 + period;
                    break;
                }
                if (period <= 53) break;
                period -= 53;
                break;
            }
            case 6: {
                period = dayOfYear;
                GregorianCalendar startCalendar = new GregorianCalendar(year, month - 1, dayOfMonth);
                ((Calendar)startCalendar).add(5, -periodOffset);
                if ((period += (int)((calendar.getTimeInMillis() - startCalendar.getTimeInMillis()) / 86400000L)) != 0) break;
                period = 1;
                break;
            }
        }
        return period;
    }

    private static int checkYearVaild(int year, int startYear, int stopYear) {
        if (year > stopYear) {
            year = stopYear;
        } else if (year < startYear) {
            year = startYear;
        }
        return year;
    }

    public static GregorianCalendar period2Calendar(String periodStr) {
        return PeriodUtil.period2Calendar(new PeriodWrapper(periodStr));
    }

    public static GregorianCalendar period2Calendar(PeriodWrapper pWrapper) {
        GregorianCalendar calendar = new GregorianCalendar();
        switch (pWrapper.getType()) {
            case 1: {
                calendar.set(1, pWrapper.getYear());
                break;
            }
            case 2: {
                calendar.set(pWrapper.getYear(), pWrapper.getPeriod() * 6 - 1, 1);
                break;
            }
            case 3: {
                calendar.set(pWrapper.getYear(), pWrapper.getPeriod() * 3 - 1, 1);
                break;
            }
            case 4: {
                calendar.set(pWrapper.getYear(), pWrapper.getPeriod() - 1, 1);
                break;
            }
            case 5: {
                calendar.set(pWrapper.getYear(), (pWrapper.getPeriod() - 1) / 3, tenday[(pWrapper.getPeriod() - 1) % 3]);
                break;
            }
            case 6: {
                calendar.set(1, pWrapper.getYear());
                calendar.set(6, pWrapper.getPeriod());
                break;
            }
            default: {
                calendar.set(1, pWrapper.getYear());
            }
        }
        return calendar;
    }

    public static GregorianCalendar period2ConcreteCalendar(PeriodWrapper pWrapper) {
        GregorianCalendar calendar = PeriodUtil.period2Calendar(pWrapper);
        calendar.set(1, pWrapper.getYear());
        return calendar;
    }

    public static String[] getTimesArr(String periodStr) {
        return PeriodUtil.getTimesArr(new PeriodWrapper(periodStr));
    }

    public static String[] getTimesArr(PeriodWrapper pWrapper) {
        return PeriodUtil.getTimesArr(pWrapper, pWrapper);
    }

    public static String[] getTimesArr(PeriodWrapper startPeriodWrapper, PeriodWrapper endPeriodWrapper) {
        if (startPeriodWrapper.getType() == 7) {
            return PeriodUtil.weekDate(startPeriodWrapper, endPeriodWrapper);
        }
        PeriodUtil periodUtil = new PeriodUtil();
        periodUtil.getClass();
        PeriodRange periodRange = periodUtil.new PeriodRange(startPeriodWrapper, endPeriodWrapper);
        return PeriodUtil.getTimes(periodRange);
    }

    private static String[] getTimes(PeriodRange periodRange) {
        String[] ret = new String[2];
        switch (periodRange.getType()) {
            case 1: {
                ret = PeriodUtil.yearDate(periodRange);
                break;
            }
            case 2: {
                ret = PeriodUtil.halfYearDate(periodRange);
                break;
            }
            case 3: {
                ret = PeriodUtil.seasonDate(periodRange);
                break;
            }
            case 4: {
                ret = PeriodUtil.monthDate(periodRange);
                break;
            }
            case 5: {
                ret = PeriodUtil.tendayDate(periodRange);
                break;
            }
            case 7: {
                ret = PeriodUtil.weekDate(periodRange);
                break;
            }
            case 6: {
                ret = PeriodUtil.dayDate(periodRange);
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
        Calendar calendar = null;
        if (isFrom) {
            calendar = PeriodUtil.getCalendar(y1, m1 - 1, d1);
            ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
            int y2 = periodRange.getEndYear();
            calendar = PeriodUtil.getCalendar(y2 + 1, m1 - 1, d1);
            calendar.add(5, -1);
            ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
        } else {
            int y2 = periodRange.getEndYear();
            calendar = PeriodUtil.getCalendar(y2, m1 - 1, d1);
            ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
            calendar = PeriodUtil.getCalendar(y1 - 1, m1 - 1, d1);
            calendar.add(5, 1);
            ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
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
        Calendar calendar = null;
        if (isFrom) {
            if (1 == periodRange.getStartPeriod()) {
                calendar = PeriodUtil.getCalendar(y1, m1 - 1, d1);
                ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
            } else {
                calendar = PeriodUtil.getCalendar(y1, m2 - 1, d2);
                ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
            }
            if (1 == periodRange.getEndPeriod()) {
                calendar = PeriodUtil.getCalendar(y2, m2 - 1, d2);
                calendar.add(5, -1);
                ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
            } else {
                calendar = PeriodUtil.getCalendar(y2 + 1, m1 - 1, d1);
                calendar.add(5, -1);
                ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
            }
        } else {
            if (1 == periodRange.getStartPeriod()) {
                calendar = PeriodUtil.getCalendar(y1 - 1, m2 - 1, d2);
                calendar.add(5, 1);
                ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
            } else {
                calendar = PeriodUtil.getCalendar(y2 + 1, m1 - 1, d1);
                calendar.add(5, 1);
                ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
            }
            if (1 == periodRange.getEndPeriod()) {
                calendar = PeriodUtil.getCalendar(y2, m1 - 1, d1);
                ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
            } else {
                calendar = PeriodUtil.getCalendar(y2, m2 - 1, d2);
                ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
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
        Calendar calendar = null;
        int i = periodRange.getStartPeriod() - 1;
        int j = periodRange.getEndPeriod() - 1;
        if (isFrom) {
            calendar = PeriodUtil.getCalendar(y1, m[i] - 1, d[i]);
            ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
            int other = (j + 1) % 4;
            int jj = j == 3 ? 1 : 0;
            calendar = PeriodUtil.getCalendar(y2 + jj, m[other] - 1, d[other]);
            calendar.add(5, -1);
            ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
        } else {
            calendar = PeriodUtil.getCalendar(y2, m[j] - 1, d[j]);
            ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
            int other = (i - 1 + 4) % 4;
            int ii = i == 0 ? 1 : 0;
            calendar = PeriodUtil.getCalendar(y1 - ii, m[other] - 1, d[other]);
            calendar.add(5, 1);
            ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
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
        Calendar calendar = null;
        if (isFrom) {
            calendar = PeriodUtil.getCalendar(y1, t1 - 1, d1);
            ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
            int balance = t2 == 12 ? 1 : 0;
            t2 = t2 == 12 ? 1 : t2 + 1;
            calendar = PeriodUtil.getCalendar(y2 + balance, t2 - 1, d1);
            calendar.add(5, -1);
            ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
        } else {
            calendar = PeriodUtil.getCalendar(y2, t2 - 1, d1);
            ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
            int balance = t1 == 1 ? 1 : 0;
            t1 = t1 == 1 ? 12 : t1 - 1;
            calendar = PeriodUtil.getCalendar(y1 - balance, t1 - 1, d1);
            calendar.add(5, 1);
            ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
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
        Calendar calendar = null;
        if (isFrom) {
            calendar = PeriodUtil.getCalendar(y1, m1 - 1, d[t1]);
            ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
            int balanceY = m2 == 12 && t2 == 3 ? 1 : 0;
            m2 = t2 == 3 ? m2 % 12 + 1 : m2;
            t2 = t2 == 3 ? 1 : t2 + 1;
            calendar = PeriodUtil.getCalendar(y2 + balanceY, m2 - 1, d[t2]);
            calendar.add(5, -1);
            ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
        } else {
            int balanceY;
            calendar = PeriodUtil.getCalendar(y2, m2 - 1, d[t2]);
            ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
            int n = balanceY = m1 == 1 && t1 == 1 ? 1 : 0;
            if (m1 == 1 && t1 == 1) {
                m1 = 12;
            } else if (t1 == 1) {
                --m1;
            }
            t1 = t1 == 1 ? 3 : t1 - 1;
            calendar = PeriodUtil.getCalendar(y1 - balanceY, m1 - 1, d[t1]);
            calendar.add(5, 1);
            ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
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
        ret[0] = PeriodUtil.getDayOfWeek(y1, t1, offset, true);
        ret[1] = PeriodUtil.getDayOfWeek(y2, t2, offset, false);
        return ret;
    }

    private static String[] weekDate(PeriodWrapper startPeriodWrapper, PeriodWrapper endPeriodWrapper) {
        String[] arr = new String[2];
        PeriodType pt = PeriodType.fromType(startPeriodWrapper.getType());
        Calendar c = pt.toCalendar(startPeriodWrapper);
        arr[0] = PeriodUtil.getTimeOfCalendar(c);
        if (startPeriodWrapper == endPeriodWrapper) {
            c.add(6, 6);
            arr[1] = PeriodUtil.getTimeOfCalendar(c);
        } else {
            PeriodWrapper endNext = new PeriodWrapper(endPeriodWrapper);
            endNext.nextPeriod();
            c = pt.toCalendar(endNext);
            c.add(6, -1);
            arr[1] = PeriodUtil.getTimeOfCalendar(c);
        }
        return arr;
    }

    private static String[] dayDate(PeriodRange periodRange) {
        String[] ret = new String[2];
        Calendar calendar = PeriodUtil.getCalendar(periodRange.getStartYear(), 0, 1);
        calendar.add(5, periodRange.getStartPeriod() - 1);
        ret[0] = PeriodUtil.getTimeOfCalendar(calendar);
        calendar = PeriodUtil.getCalendar(periodRange.getStartYear(), 0, 1);
        calendar.add(5, periodRange.getEndPeriod() - 1);
        ret[1] = PeriodUtil.getTimeOfCalendar(calendar);
        return ret;
    }

    private static String getDayOfWeek(int year, int weekIndex, int firstDay, boolean isFirst) {
        Calendar calendar = PeriodUtil.getCalendar(year, 0, 1);
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
        return PeriodUtil.getTimeOfCalendar(calendar);
    }

    private static Calendar getCalendar(int y, int m, int d) {
        GregorianCalendar calendar = new GregorianCalendar(y, m, d);
        if (m < 0 || m > 11) {
            throw new RuntimeException("\u65e0\u6548\u7684\u6708\u4efd\uff1a" + m);
        }
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

    public static PeriodWrapper getExecutePeriod(PeriodWrapper periodWrapper) {
        PeriodWrapper curPeriod = PeriodUtil.currentPeriod(periodWrapper.getType());
        int year = periodWrapper.getYear() == 0 ? curPeriod.getYear() : periodWrapper.getYear();
        int period = periodWrapper.getPeriod() == 0 ? curPeriod.getPeriod() : periodWrapper.getPeriod();
        return new PeriodWrapper(year, periodWrapper.getType(), period);
    }

    public static int getDayOfWeek(PeriodWrapper period) {
        GregorianCalendar calendar = PeriodUtil.period2ConcreteCalendar(period);
        return calendar.get(7);
    }

    public static String getDayOfWeekStr(PeriodWrapper period) {
        GregorianCalendar calendar = PeriodUtil.period2ConcreteCalendar(period);
        int day = calendar.get(7);
        switch (day) {
            case 1: {
                return "\u661f\u671f\u65e5";
            }
            case 7: {
                return "\u661f\u671f\u516d";
            }
            case 2: {
                return "\u661f\u671f\u4e00";
            }
            case 3: {
                return "\u661f\u671f\u4e8c";
            }
            case 4: {
                return "\u661f\u671f\u4e09";
            }
            case 5: {
                return "\u661f\u671f\u56db";
            }
            case 6: {
                return "\u661f\u671f\u4e94";
            }
        }
        return "";
    }

    public static ArrayList<String> getPeiodStrList(PeriodWrapper startPeriod, PeriodWrapper stopPeriod) {
        ArrayList<String> periodList = new ArrayList<String>();
        ArrayList<PeriodWrapper> periodWrapperList = PeriodUtil.getPeiodWrapperList(startPeriod, stopPeriod);
        if (null != periodWrapperList) {
            for (PeriodWrapper item : periodWrapperList) {
                periodList.add(item.toString());
            }
        }
        return periodList;
    }

    public static ArrayList<PeriodWrapper> getPeiodWrapperList(PeriodWrapper startPeriod, PeriodWrapper stopPeriod) {
        if (startPeriod != null && stopPeriod != null && startPeriod.getType() == stopPeriod.getType()) {
            int stop;
            ArrayList<PeriodWrapper> periods = new ArrayList<PeriodWrapper>();
            int type = startPeriod.getType();
            PeriodWrapper curPeriod = PeriodUtil.currentPeriod(type);
            int startYear = startPeriod.getYear() == 0 ? curPeriod.getYear() : startPeriod.getYear();
            int stopYear = stopPeriod.getYear() == 0 ? curPeriod.getYear() : stopPeriod.getYear();
            int start = startPeriod.getPeriod() == 0 ? curPeriod.getPeriod() : startPeriod.getPeriod();
            int n = stop = stopPeriod.getPeriod() == 0 ? curPeriod.getPeriod() : stopPeriod.getPeriod();
            if (startYear < stopYear) {
                int period;
                for (period = start; period <= PeriodUtil.getPeriodSize(startYear, type); ++period) {
                    periods.add(new PeriodWrapper(startYear, type, period));
                }
                for (int year = startYear + 1; year < stopYear; ++year) {
                    for (int period2 = 1; period2 <= PeriodUtil.getPeriodSize(year, type); ++period2) {
                        periods.add(new PeriodWrapper(year, type, period2));
                    }
                }
                for (period = 1; period <= stop; ++period) {
                    periods.add(new PeriodWrapper(stopYear, type, period));
                }
            } else if (startYear == stopYear) {
                if (start <= stop) {
                    for (int period = start; period <= stop; ++period) {
                        periods.add(new PeriodWrapper(startYear, type, period));
                    }
                } else {
                    periods.add(new PeriodWrapper(startYear, startPeriod.getType(), start));
                }
            } else {
                periods.add(new PeriodWrapper(startYear, startPeriod.getType(), start));
            }
            return periods;
        }
        return null;
    }

    public static int getPeriodSize(int year, int periodType) {
        switch (periodType) {
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 4;
            }
            case 4: {
                return 12;
            }
            case 5: {
                return 36;
            }
            case 7: {
                return 52;
            }
            case 6: {
                if (DateAider.isLeapYear(year)) {
                    return 366;
                }
                return 365;
            }
        }
        return 0;
    }

    public static String convertType2Str(int periodType) {
        String strType = null;
        switch (periodType) {
            case 1: {
                strType = "N";
                break;
            }
            case 2: {
                strType = "H";
                break;
            }
            case 3: {
                strType = "J";
                break;
            }
            case 4: {
                strType = "Y";
                break;
            }
            case 5: {
                strType = "X";
                break;
            }
            case 6: {
                strType = "R";
                break;
            }
            case 7: {
                strType = "Z";
                break;
            }
            case 8: {
                strType = "B";
            }
        }
        return strType;
    }

    public static ArrayList<String> getPeriodCodes(String startTime, String endTime, PeriodType periodType) {
        Date endDate;
        Date startDate;
        if (periodType == PeriodType.CUSTOM) {
            return new ArrayList<String>();
        }
        String[] startPeriodList = PeriodUtil.getTimesArr(startTime);
        String[] endPeriodList = PeriodUtil.getTimesArr(endTime);
        if (startPeriodList.length != 2 || endPeriodList.length != 2) {
            return new ArrayList<String>();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = simpleDateFormat.parse(startPeriodList[0]);
            endDate = simpleDateFormat.parse(endPeriodList[1]);
        }
        catch (Exception e) {
            return new ArrayList<String>();
        }
        GregorianCalendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(startDate);
        PeriodWrapper startWrapper = PeriodUtil.currentPeriod(startCalendar, periodType.ordinal(), 0);
        GregorianCalendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        PeriodWrapper endWrapper = PeriodUtil.currentPeriod(endCalendar, periodType.ordinal(), 0);
        ArrayList<String> periodList = PeriodUtil.getPeiodStrList(startWrapper, endWrapper);
        return periodList;
    }

    class PeriodRange {
        private int type;
        private int startYear;
        private int startPeriod;
        private int endYear;
        private int endPeriod;
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

