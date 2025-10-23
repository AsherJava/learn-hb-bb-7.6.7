/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

public class StepVO {
    private String stepId;
    private String title;
    private String message;
    private double percentage;
    private boolean fail = false;

    public String getStepId() {
        return this.stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getPercentage() {
        return this.percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public boolean isFail() {
        return this.fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }
}

