/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PeriodUtilS {
    public static final Date getDate(PeriodType periodType, int periodOffset) {
        Date currDate = new Date();
        GregorianCalendar currentCalendar = PeriodUtil.getCurrentCalendar((Date)currDate);
        PeriodWrapper currentPeriod = PeriodUtilS.currentPeriod(currentCalendar, periodType.code());
        return null;
    }

    public static final PeriodWrapper currentPeriod(GregorianCalendar calendar, int periodType) {
        PeriodWrapper currPeriod = new PeriodWrapper();
        int periodOffset = 1;
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
}

