/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.event.GcCalcTaskEvent
 */
package com.jiuqi.gcreport.calculate.listener;

import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.event.GcCalcTaskEvent;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GcCalcTaskEventListener
implements ApplicationListener<GcCalcTaskEvent> {
    @Autowired
    private GcCalcService gcCalcService;

    @Override
    public void onApplicationEvent(GcCalcTaskEvent event) {
        GcCalcEnvContextImpl env = event.getEnv();
        this.gcCalcService.calc(env);
    }
}

