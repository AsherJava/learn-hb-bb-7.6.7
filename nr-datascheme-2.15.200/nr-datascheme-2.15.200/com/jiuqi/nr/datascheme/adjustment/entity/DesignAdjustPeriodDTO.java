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

public class DesignAdjustPeriodDTO
extends AdjustPeriodDO
implements DesignAdjustPeriod {
    private static final long serialVersionUID = 5566702653361267581L;

    public static DesignAdjustPeriodDTO convert(AdjustPeriod adjustPeriod) {
        DesignAdjustPeriodDTO period = new DesignAdjustPeriodDTO();
        period.setDataSchemeKey(adjustPeriod.getDataSchemeKey());
        period.setPeriod(adjustPeriod.getPeriod());
        period.setCode(adjustPeriod.getCode());
        period.setTitle(adjustPeriod.getTitle());
        period.setOrder(adjustPeriod.getOrder());
        return period;
    }
}

