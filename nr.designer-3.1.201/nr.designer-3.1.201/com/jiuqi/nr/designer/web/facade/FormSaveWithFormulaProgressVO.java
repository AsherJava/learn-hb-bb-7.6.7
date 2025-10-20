/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.progress.ProgressItem
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.designer.web.facade.vo.StepVO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FormSaveWithFormulaProgressVO {
    private String taskID;
    private UUID progressID;
    private String message;
    private List<StepVO> infos;
    private int curStep;
    private boolean fail = false;
    private boolean over = false;

    public FormSaveWithFormulaProgressVO() {
    }

    private void initSteps() {
        this.infos = new ArrayList<StepVO>();
        StepVO step = new StepVO();
        step.setTitle("\u5f00\u59cb");
        step.setMessage("\u5f00\u59cb");
        this.infos.add(step);
    }

    private void addLastStep() {
        StepVO step = new StepVO();
        step.setTitle("\u7ed3\u675f");
        step.setMessage("\u7ed3\u675f");
        if (this.over) {
            step.setPercentage(100.0);
        }
        this.infos.add(step);
    }

    public FormSaveWithFormulaProgressVO(ProgressItem progress) {
        if (progress != null) {
            this.initSteps();
            this.taskID = progress.getProgressId();
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
                        step.setPercentage(progress.getCurrentProgess());
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
            this.addLastStep();
        }
    }

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public UUID getProgressID() {
        return this.progressID;
    }

    public void setProgressID(UUID progressID) {
        this.progressID = progressID;
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

