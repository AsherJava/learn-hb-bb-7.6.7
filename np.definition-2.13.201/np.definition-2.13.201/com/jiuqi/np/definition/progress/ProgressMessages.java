/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.progress;

public enum ProgressMessages {
    ANALYSISTABLE("\u6b63\u5728\u5206\u6790\u5b58\u50a8\u8868", 20, 0),
    RESTRUCTURETABLE("\u6b63\u5728\u91cd\u6784\u5b58\u50a8\u8868", 60, 20),
    DEPLOYTABLE("\u6b63\u5728\u53d1\u5e03\u5b58\u50a8\u8868", 10, 80),
    DEPLOYENTITYVIEW("\u6b63\u5728\u53d1\u5e03\u5b9e\u4f53\u89c6\u56fe", 10, 90);

    private String message;
    private int stepCount;
    private int currentProgress;

    private ProgressMessages(String message, int stepCount, int currentProgress) {
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

