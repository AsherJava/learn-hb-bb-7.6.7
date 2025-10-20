/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.dimension.internal.event;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import org.springframework.context.ApplicationEvent;

public class DimensionPublishedEvent
extends ApplicationEvent {
    public DimensionPublishedEvent(DimensionPublishInfo dimensionPublishInfo, NpContext context) {
        super(dimensionPublishInfo);
        NpContextHolder.setContext((NpContext)context);
    }

    public static class DimensionPublishInfo {
    }
}

