/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.k3.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.k3.penetrate.detailledger.AbstractK3DetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class K3DetailLedgerContentProvider
extends AbstractK3DetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

