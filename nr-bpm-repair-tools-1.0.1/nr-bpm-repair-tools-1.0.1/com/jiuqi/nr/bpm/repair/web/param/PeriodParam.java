/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.repair.web.param;

import com.jiuqi.nr.bpm.repair.web.param.PeriodRange;
import java.util.List;
import java.util.Map;

public class PeriodParam {
    private String dataSchemeKey;
    private String currentPeriod;
    private String currentPeriodTitle;
    private List<PeriodRange> rangePeriods;
    private Map<String, List<String>> adjustPeriods;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getCurrentPeriod() {
        return this.currentPeriod;
    }

    public void setCurrentPeriod(String currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public Map<String, List<String>> getAdjustPeriods() {
        return this.adjustPeriods;
    }

    public void setAdjustPeriods(Map<String, List<String>> adjustPeriods) {
        this.adjustPeriods = adjustPeriods;
    }

    public List<PeriodRange> getRangePeriods() {
        return this.rangePeriods;
    }

    public void setRangePeriods(List<PeriodRange> rangePeriods) {
        this.rangePeriods = rangePeriods;
    }

    public String getCurrentPeriodTitle() {
        return this.currentPeriodTitle;
    }

    public void setCurrentPeriodTitle(String currentPeriodTitle) {
        this.currentPeriodTitle = currentPeriodTitle;
    }
}

