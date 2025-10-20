/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 */
package com.jiuqi.bde.bizmodel.define.datamodel;

import com.jiuqi.bde.bizmodel.define.datamodel.AbstractFinBizDataModel;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import org.springframework.stereotype.Component;

@Component
public class BalanceDataModel
extends AbstractFinBizDataModel {
    public String getCode() {
        return BizDataModelEnum.BALANCEMODEL.getCode();
    }

    public String getName() {
        return BizDataModelEnum.BALANCEMODEL.getName();
    }

    public int getOrder() {
        return 10;
    }

    public String getEffectScope() {
        return "";
    }
}

