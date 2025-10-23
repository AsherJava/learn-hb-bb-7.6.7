/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 */
package com.jiuqi.nr.multcheck2.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MCRealTimeTaskMonitor
extends RealTimeTaskMonitor {
    private static final Logger logger = LoggerFactory.getLogger(MCRealTimeTaskMonitor.class);
    private JobContext jobContext1;

    public MCRealTimeTaskMonitor(String taskId, String taskPoolType, JobContext jobContext) {
        super(taskId, taskPoolType, jobContext);
        this.jobContext1 = jobContext;
    }

    public void progressAndMessage(double progress, String message) {
        if (StringUtils.hasText(message)) {
            // empty if block
        }
    }

    public boolean isCancel() {
        if (this.jobContext1 == null) {
            return false;
        }
        return super.isCancel();
    }

    public void finish(String result, Object detail) {
    }

    public void finished(String result, Object detail) {
    }

    public void canceling(String result, Object detail) {
    }

    public void canceled(String result, Object detail) {
    }

    public void error(String cause, Throwable t) {
    }

    public void error(String result, Throwable t, String detail) {
    }
}

