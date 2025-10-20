/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.nc5.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.nc5.penetrate.balance.AbstractNc5BalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Nc5BalanceContentProvider
extends AbstractNc5BalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

