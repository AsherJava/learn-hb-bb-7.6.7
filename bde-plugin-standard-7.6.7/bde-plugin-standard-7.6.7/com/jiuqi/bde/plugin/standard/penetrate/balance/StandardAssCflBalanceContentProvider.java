/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.standard.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.standard.penetrate.balance.AbstractStandardCflBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class StandardAssCflBalanceContentProvider
extends AbstractStandardCflBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSCFLBALANCE.getCode();
    }
}

