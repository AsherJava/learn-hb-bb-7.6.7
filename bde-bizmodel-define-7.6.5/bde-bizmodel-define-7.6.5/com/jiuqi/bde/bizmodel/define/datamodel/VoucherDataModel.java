/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 */
package com.jiuqi.bde.bizmodel.define.datamodel;

import com.jiuqi.bde.bizmodel.define.datamodel.AbstractFinBizDataModel;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class VoucherDataModel
extends AbstractFinBizDataModel {
    public String getCode() {
        return BizDataModelEnum.VOUCHERMODEL.getCode();
    }

    public String getName() {
        return BizDataModelEnum.VOUCHERMODEL.getName();
    }

    public int getOrder() {
        return 40;
    }

    public String getEffectScope() {
        return MemoryBalanceTypeEnum.VOUCHER.getCode();
    }
}

