/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.intf.ConditionMatchRule
 */
package com.jiuqi.bde.plugin.common.service;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.intf.ConditionMatchRule;

public interface FetchResultConvert {
    public void covertFetchData(FetchData var1, BalanceCondition var2);

    public Boolean subjectIsItemMapping(String var1);

    public ConditionMatchRule coverSubjectCode(String var1, ConditionMatchRule var2);
}

