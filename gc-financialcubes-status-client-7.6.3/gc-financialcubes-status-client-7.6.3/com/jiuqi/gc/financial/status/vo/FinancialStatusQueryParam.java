/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.status.vo;

import java.util.Set;

public class FinancialStatusQueryParam {
    private String moduleCode;
    private Set<String> unitCodeSet;
    private String dataTime;
    private String periodType;

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Set<String> getUnitCodeSet() {
        return this.unitCodeSet;
    }

    public void setUnitCodeSet(Set<String> unitCodeSet) {
        this.unitCodeSet = unitCodeSet;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}

