/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan;

import java.util.Set;

public class ChannelParam {
    private String planId;
    private String source;
    private String execUser;
    private String taskId;
    private String formSchemeId;
    private String execUnit;
    private String execPeriod;
    private Set<String> execFormIds;

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExecUser() {
        return this.execUser;
    }

    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getExecUnit() {
        return this.execUnit;
    }

    public void setExecUnit(String execUnit) {
        this.execUnit = execUnit;
    }

    public String getExecPeriod() {
        return this.execPeriod;
    }

    public void setExecPeriod(String execPeriod) {
        this.execPeriod = execPeriod;
    }

    public Set<String> getExecFormIds() {
        return this.execFormIds;
    }

    public void setExecFormIds(Set<String> execFormIds) {
        this.execFormIds = execFormIds;
    }
}

