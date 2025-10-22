/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

public class FileMergeReturnInfo {
    private double progress = -1.0;
    private boolean isCompleted = false;

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}

