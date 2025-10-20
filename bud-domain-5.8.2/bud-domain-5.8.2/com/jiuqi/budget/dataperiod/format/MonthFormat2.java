/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component(value="budMonthFormat2")
public class MonthFormat2
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "2020\u5e741\u6708";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        if (Locale.CHINA.equals(locale)) {
            return dataPeriod.getYear() + "\u5e74" + dataPeriod.getPeriod() + "\u6708";
        }
        LocalDateTime startDay = dataPeriod.getStartDay();
        return DateTimeCenter.formatTime((LocalDateTime)startDay, (String)"MMM yyyy", (Locale)locale);
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_MONTH;
    }
}

