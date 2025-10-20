/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.nbrj_n9.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.nbrj_n9.penetrate.balance.AbstractNbrjN9aBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class NbrjN9AssBalanceContentProvider
extends AbstractNbrjN9aBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSBALANCE.getCode();
    }
}

