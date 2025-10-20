/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component(value="budNoneFormat")
public class NoneFormat
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "\u65e0\u65f6\u671f";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        if (Locale.CHINA.equals(locale)) {
            return DataPeriodType.PERIOD_TYPE_NONE.getTitle();
        }
        return "NONE";
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_NONE;
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}

