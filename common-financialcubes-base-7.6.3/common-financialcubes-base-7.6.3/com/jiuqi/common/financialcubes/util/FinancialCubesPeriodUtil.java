/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 */
package com.jiuqi.common.financialcubes.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import java.text.ParseException;
import java.util.Date;

public class FinancialCubesPeriodUtil {
    public static int[] getPeriodMonthScope(int period, String periodType) {
        return FinancialCubesPeriodUtil.getPeriodMonthScope(period, FinancialCubesPeriodTypeEnum.getByCode(periodType));
    }

    public static int[] getPeriodMonthScope(int period, FinancialCubesPeriodTypeEnum periodTypeEnum) {
        switch (periodTypeEnum) {
            case J: {
                return new int[]{(period - 1) * 3 + 1, period * 3};
            }
            case H: {
                return new int[]{(period - 1) * 6 + 1, period * 6};
            }
            case N: {
                return new int[]{1, 12};
            }
        }
        return new int[]{period, period};
    }

    public static String getDataTime(Integer acctYear, Integer acctPeriod, FinancialCubesPeriodTypeEnum periodTypeEnum) {
        return acctYear + periodTypeEnum.getCode() + String.format("%04d", acctPeriod);
    }

    public static Date getEndDate(String periodStr) {
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        try {
            Date[] periodDateRegion = defaultPeriodAdapter.getPeriodDateRegion(periodStr);
            return periodDateRegion[1];
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException("\u65f6\u671f\u5b57\u7b26\u4e32\u3010" + periodStr + "\u3011\u89e3\u6790\u65e5\u671f\u5931\u8d25");
        }
    }
}

