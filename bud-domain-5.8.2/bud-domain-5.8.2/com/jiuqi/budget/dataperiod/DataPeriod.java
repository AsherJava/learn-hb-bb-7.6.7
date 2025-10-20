/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.CommonUtil
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.common.utils.CommonUtil;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.dataperiod.DataPeriodTypeRegistrar;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface DataPeriod
extends Comparable<DataPeriod> {
    public static final String NONE_TYPE_PERIOD_STR = "0000W0000";

    public static String getPeriodTypeStr(String periodStr) {
        if (periodStr.length() == 9) {
            return periodStr.substring(4, 5);
        }
        return periodStr.substring(periodStr.length() - 1);
    }

    public static char getPeriodTypeChar(String periodStr) {
        if (periodStr.length() == 9) {
            return periodStr.charAt(4);
        }
        return periodStr.charAt(periodStr.length() - 1);
    }

    @Override
    default public int compareTo(DataPeriod dataPeriod) {
        LocalDateTime periodStartDay;
        LocalDateTime startDay = this.getStartDay();
        if (startDay.equals(periodStartDay = dataPeriod.getStartDay())) {
            return this.getType().compareTo(dataPeriod.getType());
        }
        return startDay.compareTo(periodStartDay);
    }

    public static String getTitle(String name) {
        return DataPeriodFactory.valueOf(name).getTitle();
    }

    public static DataPeriod noneTypePeriod() {
        return NoneTypePeriodHolder.PERIOD;
    }

    public long getTime();

    public LocalDateTime getDate();

    public LocalDateTime toLocalDateTime();

    public IDataPeriodType getType();

    public int getPeriod();

    public int getYear();

    public LocalDateTime getStartDay();

    public LocalDateTime getEndDay();

    public String getTitle();

    public String getName();

    default public DataPeriod offSet(String offSet) {
        if (offSet.length() == 9 && offSet.indexOf(44) == -1) {
            return DataPeriodFactory.valueOf(offSet);
        }
        ArrayList strings = CommonUtil.splitStr((String)offSet, (char)',');
        LocalDateTime startDay = this.getStartDay();
        for (String offSetStr : strings) {
            char firstChar = offSetStr.charAt(0);
            boolean fixed = '0' < firstChar && '9' >= firstChar;
            int offSetVal = Integer.parseInt(offSetStr.substring(0, offSetStr.length() - 1));
            IDataPeriodType iDataPeriodType = DataPeriodTypeRegistrar.typeOf(String.valueOf(offSetStr.charAt(offSetStr.length() - 1)));
            startDay = iDataPeriodType.calcOffSetResult(startDay, fixed, offSetVal);
        }
        return DataPeriodFactory.valueOf(startDay, DataPeriodTypeRegistrar.typeOf(String.valueOf(offSet.charAt(offSet.length() - 1))));
    }

    public DataPeriod getLogicParentPeriod();

    public String getRelativePeriod(String var1);

    public String getRelativePeriod(DataPeriod var1);

    public static class NoneTypePeriodHolder {
        private static final DataPeriod PERIOD = DataPeriodFactory.valueOf("0000W0000");
    }
}

