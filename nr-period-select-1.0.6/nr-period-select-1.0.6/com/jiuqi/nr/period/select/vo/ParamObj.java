/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.vo;

import com.jiuqi.nr.period.select.common.RunType;
import java.util.ArrayList;
import java.util.List;

public class ParamObj {
    private String dataScheme;
    private String taskId;
    private String formScheme;
    private List<String> selectedPeriod = new ArrayList<String>();
    private String adjust;
    private boolean markData = false;
    private RunType runType = RunType.RUNTIME;

    public RunType getRunType() {
        return this.runType;
    }

    public void setRunType(RunType runType) {
        this.runType = runType;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public List<String> getSelectedPeriod() {
        return this.selectedPeriod;
    }

    public void setSelectedPeriod(List<String> selectedPeriod) {
        this.selectedPeriod = selectedPeriod;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public boolean isMarkData() {
        return this.markData;
    }

    public void setMarkData(boolean markData) {
        this.markData = markData;
    }

    public String toString() {
        return "ParamObj{dataScheme='" + this.dataScheme + '\'' + ", taskId='" + this.taskId + '\'' + ", formScheme='" + this.formScheme + '\'' + ", selectedPeriod=" + this.selectedPeriod + ", adjust='" + this.adjust + '\'' + ", markData=" + this.markData + '}';
    }
}

