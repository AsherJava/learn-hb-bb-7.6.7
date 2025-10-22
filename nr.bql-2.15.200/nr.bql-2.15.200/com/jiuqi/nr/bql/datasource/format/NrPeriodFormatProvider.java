/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.time.IFormatProvider
 *  com.jiuqi.bi.util.time.IFormatProvider$Formatter
 *  com.jiuqi.bi.util.time.IFormatProvider$Parser
 *  com.jiuqi.bi.util.time.TimeFieldInfo
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.bql.datasource.format;

import com.jiuqi.bi.util.time.IFormatProvider;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.nr.bql.datasource.format.NrPeriodFormater;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrPeriodFormatProvider
implements IFormatProvider {
    public static final String NAME = "nr.period.format";
    @Autowired
    private PeriodEngineService periodEngineService;

    public String name() {
        return NAME;
    }

    public IFormatProvider.Parser createParser(TimeFieldInfo field, String param, Locale locale) {
        return null;
    }

    public IFormatProvider.Formatter createFormatter(TimeFieldInfo field, String param, Locale locale) {
        if (field.isTimeKey()) {
            IPeriodProvider provider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(param);
            return new NrPeriodFormater(provider, locale);
        }
        return null;
    }
}

