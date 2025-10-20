/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.simpleschedule.bean;

import com.jiuqi.bi.core.jobs.model.JobModel;

public class SimpleJobBean
extends JobModel {
    private String groupId;
    private String executeType;
    private boolean finished;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getExecuteType() {
        return this.executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

