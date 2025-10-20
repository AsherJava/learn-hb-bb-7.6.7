/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.nc6.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.nc6.penetrate.balance.AbstractNc6CflBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Nc6CflBalanceContentProvider
extends AbstractNc6CflBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.CFLBALANCE.getCode();
    }
}

