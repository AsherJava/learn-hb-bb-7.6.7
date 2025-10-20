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

@Component(value="budDayFormat2")
public class DayFormat2
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "1\u67081\u65e5";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        LocalDateTime startDay = dataPeriod.getStartDay();
        if (Locale.CHINA.equals(locale)) {
            return startDay.getMonthValue() + "\u6708" + startDay.getDayOfMonth() + "\u65e5";
        }
        return DateTimeCenter.formatTime((LocalDateTime)startDay, (String)"MMM d", (Locale)locale);
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_DAY;
    }
}

