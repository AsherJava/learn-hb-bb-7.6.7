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

@Component(value="budTendayFormat3")
public class TendayFormat3
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "2020\u5e741\u6708\u4e0a\u65ec";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        LocalDateTime startDay = dataPeriod.getStartDay();
        int dayOfMonth = startDay.getDayOfMonth();
        int monthValue = startDay.getMonthValue();
        int year = startDay.getYear();
        if (dayOfMonth < 11) {
            return year + "\u5e74" + monthValue + "\u6708\u4e0a\u65ec";
        }
        if (dayOfMonth < 21) {
            return year + "\u5e74" + monthValue + "\u6708\u4e2d\u65ec";
        }
        return year + "\u5e74" + monthValue + "\u6708\u4e0b\u65ec";
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_TENDAY;
    }
}

