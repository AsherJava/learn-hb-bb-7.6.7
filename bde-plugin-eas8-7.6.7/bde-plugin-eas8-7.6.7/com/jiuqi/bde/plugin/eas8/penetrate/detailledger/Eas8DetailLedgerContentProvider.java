/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.eas8.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.eas8.penetrate.detailledger.AbstractEas8DetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class Eas8DetailLedgerContentProvider
extends AbstractEas8DetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

