/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DesignAdjustPeriod
 */
package com.jiuqi.nr.datascheme.adjustment.entity;

import com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DesignAdjustPeriod;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;

@DBAnno.DBTable(dbTable="NR_ADJUST_PERIOD_DES")
public class DesignAdjustPeriodDO
extends AdjustPeriodDO
implements DesignAdjustPeriod {
    private static final long serialVersionUID = -8104138281346305834L;

    public static DesignAdjustPeriodDO convert(AdjustPeriod adjustPeriod) {
        DesignAdjustPeriodDO period = new DesignAdjustPeriodDO();
        period.setDataSchemeKey(adjustPeriod.getDataSchemeKey());
        period.setPeriod(adjustPeriod.getPeriod());
        period.setCode(adjustPeriod.getCode());
        period.setTitle(adjustPeriod.getTitle());
        period.setOrder(adjustPeriod.getOrder());
        return period;
    }
}

