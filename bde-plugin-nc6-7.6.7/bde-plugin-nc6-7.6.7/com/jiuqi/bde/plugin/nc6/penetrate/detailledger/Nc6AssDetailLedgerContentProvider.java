/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.nc6.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.nc6.penetrate.detailledger.AbstractNc6DetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Nc6AssDetailLedgerContentProvider
extends AbstractNc6DetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSBALANCE.getCode();
    }
}

