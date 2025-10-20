/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.sap.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.sap.penetrate.detailledger.AbstractSapDetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class SapCflDetailLedgerContentProvider
extends AbstractSapDetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.CFLBALANCE.getCode();
    }
}

