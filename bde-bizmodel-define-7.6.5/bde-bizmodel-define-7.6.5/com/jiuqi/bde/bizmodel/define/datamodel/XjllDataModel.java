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

public class XjllDataModel
extends AbstractFinBizDataModel {
    public String getCode() {
        return BizDataModelEnum.XJLLMODEL.getCode();
    }

    public String getName() {
        return BizDataModelEnum.XJLLMODEL.getName();
    }

    public int getOrder() {
        return 30;
    }

    public String getEffectScope() {
        return MemoryBalanceTypeEnum.XJLLBALANCE.getCode();
    }
}

