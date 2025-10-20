/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component(value="budHalfYearFormat1")
public class HalfYearFormat1
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "\u4e0a\u534a\u5e74";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        if (Locale.US.equals(locale)) {
            int year = dataPeriod.getYear();
            return dataPeriod.getPeriod() == 1 ? "First half of " + year : "Second half of " + year;
        }
        return dataPeriod.getPeriod() == 1 ? "\u4e0a\u534a\u5e74" : "\u4e0b\u534a\u5e74";
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_HALFYEAR;
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}

