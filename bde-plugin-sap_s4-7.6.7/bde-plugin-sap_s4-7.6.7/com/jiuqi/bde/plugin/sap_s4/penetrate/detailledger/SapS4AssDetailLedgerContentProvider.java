/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.sap_s4.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.sap_s4.penetrate.detailledger.AbstractSapS4DetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class SapS4AssDetailLedgerContentProvider
extends AbstractSapS4DetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSBALANCE.getCode();
    }
}

