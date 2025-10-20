/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.calculate.event;

import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import org.springframework.context.ApplicationEvent;

public class GcCalcTaskEvent
extends ApplicationEvent {
    private GcCalcEnvContextImpl env;

    public GcCalcTaskEvent(Object source, GcCalcEnvContextImpl env) {
        super(source);
        this.env = env;
    }

    public GcCalcEnvContextImpl getEnv() {
        return this.env;
    }

    public void setEnv(GcCalcEnvContextImpl env) {
        this.env = env;
    }
}

