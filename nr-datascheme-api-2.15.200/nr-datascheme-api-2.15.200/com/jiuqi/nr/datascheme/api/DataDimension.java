/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.api.type.PeriodPattern;
import java.io.Serializable;
import java.time.Instant;

public interface DataDimension
extends Ordered,
Cloneable,
Serializable {
    public String getDataSchemeKey();

    public DimensionType getDimensionType();

    public String getDimKey();

    public PeriodType getPeriodType();

    public PeriodPattern getPeriodPattern();

    public String getVersion();

    public String getLevel();

    public Instant getUpdateTime();

    public Boolean getReportDim();

    public String getDimAttribute();

    default public String getDefaultValue() {
        if ("ADJUST".equals(this.getDimKey())) {
            return "0";
        }
        return null;
    }
}

