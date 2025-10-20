/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 */
package com.jiuqi.gcreport.datatrace.datatracer;

import com.jiuqi.gcreport.datatrace.context.GcDataTracerContext;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;

public interface GcDataTracer {
    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTracerContext var1);

    public boolean isMatch(GcDataTracerContext var1);

    default public int order() {
        return 0;
    }
}

