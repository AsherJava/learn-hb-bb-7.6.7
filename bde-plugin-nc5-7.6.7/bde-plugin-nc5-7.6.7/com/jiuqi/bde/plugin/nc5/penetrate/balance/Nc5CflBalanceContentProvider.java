/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.nc5.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.nc5.penetrate.balance.AbstractNc5CflBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Nc5CflBalanceContentProvider
extends AbstractNc5CflBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.CFLBALANCE.getCode();
    }
}

