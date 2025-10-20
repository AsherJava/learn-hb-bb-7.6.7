/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.provider;

import com.jiuqi.nvwa.link.provider.ResourceNode;

public class CheckResult {
    private boolean success;
    private String msg;
    private ResourceNode data;

    public CheckResult(boolean success, String msg, ResourceNode data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResourceNode getData() {
        return this.data;
    }

    public void setData(ResourceNode data) {
        this.data = data;
    }
}

