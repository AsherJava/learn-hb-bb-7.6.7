/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.vo;

import java.util.List;

public class MCExecuteParamVO {
    private boolean showTask;
    private boolean showScheme;
    private List<String> tasks;
    private String task;
    private String period;
    private String mcScheme;
    private String appointScheme;
    private String org;

    public boolean isShowTask() {
        return this.showTask;
    }

    public void setShowTask(boolean showTask) {
        this.showTask = showTask;
    }

    public boolean isShowScheme() {
        return this.showScheme;
    }

    public void setShowScheme(boolean showScheme) {
        this.showScheme = showScheme;
    }

    public List<String> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMcScheme() {
        return this.mcScheme;
    }

    public void setMcScheme(String mcScheme) {
        this.mcScheme = mcScheme;
    }

    public String getAppointScheme() {
        return this.appointScheme;
    }

    public void setAppointScheme(String appointScheme) {
        this.appointScheme = appointScheme;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}

