/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.bip_flagship.pentrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.bip_flagship.pentrate.detailledger.AbstractBipFlagDetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class BipFlagAssCflDetailLedgerContentProvider
extends AbstractBipFlagDetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSCFLBALANCE.getCode();
    }
}

