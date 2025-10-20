/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component(value="budWeekFormat2")
public class WeekFormat2
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "2020\u5e74\u7b2c1\u5468";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        return dataPeriod.getYear() + "\u5e74\u7b2c" + dataPeriod.getPeriod() + "\u5468";
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_WEEK;
    }
}

