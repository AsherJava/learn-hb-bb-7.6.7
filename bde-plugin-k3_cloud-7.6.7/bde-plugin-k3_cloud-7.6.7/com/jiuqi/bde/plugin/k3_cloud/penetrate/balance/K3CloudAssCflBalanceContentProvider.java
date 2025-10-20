/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.k3_cloud.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.k3_cloud.penetrate.balance.AbstractK3CloudCflBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class K3CloudAssCflBalanceContentProvider
extends AbstractK3CloudCflBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSCFLBALANCE.getCode();
    }
}

