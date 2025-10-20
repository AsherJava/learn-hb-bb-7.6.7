/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.local;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.AbstractRealTimeJobContext;

public class RealTimeLocalJobContextImpl
extends AbstractRealTimeJobContext {
    public RealTimeLocalJobContextImpl(AbstractRealTimeJob realTimeJob) {
        super(realTimeJob);
    }

    @Override
    public String getFireInstanceId() {
        return null;
    }
}

