/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge;

import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import java.util.List;

public interface IAgingExecutor {
    public ReconciliationModeEnum getExecutorName();

    public void calcAging(List<GcOffsetRelatedItemEO> var1, String var2);
}

