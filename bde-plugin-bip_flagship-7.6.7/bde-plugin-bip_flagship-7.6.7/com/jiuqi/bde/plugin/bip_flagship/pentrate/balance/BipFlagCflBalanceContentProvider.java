/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.bip_flagship.pentrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.bip_flagship.pentrate.balance.AbstractBipFlagCflBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class BipFlagCflBalanceContentProvider
extends AbstractBipFlagCflBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.CFLBALANCE.getCode();
    }
}

