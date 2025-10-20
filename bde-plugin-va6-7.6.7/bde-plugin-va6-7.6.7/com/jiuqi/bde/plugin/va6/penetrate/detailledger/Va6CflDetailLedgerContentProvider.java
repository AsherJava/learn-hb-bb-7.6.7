/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.va6.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.va6.penetrate.detailledger.AbstractVa6DetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Va6CflDetailLedgerContentProvider
extends AbstractVa6DetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.CFLBALANCE.getCode();
    }
}

