/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.StrategyDTO
 *  com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.domain.biz.StrategyDTO;
import com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule;
import java.util.List;

public class SubProcessBranchStrategyTree {
    private SubProcessBranchStrategyModule subProcessBranchStrategyModule;
    private List<StrategyDTO> strategys;

    public SubProcessBranchStrategyModule getSubProcessBranchStrategyModule() {
        return this.subProcessBranchStrategyModule;
    }

    public void setSubProcessBranchStrategyModule(SubProcessBranchStrategyModule subProcessBranchStrategyModule) {
        this.subProcessBranchStrategyModule = subProcessBranchStrategyModule;
    }

    public List<StrategyDTO> getStrategys() {
        return this.strategys;
    }

    public void setStrategys(List<StrategyDTO> strategys) {
        this.strategys = strategys;
    }
}

