/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.nbrj_n9.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.nbrj_n9.penetrate.detailledger.AbstractNbrjN9DetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class NbrjN9DetailLedgerContentProvider
extends AbstractNbrjN9DetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

