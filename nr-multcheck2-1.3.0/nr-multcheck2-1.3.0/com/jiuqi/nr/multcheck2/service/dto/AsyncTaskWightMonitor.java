/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 */
package com.jiuqi.nr.multcheck2.service.dto;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;

public class AsyncTaskWightMonitor
extends RealTimeTaskMonitor {
    private double begin;
    private double wight;
    private JobContext job;

    public AsyncTaskWightMonitor(String taskId, String taskPoolType, JobContext jobContext, double begin, double wight) {
        super(taskId, taskPoolType, jobContext);
        this.job = jobContext;
        this.begin = begin;
        this.wight = wight;
    }

    public double getBegin() {
        return this.begin;
    }

    public double getWight() {
        return this.wight;
    }

    public JobContext getJob() {
        return this.job;
    }
}

