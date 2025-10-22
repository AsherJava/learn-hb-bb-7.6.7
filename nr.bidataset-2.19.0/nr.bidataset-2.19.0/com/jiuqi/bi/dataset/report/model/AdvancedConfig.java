/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.model;

import com.jiuqi.bi.dataset.report.model.PeriodChangeMode;
import com.jiuqi.bi.dataset.report.model.UnitChangeMode;

public class AdvancedConfig {
    private UnitChangeMode unitChangeMode;
    private PeriodChangeMode periodChangeMode;
    private String periodOffset;

    public UnitChangeMode getUnitChangeMode() {
        return this.unitChangeMode;
    }

    public void setUnitChangeMode(UnitChangeMode unitChangeMode) {
        this.unitChangeMode = unitChangeMode;
    }

    public PeriodChangeMode getPeriodChangeMode() {
        return this.periodChangeMode;
    }

    public void setPeriodChangeMode(PeriodChangeMode periodChangeMode) {
        this.periodChangeMode = periodChangeMode;
    }

    public String getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(String periodOffset) {
        this.periodOffset = periodOffset;
    }
}

