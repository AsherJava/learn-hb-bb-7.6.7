/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.financialstatus.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FinancialStatusDTO
implements Serializable {
    private List<String> openPeriodList;
    private Map<String, Set<String>> periodToUnitSetMap;

    public List<String> getOpenPeriodList() {
        return this.openPeriodList;
    }

    public void setOpenPeriodList(List<String> openPeriodList) {
        this.openPeriodList = openPeriodList;
    }

    public Map<String, Set<String>> getPeriodToUnitSetMap() {
        return this.periodToUnitSetMap;
    }

    public void setPeriodToUnitSetMap(Map<String, Set<String>> periodToUnitSetMap) {
        this.periodToUnitSetMap = periodToUnitSetMap;
    }
}

