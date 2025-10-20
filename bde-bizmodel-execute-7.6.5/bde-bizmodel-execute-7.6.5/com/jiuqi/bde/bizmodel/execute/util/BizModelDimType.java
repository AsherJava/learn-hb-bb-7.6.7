/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.bde.bizmodel.execute.util;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;

public enum BizModelDimType implements IDimType
{
    BALANCE(ComputationModelEnum.BALANCE),
    ASSBALANCE(ComputationModelEnum.ASSBALANCE),
    CFLBALANCE(ComputationModelEnum.CFLBALANCE),
    ASSCFLBALANCE(ComputationModelEnum.ASSCFLBALANCE),
    DJYEBALANCE(ComputationModelEnum.DJYEBALANCE),
    ASSRECLASSIFYBALANCE(ComputationModelEnum.ASSRECLASSIFYBALANCE),
    AGINGBALANCE(ComputationModelEnum.AGINGBALANCE),
    ASSAGINGBALANCE(ComputationModelEnum.ASSAGINGBALANCE),
    XJLLBALANCE(ComputationModelEnum.XJLLBALANCE),
    TFV(ComputationModelEnum.TFV),
    BASEDATA(ComputationModelEnum.BASEDATA),
    CEDXBALANCE(ComputationModelEnum.CEDXBALANCE),
    VOUCHER(ComputationModelEnum.VOUCHER),
    FORMULA(ComputationModelEnum.FORMULA),
    CUSTOMFETCH(ComputationModelEnum.CUSTOMFETCH),
    FINANCIALASSAGINGBALANCE(ComputationModelEnum.FINANCIALASSAGINGBALANCE),
    INVEST_BILL(ComputationModelEnum.INVEST_BILL);

    private final ComputationModelEnum bizModel;

    private BizModelDimType(ComputationModelEnum bizModel) {
        this.bizModel = bizModel;
    }

    public ComputationModelEnum getBizModel() {
        return this.bizModel;
    }

    public String getTitle() {
        return this.bizModel.getName();
    }

    public String getName() {
        return this.bizModel.getCode();
    }

    public static BizModelDimType getEnumByName(String name) {
        if (StringUtils.isEmpty((String)name)) {
            return null;
        }
        for (BizModelDimType dimType : BizModelDimType.values()) {
            if (!dimType.getName().equals(name.toUpperCase())) continue;
            return dimType;
        }
        return null;
    }
}

