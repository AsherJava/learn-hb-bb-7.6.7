/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;

public class ActorStrategyInstanceImpl
implements ActorStrategyInstance {
    private String type;
    private String parameterJson;

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getParameterJson() {
        return this.parameterJson;
    }

    public void setParameterJson(String parameterJson) {
        this.parameterJson = parameterJson;
    }
}

