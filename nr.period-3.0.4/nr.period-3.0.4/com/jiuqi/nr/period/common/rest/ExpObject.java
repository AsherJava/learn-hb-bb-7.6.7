/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.rest;

import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;

public class ExpObject {
    private PeriodDefineImpl period;

    public ExpObject() {
    }

    public ExpObject(PeriodDefineImpl period) {
        this.period = period;
    }

    public PeriodDefineImpl getPeriod() {
        return this.period;
    }

    public void setPeriod(PeriodDefineImpl period) {
        this.period = period;
    }
}

