/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import java.util.List;

public interface IAdjustPeriodService {
    public List<AdjustPeriod> queryAdjustPeriods(String var1);

    public List<AdjustPeriod> queryAdjustPeriods(String var1, String var2);

    public AdjustPeriod queryAdjustPeriods(String var1, String var2, String var3);
}

