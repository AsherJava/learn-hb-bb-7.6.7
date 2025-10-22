/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.gather.refactor.provider;

import com.jiuqi.nr.data.gather.refactor.provider.NodeGatherOptionProvider;
import org.springframework.stereotype.Component;

@Component
public class DefaultNodeGatherOptionProvider
implements NodeGatherOptionProvider {
    @Override
    public boolean isIgnoreWorkFlow() {
        return false;
    }
}

