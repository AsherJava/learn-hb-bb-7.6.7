/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 */
package com.jiuqi.bde.bizmodel.execute.datamodel.balance;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import java.util.Map;

public abstract class AbstractBalanceDataLoader
extends AbstractFinBizDataModelLoader<BalanceCondition, FetchData> {
    public abstract Map<String, Integer> loadSubject(BalanceCondition var1);

    public String getBizDataModelCode() {
        return BizDataModelEnum.BALANCEMODEL.getCode();
    }
}

