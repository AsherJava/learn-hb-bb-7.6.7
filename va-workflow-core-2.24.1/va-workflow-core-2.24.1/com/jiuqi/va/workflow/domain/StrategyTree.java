/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.StrategyDTO
 *  com.jiuqi.va.domain.workflow.StrategyModule
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.domain.biz.StrategyDTO;
import com.jiuqi.va.domain.workflow.StrategyModule;
import java.util.List;

public class StrategyTree {
    private StrategyModule strategyModule;
    private List<StrategyDTO> strategys;

    public StrategyModule getStrategyModule() {
        return this.strategyModule;
    }

    public void setStrategyModule(StrategyModule strategyModule) {
        this.strategyModule = strategyModule;
    }

    public List<StrategyDTO> getStrategys() {
        return this.strategys;
    }

    public void setStrategys(List<StrategyDTO> strategys) {
        this.strategys = strategys;
    }
}

