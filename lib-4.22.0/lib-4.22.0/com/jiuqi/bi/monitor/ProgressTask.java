/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.monitor;

final class ProgressTask {
    private String name;
    private int stepCount;
    private int[] steps;
    private int curStep;
    private double start;
    private double total;

    public ProgressTask(ProgressTask parent, String name, int stepCount) {
        this(parent, name, stepCount, null);
    }

    public ProgressTask(ProgressTask parent, String name, int[] steps) {
        this(parent, name, steps.length, steps);
    }

    private ProgressTask(ProgressTask parent, String name, int stepCount, int[] steps) {
        this.name = name;
        this.stepCount = stepCount;
        this.steps = steps;
        this.curStep = 0;
        if (parent == null) {
            this.start = 0.0;
            this.total = 1.0;
        } else {
            this.start = parent.position();
            this.total = parent.total() * parent.currentRate();
        }
    }

    public String name() {
        return this.name;
    }

    public void stepIn() {
        if (this.curStep < this.stepCount) {
            ++this.curStep;
        }
    }

    public double total() {
        return this.total;
    }

    public double position() {
        if (this.steps == null) {
            return this.start + (double)this.curStep / (double)this.stepCount * this.total;
        }
        int all = 0;
        for (int i = 0; i < this.stepCount; ++i) {
            all += this.steps[i];
        }
        int established = 0;
        for (int i = 0; i < this.curStep; ++i) {
            established += this.steps[i];
        }
        if (all == 0) {
            return this.start;
        }
        return this.start + (double)established / (double)all * this.total;
    }

    public double currentRate() {
        if (this.steps == null) {
            return 1.0 / (double)this.stepCount;
        }
        if (this.curStep >= this.stepCount) {
            return 0.0;
        }
        int all = 0;
        for (int i = 0; i < this.stepCount; ++i) {
            all += this.steps[i];
        }
        if (all == 0) {
            return 0.0;
        }
        return (double)this.steps[this.curStep] / (double)all;
    }

    public String toString() {
        return this.name;
    }
}

