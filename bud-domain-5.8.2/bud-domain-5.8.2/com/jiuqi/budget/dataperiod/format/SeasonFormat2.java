/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 *  com.jiuqi.budget.common.utils.Num2Ch
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.common.utils.Num2Ch;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component(value="budSeasonFormat2")
public class SeasonFormat2
extends BaseDataPeriodFormat {
    @Override
    public String getTitle() {
        return "\u4e00\u5b63\u5ea6";
    }

    @Override
    public String format(Locale locale, DataPeriod dataPeriod) {
        if (Locale.CHINA.equals(locale)) {
            return Num2Ch.cvt((long)dataPeriod.getPeriod()) + "\u5b63\u5ea6";
        }
        return DateTimeCenter.formatTime((LocalDateTime)dataPeriod.getStartDay(), (String)"QQQQ", (Locale)locale);
    }

    @Override
    public DataPeriodType adaptType() {
        return DataPeriodType.PERIOD_TYPE_SEASON;
    }
}

