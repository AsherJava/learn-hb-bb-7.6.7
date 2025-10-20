/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.k3.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.k3.penetrate.balance.AbstractK3BalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class K3BalanceContentProvider
extends AbstractK3BalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

