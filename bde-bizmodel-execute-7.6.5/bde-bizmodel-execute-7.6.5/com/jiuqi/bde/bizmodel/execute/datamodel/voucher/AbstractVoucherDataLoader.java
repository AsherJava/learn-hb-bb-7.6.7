/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.dto.SimpleComposeDateDTO
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 */
package com.jiuqi.bde.bizmodel.execute.datamodel.voucher;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.dto.SimpleComposeDateDTO;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;

public abstract class AbstractVoucherDataLoader
extends AbstractFinBizDataModelLoader<BalanceCondition, FetchData> {
    public String getBizDataModelCode() {
        return BizDataModelEnum.VOUCHERMODEL.getCode();
    }

    @Override
    public abstract FetchFloatRowResult simpleFloatQuery(BalanceCondition var1, SimpleComposeDateDTO var2);

    @Override
    public abstract FetchFloatRowResult seniorFloatQuery(BalanceCondition var1, String var2);
}

