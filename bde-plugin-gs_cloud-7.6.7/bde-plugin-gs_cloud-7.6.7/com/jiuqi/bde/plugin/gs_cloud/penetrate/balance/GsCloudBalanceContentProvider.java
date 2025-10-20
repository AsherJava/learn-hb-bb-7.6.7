/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.plugin.gs_cloud.penetrate.balance;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.plugin.gs_cloud.penetrate.balance.AbstractGsCloudBalanceContentProvider;
import org.springframework.stereotype.Component;

@Component
public class GsCloudBalanceContentProvider
extends AbstractGsCloudBalanceContentProvider {
    public String getBizModel() {
        return ComputationModelEnum.BALANCE.getCode();
    }
}

