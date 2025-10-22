/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;

public interface TargetDimProviderFactory {
    public TargetDimProvider getTargetDimProvider(SummaryScheme var1);
}

