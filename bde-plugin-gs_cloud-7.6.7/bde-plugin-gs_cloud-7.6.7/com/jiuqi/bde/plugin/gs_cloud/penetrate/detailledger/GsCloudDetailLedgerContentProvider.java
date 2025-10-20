/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.gs_cloud.penetrate.detailledger;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.gs_cloud.penetrate.detailledger.AbstractGsCloudDetailLedgerContentProvider;
import org.springframework.stereotype.Component;

@Component
public class GsCloudDetailLedgerContentProvider
extends AbstractGsCloudDetailLedgerContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

