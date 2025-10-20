/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.acctperiod.service;

import com.jiuqi.dc.base.impl.onlinePeriod.data.OnlinePeriod;
import java.util.List;

public interface AcctPeriodService {
    public List<Integer> listYear();

    public OnlinePeriod getOnlinePeriod(String var1, String var2);
}

