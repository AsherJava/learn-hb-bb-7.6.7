/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod.hist;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dataperiod.hist.HistPeriodCompareDTO;
import com.jiuqi.budget.dataperiod.hist.IHistPeriodComparator;

public class HistPeriodComparator
implements IHistPeriodComparator {
    @Override
    public boolean checkIsHistPeriod(DataPeriod periodA, HistPeriodCompareDTO dtoB) {
        IDataPeriodType type = periodA.getType();
        if (type == DataPeriodType.PERIOD_TYPE_NONE) {
            return false;
        }
        DataPeriod periodB = dtoB.getDataPeriod();
        return periodB.getYear() < periodA.getYear();
    }
}

