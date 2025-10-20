/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.logquery.client.vo;

public class ExecuteStateVO {
    private Integer total;
    private Integer failed;
    private Integer success;
    private Integer unExecute;
    private Integer executing;
    private Integer canceled;
    private Boolean hasCancel;

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getFailed() {
        return this.failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getSuccess() {
        return this.success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getUnExecute() {
        return this.unExecute;
    }

    public void setUnExecute(Integer unExecute) {
        this.unExecute = unExecute;
    }

    public Integer getExecuting() {
        return this.executing;
    }

    public void setExecuting(Integer executing) {
        this.executing = executing;
    }

    public Integer getCanceled() {
        return this.canceled;
    }

    public void setCanceled(Integer canceled) {
        this.canceled = canceled;
    }

    public Boolean getHasCancel() {
        return this.hasCancel;
    }

    public void setHasCancel(Boolean hasCancel) {
        this.hasCancel = hasCancel;
    }
}

