/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.web.facade.StepVO
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.fix.web.facade;

import com.jiuqi.nr.datascheme.web.facade.StepVO;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.ArrayList;
import java.util.List;

public class ProgressVO {
    private String progressId;
    private String message;
    private List<StepVO> infos;
    private int curStep;
    private boolean fail = false;
    private boolean over = false;

    public ProgressVO() {
    }

    private void initSteps() {
        this.infos = new ArrayList<StepVO>();
        StepVO step = new StepVO();
        step.setTitle("\u5f00\u59cb");
        step.setMessage("\u5f00\u59cb");
        this.infos.add(step);
    }

    private void addLastSuccessStep() {
        StepVO step = new StepVO();
        step.setTitle("\u4fee\u590d\u6210\u529f");
        step.setMessage("\u4fee\u590d\u6210\u529f");
        if (this.over) {
            step.setPercentage(100.0);
        }
        this.infos.add(step);
    }

    private void addLastFailStep() {
        StepVO step = new StepVO();
        step.setTitle("\u4fee\u590d\u5931\u8d25");
        step.setMessage("\u4fee\u590d\u5931\u8d25");
        if (this.over) {
            step.setPercentage(100.0);
        }
        this.infos.add(step);
    }

    public ProgressVO(ProgressItem progress) {
        if (progress != null) {
            this.initSteps();
            this.progressId = progress.getProgressId();
            this.message = progress.getMessage();
            this.curStep = progress.getCurrentStep() + 1;
            this.over = progress.isFinished();
            this.fail = progress.isFailed();
            List titles = progress.getStepTitles();
            if (titles != null) {
                for (int i = 0; i < titles.size(); ++i) {
                    StepVO step = new StepVO();
                    String title = (String)titles.get(i);
                    step.setTitle(title);
                    if (i == progress.getCurrentStep()) {
                        step.setMessage(this.message);
                        step.setPercentage((double)progress.getCurrentProgess());
                        step.setFail(this.fail);
                    } else if (i < progress.getCurrentStep()) {
                        step.setMessage(title + "\u6210\u529f!");
                        step.setPercentage(100.0);
                    } else {
                        step.setMessage(title);
                    }
                    this.infos.add(step);
                }
            }
            if (this.fail) {
                this.addLastFailStep();
            } else {
                this.addLastSuccessStep();
            }
        }
    }

    public String getProgressId() {
        return this.progressId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StepVO> getInfos() {
        return this.infos;
    }

    public void setInfos(List<StepVO> infos) {
        this.infos = infos;
    }

    public int getCurStep() {
        return this.curStep;
    }

    public void setCurStep(int curStep) {
        this.curStep = curStep;
    }

    public boolean isFail() {
        return this.fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public boolean isOver() {
        return this.over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}

