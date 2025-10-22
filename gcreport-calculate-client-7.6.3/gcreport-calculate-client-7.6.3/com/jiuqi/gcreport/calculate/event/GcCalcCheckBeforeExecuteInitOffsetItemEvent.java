/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.calculate.event;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import org.springframework.context.ApplicationEvent;

public class GcCalcCheckBeforeExecuteInitOffsetItemEvent
extends ApplicationEvent {
    private GcCalcEnvContext env;

    public GcCalcCheckBeforeExecuteInitOffsetItemEvent(Object source, GcCalcEnvContext env) {
        super(source);
        this.env = env;
    }

    public GcCalcEnvContext getEnv() {
        return this.env;
    }

    public void setEnv(GcCalcEnvContext env) {
        this.env = env;
    }
}

