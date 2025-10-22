/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm;

import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.RunTimeService;

public abstract class MultiTenantProcessEngine
implements ProcessEngine {
    protected static final String DEFAULT_TENANT = "__default_tenant__";

    protected abstract ProcessEngine getCurrentProcessEngine();

    @Override
    public DeployService getDeployService() {
        return this.getCurrentProcessEngine().getDeployService();
    }

    @Override
    public RunTimeService getRunTimeService() {
        return this.getCurrentProcessEngine().getRunTimeService();
    }

    @Override
    public HistoryService getHistoryService() {
        return this.getCurrentProcessEngine().getHistoryService();
    }

    @Override
    public ProcessEngine setProcessType(ProcessType processType) {
        return this.getCurrentProcessEngine().setProcessType(processType);
    }
}

