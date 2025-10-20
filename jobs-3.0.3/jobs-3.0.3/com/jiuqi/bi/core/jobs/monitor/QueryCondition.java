/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.monitor;

import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.core.jobs.monitor.State;

public class QueryCondition {
    private int start;
    private int end;
    private String[] categoryIds;
    private String[] jobIds;
    private JobType[] jobTypes;
    private State[] status;

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String[] getCategoryIds() {
        return this.categoryIds;
    }

    public void setCategoryIds(String[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String[] getJobIds() {
        return this.jobIds;
    }

    public void setJobIds(String[] jobIds) {
        this.jobIds = jobIds;
    }

    public JobType[] getJobTypes() {
        return this.jobTypes;
    }

    public void setJobTypes(JobType[] jobTypes) {
        this.jobTypes = jobTypes;
    }

    public State[] getStatus() {
        return this.status;
    }

    public void setStatus(State[] status) {
        this.status = status;
    }
}

