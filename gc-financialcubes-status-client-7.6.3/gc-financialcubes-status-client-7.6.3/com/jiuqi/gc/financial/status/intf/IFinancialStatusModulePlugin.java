/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.status.intf;

import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusConfigVo;
import com.jiuqi.gc.financial.status.event.FinancialStatusChangeEventData;

public interface IFinancialStatusModulePlugin {
    public String getModuleCode();

    public String getModuleName();

    public String getAppName();

    public String getProdLine();

    public FinancialStatusConfigVo getPluginDefaultConfig();

    default public String getFinancialStatusModuleQueryExecute() {
        return "DefaultFinancialStatusModuleQueryExecute";
    }

    default public void afterGroupOpen(FinancialStatusChangeEventData changeEventData) {
    }

    default public void afterUnitOpen(FinancialStatusChangeEventData changeEventData) {
    }

    default public boolean isDefaultOpen() {
        return true;
    }

    public int order();
}

