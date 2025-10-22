/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ProgressConsumer
 *  com.jiuqi.nr.definition.common.ProgressConsumer$Progress
 *  com.jiuqi.nr.definition.common.ProgressConsumer$ProgressUpdater
 *  com.jiuqi.nr.definition.common.ProgressConsumer$Step
 */
package com.jiuqi.nr.definition.deploy.common;

import com.jiuqi.nr.definition.common.ProgressConsumer;
import java.util.ArrayList;
import java.util.List;

public class ParamDeployProgressUpdater
implements ProgressConsumer.ProgressUpdater {
    private final ProgressConsumer progressConsumer;
    private final List<ProgressConsumer.Step> steps;
    private int currentStep;
    private int progress;
    private String message;
    private boolean failed;

    private ParamDeployProgressUpdater(ProgressConsumer progressConsumer) {
        this.progressConsumer = progressConsumer;
        this.steps = new ArrayList<ProgressConsumer.Step>();
    }

    public static ParamDeployProgressUpdater newUpdater(ProgressConsumer progressConsumer) {
        ParamDeployProgressUpdater updater = new ParamDeployProgressUpdater(progressConsumer);
        updater.steps.add(new ProgressConsumer.Step("\u5206\u6790\u53c2\u6570", 10));
        updater.steps.add(new ProgressConsumer.Step("\u53d1\u5e03\u6570\u636e\u65b9\u6848", 40));
        updater.steps.add(new ProgressConsumer.Step("\u53d1\u5e03\u62a5\u8868\u53c2\u6570", 30));
        updater.steps.add(new ProgressConsumer.Step("\u53d1\u5e03\u5176\u5b83\u53c2\u6570", 20));
        updater.progressConsumer.accept((ProgressConsumer.Progress)updater);
        return updater;
    }

    public static ParamDeployProgressUpdater newNoDataSchemeUpdater(ProgressConsumer progressConsumer) {
        ParamDeployProgressUpdater updater = new ParamDeployProgressUpdater(progressConsumer);
        updater.steps.add(new ProgressConsumer.Step("\u5206\u6790\u53c2\u6570", 20));
        updater.steps.add(new ProgressConsumer.Step("\u53d1\u5e03\u62a5\u8868\u53c2\u6570", 60));
        updater.steps.add(new ProgressConsumer.Step("\u53d1\u5e03\u5176\u5b83\u53c2\u6570", 20));
        updater.progressConsumer.accept((ProgressConsumer.Progress)updater);
        return updater;
    }

    public void nextStep() {
        if (this.currentStep < this.steps.size()) {
            this.progress = 0;
            this.message = "";
            ++this.currentStep;
        }
        this.progressConsumer.accept((ProgressConsumer.Progress)this);
    }

    public void updateProgress(int progress, String message) {
        this.progress = progress;
        this.message = message;
        this.progressConsumer.accept((ProgressConsumer.Progress)this);
    }

    public void fail(String message) {
        this.failed = true;
        this.message = message;
        this.progressConsumer.accept((ProgressConsumer.Progress)this);
    }

    public void finish(String message) {
        this.currentStep = this.steps.size();
        this.message = message;
        this.progressConsumer.accept((ProgressConsumer.Progress)this);
    }

    public List<ProgressConsumer.Step> getSteps() {
        return this.steps;
    }

    public int getCurrentStep() {
        return this.currentStep;
    }

    public int getProgress() {
        return this.progress;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isFailed() {
        return this.failed;
    }

    public boolean isFinished() {
        return this.currentStep == this.steps.size();
    }
}

