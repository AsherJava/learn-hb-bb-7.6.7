/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.ProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.service.execute.runtime.ProcessAsyncMonitor$Type
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.IProcessStartupAsyncMonitor;
import com.jiuqi.nr.workflow2.schedule.enumeration.WFSTriggerStatus;
import com.jiuqi.nr.workflow2.service.execute.runtime.ProcessAsyncMonitor;
import org.slf4j.Logger;

public class ProcessStartupAsyncMonitor
extends ProcessAsyncMonitor
implements IProcessStartupAsyncMonitor {
    private WFSTriggerStatus status = WFSTriggerStatus.SUCCESS;

    public ProcessStartupAsyncMonitor(JobContext jobContext, Logger logger, int weight) {
        super(jobContext, logger, weight);
    }

    protected String newActiveMessage(ProcessAsyncMonitor.Type type, String message) {
        return type.showText + message;
    }

    protected String newResultMessage(AsyncJobResult jobResult, String message) {
        return jobResult.name + message;
    }

    @Override
    public void setExecuteResult(AsyncJobResult jobResult, String resultMessage) {
        if (AsyncJobResult.FAILURE == jobResult || AsyncJobResult.EXCEPTION == jobResult) {
            this.status = WFSTriggerStatus.FAILED;
        }
    }

    @Override
    public void setExecuteResult(AsyncJobResult jobResult, String resultMessage, Object detail) {
        this.setExecuteResult(jobResult, resultMessage);
    }

    public WFSTriggerStatus getStatus() {
        return this.status;
    }
}

