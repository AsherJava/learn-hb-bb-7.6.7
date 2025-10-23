/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.vo.TaskParamVO;

public class SummarySolutionModelForm {
    private SummarySolutionModel model;
    private String mainTaskTitle;
    private TaskParamVO taskParam;
    private boolean deployed;
    private boolean hasDataInTable;

    public SummarySolutionModel getModel() {
        return this.model;
    }

    public void setModel(SummarySolutionModel model) {
        this.model = model;
    }

    public String getMainTaskTitle() {
        return this.mainTaskTitle;
    }

    public void setMainTaskTitle(String mainTaskTitle) {
        this.mainTaskTitle = mainTaskTitle;
    }

    public TaskParamVO getTaskParam() {
        return this.taskParam;
    }

    public void setTaskParam(TaskParamVO taskParam) {
        this.taskParam = taskParam;
    }

    public boolean isDeployed() {
        return this.deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    public boolean isHasDataInTable() {
        return this.hasDataInTable;
    }

    public void setHasDataInTable(boolean hasDataInTable) {
        this.hasDataInTable = hasDataInTable;
    }
}

