/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.financialcheckcore.utils;

import com.jiuqi.np.period.PeriodWrapper;
import java.util.Calendar;
import java.util.Date;

public class PeriodUtils {
    public static int getMonth(int year, int periodType, int period) {
        new PeriodWrapper(year, periodType, period);
        switch (periodType) {
            case 1: {
                return 12;
            }
            case 2: {
                return 6 * period;
            }
            case 3: {
                return 3 * period;
            }
            case 4: {
                return period;
            }
            case 5: {
                return period / 3 + (period % 3 != 0 ? 1 : 0);
            }
        }
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\u6708\u4efd\u83b7\u53d6\u3002");
    }

    public static Date getDate(int year, int periodType, int period) {
        int month = PeriodUtils.getMonth(year, periodType, period);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 15);
        return calendar.getTime();
    }

    public static int standardPeriod(int period) {
        if (period == 0) {
            return 1;
        }
        if (period == 13) {
            return 12;
        }
        return period;
    }
}

