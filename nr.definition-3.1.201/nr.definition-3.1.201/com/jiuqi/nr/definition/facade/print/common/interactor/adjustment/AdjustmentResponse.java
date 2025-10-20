/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print.common.interactor.adjustment;

public class AdjustmentResponse {
    private boolean isAdjustment = true;
    private String msg;

    public boolean isAdjustment() {
        return this.isAdjustment;
    }

    public void setAdjustment(boolean isAdjustment) {
        this.isAdjustment = isAdjustment;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

