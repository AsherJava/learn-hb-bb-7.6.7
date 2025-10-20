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

public class AssBalanceDataModel
extends AbstractFinBizDataModel {
    public String getCode() {
        return BizDataModelEnum.ASSBALANCEMODEL.getCode();
    }

    public String getName() {
        return BizDataModelEnum.ASSBALANCEMODEL.getName();
    }

    public int getOrder() {
        return 20;
    }

    public String getEffectScope() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getCode();
    }
}

