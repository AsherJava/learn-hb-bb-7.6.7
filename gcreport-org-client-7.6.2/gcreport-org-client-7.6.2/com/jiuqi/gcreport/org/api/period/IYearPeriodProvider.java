/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.period;

import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import java.util.Calendar;

public interface IYearPeriodProvider {
    public YearPeriodDO transform();

    public YearPeriodDO transform(int var1, int var2);

    public YearPeriodDO transform(String var1);

    public YearPeriodDO transform(String var1, String var2);

    public YearPeriodDO transform(String var1, int var2, int var3, int var4);

    public YearPeriodDO transform(String var1, int var2, Calendar var3);

    public YearPeriodDO transformByDataSchemeKey(String var1, String var2);
}

