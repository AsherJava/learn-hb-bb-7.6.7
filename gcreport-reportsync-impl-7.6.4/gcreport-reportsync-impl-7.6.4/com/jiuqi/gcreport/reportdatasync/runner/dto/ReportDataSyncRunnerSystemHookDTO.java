/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.runner.dto;

public class ReportDataSyncRunnerSystemHookDTO {
    private String hookName;
    private String hookTitle;
    private String HookDescription;

    public String getHookName() {
        return this.hookName;
    }

    public void setHookName(String hookName) {
        this.hookName = hookName;
    }

    public String getHookTitle() {
        return this.hookTitle;
    }

    public void setHookTitle(String hookTitle) {
        this.hookTitle = hookTitle;
    }

    public String getHookDescription() {
        return this.HookDescription;
    }

    public void setHookDescription(String hookDescription) {
        this.HookDescription = hookDescription;
    }
}

