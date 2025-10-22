/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.PeriodRegion
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.func.para;

import com.jiuqi.nr.dataentry.bean.PeriodRegion;

public class GatherParam {
    private String dataSchemeKey;
    private String currentPeriodInfo;
    private PeriodRegion periodRange;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getCurrentPeriodInfo() {
        return this.currentPeriodInfo;
    }

    public void setCurrentPeriodInfo(String currentPeriodInfo) {
        this.currentPeriodInfo = currentPeriodInfo;
    }

    public PeriodRegion getPeriodRange() {
        return this.periodRange;
    }

    public void setPeriodRange(PeriodRegion periodRange) {
        this.periodRange = periodRange;
    }
}

