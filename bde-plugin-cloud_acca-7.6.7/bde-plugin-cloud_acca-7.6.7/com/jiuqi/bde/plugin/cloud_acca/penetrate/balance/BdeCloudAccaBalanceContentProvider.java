/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.cloud_acca.penetrate.balance.AbstractBdeCloudAccaBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class BdeCloudAccaBalanceContentProvider
extends AbstractBdeCloudAccaBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

