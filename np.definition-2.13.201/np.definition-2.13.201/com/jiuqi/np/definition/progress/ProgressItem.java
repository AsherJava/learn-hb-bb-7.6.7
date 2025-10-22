/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.progress;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgressItem
implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ProgressItem.class);
    private static final long serialVersionUID = -6899827821041667916L;
    private String progressId;
    private List<String> stepTitles = new ArrayList<String>();
    private int currentStep = 0;
    private int currentProgess = 0;
    private String message;
    private boolean isFailed;
    private boolean isFinished;
    private List<String> warnList = new ArrayList<String>();

    public String getProgressId() {
        return this.progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public int getCurrentProgess() {
        return this.currentProgess;
    }

    public void setCurrentProgess(int currentProgess) {
        this.currentProgess = currentProgess;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void addStepTitle(String stepTitle) {
        this.stepTitles.add(stepTitle);
    }

    public List<String> getStepTitles() {
        return this.stepTitles;
    }

    public void setStepTitles(List<String> stepTitles) {
        this.stepTitles = stepTitles;
    }

    public int getCurrentStep() {
        return this.currentStep;
    }

    public void nextStep() {
        if (this.currentStep >= this.stepTitles.size() || this.isFinished) {
            this.isFinished = true;
            return;
        }
        ++this.currentStep;
        this.currentProgess = 0;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public boolean isFailed() {
        return this.isFailed;
    }

    public void setFailed(boolean isFailed) {
        this.isFailed = isFailed;
    }

    public void markException(Exception ex) {
        this.isFailed = true;
        this.isFinished = true;
        this.message = ex.getMessage();
        logger.error(ex.getMessage(), ex);
    }

    public void finish() {
        this.isFailed = false;
        this.isFinished = true;
        this.currentStep = this.stepTitles.size();
        this.currentProgess = 100;
    }

    public List<String> getWarnList() {
        return this.warnList;
    }

    public void setWarnList(List<String> warnList) {
        this.warnList = warnList;
    }
}

