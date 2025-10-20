/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect;

import com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.FcIncrementCollectParam;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;

public interface FcIncrementCollectService {
    public void collect(FcIncrementCollectParam var1);

    public void collect(List<GcRelatedItemEO> var1);
}

