/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.k3_cloud.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.k3_cloud.penetrate.balance.AbstractK3CloudBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class K3CloudAssBalanceContentProvider
extends AbstractK3CloudBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSBALANCE.getCode();
    }
}

