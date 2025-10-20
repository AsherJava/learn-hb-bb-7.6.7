/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.SimpleComposeDateDTO
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 */
package com.jiuqi.bde.bizmodel.execute.datamodel.invest;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.SimpleComposeDateDTO;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import java.util.Map;

public abstract class AbstractInvestBillDataLoader
extends AbstractFinBizDataModelLoader<BalanceCondition, FetchData> {
    public String getBizDataModelCode() {
        return ComputationModelEnum.INVEST_BILL.getCode();
    }

    public abstract Map<String, Integer> loadSubject(BalanceCondition var1);

    @Override
    public abstract FetchFloatRowResult simpleFloatQuery(BalanceCondition var1, SimpleComposeDateDTO var2);

    @Override
    public abstract FetchFloatRowResult seniorFloatQuery(BalanceCondition var1, String var2);
}

