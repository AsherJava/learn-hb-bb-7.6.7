/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.standard.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.standard.penetrate.detailledger.AbstractStandardDetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class StandardAssDetailLedgerContentProvider
extends AbstractStandardDetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSBALANCE.getCode();
    }
}

