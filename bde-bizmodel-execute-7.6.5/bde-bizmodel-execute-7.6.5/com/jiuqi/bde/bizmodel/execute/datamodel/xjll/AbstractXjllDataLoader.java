/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.dto.SimpleComposeDateDTO
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 */
package com.jiuqi.bde.bizmodel.execute.datamodel.xjll;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.dto.SimpleComposeDateDTO;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;

public abstract class AbstractXjllDataLoader
extends AbstractFinBizDataModelLoader<BalanceCondition, FetchData> {
    public String getBizDataModelCode() {
        return BizDataModelEnum.XJLLMODEL.getCode();
    }

    @Override
    public FetchFloatRowResult simpleFloatQuery(BalanceCondition balanceCondition, SimpleComposeDateDTO simpleComposeDateDTO) {
        return new FetchFloatRowResult();
    }

    @Override
    public abstract FetchFloatRowResult seniorFloatQuery(BalanceCondition var1, String var2);
}

