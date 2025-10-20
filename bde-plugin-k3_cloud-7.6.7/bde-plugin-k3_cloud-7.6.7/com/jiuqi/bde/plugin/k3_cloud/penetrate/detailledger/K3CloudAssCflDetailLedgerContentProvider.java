/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.k3_cloud.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.k3_cloud.penetrate.detailledger.AbstractK3CloudDetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class K3CloudAssCflDetailLedgerContentProvider
extends AbstractK3CloudDetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.ASSCFLBALANCE.getCode();
    }
}

