/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;

public interface BillCoreWorkFlowService {
    public void clearWorkFowParamsFormulaCache();

    public Map<String, Object> getWorkFlowParamsValueMap(BillModel var1, Map<String, Object> var2);
}

