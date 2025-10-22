/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.deploy.progress;

public enum DSProgressMessages {
    ANALYSIS("\u6b63\u5728\u5206\u6790\u6570\u636e\u65b9\u6848", 10, 0),
    INITNVWAMODEL_AND_CHECK("\u6b63\u5728\u6784\u5efa\u5b58\u50a8\u6a21\u578b\u5e76\u68c0\u67e5", 20, 10),
    INITNVWAMODEL("\u6b63\u5728\u6784\u5efa\u5b58\u50a8\u6a21\u578b", 20, 10),
    DEPLOY_DATATABLE("\u6b63\u5728\u53d1\u5e03\u6570\u636e\u8868", 60, 30),
    DEPLOY_CATALOG("\u6b63\u5728\u53d1\u5e03\u6570\u636e\u65b9\u6848", 10, 90);

    private String message;
    private int stepCount;
    private int currentProgress;

    private DSProgressMessages(String message, int stepCount, int currentProgress) {
        this.message = message;
        this.stepCount = stepCount;
        this.currentProgress = currentProgress;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCurrentProgress() {
        return this.currentProgress;
    }

    public int getStepCount() {
        return this.stepCount;
    }
}

