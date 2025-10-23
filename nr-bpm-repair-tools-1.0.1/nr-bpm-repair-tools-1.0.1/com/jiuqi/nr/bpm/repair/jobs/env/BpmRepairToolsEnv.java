/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bpm.repair.jobs.env;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class BpmRepairToolsEnv {
    private String period;
    private TaskDefine taskDefine;
    private FormSchemeDefine formScheme;
    private TaskFlowsDefine flowsSetting;
    private TableModelDefine stateTableDefine;
    private TableModelDefine stateHiTableDefine;

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(FormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }

    public TaskFlowsDefine getFlowsSetting() {
        return this.flowsSetting;
    }

    public void setFlowsSetting(TaskFlowsDefine flowsSetting) {
        this.flowsSetting = flowsSetting;
    }

    public TableModelDefine getStateTableDefine() {
        return this.stateTableDefine;
    }

    public void setStateTableDefine(TableModelDefine stateTableDefine) {
        this.stateTableDefine = stateTableDefine;
    }

    public TableModelDefine getStateHiTableDefine() {
        return this.stateHiTableDefine;
    }

    public void setStateHiTableDefine(TableModelDefine stateHiTableDefine) {
        this.stateHiTableDefine = stateHiTableDefine;
    }
}

