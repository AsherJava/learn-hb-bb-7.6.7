/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.nc5.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.nc5.penetrate.detailledger.AbstractNc5DetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Nc5DetailLedgerContentProvider
extends AbstractNc5DetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

