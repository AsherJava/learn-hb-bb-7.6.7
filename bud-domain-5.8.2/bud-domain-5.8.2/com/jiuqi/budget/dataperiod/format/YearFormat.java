/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component(value="budYearDataPeriodFormat")
public class YearFormat
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "2020\u5e74";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        if (Locale.CHINA.equals(locale)) {
            return dataPeriod.getYear() + "\u5e74";
        }
        return Integer.toString(dataPeriod.getYear());
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_YEAR;
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}

