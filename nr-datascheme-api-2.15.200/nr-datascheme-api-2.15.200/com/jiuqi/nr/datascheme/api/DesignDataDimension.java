/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.api.type.PeriodPattern;
import java.time.Instant;

public interface DesignDataDimension
extends DataDimension,
OrderSetter {
    public void setDataSchemeKey(String var1);

    public void setDimensionType(DimensionType var1);

    public void setDimKey(String var1);

    public void setPeriodType(PeriodType var1);

    public void setPeriodPattern(PeriodPattern var1);

    public void setVersion(String var1);

    public void setLevel(String var1);

    public void setUpdateTime(Instant var1);

    public void setReportDim(Boolean var1);

    public void setDimAttribute(String var1);
}

