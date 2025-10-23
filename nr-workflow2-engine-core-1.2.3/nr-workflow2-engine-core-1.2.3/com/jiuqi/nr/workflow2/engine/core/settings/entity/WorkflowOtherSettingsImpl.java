/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl;

public class WorkflowOtherSettingsImpl
implements WorkflowOtherSettings {
    private FillInStartTimeConfig fillInStartTimeConfig;
    private FillInEndTimeConfig fillInEndTimeConfig;
    private ManualTerminationConfig manualTerminationConfig;
    private WorkflowSelfControl workflowSelfControl;

    @Override
    public FillInStartTimeConfig getFillInStartTimeConfig() {
        return this.fillInStartTimeConfig;
    }

    public void setFillInStartTimeConfig(FillInStartTimeConfig fillInStartTimeConfig) {
        this.fillInStartTimeConfig = fillInStartTimeConfig;
    }

    @Override
    public FillInEndTimeConfig getFillInEndTimeConfig() {
        return this.fillInEndTimeConfig;
    }

    public void setFillInEndTimeConfig(FillInEndTimeConfig fillInEndTimeConfig) {
        this.fillInEndTimeConfig = fillInEndTimeConfig;
    }

    @Override
    public ManualTerminationConfig getManualTerminationConfig() {
        return this.manualTerminationConfig;
    }

    public void setManualTerminationConfig(ManualTerminationConfig manualTerminationConfig) {
        this.manualTerminationConfig = manualTerminationConfig;
    }

    @Override
    public WorkflowSelfControl getWorkflowSelfControl() {
        return this.workflowSelfControl;
    }

    public void setWorkflowSelfControl(WorkflowSelfControl workflowSelfControl) {
        this.workflowSelfControl = workflowSelfControl;
    }
}

