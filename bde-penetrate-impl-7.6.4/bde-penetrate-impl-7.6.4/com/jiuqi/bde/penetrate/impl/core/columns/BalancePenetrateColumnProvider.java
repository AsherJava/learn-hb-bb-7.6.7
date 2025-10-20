/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.penetrate.impl.core.columns;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.columns.AbstractBalancePenetrateColumnBuilder;
import org.springframework.stereotype.Component;

@Component
public class BalancePenetrateColumnProvider
extends AbstractBalancePenetrateColumnBuilder {
    @Override
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }

    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }
}

