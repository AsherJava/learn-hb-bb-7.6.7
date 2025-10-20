/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.va6.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.va6.penetrate.balance.AbstractVa6BalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Va6AssBalanceContentProvider
extends AbstractVa6BalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSBALANCE.getCode();
    }
}

