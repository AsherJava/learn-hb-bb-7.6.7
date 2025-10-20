/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.eas8.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.eas8.penetrate.balance.AbstractEas8BalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Eas8AssBalanceContentProvider
extends AbstractEas8BalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSBALANCE.getCode();
    }
}

