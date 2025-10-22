/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.internal.service.impl;

import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.service.PeriodIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodEngineServiceImpl
implements PeriodEngineService {
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private PeriodIOService periodIOService;

    @Override
    public IPeriodEntityAdapter getPeriodAdapter() {
        return this.periodAdapter;
    }

    @Override
    public PeriodIOService getPeriodIOService() {
        return this.periodIOService;
    }
}

