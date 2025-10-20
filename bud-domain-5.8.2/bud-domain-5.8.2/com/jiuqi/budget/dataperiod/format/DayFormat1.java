/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component(value="budDayFormat1")
public class DayFormat1
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "1\u65e5";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        int year = dataPeriod.getYear();
        LocalDateTime startDay = dataPeriod.getStartDay();
        if (Locale.CHINA.equals(locale)) {
            return dataPeriod.getStartDay().getDayOfMonth() + "\u65e5";
        }
        return Integer.toString(dataPeriod.getStartDay().getDayOfMonth());
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_DAY;
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}

