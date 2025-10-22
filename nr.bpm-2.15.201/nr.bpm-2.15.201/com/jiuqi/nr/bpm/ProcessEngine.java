/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm;

import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.RunTimeService;

public interface ProcessEngine {
    public ProcessEngineType getType();

    public DeployService getDeployService();

    public RunTimeService getRunTimeService();

    public HistoryService getHistoryService();

    public ProcessEngine setProcessType(ProcessType var1);

    public static enum ProcessEngineType {
        UPLOAD,
        ACTIVITI;

    }
}

