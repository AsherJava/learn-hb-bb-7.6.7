/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.penetrate.impl.core.columns;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.impl.core.columns.AbstractCflBalancePenetrateColumnProvider;
import org.springframework.stereotype.Component;

@Component
public class DjyeBalancePenetrateColumnProvider
extends AbstractCflBalancePenetrateColumnProvider {
    @Override
    public String getBizModel() {
        return ComputationModelEnum.DJYEBALANCE.getCode();
    }
}

