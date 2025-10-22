/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.bpm.setting.pojo;

import com.jiuqi.nr.bpm.setting.pojo.CustomPeriodData;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;

public class BaseData {
    private TaskDefine taskDefine;
    private FormSchemeDefine formSchemeDefine;
    private String workflowTitle;
    private String commitType;
    private List<CustomPeriodData> customPeriodDataList;
    private String period;
    private String fromPeriod;
    private String toPeriod;
    private String entityKey;

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public String getCommitType() {
        return this.commitType;
    }

    public void setCommitType(String commitType) {
        this.commitType = commitType;
    }

    public List<CustomPeriodData> getCustomPeriodDataList() {
        return this.customPeriodDataList;
    }

    public void setCustomPeriodDataList(List<CustomPeriodData> customPeriodDataList) {
        this.customPeriodDataList = customPeriodDataList;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getWorkflowTitle() {
        return this.workflowTitle;
    }

    public void setWorkflowTitle(String workflowTitle) {
        this.workflowTitle = workflowTitle;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }
}

