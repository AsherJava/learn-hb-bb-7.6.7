/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class DesignContextVO {
    private String taskKey;
    private boolean enableAccount = true;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public boolean isEnableAccount() {
        return this.enableAccount;
    }

    public void setEnableAccount(boolean enableAccount) {
        this.enableAccount = enableAccount;
    }
}

