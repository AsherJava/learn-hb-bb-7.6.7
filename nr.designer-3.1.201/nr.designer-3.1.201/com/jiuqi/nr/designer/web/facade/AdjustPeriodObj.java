/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import com.jiuqi.nr.designer.web.facade.AdjustPeriodRow;
import java.util.List;

public class AdjustPeriodObj {
    private boolean adjustPeriod = true;
    private List<AdjustPeriodRow> adjustPeriods;
    private EFDCPeriodSettingDefineImpl data;

    public boolean isAdjustPeriod() {
        return this.adjustPeriod;
    }

    public void setAdjustPeriod(boolean adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }

    public List<AdjustPeriodRow> getAdjustPeriods() {
        return this.adjustPeriods;
    }

    public void setAdjustPeriods(List<AdjustPeriodRow> adjustPeriods) {
        this.adjustPeriods = adjustPeriods;
    }

    public EFDCPeriodSettingDefineImpl getData() {
        return this.data;
    }

    public void setData(EFDCPeriodSettingDefineImpl data) {
        this.data = data;
    }
}

