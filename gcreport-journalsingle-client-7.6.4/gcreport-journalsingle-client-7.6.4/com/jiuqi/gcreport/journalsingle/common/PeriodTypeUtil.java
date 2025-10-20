/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.journalsingle.common;

import com.jiuqi.common.base.BusinessRuntimeException;
import java.util.ArrayList;
import java.util.List;

public class PeriodTypeUtil {
    public static Integer[] getLaterPeriodByType(int periodType, int currPeriod, boolean containCurrPeriod) {
        List<Integer> currYearAllPeriod = PeriodTypeUtil.getCurrYearAllPeriod(periodType);
        Integer[] periods = currYearAllPeriod.toArray(new Integer[currYearAllPeriod.size()]);
        ArrayList<Integer> laterPeriods = new ArrayList<Integer>();
        if (containCurrPeriod) {
            laterPeriods.add(currPeriod);
        }
        for (Integer period : periods) {
            if (period <= currPeriod) continue;
            laterPeriods.add(period);
        }
        return laterPeriods.toArray(new Integer[laterPeriods.size()]);
    }

    private static List<Integer> getCurrYearAllPeriod(int periodType) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (periodType == 1) {
            result.add(1);
        } else if (periodType == 2) {
            result.add(1);
            result.add(2);
        } else if (periodType == 3) {
            result.add(1);
            result.add(2);
            result.add(3);
            result.add(4);
        } else if (periodType == 4) {
            for (int i = 1; i <= 12; ++i) {
                result.add(i);
            }
        } else {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + periodType);
        }
        return result;
    }
}

