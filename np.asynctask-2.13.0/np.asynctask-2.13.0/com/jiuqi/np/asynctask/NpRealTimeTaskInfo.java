/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;

public class NpRealTimeTaskInfo {
    private String taskKey;
    private String formSchemeKey;
    private String args;
    private AbstractRealTimeJob abstractRealTimeJob;

    public NpRealTimeTaskInfo() {
    }

    public NpRealTimeTaskInfo(String taskKey, String formSchemeKey, String args) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.args = args;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getArgs() {
        return this.args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public AbstractRealTimeJob getAbstractRealTimeJob() {
        return this.abstractRealTimeJob;
    }

    public void setAbstractRealTimeJob(AbstractRealTimeJob abstractRealTimeJob) {
        this.abstractRealTimeJob = abstractRealTimeJob;
    }
}

