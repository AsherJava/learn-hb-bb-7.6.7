/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.internal.service;

import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.service.PeriodIOService;

public interface PeriodEngineService {
    public IPeriodEntityAdapter getPeriodAdapter();

    public PeriodIOService getPeriodIOService();
}

