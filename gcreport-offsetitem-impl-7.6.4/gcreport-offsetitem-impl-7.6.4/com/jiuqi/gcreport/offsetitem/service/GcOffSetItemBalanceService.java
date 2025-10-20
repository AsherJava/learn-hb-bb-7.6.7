/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import java.util.List;
import java.util.concurrent.Future;

public interface GcOffSetItemBalanceService {
    public void syncUpdateBalance(boolean var1, List<GcOffSetVchrItemAdjustEO> var2);

    public Future<Boolean> asyncUpdateBalance(boolean var1, List<GcOffSetVchrItemAdjustEO> var2);
}

