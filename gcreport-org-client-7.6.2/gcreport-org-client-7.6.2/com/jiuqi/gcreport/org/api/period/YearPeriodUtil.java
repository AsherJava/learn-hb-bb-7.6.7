/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.org.api.period;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.api.period.IYearPeriodProvider;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import java.util.Calendar;

public class YearPeriodUtil {
    public static YearPeriodDO transform() {
        return YearPeriodUtil.getProvider().transform();
    }

    public static YearPeriodDO transform(String formSchemeKey, String periodString) {
        return YearPeriodUtil.getProvider().transform(formSchemeKey, periodString);
    }

    public static YearPeriodDO transform(String formSchemeKey, int year, int type, int period) {
        return YearPeriodUtil.getProvider().transform(formSchemeKey, year, type, period);
    }

    public static YearPeriodDO transform(String formSchemeKey, int type, Calendar calendar) {
        return YearPeriodUtil.getProvider().transform(formSchemeKey, type, calendar);
    }

    public static YearPeriodDO transformByDataSchemeKey(String dataSchemeKey, String periodString) {
        return YearPeriodUtil.getProvider().transformByDataSchemeKey(dataSchemeKey, periodString);
    }

    private static IYearPeriodProvider getProvider() {
        IYearPeriodProvider provider = (IYearPeriodProvider)SpringContextUtils.getBean(IYearPeriodProvider.class);
        if (provider == null) {
            throw new RuntimeException("\u5f53\u524d\u670d\u52a1\u6ca1\u6709\u6ce8\u518c\u671f\u95f4\u7248\u672c\u89e3\u6790\u5668.");
        }
        return provider;
    }
}

