/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;

public class ParticipantItemImpl
implements ParticipantItem {
    private String strategy;
    private String param;

    @Override
    public String getStrategy() {
        return this.strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}

