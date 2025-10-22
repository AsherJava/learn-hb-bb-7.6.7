/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;

public class ProcessActorStrategy
implements ActorStrategyInstance {
    private String type;
    private String parameterJson;

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getParameterJson() {
        return this.parameterJson;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParameterJson(String parameterJson) {
        this.parameterJson = parameterJson;
    }
}

