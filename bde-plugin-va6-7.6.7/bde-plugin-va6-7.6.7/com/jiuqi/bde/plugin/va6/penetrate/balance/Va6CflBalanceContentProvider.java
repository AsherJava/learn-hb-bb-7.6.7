/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.va6.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.va6.penetrate.balance.AbstractVa6CflBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Va6CflBalanceContentProvider
extends AbstractVa6CflBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.CFLBALANCE.getCode();
    }
}

