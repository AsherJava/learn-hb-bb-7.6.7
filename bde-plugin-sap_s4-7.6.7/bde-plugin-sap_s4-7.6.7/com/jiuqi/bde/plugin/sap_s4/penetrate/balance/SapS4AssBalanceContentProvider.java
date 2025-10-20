/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.sap_s4.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.sap_s4.penetrate.balance.AbstractSapS4BalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class SapS4AssBalanceContentProvider
extends AbstractSapS4BalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSBALANCE.getCode();
    }
}

