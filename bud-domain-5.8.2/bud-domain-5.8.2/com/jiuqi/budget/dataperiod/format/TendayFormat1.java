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

@Component(value="budTendayFormat1")
public class TendayFormat1
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "\u4e0a\u65ec";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        LocalDateTime startDay = dataPeriod.getStartDay();
        int dayOfMonth = startDay.getDayOfMonth();
        int year = dataPeriod.getYear();
        boolean isCN = Locale.CHINA.equals(locale);
        if (dayOfMonth < 11) {
            return isCN ? "\u4e0a\u65ec" : "First " + DateTimeCenter.formatTime((LocalDateTime)startDay, (String)"MMM", (Locale)locale) + " " + year;
        }
        if (dayOfMonth < 21) {
            return isCN ? "\u4e2d\u65ec" : "Secend " + DateTimeCenter.formatTime((LocalDateTime)startDay, (String)"MMM", (Locale)locale) + " " + year;
        }
        return isCN ? "\u4e0b\u65ec" : "Third " + DateTimeCenter.formatTime((LocalDateTime)startDay, (String)"MMM", (Locale)locale) + " " + year;
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_TENDAY;
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}

