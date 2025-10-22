/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 */
package com.jiuqi.gcreport.datatrace.service.impl;

import com.jiuqi.gcreport.datatrace.context.GcDataTracerContext;
import com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer;
import com.jiuqi.gcreport.datatrace.service.GcDataTracerService;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcDataTracerServiceImpl
implements GcDataTracerService {
    @Autowired(required=false)
    private List<GcDataTracer> gcDataTracers = Collections.emptyList();

    @Override
    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTracerContext context) {
        Collections.sort(this.gcDataTracers, Comparator.comparing(o -> o.order()));
        Optional<GcDataTracer> gcDataTracerOptional = this.gcDataTracers.stream().filter(gcDataTracer -> gcDataTracer.isMatch(context)).findFirst();
        if (!gcDataTracerOptional.isPresent()) {
            return null;
        }
        GcDataTracer gcDataTracer2 = gcDataTracerOptional.get();
        GcDataTraceCondi gcDataTraceCondi = gcDataTracer2.queryGcDataTraceCondi(context);
        return gcDataTraceCondi;
    }
}

